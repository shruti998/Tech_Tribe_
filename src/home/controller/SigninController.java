package home.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import home.Main;
import home.model.MaintenanceAdmin;
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
    
    @FXML
    private ComboBox<String> cbUserTypeLogin;
    
    
    @FXML
	public void initialize() {
		
    	cbUserTypeLogin.getItems().removeAll(cbUserTypeLogin.getItems());
    	cbUserTypeLogin.getItems().addAll(userTypeList());
    	
    	
	}
    
    
    @Override
    public void userLogIn(ActionEvent event) throws IOException, SQLException {
    	
    	txtError.setText("");
    	
    	if(txtUName.getText().isEmpty() || txtUName.getText().isBlank() && txtPass.getText().isEmpty() || txtPass.getText().isBlank()) {
    		
            txtError.setText("Please enter your data.");
            
        } else if(cbUserTypeLogin.getValue() == null) {
        	
        	showAlert(AlertType.ERROR, "Error", "Select User Type!!!");
        	
        } else {
        	if(cbUserTypeLogin.getValue().equals("Member")) {
        		
        		Member member = new Member();
            	if(member.checkMemberLogin(txtUName.getText(), txtPass.getText())) {
            		String uName = txtUName.getText();
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Dashboard.fxml"));
                    Parent root = loader.load();
                    
                    DashboardController dashboardController = loader.getController();
                    dashboardController.displayInfo(uName);
                    
//                    Stage stage = new Stage();
//                    stage.setScene(new Scene(root));
//                    stage.setTitle("Dashboard");
//                    stage.show();
                    
                    Main m = new Main();
                    
                    m.changeStage(root);
            		
            	} else {
            		
            		showAlert(AlertType.ERROR, "Error", "User Name or Password is incorrect!!!");
            		
            	} 
            	
        	} else if(cbUserTypeLogin.getValue().equals("Maintenance Admin")) {
        		
        		MaintenanceAdmin mAdmin = new MaintenanceAdmin();
            	if(mAdmin.checkAdminLogin(txtUName.getText(), txtPass.getText())) {
            		String uName = txtUName.getText();
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Maintenance.fxml"));
                    Parent root = loader.load();
                    
                    //System.out.println(uName);
                    MaintenanceController maintenanceController = loader.getController();
                    maintenanceController.displayInfo(uName);
               
                    
                    Main m = new Main();
                    m.changeStage(root);
                    
                    
            		
            	} else {
            		
            		showAlert(AlertType.ERROR, "Error", "User Name or Password is incorrect!!!");
            		
            	}
        		
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
    
    public List<String> userTypeList() {
		List<String> userType = new ArrayList<>();
		
		userType.add("Member");
		userType.add("Maintenance Admin");
		
		
		return userType;
	}
    
    
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
    public void changeScene(String fxml,String uName) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        
        RaiseQueryController raiseQueryController = loader.getController();
        raiseQueryController.displayInfo(uName);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Raise Query");
        stage.show();
       
    }
		
	

}



