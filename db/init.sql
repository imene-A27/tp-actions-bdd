CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    grade NUMERIC(4,2) NOT NULL
);

INSERT INTO students (name, grade) VALUES ('Imene', 16);
INSERT INTO students (name, grade) VALUES ('Ali', 12);


