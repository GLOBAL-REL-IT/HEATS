<%-- 
    Document   : registration_ft
    Created on : Sep 15, 2026, 11:51:07 AM
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
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${empty data.disposition1Date or empty data.disposition2Date ? contextPath.concat('/maverick/repair/').concat(id) : '#'}" class="btn btn-outline-${empty data.disposition1Date or empty data.disposition2Date ? 'info' : 'secondary disabled'} me-2" role="button"><i class="bi bi-tools"></i>&nbsp;&nbsp;Repair</a>
                        <a href="${contextPath}/maverick/scrap/${id}" class="btn btn-outline-info me-2" role="button"><i class='bi bi-recycle'></i>&nbsp;&nbsp;Scrap</a>
                        <a href="${contextPath}/maverick/bypass/${id}" class="btn btn-outline-info me-2 ms-auto" role="button"><i class="bi bi-box-arrow-up-right"></i>&nbsp;&nbsp;Bypass</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <c:if test="${leakCheck eq 'Yes'}">
                                <div class="accordion-item">
                                    <div class="card-header">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            <div>Leakage Test</div>
                                        </h5>
                                    </div>
                                    <div id="panelsStayOpen-collapseThree" class="accordion-collapse ${leakshow}" aria-labelledby="panelsStayOpen-headingThree">
                                        <div class="accordion-body">
                                            <fieldset disabled>
                                                <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetail/ftest/save/leakTest" method="post" enctype="multipart/form-data" novalidate>
                                                    <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                    <div class="form-group required col-xl-1 col-sm-12">
                                                        <div class="mb-3">
                                                            <label for="quantity" class="form-label">Quantity</label>
                                                            <div class="input input-group">
                                                                <input type="number" class="form-control" id="totalQty" name="totalQty" value="${dataTest.leakQty}" style="width: 100%" required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="leakResult" class="form-label">Leakage Result</label>
                                                            <select class="select-single js-states form-control" id="leakResult" name="leakResult" title="Select Leakage Result" data-live-search="true" style="width: 100%" required>
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
                                                    <c:if test="${not empty dataTest.leakUpload}">
                                                        <div class="col-xl-2 col-sm-12">
                                                            <div class="mb-3">
                                                                <a class="form-label" href="${contextPath}/maverick/checkfile/leaktest/${mibItemId}" id="leakTestAttach" name="leakTestAttach">Download Leakage Test</a>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                </form>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${manCheck eq 'Yes'}">
                                <div class="accordion-item">
                                    <div class="card-header">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            <div>Manual Test</div>
                                        </h5>
                                    </div>
                                    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse ${manshow}" aria-labelledby="panelsStayOpen-headingTwo">
                                        <div class="accordion-body">
                                            <fieldset>
                                                <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetail/createManualTest" method="post" enctype="multipart/form-data">
                                                    <div class="row">
                                                        <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                        <div class="col-sm-4">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div>
                                                                <input type="text" class="form-control" id="labelStatus" name="labelStatus" value="Failed Functional Test - Manual Test" readonly>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-2">
                                                            <div class="card-body d-flex flex-column">
                                                                <a href="${contextPath}/maverickdetails/manualtest" target="_blank" class="btn btn-primary">Details</a>
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
                                    <div class="card-header">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            <div>BIB Test</div>
                                        </h5>
                                    </div>
                                    <div id="panelsStayOpen-collapseOne" class="accordion-collapse ${bibshow}" aria-labelledby="panelsStayOpen-headingOne">
                                        <div class="accordion-body">
                                            <fieldset disabled>
                                                <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetail/ftest/save/bibTest" method="post" enctype="multipart/form-data" novalidate>
                                                    <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                    <div class="form-group required col-xl-1 col-sm-12">
                                                        <div class="mb-3">
                                                            <label for="quantity" class="form-label">Quantity</label>
                                                            <div class="input input-group">
                                                                <input type="number" class="form-control" id="totalQty" name="totalQty" value="${dataTest.bibQty}" style="width: 100%" required>
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
                                                    <c:if test="${not empty dataTest.bibUpload}">
                                                        <div class="col-xl-2 col-sm-12">
                                                            <div class="mb-3">
                                                                <a class="form-label" href="${contextPath}/maverick/checkfile/bibtest/${mibItemId}" id="bibTestAttach" name="bibTestAttach">Download BIB Test</a>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                </form>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${daqCheck eq 'Yes'}">
                                <div class="accordion-item">
                                    <div class="card-header">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            <div>BIB DAQ</div>
                                        </h5>
                                    </div>
                                    <div id="panelsStayOpen-collapseSix" class="accordion-collapse ${bibDshow}" aria-labelledby="panelsStayOpen-headingSix">
                                        <div class="accordion-body">
                                            <fieldset disabled>
                                                <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetail/ftest/save/bibDaqTest" method="post" enctype="multipart/form-data" novalidate>
                                                    <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                    <div class="form-group required col-xl-1 col-sm-12">
                                                        <div class="mb-3">
                                                            <label for="quantity" class="form-label">Quantity</label>
                                                            <div class="input input-group">
                                                                <input type="number" class="form-control" id="totalQty" name="totalQty" value="${dataTest.bibDaqQty}" style="width: 100%" required>
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
                                                    <c:if test="${not empty dataTest.bibDaqUpload}">
                                                        <div class="col-xl-2 col-sm-12">
                                                            <div class="mb-3">
                                                                <a class="form-label" href="${contextPath}/maverick/checkfile/bibdaqtest/${mibItemId}" id="bibDaqTestAttach" name="bibDaqTestAttach">Download BIB DAQ Test</a>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                </form>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${psCheck eq 'Yes'}">
                                <div class="accordion-item">
                                    <div class="card-header">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            <div>Power Supply Leakage Test</div>
                                        </h5>
                                    </div>
                                    <div id="panelsStayOpen-collapseFour" class="accordion-collapse ${psshow}" aria-labelledby="panelsStayOpen-headingFour">
                                        <div class="accordion-body">
                                            <fieldset disabled>
                                                <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetail/ftest/save/psTest" method="post" enctype="multipart/form-data" novalidate>
                                                    <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quantity" class="form-label">Quantity</label>
                                                            <div class="input input-group">
                                                                <input type="number" class="form-control" id="totalQty" name="totalQty" value="${dataTest.psQty}" style="width: 100%" required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="psResult" class="form-label">Power Supply Leakage Result</label>
                                                            <select class="select-single js-states form-control" id="psResult" name="psResult" title="Select Leakage Result" data-live-search="true" style="width: 100%" >
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
                                                    <c:if test="${not empty dataTest.psUpload}">
                                                        <div class="col-xl-2 col-sm-12">
                                                            <div class="mb-3">
                                                                <a class="form-label" href="${contextPath}/maverick/checkfile/pstest/${mibItemId}" id="psTestAttach" name="psTestAttach">Download Power Supply Leakage Test</a>
                                                            </div>
                                                        </div>        
                                                    </c:if>
                                                </form>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${winCheck eq 'Yes'}">
                                <div class="accordion-item">
                                    <div class="card-header">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            <div>Winchester Chamber Leakage Test</div>
                                        </h5>
                                    </div>
                                    <div id="panelsStayOpen-collapseFive" class="accordion-collapse ${winshow}" aria-labelledby="panelsStayOpen-headingFive">
                                        <div class="accordion-body">
                                            <fieldset disabled>
                                                <form class="row gx-3 align-items-end" role="form" action="${contextPath}/rmsbookingDetail/ftest/save/winTest" method="post" enctype="multipart/form-data" novalidate>
                                                    <input type="hidden" class="form-control" id="motherboardId" name="motherboardId" value="${mibItemId}">
                                                    <div class="form-group required col-xl-1 col-sm-12">
                                                        <div class="mb-3">
                                                            <label for="quantity" class="form-label">Quantity</label>
                                                            <div class="input input-group">
                                                                <input type="number" class="form-control" id="totalQty" name="totalQty" value="${dataTest.winQty}" style="width: 100%" required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="winResult" class="form-label">Winchester Chamber Leakage Result</label>
                                                            <select class="select-single js-states form-control" id="winResult" name="winResult" title="Select Winchester Chamber Leakage Result" data-live-search="true" style="width: 100%" required>
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
                                                    <c:if test="${not empty dataTest.winUpload}">
                                                        <div class="col-xl-2 col-sm-12">
                                                            <div class="mb-3">
                                                                <a class="form-label" href="${contextPath}/maverick/checkfile/wintest/${mibItemId}" id="winTestAttach" name="winTestAttach">Download Winchester Chamber Leakage Test</a>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                </form>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
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
        <script src="${contextPath}/resources/vendor/DataTables/customitem/dataTables.js"></script>

<!--        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>
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
        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select-custom.js"></script>-->
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
        </script>
    </s:layout-component>
</s:layout-render>