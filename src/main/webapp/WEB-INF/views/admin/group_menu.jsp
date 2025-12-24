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
        <!-- Date Range CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/daterange/daterange.css">
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
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Group - Access Per Group List</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form id="admin_group_access" action="${contextPath}/admin/group/menu/save" method="post">
                                <div class="clearfix">
                                    <div class="form-group">
                                        <input type="hidden" id="selectedGroup" name="groupId" value="${groupId}" >
                                        <a href="${contextPath}/admin/group" class="btn btn-outline-warning me-2"><i class="bi bi-arrow-bar-left"></i> Back</a>
                                        <button type="reset" id="selectedGroupReset" class="btn btn-secondary">Reset</button>
                                        <button type="submit" class="btn btn-primary">Save</button>
                                    </div>
                                </div>
                                        &nbsp;
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th style="width: 70px;"><span><input id="group_access_all" type="checkbox" class="group_access"></span></th>
                                                <th style="width: 200px;"><span>Menu</span>
                                                <th style="width: 50px;"><span>&nbsp;</span></th>
                                                <th style="width: 200px;"><span>Sub Menu</span></th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${empty userGroupAccessList}">
                                            <tr>
                                                <td colspan="5">
                                                    <p align="center" style="padding-top: 6px; font-size: 13px;">Please select group!</p>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:forEach items="${userGroupAccessList}" var="access" varStatus="accessLoop">
                                            <tr>
                                                <td style="padding-left: 25px;">
                                            <c:if test="${access.parentCode == '0'}">
                                                <input id="code_<c:out value="${access.code}"/>" parentcode="<c:out value="${access.parentCode}"/>" code="<c:out value="${access.code}"/>" type="checkbox" name="groupAccess" value="<c:out value="${access.menuId}"/>" class="group_access group_access_count parent_<c:out value="${access.parentCode}"/>" <c:out value="${access.selected}"/>>
                                            </c:if>
                                            </td>
                                            <td>
                                                <div class="form-group" style="margin-top: 0px; margin-bottom: 0px;">
                                                    <c:if test="${access.parentCode == '0'}">
                                                        <c:out value="${access.name}"/>
                                                    </c:if>
                                                </div>
                                            </td>
                                            <td>
                                            <c:if test="${access.parentCode != '0'}">
                                                <input id="code_<c:out value="${access.code}"/>" parentcode="<c:out value="${access.parentCode}"/>" code="<c:out value="${access.code}"/>" type="checkbox" name="groupAccess" value="<c:out value="${access.menuId}"/>" class="group_access group_access_count parent_<c:out value="${access.parentCode}"/>" <c:out value="${access.selected}"/>>  
                                            </c:if>
                                            </td>
                                            <td>
                                            <c:if test="${access.parentCode != '0'}">
                                                <c:out value="${access.name}"/>
                                            </c:if>
                                            </td>
                                            <td>
                                                &nbsp;
                                            </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
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
    </s:layout-component>
    <s:layout-component name="page_js">
        <!-- Date Range JS -->
        <script src="${contextPath}/resources/statflow/vendor/daterange/daterange.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/daterange/custom-daterange.js"></script>

        <!-- Data Tables -->
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.bootstrap.min.js"></script>

        <!-- Custom Data tables -->
        <script src="${contextPath}/resources/statflow/vendor/datatables/custom/custom-datatables.js"></script>

        <!-- JQuery Validation -->
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>

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
                $(".group_access_count").change(function () {
                    group_access();
                    if ($(this).attr("parentCode") === "0") {
                        unchecked_child_access($(this).attr("code"));
                    } else {
                        var parentCode = $(this).attr("parentCode");
                        if ($(this).prop("checked")) {
                            $("#code_" + parentCode).prop("checked", true);
                        } else {
                            if ($(".parent_" + parentCode + ":checked").length === 0) {
                                $("#code_" + parentCode).prop("checked", false);
                            } else {
                                $("#code_" + parentCode).prop("checked", true);
                            }
                        }
                    }
                    ;
                });
                $("#group_access_all").change(function () {
                    if ($("#group_access_all").prop("checked")) {
                        $(".group_access").prop("checked", true);
                    } else {
                        $(".group_access").prop("checked", false);
                    }
                });
                $("#selectedGroupReset").click(function (event) {
                    event.preventDefault();
                    $(this).closest('form').get(0).reset();
                    group_access();
                });
                function group_access() {
                    if ($('.group_access_count:checked').length === $('.group_access_count').length) {
                        $("#group_access_all").prop("checked", true);
                    } else {
                        $("#group_access_all").prop("checked", false);
                    }
                }
                function unchecked_child_access(code) {
                    if ($("#code_" + code).prop("checked")) {
                        $(".parent_" + code).prop("checked", true);
                    } else {
                        $(".parent_" + code).prop("checked", false);
                    }
                    group_access();
                }
                group_access();
            });
        </script>
    </s:layout-component>
</s:layout-render>