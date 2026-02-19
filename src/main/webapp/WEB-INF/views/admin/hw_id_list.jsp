<%-- 
    Document   : hwIdList
    Created on : Feb 5, 2026, 9:30:55 AM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!-- Data Tables -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .fw-bolder {
                -webkit-text-stroke: 0.5px currentColor;
                text-stroke: 0.5px currentColor;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/admin/hw/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-plus-square'></i>&nbsp;&nbsp;Add Hardware ID Config</a>
                    </div>
                </nav>
            </div>
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Configuration - <span style="color:#D97D55">Hardware ID List</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-3">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th><span>No</span></th>
                                                    <th><span>Item Type</span></th>
                                                    <th><span>Sub Type</span></th>
                                                    <th><span>Same Item ID?</span></th>
                                                    <!--<th><span>Supplier</span></th>-->
                                                    <th><span>Assembly No</span></th>
                                                    <!--<th><span>Revision</span></th>-->
                                                    <th><span>Mfg Date</span></th>
                                                    <!--<th><span>Component</span></th>-->
                                                    <th><span>Event</span></th>
                                                    <th><span>Part Number</span></th>
                                                    <th><span>ALU?</span></th>
                                                    <th><span>Shelf Time?</span></th>
                                                    <th><span>Manage</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${itemList}" var="user" varStatus="userLoop">
                                                <tr>
                                                    <td><c:out value="${userLoop.index+1}"/></td>
                                                <td id="modal_delete_info_${user.id}"><c:out value="${user.itemType}"/></td>
                                                <td><c:out value="${user.subType}"/></td>
                                                <td><c:out value="${user.sameItemId == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                <!--<td><c:out value="${user.supplier == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>-->
                                                <td><c:out value="${user.assemblyNo == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                <!--<td><c:out value="${user.revision == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>-->
                                                <td><c:out value="${user.mfgDate == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                <!--<td><c:out value="${user.component == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>-->
                                                <td><c:out value="${user.event == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.partNumber == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.alu == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.shelfTime == 'No' ? '<i class=\"bi bi-x-square h4 fw-bolder\" style=\"color:red\"></i>' : '<i class=\"bi bi-check2-square h4 fw-bolder\" style=\"color:green\"></i>'}" escapeXml="false"/></td>
                                                
<!--                                                <td><c:out value="${user.sameItemId == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.assemblyNo == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.mfgDate == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.event == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.partNumber == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.alu == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>
                                                <td><c:out value="${user.shelfTime == 'No' ? '<span class=\"badge border border-2 border-danger text-danger px-3 py-2\">No</span>' : '<span class=\"badge border border-2 border-success text-success px-3 py-2\">Yes</span>'}" escapeXml="false"/></td>-->
                                                
                                                <td align="center">
                                                    <a href="${contextPath}/admin/hw/edit/${user.id}" class="table-link">
                                                        <i class="bi bi-box-arrow-in-right h3"></i>
                                                    </a>
                                                    <a modaldeleteid="${user.id}" title="Delete" data-bs-toggle="modal" data-bs-target="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                        <i class="bi bi-trash h3" style="color:red"></i>
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

        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.bootstrap.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(function () {
                $("#customButtons1").DataTable({
                    lengthMenu: [
                        [10, 25, 50],
                        [10, 25, 50, "All"]
                    ],
                    language: {
                        lengthMenu: "Display _MENU_ Records Per Page",
                        info: "Showing Page _PAGE_ of _PAGES_"
                    },
                    dom: "Blfrtip",
                    buttons: ["copy", "csv", "pdf", "print"]
                });
            });

            function modalDelete(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/admin/hw/delete/" + deleteId;
                var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }
        </script>
    </s:layout-component>
</s:layout-render>