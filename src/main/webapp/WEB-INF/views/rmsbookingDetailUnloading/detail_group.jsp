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
            .select2-dropdown.select2-dropdown--below{
                /*width: 148px !important;*/
            }
            .select2-container--default .select2-selection--single{
                border: 1.5px solid #000;
                border-radius: 0.5rem;
                box-shadow: 2.5px 3px 0 #000;
                outline: none;
                transition: ease 0.25s;
            }
            .input {
                /*max-width: 190px;*/
                /*padding: 0.875rem;*/
                /*font-size: 1rem;*/
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
                content:" *";
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
            .email-btn {
                padding: 12px 24px;
                /*background-color: #6200ee;*/
                background-color: #EE4B00;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 500;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/rmsbookingDetailUnloading" class="btn btn-outline-warning me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title d-flex justify-content-between align-items-center">
                                <div>
                                    HW Return from Loading Module - <span style="color:#D97D55">Detail (${motherboardId})</span>
                                </div>
                            </h5>
                        </div>
                        <div class="card-body">
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/xde apa pon" method="post">
                                <div class="row mb-3">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="rms" class="form-label">RMS</label>
                                            <div class="input input-group">
                                                <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${rms.id}">
                                                <input type="hidden" class="form-control" id="bookingPkid" name="bookingPkid" placeholder="" value="${rms.bookingPkid}">
                                                <input type="hidden" class="form-control" id="groupId" name="groupId" placeholder="" value="${groupId}">
                                                <input type="text" class="form-control" id="rmsNo" name="rmsNo" placeholder="" value="${rms.rmsNo}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="event" class="form-label">Event</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="event" name="event" placeholder="" value="${rms.event}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="device" class="form-label">Device</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="device" name="device" placeholder="" value="${rms.device}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="packages" class="form-label">Package</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="packages" name="packages" placeholder="" value="${rms.packages}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="actStartDate" class="form-label">Unloading Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="actStartDate" name="actStartDate" placeholder="" value="${unloadingDate}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="equipmentLocation" class="form-label">Equipment Location</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="equipmentLocation" name="equipmentLocation" placeholder="" value="${rms.equipmentLocation}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="hwStatus" class="form-label">HW Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="hwStatus" name="hwStatus" placeholder="" value="${status}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="subStatus" class="form-label">Progress Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="subStatus" name="subStatus" placeholder="" value="${subStatus}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="bookingRemarks" class="form-label">Booking Remarks</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="3" id="bookingRemarks" name="bookingRemarks" readonly>${rmsRemarks}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="custom-tabs-container">
                                <ul class="fs-6 nav nav-tabs justify-content-center" id="customTab4" role="tablist">
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${hwActive}" id="tab-oneAAA" data-bs-toggle="tab" href="#oneAAA" role="tab"
                                           aria-controls="oneAAA" aria-selected="true" ><i class="bi bi-list-ol"></i>HW List</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${ionicActive}" id="tab-twoAAA" ${ionicColorStyle} data-bs-toggle="tab" href="#twoAAA" role="tab"
                                           aria-controls="twoAAA" aria-selected="true" ><i class="bi bi-moisture"></i>Ionic Test</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${vmActive}" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab"
                                           aria-controls="threeAAA" aria-selected="false"><i class="bi bi-search"></i>Visual Inspection Check</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${teActive}" id="tab-fourAAA" data-bs-toggle="tab" href="#fourAAA" role="tab"
                                           aria-controls="fourAAA" aria-selected="false"><i class="bi bi-clipboard-check"></i>Functional Test</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${haActive}" id="tab-fiveAAA" ${haColorStyle} data-bs-toggle="tab" href="#fiveAAA" role="tab"
                                           aria-controls="fiveAAA" aria-selected="false"><i class="bi bi-shuffle"></i>Release or Return (HAST)</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade ${hwActiveTab}" id="oneAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="col-12">
                                            <!-- Card start -->
                                            <div class="card mb-4">
                                                <div class="card-body">
                                                    <!-- Row start -->
                                                    <div class="row gx-3">
                                                        <!-- Personal Information Section -->
                                                        <div class="card-body">
                                                            <div class="table-responsive">
                                                                <table id="customButtons1" class="table custom-table pending">
                                                                    <thead>
                                                                        <tr>
                                                                            <!--<th class="col-12">Site</th>-->
                                                                            <th>No</th>
                                                                            <th>Hardware ID</th>
                                                                            <th>Item Type</th>
                                                                            <th>Item ID</th>
                                                                            <th>Registered By</th>
                                                                            <th>Registered Date</th>
                                                                            <!--<th>Manage</th>-->
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:forEach items="${hwGroupList}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                                        <tr>
                                                                            <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                                        <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.hardwareId}"/></td>
                                                                        <td><c:out value="${parameterMaster.itemType}"/></td>
                                                                        <td><c:out value="${parameterMaster.itemId}"/></td>
                                                                        <td><c:out value="${parameterMaster.createdBy}"/></td>
                                                                        <td><c:out value="${parameterMaster.createdDate}"/></td>
                                                                        <!--                                                                                <td align="center">
                                                                        <c:if test="${subStatus == 'Pending HW Registration'}">
                                                                            <a modaldeleteid="${parameterMaster.id}" type ="button" title="Delete" data-bs-toggle="modal" data-bs-target="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                                                <i class="bi bi-trash h3" style="color:red"></i>
                                                                            </a> 
                                                                        </c:if>
                                                                    </td>-->
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
                                    <div class="tab-pane fade ${ionicActiveTab}" id="twoAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="col-12">
                                            <div class="card mb-4">
                                                <div class="card-body">
                                                    <div class="row gx-3">
                                                        <div class="card-body">
                                                            <c:choose>    
                                                                <c:when test="${uac.unloadingIonic ne 'Yes'}">
                                                                    <fieldset disabled>
                                                                </c:when>    
                                                                <c:otherwise>
                                                                    <c:if test="${subStatus == 'Pending Ionic Test'}">
                                                                        <fieldset>
                                                                    </c:if>
                                                                    <c:if test="${subStatus != 'Pending Ionic Test'}">
                                                                        <fieldset disabled>
                                                                    </c:if>

                                                                </c:otherwise>
                                                            </c:choose>
                                                            <form class="row gx-3 needs-validation" role="form" action="${contextPath}/rmsbookingDetailUnloading/ionic/save" method="post" enctype="multipart/form-data" novalidate>
                                                                <input type="hidden" class="form-control" id="bookingPkid" name="bookingPkid" placeholder="" value="${rms.bookingPkid}">
                                                                <input type="hidden" class="form-control" id="groupId" name="groupId" placeholder="" value="${groupId}">
                                                                <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" placeholder="" value="${motherboardId}">
                                                                <input type="hidden" class="form-control" id="passValue" name="passValue" value="${ionicPassValue}">
                                                                <input type="hidden" class="form-control" id="hwStatus" name="hwStatus" value="${status}">
                                                                <div class="row">
                                                                    <div class="form-check">
                                                                        <input class="form-check-input" type="checkbox" value="" id="bypassIonic" name="bypassIonic" ${countPrevIonic}>
                                                                        <label class="form-check-label" for="bypassIonic">
                                                                            Bypass Ionic Test. Proceed with Clean & Bake
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-xl-1 col-sm-12">
                                                                        <div class="mb-3">
                                                                            <label for="quantity" class="form-label">BIB Result</label>
                                                                            <div class="input input-group">
                                                                                <input type="number" class="form-control" id="bibResult" name="bibResult" value="${ionic.bibResult}" autofocus="" style="width: 100%" ${requiredDisable}>
                                                                                <div id="result" class="mt-2"></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-xl-1 col-sm-12">
                                                                        <label class="form-label">BIB Status</label>
                                                                        <input type="text" class="form-control" id="bibStatus" name="bibStatus" value="${ionic.bibStatus}" readonly>
                                                                    </div>

                                                                    <div class="col-xl-5 col-sm-12">
                                                                        <div class="mb-3">
                                                                            <label for="winUpload" class="form-label">Upload BIB Result</label>
                                                                            <div class="input input-group">
                                                                                <input class="form-control" type="file" id="bibUpload" name="bibUpload" ${disabledUpload}>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <c:if test="${not empty ionic.bibUpload}">
                                                                        <div class="row gx-4">
                                                                            <div class="col-xl-2 col-sm-12 col-12">
                                                                                <div class="mb-2">
                                                                                    <a class="form-label" style="color:red;" href="${contextPath}/rmsbookingDetailUnloading/ionic/downloadAttach/${ionic.id}/bib" id="bibAttach" name="bibAttach"> Download BIB Result</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>

                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-xl-1 col-sm-12">
                                                                        <div class="mb-3">
                                                                            <label for="quantity" class="form-label">BIB Card Result</label>
                                                                            <div class="input input-group">
                                                                                <input type="number" class="form-control" id="bibCardResult" name="bibCardResult" value="${ionic.bibCardResult}" style="width: 100%" ${requiredDisable}>
                                                                                <div id="resultCard" class="mt-2"></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-xl-1 col-sm-12">
                                                                        <label class="form-label">BIB Card Status</label>
                                                                        <input type="text" class="form-control" id="bibCardStatus" name="bibCardStatus" value="${ionic.bibCardStatus}" readonly>
                                                                    </div>

                                                                    <div class="col-xl-5 col-sm-12">
                                                                        <div class="mb-3">
                                                                            <label for="winUpload" class="form-label">Upload BIB Card Result</label>
                                                                            <div class="input input-group">
                                                                                <input class="form-control" type="file" id="bibCardUpload" name="bibCardUpload" ${disabledUpload}>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <c:if test="${not empty ionic.bibCardUpload}">
                                                                        <div class="row gx-4">
                                                                            <div class="col-xl-2 col-sm-12 col-12">
                                                                                <div class="mb-2">
                                                                                    <a class="form-label" style="color:red;" href="${contextPath}/rmsbookingDetailUnloading/ionic/downloadAttach/${ionic.id}/bibCard" id="bibCardAttach" name="bibCardAttach"> Download BIB Card Result</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                </div>
                                                                <div class="col-md-12">
                                                                    <button type="submit" id="saveIonic" class="btn btn-primary float-end ${disabledUpload}">Save</button>
                                                                </div>
                                                            </form>
                                                            </fieldset>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Row end -->
                                    </div> 
                                    <div class="tab-pane fade ${vmActiveTab}" id="threeAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <c:choose>    
                                                <c:when test="${uac.unloadingVm ne 'Yes'}">
                                                    <fieldset disabled>
                                                </c:when>    
                                                <c:otherwise>
                                                    <c:if test="${subStatus == 'Pending VM'}">
                                                        <fieldset>
                                                    </c:if>
                                                    <c:if test="${subStatus != 'Pending VM'}">
                                                        <fieldset disabled>
                                                    </c:if>

                                                </c:otherwise>
                                            </c:choose>
                                            <form class="row gx-3 needs-validation" role="form" action="${contextPath}/rmsbookingDetailUnloading/vm/save" method="post" enctype="multipart/form-data" novalidate>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="pcb">PCB</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input type="hidden" class="form-control" id="groupId" name="groupId" placeholder="" value="${groupId}">
                                                                            <input type="hidden" class="form-control" id="bookingPkid" name="bookingPkid" placeholder="" value="${rms.bookingPkid}">
                                                                            <input type="hidden" class="form-control" id="hwStatus" name="hwStatus" value="${status}">
                                                                            <input class="form-check-input" type="radio" name="pcb" id="pcb1"
                                                                                   value="Pass" <c:if test="${itemVm.pcb == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="inlineRadio1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcb" id="pcb2"
                                                                                   value="Fail" <c:if test="${itemVm.pcb == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="inlineRadio2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcb" id="pcb3"
                                                                                   value="NA" <c:if test="${itemVm.pcb == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="inlineRadio3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="pcbHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="pcbHardwareId" name="pcbHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="pcbRejectQty" name="pcbRejectQty" placeholder="" value="${itemVm.pcbRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="pcbReject" name="pcbReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${pcbReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="pcbRejectUpload" name="pcbRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/pcb" id="pcbAttach" name="pcbAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="Handle">Handle</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="handle" id="handle1"
                                                                                   value="Pass" <c:if test="${itemVm.handle == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="handle1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="handle" id="handle2"
                                                                                   value="Fail" <c:if test="${itemVm.handle == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="handle2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="handle" id="handle3"
                                                                                   value="NA" <c:if test="${itemVm.handle == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="handle3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="handleHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="handleHardwareId" name="handleHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="handleRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="handleRejectQty" name="handleRejectQty" placeholder="" value="${itemVm.handleRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="handleReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="handleReject" name="handleReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${handleReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="handleRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="handleRejectUpload" name="handleRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/handle" id="handleAttach" name="handleAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="metalFrame">Metal Frame</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="metalFrame" id="metalFrame1"
                                                                                   value="Pass" <c:if test="${itemVm.metalFrame == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="metalFrame1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="metalFrame" id="metalFrame2"
                                                                                   value="Fail" <c:if test="${itemVm.metalFrame == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="metalFrame2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="metalFrame" id="metalFrame3"
                                                                                   value="NA" <c:if test="${itemVm.metalFrame == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="metalFrame">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="metalFrameHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="metalFrameHardwareId" name="metalFrameHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="metalFrameRejectQty" name="metalFrameRejectQty" placeholder="" value="${itemVm.metalFrameRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="metalFrameReject" name="metalFrameReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${metalFrameReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="metalFrameRejectUpload" name="metalFrameRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/metalFrame" id="metalFrameAttach" name="metalFrameAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="hardwareFasterners">Hardware Fasteners</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="hardwareFasterners" id="hardwareFasterners1"
                                                                                   value="Pass" <c:if test="${itemVm.hardwareFasterners == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="hardwareFasterners1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="hardwareFasterners" id="hardwareFasterners2"
                                                                                   value="Fail" <c:if test="${itemVm.hardwareFasterners == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="hardwareFasterners2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="hardwareFasterners" id="hardwareFasterners3"
                                                                                   value="NA" <c:if test="${itemVm.hardwareFasterners == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="hardwareFasterners3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="hardwareFasternersHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="hardwareFasternersHardwareId" name="hardwareFasternersHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="hardwareFasternersRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="hardwareFasternersRejectQty" name="hardwareFasternersRejectQty" placeholder="" value="${itemVm.hardwareFasternersRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="hardwareFasternersReject" name="hardwareFasternersReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${hardwareFasternersReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="hardwareFasternersRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control required" type="file" id="hardwareFasternersRejectUpload" name="hardwareFasternersRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/hardwareFasterners" id="hardwareFasternersAttach" name="hardwareFasternersAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="clipHolder">Clip Holder</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="clipHolder" id="clipHolder1"
                                                                                   value="Pass" <c:if test="${itemVm.clipHolder == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="clipHolder1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="clipHolder" id="clipHolder2"
                                                                                   value="Fail" <c:if test="${itemVm.clipHolder == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="clipHolder2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="clipHolder" id="clipHolder3"
                                                                                   value="NA" <c:if test="${itemVm.clipHolder == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="clipHolder3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="clipHolderHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="clipHolderHardwareId" name="clipHolderHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="clipHolderRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="clipHolderRejectQty" name="clipHolderRejectQty" placeholder="" value="${itemVm.clipHolderRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="clipHolderReject" name="clipHolderReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${clipHolderReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="clipHolderRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="clipHolderRejectUpload" name="clipHolderRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/clipHolder" id="clipHolderAttach" name="clipHolderAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="pcbEdgeFinger">PCB Edge Finger</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcbEdgeFinger" id="pcbEdgeFinger1"
                                                                                   value="Pass" <c:if test="${itemVm.pcbEdgeFinger == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="pcbEdgeFinger1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcbEdgeFinger" id="pcbEdgeFinger2"
                                                                                   value="Fail" <c:if test="${itemVm.pcbEdgeFinger == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pcbEdgeFinger2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcbEdgeFinger" id="pcbEdgeFinger3"
                                                                                   value="NA" <c:if test="${itemVm.pcbEdgeFinger == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pcbEdgeFinger3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="pcbEdgeFingerHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="pcbEdgeFingerHardwareId" name="pcbEdgeFingerHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbEdgeFingerRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="pcbEdgeFingerRejectQty" name="pcbEdgeFingerRejectQty" placeholder="" value="${itemVm.pcbEdgeFingerRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="pcbEdgeFingerReject" name="pcbEdgeFingerReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${pcbEdgeFingerReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbEdgeFingerRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="pcbEdgeFingerRejectUpload" name="pcbEdgeFingerRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/pcbEdgeFinger" id="pcbEdgeFingerAttach" name="pcbEdgeFingerAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="connector">Connector</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="connector" id="connector1"
                                                                                   value="Pass" <c:if test="${itemVm.connector == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="connector1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="connector" id="connector2"
                                                                                   value="Fail" <c:if test="${itemVm.connector == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="connector2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="connector" id="connector3"
                                                                                   value="NA" <c:if test="${itemVm.connector == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="connector3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="connectorHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="connectorHardwareId" name="connectorHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="connectorRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                            <input type="number" class="form-control" id="connectorRejectQty" name="connectorRejectQty" placeholder="" value="${itemVm.connectorRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="connectorReject" name="connectorReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${connectorReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="connectorRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="connectorRejectUpload" name="connectorRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/connector" id="connectorAttach" name="connectorAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="dutSockets">DUT Sockets</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="dutSockets" id="dutSockets1"
                                                                                   value="Pass" <c:if test="${itemVm.dutSockets == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="dutSockets1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="dutSockets" id="dutSockets2"
                                                                                   value="Fail" <c:if test="${itemVm.dutSockets == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="dutSockets2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="dutSockets" id="dutSockets3"
                                                                                   value="NA" <c:if test="${itemVm.dutSockets == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="dutSockets3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="dutSocketsHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="dutSocketsHardwareId" name="dutSocketsHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="dutSocketsRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="dutSocketsRejectQty" name="dutSocketsRejectQty" placeholder="" value="${itemVm.dutSocketsRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="dutSocketsReject" name="dutSocketsReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${dutSocketsReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="dutSocketsRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="dutSocketsRejectUpload" name="dutSocketsRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/dutSockets" id="dutSocketsAttach" name="dutSocketsAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="edgeMbBanana">Edge MB Banana</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="edgeMbBanana" id="edgeMbBanana1"
                                                                                   value="Pass" <c:if test="${itemVm.edgeMbBanana == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="edgeMbBanana1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="edgeMbBanana" id="edgeMbBanana2"
                                                                                   value="Fail" <c:if test="${itemVm.edgeMbBanana == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="edgeMbBanana2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="edgeMbBanana" id="edgeMbBanana3"
                                                                                   value="NA" <c:if test="${itemVm.edgeMbBanana == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="edgeMbBanana3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="edgeMbBananaHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="edgeMbBananaHardwareId" name="edgeMbBananaHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="edgeMbBananaRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="edgeMbBananaRejectQty" name="edgeMbBananaRejectQty" placeholder="" value="${itemVm.edgeMbBananaRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="edgeMbBananaReject" name="edgeMbBananaReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${edgeMbBananaReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="edgeMbBananaRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="edgeMbBananaRejectUpload" name="edgeMbBananaRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/edgeMbBanana" id="edgeMbBananaAttach" name="edgeMbBananaAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="electComponent">Electronic Components</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="electComponent" id="electComponent1"
                                                                                   value="Pass" <c:if test="${itemVm.electComponent == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="electComponent1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="electComponent" id="electComponent2"
                                                                                   value="Fail" <c:if test="${itemVm.electComponent == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="electComponent2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="electComponent" id="electComponent3"
                                                                                   value="NA" <c:if test="${itemVm.electComponent == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="electComponent3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="electComponentHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="electComponentHardwareId" name="electComponentHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="electComponentRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="electComponentRejectQty" name="electComponentRejectQty" placeholder="" value="${itemVm.electComponentRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="electComponentReject" name="electComponentReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${electComponentReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="electComponentRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="electComponentRejectUpload" name="electComponentRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/electComponent" id="electComponentAttach" name="electComponentAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="solderJoint">Solder Joint</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="solderJoint" id="solderJoint1"
                                                                                   value="Pass" <c:if test="${itemVm.solderJoint == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="solderJoint1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="solderJoint" id="solderJoint2"
                                                                                   value="Fail" <c:if test="${itemVm.solderJoint == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="solderJoint2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="solderJoint" id="solderJoint3"
                                                                                   value="NA" <c:if test="${itemVm.solderJoint == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="solderJoint3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="solderJointHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="solderJointHardwareId" name="solderJointHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="solderJointRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="solderJointRejectQty" name="solderJointRejectQty" placeholder="" value="${itemVm.solderJointRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="solderJointReject" name="solderJointReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${solderJointReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="solderJointRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="solderJointRejectUpload" name="solderJointRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/solderJoint" id="solderJointAttach" name="solderJointAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="winConnector">Win Connector</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="winConnector" id="winConnector1"
                                                                                   value="Pass" <c:if test="${itemVm.winConnector == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="winConnector1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="winConnector" id="winConnector2"
                                                                                   value="Fail" <c:if test="${itemVm.winConnector == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="winConnector2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="winConnector" id="winConnector3"
                                                                                   value="NA" <c:if test="${itemVm.winConnector == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="winConnector3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="winConnectorHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="winConnectorHardwareId" name="winConnectorHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="winConnectorRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="winConnectorRejectQty" name="winConnectorRejectQty" placeholder="" value="${itemVm.winConnectorRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="winConnectorReject" name="winConnectorReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${winConnectorReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="winConnectorRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="winConnectorRejectUpload" name="winConnectorRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/winConnector" id="winConnectorAttach" name="winConnectorAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="teflonConnector">Teflon Connector</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="teflonConnector" id="teflonConnector1"
                                                                                   value="Pass" <c:if test="${itemVm.teflonConnector == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="teflonConnector1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="teflonConnector" id="teflonConnector2"
                                                                                   value="Fail" <c:if test="${itemVm.teflonConnector == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="teflonConnector2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="teflonConnector" id="teflonConnector3"
                                                                                   value="NA" <c:if test="${itemVm.teflonConnector == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="teflonConnector3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="teflonConnectorHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="teflonConnectorHardwareId" name="teflonConnectorHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="teflonConnectorRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="teflonConnectorRejectQty" name="teflonConnectorRejectQty" placeholder="" value="${itemVm.teflonConnectorRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="teflonConnectorReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="teflonConnectorReject" name="teflonConnectorReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${teflonConnectorReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="teflonConnectorRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="teflonConnectorRejectUpload" name="teflonConnectorRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/teflonConnector" id="teflonConnectorAttach" name="teflonConnectorAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="pogoReceptaclesPin">Pogo / Receptacles Pin</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pogoReceptaclesPin" id="pogoReceptaclesPin1"
                                                                                   value="Pass" <c:if test="${itemVm.pogoReceptaclesPin == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="pogoReceptaclesPin1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pogoReceptaclesPin" id="pogoReceptaclesPin2"
                                                                                   value="Fail" <c:if test="${itemVm.pogoReceptaclesPin == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pogoReceptaclesPin2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pogoReceptaclesPin" id="pogoReceptaclesPin3"
                                                                                   value="NA" <c:if test="${itemVm.pogoReceptaclesPin == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pogoReceptaclesPin3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="pogoReceptaclesPinHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="pogoReceptaclesPinHardwareId" name="pogoReceptaclesPinHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pogoReceptaclesPinRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="pogoReceptaclesPinRejectQty" name="pogoReceptaclesPinRejectQty" placeholder="" value="${itemVm.pogoReceptaclesPinRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pogoReceptaclesPinReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="pogoReceptaclesPinReject" name="pogoReceptaclesPinReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${pogoReceptaclesPinReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pogoReceptaclesPinRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="pogoReceptaclesPinRejectUpload" name="pogoReceptaclesPinRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/pogoReceptaclesPin" id="pogoReceptaclesPinAttach" name="pogoReceptaclesPinAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="cableWiredCopperWire">Cable/Wired/Copper Wire</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="cableWiredCopperWire" id="cableWiredCopperWire1"
                                                                                   value="Pass" <c:if test="${itemVm.cableWiredCopperWire == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="cableWiredCopperWire1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="cableWiredCopperWire" id="cableWiredCopperWire2"
                                                                                   value="Fail" <c:if test="${itemVm.cableWiredCopperWire == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="cableWiredCopperWire2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="cableWiredCopperWire" id="cableWiredCopperWire3"
                                                                                   value="NA" <c:if test="${itemVm.cableWiredCopperWire == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="cableWiredCopperWire3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="cableWiredCopperWireHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="cableWiredCopperWireHardwareId" name="cableWiredCopperWireHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="cableWiredCopperWireRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="cableWiredCopperWireRejectQty" name="cableWiredCopperWireRejectQty" placeholder="" value="${itemVm.cableWiredCopperWireRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="cableWiredCopperWireReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="cableWiredCopperWireReject" name="cableWiredCopperWireReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${cableWiredCopperWireReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="cableWiredCopperWireRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="cableWiredCopperWireRejectUpload" name="cableWiredCopperWireRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/cableWiredCopperWire" id="cableWiredCopperWireAttach" name="cableWiredCopperWireAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="labelIdentification">Label & Identification</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="labelIdentification" id="labelIdentification1"
                                                                                   value="Pass" <c:if test="${itemVm.labelIdentification == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="labelIdentification1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="labelIdentification" id="labelIdentification2"
                                                                                   value="Fail" <c:if test="${itemVm.labelIdentification == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="labelIdentification2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="labelIdentification" id="labelIdentification3"
                                                                                   value="NA" <c:if test="${itemVm.labelIdentification == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="labelIdentification3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="labelIdentificationHardwareId" class="form-label">Reject Hardware ID</label>
                                                                    <div class="input input-group">
                                                                        <select class="js-example-basic-multiple" id="labelIdentificationHardwareId" name="labelIdentificationHardwareId" multiple="multiple" title="" data-live-search="true" style="width: 100%">
                                                                            <c:forEach items="${hwGroupList}" var="invInner">
                                                                                <option value="${invInner.hardwareId}">${invInner.hardwareId}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="labelIdentificationRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="labelIdentificationRejectQty" name="labelIdentificationRejectQty" placeholder="" value="${itemVm.labelIdentificationRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="labelIdentificationReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="labelIdentificationReject" name="labelIdentificationReject"
                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                                <option></option>
                                                                                <c:forEach items="${labelIdentificationReject}" var="invInner">
                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-xl-6 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="labelIdentificationRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="labelIdentificationRejectUpload" name="labelIdentificationRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/rmsbookingDetailUnloading/vm/downloadAttach/${itemVm.id}/labelIdentification" id="labelIdentificationAttach" name="labelIdentificationAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Form actions start -->
                                                <div class="col-md-12">
                                                    <button type="submit" id="submitVm" name="submitVm" class="btn btn-primary float-end ${buttonVm}">Save</button>
                                                </div>
                                            </form>
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade ${teActiveTab}" id="fourAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <div class="col-12">
                                                <div class="col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <div class="accordion" id="accordionPanelsStayOpenExample">
                                                                <c:choose>
                                                                    <c:when test="${configMotherboard == 'TRIGGERERROR'}">
                                                                        <p>${message}</p>
<!--                                                                        <p>${itemIdMB} - DATA MOTHERBOARD</p>
                                                                        <p>${itemIdLC} - DATA LOAD CARD</p>-->
                                                                    </c:when>
                                                                    <c:when test="${configMotherboard == 'HW' || configMotherboard == 'VM'}">
                                                                        <p>${message}</p>
                                                                    </c:when>
                                                                    <c:otherwise>
<!--                                                                        <p>DEKAT SINI PTT KELUAR DATA MACAM BIASA. :::: ${configMotherboard}</p>
                                                                        <p>${itemIdMB} - DATA MOTHERBOARD</p>
                                                                        <p>${itemIdLC} - DATA LOAD CARD</p>
                                                                        <p>book pkid ::: ${configMotherboard}</p>-->
                                                                        <!--<p>current status ::: ${currentStatus}</p>-->
                                                                        <p>${message}</p>

                                                                        <c:if test="${leakCheck eq 'Yes'}">
                                                                            <div class="accordion-item">
                                                                                <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                                                                                    <button class="accordion-button ${leaktab}" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" id="tabLeak" aria-controls="panelsStayOpen-collapseThree">
                                                                                        Leakage Test
                                                                                    </button>
                                                                                </h2>
                                                                                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse ${leakshow}" aria-labelledby="panelsStayOpen-headingThree">
                                                                                    <div class="accordion-body">
                                                                                        <c:choose>    
                                                                                            <c:when test="${uac.unloadingFt ne 'Yes'}">
                                                                                                <fieldset disabled>
                                                                                            </c:when>    
                                                                                            <c:otherwise>
                                                                                                <fieldset>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetailUnloading/ftest/save/leakTest" method="post" enctype="multipart/form-data" novalidate>
                                                                                            <input type="hidden" class="form-control" id="bookId" name="bookId" value="${bookId}">
                                                                                            <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                                                            <div class="form-group required col-xl-1 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="quantity" class="form-label">Quantity</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input type="number" class="form-control" id="totalQty" name="totalQty" value="${testResult.leakQty}" style="width: 100%" required>
                                                                                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" value="${item.id}">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="leakResult" class="form-label">Leakage Result</label>
                                                                                                    <select class="select-single js-states form-control" id="leakResult" name="leakResult"
                                                                                                            title="Select Leakage Result" data-live-search="true" style="width: 100%" required>
                                                                                                        <option></option>
                                                                                                        <c:forEach items="${leakResultData}" var="leakResult">
                                                                                                            <option value="${leakResult.name}" ${leakResult.selected}>${leakResult.name}</option>
                                                                                                        </c:forEach>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-xl-3 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="leakUpload" class="form-label">Upload Result</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input class="form-control" type="file" id="leakUpload" name="leakUpload">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <c:if test="${not empty testResult.leakUpload}">
                                                                                                <div class="col-xl-2 col-sm-12">
                                                                                                    <div class="mb-3">
                                                                                                        <a class="form-label" href="${contextPath}/hw/item/ft/leaktest/${item.id}" id="leakTestAttach" name="leakTestAttach">Download Leakage Test</a>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </c:if>
                                                                                            <div class="col-xl-6 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="leakHardware" class="form-label">Reject Hardware ID</label>
                                                                                                    <div class="input input-group">
                                                                                                        <select class="js-example-basic-multiple" id="leakHardware" name="leakHardware" multiple="multiple" style="width: 100%">
                                                                                                            <c:forEach items="${hwGroupList}" var="leakHw">
                                                                                                                <option value="${leakHw.hardwareId}">${leakHw.hardwareId}</option>
                                                                                                            </c:forEach>
                                                                                                        </select>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-12">
                                                                                                <button type="submit" id="saveLeak" class="btn btn-primary me-2 float-end ${leakbutton}">Save</button>
                                                                                                <button type="submit" id="updateLeak" class="btn btn-primary me-2 float-end visually-hidden">Update</button>
                                                                                                <a id="editLeak" class="btn btn-secondary me-2 float-end ${editleakbutton}" onclick="allowUpdateLeak();" role="button">Edit</a>
                                                                                                <a id="editLeak2" class="btn btn-secondary me-2 float-end visually-hidden" onclick="allowUpdateLeak2();" role="button">Cancel</a>
                                                                                            </div>
                                                                                        </form>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                        <c:if test="${manCheck eq 'Yes'}">
                                                                            <div class="accordion-item">
                                                                                <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                                                                    <button class="accordion-button ${manualtab}" type="button" data-bs-toggle="collapse"
                                                                                            data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" id="tabManual"
                                                                                            aria-controls="panelsStayOpen-collapseTwo">
                                                                                        Manual Test
                                                                                    </button>
                                                                                </h2>
                                                                                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse ${manshow}" aria-labelledby="panelsStayOpen-headingTwo">
                                                                                    <div class="accordion-body">
                                                                                        <c:choose>    
                                                                                            <c:when test="${uac.unloadingFt ne 'Yes'}">
                                                                                                <fieldset disabled>
                                                                                            </c:when>    
                                                                                            <c:otherwise>
                                                                                                <fieldset>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetailUnloading/createManualTest" method="post" enctype="multipart/form-data">
                                                                                            <div class="row">
                                                                                                <input type="hidden" class="form-control" id="bookId" name="bookId" value="${bookId}">
                                                                                                <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                                                                <div class="col-sm-6">
                                                                                                    <label for="status" class="form-label">Status</label>
                                                                                                    <div>
                                                                                                        <input type="text" class="form-control" id="labelStatus" name="labelStatus" value="${currentStatus}" readonly>
                                                                                                        <input type="hidden" class="form-control" name="mbItemId" id="mbItemId" value="${itemIdMB}">
                                                                                                        <input type="hidden" class="form-control" name="lcItemId" id="lcItemId" value="${itemIdLC}">
                                                                                                        <input type="hidden" class="form-control" name="groupId" id="groupId" value="${groupId}">
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="col-sm-3">
                                                                                                </div>
                                                                                                <div class="col-sm-1">
                                                                                                    <label>Quantity <span style="color:red;">*</span></label>
                                                                                                    <c:choose>
                                                                                                        <c:when test="${not empty testResult.manualQty}">
                                                                                                            <div class="input input-group">
                                                                                                                <input type="number" class="form-control" name="totalQty" id="totalQty" value="${testResult.manualQty}" readonly>
                                                                                                            </div>
                                                                                                        </c:when>
                                                                                                        <c:otherwise>
                                                                                                            <div class="input input-group">
                                                                                                                <input type="number" class="form-control" name="totalQty" id="totalQty" required>
                                                                                                            </div>
                                                                                                        </c:otherwise>
                                                                                                    </c:choose>
                                                                                                </div>
                                                                                                <div class="col-sm-2">
                                                                                                    <div class="p-3 d-flex justify-content-end">
                                                                                                        <!--<a href="https://mysed-rel-app05/HEATS-mini/manual_test_before_loading.php?id=${item.id}" class="leads rounded-3 d-xxl-flex d-none">-->
    <!--                                                                                                    <a href="http://zbqb9x-7jwwld4:85//Tutorial/sample-heat/manual_test_before_loading.php?id=${item.id}" class="leads rounded-3 d-xxl-flex d-none ${manualbutton}">
                                                                                                            <i class="bi bi-box-arrow-right" style="color:#ffffff"></i>&nbsp;&nbsp;Inspect Manual Test
                                                                                                        </a>-->
                                                                                                        <button type="submit" id="saveManual" class="btn btn-primary me-2 float-end ${manualbutton}">Save Manual</button>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </form>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                        <c:if test="${bibCheck eq 'Yes'}">
                                                                            <div class="accordion-item">
                                                                                <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                                                                    <button class="accordion-button ${bibtab}" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false" aria-controls="panelsStayOpen-collapseOne" id="tabBib">
                                                                                        BIB Test
                                                                                    </button>
                                                                                </h2>
                                                                                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse ${bibshow}" aria-labelledby="panelsStayOpen-headingOne">
                                                                                    <div class="accordion-body">
                                                                                        <c:choose>    
                                                                                            <c:when test="${uac.unloadingFt ne 'Yes'}">
                                                                                                <fieldset disabled>
                                                                                            </c:when>    
                                                                                            <c:otherwise>
                                                                                                <fieldset>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetailUnloading/ftest/save/bibTest" method="post" enctype="multipart/form-data" novalidate>
                                                                                            <input type="hidden" class="form-control" id="bookId" name="bookId" value="${bookId}">
                                                                                            <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                                                            <div class="form-group required col-xl-1 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="quantity" class="form-label">Quantity</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input type="number" class="form-control" id="totalQty" name="totalQty" value="${testResult.bibQty}" style="width: 100%" required>
                                                                                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" value="${item.id}">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="bibResult" class="form-label">BIB Result</label>
                                                                                                    <select class="select-single js-states form-control" id="bibResult" name="bibResult" title="Select BIB Result" data-live-search="true" style="width: 100%" required>
                                                                                                        <option></option>
                                                                                                        <c:forEach items="${bibResultData}" var="bibResult">
                                                                                                            <option value="${bibResult.name}" ${bibResult.selected}>${bibResult.name}</option>
                                                                                                        </c:forEach>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-xl-3 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="bibUpload" class="form-label">Upload Result</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input class="form-control" type="file" id="bibUpload" name="bibUpload">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <c:if test="${not empty testResult.bibUpload}">
                                                                                                <div class="col-xl-2 col-sm-12">
                                                                                                    <div class="mb-3">
                                                                                                        <a class="form-label" href="${contextPath}/hw/item/ft/bibtest/${item.id}" id="bibTestAttach" name="bibTestAttach">Download BIB Test</a>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </c:if>
                                                                                            <div class="col-xl-6 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="bibHardware" class="form-label">Reject Hardware ID</label>
                                                                                                    <div class="input input-group">
                                                                                                        <select class="js-example-basic-multiple" id="bibHardware" name="bibHardware" multiple="multiple" style="width: 100%">
                                                                                                            <c:forEach items="${hwGroupList}" var="bibHw">
                                                                                                                <option value="${bibHw.hardwareId}">${bibHw.hardwareId}</option>
                                                                                                            </c:forEach>
                                                                                                        </select>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-12">
                                                                                                <button type="submit" id="saveBib" class="btn btn-primary float-end ${bibbutton}">Save</button>
                                                                                                <button type="submit" id="updateBib" class="btn btn-primary me-2 float-end visually-hidden">Update</button>
                                                                                                <a id="editBib1" class="btn btn-secondary me-2 float-end ${editbibbutton}" onclick="allowUpdateBib1();" role="button">Edit</a>
                                                                                                <a id="editBib2" class="btn btn-secondary me-2 float-end visually-hidden" onclick="allowUpdateBib2();" role="button">Cancel</a>
                                                                                            </div>
                                                                                        </form>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                        <c:if test="${daqCheck eq 'Yes'}">
                                                                            <div class="accordion-item">
                                                                                <h2 class="accordion-header" id="panelsStayOpen-headingSix">
                                                                                    <button class="accordion-button ${daqtab}" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseSix" aria-expanded="false" aria-controls="panelsStayOpen-collapseSix" id="tabBibD">
                                                                                        BIB DAQ
                                                                                    </button>
                                                                                </h2>
                                                                                <div id="panelsStayOpen-collapseSix" class="accordion-collapse collapse ${bibDshow}" aria-labelledby="panelsStayOpen-headingSix">
                                                                                    <div class="accordion-body">
                                                                                        <c:choose>    
                                                                                            <c:when test="${uac.unloadingFt ne 'Yes'}">
                                                                                                <fieldset disabled>
                                                                                            </c:when>    
                                                                                            <c:otherwise>
                                                                                                <fieldset>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetailUnloading/ftest/save/bibDaqTest" method="post" enctype="multipart/form-data" novalidate>
                                                                                            <input type="hidden" class="form-control" id="bookId" name="bookId" value="${bookId}">
                                                                                            <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                                                            <div class="form-group required col-xl-1 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="quantity" class="form-label">Quantity</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input type="number" class="form-control" id="totalQty" name="totalQty" value="${testResult.bibDaqQty}" style="width: 100%" required>
                                                                                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" value="${item.id}">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="bibDaqResult" class="form-label">BIB DAQ Result</label>
                                                                                                    <select class="select-single js-states form-control" id="bibDaqResult" name="bibDaqResult" title="Select BIB DAQ Result" data-live-search="true" style="width: 100%" required>
                                                                                                        <option></option>
                                                                                                        <c:forEach items="${bibDaqResultData}" var="daqResult">
                                                                                                            <option value="${daqResult.name}" ${daqResult.selected}>${daqResult.name}</option>
                                                                                                        </c:forEach>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-xl-3 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="bibDaqUpload" class="form-label">Upload Result</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input class="form-control" type="file" id="bibDaqUpload" name="bibDaqUpload">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <c:if test="${not empty testResult.bibDaqUpload}">
                                                                                                <div class="col-xl-2 col-sm-12">
                                                                                                    <div class="mb-3">
                                                                                                        <a class="form-label" href="${contextPath}/hw/item/ft/bibdaqtest/${item.id}" id="bibDaqTestAttach" name="bibDaqTestAttach">Download BIB DAQ Test</a>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </c:if>
                                                                                            <div class="col-xl-6 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="bibDaqHardware" class="form-label">Reject Hardware ID</label>
                                                                                                    <div class="input input-group">
                                                                                                        <select class="js-example-basic-multiple" id="bibDaqHardware" name="bibDaqHardware" multiple="multiple" style="width: 100%">
                                                                                                            <c:forEach items="${hwGroupList}" var="bibHw">
                                                                                                                <option value="${bibHw.hardwareId}">${bibHw.hardwareId}</option>
                                                                                                            </c:forEach>
                                                                                                        </select>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-12">
                                                                                                <button type="submit" id="saveBibDaq" class="btn btn-primary float-end ${bibdaqbutton}">Save</button>
                                                                                                <button type="submit" id="updateBibDaq" class="btn btn-primary me-2 float-end visually-hidden">Update</button>
                                                                                                <a id="editBibDaq1" class="btn btn-secondary me-2 float-end ${editbibdaqbutton}" onclick="allowUpdateBibDaq1();" role="button">Edit</a>
                                                                                                <a id="editBibDaq2" class="btn btn-secondary me-2 float-end visually-hidden" onclick="allowUpdateBibDaq2();" role="button">Cancel</a>
                                                                                            </div>
                                                                                        </form>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                        <c:if test="${psCheck eq 'Yes'}">
                                                                            <div class="accordion-item">
                                                                                <h2 class="accordion-header" id="panelsStayOpen-headingFour">
                                                                                    <button class="accordion-button ${powertab}" type="button" data-bs-toggle="collapse"
                                                                                            data-bs-target="#panelsStayOpen-collapseFour" aria-expanded="false" id="tabPs"
                                                                                            aria-controls="panelsStayOpen-collapseFour">
                                                                                        Power Supply Leakage Test
                                                                                    </button>
                                                                                </h2>
                                                                                <div id="panelsStayOpen-collapseFour" class="accordion-collapse collapse ${psshow}"
                                                                                     aria-labelledby="panelsStayOpen-headingFour">
                                                                                    <div class="accordion-body">
                                                                                        <c:choose>    
                                                                                            <c:when test="${uac.unloadingFt ne 'Yes'}">
                                                                                                <fieldset disabled>
                                                                                            </c:when>    
                                                                                            <c:otherwise>
                                                                                                <fieldset>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetailUnloading/ftest/save/psTest" method="post" enctype="multipart/form-data" novalidate>
                                                                                            <input type="hidden" class="form-control" id="bookId" name="bookId" value="${bookId}">
                                                                                            <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                                                            <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="quantity" class="form-label">Quantity</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input type="number" class="form-control" id="totalQty" name="totalQty" value="${testResult.psQty}" style="width: 100%" required>
                                                                                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" value="${item.id}">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="psResult" class="form-label">Power Supply Leakage Result</label>
                                                                                                    <select class="select-single js-states form-control" id="psResult" name="psResult"
                                                                                                            title="Select Leakage Result" data-live-search="true" style="width: 100%" >
                                                                                                        <option></option>
                                                                                                        <c:forEach items="${psResultData}" var="invInner">
                                                                                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                                        </c:forEach>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-xl-3 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="psUpload" class="form-label">Upload Result</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input class="form-control" type="file" id="psUpload" name="psUpload">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <c:if test="${not empty testResult.psUpload}">
                                                                                                <div class="col-xl-2 col-sm-12">
                                                                                                    <div class="mb-3">
                                                                                                        <a class="form-label" href="${contextPath}/hw/item/ft/pstest/${item.id}" id="psTestAttach" name="psTestAttach">Download Power Supply Leakage Test</a>
                                                                                                    </div>
                                                                                                </div>        
                                                                                            </c:if>
                                                                                            <div class="col-xl-6 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="psHardware" class="form-label">Reject Hardware ID</label>
                                                                                                    <div class="input input-group">
                                                                                                        <select class="js-example-basic-multiple" id="psHardware" name="psHardware" multiple="multiple" style="width: 100%">
                                                                                                            <c:forEach items="${hwGroupList}" var="psHw">
                                                                                                                <option value="${psHw.hardwareId}">${psHw.hardwareId}</option>
                                                                                                            </c:forEach>
                                                                                                        </select>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-12">
                                                                                                <button type="submit" id="savePower" class="btn btn-primary float-end ${psbutton}">Save</button>
                                                                                                <button type="submit" id="updatePower" class="btn btn-primary me-2 float-end visually-hidden">Update</button>
                                                                                                <a id="editPower1" class="btn btn-secondary me-2 float-end ${editpsbutton}" onclick="allowUpdatePower1();" role="button">Edit</a>
                                                                                                <a id="editPower2" class="btn btn-secondary me-2 float-end visually-hidden" onclick="allowUpdatePower2();" role="button">Cancel</a>
                                                                                            </div>
                                                                                        </form>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                        <c:if test="${winCheck eq 'Yes'}">
                                                                            <div class="accordion-item">
                                                                                <h2 class="accordion-header" id="panelsStayOpen-headingFive">
                                                                                    <button class="accordion-button ${wintab}" type="button" data-bs-toggle="collapse"
                                                                                            data-bs-target="#panelsStayOpen-collapseFive" aria-expanded="false" id="tabWin"
                                                                                            aria-controls="panelsStayOpen-collapseFive">
                                                                                        Winchester Chamber Leakage Test
                                                                                    </button>
                                                                                </h2>
                                                                                <div id="panelsStayOpen-collapseFive" class="accordion-collapse collapse ${winshow}"
                                                                                     aria-labelledby="panelsStayOpen-headingFive">
                                                                                    <div class="accordion-body">
                                                                                        <c:choose>    
                                                                                            <c:when test="${uac.unloadingFt ne 'Yes'}">
                                                                                                <fieldset disabled>
                                                                                            </c:when>    
                                                                                            <c:otherwise>
                                                                                                <fieldset>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetailUnloading/ftest/save/winTest" method="post" enctype="multipart/form-data" novalidate>
                                                                                            <input type="hidden" class="form-control" id="bookId" name="bookId" value="${bookId}">
                                                                                            <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                                                            <div class="form-group required col-xl-1 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="quantity" class="form-label">Quantity</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input type="number" class="form-control" id="totalQty" name="totalQty" value="${testResult.winQty}" style="width: 100%" required>
                                                                                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" value="${item.id}">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="winResult" class="form-label">Winchester Chamber Leakage Result</label>
                                                                                                    <select class="select-single js-states form-control" id="winResult" name="winResult"
                                                                                                            title="Select Winchester Chamber Leakage Result" data-live-search="true" style="width: 100%" required>
                                                                                                        <option></option>
                                                                                                        <c:forEach items="${winResultData}" var="invInner">
                                                                                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                                        </c:forEach>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-xl-3 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="winUpload" class="form-label">Upload Result</label>
                                                                                                    <div class="input input-group">
                                                                                                        <input class="form-control" type="file" id="winUpload" name="winUpload">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <c:if test="${not empty testResult.winUpload}">
                                                                                                <div class="col-xl-2 col-sm-12">
                                                                                                    <div class="mb-3">
                                                                                                        <a class="form-label" href="${contextPath}/hw/item/ft/wintest/${item.id}" id="winTestAttach" name="winTestAttach">Download Winchester Chamber Leakage Test</a>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </c:if>
                                                                                            <div class="col-xl-6 col-sm-12">
                                                                                                <div class="mb-3">
                                                                                                    <label for="winHardware" class="form-label">Reject Hardware ID</label>
                                                                                                    <div class="input input-group">
                                                                                                        <select class="js-example-basic-multiple" id="winHardware" name="winHardware" multiple="multiple" style="width: 100%">
                                                                                                            <c:forEach items="${hwGroupList}" var="winHw">
                                                                                                                <option value="${winHw.hardwareId}">${winHw.hardwareId}</option>
                                                                                                            </c:forEach>
                                                                                                        </select>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-12">
                                                                                                <button type="submit" id="saveWin" class="btn btn-primary float-end ${winbutton}">Save</button>
                                                                                                <button type="submit" id="updateWin" class="btn btn-primary me-2 float-end visually-hidden">Update</button>
                                                                                                <a id="editWin1" class="btn btn-secondary me-2 float-end ${editwinbutton}" onclick="allowUpdateWin1();" role="button">Edit</a>
                                                                                                <a id="editWin2" class="btn btn-secondary me-2 float-end visually-hidden" onclick="allowUpdateWin2();" role="button">Cancel</a>
                                                                                            </div>
                                                                                        </form>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade ${haActiveTab}" id="fiveAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <c:choose>    
                                                <c:when test="${uac.unloadingReleaseClose ne 'Yes'}">
                                                    <fieldset disabled>
                                                </c:when>    
                                                <c:otherwise>
                                                    <fieldset>
                                                </c:otherwise>
                                            </c:choose>
                                            <form class="row gx-3 needs-validation" role="form" action="${contextPath}/rmsbookingDetailUnloading/releaseOrReturn/save" method="post" novalidate>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">
                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="pcb">Production Disposition</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input type="hidden" class="form-control" id="groupId" name="groupId" placeholder="" value="${groupId}">
                                                                            <input type="hidden" class="form-control" id="bookingPkid" name="bookingPkid" placeholder="" value="${rms.bookingPkid}">
                                                                            <input type="hidden" class="form-control" id="hwStatus" name="hwStatus" value="${status}">
                                                                            <input class="form-check-input" type="radio" name="releaseOrReturn" id="releaseOrReturn1" required>
                                                                            <label class="form-check-label" for="releaseOrReturn1" required>Released to Production</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="releaseOrReturn" id="releaseOrReturn2" required>
                                                                            <label class="form-check-label" for="releaseOrReturn2">Return from Production Staging (Return to Inventory)</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-12 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <label for="pcbHardwareId" class="form-label">Remarks</label>
                                                                    <div class="input input-group">
                                                                        <textarea class="form-control" rows="3" id="releaseOrReturnRemarks" name="releaseOrReturnRemarks" required></textarea>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Form actions start -->
                                                <div class="col-md-12">
                                                    <button type="submit" id="submitHa" name="submitHa" class="btn btn-primary float-end ${buttonHa}">Save</button>
                                                </div>
                                            </form>
                                            </fieldset>
                                        </div>
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
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/customitem/dataTables.js"></script>
        <!--<script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>-->

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


                                                                                                    $("#bypassIonic").change(function () {
                                                                                                        if ($(this).is(":checked")) {
                                                                                                            $("#bibResult").val("0");
                                                                                                            $("#bibStatus").val("BYPASS");
                                                                                                            $("#bibCardResult").val("0");
                                                                                                            $("#bibCardStatus").val("BYPASS");
                                                                                                        } else {
                                                                                                            $("#bibResult").val("");
                                                                                                            $("#bibStatus").val("");
                                                                                                            $("#bibCardResult").val("");
                                                                                                            $("#bibCardStatus").val("");
                                                                                                        }
                                                                                                    });




                                                                                                    document.addEventListener("DOMContentLoaded", function () {

                                                                                                        function checkStatus(inputEl, statusEl, passValueEl) {
                                                                                                            let value = parseFloat(inputEl.value);
                                                                                                            let passValue = parseFloat(passValueEl.value);

                                                                                                            if (isNaN(value)) {
                                                                                                                statusEl.value = "";
                                                                                                                statusEl.classList.remove("is-valid", "is-invalid");
                                                                                                                return;
                                                                                                            }

                                                                                                            if (value <= passValue) {
                                                                                                                statusEl.value = "PASS";
                                                                                                                statusEl.classList.add("is-valid");
                                                                                                                statusEl.classList.remove("is-invalid");
                                                                                                            } else {
                                                                                                                statusEl.value = "FAIL";
                                                                                                                statusEl.classList.add("is-invalid");
                                                                                                                statusEl.classList.remove("is-valid");
                                                                                                            }
                                                                                                        }

                                                                                                        // ✅ Elements
                                                                                                        const bibResult = document.getElementById("bibResult");
                                                                                                        const bibStatus = document.getElementById("bibStatus");
                                                                                                        const bibPassValue = document.getElementById("passValue");

                                                                                                        const bibCardResult = document.getElementById("bibCardResult");
                                                                                                        const bibCardStatus = document.getElementById("bibCardStatus");

                                                                                                        // ✅ Events
                                                                                                        bibResult.addEventListener("input", function () {
                                                                                                            checkStatus(bibResult, bibStatus, bibPassValue);
                                                                                                        });

                                                                                                        bibCardResult.addEventListener("input", function () {
                                                                                                            checkStatus(bibCardResult, bibCardStatus, bibPassValue);
                                                                                                        });

                                                                                                    });



                                                                                                    $(document).ready(function () {
                                                                                                        const divBib = document.getElementById('tabBib');
                                                                                                        const divDaq = document.getElementById('tabBibD');
                                                                                                        const divMan = document.getElementById('tabManual');
                                                                                                        const divLeak = document.getElementById('tabLeak');
                                                                                                        const divPs = document.getElementById('tabPs');
                                                                                                        const divWin = document.getElementById('tabWin');

                                                                                                        const checkStatus = "${currentStatus}";

                                                                                                        //                console.log("KITA TENGOK STATUS SINI :::: "+checkStatus);

                                                                                                        $('.js-example-basic-multiple').select2();
                                                                                                        var valueJsonPcb = ${valueJsonPcb};
                                                                                                        $('#pcbHardwareId').val(valueJsonPcb).trigger('change');

                                                                                                        var valueJsonHandle = ${valueJsonHandle};
                                                                                                        $('#handleHardwareId').val(valueJsonHandle).trigger('change');

                                                                                                        var valueJsonMetalFrame = ${valueJsonMetalFrame};
                                                                                                        $('#metalFrameHardwareId').val(valueJsonMetalFrame).trigger('change');

                                                                                                        var valueJsonHardwareFasterners = ${valueJsonHardwareFasterners};
                                                                                                        $('#hardwareFasternersHardwareId').val(valueJsonHardwareFasterners).trigger('change');

                                                                                                        var valueJsonClipHolder = ${valueJsonClipHolder};
                                                                                                        $('#clipHolderHardwareId').val(valueJsonClipHolder).trigger('change');

                                                                                                        var valueJsonPcbEdgeFinger = ${valueJsonPcbEdgeFinger};
                                                                                                        $('#pcbEdgeFingerHardwareId').val(valueJsonPcbEdgeFinger).trigger('change');

                                                                                                        var valueJsonConnector = ${valueJsonConnector};
                                                                                                        $('#connectorHardwareId').val(valueJsonConnector).trigger('change');

                                                                                                        var valueJsonDutSockets = ${valueJsonDutSockets};
                                                                                                        $('#dutSocketsHardwareId').val(valueJsonDutSockets).trigger('change');

                                                                                                        var valueJsonEdgeMbBanana = ${valueJsonEdgeMbBanana};
                                                                                                        $('#edgeMbBananaHardwareId').val(valueJsonEdgeMbBanana).trigger('change');

                                                                                                        var valueJsonElectComponent = ${valueJsonElectComponent};
                                                                                                        $('#electComponentHardwareId').val(valueJsonElectComponent).trigger('change');

                                                                                                        var valueJsonSolderJoint = ${valueJsonSolderJoint};
                                                                                                        $('#solderJointHardwareId').val(valueJsonSolderJoint).trigger('change');

                                                                                                        var valueJsonWinConnector = ${valueJsonWinConnector};
                                                                                                        $('#winConnectorHardwareId').val(valueJsonWinConnector).trigger('change');

                                                                                                        var valueJsonTeflonConnector = ${valueJsonTeflonConnector};
                                                                                                        $('#teflonConnectorHardwareId').val(valueJsonTeflonConnector).trigger('change');

                                                                                                        var valueJsonPogoReceptaclesPin = ${valueJsonPogoReceptaclesPin};
                                                                                                        $('#pogoReceptaclesPinHardwareId').val(valueJsonPogoReceptaclesPin).trigger('change');

                                                                                                        var valueJsonCableWiredCopperWire = ${valueJsonCableWiredCopperWire};
                                                                                                        $('#cableWiredCopperWireHardwareId').val(valueJsonCableWiredCopperWire).trigger('change');

                                                                                                        var valueJsonLabelIdentification = ${valueJsonLabelIdentification};
                                                                                                        $('#labelIdentificationHardwareId').val(valueJsonLabelIdentification).trigger('change');

                                                                                                        var element2 = $('#viId');
                                                                                                        if (element2.val()) {
                                                                                                            $("#submitVm").attr("disabled", true);
                                                                                                        }

                                                                                                        $('#leakResult').trigger('change');
                                                                                                        $('#bibResult').trigger('change');
                                                                                                        $('#bibDaqResult').trigger('change');
                                                                                                        $('#psResult').trigger('change');
                                                                                                        $('#winResult').trigger('change');
                                                                                                    });

                                                                                                    $('#leakResult').on('change', function () {
                                                                                                        var selectedValue = $(this).val();
                                                                                                        var $hardwareField = $('#leakHardware');
                                                                                                        if (selectedValue === 'Fail') {
                                                                                                            $hardwareField.prop('disabled', false);
                                                                                                        } else {
                                                                                                            $hardwareField.prop('disabled', true).val(null);
                                                                                                            if ($hardwareField.hasClass('select2-hidden-accessible')) {
                                                                                                                $hardwareField.trigger('change');
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                                    $('#bibResult').on('change', function () {
                                                                                                        var selectedValue = $(this).val();
                                                                                                        var $hardwareField = $('#bibHardware');
                                                                                                        if (selectedValue === 'Fail') {
                                                                                                            $hardwareField.prop('disabled', false);
                                                                                                        } else {
                                                                                                            $hardwareField.prop('disabled', true).val(null);
                                                                                                            if ($hardwareField.hasClass('select2-hidden-accessible')) {
                                                                                                                $hardwareField.trigger('change');
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                                    $('#bibDaqResult').on('change', function () {
                                                                                                        var selectedValue = $(this).val();
                                                                                                        var $hardwareField = $('#bibDaqHardware');
                                                                                                        if (selectedValue === 'Fail') {
                                                                                                            $hardwareField.prop('disabled', false);
                                                                                                        } else {
                                                                                                            $hardwareField.prop('disabled', true).val(null);
                                                                                                            if ($hardwareField.hasClass('select2-hidden-accessible')) {
                                                                                                                $hardwareField.trigger('change');
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                                    $('#psResult').on('change', function () {
                                                                                                        var selectedValue = $(this).val();
                                                                                                        var $hardwareField = $('#psHardware');
                                                                                                        if (selectedValue === 'Fail') {
                                                                                                            $hardwareField.prop('disabled', false);
                                                                                                        } else {
                                                                                                            $hardwareField.prop('disabled', true).val(null);
                                                                                                            if ($hardwareField.hasClass('select2-hidden-accessible')) {
                                                                                                                $hardwareField.trigger('change');
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                                    $('#winResult').on('change', function () {
                                                                                                        var selectedValue = $(this).val();
                                                                                                        var $hardwareField = $('#winHardware');
                                                                                                        if (selectedValue === 'Fail') {
                                                                                                            $hardwareField.prop('disabled', false);
                                                                                                        } else {
                                                                                                            $hardwareField.prop('disabled', true).val(null);
                                                                                                            if ($hardwareField.hasClass('select2-hidden-accessible')) {
                                                                                                                $hardwareField.trigger('change');
                                                                                                            }
                                                                                                        }
                                                                                                    });

                                                                                                    function allowUpdateLeak() {
                                                                                                        const hehehe = document.getElementById('updateLeak');
                                                                                                        const huhu = document.getElementById('editLeak2');
                                                                                                        $("#editLeak").prop("hidden", true);
                                                                                                        $("#updateLeak").prop("hidden", false);
                                                                                                        hehehe.classList.remove('visually-hidden');
                                                                                                        huhu.classList.remove('visually-hidden');
                                                                                                    }
                                                                                                    function allowUpdateLeak2() {
                                                                                                        const hehehe = document.getElementById('updateLeak');
                                                                                                        const huhu = document.getElementById('editLeak2');
                                                                                                        $("#editLeak").prop("hidden", false);
                                                                                                        $("#updateLeak").prop("hidden", true);
                                                                                                        hehehe.classList.add('visually-hidden');
                                                                                                        huhu.classList.add('visually-hidden');
                                                                                                    }

                                                                                                    function allowUpdateBib1() {
                                                                                                        const hehehe = document.getElementById('updateBib');
                                                                                                        const huhu = document.getElementById('editBib2');
                                                                                                        $("#editBib1").prop("hidden", true);
                                                                                                        $("#updateBib").prop("hidden", false);
                                                                                                        hehehe.classList.remove('visually-hidden');
                                                                                                        huhu.classList.remove('visually-hidden');
                                                                                                    }
                                                                                                    function allowUpdateBib2() {
                                                                                                        const hehehe = document.getElementById('updateBib');
                                                                                                        const huhu = document.getElementById('editBib2');
                                                                                                        $("#editBib1").prop("hidden", false);
                                                                                                        $("#updateBib").prop("hidden", true);
                                                                                                        hehehe.classList.add('visually-hidden');
                                                                                                        huhu.classList.add('visually-hidden');
                                                                                                    }

                                                                                                    function allowUpdateBibDaq1() {
                                                                                                        const hehehe = document.getElementById('updateBibDaq');
                                                                                                        const huhu = document.getElementById('editBibDaq2');
                                                                                                        $("#editBibDaq1").prop("hidden", true);
                                                                                                        $("#updateBibDaq").prop("hidden", false);
                                                                                                        hehehe.classList.remove('visually-hidden');
                                                                                                        huhu.classList.remove('visually-hidden');
                                                                                                    }
                                                                                                    function allowUpdateBib2() {
                                                                                                        const hehehe = document.getElementById('updateBibDaq');
                                                                                                        const huhu = document.getElementById('editBibDaq2');
                                                                                                        $("#editBibDaq1").prop("hidden", false);
                                                                                                        $("#updateBibDaq").prop("hidden", true);
                                                                                                        hehehe.classList.add('visually-hidden');
                                                                                                        huhu.classList.add('visually-hidden');
                                                                                                    }

                                                                                                    function allowUpdatePower1() {
                                                                                                        const hehehe = document.getElementById('updatePower');
                                                                                                        const huhu = document.getElementById('editPower2');
                                                                                                        $("#editPower1").prop("hidden", true);
                                                                                                        $("#updatePower").prop("hidden", false);
                                                                                                        hehehe.classList.remove('visually-hidden');
                                                                                                        huhu.classList.remove('visually-hidden');
                                                                                                    }
                                                                                                    function allowUpdatePower2() {
                                                                                                        const hehehe = document.getElementById('updatePower');
                                                                                                        const huhu = document.getElementById('editPower2');
                                                                                                        $("#editPower1").prop("hidden", false);
                                                                                                        $("#updatePower").prop("hidden", true);
                                                                                                        hehehe.classList.add('visually-hidden');
                                                                                                        huhu.classList.add('visually-hidden');
                                                                                                    }

                                                                                                    function allowUpdateWin1() {
                                                                                                        const hehehe = document.getElementById('updateWin');
                                                                                                        const huhu = document.getElementById('editWin2');
                                                                                                        $("#editWin1").prop("hidden", true);
                                                                                                        $("#updateWin").prop("hidden", false);
                                                                                                        hehehe.classList.remove('visually-hidden');
                                                                                                        huhu.classList.remove('visually-hidden');
                                                                                                    }
                                                                                                    function allowUpdateWin2() {
                                                                                                        const hehehe = document.getElementById('updateWin');
                                                                                                        const huhu = document.getElementById('editWin2');
                                                                                                        $("#editWin1").prop("hidden", false);
                                                                                                        $("#updateWin").prop("hidden", true);
                                                                                                        hehehe.classList.add('visually-hidden');
                                                                                                        huhu.classList.add('visually-hidden');
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

                                                                                                    function modalDelete(e) {
                                                                                                        var deleteId = $(e).attr("modaldeleteid");
                                                                                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                                                                        var deleteUrl = "${contextPath}/rmsbookingDetailUnloading/deleteHwId/" + deleteId;
                                                                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                                                                    }

                                                                                                    function modalFinalize(e) {
                                                                                                        var groupId = $(e).attr("modaldeleteid");
                                                                                                        var deleteUrl = "${contextPath}/rmsbookingDetailUnloading/finalize/" + groupId;
                                                                                                        var deleteMsg = "Do you confirm that all information is complete and ready to finalize?";
                                                                                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                                                                                        $("#modal_button").attr("href", deleteUrl);
                                                                                                    }

                                                                                                    function modalUndoFinalize(e) {
                                                                                                        var groupId = $(e).attr("modaldeleteid");
                                                                                                        var deleteUrl = "${contextPath}/rmsbookingDetailUnloading/undoFinalize/" + groupId;
                                                                                                        var deleteMsg = "Do you confirm to undo the finalization?";
                                                                                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                                                                                        $("#modal_button").attr("href", deleteUrl);
                                                                                                    }

                                                                                                    // Fetch all the forms we want to apply custom Bootstrap validation styles to
                                                                                                    const forms = document.querySelectorAll('.needs-validation');

                                                                                                    // Loop over them and prevent submission
                                                                                                    Array.prototype.slice.call(forms).forEach((form) => {
                                                                                                        form.addEventListener('submit', (event) => {
                                                                                                            if (!form.checkValidity()) {
                                                                                                                event.preventDefault();
                                                                                                                event.stopPropagation();
                                                                                                            }
                                                                                                            form.classList.add('was-validated');
                                                                                                        }, false);
                                                                                                    });

                                                                                                    $(".js-example-basic-single").select2({
                                                                                                        placeholder: "Choose one",
                                                                                                        allowClear: true
                                                                                                    });

                                                                                                    $(".js-example-tags").select2({
                                                                                                        tags: true
                                                                                                    });
                                                                                                    // Get references to the elements

                                                                                                    const pcbPass = document.getElementById('pcb1');
                                                                                                    const pcbNa = document.getElementById('pcb3');
                                                                                                    const pcbFail = document.getElementById('pcb2');
                                                                                                    const pcbRejectCriteria = document.getElementById('pcbReject');
                                                                                                    const pcbRejectQty = document.getElementById('pcbRejectQty');
                                                                                                    const pcbRejectUpload = document.getElementById('pcbRejectUpload');
                                                                                                    const pcbAttach = document.getElementById('pcbAttach');
                                                                                                    const pcbHardwareId = document.getElementById('pcbHardwareId');

                                                                                                    const handlePass = document.getElementById('handle1');
                                                                                                    const handleNa = document.getElementById('handle3');
                                                                                                    const handleFail = document.getElementById('handle2');
                                                                                                    const handleRejectCriteria = document.getElementById('handleReject');
                                                                                                    const handleRejectQty = document.getElementById('handleRejectQty');
                                                                                                    const handleRejectUpload = document.getElementById('handleRejectUpload');
                                                                                                    const handleAttach = document.getElementById('handleAttach');
                                                                                                    const handleHardwareId = document.getElementById('handleHardwareId');

                                                                                                    const metalFramePass = document.getElementById('metalFrame1');
                                                                                                    const metalFrameNa = document.getElementById('metalFrame3');
                                                                                                    const metalFrameFail = document.getElementById('metalFrame2');
                                                                                                    const metalFrameRejectCriteria = document.getElementById('metalFrameReject');
                                                                                                    const metalFrameRejectQty = document.getElementById('metalFrameRejectQty');
                                                                                                    const metalFrameRejectUpload = document.getElementById('metalFrameRejectUpload');
                                                                                                    const metalFrameAttach = document.getElementById('metalFrameAttach');
                                                                                                    const metalFrameHardwareId = document.getElementById('metalFrameHardwareId');

                                                                                                    const hardwareFasternersPass = document.getElementById('hardwareFasterners1');
                                                                                                    const hardwareFasternersNa = document.getElementById('hardwareFasterners3');
                                                                                                    const hardwareFasternersFail = document.getElementById('hardwareFasterners2');
                                                                                                    const hardwareFasternersRejectCriteria = document.getElementById('hardwareFasternersReject');
                                                                                                    const hardwareFasternersRejectQty = document.getElementById('hardwareFasternersRejectQty');
                                                                                                    const hardwareFasternersRejectUpload = document.getElementById('hardwareFasternersRejectUpload');
                                                                                                    const hardwareFasternersAttach = document.getElementById('hardwareFasternersAttach');
                                                                                                    const hardwareFasternersHardwareId = document.getElementById('hardwareFasternersHardwareId');

                                                                                                    const clipHolderPass = document.getElementById('clipHolder1');
                                                                                                    const clipHolderNa = document.getElementById('clipHolder3');
                                                                                                    const clipHolderFail = document.getElementById('clipHolder2');
                                                                                                    const clipHolderRejectCriteria = document.getElementById('clipHolderReject');
                                                                                                    const clipHolderRejectQty = document.getElementById('clipHolderRejectQty');
                                                                                                    const clipHolderRejectUpload = document.getElementById('clipHolderRejectUpload');
                                                                                                    const clipHolderAttach = document.getElementById('clipHolderAttach');
                                                                                                    const clipHolderHardwareId = document.getElementById('clipHolderHardwareId');

                                                                                                    const pcbEdgeFingerPass = document.getElementById('pcbEdgeFinger1');
                                                                                                    const pcbEdgeFingerNa = document.getElementById('pcbEdgeFinger3');
                                                                                                    const pcbEdgeFingerFail = document.getElementById('pcbEdgeFinger2');
                                                                                                    const pcbEdgeFingerRejectCriteria = document.getElementById('pcbEdgeFingerReject');
                                                                                                    const pcbEdgeFingerRejectQty = document.getElementById('pcbEdgeFingerRejectQty');
                                                                                                    const pcbEdgeFingerRejectUpload = document.getElementById('pcbEdgeFingerRejectUpload');
                                                                                                    const pcbEdgeFingerAttach = document.getElementById('pcbEdgeFingerAttach');
                                                                                                    const pcbEdgeFingerHardwareId = document.getElementById('pcbEdgeFingerHardwareId');

                                                                                                    const connectorPass = document.getElementById('connector1');
                                                                                                    const connectorNa = document.getElementById('connector3');
                                                                                                    const connectorFail = document.getElementById('connector2');
                                                                                                    const connectorRejectCriteria = document.getElementById('connectorReject');
                                                                                                    const connectorRejectQty = document.getElementById('connectorRejectQty');
                                                                                                    const connectorRejectUpload = document.getElementById('connectorRejectUpload');
                                                                                                    const connectorAttach = document.getElementById('connectorAttach');
                                                                                                    const connectorHardwareId = document.getElementById('connectorHardwareId');

                                                                                                    const dutSocketsPass = document.getElementById('dutSockets1');
                                                                                                    const dutSocketsNa = document.getElementById('dutSockets3');
                                                                                                    const dutSocketsFail = document.getElementById('dutSockets2');
                                                                                                    const dutSocketsRejectCriteria = document.getElementById('dutSocketsReject');
                                                                                                    const dutSocketsRejectQty = document.getElementById('dutSocketsRejectQty');
                                                                                                    const dutSocketsRejectUpload = document.getElementById('dutSocketsRejectUpload');
                                                                                                    const dutSocketsAttach = document.getElementById('dutSocketsAttach');
                                                                                                    const dutSocketsHardwareId = document.getElementById('dutSocketsHardwareId');

                                                                                                    const edgeMbBananaPass = document.getElementById('edgeMbBanana1');
                                                                                                    const edgeMbBananaNa = document.getElementById('edgeMbBanana3');
                                                                                                    const edgeMbBananaFail = document.getElementById('edgeMbBanana2');
                                                                                                    const edgeMbBananaRejectCriteria = document.getElementById('edgeMbBananaReject');
                                                                                                    const edgeMbBananaRejectQty = document.getElementById('edgeMbBananaRejectQty');
                                                                                                    const edgeMbBananaRejectUpload = document.getElementById('edgeMbBananaRejectUpload');
                                                                                                    const edgeMbBananaAttach = document.getElementById('edgeMbBananaAttach');
                                                                                                    const edgeMbBananaHardwareId = document.getElementById('edgeMbBananaHardwareId');

                                                                                                    const electComponentPass = document.getElementById('electComponent1');
                                                                                                    const electComponentNa = document.getElementById('electComponent3');
                                                                                                    const electComponentFail = document.getElementById('electComponent2');
                                                                                                    const electComponentRejectCriteria = document.getElementById('electComponentReject');
                                                                                                    const electComponentRejectQty = document.getElementById('electComponentRejectQty');
                                                                                                    const electComponentRejectUpload = document.getElementById('electComponentRejectUpload');
                                                                                                    const electComponentAttach = document.getElementById('electComponentAttach');
                                                                                                    const electComponentHardwareId = document.getElementById('electComponentHardwareId');

                                                                                                    const solderJointPass = document.getElementById('solderJoint1');
                                                                                                    const solderJointNa = document.getElementById('solderJoint3');
                                                                                                    const solderJointFail = document.getElementById('solderJoint2');
                                                                                                    const solderJointRejectCriteria = document.getElementById('solderJointReject');
                                                                                                    const solderJointRejectQty = document.getElementById('solderJointRejectQty');
                                                                                                    const solderJointRejectUpload = document.getElementById('solderJointRejectUpload');
                                                                                                    const solderJointAttach = document.getElementById('solderJointAttach');
                                                                                                    const solderJointHardwareId = document.getElementById('solderJointHardwareId');

                                                                                                    const winConnectorPass = document.getElementById('winConnector1');
                                                                                                    const winConnectorNa = document.getElementById('winConnector3');
                                                                                                    const winConnectorFail = document.getElementById('winConnector2');
                                                                                                    const winConnectorRejectCriteria = document.getElementById('winConnectorReject');
                                                                                                    const winConnectorRejectQty = document.getElementById('winConnectorRejectQty');
                                                                                                    const winConnectorRejectUpload = document.getElementById('winConnectorRejectUpload');
                                                                                                    const winConnectorAttach = document.getElementById('winConnectorAttach');
                                                                                                    const winConnectorHardwareId = document.getElementById('winConnectorHardwareId');

                                                                                                    const teflonConnectorPass = document.getElementById('teflonConnector1');
                                                                                                    const teflonConnectorNa = document.getElementById('teflonConnector3');
                                                                                                    const teflonConnectorFail = document.getElementById('teflonConnector2');
                                                                                                    const teflonConnectorRejectCriteria = document.getElementById('teflonConnectorReject');
                                                                                                    const teflonConnectorRejectQty = document.getElementById('teflonConnectorRejectQty');
                                                                                                    const teflonConnectorRejectUpload = document.getElementById('teflonConnectorRejectUpload');
                                                                                                    const teflonConnectorAttach = document.getElementById('teflonConnectorAttach');
                                                                                                    const teflonConnectorHardwareId = document.getElementById('teflonConnectorHardwareId');

                                                                                                    const pogoReceptaclesPinPass = document.getElementById('pogoReceptaclesPin1');
                                                                                                    const pogoReceptaclesPinNa = document.getElementById('pogoReceptaclesPin3');
                                                                                                    const pogoReceptaclesPinFail = document.getElementById('pogoReceptaclesPin2');
                                                                                                    const pogoReceptaclesPinRejectCriteria = document.getElementById('pogoReceptaclesPinReject');
                                                                                                    const pogoReceptaclesPinRejectQty = document.getElementById('pogoReceptaclesPinRejectQty');
                                                                                                    const pogoReceptaclesPinRejectUpload = document.getElementById('pogoReceptaclesPinRejectUpload');
                                                                                                    const pogoReceptaclesPinAttach = document.getElementById('pogoReceptaclesPinAttach');
                                                                                                    const pogoReceptaclesPinHardwareId = document.getElementById('pogoReceptaclesPinHardwareId');

                                                                                                    const cableWiredCopperWirePass = document.getElementById('cableWiredCopperWire1');
                                                                                                    const cableWiredCopperWireNa = document.getElementById('cableWiredCopperWire3');
                                                                                                    const cableWiredCopperWireFail = document.getElementById('cableWiredCopperWire2');
                                                                                                    const cableWiredCopperWireRejectCriteria = document.getElementById('cableWiredCopperWireReject');
                                                                                                    const cableWiredCopperWireRejectQty = document.getElementById('cableWiredCopperWireRejectQty');
                                                                                                    const cableWiredCopperWireRejectUpload = document.getElementById('cableWiredCopperWireRejectUpload');
                                                                                                    const cableWiredCopperWireAttach = document.getElementById('cableWiredCopperWireAttach');
                                                                                                    const cableWiredCopperWireHardwareId = document.getElementById('cableWiredCopperWireHardwareId');

                                                                                                    const labelIdentificationPass = document.getElementById('labelIdentification1');
                                                                                                    const labelIdentificationNa = document.getElementById('labelIdentification3');
                                                                                                    const labelIdentificationFail = document.getElementById('labelIdentification2');
                                                                                                    const labelIdentificationRejectCriteria = document.getElementById('labelIdentificationReject');
                                                                                                    const labelIdentificationRejectQty = document.getElementById('labelIdentificationRejectQty');
                                                                                                    const labelIdentificationRejectUpload = document.getElementById('labelIdentificationRejectUpload');
                                                                                                    const labelIdentificationAttach = document.getElementById('labelIdentificationAttach');
                                                                                                    const labelIdentificationHardwareId = document.getElementById('labelIdentificationHardwareId');

                                                                                                    function handleRadioChange() {
                                                                                                        if (pcbFail.checked) {
                                                                                                            pcbRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            pcbRejectCriteria.required = true;

                                                                                                            pcbRejectQty.disabled = false;
                                                                                                            pcbRejectQty.required = true;

                                                                                                            pcbRejectUpload.disabled = false;
                                                                                                            pcbRejectUpload.required = true;

                                                                                                            pcbHardwareId.disabled = false;
                                                                                                            pcbHardwareId.required = true;
                                                                                                        } else {
                                                                                                            pcbRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            pcbRejectCriteria.required = false;
                                                                                                            pcbRejectCriteria.value = '';

                                                                                                            pcbRejectQty.disabled = true;
                                                                                                            pcbRejectQty.required = false;
                                                                                                            pcbRejectQty.value = '';

                                                                                                            pcbRejectUpload.disabled = true;
                                                                                                            pcbRejectUpload.required = false;
                                                                                                            pcbRejectUpload.value = '';

                                                                                                            pcbHardwareId.disabled = true;
                                                                                                            pcbHardwareId.required = false;
                                                                                                            pcbHardwareId.value = '';
                                                                                                        }
                                                                                                        if (handleFail.checked) {
                                                                                                            handleRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            handleRejectCriteria.required = true;

                                                                                                            handleRejectQty.disabled = false;
                                                                                                            handleRejectQty.required = true;

                                                                                                            handleRejectUpload.disabled = false;
                                                                                                            handleRejectUpload.required = true;

                                                                                                            handleHardwareId.disabled = false;
                                                                                                            handleHardwareId.required = true;
                                                                                                        } else {
                                                                                                            handleRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            handleRejectCriteria.required = false;
                                                                                                            handleRejectCriteria.value = '';

                                                                                                            handleRejectQty.disabled = true;
                                                                                                            handleRejectQty.required = false;
                                                                                                            handleRejectQty.value = '';

                                                                                                            handleRejectUpload.disabled = true;
                                                                                                            handleRejectUpload.required = false;
                                                                                                            handleRejectUpload.value = '';

                                                                                                            handleHardwareId.disabled = true;
                                                                                                            handleHardwareId.required = false;
                                                                                                            handleHardwareId.value = '';
                                                                                                        }
                                                                                                        if (metalFrameFail.checked) {
                                                                                                            metalFrameRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            metalFrameRejectCriteria.required = true;

                                                                                                            metalFrameRejectQty.disabled = false;
                                                                                                            metalFrameRejectQty.required = true;

                                                                                                            metalFrameRejectUpload.disabled = false;
                                                                                                            metalFrameRejectUpload.required = true;

                                                                                                            metalFrameHardwareId.disabled = false;
                                                                                                            metalFrameHardwareId.required = true;
                                                                                                        } else {
                                                                                                            metalFrameRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            metalFrameRejectCriteria.required = false;
                                                                                                            metalFrameRejectCriteria.value = '';

                                                                                                            metalFrameRejectQty.disabled = true;
                                                                                                            metalFrameRejectQty.required = false;
                                                                                                            metalFrameRejectQty.value = '';

                                                                                                            metalFrameRejectUpload.disabled = true;
                                                                                                            metalFrameRejectUpload.required = false;
                                                                                                            metalFrameRejectUpload.value = '';

                                                                                                            metalFrameHardwareId.disabled = true;
                                                                                                            metalFrameHardwareId.required = false;
                                                                                                            metalFrameHardwareId.value = '';
                                                                                                        }
                                                                                                        if (hardwareFasternersFail.checked) {
                                                                                                            hardwareFasternersRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            hardwareFasternersRejectCriteria.required = true;

                                                                                                            hardwareFasternersRejectQty.disabled = false;
                                                                                                            hardwareFasternersRejectQty.required = true;

                                                                                                            hardwareFasternersRejectUpload.disabled = false;
                                                                                                            hardwareFasternersRejectUpload.required = true;

                                                                                                            hardwareFasternersHardwareId.disabled = false;
                                                                                                            hardwareFasternersHardwareId.required = true;
                                                                                                        } else {
                                                                                                            hardwareFasternersRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            hardwareFasternersRejectCriteria.required = false;
                                                                                                            hardwareFasternersRejectCriteria.value = '';

                                                                                                            hardwareFasternersRejectQty.disabled = true;
                                                                                                            hardwareFasternersRejectQty.required = false;
                                                                                                            hardwareFasternersRejectQty.value = '';

                                                                                                            hardwareFasternersRejectUpload.disabled = true;
                                                                                                            hardwareFasternersRejectUpload.required = false;
                                                                                                            hardwareFasternersRejectUpload.value = '';

                                                                                                            hardwareFasternersHardwareId.disabled = true;
                                                                                                            hardwareFasternersHardwareId.required = false;
                                                                                                            hardwareFasternersHardwareId.value = '';
                                                                                                        }
                                                                                                        if (clipHolderFail.checked) {
                                                                                                            clipHolderRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            clipHolderRejectCriteria.required = true;

                                                                                                            clipHolderRejectQty.disabled = false;
                                                                                                            clipHolderRejectQty.required = true;

                                                                                                            clipHolderRejectUpload.disabled = false;
                                                                                                            clipHolderRejectUpload.required = true;

                                                                                                            clipHolderHardwareId.disabled = false;
                                                                                                            clipHolderHardwareId.required = true;
                                                                                                        } else {
                                                                                                            clipHolderRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            clipHolderRejectCriteria.required = false;
                                                                                                            clipHolderRejectCriteria.value = '';

                                                                                                            clipHolderRejectQty.disabled = true;
                                                                                                            clipHolderRejectQty.required = false;
                                                                                                            clipHolderRejectQty.value = '';

                                                                                                            clipHolderRejectUpload.disabled = true;
                                                                                                            clipHolderRejectUpload.required = false;
                                                                                                            clipHolderRejectUpload.value = '';

                                                                                                            clipHolderHardwareId.disabled = true;
                                                                                                            clipHolderHardwareId.required = false;
                                                                                                            clipHolderHardwareId.value = '';
                                                                                                        }
                                                                                                        if (pcbEdgeFingerFail.checked) {
                                                                                                            pcbEdgeFingerRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            pcbEdgeFingerRejectCriteria.required = true;

                                                                                                            pcbEdgeFingerRejectQty.disabled = false;
                                                                                                            pcbEdgeFingerRejectQty.required = true;

                                                                                                            pcbEdgeFingerRejectUpload.disabled = false;
                                                                                                            pcbEdgeFingerRejectUpload.required = true;

                                                                                                            pcbEdgeFingerHardwareId.disabled = false;
                                                                                                            pcbEdgeFingerHardwareId.required = true;
                                                                                                        } else {
                                                                                                            pcbEdgeFingerRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            pcbEdgeFingerRejectCriteria.required = false;
                                                                                                            pcbEdgeFingerRejectCriteria.value = '';

                                                                                                            pcbEdgeFingerRejectQty.disabled = true;
                                                                                                            pcbEdgeFingerRejectQty.required = false;
                                                                                                            pcbEdgeFingerRejectQty.value = '';

                                                                                                            pcbEdgeFingerRejectUpload.disabled = true;
                                                                                                            pcbEdgeFingerRejectUpload.required = false;
                                                                                                            pcbEdgeFingerRejectUpload.value = '';

                                                                                                            pcbEdgeFingerHardwareId.disabled = true;
                                                                                                            pcbEdgeFingerHardwareId.required = false;
                                                                                                            pcbEdgeFingerHardwareId.value = '';
                                                                                                        }
                                                                                                        if (connectorFail.checked) {
                                                                                                            connectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            connectorRejectCriteria.required = true;

                                                                                                            connectorRejectQty.disabled = false;
                                                                                                            connectorRejectQty.required = true;

                                                                                                            connectorRejectUpload.disabled = false;
                                                                                                            connectorRejectUpload.required = true;

                                                                                                            connectorHardwareId.disabled = false;
                                                                                                            connectorHardwareId.required = true;
                                                                                                        } else {
                                                                                                            connectorRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            connectorRejectCriteria.required = false;
                                                                                                            connectorRejectCriteria.value = '';

                                                                                                            connectorRejectQty.disabled = true;
                                                                                                            connectorRejectQty.required = false;
                                                                                                            connectorRejectQty.value = '';

                                                                                                            connectorRejectUpload.disabled = true;
                                                                                                            connectorRejectUpload.required = false;
                                                                                                            connectorRejectUpload.value = '';

                                                                                                            connectorHardwareId.disabled = true;
                                                                                                            connectorHardwareId.required = false;
                                                                                                            connectorHardwareId.value = '';
                                                                                                        }
                                                                                                        if (dutSocketsFail.checked) {
                                                                                                            dutSocketsRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            dutSocketsRejectCriteria.required = true;

                                                                                                            dutSocketsRejectQty.disabled = false;
                                                                                                            dutSocketsRejectQty.required = true;

                                                                                                            dutSocketsRejectUpload.disabled = false;
                                                                                                            dutSocketsRejectUpload.required = true;

                                                                                                            dutSocketsHardwareId.disabled = false;
                                                                                                            dutSocketsHardwareId.required = true;
                                                                                                        } else {
                                                                                                            dutSocketsRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            dutSocketsRejectCriteria.required = false;
                                                                                                            dutSocketsRejectCriteria.value = '';

                                                                                                            dutSocketsRejectQty.disabled = true;
                                                                                                            dutSocketsRejectQty.required = false;
                                                                                                            dutSocketsRejectQty.value = '';

                                                                                                            dutSocketsRejectUpload.disabled = true;
                                                                                                            dutSocketsRejectUpload.required = false;
                                                                                                            dutSocketsRejectUpload.value = '';

                                                                                                            dutSocketsHardwareId.disabled = true;
                                                                                                            dutSocketsHardwareId.required = false;
                                                                                                            dutSocketsHardwareId.value = '';
                                                                                                        }
                                                                                                        if (edgeMbBananaFail.checked) {
                                                                                                            edgeMbBananaRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            edgeMbBananaRejectCriteria.required = true;

                                                                                                            edgeMbBananaRejectQty.disabled = false;
                                                                                                            edgeMbBananaRejectQty.required = true;

                                                                                                            edgeMbBananaRejectUpload.disabled = false;
                                                                                                            edgeMbBananaRejectUpload.required = true;

                                                                                                            edgeMbBananaHardwareId.disabled = false;
                                                                                                            edgeMbBananaHardwareId.required = true;
                                                                                                        } else {
                                                                                                            edgeMbBananaRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            edgeMbBananaRejectCriteria.required = false;
                                                                                                            edgeMbBananaRejectCriteria.value = '';

                                                                                                            edgeMbBananaRejectQty.disabled = true;
                                                                                                            edgeMbBananaRejectQty.required = false;
                                                                                                            edgeMbBananaRejectQty.value = '';

                                                                                                            edgeMbBananaRejectUpload.disabled = true;
                                                                                                            edgeMbBananaRejectUpload.required = false;
                                                                                                            edgeMbBananaRejectUpload.value = '';

                                                                                                            edgeMbBananaHardwareId.disabled = true;
                                                                                                            edgeMbBananaHardwareId.required = false;
                                                                                                            edgeMbBananaHardwareId.value = '';
                                                                                                        }
                                                                                                        if (electComponentFail.checked) {
                                                                                                            electComponentRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            electComponentRejectCriteria.required = true;

                                                                                                            electComponentRejectQty.disabled = false;
                                                                                                            electComponentRejectQty.required = true;

                                                                                                            electComponentRejectUpload.disabled = false;
                                                                                                            electComponentRejectUpload.required = true;

                                                                                                            electComponentHardwareId.disabled = false;
                                                                                                            electComponentHardwareId.required = true;
                                                                                                        } else {
                                                                                                            electComponentRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            electComponentRejectCriteria.required = false;
                                                                                                            electComponentRejectCriteria.value = '';

                                                                                                            electComponentRejectQty.disabled = true;
                                                                                                            electComponentRejectQty.required = false;
                                                                                                            electComponentRejectQty.value = '';

                                                                                                            electComponentRejectUpload.disabled = true;
                                                                                                            electComponentRejectUpload.required = false;
                                                                                                            electComponentRejectUpload.value = '';

                                                                                                            electComponentHardwareId.disabled = true;
                                                                                                            electComponentHardwareId.required = false;
                                                                                                            electComponentHardwareId.value = '';
                                                                                                        }
                                                                                                        if (solderJointFail.checked) {
                                                                                                            solderJointRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            solderJointRejectCriteria.required = true;

                                                                                                            solderJointRejectQty.disabled = false;
                                                                                                            solderJointRejectQty.required = true;

                                                                                                            solderJointRejectUpload.disabled = false;
                                                                                                            solderJointRejectUpload.required = true;

                                                                                                            solderJointHardwareId.disabled = false;
                                                                                                            solderJointHardwareId.required = true;
                                                                                                        } else {
                                                                                                            solderJointRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            solderJointRejectCriteria.required = false;
                                                                                                            solderJointRejectCriteria.value = '';

                                                                                                            solderJointRejectQty.disabled = true;
                                                                                                            solderJointRejectQty.required = false;
                                                                                                            solderJointRejectQty.value = '';

                                                                                                            solderJointRejectUpload.disabled = true;
                                                                                                            solderJointRejectUpload.required = false;
                                                                                                            solderJointRejectUpload.value = '';

                                                                                                            solderJointHardwareId.disabled = true;
                                                                                                            solderJointHardwareId.required = false;
                                                                                                            solderJointHardwareId.value = '';
                                                                                                        }
                                                                                                        if (winConnectorFail.checked) {
                                                                                                            winConnectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            winConnectorRejectCriteria.required = true;

                                                                                                            winConnectorRejectQty.disabled = false;
                                                                                                            winConnectorRejectQty.required = true;

                                                                                                            winConnectorRejectUpload.disabled = false;
                                                                                                            winConnectorRejectUpload.required = true;

                                                                                                            winConnectorHardwareId.disabled = false;
                                                                                                            winConnectorHardwareId.required = true;
                                                                                                        } else {
                                                                                                            winConnectorRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            winConnectorRejectCriteria.required = false;
                                                                                                            winConnectorRejectCriteria.value = '';

                                                                                                            winConnectorRejectQty.disabled = true;
                                                                                                            winConnectorRejectQty.required = false;
                                                                                                            winConnectorRejectQty.value = '';

                                                                                                            winConnectorRejectUpload.disabled = true;
                                                                                                            winConnectorRejectUpload.required = false;
                                                                                                            winConnectorRejectUpload.value = '';

                                                                                                            winConnectorHardwareId.disabled = true;
                                                                                                            winConnectorHardwareId.required = false;
                                                                                                            winConnectorHardwareId.value = '';
                                                                                                        }
                                                                                                        if (teflonConnectorFail.checked) {
                                                                                                            teflonConnectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            teflonConnectorRejectCriteria.required = true;

                                                                                                            teflonConnectorRejectQty.disabled = false;
                                                                                                            teflonConnectorRejectQty.required = true;

                                                                                                            teflonConnectorRejectUpload.disabled = false;
                                                                                                            teflonConnectorRejectUpload.required = true;

                                                                                                            teflonConnectorHardwareId.disabled = false;
                                                                                                            teflonConnectorHardwareId.required = true;
                                                                                                        } else {
                                                                                                            teflonConnectorRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            teflonConnectorRejectCriteria.required = false;
                                                                                                            teflonConnectorRejectCriteria.value = '';

                                                                                                            teflonConnectorRejectQty.disabled = true;
                                                                                                            teflonConnectorRejectQty.required = false;
                                                                                                            teflonConnectorRejectQty.value = '';

                                                                                                            teflonConnectorRejectUpload.disabled = true;
                                                                                                            teflonConnectorRejectUpload.required = false;
                                                                                                            teflonConnectorRejectUpload.value = '';

                                                                                                            teflonConnectorHardwareId.disabled = true;
                                                                                                            teflonConnectorHardwareId.required = false;
                                                                                                            teflonConnectorHardwareId.value = '';
                                                                                                        }
                                                                                                        if (pogoReceptaclesPinFail.checked) {
                                                                                                            pogoReceptaclesPinRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            pogoReceptaclesPinRejectCriteria.required = true;

                                                                                                            pogoReceptaclesPinRejectQty.disabled = false;
                                                                                                            pogoReceptaclesPinRejectQty.required = true;

                                                                                                            pogoReceptaclesPinRejectUpload.disabled = false;
                                                                                                            pogoReceptaclesPinRejectUpload.required = true;

                                                                                                            pogoReceptaclesPinHardwareId.disabled = false;
                                                                                                            pogoReceptaclesPinHardwareId.required = true;
                                                                                                        } else {
                                                                                                            pogoReceptaclesPinRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            pogoReceptaclesPinRejectCriteria.required = false;
                                                                                                            pogoReceptaclesPinRejectCriteria.value = '';

                                                                                                            pogoReceptaclesPinRejectQty.disabled = true;
                                                                                                            pogoReceptaclesPinRejectQty.required = false;
                                                                                                            pogoReceptaclesPinRejectQty.value = '';

                                                                                                            pogoReceptaclesPinRejectUpload.disabled = true;
                                                                                                            pogoReceptaclesPinRejectUpload.required = false;
                                                                                                            pogoReceptaclesPinRejectUpload.value = '';

                                                                                                            pogoReceptaclesPinHardwareId.disabled = true;
                                                                                                            pogoReceptaclesPinHardwareId.required = false;
                                                                                                            pogoReceptaclesPinHardwareId.value = '';
                                                                                                        }
                                                                                                        if (cableWiredCopperWireFail.checked) {
                                                                                                            cableWiredCopperWireRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            cableWiredCopperWireRejectCriteria.required = true;

                                                                                                            cableWiredCopperWireRejectQty.disabled = false;
                                                                                                            cableWiredCopperWireRejectQty.required = true;

                                                                                                            cableWiredCopperWireRejectUpload.disabled = false;
                                                                                                            cableWiredCopperWireRejectUpload.required = true;

                                                                                                            cableWiredCopperWireHardwareId.disabled = false;
                                                                                                            cableWiredCopperWireHardwareId.required = true;
                                                                                                        } else {
                                                                                                            cableWiredCopperWireRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            cableWiredCopperWireRejectCriteria.required = false;
                                                                                                            cableWiredCopperWireRejectCriteria.value = '';

                                                                                                            cableWiredCopperWireRejectQty.disabled = true;
                                                                                                            cableWiredCopperWireRejectQty.required = false;
                                                                                                            cableWiredCopperWireRejectQty.value = '';

                                                                                                            cableWiredCopperWireRejectUpload.disabled = true;
                                                                                                            cableWiredCopperWireRejectUpload.required = false;
                                                                                                            cableWiredCopperWireRejectUpload.value = '';

                                                                                                            cableWiredCopperWireHardwareId.disabled = true;
                                                                                                            cableWiredCopperWireHardwareId.required = false;
                                                                                                            cableWiredCopperWireHardwareId.value = '';
                                                                                                        }
                                                                                                        if (labelIdentificationFail.checked) {
                                                                                                            labelIdentificationRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                                                                                                            labelIdentificationRejectCriteria.required = true;

                                                                                                            labelIdentificationRejectQty.disabled = false;
                                                                                                            labelIdentificationRejectQty.required = true;

                                                                                                            labelIdentificationRejectUpload.disabled = false;
                                                                                                            labelIdentificationRejectUpload.required = true;

                                                                                                            labelIdentificationHardwareId.disabled = false;
                                                                                                            labelIdentificationHardwareId.required = true;
                                                                                                        } else {
                                                                                                            labelIdentificationRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                                                                                                            labelIdentificationRejectCriteria.required = false;
                                                                                                            labelIdentificationRejectCriteria.value = '';

                                                                                                            labelIdentificationRejectQty.disabled = true;
                                                                                                            labelIdentificationRejectQty.required = false;
                                                                                                            labelIdentificationRejectQty.value = '';

                                                                                                            labelIdentificationRejectUpload.disabled = true;
                                                                                                            labelIdentificationRejectUpload.required = false;
                                                                                                            labelIdentificationRejectUpload.value = '';

                                                                                                            labelIdentificationHardwareId.disabled = true;
                                                                                                            labelIdentificationHardwareId.required = false;
                                                                                                            labelIdentificationHardwareId.value = '';
                                                                                                        }
                                                                                                    }

                                                                                                    pcbPass.addEventListener('change', handleRadioChange);
                                                                                                    pcbNa.addEventListener('change', handleRadioChange);
                                                                                                    pcbFail.addEventListener('change', handleRadioChange);

                                                                                                    handlePass.addEventListener('change', handleRadioChange);
                                                                                                    handleNa.addEventListener('change', handleRadioChange);
                                                                                                    handleFail.addEventListener('change', handleRadioChange);

                                                                                                    metalFramePass.addEventListener('change', handleRadioChange);
                                                                                                    metalFrameNa.addEventListener('change', handleRadioChange);
                                                                                                    metalFrameFail.addEventListener('change', handleRadioChange);

                                                                                                    hardwareFasternersPass.addEventListener('change', handleRadioChange);
                                                                                                    hardwareFasternersNa.addEventListener('change', handleRadioChange);
                                                                                                    hardwareFasternersFail.addEventListener('change', handleRadioChange);

                                                                                                    clipHolderPass.addEventListener('change', handleRadioChange);
                                                                                                    clipHolderNa.addEventListener('change', handleRadioChange);
                                                                                                    clipHolderFail.addEventListener('change', handleRadioChange);

                                                                                                    pcbEdgeFingerPass.addEventListener('change', handleRadioChange);
                                                                                                    pcbEdgeFingerNa.addEventListener('change', handleRadioChange);
                                                                                                    pcbEdgeFingerFail.addEventListener('change', handleRadioChange);

                                                                                                    edgeMbBananaPass.addEventListener('change', handleRadioChange);
                                                                                                    edgeMbBananaNa.addEventListener('change', handleRadioChange);
                                                                                                    edgeMbBananaFail.addEventListener('change', handleRadioChange);

                                                                                                    dutSocketsPass.addEventListener('change', handleRadioChange);
                                                                                                    dutSocketsNa.addEventListener('change', handleRadioChange);
                                                                                                    dutSocketsFail.addEventListener('change', handleRadioChange);

                                                                                                    electComponentPass.addEventListener('change', handleRadioChange);
                                                                                                    electComponentNa.addEventListener('change', handleRadioChange);
                                                                                                    electComponentFail.addEventListener('change', handleRadioChange);

                                                                                                    connectorPass.addEventListener('change', handleRadioChange);
                                                                                                    connectorNa.addEventListener('change', handleRadioChange);
                                                                                                    connectorFail.addEventListener('change', handleRadioChange);

                                                                                                    solderJointPass.addEventListener('change', handleRadioChange);
                                                                                                    solderJointNa.addEventListener('change', handleRadioChange);
                                                                                                    solderJointFail.addEventListener('change', handleRadioChange);

                                                                                                    winConnectorPass.addEventListener('change', handleRadioChange);
                                                                                                    winConnectorNa.addEventListener('change', handleRadioChange);
                                                                                                    winConnectorFail.addEventListener('change', handleRadioChange);

                                                                                                    teflonConnectorPass.addEventListener('change', handleRadioChange);
                                                                                                    teflonConnectorNa.addEventListener('change', handleRadioChange);
                                                                                                    teflonConnectorFail.addEventListener('change', handleRadioChange);

                                                                                                    pogoReceptaclesPinPass.addEventListener('change', handleRadioChange);
                                                                                                    pogoReceptaclesPinNa.addEventListener('change', handleRadioChange);
                                                                                                    pogoReceptaclesPinFail.addEventListener('change', handleRadioChange);

                                                                                                    cableWiredCopperWirePass.addEventListener('change', handleRadioChange);
                                                                                                    cableWiredCopperWireNa.addEventListener('change', handleRadioChange);
                                                                                                    cableWiredCopperWireFail.addEventListener('change', handleRadioChange);

                                                                                                    labelIdentificationPass.addEventListener('change', handleRadioChange);
                                                                                                    labelIdentificationNa.addEventListener('change', handleRadioChange);
                                                                                                    labelIdentificationFail.addEventListener('change', handleRadioChange);

                                                                                                    if (pcbFail.checked) {
                                                                                                        pcbRejectCriteria.disabled = false;
                                                                                                        pcbRejectQty.disabled = false;
                                                                                                        pcbRejectUpload.disabled = false;
                                                                                                        pcbAttach.hidden = false;
                                                                                                    } else {
                                                                                                        pcbAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (handleFail.checked) {
                                                                                                        handleRejectCriteria.disabled = false;
                                                                                                        handleRejectQty.disabled = false;
                                                                                                        handleRejectUpload.disabled = false;
                                                                                                        handleAttach.hidden = false;
                                                                                                    } else {
                                                                                                        handleAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (metalFrameFail.checked) {
                                                                                                        metalFrameRejectCriteria.disabled = false;
                                                                                                        metalFrameRejectQty.disabled = false;
                                                                                                        metalFrameRejectUpload.disabled = false;
                                                                                                        metalFrameAttach.hidden = false;
                                                                                                    } else {
                                                                                                        metalFrameAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (hardwareFasternersFail.checked) {
                                                                                                        hardwareFasternersRejectCriteria.disabled = false;
                                                                                                        hardwareFasternersRejectQty.disabled = false;
                                                                                                        hardwareFasternersRejectUpload.disabled = false;
                                                                                                        hardwareFasternersAttach.hidden = false;
                                                                                                    } else {
                                                                                                        hardwareFasternersAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (clipHolderFail.checked) {
                                                                                                        clipHolderRejectCriteria.disabled = false;
                                                                                                        clipHolderRejectQty.disabled = false;
                                                                                                        clipHolderRejectUpload.disabled = false;
                                                                                                        clipHolderAttach.hidden = false;
                                                                                                    } else {
                                                                                                        clipHolderAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (pcbEdgeFingerFail.checked) {
                                                                                                        pcbEdgeFingerRejectCriteria.disabled = false;
                                                                                                        pcbEdgeFingerRejectQty.disabled = false;
                                                                                                        pcbEdgeFingerRejectUpload.disabled = false;
                                                                                                        pcbEdgeFingerAttach.hidden = false;
                                                                                                    } else {
                                                                                                        pcbEdgeFingerAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (connectorFail.checked) {
                                                                                                        connectorRejectCriteria.disabled = false;
                                                                                                        connectorRejectQty.disabled = false;
                                                                                                        connectorRejectUpload.disabled = false;
                                                                                                        connectorAttach.hidden = false;
                                                                                                    } else {
                                                                                                        connectorAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (dutSocketsFail.checked) {
                                                                                                        dutSocketsRejectCriteria.disabled = false;
                                                                                                        dutSocketsRejectQty.disabled = false;
                                                                                                        dutSocketsRejectUpload.disabled = false;
                                                                                                        dutSocketsAttach.hidden = false;
                                                                                                    } else {
                                                                                                        dutSocketsAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (edgeMbBananaFail.checked) {
                                                                                                        edgeMbBananaRejectCriteria.disabled = false;
                                                                                                        edgeMbBananaRejectQty.disabled = false;
                                                                                                        edgeMbBananaRejectUpload.disabled = false;
                                                                                                        edgeMbBananaAttach.hidden = false;
                                                                                                    } else {
                                                                                                        edgeMbBananaAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (electComponentFail.checked) {
                                                                                                        electComponentRejectCriteria.disabled = false;
                                                                                                        electComponentRejectQty.disabled = false;
                                                                                                        electComponentRejectUpload.disabled = false;
                                                                                                        electComponentAttach.hidden = false;
                                                                                                    } else {
                                                                                                        electComponentAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (solderJointFail.checked) {
                                                                                                        solderJointRejectCriteria.disabled = false;
                                                                                                        solderJointRejectQty.disabled = false;
                                                                                                        solderJointRejectUpload.disabled = false;
                                                                                                        solderJointAttach.hidden = false;
                                                                                                    } else {
                                                                                                        solderJointAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (winConnectorFail.checked) {
                                                                                                        winConnectorRejectCriteria.disabled = false;
                                                                                                        winConnectorRejectQty.disabled = false;
                                                                                                        winConnectorRejectUpload.disabled = false;
                                                                                                        winConnectorAttach.hidden = false;
                                                                                                    } else {
                                                                                                        winConnectorAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (teflonConnectorFail.checked) {
                                                                                                        teflonConnectorRejectCriteria.disabled = false;
                                                                                                        teflonConnectorRejectQty.disabled = false;
                                                                                                        teflonConnectorRejectUpload.disabled = false;
                                                                                                        teflonConnectorAttach.hidden = false;
                                                                                                    } else {
                                                                                                        teflonConnectorAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (pogoReceptaclesPinFail.checked) {
                                                                                                        pogoReceptaclesPinRejectCriteria.disabled = false;
                                                                                                        pogoReceptaclesPinRejectQty.disabled = false;
                                                                                                        pogoReceptaclesPinRejectUpload.disabled = false;
                                                                                                        pogoReceptaclesPinAttach.hidden = false;
                                                                                                    } else {
                                                                                                        pogoReceptaclesPinAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (cableWiredCopperWireFail.checked) {
                                                                                                        cableWiredCopperWireRejectCriteria.disabled = false;
                                                                                                        cableWiredCopperWireRejectQty.disabled = false;
                                                                                                        cableWiredCopperWireRejectUpload.disabled = false;
                                                                                                        cableWiredCopperWireAttach.hidden = false;
                                                                                                    } else {
                                                                                                        cableWiredCopperWireAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }
                                                                                                    if (labelIdentificationFail.checked) {
                                                                                                        labelIdentificationRejectCriteria.disabled = false;
                                                                                                        labelIdentificationRejectQty.disabled = false;
                                                                                                        labelIdentificationRejectUpload.disabled = false;
                                                                                                        labelIdentificationAttach.hidden = false;
                                                                                                    } else {
                                                                                                        labelIdentificationAttach.hidden = true;
                                                                                                        handleRadioChange();
                                                                                                    }

                                                                                                    function sendMailMb(e) {
                                                                                                        var groupId = $(e).attr("infoGroupId");
                                                                                                        var deleteUrl = "${contextPath}/rmsbookingDetailUnloading/sendEmail/MB/" + groupId;
                                                                                                        var deleteMsg = "Do you confirm to send email to Motherboard Technician about this error?";
                                                                                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                                                                                        $("#modal_button").attr("href", deleteUrl);
                                                                                                    }

                                                                                                    function sendMailLc(e) {
                                                                                                        var groupId = $(e).attr("infoGroupId");
                                                                                                        var deleteUrl = "${contextPath}/rmsbookingDetailUnloading/sendEmail/LC/" + groupId;
                                                                                                        var deleteMsg = "Do you confirm to send email to Motherboard Technician about this error?";
                                                                                                        $("#confirmation_modal .modal-body").html(deleteMsg);
                                                                                                        $("#modal_button").attr("href", deleteUrl);
                                                                                                    }
        </script>
    </s:layout-component>
</s:layout-render>