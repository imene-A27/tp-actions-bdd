const { Pool } = require('pg');

async function add_student(conn, name, grade) {
    await conn.query(
        'INSERT INTO students (name, grade) VALUES ($1, $2)',
        [name, grade]
    );
}

async function get_student(conn, name) {
    const result = await conn.query(
        'SELECT name, grade FROM students WHERE name = $1',
        [name]
    );

    return result.rows[0] || null;
}

async function average_grade(conn) {
    const result = await conn.query(
        'SELECT AVG(grade) AS moyenne FROM students'
    );

    return result.rows[0].moyenne === null
        ? 0
        : Number(result.rows[0].moyenne);
}

module.exports = {
    add_student,
    get_student,
    average_grade
};

