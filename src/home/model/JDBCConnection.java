package home.model;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnection {
	public static Connection Connect(){
        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/householdmanagementdb", "root", "root");
	        } catch (ClassNotFoundException | SQLException exception) {
	            return null;
	        }
	        
	        
	    }   
}
