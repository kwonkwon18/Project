CREATE TABLE ClimbingParty (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    hostId VARCHAR(20) NOT NULL,
    memberId VARCHAR(20) NOT NULL,
    FOREIGN KEY (boardId) REFERENCES ClimbingBoard(id)
);

SELECT * FROM ClimbingParty;