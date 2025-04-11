module com.example.lifeisagame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lifeisagame to javafx.fxml;
    exports com.example.lifeisagame;
}