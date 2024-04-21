<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sprint" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">
    <div>
        <label>
            <sprint:message key="helloworld.registerform.username"/>
            <input name="username" placeholder="username" />
        </label>
    </div>
    <div>
        <label>
            <sprint:message key="helloworld.registerform.password"/>
            <input name="password" placeholder="password" type="password"/>
        </label>
    </div>
    <div>
        <label>
            <sprint:message key="helloworld.registerform.repeatpassword"/>
            <input name="repeatPassword" placeholder="password" type="password"/>
        </label>
    </div>
    <div>
        <label>
            <input type="submit" value="<sprint:message key="helloworld.login.login"/>"/>
        </label>
    </div>
</form>
</body>
</html>
