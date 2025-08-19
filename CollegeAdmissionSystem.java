import java.sql.*;
import java.util.*;
import java.io.FileWriter;

public class CollegeAdmissionSystem {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/CollegeAdmissionDB";
    static final String USER = "root";   // change to your MySQL username
    static final String PASS = "root";   // change to your MySQL password

    private Connection conn;

    public CollegeAdmissionSystem() throws Exception {
        conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    // Student registration
    public void registerStudent(String name, String email, double marks) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO Students(name, email, marks) VALUES(?,?,?)");
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setDouble(3, marks);
        ps.executeUpdate();
        System.out.println("✅ Student registered successfully!");
    }

    // Apply for a course
    public void applyForCourse(int studentId, int courseId) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO Applications(student_id, course_id, status) VALUES(?,?, 'Pending')");
        ps.setInt(1, studentId);
        ps.setInt(2, courseId);
        ps.executeUpdate();
        System.out.println("✅ Application submitted!");
    }

    // Admin approves/rejects based on cutoff
    public void processApplications() throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT a.app_id, s.marks, c.cutoff " +
            "FROM Applications a " +
            "JOIN Students s ON a.student_id=s.student_id " +
            "JOIN Courses c ON a.course_id=c.course_id " +
            "WHERE a.status='Pending'"
        );

        while (rs.next()) {
            int appId = rs.getInt("app_id");
            double marks = rs.getDouble("marks");
            double cutoff = rs.getDouble("cutoff");

            String newStatus = (marks >= cutoff) ? "Approved" : "Rejected";
            PreparedStatement ps = conn.prepareStatement("UPDATE Applications SET status=? WHERE app_id=?");
            ps.setString(1, newStatus);
            ps.setInt(2, appId);
            ps.executeUpdate();
        }
        System.out.println("✅ Applications processed!");
    }

    // Generate admission list CSV
    public void generateAdmissionList() throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT s.name, s.email, c.course_name, a.status " +
            "FROM Applications a " +
            "JOIN Students s ON a.student_id=s.student_id " +
            "JOIN Courses c ON a.course_id=c.course_id"
        );

        FileWriter writer = new FileWriter("admission_list.csv");
        writer.write("Name,Email,Course,Status\n");
        while (rs.next()) {
            writer.write(rs.getString("name") + "," +
                         rs.getString("email") + "," +
                         rs.getString("course_name") + "," +
                         rs.getString("status") + "\n");
        }
        writer.close();
        System.out.println("📄 Admission list saved as admission_list.csv");
    }

    public static void main(String[] args) {
        try {
            CollegeAdmissionSystem cas = new CollegeAdmissionSystem();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- College Admission Menu ---");
                System.out.println("1. Register Student");
                System.out.println("2. Apply for Course");
                System.out.println("3. Process Applications (Admin)");
                System.out.println("4. Generate Admission List");
                System.out.println("5. Exit");
                System.out.print("Choose: ");

                int choice = sc.nextInt();
                sc.nextLine();

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
                        System.out.print("Enter Student ID: ");
                        int sid = sc.nextInt();
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        cas.applyForCourse(sid, cid);
                        break;

                    case 3:
                        cas.processApplications();
                        break;

                    case 4:
                        cas.generateAdmissionList();
                        break;

                    case 5:
                        System.out.println("Goodbye!");
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
                          }
