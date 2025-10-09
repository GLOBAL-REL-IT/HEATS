<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<c:choose>
    <c:when test="${not empty pageContext.request.remoteUser}">
        <%@include file="/WEB-INF/views/home/home.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="/WEB-INF/views/home/login_new.jsp" %>
<<<<<<< HEAD
         <%--<%@include file="/WEB-INF/views/home/login.jsp" %>--%>
=======
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772
    </c:otherwise>
</c:choose>