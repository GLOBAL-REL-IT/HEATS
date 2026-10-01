<%-- 
    Document   : ft
    Created on : Oct 1, 2026, 11:42:23 AM
    Author     : zbqb9x
--%>

<div class="col-12">
    <div class="section-header" data-bs-toggle="collapse" data-bs-target="#dispoDetails" aria-expanded="false" aria-controls="dispoDetails">
        <span>MAVERICK DETAILS</span>
        <i class="bi bi-chevron-down section-arrow"></i>
    </div>
</div>
<div class="collapse col-12" id="dispoDetails">
    <div class="section-content">
        <div class="row gx-3">
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
                                    <div class="col-xl-4 col-sm-12">
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
                                    <div class="col-xl-4 col-sm-12">
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
                                    <div class="col-xl-4 col-sm-12">
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
                                    <div class="col-xl-4 col-sm-12">
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
                                    <div class="col-xl-4 col-sm-12">
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