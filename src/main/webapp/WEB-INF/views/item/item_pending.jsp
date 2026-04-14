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
                        <a href="${contextPath}/hw" class="btn btn-outline-warning me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
            </div>
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">

                <div class="col-sm-12 col-12">

                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">Pending VM / Functional Test for BIB & BIB Cards</span></h5>
                            <!--<h5 class="card-title">Hardware Module - <span style="color:#D97D55">New Hardware Registration</span></h5>-->
                        </div>
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <!--                                <div class="col-10 mb-3">
                                                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                                                        style="border-left-width: 3px !important;">
                                                                        <i class="bi bi-list-ul me-2"></i>List of Parameter
                                                                    </h6>
                                                                </div>-->
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th><span>No</span></th>
                                                    <th><span>Item Type</span></th>
                                                    <th><span>Sub Type</span></th>
                                                    <th><span>Item ID</span></th>
                                                    <th><span>Item Name</span></th>
                                                    <th><span>Assembly ID</span></th>
                                                    <th><span>Registered By</span></th>
                                                    <th><span>Registered Date</span></th>
                                                    <th><span>Status</span></th>
                                                    <th class="col-1"><span>Manage</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${item}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                    <tr>
                                                        <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                        <td><c:out value="${parameterMaster.itemType}"/></td>
                                                        <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.subType}"/></td>
                                                        <td><c:out value="${parameterMaster.itemId}"/></td>
                                                        <td><c:out value="${parameterMaster.itemName}"/></td>
                                                        <td><c:out value="${parameterMaster.assemblyId}"/></td>
                                                        <td><c:out value="${parameterMaster.createdBy}"/></td>
                                                        <td><c:out value="${parameterMaster.createdDate}"/></td>
                                                        <td><c:out value="${parameterMaster.status}"/></td>
                                                        <td align="center">
                                                            <c:set var="String1" value="${parameterMaster.activityId}"/>
                                                            <c:if test="${userItemActConfig == 'Yes'}">
                                                                <c:choose>
                                                                    <c:when test="${empty String1}">
                                                                        <c:if test="${userItemActAdd == 'Yes'}">
                                                                            <a href="${contextPath}/hw/item/addActivity/${parameterMaster.id}" class="table-link" title="Activity Config">
                                                                                <i class="bi bi-toggles h3"></i>
                                                                            </a>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${userItemActEdit == 'Yes'}">
                                                                            <a href="${contextPath}/hw/item/editActivity/${parameterMaster.activityId}" class="table-link" title="Activity Config">
                                                                                <i class="bi bi-toggles h3"></i>
                                                                            </a>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:if>
                                                            <c:set var="String" value="${parameterMaster.status}"/>
                                                            <c:choose>
                                                                <c:when test="${(fn:contains(String, 'Activity Selection'))}">
                                                                    <a href="${contextPath}/hw/item/addActivity/${parameterMaster.id}" class="table-link" title="Manage">
                                                                        <i class="bi bi-box-arrow-in-right h3"></i>
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="${contextPath}/hw/item/add2/${parameterMaster.id}" class="table-link" title="Manage">
                                                                        <i class="bi bi-box-arrow-in-right h3"></i>
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
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
                                                    $(function () {
                                                        $("#customButtons1").DataTable({
                                                            lengthMenu: [
                                                                [10, 25, 50],
                                                                [10, 25, 50, "All"],
                                                            ],
                                                            language: {
                                                                lengthMenu: "Display _MENU_ Records Per Page",
//                                                                info: "Showing Page _PAGE_ of _PAGES_",
                                                                info: "Showing _START_ to _END_ of _TOTAL_ total records",
                                                            },
                                                            dom: "Blfrtip",
                                                            buttons: ["copy", "csv", "pdf", "print"],
                                                        });
                                                    });


                                                    function modalDelete(e) {
                                                        var deleteId = $(e).attr("modaldeleteid");
                                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                        var deleteUrl = "${contextPath}/admin/parameterMaster/delete/" + deleteId;
                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                    }
//                                                    $(document).ready(function () {
//                                                        $('.js-example-basic-single').select2();
//                                                    });

        </script>
</s:layout-component>
</s:layout-render>