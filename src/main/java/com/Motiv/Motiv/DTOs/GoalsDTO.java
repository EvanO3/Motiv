package com.Motiv.Motiv.DTOs;

import com.Motiv.Motiv.Enums.GoalType;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;

public class GoalsDTO {
    @NonNull
    private GoalType goalType;
    @NotNull
    private Double targetWeight;


    
    public GoalType getGoalType() {
        return goalType;
    }
    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }
    public Double getTargetWeight() {
        return targetWeight;
    }
    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }
    public GoalsDTO(GoalType goalType, Double targetWeight) {
        this.goalType = goalType;
        this.targetWeight = targetWeight;
    }

    
    
}
