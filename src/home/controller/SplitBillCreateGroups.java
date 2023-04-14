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

public class SplitBillCreateGroups {
	private List<String> groupNames;
	// to be deleted
	private String  username = "farida";

    @FXML
    private TextField groupName;

    @FXML
    private ListView<String> listOfNames;

    @FXML
    private TextField name;
    
    @FXML
    private VBox groupListVbox;

    private Parent root;
    
    @FXML
    void initialize() {
    	// Get list of group names
    	getGroupNames();
    	
    	// Populate view with group names and buttons
    	renderGroupNames();
    }
    
    void getGroupNames() {
    	groupNames = new ArrayList<String>();
		try (Connection connection = DatabaseConnection.Connect()){
	        String groupQuery = "SELECT DISTINCT groupName FROM all_groups WHERE memberName = ?";
	        PreparedStatement groupStatement = connection.prepareStatement(groupQuery);
	        groupStatement.setString(1, username);
	        ResultSet rs = groupStatement.executeQuery();
	        while(rs.next()) {
	        	this.groupNames.add(rs.getString(1));
	        }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }
    
    void renderGroupNames() {
    	groupListVbox.getChildren().clear();
    	for(String groupName : groupNames) {
    		HBox hbox = new HBox(20);
    		Label groupLabel = new Label(groupName);
    		groupLabel.setPrefWidth(120); 
    	    groupLabel.setPrefHeight(10);
    	    //groupLabel.setStyle("-fx-padding: 0 50 0 0;"); // add right padding to the label
    	      
    		Button viewGroupButton = new Button("View Group");
    		viewGroupButton.setPrefWidth(120); 
    	    viewGroupButton.setPrefHeight(10);
    	    viewGroupButton.setStyle("-fx-background-color: #ADD8E6;");
    		
    		viewGroupButton.setOnAction(e -> openGroupView(e, groupName));
    		hbox.getChildren().addAll(groupLabel, viewGroupButton);
    		//HBox.setMargin(groupLabel, new Insets(0, 0, 0, 10));
    		groupListVbox.getChildren().add(hbox);
    	}
    }
    
    void openGroupView(ActionEvent event, String groupName) {
    	System.out.println(groupName);
        System.out.println("View Group Clicked!");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
       	 URL url = new File("src/home/fxml/GroupDetails.fxml").toURI().toURL();
      
       	 FXMLLoader loader = new FXMLLoader(url);
       	 
       	 GroupDetails controller = new GroupDetails();
       	 controller.setGroupName(groupName);
       	 
       	 loader.setController(controller);
       	 
       	 Parent root = loader.load();
       	 Scene scene = new Scene(root);
       	 stage.setScene(scene);
       	 stage.show();
        } catch (IOException e) {
       	 System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
    @FXML
    void addName(MouseEvent event) {
    	System.out.println("Adding element to " + listOfNames.getItems());
    	listOfNames.getItems().add(name.getText());
    }

    @FXML
    void removeName(MouseEvent event) {
    	int selectedID = listOfNames.getSelectionModel().getSelectedIndex();
    	listOfNames.getItems().remove(selectedID);

    }
  
    @FXML
    void viewGroup(MouseEvent event) {         
         System.out.println("View Group Clicked!");
         Node node = (Node) event.getSource();
         Stage stage = (Stage) node.getScene().getWindow();
         stage.close();
         try {
        	 URL url = new File("src/home/fxml/GroupDetails.fxml").toURI().toURL();
       
        	 FXMLLoader loader = new FXMLLoader(url);
        	 
        	 GroupDetails controller = new GroupDetails();
        	 controller.setGroupName("dummyGroup");
        	 
        	 loader.setController(controller);
        	 
        	 Parent root = loader.load();
        	 Scene scene = new Scene(root);
        	 stage.setScene(scene);
        	 stage.show();
         } catch (IOException e) {
        	 System.err.println(String.format("Error: %s", e.getMessage()));
         }
    }
    
    @FXML
    void saveData(MouseEvent event) {
    	System.out.println("Save Data Invoked!");
		try (Connection connection = DatabaseConnection.Connect()){
			for (String memberName : listOfNames.getItems()) {
		        String memberQuery = "INSERT INTO all_groups (groupName, memberName) VALUES (?, ?)";
		        PreparedStatement memberStatement = connection.prepareStatement(memberQuery);
		        memberStatement.setString(1, groupName.getText());
		        memberStatement.setString(2, memberName);
		        memberStatement.executeUpdate();
		    }
			// Get list of group names
	    	getGroupNames();
	    	
	    	// Populate view with group names and buttons
	    	renderGroupNames();
		
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }
 }


