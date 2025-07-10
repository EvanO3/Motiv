package com.Motiv.Motiv.DTOs;

import java.util.UUID;



public class UserDTO {
    

    private String name;
    private double weight;
    private int height;
    private int age;
    private UUID authUserId;


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


    public UserDTO(String name, double weight, int height, int age){
        this.name= name;
        this.weight=weight;
        this.height=height;
        this.age= age;


    }

    public UserDTO(String name, double weight, int height, int age, UUID authUserId){
        this.name= name;
        this.weight=weight;
        this.height=height;
        this.age= age;
        this.authUserId = authUserId;
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
