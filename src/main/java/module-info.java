module com.example.clinicmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.clinicmanager to javafx.fxml;
    exports com.example.clinicmanager;
}