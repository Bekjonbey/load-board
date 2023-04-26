package com.example.transaction2.service;

import com.example.transaction2.entity.Card;
import com.example.transaction2.entity.Rate;
import com.example.transaction2.entity.Transaction;
import com.example.transaction2.entity.User;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.payload.TransactionDTO;
import com.example.transaction2.repository.CardRepository;
import com.example.transaction2.repository.RateRepository;
import com.example.transaction2.repository.TransactionRepository;
import com.example.transaction2.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ApiResult<TransactionDTO> add(TransactionDTO transactionDTO) {

        Card sender_card = cardRepository.findByCardNumber(transactionDTO.getSender_card_number()).orElseThrow(()->RestException.restThrow("ERROR",HttpStatus.BAD_REQUEST));
        Card receiver_card = cardRepository.findByCardNumber(transactionDTO.getReceiver_card_number()).orElseThrow(()->RestException.restThrow("ERROR",HttpStatus.BAD_REQUEST));
        Rate rate = rateRepository.findByFromCurrencyAndToCurrency(sender_card.getCurrency(), receiver_card.getCurrency())
                .orElseThrow(() -> RestException.restThrow("Not Found", HttpStatus.CONFLICT));
        checkCardBalance(transactionDTO, sender_card);
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
                .senderCard(sender_card.getCardNumber())
                .sender_amount(sender_amount)
                .receiverCard(receiver_card.getCardNumber())
                .receiver_amount(receiver_amount)
                .status("NEW")
                .build();

        transactionRepository.save(transaction);
        return ApiResult.successResponse(mapTransactionToDTO(transaction));
    }
    @Transactional(isolation =  Isolation.SERIALIZABLE)
    public ApiResult<TransactionDTO> confirm(UUID transactionId,User user) {

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                ()->RestException.restThrow("Bad request",HttpStatus.BAD_REQUEST));
        Card sender_card = cardRepository.findByCardNumber(transaction.getReceiverCard()).orElseThrow();
        Card receiver_card = cardRepository.findByCardNumber(transaction.getReceiverCard()).orElseThrow();

        if(sender_card.getBalance()>=transaction.getSender_amount()) {
            transaction.setStatus("Success");
            sender_card.setBalance(sender_card.getBalance() - transaction.getSender_amount());
            receiver_card.setBalance(receiver_card.getBalance() + transaction.getReceiver_amount());
            cardRepository.save(sender_card);
            cardRepository.save(receiver_card);
            return ApiResult.successResponse();
        }
        transaction.setStatus("ERROR");
        return ApiResult.failResponse();
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
