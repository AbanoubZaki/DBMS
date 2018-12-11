package eg.edu.alexu.csd.oop.db.cs01.gui;

import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class dbmsGui extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Dbms.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manage Your Data Bases :D");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event ->{
			OurLogger.info(Table.class, "Buffered data is saved.");
		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
