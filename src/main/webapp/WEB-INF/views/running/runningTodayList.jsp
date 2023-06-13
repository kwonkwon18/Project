<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBar></my:navBar>
	<div class="container-lg">
		<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
			<div class="carousel-indicators">
				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
			</div>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%BA%90%EB%9F%AC%EC%85%801.jpg" class="d-block w-100" height="500px" alt="...">
				</div>
				<div class="carousel-item">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%BA%90%EB%9F%AC%EC%85%802.jpg" class="d-block w-100" height="500px" alt="...">
				</div>
				<div class="carousel-item">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/climbingMate/%EC%BA%90%EB%9F%AC%EC%85%803.jpg" height="500px" alt="...">
				</div>
			</div>
			<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>
		<h2>오늘의 러닝</h2>
		<!-- 새로 작성된 코드, 변경된 코드  -->
		<!-- table.table>thead>tr>th*4^^tbody -->
		<div style="display: flex;">
			<div style="flex: 1; margin-left: 800px;" id="mateMapBox">
				<ul style="display: flex; align-items: left;">
					<form action="/search" class="d-flex" role="search">
						<input id="searchInput" value="${param.search}" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search" style="width: 300px">
						<button id="search" class="btn btn-outline-success" type="submit">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
						<button type="button" class="btn btn-success" onclick="location.href='runningToday'" style="margin-left: 10px;">번개 글작성</button>
					</form>
				</ul>
			</div>
		</div>
		<br />
		<div class="row">
			<c:forEach items="${runningTodayList}" var="board">
				<div class="col-md-4 todayListData" data-board-boardId="${board.id }" onclick='newPage(${board.id })'>
					<div class="card" style="width: 18rem; margin-bottom: 20px;">
						<div class="card-body">
							<h5 class="card-title">🌄${board.title}</h5>
							<p class="card-text">${board.writer}</p>
							<p class="card-text">${board.body}</p>
							<p class="card-text">${board.inserted}</p>
							<p class="card-text">
								<i class="fa-solid fa-heart"></i>
								${board.likeCount }
								<i class="fa-regular fa-comments"></i>
								${board.commentCount }
							</p>

							<input type="hidden" class="idValue" value="${board.id }" />
						</div>
						<!-- 							<p class="card-text"> -->
						<c:forEach items="${board.fileName }" var="fileName" varStatus="status">
							<c:if test="${status.count lt 2 }">
								<div>
									<img class="img-fluid img-thumbnail" src="${bucketUrl }/runningToday/${board.id }/${fileName}" alt="" style="width: 285px; height: 260px !important;" />
								</div>
							</c:if>
						</c:forEach>

						<!-- 							</p> -->

					</div>
				</div>
			</c:forEach>
			<!-- 		<table class="table"> -->
			<!-- 			<thead> -->
			<!-- 				<tr> -->
			<!-- 					<th>#</th> -->
			<!-- 					<th>제목</i></th> -->
			<!-- 					<th>작성자</th> -->
			<!-- 					<th>작성일자</th> -->
			<!-- 				</tr> -->
			<!-- 			</thead> -->
			<!-- 			<tbody> -->
			<!-- 				todayList를 받았다.  -->
			<%-- 				<c:forEach items="${climbingTodayList }" var="board"> --%>

			<!-- 					<tr> -->
			<%-- 						<td>${board.id }</td> --%>
			<%-- 						<td><a href="/climbing/todayId/${board.id }"> ${board.title }</a> --%>
			<%-- 						<td>${board.writer }</td> --%>
			<%-- 						<td>${board.inserted }</td> --%>
			<!-- 					</tr> -->
			<%-- 				</c:forEach> --%>
			<!-- 			</tbody> -->
			<!-- 		</table> <-->
			</-->
		</div>

	<my:chatBtn></my:chatBtn>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="/js/chat.js"></script>
		<script src="/js/running/runningTodayList.js" charset="UTF-8"></script>
</body>
</html>