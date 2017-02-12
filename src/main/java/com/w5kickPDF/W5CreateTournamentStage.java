package com.w5kickPDF;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class W5CreateTournamentStage {

	private static TextField tournamentTFld = new TextField();
	private static TextField cityTFld = new TextField();
	private static TextField placeTFld = new TextField();
	private static TextField dateTFld = new TextField();

        private static Screen screen = Screen.getPrimary();
        private static Rectangle2D bounds = screen.getVisualBounds();
        
	public static void setCreateTournamentStage (Stage stage) {


		GridPane gp = new GridPane();
		

		gp.addRow(0, new Label("Tournament name: "), tournamentTFld);
		gp.addRow(1, new Label("City: "), cityTFld);
		gp.addRow(2, new Label("Place: "), placeTFld);
		gp.addRow(3, new Label("Date: "), dateTFld);
		gp.addRow(4, new Label(""), W5Buttons.setSaveTournamentBtn());
		gp.addRow(5, new Label(""), W5Buttons.setBackBtn(stage));


		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(5);
		//gp.setMinSize(1024,768);

		//gp.setStyle("-fx-padding: 10;");


		Scene scene = new Scene(gp);
		stage.setScene(scene);
                stage.setMaximized(true);
		//stage.centerOnScreen();
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
		stage.show();
		
		
	}


	public static String getTournamentNameText () {
		return tournamentTFld.getText().toUpperCase();
	}

	public static String getTournamentCityText () {
		return cityTFld.getText().toUpperCase();
	}

	public static String getTournamentPlaceText () {
		return placeTFld.getText().toUpperCase();
	}

	public static String getTournamentDateText () {
		return dateTFld.getText();
	}

	public static void clearTFlds () {
		tournamentTFld.clear();
		cityTFld.clear();
		placeTFld.clear();
		dateTFld.clear();
	}
}


