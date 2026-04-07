<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_title">
        <f:message key="general.label.home"/>
    </s:layout-component>
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"/>-->
        <!-- tagsCloud Keywords CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/tagsCloud/tagsCloud.css">
        <!-- Data Tables -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .font-link-rms {
                font-weight: bold;
                color:blue;
                font-style: italic;
                text-decoration: underline;
            }

            .pending thead th {
                background-color: #f06a0a; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

            .after thead th {
                background-color: #D97D55; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

            .mpe thead th {
                background-color: #5ec3f1; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

            .new thead th {
                background-color: #59AC77; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

        </style>
    </s:layout-component>
    <s:layout-component name="page_header">
        <f:message key="general.label.dashboard"/>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <div class="col-12">
                <!-- Card start -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="card-title">New Hardware - Pending Vm/Functional Test <span class="h6" style="color:red">(${countItemPending})</span></h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table id="scrollVerticalNewHw" class="table pending custom-table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>Item Type</span></th>
                                        <th><span>Sub Type</span></th>
                                        <th><span>Item ID</span></th>
                                        <th><span>Item Name</span></th>
                                        <th><span>Assembly ID</span></th>
                                        <th><span>Registered By</span></th>
                                        <th><span>Registered Date</span></th>
                                        <th><span>Status</span></th>
                                        <th class="col-1"><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${item}" var="parameterMaster" varStatus="parameterMasterLoop">
                                    <tr>
                                        <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                    <td><c:out value="${parameterMaster.itemType}"/></td>
                                    <td id="modal_delete_info_countItemPending}"><c:out value="${parameterMaster.subType}"/></td>
                                    <td><c:out value="${parameterMaster.itemId}"/></td>
                                    <td><c:out value="${parameterMaster.itemName}"/></td>
                                    <td><c:out value="${parameterMaster.assemblyId}"/></td>
                                    <td><c:out value="${parameterMaster.createdBy}"/></td>
                                    <td><c:out value="${parameterMaster.createdDate}"/></td>
                                    <td><c:out value="${parameterMaster.status}"/></td>
                                    <td align="center">
                                    <c:set var="String" value="${parameterMaster.status}"/>
                                    <c:choose>
                                        <c:when test="${(fn:contains(String, 'Activity Selection'))}">
                                            <a href="${contextPath}/hw/item/addActivity/${parameterMaster.id}" class="table-link" title="Edit">
                                                <i class="bi bi-box-arrow-in-right h3"></i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${contextPath}/hw/item/add2/${parameterMaster.id}" class="table-link" title="Edit">
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
                <!-- Card end -->
            </div>

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">RMS <span style="color:#D97D55">Pending for</span> Loading (CBMS) <span class="h6" style="color:red">(${countBooking})</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVerticalFromLoading" class="table pending custom-table">
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
                                    <td style="color: red;" id="modal_delete_info_countItemPending}"><c:out value="${parameterMaster.rmsNo}"/></td>
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
                                    <td id="modal_delete_info_countItemPending}"><c:out value="${parameterMaster.rmsNo}"/></td>
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
                                            <a href="${contextPath}/rmsbookingDetail/detail/${parameterMaster.id}" class="table-link" title="Manage">
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
                    <!-- Card end -->
                </div>
                        <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">RMS <span style="color:#D97D55">Released to Production</span> <span class="h6" style="color:red">(${countBookingReleased})</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVerticalReleasedLoading" class="table pending custom-table">
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
                                            <th>Manage</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                <c:forEach items="${bookingReleased}" var="parameterMaster" varStatus="parameterMasterLoop">
                                    <tr>
                                    <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                    <td id="modal_delete_info_countItemPending}"><c:out value="${parameterMaster.rmsNo}"/></td>
                                    <td><c:out value="${parameterMaster.event}"/></td>
                                    <td><c:out value="${parameterMaster.actStartDate}"/></td>
                                    <td><c:out value="${parameterMaster.device}"/></td>
                                    <td><c:out value="${parameterMaster.packages}"/></td>
                                    <td><c:out value="${parameterMaster.eventStartDate}"/></td>
                                    <td><c:out value="${parameterMaster.rmsStatus}"/></td>
                                    <td><c:out value="${parameterMaster.eventBeginStatus}"/></td>
                                    <td><c:out value="${parameterMaster.daysToEventStart}"/></td>
                                    <td align="center">
                                            <a href="${contextPath}/rmsbookingDetail/rmsReleased/detail/${parameterMaster.id}" class="table-link" title="Manage">
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
                    <!-- Card end -->
                </div>
                <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">RMS <span style="color:#D97D55">Return from</span> Loading (LRT) <span class="h6" style="color:red">(0)</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVerticalAfterLoading" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>RMS No</th>
                                            <th>Event Start Date</th>
                                            <th>Event End Date</th>
                                            <th>Device</th>
                                            <th>Package</th>
                                            <th>Event</th>
                                            <th>RMS Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>

                <!--<div class="col-sm-12 col-md-6">-->
                     <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <!--<h5 class="card-title" style="color:#5ec3f1">MPE</h5>-->
                            <h5 class="card-title">Maverick <span class="h6" style="color:red">(${countMaverick})</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="customButtons1" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>HW Type</th>
                                            <th>HW ID</th>
                                            <th>Module</th>
                                            <th>Sub Module</th>
                                            <th>Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${maverickList}" var="parameterMaster" varStatus="parameterMasterLoop">
                                    <tr>
                                        <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                    <td><c:out value="${parameterMaster.itemType}"/></td>
                                    <td id="modal_delete_info_countItemPending}"><c:out value="${parameterMaster.itemId}"/></td>
                                    <td><c:out value="${parameterMaster.module}"/></td>
                                    <td><c:out value="${parameterMaster.submodule}"/></td>
                                    <td><c:out value="${parameterMaster.createdDate}"/></td>
                                    <td><c:out value="${parameterMaster.status}"/></td>
                                    </tr>
                                </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <!--<div class="col-sm-12 col-md-6">-->
                     <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">New HW Fabrication <span class="h6" style="color:red">(0)</span></h5>
                            <!--<h5 class="card-title" style="color:#59AC77">New HW Fabrication</h5>-->
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVertical4" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>Hardware Type</th>
                                            <th>Device</th>
                                            <th>Package</th>
                                            <th>Event</th>
                                            <th>Requestor</th>
                                            <th>Request Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Maverick ${yearLabel}</h5>
                            <p style="font-size: 12px; color: blue;">
                                *Cumulative hardware issues across multiple modules</p>
                        </div>
                        <div class="card-body">

                            <div class="scroll370">
                                <div class="overflow-hidden">
                                    <div id="demography2" class="auto-align-graph"></div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Readiness Report ${yearLabel}</h5>
                            <!--<h6>*Cycle Time Calculation : number of HW release to production divide by number of RMS_Event (Status In Process)</h6>-->
                            <p style="font-size: 12px; color: blue;">
                                *Cycle Time : Days taken to release hardware to production from the first day the RMS_Event with 'In Process' status comes in.</p>
                        </div>
                        <div class="card-body">
                            <div id="chartsample"></div>
                        </div>
                    </div>
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
        <!-- App footer end -->
        
        <!-- Add Offcanvas Here -->
        <div class="offcanvas-placeholder">

          <!-- Toggle static offcanvas -->
          <div class="offcanvas offcanvas-start" data-bs-backdrop="static" tabindex="-1" id="staticBackdrop"
            aria-labelledby="staticBackdropLabel">
            <div class="offcanvas-header">
              <h5 class="offcanvas-title" id="staticBackdropLabel">Set Priority</h5>
              <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>
            <div class="offcanvas-body">
              <div>
                <form class="row g-3 align-items-center" role="form" action="${contextPath}/savePriority" method="post">
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
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                        <a title="Delete Priority" data-bs-toggle="modal" data-bs-target="#delete_modal" class="table-link danger group_delete" onclick="modalDelete();">
                                            <i class="bi bi-trash h3" style="color:red"></i>
                                        </a>
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                </div>
                                
                                <!-- Form actions end -->
                            </form>
              </div>
            </div>
          </div>

        </div>
    </s:layout-component>
    <s:layout-component name="page_js">

        <!-- Apex Charts -->
        <script src="${contextPath}/resources/statflow/vendor/apex/apexcharts.min.js"></script>
<!--        <script src="${contextPath}/resources/statflow/vendor/apex/custom/repotrs/demography.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/examples/bar/basic-bar-graph-grouped.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph1.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph2.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph3.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph4.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph5.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph6.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph7.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph8.js"></script>-->

        <!-- jVector Maps -->
<!--        <script src="${contextPath}/resources/statflow/vendor/jvectormap/jquery-jvectormap-2.0.5.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/gdp-data.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/world-mill-en.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/africa-mill.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/europe-mill.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/custom/map-europe.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/custom/map-africa.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/custom/world-map-markers2.js"></script>-->

        <!-- jQcloud Keywords -->
        <!--<script src="${contextPath}/resources/statflow/vendor/tagsCloud/tagsCloud.js"></script>-->

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

    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            
             $(document).ready(function () {
                $('.js-example-basic-single').select2();
            });
            
            function modalDelete() {
                var id = $('#id');
                var priorityRead = $('#priorityRead');
//                if(priorityRead.val() == ""){
//                    alert("No Priority was set for this RMS_Event");
//                   var modalElement = document.getElementById('delete_modal');
//var modalInstance = bootstrap.Modal.getOrCreateInstance(modalElement);
//modalInstance.hide();
//                }else{
                var deleteUrl = "${contextPath}/cancelPriority/" + id.val();
                var deleteMsg = "Are you sure want to remove priority for this RMS_Event?";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);  
//                }
                
            }
            
            function getData(e) {
                var id = $(e).attr("modaldeleteid");
                $.ajax({
                    url: '${contextPath}/priorityDetail', // Replace with your controller URL
                    type: 'GET',
                    data: {id: id},
                    dataType: 'json',
                    success: function (data) {
                        // Populate form fields with received data
                        $("#rmsNo").val(data.rmsNo);
                        $("#id").val(data.id);
                        $("#event").val(data.event);
                        $("#remarks").val(data.priorityRemarks);
                        if(data.priority !== "999"){
                            $("#priorityRead").val(data.priority);
                        }else{
                            $("#priorityRead").val("");
                        }
                        $('#priority').val(data.priority).trigger('change');
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.error("Error loading data: " + textStatus, errorThrown);
                    }
                });
            }


            //RMS Return from Loading
            $(function () {
                $("#scrollVerticalFromLoading").DataTable({
                    scrollY: "200px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                });
            });
            
            $(function () {
                $("#scrollVerticalReleasedLoading").DataTable({
                    scrollY: "200px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                });
            });

            $(function () {
                $("#scrollVerticalAfterLoading").DataTable({
                    scrollY: "200px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                });
            });

            $(function () {
                $("#scrollVerticalNewHw").DataTable({
                    scrollY: "200px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                });
            });

            $(function () {
                $("#customButtons1").DataTable({
                   scrollY: "200px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                });
            });
            

            $(function () {
                $("#scrollVertical4").DataTable({
                    scrollY: "200px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                });
            });
            
            if(${countMaverick} > 0){
//                            alert("Test 1");
                            
                            var options = {
                series: [${countMaverick}, 0, 0, 0, 0, 0],
                        
                chart: {
//                    width: 370,
                    height: 370,
                    type: 'polarArea',
                    fontFamily: 'Poppins, sans-serif',
                    background: '#FCFCEB',
                    toolbar: {
                        show: false
                    },
                    animations: {
                        enabled: true,
                        easing: 'easeinout',
                        speed: 800
                    }
                },
                labels: ['HW Registration','VM Before Loading', 'Functional Test before Loading', 'Abnormal Loading', 'Ionic Test', 'VM After Loading'],
                fill: {
                    opacity: 0.85,
                    gradient: {
                        enabled: true
                    }
                },
                stroke: {
                    width: 2,
                    colors: ['#ffffff']
                },
                colors: ["#50589C", "#5ec3f1", "#9DB6FF", "#59AC77","#F63049","#94A378"],
                yaxis: {
                    show: false
                },
                legend: {
                    position: 'bottom',
                    fontSize: '14px',
                    markers: {
                        radius: 3
                    }
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
//                            return val + " Million"
                            return val
                        }
                    },
                    theme: 'dark'
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return Math.round(val) + "%"
                    },
                    style: {
                        fontSize: '12px',
                        fontWeight: 'bold'
                    }
                },
                plotOptions: {
                    polarArea: {
                        rings: {
                            strokeWidth: 0
                        },
                        spokes: {
                            strokeWidth: 0
                        },
                        offsetY: 0,
                        offsetX: 0
                    }
                },
                responsive: [{
                        breakpoint: 480,
                        options: {
                            chart: {
                                width: 280
                            },
                            legend: {
                                position: 'bottom'
                            }
                        }
                    }]
            };
                            
                        }else{
//                            alert("Test 2");
                            
                            var options = {
               series: [1],
               chart: {
//                    width: 370,
                    height: 370,
                    type: 'polarArea',
                    fontFamily: 'Poppins, sans-serif',
                    background: '#FCFCEB',
                    toolbar: {
                        show: false
                    },
                    animations: {
                        enabled: true,
                        easing: 'easeinout',
                        speed: 800
                    }
                },
//              labels: [],
              labels: ['','HW Registration','VM Before Loading', 'Functional Test before Loading', 'Abnormal Loading', 'Ionic Test', 'VM After Loading'],
//              colors: ['#f0f0f0'],
              colors: ["#f0f0f0","#50589C", "#5ec3f1", "#9DB6FF", "#59AC77","#F63049","#94A378"],
              fill: {
                    opacity: 0.85,
                    gradient: {
                        enabled: true
                    }
                },
                stroke: {
                    width: 2,
                    colors: ['#ffffff']
                },
                yaxis: {
                    show: false
                },
                legend: {
                    position: 'bottom',
                    fontSize: '14px',
                    markers: {
                        radius: 3
                    }
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
//                            return val + " Million"
                            return val
                        }
                    },
                    theme: 'dark'
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return Math.round(val) + "%"
                    },
                    style: {
                        fontSize: '12px',
                        fontWeight: 'bold'
                    }
                },
                plotOptions: {
                    polarArea: {
                        rings: {
                            strokeWidth: 0
                        },
                        spokes: {
                            strokeWidth: 0
                        },
                        offsetY: 0,
                        offsetX: 0
                    }
                },
                responsive: [{
                        breakpoint: 480,
                        options: {
                            chart: {
                                width: 280
                            },
                            legend: {
                                position: 'bottom'
                            }
                        }
                    }]
                        };
                                    }

            
//            chart.updateOptions({
//                series: [1],
//                labels: [],
//                colors: ['#f0f0f0'],
//            });

            var chart = new ApexCharts(document.querySelector("#demography2"), options);
            chart.render();

            const averageValue = 3;
            const averageData = new Array(12).fill(averageValue);
            var options = {
                series: [
                    {
                        name: 'HW In',
                        type: 'column',
                        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    }, {
                        name: 'HW Released',
                        type: 'column',
                        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    }, {
                        name: 'Cycle Time Goal',
                        type: 'line',
                        data: averageData
                    }, {
                        name: 'Average Cycle Time',
                        type: 'line',
                        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    }],
                chart: {
                    height: 350,
                    type: 'line',
                    background: '#FCFCEB',
                    stacked: false
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    width: [1, 1, 4]
                },
                title: {
//                    text: 'WIP Report 2025',
                    align: 'left',
                    offsetX: 110
                },
                xaxis: {
//                    categories: ['Dec 24', 'Jan 25', 'Feb 25', 'Mar 25', 'Apr 25', 'May 25', 'Jun 25', 'Jul 25', 'Aug 25', 'Sep 25', 'Oct 25', 'Nov 25']
                    categories: ${monthNameList}
                },
                yaxis: [

                    {
                        seriesName: 'HW In',
                        min: 0,
                        max: 20,
                        axisTicks: {
                            show: true
                        },
                        axisBorder: {
                            show: true,
                            color: '#008FFB'
                        },
                        labels: {
                            style: {
                                colors: '#008FFB'
                            }
                        },
                        title: {
                            text: "HW (RMS_EVENT)",
                            style: {
                                color: '#008FFB'
                            }
                        },
                        tooltip: {
                            enabled: true
                        }
                    },
                    {
                        seriesName: 'HW Released',
                        show: false,
                        min: 0,
                        max: 20,
                        opposite: true,
                        axisTicks: {
                            show: true
                        },
                        axisBorder: {
                            show: true,
                            color: '#00E396'
                        },
                        labels: {
                            style: {
                                colors: '#00E396'
                            }
                        },
                        title: {
                            text: "New Task",
                            style: {
                                color: '#00E396'
                            }
                        }
                    }, {
                        seriesName: 'AVERAGE',
                        show: false,
                        min: 0,
                        max: 10,
                        axisTicks: {
                            show: true
                        },
                        axisBorder: {
                            show: true,
                            color: '#008FFB'
                        },
                        labels: {
                            style: {
                                colors: '#008FFB'
                            }
                        },
                        title: {
                            text: "kira dia",
                            style: {
                                color: '#00E396'
                            }
                        }
                    },
                    {
                        seriesName: 'Average Cycle Time',
                        opposite: true,
                        min: 0,
                        max: 10,
                        axisTicks: {
                            show: true
                        },
                        axisBorder: {
                            show: true,
                            color: '#C48204'
                        },
                        labels: {
                            style: {
                                colors: '#C48204'
                            }
                        },
                        title: {
                            text: "Cycle Time (Readiness Day)",
                            style: {
                                color: '#C48204'
                            }
                        }
                    }
                ],
                tooltip: {
                    fixed: {
                        enabled: true,
                        position: 'topLeft', // topRight, topLeft, bottomRight, bottomLeft
                        offsetY: 30,
                        offsetX: 60
                    }
                },
                legend: {
                    horizontalAlign: 'left',
                    offsetX: 40
                }
            };

            var chart = new ApexCharts(document.querySelector("#chartsample"), options);
            chart.render();
        </script>

    </s:layout-component>
</s:layout-render>