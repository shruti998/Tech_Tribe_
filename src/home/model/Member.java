package home.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import home.controller.DatabaseConnection;

public class Member extends Person implements Maintenance{

	@Override
	public void displayInfo() {
		// TODO Auto-generated method stub
		
	}

@Override
public void updateMaintenanceStatus(String status) {
	// TODO Auto-generated method stub
	
}

public void createAccount() {
	Connection connection = DatabaseConnection.Connect();
	try {
	    
	    PreparedStatement pst = connection.prepareStatement("INSERT INTO userTable (fName, lName, uName, email, password, userType) VALUES (?, ?, ?, ?, ?, ?)");
	    pst.setString(1, getfName());
	    pst.setString(2, getlName());
	    pst.setString(3, getuName());
	    pst.setString(4, getEmail());
	    pst.setString(5, getPassword());
	    pst.setString(6, getUserType());
	    pst.executeUpdate();
	    
	} catch (SQLException e) {
	    e.printStackTrace();
	}
}

public boolean checkUserName(String uName) {
	
	Connection connection = DatabaseConnection.Connect();
	
	try {
	    PreparedStatement pst = connection.prepareStatement("select * from userTable where uName = ?");
	    pst.setString(1, uName);
	    ResultSet resultSet = pst.executeQuery();
	    if (resultSet.next()) {
	    	return false;
	    } 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	return true;
}


public boolean checkLogin(String uName, String pass) {
	
Connection connection = DatabaseConnection.Connect();
	
	try {
	    PreparedStatement pst = connection.prepareStatement("select * from userTable where uName = ? and password = ?");
	    pst.setString(1, uName);
	    pst.setString(2, pass);
	    ResultSet resultSet = pst.executeQuery();
	    if (resultSet.next()) {
	    	return true;
	    } 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	return false;
}
	
	

}
