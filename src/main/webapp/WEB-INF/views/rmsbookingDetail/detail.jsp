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
            .input {
                border: 1.5px solid #000;
                border-radius: 0.5rem;
                box-shadow: 2.5px 3px 0 #000;
                outline: none;
                transition: ease 0.25s;
            }
            .input:focus {
                box-shadow: 5.5px 7px 0 black;
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
            div.dt-container {
                width: 900px;
                margin: 0 auto;
            }
            .offcanvas.offcanvas-start-recall {
                top: 0;
                left: 0;
                width: 900px;
                border-right: 1px solid rgba(0, 6, 28, 0.175);
                transform: translateX(-100%);
                transition: transform 0.2s ease-in-out;
                ;
            }
            .offcanvas.offcanvas-start-EmailReplacement {
                top: 0;
                left: 0;
                width: 900px;
                border-right: 1px solid rgba(0, 6, 28, 0.175);
                transform: translateX(-100%);
                transition: transform 0.2s ease-in-out;
                ;
            }
            .select2-option-line2 {
                color: #6c757d;
                font-size: .9em;
                margin-top: 2px;
                line-height: 1.2;
            }
            /* Main Select2 container */
            .select2-container {
                width: 100% !important;
            }

            /* Multiple select border box */
            .select2-container--default .select2-selection--multiple {
                min-height: 38px !important;
                height: auto !important;
                overflow: visible !important;
                padding: 4px;
                border: 1px solid #ced4da;
                border-radius: 4px;
            }

            /* Allow selected items to wrap */
            .select2-container--default .select2-selection--multiple .select2-selection__rendered {
                display: flex !important;
                flex-wrap: wrap !important;
                gap: 3px;
                overflow: visible !important;
                white-space: normal !important;
            }

            /* Selected tags */
            .select2-container--default .select2-selection--multiple .select2-selection__choice {
                margin-top: 2px !important;
                margin-bottom: 2px !important;
            }

            /* Search input */
            .select2-container--default .select2-search--inline .select2-search__field {
                margin-top: 2px !important;
            }
            .semi-bold {
                font-weight: 500;
            }
            .form-group,
            .col-md-12,
            div {
                height: auto;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">

            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/rmsbookingDetail" class="btn btn-outline-warning me-2" role="button"><i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                        <a href="${contextPath}/rmsbookingDetail/viewmbtt/${id}" class="btn btn-outline-info me-2" role="button"><i class="bi bi-qr-code-scan"></i>&nbsp;&nbsp;Trip Ticket</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title d-flex justify-content-between align-items-center">
                                <div>
                                    Hardware Preparation For Loading - <span style="color:#D97D55">Detail</span>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty rms.priority && rms.priority != '999'}">
                                        <span class="text-danger">
                                            Priority: 
                                            <c:set var="priorityStr" value="${rms.priority.toString()}" />
                                            <c:forEach var="i" begin="0" end="${fn:length(priorityStr) - 1}">
                                                <c:set var="digit" value="${fn:substring(priorityStr, i, i + 1)}" />
                                                <i class="bi bi-${digit}-square fs-4"></i>
                                            </c:forEach>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-secondary">No Priority Set</span>
                                    </c:otherwise>
                                </c:choose>
                            </h5>
                        </div>
                        <div class="card-body">
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/xde apa pon" method="post">
                                <div class="row mb-3">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">RMS</label>
                                            <div class="input input-group">
                                                <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${rms.id}">
                                                <input type="hidden" class="form-control" id="bookingPkid" name="bookingPkid" placeholder="" value="${rms.bookingPkid}">
                                                <input type="text" class="form-control" id="rmsNo" name="rmsNo" placeholder="" value="${rms.rmsNo}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Event</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="event" name="event" placeholder="" value="${rms.event}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Device</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="device" name="device" placeholder="" value="${rms.device}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Package</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="packages" name="packages" placeholder="" value="${rms.packages}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Actual Start Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="actStartDate" name="actStartDate" placeholder="" value="${rms.actStartDate}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">RMS Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="rmsStatus" name="rmsStatus" placeholder="" value="${rms.rmsStatus}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Equipment Location</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="equipmentLocation" name="equipmentLocation" placeholder="" value="${rms.equipmentLocation}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Est Event Start Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="eventStartDate" name="eventStartDate" placeholder="" value="${rms.eventStartDate}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Days to Event Start</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="daysToEventStart" name="daysToEventStart" placeholder="" value="${rms.daysToEventStart}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12 visually-hidden">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${rms.status}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${rms.priority != '999'}">
                                        <div class="col-xl-1 col-sm-12 col-12 visually-hidden">
                                            <div class="mb-1">
                                                <label for="itemId" class="form-label">Priority</label>
                                                <div class="input input-group">
                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${rms.priority}" readonly>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-xl-3 col-sm-12 col-12">
                                            <div class="mb-1">
                                                <label for="itemId" class="form-label">Priority Remarks</label>
                                                <div class="input input-group">
                                                    <textarea class="form-control" rows="5" id="remarks" name="remarks" readonly>${rms.priorityRemarks}</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${rms.priority == '999'}">
                                        <div class="col-xl-1 col-sm-12 col-12 visually-hidden">
                                            <div class="mb-1">
                                                <label for="itemId" class="form-label">Priority</label>
                                                <div class="input input-group">
                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" readonly>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-xl-3 col-sm-12 col-12">
                                            <div class="mb-1">
                                                <label for="itemId" class="form-label">Priority Remarks</label>
                                                <div class="input input-group">
                                                    <textarea class="form-control" rows="5" id="remarks" name="remarks" readonly></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="col-xl-3 col-sm-12 col-12 visually-hidden">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">FOL filename</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="5" id="remarks" name="remarks" readonly>${rms.folFilename}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Booking Remarks</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="5" id="bookingRemarks" name="bookingRemarks" readonly>${rmsRemarks}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <c:if test="${releaseButton == 'Enable'}">
                                        <c:if test="${uac.befLoadingRelease == 'Yes'}">
                                            <a modaldeleteid="${rms.id}" type ="button" title="Release to Production" data-bs-toggle="modal" data-bs-target="#confirmation_modal" class="btn btn-success float-start" onclick="modalRelease(this);">
                                                <i class="bi bi-check-circle-fill">&nbsp;&nbsp;Release to Production</i>
                                            </a>
                                        </c:if>
                                        <c:if test="${uac.befLoadingRelease ne 'Yes'}">
                                            <a modaldeleteid="${rms.id}" type ="button" title="Release to Production" class="btn float-start disabled" disabled>
                                                <i class="bi bi-check-circle-fill">&nbsp;&nbsp;Release to Production</i>
                                            </a>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${releaseButton == 'Disable'}">
                                        <a modaldeleteid="${rms.id}" type ="button" title="Release to Production" class="btn float-start disabled" disabled>
                                            <i class="bi bi-check-circle-fill">&nbsp;&nbsp;Release to Production</i>
                                        </a>
                                    </c:if>  
                                    <a type="button" data-bs-toggle="offcanvas" title="Request for HW Replacement"
                                       data-bs-target="#staticBackdropEmailReplacement" aria-controls="staticBackdropEmailReplacement" class="btn btn-primary float-end">
                                        <i class="bi bi-envelope-arrow-up">&nbsp;&nbsp;Request for HW Replacement</i>
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-sm-6 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-list-ul me-2"></i>List of Hardware <b><span style="color:#D97D55">(Other Support Items)</span></b>
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons2" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Item Type</th>
                                                    <th>Item ID</th>
                                                    <th>Qty</th>
                                                    <th>Status</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${otherList}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                    <tr>
                                                        <c:if test="${parameterMaster.flag == '99'}">
                                                            <td style="color:red"><strike><c:out value="${parameterMasterLoop.index+1}"/></strike></td>
                                                    <td style="color:red" id="modal_delete_info_${parameterMaster.id}"><strike><c:out value="${parameterMaster.itemType}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.itemId}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.qty}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.status}"/></strike></td>
                                                    <td align="center">
                                                    </td>
                                                </c:if>
                                                <c:if test="${parameterMaster.flag != '99'}">
                                                    <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                    <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.itemType}"/></td>
                                                    <td><c:out value="${parameterMaster.itemId}"/></td>
                                                    <td><c:out value="${parameterMaster.qty}"/></td>
                                                    <c:set var="String" value="${parameterMaster.status}"/>
                                                    <c:choose>
                                                        <c:when test="${String eq 'Available'}">
                                                            <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-success "><c:out value="${parameterMaster.status}"/></span></td>
                                                            </c:when>
                                                            <c:when test="${String eq 'Released to Production'}">
                                                            <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-success "><c:out value="${parameterMaster.status}"/></span></td>
                                                            </c:when>
                                                            <c:when test="${(fn:contains(String, 'Not Available'))}">
                                                            <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-danger "><c:out value="${parameterMaster.status}"/></span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-light text-body "><c:out value="${parameterMaster.status}"/></span></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    <td align="center">
                                                        <c:if test="${parameterMaster.status != 'NA'}">
                                                            <c:if test="${parameterMaster.recall == 'Yes'}">
                                                                <a modaldeleteid="${parameterMaster.itemPkid}" modaldeleteid2="${parameterMaster.id}" type="button" data-bs-toggle="offcanvas" title="Recall from Storage Factory"
                                                                   data-bs-target="#staticBackdropRecall" aria-controls="staticBackdropRecall" onclick="ajaxStorage(this);" <c:if test="${uac.befLoadingSfRecall ne 'Yes'}">disabled</c:if>>
                                                                    <i class="bi bi-house-up h4" <c:if test="${uac.befLoadingSfRecall ne 'Yes'}">style="color:gray"</c:if>></i>
                                                                </a>
                                                            </c:if>
                                                        </c:if>
                                                    </td>
                                                </c:if>
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
                </div>
                <div class="col-sm-6 col-12">

                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-list-ul me-2"></i>List of Hardware <b><span style="color:#D97D55">(Motherboard)</span></b>
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Item Type</th>
                                                    <th>Item ID</th>
                                                    <th>LC Qty</th>
                                                    <th>PC Qty</th>
                                                    <th>Status</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${BibList}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                    <tr>
                                                        <c:if test="${parameterMaster.flag == '99'}">
                                                            <td style="color:red" style="color:red"><strike><c:out value="${parameterMasterLoop.index+1}"/></strike></td>
                                                    <td style="color:red" id="modal_delete_info_${parameterMaster.id}"><strike><c:out value="${parameterMaster.itemType}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.itemId}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.lcQty}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.pcQty}"/></strike></td>
                                                    <td style="color:red"><strike><c:out value="${parameterMaster.status}"/></strike></td>
                                                    <td align="center">
                                                    </td>
                                                </c:if>
                                                <c:if test="${parameterMaster.flag != '99'}">
                                                    <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                    <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.itemType}"/></td>
                                                    <td><c:out value="${parameterMaster.itemId}"/></td>
                                                    <td><c:out value="${parameterMaster.lcQty}"/></td>
                                                    <td><c:out value="${parameterMaster.pcQty}"/></td>
                                                    <c:if test="${not empty parameterMaster.subStatus}">
                                                        <c:set var="String" value="${parameterMaster.status}"/>
                                                        <c:choose>
                                                            <c:when test="${String eq 'Available'}">
                                                                <c:set var="subString" value="${parameterMaster.subStatus}"/>
                                                                <c:choose>
                                                                    <c:when test="${(fn:contains(subString, 'Fail'))}">
                                                                        <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-warning"><c:out value="${parameterMaster.status} - ${parameterMaster.subStatus}"/></span></td> 
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                        <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-success"><c:out value="${parameterMaster.status} - ${parameterMaster.subStatus}"/></span></td> 
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:when test="${(fn:contains(String, 'Not Available'))}">
                                                                <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-danger"><c:out value="${parameterMaster.status} - ${parameterMaster.subStatus}"/></span></td>  
                                                                </c:when>
                                                                <c:otherwise>
                                                                <td style="font-size: 1.10rem;" class="semi-bold" ><span class="badge bg-light text-body"><c:out value="${parameterMaster.status}"/></span></td> 
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                        <c:if test="${empty parameterMaster.subStatus}">
                                                            <c:set var="String" value="${parameterMaster.status}"/>
                                                            <c:choose>
                                                                <c:when test="${String eq 'Available'}">
                                                                    <!--<td style="color:green" class="semi-bold"><c:out value="${parameterMaster.status}"/></td>-->  
                                                                <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-success"><c:out value="${parameterMaster.status}"/></span></td>  
                                                                </c:when>
                                                                <c:when test="${(fn:contains(String, 'Not Available'))}">
                                                                <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-danger"><c:out value="${parameterMaster.status}"/></span></td>  
                                                                </c:when>
                                                                <c:otherwise>
                                                                <td style="font-size: 1.10rem;" class="semi-bold"><span class="badge bg-light text-body"><c:out value="${parameterMaster.status}"/></span></td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                    <td align="center">
                                                        <c:if test="${parameterMaster.recall == 'Yes'}">
                                                            <a modaldeleteid="${parameterMaster.itemPkid}" modaldeleteid2="${parameterMaster.id}" type="button" data-bs-toggle="offcanvas" title="Recall from Storage Factory"
                                                               data-bs-target="#staticBackdropRecall" aria-controls="staticBackdropRecall" onclick="ajaxStorage(this);" <c:if test="${uac.befLoadingSfRecall ne 'Yes'}">disabled</c:if>>
                                                                <i class="bi bi-house-up h4" <c:if test="${uac.befLoadingSfRecall ne 'Yes'}">style="color:gray"</c:if>></i>
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${parameterMaster.status == 'Available'}">
                                                            <c:if test="${parameterMaster.subStatus == 'Released to Production'}">
                                                                <a href="${contextPath}/rmsbookingDetail/rmsReleasedSingle/groupDetail/${parameterMaster.bookingPkid}/${parameterMaster.pkid}" class="table-link" title="Manage">
                                                                    <i class="bi bi-box-arrow-in-right h3" style="color:orangered"></i>
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${parameterMaster.subStatus != 'Released to Production'}">
                                                                <a href="${contextPath}/rmsbookingDetail/groupDetail/${parameterMaster.bookingPkid}/${parameterMaster.pkid}" class="table-link" title="Manage">
                                                                    <i class="bi bi-box-arrow-in-right h3" style="color:orangered"></i>
                                                                </a>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${parameterMaster.subStatus == 'Pending Release to Production'}">
                                                            <a modaldeleteid="${parameterMaster.id}" type ="button" title="Release to Production" data-bs-toggle="modal" data-bs-target="#confirmation_modal" 
                                                               onclick="modalReleaseSingle(this);">
                                                                <i class="bi bi-check-circle h3" style="color:green"></i>
                                                            </a>
                                                        </c:if>
                                                    </td>
                                                </c:if>
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
        <!-- Content wrapper end -->

        <!-- App Footer start -->
        <div class="app-footer">
            <img class="img3" src="${contextPath}/resources/onsemi logo.webp" alt="onsemi">
            <span>© HEATs 2025</span>
        </div>
        <div class="offcanvas-placeholder">
            <!-- Toggle static offcanvas for email replacement--> 
            <div class="offcanvas offcanvas-start" data-bs-backdrop="static" tabindex="-1" id="staticBackdrop"
                 aria-labelledby="staticBackdropLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="staticBackdropLabel">Send Email for HW Replacement</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <div>
                        <form class="row g-3 align-items-center" role="form" action="${contextPath}/rmsbookingDetail/sendEmailReplacement" method="post">
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Item Type</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="itemType" name="itemType" placeholder="" value="" disabled>
                                            <input type="hidden" class="form-control" id="id2" name="id2" placeholder="" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Item ID</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="itemId2" name="itemId2" placeholder="" value="" disabled>
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
                                <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Send Email</button>
                            </div>
                            <!-- Form actions end -->

                        </form>
                    </div>
                </div>
            </div>
            <!-- Toggle static offcanvas for recall from storage factory--> 
            <div class="offcanvas offcanvas-start-recall" data-bs-backdrop="static" tabindex="-1" id="staticBackdropRecall"
                 aria-labelledby="staticBackdropLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="staticBackdropLabel">Recall from Storage Factory</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <div class="table-responsive">
                        <table id="listStorage" class="table custom-table pending">
                            <thead>
                                <tr>
                                    <th class="col-3">Item ID</th>
                                    <th class="col-1">Box No</th>
                                    <th class="col-1">Rack</th>
                                    <th class="col-2">Shelf</th>
                                    <th class="col-1">Qty</th>
                                    <th class="col-1">Inventory Date</th>
                                    <th class="col-1">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="offcanvas offcanvas-start-EmailReplacement" data-bs-backdrop="static" tabindex="-1" id="staticBackdropEmailReplacement"
                 aria-labelledby="staticBackdropLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="staticBackdropLabel">Send Email for HW Replacement</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <div class="row mb-3">
                        <form class="row g-3 align-items-center" role="form" action="${contextPath}/rmsbookingDetail/addHwReplacement" method="post">
                            <div class="row mb-3">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-1">
                                        <label for="itemId" class="form-label">Item ID *</label>
                                        <div class="input input-group">
                                            <input type="hidden" class="form-control" id="id3" name="id3" placeholder="" value="${rms.id}">
                                            <select class="js-example-basic-single" id="hwReplacement" name="hwReplacement" style="width: 100%" <c:if test="${uac.befLoadingHwReplace ne 'Yes'}">disabled</c:if> required>
                                                    <option></option>
                                                <c:forEach items="${hwList}" var="invInner">
                                                    <option value="${invInner.pkid}">
                                                        ${invInner.itemId} (${invInner.status})
                                                    </option>
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
                                            <textarea class="form-control" rows="5" id="remarks" name="remarks" <c:if test="${uac.befLoadingHwReplace ne 'Yes'}">disabled</c:if>></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end" <c:if test="${uac.befLoadingHwReplace ne 'Yes'}">disabled</c:if>>Add</button>
                                </div>
                                <!-- Form actions end -->

                            </form>
                        </div>
                        <div class="row mb-3">
                            <div class="table-responsive">
                                <table id="listStorage" class="table custom-table pending">
                                    <thead>
                                        <tr>
                                            <th class="col-1">No</th>
                                            <th class="col-1">Item Type</th>
                                            <th class="col-3">item Id</th>
                                            <th class="col-1">Qty</th>
                                            <th class="col-2">Status</th>
                                            <th class="col-3">Remarks</th>
                                            <th class="col-1">Action</th>
                                        </tr>
                                    </thead>
                                <c:forEach items="${listHwReplace}" var="parameterMaster" varStatus="parameterMasterLoop">
                                    <tr>
                                        <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                        <td><c:out value="${parameterMaster.itemType}"/></td>
                                        <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.itemId}"/></td>
                                        <td><c:out value="${parameterMaster.qty}"/></td>
                                        <td><c:out value="${parameterMaster.status}"/></td>
                                        <td><c:out value="${parameterMaster.remarks}"/></td>
                                        <td align="center">
                                            <c:if test="${parameterMaster.flag == '0'}">
                                                <c:if test="${uac.befLoadingHwReplace == 'Yes'}">
                                                    <a modaldeleteid="${parameterMaster.id}" type ="button" title="Delete" data-bs-toggle="modal" data-bs-target="#delete_modal" 
                                                       class="table-link danger group_delete" onclick="modalDelete(this);">
                                                        <i class="bi bi-trash h3" style="color:red"></i></a> 
                                                    </c:if>
                                                    <c:if test="${uac.befLoadingHwReplace ne 'Yes'}">
                                                    <a type ="button" title="Delete" class="table-link danger group_delete disabled">
                                                        <i class="bi bi-trash h3" style="color:gray"></i></a> 
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${parameterMaster.flag != '0'}">
                                                <a type ="button" title="Delete" class="table-link danger group_delete disabled">
                                                    <i class="bi bi-trash h3" style="color:gray"></i></a> 
                                                </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <c:if test="${countHwReplace != '0'}">
                            <c:if test="${countHwReplaceFlagZero != '0'}">
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-2">
                                        <label for="emailTo" class="form-label">Email To</label>
                                        <div class="input input-group">
                                            <input type="text" class="form-control" id="emailTo" name="emailTo" placeholder="" value="${emailTo}" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-12 col-sm-12 col-12">
                                    <div class="mb-2">
                                        <label for="emailCc" class="form-label">CC</label>
                                        <div class="input input-group">
                                            <select class="mySelect" id="emailCc" name="emailCc" multiple="multiple" title="" data-live-search="true" style="width: 100%" <c:if test="${uac.befLoadingHwReplace ne 'Yes'}">disabled</c:if>>
                                                <c:forEach items="${listCc}" var="group">
                                                    <option value="${group.email}">${group.name}&nbsp;&nbsp;(${group.email})</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <a type ="button" title="Send Email" data-bs-toggle="modal" data-bs-target="#confirmation_modal" class="btn btn-outline-warning me-2 float-end <c:if test="${uac.befLoadingHwReplace ne 'Yes'}">disabled</c:if>" role="button" onclick="sendEmail();">
                                        <i class='bi bi-envelope-arrow-up'></i>&nbsp;&nbsp;Send Email to Planner</a>
                                </c:if>
                            </c:if>
                    </div>
                </div>
            </div>
        </div>

        <!--</div>-->
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
                                    $(document).ready(function () {
                                        $('.js-example-basic-single').select2();

                                        var placeholder = "Email cc";
                                        $(".mySelect").select2({

                                            allowClear: true,
                                            placeholder: placeholder,
                                            minimumInputLength: 3,
                                            page: 10
                                        });
                                    });

                                    document.addEventListener('DOMContentLoaded', function () {
                                        const url = new URL(window.location.href);
                                        if (url.searchParams.get('saved') === '1') {
                                            const el = document.getElementById('staticBackdropEmailReplacement');
                                            if (el)
                                                bootstrap.Offcanvas.getOrCreateInstance(el).show();
                                            // Clean the URL so F5 or navigation won’t auto-open again
                                            url.searchParams.delete('saved');
                                            window.history.replaceState({}, '', url);
                                        }
                                    });

                                    function modalDelete(e) {
                                        var bookingDetailId = $("#id").val();
                                        var deleteId = $(e).attr("modaldeleteid");
                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                        var deleteUrl = "${contextPath}/rmsbookingDetail/deleteHwReplacement/" + deleteId + "/" + bookingDetailId;
                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                        $("#delete_modal .modal-body").html(deleteMsg);
                                        $("#modal_delete_button").attr("href", deleteUrl);
                                    }

                                    function modalRelease(e) {
                                        var id = $(e).attr("modaldeleteid");
                                        var deleteUrl = "${contextPath}/rmsbookingDetail/release/" + id;
                                        var deleteMsg = "Are you sure want to release this RMS_Event to production?";
                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                        $("#modal_button").attr("href", deleteUrl);
                                    }

                                    function modalReleaseSingle(e) {
                                        var id = $(e).attr("modaldeleteid");
                                        var deleteUrl = "${contextPath}/rmsbookingDetail/releaseSingle/" + id;
                                        var deleteMsg = "Are you sure want to release this motherboard to production?";
                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                        $("#modal_button").attr("href", deleteUrl);
                                    }

                                    function sendEmail() {
                                        var bookingPkid = $("#bookingPkid").val();
                                        var emailCc = $("#emailCc").val();

                                        if (!emailCc || emailCc.length === 0) {
                                            emailCc = "0";
                                        } else {
                                            emailCc = $("#emailCc").val();
                                        }
                                        var deleteUrl = "${contextPath}/rmsbookingDetail/sendEmailReplacementByGroup/" + bookingPkid + "/" + emailCc;
                                        var deleteMsg = "Are you sure want to send email to Planner for HW replacement?";
                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                        $("#modal_button").attr("href", deleteUrl);
                                    }

                                    function getData(e) {
                                        var id = $(e).attr("modaldeleteid");
                                        $.ajax({
                                            url: '${contextPath}/rmsbookingDetail/emailBody', // Replace with your controller URL
                                            type: 'GET',
                                            data: {id: id},
                                            dataType: 'json',
                                            success: function (data) {
                                                // Populate form fields with received data
                                                $("#itemId2").val(data.itemId);
                                                $("#id2").val(data.id);
                                                $("#itemType").val(data.itemType);
                                            },
                                            error: function (jqXHR, textStatus, errorThrown) {
                                                console.error("Error loading data: " + textStatus, errorThrown);
                                            }
                                        });
                                    }

                                    $(document).ready(function () {
                                        $('.js-example-basic-single').select2();
                                    });

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
//                                            dom: "Blfrtip",
                                            dom: '<"top"Blfi>rt<"bottom"p><"clear">',
                                            buttons: ["copy", "csv", "pdf", "print"],
                                        });
                                    });

                                    $(function () {
                                        $("#customButtons2").DataTable({
                                            lengthMenu: [
                                                [10, 25, 50],
                                                [10, 25, 50, "All"],
                                            ],
                                            language: {
                                                lengthMenu: "Display _MENU_ Records Per Page",
//                        info: "Showing Page _PAGE_ of _PAGES_",
                                                info: "Showing _START_ to _END_ of _TOTAL_ total records",
                                            },
//                                            dom: "Blfrtip",
                                            dom: '<"top"Blfi>rt<"bottom"p><"clear">',
                                            buttons: ["copy", "csv", "pdf", "print"],
                                        });
                                    });

                                    function ajaxStorage(e) {
                                        var itemPKID = $(e).attr("modaldeleteid");
                                        var id = $(e).attr("modaldeleteid2");
                                        $('#listStorage').DataTable().destroy();
                                        new DataTable('#listStorage', {
                                            ajax: {
                                                data: {itemPKID: itemPKID},
                                                url: '${contextPath}/hw/item/ajaxStorage',
                                                dataSrc: ''
                                            },
                                            columns: [
                                                {"data": "itemId"},
                                                {"data": "boxNo"},
                                                {"data": "rack"},
                                                {"data": "shelf"},
                                                {"data": "qty"},
                                                {"data": "movementDateTime"},
                                                {
                                                    data: "invId", // This column won't directly map to a data field
                                                    render: function (data, type, row) {
                                                        return '<c:if test="${userItemSfRecall == 'Yes'}"><button class="btn btn-primary edit-btn" data-inv="' + data + '" data-pkid="' + itemPKID + '" data-id="' + id + '" data-bs-toggle="modal" data-bs-target="#confirmation_modal">Recall</button></c:if>';
                                                    }
                                                }
                                            ]
                                        });
                                    }

                                    $('#listStorage tbody').on('click', '.edit-btn', function () {
                                        var rowInv = $(this).data('inv'); // Get the 'data-id' attribute
                                        var rowPkid = $(this).data('pkid'); // Get the 'data-id' attribute
                                        var rowId = $(this).data('id'); // Get the 'data-id' attribute
                                        var rmsBookingId = $("#id").val();
                                        // Perform further actions, e.g., open a modal for editing
                                        if (rowId) {
                                            var deleteUrl = "${contextPath}/rmsbookingDetail/retrieveSF/" + rowInv + "/" + rowPkid + "/" + rowId + "/" + rmsBookingId;
                                            var deleteMsg = "Are you sure want to retrieve this item from Storage Factory?";
                                            $("#confirmation_modal .modal-body").html(deleteMsg);
                                            $("#modal_button").attr("href", deleteUrl);
                                        } else {
                                            var deleteUrl = "";
                                            var deleteMsg = "No Item Selected.";
                                            $("#confirmation_modal .modal-body").html(deleteMsg);
                                            $("#modal_button").attr("href", deleteUrl);
                                        }
                                    });
        </script>
    </s:layout-component>
</s:layout-render>