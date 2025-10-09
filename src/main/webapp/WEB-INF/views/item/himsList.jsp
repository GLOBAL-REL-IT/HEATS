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

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">HIMS Inventory List</h5>
                        </div>
                        <div class="card-body">
                            <!-- Table start -->
                            <div class="table-responsive">
                                <table id="dataTable2" class="table custom-table">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="d-none d-md-table-cell">Hardware Type</th>
                                            <th>Hardware ID</th>
                                            <th class="d-none d-md-table-cell">Quantity</th>
                                            <th class="d-none d-md-table-cell">Status</th>
                                            <th class="d-none d-md-table-cell">Inventory Date</th>
                                            <th>Inventory Location</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestList}" var="ftpdata" varStatus="ftpListLoop">
                                            <tr>                                            
                                                <td><c:out value="${ftpListLoop.index+1}"/></td>
                                                <td class="d-none d-md-table-cell"><c:out value="${ftpdata.equipmentType}"/></td>
                                                <td><c:out value="${ftpdata.equipmentId}"/></td>
                                                <td class="d-none d-md-table-cell"><c:out value="${ftpdata.quantity}"/></td>
                                                <td class="d-none d-md-table-cell"><c:out value="${ftpdata.status}"/></td>
                                                <td class="d-none d-md-table-cell"><c:out value="${ftpdata.inventoryDate}"/></td>
                                                <td><c:out value="${ftpdata.inventoryShelf}"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- Table end -->
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
            <span>© HEATs 2025</span>
        </div>
    </div>
</s:layout-component>
<s:layout-component name="page_js">
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
            $("#dataTable2").DataTable({
                lengthMenu: [
                    [10, 25, 50],
                    [10, 25, 50, "All"]
                ],
                language: {
                    lengthMenu: "Display _MENU_ Records Per Page",
//                    info: "Showing Page _PAGE_ of _PAGES_",
                }
                ,
                dom: "Blfrtip",
		buttons: ["copy", "csv", "print"]
            });
        });

//            $(function () {
//                $("#scrollVertical2").DataTable({
//                    scrollY: "635px",
//                    scrollCollapse: true,
//                    paging: false,
//                    bInfo: false
//                });
//            });
//
//            $(document).ready(function () {
//
//
//                var validator = $("#edit_hardwarequest_form").validate({
//                    rules: {
//                        finalQty: {
//                            required: true,
//                            number: true
//                        },
//                        shelf: {
//                            required: true
//                        },
//                        stressTypeMidPoint: {
//                            required: "#checkboxPS:checked"
//                        }
//                    }
//                });
//
//            });
    </script>
</s:layout-component>
</s:layout-render>