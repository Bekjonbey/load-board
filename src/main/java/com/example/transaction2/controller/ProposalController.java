package com.example.transaction2.controller;

import com.example.transaction2.entity.Proposal;
import com.example.transaction2.payload.ProposalCreateDto;
import com.example.transaction2.payload.ProposalStatus;
import com.example.transaction2.payload.ProposalUpdateDto;
import com.example.transaction2.payload.StringResponse;
import com.example.transaction2.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/proposal")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService proposalService;

    @PostMapping("/add")
    public StringResponse add(@RequestBody ProposalCreateDto request) {
        return proposalService.add(request);
    }

    @PostMapping("/update")
    public StringResponse update(@RequestParam("key") UUID key, @RequestBody ProposalUpdateDto request) {
        return proposalService.update(request, key);
    }

    @GetMapping("/get-all")
    public List<Proposal> getAll(@RequestParam(required = false) ProposalStatus status ) {
        return proposalService.getAll(status);
    }

    @GetMapping("/get")
    public Proposal getOne(@RequestParam(required = false) Long id ) {
        return proposalService.getOne(id);
    }
}