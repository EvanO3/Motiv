package com.Motiv.Motiv.Models;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.Motiv.Motiv.Annotations.ValidDateRange;
import com.Motiv.Motiv.Enums.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="Goals")
//This will allow for date validations on start and end date
@ValidDateRange
public class GoalsModel {


    @Id
    @Column(name="g_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserModel user;

    @Column(name="goal_type")
    @Enumerated(EnumType.STRING)
    private Type goalType;

    // in service method, validate the dates to ensure the start date cannot come after the endDate
    
    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="deadline")
    private LocalDate endDate;

    @Column(name="target_weight")
    @Min(value=90, message="Minimum value for target weight must be >=90")
    private Double targetWeight;

    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }



}
