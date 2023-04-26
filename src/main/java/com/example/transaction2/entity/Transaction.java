package com.example.transaction2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String senderCard;
    private String receiverCard;
    @Column(nullable = false)
    private Long sender_amount;
    @Column(nullable = false)
    private Long receiver_amount;

    @Column(nullable = false)
    private String status;
    @CreationTimestamp
    private LocalDateTime created_at;


}
