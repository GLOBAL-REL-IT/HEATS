<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
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
                /*border: 1px solid $input-border-focus !important;*/
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
                /*border: 1px solid $input-border-focus !important;*/
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }
            .no-border {
                border: 0;
                box-shadow: none;  /*You may want to include this as bootstrap applies these styles too 
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
        <div class="content-wrapper">
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <c:if test="${userItemAdd == 'Yes'}"><a href="${contextPath}/admin/bibActivity/" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-upload'></i>&nbsp;&nbsp;Batch Upload</a></c:if>
                    </div>
                </nav>
            </div>
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Activity Configuration for BIB/ BIB Cards</h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-3">
                                <div class="container-fluid justify-content-start">
                                    <a href="${contextPath}/admin/bibActivity/bib" class="btn btn-outline-success me-2" role="button">
                                    <i class='bi bi-motherboard'></i>&nbsp;&nbsp;BIB</a>
                                <a href="${contextPath}/admin/bibActivity/bibCard" class="btn btn-outline-success me-2" role="button">
                                    <i class='bi bi-credit-card-2-front'></i>&nbsp;&nbsp;BIB Card</a>
                            </div>
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
                                                <th><span>Stress Type</span></th>
                                                <th class="col-1"><span>VI</span></th>
                                                <th class="col-1"><span>BIB Test</span></th>
                                                <th class="col-1"><span>BIB DAQ</span></th>
                                                <th class="col-1"><span>Manual Test</span></th>
                                                <th class="col-1"><span>Leakage Test</span></th>
                                                <th class="col-1"><span>PS Leakage Test</span></th>
                                                <th class="col-1"><span>Winchester Chamber Leakage Test</span></th>
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
                                                    <td><c:out value="${parameterMaster.stressType}"/></td>
                                                    <td><c:out value="${parameterMaster.vi}"/></td>
                                                    <td><c:out value="${parameterMaster.bibTest}"/></td>
                                                    <td><c:out value="${parameterMaster.bibDaqTest}"/></td>
                                                    <td><c:out value="${parameterMaster.manualTest}"/></td>
                                                    <td><c:out value="${parameterMaster.leakageTest}"/></td>
                                                    <td><c:out value="${parameterMaster.psLeakageTest}"/></td>
                                                    <td><c:out value="${parameterMaster.winchesterChamberLeakageTest}"/></td>
                                                    <td align="center">
                                                        <a href="${contextPath}/admin/bibActivity/edit/${parameterMaster.id}" class="table-link" title="Edit">
                                                            <i class="bi bi-box-arrow-in-right h3"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
                        info: "Showing _START_ to _END_ of _TOTAL_ total records",
                    },
//                    dom: "Blfrtip",
                    dom: '<"top"Blfi>rt<"bottom"p><"clear">',
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
            </script>
    </s:layout-component>
</s:layout-render>