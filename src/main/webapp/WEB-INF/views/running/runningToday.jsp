<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<my:navBarRunning></my:navBarRunning>

	<div class="container-lg">

		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>게시물 작성</h1>
				<form method="post" enctype="multipart/form-data">
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input id="titleInput" class="form-control" type="text" name="title" value="${runningToday.title }" />
					</div>
					
<%-- 					<div class="mb-3">
						<label for="wirterInput" class="form-label">글쓴이</label>
						<input id="wirterInput" class="form-control" type="text" name="writer" value="${runningToday.writer }" />
					</div> --%>
					
					<div class="mb-3">
						<label for="fileInput" class="form-label"></label> 
						<input type="file" multiple name="files" accept="image/*" id="fileInput" />
					</div>
					
					<input id="titleInput" class="form-control" type="hidden" name="title" value="${runningToday.title }" />
					
					<!-- application property에서 작업 하는 내용 
					spring.servlet.multipart.max-file-size=1MB
					spring.servlet.multipart.max-request-size=10MB -->
					<div class="form-text">1MB 크기의 파일, 총 10MB 크기만 허용 ==> 변경해야함</div>

					<br />
					
					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea rows="10" id="bodyTextarea" class="form-control" name="body">${runningToday.body }</textarea>
					</div>
					
					

					<input id="addButton" class="btn btn-primary" type="submit" value="등록" />
			</div>
			</form>
		</div>
	</div>
	</div>
</body>
</html>