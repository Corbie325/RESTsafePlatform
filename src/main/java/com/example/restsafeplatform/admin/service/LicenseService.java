package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.data.LicenseData;
import com.example.restsafeplatform.admin.dto.entity.WeaponLicense;
import com.example.restsafeplatform.admin.repository.LicenseRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final JwtTokenProvider token;

    @Autowired
    public LicenseService(LicenseRepository licenseRepository, JwtTokenProvider token) {
        this.licenseRepository = licenseRepository;
        this.token = token;
    }


    public WeaponLicense addLicense(ServletRequest servletRequest, LicenseData weaponLicense){
        String serialAndNumber = token.getSerialAndNumber(token.resolveToken((HttpServletRequest) servletRequest));

        WeaponLicense license = WeaponLicense
                .builder()
                .passport(serialAndNumber)
                .dateOfIssue(weaponLicense.getDateOfIssue())
                .dateValidUntil(weaponLicense.getDateValidUntil())
                .number(weaponLicense.getNumber())
                .type(weaponLicense.getType())
                .placeOfIssue(weaponLicense.getPlaceOfIssue())
                .build();
        licenseRepository.saveAndFlush(license);
        return license;
    }

    //TODO делать шидуллер для проверки выгорания лицензий
    private void checkDateLicense(){
    }

    //TODO сделать проверку чтоб нельзя было добавить одну и ту же лицензию и проверить на ее существование
    private void chekLicenseType(){
    }
}
