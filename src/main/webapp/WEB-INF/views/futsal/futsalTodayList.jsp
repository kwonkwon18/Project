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
	<my:chatBtn></my:chatBtn>

	<my:navBarFutsal></my:navBarFutsal>
	<br />
	<br />
	<br />
	<br />

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />


	<div class="container-lg">
		<h2>오늘의 풋살</h2>
		<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
			<div class="carousel-indicators">
				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
			</div>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/futsalMate/%EC%BA%90%EB%9F%AC%EC%85%801.jpg" class="d-block w-100" height="500px" alt="...">
				</div>
				<div class="carousel-item">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/futsalMate/%EC%BA%90%EB%9F%AC%EC%85%802.jpg" class="d-block w-100" height="500px" alt="...">
				</div>
				<div class="carousel-item">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/futsalMate/%EC%BA%90%EB%9F%AC%EC%85%803.jpg" height="500px" alt="...">
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

			<nav>
				<ul>
					<span style="margin-left: 50px;"></span>
					<a id="all2" href="/futsal/futsalTodayList" style="text-decoration-line: none; display: inline-block;">전체</a>
					&nbsp; &nbsp;
					<a class="dropdown-toggle" href="#" role="button" id="search2" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색</a>
					&nbsp; &nbsp;
					<span style="margin-left: 800px;">
						<button type="button" class="btn btn-warning" onclick="location.href='futsalToday'" style="margin-left: auto;">풋살 공유하기 ✨</button>
					</span>
				</ul>
				<div id="dropdown2" style="display: none">
					<ul>
						<div class="d-flex justify-content-start align-items-center">
							<form action="/futsal/futsalTodayList" class="d-flex" role="search">
								<input value="${param.search }" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="flex-grow: 1;">
								<button class="btn btn-outline-success" type="submit">
									<i class="fa-solid fa-magnifying-glass"></i>
								</button>
							</form>
						</div>
					</ul>
				</div>
			</nav>

	
	<%-- <ul>
			<div style="text-align: right;">
				<a href="/futsal/futsalMate?type=distance" style="text-decoration-line: none;">거리순</a>
				<a href="/futsal/futsalMate" style="text-decoration-line: none;">최신순</a>
			</div>
		</ul>  --%>


		<div id="todayListData" class="row">
			<c:forEach items="${futsalTodayList}" var="board">
				<div class="col-md-4 todayListData" data-board-boardId="${board.id}" onclick='newPage(${board.id})'>
					<div class="card" style="width: 18rem; margin-bottom: 20px;">
						<div class="card-body" style="width: 18rem; margin-bottom: 20px; height: 555px;">
							<h5 class="card-title">⚽ ${board.title}</h5>
							<div class="mb-3">
								<label for="" class="form-label">작성자</label>
								<span id="writerData${status.index + 1}" type="text" class="form-control">${board.writer}</span>
							</div>
							<div class="mb-3">
								<label for="" class="form-label">모임장소</label>
								<span id="addressText" class="form-control">${board.body}</span>
							</div>
							<div class="mb-3">
								<label for="" class="form-label">모임시간</label>
								<span id="timeText" class="form-control">${board.inserted}</span>
							</div>

							<c:forEach items="${board.fileName }" var="fileName" varStatus="status">
								<c:if test="${status.count lt 2 }">
									<div>
										<img class="img-fluid img-thumbnail" src="${bucketUrl }/futsalToday/${board.id }/${fileName}" alt="" style="width: 450px; height: 260px !important;" />
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="/js/chat.js"></script>
		<script src="/js/futsal/futsalTodayList.js"></script>
		
		<script type="text/javascript">
		$("#search2").click(function() {
			if ($("#dropdown2").is(":hidden")) {
				$("#dropdown2").slideDown();
			} else {
				$("#dropdown2").slideUp();
			}
		});
		</script>
		
</body>
</html>