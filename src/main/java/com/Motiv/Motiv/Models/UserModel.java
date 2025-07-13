package com.Motiv.Motiv.Models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.Motiv.Motiv.Enums.ActivityLevel;
import com.Motiv.Motiv.Enums.Experience;
import com.Motiv.Motiv.Enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
/*make the app also send daily water reminders. i.e remeber to drink 3L per day */
@Entity
@Table(name="Users")
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id" ,updatable=false, nullable=false)
    private UUID id;

    @Override
    public String toString() {
        return "UserModel [id=" + id + ", name=" + name + ", age=" + age + ", weight=" + weight + ", height=" + height
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", authUserId=" + authUserId + "]";
    }


    @NotEmpty(message = "Name cannot be blank")
    @Column(name="name")
    private String name;

    @Min(value =13, message= "Age must not be less than 13")
    @Max(value =150, message= "Age must not be More than 120")
    @Column(name="age")
    private int age;



    @Min(value =30, message= "Weight bust not be less than 30kg")
    @Max(value =500, message= "Age must not be More than 250kg")
    @Column(name="weight")
    private double weight;

 
    @Min(value = 90, message ="Height must not be less than 90cm ")
    @Max(value = 272, message ="Height must not be more than 272 cm ")
    @Column(name="height")
    private int height;

    
    @Column(name="createdAt")
    private Instant createdAt;

    @Column(name="updatedAt")
    private Instant updatedAt;


    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Roles roles;


    public List<GoalsModel> getGoals() {
        return goals;
    }


    public void setGoals(List<GoalsModel> goals) {
        this.goals = goals;
    }


    @Column(name="auth_id", columnDefinition = "uuid")
    private UUID authUserId;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <GoalsModel> goals;
        

    //add experience level with training
    @Column(name="experience")
    @Enumerated(EnumType.STRING)
    private Experience experience;

    //add the users activity level
    @Column(name="activity_status")
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;
   
    








    //constructors 

    //No args
    public UserModel(){}


    //Args

    public UserModel(String name, int age, double weight, int height, Experience experience, ActivityLevel activityLevel, UUID authUserId){
        this.name = name;
        this.age = age;
        this.weight= weight;
        this.height=height;
        this.roles =Roles.USER;
        this.experience= experience;
        this.activityLevel=activityLevel;
        this.authUserId= authUserId;
        this.createdAt= Instant.now();
        this.updatedAt=Instant.now();
    }

    public Roles getRoles(){
        return roles;
    }


    public void setRoles(Roles roles){
        this.roles =roles;
    }
    
    
    public Experience getExperience() {
        return experience;
    }


    public void setExperience(Experience experience) {
        this.experience = experience;
    }


    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }


    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }


    public UserModel(String name, int age, double weight, int height, Experience experience, ActivityLevel activityLevel){
        this.name = name;
        this.age = age;
        this.weight= weight;
        this.height=height;
        this.experience = experience;
        this.activityLevel=activityLevel;
        this.roles =Roles.USER;
        this.createdAt= Instant.now();
        this.updatedAt=Instant.now();

    }
    

    //getters and setters


  


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }


    public UUID getId() {
        return id;
    }


    public void setUserId(UUID id) {
        this.id = id;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }



    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
   

}

