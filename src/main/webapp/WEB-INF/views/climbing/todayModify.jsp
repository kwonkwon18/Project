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

	<my:navBarClimbing></my:navBarClimbing>
	<span id="boardIdText"> ${board.id } </span>
	<br />
	<br />
	<br />
	<br />


	<div class="container-lg">
	
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<form method="post" enctype="multipart/form-data">
					<input class="btn btn-secondary" type="submit" style="float: right; margin-left: 5px;" value="수정" />
					<h3 style="font-weight: bold;">오늘의 등산</h3>
					<hr />
						<div class="mb-3">
							<!-- <label for="titleInput" class="form-label"></label> -->
							<input id="titleInput" class="form-control" type="text" name="title" value="${board.title }" placeholder="제목을 입력해주세요." />
						</div>
						
	<%-- 					<div class="mb-3">
							<label for="wirterInput" class="form-label">글쓴이</label>
							<input id="wirterInput" class="form-control" type="text" name="writer" value="${board.writer }" />
							</div> --%>
						

						<!-- application property에서 작업 하는 내용 
						spring.servlet.multipart.max-file-size=1MB
						spring.servlet.multipart.max-request-size=10MB -->
						
						<!-- 그림 파일 출력 -->
						<div class="mb-3">
							<c:forEach items="${board.fileName }" var="fileName" varStatus="status">
								<div class="mb-3">
									<div class="col-6">
										<div>
											<img class="img-thumbnail img-fluid" src="${bucketUrl }/climbingToday/${board.id }/${fileName}" alt="" style="width: 250px; height: 200px; object-fit: cover;" />
										</div>
									</div>
									
									<div class="col-2 d-flex">
										<div class="form-check form-switch m-auto" >
											<input name="removeFiles" value="${fileName }" class="form-check-input" type="checkbox" role="switch" id="removeCheckBox${status.index }">
											<label class="form-check-label" for="removeCheckBox${status.index }">
												<i class="fa-solid fa-trash-can text-danger"></i>
											</label>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						
						<div class="mb-3">
							<!-- <label for="bodyTextarea" class="form-label">본문</label> -->
							<textarea rows="10" id="bodyTextarea" class="form-control" name="body" placeholder="내용을 입력해주세요.">${board.body }</textarea>
						</div>
					
						<div class="form-text">1MB 크기의 파일, 총 10MB 크기만 허용</div>
						<div class="form-group">
							<!-- <label for="fileInput" class="form-label"></label>  -->
							<input class="form-control form-control-user" type="file" multiple name="files" accept="image/*" id="fileInput" onchange="setDetailImage(event);" />
						</div>
						<div id="images_container" style="width: 250px; height: 200px; object-fit: cover;"></div>
					</form>
				</div>
			</div>
		</div>

	<sec:authorize access="isAuthenticated()">
		<my:chatBtn></my:chatBtn>
		<script src="/js/groupChat.js"></script>
		<script src="/js/chat.js" charset="UTF-8"></script>
	</sec:authorize>
	
	<script>
	function setDetailImage(event){
		for(var image of event.target.files){
			var reader = new FileReader();
			
			reader.onload = function(event){
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.setAttribute("class", "col-lg-6");
				document.querySelector("div#images_container").appendChild(img);
			};
			
			console.log(image);
			reader.readAsDataURL(image);
		}
	}
	</script>
	
	<script>
	function resetImages() {
	  var fileInput = document.getElementById("fileInput");
	  fileInput.value = ""; // 파일 입력 필드 초기화
	  
	  var imagesContainer = document.getElementById("images_container");
	  imagesContainer.innerHTML = ""; // 이미지 컨테이너 비우기
	}
	</script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>