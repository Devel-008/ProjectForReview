package com.review;

import org.slf4j.Logger;

import java.sql.*;
import java.util.Scanner;

public class DeleteStudentRecords {

    public void delete(Connection connection, StudentGetterSetter student, Logger logger, Scanner sc) throws Exception {

        logger.info("Enter Roll-no of person you want to delete :=");
        int id = sc.nextInt();
        String query = "select * from student where id = ?";
        String query1 = "select * from studentPersonalDetails where id = ?";
        String query2 = "select * from student where id = ?";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.valueOf(statement));
        while(rs.next()){
            student.setStudentId(rs.getInt(1));
            System.out.println(student.getStudentId());
        }
        //deleteStudentRecord(connection,student,logger,id);

    }
    private void deleteStudentRecord(Connection connection, StudentGetterSetter student, Logger logger, int id){

        String deleteStudent = "delete from student where rollno = ?";
        String deleteStudentMarks = "delete from studentMarks where rollno = ?";
        String deleteStudentDetails = "delete from studentPersonalDetails where rollno = ?";
        try{
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
                logger.info("Data Inserted!!");
            } else {
                logger.warn("Data Not Inserted");
            }
        }catch (Exception e){
            logger.error(String.valueOf(e));
        }
    }
}
