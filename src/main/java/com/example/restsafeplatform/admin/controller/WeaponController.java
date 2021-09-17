package com.example.restsafeplatform.admin.controller;

import com.example.restsafeplatform.admin.dto.data.AliasRequest;
import com.example.restsafeplatform.admin.dto.data.WeaponData;
import com.example.restsafeplatform.admin.dto.entity.WeaponInfo;
import com.example.restsafeplatform.admin.dto.response.WeaponResponse;
import com.example.restsafeplatform.admin.service.WeaponService;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.common.exception.WeaponNonExistException;
import com.example.restsafeplatform.utils.UriPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@Slf4j
@RequestMapping(value = UriPath.API_PATH_PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class WeaponController {

    public final WeaponService weaponService;

    @Autowired
    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    @GetMapping(UriPath.GET_ALL_WEAPON)
    @PreAuthorize("hasAuthority('users:read')")
    public List<WeaponResponse> getAllWeapon(ServletRequest servletRequest){return weaponService.getAllWeapon(servletRequest);}

    @PostMapping(UriPath.ADD_WEAPON)
    @PreAuthorize("hasAuthority('users:write')")
    public WeaponInfo addWeapon(ServletRequest servletRequest, @RequestBody WeaponData weaponData) throws UserNonExistException {
        return weaponService.addWeapon(servletRequest, weaponData);
    }

    @PostMapping(UriPath.ADD_ALIAS)
    @PreAuthorize("hasAuthority('users:read')")
    public WeaponInfo addAlias(ServletRequest servletRequest, @RequestBody AliasRequest aliasRequest) throws UserNonExistException, WeaponNonExistException {
        return weaponService.addAlias(servletRequest, aliasRequest);
    }
}
