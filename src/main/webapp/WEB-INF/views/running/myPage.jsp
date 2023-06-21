<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>

	<my:navBarRunning></my:navBarRunning>
	<!-- 	<button id="alarmList">알람 목록 보기</button> -->
	<!-- <div id="HostAlarm"></div>
	<div id="MemberAlarm"></div> -->
	<div class="container-lg" style="border: 1px solid hsla(220, 9%, 46%, .3); padding: 0; border-radius: 0.5rem; margin-bottom: 20px; max-width: 1200px; margin-top: 70px;" id="myInfo">
		<div style="justify-content: center; padding-left: 24px; padding-right: 24px; padding-bottom: 32px; padding-top: 32px; align-items: center; display: flex;">
			<div class="row" style="width: 100%;">
				<div style="flex: 0 0 auto; width: 90%; font-size: 2.5rem; line-height: 2.5rem;">${MyNickName }</div>
				<button type="button" class="btn" style="flex: 0 0 auto; width: 10%; font-weight: 500; font-size: .875rem; line-height: 1.25rem; padding-top: 0.5rem; padding-bottom: 0.5rem; border: 1px solid hsla(220, 9%, 46%, .3); border-radius: 0.375rem; justify-content: center; display: inline-flex;" onclick="location.href='/my_page'">나의 계정</button>
			</div>
		</div>
		<div style="column-gap: 2rem; border-top: 1px solid hsla(220, 9%, 46%, .3); display: flex; position: relative;">
			<button id="runningCurrentListBtn" class="btn" type="button" style="font-weight: 500; font-size: .875rem; line-height: 1.25rem; color: rgb(107 114 128/ 1); justify-content: center; display: inline-flex; text-align: center; padding: 1rem; margin-left: 20px; border: 0;">최근 게시물</button>
			<button id="runningMyPostListBtn" class="btn" type="button" style="font-weight: 500; font-size: .875rem; line-height: 1.25rem; color: rgb(107 114 128/ 1); justify-content: center; display: inline-flex; text-align: center; padding: 1rem; border: 0;">내가 올린 게시물</button>
			<button id="runningYourPostListBtn" class="btn" type="button" style="font-weight: 500; font-size: .875rem; line-height: 1.25rem; color: rgb(107 114 128/ 1); justify-content: center; display: inline-flex; text-align: center; padding: 1rem; border: 0;">내가 신청한 게시물</button>
		</div>
	</div>

	<!-- 	<div class="container-lg"> -->
	<!-- 		<div class="row row-cols-1 row-cols-md-3 g-4"> -->
	<%-- 			<c:forEach items="${totalMyData}" var="board" varStatus="status"> --%>

	<%-- 				<c:if test="${MyNickName eq board.writer}"> --%>
	<!-- 					<div class="col"> -->
	<!-- 						<div class="card text-white bg-primary mb-3" style="max-width: 21rem;"> -->
	<!-- 							<h3>내가 올린 게시물</h3> -->
	<!-- 							<img src="..." class="card-img-top" alt="..."> -->
	<!-- 							<div class="card-body"> -->
	<!-- 								<h5 class="card-title"> -->
	<%-- 									<span id="boardIdText${status.index + 1}">${board.id}</span> --%>
	<!-- 									번게시물 -->
	<!-- 								</h5> -->
	<%-- 								<div id="map${status.index + 1}" class="map-container" style="width: 300px; height: 300px;"></div> --%>
	<!-- 								<div> -->
	<!-- 									<div class="mb-3"> -->
	<!-- 										<label for="" class="form-label">제목</label> -->
	<%-- 										<input type="text" class="form-control" value="${board.title}" readonly /> --%>
	<!-- 									</div> -->
	<!-- 									<div class="mb-3"> -->
	<!-- 										<label for="" class="form-label">작성자</label> -->
	<%-- 										<input id="writerData${status.index + 1}" type="text" class="form-control" value="${board.writer}" readonly /> --%>
	<!-- 									</div> -->
	<!-- 									<div class="mb-3"> -->
	<!-- 										<label for="" class="form-label">모임시간</label> -->
	<%-- 										<input id="timeText" type="text" class="form-control" value="${board.time}" readonly /> --%>
	<!-- 									</div> -->

	<%-- 									<button type="button" onclick="location.href='/running/id/${board.id}'">내 게시물 상세 보기</button> --%>
	<%-- 									<input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" /> --%>
	<%-- 									<input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" /> --%>
	<%-- 									<c:set var="latNum" value="${board.lat}" /> --%>
	<%-- 									<c:set var="lngNum" value="${board.lng}" /> --%>
	<!-- 								</div> -->
	<!-- 							</div> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<%-- 				</c:if> --%>
	<%-- 			</c:forEach> --%>
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- 	<hr /> -->
	<!-- 	<h1>내가 신청한 게시물</h1> -->
	<!-- 	<div class="container-lg"> -->
	<!-- 		<div class="row row-cols-1 row-cols-md-3 g-4"> -->
	<%-- 			<c:forEach items="${totalMyData}" var="board" varStatus="status"> --%>

	<%-- 				<c:if test="${MyNickName eq board.memberId}"> --%>
	<!-- 					<div class="col"> -->
	<!-- 						<div class="card text-white bg-success mb-3" style="max-width: 21rem;"> -->
	<!-- 							<h3>내가 신청한 게시물</h3> -->
	<!-- 							<img src="..." class="card-img-top" alt="..."> -->
	<!-- 							<div class="card-body"> -->
	<!-- 								<h5 class="card-title"> -->
	<%-- 									<span id="boardIdText${status.index + 1}">${board.id}</span> --%>
	<!-- 									번게시물 -->
	<!-- 								</h5> -->
	<%-- 								<div id="map${status.index + 1}" class="map-container" style="width: 300px; height: 300px;"></div> --%>
	<!-- 								<div> -->
	<!-- 									<div class="mb-3"> -->
	<!-- 										<label for="" class="form-label">제목</label> -->
	<%-- 										<input type="text" class="form-control" value="${board.title}" readonly /> --%>
	<!-- 									</div> -->
	<!-- 									<div class="mb-3"> -->
	<!-- 										<label for="" class="form-label">작성자</label> -->
	<%-- 										<input id="writerData${status.index + 1}" type="text" class="form-control" value="${board.writer}" readonly /> --%>
	<!-- 									</div> -->
	<!-- 									<div class="mb-3"> -->
	<!-- 										<label for="" class="form-label">모임시간</label> -->
	<%-- 										<input id="timeText" type="text" class="form-control" value="${board.time}" readonly /> --%>
	<!-- 									</div> -->

	<%-- 									<button type="button" onclick="location.href='/running/id/${board.id}'">신청한 상태 보기</button> --%>
	<%-- 									<input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" /> --%>
	<%-- 									<input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" /> --%>
	<%-- 									<c:set var="latNum" value="${board.lat}" /> --%>
	<%-- 									<c:set var="lngNum" value="${board.lng}" /> --%>
	<!-- 								</div> -->
	<!-- 							</div> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<%-- 				</c:if> --%>
	<%-- 			</c:forEach> --%>
	<!-- 		</div> -->
	<!-- 	</div> -->
	<sec:authorize access="isAuthenticated()">
		<my:chatBtn></my:chatBtn>
		<script src="/js/groupChat.js"></script>
		<script src="/js/chat.js" charset="UTF-8"></script>
	</sec:authorize>
	<script src="/js/running/runningNavBar.js"></script>
	<script src="/js/running/myPage.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
</body>
</html>