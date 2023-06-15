<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top">
	<div class="container-fluid">
		<a class="navbar-brand" href="/main">러닝메이트</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/main">홈으로</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'add' ? 'active' : '' }" href="/running/runningMate">메이트 구하기</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/running/runningToday">오늘의 러닝</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/running/myPage">마이페이지</a>
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
				<button id="alarmList" class="btn btn-primary position-relative" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
					<i class="fa-regular fa-bell"></i>
					<span id="NumberOfAlarm" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" style="display: none;"> </span>
				</button>
			</ul>

		</div>
	</div>
</nav>

<!-- offcanvas -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="offcanvasExampleLabel">알림 목록 🏄‍♂️</h5>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	</div>
	<div class="offcanvas-body">
		<div id="HostAlarm"></div>
		<div id="MemberAlarm"></div>
		'
	</div>
</div>