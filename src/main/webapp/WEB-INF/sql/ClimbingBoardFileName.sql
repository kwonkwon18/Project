USE RunData;

CREATE TABLE ClimbingBoardFileName (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    fileName VARCHAR(300) NOT NULL,
    FOREIGN KEY (boardId) REFERENCES ClimbingBoard(id)
);

DROP TABLE ClimbingBoardFileName;

SELECT * FROM ClimbingBoardFileName;

SELECT * FROM ClimbingCourse;