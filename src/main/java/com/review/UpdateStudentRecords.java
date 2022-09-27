package com.review;

import org.slf4j.Logger;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UpdateStudentRecords {
    String query;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet rs;
    String select = " select id from student";

    public void updateRecords(Scanner sc, Logger logger, Connection connection, StudentGetterSetter student) {
        try {
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
                                }
                            }
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 43 of Update :- " + e);
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
                                }
                            }
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 61 of update:- " + e);
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
                                }
                            }
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
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
                                }
                            }
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
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
                                }
                            }
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
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
                                }
                            }
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
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
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 168 in update:- " + e);
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
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 187 in update:- " + e);
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
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 206 in update:- " + e);
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
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 225 in update:- " + e);
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
                        } catch (SQLException | InputMismatchException | NullPointerException e) {
                            logger.error("Error on line 244 in update:- " + e);
                        }
                    }
                    default -> logger.info("Wrong Choice");
                }
            } else {
                logger.warn("Wrong Choice!!");
            }
        } catch (InputMismatchException | NullPointerException e) {
            logger.error("Error at selectRecord line 253", e);
        } finally {
            tearDown(connection, logger);
        }
    }

    private boolean updateName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update student set studentName = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateName := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateName: ", e);
            }
        }
        return false;
    }

    private boolean updateLName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update student set lastName = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getStudentLastName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateLName := " + e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateLName: ", e);
            }
        }
        return false;
    }

    private boolean updateFName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set fatherName = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getFatherName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateFName := " + e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateFName: ", e);
            }
        }
        return false;
    }

    private boolean updateMName(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set motherName = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getMotherName());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateMName := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateMName: ", e);
            }
        }
        return false;
    }

    private boolean updateAddress(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set address = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getAddress());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateAddress := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateAddress: ", e);
            }
        }
        return false;
    }

    private boolean updateDob(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentPersonalDetails set dob = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getDob());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateDob := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateDOB: ", e);
            }
        }
        return false;
    }

    private boolean updateEnglish(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set english = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getEnglish());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateEnglish := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateEnglish: ", e);
            }
        }
        return false;
    }

    private boolean updateHindi(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set hindi = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getHindi());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateHindi := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateHindi: ", e);
            }
        }
        return false;
    }

    private boolean updateMaths(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set maths = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getMaths());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateMaths := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateMaths: ", e);
            }
        }
        return false;
    }

    private boolean updateScience(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set science = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getScience());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateScience := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at UpdateScience: ", e);
            }
        }
        return false;
    }

    private boolean updateSocial(Connection connection, Logger logger, StudentGetterSetter student) {
        query = "update studentMarks set social = ? where studentId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, student.getSocial());
            preparedStatement.setInt(2, student.getStudentId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                logger.info("Updated");
                return true;
            } else {
                logger.warn("Not updated");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error at updateSocial := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing preapreStatement at UpdateSocial: ", e);
            }
        }
        return false;
    }

    private boolean updatePercentage(Connection connection, Logger logger, StudentGetterSetter student) {
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
                logger.warn("Percentage not Updated!!!");
                return false;
            } else {
                logger.info("Percentage Updated!!!");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error at updatePercentage := ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepareStatement at updatePercentage: ", e);
            }
        }
        return false;
    }

    public boolean callUpdateAll(Connection connection, Logger logger, StudentGetterSetter student) {
        if (updateName(connection, logger, student) && updateLName(connection, logger, student) && updateFName(connection, logger, student) &&
                updateMName(connection, logger, student) && updateAddress(connection, logger, student) && updateDob(connection, logger, student)
                && updateEnglish(connection, logger, student) && updateHindi(connection, logger, student) && updateMaths(connection, logger, student)
                && updateScience(connection, logger, student) && updateSocial(connection, logger, student) && updatePercentage(connection, logger, student)) {
            logger.info("Updated All!!");
            return true;
        } else {
            logger.warn("Not Updated!!");
            return false;
        }
    }

    public void tearDown(Connection connection, Logger logger) {
        try {
            if (connection != null && statement != null && rs != null) {
                connection.close();
                statement.close();
                rs.close();
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("Error at tearDown := ", e);
        }

    }


}
