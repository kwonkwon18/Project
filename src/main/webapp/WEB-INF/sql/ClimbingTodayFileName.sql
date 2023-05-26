USE RunData;

CREATE TABLE ClimbingTodayFileName (
	id INT PRIMARY KEY AUTO_INCREMENT,
    todayId INT NOT NULL,
    fileName VARCHAR(300) NOT NULL,
    FOREIGN KEY (todayId) REFERENCES ClimbingToday(id)
);

SELECT * FROM ClimbingBoardFileName;

ALTER TABLE ClimbingTodayFileName
MODIFY id INT PRIMARY KEY AUTO_INCREMENT;

SELECT * FROM ClimbingCourse;

DROP TABLE ClimbingTodayFileName;