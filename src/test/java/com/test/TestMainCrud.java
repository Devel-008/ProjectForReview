package com.test;

import com.review.DeleteStudentRecords;
import com.review.InsertInStudentRecords;
import com.review.StudentGetterSetter;
import com.review.UpdateStudentRecords;
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
    public void testByID() {
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
            softAssert.assertEquals(id, student.getStudentId());
            softAssert.assertEquals(id1, student.getStudentId());
            softAssert.assertEquals(id2, student.getStudentId());
            softAssert.assertAll();
            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            logger.error("Error at testOnId := " + e);
        }
    }

    @Test
    public void testByName() {
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
            softAssert.assertEquals(name, student.getStudentName());
            softAssert.assertEquals(lName, student.getStudentLastName());
            softAssert.assertAll();
            preparedStatement.close();
            logger.info("Successful on Name");
        } catch (Exception e) {
            logger.error("Error at testOnNames := " + e);
        }
    }

    @Test
    public void testByDetails() {
        student.setStudentId(1);
        student.setStudentName("");
        student.setFatherName("");
        student.setMotherName("");
        student.setAddress("");
        student.setDob("");

        query = "select studentPersonalDetails.fatherName, studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, student.studentName from student JOIN studentPersonalDetails on student.id = studentPersonalDetails.studentId where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            rs.next();
            String name = rs.getString("studentName");
            String fName = rs.getString("fatherName");
            String mName = rs.getString("motherName");
            String address = rs.getString("address");
            String dob = rs.getString("dob");
            softAssert.assertEquals(name, student.getStudentName());
            softAssert.assertEquals(fName, student.getFatherName());
            softAssert.assertEquals(mName, student.getMotherName());
            softAssert.assertEquals(address, student.getAddress());
            softAssert.assertEquals(dob, student.getDob());
            softAssert.assertAll();
            preparedStatement.close();
            rs.close();
            logger.info("Successful on Details");
        } catch (Exception e) {
            logger.error("Error at testOnDetails := " + e);
        }
    }

    @Test
    public void testByMarks() {
        student.setStudentId(1);
        student.setStudentName("Krishna");
        student.setEnglish(99);
        student.setHindi(99);
        student.setMaths(99);
        student.setScience(99);
        student.setSocial(99);
        query = "select studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, student.id from student JOIN studentMarks on student.id = studentMarks.studentId where student.studentName = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getStudentName());
            rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            float english = rs.getFloat("english");
            float hindi = rs.getFloat("hindi");
            float maths = rs.getFloat("maths");
            float science = rs.getFloat("science");
            float social = rs.getFloat("social");
            softAssert.assertEquals(id, student.getStudentId());
            softAssert.assertEquals(english, student.getEnglish());
            softAssert.assertEquals(hindi, student.getHindi());
            softAssert.assertEquals(maths, student.getMaths());
            softAssert.assertEquals(science, student.getScience());
            softAssert.assertEquals(social, student.getSocial());
            preparedStatement.close();
            rs.close();
            softAssert.assertAll();
            logger.info("Successful on marks");
        } catch (Exception e) {
            logger.error("Error at testOnMarks := " + e);
        }
    }

    @Test
    public void testByPercentage() {
        student.setStudentId(1);
        student.setStudentName("prerna");
        student.setPercentage(90);
        query = " select student.studentName, studentPersonalDetails.studentId, studentMarks.percentage from studentPersonalDetails join studentMarks on studentPersonalDetails.studentId = studentMarks.studentId join student on studentPersonalDetails.studentId = student.id  where student.studentName = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getStudentName());
            rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("studentId");
            float percentage = rs.getFloat("percentage");
            softAssert.assertEquals(id, student.getStudentId());
            softAssert.assertEquals(percentage, student.getPercentage());
            softAssert.assertAll();
            preparedStatement.close();
            rs.close();
            logger.info("Successful with percentage");
        } catch (Exception e) {
            logger.error("Error at testOnPercentage := " + e);
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

        insert.insertInStudent(connection, logger, student);
        query = " select student.id, student.studentName, student.lastName, " +
                " studentPersonalDetails.fatherName, studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +
                " studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, studentMarks.percentage " +
                " from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on studentPersonalDetails.studentId = student.id " +
                " where student.id = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            String name = rs.getString("studentName");
            String lName = rs.getString("lastName");
            String fName = rs.getString("fatherName");
            String mName = rs.getString("motherName");
            String address = rs.getString("address");
            String dob = rs.getString("dob");
            float english = rs.getFloat("english");
            float hindi = rs.getFloat("hindi");
            float maths = rs.getFloat("maths");
            float science = rs.getFloat("science");
            float social = rs.getFloat("social");
            float percent = rs.getFloat("percentage");

            softAssert.assertEquals(id, student.getStudentId(), "Test on Insert-Id failed");
            softAssert.assertEquals(name, student.getStudentName(), "Test on Insert-Name failed");
            softAssert.assertEquals(lName, student.getStudentLastName(), "Test on Insert-LastName failed");
            softAssert.assertEquals(fName, student.getFatherName(), "Test on Insert-FatherName failed");
            softAssert.assertEquals(mName, student.getMotherName(), "Test on Insert-MotherName failed");
            softAssert.assertEquals(address, student.getAddress(), "Test on Insert-Address failed");
            softAssert.assertEquals(dob, student.getDob(), "Test on Insert-DOB failed");
            softAssert.assertEquals(english, student.getEnglish(), "Test on Insert-English-Marks failed");
            softAssert.assertEquals(hindi, student.getHindi(), "Test on Insert-Hindi-Marks failed");
            softAssert.assertEquals(maths, student.getMaths(), "Test on Insert-Maths-Marks failed");
            softAssert.assertEquals(science, student.getScience(), "Test on Insert-Science-Marks failed");
            softAssert.assertEquals(social, student.getSocial(), "Test on Insert-Social-Marks failed");
            softAssert.assertEquals(percent, student.getPercentage(), "Test on Insert-Percentage failed");
            softAssert.assertAll();
            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            logger.error("Error at testOnInsert := " + e);
        }
    }

    @Test
    public void testOnUpdate() {
        logger.info("Test on Update started!!");
        student.setStudentId(2);
        student.setStudentName("Lipika");
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

        query = " select student.id, student.studentName, student.lastName, " + " studentPersonalDetails.fatherName, studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " + " studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social, studentMarks.percentage " + " from student join studentMarks on student.id = studentMarks.studentId join studentPersonalDetails on studentPersonalDetails.studentId = student.id " + " where student.id = ? ";
        update.updateName(connection, logger, student);
        update.updateLName(connection, logger, student);
        update.updateFName(connection, logger, student);
        update.updateMName(connection, logger, student);
        update.updateAddress(connection, logger, student);
        update.updateDob(connection, logger, student);
        update.updateEnglish(connection, logger, student);
        update.updateHindi(connection, logger, student);
        update.updateMaths(connection, logger, student);
        update.updateScience(connection, logger, student);
        update.updateSocial(connection, logger, student);
        update.updatePercentage(connection, logger, student);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentId());
            rs = preparedStatement.executeQuery();
            rs.next();
            String name = rs.getString("studentName");
            String lName = rs.getString("lastName");
            String fName = rs.getString("fatherName");
            String mName = rs.getString("motherName");
            String address = rs.getString("address");
            String dob = rs.getString("dob");
            float english = rs.getFloat("english");
            float hindi = rs.getFloat("hindi");
            float maths = rs.getFloat("maths");
            float science = rs.getFloat("science");
            float social = rs.getFloat("social");
            float percent = rs.getFloat("percentage");
            softAssert.assertEquals(name, student.getStudentName(), "Test on Update-Name failed");
            softAssert.assertEquals(lName, student.getStudentLastName(), "Test on Update-LastName failed");
            softAssert.assertEquals(fName, student.getFatherName(), "Test on Update-FatherName failed");
            softAssert.assertEquals(mName, student.getMotherName(), "Test on Update-MotherName failed");
            softAssert.assertEquals(address, student.getAddress(), "Test on Update-Address failed");
            softAssert.assertEquals(dob, student.getDob(), "Test on Update-DOB failed");
            softAssert.assertEquals(english, student.getEnglish(), "Test on Update-English failed");
            softAssert.assertEquals(hindi, student.getHindi(), "Test on Update-Hindi failed");
            softAssert.assertEquals(maths, student.getMaths(), "Test on Update-Maths failed");
            softAssert.assertEquals(science, student.getScience(), "Test on Update-Science failed");
            softAssert.assertEquals(social, student.getSocial(), "Test on Update-Social failed");
            softAssert.assertEquals(percent, student.getPercentage(), "Test on Update-Percentage failed");
            softAssert.assertAll();
            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            logger.error("Error at testOnUpdates := " + e);
        }
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
