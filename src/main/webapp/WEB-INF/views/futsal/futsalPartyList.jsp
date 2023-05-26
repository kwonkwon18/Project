<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<div class="container-lg">
			<h1>진행 예정 경기</h1>
			<h5>${message }</h5>
			<!-- 새로 작성된 코드, 변경된 코드  -->
			<!-- table.table>thead>tr>th*4^^tbody -->
				<!-- boardList를 받았다.  -->
				<c:forEach items="${partyList }" var="party">
					<div class="card" style="width: 18rem;">
					  <img src="..." class="card-img-top" alt="...">
					  <div class="card-body">
					    <h5 class="card-title">${party.title }</h5>
					    <p class="card-text">${party.body }</p>
					    <a href="#" class="btn btn-primary">바로 신청</a>
					    <a href="/futsal/partyId/${party.id }" class="btn btn-primary">세부 정보</a>
					  </div>
					</div>
				</c:forEach>
					<c:forEach items="${boardList }" var="board">
						<tr>
							<td>${board.id }</td>
							<td><a href="/futsal/id/${board.id }"> ${board.title }</a>
							<td><a href="/futsal/id/${board.id }"> ${board.writer }</a>
							<td>${board.inserted }</td>
						</tr>
					</c:forEach>
				<a href="/futsal/futsalPartyAdd">매치생성</a>
			
		</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>