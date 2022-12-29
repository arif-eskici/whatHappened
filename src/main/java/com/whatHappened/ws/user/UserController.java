package com.whatHappened.ws.user;

import com.whatHappened.ws.shared.CurrentUser;
import com.whatHappened.ws.shared.GenericResponse;
import com.whatHappened.ws.user.vm.UserVM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public GenericResponse createUser (@RequestBody User user) {
       userService.save(user);
       return new GenericResponse("User created");
    }

    @GetMapping("/users")
    Page<UserVM> getUsers(Pageable page, @CurrentUser User user) {
        return userService.getUsers(page, user).map(UserVM::new);
    }



}
