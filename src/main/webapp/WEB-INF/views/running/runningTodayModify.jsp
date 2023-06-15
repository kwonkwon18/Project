<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<my:navBar></my:navBar>


	<div class="container-lg">

		<div class="row justify-content-center">

			<div class="col-12 col-md-8 col-lg-6">
				<div class="d-flex">
					<div class="me-auto"></div>
				</div>
				<form method="post" enctype="multipart/form-data">
					<div>
						<div class="mb-3">
							<label for="" class="form-label">제목</label>
							<input type="text" name="title" class="form-control" value="${board.title }" />
						</div>

						<h1>
							<span id="boardIdText"> ${board.id } 번게시물</span>
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
							<textarea name="body" class="form-control" rows="10">${board.body }</textarea>
						</div>

						<div class="mb-3">
							<label for="" class="form-label">작성자</label>
							<input id="writerText" name="writer" type="text" readonly class="form-control" value="${board.writer }" />
						</div>

						<div class="mb-3">
							<label for="" class="form-label">작성일시</label>
							<input type="text" name="inserted" readonly class="form-control" value="${board.inserted }" />
						</div>

						<!-- 새 그림파일 추가   -->
						<div class="mb-3">
							<label for="fileInput" class="form-label"> </label>
							<input type="file" multiple name="files" accept="image/*" id="fileInput" />
						</div>
						<div class="form-text">1MB 크기의 파일, 총 10MB 크기만 허용</div>
						<br />

						<div class="mb-3">
							<input class="btn btn-secondary" type="submit" value="수정" />
						</div>
				</form>

			</div>
		</div>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="/js/navBar.js"></script>
</body>
</html>