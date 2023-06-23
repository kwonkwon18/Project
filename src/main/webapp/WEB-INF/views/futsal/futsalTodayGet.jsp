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

	<my:navBarFutsal></my:navBarFutsal>
	
	<br />
	<br />
	<br />
	<br />
	
	<!-- toast -->
	<div class="toast-container position-fixed top-0 start-50 translate-middle-x p-3">
		<div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
			<div class="d-flex">
				<div class="toast-body"></div>
				<button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
		</div>
	</div>

	<div class="container-lg">

		<div class="row justify-content-center">

			<div class="col-12 col-md-8 col-lg-6">
				<div class="d-flex">
					<div class="me-auto">
					</div>
				</div>

				<div class="card">
					<div class="m-3" style="margin-left: 20px; margin-right: 20px;"  >
						<h6>오늘의 풋살</h6>
						<h3 class="card-title mb-2">${board.title }</h3>
						<h6 class="card-subtitle mb-2 text-body-secondary">${board.writer } &nbsp;&nbsp;  ${board.inserted }</h6>
						<hr />
					</div>
					<input id="boardIdText" type="hidden" value="${board.id }"/>


					<!-- 그림 파일 출력  -->
					<div>
						<c:forEach items="${board.fileName }" var="fileName">
							<div class="m-3">
								<!-- http://localhost:8080/image/게시물번호/fileName  -->
								<!-- aws로 올리면 위 만큼이 aws 주소가 됨   -->
								<img class="img-fluid img-thumbnail" src="${bucketUrl }/futsalToday/${board.id }/${fileName}" alt="" height="300" width="300" />
							</div>
						</c:forEach>
						<div class="m-3">
							<p class="card-text">${board.body }</p>
						</div>
					</div>
					<div class="m-3">
						<div class="row justify-content-end">
							<c:if test="${board.userId eq sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name }">
							<div class="col-md-2">
								<a class="btn btn-secondary" href="/futsal/futsalTodayModify/${board.id }">수정</a>
							</div>
							<div class="col-md-2">
								<button id="removeButton" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">삭제</button>
							</div>
							</c:if>
						</div>
						<hr />
					</div>


					<!-- 보안 넣어줘야함   -->
					<c:if test="${board.userId eq sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name }">
						<div class="d-none">
							<form action="/futsal/todayRemove" method="post" id="removeForm">
								<input id="inputId" type="text" name="id" value="${board.id }" />
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
					</c:if>
					
					<div id="commentContainer">
						<h5 class="m-3">
							<i class="fa-solid fa-comments"></i>
						</h5>
						<sec:authorize access="isAuthenticated()">
							<div class="m-3" id="addCommentContainer">
								

								<div class="input-group">
									<div class="form-floating">
										<textarea style="height: 97px" placeholder="댓글을 남겨주세요" class="form-control" id="commentTextArea"></textarea>
										<label for="floatingTextarea">댓글을 남겨주세요</label>
									</div>
									<button class="btn btn-outline-primary" id="sendCommentBtn">
										<i class="fa-regular fa-paper-plane"></i>
									</button>
								</div>
							</div>
						</sec:authorize>



						<ul class="list-group" id="commentListContainer">


						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="name" var="userId" />
	
		<!-- 댓글 삭제 Modal -->
		<div class="modal fade" id="deleteCommentConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5">댓글 삭제 확인</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">삭제 하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
						<button id="deleteCommentModalButton" data-bs-dismiss="modal" type="submit" class="btn btn-danger">삭제</button>
					</div>
				</div>
			</div>
		</div>
	
		<%-- 댓글 수정 모달 --%>
		<div class="modal fade" id="commentUpdateModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5">댓글 수정</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div id="updateCommentContainer">
							<input type="hidden" id="commentUpdateIdInput" />
							<textarea class="form-control" id="commentUpdateTextArea"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
						<button type="button" class="btn btn-primary" id="updateCommentBtn" data-bs-dismiss="modal">수정</button>
					</div>
				</div>
			</div>
		</div>
		
	</sec:authorize>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
		
		<script src="/js/futsal/futsalLike.js"></script>
		<script src="/js/futsal/futsalComment.js"></script>
</body>
</html>