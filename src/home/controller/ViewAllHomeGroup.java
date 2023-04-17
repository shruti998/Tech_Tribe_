

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


public class ViewAllHomeGroup {
	
	

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
		        String groupQuery = "SELECT DISTINCT homeName FROM AllHomeGroups WHERE groupMember = ?";
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
	    		
	    		//viewGroupButton.setOnAction(e -> openGroupView(e, groupName));
	    		hbox.getChildren().addAll(groupLabel);
	    		//HBox.setMargin(groupLabel, new Insets(0, 0, 0, 10));
	    		groupListVbox.getChildren().add(hbox);
	    	}
	    }
	    @FXML
	    void back(MouseEvent event) throws IOException{
	    	Main changeScreen=new Main();
	    	changeScreen.changeScene("fxml/CreateHomeGroup.fxml");

	    }
	    

	    
	  

	    
	   
	 


}
