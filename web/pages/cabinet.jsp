<%@ page import="model.User" %>
<%@ page import="constants.UserModes" %>
<%@ page import="model.Gym" %>
<%@ page import="constants.Columns" %>
<%@ page import="constants.Paths" %>
<%@ page import="constants.DispatchAttrs" %>
<%@ page import="java.util.ArrayList" %><%--
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

<%
    User user = (User) request.getAttribute(DispatchAttrs.USER);
    Gym gym = (Gym) request.getAttribute(DispatchAttrs.GYMS);
    ArrayList<String> cities = (ArrayList<String>) request.getAttribute(DispatchAttrs.CITIES);
%>

<section class="container info">

    <div class="info-wrapper rounded-border">
        <h1 class="info__h1">
            <%
                if (user.getMode().equals(UserModes.ADMIN)) {
            %>
            Admin's
            <%} else {%>
            User's
            <%}%>
            Cabinet
        </h1>
        <div class="d-flex justify-content-center">
            <p class="info-wrapper__p">
                <%=user.getEmail()%>
            </p>
            <p class="info-wrapper__p">
                <%=user.getName()%>
            </p>
        </div>
    </div>
    <div class="info-gym info-wrapper">
        <%if (gym != null) {%>
        <h3 class="info-gym__h3">Edit information about your gym</h3>
        <div class="d-flex justify-content-center">
            <form class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-12 col-md-6">
                        <img class="info-gym__img" src="<%=Paths.GYM_LOGO_PATH + gym.getLogoPath()%>"
                             alt="<%=gym.getName()%>">
                        <div class="custom-file mb-16">
                            <input type="file" class="custom-file-input" id="r_customFile">
                            <label class="custom-file-label" for="r_customFile">Choose file</label>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 d-flex align-items-end">
                        <div class="form-group">
                            <label for="r_name">Current name: <%=gym.getName()%>
                            </label>
                            <input name="r_name" type="text" class="form-control form-control--shadowed" id="r_name"
                                   placeholder="Name">
                        </div>
                    </div>
                    <div class="col-12 col-md-4">
                        <div class="form-group">
                            <label for="r_selectCity">Select city</label>
                            <select name="r_city" id="r_selectCity" class="custom-select">
                                <option value="">Select...</option>
                                <%for (String city : cities) {%>
                                <option value=""><%=city%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 col-md-8">
                        <div class="form-group">
                            <label for="r_websiteURL">Current actual link: <%=gym.getWebsiteURL()%>
                            </label>
                            <input name="r_websiteURL" type="text" class="form-control" id="r_websiteURL"
                                   placeholder="Actual link">
                        </div>
                    </div>
                    <div class="col-12 col-md-6">
                        <div class="form-group">
                            <label for="r_website">Current displayed link: <%=gym.getWebsite()%>
                            </label>
                            <input name="r_website" type="text" class="form-control" id="r_website" placeholder="Link">
                        </div>
                    </div>
                    <div class="col-12 col-md-6">
                        <div class="form-group">
                            <label for="r_phone">Current phone: <%=gym.getPhone()%>
                            </label>
                            <input name="r_phone" type="text" class="form-control" id="r_phone" placeholder="Phone">
                        </div>
                    </div>
                    <div class="col-12 col-md-12">
                        <div class="form-group">
                            <label for="r_address">Current address: <%=gym.getAddress()%>
                            </label>
                            <input name="r_address" type="text" class="form-control" id="r_address"
                                   placeholder="Address">
                        </div>
                    </div>
                    <div class="col-12 button-wrapper">
                        <button value="sendNewInfo" type="submit" class="button--info-form w-100">Send new information
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <%} else {%>
        <h3 class="info-gym__h3">Fill the information about your gym</h3>
        <div class="d-flex justify-content-center">

            <form class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-12 col-md-6">
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input name="name" type="text" class="form-control form-control--shadowed" id="name"
                                   placeholder="Name" required>
                        </div>
                        <div class="invalid-feedback">You need to provide your gym name</div>
                    </div>
                    <div class="col-12 col-md-6 d-flex align-items-end">
                        <div class="custom-file mb-16">
                            <input type="file" class="custom-file-input" id="customFile">
                            <label class="custom-file-label" for="customFile">Choose file</label>
                        </div>
                    </div>
                    <div class="com-12 col-md-4">
                        <div class="form-group">
                            <label for="selectCity">Select City</label>
                            <select name="city" id="selectCity" class="custom-select" required>
                                <option value="">Select...</option>
                                <%for (String city : cities) {%>
                                <option value=""><%=city%>
                                </option>
                                <%}%>
                            </select>
                            <div class="invalid-feedback">Select city</div>
                        </div>
                    </div>
                    <div class="com-12 col-md-4">
                        <div class="form-group">
                            <label for="website">Displayed website link</label>
                            <input name="website" type="text" class="form-control"
                                   id="website"
                                   placeholder="Link" required>
                        </div>
                        <div class="invalid-feedback">Please fill the field</div>
                    </div>
                    <div class="com-12 col-md-4">
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
        <%}%>
    </div>
    <%if (gym != null){%>
    <div class="equipment-btn">
        <form action="equipment" method="post">
            <input name="id" class="d-none" type="text" value="<%=gym.getId()%>">
            <button type="submit" class="equipment-btn__btn">EQUIPMENT</button>
        </form>
    </div>
    <%}%>
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
