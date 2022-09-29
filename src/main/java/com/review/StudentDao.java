package com.review;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentDao implements InterfaceMainCrud{
    ResultSet rs;
    int count = 0;
    String query;
    PreparedStatement preparedStatement;
    Statement statement;
    String select = " select id from student";
    @Override
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

    @Override
    public boolean selectRandom(Connection connection, Logger logger, StudentGetterSetter student) {

        String selectQuery = "select student.studentName, student.lastName, studentPersonalDetails.fatherName, studentPersonalDetails.motherName, " +
                "studentPersonalDetails.address,studentPersonalDetails.dob, studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, " +
                "studentMarks.social, studentMarks.percentage from student JOIN studentMarks on student.id = studentMarks.studentId JOIN studentPersonalDetails on " +
                "studentPersonalDetails.studentId = student.id where student.id = ?";
        try {
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                logger.info("Name := " + rs.getString("studentName") + " " + rs.getString("lastName") +
                        "\nFather's Name := " + rs.getString("FatherName") + ", Mother's Name := " + rs.getString
                        ("motherName") + ", Address := " + rs.getString("address") + ", Date of Birth := " +
                        rs.getString("dob") + "\nEnglish Marks:= " + rs.getFloat("english") + ", Hindi Marks:= "
                        + rs.getFloat("hindi") + ", Maths Marks:= " + rs.getFloat("maths") + ", Science Marks:= "
                        + rs.getFloat("science") + ", Social Marks:= " + rs.getFloat("social") + ", Percentage " +
                        "Marks:= " + rs.getFloat("percentage") + "\n");
                count++;
            }
            if (count <= 0) {
                logger.warn("No Data with such ID");
                return false;
            }else {
                return true;
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("Error at selectRandom := " , e);
        }finally {
            try {
                if(preparedStatement != null && rs != null) {
                    preparedStatement.close();
                    rs.close();
                }
            } catch (SQLException e) {
                logger.error("Error at selectRandom while closing prepareStatement : ", e);
            }
        }
        return false;
    }

    @Override
    public void selectAll(Connection connection, Logger logger) {
        try {
            statement = connection.createStatement();
            String query = " select student.id, student.studentName, student.lastName, " + " studentPersonalDetails.fatherName, " +
                    "studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +" studentMarks" +
                    ".english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, studentMarks.percentage "
                    +" from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on " +
                    "studentPersonalDetails.studentId = student.id ";
            rs = statement.executeQuery(query);

            while (rs.next()) {
                logger.info("Student-ID := " + rs.getInt("id") + ", Name := " + rs.getString("studentName") + " "
                        + rs.getString("lastName") + " \nFather's Name := " + rs.getString("FatherName") +
                        ", Mother's Name := " + rs.getString("motherName") + ", Address := " + rs.getString
                        ("address") + ", Date of Birth := " + rs.getString("dob") + "\nEnglish Marks:= " +
                        rs.getFloat("english") + ", Hindi Marks:= " + rs.getFloat("hindi") + ", Maths Marks:= " +
                        rs.getFloat("maths") + ", Science Marks:= " + rs.getFloat("science") + ", Social Marks:=" +
                        "" + rs.getFloat("social") + ", Percentage Marks:= " + rs.getFloat("percentage")+"\n");
                count++;
            }
            if (count <= 0) {
                logger.warn("No Data Found!!");
            }
        } catch (Exception e) {
            logger.error("Error at selectAll := " , e);
        }finally {
            try {
                if(statement != null && rs != null) {
                    statement.close();
                    rs.close();
                }
            } catch (SQLException | NullPointerException e ) {
                logger.error("Error at selectAll while closing statement and resultSet := " , e);
            }
        }
    }

    @Override
    public void readData(Scanner sc, Connection connect, Logger logger, StudentGetterSetter studentCheck) {

        String query = "insert into student (id, studentName,lastName) values(?, ?, ?)";
        String query1 = "insert into studentPersonalDetails(studentId, fatherName, motherName, address, dob) values(?, ?, ?, ?, ?)";
        String query2 = "insert into studentMarks(studentId, english, hindi, maths, science, social, percentage ) values(?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Enter the address of file:-");
        String filePath = sc.next();
        int count = 0;
        JSONParser parser = new JSONParser();


        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            } catch (IOException | ParseException e) {
                logger.error("Error at JsonInsert while fileReader or ");
            }
            JSONArray array = (JSONArray) jsonObject.get("students");

            for (Object object : array) {
                JSONObject record = (JSONObject) object;
                studentCheck.setStudentId(Integer.parseInt(record.get("id").toString()));
                studentCheck.setStudentName((String) record.get("studentName"));
                studentCheck.setStudentLastName((String) record.get("lastName"));
                studentCheck.setFatherName((String) record.get("fatherName"));
                studentCheck.setMotherName((String) record.get("motherName"));
                studentCheck.setAddress((String) record.get("address"));
                studentCheck.setDob((String) record.get("dob"));
                studentCheck.setEnglish(Float.parseFloat(record.get("english").toString()));
                studentCheck.setHindi(Float.parseFloat(record.get("hindi").toString()));
                studentCheck.setMaths(Float.parseFloat(record.get("maths").toString()));
                studentCheck.setScience(Float.parseFloat(record.get("science").toString()));
                studentCheck.setSocial(Float.parseFloat(record.get("social").toString()));
                float total = studentCheck.getEnglish() + studentCheck.getHindi() + studentCheck.getMaths() +
                        studentCheck.getScience() + studentCheck.getSocial();
                studentCheck.setPercentage((total * 100) / 500);
                preparedStatement = connect.prepareStatement(query);
                preparedStatement.setInt(1, studentCheck.getStudentId());
                preparedStatement.setString(2, studentCheck.getStudentName());
                preparedStatement.setString(3, studentCheck.getStudentLastName());
                count += 1;
                preparedStatement1 = connect.prepareStatement(query1);
                preparedStatement1.setInt(1, studentCheck.getStudentId());
                preparedStatement1.setString(2, studentCheck.getFatherName());
                preparedStatement1.setString(3, studentCheck.getMotherName());
                preparedStatement1.setString(4, studentCheck.getAddress());
                preparedStatement1.setString(5, studentCheck.getDob());
                count += 1;
                preparedStatement2 = connect.prepareStatement(query2);
                preparedStatement2.setInt(1, studentCheck.getStudentId());
                preparedStatement2.setFloat(2, studentCheck.getEnglish());
                preparedStatement2.setFloat(3, studentCheck.getHindi());
                preparedStatement2.setFloat(4, studentCheck.getMaths());
                preparedStatement2.setFloat(5, studentCheck.getScience());
                preparedStatement2.setFloat(6, studentCheck.getSocial());
                preparedStatement2.setFloat(7, studentCheck.getPercentage());
                count += 1;
                if (count >= 3) {
                    int i = preparedStatement.executeUpdate();
                    int i1 = preparedStatement1.executeUpdate();
                    int i2 = preparedStatement2.executeUpdate();
                    if (i > 0 || i1 > 0 || i2 > 0) {
                        logger.info("Data Saved!!");
                    } else {
                        logger.warn("Failed to save data");
                    }
                } else {
                    logger.warn("Failed to save data");
                }
            }
        } catch (SQLException e) {
            logger.error("Error at readData := " , e);
        } finally {
            if (preparedStatement != null && preparedStatement1 != null && preparedStatement2 != null){
                try {
                    preparedStatement.close();
                    preparedStatement1.close();
                    preparedStatement2.close();
                }catch (SQLException e){
                    logger.error("Error at closing prepareStatement in JsonInsert : =", e);
                }
            }
        }
    }

    @Override
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
        } catch (InputMismatchException | NullPointerException e) {
            logger.error("Error at insert := " , e);
        }
        insertInStudent(connection, logger, student);
    }


    @Override
    public boolean insertInStudent(Connection connection, Logger logger, StudentGetterSetter student) {
        String insertStudent = "insert into student (id, studentName,lastName) values(?, ?, ?)";
        String insertStudentMarks = "insert into studentMarks(studentId, english, hindi, maths, science, social, percentage ) values(?, ?, ?, ?, ?, ?, ?)";
        String insertStudentDetails = "insert into studentPersonalDetails(studentId, fatherName, motherName, address, dob) values(?, ?, ?, ?, ?)";
        int i = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            statement = connection.prepareStatement(insertStudent);
            statement1 = connection.prepareStatement(insertStudentDetails);
            statement2 = connection.prepareStatement(insertStudentMarks);
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

            if (i > 0) {
                logger.info("Data Inserted!!");
                return true;
            } else {
                logger.warn("Data Not Inserted");
                return false;
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("Error at insertInStudent := " , e);
        } finally {
            try {
                if(statement != null && statement1 != null && statement2 != null)
                    statement.close();
                statement1.close();
                statement2.close();
            } catch (SQLException e) {
                logger.error("Error at closing statement at insertInStudent", e);
            }
        }
        return false;
    }

    @Override
    public void delete(Connection connection, StudentGetterSetter student, Logger logger, Scanner sc) {

        try {
            logger.info("Enter Roll-no of person you want to delete :=");
            student.setStudentId(sc.nextInt());

            String query = " select student.studentName, student.lastName, " + " studentPersonalDetails.fatherName, " +
                    "studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +
                    "studentMarks.english,studentMarks.hindi,studentMarks.maths,studentMarks.science,studentMarks.social,studentMarks" +
                    ".percentage " + " from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on "
                    + "studentPersonalDetails.studentId = student.id " + " where student.id = ? ";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            int count = 0;

            while (rs.next()) {
                logger.info("Data you want to delete := ");

                logger.warn("Name := " + rs.getString("studentName") + " " + rs.getString("lastName")
                        + " \nFather's Name := " + rs.getString("FatherName") + ", Mother's Name := " + rs.getString
                        ("motherName") + ", Address := " + rs.getString("address") + ", Date of Birth := " + rs
                        .getString("dob") + "\nEnglish Marks:= " + rs.getFloat("english") + ", Hindi Marks:= " +
                        rs.getFloat("hindi") + ", Maths Marks:= " + rs.getFloat("maths") + ", Science Marks:= " +
                        rs.getFloat("science") + ", Social Marks:= " + rs.getFloat("social") + ", Percentage  " +
                        "Marks:= " + rs.getFloat("percentage"));
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
                if(rs != null && preparedStatement != null) {
                    rs.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("Error at delete while closing rs and prepareStatement ", e);
            }
        }
    }

    @Override
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


    @Override
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
                            logger.error("Error on line 473 of studentDao :- " , e);
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
                            logger.error("Error on line 491 of studentDao:- " , e);
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
                            logger.error("Error on line 517 in studentDao :- " , e);
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
                            logger.error("Error on line 535 in studentDao:- " , e);
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
                            logger.error("Error on line 553 in studentDao:- " , e);
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
                            logger.error("Error on line 571 in studentDao:- " , e);
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
                            logger.error("Error on line 598 in studentDao:- " , e);
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
                            logger.error("Error on line 617 in studentDao:- " , e);
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
                            logger.error("Error on line 636 in studentDao:- " , e);
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
                            logger.error("Error on line 655 in studentDao:- " , e);
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
                            logger.error("Error on line 674 in studentDao:- " , e);
                        }
                    }
                    default -> logger.info("Wrong Choice");
                }
            } else {
                logger.warn("Wrong Choice!!");
            }
        } catch (InputMismatchException | NullPointerException e) {
            logger.error("Error at studentDao line 683", e);
        } finally {
            tearDown(connection, logger);
        }
    }

    @Override
    public boolean updateName(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateLName(Connection connection, Logger logger, StudentGetterSetter student) {
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
            logger.error("Error at updateLName := " , e);
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

    @Override
    public boolean updateFName(Connection connection, Logger logger, StudentGetterSetter student) {
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
            logger.error("Error at updateFName := " , e);
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

    @Override
    public boolean updateMName(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateAddress(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateDob(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateEnglish(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateHindi(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateMaths(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateScience(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updateSocial(Connection connection, Logger logger, StudentGetterSetter student) {
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

    @Override
    public boolean updatePercentage(Connection connection, Logger logger, StudentGetterSetter student) {
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
    @Override
    public boolean callUpdateAll(Connection connection, Logger logger, StudentGetterSetter student) {
        if (updateName(connection, logger, student) && updateLName(connection, logger, student) &&
                updateFName(connection, logger, student) && updateMName(connection, logger, student) &&
                updateAddress(connection, logger, student) && updateDob(connection, logger, student) &&
                updateEnglish(connection, logger, student) && updateHindi(connection, logger, student)
                && updateMaths(connection, logger, student) && updateScience(connection, logger, student) &&
                updateSocial(connection, logger, student) && updatePercentage(connection, logger, student)) {
            logger.info("Updated All!!");
            return true;
        } else {
            logger.warn("Not Updated!!");
            return false;
        }
    }
    public void tearDown(Connection connection, Logger logger) {
        try {
            if ( statement != null && rs != null) {
                statement.close();
                rs.close();
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("Error at tearDown := ", e);
        }
    }
}
