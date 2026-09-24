<%-- 
    Document   : registration
    Created on : Sep 15, 2026, 9:49:07 AM
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
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style></style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">

            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/maverick/list" class="btn btn-outline-warning me-2" role="button"><i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title d-flex justify-content-between align-items-center">
                                <div>
                                    Maverick - <span style="color:#D97D55">Detail</span>
                                </div>
                            </h5>
                        </div>
                        <div class="card-body">
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/xde apa pon" method="post">
                                <div class="row mb-3">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Hardware Type</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="hwType" name="hwType" placeholder="" value="${data.itemType}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-4 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Hardware ID</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="hwid" name="hwid" placeholder="" value="${data.itemId}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Module</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="module" name="module" placeholder="" value="${data.module}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Sub Module</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="submodule" name="submodule" placeholder="" value="${data.submodule}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="date" name="date" placeholder="" value="${data.createdDate}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Disposition</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="dispo1" name="dispo1" placeholder="" value="${data.disposition1}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Disposition By</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="dispoby1" name="dispoby1" placeholder="" value="${data.disposition1By}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Disposition Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="dispodate1" name="dispodate1" placeholder="" value="${data.disposition1Date}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Disposition</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="dispo2" name="dispo2" placeholder="" value="${data.disposition2}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Disposition By</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="dispoby2" name="dispoby2" placeholder="" value="${data.disposition2By}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Disposition Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="dispodate2" name="dispodate2" placeholder="" value="${data.disposition2Date}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-4 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Booking Remarks</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="3" id="dispoRemark1" name="dispoRemark1" readonly>${data.dispositionRemarks1}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">&nbsp;</div>
                                    <div class="col-xl-4 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Booking Remarks</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="3" id="dispoRemark2" name="dispoRemark2" readonly>${dispositionRemarks2}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">&nbsp;</div>
                                    <div class="col-xl-4 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" style="color: red;" id="rmsStatus" name="rmsStatus" placeholder="" value="${data.status}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card-body">
                        <div class="custom-tabs-container">
                            <div class="tab-content" id="customTabContent">
                                <div id="twoAAA" role="tabpanel">
                                    <div class="row gx-4">
                                        <nav class="navbar bg-body-tertiary">
                                            <div class="container-fluid justify-content-start">
                                                <a href="${empty data.disposition1Date or empty data.disposition2Date ? contextPath.concat('/maverick/repair/').concat(id) : '#'}" class="btn btn-outline-${empty data.disposition1Date or empty data.disposition2Date ? 'info' : 'secondary disabled'} me-2" role="button"><i class="bi bi-tools"></i>&nbsp;&nbsp;Repair</a>
                                                <a href="${contextPath}/maverick/scrap/${id}" class="btn btn-outline-info me-2" role="button"><i class='bi bi-recycle'></i>&nbsp;&nbsp;Scrap</a>
                                                <a href="${contextPath}/maverick/bypass/${id}" class="btn btn-outline-info me-2 ms-auto" role="button"><i class="bi bi-box-arrow-up-right"></i>&nbsp;&nbsp;Bypass</a>
                                            </div>
                                        </nav>
                                        <fieldset disabled> 
                                        <form class="row gx-3 needs-validation" role="form" action="${contextPath}/maverick/updateDisposition" method="post" enctype="multipart/form-data" novalidate>
                                            <c:if test="${itemVm.pcb == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="pcbRejectUpload" name="pcbRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pcb" id="pcbAttach" name="pcbAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.handle == 'Fail'}">
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
                                                                <div class="row gx-4">
                                                                    <div class="col-xl-3 col-sm-12 col-12">
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
                                                                    <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                        <div class="mb-2">
                                                                            <label for="handleRejectUpload" class="form-label">Upload</label>
                                                                            <div class="input input-group">
                                                                                <input class="form-control" type="file" id="handleRejectUpload" name="handleRejectUpload">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/handle" id="handleAttach" name="handleAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${itemVm.metalFrame == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="metalFrameRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="metalFrameRejectUpload" name="metalFrameRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/metalFrame" id="metalFrameAttach" name="metalFrameAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.hardwareFasterners == 'Fail'}">
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
                                                                <div class="row gx-4">
                                                                    <div class="col-xl-3 col-sm-12 col-12">
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
                                                                    <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                        <div class="mb-2">
                                                                            <label for="hardwareFasternersRejectUpload" class="form-label">Upload</label>
                                                                            <div class="input input-group">
                                                                                <input class="form-control required" type="file" id="hardwareFasternersRejectUpload" name="hardwareFasternersRejectUpload">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row gx-4">
                                                                <div class="col-xl-4 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/hardwareFasterners" id="hardwareFasternersAttach" name="hardwareFasternersAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${itemVm.clipHolder == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="clipHolderRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="clipHolderRejectUpload" name="clipHolderRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/clipHolder" id="clipHolderAttach" name="clipHolderAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.pcbEdgeFinger == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pcbEdgeFingerRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="pcbEdgeFingerRejectUpload" name="pcbEdgeFingerRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pcbEdgeFinger" id="pcbEdgeFingerAttach" name="pcbEdgeFingerAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.connector == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="connectorRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="connectorRejectUpload" name="connectorRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/connector" id="connectorAttach" name="connectorAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.dutSockets == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-4 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="dutSocketsRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="dutSocketsRejectUpload" name="dutSocketsRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/dutSockets" id="dutSocketsAttach" name="dutSocketsAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.edgeMbBanana == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="edgeMbBananaRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="edgeMbBananaRejectUpload" name="edgeMbBananaRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/edgeMbBanana" id="edgeMbBananaAttach" name="edgeMbBananaAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.electComponent == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="electComponentRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="electComponentRejectUpload" name="electComponentRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/electComponent" id="electComponentAttach" name="electComponentAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.solderJoint == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="solderJointRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="solderJointRejectUpload" name="solderJointRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/solderJoint" id="solderJointAttach" name="solderJointAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.winConnector == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="winConnectorRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="winConnectorRejectUpload" name="winConnectorRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/winConnector" id="winConnectorAttach" name="winConnectorAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.teflonConnector == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="teflonConnectorRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="teflonConnectorRejectUpload" name="teflonConnectorRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/teflonConnector" id="teflonConnectorAttach" name="teflonConnectorAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.pogoReceptaclesPin == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="pogoReceptaclesPinRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="pogoReceptaclesPinRejectUpload" name="pogoReceptaclesPinRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pogoReceptaclesPin" id="pogoReceptaclesPinAttach" name="pogoReceptaclesPinAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.cableWiredCopperWire == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="cableWiredCopperWireRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="cableWiredCopperWireRejectUpload" name="cableWiredCopperWireRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/cableWiredCopperWire" id="cableWiredCopperWireAttach" name="cableWiredCopperWireAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${itemVm.labelIdentification == 'Fail'}">
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
                                                            <div class="row gx-4">
                                                                <div class="col-xl-3 col-sm-12 col-12">
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
                                                                <div class="form-group col-xl-5 col-sm-12 col-12">
                                                                    <div class="mb-2">
                                                                        <label for="labelIdentificationRejectUpload" class="form-label">Upload</label>
                                                                        <div class="input input-group">
                                                                            <input class="form-control" type="file" id="labelIdentificationRejectUpload" name="labelIdentificationRejectUpload">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gx-4">
                                                            <div class="col-xl-4 col-sm-12 col-12">
                                                                <div class="mb-2">
                                                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/labelIdentification" id="labelIdentificationAttach" name="labelIdentificationAttach"> Download Attachment</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>
                                            <!-- Form actions start -->
<!--                                            <div class="col-md-12">
                                                <button type="submit" id="submitVm" name="submitVm" class="btn btn-primary float-end">Save</button>
                                                <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                            </div>-->
                                            <!-- Form actions end -->
                                        </form>
                                        </fieldset>
                                    </div>
                                    <!-- Row end -->
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
    <s:layout-component name="page_container">

    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
        </script>
    </s:layout-component>
</s:layout-render>