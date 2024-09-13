package com.matias.springjwt.repository;

import com.matias.springjwt.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsernameOrEmail(String username, String email);
}
