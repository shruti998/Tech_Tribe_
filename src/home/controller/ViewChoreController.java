package home.controller;

import java.io.IOException;
import home.Main;
import home.model.Chores;
import home.model.ViewChore;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

@SuppressWarnings("deprecation")
public class ViewChoreController {

	private ViewChore choreModel;
	  public ViewChoreController() {
	        choreModel = new ViewChore();
	    } 

	    @FXML
	    private ImageView addIcon;

	    @FXML
	    private TableColumn<Chores,String> assignTo;

	    @FXML
	    private TableColumn<Chores,String> chore;

	    @FXML
	    private TableColumn<Chores,String> endDate;

	    @FXML
	    private TableColumn<Chores,String> howOften;

	    @FXML
	    private TableColumn<Chores,String> intensity;

	    @FXML
	    private ImageView refreshIcon;

	    @FXML
	    private TableColumn<Chores,String> startDate;

	    @FXML
	    private TableColumn<Chores,String> status;

	    @FXML
	    private TableView<Chores> userTable;
	    
	    @FXML
	    private Button btnAdd;

	    @FXML
	    private Button btnEdit;

	    @FXML
	    private Button btnRefresh;
	    @FXML
	    private Button btndel;
	    private String  username = "shruti";

	    
	    ObservableList<Chores> queryList;
	    
	    ChoresQueryDirectory queryDir = new ChoresQueryDirectory();
	    ObservableList<Chores> selChore;
	    
	    @FXML
	    void editData(MouseEvent event) throws IOException {
	    	
		    selChore= userTable.getSelectionModel().getSelectedItems();
		    System.out.println(selChore.get(0).getChoreName());
		    Main changeScreen=new Main();
	    	changeScreen.changeScene("fxml/EditChore.fxml");
	    	EditController ed= new EditController(selChore.get(0));
	    	ed.setData(selChore.get(0).getChoreName(), selChore.get(0).getIntensity(), selChore.get(0).getHowOften(), selChore.get(0).getStrtDate(), selChore.get(0).getEndDate(),
	    			selChore.get(0).getAssignTo(),selChore.get(0).getStatus());
	    	 System.out.println(selChore.get(0));

	    }
	   
	   
	
	
	    @FXML
	    void initialize() {
	       
	    	chore.setCellValueFactory(new PropertyValueFactory<Chores,String>("choreName"));
	    	intensity.setCellValueFactory(new PropertyValueFactory<Chores,String>("intensity"));
	    	howOften.setCellValueFactory(new PropertyValueFactory<Chores,String>("howOften"));
	    	startDate.setCellValueFactory(new PropertyValueFactory<Chores,String>("strtDate"));
	    	endDate.setCellValueFactory(new PropertyValueFactory<Chores,String>("endDate"));
	    	assignTo.setCellValueFactory(new PropertyValueFactory<Chores,String>("assignTo"));
	    	status.setCellValueFactory(new PropertyValueFactory<Chores,String>("status"));
	    	
	    	
	    	queryList = queryDir.populateQueryTable(username);
	    	userTable.setItems(queryList);
	    

	    }
	
	    @FXML
	    void addChore(MouseEvent event) throws IOException  {
	    	Main changeScreen=new Main();
	    	changeScreen.changeScene("fxml/AddChores.fxml");	 	
	    }
	   
	    @FXML
	    void delRow(MouseEvent event) {
	    	ObservableList<Chores> allChores, singleChore;
	        allChores = userTable.getItems();
	        singleChore = userTable.getSelectionModel().getSelectedItems();

	        // remove the selected rows from the ObservableList
	        allChores.removeAll(singleChore);
	        
	        // delete the selected rows from the database using the ChoresQueryDirectory class
	        ChoresQueryDirectory queryDir = new ChoresQueryDirectory();
	        for (Chores chore : singleChore) {
	            queryDir.deleteChore(chore);
	        }
	    }

	}
