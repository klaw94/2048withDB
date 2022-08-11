package com.example.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;


public class Person {

    @NotBlank
    private final String name;

    private int score;

    public Person (
            @JsonProperty("name") String name,
            @JsonProperty("score") int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
