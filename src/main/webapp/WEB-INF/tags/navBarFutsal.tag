<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<style>
#navbar-brand-link {
	padding: 0;
}

#navbar-container {
	padding: 0;
}

#navbar-padding {
	padding: 0;
}
</style>

<nav id="navbar-padding" class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top">
	<div id="navbar-container" class="container-fluid" style="background-color: #FAED7D;">

		<a id="navbar-brand-link" class="navbar-brand" href="/futsal/futsalMain">

			<img src="https://bucket0503-qqwweerr11223344.s3.ap-northeast-2.amazonaws.com/project/logo/%ED%92%8B%EC%82%B4%EB%A1%9C%EA%B3%A0.png" alt="..." width="80" height="70">
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/main" style="color: #670000; font-weight: bold;">홈으로</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'add' ? 'active' : '' }" href="/futsal/futsalMate" style="color: black;">메이트 구하기</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/futsal/futsalTodayList" style="color: black;">오늘의 풋살</a>
				</li>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/logout" style="color: red;">로그아웃</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/login" style="color: white;">로그인</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<button class="nav-item btn btn-primary" style="width: 40px; height: 40px; align-items: center; justify-content: center; display: flex; margin-right: 10px;">
							<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/running/myPage">
								<i class="fa-regular fa-bookmark btn btn-primary"></i>
							</a>
						</button>
					</li>
					<button id="alarmList" class="btn btn-primary position-relative" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
						<i class="fa-regular fa-bell"></i>
						<span id="NumberOfAlarm" style="display: none;" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger""> </span>
					</button>
				</sec:authorize>
			</ul>

		</div>
	</div>
</nav>

<!-- offcanvas -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel" style="width: 800px;">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="offcanvasExampleLabel">알림 목록 🏄‍♂️</h5>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	</div>
	<div class="offcanvas-body">
		<div id="runningHostAlarm"></div>
		<div id="runningMemberAlarm"></div>
	</div>
</div>

