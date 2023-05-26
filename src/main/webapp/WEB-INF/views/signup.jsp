<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>회원가입</h1>
				<form method="post">

					<div class="mb-3">
						<label for="inputId" class=form-label">아이디</label> 
						<input id="inputId" type="text" class="form-control" name=userId value="${member.userId }" />						
					</div>

					<div class="mb-3">
						<label for="inputPassword" class=form-label">패스워드</label> 
						<input id="inputPassword" type="password" class="form-control" name="password" value = "${member.password }"/>
					</div>

					<div class="mb-3">
						<label for="inputEmail" class=form-label">이름</label>
						<input id="inputEmail" type="text" class="form-control" name="name" value="${member.name }" />
					</div>
					
					<div class="mb-3">
						<label for="inputNickName" class=form-label">별명</label> 
						<input id="inputNickName" type="text" class="form-control" name="nickName" value="${member.nickName }" />
					</div>
					
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">생일</label>
						<input id="inputEmail" type="text" class="form-control" name="birth" value="${member.birth }" />
					</div>
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">성별</label>
						<input id="inputEmail" type="text" class="form-control" name="gender" value="${member.gender }" />
					</div>
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">경력</label>
						<input id="inputEmail" type="text" class="form-control" name="career" value="${member.career }" />
					</div>
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">주소</label>
						<input id="inputEmail" type="text" class="form-control" name="address" value="${member.address }" />
					</div>
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">핸드폰 번호</label>
						<input id="inputEmail" type="text" class="form-control" name="phone" value="${member.phone }" />
					</div>
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">이메일</label>
						<input id="inputEmail" type="text" class="form-control" name="email" value="${member.email }" />
					</div>
					
					
					<div class="mb-3">
						<label for="inputEmail" class=form-label">소개</label>
						<input id="inputEmail" type="text" class="form-control" name="introduce" value="${member.introduce }" />
					</div>

					
					<div class="mb-3">
						<input id="signupSubmit" class="btn btn-success" type="submit" value="가입">
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>