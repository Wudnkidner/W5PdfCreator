package com.w5kickPDF;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by albert on 31.08.16.
 */
public class W5FightCardPDF {

    private static String src = System.getProperty("user.home") + "/resources/pdf/clear.pdf";
    private static String destToFightCard;


    private static final String FONT = System.getProperty("user.home") + "/resources/fonts/MyriadPro-BoldCond.otf";
    private static final String FONT_NAME_DIPLOMA = System.getProperty("user.home") + "/resources/fonts/Candara-Bold.ttf";
    private static final String FONT_NAME = System.getProperty("user.home") + "/resources/fonts/open-sans-regular.ttf";
    private static final String FONT_JUDGE = System.getProperty("user.home") + "/resources/fonts/open-sans-regular.ttf";

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
    private static String selectedValue ;
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


    public static void makeFightCard(int fightNumb) throws IOException, SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fights");
        while(rs.next()) {
            eventNameList.add(rs.getString("eventname"));
            placeList.add(rs.getString("place"));
            dateList.add(rs.getString("date"));
            fightNumbList.add(rs.getString("fightnumber"));
            cornerRedList.add(rs.getString("cornerred"));
            countryRedList.add(rs.getString("countryred"));
            cornerBlueList.add(rs.getString("cornerblue"));
            countryBlueList.add(rs.getString("countryblue"));
            firstJudgeList.add(rs.getString("firstjudge"));
            secondJudgeList.add(rs.getString("secondjudge"));
            thridJudgeList.add(rs.getString("thridjudge"));
            refereeList.add(rs.getString("referee"));

        }
        connection.close();
        /*
        for (int i = 0; i < eventNameList.size(); i++) {
            System.out.println(eventNameList.get(i) + " " + placeList.get(i) + " " +
                    dateList.get(i) + " " + fightNumbList.get(i) + " " +
                    cornerRedList.get(i) + " " + countryRedList.get(i) + " " +
                    cornerBlueList.get(i) + " " + countryBlueList.get(i) +
                    " " + firstJudgeList.get(i) + " " +
                    secondJudgeList.get(i) + " " + thridJudgeList.get(i)
                    + " " + refereeList.get(i));
        }
        */
        makePDF(fightNumb);
    }

    private static void makePDF (int fightNumb) throws IOException, SQLException {

        System.out.println(fightNumb);

        for (int docNumb = 0; docNumb < 3; docNumb++) {
            switch (docNumb) {
                case 0: judgeText = firstJudgeList.get(fightNumb); break;
                case 1: judgeText = secondJudgeList.get(fightNumb); break;
                case 2: judgeText = thridJudgeList.get(fightNumb); break;
                    default: judgeText = "Ошибка";
            }
      
            destToFightCard = System.getProperty("user.home") + "/result/Fight" + (fightNumb+1) + ", Judge " + judgeText + ".pdf";
            File createFightCard = new File(destToFightCard);
            createFightCard.getParentFile().mkdirs();

            PdfReader reader = new PdfReader(src);
            PdfWriter writer = new PdfWriter(destToFightCard);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);

            PdfPage page = pdfDoc.getPage(1);
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            Rectangle weightCategoryRct = new Rectangle(354, 733, 78, 40);
            Rectangle fightNumberRct = new Rectangle(496, 733, 62, 40);
            Rectangle tournamentRct = new Rectangle(125, 681, 188, 40);
            Rectangle cityRct = new Rectangle(350, 683, 84, 40);
            Rectangle dateRct = new Rectangle(470, 682, 91, 40);
            Rectangle nameRedRct = new Rectangle(115, 621, 185, 40);
            Rectangle nationalityRedRct = new Rectangle(125, 594, 175, 40);
            Rectangle nameBlueRct = new Rectangle(335, 621, 183, 40);
            Rectangle nationalityBlueRct = new Rectangle(335, 594, 153, 40);
            Rectangle refereeRct = new Rectangle(120, 362, 258, 40);
            Rectangle refereeNationRct = new Rectangle(430, 362, 122, 40);
            Rectangle judgeRct = new Rectangle(120, 317, 258, 40);
            Rectangle judgeNationRct = new Rectangle(430, 317, 122, 40);

            //pdfCanvas.rectangle(nationalityRedRct);
            //pdfCanvas.stroke();

            Canvas weightCategoryCnvs = new Canvas(pdfCanvas, pdfDoc, weightCategoryRct);
            Canvas fightNumberCategoryCnvs = new Canvas(pdfCanvas, pdfDoc, fightNumberRct);
            Canvas tournamentCnvs = new Canvas(pdfCanvas, pdfDoc, tournamentRct);
            Canvas cityCnvs = new Canvas(pdfCanvas, pdfDoc, cityRct);
            Canvas dateCnvs = new Canvas(pdfCanvas, pdfDoc, dateRct);
            Canvas nameRedCnvs = new Canvas(pdfCanvas, pdfDoc, nameRedRct);
            Canvas nationalityRedCnvs = new Canvas(pdfCanvas, pdfDoc, nationalityRedRct);
            Canvas nameBlueCnvs = new Canvas(pdfCanvas, pdfDoc, nameBlueRct);
            Canvas nationalityBlueCnvs = new Canvas(pdfCanvas, pdfDoc, nationalityBlueRct);
            Canvas refereeCnvs = new Canvas(pdfCanvas, pdfDoc, refereeRct);
            Canvas refereeNationCnvs = new Canvas(pdfCanvas, pdfDoc, refereeNationRct);
            Canvas judgeCnvs = new Canvas(pdfCanvas, pdfDoc, judgeRct);
            Canvas judgeNationCnvs = new Canvas(pdfCanvas, pdfDoc, judgeNationRct);

            PdfFont font = PdfFontFactory.createFont(FONT, "cp1251", true);
            PdfFont font_name = PdfFontFactory.createFont(FONT_NAME, "cp1251", true);
            PdfFont font_judge = PdfFontFactory.createFont(FONT_JUDGE, "cp1251", true);

            Paragraph tournament = new Paragraph(tournamentText = eventNameList.get(fightNumb))
                    .setFont(font)
                    .setFontSize(15)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph city = new Paragraph(cityText = getCity(eventNameList.get(fightNumb)))
                    .setFont(font)
                    .setFontSize(17)
                    .setTextAlignment(TextAlignment.CENTER);

            Paragraph date = new Paragraph(dateText = dateList.get(fightNumb))
                    .setFont(font)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);

            Paragraph nameRed = new Paragraph(nameRedText = cornerRedList.get(fightNumb))
                    .setFont(font_name)
                    .setFontSize(17)
                    .setFontColor(MYRED)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph nationalityRed = new Paragraph(nationalityRedText = countryRedList.get(fightNumb))
                    .setFont(font_name)
                    .setFontSize(14)
                    .setFontColor(MYRED)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph nameBlue = new Paragraph(nameBlueText = cornerBlueList.get(fightNumb))
                    .setFont(font_name)
                    .setFontSize(17)
                    .setFontColor(MYBLUE)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph nationalityBlue = new Paragraph(nationalityBlueText = countryBlueList.get(fightNumb))
                    .setFont(font_name)
                    .setFontSize(14)
                    .setFontColor(MYBLUE)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph referee = new Paragraph(refereeText = refereeList.get(fightNumb))
                    .setFont(font_judge)
                    .setFontSize(19)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph refereeNation = new Paragraph(refereeNationText = refereeCountry(refereeText))
                    .setFont(font_judge)
                    .setFontSize(19)
                    .setTextAlignment(TextAlignment.CENTER);

            Paragraph judge = new Paragraph(judgeText)
                    .setFont(font_judge)
                    .setFontSize(19)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph judgeNation = new Paragraph(judgeNationText = judgeCountry(judgeText))
                    .setFont(font_judge)
                    .setFontSize(19)
                    .setTextAlignment(TextAlignment.CENTER);

            Paragraph weightCategory = new Paragraph(weightCategoryText = weight(fightNumb))
                    .setFont(font)
                    .setFontSize(27)
                    .setTextAlignment(TextAlignment.CENTER);

            Paragraph fightNumber = new Paragraph(fightNumberText = fightNumb(nameRedText, nameBlueText, refereeText))
                    .setFont(font)
                    .setFontSize(27)
                    .setTextAlignment(TextAlignment.CENTER);

            weightCategoryCnvs.add(weightCategory);
            fightNumberCategoryCnvs.add(fightNumber);
            tournamentCnvs.add(tournament);
            cityCnvs.add(city);
            dateCnvs.add(date);
            nameRedCnvs.add(nameRed);
            nationalityRedCnvs.add(nationalityRed);
            nameBlueCnvs.add(nameBlue);
            nationalityBlueCnvs.add(nationalityBlue);
            refereeCnvs.add(referee);
            refereeNationCnvs.add(refereeNation);
            judgeCnvs.add(judge);
            judgeNationCnvs.add(judgeNation);

            pdfDoc.close();
           // System.out.println("Цикл: "+ docNumb + " завершен");
        }

    }

    private static String refereeCountry(String name) throws SQLException {
        String[] arr = name.split(" ");

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT country FROM Judges WHERE firstname = '"+arr[0]+"' AND lastname = '"+arr[1]+"'");
        String country = null;
        while(rs.next()) {
            country = rs.getString("country");
        }
        connection.close();
        return country;
    }

    private static String judgeCountry(String name) throws SQLException {
        String[] arr = name.split(" ");

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT country FROM Judges WHERE firstname = '"+arr[0]+"' AND lastname = '"+arr[1]+"'");
        String country = null;
        while(rs.next()) {
            country = rs.getString("country");
        }
        connection.close();
        return country;
    }

    private static String weight(int fightNumb) throws SQLException {


        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT weight FROM Fights WHERE fightnumber = "+ "'"+(fightNumb+1)+"'");
        String weight = null;
        while(rs.next()) {
            weight = rs.getString("weight");
            System.out.println(weight);
        }
        connection.close();
        
        if (weight.matches(".")) {
        String[] filter = weight.split("\\.");
        if (filter[1].equals("0")) {
            weight = filter[0];
        } else {
            weight = filter[0]+"."+filter[1];
        }
        }
        
        return weight;
    }

    private static String getCity(String eventName) throws SQLException {
        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT city FROM Tournaments WHERE name = "+ "'"+(eventName)+"'");
        String city = "";
        while(rs.next()) {
            city = rs.getString("city");
        }
        connection.close();
        return city;
    }

    private static String fightNumb(String cornerRed, String cornerBlue, String referee) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fightnumber FROM Fights WHERE cornerred = '"+cornerRed+"' AND cornerblue = '"+cornerBlue+"' AND referee = '"+refereeText+"'");
        String weight = null;
        while(rs.next()) {
            weight = rs.getString("fightnumber");
        }
        connection.close();
        return weight;
    }
}
