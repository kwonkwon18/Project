<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBarFutsal></my:navBarFutsal>
	
	<br />
	<br />
	<br />
	<br />

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />



	<div style="margin-top: 53px; margin-left: 201.5px; max-width: 1903px; display: flex; min-width: 1500px;">
		<div style="width: 250px;">
			<my:advertisement1></my:advertisement1>
		</div>
		<div style="max-width: 1000px;">
			<h2>메이트구하기</h2>
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
					<a id="all1" href="/futsal/futsalMate" style="text-decoration-line: none;">전체</a>
					<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색 </a>
					<div class="dropdown-menu" aria-labelledby="search1">
						<a class="dropdown-item" href="#">메뉴 항목 1</a>
						<a class="dropdown-item" href="#">메뉴 항목 2</a>
						<a class="dropdown-item" href="#">메뉴 항목 3</a>
					</div>

					<a href="futsalMap" style="text-decoration-line: none;">지도로 보기</a>
					<span style="margin-left: 480px;"></span>
					<button type="button" class="btn btn-success" onclick="location.href='futsalAdd'">번개 글작성</button>
					<!-- <button type="button" class="btn btn-success" onclick="location.href='mateAdd'">소모임 글작성</button> -->
				</ul>
				<div id="dropdown1" style="display: none">
					<ul>


						<form action="/futsal/futsalMate" class="d-flex" role="search">
							<select class="form-select" name="type" id="">
								<option value="all">전체</option>
								<option value="title" ${param.type eq 'title' ? 'selected': '' }>제목</option>
								<option value="address" ${param.type eq 'address' ? 'selected': '' }>위치</option>
								<%-- <option value="writer" ${param.type eq 'writer' ? 'selected': '' }>글쓴이</option> --%>
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
					<a href="/futsal/futsalMate?type=distance" style="text-decoration-line: none;">거리순</a>
					<a href="/futsal/futsalMate" style="text-decoration-line: none;">최신순</a>
				</div>
			</ul>


			<div class="row row-cols-1 row-cols-md-3 g-4">
				<c:forEach items="${futsalMates}" var="board" varStatus="status">
					<fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />
					<fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />
					<div class="col">
						<div class="card">
							<div class="card-body">
								<h5 class="card-title">⚽⚽ ${board.title}</h5>
								<div>

									<div class="mb-3">
										<label for="" class="form-label">작성자</label>
										<input id="writerData${status.index + 1}" type="text" class="form-control" value="${board.writer}" readonly />
									</div>
									<div class="mb-3">
										<label for="" class="form-label">모임장소</label>
										<input id="addressText" type="text" class="form-control" value="${board.address }" readonly />
									</div>
									<div class="mb-3">
										<label for="" class="form-label">모임시간</label>
										<input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
									</div>

									<c:set var="isMember" value="false" />
									<c:forEach items="${memberList}" var="memberList">
										<c:if test="${memberList.nickName eq board.writer}">
											<c:set var="isMember" value="true" />
										</c:if>
									</c:forEach>

									<c:if test="${openDate <= nowDate }">
										<div class="card-footer card-footer-gray" style="text-align: right">
											<button class="btn btn-danger">마감된 매치</button>
										</div>
									</c:if>

									<c:if test="${openDate > nowDate }">
										<c:if test="${isMember}">
											<div class="card-footer card-footer-gray" style="text-align: right">
												<button data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="" class=" btn btn-success" onclick="location.href='/futsal/id/${board.id}'">내 게시물</button>
											</div>
										</c:if>

										<c:if test="${not isMember}">
											<div class="card-footer card-footer-gray" style="text-align: right">
												<button data-board-userId="${board.writer }" data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="listUpButton${status.index + 1}" class="listUpButton btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">더보기</button>
											</div>
										</c:if>
									</c:if>
								</div>
							</div>
						</div>
					</div>
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
		</div>

		<my:chatBtn></my:chatBtn>


		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3f405ca1718e37ea86f8585e0ca94ef5
&libraries=services"></script>
		<script src="/js/futsal/futsalMate.js" charset="UTF-8"></script>
		<script src="/js/chat.js" charset="UTF-8"></script>
</body>
</html>