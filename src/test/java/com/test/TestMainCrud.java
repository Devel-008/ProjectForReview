package com.test;

import com.review.*;
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
    InsertInStudentRecords insert = new InsertInStudentRecords();
    DeleteStudentRecords delete = new DeleteStudentRecords();
    UpdateStudentRecords update = new UpdateStudentRecords();
    FetchStudentRecords fetch = new FetchStudentRecords();

    @BeforeClass
    public void setUpH2Connection() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            logger.error("Error at setUpH2Connection := " + e);
        }
    }
    @Test
    public void testOnInsert() {
        logger.info("Test on Insert started!!");
        student.setStudentId(4);
        student.setStudentName("Noumi");
        student.setStudentLastName("Bhura");
        student.setFatherName("Mahesh");
        student.setMotherName("Durga");
        student.setAddress("Leh");
        student.setDob("1999-10-18");
        student.setEnglish(66);
        student.setHindi(77);
        student.setMaths(98);
        student.setScience(89);
        student.setSocial(89);
        float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
        student.setPercentage((total * 100) / 500);

        boolean in = insert.insertInStudent(connection, logger, student);
        System.out.println(in);
        boolean fe = fetch.selectRandom(connection, logger, student);
        System.out.println(fe);
            softAssert.assertEquals(fe, in);
            softAssert.assertAll();
    }

    @Test
    public void testOnUpdate() {
        logger.info("Test on Update started!!");
        student.setStudentId(2);
        student.setStudentName("Champa");
        student.setStudentLastName("Soni");
        student.setFatherName("Deepak");
        student.setMotherName("Jasmine");
        student.setAddress("Banglore");
        student.setDob("1999-09-02");
        student.setEnglish(89);
        student.setHindi(98);
        student.setMaths(79);
        student.setScience(87);
        student.setSocial(98);
        float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
        student.setPercentage((total * 100) / 500);

        boolean name = update.callUpdateAll(connection, logger, student);
        System.out.println(name);
        boolean select = fetch.selectRandom(connection,logger,student);
        System.out.println(select);
        softAssert.assertEquals(select, name, "Test on Update-Name failed");
        softAssert.assertAll();
    }
    @Test
    public void testOnDelete() {
        logger.info("Test on Delete started!!");
        student.setStudentId(4);
        boolean result = delete.deleteStudentRecord(connection, logger, student);
        softAssert.assertTrue(result, "Delete Test Failed");
        System.out.println(result);
        softAssert.assertAll();
    }
    @AfterClass
    public void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.error("Error at tearDown test := " + e);
            }
        }
    }
}
