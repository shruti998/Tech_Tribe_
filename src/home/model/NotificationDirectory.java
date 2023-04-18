package home.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import home.controller.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotificationDirectory {
	
public ObservableList<Notification> populateNotificationTable(String uName) {
		
		
		ObservableList<Notification> noteList = FXCollections.observableArrayList();
		
		try (Connection connection = DatabaseConnection.Connect()){
		    PreparedStatement pst = connection.prepareStatement("select notification, date from notificationTable where homeId = ?");
		    pst.setInt(1, getHomeIdUsingUName(uName));
		    ResultSet resultSet = pst.executeQuery();
		    
		    while(resultSet.next()) {
		    	
		    	String notification = resultSet.getString(1);
		    	String date = resultSet.getString(2);
		    	
		    	
		    	noteList.add(new Notification(date, notification));
		    	
		    }
		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return noteList;
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
