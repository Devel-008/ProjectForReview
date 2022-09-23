package com.review;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateStudentRecords {
    String query;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet rs;
    String select = " select id from student";

    public void updateRecords(Scanner sc, Logger logger, Connection connection, StudentGetterSetter student) {
        int i;
        logger.info("Press 1 to Update Name || Press 2 to Personal details || Press 3 to Update Marks :=");
        int choice = sc.nextInt();
        if (choice == 1) {
            logger.info("Press 1 to update StudentName || Press 2 to update LastName ");
            i = sc.nextInt();
            logger.info("Enter the ID of student whose data you want Update :=");
            student.setStudentId(sc.nextInt());
            //For Name
            switch (i) {
                case 1 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Name:=");
                                student.setStudentName(sc.next());
                                if (!student.getStudentName().matches("[A-Za-z]*")) {
                                    logger.warn("Incorrect format");
                                }
                                updateName(connection, logger, student);
                                tearDown(connection, logger);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error on line 45 of Update :- " + e);
                    }
                }
                case 2 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Last-Name:=");
                                student.setStudentLastName(sc.next());
                                if (!student.getStudentLastName().matches("[A-Za-z]*")) {
                                    logger.warn("Incorrect format");
                                }
                                updateLName(connection, logger, student);
                                tearDown(connection, logger);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error on line 64 of update:- " + e);
                    }
                }
                default -> logger.info("Wrong Choice");
            }
        } else if (choice == 2) {
            logger.info("Press 1 to update FatherName || Press 2 to update MotherName || Press 3 to update Address || Press 4 to update DOB ");
            i = sc.nextInt();
            logger.info("Enter the ID of student whose data you want Update :=");
            student.setStudentId(sc.nextInt());
            switch (i) {
                case 1 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update FatherName:=");
                                student.setFatherName(sc.next());
                                if (!student.getFatherName().matches("[A-Za-z]*")) {
                                    logger.warn("Incorrect format");
                                }
                                updateFName(connection, logger, student);
                                tearDown(connection, logger);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error on line 91 in update :- " + e);
                    }
                }
                case 2 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Mother-Name:=");
                                student.setMotherName(sc.next());
                                if (!student.getMotherName().matches("[A-Za-z]*")) {
                                    logger.warn("Incorrect format");
                                }
                                updateMName(connection, logger, student);
                                tearDown(connection, logger);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error on line 110 in update:- " + e);
                    }
                }
                case 3 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Address:=");
                                student.setAddress(sc.next());
                                if (!student.getAddress().matches("[A-Za-z][A-Za-z0-9]*")) {
                                    logger.warn("Incorrect format");
                                }
                                updateAddress(connection, logger, student);
                                tearDown(connection, logger);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error on line 129 in update:- " + e);
                    }
                }
                case 4 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update DOB:=");
                                student.setDob(sc.next());
                                if (!student.getDob().matches("[1-2][0-9][0-9][0-9][-][0-1][0-9][-][0-3][0-9]")) {
                                    logger.warn("Incorrect format");
                                }
                                updateDob(connection, logger, student);
                                tearDown(connection, logger);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error on line 148 in update:- " + e);
                    }
                }
                default -> logger.info("Wrong Choice");
            }
        } else if (choice == 3) {
            logger.info("Press 1 to update English || Press 2 to update Hindi || Press 3 to update Maths || Press 4 to update Science || Press 5 to update Social:= ");
            i = sc.nextInt();
            logger.info("Enter the ID of student whose data you want Update :=");
            student.setStudentId(sc.nextInt());
            switch (i) {
                case 1 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update English Marks:=");
                                student.setEnglish(sc.nextFloat());
                                if (student.getEnglish() > 100) {
                                    logger.warn("Marks entered must be more than 100");
                                }
                                updateEnglish(connection, logger, student);
                            }
                        }
                        updatePercentage(connection, logger, student);
                        tearDown(connection, logger);
                    } catch (Exception e) {
                        logger.error("Error on line 176 in update:- " + e);
                    }
                }
                case 2 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Hindi Marks:=");
                                student.setHindi(sc.nextFloat());
                                if (student.getHindi() > 100) {
                                    logger.warn("Marks entered must be more than 100");
                                }
                                updateHindi(connection, logger, student);
                            }
                        }
                        updatePercentage(connection, logger, student);
                        tearDown(connection, logger);
                    } catch (Exception e) {
                        logger.error("Error on line 196 in update:- " + e);
                    }
                }
                case 3 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Maths Marks:=");
                                student.setMaths(sc.nextFloat());
                                if (student.getMaths() > 100) {
                                    logger.warn("Marks entered must be more than 100");
                                }
                                updateMaths(connection, logger, student);
                            }
                        }
                        updatePercentage(connection, logger, student);
                        tearDown(connection, logger);
                    } catch (Exception e) {
                        logger.error("Error on line 215 in update:- " + e);
                    }
                }
                case 4 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Science Marks:=");
                                student.setScience(sc.nextFloat());
                                if (student.getScience() > 100) {
                                    logger.warn("Marks entered must be more than 100");
                                }
                                updateScience(connection, logger, student);
                            }
                        }
                        updatePercentage(connection, logger, student);
                        tearDown(connection, logger);
                    } catch (Exception e) {
                        logger.error("Error on line 236 in update:- " + e);
                    }
                }
                case 5 -> {
                    try {
                        statement = connection.createStatement();
                        rs = statement.executeQuery(select);
                        while (rs.next()) {
                            if (student.getStudentId() == rs.getInt("id")) {
                                logger.info("Update Social Marks:=");
                                student.setSocial(sc.nextFloat());
                                if (student.getSocial() > 100) {
                                    logger.warn("Marks entered must be more than 100");
                                }
                                updateSocial(connection, logger, student);
                            }
                        }
                        updatePercentage(connection, logger, student);
                        tearDown(connection, logger);
                    } catch (Exception e) {
                        logger.error("Error on line 256 in update:- " + e);
                    }
                }
                default -> logger.info("Wrong Choice");
            }
        } else {
            logger.warn("Wrong Choice!!");
        }
    }

    public void updateName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update student set studentName = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 270 in update:- " + e);
        }
    }
    public void updateLName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update student set lastName = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getStudentLastName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 286 in update:- " + e);
        }
    }
    public void updateFName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set fatherName = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getFatherName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 302 in update:- " + e);
        }
    }
    public void updateMName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set motherName = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getMotherName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 327 in update:- " + e);
        }
    }
    public void updateAddress(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set address = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getAddress());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 334 in update:- " + e);
        }
    }
    public void updateDob(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set dob = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getDob());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 350 in update:- " + e);
        }

    }
    public void updateEnglish(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set english = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getEnglish());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 376 in update:- " + e);
        }
    }
    public void updateHindi(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set hindi = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getHindi());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 393 in update:- " + e);
        }
    }
    public void updateMaths(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set maths = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getMaths());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 408 in update:- " + e);
        }
    }
    public void updateScience(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set science = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getScience());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 424 in update:- " + e);
        }
    }
    public void updateSocial(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set social = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getSocial());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
            } else {
                logger.warn("Not updated");
            }
        } catch (Exception e) {
            logger.error("Error on line 440 in update:- " + e);
        }
    }
    public void updatePercentage(Connection connection, Logger logger, StudentGetterSetter student) {
        select = "select english,hindi, science, maths, social from studentMarks where studentId = " + student.getStudentId();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(select);
            rs.next();
            student.setEnglish(rs.getFloat("english"));
            student.setHindi(rs.getFloat("hindi"));
            student.setMaths(rs.getFloat("maths"));
            student.setScience(rs.getFloat("science"));
            student.setSocial(rs.getFloat("social"));
            float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
            student.setPercentage((total * 100) / 500);

            query = "update studentMarks set percentage = ? where studentId = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2, student.getStudentId());
            preparedStatement.setFloat(1, student.getPercentage());
            int x = preparedStatement.executeUpdate();
            if (x <= 0) {
                logger.info("Percentage not Updated!!!");
            } else {
                logger.info("Percentage Updated!!!");
            }
        } catch (Exception e) {
            logger.error("Error on line 468 in update:- " + e);
        }
    }
    public void tearDown(Connection connection, Logger logger) {
        try {
            if (connection != null || statement != null || preparedStatement != null || rs != null) {
                connection.close();
                statement.close();
                preparedStatement.close();
                rs.close();
            }
        } catch (Exception e) {
            logger.error("Error on line 480 in update:- " + e);
        }

    }


}
