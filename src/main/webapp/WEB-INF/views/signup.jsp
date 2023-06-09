<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

</head>
<body>
	<my:alert></my:alert>
	<my:navBar> </my:navBar>
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
						<label for="inputEmail" class=form-label">주소</label>
						<%-- <input id="inputEmail" type="text" class="form-control" name="address" value="${member.address }" /> --%>
						<select class="form-control" name = "address" id="address">
						<option value="강남구">강남구</option>
						<option value="강동구">강동구</option>
						<option value="강북구">강북구</option>
						<option value="강서구">강서구</option>
						<option value="관악구">관악구</option>
						<option value="광진구">광진구</option>
						<option value="구로구">구로구</option>
						<option value="금천구">금천구</option>
						<option value="노원구">노원구</option>
						<option value="도봉구">도봉구</option>
						<option value="동대문구">동대문구</option>
						<option value="동작구">동작구</option>
						<option value="마포구">마포구</option>
						<option value="서대문구">서대문구</option>
						<option value="서초구">서초구</option>
						<option value="성동구">성동구</option>
						<option value="성북구">성북구</option>
						<option value="송파구">송파구</option>
						<option value="양천구">양천구</option>
						<option value="영등포구">영등포구</option>
						<option value="용산구">용산구</option>
						<option value="은평구">은평구</option>
						<option value="종로구">종로구</option>
						<option value="중구">중구</option>
						<option value="중랑구">중랑구</option>
						
						
						</select>
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