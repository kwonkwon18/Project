<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<div class="col-5 col-md-8 col-lg-6">
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
				<br>
				<br>
				<br>
				<br>
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/running/myPage">나의 러닝</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/futsal/myPage">나의 풋살</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/climbing/myPage">나의 등산</a></li>