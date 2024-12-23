package com.example.transaction2.payload;

import lombok.Data;

@Data
public class ProposalUpdateDto {
    private Long proposalId;
    private ProposalStatus newStatus;
}
