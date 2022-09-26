package com.review;

import org.slf4j.Logger;

import java.sql.*;
import java.util.Scanner;


public class FetchStudentRecords {
    ResultSet rSet;
    int count = 0;

    public void select(Connection connection, Logger logger, StudentGetterSetter student, Scanner sc) {
        logger.info("Press 1 to see all data || Press 2 to see random data := ");
        int check = sc.nextInt();

        if (check == 1) {
            selectAll(connection, logger);
        } else if (check == 2) {
            logger.info("Enter ID of person you want to select :=");
            student.setStudentId(sc.nextInt());
            selectRandom(connection, logger, student);
        } else {
            logger.warn("Wrong Choice");
        }
    }

    private void selectRandom(Connection connection, Logger logger, StudentGetterSetter student) {
        String query = " select student.studentName, student.lastName, " +
                " studentPersonalDetails.fatherName, studentPersonalDetails.motherName, studentPersonalDetails.address, "
                + "studentPersonalDetails.dob, " + " studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, " +
                "studentMarks.social, studentMarks.percentage " + " from student JOIN studentMarks on student.id = studentMarks.studentId " +
                "JOIN studentPersonalDetails on studentPersonalDetails.studentId = student.id " + " where student.id = ? ";
        PreparedStatement preStatement;
        try {
            preStatement = connection.prepareStatement(query);
            preStatement.setInt(1, student.getStudentId());
            rSet = preStatement.executeQuery();
            while (rSet.next()) {
                logger.info("Name := " + rSet.getString("studentName") + " " + rSet.getString("lastName") + " " +
                        "\nFather's Name := " + rSet.getString("FatherName") + ", Mother's Name := " + rSet.getString
                        ("motherName") + ", Address := " + rSet.getString("address") + ", Date of Birth := " +
                        rSet.getString("dob") + "\nEnglish Marks:= " + rSet.getFloat("english") + ", Hindi Marks:= "
                        + rSet.getFloat("hindi") + ", Maths Marks:= " + rSet.getFloat("maths") + ", Science Marks:= "
                        + rSet.getFloat("science") + ", Social Marks:= " + rSet.getFloat("social") + ", Percentage " +
                        "Marks:= " + rSet.getFloat("percentage") + "\n");
                count++;
            }
            if (count <= 0) {
                logger.warn("No Data with such ID");
            }
            preStatement.close();
        } catch (Exception e) {
            logger.error("Error at selectRandom := " + e);
        }
    }

    private void selectAll(Connection connection, Logger logger) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String query = " select student.id, student.studentName, student.lastName, " + " studentPersonalDetails.fatherName, " +
                    "studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +" studentMarks" +
                    ".english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, studentMarks.percentage "
                    + " from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on " +
                    "studentPersonalDetails.studentId = student.id ";
            rSet = stmt.executeQuery(query);
            while (rSet.next()) {
                logger.info("Student-ID := " + rSet.getInt("id") + ", Name := " + rSet.getString("studentName") + " "
                        + rSet.getString("lastName") + " \nFather's Name := " + rSet.getString("FatherName") + ", " +
                        "Mother's Name := " + rSet.getString("motherName") + ", Address := " + rSet.getString
                        ("address") + ", Date of Birth := " + rSet.getString("dob") + "\nEnglish Marks:= " +
                        rSet.getFloat("english") + ", Hindi Marks:= " + rSet.getFloat("hindi") + ", Maths Marks:= " +
                        rSet.getFloat("maths") + ", Science Marks:= " + rSet.getFloat("science") + ", Social Marks:=" +
                        "" + rSet.getFloat("social") + ", Percentage Marks:= " + rSet.getFloat("percentage"));
                count++;
            }
            if (count <= 0) {
                logger.warn("No Data Found!!");
            }

        } catch (Exception e) {
            logger.error("Error at selectAll := " + e);
        }finally {
            try {
                stmt.close();
                rSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
