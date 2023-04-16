package home.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class GroupDetails {
    public String groupName;
    public List<String> members;
    public List<String> transactions;
    public Map<String, List<Map<String, Double>>> balances;
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
    	renderBalances();
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
		
		getBalances();
    }
    
    void getBalances() {
    	balances = new HashMap<>();
		try (Connection connection = DatabaseConnection.Connect()){
	        String balanceQuery = "SELECT TS.payeeName, T.payerName, SUM(TS.individualAmount) owes "
	        		+ "FROM householdmanagementdb.transaction T join transaction_split TS "
	        		+ "on T.id = TS.transactionId "
	        		+ "WHERE groupName = '" + this.groupName + "' "
    				+ "GROUP BY payeeName, payerName; ";
	        PreparedStatement balanceStatement = connection.prepareStatement(balanceQuery);
	        ResultSet rs = balanceStatement.executeQuery();
	        while(rs.next()) {
	        	String payeeName = rs.getString(1); // the person that owes
	        	String payerName = rs.getString(2); // The person that gets back
	        	double amount = rs.getDouble(3);
	        	
	        	if(payeeName.equals(payerName)) continue;
	        	
	        	// Add to the owes map
	        	List<Map<String, Double>> memberBalance = balances.getOrDefault(payeeName, new ArrayList<Map<String, Double>>());
	        	if(memberBalance.size() == 0) {
	        		memberBalance.add(new HashMap<String, Double>()); // owes map
	        		memberBalance.add(new HashMap<String, Double>()); // gets back map
	        	}
	        	memberBalance.get(0).put(payerName, amount);
	        	balances.put(payeeName, memberBalance);
	        	
	        	// Add to the gets back map
	        	memberBalance = balances.getOrDefault(payerName, new ArrayList<Map<String, Double>>());
	        	if(memberBalance.size() == 0) {
	        		memberBalance.add(new HashMap<String, Double>()); // owes map
	        		memberBalance.add(new HashMap<String, Double>()); // gets back map
	        	}
	        	memberBalance.get(1).put(payeeName, amount);
	        	
	        	balances.put(payerName, memberBalance);
	        }
	        
	        for(Map.Entry<String, List<Map<String, Double>>> e : balances.entrySet()) {
	        	System.out.println("Balances for " + e.getKey());
	        	System.out.println("Owes list");
	        	for(Map.Entry<String, Double> e1 : e.getValue().get(0).entrySet()) {
	        		System.out.println(e1.getKey() + " : " + e1.getValue());
	        	}
	        	System.out.println("Gets back list");
	        	for(Map.Entry<String, Double> e1 : e.getValue().get(1).entrySet()) {
	        		System.out.println(e1.getKey() + " : " + e1.getValue());
	        	}
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
	        ResultSet rs = transactionStatement.executeQuery();
	        while(rs.next()) {
	        	this.transactions.add(rs.getString(2));
	        }
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
    
    void renderBalances() {
    	ObservableList<Node> vbox = balancesVBox.getChildren();
    	vbox.clear();
        for(Map.Entry<String, List<Map<String, Double>>> e : balances.entrySet()) {
        	String blanks = "";
        	for(int i = 0; i < e.getKey().length(); i++) blanks += " ";
        	
        	HBox headerBox = new HBox(30);
        	headerBox.setAlignment(Pos.CENTER_LEFT);
        	Label headerLabelTitle = new Label(e.getKey());
        	Label headerLabelTotalOwed = new Label();
        	headerLabelTitle.setStyle("-fx-font-weight: 700");
        	headerBox.getChildren().addAll(headerLabelTitle, headerLabelTotalOwed);
        	vbox.add(headerBox);
        	
        	HBox secondHeaderBox = new HBox(30);
        	secondHeaderBox.setAlignment(Pos.CENTER_LEFT);
        	Label headerLabelTotalGets = new Label();
        	secondHeaderBox.getChildren().addAll(new Label(blanks), headerLabelTotalGets);
        	
        	double totalOwed = 0;
        	for(Map.Entry<String, Double> e1 : e.getValue().get(0).entrySet()) {
        		HBox tempBox = new HBox(30);
        		tempBox.setAlignment(Pos.CENTER_LEFT);
        		
        		Label tempLabel = new Label("Owes " + e1.getKey() + " : " + e1.getValue());
        		tempBox.getChildren().addAll(new Label(blanks), tempLabel);
        		vbox.add(tempBox);
        		totalOwed += e1.getValue();
        	}
        	headerLabelTotalOwed.setText("Total Owes : " + totalOwed);
        	headerLabelTotalOwed.setStyle("-fx-font-weight: 700");

        	vbox.add(secondHeaderBox);
        	double totalGetsBack = 0;
        	for(Map.Entry<String, Double> e1 : e.getValue().get(1).entrySet()) {
        		HBox tempBox = new HBox(30);
        		tempBox.setAlignment(Pos.CENTER_LEFT);
        		
        		Label tempLabel = new Label(e1.getKey() + " owes : " + e1.getValue());
        		tempBox.getChildren().addAll(new Label(blanks), tempLabel);
        		vbox.add(tempBox);
        		totalGetsBack += e1.getValue();
        	}
        	headerLabelTotalGets.setText("Total Owed: " + totalGetsBack);
        	headerLabelTotalGets.setStyle("-fx-font-weight: 700");
        }
    }
    
    public void setGroupName(String groupName) {
    	this.groupName = groupName;
    }
}