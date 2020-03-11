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

<section class="equipment">
    <h1 class="equipment__h1"></h1>
    <div class="equipment-wrapper">
        <div class="equipment__img-wrapper">
            <img src="" alt="">
        </div>
        <div class="equipment__text-wrapper">
            <form class="needs-validation" novalidate>

                        <div class="form-group">
                            <h3 class="equipment__h3"></h3>
                            <input name="name" type="text" class="form-control form-control--shadowed" id="name"
                                   placeholder="Name">
                        </div>

                        <div class="custom-file mb-16">
                            <input type="file" class="custom-file-input" id="customFile">
                            <label class="custom-file-label" for="customFile">Choose file</label>
                        </div>

                        <div class="form-group">
                            <label for="selectCity">Select City</label>
                            <select name="city" id="selectCity" class="custom-select" required>
                                <option value="">Select...</option>
                                <%for (String city : cities) {%>
                                <option value=""><%=city%>
                                </option>
                                <%}%>
                            </select>

                        </div>

                        <div class="form-group">
                            <label for="website">Displayed website link</label>
                            <input name="website" type="text" class="form-control"
                                   id="website"
                                   placeholder="Link" required>
                        </div>


                        <div class="form-group">
                            <label for="websiteURL">Actual website link</label>
                            <input name="websiteURL" type="text" class="form-control"
                                   id="websiteURL" placeholder="Actual link" required>
                        </div>
                        <div class="invalid-feedback">Please fill the field</div>
                    </div>
                    <div class="col-12 col-md-6">
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input name="phone" type="text" class="form-control"
                                   id="phone"
                                   placeholder="Phone" required>
                        </div>
                        <div class="invalid-feedback">Please fill the field</div>
                    </div>
                    <div class="col-12 col-md-6">
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input name="address" type="text" class="form-control"
                                   id="address"
                                   placeholder="Address" required>
                        </div>
                        <div class="invalid-feedback">Please fill the field</div>
                    </div>
                    <div class="col-12 button-wrapper">
                        <button value="sendInfo" type="submit" class="button--info-form w-100">Send information</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
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
