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

            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
                box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
            }

            .select2-dropdown-open .select2-choice {
                border-bottom: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open.select2-drop-above .select2-choice,
            .select2-dropdown-open.select2-drop-above .select2-choices {
                border: 1px solid $input-border-focus !important;
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
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
                /*color: var(--bs-accordion-active-color);*/
                background-color: #f06a0a;
                ;
                /*box-shadow: inset 0 calc(-1 * var(--bs-accordion-border-width)) 0 var(--bs-accordion-border-color);*/
            }
            .accordion-button {
                /*color: var(--bs-accordion-active-color);*/
                background-color: #f8f9ff;
                ;
                /*box-shadow: inset 0 calc(-1 * var(--bs-accordion-border-width)) 0 var(--bs-accordion-border-color);*/
            }

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">

                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">New Transaction/Movement</span></h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <div class="row gx-4">
                                <!--<form class="row gx-3 needs-validation" role="form" action="${contextPath}/hw/item/transaction/save" method="post" novalidate>-->
                                <form id="add_hardwarequest_form" class="row gx-3" role="form" action="${contextPath}/hw/item/transaction/save" method="post">

                                    <div class="col-12">
                                        <!--<div class="card mb-2">-->
                                        <div class="card-body">
                                            <div class="row gx-4">
                                                <div class="col-xl-2 col-sm-12 col-12">
                                                    <div class="mb-4">
                                                        <label for="itemType" class="form-label">Item Type</label>
                                                        <div class="input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                            <input type="text" class="form-control" id="itemType" name="itemType" placeholder="" value="${item.itemType}" disabled>
                                                            <input type="hidden" class="form-control" id="sptsPkid" name="sptsPkid" placeholder="" value="${item.sptsPkid}">
                                                            <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">
                                                            <input type="hidden" class="form-control" id="countAlu" name="countAlu" placeholder="" value="${countAlu}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-2 col-sm-12 col-12">
                                                    <div class="mb-4">
                                                        <label for="subType" class="form-label">Sub Type</label>
                                                        <div class="input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                            <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-4 col-sm-12 col-12">
                                                    <div class="mb-4">
                                                        <label for="itemId" class="form-label">Item ID</label>
                                                        <div class="input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                            <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-4 col-sm-12 col-12">
                                                    <div class="mb-4">
                                                        <label for="itemName" class="form-label">Item Name</label>
                                                        <div class="input-group">
                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                            <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="onHandQty">On Hand Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="onHandQty" name="onHandQty" value="${item.onHandQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="productionQty">Prod. Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="productionQty" name="productionQty" value="${item.productionQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="productionStagingQty">Prod. Staging Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty" value="${item.productionStagingQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="repairQty">Repair Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="repairQty" name="repairQty" value="${item.repairQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="quarantineQty">Quarantine Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="quarantineQty" name="quarantineQty" value="${item.quarantineQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="externalCleanQty">Ext. Clean Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty" value="${item.externalCleanQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>    
                                            </div>
                                            <div class="row mb-1">
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="externalRecleanQty">Ext. Re-clean Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty" value="${item.externalRecleanQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="internalCleanQty">Int. clean Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty" value="${item.internalCleanQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="internalRecleanQty">Int. Re-clean Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty" value="${item.internalRecleanQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="otherQty">Other Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="otherQty" name="otherQty" value="${item.otherQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="vendorQty">Vendor Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="vendorQty" name="vendorQty" value="${item.vendorQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="otherOnsemiQty">Other onsemi Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" value="${item.otherOnsemiQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row mb-2">
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="storageFactoryQty">Storage Factory Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty" value="${item.storageFactoryQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="totalQty">Total Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="totalQty" name="totalQty" value="${item.totalQty}" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xl-6 col-sm-8 col-12">
                                                <div class="mb-2">
                                                    <label class="form-label" for="transaction">Transaction/Movement</label>
                                                    <div class="m-0">
                                                        <div class="form-check form-check-inline">
                                                            <!--<input type="hidden" class="form-control" id="mibItemId" name="mibItemId" placeholder="" value="${item.id}">-->
                                                            <input class="form-check-input" type="radio" name="transaction" id="transactionNew"
                                                                   value="New" required>
                                                            <label class="form-check-label" for="transactionNew">New</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transaction" id="transactionOut"
                                                                   value="Out">
                                                            <label class="form-check-label" for="transactionOut">Out</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transaction" id="transactionReturn"
                                                                   value="Return">
                                                            <label class="form-check-label" for="transactionReturn">Return</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--<div class="col-xl-6 col-sm-8 col-12">-->
                                            <div class="col-12">
                                                <div class="mb-1">
                                                    <label class="form-label" for="transactionToFrom">Out For /Return From</label>
                                                    <div class="m-0">
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionProd"
                                                                   value="Production" required>
                                                            <label class="form-check-label" for="transactionProd">Production</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionProdStaging"
                                                                   value="Production Staging">
                                                            <label class="form-check-label" for="transactionProdStaging">Production Staging</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionRepair"
                                                                   value="Repair">
                                                            <label class="form-check-label" for="transactionRepair">Repair</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionQuarantine"
                                                                   value="Quarantine">
                                                            <label class="form-check-label" for="transactionQuarantine">Quarantine</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionExtClean"
                                                                   value="External Clean">
                                                            <label class="form-check-label" for="transactionExtClean">Ext. Clean</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionExtReclean"
                                                                   value="External Re-clean">
                                                            <label class="form-check-label" for="transactionExtReclean">Ext. Re-Clean</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionIntClean"
                                                                   value="Internal Clean">
                                                            <label class="form-check-label" for="transactionIntClean">Int. Clean</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionIntReclean"
                                                                   value="Internal Re-clean">
                                                            <label class="form-check-label" for="transactionIntReclean">Int. Re-clean</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionOther"
                                                                   value="Other">
                                                            <label class="form-check-label" for="transactionOther">Other</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionVendor"
                                                                   value="Vendor">
                                                            <label class="form-check-label" for="transactionVendor">Vendor</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionOtherOnsemi"
                                                                   value="Other Onsemi">
                                                            <label class="form-check-label" for="transactionOtherOnsemi">Other onsemi</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionStorageFactory"
                                                                   value="Storage Factory">
                                                            <label class="form-check-label" for="transactionStorageFactory">Storage Factory</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionAdjustment"
                                                                   value="Adjustment">
                                                            <label class="form-check-label" for="transactionAdjustment">Adjustment (Non Returnable)</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="mb-2">
                                                    <div class="m-0">
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionOutProdFromStaging"
                                                                   value="Out Production From Staging" required>
                                                            <label class="form-check-label" for="transactionOutProdFromStaging">To Production from Staging</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="transactionToFrom" id="transactionReturnProdToStaging"
                                                                   value="Retun Production to Staging">
                                                            <label class="form-check-label" for="transactionReturnProdToStaging">From Production to Staging</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row mb-2">
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="qty">Qty</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="number" class="form-control" id="qty" name="qty" value="" required>
                                                            <!--<div class="invalid-feedback"> Min is 1 and Max is ${item.totalQty} </div>-->
                                                        </div>
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="transactionDate">Transaction DateTime</label>
                                                <div class="col-sm-2 col-md-2">
                                                    <div class="input-group">
                                                        <span class="input-group-text">
                                                            <i class="bi bi-calendar4"></i>
                                                        </span>
                                                        <input type="text" id="transactionDate" name="transactionDate" class="form-control datepicker-time-seconds" required> 
                                                    </div>
                                                </div>
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="alu" id="aluLabel">ALU (hrs)</label>
                                                <div class="col-sm-1 col-md-1">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="alu" name="alu" value="" >
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row mb-2">
                                                <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="remarks">Remarks</label>
                                                <div class="col-sm-3 col-md-3">
                                                    <div class="row g-2">
                                                        <div class="col-sm-12">
                                                            <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <!-- Form actions start -->
                                        <div class="col-md-12">
                                            <!--<div class="justify-content-end">-->
                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                            <button type="submit" id="submitVm" name="submitVm" class="btn btn-primary float-end">Save</button>
                                            <!--</div>-->
                                            <!--<div class="justify-content-start">-->
                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                            <a href="${contextPath}/hw/${item.sptsPkid}" class="btn btn-dark float-start">Back</a>
                                            <!--</div>-->
                                        </div>
                                        <!-- Form actions end -->                    

                                        <!--</div>-->
                                    </div>


                                </form>
                            </div>
                            <!-- Row end -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">

                <div class="col-sm-12 col-12">

                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">Movement List</span></h5>
                            <!--<h5 class="card-title">Hardware Module - <span style="color:#D97D55">New Hardware Registration</span></h5>-->
                        </div>
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th><span>No</span></th>
                                                    <th><span>Date</span></th>
                                                    <th><span>Movement Type</span></th>
                                                    <th><span>In</span></th>
                                                    <th><span>Out</span></th>
                                                    <th><span>ALU</span></th>
                                                    <th><span>Remarks</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${itemList}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                <tr>
                                                    <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                <td><c:out value="${parameterMaster.dateTime}"/></td>
                                                <td><c:out value="${parameterMaster.transTypeName}"/></td>
                                                <td><c:out value="${parameterMaster.transInQty}"/></td>
                                                <td><c:out value="${parameterMaster.transOutQty}"/></td>
                                                <td><c:out value="${parameterMaster.alu}"/></td>
                                                <td><c:out value="${parameterMaster.remarks}"/></td>
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

        <!-- JQuery Validation -->
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>

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
                    dom: "Blfrtip",
                    buttons: ["copy", "csv", "pdf", "print"],
                });
            });

            $(".js-example-basic-single").select2({
                placeholder: "Choose one",
                allowClear: true
            });
            // Get references to the elements

            $(".datepicker-time-seconds").daterangepicker({
                drops: "up",
                singleDatePicker: true,
                timePicker: true,
                timePicker24Hour: true,
                startDate: moment().startOf("minute"),
                endDate: moment().startOf("hour").add(32, "hour"),
                locale: {
                    //                format: "DD/MM/YYYY hh:mm:ss A",
                    format: "yyyy-MM-DD HH:mm:ss",
                },
            });

            $(document).ready(function () {
                // Disable all radio buttons with name "myRadioGroup"
                $("input[name='transactionToFrom']").prop("disabled", true);

                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        qty: {
                            required: true,
                            min: 1,
                            max: function (value, element) {
                                // Example: If a radio button with name 'option' and value '2' is checked,
                                // the max value for 'myField' is 100. Otherwise, it's 50.
                                if ($("input[name='transaction']:checked").val() === 'Out') {
                                    if ($("input[name='transactionToFrom']:checked").val() !== 'Out Production From Staging') {
                                        return parseInt($('#onHandQty').val());
                                    } else {
                                        return parseInt($('#productionStagingQty').val());
                                    }
                                } else if ($("input[name='transaction']:checked").val() === 'Return') {
                                    if ($("input[name='transactionToFrom']:checked").val() === 'Production') {
                                        return parseInt($('#productionQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Production Staging') {
                                        return parseInt($('#productionStagingQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Repair') {
                                        return parseInt($('#repairQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Vendor') {
                                        return parseInt($('#vendorQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Other') {
                                        return parseInt($('#otherQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Quarantine') {
                                        return parseInt($('#quarantineQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'External Clean') {
                                        return parseInt($('#externalCleanQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'External Re-clean') {
                                        return parseInt($('#externalRecleanQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Internal Clean') {
                                        return parseInt($('#internalCleanQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Internal Re-clean') {
                                        return parseInt($('#internalRecleanQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Other Onsemi') {
                                        return parseInt($('#otherOnsemiQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Storage Factory') {
                                        return parseInt($('#storageFactoryQty').val());
                                    } else if ($("input[name='transactionToFrom']:checked").val() === 'Retun Production to Staging') {
                                        return parseInt($('#productionQty').val());
                                    }
                                }
                            }
                        },
                        alu: {
                            required: true
                        },
                        transactionDate: {
                            required: true
                        },
                        transaction: {
                            required: true
                        },
                        //inventory
                        transactionToFrom: {
                            required: true
                        }
                    }
                });

            });

            const transactionNew = document.getElementById('transactionNew');
            const transactionOut = document.getElementById('transactionOut');
            const transactionReturn = document.getElementById('transactionReturn');
            const transactionProd = document.getElementById('transactionProd');
            const transactionProdStaging = document.getElementById('transactionProdStaging');
            const transactionRepair = document.getElementById('transactionRepair');
            const transactionQuarantine = document.getElementById('transactionQuarantine');
            const transactionExtClean = document.getElementById('transactionExtClean');
            const transactionExtReclean = document.getElementById('transactionExtReclean');
            const transactionIntClean = document.getElementById('transactionIntClean');
            const transactionIntReclean = document.getElementById('transactionIntReclean');
            const transactionOther = document.getElementById('transactionOther');
            const transactionVendor = document.getElementById('transactionVendor');
            const transactionOtherOnsemi = document.getElementById('transactionOtherOnsemi');
            const transactionStorageFactory = document.getElementById('transactionStorageFactory');
            const transactionAdjustment = document.getElementById('transactionAdjustment');
            const transactionOutProdFromStaging = document.getElementById('transactionOutProdFromStaging');
            const transactionReturnProdToStaging = document.getElementById('transactionReturnProdToStaging');

            const onHandQty = document.getElementById('onHandQty');
            const productionQty = document.getElementById('productionQty');
            const productionStagingQty = document.getElementById('productionStagingQty');
            const repairQty = document.getElementById('repairQty');
            const quarantineQty = document.getElementById('quarantineQty');
            const externalCleanQty = document.getElementById('externalCleanQty');
            const externalRecleanQty = document.getElementById('externalRecleanQty');
            const internalCleanQty = document.getElementById('internalCleanQty');
            const internalRecleanQty = document.getElementById('internalRecleanQty');
            const otherQty = document.getElementById('otherQty');
            const vendorQty = document.getElementById('vendorQty');
            const otherOnsemiQty = document.getElementById('otherOnsemiQty');
            const storageFactoryQty = document.getElementById('storageFactoryQty');
            const totalQty = document.getElementById('totalQty');

            const alu = document.getElementById('alu');
            const aluLabel = document.getElementById('aluLabel');

            //            Function to handle the radio button change
            function handleRadioChange() {
                if (transactionProd.checked || transactionOutProdFromStaging.checked || transactionReturnProdToStaging.checked) {
                    if (${countAlu == 1}) {
                        //                    alert();
                        alu.hidden = false;
                        aluLabel.hidden = false;
                        alu.required = true;
                    } else {
                        alu.hidden = true;
                        aluLabel.hidden = true;
                    }
                } else {
                    alu.hidden = true;
                    aluLabel.hidden = true;
                }

                if (transactionNew.checked) {
                    $("input[name='transactionToFrom']").prop("checked", false);
                    $("input[name='transactionToFrom']").prop("disabled", true);
                    $("input[name='transactionToFrom']").prop("required", false);
                } else {
                    $("input[name='transactionToFrom']").prop("disabled", false);
                }

                if (transactionOut.checked) {
                    $("input[name='transactionToFrom']").prop("required", true);
                    transactionReturnProdToStaging.disabled = true;
                    if (onHandQty.value === "0") {
                        transactionProd.disabled = true;
                        transactionProdStaging.disabled = true;
                        transactionOther.disabled = true;
                        transactionVendor.disabled = true;
                        transactionQuarantine.disabled = true;
                        transactionExtClean.disabled = true;
                        transactionExtReclean.disabled = true;
                        transactionIntClean.disabled = true;
                        transactionIntReclean.disabled = true;
                        transactionOtherOnsemi.disabled = true;
                        transactionRepair.disabled = true;
                        transactionStorageFactory.disabled = true;
                        transactionAdjustment.disabled = true;
                        transactionReturnProdToStaging.disabled = true;
                    }
                    if (productionStagingQty.value === "0" || productionStagingQty.value === null || typeof productionStagingQty.value === 'undefined' || productionStagingQty.value === '') {
                        transactionOutProdFromStaging.disabled = true;
                    } else {
                        transactionOutProdFromStaging.disabled = false;
                    }

                }
                if (transactionReturn.checked) {
                    transactionAdjustment.disabled = true;
                    transactionAdjustment.checked = false;

                    transactionOutProdFromStaging.disabled = true;
                    transactionOutProdFromStaging.checked = false;

                    $("input[name='transactionToFrom']").prop("required", true);

                    if (productionQty.value === "0" || productionQty.value === null || typeof productionQty.value === 'undefined' || productionQty.value === '') {
                        transactionProd.disabled = true;
                        transactionReturnProdToStaging.disabled = true;
                    } else {
                        transactionProd.disabled = false;
                        transactionReturnProdToStaging.disabled = false;
                    }

                    if (productionStagingQty.value === "0" || productionStagingQty.value === null || typeof productionStagingQty.value === 'undefined' || productionStagingQty.value === '') {
                        transactionProdStaging.disabled = true;
                    } else {
                        transactionProdStaging.disabled = false;
                    }

                    if (repairQty.value === "0" || repairQty.value === null || typeof repairQty.value === 'undefined' || repairQty.value === '') {
                        transactionRepair.disabled = true;
                    } else {
                        transactionRepair.disabled = false;
                    }

                    if (otherQty.value === "0" || otherQty.value === null || typeof otherQty.value === 'undefined' || otherQty.value === '') {
                        transactionOther.disabled = true;
                    } else {
                        transactionOther.disabled = false;
                    }

                    if (quarantineQty.value === "0" || quarantineQty.value === null || typeof quarantineQty.value === 'undefined' || quarantineQty.value === '') {
                        transactionQuarantine.disabled = true;
                    } else {
                        transactionQuarantine.disabled = false;
                    }

                    if (externalCleanQty.value === "0" || externalCleanQty.value === null || typeof externalCleanQty.value === 'undefined' || externalCleanQty.value === '') {
                        transactionExtClean.disabled = true;
                    } else {
                        transactionExtClean.disabled = false;
                    }

                    if (externalRecleanQty.value === "0" || externalRecleanQty.value === null || typeof externalRecleanQty.value === 'undefined' || externalRecleanQty.value === '') {
                        transactionExtReclean.disabled = true;
                    } else {
                        transactionExtReclean.disabled = false;
                    }

                    if (internalCleanQty.value === "0" || internalCleanQty.value === null || typeof internalCleanQty.value === 'undefined' || internalCleanQty.value === '') {
                        transactionIntClean.disabled = true;
                    } else {
                        transactionIntClean.disabled = false;
                    }

                    if (internalRecleanQty.value === "0" || internalRecleanQty.value === null || typeof internalRecleanQty.value === 'undefined' || internalRecleanQty.value === '') {
                        transactionIntReclean.disabled = true;
                    } else {
                        transactionIntReclean.disabled = false;
                    }

                    if (otherOnsemiQty.value === "0" || otherOnsemiQty.value === null || typeof otherOnsemiQty.value === 'undefined' || otherOnsemiQty.value === '') {
                        transactionOtherOnsemi.disabled = true;
                    } else {
                        transactionOtherOnsemi.disabled = false;
                    }

                    if (vendorQty.value === "0" || vendorQty.value === null || typeof vendorQty.value === 'undefined' || vendorQty.value === '') {
                        transactionVendor.disabled = true;
                    } else {
                        transactionVendor.disabled = false;
                    }

                    if (storageFactoryQty.value === "0" || storageFactoryQty.value === null || typeof storageFactoryQty.value === 'undefined' || storageFactoryQty.value === '') {
                        transactionStorageFactory.disabled = true;
                    } else {
                        transactionStorageFactory.disabled = false;
                    }
                }
            }

            // Attach event listeners to the radio buttons
            transactionNew.addEventListener('change', handleRadioChange);
            transactionOut.addEventListener('change', handleRadioChange);
            transactionReturn.addEventListener('change', handleRadioChange);
            transactionProd.addEventListener('change', handleRadioChange);
            transactionProdStaging.addEventListener('change', handleRadioChange);
            transactionRepair.addEventListener('change', handleRadioChange);
            transactionQuarantine.addEventListener('change', handleRadioChange);
            transactionExtClean.addEventListener('change', handleRadioChange);
            transactionExtReclean.addEventListener('change', handleRadioChange);
            transactionIntClean.addEventListener('change', handleRadioChange);
            transactionIntReclean.addEventListener('change', handleRadioChange);
            transactionOther.addEventListener('change', handleRadioChange);
            transactionVendor.addEventListener('change', handleRadioChange);
            transactionOtherOnsemi.addEventListener('change', handleRadioChange);
            transactionStorageFactory.addEventListener('change', handleRadioChange);
            transactionAdjustment.addEventListener('change', handleRadioChange);
            transactionOutProdFromStaging.addEventListener('change', handleRadioChange);
            transactionReturnProdToStaging.addEventListener('change', handleRadioChange);


            // Initial state: Disable button if no radio is checked on page load
            // Or set based on a default checked radio
            alu.hidden = true;
            aluLabel.hidden = true;

            if (totalQty.value === "0") {
                transactionOut.disabled = true;
                transactionReturn.disabled = true;
            }
            if (onHandQty.value === "0") {
                transactionOut.disabled = true;
                if (!(productionStagingQty.value === "0" || productionStagingQty.value === null || typeof productionStagingQty.value === 'undefined' || productionStagingQty.value === '')) {
                    transactionOut.disabled = false;
                }
            }
            if ((productionQty.value === "0" || productionQty.value === null || typeof productionQty.value === 'undefined' || productionQty.value === '')
                    && (productionStagingQty.value === "0" || productionStagingQty.value === null || typeof productionStagingQty.value === 'undefined' || productionStagingQty.value === '')
                    && (repairQty.value === "0" || repairQty.value === null || typeof repairQty.value === 'undefined' || repairQty.value === '')
                    && (otherQty.value === "0" || otherQty.value === null || typeof otherQty.value === 'undefined' || otherQty.value === '')
                    && (quarantineQty.value === "0" || quarantineQty.value === null || typeof quarantineQty.value === 'undefined' || quarantineQty.value === '')
                    && (externalCleanQty.value === "0" || externalCleanQty.value === null || typeof externalCleanQty.value === 'undefined' || externalCleanQty.value === '')
                    && (externalRecleanQty.value === "0" || externalRecleanQty.value === null || typeof externalRecleanQty.value === 'undefined' || externalRecleanQty.value === '')
                    && (internalCleanQty.value === "0" || internalCleanQty.value === null || typeof internalCleanQty.value === 'undefined' || internalCleanQty.value === '')
                    && (internalRecleanQty.value === "0" || internalRecleanQty.value === null || typeof internalRecleanQty.value === 'undefined' || internalRecleanQty.value === '')
                    && (otherOnsemiQty.value === "0" || otherOnsemiQty.value === null || typeof otherOnsemiQty.value === 'undefined' || otherOnsemiQty.value === '')
                    && (vendorQty.value === "0" || vendorQty.value === null || typeof vendorQty.value === 'undefined' || vendorQty.value === '')
                    && (storageFactoryQty.value === "0" || storageFactoryQty.value === null || typeof storageFactoryQty.value === 'undefined' || storageFactoryQty.value === '')) {
                transactionReturn.disabled = true;
            } else {
                transactionReturn.disabled = false;
            }
            //            else {
            //                handleRadioChange(); // Set initial state based on default checked radio
            //            }

        </script>
    </s:layout-component>
</s:layout-render>