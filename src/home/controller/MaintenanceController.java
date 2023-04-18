package home.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import home.Main;
import home.model.MaintenanceAdmin;
import home.model.Query;
import home.model.QueryDirectory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class MaintenanceController {
	
	


	    @FXML
	    private Button btnSubmit;
	    
	    @FXML
	    private Button btnSubmitQuery;
	    
	    @FXML
	    private Button btnUpdate;
	    
	    @FXML
	    private Label lblErrorP;

	    @FXML
	    private ComboBox<String> cbHomeAddress;

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
	    private PasswordField txtCnfrmPwdP;

	    @FXML
	    private TextField txtEmailP;

	    @FXML
	    private PasswordField txtPwdP;
	    
	    @FXML
	    private TextField txtQueryId;
	    
	    @FXML
	    private TextField txtUserNameP;
	    
	    @FXML
	    private Button btnLogout;
	    
	    ObservableList<Query> queryList;
	    
	    QueryDirectory queryDir = new QueryDirectory();
	    
	    private String uName;
	    private String email;
	    
	    
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
	    	
	    	
	    	cbHomeAddress.getItems().removeAll(cbHomeAddress.getItems());
	    	cbHomeAddress.getItems().addAll(queryDir.populateHomeDet());
	    	
	    	
	    	
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
	    	
	    	if(txtArNote.getText().isEmpty() || txtArNote.getText().isBlank()) {
	    		
	    		showAlert(AlertType.ERROR, "Error", "Note field is Empty!!");
	    		
	    	} else {
	    		
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
	    	
	    	
	    }
	    
	    
	    private void showAlert(AlertType alertType, String title, String content) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);

	        alert.showAndWait();
	    }
	    
	    
	    public void sendNotification() {
	    	
	    	
	    	if(cbHomeAddress.getValue() == null) {
	    		
	    		showAlert(AlertType.ERROR, "Error", "Select Home Address!!");
	    		
	    		
	    	} else if(txtArNotification.getText().isEmpty() || txtArNotification.getText().isBlank()) {
	    		
	    		showAlert(AlertType.ERROR, "Error", "Notification field is Empty!!");
	    		
	    		
	    	} else {
	    		
	    		String address = cbHomeAddress.getValue();
		    	String notification = txtArNotification.getText();
	    		
	    		queryDir.notifyHome(address, notification);
		    	showAlert(AlertType.INFORMATION, "Alert", "Notification Sent!!!");
		    	
		    	cbHomeAddress.getSelectionModel().clearSelection();
		    	txtArNotification.clear();
	    		
	    	}
	    	
	    	
	    	
	    }
	    
	    
	    public void updateProfile() {
	    	
			String uName = txtUserNameP.getText();
			String email = txtEmailP.getText();
			String confirmPass = txtCnfrmPwdP.getText();
			
			if(updateValidation()) {
				
				MaintenanceAdmin mAdmin = new MaintenanceAdmin();

				mAdmin.setuName(uName);
				mAdmin.setEmail(email);
				mAdmin.setPassword(confirmPass);
				
				
				mAdmin.updateAdminProfile();
				//populate profile
		        
		        showAlert(AlertType.INFORMATION, "Alert", "Profile Updated!");
	            
		        
				
			}
	    }
	    
	    
		
	    private boolean updateValidation() {
			
			
			if (txtEmailP.getText().isEmpty() || txtCnfrmPwdP.getText().isEmpty() || txtPwdP.getText().isEmpty()) {
				lblErrorP.setTextFill(Color.RED);
	            lblErrorP.setText("Please fill in all fields.");
	            
	            return false;
	        }
			
			
			
			if(!txtEmailP.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
		       {
		           lblErrorP.setTextFill(Color.RED);
		           lblErrorP.setText("Email Id Incorrect.");
		           
		           return false;
		       }
			
			if (!txtPwdP.getText().equals(txtCnfrmPwdP.getText())) {
				lblErrorP.setTextFill(Color.RED);
				lblErrorP.setText("Passwords do not match.");
				
				return false;
	        }
			
			
			return true;
		}


		public String getuName() {
			System.out.println("test2 "+uName);
			return uName;
			
		}


		public void setuName(String uName) {
			this.uName = uName;
			System.out.println("test1 "+this.uName);
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}

		
		public void displayInfo(String uName) {
         MaintenanceAdmin mainC = new MaintenanceAdmin();
			
			
			//System.out.println(getuName());
			txtUserNameP.setText(uName);
			txtEmailP.setText(mainC.getAdminDetails(uName));
		}
		
		
		public void logOut() throws IOException {
	        
	        
	        Main m = new Main();
	        
	        m.changeScene("fxml/SignIn.fxml");
			
		}
		
		
		
	    

	    
	


}
