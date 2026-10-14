<%-- 
    Document   : maverick_vmft
    Created on : Oct 14, 2026, 10:02:45 AM
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
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/daterange/daterange.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/css/animate.css">
        <link rel="stylesheet" href="${contextPath}/resources/css/maverick_002.css"/>
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <!-- Row start -->
            <div class="row gx-4">

                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">New Hardware Registration</span></h5>
                        </div>
                        <div class="card-body">
                            <!--<div class="card mb-4">-->

                            <!--</div>-->
                            <div class="custom-tabs-container">
                                <ul class="fs-6 nav nav-tabs justify-content-center" id="customTab4" role="tablist">
                                    <li>
                                        <a class="nav-link" href="${contextPath}/maverick/maverickdetails/11/Hardware%20Registration/35187"><i class="bi bi-person-badge"></i>Maverick Details</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link" id="tab-twoAAA" data-bs-toggle="tab" href="#twoAAA" role="tab" aria-controls="twoAAA" aria-selected="false"><i class="bi bi-search"></i>Visual Inspection Check</a>
                                    </li>
                                    <li class="nav-item disabled" aria-disabled="true" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab" aria-controls="threeAAA" aria-selected="false"><i class="bi bi-clipboard-check"></i>Functional Test</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade ${hwActiveTab}" id="oneAAA" role="tabpanel">
                                        <%@ include file="registration.jsp" %>
                                    </div> 
                                    <div class="tab-pane active" id="twoAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <form class="row gx-3 needs-validation" role="form" action="${contextPath}/hw/item/vm/save" method="post" enctype="multipart/form-data" novalidate>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-2">
                                                        <div class="card-body">

                                                            <div class="col-xl-6 col-sm-8 col-12">
                                                                <div class="mb-2">
                                                                    <label class="form-label" for="pcb">PCB</label>
                                                                    <div class="m-0">
                                                                        <div class="form-check form-check-inline">
                                                                            <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                                                            <input type="hidden" class="form-control" id="viId" name="viId" placeholder="" value="${itemVm.id}">
                                                                            <input class="form-check-input" type="radio" name="pcb" id="pcb1" value="Pass" <c:if test="${itemVm.pcb == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="inlineRadio1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcb" id="pcb2" value="Fail" <c:if test="${itemVm.pcb == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="inlineRadio2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcb" id="pcb3" value="NA" <c:if test="${itemVm.pcb == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="inlineRadio3">NA</label>
                                                                        </div>
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
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pcb" id="pcbAttach" name="pcbAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="handle" id="handle1" value="Pass" <c:if test="${itemVm.handle == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="handle1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="handle" id="handle2" value="Fail" <c:if test="${itemVm.handle == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="handle2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="handle" id="handle3" value="NA" <c:if test="${itemVm.handle == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="handle3">NA</label>
                                                                        </div>
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/handle" id="handleAttach" name="handleAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="metalFrame" id="metalFrame1" value="Pass" <c:if test="${itemVm.metalFrame == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="metalFrame1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="metalFrame" id="metalFrame2" value="Fail" <c:if test="${itemVm.metalFrame == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="metalFrame2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="metalFrame" id="metalFrame3" value="NA" <c:if test="${itemVm.metalFrame == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="metalFrame">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="metalFrameReject" name="metalFrameReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/metalFrame" id="metalFrameAttach" name="metalFrameAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="hardwareFasterners" id="hardwareFasterners1" value="Pass" <c:if test="${itemVm.hardwareFasterners == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="hardwareFasterners1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="hardwareFasterners" id="hardwareFasterners2" value="Fail" <c:if test="${itemVm.hardwareFasterners == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="hardwareFasterners2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="hardwareFasterners" id="hardwareFasterners3" value="NA" <c:if test="${itemVm.hardwareFasterners == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="hardwareFasterners3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="hardwareFasternersReject" name="hardwareFasternersReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/hardwareFasterners" id="hardwareFasternersAttach" name="hardwareFasternersAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="clipHolder" id="clipHolder1" value="Pass" <c:if test="${itemVm.clipHolder == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="clipHolder1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="clipHolder" id="clipHolder2" value="Fail" <c:if test="${itemVm.clipHolder == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="clipHolder2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="clipHolder" id="clipHolder3" value="NA" <c:if test="${itemVm.clipHolder == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="clipHolder3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="clipHolderReject" name="clipHolderReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/clipHolder" id="clipHolderAttach" name="clipHolderAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="pcbEdgeFinger" id="pcbEdgeFinger1" value="Pass" <c:if test="${itemVm.pcbEdgeFinger == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="pcbEdgeFinger1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcbEdgeFinger" id="pcbEdgeFinger2" value="Fail" <c:if test="${itemVm.pcbEdgeFinger == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pcbEdgeFinger2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pcbEdgeFinger" id="pcbEdgeFinger3" value="NA" <c:if test="${itemVm.pcbEdgeFinger == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pcbEdgeFinger3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="pcbEdgeFingerReject" name="pcbEdgeFingerReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pcbEdgeFinger" id="pcbEdgeFingerAttach" name="pcbEdgeFingerAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="connector" id="connector1" value="Pass" <c:if test="${itemVm.connector == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="connector1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="connector" id="connector2" value="Fail" <c:if test="${itemVm.connector == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="connector2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="connector" id="connector3" value="NA" <c:if test="${itemVm.connector == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="connector3">NA</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-2 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="connectorRejectQty" class="form-label">Reject Qty</label>
                                                                        <div class="input input-group">
                                                                            <input type="number" class="form-control" id="connectorRejectQty" name="connectorRejectQty" placeholder="" value="${itemVm.connectorRejectQty}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameReject" class="form-label">Reject Criteria</label>
                                                                        <div class="input input-group">
                                                                            <select class="form-control" id="connectorReject" name="connectorReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/connector" id="connectorAttach" name="connectorAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="dutSockets" id="dutSockets1" value="Pass" <c:if test="${itemVm.dutSockets == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="dutSockets1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="dutSockets" id="dutSockets2" value="Fail" <c:if test="${itemVm.dutSockets == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="dutSockets2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="dutSockets" id="dutSockets3" value="NA" <c:if test="${itemVm.dutSockets == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="dutSockets3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="dutSocketsReject" name="dutSocketsReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/dutSockets" id="dutSocketsAttach" name="dutSocketsAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="edgeMbBanana" id="edgeMbBanana1" value="Pass" <c:if test="${itemVm.edgeMbBanana == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="edgeMbBanana1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="edgeMbBanana" id="edgeMbBanana2" value="Fail" <c:if test="${itemVm.edgeMbBanana == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="edgeMbBanana2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="edgeMbBanana" id="edgeMbBanana3" value="NA" <c:if test="${itemVm.edgeMbBanana == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="edgeMbBanana3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="edgeMbBananaReject" name="edgeMbBananaReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/edgeMbBanana" id="edgeMbBananaAttach" name="edgeMbBananaAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="electComponent" id="electComponent1" value="Pass" <c:if test="${itemVm.electComponent == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="electComponent1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="electComponent" id="electComponent2" value="Fail" <c:if test="${itemVm.electComponent == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="electComponent2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="electComponent" id="electComponent3" value="NA" <c:if test="${itemVm.electComponent == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="electComponent3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="electComponentReject" name="electComponentReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/electComponent" id="electComponentAttach" name="electComponentAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="solderJoint" id="solderJoint1" value="Pass" <c:if test="${itemVm.solderJoint == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="solderJoint1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="solderJoint" id="solderJoint2" value="Fail" <c:if test="${itemVm.solderJoint == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="solderJoint2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="solderJoint" id="solderJoint3" value="NA" <c:if test="${itemVm.solderJoint == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="solderJoint3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="solderJointReject" name="solderJointReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/solderJoint" id="solderJointAttach" name="solderJointAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="winConnector" id="winConnector1" value="Pass" <c:if test="${itemVm.winConnector == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="winConnector1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="winConnector" id="winConnector2" value="Fail" <c:if test="${itemVm.winConnector == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="winConnector2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="winConnector" id="winConnector3" value="NA" <c:if test="${itemVm.winConnector == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="winConnector3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="winConnectorReject" name="winConnectorReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/winConnector" id="winConnectorAttach" name="winConnectorAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="teflonConnector" id="teflonConnector1" value="Pass" <c:if test="${itemVm.teflonConnector == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="teflonConnector1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="teflonConnector" id="teflonConnector2" value="Fail" <c:if test="${itemVm.teflonConnector == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="teflonConnector2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="teflonConnector" id="teflonConnector3" value="NA" <c:if test="${itemVm.teflonConnector == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="teflonConnector3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="teflonConnectorReject" name="teflonConnectorReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/teflonConnector" id="teflonConnectorAttach" name="teflonConnectorAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="pogoReceptaclesPin" id="pogoReceptaclesPin1" value="Pass" <c:if test="${itemVm.pogoReceptaclesPin == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="pogoReceptaclesPin1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pogoReceptaclesPin" id="pogoReceptaclesPin2" value="Fail" <c:if test="${itemVm.pogoReceptaclesPin == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pogoReceptaclesPin2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="pogoReceptaclesPin" id="pogoReceptaclesPin3" value="NA" <c:if test="${itemVm.pogoReceptaclesPin == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="pogoReceptaclesPin3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="pogoReceptaclesPinReject" name="pogoReceptaclesPinReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pogoReceptaclesPin" id="pogoReceptaclesPinAttach" name="pogoReceptaclesPinAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="cableWiredCopperWire" id="cableWiredCopperWire1" value="Pass" <c:if test="${itemVm.cableWiredCopperWire == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="cableWiredCopperWire1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="cableWiredCopperWire" id="cableWiredCopperWire2" value="Fail" <c:if test="${itemVm.cableWiredCopperWire == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="cableWiredCopperWire2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="cableWiredCopperWire" id="cableWiredCopperWire3" value="NA" <c:if test="${itemVm.cableWiredCopperWire == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="cableWiredCopperWire3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="cableWiredCopperWireReject" name="cableWiredCopperWireReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/cableWiredCopperWire" id="cableWiredCopperWireAttach" name="cableWiredCopperWireAttach"> Download Attachment</a>
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
                                                                            <input class="form-check-input" type="radio" name="labelIdentification" id="labelIdentification1" value="Pass" <c:if test="${itemVm.labelIdentification == 'Pass'}">checked</c:if> required>
                                                                            <label class="form-check-label" for="labelIdentification1">Pass</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="labelIdentification" id="labelIdentification2" value="Fail" <c:if test="${itemVm.labelIdentification == 'Fail'}">checked</c:if> >
                                                                            <label class="form-check-label" for="labelIdentification2">Fail</label>
                                                                        </div>
                                                                        <div class="form-check form-check-inline">
                                                                            <input class="form-check-input" type="radio" name="labelIdentification" id="labelIdentification3" value="NA" <c:if test="${itemVm.labelIdentification == 'NA'}">checked</c:if> >
                                                                            <label class="form-check-label" for="labelIdentification3">NA</label>
                                                                        </div>
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
                                                                            <select class="form-control" id="labelIdentificationReject" name="labelIdentificationReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/labelIdentification" id="labelIdentificationAttach" name="labelIdentificationAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-12">
                                                    <button type="submit" id="submitVm" name="submitVm" class="btn btn-primary float-end">Save</button>
                                                    <!--<a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>-->
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade ${teActiveTab}" id="threeAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <div class="col-12">
                                                &nbsp;
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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

            const handlePass = document.getElementById('handle1');
            const handleNa = document.getElementById('handle3');
            const handleFail = document.getElementById('handle2');
            const handleRejectCriteria = document.getElementById('handleReject');
            const handleRejectQty = document.getElementById('handleRejectQty');
            const handleRejectUpload = document.getElementById('handleRejectUpload');
            const handleAttach = document.getElementById('handleAttach');

            const metalFramePass = document.getElementById('metalFrame1');
            const metalFrameNa = document.getElementById('metalFrame3');
            const metalFrameFail = document.getElementById('metalFrame2');
            const metalFrameRejectCriteria = document.getElementById('metalFrameReject');
            const metalFrameRejectQty = document.getElementById('metalFrameRejectQty');
            const metalFrameRejectUpload = document.getElementById('metalFrameRejectUpload');
            const metalFrameAttach = document.getElementById('metalFrameAttach');

            const hardwareFasternersPass = document.getElementById('hardwareFasterners1');
            const hardwareFasternersNa = document.getElementById('hardwareFasterners3');
            const hardwareFasternersFail = document.getElementById('hardwareFasterners2');
            const hardwareFasternersRejectCriteria = document.getElementById('hardwareFasternersReject');
            const hardwareFasternersRejectQty = document.getElementById('hardwareFasternersRejectQty');
            const hardwareFasternersRejectUpload = document.getElementById('hardwareFasternersRejectUpload');
            const hardwareFasternersAttach = document.getElementById('hardwareFasternersAttach');

            const clipHolderPass = document.getElementById('clipHolder1');
            const clipHolderNa = document.getElementById('clipHolder3');
            const clipHolderFail = document.getElementById('clipHolder2');
            const clipHolderRejectCriteria = document.getElementById('clipHolderReject');
            const clipHolderRejectQty = document.getElementById('clipHolderRejectQty');
            const clipHolderRejectUpload = document.getElementById('clipHolderRejectUpload');
            const clipHolderAttach = document.getElementById('clipHolderAttach');

            const pcbEdgeFingerPass = document.getElementById('pcbEdgeFinger1');
            const pcbEdgeFingerNa = document.getElementById('pcbEdgeFinger3');
            const pcbEdgeFingerFail = document.getElementById('pcbEdgeFinger2');
            const pcbEdgeFingerRejectCriteria = document.getElementById('pcbEdgeFingerReject');
            const pcbEdgeFingerRejectQty = document.getElementById('pcbEdgeFingerRejectQty');
            const pcbEdgeFingerRejectUpload = document.getElementById('pcbEdgeFingerRejectUpload');
            const pcbEdgeFingerAttach = document.getElementById('pcbEdgeFingerAttach');

            const connectorPass = document.getElementById('connector1');
            const connectorNa = document.getElementById('connector3');
            const connectorFail = document.getElementById('connector2');
            const connectorRejectCriteria = document.getElementById('connectorReject');
            const connectorRejectQty = document.getElementById('connectorRejectQty');
            const connectorRejectUpload = document.getElementById('connectorRejectUpload');
            const connectorAttach = document.getElementById('connectorAttach');

            const dutSocketsPass = document.getElementById('dutSockets1');
            const dutSocketsNa = document.getElementById('dutSockets3');
            const dutSocketsFail = document.getElementById('dutSockets2');
            const dutSocketsRejectCriteria = document.getElementById('dutSocketsReject');
            const dutSocketsRejectQty = document.getElementById('dutSocketsRejectQty');
            const dutSocketsRejectUpload = document.getElementById('dutSocketsRejectUpload');
            const dutSocketsAttach = document.getElementById('dutSocketsAttach');

            const edgeMbBananaPass = document.getElementById('edgeMbBanana1');
            const edgeMbBananaNa = document.getElementById('edgeMbBanana3');
            const edgeMbBananaFail = document.getElementById('edgeMbBanana2');
            const edgeMbBananaRejectCriteria = document.getElementById('edgeMbBananaReject');
            const edgeMbBananaRejectQty = document.getElementById('edgeMbBananaRejectQty');
            const edgeMbBananaRejectUpload = document.getElementById('edgeMbBananaRejectUpload');
            const edgeMbBananaAttach = document.getElementById('edgeMbBananaAttach');

            const electComponentPass = document.getElementById('electComponent1');
            const electComponentNa = document.getElementById('electComponent3');
            const electComponentFail = document.getElementById('electComponent2');
            const electComponentRejectCriteria = document.getElementById('electComponentReject');
            const electComponentRejectQty = document.getElementById('electComponentRejectQty');
            const electComponentRejectUpload = document.getElementById('electComponentRejectUpload');
            const electComponentAttach = document.getElementById('electComponentAttach');

            const solderJointPass = document.getElementById('solderJoint1');
            const solderJointNa = document.getElementById('solderJoint3');
            const solderJointFail = document.getElementById('solderJoint2');
            const solderJointRejectCriteria = document.getElementById('solderJointReject');
            const solderJointRejectQty = document.getElementById('solderJointRejectQty');
            const solderJointRejectUpload = document.getElementById('solderJointRejectUpload');
            const solderJointAttach = document.getElementById('solderJointAttach');

            const winConnectorPass = document.getElementById('winConnector1');
            const winConnectorNa = document.getElementById('winConnector3');
            const winConnectorFail = document.getElementById('winConnector2');
            const winConnectorRejectCriteria = document.getElementById('winConnectorReject');
            const winConnectorRejectQty = document.getElementById('winConnectorRejectQty');
            const winConnectorRejectUpload = document.getElementById('winConnectorRejectUpload');
            const winConnectorAttach = document.getElementById('winConnectorAttach');

            const teflonConnectorPass = document.getElementById('teflonConnector1');
            const teflonConnectorNa = document.getElementById('teflonConnector3');
            const teflonConnectorFail = document.getElementById('teflonConnector2');
            const teflonConnectorRejectCriteria = document.getElementById('teflonConnectorReject');
            const teflonConnectorRejectQty = document.getElementById('teflonConnectorRejectQty');
            const teflonConnectorRejectUpload = document.getElementById('teflonConnectorRejectUpload');
            const teflonConnectorAttach = document.getElementById('teflonConnectorAttach');

            const pogoReceptaclesPinPass = document.getElementById('pogoReceptaclesPin1');
            const pogoReceptaclesPinNa = document.getElementById('pogoReceptaclesPin3');
            const pogoReceptaclesPinFail = document.getElementById('pogoReceptaclesPin2');
            const pogoReceptaclesPinRejectCriteria = document.getElementById('pogoReceptaclesPinReject');
            const pogoReceptaclesPinRejectQty = document.getElementById('pogoReceptaclesPinRejectQty');
            const pogoReceptaclesPinRejectUpload = document.getElementById('pogoReceptaclesPinRejectUpload');
            const pogoReceptaclesPinAttach = document.getElementById('pogoReceptaclesPinAttach');

            const cableWiredCopperWirePass = document.getElementById('cableWiredCopperWire1');
            const cableWiredCopperWireNa = document.getElementById('cableWiredCopperWire3');
            const cableWiredCopperWireFail = document.getElementById('cableWiredCopperWire2');
            const cableWiredCopperWireRejectCriteria = document.getElementById('cableWiredCopperWireReject');
            const cableWiredCopperWireRejectQty = document.getElementById('cableWiredCopperWireRejectQty');
            const cableWiredCopperWireRejectUpload = document.getElementById('cableWiredCopperWireRejectUpload');
            const cableWiredCopperWireAttach = document.getElementById('cableWiredCopperWireAttach');

            const labelIdentificationPass = document.getElementById('labelIdentification1');
            const labelIdentificationNa = document.getElementById('labelIdentification3');
            const labelIdentificationFail = document.getElementById('labelIdentification2');
            const labelIdentificationRejectCriteria = document.getElementById('labelIdentificationReject');
            const labelIdentificationRejectQty = document.getElementById('labelIdentificationRejectQty');
            const labelIdentificationRejectUpload = document.getElementById('labelIdentificationRejectUpload');
            const labelIdentificationAttach = document.getElementById('labelIdentificationAttach');

            //        const bibResult = document.getElementById('bibResult');
            //
            //        bibResult.required = true;

            // Function to handle the radio button change
            function handleRadioChange() {
                if (pcbFail.checked) {
                    pcbRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    pcbRejectCriteria.required = true;
                    //                pcbAttach.required = true;

                    pcbRejectQty.disabled = false;
                    pcbRejectQty.required = true;
                    pcbRejectUpload.disabled = false;
                    pcbRejectUpload.required = true;
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
                }
                if (handleFail.checked) {
                    handleRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    handleRejectCriteria.required = true;

                    handleRejectQty.disabled = false;
                    handleRejectQty.required = true;
                    handleRejectUpload.disabled = false;
                    handleRejectUpload.required = true;
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
                }
                if (metalFrameFail.checked) {
                    metalFrameRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    metalFrameRejectCriteria.required = true;

                    metalFrameRejectQty.disabled = false;
                    metalFrameRejectQty.required = true;
                    metalFrameRejectUpload.disabled = false;
                    metalFrameRejectUpload.required = true;
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
                }
                if (hardwareFasternersFail.checked) {
                    hardwareFasternersRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    hardwareFasternersRejectCriteria.required = true;

                    hardwareFasternersRejectQty.disabled = false;
                    hardwareFasternersRejectQty.required = true;
                    hardwareFasternersRejectUpload.disabled = false;
                    hardwareFasternersRejectUpload.required = true;
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
                }
                if (clipHolderFail.checked) {
                    clipHolderRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    clipHolderRejectCriteria.required = true;

                    clipHolderRejectQty.disabled = false;
                    clipHolderRejectQty.required = true;
                    clipHolderRejectUpload.disabled = false;
                    clipHolderRejectUpload.required = true;
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
                }
                if (pcbEdgeFingerFail.checked) {
                    pcbEdgeFingerRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    pcbEdgeFingerRejectCriteria.required = true;

                    pcbEdgeFingerRejectQty.disabled = false;
                    pcbEdgeFingerRejectQty.required = true;
                    pcbEdgeFingerRejectUpload.disabled = false;
                    pcbEdgeFingerRejectUpload.required = true;
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
                }
                if (connectorFail.checked) {
                    connectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    connectorRejectCriteria.required = true;

                    connectorRejectQty.disabled = false;
                    connectorRejectQty.required = true;
                    connectorRejectUpload.disabled = false;
                    connectorRejectUpload.required = true;
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
                }
                if (dutSocketsFail.checked) {
                    dutSocketsRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    dutSocketsRejectCriteria.required = true;

                    dutSocketsRejectQty.disabled = false;
                    dutSocketsRejectQty.required = true;
                    dutSocketsRejectUpload.disabled = false;
                    dutSocketsRejectUpload.required = true;
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
                }
                if (edgeMbBananaFail.checked) {
                    edgeMbBananaRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    edgeMbBananaRejectCriteria.required = true;

                    edgeMbBananaRejectQty.disabled = false;
                    edgeMbBananaRejectQty.required = true;
                    edgeMbBananaRejectUpload.disabled = false;
                    edgeMbBananaRejectUpload.required = true;
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
                }
                if (electComponentFail.checked) {
                    electComponentRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    electComponentRejectCriteria.required = true;

                    electComponentRejectQty.disabled = false;
                    electComponentRejectQty.required = true;
                    electComponentRejectUpload.disabled = false;
                    electComponentRejectUpload.required = true;
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
                }
                if (solderJointFail.checked) {
                    solderJointRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    solderJointRejectCriteria.required = true;

                    solderJointRejectQty.disabled = false;
                    solderJointRejectQty.required = true;
                    solderJointRejectUpload.disabled = false;
                    solderJointRejectUpload.required = true;
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
                }
                if (winConnectorFail.checked) {
                    winConnectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    winConnectorRejectCriteria.required = true;

                    winConnectorRejectQty.disabled = false;
                    winConnectorRejectQty.required = true;
                    winConnectorRejectUpload.disabled = false;
                    winConnectorRejectUpload.required = true;
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
                }
                if (teflonConnectorFail.checked) {
                    teflonConnectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    teflonConnectorRejectCriteria.required = true;

                    teflonConnectorRejectQty.disabled = false;
                    teflonConnectorRejectQty.required = true;
                    teflonConnectorRejectUpload.disabled = false;
                    teflonConnectorRejectUpload.required = true;
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
                }
                if (pogoReceptaclesPinFail.checked) {
                    pogoReceptaclesPinRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    pogoReceptaclesPinRejectCriteria.required = true;

                    pogoReceptaclesPinRejectQty.disabled = false;
                    pogoReceptaclesPinRejectQty.required = true;
                    pogoReceptaclesPinRejectUpload.disabled = false;
                    pogoReceptaclesPinRejectUpload.required = true;
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
                }
                if (cableWiredCopperWireFail.checked) {
                    cableWiredCopperWireRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    cableWiredCopperWireRejectCriteria.required = true;

                    cableWiredCopperWireRejectQty.disabled = false;
                    cableWiredCopperWireRejectQty.required = true;
                    cableWiredCopperWireRejectUpload.disabled = false;
                    cableWiredCopperWireRejectUpload.required = true;
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
                }
                if (labelIdentificationFail.checked) {
                    labelIdentificationRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                    labelIdentificationRejectCriteria.required = true;

                    labelIdentificationRejectQty.disabled = false;
                    labelIdentificationRejectQty.required = true;
                    labelIdentificationRejectUpload.disabled = false;
                    labelIdentificationRejectUpload.required = true;
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
                }
            }

            // Attach event listeners to the radio buttons
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

            // Initial state: Disable button if no radio is checked on page load
            // Or set based on a default checked radio
            if (pcbFail.checked) {
                pcbRejectCriteria.disabled = false;
                pcbRejectQty.disabled = false;
                pcbRejectUpload.disabled = false;
                pcbAttach.hidden = false;
            } else {
                pcbAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (handleFail.checked) {
                handleRejectCriteria.disabled = false;
                handleRejectQty.disabled = false;
                handleRejectUpload.disabled = false;
                handleAttach.hidden = false;
            } else {
                handleAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (metalFrameFail.checked) {
                metalFrameRejectCriteria.disabled = false;
                metalFrameRejectQty.disabled = false;
                metalFrameRejectUpload.disabled = false;
                metalFrameAttach.hidden = false;
            } else {
                metalFrameAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (hardwareFasternersFail.checked) {
                hardwareFasternersRejectCriteria.disabled = false;
                hardwareFasternersRejectQty.disabled = false;
                hardwareFasternersRejectUpload.disabled = false;
                hardwareFasternersAttach.hidden = false;
            } else {
                hardwareFasternersAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (clipHolderFail.checked) {
                clipHolderRejectCriteria.disabled = false;
                clipHolderRejectQty.disabled = false;
                clipHolderRejectUpload.disabled = false;
                clipHolderAttach.hidden = false;
            } else {
                clipHolderAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (pcbEdgeFingerFail.checked) {
                pcbEdgeFingerRejectCriteria.disabled = false;
                pcbEdgeFingerRejectQty.disabled = false;
                pcbEdgeFingerRejectUpload.disabled = false;
                pcbEdgeFingerAttach.hidden = false;
            } else {
                pcbEdgeFingerAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (connectorFail.checked) {
                connectorRejectCriteria.disabled = false;
                connectorRejectQty.disabled = false;
                connectorRejectUpload.disabled = false;
                connectorAttach.hidden = false;
            } else {
                connectorAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (dutSocketsFail.checked) {
                dutSocketsRejectCriteria.disabled = false;
                dutSocketsRejectQty.disabled = false;
                dutSocketsRejectUpload.disabled = false;
                dutSocketsAttach.hidden = false;
            } else {
                dutSocketsAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (edgeMbBananaFail.checked) {
                edgeMbBananaRejectCriteria.disabled = false;
                edgeMbBananaRejectQty.disabled = false;
                edgeMbBananaRejectUpload.disabled = false;
                edgeMbBananaAttach.hidden = false;
            } else {
                edgeMbBananaAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (electComponentFail.checked) {
                electComponentRejectCriteria.disabled = false;
                electComponentRejectQty.disabled = false;
                electComponentRejectUpload.disabled = false;
                electComponentAttach.hidden = false;
            } else {
                electComponentAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (solderJointFail.checked) {
                solderJointRejectCriteria.disabled = false;
                solderJointRejectQty.disabled = false;
                solderJointRejectUpload.disabled = false;
                solderJointAttach.hidden = false;
            } else {
                solderJointAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (winConnectorFail.checked) {
                winConnectorRejectCriteria.disabled = false;
                winConnectorRejectQty.disabled = false;
                winConnectorRejectUpload.disabled = false;
                winConnectorAttach.hidden = false;
            } else {
                winConnectorAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (teflonConnectorFail.checked) {
                teflonConnectorRejectCriteria.disabled = false;
                teflonConnectorRejectQty.disabled = false;
                teflonConnectorRejectUpload.disabled = false;
                teflonConnectorAttach.hidden = false;
            } else {
                teflonConnectorAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (pogoReceptaclesPinFail.checked) {
                pogoReceptaclesPinRejectCriteria.disabled = false;
                pogoReceptaclesPinRejectQty.disabled = false;
                pogoReceptaclesPinRejectUpload.disabled = false;
                pogoReceptaclesPinAttach.hidden = false;
            } else {
                pogoReceptaclesPinAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (cableWiredCopperWireFail.checked) {
                cableWiredCopperWireRejectCriteria.disabled = false;
                cableWiredCopperWireRejectQty.disabled = false;
                cableWiredCopperWireRejectUpload.disabled = false;
                cableWiredCopperWireAttach.hidden = false;
            } else {
                cableWiredCopperWireAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }
            if (labelIdentificationFail.checked) {
                labelIdentificationRejectCriteria.disabled = false;
                labelIdentificationRejectQty.disabled = false;
                labelIdentificationRejectUpload.disabled = false;
                labelIdentificationAttach.hidden = false;
            } else {
                labelIdentificationAttach.hidden = true;
                handleRadioChange(); // Set initial state based on default checked radio
            }

            $(".datepicker-week-numbers").daterangepicker({
                singleDatePicker: true,
                showWeekNumbers: true,
                startDate: moment().startOf("hour"),
                endDate: moment().startOf("hour").add(32, "hour"),
                locale: {
                    //		format: "DD/MM/YYYY",
                    format: "YYYY/MM/DD",
                },
            });

            $(document).ready(function () {
                var element = $('#groupId');
                if (element.val() !== '1' && element.val() !== '2' && element.val() !== '12' && element.val() !== '13') {
                    //                $("#submit").removeAttr('disabled');
                    $("#submitHw").attr("disabled", true);
                } else {
                    $("#submitHw").removeAttr('disabled');
                }

                var element2 = $('#viId');
                if (element2.val()) {
                    $("#submitVm").attr("disabled", true);
                }
            });
        </script>
    </s:layout-component>
</s:layout-render>