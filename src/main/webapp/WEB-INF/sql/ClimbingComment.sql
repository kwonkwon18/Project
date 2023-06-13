USE RunData;

CREATE TABLE ClimbingComment (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT,
    content VARCHAR(500),
    memberId VARCHAR(20),
    inserted DATETIME NOT NULL DEFAULT NOW()
);

SELECT * FROM ClimbingComment;