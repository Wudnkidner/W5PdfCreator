package com.w5kickPDF;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class W5PDFCreator {

    private static String element = "";
    private static final String SRС_JUDGE_LIST = System.getProperty("user.home") + "/resources/pdf/judge_list.pdf";
    private static final String SRC_DIPLOMA = System.getProperty("user.home") + "/resources/pdf/diploma.pdf";

    private static String DEST = System.getProperty("user.home") + "/result/Clear1.pdf";
    private static String DEST_JUDGE_LIST = System.getProperty("user.home") + "/judge_list1.pdf";


    private static final String FONT = System.getProperty("user.home") + "/resources/fonts/MyriadPro-BoldCond.otf";
    private static final String FONT_NAME_DIPLOMA = System.getProperty("user.home") + "/resources/fonts/CondaraBold.ttf";
    private static final String FONT_NAME = System.getProperty("user.home") + "/resources/fonts/OpenSans-Regular.ttf";
    private static final String FONT_JUDGE = System.getProperty("user.home") + "/resources/fonts/OpenSans-Regular.ttf";


    private static final Color MYBLUE = new DeviceCmyk(70, 30, 0, 18);
    private static final Color MYRED = new DeviceCmyk(0, 83, 84, 13);

    private static String weightCategoryText;
    private static String fightNumberText;
    private static String tournamentText;
    private static String cityText;
    private static String placeText;
    private static String dateText;
    private static String nameRedText;
    private static String nationalityRedText;
    private static String nameBlueText;
    private static String nationalityBlueText;
    private static String refereeText;
    private static String refereeNationText;
    private static String judgeText;
    private static String judgeNationText;
    private static String selectedValue;
    private static String selectedValue2;

    private static ArrayList<String> eventNameList = new ArrayList<String>();
    private static ArrayList<String> placeList = new ArrayList<String>();
    private static ArrayList<String> dateList = new ArrayList<String>();
    private static ArrayList<String> fightNumbList = new ArrayList<String>();
    private static ArrayList<String> cornerRedList = new ArrayList<String>();
    private static ArrayList<String> countryRedList = new ArrayList<String>();
    private static ArrayList<String> cornerBlueList = new ArrayList<String>();
    private static ArrayList<String> countryBlueList = new ArrayList<String>();
    private static ArrayList<String> firstJudgeList = new ArrayList<String>();
    private static ArrayList<String> secondJudgeList = new ArrayList<String>();
    private static ArrayList<String> thridJudgeList = new ArrayList<String>();
    private static ArrayList<String> refereeList = new ArrayList<String>();

		/*
		 * Set the padding of the GridPane
		 * Set the border-style of the GridPane
		 * Set the border-width of the GridPane
		 * Set the border-insets of the GridPane
		 * Set the border-radius of the GridPane
		 * Set the border-color of the GridPane
		*/



}
/*
class TestDrivePDF {

    public static void main(String[] args) throws IOException, SQLException {



        //

        for (int i = 0; i < W5MySQLRequests.getFightsList().size(); i++) {
            //W5FightCardPDF.makeFightCard(i);
            //W5JudgesListPDF.makeJudgeList(i);
            //W5DiplomaPDF.makeDiploma(i);
            //W5MedicalPDF.makeMedical(i);
            W5WeightInPDF.makeWeightIn(i);
        }
    }

}
*/