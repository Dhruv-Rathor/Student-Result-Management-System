import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager.java
 * Handles all JDBC connectivity and CRUD operations for Student and Result entities.
 * Includes exception handling for database errors.
 */
public class DatabaseManager {

    // IMPORTANT: Update these constants if your MySQL setup is different
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // --- Helper Method for Connection ---

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Ensure the JAR is in the classpath.");
            throw new SQLException("JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // --- Student CRUD Operations ---

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO Students (name, age) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        student.setStudentId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during student insertion: " + e.getMessage());
        }
        return false;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT student_id, name, age FROM Students WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getInt("age")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during student lookup: " + e.getMessage());
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_id, name, age FROM Students ORDER BY name";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error during fetching all students: " + e.getMessage());
        }
        return students;
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE Students SET name = ?, age = ? WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setInt(3, student.getStudentId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Database error during student update: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        deleteResultsByStudentId(id);

        String sql = "DELETE FROM Students WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Database error during student deletion: " + e.getMessage());
        }
        return false;
    }
    
    // --- Result CRUD Operations ---
    
    public boolean addResult(Result result) {
        String sql = "INSERT INTO Results (student_id, subject, marks) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, result.getStudentId());
            pstmt.setString(2, result.getSubject());
            pstmt.setDouble(3, result.getMarks());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        result.setResultId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during result insertion: " + e.getMessage());
        }
        return false;
    }

    public List<Result> getResultsByStudentId(int studentId) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT result_id, student_id, subject, marks FROM Results WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Result(
                        rs.getInt("result_id"),
                        rs.getInt("student_id"),
                        rs.getString("subject"),
                        rs.getDouble("marks")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during result lookup: " + e.getMessage());
        }
        return results;
    }

    public boolean deleteResultsByStudentId(int studentId) {
        String sql = "DELETE FROM Results WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Database error during results deletion: " + e.getMessage());
        }
        return false;
    }
}
