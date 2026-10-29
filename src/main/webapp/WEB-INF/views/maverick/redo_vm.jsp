<%-- 
    Document   : redo_vm
    Created on : Oct 15, 2026, 10:12:18 AM
    Author     : zbqb9x
--%>

<form class="row gx-3 needs-validation" role="form" action="${contextPath}/maverick/vm/save" method="post" enctype="multipart/form-data" novalidate>
    <div class="row">

        <!-- PCB -->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-12">
                        <label class="form-label" for="pcb">PCB</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input type="hidden" class="form-control" name="mibItemId" id="mibItemId" value="${item.id}">
                                <input type="hidden" class="form-control" name="viId" id="viId" value="${itemVm.id}">
                                <input class="form-check-input" type="radio" name="pcb" value="Pass" <c:if test="${itemVm.pcb == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pcb" value="Fail" <c:if test="${itemVm.pcb == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pcb" value="NA" <c:if test="${itemVm.pcb == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="pcbRejectQty" value="${itemVm.pcbRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="pcbReject" name="pcbReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${pcbReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="pcbRejectUpload" name="pcbRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pcb" id="pcbAttach" name="pcbAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Handle -->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="handle">Handle</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="handle" value="Pass" <c:if test="${itemVm.handle == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="handle" value="Fail" <c:if test="${itemVm.handle == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="handle" value="NA" <c:if test="${itemVm.handle == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="handleRejectQty" value="${itemVm.handleRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="handleReject" name="handleReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${handleReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="handleRejectUpload" name="handleRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/handle" id="handleAttach" name="handleAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Metal Frame -->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="metalFrame">Metal Frame</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="metalFrame" value="Pass" <c:if test="${itemVm.metalFrame == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="metalFrame" value="Fail" <c:if test="${itemVm.metalFrame == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="metalFrame" value="NA" <c:if test="${itemVm.metalFrame == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="metalFrameRejectQty" value="${itemVm.metalFrameRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="metalFrameReject" name="metalFrameReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${metalFrameReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="metalFrameRejectUpload" name="metalFrameRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/metalFrame" id="metalFrameAttach" name="metalFrameAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Hardware Fasteners -->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="hardwareFasterners">Hardware Fasteners</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="hardwareFasterners" value="Pass" <c:if test="${itemVm.hardwareFasterners == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="hardwareFasterners" value="Fail" <c:if test="${itemVm.hardwareFasterners == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="hardwareFasterners" value="NA" <c:if test="${itemVm.hardwareFasterners == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="hardwareFasternersRejectQty" value="${itemVm.hardwareFasternersRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="hardwareFasternersReject" name="hardwareFasternersReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${hardwareFasternersReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="hardwareFasternersRejectUpload" name="hardwareFasternersRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/hardwareFasterners" id="hardwareFasternersAttach" name="hardwareFasternersAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--Clip Holder-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="clipHolder">Clip Holder</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="clipHolder" value="Pass" <c:if test="${itemVm.clipHolder == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="clipHolder" value="Fail" <c:if test="${itemVm.clipHolder == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="clipHolder" value="NA" <c:if test="${itemVm.clipHolder == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="clipHolderRejectQty" value="${itemVm.clipHolderRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="clipHolderReject" name="clipHolderReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${clipHolderReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="clipHolderRejectUpload" name="clipHolderRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/clipHolder" id="clipHolderAttach" name="clipHolderAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--PCB Edge Finger-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="pcbEdgeFinger">PCB Edge Finger</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pcbEdgeFinger" value="Pass" <c:if test="${itemVm.pcbEdgeFinger == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pcbEdgeFinger" value="Fail" <c:if test="${itemVm.pcbEdgeFinger == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pcbEdgeFinger" value="NA" <c:if test="${itemVm.pcbEdgeFinger == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="pcbEdgeFingerRejectQty" value="${itemVm.pcbEdgeFingerRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="pcbEdgeFingerReject" name="pcbEdgeFingerReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${pcbEdgeFingerReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="pcbEdgeFingerRejectUpload" name="pcbEdgeFingerRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pcbEdgeFinger" id="pcbEdgeFingerAttach" name="pcbEdgeFingerAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--Connector-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="connector">Connector</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="connector" value="Pass" <c:if test="${itemVm.connector == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="connector" value="Fail" <c:if test="${itemVm.connector == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="connector" value="NA" <c:if test="${itemVm.connector == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="connectorRejectQty" value="${itemVm.connectorRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="connectorReject" name="connectorReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${connectorReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="connectorRejectUpload" name="connectorRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/connector" id="connectorAttach" name="connectorAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--DUT Sockets-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="dutSockets">DUT Sockets</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="dutSockets" value="Pass" <c:if test="${itemVm.dutSockets == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="dutSockets" value="Fail" <c:if test="${itemVm.dutSockets == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="dutSockets" value="NA" <c:if test="${itemVm.dutSockets == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="dutSocketsRejectQty" value="${itemVm.dutSocketsRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="dutSocketsReject" name="dutSocketsReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${dutSocketsReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="dutSocketsRejectUpload" name="dutSocketsRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/dutSockets" id="dutSocketsAttach" name="dutSocketsAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--Edge MB Banana-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="edgeMbBanana">Edge MB Banana</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="edgeMbBanana" value="Pass" <c:if test="${itemVm.edgeMbBanana == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="edgeMbBanana" value="Fail" <c:if test="${itemVm.edgeMbBanana == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="edgeMbBanana" value="NA" <c:if test="${itemVm.edgeMbBanana == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="edgeMbBananaRejectQty" value="${itemVm.edgeMbBananaRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="edgeMbBananaReject" name="edgeMbBananaReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${edgeMbBananaReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="edgeMbBananaRejectUpload" name="edgeMbBananaRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/edgeMbBanana" id="edgeMbBananaAttach" name="edgeMbBananaAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- Electronic Component-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="electComponent">Electronic Component</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="electComponent" value="Pass" <c:if test="${itemVm.electComponent == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="electComponent" value="Fail" <c:if test="${itemVm.electComponent == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="electComponent" value="NA" <c:if test="${itemVm.electComponent == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="electComponentRejectQty" value="${itemVm.electComponentRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="electComponentReject" name="electComponentReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${electComponentReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="electComponentRejectUpload" name="electComponentRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/electComponent" id="electComponentAttach" name="electComponentAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- Solder Joint-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="solderJoint">Solder Joint</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="solderJoint" value="Pass" <c:if test="${itemVm.solderJoint == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="solderJoint" value="Fail" <c:if test="${itemVm.solderJoint == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="solderJoint" value="NA" <c:if test="${itemVm.solderJoint == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="solderJointRejectQty" value="${itemVm.solderJointRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="solderJointReject" name="solderJointReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${solderJointReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="solderJointRejectUpload" name="solderJointRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/solderJoint" id="solderJointAttach" name="solderJointAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- Win Connector-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="winConnector">Win Connector</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="winConnector" value="Pass" <c:if test="${itemVm.winConnector == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="winConnector" value="Fail" <c:if test="${itemVm.winConnector == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="winConnector" value="NA" <c:if test="${itemVm.winConnector == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="winConnectorRejectQty" value="${itemVm.winConnectorRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="winConnectorReject" name="winConnectorReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${winConnectorReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="winConnectorRejectUpload" name="winConnectorRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/winConnector" id="winConnectorAttach" name="winConnectorAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- Teflon Connector-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="teflonConnector">Teflon Connector</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="teflonConnector" value="Pass" <c:if test="${itemVm.teflonConnector == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="teflonConnector" value="Fail" <c:if test="${itemVm.teflonConnector == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="teflonConnector" value="NA" <c:if test="${itemVm.teflonConnector == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="teflonConnectorRejectQty" value="${itemVm.teflonConnectorRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="teflonConnectorReject" name="teflonConnectorReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${teflonConnectorReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="teflonConnectorRejectUpload" name="teflonConnectorRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/teflonConnector" id="teflonConnectorAttach" name="teflonConnectorAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- Pogo / Receptacles Pin-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="pogoReceptaclesPin">Hardware Fasteners</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pogoReceptaclesPin" value="Pass" <c:if test="${itemVm.pogoReceptaclesPin == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pogoReceptaclesPin" value="Fail" <c:if test="${itemVm.pogoReceptaclesPin == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="pogoReceptaclesPin" value="NA" <c:if test="${itemVm.pogoReceptaclesPin == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="pogoReceptaclesPinRejectQty" value="${itemVm.pogoReceptaclesPinRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="pogoReceptaclesPinReject" name="pogoReceptaclesPinReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${pogoReceptaclesPinReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="pogoReceptaclesPinRejectUpload" name="pogoReceptaclesPinRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/pogoReceptaclesPin" id="pogoReceptaclesPinAttach" name="pogoReceptaclesPinAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--Cable/Wired/Copper Wire-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="cableWiredCopperWire">Cable/Wired/Copper Wire</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="cableWiredCopperWire" value="Pass" <c:if test="${itemVm.cableWiredCopperWire == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="cableWiredCopperWire" value="Fail" <c:if test="${itemVm.cableWiredCopperWire == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="cableWiredCopperWire" value="NA" <c:if test="${itemVm.cableWiredCopperWire == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="cableWiredCopperWireRejectQty" value="${itemVm.cableWiredCopperWireRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="cableWiredCopperWireReject" name="cableWiredCopperWireReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${cableWiredCopperWireReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="cableWiredCopperWireRejectUpload" name="cableWiredCopperWireRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/cableWiredCopperWire" id="cableWiredCopperWireAttach" name="cableWiredCopperWireAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--Label & Identification-->
        <div class="col-lg-6">
            <div class="card mb-2">
                <div class="card-body">
                    <div class="col-lg-12">
                        <label class="form-label" for="labelIdentification">Label & Identification</label>
                        <div class="radio-group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="labelIdentification" value="Pass" <c:if test="${itemVm.labelIdentification == 'Pass'}">checked</c:if>>
                                <label class="form-check-label">Pass</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="labelIdentification" value="Fail" <c:if test="${itemVm.labelIdentification == 'Fail'}">checked</c:if>>
                                <label class="form-check-label">Fail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="labelIdentification" value="NA" <c:if test="${itemVm.labelIdentification == 'NA'}">checked</c:if>>
                                <label class="form-check-label">NA</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-2 col-sm-12 col-12">
                                <label class="form-label">Reject Qty</label>
                                <div class="input input-group">
                                    <input type="number" class="form-control" name="labelIdentificationRejectQty" value="${itemVm.labelIdentificationRejectQty}">
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-12 col-12">
                                <label class="form-label">Reject Criteria</label>
                                <div class="input input-group">
                                    <select class="form-control" id="labelIdentificationReject" name="labelIdentificationReject" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                        <option></option>
                                        <c:forEach items="${labelIdentificationReject}" var="invInner">
                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <label class="form-label">Upload</label>
                                <div class="input input-group">
                                    <input type="file" class="form-control" id="labelIdentificationRejectUpload" name="labelIdentificationRejectUpload">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6 col-sm-12 col-12">
                                &nbsp;
                            </div>
                            <div class="col-xl-6 col-sm-12 col-12">
                                <div class="mb-2">
                                    <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/labelIdentification" id="labelIdentificationAttach" name="labelIdentificationAttach"> Download Attachment</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <div>
            <button type="submit" id="submit" class="btn btn-primary float-end" disabled>Save</button>
        </div>

    </div>
</form>