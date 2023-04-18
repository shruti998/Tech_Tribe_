package home.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import home.model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class ForgotPassword {
	@FXML
    private TextField txtUName;
	
    @FXML
    private PasswordField txtPass;
    
    @FXML
    private PasswordField txtPass1;

    @FXML
    private void handleForgotPassword() {
    	
    	if(forgotPassValidation()) {
    		
    		String uName = txtUName.getText();
            String newPassword = txtPass.getText();
            
            
        
            if (updatePassword(uName, newPassword)) {
                showAlert(AlertType.INFORMATION, "Password Updated", "Your password has been updated successfully.");
                
                txtUName.clear();
                txtPass.clear();
                txtPass1.clear();
                
                
                
            } else {
                showAlert(AlertType.ERROR, "Update Failed", "Failed to update the password.");
            }
    		
    	}
    	
        
        
    }
    

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE userTable SET password = ? WHERE uName = ?;";
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
    
    
    private boolean forgotPassValidation() {
		//boolean validation = true;
		
    	if(txtUName.getText().isBlank() || txtUName.getText().isEmpty() ) {
    		showAlert(AlertType.ERROR, "Error", "Fields are Empty!!!");
            return false;
        }
		
		Member m = new Member();
		
		if(m.checkUserName(txtUName.getText())) {
			
			showAlert(AlertType.ERROR, "Error", "User Name does not exist.");
	        return false;
		}
		
		
		if (!txtPass.getText().equals(txtPass1.getText())) {
			
			showAlert(AlertType.ERROR, "Error", "Passwords do not match.");
			return false;
        }
		
		
		return true;
	}
    
    

}


