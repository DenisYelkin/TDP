drop trigger onActorDelete;
drop trigger onMovieDelete;
drop trigger onDirectorDelete;

drop table MovieDirectorConnector;
drop table director;
drop table character;
drop table actor;
drop table movie;

CREATE TABLE movie
(
	id VARCHAR2(100) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	release_date DATE,
	duration NUMBER(10),
	budget NUMBER(20),
	description LONG,
	countries VARCHAR2(1000),
	genres VARCHAR2(1000)
);

CREATE TABLE director
(
	id VARCHAR2(100) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	birth_date DATE,
	birth_country VARCHAR2(100)
);

CREATE TABLE actor
(
	id VARCHAR2(100) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	birth_date DATE,
	birth_country VARCHAR2(100)
);

CREATE TABLE character
(
	id VARCHAR2(100) PRIMARY KEY,
	actor_id VARCHAR2(100),
	movie_id VARCHAR2(100),
	char_name VARCHAR(50) NOT NULL,
	description LONG,
	CONSTRAINT fk_actor_char FOREIGN KEY (actor_id) REFERENCES actor(id),
	CONSTRAINT fk_movie_char FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE MovieDirectorConnector
(
	movie_id VARCHAR2(100),
	director_id VARCHAR2(100),
	CONSTRAINT fk_movie_MDConnector FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_director_MDConnector FOREIGN KEY (director_id) REFERENCES director(id)
);

CREATE OR REPLACE TRIGGER onMovieDelete
AFTER DELETE
ON MOVIE
FOR EACH ROW
DECLARE
BEGIN
  DELETE FROM MOVIEDIRECTORCONNECTOR
  WHERE MOVIE_ID = :OLD.id;
  DELETE FROM CHARACTER
  WHERE MOVIE_ID = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER onActorDelete
AFTER DELETE
ON ACTOR
FOR EACH ROW
DECLARE
BEGIN
  DELETE FROM CHARACTER
  WHERE ACTOR_ID = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER onDirectorDelete
AFTER DELETE
ON DIRECTOR
FOR EACH ROW
BEGIN
	DELETE FROM MOVIEDIRECTORCONNECTOR
	WHERE DIRECTOR_ID = :OLD.ID;
END;
/