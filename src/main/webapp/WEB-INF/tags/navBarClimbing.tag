<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top" >
	<div class="container-fluid">
		<a class="navbar-brand" href="/climbing/list">등산 메이트</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/main">홈으로</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'add' ? 'active' : '' }" href="/climbing/mateList">메이트 구하기</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/climbing/todayList">오늘의 등산</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/climbing/courseList">추천 코스</a></li>
				<sec:authorize access="isAuthenticated()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/climbing/myPage">마이페이지</a></li></sec:authorize>
				<sec:authorize access="isAuthenticated()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/logout">로그아웃</a></li></sec:authorize>
				<sec:authorize access="isAnonymous()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/login">로그인</a></li></sec:authorize>
			</ul>
			
		</div>
	</div>
</nav>