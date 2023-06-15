<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

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

	<my:navBarClimbing>
	</my:navBarClimbing>

	<my:chatBtn></my:chatBtn>

	<div class="container-lg">
		<!-- 		<h2>메이트구하기</h2> -->
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
		<!-- 		<nav> -->
		<ul>
			<!-- 				<a id="all1" href="/climbing/mateList" style="text-decoration-line: none;">전체</a> -->
			<button type="button" class="btn btn-warning" style="margin-left: 70px;" onclick="location.href='mateList'">전체 보기</button>

			<!-- 				<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색 </a> -->
			<!-- 				<div class="dropdown-menu" aria-labelledby="search1"> -->
			<!-- 					<a class="dropdown-item" href="#">메뉴 항목 1</a> <a class="dropdown-item" href="#">메뉴 항목 2</a> <a class="dropdown-item" href="#">메뉴 항목 3</a> -->
			<!-- 				</div> -->
			<!-- 				<a href="mateMap" style="text-decoration-line: none;">지도로 보기</a> -->
			<div style="float: right;">
				<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">번개 글작성</button>
				<!-- 					<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">소모임 글작성</button> -->
			</div>
		</ul>
	</div>
	
	
	
<!-- 		<div style="display: flex;"> -->
<!-- 			<div style="flex: 1; margin-left: 70px;" id="mateMapBox"> -->
<!-- 				<ul style="display: flex; align-items: left;"> -->
<!-- 					<form action="/climbing/search" class="d-flex" role="search"> -->
<%-- 						<input id="searchInput" value="${param.search}" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search" style="width: 300px"> --%>
<!-- 						<button id="search" class="btn btn-outline-success" type="submit"> -->
<!-- 							<i class="fa-solid fa-magnifying-glass"></i> -->
<!-- 						</button> -->
<!-- 					</form> -->
<!-- 				</ul> -->


<!-- 				<br /> -->
<!-- 				<div id="mateMapData"></div> -->
<!-- 			</div> -->
<!-- 			<div id="map" style="width: 60%; height: 655px;"></div> -->
<!-- 		</div> -->
	<!-- 		</nav> -->
	<!-- 				<div class="row" id="all3"> -->
	<%-- 					<c:forEach items="${climbingMateList}" var="board"> --%>
	<!-- 						<div class="col-md-4"> -->
	<!-- 							<div class="card" style="width: 18rem;"> -->
	<!-- 								<div class="card-body"> -->
	<%-- 									<h5 class="card-title">🌄${board.title}</h5> --%>
	<%-- 									<p class="card-text">작성자: ${board.writer}</p> --%>
	<%-- 									<p class="card-text">작성일자: ${board.inserted}</p> --%>
	<!-- 									<div style="text-align: right"> -->
	<%-- 										<a href="/climbing/mateId/${board.id}" class="btn btn-primary">더보기</a> --%>
	<!-- 									</div> -->
	<!-- 								</div> -->
	<!-- 							</div> -->
	<!-- 						</div> -->
	<%-- 					</c:forEach> --%>
	<!-- 				</div> -->

	<br />

		<div style="display: flex;">
			<div style="flex: 1; margin-left: 70px;" id="mateMapBox">
				<ul style="display: flex; align-items: left;">
					<form action="/climbing/search" class="d-flex" role="search">
						<input id="searchInput" value="${param.search}" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search" style="width: 300px">
						<button id="search" class="btn btn-outline-success" type="submit">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</form>
				</ul>


				<br />
				<div id="mateMapData">

				</div>
			</div>
			<div id="map" style="width: 60%; height: 655px;"></div>
		</div>
	</div>



	<br />
	<br />


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>

	<script type="text/javascript">
		$("#search1").click(function() {
			if ($("#dropdown1").is(":hidden")) {
				$("#dropdown1").slideDown();
			} else {
				$("#dropdown1").slideUp();
			}
		});
	</script>
	<script src="/js/climbing/mateMap.js"></script>

	<!-- ******************************************************************  -->

	<script>
		$(document).ready(function() {
			var latNum = 37.566736219721896;
			var lngNum = 126.9779137163515;

			var mapContainer = document.getElementById('map');
			var mapOption = {
				center : new kakao.maps.LatLng(latNum, lngNum),
				level : 1
			};

			var map = new kakao.maps.Map(mapContainer, mapOption);

			var markerPosition = new kakao.maps.LatLng(latNum, lngNum);
			var marker = new kakao.maps.Marker({
				position : markerPosition
			});

			marker.setMap(map);

		});
	</script>


</body>
</html>
