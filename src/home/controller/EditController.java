package home.controller;
import java.time.LocalDate;

import home.model.Chores;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class EditController {




	    @FXML
	    private Button btnBack;

	    @FXML
	    private Button btnSubmit;

	    @FXML
	    private ToggleGroup changeStatus;

	    @FXML
	    private Label choreName;

	    @FXML
	    private DatePicker endDate;

	    @FXML
	    private Label lblStatus;

	    @FXML
	    private MenuButton menuHowOften;

	    @FXML
	    private MenuButton menuIntensity;

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
	    void addChore(MouseEvent event) {

	    }

	    @FXML
	    void back(MouseEvent event) {

	    }
	    private Chores chore;
	    public EditController(Chores data)
	    {
	    	
	    }
	    
	 

	    void setData(String choreName, String intensity, String howOften, String strtDate, String endDate,
				String assignTo, String status)
	    {
		    System.out.println("bb"+choreName);
	        //txtChore.setText(choreName);
	        //txtChore.setPromptText(choreName);
	        //txtChore.setPromptText(status);
	        
	        //txtAssignTo.setText(choreName);
//	        menuHowOften.setText(howOften);
//	        menuIntensity.setText(intensity);
//	        startDate.setValue(LocalDate.parse(strtDate));
//	       // endDate.setValue(LocalDate.parse(endDate));
//	     
//	        if (status.equals("Complete")) {
//	            rdCompleted.setSelected(true);
//	        } else {
//	            rdImcomplete.setSelected(true);
//	        }
	       
	        System.out.println("choreName"+choreName);
	        if(txtChore != null){
	        	   txtChore.setPromptText("Enter Chore");
	        	}
	       // txtChore.setPromptText(choreName);
	       // txtChore.setText(choreName);
	       // txtChore.setString(choreName);
	    }

	    @FXML
	    void initialize() {
	    	
	    	   //txtChore = new TextField();	
	    }

	}


