package com.Motiv.Motiv.DTOs;

import java.util.UUID;

import com.Motiv.Motiv.Enums.ActivityLevel;
import com.Motiv.Motiv.Enums.Experience;



public class UserDTO {
    

    private String name;
    private double weight;
    private int height;
    private int age;
    private UUID authUserId;
    private Experience experience;
    private ActivityLevel activityLevel;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public UserDTO(String name, double weight, int height, int age, Experience experience, ActivityLevel activityLevel){
        this.name= name;
        this.weight=weight;
        this.height=height;
        this.age= age;
        this.experience= experience;
        this.activityLevel= activityLevel;


    }

    public UserDTO(String name, double weight, int height, int age, Experience experience, ActivityLevel activityLevel, UUID authUserId){
        this.name= name;
        this.weight=weight;
        this.height=height;
        this.age= age;
        this.experience= experience;
        this.activityLevel= activityLevel;
        this.authUserId = authUserId;
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


    public UserDTO(){

    }

    public int getAge(){
        return age;
    }

public void setAge(int age){
    this.age= age;
}

public double getWeight(){
    return weight;

}
}
