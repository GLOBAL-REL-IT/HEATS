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
            div.dt-container {
                width: 800px;
                margin: 0 auto;
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
                        <a href="${contextPath}/hw" class="btn btn-outline-warning me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">Query</span></h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/hw/item/query" method="post">
                                <div class="row mb-3">
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemType" class="form-label">Item Type</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="itemType" name="itemType" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${paramItemType}" var="invInner">
                                                        <option value="${invInner.name}">${invInner.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>    
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="subType" class="form-label">Sub Type</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="subType" name="subType" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listSubType}" var="invInner">
                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Item ID</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemName" class="form-label">Item Name</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="model" class="form-label">Assembly ID</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="assemblyId" name="assemblyId" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                        <option value="${invInner.assemblyId}">${invInner.assemblyId}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="model" class="form-label">Model</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="model2" name="model2" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listModel}" var="invInner">
                                                        <option value="${invInner.model}">${invInner.model}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="manufacturer" class="form-label">Manufacturer</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="manufacturer" name="manufacturer" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                        <option value="${invInner.manufacturer}">${invInner.manufacturer}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="equipmentType" class="form-label">Eqpt Type</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="equipmentType" name="equipmentType" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                        <option value="${invInner.equipmentType}">${invInner.equipmentType}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="equipmentModel" class="form-label">Eqpt Model</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="equipmentModel" name="equipmentModel" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                        <option value="${invInner.equipmentModel}">${invInner.equipmentModel}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="equipmentManufacturer" name="equipmentManufacturer" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                        <option value="${invInner.equipmentManufacturer}">${invInner.equipmentManufacturer}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="stressType" class="form-label">Stress Type</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="stressType" name="stressType" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listStressType}" var="invInner">
                                                        <option value="${invInner.stressType}">${invInner.stressType}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="status" class="form-label">Status</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="status" name="status" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${listStatus}" var="invInner">
                                                        <option value="${invInner.status}">${invInner.status}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Search</button>
                                </div>
                                <!-- Form actions end -->
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
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="custom-tabs-container">
                                <ul class="fs-6 nav nav-tabs justify-content-center" id="customTab4" role="tablist">
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link active" id="tab-oneAAA" data-bs-toggle="tab" href="#oneAAA" role="tab"
                                           aria-controls="oneAAA" aria-selected="true"><i class="bi bi-person-badge"></i>HW Details</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link" id="tab-twoAAA" data-bs-toggle="tab" href="#twoAAA" role="tab"
                                           aria-controls="twoAAA" aria-selected="false"><i class="bi bi-list-task"></i>List of HW ID</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab"
                                           aria-controls="threeAAA" aria-selected="false"><i class="bi bi-arrow-left-right"></i>Movement</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link" id="tab-fourAAA" data-bs-toggle="tab" href="#fourAAA" role="tab"
                                           aria-controls="fourAAA" aria-selected="false"><i class="bi bi-house-door"></i>Storage Factory</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade show active" id="oneAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <div class="table-responsive">
                                                <table id="customButtons1" class="table custom-table pending display nowrap">
                                                    <thead>
                                                        <tr>
                                                            <th>No.</th>
                                                            <th class="col-5">Detail</th>
                                                            <th class="col-2">Item Type</th>
                                                            <th class="col-2">Sub Type</th>
                                                            <th class="col-2">Item ID</th>
                                                            <th class="col-2">Item Name</th>
                                                            <th class="col-2">Assembly ID</th>
                                                            <th class="col-2">Item Usage</th>
                                                            <th class="col-2">Stress Type</th>
                                                            <th class="col-2">ALU (hrs)</th>
                                                            <th class="col-2">Model</th>
                                                            <th class="col-2">Manufacturer</th>
                                                            <th class="col-2">Eqpt Type</th>
                                                            <th class="col-2">Eqpt Model</th>
                                                            <th class="col-2">Eqpt Manufacturer</th>
                                                            <th class="col-2">Consumable</th>
                                                            <th class="col-2">Min. Qty</th>
                                                            <th class="col-2">Max. Qty</th>
                                                            <th class="col-2">Rack</th>
                                                            <th class="col-2">Shelf</th>
                                                            <th class="col-2">Unit Cost</th>
                                                            <th class="col-2">Total Cost</th>
                                                            <th class="col-2">Expiration Date</th>
                                                            <th class="col-2">On Hand Qty</th>
                                                            <th class="col-2">Prod. Qty</th>
                                                            <th class="col-2">Prod. Staging Qty</th>
                                                            <th class="col-2">Repair Qty</th>
                                                            <th class="col-2">Quarantine Qty</th>
                                                            <th class="col-2">Other Qty</th>
                                                            <th class="col-1">Other onsemi Qty</th>
                                                            <th class="col-1">Vendor Qty</th>
                                                            <th class="col-1">Ext. Cleaning</th>
                                                            <th class="col-1">Ext. Re-cleaning</th>
                                                            <th class="col-1">Int. Cleaning</th>
                                                            <th class="col-1">Int. Re-cleaning</th>
                                                            <th class="col-1">Storage Factory</th>
                                                            <th class="col-1">Total Qty</th>
                                                            <th class="col-1">Status</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${resultQuery}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                            <tr>
                                                                <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                                <td>
                                                                    <a sptsPkid="${parameterMaster.sptsPkid}" href="#" onclick="ajaxHardware(this);" class="table-link" title="Hardware List"><i class="bi bi-list-task h4"></i></a>
                                                                    <a sptsPkid="${parameterMaster.sptsPkid}" href="#" onclick="ajaxTrans(this);" class="table-link" title="Movement"><i class="bi bi-arrow-left-right h4"></i></a>
                                                                    <a sptsPkid="${parameterMaster.sptsPkid}" href="#" onclick="ajaxStorage(this);" class="table-link" title="Storage Factory"><i class="bi bi-house-door h4"></i></a>
                                                                    <c:if test="${not empty parameterMaster.vmId}">
                                                                    <a href="${contextPath}/hw/item/add2Query/${parameterMaster.id}" class="table-link" title="VM and Functional Test Data">
                                                                        <i class="bi bi-clock-history h4"></i>
                                                                    </a>
                                                                    </c:if>
                                                                </td>
                                                                <td><c:out value="${parameterMaster.itemType}"/></td>
                                                                <td><c:out value="${parameterMaster.subType}"/></td>
                                                                <td><c:out value="${parameterMaster.itemId}"/></td>
                                                                <td><c:out value="${parameterMaster.itemName}"/></td>
                                                                <td><c:out value="${parameterMaster.assemblyId}"/></td>
                                                                <td><c:out value="${parameterMaster.itemUsage}"/></td>
                                                                <td><c:out value="${parameterMaster.stressType}"/></td>
                                                                <td><c:out value="${parameterMaster.aluHrs}"/></td>
                                                                <td><c:out value="${parameterMaster.model}"/></td>
                                                                <td><c:out value="${parameterMaster.manufacturer}"/></td>
                                                                <td><c:out value="${parameterMaster.equipmentType}"/></td>
                                                                <td><c:out value="${parameterMaster.equipmentModel}"/></td>
                                                                <td><c:out value="${parameterMaster.equipmentManufacturer}"/></td>
                                                                <td><c:out value="${parameterMaster.isConsumable}"/></td>
                                                                <td><c:out value="${parameterMaster.minQty}"/></td>
                                                                <td><c:out value="${parameterMaster.maxQty}"/></td>
                                                                <td><c:out value="${parameterMaster.rack}"/></td>
                                                                <td><c:out value="${parameterMaster.shelf}"/></td>
                                                                <td><c:out value="${parameterMaster.unitCost}"/></td>
                                                                <td><c:out value="${parameterMaster.totalCost}"/></td>
                                                                <td><c:out value="${parameterMaster.expirationDate}"/></td>
                                                                <td><c:out value="${parameterMaster.onHandQty}"/></td>
                                                                <td><c:out value="${parameterMaster.productionQty}"/></td>
                                                                <td><c:out value="${parameterMaster.productionStagingQty}"/></td>
                                                                <td><c:out value="${parameterMaster.repairQty}"/></td>
                                                                <td><c:out value="${parameterMaster.quarantineQty}"/></td>
                                                                <td><c:out value="${parameterMaster.otherQty}"/></td>
                                                                <td><c:out value="${parameterMaster.otherOnsemiQty}"/></td>
                                                                <td><c:out value="${parameterMaster.vendorQty}"/></td>
                                                                <td><c:out value="${parameterMaster.externalCleanQty}"/></td>
                                                                <td><c:out value="${parameterMaster.externalRecleanQty}"/></td>
                                                                <td><c:out value="${parameterMaster.internalCleanQty}"/></td>
                                                                <td><c:out value="${parameterMaster.internalRecleanQty}"/></td>
                                                                <td><c:out value="${parameterMaster.storageFactoryQty}"/></td>
                                                                <td><c:out value="${parameterMaster.totalQty}"/></td>
                                                                <td><c:out value="${parameterMaster.status}"/></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- Row end -->
                                    </div> 
                                    <!--end div for 1st tab-->
                                    <div class="tab-pane fade" id="twoAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <!--                                            <div class="mb-3 container-fluid justify-content-start">
                                                                                            <a href="" class="btn btn-outline-success me-2" role="button">
                                                                                                <i class='bi bi-plus-circle'></i>&nbsp;&nbsp;New Hardware ID</a>
                                                                                        </div>-->
                                            <div class="table-responsive">
                                                <table id="listHardware" class="table custom-table pending">
                                                    <thead>
                                                        <tr>
                                                            <th>Site</th>
                                                            <th>Hardware ID</th>
                                                            <th>ALU</th>
                                                            <th>MFG Date</th>
                                                            <th>RMS_Event</th>
                                                            <th>Status</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <!--end dive for second tab-->
                                    <div class="tab-pane fade" id="threeAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <!--                                            <div class="mb-3 container-fluid justify-content-start">
                                                                                            <button onclick="goToPageNewMovement();" class="btn btn-outline-success me-2">
                                                                                                <i class='bi bi-plus-circle'></i>&nbsp;&nbsp;New Movement</button>
                                                                                        </div>-->
                                            <div class="table-responsive">
                                                <table id="listMovement" class="table custom-table pending">
                                                    <thead>
                                                        <tr>
                                                            <!--<th class="col-12">Site</th>-->
                                                            <th>Item ID</th>
                                                            <th>Date</th>
                                                            <th>Movement Type</th>
                                                            <th>In</th>
                                                            <th>Out</th>
                                                            <th>ALU</th>
                                                            <th>Remarks</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <!--end dive for third tab-->
                                    <div class="tab-pane fade" id="fourAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <!--                                            <div class="mb-3 container-fluid justify-content-start">
                                                                                            <a href="https://mysed-rel-app03:8443/CDARS/wh/whShipping" target="_blank" class="btn btn-outline-success me-2" role="button">
                                                                                                <i class='bi bi-arrow-up-right-circle'></i>&nbsp;&nbsp;Request Through HIMS</a>
                                                                                            <a href="${contextPath}/hw/item/ListRetrieveSF" class="btn btn-outline-success me-2" role="button">
                                                                                                <i class='bi bi-list-task'></i>&nbsp;&nbsp;List of Recall Item</a>
                                                                                        </div>-->
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
                                                            <!--<th class="col-2">Movement Type</th>-->
                                                            <!--<th class="col-1">Action</th>-->
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <!--end dive for fourth tab-->
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

                                                                        $(document).ready(function () {
                                                                            $('.js-example-basic-single').select2();
                                                                        });

                                                                        function goToPageNewMovement() {
                                                                            if ($("#itemPKID").val()) {
                                                                                var itemPKID = $("#itemPKID").val();
                                                                                var Url = "${contextPath}/hw/item/transaction/" + itemPKID;
                                                                                window.location.href = Url;
                                                                            } else {
                                                                                alert("No Item Selected.")
                                                                            }
                                                                        }

                                                                        $(function () {
                                                                            $("#customButtons1").DataTable({
                                                                                lengthMenu: [
                                                                                    [10, 25, 50],
                                                                                    [10, 25, 50, "All"],
                                                                                ],
                                                                                scrollX: true,
                                                                                autoWidth: false,
                                                                                language: {
                                                                                    lengthMenu: "Display _MENU_ Records Per Page",
                                                                                    info: "Showing Page _PAGE_ of _PAGES_",
                                                                                },
                                                                                dom: "Blfrtip",
                                                                                buttons: ["copy", "csv", "pdf", "print"],
                                                                            });
                                                                        });

                                                                        function ajaxTrans(e) {
                                                                            var itemPKID = $(e).attr("sptsPkid");
                                                                            $('#listMovement').DataTable().destroy();
                                                                            new DataTable('#listMovement', {
                                                                                ajax: {
                                                                                    data: {itemPKID: itemPKID},
                                                                                    url: '${contextPath}/hw/item/ajaxTransactionQuery',
                                                                                    dataSrc: ''
                                                                                },
                                                                                columns: [
                                                                                    {"data": "itemId"},
                                                                                    {"data": "dateTime"},
                                                                                    {"data": "transTypeName"},
                                                                                    {"data": "transInQty"},
                                                                                    {"data": "transOutQty"},
                                                                                    {"data": "alu"},
                                                                                    {"data": "remarks"}
                                                                                ],
                                                                                lengthMenu: [
                                                                                    [10, 25, 50],
                                                                                    [10, 25, 50, "All"],
                                                                                ],
                                                                                language: {
                                                                                    lengthMenu: "Display _MENU_ Records Per Page",
                                                                                    info: "Showing Page _PAGE_ of _PAGES_",
                                                                                },
                                                                                dom: "Blfrtip",
                                                                                buttons: ["copy", "csv", "pdf", "print"],
                                                                                //                                                        processing: true,
                                                                            });
                                                                            document.querySelector('#tab-threeAAA').click();
                                                                        }

                                                                        function ajaxListHardware() {
                                                                            var itemPKID = $("#itemPKID").val();
                                                                            $('#listHardware').DataTable().destroy();
                                                                            new DataTable('#listHardware', {
                                                                                ajax: {
                                                                                    data: {itemPKID: itemPKID},
                                                                                    url: '${contextPath}/hw/item/ajaxHtmlSampleHardware',
                                                                                    dataSrc: ''
                                                                                },
                                                                                columns: [
                                                                                    {data: 'item_id'},
                                                                                    {data: 'item_name'},
                                                                                    {data: 'item_type'},
                                                                                    {data: 'assembly_id'},
                                                                                    {data: 'spts_id'},
                                                                                    {data: 'aluhrs'},
                                                                                ],
                                                                                lengthMenu: [
                                                                                    [10, 25, 50],
                                                                                    [10, 25, 50, "All"],
                                                                                ],
                                                                                language: {
                                                                                    lengthMenu: "Display _MENU_ Records Per Page",
                                                                                    info: "Showing Page _PAGE_ of _PAGES_",
                                                                                },
                                                                                dom: "Blfrtip",
                                                                                buttons: ["copy", "csv", "pdf", "print"],
                                                                            });
                                                                        }

                                                                        function ajaxStorage(e) {
                                                                            var itemPKID = $(e).attr("sptsPkid");
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
                                                                                            //                                                           {"data": "movementType"},
//                                                                {
//                                                                    data: "invId", // This column won't directly map to a data field
//                                                                    render: function (data, type, row) {
//                                                                        return '<button class="btn btn-primary edit-btn" data-id="' + data + '" data-pkid="' + itemPKID + '" data-bs-toggle="modal" data-bs-target="#confirmation_modal">Recall</button>';
//                                                                        // 'row' contains the entire data object for the current row
//
//                                                                    }
//                                                                }
                                                                                ],
                                                                                lengthMenu: [
                                                                                    [10, 25, 50],
                                                                                    [10, 25, 50, "All"],
                                                                                ],
                                                                                language: {
                                                                                    lengthMenu: "Display _MENU_ Records Per Page",
                                                                                    info: "Showing Page _PAGE_ of _PAGES_",
                                                                                },
                                                                                dom: "Blfrtip",
                                                                                buttons: ["copy", "csv", "pdf", "print"],
                                                                                //                                                        processing: true,
                                                                            });
                                                                            document.querySelector('#tab-fourAAA').click();
                                                                        }

                                                                        $('#listStorage tbody').on('click', '.edit-btn', function () {
                                                                            var rowId = $(this).data('id'); // Get the 'data-id' attribute
                                                                            var rowPkid = $(this).data('pkid'); // Get the 'data-id' attribute
                                                                            //                                                   alert('Edit button clicked for ID: ' + rowId +' Pkid: '+rowPkid);
                                                                            // Perform further actions, e.g., open a modal for editing
                                                                            //                                                   var deleteId = $(e).attr("modaldeleteid");
                                                                            //                                                   var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                                            //                                                   var deleteUrl = "${contextPath}/admin/parameterMaster/delete/" + rowId;
                                                                            if (rowId) {
                                                                                var deleteUrl = "${contextPath}/hw/item/retrieveSF/" + rowId + "/" + rowPkid;
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