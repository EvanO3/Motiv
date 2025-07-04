package com.Motiv.Motiv.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Motiv.Motiv.Models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
     
    Optional<UUID> findUserById(UUID userId);

    @Query(value ="SELECT id from users where authUserId = :autherUserId", nativeQuery=true)
    Optional<UUID> findIdByAuthUserId(UUID authUserId);
    

}
