USE RunData;

CREATE TABLE ClimbingMateFileName (
	id INT PRIMARY KEY AUTO_INCREMENT,
    mateId INT NOT NULL,
    fileName VARCHAR(300) NOT NULL,
    FOREIGN KEY (mateId) REFERENCES ClimbingMate(id)
);

DROP TABLE ClimbingBoardFileName;

SELECT * FROM ClimbingMateFileName;

SELECT * FROM ClimbingCourse;