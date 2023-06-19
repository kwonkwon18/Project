USE RunData;

CREATE TABLE ClimbingTodayComment (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    content VARCHAR(500),
    memberId VARCHAR(20) NOT NULL,
    inserted DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (boardId) REFERENCES ClimbingToday(id),
    FOREIGN KEY (memberId) REFERENCES Member(userId)
);

DESC ClimbingTodayComment;
			
ALTER TABLE ClimbingTodayComment
MODIFY memberId VARCHAR(20) NOT NULL;

SELECT * FROM ClimbingTodayComment;