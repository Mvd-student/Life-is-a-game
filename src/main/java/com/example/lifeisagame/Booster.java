package com.example.lifeisagame;


public class Booster extends Item {
    private int multiplier;

    public Booster(String name, int price, int multiplier) {
        super(name, price);
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
