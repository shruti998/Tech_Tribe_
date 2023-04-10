package home;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.stage.Stage;
//import jfxtras.styles.jmetro8.JMetro;
public class Main extends Application {
	
	private static Stage stage;

	 @Override
	    public void start(Stage primaryStage) throws Exception{
		 	stage = primaryStage;
		 	primaryStage.setResizable(false);
	        Parent root = FXMLLoader.load(getClass().getResource("fxml/SignIn.fxml"));
	        primaryStage.setTitle("Household Management");
	        //primaryStage.getIcons().add(new Image("/home/icons/icon.png"));
	        primaryStage.setScene(new Scene(root, 700, 400));
	        primaryStage.show();
	    }
	 
	 	
	 public void changeScene(String fxml) throws IOException {
	        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
	        stage.getScene().setRoot(pane);
	    }


	    
	    public static void main(String[] args) {
	        launch(args);
	    }

}
