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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;


/**
 * Created by albert on 11.09.16.
 */
public class W5SearchFighters {

        public ListView createListView(final TextField txt) throws SQLException {
            final ObservableList<String> entries = FXCollections.observableArrayList();
            final ListView list = new ListView();

            txt.setPromptText("Search");
            txt.textProperty().addListener(
                    new ChangeListener() {
                        public void changed(ObservableValue observable,
                                            Object oldVal, Object newVal) {
                            handleSearchByKey(txt, list, entries, (String)oldVal, (String)newVal);
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

        private void handleSearchByKey(final TextField txt, final ListView list, final ObservableList<String> entries, String oldVal, String newVal) {
            // If the number of characters in the text box is less than last time
            // it must be because the user pressed delete
            if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
                // Restore the lists original set of entries
                // and start from the beginning
                list.setItems( entries );
            }

            //Clone newVal
            String newVAlClone = newVal;

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
            newVal = newVAlClone;

            if (newVal.length() > 0) {

                list.setPrefHeight(180);
                list.setVisible(true);

                if (newVal.length() > 6 && subentries.size() == 0) {
                    ObservableList<String> fighter = FXCollections.observableArrayList();
                    fighter.add("Click to add a "+ newVal +" to the database.");
                    list.setItems(fighter);
                    final String finalNewVal = newVal;
                    list.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                                if(mouseEvent.getClickCount() == 2){

                                    try {
                                        newFighterQuery(list,finalNewVal,entries);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Сработала 2 команда");
                                    list.setPrefHeight(0);
                                    list.setVisible(false);
                                }
                            }
                        }
                    });
                } else {
                    list.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                                if(mouseEvent.getClickCount() == 2){
                                    txt.setText((String)list.getSelectionModel()
                                            .getSelectedItem());
                                    System.out.println("Сработала 1 команда");
                                    list.setPrefHeight(0);
                                    list.setVisible(false);
                                }
                            }
                        }
                    });
                }

            } else {
                list.setPrefHeight(0);
                list.setVisible(false);
            }



        }

        public String getFighterText (TextField txt) {
            String fighterText = txt.getText();
            return fighterText;
        }

        public static void newFighterQuery (ListView list, String finalNewVal, final ObservableList<String> entries) throws SQLException {
            Connection connection = W5MySQLConnection.getConnection();

            String insertString =
                    "INSERT INTO Fighters"+
                            "(firstname, lastname,country,weight)" +
                            "VALUES" +
                            "(?,?,?,?)";

            if ( connection != null) {
                PreparedStatement preparedStmt = null;

                    preparedStmt = connection.prepareStatement(insertString);

                String[] nameFilter = finalNewVal.split(" ");
                preparedStmt.setString(1, nameFilter[0]);
                preparedStmt.setString(2, nameFilter[1]);
                preparedStmt.setString(3, "");
                preparedStmt.setString(4, "");
                preparedStmt.execute();
                connection.close();
                entries.clear();

                    entries.addAll(W5MySQLRequests.getFightersList());
                list.setItems( entries );
                W5CreateFightStage.setStatus("Complete");
            } else {
                W5CreateFightStage.setStatus("Error");
            }
        }
}

