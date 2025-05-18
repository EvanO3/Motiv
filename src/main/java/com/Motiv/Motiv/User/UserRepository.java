package com.Motiv.Motiv.User;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
     
}
