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

@Data 
@Entity
public class Task {

    private @Id @GeneratedValue Long id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Task title is mandatory")
    private String title;

    @NotNull(message = "# of minutes is mandatory")
    private int minute;

    @NotNull(message = "# of hours is mandatory")
    private int hour;

    @NotNull(message = "Mood is mandatory")
    private int mood;

    @NotNull(message = "Energy is mandatory")
    private int energy;

    @NotNull(message = "Inside is mandatory")
    private boolean inside;

    @NotBlank(message = "Inside is mandatory")
    private String location;

    @NotNull(message = "Temperature is mandatory")
    private float temperature; 

    @NotBlank(message = "Time of day is mandatory")
    private String time_of_day;

    //Remaining values are initialized by the Task constructor
    private int novelty;    
    private long right_child;
    private long left_child;

    public Task() {}

    public Task(   
        String username, 
        String title, 
        int minute, 
        int hour, 
        int mood,
        int energy, 
        boolean inside,
        String location,
        float temperature,
        String time_of_day 
    ){
        this.username = username;
        this.title = title;
        this.minute = minute;
        this.hour = hour;
        this.mood = mood;
        this.energy = energy;
        this.inside = inside;
        this.location = location;
        this.temperature = temperature;
        this.time_of_day = time_of_day;
        this.novelty = 10;
        this.right_child = -1;
        this.left_child = -1;
    }

    //Getters 
    public Long getId() { return this.id; }
    public String getUser() { return this.username; }
    public String getTitle(){ return this.title; }
    public int[] getTime() { 
        int[] time = {this.hour, this.minute}; 
        return time;
    }
    public int getMood() { return this.mood; }
    public int getEnergy() { return this.energy; }
    public boolean getInside() { return this.inside; }
    public String getLocation() { return this.location; }
    public int getNovelty() { return this.novelty; }
    public String getTimeOfDay() { return this.time_of_day; }
    public float getTemperature() { return this.temperature; }
    public long[] getChildNodes() { 
        long[] children = {this.left_child, this.right_child};
        return children;
    }

    //Setters
    public void setTitle(String title){ 
        this.title = title; 
    }
    public void setTime(int hour, int minute) { 
        this.hour = hour;
        this.minute = minute;
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
    public void setLocation(String location) { 
        this.location = location; 
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
    public void setLeftChild(Long left) { 
        this.left_child = left;
    }
    public void setRightChild(Long right) {
        this.right_child = right;
    }
    public void setId(Long id) {
        this.id = id;
    }

}