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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by albert on 02.09.16.
 */
public class W5MedicalPDF {

    private static final String FONT = System.getProperty("user.home") + "/resources/fonts/MyriadPro-BoldCond.otf";
    private static final String FONT_NAME_DIPLOMA = System.getProperty("user.home") + "/resources/fonts/CondaraBold.ttf";
    private static final String FONT_NAME = System.getProperty("user.home") + "/resources/fonts/OpenSans-Regular.ttf";
    private static final String FONT_JUDGE = System.getProperty("user.home") + "/resources/fonts/OpenSans-Regular.ttf";


    private static final Color MYBLUE = new DeviceCmyk(70, 30, 0, 18);
    private static final Color MYRED = new DeviceCmyk(0, 83, 84, 13);

    private static String cityText;
    private static String dateText;

    private static ArrayList<String> cornerRedAL = new ArrayList<String>();
    private static ArrayList<String> cornerBlueAL = new ArrayList<String>();
    private static ArrayList<String> placeAL = new ArrayList<String>();
    private static ArrayList<String> dateAL = new ArrayList<String>();
    private static ArrayList<String> allFightersAL = new ArrayList<String>();

    public static void makeMedical(int fightNumb) throws IOException, SQLException {
        fightNumb +=1; //Костыль для правильного отображения номера раунда
        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Fights");
        while(rs.next()) {
            placeAL.add(rs.getString("place"));
            dateAL.add(rs.getString("date"));
            cornerRedAL.add(rs.getString("cornerred"));
            cornerBlueAL.add(rs.getString("cornerblue"));
        }
        connection.close();

        for (int i = 0; i < cornerRedAL.size();i++) {
            allFightersAL.add(cornerRedAL.get(i));
        }

        for (int i = 0; i < cornerBlueAL.size(); i++) {
            allFightersAL.add(cornerBlueAL.get(0));
        }



        Set<String> hs = new HashSet<>();

        hs.addAll(allFightersAL);
        allFightersAL.clear();
        allFightersAL.addAll(hs);

        cityText = placeAL.get(fightNumb-1);
        dateText = dateAL.get(fightNumb-1);

        String srcToMedicalPdf = System.getProperty("user.home") + "/resources/pdf/medical.pdf";
        String destToMedicalPdf = System.getProperty("user.home") + "/result/medical.pdf";


        File medical = new File(destToMedicalPdf);
        medical.getParentFile().mkdirs();

        PdfReader reader = new PdfReader(srcToMedicalPdf);

        PdfWriter writer = new PdfWriter(destToMedicalPdf);


        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        PdfPage page1 = pdfDoc.getPage(1);
        PdfPage page2 = pdfDoc.getPage(2);
        PdfPage page3 = pdfDoc.getPage(3);

        PdfCanvas pdfCanvas1 = new PdfCanvas(page1);
        PdfCanvas pdfCanvas2 = new PdfCanvas(page2);
        PdfCanvas pdfCanvas3 = new PdfCanvas(page3);

        ArrayList<Rectangle> Page1Rct = new ArrayList<Rectangle>();
        ArrayList<Rectangle> Page2Rct = new ArrayList<Rectangle>();
        ArrayList<Rectangle> Page3Rct = new ArrayList<Rectangle>();

        Rectangle page1CityRct = new Rectangle(100, 446, 140, 20);
        Rectangle page1DateRct = new Rectangle(100, 428, 65, 20);

        float x = 95;
        float y = 365;
        float weight = 240;
        float height = 40;

        for (int index = 0; index < allFightersAL.size(); index++) {
            if (index % 14 == 0) {
                x = 95;
                y = 365;
                weight = 240;
                height = 40;
            }
            if (index < 14) {
                if (index != 0) y -= 0.2;
                Page1Rct.add(new Rectangle(x, y, weight, height));
                y -= 17;

            }
            if (index >= 14 && index < 28) {
                if (index != 0) y -= 0.2;
                Page2Rct.add(new Rectangle(x, y, weight, height));
                y -= 17;
            }
            if (index >= 28 && index < 42) {
                if (index != 0) y -= 0.2;
                Page3Rct.add(new Rectangle(x, y, weight, height));
                y -= 17;
            }

        }

        ArrayList<Canvas> Page1Cnvs = new ArrayList<Canvas>();
        ArrayList<Canvas> Page2Cnvs = new ArrayList<Canvas>();
        ArrayList<Canvas> Page3Cnvs = new ArrayList<Canvas>();

        Canvas page1CityCnvs = new Canvas(pdfCanvas1, pdfDoc, page1CityRct);
        Canvas page1DateCnvs = new Canvas(pdfCanvas1, pdfDoc, page1DateRct);

        Canvas page2CityCnvs = new Canvas(pdfCanvas2, pdfDoc, page1CityRct);
        Canvas page2DateCnvs = new Canvas(pdfCanvas2, pdfDoc, page1DateRct);

        Canvas page3CityCnvs = new Canvas(pdfCanvas3, pdfDoc, page1CityRct);
        Canvas page3DateCnvs = new Canvas(pdfCanvas3, pdfDoc, page1DateRct);

        //pdfCanvas1.rectangle(page1CityRct);
        //pdfCanvas1.stroke();

        for (int index = 0; index < Page1Rct.size(); index++) {
            Page1Cnvs.add(new Canvas(pdfCanvas1, pdfDoc, Page1Rct.get(index)));
        }

        for (int index = 0; index < Page2Rct.size(); index++) {
            Page2Cnvs.add(new Canvas(pdfCanvas2, pdfDoc, Page2Rct.get(index)));
        }

        for (int index = 0; index < Page3Rct.size(); index++) {
            Page3Cnvs.add(new Canvas(pdfCanvas3, pdfDoc, Page3Rct.get(index)));
        }

        PdfFont font = PdfFontFactory.createFont(FONT, "Cp1251", true);

        ArrayList<Paragraph> Page1Prg = new ArrayList<Paragraph>();
        ArrayList<Paragraph> Page2Prg = new ArrayList<Paragraph>();
        ArrayList<Paragraph> Page3Prg = new ArrayList<Paragraph>();



        for (int index = 0; index < allFightersAL.size(); index++) {
            if (index < 14) {
                Page1Prg.add(new Paragraph(allFightersAL.get(index))
                        .setFont(font)
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.LEFT));
            }

            if (index >= 14 && index < 28) {
                Page2Prg.add(new Paragraph(allFightersAL.get(index))
                        .setFont(font)
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.LEFT));
            }

            if (index >= 28 && index < 42) {
                Page2Prg.add(new Paragraph(allFightersAL.get(index))
                        .setFont(font)
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.LEFT));
            }

        }


        Paragraph page1CityPrg = new Paragraph(cityText)
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);
        Paragraph page1DatePrg = new Paragraph(dateText)
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);

        Paragraph page2CityPrg = new Paragraph(cityText)
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);
        Paragraph page2DatePrg = new Paragraph(dateText)
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);

        Paragraph page3CityPrg = new Paragraph(cityText)
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);
        Paragraph page3DatePrg = new Paragraph(dateText = eventDate(fightNumb))
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);

        for (int index = 0; index < Page1Cnvs.size(); index++) {
            Canvas cnvs = Page1Cnvs.get(index);
            cnvs.add(Page1Prg.get(index));
        }


        for (int index = 0; index < Page2Cnvs.size(); index++) {
            Canvas cnvs = Page2Cnvs.get(index);
            cnvs.add(Page2Prg.get(index));
        }

        for (int index = 0; index < Page3Cnvs.size(); index++) {
            Canvas cnvs = Page2Cnvs.get(index);
            cnvs.add(Page2Prg.get(index));
        }

        page1CityCnvs.add(page1CityPrg);
        page1DateCnvs.add(page1DatePrg);

        page2CityCnvs.add(page2CityPrg);
        page2DateCnvs.add(page2DatePrg);

        page3CityCnvs.add(page3CityPrg);
        page3DateCnvs.add(page3DatePrg);

        pdfDoc.close();
    }


    private static String eventName(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT eventname FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String eventname = null;
        while(rs.next()) {
            eventname = rs.getString("eventname");
        }
        connection.close();
        return eventname;
    }

    private static String eventDate(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT date FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String eventDate = null;
        while(rs.next()) {
            eventDate = rs.getString("date");
        }
        connection.close();
        return eventDate;
    }

    private static String eventCity(String eventName) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT city FROM Tournaments WHERE name = "+"'"+eventName+"'");
        String eventCity = null;
        while(rs.next()) {
            eventCity = rs.getString("city");
        }
        connection.close();
        return eventCity;
    }

    private static String eventPlace(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT place FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String eventPlace = null;
        while(rs.next()) {
            eventPlace = rs.getString("place");
        }
        connection.close();
        return eventPlace;
    }

    private static String cornerRed(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT cornerred FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String cornerRed = null;
        while(rs.next()) {
            cornerRed = rs.getString("cornerred");
        }
        connection.close();
        return cornerRed;
    }

    private static String cornerBlue(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT cornerblue FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String cornerBlue = null;
        while(rs.next()) {
            cornerBlue = rs.getString("cornerblue");
        }
        connection.close();
        return cornerBlue;
    }

    private static String firstJudge(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT firstjudge FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String judgeName = null;
        while(rs.next()) {
            judgeName = rs.getString("firstjudge");
        }
        connection.close();
        return judgeName;
    }

    private static String secondJudge(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT secondjudge FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String judgeName = null;
        while(rs.next()) {
            judgeName = rs.getString("secondjudge");
        }
        connection.close();
        return judgeName;
    }

    private static String thridJudge(int fightNumb) throws SQLException {

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT thridjudge FROM Fights WHERE fightnumber = "+"'"+(fightNumb)+"'");
        String judgeName = null;
        while(rs.next()) {
            judgeName = rs.getString("thridjudge");
        }
        connection.close();
        return judgeName;
    }

    private static String weight(String name) throws SQLException {
        String[] arr = name.split(" ");

        Connection connection = W5MySQLConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT weight FROM Fighters WHERE firstname = '"+arr[0]+"' AND lastname = '"+arr[1]+"'");
        String weight = null;
        while(rs.next()) {
            weight = rs.getString("weight");
        }
        connection.close();
        return weight;
    }

}
