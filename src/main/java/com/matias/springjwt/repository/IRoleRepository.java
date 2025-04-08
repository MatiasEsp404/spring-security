package com.matias.springjwt.repository;

import com.matias.springjwt.model.RoleEntity;
import com.matias.springjwt.security.dto.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(ERole name);

}