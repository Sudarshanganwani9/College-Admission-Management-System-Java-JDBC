# College-Admission-Management-System-Java-JDBC

# 🎓 College Admission Management System

## 📌 Objective
A Java-based application to **manage student applications, course allocation, and merit lists**.  
It allows students to register, apply for courses, and helps the admin process applications based on cut-off marks.

---

## 🛠️ Tools & Technologies
- **Java** (Core Java + JDBC)
- **MySQL** (Database)
- **JDBC Driver** (Connector for Java & MySQL)

---

## 📂 Project Structure

CollegeAdmissionSystem/ │── CollegeAdmissionSystem.java  │── README.md                     # Project documentation └── Database.sql           
---

## 📊 Database Schema

### Students Table
| Field       | Type         | Description             |
|-------------|-------------|-------------------------|
| student_id  | INT (PK)    | Unique student ID       |
| name        | VARCHAR(100)| Student name            |
| email       | VARCHAR(100)| Unique student email    |
| marks       | DOUBLE      | Student marks           |

### Courses Table
| Field       | Type         | Description             |
|-------------|-------------|-------------------------|
| course_id   | INT (PK)    | Unique course ID        |
| course_name | VARCHAR(100)| Course name             |
| cutoff      | DOUBLE      | Minimum cutoff marks    |

### Applications Table
| Field       | Type         | Description                        |
|-------------|-------------|------------------------------------|
| app_id      | INT (PK)    | Unique application ID              |
| student_id  | INT (FK)    | Reference to Students table        |
| course_id   | INT (FK)    | Reference to Courses table         |
| status      | VARCHAR(20) | Application status (Pending/Approved/Rejected) |

---

## ⚙️ Features
- Student registration with marks
- Apply for multiple courses
- Admin processing (approve/reject based on cut-off)
- Generate **admission list in CSV format**

---

## 🚀 How to Run

### 1. Setup Database
Run the following in MySQL:
```sql
CREATE DATABASE CollegeAdmissionDB;
USE CollegeAdmissionDB;

-- Create tables
CREATE TABLE Students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    marks DOUBLE
);

CREATE TABLE Courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100),
    cutoff DOUBLE
);

CREATE TABLE Applications (
    app_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    status VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- Insert sample courses
INSERT INTO Courses(course_name, cutoff) VALUES
('Computer Science', 85),
('Electronics', 75),
('Mechanical', 70);

2. Compile & Run Java Program

javac CollegeAdmissionSystem.java
java CollegeAdmissionSystem

3. Menu Options

1 → Register Student

2 → Apply for Course

3 → Process Applications (Admin)

4 → Generate Admission List (CSV)

5 → Exit



---

📄 Sample Output

admission_list.csv

Name,Email,Course,Status
Alice,alice@gmail.com,Computer Science,Approved
Bob,bob@gmail.com,Electronics,Rejected


---

📦 Deliverables

✅ Database Schema (Database.sql)

✅ Java Source Code (CollegeAdmissionSystem.java)

✅ User Guide (This README)

✅ Output Files (admission_list.csv)

