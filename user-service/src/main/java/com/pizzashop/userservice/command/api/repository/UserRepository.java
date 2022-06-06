package com.pizzashop.userservice.command.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizzashop.userservice.command.api.entity.Users;

public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> getUsersByLogin(String login);
}