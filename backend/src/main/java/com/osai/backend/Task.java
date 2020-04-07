package com.osai.backend;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data 
@Entity
public class Task {

    private @Id @GeneratedValue Long id;
    private Long userID;
    private String title;
    private int minute;
    private int hour;
    private int mood;
    private int energy;
    private boolean inside;
    private String location;
    private int novelty;    
    private String time_of_day;
    private float temperature; 
    private long right_child;
    private long left_child;

    Task() {}

    Task(   
        Long user, 
        String title, 
        int minute, 
        int hour, 
        int mood,
        int energy, 
        boolean inside,
        String location, 
        int novelty, 
        String time_of_day,
        float temperature, 
        long right_child,
        long left_child
    ){
        this.userID = user;
        this.title = title;
        this.minute = minute;
        this.hour = hour;
        this.mood = mood;
        this.energy = energy;
        this.inside = inside;
        this.location = location;
        this.novelty = novelty;
        this.time_of_day = time_of_day;
        this.temperature = temperature;
        this.right_child = right_child;
        this.left_child = left_child;
    }

    //Getters 
=   public Long getUserById() { return this.userID; }
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

}