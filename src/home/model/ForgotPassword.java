//package home.model;
//
//import java.sql.*;
//
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//public class ForgotPassword extends Application {
//    // MySQL database credentials
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/householdmanagementdb";
//    private static final String USER = "root";
//    private static final String PASS = "root";
//
//    @Override
//    public void start(Stage primaryStage) {
//        // create form elements
//        Label usernameLabel = new Label("Username:");
//        TextField usernameField = new TextField();
//        Button forgotButton = new Button("Forgot Password");
//
//        // create form layout
//        GridPane formLayout = new GridPane();
//        formLayout.setPadding(new Insets(10));
//        formLayout.setVgap(10);
//        formLayout.setHgap(10);
//        formLayout.add(usernameLabel, 0, 0);
//        formLayout.add(usernameField, 1, 0);
//        formLayout.add(forgotButton, 1, 1);
//
//        // create scene and add form layout
//        Scene scene = new Scene(formLayout, 300, 200);
//
//        // handle button click
//        forgotButton.setOnAction(event -> {
//            String username = usernameField.getText();
//
//            // create password reset dialog
//            Dialog<String> resetDialog = new Dialog<>();
//            resetDialog.setTitle("Reset Password");
//
//            // create dialog elements
//            Label newPasswordLabel = new Label("New Password:");
//            PasswordField newPasswordField = new PasswordField();
//            Button resetButton = new Button("Reset Password");
//            resetDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
//
//            // create dialog layout
//            GridPane resetLayout = new GridPane();
//            resetLayout.setPadding(new Insets(10));
//            resetLayout.setVgap(10);
//            resetLayout.setHgap(10);
//            resetLayout.add(newPasswordLabel, 0, 0);
//            resetLayout.add(newPasswordField, 1, 0);
//            resetLayout.add(resetButton, 1, 1);
//
//            // add layout to dialog and set result converter
//            resetDialog.getDialogPane().setContent(resetLayout);
//            resetDialog.setResultConverter(dialogButton -> {
//                if (dialogButton == ButtonType.OK) {
//                    return newPasswordField.getText();
//                }
//                return null;
//            });
//
//            // show dialog and handle reset button click
//            resetDialog.showAndWait().ifPresent(newPassword -> {
//                // update password in database
//                try {
//                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//                    String query = "UPDATE users SET password = ? WHERE username = ?";
//                    PreparedStatement stmt = conn.prepareStatement(query);
//                    stmt.setString(1, newPassword);
//                    stmt.setString(2, username);
//                    stmt.executeUpdate();
//                    stmt.close();
//                    conn.close();
//                    System.out.println("Password reset for user " + username);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            });
//        });
//
//        // set stage properties and show
//        primaryStage.setTitle("Password Reset");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//
