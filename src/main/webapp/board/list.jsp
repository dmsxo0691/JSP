<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<div class="m-2">
		<form class="form-inline d-flex justify-content-end"
			action="/JSP/board">
			<input type="hidden" name="cmd" value="search" /> <input
				type="hidden" name="page" value="0" /> <input type="text"
				name="keyword" class="form-control mr-sm-2" placeholder="Search">
			<button class="btn btn-primary m-1">검색</button>

		</form>
	</div>

	<div class="progress col-md-12 m-2">
		<div class="progress-bar" style="width: ${currentPosition}%"></div>
	</div>

	<c:forEach var="board" items="${boards}">
		<div class="card col-md-12 m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/JSP/board?cmd=detail&id=${board.id}"
					class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>

	<br />
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${empty param.keyword}">
				<c:set var="pagePrev"
					value="/JSP/board?cmd=list&page=${param.page-1 }"></c:set>
				<c:set var="pageNext"
					value="/JSP/board?cmd=list&page=${param.page+1}" />
			</c:when>
			<c:otherwise>
				<c:set var="pagePrev"
					value="/JSP/board?cmd=search&page=${param.page-1}&keyword=${param.keyword}" />
				<c:set var="pageNext"
					value="/JSP/board?cmd=search&page=${param.page+1}&keyword=${param.keyword}" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${param.page == 0}">
				<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="${pageScope.pagePrev}">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${lastPage == param.page}">
				<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="${pageScope.pageNext}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
</body>

<%@ include file="../layout/footer.jsp"%>
</html>