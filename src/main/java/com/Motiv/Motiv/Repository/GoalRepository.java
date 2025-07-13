package com.Motiv.Motiv.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Motiv.Motiv.Models.GoalsModel;
import java.util.List;
import com.Motiv.Motiv.Enums.GoalType;

@Repository
public interface GoalRepository extends JpaRepository<GoalsModel, UUID> {
    
    Optional<GoalsModel> getGoalsById(UUID Id);
    Optional<GoalsModel>findByGoalType(GoalType goalType);
    @Query(value ="SELECT g.goals from goals g where g.user_id = :id")
    Optional<GoalsModel>findAllUserGoals(UUID Id);
}
