def add_student(conn, name, grade):
    """Ajouter un étudiant dans la base de données."""
    with conn.cursor() as cursor:
        cursor.execute(
            "INSERT INTO students (name, grade) VALUES (%s, %s)",
            (name, grade)
        )
    conn.commit()


def get_student(conn, name):
    """Rechercher un étudiant par son nom."""
    with conn.cursor() as cursor:
        cursor.execute(
            "SELECT name, grade FROM students WHERE name = %s",
            (name,)
        )
        return cursor.fetchone()


def average_grade(conn):
    """Calculer la moyenne des notes."""
    with conn.cursor() as cursor:
        cursor.execute("SELECT AVG(grade) FROM students")
        result = cursor.fetchone()

    return float(result[0]) if result[0] is not None else 0.0

