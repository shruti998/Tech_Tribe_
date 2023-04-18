package home.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import home.controller.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QueryDirectory{
	
	
	
	public ObservableList<Query> populateQueryTable() {
		
		
		ObservableList<Query> queryList = FXCollections.observableArrayList();
		
		try (Connection connection = DatabaseConnection.Connect()){
		    PreparedStatement pst = connection.prepareStatement("select id,homeId,address,status,description,note from queryTable");
		    ResultSet resultSet = pst.executeQuery();
		    
		    while(resultSet.next()) {
		    	
		    	int queryId = Integer.parseInt(resultSet.getString(1));
		    	int homeId = Integer.parseInt(resultSet.getString(2));
		    	String address = resultSet.getString(3);
		    	String status = resultSet.getString(4);
		    	String description = resultSet.getString(5);
		    	String note = resultSet.getString(6);
		    	
		    	queryList.add(new Query(queryId, homeId, address, status, description, note));
		    	
		    }
		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return queryList;
	}
	
	
	public void updateQueryTable(Query query) {
		
		
        try(Connection connection = DatabaseConnection.Connect()) {
            PreparedStatement pst = connection.prepareStatement("update queryTable set note = ?, status = ? where id = ?");
            pst.setString(1, query.getNote());
            pst.setString(2, query.getStatus());
            pst.setInt(3, query.getQueryId());
            pst.executeUpdate();
        } catch (SQLException e) {
		    e.printStackTrace();
		}
		
		
	}
	
	
	public List<String> populateHomeDet(){
    	 
    	 List<String> homeDet = new ArrayList<>();
    	 
    	 try (Connection connection = DatabaseConnection.Connect()){
 		    PreparedStatement pst = connection.prepareStatement("select address from AllHomeGroups");
 		    ResultSet resultSet = pst.executeQuery();
 		    
 		    while(resultSet.next()) {
 		    	
 		    	String address = resultSet.getString(1);
 		    	
 		    	homeDet.add(address);
 		    	
 		    }
 		     
 		} catch (SQLException e) {
 		    e.printStackTrace();
 		}
    	 
    	 
    	 
    	 return homeDet;
     }
	
	public void notifyHome(String address, String notification) {
		

		try (Connection connection = DatabaseConnection.Connect()){
			PreparedStatement pst = connection.prepareStatement("INSERT INTO notificationTable (homeId, notification) VALUES (?, ?)");
		    pst.setInt(1, getHomeId(address));
		    pst.setString(2, notification);
		    pst.executeUpdate(); 		     
 		} catch (SQLException e) {
 		    e.printStackTrace();
 		}
		
		
		
	}
	
	public static int getHomeId(String address) {
		int homeId = 0;
		
		try (Connection connection = DatabaseConnection.Connect()){
 		    PreparedStatement pst = connection.prepareStatement("select homeId from AllHomeGroups where address = ?");
 		    pst.setString(1, address);
 		    ResultSet resultSet = pst.executeQuery();
 		    
 		    while(resultSet.next()) {
 		    	
 		    	homeId = resultSet.getInt(1);
 		    	
 		    }
 		     
 		} catch (SQLException e) {
 		    e.printStackTrace();
 		}
		
		
		
		return homeId;
	}
	
	
public ObservableList<Query> populateMemberQueryTable(String uName) {
		
		
		ObservableList<Query> queryList = FXCollections.observableArrayList();
		
		try (Connection connection = DatabaseConnection.Connect()){
		    PreparedStatement pst = connection.prepareStatement("select id,homeId,address,status,description,note from queryTable where homeId = ?");
		    pst.setInt(1, getHomeIdUsingUName(uName));
		    ResultSet resultSet = pst.executeQuery();
		    
		    while(resultSet.next()) {
		    	
		    	int queryId = Integer.parseInt(resultSet.getString(1));
		    	int homeId = Integer.parseInt(resultSet.getString(2));
		    	String address = resultSet.getString(3);
		    	String status = resultSet.getString(4);
		    	String description = resultSet.getString(5);
		    	String note = resultSet.getString(6);
		    	
		    	queryList.add(new Query(queryId, homeId, address, status, description, note));
		    	
		    }
		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return queryList;
	}


public static int getHomeIdUsingUName(String uName) {
	int homeId = 0;
	
	try (Connection connection = DatabaseConnection.Connect()){
		    PreparedStatement pst = connection.prepareStatement("select homeId from userTable where uName = ?");
		    pst.setString(1, uName);
		    ResultSet resultSet = pst.executeQuery();
		    
		    while(resultSet.next()) {
		    	
		    	homeId = resultSet.getInt(1);
		    	
		    }
		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	
	
	
	return homeId;
}
	
	
	
	
	
	
}
