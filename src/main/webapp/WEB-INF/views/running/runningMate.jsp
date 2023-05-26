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
	확인용
	<div class="d-flex">
		<div class="row">
			<c:forEach items="${runningMates}" var="board" varStatus="status">
				<div class="col-md-4">
					<div class="card" style="width: 18rem;">
						<img src="..." class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								<div class="me-auto">
									<h1>
										<span id="boardIdText">${board.id}</span>
										번게시물
									</h1>
								</div>
							</h5>
							<div>
								<div id="map${status.index + 1}" class="map-container" style="width: 300px; height: 300px;"></div>
								<div class="mb-3">
									<label for="" class="form-label">제목</label>
									<input type="text" class="form-control" value="${board.title}" readonly />
								</div>
								<div class="mb-3">
									<label for="" class="form-label">작성자</label>
									<input id="writerData" type="text" class="form-control" value="${board.writer}" readonly />
								</div>
								<!-- 본문 내용 -->
								<%-- <label for="" class="form-label">같이 달릴 사람 🏃‍♀️🏃‍♂️🏃‍♀️🏃‍♂️</label>
								<c:forEach items="${members}" var="member">
									<c:if test="${board.id eq member.boardId}">
										<div class="mb-3">
											<input type="text" readonly class="form-control" value="${member.memberId}" />
										</div>
									</c:if>
								</c:forEach> --%>
								<div class="mb-3">
									<label for="" class="form-label">작성일시</label>
									<input type="text" readonly class="form-control" value="${board.inserted}" />
								</div>
								<input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" />
								<input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" />
								<a href="/running/id/${board.id}" class="btn btn-primary">Go somewhere</a>
								<div>모집인원 : ${board.people } / 현재인원 : ${board.currentNum }</div>

								<c:if test="${board.people > board.currentNum }">
									<button id="joinPartyBtn${status.index + 1}">나도 껴조 ~</button>
									<p id="message${status.index + 1}"></p>
								</c:if>

								<c:if test="${board.people <= board.currentNum }">
									<button>마감됐어..</button>
								</c:if>

								<input type="hidden" id="totalPeople" value="${board.people }" />
								<input type="hidden" id="currentPeopleHidden" value="${board.currentNum }" />
								<p id="currentPeople"></p>


							</div>
						</div>
					</div>
				</div>
				<c:set var="latNum" value="${board.lat}" />
				<c:set var="lngNum" value="${board.lng}" />
			</c:forEach>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
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
			level : 3
		};

		var map${status.index + 1} = new kakao.maps.Map(mapContainer${status.index + 1}, mapOption${status.index + 1});

		var markerPosition${status.index + 1} = new kakao.maps.LatLng(latNum${status.index + 1}, lngNum${status.index + 1});
		var marker${status.index + 1} = new kakao.maps.Marker({
			position : markerPosition${status.index + 1}
		});

		marker${status.index + 1}.setMap(map${status.index + 1});

		// 버튼 클릭 이벤트 핸들러
		$('#joinPartyBtn${status.index + 1}').click(function() {
			const data = {
				boardId: "${board.id}",
				userId: "${board.writer}"
			};

			$.ajax("/running/joinParty", {
				method: "post",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function() {
					alert("신청되었습니다. ");
					$("#message${status.index + 1}").text("성공");

				},
				error: function() {
					alert("신청 실패 ");
					$("#message${status.index + 1}").text("실패");
				}, 
				
				complete : function(){
			history.scrollRestoration = "auto";
			
			location.replace("/running/id/${board.id}");
					
				}
			})
		});
	</c:forEach>
});
</script>


	<script src="/js/running/runningMate.js"></script>
</body>
</html>