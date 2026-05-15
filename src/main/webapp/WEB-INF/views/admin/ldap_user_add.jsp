<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
        <!-- Bootstrap Select CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/private/css/sweetalert.css">
        <!--<link rel="stylesheet" href="bower_components/sweetalert/dist/sweetalert.css">-->
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
    <s:layout-component name="page_container">\

        <div class="content-wrapper">
            <!-- Row start -->
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/admin/user" class="btn btn-outline-warning me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
            </div>
            <!-- Row end -->

            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Configuration - <span style="color:#D97D55">Add User</span></h5>
                        </div>

                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/admin/user/add" method="post">
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="loginId">Login ID</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" name="loginId" placeholder="Search by Login ID">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="selectedGroup">Group</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <select class="js-example-basic-single" id="selectedGroup" name="selectedGroup" style="width: 100%">
                                                    <option value="" selected="">Assign User Group</option>
                                                    <c:forEach items="${userGroupList}" var="group">
                                                        <option value="${group.id}" ${group.selected}>${group.code} - ${group.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Search</button>
                                    <!--</div>-->
                                </div>
                                <!-- Form actions end -->
                            </form>
                            <!-- Row end -->

                        </div>
                    </div>
                    <!-- Card end -->
                </div>
            </div>
            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <!--                                <div class="col-12 mb-3">
                                                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                                                        style="border-left-width: 3px !important;">
                                                                        <i class="bi bi-list-ul me-2"></i>List of Parameter Details
                                                                    </h6>
                                                                </div>-->
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th><span>No</span></th>
                                                    <th><span>Firstname</span></th>
                                                    <th><span>Lastname</span></th>
                                                    <th><span>Title</span></th>
                                                    <th><span>Login ID</span></th>
                                                    <th><span>Email</span></th>
                                                    <th><span>Oncid</span></th>
                                                    <th><span>Add</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${userList}" var="user" varStatus="userLoop">
                                                <tr>
                                                    <td><c:out value="${userLoop.index+1}"/></td>
                                                <td id="firstname_${user.loginId}"><c:out value="${user.firstname}"/></td>
                                                <td id="lastname_${user.loginId}"><c:out value="${user.lastname}"/></td>
                                                <td id="title_${user.loginId}"><c:out value="${user.title}"/></td>
                                                <td id="login_id_${user.loginId}"><c:out value="${user.loginId}"/></td>
                                                <td id="email_${user.loginId}"><c:out value="${user.email}"/></td>
                                                <td id="oncid_${user.loginId}"><c:out value="${user.oncid}"/></td>
                                                <td align="center">
                                                    <a addid="${user.loginId}" href="#add" class="table-link sync_user">
                                                        <i class="bi bi-plus-square h3"></i>
                                                    </a>
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

                $(document).ready(function () {
                    $('.js-example-basic-single').select2();
                });

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
//                        dom: "Blfrtip",
                        dom: '<"top"Blfi>rt<"bottom"p><"clear">',
                        buttons: ["copy", "csv", "pdf", "print"],
                    });
                });

                $(".sync_user").click(function () {
                    var selectedGroup = $("#selectedGroup").val();
                    if (selectedGroup === "") {
                        swal('Error!', 'Please select group to assign first!', 'error');
                    } else {
                        showBlockUI();
                        var loginId = $(this).attr("addid");
                        userExistence(loginId, selectedGroup);
                    }

                    function userExistence(loginId, selectedGroup) {
                        var url = '${contextPath}/admin/user/loginid/' + loginId;
                        var data = {};

                        function success(res) {
                            if (res.status) {
                                hideBlockUI();
                                swal('Error!', res.statusMessage, 'error');
                            } else {
                                hideBlockUI();
                                addUser(loginId, selectedGroup);
                            }
                        }

                        $.get(url, data, success, 'json').fail(function (res) {
                            hideBlockUI();
                            swal('Error!', JSON.stringify(res, null, 4), 'error');
                        });
                    }

                    function addUser(loginId, selectedGroup) {
                        var firstname = $("#firstname_" + loginId).html();
                        var lastname = $("#lastname_" + loginId).html();
                        var title = $("#title_" + loginId).html();
                        var email = $("#email_" + loginId).html();
                        var oncid = $("#oncid_" + loginId).html();

                        var url = '${contextPath}/admin/user/ldap/save';
                        var data = {
                            firstname: firstname,
                            lastname: lastname,
                            title: title,
                            email: email,
                            oncid: oncid,
                            loginId: loginId,
                            groupId: selectedGroup
                        };

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
                                           
                                            window.location = "${contextPath}/admin/user";
                                        });
                            } else {
                                swal('Error!', res.statusMessage, 'error');
                            }
                        }

                        $.post(url, data, success, 'json').fail(function (res) {
                            hideBlockUI();
                            swal('Error!', JSON.stringify(res, null, 4), 'error');
                        });
                    }
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