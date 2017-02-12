package com.w5kickPDF;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;


public class W5MainApp extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	public void start (Stage stage) throws Exception, SQLException {
		W5FirstScene.setFirstScene(stage);
	}
	
}
