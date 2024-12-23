package com.example.transaction2.entity;

import com.example.transaction2.payload.ProposalCreateDto;
import com.example.transaction2.payload.ProposalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String FIO;
    private ProposalStatus status;

    public Proposal(ProposalCreateDto request) {
        this.phoneNumber = request.getPhoneNumber();
        this.FIO = request.getFIO();
        this.status = ProposalStatus.NEW;
    }
}
