package com.w5kickPDF;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;


/**
 * Created by albert on 11.09.16.
 */
public class W5Search {




        public ListView createListView(final TextField txt) throws SQLException {
            final ObservableList<String> entries = FXCollections.observableArrayList();
            final ListView list = new ListView();

            txt.setPromptText("Search");
            txt.textProperty().addListener(
                    new ChangeListener() {
                        public void changed(ObservableValue observable,
                                            Object oldVal, Object newVal) {
                            handleSearchByKey(list, entries, (String)oldVal, (String)newVal);
                        }
                    });

            list.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            txt.setText((String)list.getSelectionModel()
                                    .getSelectedItem());

                            list.setPrefHeight(0);
                            list.setVisible(false);
                        }
                    }
                }
            });



            // Set up the ListView

            list.setPrefHeight(0);
            list.setVisible(false);

            // Populate the list's entries
            entries.addAll(W5MySQLRequests.getFightersList());
            list.setItems( entries );

            return list;
        }

        private void handleSearchByKey(ListView list, ObservableList<String> entries, String oldVal, String newVal) {
            // If the number of characters in the text box is less than last time
            // it must be because the user pressed delete
            if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
                // Restore the lists original set of entries
                // and start from the beginning
                list.setItems( entries );
            }

            if (newVal.length() > 0) {

                list.setPrefHeight(180);
                list.setVisible(true);

            } else {
                list.setPrefHeight(0);
                list.setVisible(false);
            }


            // Change to upper case so that case is not an issue
            newVal = newVal.toUpperCase();

            // Filter out the entries that don't contain the entered text
            ObservableList<String> subentries = FXCollections.observableArrayList();
            for ( Object entry: list.getItems() ) {
                String entryText = (String)entry;
                if ( entryText.toUpperCase().contains(newVal) ) {
                    subentries.add(entryText);
                }

            }


            list.setItems(subentries);
        }


        public String getFighterText (TextField txt) {
            String fighterText = txt.getText();
            return fighterText;
        }
}

