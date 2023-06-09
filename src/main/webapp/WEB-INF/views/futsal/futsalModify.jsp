<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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

	<%
	java.util.Date now = new java.util.Date();
	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	String nowString = dateFormat.format(now);
	%>

	<my:navBarFutsal></my:navBarFutsal>
	<br />
	<br />
	<br />

	<%-- <jsp:useBean id="now" class="java.util.Date"></jsp:useBean> --%>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />

	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />
	<fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />

	<!-- <div id="map" style="width: 100%; height: 500px;"></div> -->


	<!-- 모일 장소를 찍어주세요 ! -->
	<!-- <br />
	<input id="inputValue" type="text" placeholder="예)서울숲" />
	<br /> -->


	<div id="clickLatlng"></div>

	<!-- 본문  -->
	<div class="container-lg">
		<form method="post" enctype="multipart/form-data">
			<div class="row justify-content-center">
				<div class="row">
					<div class="col-12 col-md-8 col-lg-6">
						<br />
						<br />
						<div id="map" style="width: 100%; height: 500px; border-radius: 8px;"></div>
						<h4 style="font-weight: bold;">모일 장소를 찍어주세요 !</h4>
						<div class="row">
							<div class="col-md-5">
								<input id="inputValue" type="text" placeholder="예)서울숲" class="form-control"/>
							</div>
							<div class="col-md-1">
								<button id="searchPlace" class="btn btn-outline-success">
									<i class="fa-solid fa-magnifying-glass"></i>
								</button>
							</div>
						</div>
					</div>				
					<div class="col-12 col-md-8 col-lg-6">
						<input id="addButton" class="btn btn-secondary" type="submit" style="float: right; margin-left: 5px;" value="수정" />
						<h3 style="font-weight: bold;">메이트 구하기</h3>
						<hr />
						<div class="mb-3">
							<div class="row">
								<div class="col-md-2">
									<label for="titleInput" class="form-label">제목</label>
								</div>
								<div class="col-md-10">
									<input id="titleInput" class="form-control" type="text"
										name="title" value="${board.title }" required placeholder="제목을 입력해주세요."/>
								</div>
							</div>
						</div>
						<hr />
	
						<%--<div class="mb-3">
								<label for="wirterInput" class="form-label">글쓴이</label>
								<input id="wirterInput" class="form-control" type="text" name="writer" value="${board.writer }" />
							</div>  --%>
						<input type="hidden" class="form-control" name="writer" value="${board.writer }" />
	
						<div class="mb-3">
							<div class="row">
								<div class="col-md-2">
									<label for="bodyTextarea" class="form-label">본문</label>
								</div>
								<div class="col-md-10">
									<textarea rows="10" id="bodyTextarea" class="form-control" name="body" placeholder="최대 1000자까지 입력해주세요." required>${board.body } </textarea>
								</div>
							</div>
						</div>
	
						<hr />
	
						<div class="mb-3">
							<div class="row">
								<div class="col-md-2">
									<label for="dateInput" class="form-label">장소</label>
								</div>
								<div class="col-md-10">
									<input required id="addressInput" name="address" type="text"
										readonly class="form-control" value="${board.address }" />
								</div>
							</div>
						</div>
	
						<hr />
	
						<div class="mb-3">
							<div class="row">
								<div class="col-md-2">
									<label for="" class="form-label">인원수</label>
								</div>
								<div class="col-md-10">
									<input id="peopleInput" type="number" required
										class="form-control" name="people"
										value="${board.people }" placeholder="숫자를 입력해주세요 ex) 1" />
								</div>
							</div>
						</div>
	
						<hr />
	
						<div class="mb-3">
							<div class="row">
								<div class="col-md-2">
									<label for="dateInput" class="form-label">모이는시간</label>
								</div>
								<div class="col-md-10">
									<input class="form-control" required id="dateInput" name="time" type="datetime-local" min="<%=nowString%>" value="${board.time }" />
								</div>
							</div>
						</div>
						
						<hr />
	
						<div class="mb-3">
							<input type="hidden" readonly class="form-control" value="${board.inserted }" />
						</div>
	
						<input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" /> 
						<input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" />
	
					</div>
				</div>
			</div>
		</form>
	</div>

		<%-- <div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<form method="post" enctype="multipart/form-data">
					<div class="d-flex">
						<div class="me-auto">
							<h1>
								<span id="boardIdText"> ${board.id } </span>
								번게시물
							</h1>
						</div>
					</div>

					<div>
						<div class="mb-3">
							<label for="" class="form-label">제목</label>
							<input type="text" name="title" class="form-control" value="${board.title }" />
						</div>

						<div class="mb-3">
							<label for="" class="form-label">본문</label>
							<textarea name="body" class="form-control" rows="10">${board.body }</textarea>
						</div>

						<div class="mb-3">
							<label for="" class="form-label">작성자</label>
							<input name="writer" id="writerText" type="text" readonly class="form-control" value="${board.writer }" />
						</div>

						<div class="mb-3">
							<label for="dateInput" class="form-label">장소</label>
							<input required id="addressInput" readonly name="address" type="text" value="${board.address }" />
						</div>

						<div class="mb-3">
							<label for="dateInput" class="form-label">모이는 시간</label>
							<input required id="dateInput" name="time" type="datetime-local" />
						</div>

						<div class="mb-3">
							<label for="" class="form-label">인원수</label>
							<input id="peopleInput" type="number" required class="form-control" name="people" value="${climbingMate.people }" placeholder="숫자를 입력해주세요 ex) 1" />
						</div>

						<div class="mb-3">
							<label for="" class="form-label">작성일시</label>
							<input id="insertedText" type="text" readonly class="form-control" value="${board.inserted }" />
						</div>

						<input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" />
						<input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" />



						<label for="" class="form-label">신청한 사람 </label>
						<c:forEach items="${members}" var="member">
							<c:if test="${board.id eq member.boardId}">
								<div class="mb-3">
									<input type="text" readonly class="form-control" value="${member.memberId}" />
								</div>
							</c:if>
						</c:forEach>
						<div class="mb-3">
							<input class="btn btn-secondary" type="submit" value="수정" />
						</div>
				</form>
			</div>
		</div>
	</div> --%>


	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3f405ca1718e37ea86f8585e0ca94ef5
&libraries=services"></script>
	<script src="/js/futsal/futsalAdd.js"></script>
</body>
</html>