<%-- 
    Document   : list
    Created on : Sep 10, 2026, 11:02:48 AM
    Author     : zbqb9x
--%>

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
            .dataTables_wrapper .dt-buttons {
                float:none;
                text-align:right;
            }
            .img3 {
                width: 55px;
                height: 18px;
            }
            .pending thead th {
                background-color: #f06a0a;
                color: #FFFFFF;
            }
            input.no-box {
                border: none;
                background: transparent;
                outline: none;
                width: 100%;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Maverick List</h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-3">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Hardware Type</th>
                                                    <th>Hardware ID</th>
                                                    <th>Module</th>
                                                    <th>Sub Module</th>
                                                    <th>Date</th>
                                                    <th>Status</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${maverickList}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                    <tr>
                                                        <td><c:out value="${parameterMasterLoop.index + 1}" /></td>
                                                        <td id="modal_delete_info_${parameterMaster.id2}"><c:out value="${parameterMaster.itemType}" /></td>
                                                        <td><c:out value="${parameterMaster.itemId}" /></td>
                                                        <td><span class="badge border border-secondary text-body fs-6"><c:out value="${parameterMaster.module}" /></span></td>
                                                        <td><c:out value="${parameterMaster.submodule}" /></td>
                                                        <c:set var="textColor" value="" />
                                                        <c:choose>
                                                            <c:when test="${parameterMaster.flag == '1'}">
                                                                <c:set var="textColor" value="green" />
                                                            </c:when>
                                                            <c:when test="${fn:contains(parameterMaster.module, 'oading')}">
                                                                <c:set var="textColor" value="purple" />
                                                            </c:when>
                                                            <c:when test="${fn:contains(parameterMaster.module, 'Registration')}">
                                                                <c:set var="textColor" value="red" />
                                                            </c:when>
                                                            <c:otherwise>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td style="color: ${textColor};"><c:out value="${parameterMaster.createdDate}" /></td>
                                                        <td style="color: ${textColor};"><c:out value="${parameterMaster.status}" /></td>
                                                        <td align="center">
                                                            <c:choose>
                                                                <c:when test="${parameterMaster.flag == '1'}">
                                                                    <a href="maverickdetails/${parameterMaster.id}/${parameterMaster.module}/${parameterMaster.id2}" title="Maverick Complete?"><i class="bi bi-search h3"></i></a>
                                                                </c:when>
                                                                <c:when test="${parameterMaster.flag == '0'}">
                                                                    <a href="maverickdetails/${parameterMaster.id}/${parameterMaster.module}/${parameterMaster.id2}" title="View Maverick details">
                                                                    <c:choose>
                                                                        <c:when test="${fn:contains(parameterMaster.module, 'oading')}">
                                                                            <i class="bi bi-motherboard h3"></i>
                                                                        </c:when>
                                                                        <c:when test="${fn:contains(parameterMaster.module, 'Registration')}">
                                                                            <i class="bi bi-postcard h3"></i>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <i class="bi bi-search h3"></i>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${parameterMaster.flag == '2'}">
                                                                    <a href="maverickdetails/${parameterMaster.id}/${parameterMaster.module}/${parameterMaster.id2}" title="Repair"><i class="bi bi-wrench-adjustable-circle h3" ></i></a>
                                                                </c:when>
                                                                <c:when test="${parameterMaster.flag == '3'}">
                                                                    <a href="maverickdetails/${parameterMaster.id}/${parameterMaster.module}/${parameterMaster.id2}" title="Item sent to Scrap"><i class="bi bi-recycle h3"></i></a>
                                                                </c:when>
                                                                <c:when test="${parameterMaster.flag == '4'}">
                                                                    <a href="maverickdetails/${parameterMaster.id}/${parameterMaster.module}/${parameterMaster.id2}" title="This one still no decided yet"><i class="bi bi-search h3"></i></a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="maverickdetails/scrap" title="Bukan button ini yang kita mahu"><i class="bi bi-eraser-fill h3"></i></a>
                                                                    <a href="maverickdetails/${parameterMaster.id}/${parameterMaster.module}/${parameterMaster.id2}" title="Patut tak jumpa yang ini"><i class="bi bi-search h3"></i></a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <!--ADD SOME MORE CONDITION IF NEEDED-->
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
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/customitem/dataTables.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.bootstrap.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/custom/custom-datatables.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/jszip.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/pdfmake.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/vfs_fonts.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.colVis.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                
            });

            $(function () {
                $("#customButtons1").DataTable({
                    lengthMenu: [
                        [10, 25, 50],
                        [10, 25, 50, "All"]
                    ],
                    language: {
                        lengthMenu: "Display _MENU_ Records Per Page",
                        info: "Showing _START_ to _END_ of _TOTAL_ total records"
                    },
                    dom: '<"top"Blfi>rt<"bottom"p><"clear">',
                    buttons: ["copy", "csv", "pdf", "print"]
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>