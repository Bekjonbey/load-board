package com.example.transaction2.payload;

import com.example.transaction2.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class LoadDTO {
    private String description;
    private Long payment;
    private Long loadId;
    private LocalDateTime created_at;
    private boolean booked;
}
