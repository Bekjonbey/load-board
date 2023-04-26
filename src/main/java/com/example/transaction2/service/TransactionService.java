package com.example.transaction2.service;

import com.example.transaction2.entity.*;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.payload.TransactionDTO;
import com.example.transaction2.repository.*;
import com.example.transaction2.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final RateRepository rateRepository;
    private final LoadRepository loadRepository;
    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ApiResult<UUID> add(Long id,User user) {

        Load load = loadRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("LOAD NOT FOUND", HttpStatus.BAD_REQUEST));

        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> RestException.restThrow("USER NOT FOUND", HttpStatus.BAD_REQUEST));

        UUID receiverUser_id = currentUser.getId();

        UUID senderUser_id = load.getUser().getId();

        Card senderCard = cardRepository.findByUserId(senderUser_id)
                .orElseThrow(() -> RestException.restThrow("Card NOT FOUND", HttpStatus.BAD_REQUEST));

        Card receiverCard = cardRepository.findByUserId(receiverUser_id)
                .orElseThrow(() -> RestException.restThrow("Card NOT FOUND", HttpStatus.BAD_REQUEST));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setReceiver_amount(load.getPayment());
        transactionDTO.setSender_amount(load.getPayment());
        transactionDTO.setSender_card_number(senderCard.getCardNumber());
        transactionDTO.setReceiver_card_number(receiverCard.getCardNumber());


        Rate rate = rateRepository.findByFromCurrencyAndToCurrency(senderCard.getCurrency(), receiverCard.getCurrency())
                .orElseThrow(() -> RestException.restThrow("Not Found", HttpStatus.CONFLICT));

        checkCardBalance(transactionDTO, senderCard);

        long receiver_amount,sender_amount;
        if(rate.getRate()<0){
            long rate_amount = Math.abs(rate.getRate());
            receiver_amount = transactionDTO.getSender_amount()/rate_amount;
            if(receiver_amount==0)
                throw RestException.restThrow("Bad request",HttpStatus.BAD_REQUEST);
            sender_amount = receiver_amount*rate_amount;
        }
        else {
            receiver_amount = transactionDTO.getSender_amount()* rate.getRate();
            sender_amount = transactionDTO.getSender_amount();
        }

        Transaction transaction = Transaction.builder()
                .senderCard(senderCard.getCardNumber())
                .sender_amount(sender_amount)
                .receiverCard(receiverCard.getCardNumber())
                .receiver_amount(receiver_amount)
                .load_id(id)
                .status("NEW")
                .build();

        transactionRepository.save(transaction);
        return ApiResult.successResponse(transaction.getId());
    }
//    @Transactional(isolation =  Isolation.SERIALIZABLE)
    public ApiResult<TransactionDTO> confirm(UUID transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                ()->RestException.restThrow("Bad request",HttpStatus.BAD_REQUEST));

        Card sender_card = cardRepository.findByCardNumber(transaction.getSenderCard())
                .orElseThrow(() -> RestException.restThrow("Not Found", HttpStatus.CONFLICT));

        Card receiver_card = cardRepository.findByCardNumber(transaction.getReceiverCard())
                .orElseThrow(() -> RestException.restThrow("Not Found", HttpStatus.CONFLICT));
        Load load = loadRepository.findById(transaction.getLoad_id())
                .orElseThrow(() -> RestException.restThrow("LOAD NOT Found", HttpStatus.CONFLICT));

        if(sender_card.getBalance()<=transaction.getSender_amount()||load.isBooked()) {
            transaction.setStatus("ERROR");
            return ApiResult.failResponse();
        }
        load.setBooked(true);
        loadRepository.save(load);
        transaction.setStatus("Success");
        long sender_balance = sender_card.getBalance() - transaction.getSender_amount();
        sender_card.setBalance(sender_balance);
        long receiver_balance = receiver_card.getBalance() + transaction.getSender_amount();
        receiver_card.setBalance(receiver_balance);

        cardRepository.save(sender_card);
        cardRepository.save(receiver_card);
        return ApiResult.successResponse();
    }


    private void checkCardBalance(TransactionDTO transactionDTO, Card senderCard) {
        if (senderCard.getBalance() < transactionDTO.getSender_amount())
            throw RestException.restThrow("Not enough money", HttpStatus.BAD_REQUEST);
    }
    private TransactionDTO mapTransactionToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .sender_card_number(transaction.getSenderCard())
                .receiver_card_number(transaction.getReceiverCard())
                .sender_amount(transaction.getSender_amount())
                .receiver_amount(transaction.getReceiver_amount())
                .build();
    }
}
