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
        <!-- Date Range CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/daterange/daterange.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/css/animate.css">
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
            .select2-container--default .select2-selection--single{
                border: 1.5px solid #000;
                border-radius: 0.5rem;
                box-shadow: 2.5px 3px 0 #000;
                outline: none;
                transition: ease 0.25s;
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
            .accordion-button:not(.collapsed) {
                background-color: lightsalmon;
            }
            .accordion-button {
                background-color: #f8f9ff;
            }
            
            /* INI UNTUK TABLE MANUAL TEST SAHAJA*/
            table {
                border-collapse: collapse;
                width: 95%;
                max-width: 1500px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                border-radius: 8px;
                overflow: hidden;
            }
            th, td {
                border: 1px solid #dee2e6;
                padding: 0;
                text-align: center;
            }
            thead th {
                /*background-color: #007bff;*/
                background-color: gray;
                color: white;
                font-weight: 600;
                padding: 12px 8px;
            }
            .qty-header {
                /*background-color: #e9ecef;*/
                background-color: lightgray;
                font-weight: bold;
                color: #333;
                padding: 12px 16px;
            }
            thead tr:nth-child(2) th {
                /*background-color: #4da6ff;*/
                background-color: lightgray;
                color: white;
                font-weight: normal;
                font-size: 0.85em;
                padding: 6px 4px;
                white-space: nowrap;
            }
            tbody tr:nth-child(even) {
                background-color: #f8f9fa;
            }
            tbody tr:hover {
                background-color: #e0f7fa;
            }
            .input-wrapper {
                padding: 8px 4px 2px;
            }
            .param-cell input {
                width: 100%;
                padding: 4px;
                box-sizing: border-box;
                border: none;
                text-align: center;
                background-color: transparent;
                font-family: inherit;
                font-size: inherit;
            }
            .limits {
                font-size: 0.75em;
                color: #6c757d;
                display: block;
                padding-bottom: 4px;
            }
            .status-cell {
                background-color: #eafdea;
                padding: 4px;
            }
            select {
                padding: 6px;
                border-radius: 4px;
                border: 1px solid #ccc;
                font-size: 0.9em;
                width: 100%;
                box-sizing: border-box;
                background-color: #fff;
            }
            .status-header {
                /*background-color: #28a745 !important;*/
                background-color: lightgreen !important;
                color: white !important;
                font-weight: bold !important;
            }
            .saving {
                opacity: 0.6;
                cursor: wait;
            }
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
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${hwActive}" id="tab-oneAAA" data-bs-toggle="tab" href="#oneAAA" role="tab"
                                           aria-controls="oneAAA" aria-selected="true" ><i class="bi bi-person-badge"></i>HW Details</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${vmActive}" id="tab-twoAAA" data-bs-toggle="tab" href="#twoAAA" role="tab"
                                           aria-controls="twoAAA" aria-selected="false"><i class="bi bi-search"></i>Visual Inspection Check</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link ${teActive}" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab"
                                           aria-controls="threeAAA" aria-selected="false"><i class="bi bi-clipboard-check"></i>Functional Test</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade ${hwActiveTab}" id="oneAAA" role="tabpanel">
                                        <!--differentiate form attribute based on item type-->
                                        <c:set var="String" value="${item.itemType}"/>
                                        <c:choose>
                                            <c:when test="${(fn:contains(String, 'BIB')) || (fn:contains(String, 'DRIVER BOARD'))}">
                                                <div class="bib" id="bib">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers" value="${item.expirationDate}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Flux')) || (fn:contains(String, 'IPA')) || (fn:contains(String, 'Ionox'))}">
                                                <div class="chemical" id="chemical">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers" value="${item.expirationDate}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'ATE'))}">
                                                <div class="ate" id="ate">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemUsage" class="form-label">Item Usage</label>
                                                                <div class="input input-group">
                                                                    <select class="select-single js-states form-control" id="itemUsage" name="itemUsage" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                        <c:forEach items="${paramItemUsage}" var="invInner">
                                                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'EQP_'))}">
                                                <div class="eqp" id="eqp">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemUsage" class="form-label">Item Usage</label>
                                                                <div class="input input-group">
                                                                    <select class="select-single js-states form-control" id="itemUsage" name="itemUsage" title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                        <c:forEach items="${paramItemUsage}" var="invInner">
                                                                            <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 mt-2 mb-3">
                                                            <h6 class="fw-semibold mb-3 border-start border-warning ps-2" style="border-left-width: 3px !important;">
                                                                <i class="bi bi-chat-square-text me-2"></i>Additional Information
                                                            </h6>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="mb-3">
                                                                <label for="remarks" class="form-label">Remarks</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'FOL')) || (fn:contains(String, 'TRAY'))}">
                                                <div class="fol" id="fol">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'PCB'))}">
                                                <div class="pcb" id="pcb">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 mt-2 mb-3">
                                                            <h6 class="fw-semibold mb-3 border-start border-warning ps-2" style="border-left-width: 3px !important;">
                                                                <i class="bi bi-chat-square-text me-2"></i>Additional Information
                                                            </h6>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="mb-3">
                                                                <label for="remarks" class="form-label">Remarks</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Solder Paste'))}">
                                                <div class="solderPaste" id="solderPaste">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers" value="${item.expirationDate}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Stencil'))}">
                                                <div class="stencil" id="stencil">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="initial" id="initial">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input input-group">
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input input-group">
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input input-group">
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty" placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers" value="${item.expirationDate}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="input form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="input form-control" id="remarks" name="remarks" placeholder="Enter Message" rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div> 
                                    <div class="tab-pane fade ${vmActiveTab}" id="twoAAA" role="tabpanel">
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
                                                                        <a class="form-label" href="${contextPath}/hw/item/vm/downloadAttach/${itemVm.id}/winConnector" id="winConnectorAttach" name="winConnectorAttach"> Download Attachment</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Form actions start -->
                                                <div class="col-md-12">
                                                    <button type="submit" id="submitVm" name="submitVm" class="btn btn-primary float-end">Save</button>
                                                    <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                </div>
                                                <!-- Form actions end -->
                                            </form>
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <div class="tab-pane fade ${teActiveTab}" id="threeAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <div class="col-12">
                                                <div class="col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <div class="accordion" id="accordionPanelsStayOpenExample">
                                                                <c:if test="${bibCheck eq 'Yes'}">
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                                                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false" aria-controls="panelsStayOpen-collapseOne">
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
                                                                                            <select class="select-single js-states form-control" id="bibResult" name="bibResult"
                                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%" required>
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
                                                                <c:if test="${manCheck eq 'Yes'}">
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseTwo">
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
                                                                                        <!--<a href="http://zbqb9x-7jwwld4:85//Tutorial/sample-heat/manual_test.php?id=${item.id}" class="leads rounded-3 d-xxl-flex d-none">-->
                                                                                            <i class="bi bi-box-arrow-right" style="color:#ffffff"></i>&nbsp;&nbsp;Inspect Manual Test
                                                                                        </a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                </c:if>
                                                                <c:if test="${leakCheck eq 'Yes'}">
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseThree">
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
                                                                                            <select class="select-single js-states form-control" id="leakResult" name="leakResult"
                                                                                                    title="Select Leakage Result" data-live-search="true" style="width: 100%" required>
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
                                                                <c:if test="${psCheck eq 'Yes'}">
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingFour">
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseFour" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseFour">
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
                                                                                            <select class="select-single js-states form-control" id="psResult" name="psResult"
                                                                                                    title="Select Leakage Result" data-live-search="true" style="width: 100%" >
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
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseFive" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseFive">
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
                                                                                            <select class="select-single js-states form-control" id="winResult" name="winResult"
                                                                                                    title="Select Winchester Chamber Leakage Result" data-live-search="true" style="width: 100%" required>
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
                                                    </div>
                                                    <div class="col-md-12">
                                                        <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <!--end dive for third tab-->
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

        const bibResult = document.getElementById('bibResult');

        bibResult.required = true;

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
            var element = $('#itemTypeRead');
            if (element.val()) {
                $("#submit").attr("disabled", true);
            } else {
                $("#submit").removeAttr('disabled');
            }

            var element2 = $('#viId');
            if (element2.val()) {
                $("#submitVm").attr("disabled", true);
            }
        });
    </script>
</s:layout-component>
</s:layout-render>