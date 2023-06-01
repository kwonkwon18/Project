<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

	<h1>내가 올린 메이트</h1>
	<div class="row row-cols-1 row-cols-md-3 g-4">
		<c:forEach items="${runningBoards}" var="board" varStatus="status">
			<div class="col">
				<div class="card">
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
								<input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
							</div>

							<div class="mb-3">
								<label for="" class="form-label">참석자</label>
								<c:forEach items="${members}" var="members">
								<c:if test="${members.boardId eq board.id}">
									<input id="timeText" type="text" class="form-control" value="${members.memberId }" readonly />
								</c:if>
								</c:forEach>
							</div>


							<button type="button" onclick="location.href='/running/id/${board.id}' ">내 게시물 상세 보기</button>


							<input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" />
							<input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" />

							<c:set var="latNum" value="${board.lat}" />
							<c:set var="lngNum" value="${board.lng}" />

						</div>
					</div>
				</div>
			</div>

			<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
			<script>
				var latNum = ${latNum};
				var lngNum = ${lngNum};

				var mapContainer${status.index + 1} = document.getElementById('map${status.index + 1}');
				var mapOption${status.index + 1} = {
					center : new kakao.maps.LatLng(latNum, lngNum),
					level : 3
				};

				var map${status.index + 1} = new kakao.maps.Map(mapContainer${status.index + 1}, mapOption${status.index + 1});

				var markerPosition${status.index + 1} = new kakao.maps.LatLng(latNum, lngNum);
				
				var marker${status.index + 1} = new kakao.maps.Marker({
					position : markerPosition${status.index + 1}
				});

				marker${status.index + 1}.setMap(map${status.index + 1});
			</script>
		</c:forEach>
	</div>




	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>
