package com.example.restsafeplatform.authorization.dto.data;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;

}
