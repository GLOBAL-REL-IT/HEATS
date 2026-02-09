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
                width: 55px;
                height: 18px;
            }
            .pending thead th {
                background-color: #f06a0a;
                color: #FFFFFF;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/admin/user/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-plus-square'></i>&nbsp;&nbsp;Add User</a>
                    </div>
                </nav>
            </div>
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Configuration - <span style="color:#D97D55">User Management</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-3">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th><span>No</span></th>
                                                    <th><span>Firstname</span></th>
                                                    <th><span>Lastname</span></th>
                                                    <th><span>Title</span></th>
                                                    <th><span>Group</span></th>
                                                    <th><span>Login ID</span></th>
                                                    <th><span>Email</span></th>
                                                    <th><span>Oncid</span></th>
                                                    <th><span>Manage</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${userList}" var="user" varStatus="userLoop">
                                                <tr>
                                                    <td><c:out value="${userLoop.index+1}"/></td>
                                                <td id="modal_delete_info_${user.id}"><c:out value="${user.firstname}"/></td>
                                                <td><c:out value="${user.lastname}"/></td>
                                                <td><c:out value="${user.title}"/></td>
                                                <td>
                                                <c:if test="${not empty user.groupCode}">
                                                    <c:out value="${user.groupCode} - ${user.groupName}"/>
                                                </c:if>
                                                </td>
                                                <td><c:out value="${user.loginId}"/></td>
                                                <td><c:out value="${user.email}"/></td>
                                                <td><c:out value="${user.oncid}"/></td>
                                                <td align="center">
                                                    <a href="${contextPath}/admin/user/edit/${user.id}" class="table-link">
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

        <script src="${contextPath}/resources/statflow/vendor/datatables/custom/custom-datatables.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/jszip.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/pdfmake.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/vfs_fonts.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.colVis.min.js"></script>

        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select-custom.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                $.fn.center = function () {
                    this.css("position", "absolute");
                    this.css("top", ($(window).height() - this.height()) / 2 + $(window).scrollTop() + "px");
                    this.css("left", ($(window).width() - this.width()) / 2 + $(window).scrollLeft() + "px");
                    return this;
                };

                function showBlockUI() {
                    $.blockUI({
                        css: {
                            width: 'auto',
                            padding: '5px',
                            backgroundColor: '#fff',
                            '-webkit-border-radius': '10px',
                            '-moz-border-radius': '10px'
                        },
                        message: '<img src="${contextPath}/resources/private/img/loading_gedik.gif" width="100" />'
                    });
                    $('.blockUI.blockMsg').center();
                }

                function hideBlockUI() {
                    $.unblockUI();
                }

                function userSync(loginId) {
                    var url = '${contextPath}/admin/user/sync/' + loginId;
                    var data = {};

                    function success(res) {
                        hideBlockUI();
                        if (res.status) {
                            swal({
                                title: "Success",
                                text: res.statusMessage,
                                html: true,
                                type: "success",
                                showCancelButton: false,
                                closeOnConfirm: false
                            },
                                    function () {
                                        window.location.reload(true);
                                    });
                        } else {
                            swal('Error!', res.statusMessage, 'error');
                        }
                    }

                    $.get(url, data, success, 'json').fail(function (res) {
                        hideBlockUI();
                        swal('Error!', JSON.stringify(res, null, 4), 'error');
                    });
                }
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
                var deleteUrl = "${contextPath}/admin/user/delete/" + deleteId;
                var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }

        </script>
    </s:layout-component>
</s:layout-render>