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
                box-shadow: none;
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
        <!-- Content wrapper start -->
        <div class="content-wrapper">
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/rmsbookingDetail/rmsReleased" class="btn btn-success me-2" role="button"><i class='bi bi-arrow-bar-right'></i>&nbsp;&nbsp;RMS Released to Production</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">HW Prep For Loading Module</h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-3">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>RMS No</th>
                                                    <th>Event</th>
                                                    <th>Actual Start Date</th>
                                                    <th>Device</th>
                                                    <th>Package</th>
                                                    <th>Est Event Start Date</th>
                                                    <th>RMS Status</th>
                                                    <th>Event Begin Status</th>
                                                    <th>Days to Event Start</th>
                                                    <th>Priority</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${booking}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                    <tr>
                                                        <c:if test="${parameterMaster.priority != '999'}">
                                                            <td style="color: red;"><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                            <td style="color: red;" id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.rmsNo}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.event}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.actStartDate}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.device}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.packages}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.eventStartDate}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.rmsStatus}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.eventBeginStatus}"/></td>
                                                            <td style="color: red;"><c:out value="${parameterMaster.daysToEventStart}"/></td>
                                                            <td style="color: red;font-size: 1.2em;"><span class="badge bg-danger"><c:out value="${parameterMaster.priority}"/></span></td>
                                                            </c:if>
                                                            <c:if test="${parameterMaster.priority == '999'}">
                                                            <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                            <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.rmsNo}"/></td>
                                                            <td><c:out value="${parameterMaster.event}"/></td>
                                                            <td><c:out value="${parameterMaster.actStartDate}"/></td>
                                                            <td><c:out value="${parameterMaster.device}"/></td>
                                                            <td><c:out value="${parameterMaster.packages}"/></td>
                                                            <td><c:out value="${parameterMaster.eventStartDate}"/></td>
                                                            <td><c:out value="${parameterMaster.rmsStatus}"/></td>
                                                            <td><c:out value="${parameterMaster.eventBeginStatus}"/></td>
                                                            <td><c:out value="${parameterMaster.daysToEventStart}"/></td>
                                                            <td><c:out value=""/></td>
                                                        </c:if>
                                                        <td align="center">
                                                            <a modaldeleteid="${parameterMaster.id}" type="button" data-bs-toggle="offcanvas" title="Set Priority"
                                                               data-bs-target="#staticBackdrop" aria-controls="staticBackdrop" onclick="getData(this);">
                                                                <i class="bi bi-list-ol h3"></i>
                                                            </a>
                                                            <c:if test="${parameterMaster.totalBooking == '1'}">
                                                                <a href="${contextPath}/rmsbookingDetail/detail/${parameterMaster.id}" class="table-link" title="Manage">
                                                                    <i class="bi bi-box-arrow-in-right h3"></i>
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${parameterMaster.totalBooking == '0'}">
                                                                <a modaldeleteid="${parameterMaster.id}" modalRms="${parameterMaster.rmsNo}" modalEvent="${parameterMaster.event}" type="button" title="No CBMS Booking" data-bs-toggle="modal" data-bs-target="#email_modal" class="table-link" onclick="sendEmail(this);">
                                                                    <i class="bi bi-exclamation-octagon h3" style="color: red;"></i>
                                                                </a>
<!--                                                                <div class="modal fade" id="exampleModalLg" tabindex="-1" aria-labelledby="exampleModalLgLabel"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog modal-lg">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title h4" id="exampleModalLgLabel">
                                                                                    No CBMS Booking
                                                                                </h5>
                                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                            </div>
                                                                            <div class="modal-body"><p style="vertical-align: middle;">This RMS_Event does not have an associated booking in CBMS. 
                                                                                    Please contact the Capacity Planner to complete the booking in CBMS before proceeding.</p></div>
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn btn-dark" data-bs-dismiss="modal">Cancel</button>
                                                                                <a id="modal_button" href="${contextPath}/rmsbookingDetail/sendEmailBooking/${parameterMaster.id}" class="btn btn-primary"><i class="bi bi-envelope"></i> Send Email to Planner</a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>-->
                                                            </c:if>
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
        <div class="offcanvas-placeholder">

            <div class="offcanvas offcanvas-start" data-bs-backdrop="static" tabindex="-1" id="staticBackdrop"
                 aria-labelledby="staticBackdropLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="staticBackdropLabel">Set Priority</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <div>
                        <form class="row g-3 align-items-center" role="form" action="${contextPath}/rmsbookingDetail/savePriority" method="post">
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">RMS</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="rmsNo" name="rmsNo" placeholder="" value="" disabled>
                                            <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Event</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="event" name="event" placeholder="" value="" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Priority</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="priorityRead" name="priorityRead" placeholder="" value="" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="model" class="form-label">Priority</label>
                                        <div class="input input-group">
                                            <select class="input input-group" id="priority" name="priority" style="width: 100%">
                                                <!--<option></option>-->
                                                <c:forEach items="${priorityList}" var="invInner">
                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Remarks</label>
                                        <div class="input input-group">
                                            <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <a title="Delete Priority" data-bs-toggle="modal" data-bs-target="#delete_modal" class="table-link danger group_delete" onclick="modalDeletePriority();">
                                    <i class="bi bi-trash h3" style="color:red"></i>
                                </a>
                                <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
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
            function getData(e) {
                var id = $(e).attr("modaldeleteid");
                $.ajax({
                    url: '${contextPath}/rmsbookingDetail/priorityDetail', // Replace with your controller URL
                    type: 'GET',
                    data: {id: id},
                    dataType: 'json',
                    success: function (data) {
                        // Populate form fields with received data
                        $("#rmsNo").val(data.rmsNo);
                        $("#id").val(data.id);
                        $("#event").val(data.event);
                        $("#remarks").val(data.priorityRemarks);
                        if (data.priority !== "999") {
                            $("#priorityRead").val(data.priority);
                        } else {
                            $("#priorityRead").val("");
                        }
                        $('#priority').val(data.priority).trigger('change');
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.error("Error loading data: " + textStatus, errorThrown);
                    }
                });
            }
            
            function sendEmail(e) {
                var modaldeleteid = $(e).attr("modaldeleteid");
                var modalRms = $(e).attr("modalRms");
                var modalEvent = $(e).attr("modalEvent");
                var deleteUrl = "${contextPath}/rmsbookingDetail/sendEmailBooking/" + modaldeleteid;
                var deleteMsg = modalRms + "_" + modalEvent + " does not have an associated booking in CBMS. Please contact the Capacity Planner to complete the booking in CBMS before proceeding.";
                $("#email_modal .modal-body").html(deleteMsg);
                $("#modal_email_button").attr("href", deleteUrl);
            }

            function modalDeletePriority() {
                var id = $('#id');
                var priorityRead = $('#priorityRead');
                var deleteUrl = "${contextPath}/rmsbookingDetail/cancelPriority/" + id.val();
                var deleteMsg = "Are you sure want to remove priority for this RMS_Event?";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }

            $(function () {
                $("#customButtons1").DataTable({
                    lengthMenu: [
                        [10, 25, 50],
                        [10, 25, 50, "All"],
                    ],
                    language: {
                        lengthMenu: "Display _MENU_ Records Per Page",
//                        info: "Showing Page _PAGE_ of _PAGES_",
                        info: "Showing _START_ to _END_ of _TOTAL_ total records",
                    },
//                    dom: "Blfrtip",
                    dom: '<"top"Blfi>rt<"bottom"p><"clear">',
                    buttons: ["copy", "csv", "pdf", "print"],
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>