package com.example.restsafeplatform.admin.controller;

import com.example.restsafeplatform.admin.dto.data.UnitData;
import com.example.restsafeplatform.admin.dto.entity.Unit;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.dto.response.UnitResponse;
import com.example.restsafeplatform.admin.service.UnitService;
import com.example.restsafeplatform.common.exception.AlreadyExistException;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.common.exception.WeaponNonExistException;
import com.example.restsafeplatform.utils.CheckActiveUnit;
import com.example.restsafeplatform.utils.UriPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = UriPath.API_PATH_PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UnitController {

    private final UnitService unitService;
    private final CheckActiveUnit checkActiveUnit;

    @Autowired
    public UnitController(UnitService unitService, CheckActiveUnit checkActiveUnit) {
        this.unitService = unitService;
        this.checkActiveUnit = checkActiveUnit;
    }

    @GetMapping(UriPath.GET_UNIT_STATUS)
    @PreAuthorize("hasAuthority('users:read')")
    public boolean getStatus(@PathVariable String serial){
       return unitService.checkStatus(serial);
    }

    @GetMapping(UriPath.GET_ALL_UNIT)
    @PreAuthorize("hasAuthority('users:read')")
    public List<UnitResponse> getAll(ServletRequest servletRequest){return unitService.gelAllUnit(servletRequest);}

    @PutMapping(UriPath.ACTIVATE_UNIT)
    @PreAuthorize("hasAuthority('users:read')")
    public Unit activate(@PathVariable String serial){return unitService.activate(serial);}

    @PostMapping(UriPath.ADD_UNIT)
    @PreAuthorize("hasAuthority('users:write')")
    public ResponseEntity<Unit> addUnit(@RequestBody UnitData unitData) throws UserNonExistException, ExecutionException, InterruptedException, WeaponNonExistException {
        Unit unit = unitService.addUnit(unitData);
        boolean flag = checkActiveUnit.taskThread(unitData.getSerial());
        if (flag){
//            unitService.bcSetting(unitData);
            return ResponseEntity.ok(unit);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
