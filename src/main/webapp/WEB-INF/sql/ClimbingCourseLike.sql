USE RunData;

CREATE TABLE ClimbingCourseLike (
	boardId INT NOT NULL,
    memberId VARCHAR(20) NOT NULL,
    PRIMARY KEY(boardId, memberId),
    FOREIGN KEY (boardId) REFERENCES ClimbingCourse(id),
    FOREIGN KEY (memberId) REFERENCES Member(userId)
);

SELECT * FROM ClimbingCourseLike;

SELECT * FROM Member;