package com.pizzashop.userservice.command.api.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzashop.userservice.command.api.commands.CreateUserCommand;
import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.command.api.data.UserRestModel;
import com.pizzashop.userservice.command.api.entity.Users;
import com.pizzashop.userservice.command.api.repository.UserRepository;
import com.pizzashop.userservice.command.api.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public CreateUserCommand addUser(UserRestModel userRestModel) {
        return CreateUserCommand.builder()
                .userId(UUID.randomUUID().toString())
                .age(userRestModel.getAge())
                .login(userRestModel.getLogin())
                .password(passwordEncoder.encode(userRestModel.getPassword()))
                .build();
    }

    @Override
    public String getToken(String userName) {
        return this.jwtService.generateToken(userName);
    }
//
//    @Override
//    @Transactional
//    public UserDto signUp(UserRestModel userRestModel) throws Exception {
//        Optional<Users> user = this.userRepository.getUsersByLogin(userRestModel.getLogin());
//        if (user.isPresent()) {
//            throw new Exception("User is already exist");
//        }
//        Users userSaved = this.userRepository.save(Users.builder()
//                .age(userRestModel.getAge())
//                .login(userRestModel.getLogin())
//                .password(userRestModel.getPassword())
//                .build());
//        return UserDto.builder()
//                .id(userSaved.getId())
//                .name(userSaved.getLogin())
//                .token(this.createToken(userSaved))
//                .build();
//    }
}
