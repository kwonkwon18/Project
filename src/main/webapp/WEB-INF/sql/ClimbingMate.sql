USE RunData;

CREATE TABLE ClimbingMate (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    inserted DATETIME DEFAULT NOW()
);

DROP TABLE ClimbingMate;

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

SELECT * FROM ClimbingMate;



