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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
    ArrayList<String> cities = (ArrayList<String>) request.getAttribute(DispatchAttrs.CITIES);
    ArrayList<String> gyms = (ArrayList<String>) request.getAttribute(DispatchAttrs.GYMS);
    Gym gym;
    try {
        gym = (Gym) request.getAttribute(DispatchAttrs.GYM);
    } catch (Exception e) {
        gym = null;
    }
%>

<section class="container info">

    <div class="info-wrapper rounded-border">
        <h1 class="info__h1">
            <%if (user.getMode().equals(UserModes.ADMIN)) {%>
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
    <div class="info-gym info-wrapper d-flex flex-row">
        <%if (gym != null) {%>
        <div class="info-gym__labels-container">
                <ul class="list-unstyled labels__ul">
                    <li class="labels__li li-header">Image</li>
                    <li class="labels__li li-value">Displayed</li>
                    <li class="labels__li li-header">City</li>
                    <li class="labels__li li-value">Select</li>
                    <li class="labels__li li-header">Name</li>
                    <li class="labels__li li-value"><%=gym.getName()%></li>
                    <li class="labels__li li-header">Actual link</li>
                    <li class="labels__li li-value"><%=gym.getWebsiteURL()%></li>
                    <li class="labels__li li-header">Displayed link</li>
                    <li class="labels__li li-value"><%=gym.getWebsite()%></li>
                    <li class="labels__li li-header">Phone</li>
                    <li class="labels__li li-value"><%=gym.getPhone()%></li>
                    <li class="labels__li li-header">Address</li>
                    <li class="labels__li li-value"><%=gym.getAddress()%></li>
                </ul>
        </div>
        <div class="info-gym__form-container">
            <h3 class="info-gym__h3">Rewrite your gym info</h3>
            <form class="needs-validation" novalidate>

                <img class="info-gym__img" src="<%=Paths.GYM_LOGO_PATH + gym.getLogoPath()%>"
                     alt="<%=gym.getName()%>">
                <div class="custom-file mb-16">
                    <input type="file" class="custom-file-input h40" id="r_customFile">
                    <label class="custom-file-label" for="r_customFile">Choose file</label>
                </div>
                <div class="form-group">
                    <%--<label for="r_selectCity">Select city</label>--%>
                    <select name="r_city" id="r_selectCity" class="custom-select  h40">
                        <option value="">Select city</option>
                        <%for (String city : cities) {%>
                        <option value=""><%=city%>
                        </option>
                        <%}%>
                    </select>
                </div>
                <div class="form-group">
                    <%--<label for="r_name">Current name: <%=gym.getName()%>
                    </label>--%>
                    <input name="r_name" type="text" class="form-control form-control--custom primary-border"
                           id="r_name" placeholder="Name">
                </div>
                <div class="form-group">
                    <%-- <label for="r_websiteURL">Current actual link: <%=gym.getWebsiteURL()%>
                     </label>--%>
                    <input name="r_websiteURL" type="text"
                           class="form-control  form-control--custom primary-border" id="r_websiteURL"
                           placeholder="Actual link">
                </div>

                <div class="form-group">
                    <%--<label for="r_website">Current displayed link: <%=gym.getWebsite()%>
                    </label>--%>
                    <input name="r_website" type="text"
                           class="form-control  form-control--custom primary-border" id="r_website"
                           placeholder="Link">
                </div>

                <div class="form-group">
                    <%--<label for="r_phone">Current phone: <%=gym.getPhone()%>
                    </label>--%>
                    <input name="r_phone" type="text" class="form-control  form-control--custom primary-border"
                           id="r_phone" placeholder="Phone">
                </div>

                <div class="form-group">
                    <%--<label for="r_address">Current address: <%=gym.getAddress()%>
                    </label>--%>
                    <input name="r_address" type="text"
                           class="form-control  form-control--custom primary-border" id="r_address"
                           placeholder="Address">
                </div>

                <button value="sendNewInfo" type="submit" class="button--primary w-100 mt-4">Send new information
                </button>

            </form>
        </div>
        <%} else {%>
        <div class="m50">
            <h3 class="info-gym__h3">Fill the information about your gym</h3>
            <form class="needs-validation" novalidate>

                <div class="form-group">
                    <%if (user.getMode().equals(UserModes.ADMIN)) {%>
                    <label for="name">Write your gym name</label>
                    <input name="name" type="text" class="form-control form-control--custom primary-border"
                           id="name"
                           placeholder="Name" required>
                    <%} else {%>
                    <lable for="name">Select your gym</lable>
                    <select name="name" id="name" class="custom-select" title="Select..." required>
                        <%for (String g : gyms) {%>
                        <option value=""><%=g%>
                        </option>
                        <%}%>
                    </select>
                    <%}%>
                </div>
                <div class="invalid-feedback">This field is required</div>

                <%if (user.getMode().equals(UserModes.ADMIN)) {%>

                <div class="custom-file mb-16">
                    <input type="file" class="form-control form-control--custom primary-border" id="customFile"
                           required>
                    <label class="custom-file-label" for="customFile">Choose image file</label>
                </div>
                <div class="invalid-feedback">Please fill the field</div>

                <%}%>

                <div class="form-group">
                    <label for="selectCity">Select City</label>
                    <select name="city" id="selectCity" class="custom-select" title="Select..." required>
                        <%for (String city : cities) {%>
                        <option value=""><%=city%>
                        </option>
                        <%}%>
                    </select>
                    <div class="invalid-feedback">Select city</div>
                </div>
                <div class="invalid-feedback">Please select the city</div>

                <%if (user.getMode().equals(UserModes.ADMIN)) {%>

                <div class="form-group">
                    <label for="website">Displayed website link</label>
                    <input name="website" type="text" class="form-control form-control--custom primary-border"
                           id="website" placeholder="Link" required>
                </div>
                <div class="invalid-feedback">Please fill the field</div>

                <div class="form-group">
                    <label for="websiteURL">Actual website link</label>
                    <input name="websiteURL" type="text"
                           class="form-control form-control--custom primary-border" id="websiteURL"
                           placeholder="Actual link" required>
                </div>
                <div class="invalid-feedback">Please fill the field</div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input name="phone" type="text" class="form-control form-control--custom primary-border"
                           id="phone" placeholder="Phone" required>
                </div>
                <div class="invalid-feedback">Please fill the field</div>


                <div class="form-group">
                    <label for="address">Address</label>
                    <input name="address" type="text" class="form-control form-control--custom primary-border"
                           id="address" placeholder="Address" required>
                </div>
                <div class="invalid-feedback">Please fill the field</div>

                <%}%>
                <div class="button-wrapper">
                    <button value="sendInfo" type="submit" class="button--primary w-100 mt-4">Send information</button>
                </div>
            </form>
        </div>

        <%}%>
    </div>
    <%if (gym != null) {%>
    <div class="equipment-btn">
        <form action="equipment" method="post">
            <input name="id" class="d-none" type="text" value="<%=gym.getId()%>">
            <button type="submit" class="equipment-btn__btn">EQUIPMENT</button>
        </form>
    </div>
    <%} else {%>
    <div class="equipment-btn">
        <form action="setEquipment" method="post">
            <input name="id" class="d-none" type="text" value="">
            <button type="submit" class="equipment-btn__btn">SET EQUIPMENT</button>
        </form>
    </div>
    <%}%>
</section>

<%@ include file="../components/footer.html" %>

<!-- Optional JavaScript -->
<script src="assets/js/needs-validation.js"></script>
<script src="assets/js/shrink-nav-on-scroll.js"></script>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
