<%--
  Created by IntelliJ IDEA.
  User: kiril
  Date: 01.03.2020
  Time: 14:04
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

    <title>Cabinet</title>
</head>
<body>

<%@ include file="../components/navigation.jsp" %>

<section class="container addit-info">
    <h1 class="addit-info__h1">Feel additional info fields</h1>
    <p class="addit-info__p">This information's gonna be used to provide you with a corresponding training program.</p>
    <form class="needs-validation" novalidate>
        <div class="custom-control custom-checkbox custom-control-inline mb-3">
            <input type="checkbox" class="custom-control-input" id="customControlValidation1" required>
            <label class="custom-control-label" for="customControlValidation1">Male</label>
        </div>
        <div class="custom-control custom-checkbox custom-control-inline mb-3">
            <input type="checkbox" class="custom-control-input" id="customControlValidation2" required>
            <label class="custom-control-label" for="customControlValidation2">Female</label>
        </div>
        <div class="invalid-feedback">Select your gender</div>

        <div class="form-group">
            <label for="weightInput">Weight</label>
            <input name="weight" type="text" class="form-control form-control--custom" id="weightInput"
                   placeholder="Weight" required>
        </div>
        <div class="form-group">
            <label for="heightInput">Height</label>
            <input name="height" type="text" class="form-control form-control--custom" id="heightInput"
                   placeholder="Height" required>
        </div>

        <div class="form-group">
            <select class="custom-select" required>
                <option value="">Select...</option>
                <option value="">Gain muscles</option>
                <option value="">Loose weight</option>
                <option value="">Keep in shape</option>
            </select>
            <div class="invalid-feedback">Select a training type</div>
        </div>

        <%--<div class="form-group">
            <label for="signUpCity">City</label>
            <select name="city" id="signUpCity" class="form-control form-control--custom" required>
                <option selected>Choose...</option>
                <%
                    ArrayList<String> cities = (ArrayList<String>) request.getAttribute("cities");
                    for (String city : cities) {
                %>
                <option value=""><%=city%>
                </option>
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
                <option value=""><%=gym%>
                </option>
                <%
                    }
                %>
            </select>
        </div>--%>
    </form>
</section>

<%@ include file="../components/footer.html" %>

<!-- Optional JavaScript -->

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
