<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



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

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />

	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />
	<fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />

	<!-- 본문  -->

	<div class="container-lg">

		<div class="row justify-content-center">
			<div id="map" style="width: 500px; height: 500x;"></div>
			<div class="col-12 col-md-8 col-lg-6">
				<div class="d-flex">
					<div class="me-auto">
						<h1>
							<span id="boardIdText"> ${board.id } </span>
							번게시물${formattedDate }
						</h1>
					</div>
				</div>

				<div>
					<div class="mb-3">
						<label for="" class="form-label">제목</label>
						<input type="text" class="form-control" value="${board.title }" readonly />
					</div>


					<div class="mb-3">
						<label for="" class="form-label">본문</label>
						<textarea class="form-control" readonly rows="10">${board.body }</textarea>
					</div>

					<div class="mb-3">
						<label for="" class="form-label">작성자</label>
						<input id="writerText" type="text" class="form-control" value="${board.writer }" readonly />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">모임시간</label>
						<input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">작성일시</label>
						<input id="insertedText" type="text" readonly class="form-control" value="${board.inserted }" />
					</div>
					<input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" />
					<input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" />


					<!--  -->
					<label for="" class="form-label">신청한 사람 </label>
					<c:forEach items="${members}" var="member">
						<c:if test="${board.id eq member.boardId}">
							<div class="mb-3">
								<input type="text" readonly class="form-control" value="${member.memberId}" />
							</div>
						</c:if>
					</c:forEach>
					<!--  -->



				</div>
			</div>
		</div>




		<div>

			<c:if test="${openDate < nowDate }">
				<button>마감된 러닝</button>
			</c:if>

			<c:if test="${openDate > nowDate }">

				<c:set var="currentUserId" value="${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}" />
				<c:set var="isMember" value="false" />

				<c:forEach items="${members}" var="member">
					<c:if test="${currentUserId eq member.memberId}">
						<c:set var="isMember" value="true" />
					</c:if>
				</c:forEach>

				<c:choose>
					<c:when test="${isMember}">
						<button id="rejectPartyBtn">취소하기🙅‍♀️🙅‍♂️🙅‍♀️🙅‍♂️></button>
					</c:when>
					<c:otherwise>
						<c:if test="${board.people > board.currentNum }">
							<button id="joinPartyBtn">참여하기🙋‍♂️🙋‍♀️🙋‍♂️🙋‍♀</button>️
					</c:if>
					</c:otherwise>
				</c:choose>




				<%-- 			<c:if test="${board.people > board.currentNum }">
				<button id="joinPartyBtn">참여하기1</button>
				${currentUserId }
			</c:if>






			<c:if test="${board.people <= board.currentNum }">
				<button id="rejectPartyBtn">취소하기2</button>
			</c:if> --%>




				<c:if test="${board.people <= board.currentNum }">
					<button>마감</button>
				</c:if>








				<input type="text" id="totalPeople" value="${board.people }" />
				<input type="text" id="currentPeopleHidden" value="${board.currentNum }" />
				<p id="currentPeople"></p>
				<%-- <input type="text" id = "currentPeopleHidden" value = "${board.currentNum }"  /> --%>
			</c:if>
		</div>



		<!-- **************************************************  -->


		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
		<script>
			var latNum = $("#LatSubmit").val();
			var lngNum = $("#LngSubmit").val();
			console.log("latNum:", latNum);
			console.log("lngNum:", lngNum);

			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			mapOption = {
				center : new kakao.maps.LatLng(latNum, lngNum), // 지도의 중심좌표
				level : 3
			// 지도의 확대 레벨
			};

			var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

			// 마커가 표시될 위치입니다 
			var markerPosition = new kakao.maps.LatLng(latNum, lngNum);

			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
				position : markerPosition
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);

			// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
			// marker.setMap(null);
		</script>



		<script src="/js/running/runningParty.js"></script>
</body>
</html>