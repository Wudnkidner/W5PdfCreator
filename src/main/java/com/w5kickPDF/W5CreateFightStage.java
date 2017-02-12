package com.w5kickPDF;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class W5CreateFightStage {

        private static Screen screen = Screen.getPrimary();
        private static Rectangle2D bounds = screen.getVisualBounds();
    
	private static TextField fighterRedTF = new TextField();
	private static TextField fighterBlueTF = new TextField();
	private static TextField fightNumberTF = new TextField();
	private static TextField weightTF = new TextField();
	private static TextField countryRedTF = new TextField();
	private static TextField countryBlueTF = new TextField();

	private static W5SearchFighters fighterRedSearch = new W5SearchFighters();
	private static W5SearchFighters fighterBlueSearch = new W5SearchFighters();
	private static W5SearchFightNumber fightNumberSearch = new W5SearchFightNumber();
	private static W5SearchWeight weightSearch = new W5SearchWeight();
	private static W5SearchCountry2 countryRedSearch = new W5SearchCountry2();
	private static W5SearchCountry2 countryBlueSearch = new W5SearchCountry2();



	private static ComboBox tournamentCBox;
	private static ComboBox placeCBox;
	private static ComboBox dateCBox;
	private static ComboBox fightNumCBox;
	private static ComboBox weightCBox;
	private static ComboBox fighterRedCBox;
	private static ComboBox countryRedCBox;
	private static ComboBox fighterBlueCBox;
	private static ComboBox countryBlueCBox;
	private static ComboBox judge1CBox;
	private static ComboBox judge2CBox;
	private static ComboBox judge3CBox;
	private static ComboBox refereeCBox;

	private static String tournamentText;
	private static String placeText;
	private static String dateText;
	private static String fightNumText;
	private static String weightText;
	private static String fighterRedText;
	private static String fighterBlueText;
	private static String countryRedText;
	private static String countryBlueText;
	private static String judge1Text;
	private static String judge2Text;
	private static String judge3Text;
	private static String refereeText;

	private static Label statusLbl = new Label("waiting...");


	private static final ObservableList<W5FightsData> data =
			FXCollections.observableArrayList();

	private static GridPane gridPane;
	private static TableView<W5FightsData> tableView;

	public static void setCreateFightsStage (Stage stage) throws SQLException {
                System.out.println(screen);
                System.out.println(bounds);
		data.clear();
		gridPane = new GridPane();
		tableView = new TableView<W5FightsData>();
		data.addAll(W5MySQLRequests.getFightsList());

		//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		Scene scene = new Scene(new Group());
		//GridPane

		gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					if(mouseEvent.getClickCount() == 1){
						countryRedSearch.hideList(countryRedSearch.getList());
						countryBlueSearch.hideList(countryBlueSearch.getList());
					}
				}
			}
		});
		//gridPane.setPrefSize(1280, 500);
                gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(5);
		gridPane.setHgap(10);
		//gridPane.setPadding(new Insets(10.0));
		//gridPane.setAlignment(Pos.TOP_CENTER);
                gridPane.getColumnConstraints().add(new ColumnConstraints(74));
                gridPane.getColumnConstraints().add(new ColumnConstraints(100));
                gridPane.getColumnConstraints().add(new ColumnConstraints(74));
                gridPane.getColumnConstraints().add(new ColumnConstraints(100));
                
                gridPane.addRow(0, W5Buttons.setBackBtn(stage));
		gridPane.addRow(1, new Label("Event name:"), W5CreateFightStage.createTournamentCBox());
		gridPane.addRow(2, new Label("Place:") , W5CreateFightStage.createPlaceCBox(),new Label("Date:"), W5CreateFightStage.createDateCBox());
		gridPane.addRow(3, new Label("Fight number:"), fightNumberTF, new Label("Weight:"), weightTF);
		gridPane.addRow(4, new Label(""), fightNumberSearch.createListView(fightNumberTF) , new Label(""), weightSearch.createListView(weightTF));
		gridPane.addRow(5, new Label("Fighter red:"), fighterRedTF, new Label("Fighter blue:"), fighterBlueTF);
		gridPane.addRow(6, new Label(""), fighterRedSearch.createListView(fighterRedTF) , new Label(""), fighterRedSearch.createListView(fighterBlueTF));
		gridPane.addRow(7, new Label("Country red:"), countryRedTF, new Label("Country blue:"), countryBlueTF);
		gridPane.addRow(8, new Label(""), countryRedSearch.createListView(countryRedTF) , new Label(""), countryBlueSearch.createListView(countryBlueTF));
		gridPane.addRow(9, new Label("First judge:"), createJudge1CBox(), new Label("Second jdge:"),createJudge2CBox(), new Label("Third judge: "), createJudge3CBox());
		gridPane.addRow(10, new Label("Referee:"), createRefereeCBox());
		gridPane.addRow(11, new Label("Status: "), statusLbl);
		gridPane.addRow(12, new Label(""),new Label(""),new Label(""),new Label(""));
		gridPane.addRow(13, createAddBtn(),createDeleteBtn(),new Label(""),createMakePDFBtn());

		//TableView
		//tableView.setPrefSize(1280, 300);
		tableView.setEditable(true);
                
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn eventNameTC = new TableColumn("Event name");
		eventNameTC.setMinWidth(100);
		eventNameTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("eventName")
		);
		TableColumn placeTC = new TableColumn("Place");
		placeTC.setMinWidth(100);
		placeTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("place")
		);

		TableColumn dateTC = new TableColumn("Date");
		dateTC.setMinWidth(100);
		dateTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("date")
		);

		TableColumn fightNumbTC = new TableColumn("Fight #");
		fightNumbTC.setMinWidth(100);
		fightNumbTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("fightNumb")
		);

		TableColumn weightTC = new TableColumn("Weight");
		weightTC.setMinWidth(100);
		weightTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("weight")
		);

		TableColumn cornerRedTC = new TableColumn("Corner red");
		cornerRedTC.setMinWidth(100);
		cornerRedTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("cornerRed")
		);

		TableColumn countryRedTC = new TableColumn("Country red");
		countryRedTC.setMinWidth(100);
		countryRedTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("countryRed")
		);

		TableColumn cornerBlueTC = new TableColumn("Corner blue");
		cornerBlueTC.setMinWidth(100);
		cornerBlueTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("cornerBlue")
		);

		TableColumn countryBlueTC = new TableColumn("Country blue");
		countryBlueTC.setMinWidth(100);
		countryBlueTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("countryBlue")
		);

		TableColumn firstJudgeTC = new TableColumn("First judge");
		firstJudgeTC.setMinWidth(100);
		firstJudgeTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("firstJudge")
		);

		TableColumn secondJudgeTC = new TableColumn("Second judge");
		secondJudgeTC.setMinWidth(100);
		secondJudgeTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("secondJudge")
		);
		TableColumn thridJudgeTC = new TableColumn("Thrid judge");
		thridJudgeTC.setMinWidth(100);
		thridJudgeTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("thridJudge")
		);
		TableColumn refereeTC = new TableColumn("Referee");
		refereeTC.setMinWidth(100);
		refereeTC.setCellValueFactory(
				new PropertyValueFactory<W5FightsData, String>("referee")
		);
		tableView.setItems(data);
		tableView.getColumns().addAll(eventNameTC, placeTC, dateTC, fightNumbTC, weightTC, cornerRedTC,
				countryRedTC, cornerBlueTC, countryBlueTC, firstJudgeTC,
				secondJudgeTC,thridJudgeTC,refereeTC);

		final VBox vbox = new VBox();
                
		vbox.setSpacing(5);
               
                System.out.println(bounds.getWidth()/ 10);
		vbox.setPadding(new Insets(10,10,10,bounds.getWidth()/ 10.6666));
            
                
		vbox.getChildren().addAll(gridPane, tableView);
                
                
                
		((Group) scene.getRoot()).getChildren().addAll(vbox);
                
		//stage.setX((primaryScreenBounds.getMaxX()/2) - 640);
                
                //gridPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                
		stage.setScene(scene);
                
                stage.setMaximized(true);
		stage.show();

	}



	private static Button createMakePDFBtn () throws SQLException {
		Button addBtn = new Button();
		addBtn.setPrefWidth(156);
		addBtn.setText("CreatePDF");
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event)  {
				try {
					Connection connection = W5MySQLConnection.getConnection();
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM Fights");
					ArrayList<String> countFight = new ArrayList<String>();
					while(rs.next()) {
						countFight.add(rs.getString("fightnumber"));
					}
					connection.close();
					for (int i = 0; i < countFight.size(); i++) {
						W5FightCardPDF.makeFightCard(i);
						W5JudgesListPDF.makeJudgeList(i);
						W5DiplomaPDF.makeDiploma(i);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}) ;

		return addBtn;
	}

	private static Button createAddBtn () throws SQLException {
		Button addBtn = new Button();
		addBtn.setPrefWidth(156);
		addBtn.setText("Add Row");
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event)  {
				try {
					data.clear();//"обнуляю" ячейки в tableview
					W5MySQLRequests.insertRow(tournamentText, placeText, dateText,
                            fightNumberTF.getText(),  weightTF.getText(), fighterRedTF.getText(), countryRedTF.getText(),
                            fighterBlueTF.getText(), countryBlueTF.getText(), judge1Text,
                            judge2Text, judge3Text, refereeText);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					data.addAll(W5MySQLRequests.getFightsList());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				fighterRedTF.clear();
				fighterBlueTF.clear();
			}
		}) ;

		return addBtn;
	}

	private static Button createDeleteBtn () {
		Button addBtn = new Button("Delete Row");
		addBtn.setPrefWidth(156);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				W5FightsData selectedItem = tableView.getSelectionModel().getSelectedItem();
				tableView.getItems().remove(selectedItem);
				try {
					W5MySQLRequests.deleteRow(selectedItem.getFightNumb());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					data.clear();
					tableView.refresh();
					data.addAll(W5MySQLRequests.getFightsList());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return addBtn;
	}

	private static ComboBox createTournamentCBox () throws SQLException {
		tournamentCBox = new ComboBox();
		tournamentCBox.getItems().addAll(W5MySQLRequests.getTournamentsList());
		tournamentCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tournamentText = newValue;
			}
		});
		return tournamentCBox;
	}

	private static ComboBox createPlaceCBox () throws SQLException {
		placeCBox = new ComboBox();
		placeCBox.getItems().addAll(W5MySQLRequests.getPlaceList());
		placeCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				placeText = newValue;
			}
		});
		return placeCBox;
	}

	private static ComboBox createDateCBox () throws SQLException {
		dateCBox = new ComboBox();
		dateCBox.getItems().addAll(W5MySQLRequests.getDateList());
		dateCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				dateText = newValue;
			}
		});
		return dateCBox;
	}

	private static ComboBox createFightNumCBox () throws SQLException {
		fightNumCBox = new ComboBox();
		fightNumCBox.getItems().addAll(W5MySQLRequests.getFightNumList());
		fightNumCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				fightNumText = newValue;
			}
		});
		return fightNumCBox;
	}
/*
	private static ComboBox createWeightCBox () throws SQLException {
		weightCBox = new ComboBox();
		ArrayList<String> allWC= new ArrayList();
		for (double i = 50; i < 150; i+=0.5) {
			allWC.add(Double.toString(i));
		}
		weightCBox.getItems().addAll(allWC);
		//weightCBox.getItems().addAll(W5MySQLRequests.getWeightList());
		weightCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				weightText = newValue;
			}
		});
		return weightCBox;
	}
*/

	private static TextField createWeightTF () {
		TextField weightTF = new TextField();
		weightTF.setPrefColumnCount(13);

		return weightTF;
	}

	private static ComboBox createFighterRedCBox () throws SQLException {
		fighterRedCBox = new ComboBox();
		fighterRedCBox.getItems().addAll(W5MySQLRequests.getFightersList());
		fighterRedCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				fighterRedText = newValue;
			}
		});
		return fighterRedCBox;
	}

	private static ComboBox createFighterBlueCBox () throws SQLException {
		fighterBlueCBox = new ComboBox();
		fighterBlueCBox.getItems().addAll(W5MySQLRequests.getFightersList());
		fighterBlueCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				fighterBlueText = newValue;
			}
		});
		return fighterBlueCBox;
	}


	private static ComboBox createCountryRedCBox () throws SQLException {
		countryRedCBox = new ComboBox();
		countryRedCBox.getItems().addAll(W5MySQLRequests.getCountryList());
		countryRedCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				countryRedText = newValue;
			}
		});
		return countryRedCBox;
	}

	private static ComboBox createCountryBlueCBox () throws SQLException {
		countryBlueCBox = new ComboBox();
		countryBlueCBox.getItems().addAll(W5MySQLRequests.getCountryList());
		countryBlueCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				countryBlueText = newValue;
			}
		});
		return countryBlueCBox;
	}

	private static ComboBox createJudge1CBox () throws SQLException {
		judge1CBox = new ComboBox();
		judge1CBox.getItems().addAll(W5MySQLRequests.getJudgeList());
		judge1CBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				judge1Text = newValue;
			}
		});
		return judge1CBox;
	}

	private static ComboBox createJudge2CBox () throws SQLException {
		judge2CBox = new ComboBox();
		judge2CBox.getItems().addAll(W5MySQLRequests.getJudgeList());
		judge2CBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				judge2Text = newValue;
			}
		});
		return judge2CBox;
	}

	private static ComboBox createJudge3CBox () throws SQLException {
		judge3CBox = new ComboBox();
		judge3CBox.getItems().addAll(W5MySQLRequests.getJudgeList());
		judge3CBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				judge3Text = newValue;
			}
		});
		return judge3CBox;
	}

	private static ComboBox createRefereeCBox () throws SQLException {
		refereeCBox = new ComboBox();
		refereeCBox.getItems().addAll(W5MySQLRequests.getRefereeList());
		refereeCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				refereeText = newValue;
			}
		});
		return refereeCBox;
	}

	public static void setStatus (final String status) {
		statusLbl.setText(status);
		statusLbl.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY.EMPTY,Insets.EMPTY)));
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				statusLbl.setText("waiting...");
				statusLbl.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY.EMPTY,Insets.EMPTY)));
			}
		});
		new Thread(sleeper).start();
	}

	public static void setFighterRedText(String txt) {
		fighterRedText = txt;
	}

	public static void setFighterBlueText(String txt) {
		fighterBlueText = txt;
	}

	public static String getRedCountry () {
		return countryRedText;
	}
}
