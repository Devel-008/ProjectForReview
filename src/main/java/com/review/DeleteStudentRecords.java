package com.review;

import org.slf4j.Logger;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DeleteStudentRecords {

    public void delete(Connection connection, StudentGetterSetter student, Logger logger, Scanner sc) {
        ResultSet resultSet = null;
        PreparedStatement preStatement = null;
        try {
            logger.info("Enter Roll-no of person you want to delete :=");
            student.setStudentId(sc.nextInt());

            String query = " select student.studentName, student.lastName, " + " studentPersonalDetails.fatherName, " +
                    "studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +
                    "studentMarks.english,studentMarks.hindi,studentMarks.maths,studentMarks.science,studentMarks.social,studentMarks" +
                    ".percentage " + " from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on "
                    + "studentPersonalDetails.studentId = student.id " + " where student.id = ? ";

            preStatement = connection.prepareStatement(query);
            preStatement.setInt(1, student.getStudentId());
            resultSet = preStatement.executeQuery();
            int count = 0;

            while (resultSet.next()) {
                logger.info("Data you want to delete := ");

                logger.warn("Name := " + resultSet.getString("studentName") + " " + resultSet.getString("lastName")
                        + " \nFather's Name := " + resultSet.getString("FatherName") + ", Mother's Name := " + resultSet.getString
                        ("motherName") + ", Address := " + resultSet.getString("address") + ", Date of Birth := " + resultSet
                        .getString("dob") + "\nEnglish Marks:= " + resultSet.getFloat("english") + ", Hindi Marks:= " +
                        resultSet.getFloat("hindi") + ", Maths Marks:= " + resultSet.getFloat("maths") + ", Science Marks:= " +
                        resultSet.getFloat("science") + ", Social Marks:= " + resultSet.getFloat("social") + ", Percentage  " +
                        "Marks:= " + resultSet.getFloat("percentage"));
                count++;
            }
            if (count <= 0) {
                logger.warn("No Data with such ID");
            } else {
                logger.warn("Do you really want to delete ? ");
                logger.info("Then Press y to delete or press any other key to not to delete");
                String check = sc.next();

                if (check.equals("y")) {
                    deleteStudentRecord(connection, logger, student);
                } else {
                    logger.warn("Data not Deleted");
                }
            }

        } catch (SQLException | InputMismatchException | IllegalArgumentException e) {
            logger.error("Error at delete :=" , e);
        } finally {
            try {
                if(resultSet != null && preStatement != null) {
                    resultSet.close();
                    preStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error at delete while closing resultSet and prepareStatement ", e);
            }
        }
    }

    public boolean deleteStudentRecord(Connection connection, Logger logger, StudentGetterSetter student) {
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            String deleteStudent = "delete from student where id = ?";
            String deleteStudentMarks = "delete from studentMarks where studentId = ?";
            String deleteStudentDetails = "delete from studentPersonalDetails where studentId = ?";
            statement = connection.prepareStatement(deleteStudent);
            statement.setInt(1, student.getStudentId());
            statement1 = connection.prepareStatement(deleteStudentDetails);
            statement1.setInt(1, student.getStudentId());
            statement2 = connection.prepareStatement(deleteStudentMarks);
            statement2.setInt(1, student.getStudentId());
            int i = statement.executeUpdate();
            int i1 = statement1.executeUpdate();
            int i2 = statement2.executeUpdate();

            if (i > 0 && i1 > 0 && i2 > 0) {
                logger.info("Data Deleted !!");
                return true;
            } else {
                return false;
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("Error in deleteStudentRecord :=" , e);
        } finally {
            try {
                if(statement != null && statement1 != null && statement2 != null) {
                    statement.close();
                    statement1.close();
                    statement2.close();
                }
            }catch (SQLException e){
                logger.error("Error at closing prepareStatement:, e");
            }
        }
        return false;
    }
}
