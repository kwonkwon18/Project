<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="pageNum" %>

<c:url value="/list" var="pageLink">
	<c:param name="page" value="${pageNum }" />
	<c:if test="${not empty param.search }">
		<c:param name="search" value="${param.search }" />
	</c:if>
	<c:if test="${not empty param.type }">
		<c:param name="type" value="${param.type }" />
	</c:if>
</c:url>
<li class="page-item">
	<a class="page-link ${pageNum eq pageInfo.currentPageNum ? 'active' : '' }" href="${pageLink }">
		<jsp:doBody></jsp:doBody>
	</a>
</li>








