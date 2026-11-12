<%-- 
    Document   : redo_ft
    Created on : Nov 3, 2026, 3:33:52 PM
    Author     : zbqb9x
--%>

<h6>Hello World!</h6>
<h6>Masih tak buat lagi</h6>
<div class="card-body">
    <div class="accordion" id="accordionPanelsStayOpenExample">
        <c:if test="${leakCheck eq 'Yes'}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                        Leakage Test
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse ${leakshow}"
                     aria-labelledby="panelsStayOpen-headingThree">
                    <div class="accordion-body">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save/leakTest" method="post" enctype="multipart/form-data" novalidate>
                            <div class="col-xl-1 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Quantity</label>
                                    <div class="input input-group">
                                        <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${dataTest.leakQty}" min="1" required>
                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="leakResult" class="form-label">Leakage Result</label>
                                    <div class="input input-group">
                                        <select class="select-single js-states form-control" id="leakResult" name="leakResult" title="Select Leakage Result" data-live-search="true" style="width: 100%" required>
                                            <option></option>
                                            <c:forEach items="${leakResultData}" var="invInner">
                                                <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xl-4 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="leakUpload" class="form-label">Upload Result</label>
                                    <div class="input input-group">
                                        <input class="form-control" type="file" id="leakUpload" name="leakUpload">
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty dataTest.leakUpload}">
                                <div class="row gx-4">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-2">
                                            <a class="form-label" href="${contextPath}/hw/item/ft/leaktest/${item.id}" id="leakTestAttach" name="leakTestAttach"> Download Attachment</a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="col-md-12">
                                <button type="submit" id="submit" class="btn btn-primary float-end ${leakbutton}">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${manCheck eq 'Yes'}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                        Manual Test
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse ${manshow}" aria-labelledby="panelsStayOpen-headingTwo">
                    <div class="accordion-body">
                        <div class="row">
                            <div class="col-sm-6">
                                <label for="status" class="form-label">Status</label>
                                <div>
                                    <input type="text" class="form-control" id="labelStatus" name="labelStatus" placeholder="" value="${item.status}" readonly>
                                    <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                </div>
                            </div>
                            <div class="col-sm-4">
                            </div>
                            <div class="col-sm-2">
                                <div class="p-3 d-flex justify-content-end">
                                    <a href="https://mysed-rel-app05/HEATS-mini/manual_test.php?id=${item.id}" class="leads rounded-3 d-xxl-flex d-none">
                                        <i class="bi bi-box-arrow-right" style="color:#ffffff"></i>&nbsp;&nbsp;Inspect Manual Test
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${bibCheck eq 'Yes'}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false" aria-controls="panelsStayOpen-collapseOne">
                        BIB Test
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse ${bibshow}"
                     aria-labelledby="panelsStayOpen-headingOne">
                    <div class="accordion-body">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save/bibTest" method="post" enctype="multipart/form-data" novalidate>
                            <div class="col-xl-1 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Quantity</label>
                                    <div class="input input-group">
                                        <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${dataTest.bibQty}" min="1" required>
                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="bibResult" class="form-label">BIB Result</label>
                                    <div class="input input-group">
                                        <select class="select-single js-states form-control" id="bibResult" name="bibResult" title="Select Item Usage" data-live-search="true" style="width: 100%" required>
                                            <option></option>
                                            <c:forEach items="${bibResultData}" var="invInner">
                                                <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xl-4 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="bibUpload" class="form-label">Upload Result</label>
                                    <div class="input input-group">
                                        <input class="form-control" type="file" id="bibUpload" name="bibUpload">
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty dataTest.bibUpload}">
                                <div class="row gx-4">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-2">
                                            <a class="form-label" href="${contextPath}/hw/item/ft/bibtest/${item.id}" id="bibTestAttach" name="bibTestAttach"> Download Attachment</a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="col-md-12">
                                <button type="submit" id="submit" class="btn btn-primary float-end ${bibbutton}">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${bibDaqCheck eq 'Yes'}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="panelsStayOpen-headingSix">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseSix" aria-expanded="false" aria-controls="panelsStayOpen-collapseSix">
                        BIB DAQ Test
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseSix" class="accordion-collapse collapse ${bibdaqshow}"
                     aria-labelledby="panelsStayOpen-headingSix">
                    <div class="accordion-body">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save/bibDaqTest" method="post" enctype="multipart/form-data" novalidate>
                            <div class="col-xl-1 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Quantity</label>
                                    <div class="input input-group">
                                        <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${dataTest.bibDaqQty}" min="1" required>
                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="bibDaqResult" class="form-label">BIB Result</label>
                                    <div class="input input-group">
                                        <select class="select-single js-states form-control" id="bibDaqResult" name="bibDaqResult" title="Select Item Usage" data-live-search="true" style="width: 100%" required>
                                            <option></option>
                                            <c:forEach items="${daqResultData}" var="invInner">
                                                <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xl-4 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="bibDaqUpload" class="form-label">Upload Result</label>
                                    <div class="input input-group">
                                        <input class="form-control" type="file" id="bibDaqUpload" name="bibDaqUpload">
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty dataTest.bibDaqUpload}">
                                <div class="row gx-4">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-2">
                                            <a class="form-label" href="${contextPath}/hw/item/ft/bibdaqtest/${item.id}" id="daqTestAttach" name="daqTestAttach"> Download Attachment</a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="col-md-12">
                                <button type="submit" id="submit" class="btn btn-primary float-end ${bibdaqbutton}">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${psCheck eq 'Yes'}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="panelsStayOpen-headingFour">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseFour" aria-expanded="false" aria-controls="panelsStayOpen-collapseFour">
                        Power Supply Leakage Test
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseFour" class="accordion-collapse collapse ${psshow}"
                     aria-labelledby="panelsStayOpen-headingFour">
                    <div class="accordion-body">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save/psTest" method="post" enctype="multipart/form-data" novalidate>
                            <div class="col-xl-1 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Quantity</label>
                                    <div class="input input-group">
                                        <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${dataTest.psQty}" min="1" required>
                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="psResult" class="form-label">Power Supply Leakage Result</label>
                                    <div class="input input-group">
                                        <select class="select-single js-states form-control" id="psResult" name="psResult" title="Select Leakage Result" data-live-search="true" style="width: 100%" >
                                            <option></option>
                                            <c:forEach items="${psResultData}" var="invInner">
                                                <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xl-4 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="psUpload" class="form-label">Upload Result</label>
                                    <div class="input input-group">
                                        <input class="form-control" type="file" id="psUpload" name="psUpload">
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty dataTest.psUpload}">
                                <div class="row gx-4">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-2">
                                            <a class="form-label" href="${contextPath}/hw/item/ft/pstest/${item.id}" id="psTestAttach" name="psTestAttach"> Download Attachment</a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="col-md-12">
                                <button type="submit" id="submit" class="btn btn-primary float-end ${psbutton}">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${winCheck eq 'Yes'}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="panelsStayOpen-headingFive">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseFive" aria-expanded="false" aria-controls="panelsStayOpen-collapseFive">
                        Winchester Chamber Leakage Test
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseFive" class="accordion-collapse collapse ${winshow}"
                     aria-labelledby="panelsStayOpen-headingFive">
                    <div class="accordion-body">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save/winTest" method="post" enctype="multipart/form-data" novalidate>
                            <div class="col-xl-1 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Quantity</label>
                                    <div class="input input-group">
                                        <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${dataTest.winQty}" min="1" required>
                                        <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="winResult" class="form-label">Winchester Chamber Leakage Result</label>
                                    <div class="input input-group">
                                        <select class="select-single js-states form-control" id="winResult" name="winResult" title="Select Winchester Chamber Leakage Result" data-live-search="true" style="width: 100%" required>
                                            <option></option>
                                            <c:forEach items="${winResultData}" var="invInner">
                                                <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xl-4 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="winUpload" class="form-label">Upload Result</label>
                                    <div class="input input-group">
                                        <input class="form-control" type="file" id="winUpload" name="winUpload">
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty dataTest.winUpload}">
                                <div class="row gx-4">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-2">
                                            <a class="form-label" href="${contextPath}/hw/item/ft/wintest/${item.id}" id="winTestAttach" name="winTestAttach"> Download Attachment</a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="col-md-12">
                                <button type="submit" id="submit" class="btn btn-primary float-end ${winbutton}">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>