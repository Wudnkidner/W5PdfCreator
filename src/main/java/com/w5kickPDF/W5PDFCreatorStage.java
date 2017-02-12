package com.w5kickPDF;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by albert on 28.08.16.
 */
public class W5PDFCreatorStage {

    public static void setCreateW5PDFCreatorStage (Stage stage) {
        GridPane gridPane = new GridPane();

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();

    }

}
