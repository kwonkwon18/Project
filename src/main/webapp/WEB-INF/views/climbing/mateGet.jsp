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
	<my:navBarclimbing>
	</my:navBarclimbing>

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
							<span id="boardIdText"> ${board.id } </span> 번게시물${formattedDate }
						</h1>
					</div>
				</div>

				<div>
					<div class="mb-3">
						<label for="" class="form-label">제목</label> <input type="text" class="form-control" value="${board.title }" readonly />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">본문</label>
						<textarea class="form-control" readonly rows="10">${board.body }</textarea>
					</div>

					<div class="mb-3">
						<label for="" class="form-label">작성자</label> <input id="writerText" type="text" class="form-control" value="${board.writer }" readonly />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">모임시간</label> <input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">작성일시</label> <input id="insertedText" type="text" readonly class="form-control" value="${board.inserted }" />
					</div>
					<input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" /> <input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" /> <label for="" class="form-label">신청한 사람 </label>
					<c:forEach items="${members}" var="member">
						<!-- 보드아이디와 멤버의 보드아이디가 같은 경우 -->
						<!-- 멤버의 아이디와 작성자가 같은 경우는 해주면 안됨  -->
						<c:if test="${board.id eq member.boardId && board.writer ne member.memberId}">
							<div class="mb-3">
								<input type="text" readonly class="form-control" value="${member.memberId}" />
							</div>
						</c:if>
					</c:forEach>

					<div>
						<a class="btn btn-secondary" href="/climbing/mateModify/${board.id }">수정</a>
						<button data-bs-toggle="modal" data-bs-target="#deleteConfirmModal" class="btn btn-danger">삭제</button>
					</div>


					<div class="d-none">
						<form action="/climbing/mateRemove" method="post" id="removeForm">
							<input type="text" name="id" value="${board.id }" />
						</form>
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
								<button>마감된 러닝</button>
							</c:if>

							<c:if test="${openDate > nowDate }">

								<c:choose>
									<c:when test="${isMember}">
										<button id="rejectPartyBtn">취소하기🙅‍♀️🙅‍♂️🙅‍♀️🙅‍♂️></button>
									</c:when>
									<c:otherwise>
										<c:if test="${board.people > board.currentNum }">
											<button id="joinPartyBtn">참여하기🙋‍♂️🙋‍♀️🙋‍♂️🙋‍♀</button>
										</c:if>
									</c:otherwise>
								</c:choose>

								<c:if test="${board.people <= board.currentNum }">
									<button>마감</button>
								</c:if>
							</c:if>


							<input type="text" id="totalPeople" value="${board.people }" />
							<input type="text" id="currentPeopleHidden" value="${board.currentNum }" />
							<p id="currentPeople"></p>
							<%-- <input type="text" id = "currentPeopleHidden" value = "${board.currentNum }"  /> --%>
						</c:if>

						<c:if test="${isUser}">
							<button>내가 올린 게시물</button>
						</c:if>
					</div>





					<!-- **************************************************  -->

					<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
					<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
					<script src="/js/climbing/climbingGet.js"></script>
					<script src="/js/navBarClimbing.js"></script>
</body>
</html>