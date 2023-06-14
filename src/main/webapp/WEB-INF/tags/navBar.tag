<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top" >
	<div class="container-fluid">
		<a class="navbar-brand" href="/main">메이트</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/running/runningMate">러닝</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'add' ? 'active' : '' }" href="/futsal/list">풋살</a></li>
				<li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/climbing/mateList">등산</a></li>
				<sec:authorize access="isAuthenticated()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/logout">로그아웃</a></li></sec:authorize>
				<sec:authorize access="isAnonymous()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/login">로그인</a></li></sec:authorize>
				<sec:authorize access="isAnonymous()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/signup">회원가입</a></li></sec:authorize>
				<sec:authorize access="isAuthenticated()"><li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/my_page">마이페이지</a></li></sec:authorize>
<button type="button" class="btn btn-primary position-relative">
  <i class="fa-regular fa-bell"></i>
  <span id = "NumberOfAlarm" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
  </span>
</button>
				 <%-- <li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/list">회원목록</a></li> --%>
			</ul>
			
		</div>
	</div>
</nav>
