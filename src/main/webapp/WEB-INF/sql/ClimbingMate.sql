USE RunData;

CREATE TABLE ClimbingMate (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    inserted DATETIME DEFAULT NOW()
);


SELECT * FROM ClimbingMate;

ALTER TABLE ClimbingMate	
MODIFY inserted DATETIME NOT NULL DEFAULT NOW();

ALTER TABLE ClimbingMate
MODIFY fileName VARCHAR(100);

ALTER TABLE ClimbingMate
ADD Lat INT;

ALTER TABLE ClimbingMate
ADD Lng INT;

ALTER TABLE ClimbingMate
MODIFY Lat Double;

ALTER TABLE ClimbingMate
MODIFY Lng Double;

ALTER TABLE ClimbingMate
ADD people INT;

ALTER TABLE ClimbingMate
ADD currentNum INT;

ALTER TABLE ClimbingMate
ADD userId VARCHAR(100);

ALTER TABLE ClimbingMate
ADD memberId VARCHAR(100);

SELECT * FROM ClimbingMate;
SELECT * FROM Member	;

SELECT * FROM ClimbingMate
WHERE title LIKE '%시식%'
LIMIT 3;

USE RunData;
SELECT * FROM ClimbingMate;

ALTER TABLE ClimbingMate
ADD time DATETIME NOT NULL;

ALTER TABLE ClimbingMate
MODIFY COLUMN time DATETIME NOT NULL;

ALTER TABLE ClimbingMate
ADD FOREIGN KEY (writer) REFERENCES Member(nickName);

select * FROM RunningBoard;

DESC RunningBoard;
DESC ClimbingMate;
ALTER TABLE ClimbingMate ADD FOREIGN KEY (writer) REFERENCES Member (nickName);