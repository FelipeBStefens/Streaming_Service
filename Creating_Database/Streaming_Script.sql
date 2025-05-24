-- Creating database
CREATE DATABASE stream_db;

-- Using our database
USE stream_db;

-- Creating user table
CREATE TABLE user(
	id_user BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
	nickname VARCHAR(35) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_user VARCHAR(35) NOT NULL,
    image_user VARCHAR(300),
    PRIMARY KEY(id_user)
);

-- Creating stream table
CREATE TABLE streamTable(
	id_stream BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description_stream VARCHAR(500),
    poster VARCHAR(300) UNIQUE,
    release_date DATE NOT NULL,
    PRIMARY KEY(id_stream)
);

-- Creating serie table
CREATE TABLE serie(
	id_serie BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id_serie),
    FOREIGN KEY(id_serie) REFERENCES streamTable(id_stream)
);

-- Creating movie table
CREATE TABLE movie(
	id_movie BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    duration TIME NOT NULL,
    PRIMARY KEY(id_movie),
    FOREIGN KEY(id_movie) REFERENCES streamTable(id_stream)
);

-- Creating season table
CREATE TABLE season(
	id_season BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    number_season INT NOT NULL,
    id_serie BIGINT UNIQUE NOT NULL,
    PRIMARY KEY(id_season),
    FOREIGN KEY(id_serie) REFERENCES serie(id_serie)
);

-- Creating episode table
CREATE TABLE episode(
	id_episode BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description_stream VARCHAR(500),
    release_date DATE NOT NULL,
    duration TIME NOT NULL,
    id_season BIGINT UNIQUE NOT NULL,
    PRIMARY KEY(id_episode),
    FOREIGN KEY(id_season) REFERENCES season(id_season)
);

-- Creating rating table
CREATE TABLE rating(
	id_rating BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    rate INT,
    id_stream BIGINT UNIQUE NOT NULL,
    id_user BIGINT UNIQUE NOT NULL,
	PRIMARY KEY(id_rating),
    FOREIGN KEY(id_stream) REFERENCES streamTable(id_stream),
    FOREIGN KEY(id_user) REFERENCES user(id_user)
);