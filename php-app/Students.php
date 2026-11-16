<?php

class Students
{
    public static function add_student(PDO $conn, string $name, float $grade): void
    {
        $sql = "INSERT INTO students (name, grade) VALUES (:name, :grade)";

        $stmt = $conn->prepare($sql);
        $stmt->execute([
            ':name' => $name,
            ':grade' => $grade
        ]);
    }

    public static function get_student(PDO $conn, string $name): ?array
    {
        $sql = "SELECT name, grade FROM students WHERE name = :name";

        $stmt = $conn->prepare($sql);
        $stmt->execute([':name' => $name]);

        $student = $stmt->fetch(PDO::FETCH_ASSOC);

        return $student ?: null;
    }

    public static function average_grade(PDO $conn): float
    {
        $sql = "SELECT AVG(grade) FROM students";

        $result = $conn->query($sql)->fetchColumn();

        return $result !== null ? (float) $result : 0.0;
    }
}


