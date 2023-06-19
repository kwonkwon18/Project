USE RunData;

CREATE TABLE ClimbingCourseComment (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    content VARCHAR(500),
    memberId VARCHAR(20) NOT NULL,
    inserted DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (boardId) REFERENCES ClimbingCourse(id),
    FOREIGN KEY (memberId) REFERENCES Member(userId)
);

ALTER TABLE ClimbingCourseComment
MODIFY memberId VARCHAR(20) NOT NULL;

SELECT * FROM ClimbingCourseComment;

DESC ClimbingCourseComment;