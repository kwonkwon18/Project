USE RunData;

CREATE TABLE ClimbingCourseFileName (
	id INT PRIMARY KEY AUTO_INCREMENT,
    courseId INT NOT NULL,
    fileName VARCHAR(300) NOT NULL,
    FOREIGN KEY (courseId) REFERENCES ClimbingCourse(id)
);

DROP TABLE ClimbingCourseFileName;

SELECT * FROM ClimbingBoardFileName;
DESC ClimbingCourseFileName;