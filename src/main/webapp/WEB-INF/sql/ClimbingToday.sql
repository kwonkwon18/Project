USE RunData;

CREATE TABLE ClimbingToday (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    inserted DATETIME DEFAULT NOW()
);


SELECT * FROM ClimbingToday;


		    SELECT
		        c.id,
		        c.title,
		        c.body,
		        c.writer,
		        c.inserted,
		        f.fileName,
		        (SELECT COUNT(*) FROM ClimbingLike cl WHERE cl.boardId = c.id) likeCount,
		        (SELECT COUNT(*) FROM ClimbingComment ct WHERE ct.boardId = c.id) commentCount
		    FROM 
		        ClimbingToday c 
		        LEFT JOIN ClimbingTodayFileName f ON c.id = f.todayId
		        LEFT JOIN ClimbingLike cl on c.id = cl.boardId
		        LEFT JOIN ClimbingComment ct on c.id = ct.boardId
		    ORDER BY c.id DESC;



