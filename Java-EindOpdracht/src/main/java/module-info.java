module com.example.javaeindopdracht {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaeindopdracht to javafx.fxml;
    exports com.example.javaeindopdracht;

    exports Model;
    opens Model to javafx.fxml;

}