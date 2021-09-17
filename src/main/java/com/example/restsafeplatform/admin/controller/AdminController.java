package com.example.restsafeplatform.admin.controller;

import com.example.restsafeplatform.admin.dto.data.LicenseData;
import com.example.restsafeplatform.admin.dto.entity.WeaponLicense;
import com.example.restsafeplatform.admin.dto.request.ConfidantRequest;
import com.example.restsafeplatform.admin.dto.request.UserLocation;
import com.example.restsafeplatform.admin.dto.request.UserQuestionRequest;
import com.example.restsafeplatform.admin.dto.response.ConfidantResponse;
import com.example.restsafeplatform.admin.dto.response.UserQuestionResponse;
import com.example.restsafeplatform.admin.service.*;
import com.example.restsafeplatform.admin.dto.data.UserData;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.common.exception.AlreadyExistException;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.utils.UriPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = UriPath.API_PATH_PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AdminController {

   private final UserAndAdminRegistrationService userAndAdminRegistrationService;
   private final LicenseService licenseService;
   private final UserService userService;

   @Autowired
   public AdminController(UserAndAdminRegistrationService userAndAdminRegistrationService, LicenseService licenseService, UserService userService) {
        this.userAndAdminRegistrationService = userAndAdminRegistrationService;
        this.licenseService = licenseService;
        this.userService = userService;
    }


    @PostMapping(UriPath.ADMIN_REGISTRATION)
    public UserEntity createAdmin(@RequestBody UserData userData) throws AlreadyExistException {
        return userAndAdminRegistrationService.adminRegistration(userData);
    }

    @GetMapping(UriPath.GET_ALL_USER)
    @PreAuthorize("hasAuthority('users:write')")
    public List<UserEntity> getAll() {
        return userAndAdminRegistrationService.gelAllUser();
    }

    @GetMapping(UriPath.GET_USER_BY_ID)
    @PreAuthorize("hasAuthority('users:write')")
    public UserEntity getById(@PathVariable Integer id){return userAndAdminRegistrationService.getUser(id);}


    @GetMapping(UriPath.GET_USER_BY_TOKEN)
    @PreAuthorize("hasAuthority('users:read')")
    public UserEntity getByToken(ServletRequest servletRequest) throws UserNonExistException {
       log.info(" = {}", servletRequest);
       return userAndAdminRegistrationService.getUserByToken(servletRequest);
    }

    @PostMapping(UriPath.USER_REGISTRATION)
    @PreAuthorize("hasAuthority('users:write')")
    public UserEntity create(@RequestBody UserData userData) throws AlreadyExistException { return userAndAdminRegistrationService.userRegistration(userData); }

    @DeleteMapping(UriPath.DELETE_USER_BY_ID)
    @PreAuthorize("hasAuthority('users:write')")
    public void deleteById(@PathVariable Integer id) {
        userAndAdminRegistrationService.deleteUser(id);
    }

    @PostMapping(UriPath.ADD_LICENSE)
    @PreAuthorize("hasAuthority('users:read')")
    public WeaponLicense addLicense(ServletRequest servletRequest, @RequestBody LicenseData weaponLicense){
        return licenseService.addLicense(servletRequest, weaponLicense);
    }


    @PostMapping(UriPath.ADD_USER_QUESTION)
    @PreAuthorize("hasAuthority('users:read')")
    public UserQuestionResponse addLicense(ServletRequest servletRequest, @RequestBody UserQuestionRequest userQuestionRequest) throws UserNonExistException {
        return userService.addQuestion(userQuestionRequest, servletRequest);
    }

    @PostMapping(UriPath.ADD_CONFIDANT)
    @PreAuthorize("hasAuthority('users:read')")
    public ConfidantResponse addConfidant(ServletRequest servletRequest, @RequestBody ConfidantRequest confidantRequest) throws UserNonExistException {
       return userService.addConfidant(servletRequest, confidantRequest);
    }

    @PostMapping(UriPath.ADD_LOCATION)
    @PreAuthorize("hasAuthority('users:read')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserLocation(ServletRequest servletRequest, @RequestBody UserLocation userLocation) throws UserNonExistException {
       userService.addUserLocation(servletRequest, userLocation);
    }

    @GetMapping("check/maill")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void checkMail (HttpServletRequest request) throws UserNonExistException {
            request.getRequestURL().toString();
   }

    @PostMapping(UriPath.ADD_FAST_CODE)
    @PreAuthorize("hasAuthority('users:read')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addFastCode(ServletRequest servletRequest, @PathVariable String code) throws UserNonExistException {
       userService.addFastCode(servletRequest, code);
    }

}
