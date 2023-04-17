package home.controller;


import java.util.ArrayList;
import java.util.List;

import home.model.Query;
import home.model.QueryDirectory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class MaintenanceController {
	
	


	    @FXML
	    private Button btnSubmit;
	    
	    @FXML
	    private Button btnSubmitQuery;

	    @FXML
	    private ComboBox<String> cbHomeId;

	    @FXML
	    private ComboBox<String> cbStatus;
	    
	    @FXML
	    private TableView<Query> tblQuery;

	    @FXML
	    private TableColumn<Query, String> colAddress;

	    @FXML
	    private TableColumn<Query, String> colDesc;

	    @FXML
	    private TableColumn<Query, Integer> colHomeId;

	    @FXML
	    private TableColumn<Query, String> colNote;

	    @FXML
	    private TableColumn<Query, Integer> colQueryId;

	    @FXML
	    private TableColumn<Query, String> colStatus;
	    
	    @FXML
	    private TextArea txtArNote;

	    @FXML
	    private TextArea txtArNotification;
	    
	    @FXML
	    private TextField txtQueryId;
	    
	    ObservableList<Query> queryList;
	    
	    QueryDirectory queryDir = new QueryDirectory();
	    
	    int index = -1;
	    
	    
	    @FXML
		public void initialize() {
			
	    	colQueryId.setCellValueFactory(new PropertyValueFactory<Query,Integer>("queryId"));
	    	colHomeId.setCellValueFactory(new PropertyValueFactory<Query,Integer>("homeId"));
	    	colAddress.setCellValueFactory(new PropertyValueFactory<Query,String>("address"));
	    	colStatus.setCellValueFactory(new PropertyValueFactory<Query,String>("status"));
	    	colDesc.setCellValueFactory(new PropertyValueFactory<Query,String>("description"));
	    	colNote.setCellValueFactory(new PropertyValueFactory<Query,String>("note"));
	    	
	    	
	    	queryList = queryDir.populateQueryTable();
	    	tblQuery.setItems(queryList);
	    	
	    	cbStatus.getItems().removeAll(cbStatus.getItems());
	    	cbStatus.getItems().addAll(statusList());
	    	cbStatus.getSelectionModel().select(statusList().get(0));
	    	
	    	
		}
	    
	    
	    public List<String> statusList() {
			List<String> status = new ArrayList<>();
			
			status.add("Query Raised");
			status.add("Technician Assigned");
			status.add("In - Progress");
			status.add("Completed");
			
			return status;
		}
	    
	    @FXML
	    public void getSelected(MouseEvent event) {
	    	index = tblQuery.getSelectionModel().getSelectedIndex();
	    	
	    	if(index <= -1) {
	    		return;
	    	}
	    	
	    	txtQueryId.setText(colQueryId.getCellData(index).toString());
	    	//txtArNote.setText(colNote.getCellData(index).toString());
	    	String status = colStatus.getCellData(index).toString();
	    	
	    	if(status.equals("Query Raised")) {
	    		cbStatus.getSelectionModel().select(statusList().get(0));
	    	} else if(status.equals("Technician Assigned")) {
	    		cbStatus.getSelectionModel().select(statusList().get(1));
	    	} else if(status.equals("In - Progress")) {
	    		cbStatus.getSelectionModel().select(statusList().get(2));
	    	} else if(status.equals("Completed")) {
	    		cbStatus.getSelectionModel().select(statusList().get(3));
	    	} 
	    }
	    
	    @FXML
	    public void updateQuery() {
	    	
	    	Query q = new Query();
	    	
	    	q.setQueryId(Integer.parseInt(txtQueryId.getText()));
	    	q.setNote(txtArNote.getText());
	    	q.setStatus(cbStatus.getValue());
	    	
	    	queryDir.updateQueryTable(q);
	    	showAlert(AlertType.INFORMATION, "Alert", "Query Updated!");
	    	initialize();
	    	
	    	txtQueryId.clear();
	    	txtArNote.clear();
	    	cbStatus.getSelectionModel().clearSelection();
	    	
	    	
	    }
	    
	    
	    private void showAlert(AlertType alertType, String title, String content) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);

	        alert.showAndWait();
	    }
		

	


}
