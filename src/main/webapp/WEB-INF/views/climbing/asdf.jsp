<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>메이트구하기 🏕</h2>
	<br />
	<nav>
		<ul>
			<a id="all1" href="mateList" style="text-decoration-line: none;">전체</a>
			<a class="dropdown-toggle" href="#" role="button" id="search1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="text-decoration-line: none;">검색 </a>
			<div class="dropdown-menu" aria-labelledby="search1">
				<a class="dropdown-item" href="#">메뉴 항목 1</a>
				<a class="dropdown-item" href="#">메뉴 항목 2</a>
				<a class="dropdown-item" href="#">메뉴 항목 3</a>
			</div>
			<a href="mateMap" style="text-decoration-line: none;">지도로 보기</a>
			<span style="margin-left: 735px;">
				<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">번개 글작성</button>
				<button type="button" class="btn btn-success" onclick="location.href='https://www.weather.go.kr/w/weather/forecast/mid-term.do'">날씨 보기</button>
			</span>
			<!-- 				<button type="button" class="btn btn-success" onclick="location.href='mateAdd'">소모임 글작성</button> -->
		</ul>
		<div id="dropdown1" style="display: none">
			<ul>
				<button type="button" class="btn btn-success" style="pointer-events: none;">검색🌄</button>
				<form action="/climbing/mateList" class="d-flex" role="mateSearch">
					<select class="form-select" name="type" id="" style="width: 150px">
						<option value="all">전체</option>
						<option value="title" ${param.type eq 'title' ? 'selected': '' }>제목</option>
						<option value="address" ${param.type eq 'address' ? 'selected': '' }>위치</option>
						<%-- <option value="writer" ${param.type eq 'writer' ? 'selected': '' }>글쓴이</option> --%>
					</select>
					<input value="${param.mateSearch}" name="mateSearch" class="form-control" type="mateSearch" aria-label="mateSearch">
					<button class="btn btn-outline-success" type="submit">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</form>
			</ul>
		</div>
	</nav>

	<ul>
		<div style="text-align: right;">
			<a href="/climbing/mateList?type=distance" style="text-decoration-line: none;">거리순</a>
			<a href="/climbing/mateList" style="text-decoration-line: none;">최신순</a>
		</div>
	</ul>

	<fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />
	<fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />
	<div id="mateListData" class="row">
		<c:forEach items="${climbingMateList}" var="board" varStatus="status">
			<c:if test="${status.index < 3 }">
				<div class="col-md-4">
					<div class="card" style="width: 18rem; margin-bottom: 20px; height: 350px;">
						<div class="card-body">
							<h5 class="card-title">🌄${board.title}</h5>
							<p class="card-text">작성자: ${board.writer}</p>
							<p class="card-text">작성일자: ${board.inserted}</p>
							<p class="card-text">모임장소: ${board.address}</p>
							<p class="card-text">모임시간: ${board.time}</p>
							${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}

							<c:set var="isMember" value="false" />
							<c:forEach items="${memberList}" var="memberList">
								<c:if test="${memberList.nickName eq board.writer}">
									<c:set var="isMember" value="true" />
								</c:if>
							</c:forEach>

							<c:if test="${openDate <= nowDate }">
								<button>마감된 등산</button>
							</c:if>

							<c:if test="${openDate > nowDate }">
								<c:if test="${isMember}">
									<button type="button" onclick="location.href='/climbing/id/${board.id}' ">지원 사항 상세보기</button>
								</c:if>

								<c:if test="${not isMember}">
									<button data-board-userId="${board.writer }" data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" id="listUpButton${status.index + 1}" class="listUpButton btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">더보기</button>
								</c:if>
							</c:if>
						</div>
						<div class="card-footer" style="text-align: right">
							<button data-board-userId="${board.writer }" data-board-userId="${board.writer }" data-board-id="${board.id }" type="button" class="listUpButton btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">더보기</button>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
	</div>

	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">게시물 상세 보기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" id="resMate"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">게시물 상세 보기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" id="resMate"></div>
			</div>
		</div>
	</div>

</body>
</html>