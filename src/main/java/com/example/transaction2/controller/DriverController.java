package com.example.transaction2.controller;

import com.example.transaction2.aop.CurrentUser;
import com.example.transaction2.entity.User;
import com.example.transaction2.payload.DriverAddDTO;
import com.example.transaction2.payload.DriverDTO;
import com.example.transaction2.payload.UpdateDriverDTO;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/add")
    public ApiResult<DriverAddDTO> addDriver(@Valid @RequestBody DriverAddDTO driverDTO, @CurrentUser User user) {
        return driverService.add(driverDTO,user);
    }
    @GetMapping("/all")
    public ApiResult<List<DriverDTO>> getAll(@CurrentUser User user) {
        return driverService.getAll(user);
    }
    @GetMapping("/{name}")
    public ApiResult<DriverDTO> get(@PathVariable String name) {
        return driverService.get(name);
    }
    @PutMapping("/edit")
    public ApiResult<DriverDTO> edit(@RequestBody UpdateDriverDTO driverDTO) {
        return driverService.updateLocation(driverDTO);
    }

}
