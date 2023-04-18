package home.controller;

import java.io.IOException;
import home.Main;
import home.model.Chores;
import home.model.ViewChore;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
	    
	    @FXML
	    private Button btnBack;
	    
	    private String  username;

	    
	    ObservableList<Chores> queryList;
	    
	    ChoresQueryDirectory queryDir = new ChoresQueryDirectory();
	    ObservableList<Chores> selChore;
	    
	    @FXML
	    void editData(MouseEvent event) throws IOException {
	    	
	    	selChore= userTable.getSelectionModel().getSelectedItems();
	    	System.out.println(selChore.get(0).getChoreName());
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditChore.fxml"));
            Parent root = loader.load();
         
         EditController raiseQueryController = loader.getController();
         raiseQueryController.setData(selChore.get(0).getChoreName(), selChore.get(0).getIntensity(), selChore.get(0).getHowOften(), selChore.get(0).getStrtDate().toString(), selChore.get(0).getEndDate().toString(),
	    			selChore.get(0).getAssignTo(),selChore.get(0).getStatus(),username);
         
//         Stage stage = new Stage();
//         stage.setScene(new Scene(root));
//         stage.setTitle("Edit Chore");
//         stage.show();
         
         	Main m = new Main();
	        m.changeStage(root);

	    }
	   
	   
	
	
	    @FXML
	    void initialize(String username) {
	    	
	    	this.username = username;
	       
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
//	    	Main changeScreen=new Main();
//	    	changeScreen.changeScene("fxml/AddChores.fxml");
	    	
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AddChores.fxml"));
	        Parent root = loader.load();
	       
	        ChoreController choreController = loader.getController();
	        choreController.initialize(username);
	   
	   
	        
	        Main m = new Main();
	        m.changeStage(root);
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
	    
	    public void goBack() throws IOException {
			
			String uName = username;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Dashboard.fxml"));
	        Parent root = loader.load();
	        
	        DashboardController dashboardController = loader.getController();
	        dashboardController.displayInfo(uName);
	        
	        
	        Main m = new Main();
	        
	        m.changeStage(root);
	    	
	    	
		}
	    
	    

	}
