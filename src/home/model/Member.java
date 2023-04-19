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


public boolean checkMemberLogin(String uName, String pass) {
	

	
	try(Connection connection = DatabaseConnection.Connect()) {
	    PreparedStatement pst = connection.prepareStatement("select * from userTable where uName = ? and password = ? and userType = ?");
	    pst.setString(1, uName);
	    pst.setString(2, pass);
	    pst.setString(3, "Member");
	    ResultSet resultSet = pst.executeQuery();
	    if (resultSet.next()) {
	    	return true;
	    } 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	return false;
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


public void raiseMemberQuery(String uName, String queryDesc) {
	
	int homeId = getHomeIdUsingUName(uName);

	try (Connection connection = DatabaseConnection.Connect()){
		PreparedStatement pst = connection.prepareStatement("INSERT INTO queryTable (homeId, address, status, description) VALUES (?, ?, ?, ?)");
	    pst.setInt(1, homeId);
	    pst.setString(2, getAddress(homeId));
	    pst.setString(3, "Query Raised");
	    pst.setString(4, queryDesc);
	    pst.executeUpdate(); 		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	
	
	
}

public static String getAddress(int homeId) {
	String address = "";
	
	try (Connection connection = DatabaseConnection.Connect()){
		    PreparedStatement pst = connection.prepareStatement("select address from AllHomeGroups where homeId = ?");
		    pst.setInt(1, homeId);
		    ResultSet resultSet = pst.executeQuery();
		    
		    while(resultSet.next()) {
		    	
		    	address = resultSet.getString(1);
		    	
		    }
		     
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	
	
	
	return address;
}
	
	

}
