<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>같이 달릴 사람 !</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<my:navBarRunning></my:navBarRunning>

	<%
	java.util.Date now = new java.util.Date();
	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	String nowString = dateFormat.format(now);
	%>

	<div id="map" style="width: 100%; height: 500px;"></div>


	모일 장소를 찍어주세요 !
	<br />
	<input id="inputValue" type="text" placeholder="예)서울숲" />
	<br />


	<div id="clickLatlng"></div>

	<div class="container-lg">

		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>게시물 작성</h1>
				<form method="post">
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input id="titleInput" class="form-control" type="text" name="title" value="${runningBoard.title }" required />
					</div>

					<%--<div class="mb-3">
						<label for="wirterInput" class="form-label">글쓴이</label>
						<input id="wirterInput" class="form-control" type="text" name="writer" value="${runningBoard.writer }" />
					</div>  --%>

					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea rows="10" id="bodyTextarea" class="form-control" name="body" required>${runningBoard.body }</textarea>
					</div>

					<div class="mb-3">
						<label for="dateInput" class="form-label">장소</label>
						<input required id="addressInput" name="address" type="text" readonly />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">인원수</label>
						<input id="peopleInput" type="number" required class="form-control" name="people" value="${runningBoard.people }" placeholder="숫자를 입력해주세요 ex) 1" />
					</div>

					<div class="mb-3">
						<label for="dateInput" class="form-label">모이는 시간</label>
						<input required id="dateInput" name="time" type="datetime-local" min="<%=nowString%>" />
					</div>

					<input id="LatSubmit" type="text" name="Lat" value="" />
					<input id="LngSubmit" type="text" name="Lng" value="" />
					<input id="addButton" class="btn btn-primary" type="submit" value="등록" />
			</div>
			</form>
		</div>
	</div>
	</div>

	<!-- ******************************************************************  -->
	<sec:authorize access="isAuthenticated()">
	<my:chatBtn></my:chatBtn>
	<script src="/js/running/runningMate.js" charset="UTF-8"></script>
	<script src="/js/chat.js" charset="UTF-8"></script>
	</sec:authorize>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>

	<script src="/js/running/runningAdd.js"></script>
	<script src = "/js/navBar.js"></script>

</body>
</html>