package home.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class ForgotPassword {
	@FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleForgotPassword() {
        String email = emailField.getText();
        String newPassword = passwordField.getText();
    
        if (updatePassword(email, newPassword)) {
            showAlert(AlertType.INFORMATION, "Password Updated", "Your password has been updated successfully.");
        } else {
            showAlert(AlertType.ERROR, "Update Failed", "Failed to update the password.");
        }
        
    }
    

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?;";
        try (Connection connection = DatabaseConnection.Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


