package com.review;

public class StudentGetterSetter {
    private int studentId;
    private String studentName;
    private String studentLastName;
    private String fatherName;
    private String motherName;
    private String address;
    private String dob;
    private float english;
    private float hindi;
    private float maths;
    private float science;
    private float social;
    private float percentage;

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public float getEnglish() {
        return english;
    }

    public void setEnglish(float english) {
        this.english = english;
    }

    public float getHindi() {
        return hindi;
    }

    public void setHindi(float hindi) {
        this.hindi = hindi;
    }

    public float getMaths() {
        return maths;
    }

    public void setMaths(float maths) {
        this.maths = maths;
    }

    public float getScience() {
        return science;
    }

    public void setScience(float science) {
        this.science = science;
    }

    public float getSocial() {
        return social;
    }

    public void setSocial(float social) {
        this.social = social;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public StudentGetterSetter(int studentId, String studentName, String studentLastName, String fatherName, String motherName, String address, String dob, float english, float hindi, float maths, float science, float social, float percentage) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.dob = dob;
        this.english = english;
        this.hindi = hindi;
        this.maths = maths;
        this.science = science;
        this.social = social;
        this.percentage = percentage;
    }

    public StudentGetterSetter() {
        super();
    }
}

