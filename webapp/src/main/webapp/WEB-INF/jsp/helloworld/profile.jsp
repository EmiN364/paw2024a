<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
<h2><spring:message code="helloworld.profile.hello" arguments="${user.username}" htmlEscape="true"/></h2>
<h5><spring:message code="helloworld.profile.id" arguments="${user.userId}" htmlEscape="true"/></h5>

<p>El usuario actual es ${currentUser.username}</p>

</body>
</html>
