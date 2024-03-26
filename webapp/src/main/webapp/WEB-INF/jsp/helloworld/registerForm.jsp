<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
<c:url value="/create" var="registerUrl"/>
<form action="${registerUrl}" method="post">
    <div>
        <label>
            Username:
            <input name="username" placeholder="username">
        </label>
    </div>
    <div>
        <label>
            <input type="submit" value="Register!">
        </label>
    </div>
</form>
</body>
</html>
