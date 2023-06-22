<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top">
    <div class="container-fluid ">
        <a class="navbar-brand" href="/main">메이트</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/login">로그인</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/signup">회원가입</a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>

