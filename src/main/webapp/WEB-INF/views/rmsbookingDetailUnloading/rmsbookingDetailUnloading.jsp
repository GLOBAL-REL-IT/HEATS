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
                    <!--                    <div class="container-fluid justify-content-start">
                                            <a href="${contextPath}/rmsbookingDetail/rmsReleased" class="btn btn-success me-2" role="button"><i class='bi bi-arrow-bar-right'></i>&nbsp;&nbsp;RMS Released to Production</a>
                                        </div>-->
                </nav>
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">HW Return from Loading Module</h5>
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
                                                    <th>BIB ID</th>
                                                    <th>LC Qty</th>
                                                    <th>PC Qty</th>
                                                    <th>Unloading Date</th>
                                                    <th>Return By</th>
                                                    <th>Return Date</th>
                                                    <th>HW Status</th>
                                                    <th>Progress Status</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${bookingReturnProduction}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                    <tr>
                                                        <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                        <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.rmsNo}"/></td>
                                                        <td><c:out value="${parameterMaster.event}"/></td>
                                                        <td><c:out value="${parameterMaster.hardwareId}"/></td>
                                                        <td><c:out value="${parameterMaster.lcQty}"/></td>
                                                        <td><c:out value="${parameterMaster.pcQty}"/></td>
                                                        <td><c:out value="${parameterMaster.unloadingDate}"/></td>
                                                        <td><c:out value="${parameterMaster.hardwareReturnBy}"/></td>
                                                        <td><c:out value="${parameterMaster.hardwareReturnDate}"/></td>
                                                        <td><c:out value="${parameterMaster.bookingHwStatus}"/></td>
                                                        <td><c:out value="${parameterMaster.bookingHwSubStatus}"/></td>
                                                        <td align="center">
                                                            <c:set var="name" value="${parameterMaster.hardwareReturnDate}"/>
                                                            <c:choose>
                                                                <c:when test="${empty name}">
                                                                    <a modaldeleteid="${parameterMaster.groupId}" type="button" data-bs-toggle="offcanvas" title="Return HW to MB Room/Ionic Area"
                                                                       data-bs-target="#staticBackdrop" aria-controls="staticBackdrop" onclick="getData(this);">
                                                                        <i class="bi bi-box-arrow-in-down-left h3"></i>
                                                                    </a>
                                                                    <a class="table-link" title="Manage" >
                                                                        <i class="bi bi-box-arrow-in-right h3" style="color: lightgrey;"></i>
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a modaldeleteid="${parameterMaster.groupId}" title="Return HW to MB Room/Ionic Area">
                                                                        <i class="bi bi-box-arrow-in-down-left h3" style="color: lightgrey;"></i>
                                                                    </a>
                                                                    <a href="${contextPath}/rmsbookingDetailUnloading/groupDetail/${parameterMaster.bookingPkid}/${parameterMaster.bookingHwPkid}" class="table-link" title="Manage">
                                                                        <i class="bi bi-box-arrow-in-right h3"></i>
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>

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
                    <h5 class="offcanvas-title" id="staticBackdropLabel">Return HW to MB Room/Ionic Area</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <div>
                        <form id="add_group_form" class="row g-3 align-items-center" role="form" action="${contextPath}/rmsbookingDetailUnloading/updateReturn" method="post">
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="rmsNo" class="form-label">RMS</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="rmsNo" name="rmsNo" placeholder="" value="" disabled>
                                            <input type="hidden" class="form-control" id="groupId" name="groupId" placeholder="" value="">
                                            <input type="hidden" class="form-control" id="bookingHwGroupId" name="bookingHwGroupId" placeholder="" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="event" class="form-label">Event</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="event2" name="event2" placeholder="" value="" disabled>
                                            <input type="hidden" class="form-control" id="event" name="event" placeholder="" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-6 col-sm-6 col-6">
                                    <div class="mb-1">
                                        <label for="lcQty" class="form-label">Load Card Qty</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="lcQty" name="lcQty" placeholder="" value="" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-sm-6 col-6">
                                    <div class="mb-1">
                                        <label for="pcQty" class="form-label">Program Card Qty</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="pcQty" name="pcQty" placeholder="" value="" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Scan BIB ID</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                            <input type="hidden" class="form-control" id="hardwareId" name="hardwareId" placeholder="" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <!--                                <a title="Delete Priority" data-bs-toggle="modal" data-bs-target="#delete_modal" class="table-link danger group_delete" onclick="modalDeletePriority();">
                                                                    <i class="bi bi-trash h3" style="color:red"></i>
                                                                </a>-->
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
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            
            const myOffcanvas = document.getElementById('staticBackdrop');

myOffcanvas.addEventListener('shown.bs.offcanvas', () => {
  const myInput = document.getElementById('itemId');
  myInput.focus();
});

                                                               $(document).ready(function () {
                                                                   var validator = $("#add_group_form").validate({
                                                                       rules: {
                                                                           itemId: {
                                                                               required: true,
                                                                               equalTo: "#hardwareId"
                                                                           }
                                                                       }
                                                                   });
                                                               });

                                                               function getData(e) {
                                                                   var groupId = $(e).attr("modaldeleteid");
                                                                   $.ajax({
                                                                       url: '${contextPath}/rmsbookingDetailUnloading/rmsDetailForHwReturn', // Replace with your controller URL
                                                                       type: 'GET',
                                                                       data: {groupId: groupId},
                                                                       dataType: 'json',
                                                                       success: function (data) {
                                                                           // Populate form fields with received data
                                                                           $("#rmsNo").val(data.rmsNo);
                                                                           $("#bookingHwGroupId").val(data.bookingHwGroupId);
                                                                           $("#groupId").val(data.groupId);
                                                                           $("#event").val(data.event);
                                                                           $("#event2").val(data.event);
                                                                           $("#lcQty").val(data.lcQty);
                                                                           $("#pcQty").val(data.pcQty);
                                                                           $("#hardwareId").val(data.hardwareId);
                                                                       },
                                                                       error: function (jqXHR, textStatus, errorThrown) {
                                                                           console.error("Error loading data: " + textStatus, errorThrown);
                                                                       }
                                                                   });
                                                               }

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
        </script>
    </s:layout-component>
</s:layout-render>