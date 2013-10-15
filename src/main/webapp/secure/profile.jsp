<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sn" uri="/taglib/fbConnect.tld"%>
<html>
<head>
    <title>FB Connect - Profile</title>

    <link rel="stylesheet" href="../static/css/reset.css">
    <link rel="stylesheet" href="../static/css/style.css">

</head>
<body>

    <h2>Congratulations! You are successfully logged in!</h2>

    <div>
        <sn:avatar />
        <sn:userName />
    </div>

    <a href="logout">Logout</a>

</body>
</html>