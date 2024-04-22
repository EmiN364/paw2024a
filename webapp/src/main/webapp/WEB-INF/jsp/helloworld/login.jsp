<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">
    <div>
        <label>
            <spring:message code="helloworld.registerform.username"/>
            <input name="username" placeholder="username" />
        </label>
    </div>
    <div>
        <label>
            <spring:message code="helloworld.registerform.password"/>
            <input name="password" placeholder="password" type="password"/>
        </label>
    </div>
    <div>
        <label>
            <input name="rememberme" type="checkbox"/>
            <spring:message code="helloworld.login.rememberme"/>
        </label>
    </div>
    <div>
        <label>
            <input type="submit" value="<spring:message code="helloworld.login.login"/>"/>
        </label>
    </div>
</form>
</body>
</html>
