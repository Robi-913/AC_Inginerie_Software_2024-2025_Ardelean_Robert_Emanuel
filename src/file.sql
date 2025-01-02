USE library;

INSERT INTO role (role) VALUES ('administrator'), ('employee'), ('customer');

INSERT INTO `right` (`right`)
VALUES
    ('create_user'), ('delete_user'), ('update_user'),
    ('create_book'), ('delete_book'), ('update_book'),
    ('sell_book'), ('buy_book'), ('return_book');

INSERT INTO role_right (role_id, right_id)
SELECT r.id, rt.id
FROM role r CROSS JOIN `right` rt
WHERE r.role = 'administrator';

INSERT INTO role_right (role_id, right_id)
SELECT r.id, rt.id
FROM role r JOIN `right` rt
                 ON rt.right IN ('create_book', 'delete_book', 'update_book', 'sell_book')
WHERE r.role = 'employee';

INSERT INTO role_right (role_id, right_id)
SELECT r.id, rt.id
FROM role r JOIN `right` rt
                 ON rt.right IN ('sell_book', 'buy_book', 'return_book')
WHERE r.role = 'customer';

INSERT INTO user (username, password)
VALUES
    ('admin@example.com', SHA2('Admin@123', 256)),
    ('employee1@example.com', SHA2('Employee@123', 256)),
    ('customer1@example.com', SHA2('Customer@123', 256));

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id FROM user u, role r
WHERE u.username = 'admin@example.com' AND r.role = 'administrator';

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id FROM user u, role r
WHERE u.username = 'employee1@example.com' AND r.role = 'employee';

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id FROM user u, role r
WHERE u.username = 'customer1@example.com' AND r.role = 'customer';

INSERT INTO book (author, title, publishedDate, price, stock)
VALUES
    ('J.K. Rowling', 'Harry Potter and the Philosopher\'s Stone', '1997-06-26', 29.99, 10),
    ('J.R.R. Tolkien', 'The Lord of the Rings: The Fellowship of the Ring', '1954-07-29', 39.99, 8),
    ('George Orwell', '1984', '1949-06-08', 19.99, 15),
    ('Harper Lee', 'To Kill a Mockingbird', '1960-07-11', 24.99, 12),
    ('F. Scott Fitzgerald', 'The Great Gatsby', '1925-04-10', 14.99, 20),
    ('Gabriel García Márquez', 'One Hundred Years of Solitude', '1967-05-30', 34.99, 7),
    ('Leo Tolstoy', 'War and Peace', '1869-01-01', 49.99, 5),
    ('Jane Austen', 'Pride and Prejudice', '1813-01-28', 9.99, 25),
    ('Mary Shelley', 'Frankenstein', '1818-01-01', 12.99, 18),
    ('Markus Zusak', 'The Book Thief', '2005-03-14', 22.99, 10);

USE test_library;

INSERT INTO role (role) VALUES ('administrator'), ('employee'), ('customer');

INSERT INTO `right` (`right`)
VALUES
    ('create_user'), ('delete_user'), ('update_user'),
    ('create_book'), ('delete_book'), ('update_book'),
    ('sell_book'), ('buy_book'), ('return_book');

INSERT INTO role_right (role_id, right_id)
SELECT r.id, rt.id
FROM role r CROSS JOIN `right` rt
WHERE r.role = 'administrator';

INSERT INTO role_right (role_id, right_id)
SELECT r.id, rt.id
FROM role r JOIN `right` rt
                 ON rt.right IN ('create_book', 'delete_book', 'update_book', 'sell_book')
WHERE r.role = 'employee';

INSERT INTO role_right (role_id, right_id)
SELECT r.id, rt.id
FROM role r JOIN `right` rt
                 ON rt.right IN ('sell_book', 'buy_book', 'return_book')
WHERE r.role = 'customer';

INSERT INTO user (username, password)
VALUES
    ('admin@example.com', SHA2('Admin@123', 256)),
    ('employee1@example.com', SHA2('Employee@123', 256)),
    ('customer1@example.com', SHA2('Customer@123', 256));

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id FROM user u, role r
WHERE u.username = 'admin@example.com' AND r.role = 'administrator';

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id FROM user u, role r
WHERE u.username = 'employee1@example.com' AND r.role = 'employee';

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id FROM user u, role r
WHERE u.username = 'customer1@example.com' AND r.role = 'customer';

INSERT INTO book (author, title, publishedDate, price, stock)
VALUES
    ('J.K. Rowling', 'Harry Potter and the Philosopher\'s Stone', '1997-06-26', 29.99, 10),
    ('J.R.R. Tolkien', 'The Lord of the Rings: The Fellowship of the Ring', '1954-07-29', 39.99, 8),
    ('George Orwell', '1984', '1949-06-08', 19.99, 15),
    ('Harper Lee', 'To Kill a Mockingbird', '1960-07-11', 24.99, 12),
    ('F. Scott Fitzgerald', 'The Great Gatsby', '1925-04-10', 14.99, 20),
    ('Gabriel García Márquez', 'One Hundred Years of Solitude', '1967-05-30', 34.99, 7),
    ('Leo Tolstoy', 'War and Peace', '1869-01-01', 49.99, 5),
    ('Jane Austen', 'Pride and Prejudice', '1813-01-28', 9.99, 25),
    ('Mary Shelley', 'Frankenstein', '1818-01-01', 12.99, 18),
    ('Markus Zusak', 'The Book Thief', '2005-03-14', 22.99, 10);
