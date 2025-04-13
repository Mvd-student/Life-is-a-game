package com.example.lifeisagame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HelloApplication extends Application {
    private Stage stage;
    private Scene scene;

    // Declare the currentUser globally to share across scenes
    User currentUser = new User("Michael", "Ailias", 1000, 0);

    // Example tasks
    Task task1 = new Task("Eat breakfast", 20);
    Task task2 = new Task("Go for a run", 30);
    Task task3 = new Task("Complete a project", 50);

    // Example store items
    Item item1 = new Item("Sword", 200);
    Item item2 = new Item("Shield", 150);
    Item item3 = new Item("Potion", 50);

    @FXML
    private Label balanceLabel;

    @FXML
    private VBox storeItemsVBox;

    @FXML
    private VBox taskListVBox; // Added for task list

    @FXML
    private VBox inventoryVBox;  // Added for the inventory items

    @Override
    public void start(Stage stage) throws IOException {
        // Load the DailyTaskList.fxml and pass currentUser to the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DailyTaskList.fxml"));
        Parent root = loader.load();

        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);  // Pass currentUser to controller

        // Add the task only once when the app starts
        if (currentUser.getTasks().isEmpty()) {
            currentUser.addTask(task1);
            currentUser.addTask(task2);
            currentUser.addTask(task3);
        }

        controller.updateBalance();
        controller.updateTasks();  // Call updateTasks() method

        Scene scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Daily Tasks");
        stage.setScene(scene);
        stage.show();
    }

    // Setter method to pass the currentUser to each controller
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    // Update the balance label in UI
    public void updateBalance() {
        if (balanceLabel != null) {
            balanceLabel.setText(Integer.toString(currentUser.getBalance()));
        }
    }

    // Method to update the store items dynamically
    public void updateStoreItems() {
        storeItemsVBox.getChildren().clear(); // Clear previous store items

        List<Item> storeItems = List.of(item1, item2, item3);

        // Display each item in the store
        for (Item item : storeItems) {
            Label nameLabel = new Label(item.getName());
            nameLabel.setPrefWidth(482);
            nameLabel.setPrefHeight(40);

            Label priceLabel = new Label("Price: " + item.getPrice());
            priceLabel.setPrefWidth(100);

            Button buyButton = new Button("Buy");
            buyButton.setPrefWidth(100);
            buyButton.setPrefHeight(35);
            buyButton.getStyleClass().add("customButton");

            buyButton.setOnAction(e -> {
                if (currentUser.getBalance() >= item.getPrice()) {
                    currentUser.setBalance(currentUser.getBalance() - item.getPrice());
                    updateBalance(); // Update balance after purchase
                    currentUser.addItem(item);
                    System.out.println("Bought: " + item.getName());
                } else {
                    System.out.println("Not enough balance!");
                }
            });

            HBox itemBox = new HBox(10);
            itemBox.setPrefWidth(1003);
            itemBox.setPrefHeight(40);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(nameLabel, priceLabel, buyButton);

            storeItemsVBox.getChildren().add(itemBox);
        }
    }

    // Method to update the inventory items dynamically
    public void updateInventoryItems() {
        inventoryVBox.getChildren().clear(); // Clear previous inventory items

        // Display each item in the user's inventory
        for (Item item : currentUser.getItems()) {
            Label nameLabel = new Label(item.getName());
            nameLabel.setPrefWidth(482);  // Adjust this based on your preferred width
            nameLabel.setPrefHeight(40);

            Label priceLabel = new Label("Price: " + item.getPrice());
            priceLabel.setPrefWidth(100);

            HBox itemBox = new HBox(10);
            itemBox.setPrefWidth(1003);
            itemBox.setPrefHeight(40);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(nameLabel, priceLabel);

            inventoryVBox.getChildren().add(itemBox);
        }
    }

    // Method to update the task list dynamically (fix for the updateTasks() error)
    public void updateTasks() {
        taskListVBox.getChildren().clear(); // Clear previous task entries

        // Display the tasks that are in the user's task list
        for (Task task : currentUser.getTasks()) {
            Label nameLabel = new Label(task.getName());
            nameLabel.setPrefWidth(482);  // Adjust this based on your preferred width
            nameLabel.setPrefHeight(40);

            Button doneButton = new Button("Finished");
            doneButton.setPrefWidth(133);
            doneButton.setPrefHeight(35);
            doneButton.getStyleClass().add("customButton");

            doneButton.setOnAction(e -> {
                System.out.println("Completed: " + task.getName());
                int newBalance = currentUser.getBalance() + task.getReward();
                currentUser.setBalance(newBalance);
                currentUser.getTasks().remove(task); // Remove task from user's list
                updateBalance(); // Update balance
                updateTasks(); // Refresh task list visually
            });

            HBox taskBox = new HBox(10);
            taskBox.setPrefWidth(1003);
            taskBox.setPrefHeight(40);
            taskBox.setAlignment(Pos.CENTER);
            taskBox.getChildren().addAll(nameLabel, doneButton);

            taskListVBox.getChildren().add(taskBox);
        }
    }

    // Switch to the Store page and pass currentUser to the new controller
    public void goToStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store.fxml"));
        Parent root = loader.load();

        // Pass currentUser to the controller of the new scene
        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);  // Pass currentUser to Store page
        controller.updateBalance();  // Update balance on the store page
        controller.updateStoreItems(); // Update store items

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Store");
        stage.setScene(scene);
        stage.show();
    }

    // Switch to the Daily Task List page and pass currentUser to the new controller
    public void goToDailyTaskList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DailyTaskList.fxml"));
        Parent root = loader.load();

        // Pass currentUser to the controller of the new scene
        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);  // Pass currentUser to the Daily Task List page
        controller.updateBalance();
        controller.updateTasks();  // Call updateTasks() to refresh tasks

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Daily Tasks");
        stage.setScene(scene);
        stage.show();
    }

    // Switch to the Custom Task List page and pass currentUser to the new controller
    public void goToCustomTaskList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomTaskList.fxml"));
        Parent root = loader.load();

        // Pass currentUser to the controller of the new scene
        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);  // Pass currentUser to the Custom Task List page
        controller.updateBalance();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Custom Tasks");
        stage.setScene(scene);
        stage.show();
    }

    public void goToInventory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
        Parent root = loader.load();

        // Pass currentUser to the controller of the new scene
        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);  // Pass currentUser to the Inventory page
        controller.updateBalance();
        controller.updateInventoryItems();  // Update inventory items

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
