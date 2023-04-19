package home.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import home.Main;

public class CreateHomeGroupController {
	private List<String> groupNames;
	// to be deleted
	private String  username;

    @FXML
    private TextField groupName;

    @FXML
    private ListView<String> listOfNames;

    @FXML
    private TextField name;
    
	@FXML
	private ChoiceBox<String> myChoiceBox;
	ArrayList<String > userNames = new ArrayList<String>();
	@FXML

    private Parent root;
    @FXML
    private TextArea address;
    
    @FXML
    private Button btnBack;
    
    
    
    
    
    
    @FXML
    void initialize(String username) {
    	this.username = username;
    	getMembersName();
    	myChoiceBox.getItems().addAll(userNames);
    	System.out.println(userNames.size());
    	myChoiceBox.setOnAction(this::getUser);
    	
    	
    }

    void getMembersName() {
    	
		try (Connection connection = DatabaseConnection.Connect()){
	        String userQuery = "SELECT DISTINCT uName FROM userTable WHERE homeid IS NULL AND uName <> ? and userType = ?";
	        PreparedStatement groupStatement = connection.prepareStatement(userQuery);
	        groupStatement.setString(1, username);
	        groupStatement.setString(2, "Member");
	        ResultSet rs = groupStatement.executeQuery();
	        while(rs.next()) {
	        	this.userNames.add(rs.getString(1));
	        }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }
    
    public void setHouseId()
    {
    	
    }
    
public void getUser(ActionEvent event) {

	listOfNames.getItems().add(myChoiceBox.getValue());
	}
    @FXML
    void addName(MouseEvent event) {
    	System.out.println("Adding element to " + listOfNames.getItems());
    	//listOfNames.getItems().add(name.getText());
    	listOfNames.getItems().add(myChoiceBox.getValue());
    }
    @FXML
    void removeName(MouseEvent event) {
    	int selectedID = listOfNames.getSelectionModel().getSelectedIndex();
    	listOfNames.getItems().remove(selectedID);

    }
  
    @FXML
    void saveData(MouseEvent event) throws IOException {
    	System.out.println("Save Data Invoked!");
		try (Connection connection = DatabaseConnection.Connect()){
			for (String memberName : listOfNames.getItems()) {
		        String memberQuery = "INSERT INTO AllHomeGroups (homeName,address) VALUES (?, ?)";
		        PreparedStatement memberStatement = connection.prepareStatement(memberQuery);
		        memberStatement.setString(1, groupName.getText());
		        memberStatement.setString(2, address.getText());
		        memberStatement.executeUpdate();
		    }
			// Get the newly created houseId
	        String houseIdQuery = "SELECT LAST_INSERT_ID()";
	        PreparedStatement houseIdStatement = connection.prepareStatement(houseIdQuery);
	        ResultSet rs = houseIdStatement.executeQuery();
	        rs.next();
	        int houseId = rs.getInt(1);

	        // Update userTable with the newly created houseId
	        String userQuery = "UPDATE userTable SET homeid = ? WHERE uName = ?";
	        PreparedStatement userStatement = connection.prepareStatement(userQuery);

	        for (String memberName : listOfNames.getItems()) {
	            userStatement.setInt(1, houseId);
	            userStatement.setString(2, memberName);
	            userStatement.executeUpdate();
	        }
	        // For the user who is creating the group
	        userStatement.setInt(1, houseId);
            userStatement.setString(2, username);
            userStatement.executeUpdate();

			//Alert
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setTitle("Group Created");
	    	alert.setHeaderText("You Have successfully created the group");
	    	alert.setResizable(false);
	    	alert.setContentText("Select okay or cancel this alert.");

	    	Optional<ButtonType> result = alert.showAndWait();
	    	ButtonType button = result.orElse(ButtonType.CANCEL);

	    	if (button == ButtonType.OK) {
	    		Main changeScreen=new Main();
		    	changeScreen.changeScene("fxml/Dashboard.fxml");

	    	    
	    	} else {
	    	    System.out.println("canceled");
	    	}
		
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }
    
    public void goBack() throws IOException {
		
		String uName = username;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Dashboard.fxml"));
        Parent root = loader.load();
        
        DashboardController dashboardController = loader.getController();
        dashboardController.displayInfo(uName);
        
        
        Main m = new Main();
        
        m.changeStage(root);
    	
//    	Main m = new Main();
//        
//        m.changeScene("fxml/Dashboard.fxml");
		
	}

}
