package home.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class Transaction {
    public String groupName;
    public String transactionId;
    
	public String transactionName;
    public double amount;
    public String paidBy;
    private String userName = "Farida";
    public List<String> members;
    
    @FXML
    public Label groupNameLabel;
    
    @FXML
    public VBox splitVBox;
    
    @FXML
    public TextField transactionNameField;
    
    @FXML
    public TextField amountField;
    
    @FXML
    public ComboBox paidByPicker;
    
    @FXML
    void initialize() {
    	// Set the group name label here
    	groupNameLabel.setText(this.groupName);
    	transactionNameField.setText(this.transactionName);
    	amountField.setText("" + this.amount);
    	
    	// Get group members names
    	getMemberNames();
    	
    	// render the vbox contents
    	renderSplitBox();
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
		
		ObservableList<Label> pickerList = FXCollections.observableArrayList();
		
		for(String member : this.members) {
			Label tempLabel = new Label(member);
			tempLabel.setTextFill(Color.BLACK);
			pickerList.add(new Label(member));
		}
		
		paidByPicker.setItems(pickerList);
		
		paidByPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Label selectedLabel = (Label) paidByPicker.getSelectionModel().getSelectedItem();
				selectedLabel.setTextFill(Color.BLACK);
				setPaidBy(selectedLabel.getText());
				System.out.println(getPaidBy());
			}
		});
    }
    
    void renderSplitBox() {
    	
    }
    
    @FXML
    void onSaveClick(ActionEvent event) {
    	System.out.println("Save clicked!");
    }
    
    @FXML 
    void onSplitClick(ActionEvent event) {
    	System.out.println("Split Clicked");
    }
    
    public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
}
