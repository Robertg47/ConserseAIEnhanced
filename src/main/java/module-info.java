module com.example.converseaienhanced {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.converseaienhanced to javafx.fxml;
    exports com.example.converseaienhanced;
    exports com.example.converseaienhanced.Controller;
    opens com.example.converseaienhanced.Controller to javafx.fxml;
}