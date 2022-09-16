create table student(id int primary key not null, studentName varchar(100) not null);

create table studentMarks(studentid int primary key not null, english float not null, hindi float not null, maths float not null, science float not null, social float not null, percentage float not null);

create table studentPersonalDetails(studentID int primary key not null, fathername varchar(100) not null, mothername varchar(100) not null, address varchar(50), dob date not null);