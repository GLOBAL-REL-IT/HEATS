<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!-- Data Tables -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
        <!-- Bootstrap Select CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">

<!--        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/customitem/dataTables.dataTables.css"/>
<link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.min.css"/>-->
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
                table thead {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
                table tbody {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
            }
            .dataTables_wrapper .dt-buttons {
                float:none;
                text-align:right;
            }

            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
                box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
            }

            .select2-dropdown-open .select2-choice {
                border-bottom: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open.select2-drop-above .select2-choice,
            .select2-dropdown-open.select2-drop-above .select2-choices {
                border: 1px solid $input-border-focus !important;
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .no-border {
                border: 0;
                box-shadow: none;  /*You may want to include this as bootstrap applies these styles too */
            }

            span.tab-space {
                padding-left:20em;
            }

            .move-left {
                width: auto;
                box-shadow: none;
            }

            .form-group.required .form-label:after {
                content:"*";
                color:red;
            }

            .img3 {
                width: 55px; /* Sets a fixed width */
                height: 18px; /* Sets a fixed height */
            }

            .pending thead th {
                background-color: #f06a0a; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/admin/user" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-10 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Configuration - <span style="color:#D97D55">Edit User</span></h5>
                        </div>

                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/admin/user/update" method="post">
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="loginId">Login ID</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="loginId" name="loginId" placeholder="Login ID" value="${user.loginId}" readonly>
                                                <input type="hidden" name="userId" value="${user.id}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="fullname">Name</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Name" value="${user.firstname} ${user.lastname}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="email">Email</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="email" name="email" placeholder="Email" value="${user.email}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="subType">Group</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <select class="js-example-basic-single" id="groupId" name="groupId"
                                                        style="width: 100%">
                                                    <option value="" selected="">Select Group...</option>
                                                    <c:forEach items="${userGroupList}" var="group">
                                                        <option value="${group.id}" ${group.selected}>${group.code} - ${group.name}</option>
                                                    </c:forEach>
                                                </select>                                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="subType">Active</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <select id="isActive" name="isActive" class="form-control">
                                                    <option value="0" <c:if test="${user.isActive == '0'}">selected=""</c:if>>Inactive</option>
                                                    <option value="1" <c:if test="${user.isActive == '1'}">selected=""</c:if>>Active</option>
                                                </select>                                       
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Update</button>
                                </div>
                                <!-- Form actions end -->
                            </form>
                            <!-- Row end -->

                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <div class="col-sm-10 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Configuration - <span style="color:#D97D55">Edit User (Access Control)</span></h5>
                        </div>

                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/admin/user/updateAccess" method="post">
                                <div class="col-12 mb-1">
                                    <h6 class="fw-semibold mb-1 border-start border-primary ps-2"style="border-left-width: 3px !important;">Hardware Module</h6>
                                </div>
                                <div class="col-12">
                                    <!--<div class="card mb-4">-->
                                    <!--<div class="card-body">-->
                                    <div class="form-check form-check-inline">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input class="form-check-input" type="checkbox" id="itemAdd" name="itemAdd" value="Yes" <c:if test="${user.itemAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Item</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemEdit" name="itemEdit" value="Yes" <c:if test="${user.itemEdit == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Edit Item</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemDelete" name="itemDelete" value="Yes" <c:if test="${user.itemDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Item</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemHwAdd" name="itemHwAdd" value="Yes" <c:if test="${user.itemHardwareAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Hardware ID</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemHwEdit" name="itemHwEdit" value="Yes" <c:if test="${user.itemHardwareEdit == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Edit Hardware ID</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemHwDelete" name="itemHwDelete" value="Yes" <c:if test="${user.itemHardwareDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Hardware ID</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemActConfig" name="itemActConfig" value="Yes" <c:if test="${user.itemActivityConfig == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Access Activity Config</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemActAdd" name="itemActAdd" value="Yes" <c:if test="${user.itemActivityAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Activity Config</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemActEdit" name="itemActEdit" value="Yes" <c:if test="${user.itemActivityEdit == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Edit Activity Config</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemMovement" name="itemMovementAdd" value="Yes" <c:if test="${user.itemMovementAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Item Movement</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="itemSfRecell" name="itemSfRecall" value="Yes" <c:if test="${user.itemSfRecall == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Recall Item from SF</label>
                                    </div>
                                    <!--</div>-->
                                    <!--</div>-->
                                </div>
                                <!--eqpt-->
                                <div class="col-12 mb-1">
                                    <h6 class="fw-semibold mb-1 border-start border-primary ps-2"style="border-left-width: 3px !important;">Equipment Module</h6>
                                </div>
                                <div class="col-12">
                                    <!--<div class="card mb-4">-->
                                    <!--<div class="card-body">-->
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptAdd" name="eqptAdd" value="Yes" <c:if test="${user.eqptAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptEdit" name="eqptEdit" value="Yes" <c:if test="${user.eqptEdit == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Edit Eqpt</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptDelete" name="eqptDelete" value="Yes" <c:if test="${user.eqptDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Eqpt</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptFamilyAdd" name="eqptFamilyAdd" value="Yes" <c:if test="${user.eqptFamilyAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt Family</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptFamilyDelete" name="eqptFamilyDelete" value="Yes" <c:if test="${user.eqptFamilyDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Eqpt Family</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptFamilyAddGlobal" name="eqptFamilyAddGlobal" value="Yes" <c:if test="${user.eqptFamilyAddGlobal == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt Family into Global List</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptRelTestGroupAdd" name="eqptRelTestGroupAdd" value="Yes" <c:if test="${user.eqptRelTestGroupAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt Rel Test Group</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptRelTestGroupDelete" name="eqptRelTestGroupDelete" value="Yes" <c:if test="${user.eqptRelTestGroupDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Eqpt Rel Test Group</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptRelTestGroupAddGlobal" name="eqptRelTestGroupAddGlobal" value="Yes" <c:if test="${user.eqptRelTestGroupAddGlobal == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt Rel Test Group into Global List</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptTechAdd" name="eqptTechAdd" value="Yes" <c:if test="${user.eqptTechAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt Tech</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptTechDelete" name="eqptTechDelete" value="Yes" <c:if test="${user.eqptTechDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Eqpt Tech</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptMonAdd" name="eqptMonAdd" value="Yes" <c:if test="${user.eqptMonAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt Monitoring</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptMonDelete" name="eqptMonDelete" value="Yes" <c:if test="${user.eqptMonDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Eqpt Monitoring</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptViMonAdd" name="eqptViMonAdd" value="Yes" <c:if test="${user.eqptViMonAdd == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Add Eqpt VI Monitoring</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" id="eqptViMonDelete" name="eqptViMonDelete" value="Yes" <c:if test="${user.eqptViMonDelete == 'Yes'}">checked</c:if>>
                                        <label class="form-check-label" for="inlineCheckbox1">Delete Eqpt VI Monitoring</label>
                                    </div>
                                    <!--</div>-->
                                    <!--</div>-->
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Update</button>
                                </div>
                                <!-- Form actions end -->
                            </form>
                            <!-- Row end -->

                        </div>
                    </div>
                    <!-- Card end -->
                </div>
            </div>
            <!-- Row end -->


        </div>
        <!-- Content wrapper end -->

        <!-- App Footer start -->
        <div class="app-footer">
            <img class="img3" src="${contextPath}/resources/onsemi logo.webp" alt="onsemi">
            <span>© HEATs 2025</span>
        </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/customitem/dataTables.js"></script>

        <!-- Data Tables -->
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.bootstrap.min.js"></script>

        <!-- Custom Data tables -->
        <script src="${contextPath}/resources/statflow/vendor/datatables/custom/custom-datatables.js"></script>

        <!-- DataTable Buttons -->
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/jszip.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/pdfmake.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/vfs_fonts.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.colVis.min.js"></script>

        <!-- Bootstrap Select JS -->
        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select-custom.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>

            $(document).ready(function () {
                $('.js-example-basic-single').select2();
            });

            $(function () {
                $("#customButtons1").DataTable({
                    lengthMenu: [
                        [10, 25, 50],
                        [10, 25, 50, "All"],
                    ],
                    language: {
                        lengthMenu: "Display _MENU_ Records Per Page",
                        info: "Showing Page _PAGE_ of _PAGES_",
                    },
                    dom: "Blfrtip",
                    buttons: ["copy", "csv", "pdf", "print"],
                });
            });


            function modalDelete(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/admin/aluConfig/delete/" + deleteId;
                var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }
        </script>
    </s:layout-component>
</s:layout-render>