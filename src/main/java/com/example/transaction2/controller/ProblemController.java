package com.example.transaction2.controller;

import com.example.transaction2.payload.ProblemCreateRequestDto;
import com.example.transaction2.payload.ProblemCreateResponse;
import com.example.transaction2.payload.ProblemCreateResponseDto;
import com.example.transaction2.service.ProblemService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService service;

    @PostMapping("/create")
    @PermitAll
    public ProblemCreateResponse createProblem(@RequestBody ProblemCreateRequestDto request) {
        return service.createProblem(request);
    }
}
