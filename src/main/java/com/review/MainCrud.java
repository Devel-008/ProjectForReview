package com.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainCrud {
    public static void main(String[] args) {
        Logger logger = null;
        Connection connection = null;
        Scanner sc = new Scanner(System.in);
        try {
            connection = null;
            logger = LoggerFactory.getLogger(MainCrud.class);
            logger.info("Project starts here !!");

            String url = "jdbc:h2:tcp://localhost/~/test";
            String username = "sa";
            String password = "";
            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(url, username, password);
                logger.info("Connected");
            } catch (SQLException | ClassNotFoundException e) {
                logger.error("Error at Connected := ", e);
            }
            try {
                assert connection != null;
                if (connection.isClosed()) {
                    logger.info("Not Connected");
                }
            } catch (SQLException e) {
                logger.error("Error at notConnected := ", e);
            }

            InsertInStudentRecords insert = new InsertInStudentRecords();
            StudentGetterSetter student = new StudentGetterSetter();
            DeleteStudentRecords delete = new DeleteStudentRecords();
            FetchStudentRecords fetch = new FetchStudentRecords();
            UpdateStudentRecords up = new UpdateStudentRecords();
            JsonInsert json = new JsonInsert();
            int choice;
            while (true) {
                logger.info("""
                        1] Press 1 to INSERT 2] Press 2 to DELETE 3]Press 3 to READ\s
                        4]Press 4 to UPDATE 5]Press 5 to insert data from any\040
                        JSON-File in database\s6]Press any other key to exit""");
                choice = sc.nextInt();
                switch (choice) {
                    case 1 -> insert.insert(connection, sc, student, logger);
                    case 2 -> delete.delete(connection, student, logger, sc);
                    case 3 -> fetch.select(connection, logger, student, sc);
                    case 4 -> up.updateRecords(sc, logger, connection, student);
                    case 5 -> json.readData(sc, connection, logger, student);
                    default -> {
                        logger.info("\nDo you want to continue:= Press any key or else Press 0 to exit!!");
                        int n = sc.nextInt();
                        if (n == 0) {
                            logger.info("Process Successful");
                            return;
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | InputMismatchException e) {
            logger.error("Error at MainCrud", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    logger.info("Connection Closed!!");
                }
            } catch (SQLException e) {
                logger.error("Error at connection closing", e);
            }
        }
    }
}
