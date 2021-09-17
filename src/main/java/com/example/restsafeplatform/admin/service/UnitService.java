package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.entity.Unit;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.dto.entity.WeaponInfo;
import com.example.restsafeplatform.admin.dto.response.UnitResponse;
import com.example.restsafeplatform.admin.repository.UnitRepository;
import com.example.restsafeplatform.admin.dto.data.UnitData;
import com.example.restsafeplatform.admin.repository.WeaponRepository;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.common.exception.WeaponNonExistException;
import com.example.restsafeplatform.utils.CheckActiveUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UnitService {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final JwtTokenProvider token;
    private final WeaponRepository weaponRepository;
    private final PassportService passportService;
    private final CheckActiveUnit checkActiveUnit;


    public UnitService(UserRepository userRepository, UnitRepository unitRepository, JwtTokenProvider token, WeaponRepository weaponRepository, PassportService passportService, CheckActiveUnit checkActiveUnit) {
        this.userRepository = userRepository;
        this.unitRepository = unitRepository;
        this.token = token;
        this.weaponRepository = weaponRepository;
        this.passportService = passportService;
        this.checkActiveUnit = checkActiveUnit;
    }

    public boolean checkStatus(String serial){
        Optional<Unit> unit = unitRepository.getUnitBySerial(serial);
        if (unit.isEmpty()){
            return false;
        }
        return unit.get().isActive();
    }

    public Unit activate(String serial){
        Optional<Unit> unit = unitRepository.getUnitBySerial(serial);
        if (unit.isEmpty()){
            return null;
        }
        unit.get().setActive(true);
        unitRepository.save(unit.get());
        return unit.get();
    }

    public List<UnitResponse> gelAllUnit(ServletRequest servletRequest) {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        List <Unit> units = unitRepository.getUnitByUserEntityId(Long.valueOf(userId));
        return units
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Unit addUnit(UnitData unitData) throws UserNonExistException {
        Optional<UserEntity> userEntity = userRepository.getUserByPassport(unitData.getPassport_id());
        if (userEntity.isEmpty())
        {
            throw new UserNonExistException("Пользователя не существует");
        }
        Unit unit = Unit
                .builder()
                .serial(unitData.getSerial())
                .date(LocalDateTime.now())
                .userEntity(userEntity.get())
                .build();
        unitRepository.saveAndFlush(unit);
        return unit;
    }

//    public void bcSetting(UnitData unitData) throws UserNonExistException, WeaponNonExistException {
//        Optional<UserEntity> userEntityIUserEntityOptional = userRepository.getUserByPassport(unitData.getPassport_id());
//        if (userEntityIUserEntityOptional.isEmpty()) {
//            throw new UserNonExistException("Пользователя не существует");
//        }
//        Optional<WeaponInfo> weaponFromId = weaponRepository.findById(unitData.getWeapon_id());
//        if (weaponFromId.isEmpty()){
//            throw new WeaponNonExistException("Оружия не существует");
//        }
//        String phone = userEntityIUserEntityOptional.get().getContactPhone();
////        userEntityIUserEntityOptional.get().
//    }

    private UnitResponse map(Unit unit){
        return UnitResponse
                .builder()
                .id(unit.getId())
                .color(unit.getColor())
                .isActive(unit.isActive())
                .imei(unit.getImei())
                .serial(unit.getSerial())
                .simImei(unit.getSimImei())
                .unitType(unit.getUnitType())
                .passport(unit.getPassport())
                .phoneNumber(unit.getPhoneNumber())
                .mode(unit.getMode())
                .userId(unit.getUserEntity().getId())
                .build();
    }
}
