<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>

		<form action="/modify" method="post">
		<div class="mb-3">
			<label for="inputId">
			아이디		
			</label>
			<input id ="inputId" class="form-control" type="text" name="id" value="${member.userId }" readonly" />
		</div>
		<div class="mb-3">
			<label for="inputPassWord">
			비밀번호		
			</label>
			<input id ="inputPassword" class="form-control" type="text" name="password" value="${member.password }" readonly" />
		</div>
		<div class="mb-3">
			<label for="inputName">
			이름		
			</label >
			<input id ="inputName" type="text" class="form-control" name="name" value="${member.name }" readonly" />
		
		</div>
		<div class="mb-3">
			<label for="inputNickName">
			별명		
			</label>
			<input id="inputNickName" class="form-control" type="text" name="nickName" value="${member.nickName }" readonly" />
			
		</div>
		<div class="mb-3">
			<label for="inputBirth">
			생년월일		
			</label>
			<input id="inputBirth" class="form-control" type="text" name="birth" value="${member.birth }" readonly" />
			
		</div>
		<div class="mb-3">
			<label for="inputGender">
			성별		
			</label>
			<input id="inputGender" type="text" name="gender" value="${member.gender }" readonly" />
		
		</div>
		<div class="mb-3">
			<label for="inputAddress">
			주소		
			</label>
			<select id="inputAddress" class="form-control" name="address" id="address">
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
			<label for="inputPhone"></label>
			핸드폰 번호		
			<input id ="inputPhone" class="form-control" type="text" name="phone" value="${member.phone }" readonly" />
		
		</div>
		<div class="mb-3">
			<label for="inputEmail">
			이메일
			</label>
			<input id="inputEmail" class="form-control" type="text" name="email" value="${member.email }" readonly" />
		
		</div>
		<div class="mb-3">
			<label for="inputIntroduce">
			자기소개	
			</label>
			<input id="inputIntroduce" class="form-control" type="text" name="introduce" value="${member.introduce }" readonly" />
		
		</div>
		
		<button class="btn btn-primary">수정</button>
		</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>


</body>
</html>