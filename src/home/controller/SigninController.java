package home.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import home.Main;
import home.model.Member;

public class SigninController implements SigninInterface{
	
	public SigninController() {
		
	}
	
	@FXML
	private Button btnLogin;
	@FXML
	private Label txtError;
	@FXML
    private TextField txtUName;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Hyperlink linkCreateAcc;
    @FXML
    private Hyperlink linkForgotPass;
    
    
    @Override
    public void userLogIn(ActionEvent event) throws IOException, SQLException {
    	
    	if(txtUName.getText().isEmpty() && txtPass.getText().isEmpty()) {
            txtError.setText("Please enter your data.");
        } else {
        	Member member = new Member();
        	if(member.checkLogin(txtUName.getText(), txtPass.getText())) {
        		Main m = new Main();
        		m.changeScene("fxml/Home.fxml");
        		
        	} else {
        		
        		showAlert(AlertType.ERROR, "Error", "User Name or Password is incorrect!!!");
        		
        	}
        	
        }

    }

    
    public void createAccount(ActionEvent event) throws IOException {
    	
    	Main m = new Main();
    	
    	m.changeScene("fxml/SignUp.fxml");
        

    }
    
    
    public void forgotPassword(ActionEvent event) throws IOException {
    	
    	Main m = new Main();
    	
    	m.changeScene("fxml/ForgotPassword.fxml");
        

    }
    
    
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
		
	

}



