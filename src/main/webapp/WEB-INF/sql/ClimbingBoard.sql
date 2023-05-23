USE RunData;

CREATE TABLE ClimbingBoard (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    writer VARCHAR(20) NOT NULL UNIQUE,
    inserted DATETIME DEFAULT NOW()
);

SELECT * FROM ClimbingBoard;

ALTER TABLE ClimbingBoard
MODIFY fileName VARCHAR(100);

ALTER TABLE ClimbingBoard
ADD Lat INT;

ALTER TABLE ClimbingBoard
ADD Lng INT;

ALTER TABLE ClimbingBoard
MODIFY Lat Double;

ALTER TABLE ClimbingBoard
MODIFY Lng Double;

SELECT * FROM ClimbingBoard;

DELETE FROM C
WHERE id <=3;


DESC ClimbingBoard;


