USE RunData;

CREATE TABLE ClimbingLike (
	boardId INT NOT NULL,
    memberId VARCHAR(20) NOT NULL,
    PRIMARY KEY(boardId, memberId),
    FOREIGN KEY (boardId) REFERENCES ClimbingToday(id),
    FOREIGN KEY (memberId) REFERENCES Member(userId)
);

SELECT * FROM ClimbingLike;

SELECT * FROM Member;