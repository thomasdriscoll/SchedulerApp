/*
    Author: Thomas Driscoll
    Date: 18 April 2020
    Remaining work: 
*/

package com.osai.backend.task;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// https://stackoverflow.com/questions/3405229/specifying-an-index-non-unique-key-using-jpa
// Very helpful
@Data 
@Entity
public class Task {

    private @Id @GeneratedValue Long id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Task title is mandatory")
    private String title;

    @NotNull(message = "# of minutes is mandatory")
    private int time;

    @NotNull(message = "Mood is mandatory")
    private int mood;

    @NotNull(message = "Energy is mandatory")
    private int energy;

    @NotNull(message = "Inside is mandatory")
    private boolean inside;

    @NotNull(message = "Latitude is necessary")
    private double latitude;

    @NotNull(message = "Latitude is necessary")
    private double longitude;

    @NotNull(message = "Temperature is mandatory")
    private float temperature; 

    @NotBlank(message = "Time of day is mandatory")
    private String time_of_day;

    //Remaining values are initialized by the Task constructor
    private int novelty;    
    private String ancestry;
    private int left_child;
    private int right_child;

    public Task() {}

    public Task(   
        String username, 
        String title, 
        int time, 
        int mood,
        int energy, 
        boolean inside,
        double latitude,
        double longitude,
        float temperature,
        String time_of_day 
    ){
        this.username = username;
        this.title = title;
        this.time = time;
        this.mood = mood;
        this.energy = energy;
        this.inside = inside;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.time_of_day = time_of_day;
        this.novelty = 10;
        this.ancestry = null;
        this.left_child = -1;
        this.right_child = -1;
    }

    //Getters 
    public Long getId() { return this.id; }
    public String getUser() { return this.username; }
    public String getTitle(){ return this.title; }
    public int getTime() { return this.time;  }
    public int getMood() { return this.mood; }
    public int getEnergy() { return this.energy; }
    public boolean getInside() { return this.inside; }
    public double getLatitude() { return this.latitude; }
    public double getLongitude() { return this.longitude; }
    public int getNovelty() { return this.novelty; }
    public String getTimeOfDay() { return this.time_of_day; }
    public float getTemperature() { return this.temperature; }
    public String getAncestry() { return this.ancestry; }
    public int getLeftChild() { return this.left_child; }
    public int getRightChild() { return this.right_child; }


    //Setters
    public void setTitle(String title){ 
        this.title = title; 
    }
    public void setTime(int time) { 
        this.time = time;
    }
    public void setMood(int mood) { 
        this.mood = mood; 
    }
    public void setEnergy(int energy) { 
        this.energy = energy; 
    }
    public void setInside(boolean inside) { 
        this.inside = inside;
    }
    public void setLocation(double latitude, double longitude) { 
        this.latitude = latitude; 
        this.longitude = longitude;
    }
    public void setNovelty(int novelty) { 
        this.novelty = novelty; 
    }
    public void setTimeOfDay(String time_of_day) { 
        this.time_of_day = time_of_day; 
    }
    public void setTemperature(float temp) { 
        this.temperature = temp; 
    }
    public void setAncestry(String ancestry) {
        this.ancestry = ancestry;
    }
    public void setId(long i) {
        this.id = i;
    }
    public void setLeftChild(int i){
        this.left_child = i;
    }
    public void setRightChild(int i){
        this.right_child = i;
    }

}