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

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">

<!--                <nav class="navbar bg-body-tertiary">
                    <form class="container-fluid justify-content-end">
                        <button class="btn btn-outline-success me-2" type="button">Pending Registration (BIB/ Bib Card)</button>
                        <button class="btn btn-sm btn-outline-secondary" type="button">Smaller button</button>
                    </form>
                </nav>-->

                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - New Registration</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/hw/item/add" method="post">
                                <div class="row mb-3">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Item Type</label>
                                    <div class="col-sm-3 col-md-3">
                                        <div class="row g-1">
                                            <div class="col-sm-11 col-md-12">
                                                <select class="select-single js-states form-control" id="itemType" name="itemType"
                                                        title="Select Item Type" data-live-search="true">
                                                    <option></option>
                                                    <c:forEach items="${paramItemType}" var="invInner">
                                                        <option value="${invInner.name}" ${invInner.selected}>${invInner.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-primary">Fetch</button>
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
                                        <a class="nav-link disabled" id="tab-twoAAA" data-bs-toggle="tab" href="#twoAAA" role="tab"
                                           aria-controls="twoAAA" aria-selected="false"><i class="bi bi-search"></i>Visual Inspection Check</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link disabled" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab"
                                           aria-controls="threeAAA" aria-selected="false"><i class="bi bi-clipboard-check"></i>Functional Test</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade show active" id="oneAAA" role="tabpanel">
                                        <!--differentiate form attribute based on item type-->
                                        <c:set var="String" value="${itemType}"/>
                                        <c:choose>
                                            <c:when test="${(fn:contains(String, 'BIB')) || (fn:contains(String, 'DRIVER BOARD'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <div class="bib" id="bib">
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" readonly>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="status" class="form-label">Status</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                                                                                                            <input type="text" class="form-control" id="status" name="status" placeholder="" value="">
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemUsage" class="form-label">Item Usage</label>
                                                                <div class="input-group">
                                                                    <select class="select-single js-states form-control" id="itemUsage" name="itemUsage"
                                                                            title="Select Item Usage" data-live-search="true">
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
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="status" class="form-label">Status</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                                                                                                            <input type="text" class="form-control" id="status" name="status" placeholder="" value="">
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemUsage" class="form-label">Item Usage</label>
                                                                <div class="input-group">
                                                                    <select class="select-single js-states form-control" id="itemUsage" name="itemUsage"
                                                                            title="Select Item Usage" data-live-search="true">
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
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="status" class="form-label">Status</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                                                                                                            <input type="text" class="form-control" id="status" name="status" placeholder="" value="">
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="status" class="form-label">Status</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                                                                                                            <input type="text" class="form-control" id="status" name="status" placeholder="" value="">
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="status" class="form-label">Status</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                                                                                                            <input type="text" class="form-control" id="status" name="status" placeholder="" value="">
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="status" class="form-label">Status</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                                                                                                            <input type="text" class="form-control" id="status" name="status" placeholder="" value="">
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                                    <form class="row gx-3 " role="form" action="${contextPath}/hw/item/save" method="post">
                                                        <!-- Row start -->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Item Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemTypeRead" name="itemTypeRead" placeholder="" value="${itemType}" readonly>
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="subType" class="form-label">Sub Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                    <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemId" class="form-label">Item ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="itemName" class="form-label">Item Name</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="aluHrs" class="form-label">ALU</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="assemblyId" class="form-label">Assembly ID</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="assemblyId" name="assemblyId" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="model" class="form-label">Spare Part Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="model2" name="model2" value="">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="number" step="0.01" class="form-control" id="unitCost" name="unitCost" placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentType" class="form-label">Equipment Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentModel" name="equipmentModel" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                    <input type="text" class="form-control" id="equipmentManufacturer" name="equipmentManufacturer" value="" >
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="minQty" class="form-label">Min. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="minQty" name="minQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="maxQty" class="form-label">Max. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="maxQty" name="maxQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="rack" class="form-label">Rack</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="rack" name="rack"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="shelf" class="form-label">Shelf</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="shelf" name="shelf"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="stressType" class="form-label">Stress Type</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="text" class="form-control" id="stressType" name="stressType"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="onHandQty" name="onHandQty"
                                                                           placeholder="" value="" required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionQty" class="form-label">Prod. Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionQty" name="productionQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="repairQty" class="form-label">Repair Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="repairQty" name="repairQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherQty" class="form-label">Other Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherQty" name="otherQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="vendorQty" name="vendorQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div><div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                                <div class="input-group">
                                                                    <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                    <input type="number" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                           placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                                                                                    <div class="mb-3">
                                                                                                                        <label for="totalQty" class="form-label">Total Qty</label>
                                                                                                                        <div class="input-group">
                                                                                                                            <span class="input-group-text"><i class="bi bi-briefcase"></i></span>
                                                                                                                            <input type="number" class="form-control" id="totalQty" name="totalQty"
                                                                                                                                   placeholder="" value="" disabled>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>-->
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="expirationDate" class="form-label">Expiration Date</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="bi bi-calendar4"></i>
                                                                    </span>
                                                                    <input type="text" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-sm-12 col-12">
                                                            <div class="mb-3">
                                                                <label for="isConsumable" class="form-label">Consumable?</label>
                                                                <div class="input-group form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" id="isConsumable" name="isConsumable">
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
                                                                              rows="3"></textarea>
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
                                                            <a href="${contextPath}/hw/item/" class="btn btn-dark float-start">Back</a>
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
                                    <div class="tab-pane fade" id="twoAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <!--                                            <div class="table-responsive">
                                                                                            <table id="customButtons" class="table custom-table pending">
                                                                                                <thead>
                                                                                                    <tr>
                                                                                                        <th class="col-12">Site</th>
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
                                                                                        </div>-->
                                        </div>
                                        <!-- Row end -->
                                    </div>
                                    <!--end dive for second tab-->
                                    <div class="tab-pane fade" id="threeAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <!--                                            <div class="table-responsive">
                                                                                            <table id="example" class="table custom-table pending">
                                                                                                <thead>
                                                                                                    <tr>
                                                                                                        <th class="col-12">Site</th>
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
                                                                                        </div>-->
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

//            $('#onHandQty').change(function () {
////                    $('#totalQty').val(parseInt($('#onHandQty').val()) + parseInt($('#productionQty').val()) + parseInt($('#productionStagingQty').val()) + parseInt($('#repairQty').val()));
//                $('#totalQty').val(parseInt($('#onHandQty').val()));
//            });
            var element = $('#itemTypeRead');
                    if (!element.val()) {
//                        alert();
                          $("#submit").attr("disabled", true);
                    } else {
                         $("#submit").removeAttr('disabled');
                    }

        });
    </script>
</s:layout-component>
</s:layout-render>