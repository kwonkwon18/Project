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
	<my:navBarRunning></my:navBarRunning>
	<div class="container-lg">
		<h1>러닝 게시판 게시물 목록</h1>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>제목</th>
					<th>참여인원</th>
					<th>작성자</th>
					<th>작성일자</th>
				</tr>
			</thead>
			<tbody>
				<!-- boardList를 받았다.  -->
				<c:forEach items="${boardList }" var="board">
					<tr>
						<td>${board.id }</td>
						<td>
							<a href="/running/id/${board.id }"> ${board.title }</a>
							<!-- 최대 참여인원과 현재 참여인원을 보여줘야함. 현재 참여인원은 party 테이블을 활용 하자.   -->
						<td>${board.people }</td>
						<td>
							<a href="/running/id/${board.id }"> ${board.writer }</a>
						<td>${board.inserted }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<div class="container-lg">
		<h1>오늘의 러닝</h1>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>제목</th>
					<th>참여인원</th>
					<th>작성자</th>
					<th>작성일자</th>
				</tr>
			</thead>
			<tbody>
				<!-- boardList를 받았다.  -->
				<c:forEach items="${todayList }" var="board">
					<tr>
						<td>${board.id }</td>
						<td>
							<a href="/running/todayId/${board.id }"> ${board.title }</a>
							<!-- 최대 참여인원과 현재 참여인원을 보여줘야함. 현재 참여인원은 party 테이블을 활용 하자.   -->
						<td>
							<a href="/running/todayId/${board.id }"> ${board.writer }</a>
						<td>${board.inserted }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<sec:authorize access="isAuthenticated()">
		<my:chatBtn></my:chatBtn>
		<script src="/js/groupChat.js"></script>
		<script src="/js/chat.js" charset="UTF-8"></script>
	</sec:authorize>
	<script src="/js/running/runningMate.js" charset="UTF-8"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>

<script src = "/js/navBar.js"></script>
</body>
</html>