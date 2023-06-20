<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<my:navBarRunning></my:navBarRunning>
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
		<br />
		<!-- 새로 작성된 코드, 변경된 코드  -->
		<!-- table.table>thead>tr>th*4^^tbody -->

		<%-- 		<ul style="display: flex; align-items: center;">
			<span style="margin-left: 50px;"></span>
			<a id="all2" href="/running/runningTodayList" style="text-decoration-line: none; display: inline-block;">전체</a>
			&nbsp; &nbsp;
			<form action="/running/runningTodayList" class="d-flex" role="search">
				<input value="${param.search }" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="flex-grow: 1;">
				<button class="btn btn-outline-success" type="submit">
					<i class="fa-solid fa-magnifying-glass"></i>
				</button>
			</form>
			<button type="button" class="btn btn-success" onclick="location.href='mateAdd'" style="margin-left: auto;">러닝 공유하기 ⚡</button>
		</ul> --%>

		<nav>
			<ul>
				<span style="margin-left: 50px;"></span>
				<a id="all2" href="/running/runningTodayList" style="text-decoration-line: none; display: inline-block;">전체</a>
				&nbsp; &nbsp;
				<a class="dropdown-toggle" href="#" role="button" id="search2" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색</a>
				&nbsp; &nbsp;
				<span style="margin-left: 900px;">
					<button type="button" class="btn btn-warning" onclick="location.href='runningToday'" style="margin-left: auto;">러닝 공유하기 ✨</button>
				</span>
			</ul>
			<div id="dropdown2" style="display: none">
				<ul>
					<div class="d-flex justify-content-start align-items-center">
						<form action="/running/runningTodayList" class="d-flex" role="search">
							<input value="${param.search }" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="flex-grow: 1;">
							<button class="btn btn-outline-success" type="submit">
								<i class="fa-solid fa-magnifying-glass"></i>
							</button>
						</form>
					</div>
				</ul>
			</div>
		</nav>




		<div class="row">
			<c:forEach items="${runningTodayList}" var="boardToday" varStatus="status">
				<div class="col-md-4 todayListData" data-board-boardId="${boardToday.id}" onclick='newPage(${boardToday.id})'>
					<div class="card todayCard">
						<div class="card-body">

							<h5 class="card-title">🏃‍♀️🏃‍♂️ ${boardToday.title}</h5>
							<div>
								<div class="mb-3">
									<label for="" class="form-label">작성자</label>
									<span id="writerData${status.index + 1}" type="text" class="form-control">${boardToday.writer}</span>
								</div>
								<div class="mb-3">
									<label for="" class="form-label">모임장소</label>
									<span id="addressText" class="form-control">${boardToday.body}</span>
								</div>
								<div class="mb-3">
									<label for="" class="form-label">모임시간</label>
									<span id="timeText" class="form-control">${boardToday.inserted}</span>
								</div>

								<%-- <h5 class="card-title">🌄${boardToday.title}</h5>
								<p class="card-text">${boardToday.writer}</p>
								<p class="card-text">${boardToday.body}</p>
								<p class="card-text">${boardToday.inserted}</p> --%>
								<c:forEach items="${boardToday.fileName }" var="fileName" varStatus="status">
									<c:if test="${status.count lt 2 }">
										<div>
											<img class="img-fluid img-thumbnail" src="${bucketUrl }/runningToday/${boardToday.id }/${fileName}" alt="" style="width: 450px; height: 260px !important;" />
										</div>
									</c:if>
								</c:forEach>
								<input type="hidden" class="idValue" value="${boardToday.id }" />
							</div>
							<p class="card-text" style="font-size: 25px; text-align: right; margin-right: 10px; margin-bottom: 30px;">
								<i class="fa-regular fa-heart"></i>
								${boardToday.likeCount}
								<i class="fa-regular fa-comment"></i>
								${boardToday.commentCount}
							</p>

						</div>
					</div>
				</div>
				<br />
			</c:forEach>
		</div>

		<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">게시물 상세 보기</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body" id="resMate"></div>
				</div>
			</div>
		</div>

		<sec:authorize access="isAuthenticated()">
			<my:chatBtn></my:chatBtn>
			<script src="/js/groupChat.js"></script>
			<script src="/js/chat.js" charset="UTF-8"></script>
		</sec:authorize>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="/js/running/runningTodayList.js" charset="UTF-8"></script>
		<script src="/js/navBar.js"></script>

		<script type="text/javascript">
		$("#search2").click(function() {
			if ($("#dropdown2").is(":hidden")) {
				$("#dropdown2").slideDown();
			} else {
				$("#dropdown2").slideUp();
			}
		});
	</script>

		<style>
.todayListData {
	margin-bottom: 20px;
}

.card-member {
	border: 4px solid #56B37F;
}

.card-nonMember {
	border: 4px solid #646EFF;
}

.todayCard {
	border: 4px solid #DCEBFF;
}

h2 {
	font-family: 'Gasoek One', sans-serif;
	font-family: 'Orbit', sans-serif;
}
</style>
</body>
</html>