package com.test;

import com.review.StudentGetterSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.*;


public class TestMainCrud {

    StudentGetterSetter student = new StudentGetterSetter();
    Connection connection;
    Statement statement;
    Logger logger = LoggerFactory.getLogger(TestMainCrud.class);
    ResultSet rs;
    PreparedStatement preparedStatement;
    SoftAssert softAssert = new SoftAssert();
    String query;

    @BeforeClass
    public void setUpH2Connection() {
        try {
            Class.forName("org.h2.Driver");
          connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }
    @Test(priority = 1)
   public void testByID(){
        student.setStudentId(3);

        query = "select student.id, studentPersonalDetails.studentId, studentMarks.studentId from student join studentPersonalDetails on student.id = studentPersonalDetails.studentId join studentMarks on student.id = studentMarks.studentId where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            int id1 = rs.getInt("studentId");
            int id2 = rs.getInt("studentId");
            softAssert.assertEquals(id,student.getStudentId());
            softAssert.assertEquals(id1,student.getStudentId());
            softAssert.assertEquals(id2,student.getStudentId());
            softAssert.assertAll();
            logger.info("Successful on ID");
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }
    @Test(dependsOnMethods = {"testByID"})
    public void testByName(){

        student.setStudentId(1);
        student.setStudentName("Krishna");
        student.setStudentLastName("Somani");
     query = "select studentName, lastName from student where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            rs.next();
            String name = rs.getString("studentName");
            String lName = rs.getString("lastName");
            softAssert.assertEquals(name,student.getStudentName());
            softAssert.assertEquals(lName,student.getStudentLastName());
            softAssert.assertAll();
            logger.info("Successful on Name");
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }
    @Test(dependsOnMethods = {"testByName"})
    public void testByDetails(){

        student.setStudentId(1);
        student.setStudentName("");
        student.setFatherName("");
        student.setMotherName("");
        student.setAddress("");
        student.setDob("");

        query = "select studentPersonalDetails.fatherName, studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, student.studentName from student JOIN studentPersonalDetails on student.id = studentPersonalDetails.studentId where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,student.getStudentId());
            rs = preparedStatement.executeQuery();
            rs.next();
            String name = rs.getString("studentName");
            String fName = rs.getString("fatherName");
            String mName = rs.getString("motherName");
            String address = rs.getString("address");
            String dob = rs.getString("dob");
            softAssert.assertEquals(name,student.getStudentName());
            softAssert.assertEquals(fName,student.getFatherName());
            softAssert.assertEquals(mName,student.getMotherName());
            softAssert.assertEquals(address,student.getAddress());
            softAssert.assertEquals(dob,student.getDob());
            softAssert.assertAll();
            logger.info("Successful on Details");
        }catch(Exception e){
            logger.error(String.valueOf(e));
        }
    }
    @Test(dependsOnMethods = {"testByID"})
    public void testByMarks(){
        student.setStudentId(1);
        student.setStudentName("Krishna");
        student.setEnglish(99);
        student.setHindi(99);
        student.setMaths(99);
        student.setScience(99);
        student.setSocial(99);

        query = "select studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, student.id from student JOIN studentMarks on student.id = studentMarks.studentId where student.studentName = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student.getStudentName());
            rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            float english = rs.getFloat("english");
            float hindi = rs.getFloat("hindi") ;
            float maths = rs.getFloat("maths") ;
            float science = rs.getFloat("science") ;
            float social = rs.getFloat("social") ;
            softAssert.assertEquals(id, student.getStudentId());
            softAssert.assertEquals(english,student.getEnglish());
            softAssert.assertEquals(hindi,student.getHindi());
            softAssert.assertEquals(maths, student.getMaths());
            softAssert.assertEquals(science,student.getScience());
            softAssert.assertEquals(social,student.getSocial());
            logger.info("Successful on marks");
        }catch(Exception e){
            logger.error(String.valueOf(e));
        }
    }
    @Test
    public void testByPercentage(){
        student.setStudentId(1);
        student.setStudentName("prerna");
        student.setPercentage(90);
        query = " select student.studentName, studentPersonalDetails.studentId, studentMarks.percentage from studentPersonalDetails join studentMarks on studentPersonalDetails.studentId = studentMarks.studentId join student on studentPersonalDetails.studentId = student.id  where student.studentName = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student.getStudentName());
            rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("studentId") ;
            float percentage = rs.getFloat("percentage");
            softAssert.assertEquals(id, student.getStudentId());
            softAssert.assertEquals(percentage,student.getPercentage());
            softAssert.assertAll();
            logger.info("Successful with percentage");
        }catch (Exception e){
            logger.error(String.valueOf(e));
        }
        }
        @Test
        public void testOnInsert(){

        }
    @AfterClass
    public void tearDown(){
        if(connection != null){
            try {
                connection.close();
                preparedStatement.close();
                rs.close();
            }catch (Exception e){
                logger.error(String.valueOf(e));
            }
        }
    }





}
