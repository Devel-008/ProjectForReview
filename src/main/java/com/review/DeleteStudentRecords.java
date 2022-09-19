package com.review;

import org.slf4j.Logger;

import java.sql.*;
import java.util.Scanner;

public class DeleteStudentRecords {

    public void delete(Connection connection, StudentGetterSetter student, Logger logger, Scanner sc) {
        try {
            logger.info("Enter Roll-no of person you want to delete :=");
            student.setStudentId(sc.nextInt());

            String query = " select student.studentName, student.lastName, " +
                    " studentPersonalDetails.fatherName, studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +
                    " studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, studentMarks.percentage " +
                    " from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on studentPersonalDetails.studentId = student.id " +
                    " where student.id = ? ";

            PreparedStatement preStatement = connection.prepareStatement(query);
            preStatement.setInt(1, student.getStudentId());
            ResultSet rSet = preStatement.executeQuery();
            int count = 0;

            while (rSet.next()) {
                logger.info("Data you want to delete := ");

                logger.info("Name := " + rSet.getString("studentName") + " " + rSet.getString("lastName")
                        + " \nFather's Name := " + rSet.getString("FatherName") + ", Mother's Name := " + rSet.getString("motherName") + ", Address := " + rSet.getString("address") + ", Date of Birth := " + rSet.getString("dob") +
                        "\nEnglish Marks:= " + rSet.getFloat("english") + ", Hindi Marks:= " + rSet.getFloat("hindi") + ", Maths Marks:= " + rSet.getFloat("maths") + ", Science Marks:= " + rSet.getFloat("science") + ", Social Marks:= " + rSet.getFloat("social") + ", Percentage Marks:= " + rSet.getFloat("percentage"));
                count++;
            }
            if (count <= 0) {
                logger.warn("No Data with such ID");
            } else {
                logger.warn("Do you really want to delete ? ");
                logger.info("Then Press y to delete or press any other key to not to delete");
                String check = sc.next();

                if (check.equals("y")) {
                    deleteStudentRecord(connection, logger, student.getStudentId());
                } else {
                    logger.info("Data not Deleted");
                }
            }
            rSet.close();
            preStatement.close();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private void deleteStudentRecord(Connection connection, Logger logger, int id) {

        String deleteStudent = "delete from student where id = ?";
        String deleteStudentMarks = "delete from studentMarks where studentId = ?";
        String deleteStudentDetails = "delete from studentPersonalDetails where studentId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteStudent);
            statement.setInt(1, id);
            PreparedStatement statement1 = connection.prepareStatement(deleteStudentDetails);
            statement1.setInt(1, id);
            PreparedStatement statement2 = connection.prepareStatement(deleteStudentMarks);
            statement2.setInt(1, id);

            int i = statement.executeUpdate();
            int i1 = statement1.executeUpdate();
            int i2 = statement2.executeUpdate();
            statement.close();
            statement1.close();
            statement2.close();

            if (i > 0 || i1 > 0 || i2 > 2) {
                logger.info("Data Deleted !!");
            } else {
                logger.warn("Data Not Deleted");
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }
}
