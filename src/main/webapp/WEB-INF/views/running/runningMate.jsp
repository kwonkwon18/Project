<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

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
	<my:chatBtn></my:chatBtn>

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
		<nav>
			<ul>
				<span style="margin-left: 50px;"></span>
				<a id="all1" href="#" style="text-decoration-line: none;">전체</a>
				<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색 </a>
				<div class="dropdown-menu" aria-labelledby="search1">
					<a class="dropdown-item" href="#">메뉴 항목 1</a>
					<a class="dropdown-item" href="#">메뉴 항목 2</a>
					<a class="dropdown-item" href="#">메뉴 항목 3</a>
				</div>
				<a href="mateMap" style="text-decoration-line: none;">지도로 보기</a>
				<span style="margin-left: 480px;"></span>
				<button type="button" class="btn btn-success" onclick="location.href='runningAdd'">번개 글작성</button>
				<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">소모임 글작성</button>
			</ul>
			<div id="dropdown1" style="display: none">
				<ul>
					<button type="button" class="btn btn-success" style="pointer-events: none;">종류🌄</button>
					<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">전체</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
						<li>
							<a class="dropdown-item" href="#">Action</a>
						</li>
						<li>
							<a class="dropdown-item" href="#">Another action</a>
						</li>
						<li>
							<a class="dropdown-item" href="#">Something else here</a>
						</li>
					</ul>
				</ul>
				<ul>
					<button type="button" class="btn btn-success" style="pointer-events: none;">검색🌄</button>
					<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">제목</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
						<li>
							<a class="dropdown-item" href="#">Action</a>
						</li>
						<li>
							<a class="dropdown-item" href="#">Another action</a>
						</li>
						<li>
							<a class="dropdown-item" href="#">Something else here</a>
						</li>
					</ul>
					<input value="${param.search }" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search">
					<button class="btn btn-outline-success" type="submit">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</ul>
			</div>
		</nav>

		<ul>
			<div style="text-align: right;">
				<a href="menu1.jsp" style="text-decoration-line: none;">거리순</a>
				<a href="menu2.jsp" style="text-decoration-line: none;">최신순</a>
			</div>
		</ul>







		<div class="row row-cols-1 row-cols-md-3 g-4">
			<c:forEach items="${runningMates}" var="board" varStatus="status">
				<div class="col">
					<div class="card">
						<img src="..." class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								<span id="boardIdText${status.index + 1}">${board.id}</span>
								번게시물
							</h5>
							<div>
								<div class="mb-3">
									<label for="" class="form-label">제목</label>
									<input type="text" class="form-control" value="${board.title}" readonly />
								</div>
								<div class="mb-3">
									<label for="" class="form-label">작성자</label>
									<input id="writerData${status.index + 1}" type="text" class="form-control" value="${board.writer}" readonly />
								</div>
								<div class="mb-3">
									<label for="" class="form-label">모임시간</label>
									<input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
								</div>
								${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}

								<c:set var="isMember" value="false" />
								<c:forEach items="${memberList}" var="memberList">
									<c:if test="${memberList.nickName eq board.writer}">
										<c:set var="isMember" value="true" />
									</c:if>
								</c:forEach>

								<c:if test="${isMember}">
									<button type="button" onclick="location.href='/running/id/${board.id}' ">지원 사항 상세보기</button>
								</c:if>

								<c:if test="${not isMember}">
									<button data-board-userId="${board.writer }" data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="listUpButton${status.index + 1}" class="listUpButton btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">더보기</button>
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
		
	<div class="modal fade" id="createChatRoom" tabindex="-1" aria-labelledby="chatRoomModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" id="chatRoomModalBefore">
					<h1 class="modal-title fs-5" id="chatRoomModalLabel">대화방 생성</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body chatRoomModalBody">님과의 대화방을 생성하시겠습니까?</div>
				<div class="modal-footer">
					<button type="button" id="createChatRoomBtn" class="btn btn-primary">예</button>
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니요</button>
				</div>
			</div>
		</div>
	</div>


		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
		<script src="/js/running/runningMate.js"></script>
		<script src="/js/chat.js"></script>
</body>
</html>