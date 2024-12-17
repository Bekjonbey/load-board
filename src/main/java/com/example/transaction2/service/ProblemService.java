package com.example.transaction2.service;

import com.example.transaction2.payload.ProblemCreateRequestDto;
import com.example.transaction2.payload.ProblemCreateResponse;
import com.example.transaction2.payload.ProblemCreateResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    public ProblemCreateResponse createProblem(ProblemCreateRequestDto request) {
        ProblemCreateResponseDto problemCreateResponseDto = new ProblemCreateResponseDto(request);
        return new ProblemCreateResponse(problemCreateResponseDto);
    }
}
