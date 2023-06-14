+<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBar></my:navBar>

	<my:alert></my:alert>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>회원 정보</h1>
				<!-- .mb-3*4>label+input -->
				<div class="mb-3">
					<label class="form-label" for="">아이디</label>
					<input class="form-control" type="text" value="${member.userId }" readonly/>
				</div>
				<div class="mb-3">
					<label class="form-label" for="">별명</label>
					<input class="form-control" type="text" value="${member.nickName }" readonly/>
				</div>
				<div class="mb-3">
					<label class="form-label" for="">생일</label>
					<input class="form-control" type="text" value="${member.birth }" readonly />
				</div>
				<div class="mb-3">
					<label class="form-label" for="">성별</label>
					<input class="form-control" type="text" value="${member.gender }" readonly />
				</div>
				<div class="mb-3">
					<label class="form-label" for="">주소</label>
					<input class="form-control" type="text" value="${member.address }" readonly />
				</div>
				<div class="mb-3">
					<label class="form-label" for="">핸드폰 번호</label>
					<input class="form-control" type="text" value="${member.phone }" readonly />
				</div>
				<div class="mb-3">
					<label class="form-label" for="">이메일</label>
					<input class="form-control" type="text" value="${member.email }" readonly />
				</div>
				<div class="mb-3">
					<label class="form-label" for="">소개</label>
					<input class="form-control" type="text" value="${member.introduce }" readonly />
				</div>
				<a class="btn btn-secondary" href="/modify?userId=${member.userId }">수정</a>
				<button type="button" data-bs-toggle="modal"
					class="btn btn-danger"
					data-bs-target="#confirmModal">회원탈퇴</button>
			</div>
		</div>
	</div>	
	<%-- <div class="d-none">
		<form id="removeForm" action="/remove" method="post">
			<input type="text" name="id" value="${member.id }" />
		</form>
	</div> --%>
	
	<!-- 탈퇴 확인Modal -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">탈퇴 확인</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
     	<form id="removeForm" action="/remove" method="post">
     		<input type="hidden" name="id" value="${member.userId }"/>
     		<label for="passwordInput1" class="form-label">암호</label>
     		<input id="passwordInput1" type="password" name="password" class="form-control" />
     	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니오</button>
        <button type="submit" form="removeForm" class="btn btn-danger">예</button>
      </div>
    </div>
  </div>
</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>