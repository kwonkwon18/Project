<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<style>
    #navbar-container {
        border: 3px solid #aaaaaa; /* 여기서 #ff0000은 원하는 보더 색상을 나타냅니다. */
    }
</style>

<nav id="navbar-padding" class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top">
	<div id="navbar-container" class="container-fluid"  style="background-color: white;">
		<a id="navbar-brand-link" class="navbar-brand" href="/main">
			<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/logo/%5B%EA%BE%B8%EB%AF%B8%EA%B8%B0%5Dlogo.jpg" alt="..." width="80" height="70">
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/running/runningMain">러닝</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'add' ? 'active' : '' }" href="/futsal/futsalList">풋살</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/climbing/list">등산</a>
				</li>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/logout">로그아웃</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/login">로그인</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/signup">회원가입</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/my_page">마이페이지</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<button id="alarmList" class="btn btn-primary position-relative" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
						<i class="fa-regular fa-bell"></i>
						<span id="NumberOfAlarm" style="display: none;" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger""> </span>
					</button>
				</sec:authorize>
				<%-- <li class="nav-item"><a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/list">회원목록</a></li> --%>
			</ul>

		</div>
	</div>
</nav>



<!-- offcanvas -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel" style="width: 600px;">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="offcanvasExampleLabel">알림 목록 🏄‍♂️</h5>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	</div>
	<div class="offcanvas-body">
		<div id="HostAlarm"></div>
		<div id="MemberAlarm"></div>
	</div>
</div>

