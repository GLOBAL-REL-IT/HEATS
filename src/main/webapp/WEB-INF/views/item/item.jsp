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
        <!--<link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/daterange/daterange.css">-->

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
                        <c:if test="${userItemAdd == 'Yes'}"><a href="${contextPath}/hw/item/add" class="btn btn-outline-success me-2" role="button">
                                <i class='bi bi-plus-square'></i>&nbsp;&nbsp;Add New</a></c:if>
                        <a href="${contextPath}/hw/item/pending" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-hourglass'></i>&nbsp;&nbsp;Pending VM/Functional Test</a>
                        <a href="${contextPath}/hw/item/query" class="btn btn-outline-success me-2" role="button">
                            <i class='bi bi-search'></i>&nbsp;&nbsp;Query</a>

                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/hw" method="post">
                                <div class="row mb-3">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Item Type</label>
                                    <div class="col-sm-3 col-md-3">
                                        <div class="row g-1">
                                            <div class="col-sm-11 col-md-12">
                                                <select class="js-example-basic-single" id="itemType" name="itemType"
                                                        style="width: 100%" onchange="toggleLinkVisibility()">
                                                    <option></option>
                                                    <c:forEach items="${itemTpeAll}" var="invInner">
                                                        <option value="${invInner.ItemType}">${invInner.ItemType}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" class="form-control" id="userItemEdit" name="userItemEdit" placeholder="" value="${userItemEdit}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-primary">Fetch</button>
                                        <span id="linkContainer" style="display:none;">
                                            <a href="${contextPath}/hw/item2" class="btn btn-outline-success me-2" role="button" id="myLink">
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
                                           aria-controls="twoAAA" aria-selected="false" onclick="ajaxListHardware();"><i class="bi bi-list-task"></i>List of HW ID</a>
                                    </li>
                                    <li class="nav-item" role="presentation" style="border:1px; border-right-style: ridge;">
                                        <a class="nav-link" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab"
                                           aria-controls="threeAAA" aria-selected="false" onclick="ajaxTrans();"><i class="bi bi-arrow-left-right"></i>Movement</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link" id="tab-fourAAA" data-bs-toggle="tab" href="#fourAAA" role="tab"
                                           aria-controls="fourAAA" aria-selected="false" onclick="ajaxStorage();"><i class="bi bi-house-door"></i>Storage Factory</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade show active" id="oneAAA" role="tabpanel">
                                        <form class="row gx-3 needs-validation" role="form" action="${contextPath}/hw/item/update" method="post" novalidate>
                                            <c:if test="${not empty itemTypeTitle}"><div class="mb-3 container-fluid justify-content-start">
                                                    <c:if test="${userItemDelete == 'Yes'}"><a onclick="scrapModal();" role="button" title="Scrap" data-bs-toggle="modal" data-bs-target="#delete_modal" class="btn btn-outline-danger me-2">
                                                            <i class="bi bi-trash3" style="color:red"></i>&nbsp;&nbsp;Scrap</a></c:if>
                                                    <c:if test="${itemTypeForBib == 'BIB' || itemTypeForBib == 'BIB Card'}">
                                                        <c:if test="${userItemActConfig == 'Yes'}">
                                                           <a class="btn btn-outline-success me-2" role="button" onclick="goToActivityConfig()"><i class='bi bi-sliders2'></i>&nbsp;&nbsp;Add/Edit Activity config</a> 
                                                        </c:if>
                                                    </c:if>
                                                </div></c:if>
                                            <input type="hidden" class="form-control" id="configMibItemId" name="configMibItemId" value="${item.configMibItemId}" readonly> <!--for activity config button -->
                                            <input type="hidden" class="form-control" id="activityConfigId" name="activityConfigId" value="${item.activityConfigId}" readonly> <!--for activity config button -->
                                            <!--differentiate form attribute based on item type-->
                                            <c:set var="String" value="${itemTypeTitle}"/>
                                            <c:choose>
                                                <c:when test="${(fn:contains(String, 'BIB')) || (fn:contains(String, 'DRIVER BOARD'))}">
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="expirationDate" class="form-label">Expiration Date</label>
                                                            <div class="input input-group">
                                                                <span class="input-group-text">
                                                                    <i class="bi bi-calendar4"></i>
                                                                </span>
                                                                <!--<input type="date" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers">--> 
                                                                <input type="date" id="expirationDate" name="expirationDate" class="form-control" data-date-format="yyyy-mm-dd" value="${item.expirationDate}"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                        <!--                                                        <button type="submit" class="btn btn-primary">Update</button>-->
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'Flux')) || (fn:contains(String, 'IPA')) || (fn:contains(String, 'Ionox'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/hardware/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <!--<input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="expirationDate" class="form-label">Expiration Date</label>
                                                            <div class="input input-group">
                                                                <span class="input-group-text">
                                                                    <i class="bi bi-calendar4"></i>
                                                                </span>
                                                                <!--<input type="date" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers" required>--> 
                                                                <input type="date" id="expirationDate" name="expirationDate" class="form-control" data-date-format="yyyy-mm-dd" value="${item.expirationDate}" required> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'ATE'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/hardware/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <!--<input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemUsage" class="form-label">Item Usage</label>
                                                            <div class="input input-group">
                                                                <select class="form-control" id="itemUsage" name="itemUsage"
                                                                        title="Select Item Usage" data-live-search="true" required>
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
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'EQP_'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <!--<input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemUsage" class="form-label">Item Usage</label>
                                                            <div class="input input-group">
                                                                <select class="form-control" id="itemUsage" name="itemUsage"
                                                                        title="Select Item Usage" data-live-search="true" required>
                                                                    <option></option>
                                                                    <c:forEach items="${paramItemUsageEqpt}" var="invInner">
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
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'FOL')) || (fn:contains(String, 'TRAY'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
<!--                                                                <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'PCB'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <!--<input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'Solder Paste'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
<!--                                                                <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="expirationDate" class="form-label">Expiration Date</label>
                                                            <div class="input input-group">
                                                                <span class="input-group-text">
                                                                    <i class="bi bi-calendar4"></i>
                                                                </span>
                                                                <!--<input type="date" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers" required>--> 
                                                                <input type="date" id="expirationDate" name="expirationDate" class="form-control" data-date-format="yyyy-mm-dd" value="${item.expirationDate}" required> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:when test="${(fn:contains(String, 'Stencil'))}">
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <!--<input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="visually-hidden col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                    <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Enter Message"
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <div class="d-flex justify-content-end gap-2">
                                                    </div>
                                                    <!-- Form actions end -->
                                                    <!--</form>-->
                                                    <!--form disable 5/3/26-->
                                                    <!-- Card end -->
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Card start -->
                                                    <!--<div class="card mb-4">-->
                                                    <!--<div class="card-body">-->
                                                    <!--<form class="row gx-3 " role="form" action="${contextPath}/hw/item/update" method="post">-->
                                                    <!-- Row start -->
                                                    <div class="form-group required col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <!--<div class="mb-3 was-validated">-->
                                                            <label for="itemType2" class="form-label">Item Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="" value="${item.itemType}" readonly required>
                                                                <!--<div class="valid-feedback">Looks good!</div>-->
                                                                <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="subType" class="form-label">Sub Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <!--<input type="text" class="input form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>-->
                                                                <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="${item.sptsPkid}" readonly>
                                                                <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
                                                                <select class="js-example-tags" id="subType" name="subType"
                                                                        style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listSubType}" var="invInner">
                                                                        <option value="${invInner.subType}">${invInner.subType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemId" class="form-label">Item ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="input form-control" id="itemId" name="itemId" placeholder="" value="${item.itemId}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="itemName" class="form-label">Item Name</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <input type="text" class="input form-control" id="itemName" name="itemName" placeholder="" value="${item.itemName}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="status" class="form-label">Status</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <input type="text" class="input form-control" id="status" name="status" placeholder="" value="${item.status}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="visually-hidden col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="aluHrs" class="form-label">ALU</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="number" step="0.01" class="input form-control" id="aluHrs" name="aluHrs" placeholder="" value="${item.aluHrs}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="assemblyId" class="form-label">Assembly ID</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="input js-example-tags" id="assemblyId" name="assemblyId"
                                                                        title="Select AssemblyId" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listAssemblyId}" var="invInner">
                                                                        <option value="${invInner.assemblyId}" ${invInner.selected}>${invInner.assemblyId}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="model" class="form-label">Spare Part Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="input js-example-tags" id="model" name="model"
                                                                        title="Select Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listModel}" var="invInner">
                                                                        <option value="${invInner.model}" ${invInner.selected}>${invInner.model}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="manufacturer" class="form-label">Spare Part Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="input js-example-tags" id="manufacturer" name="manufacturer"
                                                                        title="Select Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listManufacturer}" var="invInner">
                                                                        <option value="${invInner.manufacturer}" ${invInner.selected}>${invInner.manufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="unitCost" class="form-label">Unit cost (USD)</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <input type="text" class="input form-control" id="unitCost" name="unitCost" placeholder="" value="${item.unitCost}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentType" class="form-label">Equipment Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                                                <select class="input js-example-tags" id="equipmentType" name="equipmentType"
                                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptType}" var="invInner">
                                                                        <option value="${invInner.equipmentType}" ${invInner.selected}>${invInner.equipmentType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentModel" class="form-label">Equipment Model</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                                                <select class="input js-example-tags" id="equipmentModel" name="equipmentModel"
                                                                        title="Select Eqpt Model" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptModel}" var="invInner">
                                                                        <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="equipmentManufacturer" class="form-label">Eqpt Manufacturer</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                                                <select class="input js-example-tags" id="equipmentManufacturer" name="equipmentManufacturer"
                                                                        title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listEqptManufacturer}" var="invInner">
                                                                        <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="minQty" class="form-label">Min. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="minQty" name="minQty"
                                                                       placeholder="" value="${item.minQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-1 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="maxQty" class="form-label">Max. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="maxQty" name="maxQty"
                                                                       placeholder="" value="${item.maxQty}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="rack" class="form-label">Rack</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="rack" name="rack"
                                                                       placeholder="" value="${item.rack}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-3 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="shelf" class="form-label">Shelf</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="shelf" name="shelf"
                                                                       placeholder="" value="${item.shelf}" readonly required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group required col-xl-4 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="stressType" class="form-label">Stress Type</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <select class="input js-example-tags" id="stressType" name="stressType"
                                                                        title="Select Stress Type" data-live-search="true" style="width: 100%" readonly>
                                                                    <option></option>
                                                                    <c:forEach items="${listStressType}" var="invInner">
                                                                        <option value="${invInner.stressType}" ${invInner.selected}>${invInner.stressType}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="onHandQty" class="form-label">On Hand Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="onHandQty" name="onHandQty"
                                                                       placeholder="" value="${item.onHandQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionQty" class="form-label">Prod. Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="productionQty" name="productionQty"
                                                                       placeholder="" value="${item.productionQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="productionStagingQty" class="form-label">Prod. Staging Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="productionStagingQty" name="productionStagingQty"
                                                                       placeholder="" value="${item.productionStagingQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="repairQty" class="form-label">Repair Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="repairQty" name="repairQty"
                                                                       placeholder="" value="${item.repairQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="quarantineQty" class="form-label">Quarantine Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="quarantineQty" name="quarantineQty"
                                                                       placeholder="" value="${item.quarantineQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalCleanQty" class="form-label">Ext Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="externalCleanQty" name="externalCleanQty"
                                                                       placeholder="" value="${item.externalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="externalRecleanQty" class="form-label">Ext Re-clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="externalRecleanQty" name="externalRecleanQty"
                                                                       placeholder="" value="${item.externalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalCleanQty" class="form-label">Int. Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="internalCleanQty" name="internalCleanQty"
                                                                       placeholder="" value="${item.internalCleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="internalRecleanQty" class="form-label">Int. Re-Clean Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="internalRecleanQty" name="internalRecleanQty"
                                                                       placeholder="" value="${item.internalRecleanQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherQty" class="form-label">Other Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="otherQty" name="otherQty"
                                                                       placeholder="" value="${item.otherQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="vendorQty" class="form-label">Vendor Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="vendorQty" name="vendorQty"
                                                                       placeholder="" value="${item.vendorQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div><div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="otherOnsemiQty" class="form-label">Other onsemi Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="otherOnsemiQty" name="otherOnsemiQty"
                                                                       placeholder="" value="${item.otherOnsemiQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="storageFactoryQty" class="form-label">Storage Factory Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="storageFactoryQty" name="storageFactoryQty"
                                                                       placeholder="" value="${item.storageFactoryQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="totalQty" class="form-label">Total Qty</label>
                                                            <div class="input input-group">
                                                                <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                                                <input type="text" class="input form-control" id="totalQty" name="totalQty"
                                                                       placeholder="" value="${item.totalQty}" readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="expirationDate" class="form-label">Expiration Date</label>
                                                            <div class="input input-group">
                                                                <span class="input-group-text">
                                                                    <i class="bi bi-calendar4"></i>
                                                                </span>
                                                                <!--<input type="date" id="expirationDate" name="expirationDate" class="form-control datepicker-week-numbers">--> 
                                                                <input type="date" id="expirationDate" name="expirationDate" class="form-control" data-date-format="yyyy-mm-dd" value="${item.expirationDate}"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xl-2 col-sm-12 col-12">
                                                        <div class="mb-3">
                                                            <label for="isConsumable" class="form-label">Consumable?</label>
                                                            <div class="input-group form-check form-switch">
                                                                <input class="form-check-input" type="checkbox" id="isConsumable" name="isConsumable" <c:if test="${item.isConsumable == 'true'}">checked</c:if> disabled>
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
                                                                              rows="3" readonly >${item.remarks}</textarea>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Form actions start -->
                                                    <!--<div class="d-flex justify-content-end gap-2">-->
                                                    <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                    <!--                                                        <button type="submit" class="btn btn-primary">Update</button>-->
                                                    <!--</div>-->
                                                    <!-- Form actions end -->
                                                    <!--</form>-->  
                                                    <!--disable 5/3/26-->
                                                    <!--</div>-->
                                                    <!--</div>-->
                                                    <!-- Card end -->
                                                </c:otherwise>
                                            </c:choose>
                                            <!--end of differentiate form attribute based on item type-->
                                            <!-- Form actions start -->
                                            <div class="d-flex justify-content-end gap-2">
                                                <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                                <c:if test="${userItemEdit == 'Yes'}"><button type="submit" class="btn btn-primary">Update</button></c:if>
                                                </div>
                                                <!-- Form actions end -->
                                            </form>
                                        </div> 
                                        <!--end div for 1st tab-->
                                        <div class="tab-pane fade" id="twoAAA" role="tabpanel">
                                            <!-- Row start -->
                                            <div class="row gx-4">
                                                <div class="mb-3 container-fluid justify-content-start">
                                                <c:if test="${userItemHwAdd == 'Yes'}">
                                                    <a class="btn btn-outline-success me-2" role="button" onclick="createHardwareID()"><i class='bi bi-plus-circle'></i>&nbsp;&nbsp;New Hardware ID</a>
                                                    <!--<a id="btnNewHWID" class="btn btn-outline-success me-2" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom"><i class='bi bi-plus-circle'></i>&nbsp;&nbsp;New Hardware ID-->
                                                    </a>
                                                    <input type="hidden" name="hwconfig" id="hwconfig" value="${item.activityId}">
                                                </c:if>
                                            </div>
                                            <div class="table-responsive">
                                                <table id="listHardware" class="table custom-table pending">
                                                    <thead>
                                                        <tr>
                                                            <th>Hardware ID</th>
                                                            <th>ALU</th>
                                                            <th>Shelf Time</th>
                                                            <th>Status</th>
                                                            <th>Manage</th>
<!--                                                            <th>RMS_Event</th>
                                                            <th>Flag</th>-->
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
                                            <div class="mb-3 container-fluid justify-content-start">
                                                <c:if test="${userItemMovement == 'Yes'}"><button onclick="goToPage();" class="btn btn-outline-success me-2">
                                                        <i class='bi bi-plus-circle'></i>&nbsp;&nbsp;New Movement</button></c:if>
                                                </div>
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
                                                <div class="mb-3 container-fluid justify-content-start">
                                                    <a href="https://mysed-rel-app03:8443/CDARS/wh/whShipping" target="_blank" class="btn btn-outline-success me-2" role="button">
                                                        <i class='bi bi-arrow-up-right-circle'></i>&nbsp;&nbsp;Request Through HIMS</a>
                                                    <a href="${contextPath}/hw/item/ListRetrieveSF" class="btn btn-outline-success me-2" role="button">
                                                    <i class='bi bi-list-task'></i>&nbsp;&nbsp;List of Recall Item</a>
                                            </div>
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
                                                            <th class="col-1">Action</th>
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

        <div class="offcanvas-placeholder">
            <div class="offcanvas offcanvas-bottom" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasBottomLabel">Create Hardware ID</h5>
                    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <input type="input" class="form-control" id="hui" name="hui" value="${item.sptsPkid}">
                    <input type="input" name="name" value="-">
                    <div class="col-xl-2 col-sm-12 col-12">
                        <div class="mb-3">
                            <label for="otherQty" class="form-label">Other Qty</label>
                            <div class="input input-group">
                                <input type="text" class="input form-control" id="otherQty" name="otherQty" value="${item.otherQty}">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- App Footer start -->
        <div class="app-footer">
            <img class="img3" src="${contextPath}/resources/onsemi logo.webp" alt="onsemi">
            <span>© HEATs 2025</span>
        </div>
    </div>
</s:layout-component>
<s:layout-component name="page_js">

    <!-- Date Range JS -->
<!--    <script src="${contextPath}/resources/statflow/vendor/daterange/daterange.js"></script>
   <script src="${contextPath}/resources/statflow/vendor/daterange/custom-daterange.js"></script>-->

<!--    <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
<script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>
<script src="${contextPath}/resources/vendor/DataTables/customitem/dataTables.js"></script>-->

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

//                                                    $(".datepicker-week-numbers").daterangepicker({
//                                                        singleDatePicker: true,
//                                                        showWeekNumbers: true,
////                                                        startDate: moment().startOf("hour"),
////                                                        endDate: moment().startOf("hour").add(32, "hour"),
//                                                        locale: {
//                                                            format: "YYYY-MM-DD"
//                                                        },
//                                                    });

                                                    function toggleLinkVisibility() {
                                                        var select = document.getElementById('itemType');
                                                        var container = document.getElementById('linkContainer');
                                                        var link = document.getElementById('myLink');

                                                        if (select.value !== "") {
                                                            link.href = "";
                                                            link.href = "${contextPath}/hw/item2/" + select.value;
                                                            container.style.display = 'inline'; // Show the link
//                                                            alert(link.href);
                                                        } else {
                                                            container.style.display = 'none'; // Hide the link
                                                        }
                                                    }
                                                    $(document).ready(function () {
                                                        $('.js-example-basic-single').select2();
                                                        $(".js-example-tags").select2({
                                                            tags: true
                                                        });
//                                                        $('input[readonly]').removeAttr('readonly');

                                                        if ($('#userItemEdit').val() === "Yes") {
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
//                                                            $("#onHandQty").prop("readonly", false);
//                                                            $("#productionQty").prop("readonly", false);
//                                                            $("#productionStagingQty").prop("readonly", false);
//                                                            $("#repairQty").prop("readonly", false);
//                                                            $("#quarantineQty").prop("readonly", false);
//                                                            $("#otherQty").prop("readonly", false);
//                                                            $("#vendorQty").prop("readonly", false);
//                                                            $("#otherOnsemiQty").prop("readonly", false);
//                                                            $("#externalCleanQty").prop("readonly", false);
//                                                            $("#externalRecleanQty").prop("readonly", false);
//                                                            $("#internalCleanQty").prop("readonly", false);
//                                                            $("#internalRecleanQty").prop("readonly", false);
//                                                            $("#storageFactoryQty").prop("readonly", false);
                                                            $("#stressType").prop("readonly", false);
                                                            $("#remarks").prop("readonly", false);
                                                            $("#isConsumable").prop("disabled", false);
                                                        }
                                                    });

                                                    function scrapModal() {
                                                        var itemPKID = $("#itemPKID").val();
                                                        var mibId = $("#mibId").val();
                                                        var itemId = $("#itemId").val();
//                                                        var deleteUrl = "${contextPath}/hw/delete/" + deleteId;
                                                        if (itemPKID) {
                                                            var deleteUrl = "${contextPath}/hw/item/delete/" + itemPKID + "/" + mibId;
                                                            var deleteMsg = "Are you sure want to scrap " + itemId + "?";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        } else {
                                                            var deleteUrl = "";
                                                            var deleteMsg = "No Item Selected.";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }
                                                    }

                                                    function goToPage() {
                                                        if ($("#itemPKID").val()) {
                                                            var itemPKID = $("#itemPKID").val();
                                                            var Url = "${contextPath}/hw/item/transaction/" + itemPKID;
                                                            window.location.href = Url;
                                                        } else {
                                                            alert("No Item Selected.")
                                                        }
                                                    }
                                                    function createHardwareID() {
                                                        if ($("#itemPKID").val()) {
                                                            if ($("#hwconfig").val()) {
                                                                var itemPKID = $("#itemPKID").val();
                                                                var itemType = $("#itemType2").val();
                                                                var Url = "${contextPath}/hw/hardware/" + itemPKID + "/" + itemType;
                                                                window.location.href = Url;
                                                            } else {
                                                                alert("No Hardware ID Configuration");
                                                            }
                                                        } else {
                                                            alert("No Item Selected.")
                                                        }
                                                    }
                                                    
                                                    function goToActivityConfig() {
                                                        if ($("#itemPKID").val()) {
                                                            if ($("#configMibItemId").val()) { 
                                                                var activityConfigId = $("#activityConfigId").val();
                                                                var Url = "${contextPath}/admin/bibActivity/edit2/" + activityConfigId;
                                                                window.location.href = Url;
                                                            } else {
                                                                 var mibItemId = $("#mibId").val();
                                                                var Url = "${contextPath}/admin/bibActivity/add/" + mibItemId;
                                                                window.location.href = Url;
                                                            }
                                                        } else {
                                                            alert("No Item Selected.")
                                                        }
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

                                                    $(function () {
                                                        $("#scrollVertical4").DataTable({
                                                            scrollY: "150px",
                                                            scrollCollapse: false,
                                                            paging: false,
                                                            //                                                       bInfo: false,
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
                                                                // Populate form fields with received data
                                                                $("#itemType2").val(data.itemType);
                                                                $("#subType").val(data.subType).trigger('change');
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
                                                                $("#otherOnsemiQty").val(data.otherOnsemiQty);
                                                                $("#externalCleanQty").val(data.externalCleanQty);
                                                                $("#externalRecleanQty").val(data.externalRecleanQty);
                                                                $("#internalCleanQty").val(data.internalCleanQty);
                                                                $("#internalRecleanQty").val(data.internalRecleanQty);
                                                                $("#storageFactoryQty").val(data.storageFactoryQty);
                                                                $("#totalQty").val(data.totalQty);
                                                                $("#remarks").val(data.remarks);
                                                                $("#itemUsage").val(data.itemUsage);
                                                                $("#hwconfig").val(data.activityId);
                                                                $("#activityConfigId").val(data.activityConfigId);
                                                                $("#configMibItemId").val(data.configMibItemId);
                                                                $("#stressType").val(data.stressType).trigger('change');

//                                                                     const data001 = data.assemblyId;
//                                                            const myDropdown = document.getElementById("assemblyId");
//                                                            alert(myDropdown.value);
//                                                            const selectedValue = myDropdown.value;
//                                                            
//                                                            for (let i = 0; i < myDropdown.options.length; i++) {
//                                                                if (myDropdown.options[i].value === data001) {
//                                                                    myDropdown.options[i].selected = true;
//                                                                    break; // Exit the loop once the option is found and selected
//                                                                } else {
////                                                                 
//                                                                }
//                                                            }

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

                                                    function ajaxTrans() {
                                                        var itemPKID = $("#itemPKID").val();
                                                        $('#listMovement').DataTable().destroy();
                                                        new DataTable('#listMovement', {
                                                            ajax: {
                                                                data: {itemPKID: itemPKID},
                                                                url: '${contextPath}/hw/item/ajaxTransaction',
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
                                                            //                                                        processing: true,
                                                        });
                                                    }

                                                    function ajaxListHardware() {
                                                        var itemPKID = $("#itemPKID").val();
                                                        var mibItemId = $("#mibId").val();
                                                        $('#listHardware').DataTable().destroy();
                                                        new DataTable('#listHardware', {
                                                            ajax: {
                                                                data: {itemPKID: mibItemId},
                                                                url: '${contextPath}/hw/item/ajaxHtmlSampleHardware',
                                                                dataSrc: ''
                                                            },
                                                            order: [[3, 'desc']], 
                                                            columns: [
//                                                                {data: 'item_id'},
//                                                                {data: 'item_name'},
//                                                                {data: 'item_type'},
//                                                                {data: 'assembly_id'},
//                                                                {data: 'spts_id'},
//                                                                {data: 'aluhrs'},
                                                                {data: 'hardware_id'},
                                                                {data: 'alu'},
                                                                {data: 'shelf_time'},
                                                                {data: 'status'},
//                                                                {data: 'rms_event'},
//                                                                {data: 'flag'},
                                                                {
                                                                    data: "invId", // This column won't directly map to a data field
                                                                    render: function (data, type, row) {
//                                                                        if (row.status === 'Available') {
//                                                                            return `<button class="btn btn-secondary edit-btn">Verify</button> 
//                                                                                    <button class="btn btn-info edit-btn" onclick="viewRow(`+row.id+`)">Log</button>`;
                                                                        if (row.status === 'Available') {
                                                                            return `<button class="btn btn-info edit-btn" onclick="viewRow(`+row.id+`)">Log</button>
                                                                                    <button class="btn btn-light edit-btn" onclick="deleteRow(`+row.id+`)"><i class="bi bi-trash h5" style="color:red"></i></button>`;
                                                                        } else {
                                                                            return `<button class="btn btn-success edit-btn" onclick="verifyRow(`+row.id+`)">Verify</button> 
                                                                                    <button class="btn btn-info edit-btn" onclick="viewRow(`+row.id+`)">Log</button>
                                                                                    <button class="btn btn-light edit-btn" onclick="deleteRow(`+row.id+`)"><i class="bi bi-trash h5" style="color:red"></i></button>`;
                                                                        }
                                                                    }
                                                                }
                                                            ]
                                                        });
                                                    }

                                                    function verifyRow(itemPKID) {
                                                        var Url = "${contextPath}/hw/item/toverifyHardwareId/" + itemPKID;
                                                        window.location.href = Url;
                                                        // Add your delete logic here (e.g., confirmation dialog, API call)
                                                    }
                                                    
                                                    function viewRow(itemPKID) {
                                                        var Url = "${contextPath}/hw/item/deleteHardwareId/" + itemPKID;
                                                        window.location.href = Url;
                                                        // Add your delete logic here (e.g., confirmation dialog, API call)
                                                    }
                                                    
                                                    function deleteRow(itemPKID) {
                                                        alert("DELETE ITEM PKID : "+itemPKID);
                                                        var Url = "${contextPath}/hw/item/hardware/delete/" + itemPKID;
                                                        window.location.href = Url;
                                                        // Add your delete logic here (e.g., confirmation dialog, API call)
                                                    }

                                                    function ajaxStorage() {
                                                        var itemPKID = $("#itemPKID").val();
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
                                                                {
                                                                    data: "invId", // This column won't directly map to a data field
                                                                    render: function (data, type, row) {
                                                                        return '<c:if test="${userItemSfRecall == 'Yes'}"><button class="btn btn-primary edit-btn" data-id="' + data + '" data-pkid="' + itemPKID + '" data-bs-toggle="modal" data-bs-target="#confirmation_modal">Recall</button></c:if>';
                                                                        // 'row' contains the entire data object for the current row

                                                                    }
                                                                }
                                                            ],
                                                            //                                                        processing: true,
                                                        });
                                                    }

                                                    $('#listStorage tbody').on('click', '.edit-btn', function () {
                                                        var rowId = $(this).data('id'); // Get the 'data-id' attribute
                                                        var rowPkid = $(this).data('pkid'); // Get the 'data-id' attribute
                                                        // Perform further actions, e.g., open a modal for editing
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

//                                                    document.getElementById('btnNewHWID').addEventListener('click', function (e) {
//                                                        if (!$("#itemPKID").val()) {
//                                                            e.preventDefault();  // stops data API from triggering show
//                                                            e.stopPropagation(); // belt & suspenders
//                                                            alert("No Item Selected.");
//                                                        }
//                                                    });

                                                    // (Optional) Keep show.bs.offcanvas clean (no alert) if you use the click for alert
//                                                    document.getElementById('offcanvasBottom').addEventListener('show.bs.offcanvas', function (e) {
//                                                        if (!$("#itemPKID").val()) {
//                                                            e.preventDefault();
//                                                            // No alert here to avoid double prompts
//                                                        }
//                                                    });
    </script>
</s:layout-component>
</s:layout-render>