package com.example.transaction2.repository;

import com.example.transaction2.entity.Proposal;
import com.example.transaction2.payload.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProposalRepo extends JpaRepository<Proposal, Long> {
    List<Proposal> findAllByStatus(ProposalStatus status);
}
