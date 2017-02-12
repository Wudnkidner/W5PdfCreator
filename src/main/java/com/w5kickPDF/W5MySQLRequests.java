package com.w5kickPDF;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by albert on 27.08.16.
 */
public class W5MySQLRequests {

    private static ArrayList<String> tournamentList;
    private static  ArrayList<String> placeList;
    private static  ArrayList<String> dateList;
    private static  ArrayList<String> fightNumList;
    private static  ArrayList<String> weightList;
    private static ArrayList<String> fightersList;
    private static ArrayList<String> countryList;
    //private static ArrayList<String> countryBlueList;
    private static  ArrayList<String> judgeList;
    private static  ArrayList<String> refereeList;
    private static ObservableList<W5FightsData> fightsList;

    public static void insertRow (
            String tournamentText, String placeText, String dateText,
            String fightNumText, String weightText, String fighterRedText, String countryRedText,
            String fighterBlueText, String countryBlueText, String judge1Text,
            String judge2Text, String judge3Text, String refereeText) throws SQLException{

        Connection connection = W5MySQLConnection.getConnection();

        String insertString =
                "INSERT INTO Fights"+
                "(eventname, place, date, fightnumber, weight, cornerred,countryred," +
                "cornerblue,countryblue,firstjudge,secondjudge,thridjudge, referee)" +
                "VALUES" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        if ( connection != null) {
            PreparedStatement preparedStmt = connection.prepareStatement(insertString);
            preparedStmt.setString(1, tournamentText);
            preparedStmt.setString(2, placeText);
            preparedStmt.setString(3, dateText);
            preparedStmt.setString(4, fightNumText);
            preparedStmt.setString(5, weightText);
            preparedStmt.setString(6, fighterRedText);
            preparedStmt.setString(7, countryRedText);
            preparedStmt.setString(8, fighterBlueText);
            preparedStmt.setString(9, countryBlueText);
            preparedStmt.setString(10, judge1Text);
            preparedStmt.setString(11, judge2Text);
            preparedStmt.setString(12, judge3Text);
            preparedStmt.setString(13, refereeText);
            preparedStmt.execute();
            connection.close();
            W5CreateFightStage.setStatus("Complete");
        } else {
            W5CreateFightStage.setStatus("Error");
        }
    }




    public static void deleteRow (String fighNumb) throws SQLException {
        Connection connection = W5MySQLConnection.getConnection();

        String insertString = "DELETE FROM Fights WHERE fightnumber = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setString(1,fighNumb);
        preparedStatement.execute();
        connection.close();

    }

    public static ArrayList<String> getTournamentsList() throws SQLException{
        tournamentList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Tournaments");
        while(rs.next()) {
            tournamentList.add(rs.getString("name"));
        }
        return tournamentList;
    }

    public static ArrayList<String> getPlaceList() throws SQLException{
        placeList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Tournaments");
        while(rs.next()) {
            placeList.add(rs.getString("place"));
        }
        return placeList;
    }

    public static ArrayList<String> getDateList() throws SQLException{
        dateList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Tournaments");
        while(rs.next()) {
            dateList.add(rs.getString("date"));
        }
        return dateList;
    }

    public static ArrayList<String> getFightNumList() throws SQLException{
        fightNumList = new ArrayList<String>();
        /*
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fights");

        while(rs.next()) {
            fightNumList.add(rs.getString("id"));
        }
        */
        for (int i = 1; i < 100; i++) {
            fightNumList.add(Integer.toString(i));
        }

        return fightNumList;
    }

    public static ArrayList<String> getWeightList() throws SQLException{
        weightList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fighters");
        while(rs.next()) {
            weightList.add(rs.getString("weight"));
        }
        return weightList;
    }

    public static ArrayList<String> getFightersList() throws SQLException{
        fightersList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fighters");
            while(rs.next()) {
                fightersList.add(rs.getString("firstname") + " " + rs.getString("lastname"));
            }
        return fightersList;
    }

    public static ArrayList<String> getCountryList() throws SQLException{
        countryList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fighters");
        while(rs.next()) {
            countryList.add(rs.getString("country"));
        }
        return countryList;
    }

    public static ArrayList<String> getJudgeList() throws SQLException{
        judgeList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Judges");
        while(rs.next()) {
            judgeList.add(rs.getString("firstname")+" "+rs.getString("lastname"));
        }
        return judgeList;
    }

    public static ArrayList<String> getRefereeList() throws SQLException{
        refereeList = new ArrayList<String>();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Judges");
        while(rs.next()) {
            refereeList.add(rs.getString("firstname")+" "+rs.getString("lastname"));
        }
        return refereeList;
    }

    public static ObservableList<W5FightsData> getFightsList() throws SQLException{
        fightsList = FXCollections.observableArrayList();
        Connection connection = W5MySQLConnection.getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fights");
        while(rs.next()) {
            fightsList.add(new W5FightsData(rs.getString("eventname"),
                    rs.getString("place"), rs.getString("date"),
                    rs.getString("fightnumber"), rs.getString("weight"), rs.getString("cornerred"),
                    rs.getString("countryred"),  rs.getString("cornerblue"),
                    rs.getString("countryblue"),  rs.getString("firstjudge"),
                    rs.getString("secondjudge"),  rs.getString("thridjudge"),
                    rs.getString("referee")
                    ));
        }
        return fightsList;
    }


}


