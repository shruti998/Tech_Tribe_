package home.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.*;

public class GroupDetails {
    public String groupName;
    public List<String> members;
    public List<String> transactions;
    private String userName = "farida";
    
    @FXML
    public Label groupNameLabel;
    
    @FXML
    public VBox balancesVBox;
    
    @FXML
    private ListView<String> listOfTransactionNames;
    
    @FXML
    void initialize() {
    	// Set the group name label here
    	groupNameLabel.setText(this.groupName);
    	
    	// Get group members names
    	getMemberNames();
    	System.out.println(this.members);
    	
    	// display member names in vBox
    	renderMemberNames();
    	getTransactionNames();
    	renderTransactionNames();
    }
    
    void getMemberNames() {
    	members = new ArrayList<String>();
		try (Connection connection = DatabaseConnection.Connect()){
	        String memberQuery = "SELECT memberName FROM all_groups WHERE groupName = '" + this.groupName + "'";
	        PreparedStatement memberStatement = connection.prepareStatement(memberQuery);
	        ResultSet rs = memberStatement.executeQuery();
	        while(rs.next()) {
	        	this.members.add(rs.getString(1));
	        }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }
    
    void getTransactionNames() {
    	transactions = new ArrayList<String>();
		try (Connection connection = DatabaseConnection.Connect()){
	        String transactionQuery = "SELECT distinct t.id, t.transactionName FROM transaction t JOIN transaction_split ts ON t.id = ts.transactionId WHERE t.payerName = '" + userName + "' OR ts.payeeName = '" + userName + "' AND t.groupName = '" + groupName + "'";
	        PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery);
	        System.out.println(transactionStatement.toString());
	        ResultSet rs = transactionStatement.executeQuery();
	        while(rs.next()) {
	        	this.transactions.add(rs.getString(2));
	        }
	        System.out.println(transactions);
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }
    
    
    @FXML
    void renderTransactionNames() {
    	System.out.println("Adding element to " + listOfTransactionNames.getItems());
    	for(String name : transactions) 
    	{
    		listOfTransactionNames.getItems().add(name);
    	}
  
    }
    
    void renderMemberNames() {
    	for(String memberName : members) {
    		Label memberLabel = new Label(memberName);
    		balancesVBox.getChildren().add(memberLabel);
    	}
    }
    
    public void setGroupName(String groupName) {
    	this.groupName = groupName;
    }
}
