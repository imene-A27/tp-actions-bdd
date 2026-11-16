import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Students {

    public static void add_student(Connection conn, String name, double grade)
            throws SQLException {

        String sql = "INSERT INTO students (name, grade) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, grade);
            stmt.executeUpdate();
        }
    }

    public static Student get_student(Connection conn, String name)
            throws SQLException {

        String sql = "SELECT name, grade FROM students WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getString("name"),
                        rs.getDouble("grade")
                    );
                }
            }
        }

        return null;
    }

    public static double average_grade(Connection conn) throws SQLException {

        String sql = "SELECT AVG(grade) FROM students";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }

        return 0.0;
    }

    public static class Student {
        public String name;
        public double grade;

        public Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }
}

