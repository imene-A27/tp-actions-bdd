import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class StudentsTest {

    private static Connection conn;

    @BeforeAll
    static void connect() throws Exception {
        conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/tp",
            "postgres",
            "postgres"
        );
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE students RESTART IDENTITY");
        }
    }

    @AfterAll
    static void closeConnection() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    void testAddAndGetStudent() throws Exception {
        Students.add_student(conn, "Sara", 16);

        Students.Student student = Students.get_student(conn, "Sara");

        assertNotNull(student);
        assertEquals("Sara", student.name);
        assertEquals(16.0, student.grade);
    }

    @Test
    void testGetNonexistentStudent() throws Exception {
        Students.Student student =
            Students.get_student(conn, "Inconnu");

        assertNull(student);
    }

    @Test
    void testAverageGrade() throws Exception {
        Students.add_student(conn, "Amel", 10);
        Students.add_student(conn, "Karim", 14);

        assertEquals(12.0, Students.average_grade(conn));
    }
}

