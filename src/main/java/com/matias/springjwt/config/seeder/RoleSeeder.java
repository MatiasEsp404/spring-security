package com.matias.springjwt.config.seeder;

import com.matias.springjwt.model.RoleEntity;
import com.matias.springjwt.repository.IRoleRepository;
import com.matias.springjwt.security.dto.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleSeeder {

    @Autowired
    private IRoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        createRoles();
    }

    private void createRoles() {
        roleRepository.deleteAll();
        Arrays.stream(ERole.values()).map(this::buildRole).forEach(roleRepository::save);
        System.out.println(roleRepository.findAll());
    }

    private RoleEntity buildRole(ERole role) {
        return RoleEntity.builder().name(role).build();
    }

}
