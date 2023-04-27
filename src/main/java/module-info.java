module com.example.converseaienhanced {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.converseaienhanced to javafx.fxml;
    exports com.example.converseaienhanced;
}