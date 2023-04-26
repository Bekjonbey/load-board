package com.example.transaction2.component;

import com.example.transaction2.entity.User;
import com.example.transaction2.repository.RoleRepository;
import com.example.transaction2.entity.Rate;
import com.example.transaction2.entity.Role;
import com.example.transaction2.entity.enums.CurrencyEnum;
import com.example.transaction2.entity.enums.PermissionEnum;
import com.example.transaction2.entity.enums.RoleEnum;
import com.example.transaction2.repository.RateRepository;
import com.example.transaction2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RateRepository rateRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String modeType;

    @Override
    public void run(String... args) {
        if (Objects.equals("create", modeType)) {
            addRoles();
            addRates();
//            addAdmin();
        }
    }
    private void addAdmin() {
        userRepository.save(
                User.builder()
                        .phone("bekjon")
                        .password("Bekjon@03")
                        .build()
        );
    }
    private void addRoles() {
        roleRepository.save(
                Role.builder()
                        .name(RoleEnum.USER.name())
                        .description("Client")
                        .permissions(setPermissionOfUser())
                        .build()
        );
        roleRepository.save(
                Role.builder()
                        .name(RoleEnum.ADMIN.name())
                        .description("Admin")
                        .permissions(setPermissionsOfAdmin())
                        .build()
        );
        roleRepository.save(
                Role.builder()
                        .name(RoleEnum.DRIVER.name())
                        .description("Driver")
                        .permissions(setPermissionsOfAdmin())
                        .build()
        );
        roleRepository.save(
                Role.builder()
                        .name(RoleEnum.COMPANY.name())
                        .description("Company")
                        .permissions(setPermissionsOfAdmin())
                        .build()
        );
    }

    private void addRates() {
        rateRepository.save(
                Rate.builder()
                        .fromCurrency(CurrencyEnum.USD)
                        .toCurrency(CurrencyEnum.SUM)
                        .rate(11330L)
                        .build()
        );
        rateRepository.save(
                Rate.builder()
                        .fromCurrency(CurrencyEnum.SUM)
                        .toCurrency(CurrencyEnum.USD)
                        .rate(-11330L)
                        .build()
        );
        rateRepository.save(
                Rate.builder()
                        .fromCurrency(CurrencyEnum.SUM)
                        .toCurrency(CurrencyEnum.SUM)
                        .rate(1L)
                        .build()
        );
        rateRepository.save(
                Rate.builder()
                        .fromCurrency(CurrencyEnum.USD)
                        .toCurrency(CurrencyEnum.USD)
                        .rate(1L)
                        .build()
        );
    }


    private Set<PermissionEnum> setPermissionOfUser() {
        return new HashSet<>(Arrays.stream(PermissionEnum.values()).toList());
    }

    private Set<PermissionEnum> setPermissionsOfAdmin() {
        return new HashSet<>(Arrays.stream(PermissionEnum.values()).toList());
    }

}