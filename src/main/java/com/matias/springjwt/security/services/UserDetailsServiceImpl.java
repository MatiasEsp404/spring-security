package com.matias.springjwt.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matias.springjwt.model.UserEntity;
import com.matias.springjwt.repository.IUserRepository;
import com.matias.springjwt.security.dto.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserEntity user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	    return UserDetailsImpl.builder()
	    		.id(user.getId())
	    		.username(user.getUsername())
	    		.email(user.getEmail())
	    		.password(user.getPassword())
	    		.authorities(user.getAuthorities())
	    		.build();
	}

}