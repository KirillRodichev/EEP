<%@ page import="model.Equipment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="constants.DispatchAttrs" %>
<%@ page import="constants.Paths" %>
<%@ page import="java.util.List" %><%--
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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">

    <!-- Favicon -->
    <link rel="shortcut icon" href="favicon.ico">

    <!-- Roboto font -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,500,700&display=swap&subset=cyrillic"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@400;600;800&display=swap" rel="stylesheet">

    <!-- Custom styles -->
    <link rel="stylesheet" href="assets/css/main.css">

    <!-- FontAwesome kit -->
    <script src="https://kit.fontawesome.com/9ec0b3417c.js" crossorigin="anonymous"></script>

    <title>Equipment</title>
</head>
<body>

<%@ include file="../components/navigation.jsp" %>

<%
    List<Equipment> equipment = (ArrayList<Equipment>) request.getAttribute(DispatchAttrs.EQUIPMENT);
    Map<Integer, List<String>> equipmentBodyGroups =
            (Map<Integer, List<String>>) request.getAttribute(DispatchAttrs.EQUIPMENT_BODY_GROUP_MAP);
    List<String> bodyGroups = (List<String>) request.getAttribute(DispatchAttrs.BODY_GROUPS);
    List<Equipment> restEquipment = (List<Equipment>) request.getAttribute(DispatchAttrs.REST_EQUIPMENT);
    int gymID = (Integer) request.getAttribute(DispatchAttrs.GYM);
%>

<section class="equipment container-fluid">
    <div class="content-wrapper">
        <div class="content-sidebar">
            <div class="sidebar-top">
                <label for="equipment-selector">Add more equipment</label>
                <form action="addEquipment" method="post">
                    <select name="bodyGroups" id="equipment-selector" class="selectpicker" data-live-search="true"
                            title="Select...">
                        <%for (Equipment eq : restEquipment) {%>
                        <option class="add-select-option" value="<%=eq.getId()%>"><%=eq.getName()%></option>
                        <%}%>
                    </select>
                    <input hidden id="addedId" name="<%=DispatchAttrs.EQUIPMENT_ID%>" type="text" />
                    <input hidden name="<%=DispatchAttrs.GYM_ID%>" type="text" value="<%=gymID%>"/>
                    <button type="submit" id="add-button" class="sidebar-btn mb-3 mt-3">Add</button>
                </form>
                <hr>
                <h4>Filters</h4>
                <input class="search-input form-control" name="search" id="search" type="text"
                       placeholder="Search by name">
                <button type="button" id="search-button" class="sidebar-btn mt-3 mb-3">Search</button>
            </div>
            <div class="sidebar-item">
                <h5>BodyGroups</h5>
                <div class="collapse-item__buttons">
                    <button id="apply" class="sidebar-btn mr-3">Apply</button>
                    <button id="clear" class="sidebar-btn">Clear</button>
                </div>
                <div class="form-wrapper">
                    <%for (int i = 0; i < bodyGroups.size(); i++) {%>
                    <div class="form-check form-check-inline form-check--customized">
                        <input class="form-check-input" type="checkbox" id="inlineCheckbox<%=i + 1%>"
                               value="<%=bodyGroups.get(i)%>">
                        <label class="form-check-label" for="inlineCheckbox<%=i + 1%>"><%=bodyGroups.get(i)%>
                        </label>
                        <span class="checkmark"></span>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <div class="content-insides">
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
                            <label for="r_name<%=eq.getId()%>">Rewrite name with:</label>
                            <input name="r_name" type="text" class="form-control form-control--shadowed" id="r_name<%=eq.getId()%>"
                                   placeholder="Name">
                        </div>
                        <div class="form-group">
                            <h4 class="equipment__h4">Description</h4>
                            <p class="equipment__p">
                                <%=eq.getDescription()%>
                            </p>
                            <label for="r_websiteURL<%=eq.getId()%>">Rewrite description with:</label>
                            <textarea name="r_websiteURL" type="text" class="form-control" id="r_websiteURL<%=eq.getId()%>"
                                      placeholder="Description"></textarea>
                        </div>
                        <div class="equipment-body-g">
                            <h4 class="equipment__h4">Body Groups</h4>
                            <div class="d-flex flex-row">
                                <ul class="list-unstyled equipment-body-g__ul">
                                    <%for (String bodyGroup : equipmentBodyGroups.get(eq.getId())) {%>
                                    <span class="equipment-li__span"><i
                                            class="fas fa-circle equipment-li__i"></i></span>
                                    <li data-parent="<%=eq.getName()%>" class="equipment__li">
                                        <%=bodyGroup%>
                                    </li>
                                    <%}%>
                                </ul>
                                <div class="form-group">
                                    <label for="selectBodyGroup<%=eq.getId()%>">Select other body groups</label>
                                    <select name="bodyGroups" id="selectBodyGroup<%=eq.getId()%>" class="selectpicker" multiple
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
                                <input type="file" class="custom-file-input" id="r_customFile<%=eq.getId()%>">
                                <label class="custom-file-label" for="r_customFile<%=eq.getId()%>">Choose file</label>
                            </div>
                        </div>
                        <div class="equipment-btns">
                            <form action="updateEquipment" method="post">
                                <input hidden name="<%=DispatchAttrs.EQUIPMENT_ID%>" type="text" value="<%=eq.getId()%>" />
                                <input hidden name="<%=DispatchAttrs.GYM_ID%>" type="text" value="<%=gymID%>"/>
                                <button value="rewriteEquipment" type="submit" class="button--primary">
                                    Rewrite
                                </button>
                            </form>
                            <form action="deleteEquipment" method="post">
                                <input hidden name="<%=DispatchAttrs.EQUIPMENT_ID%>" type="text" value="<%=eq.getId()%>" />
                                <input hidden name="<%=DispatchAttrs.GYM_ID%>" type="text" value="<%=gymID%>"/>
                                <button value="deleteEquipment" type="submit" class="button--secondary ml-3">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </form>
            </div>
            <%}%>
        </div>
    </div>
</section>

<%@ include file="../components/footer.html" %>

<!-- Optional JavaScript -->
<script src="assets/js/shrink-nav-on-scroll.js"></script>
<script src="assets/js/equipment.js"></script>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
</body>
</html>
