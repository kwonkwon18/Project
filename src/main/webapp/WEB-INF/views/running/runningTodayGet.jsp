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
	<div>
		<my:navBarRunning></my:navBarRunning>
	</div>
	<br />
	<br />
	<br />
	<br />




	<!-- toast  -->
	<div class="toast-container top-0 start-50 translate-middle-x">
		<div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
			<div class="toast-header">
				<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
			<div class="toast-body"></div>
		</div>
	</div>


	<div class="container-lg">

		<div class="row justify-content-center">

			<div class="col-12 col-md-8 col-lg-6">
				<div class="d-flex">
					<div class="me-auto"></div>
				</div>

				<!-- 좋아요  -->
				<h1>

					<span id="likeIcon">
						<c:if test="${board.liked }">
							<i class="fa-solid fa-heart"></i>
						</c:if>
						<c:if test="${not board.liked }">
							<i class="fa-regular fa-heart"></i>
						</c:if>

					</span>
					<span id="likeNumber"> ${board.likeCount } </span>

				</h1>


				<div>
					<div class="mb-3">
						<label for="" class="form-label">제목</label>
						<input type="text" class="form-control" value="${board.title }" readonly />
					</div>
					<h1>
						<span style="display: none;" id="boardIdText"> ${board.title } </span>
						<span> ${board.title } </span>

					</h1>


					<!-- 그림 파일 출력  -->
					<div class="mb-3">
						<c:forEach items="${board.fileName }" var="fileName">
							<div>
								<!-- http://localhost:8080/image/게시물번호/fileName  -->
								<!-- aws로 올리면 위 만큼이 aws 주소가 됨   -->
								<img class="img-fluid img-thumbnail" src="${bucketUrl }/runningToday/${board.id }/${fileName}" alt="" height="300" width="300" />
							</div>
						</c:forEach>
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
						<label for="" class="form-label">작성일시</label>
						<input type="text" readonly class="form-control" value="${board.inserted }" />
					</div>

					<!-- 댓글  -->
					<div id="commentContainer">
						<h1>
							<i class="fa-solid fa-comments"></i>
						</h1>
						<sec:authorize access="isAuthenticated()">
							<div class="mb-3" id="addCommentContainer">


								<div class="input-group">
									<div class="form-floating">
										<textarea style="height: 97px" placeholder="댓글을 남겨주세요" class="form-control" id="commentTextArea"></textarea>
										<label for="floatingTextarea">댓글을 남겨주세요</label>
									</div>
									<button class="btn btn-outline-primary" id="sendCommentBtn">
										<i id="sendingComment" class="fa-regular fa-paper-plane"></i>
									</button>
								</div>
							</div>
						</sec:authorize>



						<ul class="list-group" id="commentListContainer">


						</ul>
					</div>

					<c:if test="${board.userId eq sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name }">
						<a class="btn btn-secondary" href="/running/runningTodayModify/${board.id }">수정</a>
						<button id="removeButton" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">삭제</button>
					</c:if>


					<!-- 보안 넣어줘야함   -->
					<c:if test="${board.userId eq sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name }">
						<div class="d-none">
							<form action="/running/todayRemove" method="post" id="removeForm">
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
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<my:chatBtn></my:chatBtn>
		<script src="/js/groupChat.js"></script>
		<script src="/js/chat.js" charset="UTF-8"></script>
	</sec:authorize>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="/js/running/runningTodayGet.js"></script>
	<script src="/js/running/runningComment.js"></script>
	<script src="/js/navBar.js"></script>

</body>
</html>