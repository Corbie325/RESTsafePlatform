package com.example.restsafeplatform.authorization.dto.data;

import lombok.Data;

@Data
public class FastAuthenticationRequest {

    private String email;

    private String fastCode;
}
