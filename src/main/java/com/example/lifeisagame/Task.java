package com.example.lifeisagame;

public class Task {
    private String name;
    private int reward;

    public Task(String name, int reward) {
        this.name = name;
        this.reward = reward;
    }

    public String getName() {
        return this.name;
    }
    public int getReward() {
        return this.reward;
    }
}
