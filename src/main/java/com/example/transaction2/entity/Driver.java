package com.example.transaction2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String CompanyName;
    @Column(nullable = false, unique = true)
    private String driverLicenceNumber;
    @Column()
    private String currentLocation;
    @Column()
    private boolean hasLoad;
    @ManyToOne(optional = false)
    private User user;
}
