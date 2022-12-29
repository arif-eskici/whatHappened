package com.whatHappened.ws.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whatHappened.ws.error.NotFoundException;


@Service
public class UserService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }


    public void save (User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Page<User> getUsers(Pageable page, User user) {
        if (user != null) {
            return userRepository.findByUsernameNot(user.getUsername(), page);
        }
        return userRepository.findAll(page);
    }

    public User getByUsername(String username) {
        User inDB = userRepository.findByUsername(username);
        if (inDB == null) {
            throw new NotFoundException();
        }
        return inDB;
    }







}
