package com.Motiv.Motiv.Models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Column(name="id")
    private UUID id;

    @NotEmpty(message = "Name cannot be blank")
    @Column(name="name")
    private String name;

    @NotEmpty(message ="Age cannot be empty")
    @Min(value =13, message= "Age must not be less than 13")
    @Max(value =150, message= "Age must not be More than 150")
    @Column(name="age")
    private int age;



    @NotEmpty(message ="Weight cannot be empty")
    @Min(value =30, message= "Weight bust not be less than 30kg")
    @Max(value =250, message= "Age must not be More than 250kg")
    @Column(name="weight")
    private double weight;

    @NotEmpty
    @Min(value = 90, message ="Height must not be less than 90cm ")
    @Max(value = 272, message ="Height must not be more than 272 cm ")
    @Column(name="height")
    private int height;

    
    @Column(name="createdAt")
    private Instant createdAt;

    @Column(name="updatedAt")
    private Instant updatedAt;


    @Column(name="auth_id", columnDefinition = "uuid")
    private UUID authUserId;









    //constructors 

    //No args
    public UserModel(){}


    //Args

    public UserModel(String name, int age, double weight, int height, UUID authUserId){
        this.name = name;
        this.age = age;
        this.weight= weight;
        this.height=height;
        this.authUserId= authUserId;
        this.createdAt= Instant.now();
        this.updatedAt=Instant.now();
    }


    
    
    public UserModel(String name, int age, double weight, int height){
        this.name = name;
        this.age = age;
        this.weight= weight;
        this.height=height;
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

