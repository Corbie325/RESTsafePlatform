package com.example.restsafeplatform.authorization.controller;

import com.example.restsafeplatform.admin.dto.data.TokenData;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.authorization.dto.data.AuthenticationRequest;
import com.example.restsafeplatform.authorization.dto.data.FastAuthenticationRequest;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.configuration.SecurityConfiguration;
import com.example.restsafeplatform.common.exception.AlreadyExistException;
import com.example.restsafeplatform.utils.UriPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping(UriPath.API_PATH_PREFIX)
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityConfiguration securityConfiguration;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, SecurityConfiguration securityConfiguration) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityConfiguration = securityConfiguration;
    }


    //ResponseEntity<?>
    @PostMapping("/login")
    public TokenData authenticate(@RequestBody AuthenticationRequest request) throws AlreadyExistException {
        try {
            log.info("login = {}", request.getEmail());
            log.info("pass = {}", request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(request.getEmail(), userEntity.getRole().name(), userEntity.getPassport().getPassportNumberAndSerial(), userEntity.getId());
            securityConfiguration.getToken().put(request.getEmail(), token);
            TokenData tokenData = new TokenData();
            tokenData.setToken(token);
            return tokenData;
            //return ResponseEntity.ok(securityConfiguration.getToken().get(request.getEmail()));
        } catch (AuthenticationException e) {
            throw new AlreadyExistException("Invalid email/password combination");
            //return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/fastLogin")
    public ResponseEntity<?> authenticate(@RequestBody FastAuthenticationRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getFastCode()));
            UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            log.info("_>>>>>>>>>>>>>>>>>>>> Дошли до сбдова");
            String token = jwtTokenProvider.createToken(request.getEmail(), userEntity.getRole().name(), userEntity.getPassport().getPassportNumberAndSerial(), userEntity.getId());
            securityConfiguration.getToken().put(request.getEmail(), token);
            return ResponseEntity.ok(securityConfiguration.getToken().get(request.getEmail()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

}
