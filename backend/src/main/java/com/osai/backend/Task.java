package com.osai.backend;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data 
@Entity
public class Task {

    private @Id @GeneratedValue Long id;
    private String user;
    private int minutes;
    private int hours;
    private int energy;
    private String location;
    private int novelty;
    private float temperature; 

    Task() {}

    Task(int minutes, int hours, int energy, String location, int novelty, float temperature){
        this.minutes = minutes;
        this.hours = hours;
        this.energy = energy;
        this.location = location;
        this.novelty = novelty;
        this.temperature = temperature;
    }
}