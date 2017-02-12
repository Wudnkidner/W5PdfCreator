package com.w5kickPDF;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by albert on 25.08.16.
 */
public class W5MySQLConnection {


    public static Connection getConnection ()  {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost/w5database2?useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
        dataSource.setPort(3306);

        try {
            Connection conn = dataSource.getConnection();
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

 /*
    public static Connection getConnection () throws Exception {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/w5database2?useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
            String username = "root";
            String password = "root";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {System.out.println(e);

        }

        return null;


    }
 */

}
