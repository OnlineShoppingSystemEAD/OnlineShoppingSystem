package com.example.usermanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String postalNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String addressPart1;

    @Column(nullable = false)
    private String addressPart2;

    @Column(nullable = false)
    private String addressPart3;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private OurUsers user;
}