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
                        </div>

                        <div class="card-body">

                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/hw/hardware" method="post">
                                <div class="row mb-5">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold" for="singleSelect">Item Type</label>
                                    <div class="col-sm-3 col-md-3">
                                        <div class="row g-1">
                                            <div class="col-sm-11 col-md-12">
                                                <select class="select-single js-states form-control" id="itemType" name="itemType"
                                                        title="Select Item Type" data-live-search="true">
                                                    <option></option>
                                                    <c:forEach items="${itemTpeAll}" var="invInner">
                                                        <option value = ""></option>
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
                                        <i class="bi bi-list-ul me-2"></i>List of HW
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="scrollVertical2" class="table custom-table">
                                            <thead>
                                                <tr>
                                                    <!--<th class="col-12">Site</th>-->
                                                    <!--<th class="col-12">Item Type</th>-->
                                                    <!--<th class="col-12">Sub Type</th>-->
                                                    <th class="col-12">Item ID</th>
                                                    <!--<th class="col-12">item Name</th>-->
                                                    <!--<th class="col-12">Assembly ID</th>-->
                                                    <!--<th class="col-12">Rack</th>-->
                                                    <!--<th class="col-12">Shelf</th>-->
                                                    <!--<th class="col-12">On Hand Qty</th>-->
                                                    <!--                                                    <th class="col-12">Production Staging Qty</th>
                                                                                                        <th class="col-12">Production Qty</th>
                                                                                                         <th class="col-12">Repair Qty</th>
                                                                                                        <th class="col-12">Other Qty</th>
                                                                                                        <th class="col-12">Quarantine Qty</th>
                                                                                                        <th class="col-12">External Cleaning Qty</th>
                                                                                                        <th class="col-12">External Re-cleaning Qty</th>
                                                                                                        <th class="col-12">Internal Cleaning Qty</th>
                                                                                                         <th class="col-12">Internal Re-cleaning Qty</th>
                                                                                                        <th class="col-12">Storage Factory Qty</th>
                                                                                                        <th class="col-12">Other onsemi Qty</th>
                                                                                                        <th class="col-12">Vendor Qty</th>-->
                                                    <!--<th class="col-12">Total Qty</th>-->
                                                    <!--<th class="col-12">Unit Cost</th>-->
                                                    <!--<th class="col-12">Total Cost</th>-->
                                                    <!--<th class="col-12">Status</th>-->
                                                    <!--<th class="col-12">ALU Hrs</th>-->
                                                    <!--<th class="col-12">Movement ALU Hrs</th>-->
                                                    <!--                                                    <th class="col-12">Min Qty</th>-->
                                                    <!--<th class="col-12">Max Qty</th>-->
                                                    <!--<th class="col-12">PM WW1</th>-->
                                                    <!--<th class="col-12">PM WW2</th>-->
                                                    <!--<th class="col-12">Expiration Date</th>-->
                                                    <!--<th class="col-12">is Critical</th>-->
                                                    <!--<th class="col-12">is Consumable</th>-->
                                                    <!--<th class="col-12">Downtime</th>-->
                                                    <!--<th class="col-12">Implementation Cost</th>-->
                                                    <!--<th class="col-12">Manpower</th>-->
                                                    <!--<th class="col-12">Complexity</th>-->
                                                    <!--                                                    <th class="col-12">Model</th>
                                                                                                        <th class="col-12">Manufacturer</th>
                                                                                                         <th class="col-12">Equipment Type</th>
                                                                                                        <th class="col-12">Equipment Model</th>
                                                                                                        <th class="col-12">Equipment Manufacturer</th>
                                                                                                        <th class="col-12">Stress Type</th>
                                                                                                        <th class="col-12">Remarks</th>-->
                                                    <th>Detail</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%--<c:forEach items="${itemList}" var="request" varStatus="requestLoop">--%>
                                                <!--                                                <tr>
                                                                                                    <td id="modal_delete_info_${request.id}"><c:out value="${request.ItemID}"/></td>
                                                                                                    <td><c:out value="${request.SiteName}"/></td>
                                                                                                <td><c:out value="${request.ItemType}"/></td>
                                                                                                <td><c:out value="${request.SubType}"/></td>
                                                                                                <td><c:out value="${request.ItemID}"/></td>
                                                                                                <td><c:out value="${request.ItemName}"/></td>
                                                                                                <td><c:out value="${request.AssemblyID}"/></td>
                                                                                                <td><c:out value="${request.Rack}"/></td>
                                                                                                <td><c:out value="${request.Shelf}"/></td>
                                                                                                <td><c:out value="${request.OnHandQty}"/></td>
                                                                                                     <td><c:out value="${request.ProductionStagingQty}"/></td>
                                                                                               <td><c:out value="${request.ProductionQty}"/></td>
                                                                                               <td><c:out value="${request.RepairQty}"/></td>
                                                                                               <td><c:out value="${request.OtherQty}"/></td>
                                                                                               <td><c:out value="${request.QuarantineQty}"/></td>
                                                                                               <td><c:out value="${request.ExternalCleaningQty}"/></td>
                                                                                               <td><c:out value="${request.ExternalRecleaningQty}"/></td>
                                                                                               <td><c:out value="${request.InternalCleaningQty}"/></td>
                                                                                               <td><c:out value="${request.InternalRecleaningQty}"/></td>
                                                                                               <td><c:out value="${request.StorageFactoryQty}"/></td>
                                                                                               <td><c:out value="${request.OtherONQty}"/></td>
                                                                                               <td><c:out value="${request.VendorQty}"/></td>
                                                                                                <td><c:out value="${request.TotalQty}"/></td>
                                                                                                <td><c:out value="${request.UnitCost}"/></td>
                                                                                                <td><c:out value="${request.TotalCost}"/></td>
                                                                                                <td><c:out value="${request.StatusName}"/></td>
                                                                                               <td><c:out value="${request.ALUHrs}"/></td>
                                                                                               <td><c:out value="${request.MovementALUHrs}"/></td>
                                                                                                     <td><c:out value="${request.MinQty}"/></td>
                                                                                               <td><c:out value="${request.MaxQty}"/></td>
                                                                                               <td><c:out value="${request.PMWW1}"/></td>
                                                                                               <td><c:out value="${request.PMWW2}"/></td>
                                                                                               <td><c:out value="${request.ExpirationDate}"/></td>
                                                                                               <td><c:out value="${request.IsCritical}"/></td>
                                                                                               <td><c:out value="${request.IsConsumeable}"/></td>
                                                                                               <td><c:out value="${request.Downtime}"/></td>
                                                                                                <td><c:out value="${request.ImplementationCost}"/></td>
                                                                                               <td><c:out value="${request.Manpower}"/></td>
                                                                                               <td><c:out value="${request.Complexity}"/></td>
                                                                                                     <td><c:out value="${request.Model}"/></td>
                                                                                                <td><c:out value="${request.Manufacturer}"/></td>
                                                                                               <td><c:out value="${request.EquipmentType}"/></td>
                                                                                               <td><c:out value="${request.EquipmentModel}"/></td>
                                                                                               <td><c:out value="${request.EquipmentManufacturer}"/></td>
                                                                                               <td><c:out value="${request.StressType}"/></td>
                                                                                               <td><c:out value="${request.Remarks}"/></td>
                                                                                                <td>
                                                                                                    <i class="bi bi-box-arrow-in-right h4"></i>
                                                                                                    <a modaldeleteid="${request.PKID}" title="Detail" data-toggle="Detail" href="#" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                                                                        <i class="bi bi-box-arrow-in-right h4"></i>
                                                                                                    </a>
                                                                                                </td>
                                                                                                </tr>-->
                                                <%--</c:forEach>--%>
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

                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-info ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-person-badge me-2"></i>HW Details
                                    </h6>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="itemType2" class="form-label">Item Type</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                            <input type="text" class="form-control" id="itemType2" name="itemType2" placeholder="Enter Full Name" value="">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="itemId" class="form-label">Item ID</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                            <input type="text" class="form-control" id="itemId" name="itemId" placeholder="Enter Email Address" value="">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
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
                                        <label for="inputNumber2" class="form-label">Spare Part Model</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                            <input type="text" class="form-control" id="inputNumber2" placeholder="AVI3211-01">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputName2" class="form-label">Spare Part Manufacturer</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                            <input type="text" class="form-control" id="inputName2" placeholder="Enter Full Name" value="Motherboard">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputEmail2" class="form-label">Unit cost (USD)</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                            <input type="text" class="form-control" id="inputEmail2" placeholder="Enter Email Address" value="AVI3211-01">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputName2" class="form-label">Equipment Type</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                            <input type="text" class="form-control" id="inputName2" placeholder="Enter Full Name" value="Motherboard">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputEmail2" class="form-label">Equipment Model</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                            <input type="text" class="form-control" id="inputEmail2" placeholder="Enter Email Address" value="AVI3211-01">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputNumber2" class="form-label">Eqpt Manufacturer</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                            <input type="text" class="form-control" id="inputNumber2" placeholder="AVI3211-01">
                                        </div>
                                    </div>
                                </div>


                                <!-- Company Information Section -->
                                <!--                                <div class="col-12 mt-2 mb-3">
                                                                    <h6 class="fw-semibold mb-3 border-start border-purple ps-2"
                                                                        style="border-left-width: 3px !important;">
                                                                        <i class="bi bi-building me-2"></i>Organization Details
                                                                    </h6>
                                                                </div>-->

                                <div class="col-xl-1 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Min. Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-1 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Max. Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="1">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Rack</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="Rel01">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Shelf</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="lab01">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Stress Type</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="HAST">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">On Hand Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="1">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Prod. Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Prod. Staging Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Repair Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Quarantine Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Ext Clean Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Ext Re-clean Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="1">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Int. Clean Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Int. Re-Clean Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Other Site Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Storage Factory Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputCompanyName2" class="form-label">Total Qty</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="text" class="form-control" id="inputCompanyName2"
                                                   placeholder="Enter Company Name" value="1">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputName2" class="form-label">Status</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                            <input type="text" class="form-control" id="inputName2" placeholder="Enter Full Name" value="Motherboard">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputEmail2" class="form-label">ALU</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-envelope"></i></span>-->
                                            <input type="text" class="form-control" id="inputEmail2" placeholder="Enter Email Address" value="AVI3211-01">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="inputNumber2" class="form-label">Assembly ID</label>
                                        <div class="input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-telephone"></i></span>-->
                                            <input type="text" class="form-control" id="inputNumber2" placeholder="AVI3211-01">
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
                                        <label for="inputMessage2" class="form-label">Remarks</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                            <textarea class="form-control" id="inputMessage2" placeholder="Enter Message"
                                                      rows="3"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Row end -->

                            <!-- Form actions start -->
                            <div class="d-flex justify-content-end gap-2">
                                <button type="button" class="btn btn-light">Cancel</button>
                                <button type="button" class="btn btn-primary">Submit</button>
                            </div>
                            <!-- Form actions end -->

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
                    scrollY: "610px",
                    scrollCollapse: false,
                    paging: false,
                    bInfo: false,
                    dom: 'Bfrtip',
                    buttons: ["copy", "csv", "print"]
                            //                                                                ,
                            //                                                                columnDefs: [{
                            //                                                                        targets: [0, 1, 2, 4, 5, 6, 7, 8], visible: false
                            //                                                                    }]
                            //                    columnDefs: [{
                            //                            targets: [0,1,2,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,
                            //                            31,32,33,34,35,36,37,38,39,40,41,42,43,44], visible: false
                            //                        }]
                });
            });

            function modalDelete(e) {
                var pkId = $(e).attr("modaldeleteid");
                $.ajax({
                    url: '${contextPath}/hw/json/getitembyparam', // Replace with your controller URL
                    type: 'GET',
                    data: {pkID: pkId},
                    dataType: 'json',
                    success: function (data) {
                        // Populate form fields with received data
                        $("#itemType2").val(data.itemType);
                        $("#itemId").val(data.itemId);
                        $("#itemName").val(data.itemName);
                    }
                    ,
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.error("Error loading data: " + textStatus, errorThrown);
                    }
                }
                );
            }

            $(document).ready(function () {
                $('#itemType').change(function () {

                    var itemType = $('option:selected', this).attr('value');

                    $.ajax({
                        url: '${contextPath}/hw/json/getitembyparamitemtype', // Replace with your controller URL
                        type: 'GET',
                        data: {itemType: itemType},
                        dataType: 'json',
                        success: function (data) {
                            // Populate form fields with received data
                            var tableBody = $('#scrollVertical2 tbody');
                            tableBody.empty(); // Clear existing table rows

                            $.each(data, function (index, item) {
                                var row = '<tr>' +
                                        '<td>' + item.itemId + '</td>' +
//                                        '<td>' + item.itemName + '</td>' +
//                                        '<td>' + item.itemType + '</td>' +
                                        '<td>' +
                                        '<a modaldeleteid="' + item.sptsPkid + '" title="Detail" data-toggle="Detail" href="#" class="table-link danger group_delete" onclick="modalDelete(this);">' +
                                        '<i class="bi bi-box-arrow-in-right h4"></i>' +
                                        '</a>' +
                                        '</td>' +
                                        '</tr>';
                                tableBody.append(row);
                            });
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error loading data: " + textStatus, errorThrown);
                        }
                    });
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>