package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.data.AliasRequest;
import com.example.restsafeplatform.admin.dto.entity.Unit;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.dto.response.UnitResponse;
import com.example.restsafeplatform.admin.dto.response.WeaponResponse;
import com.example.restsafeplatform.admin.repository.TtxRepository;
import com.example.restsafeplatform.admin.dto.entity.TtxInfo;
import com.example.restsafeplatform.admin.dto.entity.WeaponInfo;
import com.example.restsafeplatform.admin.dto.data.WeaponData;
import com.example.restsafeplatform.admin.repository.WeaponRepository;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;

import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.common.exception.WeaponNonExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final JwtTokenProvider token;
    private final UserRepository userRepository;
    private final TtxRepository ttxRepository;
    private final PassportService passportService;


    @Autowired
    public WeaponService(WeaponRepository weaponRepository, JwtTokenProvider token, UserRepository userRepository, TtxRepository ttxRepository, PassportService passportService) {
        this.weaponRepository = weaponRepository;
        this.token = token;
        this.userRepository = userRepository;
        this.ttxRepository = ttxRepository;
        this.passportService = passportService;
    }

    public WeaponInfo addWeapon(ServletRequest servletRequest, WeaponData weaponData) throws UserNonExistException {
        Optional<UserEntity> userEntity = userRepository.getUserByPassport(weaponData.getPassport_id());
        if (userEntity.isEmpty())
        {
            throw new UserNonExistException("Пользователя не существует");
        }
        TtxInfo ttxInfo = TtxInfo
                .builder()
                .caliber(weaponData.getCaliber())
                .capacity(weaponData.getCapacity())
                .dimensions(weaponData.getDimensions())
                .gost(weaponData.getGost())
                .manufacture(weaponData.getManufacture())
                .weight(weaponData.getWeight())
                .extraInfo(weaponData.getExtraInfo())
                .build();
        Long ttxId = ttxRepository.save(ttxInfo).getId();

        log.info("ttx id --> {}", ttxId);

        WeaponInfo weaponInfo = WeaponInfo
                .builder()
                .passport(passportService.checkPassport(weaponData.getPassport_id()))
                .userEntity(userEntity.get())
                .model(weaponData.getModel())
                .name(weaponData.getName())
                .type(weaponData.getType())
                .place(weaponData.getPlace())
                .ttxInfo(ttxInfo)
                .build();
        weaponRepository.saveAndFlush(weaponInfo);
        return weaponInfo;
    }

    public WeaponInfo addAlias(ServletRequest servletRequest, AliasRequest aliasRequest) throws UserNonExistException, WeaponNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userId));
        if (userEntity.isEmpty())
        {
            throw new UserNonExistException("Пользователя не существует");
        }
        Optional<WeaponInfo> weaponFromId = weaponRepository.findById(aliasRequest.getWeaponId());
        if (weaponFromId.isEmpty()){
            throw new WeaponNonExistException("Оружия не существует");
        }
        WeaponInfo weaponInfo = weaponFromId.get();
        weaponInfo.setAlias(aliasRequest.getName());
        return weaponRepository.save(weaponInfo);
    }

    public List<WeaponResponse> getAllWeapon(ServletRequest servletRequest){
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        List <WeaponInfo> weapons = weaponRepository.getAllWeaponByUser(Long.valueOf(userId));
        return weapons
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private WeaponResponse map(WeaponInfo weaponInfo){
        return WeaponResponse
                .builder()
                .weaponInfoId(weaponInfo.getWeaponInfoId())
                .model(weaponInfo.getModel())
                .name(weaponInfo.getName())
                .passport(weaponInfo.getPassport())
                .place(weaponInfo.getPlace())
                .ttxInfo(weaponInfo.getTtxInfo())
                .type(weaponInfo.getType())
                .userId(weaponInfo.getUserEntity().getId())
                .build();
    }

}
