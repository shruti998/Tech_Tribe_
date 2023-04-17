package home.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SplitBillCreateGroups {

    @FXML
    private TextField groupName;

    @FXML
    private ListView<String> listOfNames;

    @FXML
    private TextField name;

    private Parent root;
    
    @FXML
    void addName(MouseEvent event) {
    	listOfNames.getItems().add(name.getText());
    }

    
    @FXML
    void removeName(MouseEvent event) {
    	int selectedID = listOfNames.getSelectionModel().getSelectedIndex();
    	listOfNames.getItems().remove(selectedID);

    }
    
    @FXML
    void viewGroup(MouseEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/GroupDetails.fxml"));
         root = loader.load();
         GroupDetails groupDetails = loader.getController();
         
         
    }
    
    @FXML
    void saveData(ActionEvent event) {
         try (Connection connection = DatabaseConnection.Connect()){
    		for (String memberName : listOfNames.getItems()) {
                String memberQuery = "INSERT INTO all_groups (groupName, memberName) VALUES (?, ?)";
                PreparedStatement memberStatement = connection.prepareStatement(memberQuery);
                memberStatement.setString(1, groupName.getText());
                memberStatement.setString(2, memberName);
                memberStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 }


