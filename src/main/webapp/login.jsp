<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<html>
<head>
    <title>FB Connect - Login</title>

    <link rel="stylesheet" href="static/css/reset.css">
    <link rel="stylesheet" href="static/css/style.css">
</head>
<body>

    <a href="https://www.facebook.com/dialog/oauth?client_id=270568069679176&redirect_uri=${baseURL}/authenticate">
        <img src="static/images/facebook_login_button.png">
    </a>

</body>
</html>