package home.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import home.model.Chores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChoresQueryDirectory {
	public void deleteChore(Chores chore) {
		Connection conn = DatabaseConnection.Connect();
	    try {
	            PreparedStatement stmt = conn.prepareStatement("DELETE FROM choresdivider WHERE id = ?") ;
	        //stmt.setInt(1, chore.getChoreId());
	        stmt.executeUpdate();
	    } catch (SQLException ex) {
	        System.err.println(ex.getMessage());
	    }
	}

	public int getHouseId(String UserName)
	   {
		   int hid=0;
		   try (Connection connection = DatabaseConnection.Connect()){
		        String userQuery = "SELECT homeId FROM userTable WHERE uName =? ";
		        PreparedStatement groupStatement = connection.prepareStatement(userQuery);
		        groupStatement.setString(1, UserName);
		
		        ResultSet rs = groupStatement.executeQuery();
		        while(rs.next()) {
		        	 hid=rs.getInt(1);
		        	System.out.println("houseID "+rs.getInt(1));
		        	 System.out.println("hid in view"+hid);
		        	
		        }
		        hid=rs.getInt(1);
		        System.out.println("hid"+hid);
		      
			} catch (SQLException e) {
			    System.out.println(e.getMessage());
			}
		   return hid;
	   }
public ObservableList<Chores> populateQueryTable(String UserName) {
		int hid=0;
		Connection connection = DatabaseConnection.Connect();
		ObservableList<Chores> queryList = FXCollections.observableArrayList();
		
		try {
		    PreparedStatement pst = connection.prepareStatement("select choreName,intensity,howOften,startDate,endDate,assignTo,statusChore from choresdivider where homeId = (SELECT homeId FROM userTable WHERE uName = ?) ");
		    pst.setString(1, UserName);
		    ResultSet resultSet = pst.executeQuery();
		    hid=getHouseId(UserName);
		    while(resultSet.next()) {
		    	
		    	String choreName = resultSet.getString(1);
		    	String intensity = resultSet.getString(2);
		    	String howOften = resultSet.getString(3);
		    	String startDate = resultSet.getString(4);
		    	String endDate = resultSet.getString(5);
		    	String assignTo = resultSet.getString(6);
		    	String statusChore = resultSet.getString(7);
		    	
		    	queryList.add(new Chores(choreName,intensity,howOften,startDate,endDate,assignTo,statusChore,hid));
		    	
		    }
		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return queryList;

}
}
