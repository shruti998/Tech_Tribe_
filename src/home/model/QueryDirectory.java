package home.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import home.controller.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QueryDirectory{
	
	
	
	public ObservableList<Query> populateQueryTable() {
		
		Connection connection = DatabaseConnection.Connect();
		ObservableList<Query> queryList = FXCollections.observableArrayList();
		
		try {
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
	
	
	
	
}
