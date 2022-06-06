package com.pizzashop.userservice.query.api.service.impl;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.command.api.entity.Users;
import com.pizzashop.userservice.command.api.repository.UserRepository;
import com.pizzashop.userservice.command.api.service.impl.JwtService;
import com.pizzashop.userservice.query.api.service.UserQueryService;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserQueryServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto validateToken(String token) throws Exception {
        String name = this.jwtService.extractName(token);
        if (!this.jwtService.validateToken(token, name)) {
            throw new Exception("Token was expired");
        }
        Optional<Users> userOptional = this.userRepository.getUsersByLogin(name);
        if (userOptional.isEmpty()) throw new Exception("User not found");
        Users user = userOptional.get();
        return UserDto.builder()
                .id(user.getId())
                .token(token)
                .name(user.getLogin())
                .build();
    }

    @Override
    public Users getUserById(String id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public UserDto signIn(String login, char[] password) throws Exception {
        Users user = this.userRepository.getUsersByLogin(login)
                .orElseThrow(() -> new Exception("User not found"));

        if (!passwordEncoder.matches(CharBuffer.wrap(password), user.getPassword())) {
            throw new Exception("Wrong login or password");
        }
        return UserDto.builder()
                .id(user.getId())
                .name(user.getLogin())
                .token(this.jwtService.generateToken(login))
                .build();
    }
}
