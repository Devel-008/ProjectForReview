package com.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class MainCrud {
    public static void main(String[] args) {

        Connection connection = null;
        Logger logger = LoggerFactory.getLogger(MainCrud.class);
        logger.debug("Project starts here !!");
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:h2:tcp://localhost/~/test";
        String username = "sa";
        String password = "";

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(url, username, password);
            logger.info("connected");
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        try {
            assert connection != null;
            if (connection.isClosed()) {
                logger.info("Not Connected");
            }
        } catch (SQLException e) {
            logger.error(String.valueOf(e));
        }

        InsertInStudentRecords insert = new InsertInStudentRecords();
        StudentGetterSetter student = new StudentGetterSetter();
        DeleteStudentRecords delete = new DeleteStudentRecords();

        int choice;
        while (true) {
            logger.info("1] Press 1 to INSERT 2] Press 2 to DELETE 3]Press s to READ \n4]Press 3 to UPDATE 5]Press 4 to insert data from any JSON-File you want in database \n6]Press 5 to insert data in JSON File you want \n7]Press any other key to exit");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> insert.insert(connection, sc, student, logger);
                case 2 -> {
                    try{
                        delete.delete(connection, student, logger, sc);
                    }catch (Exception e){
                        logger.error(String.valueOf(e));
                    }
                }
                default -> {
                    logger.info("\nDo you want to continue:= Press any key or else Press 0 to exit!!");
                    int n = sc.nextInt();
                    if (n == 0) {
                        logger.info("Process Successful");
                        try {
                            connection.close();
                            sc.close();
                            logger.info("Connection Closed!!");
                        } catch (SQLException e) {
                            logger.error(String.valueOf(e));
                        }
                        return;
                    }
                }
            }
        }
    }
}
