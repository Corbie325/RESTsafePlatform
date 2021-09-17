package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.data.UserData;
import com.example.restsafeplatform.admin.dto.entity.PassportInfo;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.authorization.dto.model.Role;
import com.example.restsafeplatform.authorization.dto.model.Status;
import com.example.restsafeplatform.admin.repository.PassportRepository;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.configuration.SecurityConfiguration;
import com.example.restsafeplatform.common.exception.AlreadyExistException;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserAndAdminRegistrationService implements UserDetails{

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    private final SecurityConfiguration securityConfiguration;

    @Autowired
    private JwtTokenProvider token;

    public UserAndAdminRegistrationService(UserRepository userRepository, PassportRepository passportRepository, SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.passportRepository = passportRepository;
        this.securityConfiguration = securityConfiguration;
    }

    @Transactional
    public UserEntity userRegistration(UserData userData) throws AlreadyExistException {
        Optional<UserEntity> userEntityIUserEntityOptional = userRepository.findByEmail(userData.getEmail());
        if (userEntityIUserEntityOptional.isPresent()) {
            throw new AlreadyExistException("Пользователь уже зарегестрирован");
        }
        PassportInfo passportInfo = PassportInfo
                .builder()
                .passportDateOfIssue(userData.getPassportData().getPassportDateOfIssue())
                .passportNumberAndSerial(userData.getPassportData().getPassportSerial() + "-" + userData.getPassportData().getPassportNumber())
                .registrationAddress(userData.getPassportData().getRegistrationAddress())
                .passportPlaceOfIssue(userData.getPassportData().getPassportPlaceOfIssue())
                .build();
        passportRepository.saveAndFlush(passportInfo);

        UserEntity userEntity = UserEntity
                .builder()
                .birthDate(userData.getBirthDate())
                .fio(userData.getFio())
                .email(userData.getEmail())
                .passport(passportInfo)
                .password(securityConfiguration.passwordEncoder().encode(userData.getPassword()))
                .role(Role.USER)
                .contactPhone(userData.getContactPhone().get(0))
                .contactPhone1(userData.getContactPhone().get(1))
                .status(userData.getStatus())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .middleName(userData.getMiddleName())
                .build();
        userRepository.saveAndFlush(userEntity);

        // mailSender.send(userData.getEmail(), "Registration in SafePlatform", message);
        return userEntity;
    }

    public UserEntity getUserByToken(ServletRequest servletRequest) throws UserNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        return userFromEntity.get();
    }

    public List<UserEntity> gelAllUser() {
        return userRepository.findAll();
    }

    public UserEntity getUser(Integer id){
        return userRepository.getById(Long.valueOf(id));
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static UserDetails fromUser(UserEntity user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }

    @Transactional
    public UserEntity adminRegistration(UserData userData) throws AlreadyExistException {
        Optional<UserEntity> userEntityIUserEntityOptional = userRepository.findByEmail(userData.getEmail());
        if (userEntityIUserEntityOptional.isPresent()) {
            throw new AlreadyExistException("Пользователь уже зарегестрирован");
        }
        PassportInfo passportInfo = PassportInfo
                .builder()
                .passportDateOfIssue(userData.getPassportData().getPassportDateOfIssue())
                .passportNumberAndSerial(userData.getPassportData().getPassportSerial() + "-" + userData.getPassportData().getPassportNumber())
                .registrationAddress(userData.getPassportData().getRegistrationAddress())
                .passportPlaceOfIssue(userData.getPassportData().getPassportPlaceOfIssue())
                .build();
        passportRepository.saveAndFlush(passportInfo);

        UserEntity userEntity = UserEntity
                .builder()
                .birthDate(userData.getBirthDate())
                .fio(userData.getFio())
                .email(userData.getEmail())
                .passport(passportInfo)
                .password(securityConfiguration.passwordEncoder().encode(userData.getPassword()))
                .role(Role.ADMIN)
                .contactPhone(userData.getContactPhone().get(0))
                .contactPhone1(userData.getContactPhone().get(1))
                .status(userData.getStatus())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .middleName(userData.getMiddleName())
                .build();
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }
}
