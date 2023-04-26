package com.example.transaction2.service;
import com.example.transaction2.entity.Load;
import com.example.transaction2.entity.User;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.payload.LoadDTO;
import com.example.transaction2.payload.LoadAddDTO;
import com.example.transaction2.repository.CardRepository;
import com.example.transaction2.repository.LoadRepository;
import com.example.transaction2.repository.UserRepository;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.security.CurrentUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadService {
    private final LoadRepository loadRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    public ApiResult<LoadAddDTO> add(LoadAddDTO loadAddDTO, User user){
        if (cardRepository.findAllByUserIdAndDeletedFalse(user.getId()).isEmpty()) {
            throw RestException.restThrow("ADD CARD FIRST",HttpStatus.BAD_REQUEST);
        }

        User currentUser = userRepository.findById(user.getId()).orElseThrow();

        if(currentUser.getPosition().equalsIgnoreCase("USER")) {
            Load load = new Load();
            load.setUser(user);
            load.setDescription(loadAddDTO.getDescription());
            load.setPayment(loadAddDTO.getPayment());
            load.setCreated_at(LocalDateTime.now());
            loadRepository.save(load);
            return ApiResult.successResponse();
        }
        throw RestException.restThrow("ONLY USERS CAN ADD LOADS", HttpStatus.BAD_REQUEST);
    }
    public ApiResult<List<LoadDTO>> getAll(User user) {
        List<Load> loads = loadRepository.findAllByUserId(user.getId());
        return ApiResult.successResponse(loads.stream().map(this::mapLoadToLoadDTO)
                .collect(Collectors.toList()));
    }

    public ApiResult<LoadDTO> get(Long id) {
        Load load= loadRepository.findById(id).orElseThrow(()->RestException.restThrow("LOAD NOT FOUND",HttpStatus.BAD_REQUEST));
        return ApiResult.successResponse(mapLoadToLoadDTO(load));
    }
    private LoadDTO mapLoadToLoadDTO(Load load) {
        return LoadDTO.builder()
                .userId(load.getUser().getId())
                .booked(load.isBooked())
                .description(load.getDescription())
                .payment(load.getPayment())
                .created_at(load.getCreated_at())
                .build();
    }

    public ApiResult<List<LoadDTO>> getOpenLoads() {
        List<Load> allLoads = loadRepository.findAll();
        return ApiResult.successResponse(allLoads.stream().map(this::mapLoadToLoadDTO)
                .collect(Collectors.toList()));
    }
}
