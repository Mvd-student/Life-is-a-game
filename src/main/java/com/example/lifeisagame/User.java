package com.example.lifeisagame;
import java.util.ArrayList;

public class User {
    private String name;
    private String username;
    private int balance;
    private int streak;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ArrayList<CustomTask> customTasks = new ArrayList<CustomTask>();
    private ArrayList<Item> items = new ArrayList<Item>();

    public User(String name, String username, int balance, int streak) {
        this.name = name;
        this.username = username;
        this.balance = balance;
        this.streak = streak;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getBalance() {
        return this.balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStreak() {
        return this.streak;
    }
    public void setStreak(int streak) {
        this.streak = streak;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }
    public void addItem(Item item) {
        this.items.add(item);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public ArrayList<CustomTask> getCustomTasks() {
        return this.customTasks;
    }
    public void addCustomTask(CustomTask customTask) {
        this.customTasks.add(customTask);
    }
    public void removeCustomTask(CustomTask customTask) {
        this.customTasks.remove(customTask);
    }
}
