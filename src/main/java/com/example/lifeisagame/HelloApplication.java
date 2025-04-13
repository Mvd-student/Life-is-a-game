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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HelloApplication extends Application {
    private Stage stage;
    private Scene scene;

    User currentUser = new User("Michael", "Ailias", 1000, 0);

    Task task1 = new Task("Eat breakfast", 20);
    Task task2 = new Task("Go for a run", 30);
    Task task3 = new Task("Complete a project", 50);

    Item item1 = new Item("Sword", 200);
    Item item2 = new Item("Shield", 150);
    Item item3 = new Item("Potion", 50);

    @FXML
    private Label balanceLabel;

    @FXML
    private VBox storeItemsVBox;

    @FXML
    private VBox taskListVBox;

    @FXML
    private VBox inventoryVBox;

    @FXML
    private TextField customTaskNameField;

    @FXML
    private VBox customTaskListVBox;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DailyTaskList.fxml"));
        Parent root = loader.load();

        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);

        if (currentUser.getTasks().isEmpty()) {
            currentUser.addTask(task1);
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void updateBalance() {
        if (balanceLabel != null) {
            balanceLabel.setText(Integer.toString(currentUser.getBalance()));
        }
    }

    public void updateStoreItems() {
        storeItemsVBox.getChildren().clear();
        List<Item> storeItems = List.of(item1, item2, item3);

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
                    updateBalance();
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

    public void updateInventoryItems() {
        inventoryVBox.getChildren().clear();
        for (Item item : currentUser.getItems()) {
            Label nameLabel = new Label(item.getName());
            nameLabel.setPrefWidth(482);
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

    public void updateTasks() {
        taskListVBox.getChildren().clear();

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
                currentUser.setBalance(currentUser.getBalance() + task.getReward());
                currentUser.getTasks().remove(task);
                updateBalance();
                updateTasks();
            });

            HBox taskBox = new HBox(10);
            taskBox.setPrefWidth(1003);
            taskBox.setPrefHeight(40);
            taskBox.setAlignment(Pos.CENTER);
            taskBox.getChildren().addAll(nameLabel, doneButton);

            taskListVBox.getChildren().add(taskBox);
        }
    }

    public void goToStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store.fxml"));
        Parent root = loader.load();

        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);
        controller.updateBalance();
        controller.updateStoreItems();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Store");
        stage.setScene(scene);
        stage.show();
    }

    public void goToDailyTaskList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DailyTaskList.fxml"));
        Parent root = loader.load();

        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);
        controller.updateBalance();
        controller.updateTasks();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Daily Tasks");
        stage.setScene(scene);
        stage.show();
    }

    public void goToCustomTaskList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomTaskList.fxml"));
        Parent root = loader.load();

        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);
        controller.updateBalance();
        controller.updateCustomTasks();

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

        HelloApplication controller = loader.getController();
        controller.setCurrentUser(currentUser);
        controller.updateBalance();
        controller.updateInventoryItems(); // ✅ This is what you want in the Inventory screen

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        stage.setTitle("Life is a game - Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public void addCustomTask(ActionEvent event) {
        String taskName = customTaskNameField.getText();

        if (taskName != null && !taskName.trim().isEmpty()) {
            CustomTask customTask = new CustomTask(taskName, 50);
            currentUser.addCustomTask(customTask);
            customTaskNameField.clear();
            updateCustomTasks();
        }
    }

    public void updateCustomTasks() {
        customTaskListVBox.getChildren().clear();

        for (Task task : currentUser.getCustomTasks()) {
            Label nameLabel = new Label(task.getName());
            nameLabel.setPrefWidth(482);
            nameLabel.setPrefHeight(40);

            Button doneButton = new Button("Finished");
            doneButton.setPrefWidth(133);
            doneButton.setPrefHeight(35);
            doneButton.getStyleClass().add("customButton");

            doneButton.setOnAction(e -> {
                System.out.println("Completed: " + task.getName());
                currentUser.setBalance(currentUser.getBalance() + task.getReward());
                currentUser.getCustomTasks().remove(task);
                updateBalance();
                updateCustomTasks();
            });

            HBox taskBox = new HBox(10);
            taskBox.setPrefWidth(1003);
            taskBox.setPrefHeight(40);
            taskBox.setAlignment(Pos.CENTER);
            taskBox.getChildren().addAll(nameLabel, doneButton);

            customTaskListVBox.getChildren().add(taskBox);
        }
    }
}
