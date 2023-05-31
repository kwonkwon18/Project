<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
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
				<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span> <span class="visually-hidden">Next</span>
			</button>
		</div>
		<nav>
			<ul>
				<span style="margin-left: 50px;"></span>
				<a id="all1" href="#" style="text-decoration-line: none;">전체</a>
				<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색 </a>
				<div class="dropdown-menu" aria-labelledby="search1">
					<a class="dropdown-item" href="#">메뉴 항목 1</a> <a class="dropdown-item" href="#">메뉴 항목 2</a> <a class="dropdown-item" href="#">메뉴 항목 3</a>
				</div>
				<a href="mateMap" style="text-decoration-line: none;">지도로 보기</a>
				<span style="margin-left: 480px;"></span>
				<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">번개 글작성</button>
				<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">소모임 글작성</button>
			</ul>
			<div id="dropdown1" style="display: none">
				<ul>
					<button type="button" class="btn btn-success" style="pointer-events: none;">종류🌄</button>
					<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">전체</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
						<li><a class="dropdown-item" href="#">Action</a></li>
						<li><a class="dropdown-item" href="#">Another action</a></li>
						<li><a class="dropdown-item" href="#">Something else here</a></li>
					</ul>
				</ul>
				<ul>
					<button type="button" class="btn btn-success" style="pointer-events: none;">검색🌄</button>
					<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">제목</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
						<li><a class="dropdown-item" href="#">Action</a></li>
						<li><a class="dropdown-item" href="#">Another action</a></li>
						<li><a class="dropdown-item" href="#">Something else here</a></li>
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
				<a href="menu1.jsp" style="text-decoration-line: none;">거리순</a> <a href="menu2.jsp" style="text-decoration-line: none;">최신순</a>
			</div>
		</ul>

		<div class="row">
			<c:forEach items="${climbingMateList}" var="board">
				<div class="col-md-4">
					<div class="card" style="width: 18rem;">
						<div class="card-body">
							<h5 class="card-title">🌄${board.title}</h5>
							<p class="card-text">작성자: ${board.writer}</p>
							<p class="card-text">작성일자: ${board.inserted}</p>
							<div style="text-align: right">
								<a href="/climbing/mateId/${board.id}" class="btn btn-primary">더보기</a>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

	<br />
	<br />

	<div>
		<ul style="display: flex; align-items: center; margin-left: 360px;">
			<input value="${param.search }" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search" style="width: 400px">
			<button class="btn btn-outline-success" type="submit">
				<i class="fa-solid fa-magnifying-glass"></i>
			</button>
		</ul>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
	<script type="text/javascript">
		$("#all1").click(function() {
			$("#dropdown1").slideUp()
		})
		$("#search1").click(function() {
			$("#dropdown1").slideDown()
		})
	</script>

	<!-- ******************************************************************  -->

	<script>
	$(document).ready(function() {
		<c:forEach items="${runningMates}" var="board" varStatus="status">
			var latNum${status.index + 1} = ${board.lat};
			var lngNum${status.index + 1} = ${board.lng};

			var mapContainer${status.index + 1} = document.getElementById('map${status.index + 1}');
			var mapOption${status.index + 1} = {
				center : new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1}),
				level : 3
			};

			var map${status.index + 1} = new kakao.maps.Map(mapContainer${status.index + 1}, mapOption${status.index + 1});

			var markerPosition${status.index + 1} = new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1});
			var marker${status.index + 1} = new kakao.maps.Marker({
				position : markerPosition${status.index + 1}
			});

			marker${status.index + 1}.setMap(map${status.index + 1});
		</c:forEach>
	});
	</script>

	<script>
$(document).ready(function() {
	<c:forEach items="${runningMates}" var="board" varStatus="status">
		// 지도 초기화
		var latNum${status.index + 1} = ${board.lat};
		var lngNum${status.index + 1} = ${board.lng};

		var mapContainer${status.index + 1} = document.getElementById('map${status.index + 1}');
		var mapOption${status.index + 1} = {
			center : new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1}),
			level : 1
		};

		var map${status.index + 1} = new kakao.maps.Map(mapContainer${status.index + 1}, mapOption${status.index + 1});

		var markerPosition${status.index + 1} = new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1});
		var marker${status.index + 1} = new kakao.maps.Marker({
			position : markerPosition${status.index + 1}
		});

		marker${status.index + 1}.setMap(map${status.index + 1});

		// 버튼 클릭 이벤트 핸들러
		$('#joinPartyBtn${status.index + 1}').click(function() {
			location.href='/running/id/${board.id}';
			

		});
	</c:forEach>
});
	</script>
</body>
</html>