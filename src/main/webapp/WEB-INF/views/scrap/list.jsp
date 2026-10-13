<%-- 
    Document   : list
    Created on : Oct 13, 2026, 11:12:08 AM
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
            
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Scrap List</h5>
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
                                                    <th>Status</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${scrapList}" var="scrap" varStatus="listLoop">
                                                    <tr>
                                                        <td><c:out value="${listLoop.index + 1}" /></td>
                                                        <td id="modal_delete_info_${scrap.id}"><c:out value="${scrap.itemType}" /></td>
                                                        <td><c:out value="${scrap.itemName}" /></td>
                                                        <td><span class="badge border border-secondary text-body fs-6"><c:out value="${scrap.module}" /></span></td>
                                                        <td><c:out value="${scrap.subModule}" /></td>
                                                        <c:set var="textColor" value="" />
                                                        <c:choose>
                                                            <c:when test="${scrap.flag == '1'}">
                                                                <c:set var="textColor" value="green" />
                                                            </c:when>
                                                            <c:when test="${fn:contains(scrap.module, 'oading')}">
                                                                <c:set var="textColor" value="purple" />
                                                            </c:when>
                                                            <c:when test="${fn:contains(scrap.module, 'Registration')}">
                                                                <c:set var="textColor" value="red" />
                                                            </c:when>
                                                            <c:otherwise>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td style="color: ${textColor};"><c:out value="${scrap.status}" /></td>
                                                        <td align="center">
                                                            <c:choose>
                                                                <c:when test="${scrap.flag == '1'}">
                                                                    <a href="maverickdetails/${scrap.id}/${scrap.module}/${scrap.id}" title="Maverick Complete?"><i class="bi bi-clipboard-check h3"></i></a>
                                                                    <a href="url" title="Proceed Visual Inspection / Functional Test"><i class="bi bi-search h3"></i></a>
                                                                </c:when>
                                                                <c:when test="${scrap.flag == '0'}">
                                                                    <a href="maverickdetails/${scrap.id}/${scrap.module}/${scrap.id}" title="View Maverick details">
                                                                    <c:choose>
                                                                        <c:when test="${fn:contains(scrap.module, 'oading')}">
                                                                            <i class="bi bi-motherboard h3"></i>
                                                                        </c:when>
                                                                        <c:when test="${fn:contains(scrap.module, 'Registration')}">
                                                                            <i class="bi bi-postcard h3"></i>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <i class="bi bi-search h3"></i>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${scrap.flag == '2'}">
                                                                    <a href="maverickdetails/${scrap.id}/${scrap.module}/${scrap.id}" title="Repair"><i class="bi bi-wrench-adjustable-circle h3" ></i></a>
                                                                </c:when>
                                                                <c:when test="${scrap.flag == '3'}">
                                                                    <a href="maverickdetails/${scrap.id}/${scrap.module}/${scrap.id}" title="Item sent to Scrap"><i class="bi bi-recycle h3"></i></a>
                                                                </c:when>
                                                                <c:when test="${scrap.flag == '4'}">
                                                                    <a href="maverickdetails/${scrap.id}/${scrap.module}/${scrap.id}" title="This one still no decided yet"><i class="bi bi-search h3"></i></a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="maverickdetails/scrap" title="Bukan button ini yang kita mahu"><i class="bi bi-eraser-fill h3"></i></a>
                                                                    <a href="maverickdetails/${scrap.id}/${scrap.module}/${scrap.id}" title="Patut tak jumpa yang ini"><i class="bi bi-search h3"></i></a>
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