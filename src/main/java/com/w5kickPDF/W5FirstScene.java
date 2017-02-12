package com.w5kickPDF;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class W5FirstScene {
        
        private static Screen screen = Screen.getPrimary();
        private static Rectangle2D bounds = screen.getVisualBounds();

	public static void setFirstScene  (Stage stage) {
		GridPane gp = new GridPane();
		//gp.setPrefSize(1024, 768);
		gp.addRow(0, W5Buttons.setCreateTournamentBtn(stage));
		//gp.addRow(1, W5Buttons.setCreateFighterBtn(stage));
		gp.addRow(1, W5Buttons.setCreateJudgeBtn(stage));
		gp.addRow(2, W5Buttons.setCreateFightBtn(stage));
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		
		Scene scene = new Scene(gp);
		stage.setScene(scene);
                stage.setMaximized(true);
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
		stage.show();
	}
}
