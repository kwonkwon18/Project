<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<my:navBar></my:navBar>
	<h1>마이페이지</h1>

	<div class="row row-cols-1 row-cols-md-3 g-4">
		<c:forEach items="${totalMyData}" var="board" varStatus="status">

			<c:if test="${MyNickName eq board.writer}">
				<c:if test="${status.index % 3 == 0}">
	</div>

	<div class="row row-cols-1 row-cols-md-3 g-4">
		</c:if>
		<div class="col">
			<div class="card text-white bg-primary mb-3" style="max-width: 21rem;">
				<h3>내가 올린 게시물</h3>
				<img src="..." class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">
						<span id="boardIdText${status.index + 1}">${board.id}</span>
						번게시물
					</h5>
					<div id="map${status.index + 1}" class="map-container" style="width: 300px; height: 300px;"></div>
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
							<input id="timeText" type="text" class="form-control" value="${board.time}" readonly />
						</div>

						<button type="button" onclick="location.href='/running/id/${board.id}'">내 게시물 상세 보기</button>
						<input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" />
						<input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" />
						<c:set var="latNum" value="${board.lat}" />
						<c:set var="lngNum" value="${board.lng}" />
					</div>
				</div>
			</div>
		</div>
		</c:if>


		<c:if test="${MyNickName eq board.memberId}">
			<c:if test="${status.index % 3 == 0}">
	</div>
	<div class="row row-cols-1 row-cols-md-3 g-4">
		</c:if>
		<div class="col">
			<div class="card text-white bg-success mb-3" style="max-width: 21rem;">
				<h3>내가 신청한 게시물</h3>
				<img src="..." class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">
						<span id="boardIdText${status.index + 1}">${board.id}</span>
						번게시물
					</h5>
					<div id="map${status.index + 1}" class="map-container" style="width: 300px; height: 300px;"></div>
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
							<input id="timeText" type="text" class="form-control" value="${board.time}" readonly />
						</div>

						<button type="button" onclick="location.href='/running/id/${board.id}'">신청한 상태 보기</button>
						<input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" />
						<input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" />
						<c:set var="latNum" value="${board.lat}" />
						<c:set var="lngNum" value="${board.lng}" />
					</div>
				</div>
			</div>
		</div>
		</c:if>
		</c:forEach>
	</div>

	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3f405ca1718e37ea86f8585e0ca94ef5
&libraries=services"></script>
	<script>
		<c:forEach items="${totalMyData}" var="board" varStatus="status">
			var latNum${status.index + 1} = ${board.lat};
			var lngNum${status.index + 1} = ${board.lng};
			var mapContainer${status.index + 1} = document.getElementById('map${status.index + 1}');
			var mapOption${status.index + 1} = {
				center: new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1}),
				level: 3
			};
			var map${status.index + 1} = new kakao.maps.Map(mapContainer${status.index + 1}, mapOption${status.index + 1});
			var markerPosition${status.index + 1} = new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1});
			var marker${status.index + 1} = new kakao.maps.Marker({
				position: markerPosition${status.index + 1}
			});
			marker${status.index + 1}.setMap(map${status.index + 1});
		</c:forEach>
	</script>
</body>
</html>
