USE RunData;

CREATE TABLE ClimbingToday (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    inserted DATETIME DEFAULT NOW()
);


SELECT * FROM ClimbingToday;

DELETE FROM ClimbingToday
WHERE id <= 13;

DESC ClimbingToday;

ALTER TABLE ClimbingToday
MODIFY inserted DATETIME NOT NULL DEFAULT NOW();

