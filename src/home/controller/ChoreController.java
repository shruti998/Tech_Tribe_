package home.controller;


import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import home.Main;
import home.model.Chores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class ChoreController {

    @FXML
    private ToggleGroup changeStatus;


    @FXML
    private Button btnSubmit;

    @FXML
    private Label choreName;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label lblStatus;
    @FXML
    private ChoiceBox<String> menuHowOften;

    @FXML
    private ChoiceBox<String> menuIntensity;

    @FXML
    private RadioButton rdCompleted;

    @FXML
    private RadioButton rdImcomplete;

 

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField txtAssignTo;

    @FXML
    private TextField txtChore;
    @FXML
    private Button btnBack;
    
	@FXML
	private ChoiceBox<String> myChoiceBox;

    private String  username = "shruti";
    @FXML
    ArrayList<String >  listOfNames = new ArrayList<String>();
    ArrayList<String > userNames = new ArrayList<String>();
    String intensity[]= {"easy","tough","hard","high"};
    String how[]= {"daily","monthly","weekly","high"};
    String houseID;
    @FXML
    void initialize() {
    	getMembersName();
    	myChoiceBox.getItems().addAll(userNames);
    	System.out.println(userNames.size());
    	myChoiceBox.setOnAction(this::getUser);
    	setIntensity();
    	setHowOften();
    }
    public void getUser(ActionEvent event) {

    	listOfNames.add(myChoiceBox.getValue());
    	}
    public void setIntensity()
    {
    	menuIntensity.getItems().addAll(intensity);
    	
    }
    public void setHowOften()
    {
    	menuHowOften.getItems().addAll(how);
    	
    }
   public int getHouseId()
   {
	   int hid=0;
	   try (Connection connection = DatabaseConnection.Connect()){
	        String userQuery = "SELECT homeid FROM userTable WHERE fName =? ";
	        PreparedStatement groupStatement = connection.prepareStatement(userQuery);
	        groupStatement.setString(1, username);
	
	        ResultSet rs = groupStatement.executeQuery();
	        while(rs.next()) {
	        	 hid=rs.getInt(1);
	        	System.out.println("houseID "+rs.getInt(1));
	        	 System.out.println("hid"+hid);
	        	
	        }
	        hid=rs.getInt(1);
	        System.out.println("hid"+hid);
	      
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
	   return hid;
   }
 void getMembersName() {
    	
		try (Connection connection = DatabaseConnection.Connect()){
	        String userQuery = "SELECT  fname FROM userTable WHERE homeid = (SELECT homeid FROM userTable WHERE fName = ?) ";
	        PreparedStatement groupStatement = connection.prepareStatement(userQuery);
	        groupStatement.setString(1, username);
	        //groupStatement.setString(2, username);
	        ResultSet rs = groupStatement.executeQuery();
	        while(rs.next()) {
	        	System.out.println(rs.getString(1));
	        	this.userNames.add(rs.getString(1));
	        }
	      
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
    }

    @FXML
    void addChore(MouseEvent event) throws IOException {
    	 try (Connection connection = DatabaseConnection.Connect()){
     		//count=count+1;
 
 	        
 	        
     	
                 String memberQuery = "INSERT INTO choresdivider (choreName,intensity,howOften,startDate,endDate,assignTo,statusChore,homeId) VALUES (?, ?,?,?,?,?,?,?)";
                 PreparedStatement memberStatement = connection.prepareStatement(memberQuery);
                 RadioButton selectedRadioButton = (RadioButton) changeStatus.getSelectedToggle();
                 String selectedValue = selectedRadioButton.getText();
                 //memberStatement.setInt(1,count );
                 memberStatement.setString(1,txtChore.getText() );
                 memberStatement.setString(2,menuIntensity.getValue() );
                 memberStatement.setString(3,menuHowOften.getValue() );
                 memberStatement.setString(4,startDate.getValue().toString() );
                 memberStatement.setString(5, endDate.getValue().toString());
                 memberStatement.setString(6,myChoiceBox.getValue() );
                 memberStatement.setString(7,selectedValue );
                 memberStatement.setInt(8,getHouseId() );
            

                 memberStatement.executeUpdate();
                 
             

         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    	 getHouseId();
    	 
    	//Alert
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setTitle("Chore Addede");
	    	alert.setHeaderText("You Have successfully created the group");
	    	alert.setResizable(false);
	    	alert.setContentText("Select okay or cancel this alert.");

	    	Optional<ButtonType> result = alert.showAndWait();
	    	ButtonType button = result.orElse(ButtonType.CANCEL);

	    	if (button == ButtonType.OK) {
	    		Main changeScreen=new Main();
		    	changeScreen.changeScene("fxml/Chore.fxml");

	    	    
	    	} else {
	    	    System.out.println("canceled");
	    	}
		
    }
    
    
    
   

}
