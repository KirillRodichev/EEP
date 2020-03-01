<%@ page import="model.FakeData" %><%--
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

<nav class="navbar navbar-expand-lg navbar--styled">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navToggler"
            aria-controls="navToggler" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navToggler">
        <a class="navbar-brand" href="index.jsp">
            <img class="navbar-brand__img" src="assets/img/logo.png" alt="logotype">
        </a>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="#">Usage</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Get started</a>
            </li>
        </ul>
        <ul class="navbar-nav mt-2 mt-lg-0">
            <li class="nav-item">
                <a href="#" class="nav-link">Account</a>
            </li>
        </ul>
    </div>
</nav>

<section class="container container--spaced container--w60" id="form">
    <h2 class="container__header">Login / SignUp Form</h2>
    <form class="needs-validation" action="login" method="post" novalidate>
            <div class="form-group">
                <label for="inputUserName">Name</label>
                <input name="name" type="text" class="form-control form-control--custom" id="inputUserName" placeholder="Name" required>
            </div>
            <div class="form-group">
                <label for="inputEmail">Email</label>
                <input name="email" type="email" class="form-control form-control--custom" id="inputEmail" placeholder="Email" required>
            </div>
            <div class="form-group">
                <label for="inputPassword">Password</label>
                <input name="password" type="password" class="form-control form-control--custom" id="inputPassword" placeholder="Password" required>
            </div>
            <div class="form-group">
                <label for="inputCity">City</label>
                <select name="city" id="inputCity" class="form-control form-control--custom" required>
                    <option selected>Choose...</option>
                    <% for (int i = 0; i < FakeData.CITIES.length; i++) { %>
                    <option value=""><%=FakeData.CITIES[i] %>
                    </option>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <label for="inputGym">Gym</label>
                <select name="gym" id="inputGym" class="form-control form-control--custom" required>
                    <option selected>Choose...</option>
                    <% for (int i = 0; i < FakeData.GYMS.length; i++) { %>
                    <option value=""><%=FakeData.GYMS[i] %>
                    </option>
                    <% } %>
                </select>
            </div>
        <div class="form-group">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="gridCheck" required>
                <label class="form-check-label" for="gridCheck">
                    Check me out
                </label>
            </div>
        </div>
        <button value="login" type="submit" class="button--main">Sign in</button>
    </form>
</section>


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
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<%--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>--%>
<script src="assets/js/bootstrap.min.js"></script>
</body>
</html>
