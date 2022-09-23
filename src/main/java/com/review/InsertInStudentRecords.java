package com.review;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class InsertInStudentRecords {

    public void insert(Connection connection, Scanner sc, StudentGetterSetter student, Logger logger) {
        try {
            //input student-id
            logger.info("Enter Student-Id:=");
            student.setStudentId(sc.nextInt());
            //input-studentName
            logger.info("Enter Student-Name:= (Example: Pratibha)");
            student.setStudentName(sc.next());
            if (!student.getStudentName().matches("[A-Za-z]*")) {
                logger.warn("Incorrect format");
            }
            //input student LastName
            logger.info("Enter Student-Last Name:- (Example: Soni");
            student.setStudentLastName(sc.next());
            if (!student.getStudentLastName().matches("[A-Za-z]*")) {
                logger.warn("Incorrect format");
            }
            //input student-FatherName
            logger.info("Enter Student's Father-Name:= (Example: Kishan)");
            student.setFatherName(sc.next());
            if (!student.getFatherName().matches("[A-Za-z]*")) {
                logger.warn("Incorrect format");
            }
            //input student motherName
            logger.info("Enter Student's Mother-Name:= (Example: Sharmila)");
            student.setMotherName(sc.next());
            if (!student.getMotherName().matches("[A-Za-z]*")) {
                logger.warn("Incorrect format");
            }
            //input student Address
            logger.info("Enter Student's Address:= (Example: Bikaner)");
            student.setAddress(sc.next());
            if (!student.getAddress().matches("[A-Za-z][A-Za-z0-9]*")) {
                logger.warn("Incorrect format");
            }
            //input student date of birth
            logger.info("Enter Student's DOB yyyy-mm-dd:= (Example: 2000-10-10 )");
            student.setDob(sc.next());
            if (!student.getDob().matches("[1-2][0-9][0-9][0-9][-][0-1][0-9][-][0-3][0-9]")) {
                logger.warn("Date has incorrect format");
            }
            //input english marks
            logger.info("Enter English-Marks out of 100:=");
            student.setEnglish(sc.nextFloat());
            if (student.getEnglish() > 100) {
                logger.warn("Marks entered must be more than 100");
            }
            //input hindi marks
            logger.info("Enter Hindi-Marks out of 100:=");
            student.setHindi(sc.nextFloat());
            if (student.getHindi() > 100) {
                logger.warn("Marks entered must be more than 100");
            }
            //input maths marks
            logger.info("Enter Maths-Marks out of 100:=");
            student.setMaths(sc.nextFloat());
            if (student.getMaths() > 100) {
                logger.warn("Marks entered must be more than 100");
            }
            //input science marks
            logger.info("Enter Science-Marks out of 100:=");
            student.setScience(sc.nextFloat());
            if (student.getScience() > 100) {
                logger.warn("Marks entered must be more than 100");
            }
            //input social marks
            logger.info("Enter Social-Marks out of 100:=");
            student.setSocial(sc.nextFloat());
            if (student.getSocial() > 100) {
                logger.warn("Marks entered must be more than 100");
            }
            //percentage
            float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
            student.setPercentage((total * 100) / 500);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        insertInStudent(connection, logger, student);
    }

    public void insertInStudent(Connection connection, Logger logger, StudentGetterSetter student) {

        String insertStudent = "insert into student (id, studentName,lastName) values(?, ?, ?)";
        String insertStudentMarks = "insert into studentMarks(studentId, english, hindi, maths, science, social, percentage ) values(?, ?, ?, ?, ?, ?, ?)";
        String insertStudentDetails = "insert into studentPersonalDetails(studentId, fatherName, motherName, address, dob) values(?, ?, ?, ?, ?)";
        int i =0;
        try {
            PreparedStatement statement = connection.prepareStatement(insertStudent);
            PreparedStatement statement1 = connection.prepareStatement(insertStudentDetails);
            PreparedStatement statement2 = connection.prepareStatement(insertStudentMarks);
            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getStudentName());
            statement.setString(3, student.getStudentLastName());
            statement1.setInt(1, student.getStudentId());
            statement1.setString(2, student.getFatherName());
            statement1.setString(3, student.getMotherName());
            statement1.setString(4, student.getAddress());
            statement1.setString(5, student.getDob());
            statement2.setInt(1, student.getStudentId());
            statement2.setFloat(2, student.getEnglish());
            statement2.setFloat(3, student.getHindi());
            statement2.setFloat(4, student.getMaths());
            statement2.setFloat(5, student.getScience());
            statement2.setFloat(6, student.getSocial());
            statement2.setFloat(7, student.getPercentage());

            statement.executeUpdate();
            statement1.executeUpdate();
            statement2.executeUpdate();
            i += 1;
            statement.close();
            statement1.close();
            statement2.close();

            if (i > 0) {
                logger.info("Data Inserted!!");
            } else {
                logger.warn("Data Not Inserted");
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

}
