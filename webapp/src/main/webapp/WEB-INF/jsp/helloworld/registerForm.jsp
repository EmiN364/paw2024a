<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
<c:url value="/create" var="registerUrl"/>
<form:form action="${registerUrl}" method="post" modelAttribute="userform">
    <div>
        <label>
            <spring:message code="helloworld.registerform.username"/>
            <form:input path="username" placeholder="username" />
        </label>

        <form:errors path="username" element="p" cssClass="error"/>
    <%--<c:if test="${username.required}">
            <p class="error">Username is required</p>
        </c:if>
        <c:if test="${username.notunique}">
            <p class="error">Username is already taken</p>
        </c:if>--%>
    </div>
    <div>
        <label>
            <spring:message code="helloworld.registerform.password"/>
            <form:input path="password" placeholder="password" type="password"/>
        </label>
        <form:errors path="password" element="p" cssClass="error"/>
        <%--<c:if test="${password.tooshort}">
            <p class="error">Password is too short</p>
        </c:if>--%>
    </div>
    <div>
        <label>
            <spring:message code="helloworld.registerform.repeatpassword"/>
            <form:input path="repeatPassword" placeholder="password" type="password"/>
        </label>
    </div>
    <form:errors path="repeatPassword" element="p" cssClass="error"/>
    <div>
        <label>
            <input type="submit" value="<spring:message code="helloworld.registerform.register"/>"/>
        </label>
    </div>
</form:form>

<p>El usuario actual es ${currentUser.username}</p>

</body>
</html>
