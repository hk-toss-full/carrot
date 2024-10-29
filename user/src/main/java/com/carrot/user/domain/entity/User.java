package com.carrot.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long userId;

    @Column(unique = true)
    private Long kakaoId;

    @Column(unique = true)
    private String randomId;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = false)
    private Date createdAt;

}
