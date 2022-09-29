package com.review;

import org.slf4j.Logger;

import java.sql.Connection;
import java.util.Scanner;

public interface InterfaceMainCrud {
    public void select(Connection connection, Logger logger, StudentGetterSetter student, Scanner sc);
    public boolean selectRandom(Connection connection, Logger logger, StudentGetterSetter student);
    public void selectAll(Connection connection, Logger logger);
    public void readData(Scanner sc, Connection connect, Logger logger, StudentGetterSetter studentCheck);
    public void insert(Connection connection, Scanner sc, StudentGetterSetter student, Logger logger);
    public boolean insertInStudent(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean deleteStudentRecord(Connection connection, Logger logger, StudentGetterSetter student);
    public void delete(Connection connection, StudentGetterSetter student, Logger logger, Scanner sc);
    public void updateRecords(Scanner sc, Logger logger, Connection connection, StudentGetterSetter student);
    public boolean updateName(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateLName(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateFName(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateMName(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateAddress(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateDob(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateEnglish(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateHindi(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateMaths(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateScience(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updateSocial(Connection connection, Logger logger, StudentGetterSetter student);
    public boolean updatePercentage(Connection connection, Logger logger, StudentGetterSetter student);

}
