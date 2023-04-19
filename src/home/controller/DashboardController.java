package home.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
	
	public void onSplitBillClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
       	 URL url = new File("src/home/fxml/SplitBillLandingPage.fxml").toURI().toURL();
      
       	 FXMLLoader loader = new FXMLLoader(url);
       	 
       	 SplitBillCreateGroups controller = new SplitBillCreateGroups();
       	 controller.setUsername(lblUName.getText());
       	 
       	 loader.setController(controller);
       	 
       	 Parent root = loader.load();
       	 Scene scene = new Scene(root);
       	 stage.setScene(scene);
       	 stage.show();
        } catch (IOException e) {
       	 System.err.println(String.format("Error: %s", e.getMessage()));
        }
	}
	
	public void logOut() throws IOException {
        
        
        Main m = new Main();
        
        m.changeScene("fxml/SignIn.fxml");
		
	}

}
