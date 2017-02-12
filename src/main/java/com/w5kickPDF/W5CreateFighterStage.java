package com.w5kickPDF;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class W5CreateFighterStage {

	private static TextField firstNameTFld = new TextField();
	private static TextField lastNameTFld = new TextField();
	private static TextField countryTFld = new TextField();
	private static TextField weightTFild = new TextField();
	private static W5Search searchFighterName = new W5Search();

	public static void setCreateFighterStage (Stage stage) {

		GridPane gp = new GridPane();
		gp.setPrefSize(1024, 768);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(5);
		gp.setHgap(10);
		gp.addRow(0, new Label("First name: "), firstNameTFld);
		gp.addRow(1, new Label("Last name: "), lastNameTFld);
		gp.addRow(2, new Label("Country: "), countryTFld);
		gp.addRow(3, new Label("Weight: "), weightTFild);
		gp.addRow(4, new Label(""), W5Buttons.setSaveFighterBtn());
		gp.addRow(5, new Label(""), W5Buttons.setBackBtn(stage));


		Scene scene = new Scene(gp);
		stage.setScene(scene);
                stage.setMaximized(true);
		stage.show();
		
		
	}

	public static String getFirstNameText () {
		return firstNameTFld.getText();
	}

	public static String getLastNameText () {
		return lastNameTFld.getText();
	}

	public static String getCountryText () {
		return countryTFld.getText();
	}

	public static String getWeightText () {
		return weightTFild.getText();
	}

	public static void clearTFlds () {
		firstNameTFld.clear();
		lastNameTFld.clear();
		countryTFld.clear();
		weightTFild.clear();
	}
}
