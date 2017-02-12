package com.w5kickPDF;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by albert on 27.08.16.
 */
public class W5FightsData {



    private final SimpleStringProperty eventNameSSP;
    private final SimpleStringProperty placeSSP;
    private final SimpleStringProperty dateSSP;
    private final SimpleStringProperty weightSSP;
    private final SimpleStringProperty fightNumbSSP;
    private final SimpleStringProperty cornerRedSSP;
    private final SimpleStringProperty countryRedSSP;
    private final SimpleStringProperty cornerBlueSSP;
    private final SimpleStringProperty countryBlueSSP;
    private final SimpleStringProperty firstJudgeSSP;
    private final SimpleStringProperty secondJudgeSSP;
    private final SimpleStringProperty thridJudgeSSP;
    private final SimpleStringProperty refereeSSP;

    public W5FightsData (String eventName, String place, String date,
                          String fightNumb, String weight, String cornerRed, String countryRed,
                          String cornerBlue, String countryBlue, String firstJudge,
                          String secondJudge,String thridJudge, String referee) {
        this.eventNameSSP = new SimpleStringProperty(eventName);
        this.placeSSP = new SimpleStringProperty(place);
        this.dateSSP = new SimpleStringProperty(date);
        this.fightNumbSSP = new SimpleStringProperty(fightNumb);
        this.weightSSP = new SimpleStringProperty(weight);
        this.cornerRedSSP = new SimpleStringProperty(cornerRed);
        this.countryRedSSP = new SimpleStringProperty(countryRed);
        this.cornerBlueSSP = new SimpleStringProperty(cornerBlue);
        this.countryBlueSSP = new SimpleStringProperty(countryBlue);
        this.firstJudgeSSP = new SimpleStringProperty(firstJudge);
        this.secondJudgeSSP = new SimpleStringProperty(secondJudge);
        this.thridJudgeSSP = new SimpleStringProperty(thridJudge);
        this.refereeSSP = new SimpleStringProperty(referee);
    }

    public String getEventName() {
        return eventNameSSP.get();
    }

    public void setEventName(String eventName) {
        eventNameSSP.set(eventName);
    }

    public String getPlace() {
        return placeSSP.get();
    }

    public void setPlace(String place) {
        placeSSP.set(place);
    }

    public String getDate() {
        return dateSSP.get();
    }

    public void setDate(String date) {
        dateSSP.set(date);
    }

    public String getFightNumb() {
        return fightNumbSSP.get();
    }

    public void setFightNumb(String fightNumb) {
        fightNumbSSP.set(fightNumb);
    }

    public String getWeight() {
        return weightSSP.get();
    }

    public void setWeight(String weight) {
        weightSSP.set(weight);
    }

    public String getCornerRed() {
        return cornerRedSSP.get();
    }

    public void setCornerRed (String cornerRed) {
        cornerRedSSP.set(cornerRed);
    }

    public String getCountryRed() {
        return countryRedSSP.get();
    }

    public void setCountryRed(String countryRed) {
        countryRedSSP.set(countryRed);
    }

    public String getCornerBlue() {
        return cornerBlueSSP.get();
    }

    public void setCornerBlue(String cornerBlue) {
        cornerBlueSSP.set(cornerBlue);
    }

    public String getCountryBlue() {
        return countryBlueSSP.get();
    }

    public void setCountryBlue(String countryBlue) {
        countryBlueSSP.set(countryBlue);
    }

    public String getFirstJudge() {
        return firstJudgeSSP.get();
    }

    public void setFirstJudge(String firstJudge) {
        firstJudgeSSP.set(firstJudge);
    }

    public String getSecondJudge() {
        return secondJudgeSSP.get();
    }

    public void setSecondJudge(String secondJudge) {
        secondJudgeSSP.set(secondJudge);
    }

    public String getThridJudge() {
        return thridJudgeSSP.get();
    }

    public void setThridJudge(String thridJudge) {
        thridJudgeSSP.set(thridJudge);
    }

    public String getReferee() {
        return refereeSSP.get();
    }

    public void setReferee(String referee) {
        refereeSSP.set(referee);
    }
}
