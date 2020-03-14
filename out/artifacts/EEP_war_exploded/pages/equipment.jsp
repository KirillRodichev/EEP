<%@ page import="model.Equipment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="constants.DispatchAttrs" %>
<%@ page import="constants.Paths" %><%--
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
    <link rel="stylesheet" href="assets/css/bootstrap-select.css">

    <!-- Favicon -->
    <link rel="shortcut icon" href="favicon.ico">

    <!-- Roboto font -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,500,700&display=swap&subset=cyrillic"
          rel="stylesheet">

    <!-- Custom styles -->
    <link rel="stylesheet" href="assets/css/main.css">

    <!-- FontAwesome kit -->
    <script src="https://kit.fontawesome.com/9ec0b3417c.js" crossorigin="anonymous"></script>

    <title>Equipment</title>
</head>
<body>

<%@ include file="../components/navigation.jsp" %>

<%
    ArrayList<Equipment> equipment = (ArrayList<Equipment>) request.getAttribute(DispatchAttrs.EQUIPMENT);
    Map<Integer, ArrayList<String>> equipmentBodyGroups =
            (Map<Integer, ArrayList<String>>) request.getAttribute(DispatchAttrs.EQUIPMENT_BODY_GROUP_MAP);
    ArrayList<String> bodyGroups = (ArrayList<String>) request.getAttribute(DispatchAttrs.BODY_GROUPS);
%>

<section class="equipment container">
    <h1 class="equipment__h1">Equipment</h1>
    <%for (Equipment eq : equipment) {%>
    <div class="equipment-wrapper">
        <form class="needs-validation equipment__from" novalidate>
            <div class="equipment__img-wrapper">
                <img class="equipment__img" src="<%=Paths.EQUIPMENT_IMG + eq.getImgPath()%>"
                     alt="<%=eq.getName()%>">
            </div>
            <div class="equipment__text-wrapper">
                <div class="form-group">
                    <h2 class="equipment__h2"><%=eq.getName()%>
                    </h2>
                    <label for="r_name">Rewrite name with:</label>
                    <input name="r_name" type="text" class="form-control form-control--shadowed" id="r_name"
                           placeholder="Name">
                </div>
                <div class="form-group">
                    <h4 class="equipment__h4">Description</h4>
                    <p class="equipment__p">
                        <%=eq.getDescription()%>
                    </p>
                    <label for="r_websiteURL">Rewrite description with:</label>
                    <textarea name="r_websiteURL" type="text" class="form-control" id="r_websiteURL"
                              placeholder="Description"></textarea>
                </div>

                <div class="equipment-body-g">
                    <h4 class="equipment__h4">Body Groups</h4>
                    <div class="d-flex flex-row">
                        <ul class="list-unstyled equipment-body-g__ul">
                            <%for (String bodyGroup : equipmentBodyGroups.get(eq.getId())) {%>
                            <span class="equipment-li__span"><i class="fas fa-circle equipment-li__i"></i></span>
                            <li class="equipment__li">
                                <%=bodyGroup%>
                            </li>
                            <%}%>
                        </ul>
                        <div class="form-group">
                            <label for="selectBodyGroup">Select other body groups</label>
                            <select name="bodyGroups" id="selectBodyGroup" class="selectpicker" multiple
                                    data-live-search="true" data-size="5"
                                    title="Select..." data-selected-text-format="count > 2">
                                <%for (String bodyGroup : bodyGroups) {%>
                                <option value=""><%=bodyGroup%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="equipment-img-select">
                    <h4 class="equipment__h4">Image</h4>
                    <div class="custom-file ">
                        <input type="file" class="custom-file-input" id="r_customFile">
                        <label class="custom-file-label" for="r_customFile">Choose file</label>
                    </div>
                </div>

                <button value="rewriteEquipment" type="submit" class="button--primary">
                    Rewrite equipment
                </button>
            </div>
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
<script src="assets/js/bootstrap-select.min.js"></script>
</body>
</html>