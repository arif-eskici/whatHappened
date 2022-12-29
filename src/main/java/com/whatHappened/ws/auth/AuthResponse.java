package com.whatHappened.ws.auth;

import com.whatHappened.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private UserVM user;
}
