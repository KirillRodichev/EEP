<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: kiril
  Date: 21.02.2020
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
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

    <!-- FontAwesome kit -->
    <script src="https://kit.fontawesome.com/9ec0b3417c.js" crossorigin="anonymous"></script>

    <title>EEP</title>
</head>
<body>

<%@ include file="components/navigation.jsp" %>

<section class="login-auth" id="form">
    <div id="wrapper" class="login-auth-flex box-left">
        <div class="login-auth__box-wrapper">
            <div class="login-auth__box box-right">
                <h2 class="container__header">SignUp Form</h2>
                <form class="needs-validation" action="signUp" method="post" novalidate>
                    <div class="form-group">
                        <label for="signUpUserName">Name</label>
                        <button class="button--default p-0" type="button" data-toggle="popover" title="Name tips"
                                data-content="Some real tips will be placed here. Now it's just this."
                        >
                            <i class="far fa-question-circle form-group__i"></i>
                        </button>
                        <input name="name" type="text" class="form-control form-control--custom primary-border" id="signUpUserName"
                               required placeholder="Name">
                        <div class="invalid-feedback">Name is required and must follow the tips</div>
                    </div>
                    <div class="form-group">
                        <label for="signUpEmail">Email</label>
                        <input name="email" type="email" class="form-control form-control--custom primary-border" id="signUpEmail"
                               required placeholder="Email">
                        <div class="invalid-feedback">Email is required</div>
                    </div>
                    <div class="form-group">
                        <label for="signUpPassword">Password</label>
                        <button class="button--default p-0" type="button" data-toggle="popover" title="Password tips"
                                data-content="Some real tips will be placed here. Now it's just this."
                        >
                            <i class="far fa-question-circle form-group__i"></i>
                        </button>
                        <input name="password" type="password" class="form-control form-control--custom primary-border"
                               id="signUpPassword"
                               required placeholder="Password">
                        <div class="invalid-feedback">Password is required and must follow the tips</div>
                    </div>
                    <div class="form-group">
                        <label for="selectMode">User / Admin</label>
                        <select name="mode" id="selectMode" class="form-control form-control--custom primary-border" required>
                            <option value="" selected>User</option>
                            <option value="" selected>Admin</option>
                        </select>
                    </div>
                    <div class="d-flex mt-5">
                        <button disabled value="signUp" type="submit" class="button--primary mr-3">Sign up</button>
                        <a role="button" id="toLogin" class="button--secondary">Switch to Login</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="login-auth__box-wrapper">
            <div class="login-auth__box box-left">
                <h2 class="container__header">Login Form</h2>
                <form class="needs-validation" action="login" method="post" novalidate>
                    <div class="form-group">
                        <label for="loginEmail">Email</label>
                        <input name="email" type="email" class="form-control form-control--custom primary-border" id="loginEmail"
                               placeholder="Email" required>
                    </div>
                    <div class="form-group">
                        <label for="loginPassword">Password</label>
                        <input name="password" type="password" class="form-control form-control--custom primary-border"
                               id="loginPassword"
                               placeholder="Password" required>
                    </div>
                    <div class="d-flex mt-5">
                        <button value="login" type="submit" class="button--primary mr-3">Login</button>
                        <a role="button" id="toSignUp" class="button--secondary">Switch to Sign Up</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<%@ include file="components/footer.html" %>

<!-- Optional JavaScript -->

<script src="assets/js/needs-validation.js"></script>

<script src="assets/js/login-auth-effect.js"></script>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="assets/js/bootstrap.min.js"></script>

<!-- Enable Popover -->
<script>
    $(function () {
        $('[data-toggle="popover"]').popover({
            trigger: 'hover'
        })
    })
</script>

</body>
</html>
