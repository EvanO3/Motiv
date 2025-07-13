package com.Motiv.Motiv.Validators;

import com.Motiv.Motiv.Annotations.ValidDateRange;
import com.Motiv.Motiv.Models.GoalsModel;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, GoalsModel> {
    public boolean isValid(GoalsModel goal, ConstraintValidatorContext context){
        if(goal.getStartDate() ==null || goal.getEndDate() == null){
            return true;
        }
        return goal.getStartDate().isBefore(goal.getEndDate());
    }

  
}
