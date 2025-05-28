package com.Motiv.Motiv.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
     
    Optional<UUID> findUserById(UUID id);

    @Query(value ="SELECT id from users where authUserId = :autherUserId", nativeQuery=true)
    Optional<UUID> findIdByAuthUserId(UUID authUserId);
    

}
