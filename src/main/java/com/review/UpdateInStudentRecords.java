package com.review;

import org.slf4j.Logger;

import java.sql.*;
import java.util.Scanner;


public class UpdateInStudentRecords {
    PreparedStatement pStatement;
    ResultSet rs;
    Statement stmt;
    ResultSetMetaData rsm = null;
    int row;

    public void updateRecords(Connection connection, Logger logger, StudentGetterSetter student, Scanner sc){
        logger.info("Press 1 to update Student's Name || Press 2 to update Student's Personal Data || Press 3 to update Student's Marks ");
        int choice = sc.nextInt();
        logger.info("Enter the ID of student whose name you want Update :=");
        student.setStudentId(sc.nextInt());
        if(choice == 1){
            updateStudentName(connection,logger,student,sc);
        }else if (choice == 2){
            updateStudentDetails(connection,logger,student,sc);
        }else if (choice == 3){
            updateStudentMarks(connection,logger,student,sc);
        }
    }

    private void updateStudentName(Connection connection, Logger logger, StudentGetterSetter student, Scanner sc) {


        String select = " select id from student";
        try {
            pStatement = connection.prepareStatement(select);
            rs = pStatement.executeQuery();

            while(rs.next()) {
                if (student.getStudentId() == rs.getInt("id")) {
                    String update = "update student set studentName = ?, lastName = ? where id = ?";
                    logger.info("Update Name:-");
                    student.setStudentName(sc.next());
                    logger.info("Update LastName :=");
                    student.setStudentLastName(sc.next());
                    try {
                        pStatement = connection.prepareStatement(update);
                        pStatement.setString(1, student.getStudentName());
                        pStatement.setString(2, student.getStudentLastName());
                        pStatement.setInt(3, student.getStudentId());
                        row = pStatement.executeUpdate();
                        if (row > 0) {
                            logger.info("Record Updated");
                        } else {
                            logger.warn("Record not Updated");
                        }
                    } catch (Exception e) {
                        logger.error(String.valueOf(e));
                    }
                } else {
                    logger.warn("No such data with this ID!!");
                }
            }
        }catch (Exception e){
            logger.error(String.valueOf(e));
        }
        }
        private void updateStudentDetails(Connection connection, Logger logger, StudentGetterSetter student, Scanner sc) {
            try {
                String select = "select * from studentPersonalDetails";
                Statement stmt = connection.createStatement();
                rs = stmt.executeQuery(select);
                rs.next();
                rsm = rs.getMetaData();

                System.out.println("Press 2 to update FatherName || Press 3 to update MotherName || Press 4 to update Address || Press 5 to update DOB ");
                int i = sc.nextInt();

                String column = rsm.getColumnName(i);
                String query = "update studentPersonalDetails set " + column + " = (?) where studentId = (?)";
                if (i == 2 || i == 3 || i == 4 || i == 5) {
                    if (i == 2) {
                        String choice = "Father-Name";
                        System.out.print("Update " + choice + ":=");
                    } else if (i == 3) {
                        String choice = "Mother-Name";
                        System.out.print("Update " + choice + ":=");
                    } else if (i == 4) {
                        String choice = "Address";
                        System.out.print("Update " + choice + ":=");
                    } else if (i == 5) {
                        String choice = "DOB";
                        System.out.print("Update " + choice + ":=");
                    }
                    String name = sc.next();
                    pStatement = connection.prepareStatement(query);
                    pStatement.setString(1, name);
                    pStatement.setInt(2, student.getStudentId());
                    int row = pStatement.executeUpdate();
                    if (row > 0) {
                        System.out.println("Updated");
                    } else {
                        System.out.println("Data Not Found");
                    }
                }
            }catch (Exception e){
                logger.error(String.valueOf(e));
            }
        }
        private void updateStudentMarks(Connection connection, Logger logger, StudentGetterSetter student, Scanner sc){

            String select = "select * from studentMarks";
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(select);
                rs.next();
                rsm = rs.getMetaData();

                System.out.println("Press 2 to update English || Press 3 to update Hindi || Press 4 to update Maths || Press 5 to update Science || Press 6 to update Social := ");
                int i = sc.nextInt();

                String column = rsm.getColumnName(i);
                String query = "update studentMarks set " + column + " = (?) where studentId = (?)";
                if (i == 2) {
                    String choice = "English";
                    System.out.println("Update " + choice + " marks:=");
                } else if (i == 3) {
                    String choice = "Hindi";
                    System.out.println("Update " + choice + " marks:=");
                } else if (i == 4) {
                    String choice = "Maths";
                    System.out.println("Update " + choice + " marks:=");
                } else if (i == 5) {
                    String choice = "Science";
                    System.out.println("Update " + choice + " marks:=");
                } else if (i == 6) {
                    String choice = "Social";
                    System.out.println("Update " + choice + " marks:=");
                }
                float name = sc.nextFloat();

                pStatement = connection.prepareStatement(query);
                pStatement.setFloat(1, name);
                pStatement.setInt(2, student.getStudentId());
                int row = pStatement.executeUpdate();
                if (row > 0) {
                    logger.info("Record Updated ");
                } else {
                    System.out.println("Data Not Updated");
                }
            }catch (Exception e){
                logger.error(String.valueOf(e));
            }
        }
}
