package home.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import home.controller.DatabaseConnection;

public class MaintenanceAdmin extends Person implements Maintenance{

//	public MaintenanceAdmin(int id, String fName, String lName, String uName, String email, String password,
//			String userType) {
//		super(id, fName, lName, uName, email, password, userType);
//		// TODO Auto-generated constructor stub
//	}


	@Override
	public void displayInfo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMaintenanceStatus(String status) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateAdminProfile() {

        try(Connection connection = DatabaseConnection.Connect()) {
            PreparedStatement pst = connection.prepareStatement("update userTable set email = ?, password = ? where uName = ?");
            pst.setString(1, getEmail());
            pst.setString(2, getPassword());
            pst.setString(3, getuName());
            pst.executeUpdate();
        } catch (SQLException e) {
		    e.printStackTrace();
		}
		
		
	}
	
	
	
	public boolean checkAdminLogin(String uName, String pass) {
		

		
		try(Connection connection = DatabaseConnection.Connect()) {
		    PreparedStatement pst = connection.prepareStatement("select * from userTable where uName = ? and password = ? and userType = ?");
		    pst.setString(1, uName);
		    pst.setString(2, pass);
		    pst.setString(3, "Maintenance Admin");
		    ResultSet resultSet = pst.executeQuery();
		    if (resultSet.next()) {
		    	return true;
		    } 
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return false;
	}
	
	
	public String getAdminDetails(String uName) {
		
		String email = "";
		

		try(Connection connection = DatabaseConnection.Connect()) {
		    PreparedStatement pst = connection.prepareStatement("select email from userTable where uName = ? and userType = ?");
		    pst.setString(1, uName);
		    pst.setString(2, "Maintenance Admin");
		    ResultSet resultSet = pst.executeQuery();
		    if (resultSet.next()) {
		    	email = resultSet.getString(1);
		    	System.out.println(email);
		    	
		    } 
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return email;
		
	}

}
