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
                        <a href="${contextPath}/equipment" class="btn btn-outline-warning me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Equipment Module - <span style="color:#D97D55">Query</span></h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/equipment/query" method="post">
                                <div class="row mb-3">
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptId" class="form-label">Eqpt ID</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="itemType" name="eqptId" style="width: 100%">
                                                    <option></option>
                                                    <option value="All">All</option>
                                                    <c:forEach items="${eqptIdList}" var="invInner">
                                                        <option value="${invInner.equipmentId}">${invInner.equipmentId}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>    
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="relTestGroup" class="form-label">Rel Test Group</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="relTestGroup" name="relTestGroup" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptRelTestGroupList}" var="invInner">
                                                        <option value="${invInner.sptsPkid}">${invInner.relTestGroupName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="familyName" class="form-label">Family Name</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="familyName" name="familyName" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptFamilyList}" var="invInner">
                                                        <option value="${invInner.sptsPkid}">${invInner.familyName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptType" class="form-label">Eqpt Type</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptType" name="eqptType"
                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%">
                                                    <option></option>
                                                    <option value="1">Life</option>
                                                    <option value="2">Environment</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptManufacturer" class="form-label">Eqpt Manufacturer</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptManufacturer" name="eqptManufacturer" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptManufacturerList}" var="invInner">
                                                        <option value="${invInner.equipmentManufacturer}">${invInner.equipmentManufacturer}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptModel" class="form-label">Eqpt Model</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptModel" name="eqptModel" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptModelList}" var="invInner">
                                                        <option value="${invInner.equipmentModel}">${invInner.equipmentModel}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptTech" class="form-label">Eqpt Tech</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptTech" name="eqptTech" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptTechList}" var="invInner">
                                                        <option value="${invInner.sptsPkid}">${invInner.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptMon" " class="form-label">Eqpt Monitoring</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptMon" " name="eqptMon" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptMonList}" var="invInner">
                                                        <option value="${invInner.sptsPkid}">${invInner.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptViMon" class="form-label">Eqpt VI Monitoring</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptViMon" name="eqptViMon" style="width: 100%">
                                                    <option></option>
                                                    <c:forEach items="${eqptViMonList}" var="invInner">
                                                        <option value="${invInner.sptsPkid}">${invInner.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="eqptModel" class="form-label">Status</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="eqptStatus" name="eqptStatus"
                                                        title="Select Eqpt Status" data-live-search="true" style="width: 100%" >
                                                    <option></option>
                                                    <option value="1">Active</option>
                                                    <option value="0">Inactive</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="cbmsType" class="form-label">CBMS Eqpt</label>
                                            <div class="input input-group">
                                                <select class="js-example-basic-single" id="cbmsType" name="cbmsType"
                                                        title="Select Eqpt Type" data-live-search="true" style="width: 100%">
                                                    <option></option>
                                                    <option value="1">Yes</option>
                                                    <option value="0">No</option>
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
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <!--<h5 class="card-title">Item Type With ALU Calculation</h5>-->
                            <div class="col-12 mb-3">
                                <h5 class="fw-semibold mb-3 border-start border-primary ps-2"
                                    style="border-left-width: 3px !important;">
                                    <i class="bi bi-list-ul me-2"></i>Equipment Detail
                                </h5>
                            </div>
                        </div>
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending display nowrap">
                                            <thead>
                                                <tr>
                                                    <th><span>No</span></th>
                                                    <th><span>Eqpt ID</span></th>
                                                    <th><span>Rel Test Group</span></th>
                                                    <th><span>Family Name</span></th>
                                                    <th><span>Eqpt Type</span></th>
                                                    <th><span>Status</span></th>
                                                    <th><span>Eqpt Manufacturer</span></th>
                                                    <th><span>Eqpt Model</span></th>
                                                    <th><span>Eqpt Tech</span></th>
                                                    <th><span>Eqpt Monitoring</span></th>
                                                    <th><span>Eqpt VI Monitoring</span></th>
                                                    <th><span>Eqpt Capability</span></th>
                                                    <th><span>Slot Qty</span></th>
                                                    <th><span>Rack Total</span></th>
                                                    <th><span>Zone per Rack</span></th>
                                                    <th><span>Tray Qty per Rack</span></th>
                                                    <th><span>Crocodile Qty Per Rack</span></th>
                                                    <th><span>Tray Qty per Zone</span></th>
                                                    <th><span>Crocodile Qty Per Zone</span></th>
                                                    <th><span>CBMS Eqpt</span></th>
                                                    <th><span>Remarks</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${resultQuery}" var="parameterMaster" varStatus="parameterMasterLoop">
                                                <tr>
                                                    <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                                                <td id="modal_delete_info_${parameterMaster.id}"><c:out value="${parameterMaster.equipmentId}"/></td>
                                                <td><c:out value="${parameterMaster.relTestGroup}"/></td>
                                                <td><c:out value="${parameterMaster.familyName}"/></td>
                                                <td><c:out value="${parameterMaster.eqptTypeName}"/></td>
                                                <td><c:out value="${parameterMaster.statusName}"/></td>
                                                <td><c:out value="${parameterMaster.equipmentManufacturer}"/></td>
                                                <td><c:out value="${parameterMaster.equipmentModel}"/></td>
                                                <td><c:out value="${parameterMaster.eqptTech}"/></td>
                                                <td><c:out value="${parameterMaster.eqptMon}"/></td>
                                                <td><c:out value="${parameterMaster.eqptViMon}"/></td>
                                                <td><c:out value="${parameterMaster.equipCapability}"/></td>
                                                <td><c:out value="${parameterMaster.slot}"/></td>
                                                <td><c:out value="${parameterMaster.rackTotal}"/></td>
                                                <td><c:out value="${parameterMaster.zonePerRack}"/></td>
                                                <td><c:out value="${parameterMaster.trayQtyPerRack}"/></td>
                                                <td><c:out value="${parameterMaster.basketQtyPerRack}"/></td>
                                                <td><c:out value="${parameterMaster.trayQtyPerZone}"/></td>
                                                <td><c:out value="${parameterMaster.basketQtyPerZone}"/></td>
                                                <td><c:out value="${parameterMaster.cbmsType}"/></td>
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
<!--        <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
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

                                                            $(document).ready(function () {
                                                                $('.js-example-basic-single').select2();
                                                            });

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
        </script>
    </s:layout-component>
</s:layout-render>