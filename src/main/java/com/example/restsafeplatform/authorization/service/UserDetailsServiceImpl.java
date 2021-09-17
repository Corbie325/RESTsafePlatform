package com.example.restsafeplatform.authorization.service;

import com.example.restsafeplatform.admin.service.UserAndAdminRegistrationService;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return UserAndAdminRegistrationService.fromUser(userEntity);
    }
}
