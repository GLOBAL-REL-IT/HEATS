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
            <!--            <div class="row gx-4">
                            <div class="col-sm-12 col-12">
                                 Card start 
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <h5 class="card-title">Hardware Module - New Registration</h5>
                                    </div>
                                </div>
                                 Card end 
                            </div>
                        </div>-->
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">

                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - New Registration</h5>
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
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="bib" id="bib">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty" placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty" placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty" placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty" placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty" placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
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
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Flux')) || (fn:contains(String, 'IPA')) || (fn:contains(String, 'Ionox'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="chemical" id="chemical">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
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
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                    <!--</div>-->
                                                </div>
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'ATE'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="ate" id="ate">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemUsage" class="form-label">Item Usage</label>
                                                                <div class="input-group">
                                                                    <select class="select-single js-states form-control" id="itemUsage" name="itemUsage"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'EQP_'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="eqp" id="eqp">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemUsage" class="form-label">Item Usage</label>
                                                                <div class="input-group">
                                                                    <select class="select-single js-states form-control" id="itemUsage" name="itemUsage"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
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
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'FOL')) || (fn:contains(String, 'TRAY'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="fol" id="fol">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'PCB'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="pcb" id="pcb">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Solder Paste'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="solderPaste" id="solderPaste">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
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
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Stencil'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="stencil" id="stencil">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:otherwise>
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="initial" id="initial">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}">
                                                                    <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="${item.assemblyId}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="${item.model}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="${item.manufacturer}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="${item.equipmentType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="${item.equipmentModel}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="${item.equipmentManufacturer}" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="${item.minQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="${item.maxQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="${item.rack}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="${item.shelf}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="${item.stressType}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="${item.onHandQty}" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="${item.productionQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="${item.productionStagingQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="repairQty" name="repairQty" placeholder="" value="${item.repairQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" <input type="number" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="" value="${item.quarantineQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty" placeholder="" value="${item.externalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty" placeholder="" value="${item.externalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty" placeholder="" value="${item.internalCleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty" placeholder="" value="${item.internalRecleanQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty" placeholder="" value="${item.otherQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty" placeholder="" value="${item.vendorQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty" placeholder="" value="${item.otherOnsemiQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="" value="${item.storageFactoryQty}">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="totalQty" class="form-label">Total Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                           placeholder="" value="${item.totalQty}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
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
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable" ${isConsumable}>
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
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3">${item.remarks}</textarea>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Form actions start -->
                                                        <div class="col-md-12">
                                                            <!--<div class="justify-content-end">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                            <!--</div>-->
                                                            <!--<div class="justify-content-start">-->
                                                            <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                            <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                            <!--</div>-->
                                                        </div>
                                                        <!-- Form actions end -->
                                                    </form>
                                                </div>
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:otherwise>
                                        </c:choose>
                                    </div> 
                                    <!--end div for 1st tab-->
                                    <div class="tab-pane fade ${vmActiveTab}" id="twoAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <form class="row gx-3 " role="form" action="${contextPath}/hw/item/vm/save" method="post">

                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">PCB</label>
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
                                                                <label class="col-sm-1 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="pcbReject" name="pcbReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${pcbReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Handle</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="handleReject" name="handleReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${handleReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Metal Frame</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="metalFrameReject" name="metalFrameReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${metalFrameReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Hardware Fasteners</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="hardwareFasternersReject" name="hardwareFasternersReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${hardwareFasternersReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Clip Holder</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="clipHolderReject" name="clipHolderReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${clipHolderReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">PCB Edge Finger</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="pcbEdgeFingerReject" name="pcbEdgeFingerReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${pcbEdgeFingerReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Connector</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="connectorReject" name="connectorReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${connectorReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">DUT Sockets</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="dutSocketsReject" name="dutSocketsReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${dutSocketsReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Edge MB Banana</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="edgeMbBananaReject" name="edgeMbBananaReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${edgeMbBananaReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Electronic Components</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="electComponentReject" name="electComponentReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${electComponentReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Solder Joint</label>
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
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="solderJointReject" name="solderJointReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${solderJointReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Win Connector</label>
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
                                                                <!--<label class="col-sm-2 col-md-2 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>-->
                                                                <!--<div class="form-check form-check-inline col-sm-5 col-md-5">-->
                                                                <label class="col-sm-2 col-md-2 col-12 col-form-label fw-semibold text-end" for="singleSelect">Reject Criteria</label>
                                                                <div class="form-check form-check-inline col-sm-12 col-md-5 col-12">
                                                                    <select class="select-single js-states form-control" id="winConnectorReject" name="winConnectorReject"
                                                                            title="Select Item Usage" data-live-search="true" style="width: 100%">
                                                                        <option></option>
                                                                    <c:forEach items="${winConnectorReject}" var="invInner">
                                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                    </c:forEach>
                                                                </select>
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
                                                    <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                                    <!--</div>-->
                                                </div>
                                                <!-- Form actions end -->
                                            </form>
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <!--end dive for second tab-->
                                    <div class="tab-pane fade ${teActiveTab}" id="threeAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <div class="col-12">
                                                <div class="col-12">
                                                    <div class="card mb-4">
                                                        <div class="card-body">
                                                            <div class="accordion" id="accordionPanelsStayOpenExample">
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                                                        <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true"
                                                                                aria-controls="panelsStayOpen-collapseOne">
                                                                            Bib Test
                                                                        </button>
                                                                    </h2>
                                                                    <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show"
                                                                         aria-labelledby="panelsStayOpen-headingOne">
                                                                        <div class="accordion-body">
                                                                            <form class="row gx-3 " role="form" action="${contextPath}/hw/item/bibTest/save" method="post">
                                                                                <!-- Row start -->
                                                                                <div class="col-xl-1 col-sm-12 col-12">
                                                                                    <div class="mb-3">
                                                                                        <label for="subType" class="form-label">Quantity</label>
                                                                                        <div class="input-group">
                                                                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                                            <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${item.totalQty}" readonly>
                                                                                            <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${item.id}">
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                                                    <div class="mb-3">
                                                                                        <label for="bibResult" class="form-label">BIB Result</label>
                                                                                        <div class="input-group">
                                                                                            <select class="select-single js-states form-control" id="bibResult" name="bibResult"
                                                                                                    title="Select Item Usage" data-live-search="true" style="width: 100%" required>
                                                                                                <option></option>
                                                                                                <c:forEach items="${BibPassFail}" var="invInner">
                                                                                                    <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                                                                </c:forEach>
                                                                                            </select>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group col-xl-4 col-sm-12 col-12">
                                                                                    <div class="mb-3">
                                                                                        <label for="itemId" class="form-label">Upload Result</label>
                                                                                        <div class="input-group">
                                                                                            <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                                            <input class="form-control" type="file" id="formFile">
                                                                                        </div>
                                                                                    </div>
                                                                                </div>

                                                                                <!-- Form actions start -->
                                                                                <div class="col-md-12">
                                                                                    <button type="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                                                                    <!--<a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>-->
                                                                                </div>
                                                                                <!-- Form actions end -->
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseTwo">
                                                                            Manual Test
                                                                        </button>
                                                                    </h2>
                                                                    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse"
                                                                         aria-labelledby="panelsStayOpen-headingTwo">
                                                                        <div class="accordion-body">
                                                                            <strong>Manual Test</strong>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseThree">
                                                                            Leakage Test
                                                                        </button>
                                                                    </h2>
                                                                    <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse"
                                                                         aria-labelledby="panelsStayOpen-headingThree">
                                                                        <div class="accordion-body">
                                                                            <strong>Leakage Test</strong>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="accordion-item">
                                                                    <h2 class="accordion-header" id="panelsStayOpen-headingFour">
                                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                                                data-bs-target="#panelsStayOpen-collapseFour" aria-expanded="false"
                                                                                aria-controls="panelsStayOpen-collapseFour">
                                                                            Power Supply Leakage Test
                                                                        </button>
                                                                    </h2>
                                                                    <div id="panelsStayOpen-collapseFour" class="accordion-collapse collapse"
                                                                         aria-labelledby="panelsStayOpen-headingFour">
                                                                        <div class="accordion-body">
                                                                            <strong>Power Supply Leakage Test</strong>
                                                                        </div>
                                                                    </div>
                                                                </div>
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

        // Get references to the elements


        const pcbPass = document.getElementById('pcb1');
        const pcbNa = document.getElementById('pcb3');
        const pcbFail = document.getElementById('pcb2');
        const pcbRejectCriteria = document.getElementById('pcbReject');

//        const pcbResult = document.getElementById('pcbResult');
//        const inputValue = pcbResult.value;
//        if (pcbResult.value == "Pass") {
//            pcbPass.click();
//        }

        const handlePass = document.getElementById('handle1');
        const handleNa = document.getElementById('handle3');
        const handleFail = document.getElementById('handle2');
        const handleRejectCriteria = document.getElementById('handleReject');

        const metalFramePass = document.getElementById('metalFrame1');
        const metalFrameNa = document.getElementById('metalFrame3');
        const metalFrameFail = document.getElementById('metalFrame2');
        const metalFrameRejectCriteria = document.getElementById('metalFrameReject');

        const hardwareFasternersPass = document.getElementById('hardwareFasterners1');
        const hardwareFasternersNa = document.getElementById('hardwareFasterners3');
        const hardwareFasternersFail = document.getElementById('hardwareFasterners2');
        const hardwareFasternersRejectCriteria = document.getElementById('hardwareFasternersReject');

        const clipHolderPass = document.getElementById('clipHolder1');
        const clipHolderNa = document.getElementById('clipHolder3');
        const clipHolderFail = document.getElementById('clipHolder2');
        const clipHolderRejectCriteria = document.getElementById('clipHolderReject');

        const pcbEdgeFingerPass = document.getElementById('pcbEdgeFinger1');
        const pcbEdgeFingerNa = document.getElementById('pcbEdgeFinger3');
        const pcbEdgeFingerFail = document.getElementById('pcbEdgeFinger2');
        const pcbEdgeFingerRejectCriteria = document.getElementById('pcbEdgeFingerReject');

        const connectorPass = document.getElementById('connector1');
        const connectorNa = document.getElementById('connector3');
        const connectorFail = document.getElementById('connector2');
        const connectorRejectCriteria = document.getElementById('connectorReject');

        const dutSocketsPass = document.getElementById('dutSockets1');
        const dutSocketsNa = document.getElementById('dutSockets3');
        const dutSocketsFail = document.getElementById('dutSockets2');
        const dutSocketsRejectCriteria = document.getElementById('dutSocketsReject');

        const edgeMbBananaPass = document.getElementById('edgeMbBanana1');
        const edgeMbBananaNa = document.getElementById('edgeMbBanana3');
        const edgeMbBananaFail = document.getElementById('edgeMbBanana2');
        const edgeMbBananaRejectCriteria = document.getElementById('edgeMbBananaReject');

        const electComponentPass = document.getElementById('electComponent1');
        const electComponentNa = document.getElementById('electComponent3');
        const electComponentFail = document.getElementById('electComponent2');
        const electComponentRejectCriteria = document.getElementById('electComponentReject');

        const solderJointPass = document.getElementById('solderJoint1');
        const solderJointNa = document.getElementById('solderJoint3');
        const solderJointFail = document.getElementById('solderJoint2');
        const solderJointRejectCriteria = document.getElementById('solderJointReject');

        const winConnectorPass = document.getElementById('winConnector1');
        const winConnectorNa = document.getElementById('winConnector3');
        const winConnectorFail = document.getElementById('winConnector2');
        const winConnectorRejectCriteria = document.getElementById('winConnectorReject');
        
        const bibResult = document.getElementById('bibResult');
        
        bibResult.required = true;

// Function to handle the radio button change
        function handleRadioChange() {
            if (pcbFail.checked) {
                pcbRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                pcbRejectCriteria.required = true;
            } else {
                pcbRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                pcbRejectCriteria.required = false;
            }
            if (handleFail.checked) {
                handleRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                handleRejectCriteria.required = true;
            } else {
                handleRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                handleRejectCriteria.required = false;
            }
            if (metalFrameFail.checked) {
                metalFrameRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                metalFrameRejectCriteria.required = true;
            } else {
                metalFrameRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                metalFrameRejectCriteria.required = false;
            }
            if (hardwareFasternersFail.checked) {
                hardwareFasternersRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                hardwareFasternersRejectCriteria.required = true;
            } else {
                hardwareFasternersRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                hardwareFasternersRejectCriteria.required = false;
            }
            if (clipHolderFail.checked) {
                clipHolderRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                clipHolderRejectCriteria.required = true;
            } else {
                clipHolderRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                clipHolderRejectCriteria.required = false;
            }
            if (pcbEdgeFingerFail.checked) {
                pcbEdgeFingerRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                pcbEdgeFingerRejectCriteria.required = true;
            } else {
                pcbEdgeFingerRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                pcbEdgeFingerRejectCriteria.required = false;
            }
            if (connectorFail.checked) {
                connectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                connectorRejectCriteria.required = true;
            } else {
                connectorRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                connectorRejectCriteria.required = false;
            }
            if (dutSocketsFail.checked) {
                dutSocketsRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                dutSocketsRejectCriteria.required = true;
            } else {
                dutSocketsRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                dutSocketsRejectCriteria.required = false;
            }
            if (edgeMbBananaFail.checked) {
                edgeMbBananaRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                edgeMbBananaRejectCriteria.required = true;
            } else {
                edgeMbBananaRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                edgeMbBananaRejectCriteria.required = false;
            }
            if (electComponentFail.checked) {
                electComponentRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                electComponentRejectCriteria.required = true;
            } else {
                electComponentRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                electComponentRejectCriteria.required = false;
            }
            if (solderJointFail.checked) {
                solderJointRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                solderJointRejectCriteria.required = true;
            } else {
                solderJointRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                solderJointRejectCriteria.required = false;
            }
            if (winConnectorFail.checked) {
                winConnectorRejectCriteria.disabled = false; // disable button if 'Fail' is checked
                winConnectorRejectCriteria.required = true;
            } else {
                winConnectorRejectCriteria.disabled = true;  // enable button otherwise (e.g., if 'Pass' is checked)
                winConnectorRejectCriteria.required = false;
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
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (handleFail.checked) {
            handleRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (metalFrameFail.checked) {
            metalFrameRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (hardwareFasternersFail.checked) {
            hardwareFasternersRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (clipHolderFail.checked) {
            clipHolderRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (pcbEdgeFingerFail.checked) {
            pcbEdgeFingerRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (connectorFail.checked) {
            connectorRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (dutSocketsFail.checked) {
            dutSocketsRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (edgeMbBananaFail.checked) {
            edgeMbBananaRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (electComponentFail.checked) {
            electComponentRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (solderJointFail.checked) {
            solderJointRejectCriteria.disabled = false;
        } else {
            handleRadioChange(); // Set initial state based on default checked radio
        }
        if (winConnectorFail.checked) {
            winConnectorRejectCriteria.disabled = false;
        } else {
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
//                                                       alert("itemTypeRead");
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