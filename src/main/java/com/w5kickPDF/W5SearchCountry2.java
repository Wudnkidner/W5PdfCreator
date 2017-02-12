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
public class W5SearchCountry2 {

    ListView list = new ListView();

        public ListView createListView(final TextField txt) throws SQLException {
            final ObservableList<String> entries = FXCollections.observableArrayList();


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
            entries.addAll("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe");

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

            if (newVal.length() > 0 && (!Objects.equals(newVal, subentries.get(0).toString()))) {

                list.setPrefHeight(180);
                list.setVisible(true);

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


            } else {
                list.setPrefHeight(0);
                list.setVisible(false);
            }



        }


        public void hideList(ListView list) {
            list.setPrefHeight(0);
            list.setVisible(false);
        }

        public ListView getList() {

            return list;
        }

}

