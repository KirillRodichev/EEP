<%@ page import="constants.ErrorMsg" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="shortcut icon" href="favicon.ico">

    <!-- Roboto font -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,500,700&display=swap&subset=cyrillic"
          rel="stylesheet">

    <!-- Custom styles -->
    <link rel="stylesheet" href="assets/css/main.css">

    <title>Error</title>
</head>
<body>

<%String errorMsg = (String) request.getAttribute(ErrorMsg.ERROR_MESSAGE);%>

<section class="container d-flex flex-column align-items-center">
    <h1 class="mb-4 mt-5"><%=errorMsg%></h1>
    <img src="assets/img/error.jpg" alt="error" class="mb-3">
    <a href="login-auth.jsp" role="button" class="button--secondary">Go Back</a>
</section>
</body>
</html>