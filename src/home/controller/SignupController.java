package home.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import home.Main;
import home.model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SignupController {

	
	@FXML
	private TextField txtFName;
	
	@FXML
	private TextField txtLName;
	
	@FXML
	private TextField txtUName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField txtPass;
	
	@FXML
	private PasswordField txtConfirmPass;
	
	@FXML
	private ComboBox<String> cbUserType;
	
	@FXML
	private Button btnSignup;
	
	@FXML
	private Label lblError;
	
	
	
	
	@FXML
	public void initialize() {
		cbUserType.getItems().removeAll(cbUserType.getItems());
		cbUserType.getItems().addAll(list());
		//cbUserType.getSelectionModel().select(list().get(0));
	}
	
	@FXML
	public void handleSignUpButton() throws IOException, SQLException {
		
		String fName = txtFName.getText();
		String lName = txtLName.getText();
		String uName = txtUName.getText();
		String email = txtEmail.getText();
		String confirmPass = txtConfirmPass.getText();
		String userType = cbUserType.getValue();
		
		if(signUpValidation()) {
			
			Member member = new Member();
			member.setfName(fName);
			member.setlName(lName);
			member.setuName(uName);
			member.setEmail(email);
			member.setPassword(confirmPass);
			member.setUserType(userType);
			
			
			member.createAccount();
			
			txtFName.clear();
	        txtLName.clear();
	        txtUName.clear();
	        txtEmail.clear();
	        txtPass.clear();
	        txtConfirmPass.clear();
	        cbUserType.getSelectionModel().clearSelection();
	        
	        showAlert(AlertType.INFORMATION, "Alert", "Registration successful!");
            
	        
	        Main m = new Main();
	        
	        m.changeScene("fxml/SignIn.fxml");
			
		}
		
		
		
	}
	
	public List<String> list() {
		List<String> userType = new ArrayList<>();
		
		userType.add("Member");
		userType.add("Maintenance Admin");
		
		return userType;
	}
	
	
	
	private boolean signUpValidation() {
		//boolean validation = true;
		
		if (txtFName.getText().isEmpty() || txtLName.getText().isEmpty() || txtUName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPass.getText().isEmpty() || txtConfirmPass.getText().isEmpty() || cbUserType == null) {
            lblError.setTextFill(Color.RED);
            lblError.setText("Please fill in all fields.");
            //validation = false;
            return false;
        }
		
		Member m = new Member();
		
		if(!m.checkUserName(txtUName.getText())) {
			
			lblError.setTextFill(Color.RED);
	        lblError.setText("User Name already exists.");
			//validation = false;
	        return false;
		}
		
		if(!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
	       {
	           lblError.setTextFill(Color.RED);
	           lblError.setText("Email Id Incorrect.");
	           //validation = false;
	           return false;
	       }
		
		if (!txtPass.getText().equals(txtConfirmPass.getText())) {
			lblError.setTextFill(Color.RED);
			lblError.setText("Passwords do not match.");
			//validation = false;
			return false;
        }
		
		
		return true;
	}
	
	private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

}
