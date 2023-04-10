package home.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import home.Main;

public class SigninController {
	
	public SigninController() {
		
	}
	
	@FXML
	private Button btnLogin;
	@FXML
	private Label txtError;
	@FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPass;
    
    
    public void userLogIn(ActionEvent event) throws IOException, SQLException {
        checkLogin();

    }


	private void checkLogin() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		Main m = new Main();
		
		
      if(txtEmail.getText().isEmpty() && txtPass.getText().isEmpty()) {
            txtError.setText("Please enter your data.");
        }


        else {
        	try {
        	    Connection connection = DatabaseConnection.Connect();
        	    PreparedStatement pst = connection.prepareStatement("select * from userTable where emailId=? and password=?");
        	    pst.setString(1, txtEmail.getText());
        	    pst.setString(2, txtPass.getText());
        	    ResultSet resultSet = pst.executeQuery();
        	    if (resultSet.next()) {
        	    	m.changeScene("fxml/Home.fxml");
        	    } else {
        	    	
        	    	txtError.setText("Wrong email or password!");
        	    }
        	} catch (IOException e) {
        	    e.printStackTrace();
        	}
            
        }
    }
		
	

}



