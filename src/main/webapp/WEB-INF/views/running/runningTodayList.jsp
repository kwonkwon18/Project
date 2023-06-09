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

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />


	<div class="container-lg">
		<h2>메이트구하기</h2>
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

		<%-- <nav>
			<ul>
				<span style="margin-left: 50px;"></span>
				<a id="all1" href="/running/runningMate" style="text-decoration-line: none;">전체</a>
				<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색 </a>
				<div class="dropdown-menu" aria-labelledby="search1">
					<a class="dropdown-item" href="#">메뉴 항목 1</a>
					<a class="dropdown-item" href="#">메뉴 항목 2</a>
					<a class="dropdown-item" href="#">메뉴 항목 3</a>
				</div>

				<a href="runningMap" style="text-decoration-line: none;">지도로 보기</a> -->
				<span style="margin-left: 480px;"></span>
				<button type="button" class="btn btn-success" onclick="location.href='runningAdd'">번개 글작성</button>
				 <button type="button" class="btn btn-success" onclick="location.href='mateAdd'">소모임 글작성</button>
			</ul>
			<div id="dropdown1" style="display: none">
				<ul>
					<button type="button" class="btn btn-success" style="pointer-events: none;">검색🌄</button>
					<form action="/running/runningMate" class="d-flex" role="search">
						<select class="form-select" name="type" id="">
							<option value="all">전체</option>
							<option value="title" ${param.type eq 'title' ? 'selected': '' }>제목</option>
							<option value="address" ${param.type eq 'address' ? 'selected': '' }>위치</option>
						</select>
						<input value="${param.search }" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
						<button class="btn btn-outline-success" type="submit">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</form>
				</ul>
			</div>
		</nav>


	<ul>
			<div style="text-align: right;">
				<a href="/running/runningMate?type=distance" style="text-decoration-line: none;">거리순</a>
				<a href="/running/runningMate" style="text-decoration-line: none;">최신순</a>
			</div>
		</ul>  --%>


		<div id="todayListData" class="row">
			<c:forEach items="${runningTodayList}" var="board">
				<div class="col-md-4">
					<div class="card" style="width: 18rem; margin-bottom: 20px;">
						<div class="card-body">
							<h5 class="card-title">🌄${board.title}</h5>
							<p class="card-text">${board.writer}</p>
							<p class="card-text">${board.inserted}</p>

							<c:forEach items="${board.fileName }" var="fileName" varStatus="status">
								<c:if test="${status.count lt 2 }">
									<div>
										<img class="img-fluid img-thumbnail" src="${bucketUrl }/runningToday/${board.id }/${fileName}" alt="" height="300" width="300" />
									</div>
								</c:if>
							</c:forEach>

							<a class="btn btn-secondary" href="/running/todayId/${board.id }">상세보기</a>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	<my:chatBtn></my:chatBtn>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="/js/chat.js"></script>
</body>
</html>