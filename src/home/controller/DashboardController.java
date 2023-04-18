package home.controller;

import java.io.IOException;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
	
	@FXML
    private Button btnBill;

    @FXML
    private Button btnChores;

    @FXML
    private Button btnGroups;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMaintenance;
    
    @FXML
    private Label lblUName;
    
    
    @FXML
	public void initialize() {
		
		
		
	}
	
    
	public void displayInfo(String uName) {
		lblUName.setText(uName);
	}
	
	public void raiseMaintenance(ActionEvent event) throws IOException  {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AddQuery.fxml"));
        Parent root = loader.load();
       
        RaiseQueryController raiseQueryController = loader.getController();
        raiseQueryController.displayInfo(lblUName.getText());
   
        
        Main m = new Main();
        m.changeStage(root);
		
		
	}
	
	public void handleChores(ActionEvent event) throws IOException  {
		
//		Main m = new Main();
//        
//        m.changeScene("fxml/Chore.fxml");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Chore.fxml"));
        Parent root = loader.load();
        
        ViewChoreController viewChoreController = loader.getController();
        viewChoreController.initialize(lblUName.getText());
       
        
        Main m = new Main();
        m.changeStage(root);
		
		
	}
	
	public void createGroups(ActionEvent event) throws IOException  {
		
//		Main m = new Main();
//        
//        m.changeScene("fxml/CreateHomeGroup.fxml");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CreateHomeGroup.fxml"));
        Parent root = loader.load();
       
        CreateHomeGroupController createHomeController = loader.getController();
        createHomeController.initialize(lblUName.getText());
   
   
        
        Main m = new Main();
        m.changeStage(root);
		
		
	}
	
	public void logOut() throws IOException {
        
        
        Main m = new Main();
        
        m.changeScene("fxml/SignIn.fxml");
		
	}

}
