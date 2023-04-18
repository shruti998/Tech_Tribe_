package home;

//import java.awt.event.ActionEvent;
import java.io.IOException;

//import home.controller.EditController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.stage.Stage;
//import javafx.scene.Node;
//import jfxtras.styles.jmetro8.JMetro;
public class Main extends Application {
	
	private static Stage stage;

	//private static ActionEvent event;


	 @Override
	    public void start(Stage primaryStage) throws Exception{
		 	stage = primaryStage;
		 	primaryStage.setResizable(false);
	        Parent root = FXMLLoader.load(getClass().getResource("fxml/SignIn.fxml"));
	        primaryStage.setTitle("Household Management");
	        //primaryStage.getIcons().add(new Image("/home/icons/icon.png"));
	        primaryStage.setScene(new Scene(root, 670, 600));
	        primaryStage.show();
	    }
	 

	 	
	 public void changeScene(String fxml) throws IOException {
	        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
	        stage.getScene().setRoot(pane);

	        
	    }
	 
	 public void changeStage(Parent root) throws IOException {
		 stage.getScene().setRoot(root);
	 }
	
	    
	    public static void main(String[] args) {
	        launch(args);
	    }
	    

}