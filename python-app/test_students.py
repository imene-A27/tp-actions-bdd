import pytest
import psycopg2

from students import add_student, get_student, average_grade


@pytest.fixture
def conn():
    connection = psycopg2.connect(
        dbname="tp",
        user="postgres",
        password="postgres",
        host="localhost",
        port="5432"
    )

    with connection.cursor() as cursor:
        cursor.execute("TRUNCATE TABLE students RESTART IDENTITY")

    connection.commit()

    yield connection

    connection.close()


def test_add_and_get_student(conn):
    add_student(conn, "Sara", 16)

    student = get_student(conn, "Sara")

    assert student is not None
    assert student[0] == "Sara"
    assert float(student[1]) == 16.0


def test_get_nonexistent_student(conn):
    student = get_student(conn, "Inconnu")

    assert student is None


def test_average_grade(conn):
    add_student(conn, "Amel", 10)
    add_student(conn, "Karim", 14)

    assert average_grade(conn) == 12.0

