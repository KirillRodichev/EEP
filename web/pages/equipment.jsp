<%@ page import="constants.DispatchAttrs" %>
<%@ page import="constants.Parameters" %>
<%@ page import="constants.Paths" %>
<%@ page import="model.Equipment" %>
<%@ page import="java.util.*" %>
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
    int size = (Integer) request.getAttribute(DispatchAttrs.SIZE);
%>

<div style="display: none" id="spinnerContainer" class="text-center spinner-wrapper">
    <div class="spinner-border" role="status">
        <span class="sr-only">Loading...</span>
    </div>
</div>

<section class="equipment container-fluid">
    <div class="content-wrapper">
        <div class="content-sidebar">
            <div class="sidebar-top">
                <h4>File manager</h4>
                <button id="download" class="sidebar-btn mb-3 mt-1">Free download</button>
                <form class="upload-xml-form" novalidate>
                    <div class="custom-file">
                        <input name="<%=DispatchAttrs.XML_FILE%>" type="file" class="custom-file-input upload-xml-input"
                               required>
                        <label class="custom-file-label" for="create-img-file">Choose xml</label>
                        <div class="invalid-feedback">
                            Please choose xml file
                        </div>
                    </div>
                    <button id="upload" type="submit" class="sidebar-btn mb-3 mt-3">Paid upload</button>
                </form>
                <hr>
                <h4>More equipment</h4>
                <form method="post">
                    <select name="bodyGroups" id="equipment-selector" class="selectpicker" data-live-search="true"
                            title="Select from existing">
                        <%for (Equipment eq : restEquipment) {%>
                        <option class="add-select-option" value="<%=eq.getId()%>"><%=eq.getName()%>
                        </option>
                        <%}%>
                    </select>
                    <input hidden id="addedId" name="<%=DispatchAttrs.EQUIPMENT_ID%>" type="text"/>
                    <input hidden name="<%=DispatchAttrs.GYM_ID%>" type="text" value="<%=gymID%>"/>
                    <button disabled type="button" id="add-button" class="sidebar-btn mb-3 mt-3">Add selected</button>
                </form>
                <form method="post">
                    <button type="button" class="sidebar-btn mb-3" data-toggle="collapse"
                            data-target="#addEquipmentCollapse" aria-expanded="false"
                            aria-controls="addEquipmentCollapse">
                        Add new
                    </button>
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
                               value="<%=i + 1%>">
                        <label class="form-check-label" for="inlineCheckbox<%=i + 1%>"><%=bodyGroups.get(i)%>
                        </label>
                        <span class="checkmark"></span>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <div class="content-insides">
            <div class="d-flex justify-content-between">
                <h1 class="equipment__h1">Equipment</h1>
                <nav aria-label="...">
                    <ul class="pagination"></ul>
                </nav>
            </div>
            <div class="equipment-collapse collapse" id="addEquipmentCollapse">
                <h2>Add new equipment</h2>
                <form id="create-form" class="needs-validation" enctype="multipart/form-data" method="post" novalidate>
                    <div class="from-row">
                        <div class="col-md-6 mb-3">
                            <div class="form-group">
                                <label for="create-name">Name</label>
                                <input name="<%=Parameters.EQUIPMENT_NAME%>" type="text"
                                       class="form-control form-control--shadowed"
                                       id="create-name" placeholder="Name" required>
                                <div class="invalid-feedback">
                                    Please write a name.
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="form-group">
                                <label for="create-description">Description</label>
                                <textarea name="<%=Parameters.EQUIPMENT_DESCRIPTION%>" type="text" class="form-control"
                                          required id="create-description" style="min-height: 150px"
                                          placeholder="Description"></textarea>
                                <div class="invalid-feedback">
                                    Please write description.
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="form-group">
                                <label for="create-bodyGroups">Body groups</label>
                                <select name="bodyGroupsSelect" id="create-bodyGroups" class="selectpicker" multiple
                                        data-live-search="true" data-size="5" required
                                        title="Select..." data-selected-text-format="count > 2">
                                    <%for (int i = 0; i < bodyGroups.size(); i++) {%>
                                    <option value="<%=i + 1%>">
                                        <%=bodyGroups.get(i)%>
                                    </option>
                                    <%}%>
                                </select>
                                <input id="bodyGroupsInput" name="<%=Parameters.BODY_GROUPS%>" type="text" hidden>
                                <div class="invalid-feedback">
                                    Please select body group.
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="custom-file">
                                <input name="<%=Parameters.EQUIPMENT_IMG_FILE%>" type="file"
                                       class="custom-file-input create-img-input" id="create-img-file" required>
                                <label class="custom-file-label" for="create-img-file">Choose file</label>
                                <div class="invalid-feedback">
                                    Please choose img file.
                                </div>
                            </div>
                        </div>
                        <button id="create-button" type="submit" class="button--secondary ml-3">Create</button>
                    </div>
                </form>
            </div>
            <div class="equipment-wrapper">
                <form enctype="multipart/form-data" method="post" class="needs-validation equipment__from update-form" novalidate>
                    <div class="equipment__img-wrapper">
                        <img src="<%=Paths.EQUIPMENT_IMG + equipment.get(0).getImgPath()%>" alt=""
                             class="equipment__img">
                    </div>
                    <div class="equipment__text-wrapper">
                        <div class="form-group">
                            <h4 class="equipment__h4">Name</h4>
                            <input name="<%=Parameters.EQUIPMENT_NAME%>" type="text" class="form-control form-control--shadowed input-name"
                                   id="name-update-0" value="<%=equipment.get(0).getName()%>">
                        </div>
                        <div class="form-group">
                            <h4 class="equipment__h4">Description</h4>
                            <textarea name="<%=Parameters.EQUIPMENT_DESCRIPTION%>" type="text" class="form-control textarea-description"
                                      id="description-update-0"><%=equipment.get(0).getDescription()%></textarea>
                        </div>
                        <div class="equipment-body-g">
                            <h4 class="equipment__h4">Body Groups</h4>
                            <div class="d-flex flex-row">
                                <ul class="list-unstyled equipment-body-g__ul">
                                    <%for (String bodyGroup : equipmentBodyGroups.get(equipment.get(1).getId())) {%>
                                    <span class="equipment-li__span">
                                        <i class="fas fa-circle equipment-li__i"></i>
                                    </span>
                                    <li data-parent="<%=equipment.get(1).getName()%>" class="equipment__li">
                                        <%=bodyGroup%>
                                    </li>
                                    <%}%>
                                </ul>
                                <div class="form-group">
                                    <label for="selectBodyGroup-0">Select other body groups</label>
                                    <select name="<%=Parameters.BODY_GROUPS%>" id="selectBodyGroup-0" class="selectpicker"
                                            multiple data-live-search="true" data-size="5" title="Select..."
                                            data-selected-text-format="count > 2">
                                        <%for (int i = 0; i < bodyGroups.size(); i++) {%>
                                        <option value="<%=i + 1%>">
                                            <%=bodyGroups.get(i)%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="equipment-img-select">
                            <h4 class="equipment__h4">Image</h4>
                            <div class="custom-file ">
                                <input name="<%=Parameters.EQUIPMENT_IMG_FILE%>" type="file" class="custom-file-input" id="img-file-update-0">
                                <label class="custom-file-label" for="img-file-update-0">
                                    Choose file
                                </label>
                            </div>
                        </div>
                        <div class="equipment-btns">
                            <form method="post">
                                <input value="<%=equipment.get(0).getId()%>" class="update dispatch-attr equipment-id"
                                       hidden type="text"/>
                                <button type="submit" class="button--primary">
                                    Rewrite
                                </button>
                            </form>
                            <form enctype="multipart/form-data" class="form-remove" method="post">
                                <input class="remove dispatch-attr equipment-id" name="<%=DispatchAttrs.EQUIPMENT_ID%>" hidden type="text"
                                       value="<%=equipment.get(0).getId()%>"/>
                                <input hidden name="<%=DispatchAttrs.GYM_ID%>" type="text" value="<%=gymID%>"/>
                                <button type="submit" class="button--secondary ml-3">
                                    Remove from the gym
                                </button>
                            </form>
                            <form enctype="multipart/form-data" class="form-delete" method="post">
                                <input class="delete dispatch-attr equipment-id" name="<%=DispatchAttrs.EQUIPMENT_ID%>" hidden type="text"
                                       value="<%=equipment.get(0).getId()%>"/>
                                <button type="submit" class="button--secondary ml-3">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </form>
            </div>
            <div class="equipment-wrapper">
                <form enctype="multipart/form-data" method="post" class="needs-validation equipment__from update-form" novalidate>
                    <div class="equipment__img-wrapper">
                        <img src="<%=Paths.EQUIPMENT_IMG + equipment.get(1).getImgPath()%>" alt=""
                             class="equipment__img">
                    </div>
                    <div class="equipment__text-wrapper">
                        <div class="form-group">
                            <h4 class="equipment__h4">Name</h4>
                            <input name="<%=Parameters.EQUIPMENT_NAME%>" type="text" class="form-control form-control--shadowed input-name"
                                   id="name-update-1" value="<%=equipment.get(1).getName()%>">
                        </div>
                        <div class="form-group">
                            <h4 class="equipment__h4">Description</h4>
                            <textarea name="<%=Parameters.EQUIPMENT_DESCRIPTION%>" type="text" class="form-control textarea-description"
                                      id="description-update-1"><%=equipment.get(1).getDescription()%></textarea>
                        </div>
                        <div class="equipment-body-g">
                            <h4 class="equipment__h4">Body Groups</h4>
                            <div class="d-flex flex-row">
                                <ul class="list-unstyled equipment-body-g__ul">
                                    <%for (String bodyGroup : equipmentBodyGroups.get(equipment.get(1).getId())) {%>
                                    <span class="equipment-li__span">
                                        <i class="fas fa-circle equipment-li__i"></i>
                                    </span>
                                    <li data-parent="<%=equipment.get(1).getName()%>" class="equipment__li">
                                        <%=bodyGroup%>
                                    </li>
                                    <%}%>
                                </ul>
                                <div class="form-group">
                                    <label for="selectBodyGroup-1">Select other body groups</label>
                                    <select name="<%=Parameters.BODY_GROUPS%>" id="selectBodyGroup-1" class="selectpicker"
                                            multiple data-live-search="true" data-size="5" title="Select..."
                                            data-selected-text-format="count > 2">
                                        <%for (int i = 0; i < bodyGroups.size(); i++) {%>
                                        <option value="<%=i + 1%>">
                                            <%=bodyGroups.get(i)%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="equipment-img-select">
                            <h4 class="equipment__h4">Image</h4>
                            <div class="custom-file ">
                                <input name="<%=Parameters.EQUIPMENT_IMG_FILE%>" type="file" class="custom-file-input" id="img-file-update-1">
                                <label class="custom-file-label" for="img-file-update-1">
                                    Choose file
                                </label>
                            </div>
                        </div>
                        <div class="equipment-btns">
                            <form method="post">
                                <input class="update dispatch-attr equipment-id" value="<%=equipment.get(1).getId()%>"
                                       name="<%=DispatchAttrs.EQUIPMENT_ID%>" hidden type="text"/>
                                <button value="rewriteEquipment" type="submit" class="button--primary">
                                    Rewrite
                                </button>
                            </form>
                            <form enctype="multipart/form-data" class="form-remove" method="post">
                                <input class="remove dispatch-attr equipment-id" name="<%=DispatchAttrs.EQUIPMENT_ID%>" hidden type="text"
                                       value="<%=equipment.get(1).getId()%>"/>
                                <input hidden name="<%=DispatchAttrs.GYM_ID%>" type="text" value="<%=gymID%>"/>
                                <button type="submit" class="button--secondary ml-3">
                                    Remove from the gym
                                </button>
                            </form>
                            <form enctype="multipart/form-data" class="form-delete" method="post">
                                <input class="delete dispatch-attr equipment-id" name="<%=DispatchAttrs.EQUIPMENT_ID%>" hidden type="text"
                                       value="<%=equipment.get(1).getId()%>"/>
                                <button type="submit" class="button--secondary ml-3">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <input id="gymID" hidden type="text" value="<%=gymID%>">
</section>

<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal--spaced">
            <h5 class="modal-title" id="modalCenterTitle"></h5>
            <div class="modal-body"></div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.html" %>

<!-- Optional JavaScript -->
<script src="assets/js/shrink-nav-on-scroll.js"></script>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>

<!-- Optional with JQERY -->
<script src="assets/js/fetch.js"></script>
<script src="assets/js/DOM.js"></script>
<script src="assets/js/pagination.js"></script>
<script src="assets/js/modal.js"></script>
<script src="assets/js/validation.js"></script>
<script src="assets/js/equipment.js"></script>
<script>createPagination(<%=size%>, <%=gymID%>)</script>

</body>
</html>
