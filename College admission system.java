import java.util.*;
import java.io.*;

class Student {
    int id;
    String name;
    String email;
    double marks;

    public Student(int id, String name, String email, double marks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.marks = marks;
    }
}

class Course {
    int id;
    String name;
    double cutoff;

    public Course(int id, String name, double cutoff) {
        this.id = id;
        this.name = name;
        this.cutoff = cutoff;
    }
}

class Application {
    int id;
    Student student;
    Course course;
    String status;

    public Application(int id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.status = "Pending";
    }
}

public class CollegeAdmissionSystem {

    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();
    private int studentCounter = 1;
    private int courseCounter = 1;
    private int applicationCounter = 1;

    // Register new student
    public void registerStudent(String name, String email, double marks) {
        Student s = new Student(studentCounter++, name, email, marks);
        students.add(s);
        System.out.println("✅ Student registered successfully with ID: " + s.id);
    }

    // Add course
    public void addCourse(String name, double cutoff) {
        Course c = new Course(courseCounter++, name, cutoff);
        courses.add(c);
        System.out.println("✅ Course added with ID: " + c.id);
    }

    // Apply for course
    public void applyForCourse(int studentId, int courseId) {
        Student s = findStudentById(studentId);
        Course c = findCourseById(courseId);
        if (s == null || c == null) {
            System.out.println("⚠️ Invalid student ID or course ID!");
            return;
        }
        Application a = new Application(applicationCounter++, s, c);
        applications.add(a);
        System.out.println("✅ Application submitted (ID: " + a.id + ")");
    }

    // Process applications
    public void processApplications() {
        int count = 0;
        for (Application a : applications) {
            if (a.status.equals("Pending")) {
                if (a.student.marks >= a.course.cutoff) {
                    a.status = "Approved";
                } else {
                    a.status = "Rejected";
                }
                count++;
            }
        }
        System.out.println("✅ Processed " + count + " applications.");
    }

    // Export admission list to CSV
    public void exportAdmissionList(String filePath) {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write("ApplicationID,StudentName,Email,Course,Status\n");
            for (Application a : applications) {
                fw.write(a.id + "," + a.student.name + "," + a.student.email + "," +
                        a.course.name + "," + a.status + "\n");
            }
            System.out.println("📄 Admission list exported to " + filePath);
        } catch (IOException e) {
            System.out.println("⚠️ Error writing file: " + e.getMessage());
        }
    }

    // Helper methods
    private Student findStudentById(int id) {
        for (Student s : students) {
            if (s.id == id) return s;
        }
        return null;
    }

    private Course findCourseById(int id) {
        for (Course c : courses) {
            if (c.id == id) return c;
        }
        return null;
    }

    // Menu
    private static void menu() {
        System.out.println("\n===== College Admission System =====");
        System.out.println("1. Register Student");
        System.out.println("2. Add Course");
        System.out.println("3. List Students");
        System.out.println("4. List Courses");
        System.out.println("5. Apply for Course");
        System.out.println("6. Process Applications");
        System.out.println("7. List Applications");
        System.out.println("8. Export Admission List (CSV)");
        System.out.println("9. Exit");
        System.out.print("Choose: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CollegeAdmissionSystem cas = new CollegeAdmissionSystem();

        // Add some default courses
        cas.addCourse("Computer Science", 85);
        cas.addCourse("Electronics", 75);
        cas.addCourse("Mechanical", 70);

        while (true) {
            menu();
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    cas.registerStudent(name, email, marks);
                    break;
                case 2:
                    System.out.print("Enter Course Name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter Cutoff Marks: ");
                    double cutoff = sc.nextDouble();
                    cas.addCourse(cname, cutoff);
                    break;
                case 3:
                    System.out.println("\n-- Students --");
                    for (Student s : cas.students) {
                        System.out.println(s.id + " | " + s.name + " | " + s.email + " | " + s.marks);
                    }
                    break;
                case 4:
                    System.out.println("\n-- Courses --");
                    for (Course c : cas.courses) {
                        System.out.println(c.id + " | " + c.name + " | Cutoff: " + c.cutoff);
                    }
                    break;
                case 5:
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();
                    System.out.print("Enter Course ID: ");
                    int cid = sc.nextInt();
                    cas.applyForCourse(sid, cid);
                    break;
                case 6:
                    cas.processApplications();
                    break;
                case 7:
                    System.out.println("\n-- Applications --");
                    for (Application a : cas.applications) {
                        System.out.println(a.id + " | " + a.student.name + " | " +
                                a.course.name + " | " + a.status);
                    }
                    break;
                case 8:
                    cas.exportAdmissionList("admission_list.csv");
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("⚠️ Invalid option!");
            }
        }
    }
}
