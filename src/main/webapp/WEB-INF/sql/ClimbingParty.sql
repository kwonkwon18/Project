USE RunData;

CREATE TABLE ClimbingParty (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    userId VARCHAR(20) NOT NULL,
    memberId VARCHAR(20) NOT NULL,
    FOREIGN KEY (boardId) REFERENCES ClimbingBoard(id)
);

SELECT * FROM ClimbingParty;

ALTER TABLE ClimbingParty
ADD participation INT;

ALTER TABLE ClimbingParty
ADD host VARCHAR(20);

ALTER TABLE ClimbingParty
ADD guest VARCHAR(20);

ALTER TABLE ClimbingParty
ADD chatCount INT;

ALTER TABLE ClimbingParty
ADD confirmation INT;