package home.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import home.controller.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ViewChore {





    private Connection connection;

    public ViewChore() {
        // Establish a database connection
        connection = DatabaseConnection.Connect();
    }
    

    public ObservableList<Chores> getChoreListFromDatabase() {
        ObservableList<Chores> choreList = FXCollections.observableArrayList();

        try {
            // Execute a SELECT query to retrieve chore data from the database
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chores");
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the ResultSet and create a Chore object for each row
            while (resultSet.next()) {
                int choreId = resultSet.getInt("choreId");
                String intensity = resultSet.getString("intensity");
                String choreName= resultSet.getString("choreName");
                String howOften = resultSet.getString("howOften");
                String startDate = resultSet.getString("startDate");
                String endDate = resultSet.getString("endDate").toString();
                String status = resultSet.getString("status");
                String assignTo = resultSet.getString("assignTo");
                int homeId = resultSet.getInt("homeid");
 
                

                Chores chore = new Chores(choreName, intensity, howOften, startDate, endDate,assignTo, status,homeId);
                choreList.add(chore);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return choreList;
    }
}

