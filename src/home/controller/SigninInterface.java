package home.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;

public interface SigninInterface {
	
	public String loginQuery = "select * from userTable where uName=? and password=?";
	
	public void userLogIn(ActionEvent event) throws IOException, SQLException;

}
