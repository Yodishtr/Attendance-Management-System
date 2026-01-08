--Courses seed data
INSERT INTO course (course_code, course_name) VALUES ('CSC108', 'Intro to Programming'),
                                                     ('CSC148', 'Intro to Computer Science'),
                                                     ('CSC236', 'Theory of Computation'),
                                                     ('CSC309', 'Web Developement');

--Student seed data
INSERT INTO students (full_name, age, email, phone, address) VALUES
                                                                 ('Alice Wong', 19,
                                                                  'alice.wong@example.com',
                                                                  4165,
                                                                  '123 King St W'),
                                                                 ('Brian Singh',
                                                                  20,
                                                                  'brian.singh@example.com',
                                                                  41655,
                                                                  '45 College St'),
                                                                 ('Carla Patel',
                                                                  18,
                                                                  'carla.patel@example.com',
                                                                  41655,
                                                                  '78 Bloor St');

-- change the spring.jpa.hibernate.ddl-auto from create-drop to update