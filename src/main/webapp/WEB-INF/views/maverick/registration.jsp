<%-- 
    Document   : registration
    Created on : Oct 2, 2026, 9:41:28 AM
    Author     : zbqb9x
--%>

<%--<%@page contentType="text/html;charset=UTF-8" %>--%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
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
            .content-wrapper {
                padding: 0 20px;
                height: calc(100vh - 10px);
                overflow: auto;
            }
            .section-header {
                background-color: #eaedf8;
                min-height: 40px;
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                position: relative;
                padding: 8px 45px;
                margin-bottom: 15px;
                font-weight: 600;
                text-align: center;
                border-radius: 3px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }
            .section-header:hover {
                background-color: #dce2f3;
            }
            .section-header .section-arrow {
                position: absolute;
                right: 15px;
                font-size: 16px;
                transition: transform 0.25s ease;
            }
            .section-header[aria-expanded="true"] .section-arrow {
                transform: rotate(180deg);
            }
            .section-content {
                padding-bottom: 5px;
            }
            @media (min-width: 768px) {
                .disposition-left {
                    padding-right: 20px;
                }
                .disposition-right {
                    padding-left: 20px;
                }
            }
            /* Hover */
            .section-header:hover {
                background-color: #dce2f3;
            }
            /* Open */
            .section-header[aria-expanded="true"],
            .section-header[aria-expanded="true"]:hover {
                background-color: #fd7e14;
                color: white;
            }
            /* Rotate arrow when open */
            .section-header[aria-expanded="true"] .section-arrow {
                transform: rotate(180deg);
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <div class="row">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/maverick/list" class="btn btn-outline-warning me-2" role="button"><i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <c:choose>
                    <c:when test="${data.flag eq '1' || data.flag eq '3'}">
                        <div class="col-sm-12 col-12">
                        </c:when>
                        <c:otherwise>
                            <div class="col-sm-8 col-12">
                            </c:otherwise>
                        </c:choose>
                        <fieldset disabled>
                            <div class="card mb-4">
                                <div class="card-header">
                                    <h5 class="card-title">Maverick Details - <span style="color:#D97D55">${data.module} [${jenis}]</span></h5>
                                </div>
                                <div class="card-body">
                                    <div class="row gx-3">
                                        <%@ include file="dispo_info.jsp" %>
                                        <c:choose>
                                            <c:when test="${jenis eq 'VM'}">
                                                <%@ include file="vm.jsp" %>
                                            </c:when>
                                            <c:when test="${jenis eq 'FT'}">
                                                <%@ include file="ft.jsp" %>
                                            </c:when>
                                        </c:choose>
                                        <%@ include file="log.jsp" %>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <c:choose>
                        <c:when test="${data.flag eq '0'}">
                            <%@ include file="dispo_action.jsp" %>
                        </c:when>
                        <c:when test="${data.flag eq '1' || data.flag eq '3'}">
                            <%--HIDE THIS PAGE, SINCE ALREADY BYPASS, SCRAP, OR COMPLETED--%> 
                        </c:when>
                        <c:when test="${data.flag eq '2'}">
                            <%@ include file="repair_action.jsp" %>
                        </c:when>
                        <c:otherwise>
                            <%--EMPTY PAGE HERE SINCE WE CANNOT DECIDE--%>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${data.flag != '1'}">

                    </c:if>
                </div>
                <div class="app-footer">
                    <span>© Bootstrap Gallery 2025</span>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            // THIS CODE TO MAKE SURE THE DATE DEFAULTED AS TODAY - START
            document.addEventListener("DOMContentLoaded", function () {
                setTodayIfEmpty("newDispositionDate");
                setTodayIfEmpty("repairDate");
            });

            function setTodayIfEmpty(elementId) {
                const input = document.getElementById(elementId);
                if (!input) {
                    return;
                }
                if (!input.value || input.value.trim() === "") {
                    const today = new Date();
                    const year = today.getFullYear();
                    const month = String(today.getMonth() + 1).padStart(2, "0");
                    const day = String(today.getDate()).padStart(2, "0");
                    input.value = year + "-" + month + "-" + day;
                }
            }
            // THIS CODE TO MAKE SURE THE DATE DEFAULTED AS TODAY - END
        </script>
    </s:layout-component>
</s:layout-render>