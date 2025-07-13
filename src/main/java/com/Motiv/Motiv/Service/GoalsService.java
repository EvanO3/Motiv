package com.Motiv.Motiv.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.Motiv.Motiv.DTOs.GoalsDTO;
import com.Motiv.Motiv.Exceptions.AuthIdNotFoundException;
import com.Motiv.Motiv.Models.GoalsModel;
import com.Motiv.Motiv.Models.UserModel;
import com.Motiv.Motiv.Repository.GoalRepository;
import com.Motiv.Motiv.Repository.UserRepository;
import com.Motiv.Motiv.Security.JwtUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class GoalsService {
    
    @Autowired
    private GoalRepository goalsRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(GoalsService.class);

    /**Error logging can be fine tuned */
    @Transactional
    public List<GoalsModel> goalSetup(String token, @Valid List<GoalsDTO> goalDTO)throws Exception{
         try{
            //apply rls
            jwtUtils.applyRLSContextFromToken(token);
            UUID userId = getUsersId(token);
            if(userId ==null){
                throw new IllegalStateException("Failed to retrieve the users id");
            }
            // start by initalizing all the variables needed for the goals, 
            //store the information in the goals model, then save it to db
            UserModel user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User Not found"));

            //.Stream() converts the list into a stream (pipleline where you can process each item one by one)
            List<GoalsModel> goals = goalDTO.stream().map(dto -> new GoalsModel(dto.getGoalType(), dto.getTargetWeight(), user))
            .collect(Collectors.toList());
            return goalsRepository.saveAll(goals);           
            

         }catch(DataAccessException e){
            //add exception stuff
            logger.error("Error accessing the users data", e);
            throw new Exception("Failed to add information to the database", e);
         }
    }



    //Helper function to retrieve user Id given the authUserId
    private UUID getUsersId(String Token) throws AuthIdNotFoundException{
        try{
        //This will return the authenticated id not users id
          String authId = jwtUtils.getSubFromClaim(Token);    
          if(authId.isEmpty()){
            throw new AuthIdNotFoundException("Failed to retrieve the Users Auth id");
          } 
          //Converting the retrieved auth id into a uuid
          UUID conversionAuthId = UUID.fromString(authId);
          Optional<UUID> userId = userRepository.findIdByAuthUserId(conversionAuthId);
          if(!userId.isPresent()){
            throw new Exception("Failed to retrieve the users Id");
          }
          return userId.get();
               
        } catch(Exception e){
            logger.error("Database Retrieval Fail ", e);
            throw new Exception("Failed to retrieve the users Id from the database")
        }
    }
}
