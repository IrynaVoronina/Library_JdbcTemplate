CREATE TABLE Author (
	author_id SERIAL PRIMARY KEY,
  	firstName VARCHAR(50) NOT NULL,
  	middleName VARCHAR(50)NOT NULL,
	lastName VARCHAR(50)NOT NULL,
	birthplace VARCHAR(50)NOT NULL
);

INSERT INTO Author (firstName, middleName, lastName, birthplace)
VALUES 
	('Ernest', 'Miller', 'Hemingway', 'United States'),
    ('Erich', 'Maria', 'Remarque', 'German Empire'),
    ('J.K.', 'Rowling', 'Joanne', 'United Kingdom'),
    ('Agatha', 'Christie', 'Mary', 'United Kingdom'),
    ('Mark', 'Twain', 'Clemens', 'United States'),
    ('Aldous', 'Leonard', 'Huxley', 'United Kingdom'),
    ('Emily', 'Jane', 'Brontë', 'United Kingdom'),
	('Herman', 'Ann', 'Melville', 'United States'),
	('Nelle', 'Harper', 'Lee', 'United Kingdom');
	

CREATE TABLE Book (
    book_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
	keyword VARCHAR(50) NOT NULL
);

INSERT INTO Book (name, keyword)
VALUES 
	('The Old Man and the Sea', 'Sea'),
    ('The Night in Lisbon', 'Night'),
    ('Harry Potter and the Sorcerers Stone', 'Potter'),
    ('Murder on the Orient Express', 'Dystopian'),
    ('The Adventures of Huckleberry Finn', 'Satire'),
    ('Brave New World', 'World'),
    ('Wuthering Heights', 'Jane');


CREATE TABLE book_author (
	id SERIAL PRIMARY KEY,
    book_id INT,
    author_id INT,
    FOREIGN KEY (book_id) REFERENCES Book(book_id),
    FOREIGN KEY (author_id) REFERENCES Author(author_id)
);

INSERT INTO book_author (book_id, author_id) 
VALUES 
	(1, 1),
	(2, 2),
	(3, 3),
	(3, 8),
	(4, 4),
	(4, 9),
	(5, 1),
	(5, 5),	
	(6, 6),
	(6, 2),
	(7, 7);
	

SELECT * FROM Author;
SELECT * FROM Book;
SELECT * FROM book_author;

--getAllAuthors
SELECT * FROM Author;

--readAuthor
SELECT author_id, firstName, middleName, lastName, birthplace 
FROM Author WHERE id = 1;

--createAuthor
INSERT INTO Author (firstName, middleName, lastName, birthplace) 
VALUES ('Charles', 'John','Dickens', 'United Kingdom');

--updateAuthor
UPDATE Author SET firstName = 'updated', 
					middleName = 'updated',
					lastName = 'updated' ,
					birthplace 'updated'
WHERE id = 10;

--deleteAuthor
DELETE FROM Author WHERE author_id = 10;
DELETE FROM book_author WHERE author_id = 10;




--getAllBooks
SELECT b.book_id, b.name, b.keyword, a.author_id, 
			a.firstName, a.middleName, a.lastName, a.birthPlace
FROM Book b
LEFT JOIN book_author ba ON b.book_id = ba.book_id 
LEFT JOIN Author a ON ba.author_id = a.author_id;

--readBook

--createBook

--updateBook

--deleteBook
DELETE FROM Book WHERE book_id = 8;
DELETE FROM book_author WHERE book_id = 8;


