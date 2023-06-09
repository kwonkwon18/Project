USE RunData;

CREATE TABLE ClimbingCourse (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    inserted DATETIME DEFAULT NOW()
);

SELECT * FROM ClimbingCourse;

ALTER TABLE ClimbingCourse
MODIFY inserted DATETIME NOT NULL DEFAULT NOW();

SELECT * FROM ClimbingCourseFileName;