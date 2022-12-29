package com.whatHappened.ws.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^(.+)@(.+)$", message = "{whatHappened.constraints.email.Pattern.message}")
    private String email;

    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{whatHappened.constraints.password.Pattern.message}")
    private String password;
}
