<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/Students.php';

class StudentsTest extends TestCase
{
    private static $conn;

    public static function setUpBeforeClass(): void
    {
        self::$conn = new PDO(
            'pgsql:host=localhost;port=5432;dbname=tp',
            'postgres',
            'postgres'
        );

        self::$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }

    protected function setUp(): void
    {
        self::$conn->exec("TRUNCATE TABLE students RESTART IDENTITY");
    }

    public static function tearDownAfterClass(): void
    {
        self::$conn = null;
    }

    public function testAddAndGetStudent(): void
    {
        Students::add_student(self::$conn, 'Sara', 16);

        $student = Students::get_student(self::$conn, 'Sara');

        $this->assertNotNull($student);
        $this->assertEquals('Sara', $student['name']);
        $this->assertEquals(16.0, (float) $student['grade']);
    }

    public function testGetNonexistentStudent(): void
    {
        $student = Students::get_student(self::$conn, 'Inconnu');

        $this->assertNull($student);
    }

    public function testAverageGrade(): void
    {
        Students::add_student(self::$conn, 'Amel', 10);
        Students::add_student(self::$conn, 'Karim', 14);

        $this->assertEquals(12.0, Students::average_grade(self::$conn));
    }
}

