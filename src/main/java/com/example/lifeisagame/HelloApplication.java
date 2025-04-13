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
import java.util.Objects;

public class HelloApplication extends Application {
    private Stage stage;
    private Scene scene;

    // Declare the currentUser globally to share across scenes
    User currentUser = new User("Michael", "Ailias", 1000, 0);

    Task task1 = new Task("Eat breakfast", 20);
    Task task2 = new Task("Go for a run", 30);
    Task task3 = new Task("Complete a project", 50);

    @FXML
    private Label balanceLabel;

    @FXML
    private VBox taskListVBox;

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
            // Add task1 to the task list
            currentUser.addTask(task2);
            currentUser.addTask(task3);
        }

        controller.updateBalance();
        controller.updateTasks();

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

    public void updateBalance() {
        if (balanceLabel != null) {
            balanceLabel.setText(Integer.toString(currentUser.getBalance()));
        }
    }

    public void updateTasks() {
        taskListVBox.getChildren().clear(); // Clear previous task entries

        // Display the tasks that are in the user's task list
        for (Task task : currentUser.getTasks()) {
            Label nameLabel = new Label(task.getName());
            nameLabel.setPrefWidth(482);
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
        controller.updateTasks();

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

    public static void main(String[] args) {
        launch();
    }
}
