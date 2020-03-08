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
                        <input name="name" type="text" class="form-control form-control--custom" id="signUpUserName"
                               placeholder="Name" required>
                    </div>
                    <div class="form-group">
                        <label for="signUpEmail">Email</label>
                        <input name="email" type="email" class="form-control form-control--custom" id="signUpEmail"
                               placeholder="Email" required>
                    </div>
                    <div class="form-group">
                        <label for="signUpPassword">Password</label>
                        <input name="password" type="password" class="form-control form-control--custom" id="signUpPassword"
                               placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <label for="signUpCity">City</label>
                        <select name="city" id="signUpCity" class="form-control form-control--custom" required>
                            <option selected>Choose...</option>
                            <%
                                ArrayList<String> cities = (ArrayList<String>) request.getAttribute("cities");
                                for (String city : cities) {
                            %>
                            <option value=""><%=city%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="signUpGym">Gym</label>
                        <select name="gym" id="signUpGym" class="form-control form-control--custom" required>
                            <option selected>Choose...</option>
                            <%
                                ArrayList<String> gyms = (ArrayList<String>) request.getAttribute("gyms");
                                for (String gym : gyms) {
                            %>
                            <option value=""><%=gym%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="d-flex mt-5">
                        <button value="signUp" type="submit" class="button--primary mr-3">Sign up</button>
                        <a role="button" id="toLogin" class="button--secondary">Switch to Login</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="login-auth__box-wrapper">
            <div class="login-auth__box box-left">
                <h2 class="container__header">Login Form</h2>
                <form class="needs-validation" action="test" method="post" novalidate>
                    <div class="form-group">
                        <label for="loginEmail">Email</label>
                        <input name="email" type="email" class="form-control form-control--custom" id="loginEmail"
                               placeholder="Email" required>
                    </div>
                    <div class="form-group">
                        <label for="loginPassword">Password</label>
                        <input name="password" type="password" class="form-control form-control--custom" id="loginPassword"
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
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            let forms = document.getElementsByClassName('needs-validation');
            let validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

<script src="assets/js/login-auth-effect.js"></script>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>
</html>
