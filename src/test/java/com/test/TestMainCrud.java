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
    StudentDao dao = new StudentDao();

    @BeforeClass
    public void setUpH2Connection() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            logger.error("Error at setUpH2Connection := " , e);
        }
    }
    @Test(priority = 1)
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

        boolean in = dao.insertInStudent(connection, logger, student);
        logger.info(String.valueOf(in));
        boolean fe = dao.selectRandom(connection, logger, student);
        logger.info(String.valueOf(fe));
            softAssert.assertEquals(fe, in,"Test failed on Insert");
            softAssert.assertAll();
    }

    @Test(priority = 2)
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

        boolean name = dao.callUpdateAll(connection, logger, student);
        logger.info(String.valueOf(name));
        boolean select = dao.selectRandom(connection,logger,student);
        logger.info(String.valueOf(select));
        softAssert.assertEquals(select, name, "Test on Update-Name failed");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testOnDelete() {
        logger.info("Test on Delete started!!");
        student.setStudentId(4);
        boolean result = dao.deleteStudentRecord(connection, logger, student);
        softAssert.assertTrue(result, "Delete Test Failed");
        logger.info(String.valueOf(result));
        softAssert.assertAll();
    }
    @AfterClass
    public void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.error("Error at tearDown test := " , e);
            }
        }
    }
}
