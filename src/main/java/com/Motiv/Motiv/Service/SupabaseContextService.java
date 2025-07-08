package com.Motiv.Motiv.Service;



import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class SupabaseContextService {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void setUserContext(String userId){
        entityManager.createNativeQuery("select set_config('jwt.claims.sub', :userId, true)")
        .setParameter("userId", userId)
        .getSingleResult();
    }
    
}
