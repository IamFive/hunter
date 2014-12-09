<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ attribute name="clazz" type="java.lang.String" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int current =  page.getNumber() + 1;
	int begin = Math.max(1, current - paginationSize/2);
	int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());
	
	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
	request.setAttribute("clazz", clazz);
%>

<div class="${empty clazz ? 'pagination': clazz }">
	<!-- display total pages -->
	<span>共 ${page.totalElements} 条纪录 </span>
	
	<!-- first page & preview page -->
	<% if (page.hasPreviousPage()){%>
		<a href="?page=1&pagesize=${page.size}&sortby=${sortby}&${_sps}">首页</a>
		<a href="?page=${current-1}&pagesize=${page.size}&sortby=${sortby}&${_sps}">上一页</a>
	<%}%>
	
	<!-- pages -->
	<c:forEach begin="${begin}" end="${end}" var="c">
		<c:choose>
			<c:when test="${c == current}">
				<a class="current" href="?page=${c}&pagesize=${page.size}&sortby=${sortby}&${_sps}">${c}</a>
			</c:when>
			<c:otherwise>
				<a href="?page=${c}&pagesize=${page.size}&sortby=${sortby}&${_sps}">${c}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<!-- next page & last page -->
	<% if (page.hasNextPage()){%>
		<a href="?page=${current+1}&sortby=${sortby}&${_sps}">下一页</a>
		<a href="?page=${page.totalPages}&pagesize=${page.size}&sortby=${sortby}&${_sps}">末页</a>
	<%}%>
	
	<!-- page selector -->
	<c:if test="${page.totalPages > 0}">
		<span>到第</span>
		<select id="page-selector" onchange="javascript:Pagination.changePage()">
			<c:forEach begin="1" end="${page.totalPages}" var="c">
				<option value="${c}" ${ current==c ? 'selected' : '' } >&nbsp;${c}&nbsp;</option>
			</c:forEach>
		</select>
		<span>页</span>
	</c:if>
	
	<script type="text/javascript">
		var Pagination = Pagination ? Pagination : {};
		Pagination.changePage = function() {
			var page = document.getElementById("page-selector").value;
			window.location = '?page=' + page + '&pagesize=' + ${page.size} + '&sortby=${sortby}&${_sps}';
		}
	</script>
</div>
