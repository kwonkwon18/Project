USE RunData;

CREATE TABLE ClimbingFileName (
	id INT PRIMARY KEY,
    boardId INT NOT NULL,
    fileName VARCHAR(300) NOT NULL,
    FOREIGN KEY (boardId) REFERENCES ClimbingBoard(id)
);

SELECT * FROM ClimbingFileName;