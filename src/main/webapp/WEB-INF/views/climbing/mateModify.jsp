<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<style>
.form-check-input:checked {
	background-color: #dc3545;
	border-color: #dc3545;
}
</style>
</head>
<body>
	<my:navBarclimbing> </my:navBarclimbing>
	<div class="container-lg">

		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">


				<h1>${board.id }번게시물수정</h1>
				<form method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value="${board.id }" />
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input class="form-control" id="titleInput" type="text" name="title" value="${board.title }" />
					</div>

					<!-- 그림 파일 출력 -->
					<div class="mb-3">
						<c:forEach items="${board.fileName }" var="fileName" varStatus="status">
							<div class="mb-3">
								<div class="row">
									<div class="col-2 d-flex">
										<div class="form-check form-switch m-auto">
											<input name="removeFiles" value="${fileName }" class="form-check-input" type="checkbox" role="switch" id="removeCheckBox${status.index }">
											<label class="form-check-label" for="removeCheckBox${status.index }">
												<i class="fa-solid fa-trash-can text-danger"></i>
											</label>
										</div>
									</div>

									<div class="col-10">
										<div>
											<img class="img-thumbnail img-fluid" src="${bucketUrl }/mate/${board.id }/${fileName}" alt="" />
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>

					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea class="form-control" id="bodyTextarea" rows="10" name="body">${board.body }</textarea>
					</div>
					<div class="mb-3">
						<label for="" class="form-label">작성일시</label>
						<input class="form-control" type="text" value="${board.inserted }" readonly />
					</div>
					<div class="mb-3">
						<input class="btn btn-secondary" type="submit" value="수정" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</body>
</html>









