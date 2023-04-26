package com.example.transaction2.service;

import com.example.transaction2.entity.User;
import com.example.transaction2.payload.ErrorData;
import com.example.transaction2.payload.SignDTO;
import com.example.transaction2.payload.VerificationDTO;
import com.example.transaction2.repository.UserRepository;
import com.example.transaction2.util.StringHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class ValidatorService {

    private final UserRepository userRepository;

    public List<ErrorData> validateUser(SignDTO user) {
        List<ErrorData> errors = new ArrayList<>();
        if (userRepository.existsByPhone(user.getPhone()))
            addError("PhoneNumber", "NOT_UNIQUE", errors);
        if (!StringHelper.isValidPassword(user.getPassword()))
            addError("Password", "Must contains at least 1 upper, 1 lower and 1 numeric character", errors);
        return errors;
    }

    public List<ErrorData> validateVerificationCode(VerificationDTO verificationDTO) {
        List<ErrorData> errors = new ArrayList<>();

        Optional<User> user = userRepository.findByPhone(verificationDTO.getPhoneNumber());

        if (user.isEmpty())
            addError("user", "NOT_FOUND", errors);
        else {
            if (user.get().isEnabled()) {
                addError("user", "ALREADY_VERIFIED", errors);
            } else {
                User user1 = user.get();
                if (!Objects.equals(user1.getVerificationCode(), verificationDTO.getVerificationCode())) {
                    addError("verification code", "VERIFICATION_CODE_MISMATCH", errors);
                } else {
                    user1.setEnabled(true);
                    userRepository.save(user1);
                }
            }
        }
        return errors;
    }

    private void addError(String fieldName, String error, List<ErrorData> list) {
        list.add(ErrorData.builder()
                .errorMsg(error)
                .fieldName(fieldName)
                .build());
    }

    public List<ErrorData> checkCardNumberIsValidOrThrew(String cardNumber) {
        List<ErrorData> errors = new ArrayList<>();
        if (cardNumber.length() != 16)
            addError("card number", "Must contains only 16 numbers", errors);

        if (!(cardNumber.startsWith("9860") || cardNumber.startsWith("4200")))
            addError("card number", "Must begin 9860 or 4200", errors);

        for (char ch : cardNumber.toCharArray()) {
            if (!Character.isDigit(ch)){
                addError("card number", "Must contains only 16 numbers", errors);
                break;
            }
        }
        return errors;
    }

}
