package com.whatHappened.ws.auth;

import com.whatHappened.ws.user.User;
import com.whatHappened.ws.user.UserRepository;
import com.whatHappened.ws.user.vm.UserVM;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    UserRepository userRepository;
    TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        super();
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
       // this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(Credentials credentials) throws AuthException {
        User inDB = userRepository.findByUsername(credentials.getUsername());
        if(inDB == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());
        if(!matches) {
            throw new AuthException();
        }
        UserVM userVM = new UserVM(inDB);
        String token = generateRandomToken();

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(inDB);
        tokenRepository.save(tokenEntity);

        AuthResponse response = new AuthResponse();
        response.setUser(userVM);
        response.setToken(token);
        return response;
    }

    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void clearToken (String token) {
        tokenRepository.deleteById(token);
    }

    @Transactional
    public UserDetails getUserDetails(String token) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        if(!optionalToken.isPresent()) {
            return null;
        }
        return optionalToken.get().getUser();
    }
}
