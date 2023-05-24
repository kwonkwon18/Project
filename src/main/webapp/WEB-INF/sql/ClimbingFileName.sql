USE RunData;

CREATE TABLE ClimbingFileName (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    fileName VARCHAR(300) NOT NULL,
    FOREIGN KEY (boardId) REFERENCES ClimbingBoard(id)
);

SELECT * FROM ClimbingFileName;

ALTER TABLE ClimbingFileName
MODIFY id INT PRIMARY KEY AUTO_INCREMENT;

SELECT * FROM ClimbingCourse;