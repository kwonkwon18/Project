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

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />


	<div class="container-lg">
		<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">

			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/runningMate/%EB%8B%AC%EB%A6%AC%EA%B8%B04.jpg" class="d-block w-100" height="500px" alt="...">
				</div>
			</div>
		</div>
	<br />
		<br />
		<nav>
			<ul>
				<h2>메이트 구하기 🏃‍♀️ 🏃‍♀️</h2>
				<span style="margin-left: 50px;"></span>
				<a id="all1" href="/running/runningMate" style="text-decoration-line: none;">전체</a>
				&nbsp; &nbsp;
				<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색</a>
				&nbsp; &nbsp;
				<a href="runningMap" style="text-decoration-line: none;">
					지도로 보기
					<i class="fa-solid fa-map-location-dot"></i>
				</a>
				<span style="margin-left: 800px;">
					<button type="button" class="btn btn-success" onclick="location.href='runningAdd'">번개 글작성 ⚡</button>
				</span>
			</ul>
			<div id="dropdown1" style="display: none">
				<ul>
					<form action="/running/runningMate" class="d-flex align-items-center" role="search">
						<select class="form-select" name="type" id="" style="width: 150px">
							<option value="all">전체</option>
							<option value="title" ${param.type eq 'title' ? 'selected': '' }>제목</option>
							<option value="address" ${param.type eq 'address' ? 'selected': '' }>위치</option>
							<%-- <option value="writer" ${param.type eq 'writer' ? 'selected': '' }>글쓴이</option> --%>
						</select>
						<input value="${param.search }" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search">
						<button class="btn btn-outline-success" type="submit">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</form>
				</ul>
			</div>
		</nav>

		<!-- <ul>
			<div style="text-align: right;">
				<a href="/running/runningMate?type=distance" style="text-decoration-line: none;">거리순</a>
				<a href="/running/runningMate" style="text-decoration-line: none;">최신순</a>
			</div>
		</ul> -->


		<div class="row row-cols-1 row-cols-md-3 g-4">
			<c:forEach items="${runningMates}" var="board" varStatus="status">

				<c:set var="isMember" value="false" />
				<c:forEach items="${memberList}" var="memberList">
					<c:if test="${memberList.nickName eq board.writer}">
						<c:set var="isMember" value="true" />
					</c:if>
				</c:forEach>


				<fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />
				<fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />
				<div class="col">
					<div class="card ${isMember ? 'card-member' : 'card-nonMember'}">
						<div class="card-body">
							<h5 class="card-title">🏃‍♀️🏃‍♂️ ${board.title}</h5>
							<div>
								<div class="mb-3">
									<label for="" class="form-label">작성자</label>
									<span id="writerData${status.index + 1}" type="text" class="form-control">${board.writer}</span>
								</div>
								<div class="mb-3">
									<label for="" class="form-label">모임장소</label>
									<span id="addressText" class="form-control">${board.address}</span>
								</div>
								<div class="mb-3">
									<label for="" class="form-label">모임시간</label>
									<span id="timeText" class="form-control">${board.time}</span>
								</div>




								<c:if test="${openDate <= nowDate }">
									<div class="card-footer card-footer-gray" style="text-align: right">
										<button class="btn btn-danger">마감된 러닝</button>
									</div>
								</c:if>

								<c:if test="${openDate > nowDate }">
									<c:if test="${isMember}">
										<div class="card-footer card-footer-gray" style="text-align: right">
											<button data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="" class=" btn btn-success" onclick="location.href='/climbing/id/${board.id}'">내 게시물</button>
										</div>
									</c:if>

									<c:if test="${not isMember}">
										<div class="card-footer card-footer-gray" style="text-align: right">
											<button data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="listUpButton${status.index + 1}" class="listUpButton btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">더보기</button>
										</div>
									</c:if>
								</c:if>
							</div>
						</div>
					</div>
				</div>

			</c:forEach>
		</div>

		<br />
		<br />



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





		<!-- 맨밑 고정  -->

		<sec:authorize access="isAuthenticated()">
			<my:chatBtn></my:chatBtn>
			<script src="/js/groupChat.js"></script>
			<script src="/js/chat.js" charset="UTF-8"></script>
		</sec:authorize>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
		<script src="/js/running/runningMate.js" charset="UTF-8"></script>

		<style>
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

		<script type="text/javascript">
			$("#search1").click(function() {
				if ($("#dropdown1").is(":hidden")) {
					$("#dropdown1").slideDown();
				} else {
					$("#dropdown1").slideUp();
				}
			});
		</script>





		<script src="/js/navBar.js"></script>
</body>
</html>