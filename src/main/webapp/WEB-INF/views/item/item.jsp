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
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module</h5>
                            <div class="float-end">
                                <a href="${contextPath}/hw/hardware" class="leads rounded-5">
                                    <i class='bi bi-plus-square' style='color:#ffffff' ></i>&nbsp;&nbsp;Add New
                                </a>
                            </div>
                        </div>

                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/hw/item" method="post">
                                <div class="row mb-5">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Item Type</label>
                                    <div class="col-sm-3 col-md-3">
                                        <div class="row g-1">
                                            <div class="col-sm-11 col-md-12">
                                                <select class="select-single js-states form-control" id="itemType" name="itemType"
                                                        title="Select Item Type" data-live-search="true">
                                                    <option></option>
                                                    <c:forEach items="${itemTpeAll}" var="invInner">
                                                        <option value="${invInner.ItemType}">${invInner.ItemType}</option>
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

                <div class="col-sm-4 col-12">

                    <!-- Card start -->
                    <div class="card mb-4">
                        <!--                        <div class="card-header">
                                                    <h5 class="card-title">Form Layout</h5>
                                                </div>-->
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-list-ul me-2"></i>List of HW ${itemTypeTitle}
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="scrollVertical2" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <!--<th class="col-12">Site</th>-->
                                                    <th>Item Type</th>
                                                    <th>Sub Type</th>
                                                    <th class="col-12">Item ID</th>
                                                    <th>item Name</th>
                                                    <!--<th>Assembly ID</th>-->
                                                    <!--<th>Total Qty</th>-->
                                                    <th>Status</th>
                                                    <th>Detail</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${itemList}" var="request" varStatus="requestLoop">
                                                    <tr>
                                                        <td><c:out value="${request.itemType}"/></td>
                                                        <td><c:out value="${request.subType}"/></td>
                                                        <td><c:out value="${request.itemId}"/></td>
                                                        <td><c:out value="${request.itemName}"/></td>
                                                        <!--<td><c:out value="${request.assemblyId}"/></td>-->
                                                        <!--<td><c:out value="${request.totalQty}"/></td>-->
                                                        <td><c:out value="${request.status}"/></td>
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
                                <ul class="fs-6 nav nav-tabs justify-content-center" id="customTab4" role="tablist">
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link active" id="tab-oneAAA" data-bs-toggle="tab" href="#oneAAA" role="tab"
                                           aria-controls="oneAAA" aria-selected="true"><i class="bi bi-person-badge"></i>HW Details</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link" id="tab-twoAAA" data-bs-toggle="tab" href="#twoAAA" role="tab"
                                           aria-controls="twoAAA" aria-selected="false" onclick="ajaxHw();"><i class="bi bi-list-task"></i>List of HW ID</a>
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
                                        <!--differentiate form attribute based on item type-->
                                        <c:set var="String" value="${itemTypeTitle}"/>
                                        <c:choose>
                                            <c:when test="${(fn:contains(String, 'BIB')) || (fn:contains(String, 'DRIVER BOARD'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <!--                                                    <div class="col-12 mb-3">
                                                                                                            <h6 class="fw-semibold mb-3 border-start border-info ps-2"
                                                                                                                style="border-left-width: 3px !important;">
                                                                                                                <i class="bi bi-person-badge me-2"></i>HW Details
                                                                                                            </h6>
                                                                                                        </div>-->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="">
                                                                <input type="text" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Flux')) || (fn:contains(String, 'IPA')) || (fn:contains(String, 'Ionox'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/hardware/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'ATE'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/hardware/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'EQP_'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="testItem" class="form-label">TEST DROPDOWN</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="testItem" name="testItem" value="">
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'FOL')) || (fn:contains(String, 'TRAY'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'PCB'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Solder Paste'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:when test="${(fn:contains(String, 'Stencil'))}">
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:when>
                                            <c:otherwise>
                                                <!-- Card start -->
                                                <!--<div class="card mb-4">-->
                                                <!--<div class="card-body">-->
                                                <form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="" required="">
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
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
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="" disabled>
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
                                                                <input type="text" class="form-control" id="model" name="model" value="">
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
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="">
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

                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="">
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
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="" disabled>
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
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                    <!-- Form actions end -->
                                                </form>
                                                <!--</div>-->
                                                <!--</div>-->
                                                <!-- Card end -->
                                            </c:otherwise>
                                        </c:choose>
                                        <!--end of differentiate form attribute based on item type-->
                                    </div> 
                                    <!--end div for 1st tab-->
                                    <div class="tab-pane fade" id="twoAAA" role="tabpanel">
                                        <!-- Row start -->
                                        <div class="row gx-4">
                                            <div class="table-responsive">
                                                <table id="customButtons" class="table custom-table pending">
                                                    <thead>
                                                        <tr>
                                                            <!--<th class="col-12">Site</th>-->
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

                                                $(function () {
                                                    $("#scrollVertical4").DataTable({
                                                            scrollY: "150px",
                                                            scrollCollapse: false,
                                                            paging: false,
                                                            bInfo: false,
                                                    });
                                                });

                                               function modalDelete(e) {
                                                   var pkId = $(e).attr("modaldeleteid");
                                                   $.ajax({
                                                       url: '${contextPath}/hw/item/detail', // Replace with your controller URL
                                                       type: 'GET',
                                                       data: {pkID: pkId},
                                                       dataType: 'json',
                                                       success: function (data) {
                                                           
                                                           var tableBody = $('#customButtons tbody');
                                                           tableBody.empty(); // Clear existing table rows
//                                                           $( "#nav-item" ).tabs( "option", "active", 0 );
//                                                           $( "#tab-oneAAA" ).tabs({ active: 1});
                                                           // Populate form fields with received data
                                                           $("#itemType2").val(data.itemType);
                                                           $("#subType").val(data.subType);
                                                           $("#itemPKID").val(data.id);
                                                           $("#itemId").val(data.itemId);
                                                           $("#itemName").val(data.itemName);
                                                           $("#aluHrs").val(data.aluHrs);

                                                           $("#assemblyId").val(data.assemblyId);
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
                                                           $("#isConsumable").val(data.isConsumeable);
                                                           $("#downtimeValue").val(data.downtimeValue);
                                                           $("#downtimeUnit").val(data.downtimeUnit);
                                                           $("#implementationCost").val(data.implementationCost);
                                                           $("#manpowerValue").val(data.manpowerValue);
                                                           $("#manpowerUnit").val(data.manpowerUnit);
                                                           $("#complexity").val(data.complexity);
                                                           $("#model").val(data.model);
                                                           $("#manufacturer").val(data.manufacturer);
                                                           $("#equipmentType").val(data.equipmentType);
                                                           $("#equipmentModel").val(data.equipmentModel);
                                                           $("#equipmentManufacturer").val(data.equipmentManufacturer);
                                                           $("#stressType").val(data.stressType);
                                                           $("#remarks").val(data.remarks);

                                                           $("#onHandQty").val(data.onHandQty);
                                                           $("#productionStagingQty").val(data.productionStagingQty);
                                                           $("#productionQty").val(data.productionQty);
                                                           $("#repairQty").val(data.repairQty);
                                                           $("#otherQty").val(data.otherQty);
                                                           $("#quarantineQty").val(data.quarantineQty);
                                                           $("#externalCleanQty").val(data.externalCleanQty);
                                                           $("#externalRecleanQty").val(data.externalRecleanQty);
                                                           $("#internalCleanQty").val(data.internalCleanQty);
                                                           $("#internalRecleanQty").val(data.internalRecleanQty);
                                                           $("#storageFactoryQty").val(data.storageFactoryQty);
                                                           $("#otherOnsemiQty").val(data.otherONQty);
                                                           $("#vendorQty").val(data.vendorQty);
                                                           $("#totalQty").val(data.totalQty);
                                                           
                                                            const data001 = 'DTS';
                                                            const myDropdown = document.getElementById("itemUsage");
                                                            const selectedValue = myDropdown.value;

                                                            console.log("AHOI > " + selectedValue);
                                                            console.log("data001 >>> " + data001);
                                                            
                                                            for (let i = 0; i < myDropdown.options.length; i++) {
                                                                if (myDropdown.options[i].value === data001) {
                                                                    console.log("MASUK SELECTED " + myDropdown.options[i].value);
                                                                    myDropdown.options[i].selected = true;
                                                                    break; // Exit the loop once the option is found and selected
                                                                } else {
                                                                    console.log("YANG LAIN : " + myDropdown.options[i].value);
                                                                }
                                                              }
                                                              
                                                              // Get the selected option's index
                                                                const selectedIndex = myDropdown.selectedIndex;

                                                                // Get the text content of the selected option
                                                                const selectedText = myDropdown.options[selectedIndex].text;
                                                                
                                                                console.log("LAST CHECK >> " + selectedText);
                                                            
                                                            $("#testItem").val(data001);
                                                           
                                                       },
                                                       error: function (jqXHR, textStatus, errorThrown) {
                                                           console.error("Error loading data: " + textStatus, errorThrown);
                                                       }
                                                   });
                                                   document.querySelector('#tab-oneAAA').click();
                                               }

                                               function ajaxHw() {
                                                   var itemPKID = $("#itemPKID").val();
//                                                   alert($("#itemPKID").val());
                                                   $.ajax({
                                                       url: '${contextPath}/hw/item/hardwareList', // Replace with your controller URL
                                                       type: 'GET',
                                                       data: {itemPKID: itemPKID},
                                                       dataType: 'json',
                                                       success: function (data) {
                                                           // Populate form fields with received data
                                                           var tableBody = $('#customButtons tbody');
                                                           tableBody.empty(); // Clear existing table rows

                                                           $.each(data, function (index, item) {
                                                               var row = '<tr>' +
                                                                       '<td>' + item.hardwareName + '</td>' +
                                                                       '<td>' + item.alu + '</td>' +
                                                                       '<td>' + item.mfgDate + '</td>' +
                                                                       '<td>' + item.rmsEvent + '</td>' +
                                                                       '<td>' + item.status + '</td>' +
                                                                       '</tr>';
                                                               tableBody.append(row);
                                                           });
                                                       },
                                                       error: function (jqXHR, textStatus, errorThrown) {
                                                           console.error("Error loading data: " + textStatus, errorThrown);
                                                       }
                                                   });
                                               }

    </script>
</s:layout-component>
</s:layout-render>