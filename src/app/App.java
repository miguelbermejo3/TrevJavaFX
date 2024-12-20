package app;



import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import app.gui.AppController;
import app.mongodb.MongoSession;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{


	@Override
	public void start(Stage primaryStage) throws Exception {
		
    
		AppController controller = new AppController(primaryStage);
		
		controller.cambiarVista(AppController.FXML_INICIO_SESION);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	

}
