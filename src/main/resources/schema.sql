CREATE TABLE home(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	space VARCHAR(100) NOT NULL,
	money VARCHAR(100) NOT NULL,
	address VARCHAR(100) NOT NULL,
	comment VARCHAR(100),
	PRIMARY KEY(id)
);

CREATE TABLE login
(
	id INT NOT NULL AUTO_INCREMENT,
	logId VARCHAR (100) NOT NULL,
	pass VARCHAR (100) NOT NULL,
	type VARCHAR (100) NOT NULL,
	name VARCHAR (100) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE loginC
(
	id INT NOT NULL AUTO_INCREMENT,
	logId VARCHAR (100) NOT NULL,
	pass VARCHAR (100) NOT NULL,
	name VARCHAR (100) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE chat
(
	id INT NOT NULL AUTO_INCREMENT,
	logId VARCHAR (100) NOT NULL,
	toId VARCHAR (100) NOT NULL,
	name VARCHAR (100) NOT NULL,
	toname VARCHAR (100) NOT NULL,
	chat VARCHAR (100) NOT NULL,
	chatC VARCHAR (100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE inquiry
(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR (100) NOT NULL,
   danjo VARCHAR (100) NOT NULL,
   mail VARCHAR (100) NOT NULL,
   tell VARCHAR (100) NOT NULL,
   job VARCHAR (100) NOT NULL,
   message VARCHAR (500) NOT NULL,
   date DATE ,
   PRIMARY KEY (id)
);