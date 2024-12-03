package com.example.clinicmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the Clinic Manager application.
 * This class launches the JavaFX application and loads the primary view.
 * It sets up the main window (stage) with the specified dimensions and title.
 * 
 * <p>This application allows users to schedule, reschedule, and cancel appointments
 * for a medical clinic, as well as manage various clinic-related records.</p>
 * 
 * @see javafx.application.Application
 */

 public class ClinicManagerMain extends Application {
   /**
     * The entry point for launching the JavaFX application.
     * This method sets up the primary stage, loads the FXML layout, 
     * and displays the main scene.
     *
     * @param stage The primary stage for the application, provided by JavaFX.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Clinic Manager");
        stage.setScene(scene);
        stage.show();
    }
/**
     * The main method that launches the JavaFX application.
     * This method is the application's entry point.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}
