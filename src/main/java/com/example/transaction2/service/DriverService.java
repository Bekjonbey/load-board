package com.example.transaction2.service;

import com.example.transaction2.entity.Driver;
import com.example.transaction2.entity.User;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.payload.DriverAddDTO;
import com.example.transaction2.payload.DriverDTO;
import com.example.transaction2.payload.UpdateDriverDTO;
import com.example.transaction2.repository.DriverRepository;
import com.example.transaction2.repository.UserRepository;
import com.example.transaction2.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    public ApiResult<DriverAddDTO> add(DriverAddDTO driverDTO, User user){
        if(driverRepository.existsByNameOrDriverLicenceNumber(driverDTO.getName(), driverDTO.getDriverLicenceNumber())){
            throw RestException.restThrow("driver name or number invalid", HttpStatus.BAD_REQUEST);
        }
        User currentUser = userRepository.findById(user.getId()).orElseThrow();

        if(currentUser.getPosition().equalsIgnoreCase("COMPANY")) {
            Driver driver = new Driver();
            driver.setUser(user);
            driver.setName(driverDTO.getName());
            driver.setCompanyName(driverDTO.getCompanyName());
            driver.setDriverLicenceNumber(driverDTO.getDriverLicenceNumber());
            driver.setCurrentLocation("OFFICE");
            driver.setHasLoad(false);
            driverRepository.save(driver);
            return ApiResult.successResponse();
        }
        throw RestException.restThrow("ONLY COMPANIES CAN ADD DRIVERS", HttpStatus.BAD_REQUEST);
    }
    public ApiResult<List<DriverDTO>> getAll(User user) {

        List<Driver> drivers = driverRepository.findAllByUserId(user.getId());
        return ApiResult.successResponse(drivers.stream().map(this::mapDriverToDriverDTO)
                .collect(Collectors.toList()));
    }
    public ApiResult<DriverDTO> get(String driverName) {
        Driver driver= driverRepository.findByName(driverName);
        return ApiResult.successResponse(mapDriverToDriverDTO(driver));
    }
    public ApiResult<DriverDTO> updateLocation(UpdateDriverDTO driverDTO) {
        Driver driver= driverRepository.findByName(driverDTO.getName());
        driver.setCurrentLocation(driverDTO.getCurrentLocation());
        driverRepository.save(driver);
        return ApiResult.successResponse(mapDriverToDriverDTO(driver));
    }
    private DriverDTO mapDriverToDriverDTO(Driver driver) {
        return DriverDTO.builder()
                .id(driver.getId())
                .hasLoad(driver.isHasLoad())
                .userId(driver.getUser().getId())
                .currentLocation(driver.getCurrentLocation())
                .name(driver.getName())
                .CompanyName(driver.getCompanyName())
                .driverLicenceNumber(driver.getDriverLicenceNumber())
                .build();
    }

}
