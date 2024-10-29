package com.carrot.user.repository;

import com.carrot.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(Long kakaoId);
}
