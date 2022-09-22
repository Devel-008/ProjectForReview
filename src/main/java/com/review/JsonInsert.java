package com.review;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class JsonInsert {

    public void readData(Scanner sc, Connection connect, Logger logger, StudentGetterSetter studentCheck) {

        String query = "insert into student (id, studentName,lastName) values(?, ?, ?)";
        String query1 = "insert into studentPersonalDetails(studentId, fatherName, motherName, address, dob) values(?, ?, ?, ?, ?)";
        String query2 = "insert into studentMarks(studentId, english, hindi, maths, science, social, percentage ) values(?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Enter the address of file:-");
        String filePath = sc.next();
        int count = 0;
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            JSONArray array = (JSONArray) jsonObject.get("students");

            PreparedStatement preparedStatement;
            PreparedStatement preparedStatement1;
            PreparedStatement preparedStatement2;

            for(Object object : array){
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
                float total = studentCheck.getEnglish() + studentCheck.getHindi() + studentCheck.getMaths() + studentCheck.getScience() + studentCheck.getSocial();
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
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

}
