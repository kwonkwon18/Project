<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gasoek+One&family=Orbit&display=swap" rel="stylesheet">
</head>
<body>

<style>
.my-card {
	border: 4px solid green;
}

h2 {
	font-family: 'Gasoek One', sans-serif;
	font-family: 'Orbit', sans-serif;
}
</style>

	<my:navBarClimbing></my:navBarClimbing>

	<div style="margin-top: 53px; margin-left: 201.5px; max-width: 1903px; display: flex; min-width: 1500px;">
		<div style="width: 250px;">
			<my:advertisement1></my:advertisement1>
		</div>
		<div style="max-width: 1000px;">
			<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
				<div class="carousel-indicators">
					<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
					<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
					<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
				</div>
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img style="width: 1000px; height: 500px;" src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%BA%90%EB%9F%AC%EC%85%801.jpg" class="d-block w-100" height="500px" alt="...">
					</div>
					<div class="carousel-item">
						<img style="width: 1000px; height: 500px;" src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%BA%90%EB%9F%AC%EC%85%802.jpg" class="d-block w-100" height="500px" alt="...">
					</div>
					<div class="carousel-item">
						<img style="width: 1000px; height: 500px;" src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%BA%90%EB%9F%AC%EC%85%803.jpg" height="500px" alt="...">
					</div>
				</div>
				<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">


					<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span class="visually-hidden">Previous</span>
				</button>
				<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span> <span class="visually-hidden">Next</span>
				</button>
			</div>
			<h2><img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%B6%94%EC%B2%9C+%EC%BD%94%EC%8A%A4.png"> </h2>

			<ul>
				<div style="display: flex; justify-content: flex-end; align-items: center; margin-bottom: 10px;">
					<a href="courseList">
						<button type="button" class="btn btn-success" style="margin-right: 10px;">전체 보기</button>
					</a>
					<button type="button" class="btn btn-success" style="pointer-events: none;">🌄지역별 보기</button>

					<form action="/climbing/courseList" class="d-flex" role="courseSearch">
						<input id="searchInput" value="${param.courseSearch}" name="courseSearch" class="form-control" type="courseSearch" placeholder="Search" aria-label="courseSearch" style="width: 300px">
						<button id="search" class="btn btn-outline-success" type="submit">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</form>
				</div>
				<div style="text-align: right;">
					<button type="button" class="btn btn-success" onclick="location.href='courseAdd'">코스 등록하기</button>
				</div>
			</ul>
			<br />
			<!-- 새로 작성된 코드, 변경된 코드  -->
			<!-- table.table>thead>tr>th*4^^tbody -->
			<div id="courseListData" class="row">
				<c:forEach items="${climbingCourseList}" var="board">
					<div class="col-md-4">
						<div class="card my-card" style="width: 18rem; margin-bottom: 20px;">
							<div onclick="location.href='courseId/${board.id}'">
								<div class="card-body">
									<h5 class="card-text d-flex justify-content-between">
										<span>🌄 ${board.writer}</span>
										<p style="font-size: medium;">${board.inserted}</p>
									</h5>
									<p class="card-title">${board.title}</p>
									<p class="card-text">

										<i class="fa-solid fa-heart"></i> ${board.likeCount } <i class="fa-regular fa-comments"></i> ${board.commentCount }

									</p>
								</div>
								<c:forEach items="${board.fileName }" var="fileName" varStatus="status">
									<c:if test="${status.count lt 2 }">
										<div>
											<img class="img-thumbnail" src="${bucketUrl}/climbingCourse/${board.id}/${fileName}" alt="" style="width: 285px; height: 260px !important;" />
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div style="width: 250px;">
			<my:advertisement2></my:advertisement2>
		</div>


      <sec:authorize access="isAuthenticated()">
			<my:chatBtn></my:chatBtn>
			<script src="/js/groupChat.js"></script>
			<script src="/js/chat.js" charset="UTF-8"></script>
		</sec:authorize>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</body>
</html>