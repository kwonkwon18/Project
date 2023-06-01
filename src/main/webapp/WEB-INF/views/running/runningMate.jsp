<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<div class="row row-cols-1 row-cols-md-3 g-4">
		<c:forEach items="${runningMates}" var="board" varStatus="status">
			<div class="col">
				<div class="card">
					<img src="..." class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title">
							<span id="boardIdText${status.index + 1}">${board.id}</span>
							번게시물
						</h5>
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
							${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}

							<c:set var="isMember" value="false" />
							<c:forEach items="${memberList}" var="memberList">
								<c:if test="${memberList.nickName eq board.writer}">
									<c:set var="isMember" value="true" />
								</c:if>
							</c:forEach>

							<c:if test="${isMember}">
								<button type="button" onclick="location.href='/running/id/${board.id}' ">지원 사항 상세보기</button>
							</c:if>

							<c:if test="${not isMember}">
								<button data-board-userId="${board.writer }" data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="listUpButton${status.index + 1}" class="listUpButton btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">더보기</button>
							</c:if>

						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">모달 제목</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" id="resMate"></div>
			</div>
		</div>
	</div>


	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
	<script src="/js/running/runningMate.js"></script>

</body>
</html>