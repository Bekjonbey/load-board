package com.example.transaction2.controller;

import com.example.transaction2.aop.CurrentUser;
import com.example.transaction2.entity.User;
import com.example.transaction2.payload.LoadDTO;
import com.example.transaction2.payload.LoadAddDTO;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.LoadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/load")
@RequiredArgsConstructor
public class LoadController {
    private final LoadService loadService;
    @PostMapping("/add")
    public ApiResult<LoadAddDTO> addLoad(@Valid @RequestBody LoadAddDTO loadAddDTO, @CurrentUser User user) {
        return loadService.add(loadAddDTO,user);
    }
    @GetMapping("/all")
    public ApiResult<List<LoadDTO>> getAll(@CurrentUser User user) {
        return loadService.getAll(user);
    }
    @GetMapping("/{id}")
    public ApiResult<LoadDTO> get(@PathVariable Long id) {
        return loadService.get(id);
    }
    @GetMapping("/open-loads")
    public ApiResult<Page<LoadDTO>> getOpenLoads(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "6") int pageSize) {
        return loadService.getOpenLoads(page, pageSize);
    }
}
