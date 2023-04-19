package home.controller;

import java.io.IOException;

import home.Main;
import home.model.Member;
import home.model.Notification;
import home.model.NotificationDirectory;
import home.model.Query;
import home.model.QueryDirectory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class RaiseQueryController {
	
	@FXML
    private Button btnBack;

    @FXML
    private Button btnSubmitQuery;

    @FXML
    private TableColumn<Notification, String> colDate;

    @FXML
    private TableColumn<Query, String> colDesc;

    @FXML
    private TableColumn<Query, String> colNote;

    @FXML
    private TableColumn<Notification, String> colNotification;

    @FXML
    private TableColumn<Query, Integer> colQueryId;

    @FXML
    private TableColumn<Query, String> colStatus;

    @FXML
    private Label lblUName;

    @FXML
    private TableView<Notification> tblNotifications;

    @FXML
    private TableView<Query> tblQueryMember;

    @FXML
    private TextArea txtArQuery;
    
    ObservableList<Notification> noteList;
    
    NotificationDirectory noteDir = new NotificationDirectory();
    
    ObservableList<Query> queryList;
    
    QueryDirectory queryDir = new QueryDirectory();
    
    int index = -1;

	
	
	@FXML
	public void initialize() {
		
		
		
	}
	
	public void displayInfo(String uName) {
		
		lblUName.setText(uName);
		
		colDate.setCellValueFactory(new PropertyValueFactory<Notification,String>("date"));
		colNotification.setCellValueFactory(new PropertyValueFactory<Notification,String>("notification"));
		
		noteList = noteDir.populateNotificationTable(uName);
    	tblNotifications.setItems(noteList);
    	
    	colQueryId.setCellValueFactory(new PropertyValueFactory<Query,Integer>("queryId"));
    	colStatus.setCellValueFactory(new PropertyValueFactory<Query,String>("status"));
    	colDesc.setCellValueFactory(new PropertyValueFactory<Query,String>("description"));
    	colNote.setCellValueFactory(new PropertyValueFactory<Query,String>("note"));
    	
    	
    	queryList = queryDir.populateMemberQueryTable(uName);
    	tblQueryMember.setItems(queryList);
		
	}
	
	public void submitQuery(ActionEvent event) throws IOException {
		
		if(txtArQuery.getText().isEmpty() || txtArQuery.getText().isBlank() ) {
    		
			showAlert(AlertType.ERROR, "Error", "Query Description field is Empty!!!");
            
        } else {
        	Member m = new Member();
        	m.raiseMemberQuery(lblUName.getText(), txtArQuery.getText());
        	//System.out.println("test");
        	
        	showAlert(AlertType.INFORMATION, "Information", "Query Submitted!!!");
        	txtArQuery.clear();
        	
        }
		
	}
	
	
	private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
	
	
	public void goBack() throws IOException {
		
		String uName = lblUName.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Dashboard.fxml"));
        Parent root = loader.load();
        
        DashboardController dashboardController = loader.getController();
        dashboardController.displayInfo(uName);
        
        
        Main m = new Main();
        
        m.changeStage(root);
		
	}
    
    

}
