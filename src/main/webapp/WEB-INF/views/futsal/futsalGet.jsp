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

<my:navBarFutsal></my:navBarFutsal>
<br />
<br />
<br />

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
	<fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />

	<fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />
	<fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />

	<!-- 본문  -->

	<div class="container-lg">
		<div style="display: flex; justify-content: center;">
			<div id="map" style=" width: 1070px; height: 400px; border-radius: 8px;"></div>
		</div>
		<div class="row justify-content-center">
			<div>
				<div class="d-flex">
					<div class="me-auto">
						<h1>
							<input id="boardIdText" type="hidden" value="${board.id }" />
							<input type="hidden" value="${formattedDate }" />
						</h1>
					</div>
				</div>
				<div>
				
				<div class="row justify-content-center" >
					<div class="col-md-6" >
						<div class="mb-3" >
							<!-- <label for="" class="form-label">제목</label> -->
							<div class="card" style="height: 400px;" >
							  <div class="card-body" >
								<h4 style="font-weight: bold;">M A T E 구하기</h4>
								<hr />
							    <h5 class="card-title" style="font-weight: bold;" >${board.title }</h5>
							    <p class="card-text">${board.body }</p>
							  </div>
							</div>
							
							<%-- <input type="text" class="form-control" value="${board.title }" readonly /> --%>
						</div>
	
	
						<%-- <div class="mb-3">
							<label for="" class="form-label">본문</label>
							<textarea class="form-control" readonly rows="10">${board.body }</textarea>
						</div> --%>
					</div>
					<div class="col-md-4">
						<div class="card" style="height: 400px;" >
							<br />
							<div class="mb-3">
								<div class="row">
									<div class="col-md-3">
										<h5>작성자</h5>
									</div>
									<div class="col-md-8">
										<h5 style="font-weight: bold;">${board.writer }</h5>
									</div>
								</div>
							</div>
		
							<div class="mb-3">
								<div class="row">
									<div class="col-md-3">
										<h5>모임시간</h5>
									</div>
									<div class="col-md-8">
										<h5 style="font-weight: bold;">${board.time }</h5>
									</div>
								</div>
							</div>
		
							<div class="mb-3">
								<div class="row">
									<div class="col-md-3">
										<h5>작성일시</h5>
									</div>
									<div class="col-md-8">
										<h5 style="font-weight: bold;">${board.inserted }</h5>
									</div>
								</div>
								<hr />
							</div>
							<input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" />
							<input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" />
		
		
		
							<div class="mb-3">
								<div class="row">
									<div class="col-md-3">
										<h5>M A T E</h5>
									</div>
									<div class="col-md-8">
										<c:forEach items="${members}" var="member">
											<c:if test="${board.id eq member.boardId}">
												<div class="mb-3">
													<h5 style="font-weight: bold;">${member.memberId}</h5>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
					<div>
						<a class="btn btn-secondary" href="/futsal/futsalModify/${board.id }">수정</a>
						<button data-bs-toggle="modal" data-bs-target="#deleteConfirmModal" class="btn btn-danger">삭제</button>
					</div>


					<div class="d-none">
						<form action="/futsal/futsalRemove" method="post" id="removeForm">
							<input type="text" name="id" value="${board.id }" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>




					<!-- 본인 게시물 확인 -->
					<c:set var="isUser" value="false" />
					<c:forEach items="${memberList}" var="memberList">
						<c:if test="${memberList.nickName eq board.writer}">
							<c:set var="isUser" value="true" />
						</c:if>
					</c:forEach>

					<c:forEach items="${memberList}" var="memberList">
						<c:set var="memberNickName" value="${memberList.nickName}" />
					</c:forEach>


					<!-- 본인 신청 확인  -->
					<%-- <c:set var="currentUserId" value="${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}" /> --%>
					<c:set var="isMember" value="false" />
					<c:forEach items="${members}" var="members">
						<c:if test="${members.memberId eq memberNickName}">
							<c:set var="isMember" value="true" />
						</c:if>
					</c:forEach>



					<div>
						<c:if test="${!isUser}">
							<c:if test="${openDate < nowDate }">
								<button>마감된 경기</button>
							</c:if>

							<div class="row">
								<c:if test="${openDate > nowDate }">
	
									<c:choose>
										<c:when test="${isMember}">
											<div class="col-md-2">
												<button class="btn btn-danger" id="joinPartyBtn">취소하기🙅‍♀️🙅‍♂️🙅‍♀️🙅‍♂️></button>
											</div>
										</c:when>
										<c:otherwise>
											<c:if test="${board.people > board.currentNum }">
												<div class="col-md-2">
													<button class="btn btn-primary" id="joinPartyBtn">참여하기🙋‍♂️🙋‍♀️🙋‍♂️🙋‍♀</button>️
												</div>
	               							</c:if>
										</c:otherwise>
									</c:choose>
	
									<c:if test="${board.people <= board.currentNum }">
										<div class="col-md-2">
											<button>마감</button>
										</div>
									</c:if>
								</c:if>
	
								<div class="col-md-1">
									<input class="form-control" type="text" id="totalPeople" value="${board.people }" readonly />
								</div>
								<div class="col-md-1">
									<input class="form-control" type="text" id="currentPeopleHidden" value="${board.currentNum }" readonly />
								</div>
							</div>
							<p id="currentPeople"></p>
							<%-- <input type="text" id = "currentPeopleHidden" value = "${board.currentNum }"  /> --%>
						</c:if>

						<c:if test="${isUser}">
							<button>내가 올린 게시물</button>
						</c:if>
					</div>

					<!-- Modal -->
					<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
									<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								</div>
								<div class="modal-body">삭제 하시겠습니까?</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
									<button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
								</div>
							</div>
						</div>
					</div>




					<!-- **************************************************  -->

					<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3f405ca1718e37ea86f8585e0ca94ef5
&libraries=services"></script>
					<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
					<script src="/js/futsal/futsalGet.js"></script>
</body>
</html>