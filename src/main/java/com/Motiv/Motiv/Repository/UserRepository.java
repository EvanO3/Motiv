package com.Motiv.Motiv.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Motiv.Motiv.Enums.Roles;
import com.Motiv.Motiv.Models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
     
    Optional<UUID> findUserById(UUID userId);
    /**
     * This is a raw sql query based on the actual DB table, if the type is stored differently from
     * the db and the object, conversion must be done when querying
     **/
    @Query(value ="SELECT id from users where auth_id = :authUserId", nativeQuery=true)
    Optional<UUID> findIdByAuthUserId(UUID authUserId);
    
    
    //This is a JPQL query(JPA QUERY) it is based on the java entites created in the entity class
    @Query(value="SELECT u.roles from UserModel u where u.authUserId = :authUserId")
    Roles findRoleByAuthUserId(@Param("authUserId") UUID authUserId);

    //This will return all the users information given their id
     Optional <UserModel> findById(UUID id);
}
