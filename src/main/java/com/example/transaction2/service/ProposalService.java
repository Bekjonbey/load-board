package com.example.transaction2.service;

import com.example.transaction2.entity.News;
import com.example.transaction2.entity.Proposal;
import com.example.transaction2.payload.ProposalCreateDto;
import com.example.transaction2.payload.ProposalStatus;
import com.example.transaction2.payload.ProposalUpdateDto;
import com.example.transaction2.payload.StringResponse;
import com.example.transaction2.repository.AdminRepository;
import com.example.transaction2.repository.ProposalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepo proposalRepo;
    private final AdminRepository adminRepository;

    public StringResponse add(ProposalCreateDto request) {
        proposalRepo.save(new Proposal(request));
        return new StringResponse("Successfully added proposal");
    }

    public List<Proposal> getAll(ProposalStatus status) {
        if (status != null) {
            return proposalRepo.findAllByStatus(status);
        } else return proposalRepo.findAll();
    }

    public Proposal getOne(Long id) {
        return proposalRepo.findById(id).get();
    }

    public StringResponse update(ProposalUpdateDto request, UUID key) {
        if (adminRepository.existsByKey(key)) {
            Optional<Proposal> byId = proposalRepo.findById(request.getProposalId());
            byId.ifPresent(proposal -> proposal.setStatus(request.getNewStatus()));
            proposalRepo.save(byId.get());
        } else {
            return new StringResponse("key not found");
        }
        return new StringResponse("Successfully updated proposal");
    }
}
