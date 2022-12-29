package com.whatHappened.ws.user.vm;

import com.whatHappened.ws.user.User;
import lombok.Data;

@Data
public class UserVM {

    private String username;

    private String email;

    public UserVM (User user) {
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
    }
}
