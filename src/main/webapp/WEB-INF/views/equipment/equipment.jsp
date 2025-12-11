<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!-- Data Tables -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
        <!-- Bootstrap Select CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">

<!--        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/customitem/dataTables.dataTables.css"/>
<link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.min.css"/>-->
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
                content:"*";
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


        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <!--<button class="btn btn-outline-success me-2" type="button">Pending Registration (BIB/ Bib Card)</button>-->
                        <a href="${contextPath}/equipment/updateListSpts" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-arrow-clockwise'></i>&nbsp;&nbsp;Update List from SPTS</a>
                        <c:if test="${userEqptAdd == 'Yes'}"><a href="${contextPath}/equipment/add" class="btn btn-outline-success me-2" role="button">
                                <i class='bi bi-plus-square'></i>&nbsp;&nbsp;Add New</a></c:if>
                        <a href="${contextPath}/equipment/family/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-list-task'></i>&nbsp;&nbsp;Eqpt Family</a>
                        <a href="${contextPath}/equipment/relTestGroup/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-list-task'></i>&nbsp;&nbsp;Eqpt Rel Test Group</a>
                        <a href="${contextPath}/equipment/monitoring/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-list-task'></i>&nbsp;&nbsp;Eqpt Monitoring</a>
                        <a href="${contextPath}/equipment/tech/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-list-task'></i>&nbsp;&nbsp;Eqpt Tech</a>
                        <a href="${contextPath}/equipment/viMonitoring/add" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-list-task'></i>&nbsp;&nbsp;Eqpt VI Monitoring</a>

                        <a href="${contextPath}/hw/item/query" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-search'></i>&nbsp;&nbsp;Query</a>

                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Equipment Module</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/equipment" method="post">
                                <div class="row mb-3">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Rel Test Group</label>
                                    <div class="col-sm-3 col-md-3">
                                        <div class="row g-1">
                                            <div class="col-sm-11 col-md-12">
                                                <select class="js-example-basic-single" id="relTestGroup" name="relTestGroup"
                                                        style="width: 100%">
                                                    <!--style="width: 100%" onchange="toggleLinkVisibility()">-->
                                                    <option></option>
                                                    <c:forEach items="${relTestGroupList}" var="invInner">
                                                        <option value="${invInner.rel_test_group_name}">${invInner.rel_test_group_name}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" class="form-control" id="userEqptEdit" name="userEqptEdit" placeholder="" value="${userEqptEdit}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-primary">Fetch</button>
                                        <span id="linkContainer" style="display:none;">
                                            <a href="${contextPath}/equipment/2" class="btn btn-outline-success me-2" role="button" id="myLink">
                                                <i class='bi bi-arrow-clockwise'></i>&nbsp;&nbsp;Update List from SPTS</a>
                                        </span>
                                    </div>
                                </div>
                            </form>
                            <!-- Row end -->

                        </div>
                    </div>
                    <!-- Card end -->
                </div>
            </div>
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">

                <div class="col-sm-4 col-12">

                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-list-ul me-2"></i>List of Equipment ${relTestGroupTitle}
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="scrollVertical2" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <!--<th class="col-12">Site</th>-->
                                                    <th>Rel Test Group</th>
                                                    <th>Family</th>
                                                    <th class="col-12">Equipment ID</th>
                                                    <th>Equipment Type</th>
                                                    <th>Status</th>
                                                    <th>Detail</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${eqptList}" var="request" varStatus="requestLoop">
                                                    <tr>
                                                        <td><c:out value="${request.relTestGroup}"/></td>
                                                        <td><c:out value="${request.familyName}"/></td>
                                                        <td><c:out value="${request.equipmentId}"/></td>
                                                        <td><c:out value="${request.eqptTypeName}"/></td>
                                                        <td><c:out value="${request.statusName}"/></td>
                                                        <td>
                                                            <a modaldeleteid="${request.sptsPkid}" class="btn btn-sm me-1" title="Detail" data-toggle="Detail" onclick="modalDelete(this);">
                                                                <!--Detail-->
                                                                <i class="bi bi-box-arrow-in-right h3"></i></a>
                                                        </td>
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
                    <!-- Card end -->
                </div>
                <div class="col-sm-8 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="custom-tabs-container">
                                <ul class="fs-6 nav nav-tabs " id="customTab4" role="tablist">
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link active" id="tab-oneAAA" data-bs-toggle="tab" href="#oneAAA" role="tab"
                                           aria-controls="oneAAA" aria-selected="true"><i class="bi bi-person-badge"></i>Equipment Details</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade show active" id="oneAAA" role="tabpanel">
                                        <form class="row gx-3 needs-validation" role="form" action="${contextPath}/equipment/update" method="post" novalidate>
                                            <c:if test="${not empty relTestGroupTitle}"><div class="mb-3 container-fluid justify-content-start">
                                                    <c:if test="${userEqptDelete == 'Yes'}"><a onclick="scrapModal();" role="button" title="Scrap" data-bs-toggle="modal" data-bs-target="#delete_modal" class="btn btn-outline-danger me-2">
                                                            <i class="bi bi-trash3" style="color:red"></i>&nbsp;&nbsp;Scrap</a></c:if>
                                                </div></c:if>
                                                <!-- Row start -->
                                                <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                    <div class="mb-3">
                                                        <!--<div class="mb-3 was-validated">-->
                                                        <label for="eqptId" class="form-label">Equipment ID</label>
                                                        <div class="input input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                            <input type="text" class="input form-control" id="eqptId" name="eqptId" placeholder="" value="" readonly required >
                                                            <!--<div class="valid-feedback">Looks good!</div>-->
                                                            <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                    <div class="mb-3">
                                                        <label for="familyName" class="form-label">Family Name</label>
                                                        <div class="input input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                            <input type="text" class="input form-control" id="familyName" name="familyName" placeholder="" value="" readonly required>
                                                            <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="" readonly>
                                                            <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="" readonly>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                    <div class="mb-3">
                                                        <label for="relTestGroup" class="form-label">Rel Test Group</label>
                                                        <div class="input input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                            <input type="text" class="input form-control" id="relTestGroup" name="relTestGroup" placeholder="" value="" readonly required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                    <div class="mb-3">
                                                        <label for="eqptType" class="form-label">Equipment Type</label>
                                                        <div class="input input-group">
                                                            <select class="input js-example-tags" id="eqptType" name="eqptType"
                                                                    title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                <option></option>
                                                                <option value="1">Life</option>
                                                                <option value="2">Environment</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                    <div class="mb-3">
                                                        <label for="eqptStatus" class="form-label">Equipment Status</label>
                                                        <div class="input input-group">
                                                            <select class="input js-example-tags" id="eqptStatus" name="eqptStatus"
                                                                    title="Select Eqpt Status" data-live-search="true" style="width: 100%" readonly>
                                                                <option></option>
                                                                <option value="1">Active</option>
                                                                <option value="0">Inactive</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-4 col-sm-12 col-12">
                                                    <div class="mb-3">
                                                        <label for="eqptManufacturer" class="form-label">Equipment Manufacturer</label>
                                                        <div class="input input-group">
                                                            <select class="input js-example-tags" id="eqptManufacturer" name="eqptManufacturer"
                                                                    title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                <option></option>
                                                            <c:forEach items="${eqptManufacturer}" var="invInner">
                                                                <option value="${invInner.eqptManufacturer}" ${invInner.selected}>${invInner.eqptManufacturer}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-4 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="eqptModel" class="form-label">Equipment Model</label>
                                                    <div class="input input-group">
                                                        <select class="input js-example-tags" id="eqptModel" name="eqptModel"
                                                                title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                            <option></option>
                                                            <c:forEach items="${listeqptModel}" var="invInner">
                                                                <option value="${invInner.eqptModel}" ${invInner.selected}>${invInner.eqptModel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-4 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="eqptTech" class="form-label">Equipment Tech</label>
                                                    <div class="input input-group">
                                                        <select class="input js-example-tags" id="eqptTech" name="eqptTech"
                                                                title="Select Eqpt Tech" data-live-search="true" style="width: 100%" readonly>
                                                            <option></option>
                                                            <c:forEach items="${listEqptTech}" var="invInner">
                                                                <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.eqptTech}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-4 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="eqptMon" class="form-label">Equipment Monitoring</label>
                                                    <div class="input input-group">
                                                        <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                        <!--<input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="" readonly>-->
                                                        <select class="input js-example-tags" id="eqptMon" name="eqptMon"
                                                                title="Select Eqpt Monitoring" data-live-search="true" style="width: 100%" readonly>
                                                            <option></option>
                                                            <c:forEach items="${listEqptMon}" var="invInner">
                                                                <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.eqptMon}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-4 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="eqptViMon" class="form-label">Equipment VI Monitoring</label>
                                                    <div class="input input-group">
                                                        <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                        <!--<input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="" readonly>-->
                                                        <select class="input js-example-tags" id="eqptViMon" name="eqptViMon"
                                                                title="Select Eqpt VI Monitoring" data-live-search="true" style="width: 100%" readonly>
                                                            <option></option>
                                                            <c:forEach items="${listEqptViMon}" var="invInner">
                                                                <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.eqptViMon}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-4 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="eqptCapability" class="form-label">Equipment Capability</label>
                                                    <div class="input input-group">
                                                        <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                        <input type="text" class="input form-control" id="eqptCapability" name="eqptCapability" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="slotQty" class="form-label">Slot Qty</label>
                                                    <div class="input input-group">
                                                        <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                        <input type="number" class="input form-control" id="slotQty" name="slotQty" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="rackQty" class="form-label">Rack Total</label>
                                                    <div class="input input-group">
                                                        <input type="number" class="input form-control" id="rackQty" name="rackQty" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="zonePerRack" class="form-label">Zone per Rack</label>
                                                    <div class="input input-group">
                                                        <input type="number" class="input form-control" id="zonePerRack" name="zonePerRack" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="trayQtyPerRack" class="form-label">Tray Qty per Rack</label>
                                                    <div class="input input-group">
                                                        <input type="number" class="input form-control" id="trayQtyPerRack" name="trayQtyPerRack" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="basketQtyPerRack" class="form-label">Crocodile Qty per Rack</label>
                                                    <div class="input input-group">
                                                        <input type="number" class="input form-control" id="basketQtyPerRack" name="basketQtyPerRack" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="trayQtyPerZone" class="form-label">Tray Qty per Zone</label>
                                                    <div class="input input-group">
                                                        <input type="number" class="input form-control" id="trayQtyPerZone" name="trayQtyPerZone" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-2 col-sm-12 col-12">
                                                <div class="mb-3">
                                                    <label for="basketQtyPerZone" class="form-label">Crocodile Qty per Zone</label>
                                                    <div class="input input-group">
                                                        <input type="number" class="input form-control" id="basketQtyPerZone" name="basketQtyPerZone" placeholder="" value="" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Additional Information Section -->
                                            <div class="col-12 mt-2 mb-3">
                                                <h6 class="fw-semibold mb-3 border-start border-warning ps-2"
                                                    style="border-left-width: 3px !important;">
                                                    <i class="bi bi-chat-square-text me-2"></i>Additional Information
                                                </h6>
                                            </div>
                                            <div class="col-12">
                                                <div class="mb-3">
                                                    <label for="remarks" class="form-label">Remarks</label>
                                                    <div class="input input-group">
                                                        <span class="input input-group-text"><i class="bi bi-pencil"></i></span>
                                                        <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                  rows="3" readonly></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="d-flex justify-content-end gap-2">
                                                <c:if test="${userEqptEdit == 'Yes'}"><button type="submit" class="btn btn-primary">Update</button></c:if>
                                                </div>
                                                <!-- Form actions end -->
                                            </form>
                                        </div> 
                                        <!--end div for 1st tab-->
                                    </div>
                                    <!--end div for tab content-->
                                </div>
                                <!--end div for tab container-->
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
    <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>
    <script src="${contextPath}/resources/vendor/DataTables/customitem/dataTables.js"></script>

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

                                                        $(document).ready(function () {
                                                            $('.js-example-basic-single').select2();
                                                            $(".js-example-tags").select2({
                                                                tags: true
                                                            });
                                                            //                                                        $('input[readonly]').removeAttr('readonly');

                                                            if ($('#userEqptEdit').val() === "Yes") {
                                                                $("#itemType2").prop("readonly", false);
                                                                $("#subType").prop("readonly", false);
                                                                $("#itemPKID").prop("readonly", false);
                                                                $("#itemId").prop("readonly", false);
                                                                $("#mibId").prop("readonly", false);
                                                                $("#itemName").prop("readonly", false);
                                                                $("#aluHrs").prop("readonly", false);

                                                                $("#assemblyId").prop("readonly", false);
                                                                $("#rack").prop("readonly", false);
                                                                $("#shelf").prop("readonly", false);
                                                                $("#unitCost").prop("readonly", false);
                                                                $("#totalCost").prop("readonly", false);
                                                                //                                                        $("#status").prop("readonly", false);
                                                                $("#minQty").prop("readonly", false);
                                                                $("#maxQty").prop("readonly", false);
                                                                $("#pmWw1").prop("readonly", false);
                                                                $("#pmWw2").prop("readonly", false);
                                                                $("#expirationDate").prop("readonly", false);
                                                                $("#isCritical").prop("readonly", false);
                                                                //                                                            $("#isConsumable").prop("readonly", false);
                                                                $("#downtimeValue").prop("readonly", false);
                                                                $("#downtimeUnit").prop("readonly", false);
                                                                $("#implementationCost").prop("readonly", false);
                                                                $("#manpowerValue").prop("readonly", false);
                                                                $("#manpowerUnit").prop("readonly", false);
                                                                $("#complexity").prop("readonly", false);
                                                                $("#model").prop("readonly", false);
                                                                $("#manufacturer").prop("readonly", false);
                                                                $("#equipmentType").prop("readonly", false);
                                                                $("#equipmentModel").prop("readonly", false);
                                                                $("#equipmentManufacturer").prop("readonly", false);
                                                                $("#onHandQty").prop("readonly", false);
                                                                $("#productionQty").prop("readonly", false);
                                                                $("#productionStagingQty").prop("readonly", false);
                                                                $("#repairQty").prop("readonly", false);
                                                                $("#quarantineQty").prop("readonly", false);
                                                                $("#otherQty").prop("readonly", false);
                                                                $("#vendorQty").prop("readonly", false);
                                                                $("#otherOnsemiQty").prop("readonly", false);
                                                                $("#externalCleanQty").prop("readonly", false);
                                                                $("#externalRecleanQty").prop("readonly", false);
                                                                $("#internalCleanQty").prop("readonly", false);
                                                                $("#internalRecleanQty").prop("readonly", false);
                                                                $("#storageFactoryQty").prop("readonly", false);
                                                                $("#stressType").prop("readonly", false);
                                                                $("#remarks").prop("readonly", false);
                                                                $("#isConsumable").prop("disabled", false);
                                                            }
                                                        });

                                                        function scrapModal() {
                                                            var itemPKID = $("#itemPKID").val();
                                                            var itemId = $("#itemId").val();
                                                            var itemType = $("#itemType").val();
                                                            var deleteUrl = "${contextPath}/admin/user/delete2222/" + deleteId;
                                                            var deleteMsg = "Are you want to scrap ";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }


                                                        $(function () {
                                                            $("#scrollVertical2").DataTable({
                                                                scrollY: "680px",
                                                                scrollCollapse: false,
                                                                paging: false,
                                                                bInfo: false,
                                                                dom: 'Bfrtip',
                                                                buttons: ["copy", "csv", "print"],
                                                                columnDefs: [{
                                                                        targets: [0, 1, 3, 4], visible: false
                                                                    }]
                                                            });
                                                        });

                                                        function modalDelete(e) {
                                                            var pkId = $(e).attr("modaldeleteid");
                                                            $.ajax({
                                                                url: '${contextPath}/equipment/detail', // Replace with your controller URL
                                                                type: 'GET',
                                                                data: {pkID: pkId},
                                                                dataType: 'json',
                                                                success: function (data) {

                                                                    // Populate form fields with received data
                                                                    $("#itemType2").val(data.itemType);
                                                                    $("#subType").val(data.subType);
                                                                    $("#itemPKID").val(data.sptsPkid);
                                                                    $("#itemId").val(data.itemId);
                                                                    $("#mibId").val(data.id);
                                                                    $("#itemName").val(data.itemName);
                                                                    $("#aluHrs").val(data.aluHrs);

                                                                    //                                                                $("#assemblyId").val(data.assemblyId);
                                                                    $('#assemblyId').val(data.assemblyId).trigger('change');
                                                                    $("#rack").val(data.rack);
                                                                    $("#shelf").val(data.shelf);
                                                                    $("#unitCost").val(data.unitCost);
                                                                    $("#totalCost").val(data.totalCost);
                                                                    $("#status").val(data.status);
                                                                    $("#minQty").val(data.minQty);
                                                                    $("#maxQty").val(data.maxQty);
                                                                    $("#pmWw1").val(data.PmWw1);
                                                                    $("#pmWw2").val(data.PmWw2);
                                                                    $("#expirationDate").val(data.expirationDate);
                                                                    $("#isCritical").val(data.isCritical);
                                                                    //                                                                $("#isConsumable").val(data.isConsumeable);
                                                                    $("#downtimeValue").val(data.downtimeValue);
                                                                    $("#downtimeUnit").val(data.downtimeUnit);
                                                                    $("#implementationCost").val(data.implementationCost);
                                                                    $("#manpowerValue").val(data.manpowerValue);
                                                                    $("#manpowerUnit").val(data.manpowerUnit);
                                                                    $("#complexity").val(data.complexity);
                                                                    $("#model").val(data.model).trigger('change');
                                                                    $("#manufacturer").val(data.manufacturer).trigger('change');
                                                                    $("#equipmentType").val(data.equipmentType).trigger('change');
                                                                    $("#equipmentModel").val(data.equipmentModel).trigger('change');
                                                                    $("#equipmentManufacturer").val(data.equipmentManufacturer).trigger('change');
                                                                    $("#onHandQty").val(data.onHandQty);
                                                                    $("#productionQty").val(data.productionQty);
                                                                    $("#productionStagingQty").val(data.productionStagingQty);
                                                                    $("#repairQty").val(data.repairQty);
                                                                    $("#quarantineQty").val(data.quarantineQty);
                                                                    $("#otherQty").val(data.otherQty);
                                                                    $("#vendorQty").val(data.vendorQty);
                                                                    $("#otherOnsemiQty").val(data.OtherONQty);
                                                                    $("#externalCleanQty").val(data.externalCleanQty);
                                                                    $("#externalRecleanQty").val(data.externalRecleanQty);
                                                                    $("#internalCleanQty").val(data.internalCleanQty);
                                                                    $("#internalRecleanQty").val(data.internalRecleanQty);
                                                                    $("#storageFactoryQty").val(data.storageFactoryQty);
                                                                    $("#totalQty").val(data.totalQty);
                                                                    $("#remarks").val(data.remarks);
                                                                    $("#itemUsage").val(data.itemUsage);
                                                                    $("#stressType").val(data.stressType).trigger('change');

                                                                    if (data.isConsumable === "true") {
                                                                        $("#isConsumable").attr('checked', true);
                                                                    } else {
                                                                        $("#isConsumable").attr('checked', false);
                                                                    }

                                                                },
                                                                error: function (jqXHR, textStatus, errorThrown) {
                                                                    console.error("Error loading data: " + textStatus, errorThrown);
                                                                }
                                                            });
                                                            document.querySelector('#tab-oneAAA').click();
                                                        }

    </script>
</s:layout-component>
</s:layout-render>