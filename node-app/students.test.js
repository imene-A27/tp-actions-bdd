const { Client } = require('pg');

const {
    add_student,
    get_student,
    average_grade
} = require('./students');

let conn;

beforeAll(async () => {
    conn = new Client({
        host: 'localhost',
        port: 5432,
        user: 'postgres',
        password: 'postgres',
        database: 'tp'
    });

    await conn.connect();
});

beforeEach(async () => {
    await conn.query('TRUNCATE TABLE students RESTART IDENTITY');
});

afterAll(async () => {
    await conn.end();
});

test('Ajouter et rechercher un étudiant', async () => {
    await add_student(conn, 'Sara', 16);

    const student = await get_student(conn, 'Sara');

    expect(student).not.toBeNull();
    expect(student.name).toBe('Sara');
    expect(Number(student.grade)).toBe(16);
});

test('Rechercher un étudiant inexistant', async () => {
    const student = await get_student(conn, 'Inconnu');

    expect(student).toBeNull();
});

test('Calculer la moyenne des notes', async () => {
    await add_student(conn, 'Amel', 10);
    await add_student(conn, 'Karim', 14);

    expect(await average_grade(conn)).toBe(12);
});

