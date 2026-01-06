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
                            <h5 class="card-title">Equipment Module - <span style="color:#D97D55">New Equipment Registration</span></h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row gx-3 needs-validation" role="form" action="${contextPath}/equipment/save" method="post" novalidate>
                                <!-- Row start -->
                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <!--<div class="mb-3 was-validated">-->
                                        <label for="eqptId" class="form-label">Equipment ID</label>
                                        <div class="input input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-person"></i></span>-->
                                            <input type="text" class="input form-control" id="eqptId" name="eqptId" placeholder="" value="" required >
                                            <input type="hidden" class="form-control" id="itemPKID" name="itemPKID" placeholder="" value="" readonly>
                                            <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="" readonly>
                                            <!--<div class="valid-feedback">Looks good!</div>-->
                                            <!--<div class="invalid-feedback">Please provide a valid zip.</div>-->
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="familyName" class="form-label">Family Name</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="familyName" name="familyName"
                                                    title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" required>
                                                <option></option>
                                                <c:forEach items="${eqptFamilyList}" var="invInner">
                                                    <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.familyName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="relTestGroupName" class="form-label">Rel Test Group</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="relTestGroupName" name="relTestGroupName"
                                                    title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" required>
                                                <option></option>
                                                <c:forEach items="${eqptRelTestGroupList}" var="invInner">
                                                    <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.relTestGroupName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group required col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="eqptType" class="form-label">Equipment Type</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="eqptType" name="eqptType"
                                                    title="Select Eqpt Type" data-live-search="true" style="width: 100%" onchange="toggleLinkVisibility()">
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
                                            <select class="input js-example-basic-single" id="eqptStatus" name="eqptStatus"
                                                    title="Select Eqpt Status" data-live-search="true" style="width: 100%" >
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
                                                    title="Select Eqpt Manufacturer" data-live-search="true" style="width: 100%" >
                                                <option></option>
                                                <c:forEach items="${eqptManufacturerList}" var="invInner">
                                                    <option value="${invInner.equipmentManufacturer}" ${invInner.selected}>${invInner.equipmentManufacturer}</option>
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
                                                    title="Select Eqpt Model" data-live-search="true" style="width: 100%" >
                                                <option></option>
                                                <c:forEach items="${eqptModelList}" var="invInner">
                                                    <option value="${invInner.equipmentModel}" ${invInner.selected}>${invInner.equipmentModel}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="eqptTech" class="form-label">Equipment Tech</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="eqptTech" name="eqptTech"
                                                    title="Select Eqpt Tech" data-live-search="true" style="width: 100%" >
                                                <option></option>
                                                <c:forEach items="${eqptTechList}" var="invInner">
                                                    <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="eqptMon" class="form-label">Equipment Monitoring</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="eqptMon" name="eqptMon"
                                                    title="Select Eqpt Monitoring" data-live-search="true" style="width: 100%" >
                                                <option></option>
                                                <c:forEach items="${eqptMonList}" var="invInner">
                                                    <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="eqptViMon" class="form-label">Equipment VI Monitoring</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="eqptViMon" name="eqptViMon"
                                                    title="Select Eqpt VI Monitoring" data-live-search="true" style="width: 100%" >
                                                <option></option>
                                                <c:forEach items="${eqptViMonList}" var="invInner">
                                                    <option value="${invInner.sptsPkid}" ${invInner.selected}>${invInner.name}</option>
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
                                            <input type="text" class="input form-control" id="eqptCapability" name="eqptCapability" placeholder="" value="" >
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12" id="slotQtyDiv">
                                    <div class="mb-3">
                                        <label for="slotQty" class="form-label">Slot Qty</label>
                                        <div class="input input-group">
                                            <!--<span class="input-group-text"><i class="bi bi-briefcase"></i></span>-->
                                            <input type="number" class="input form-control" id="slotQty" name="slotQty" placeholder="" value="0" min="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12 " id="rackQtyDiv">
                                    <div class="mb-3">
                                        <label for="rackQty" class="form-label">Rack Total</label>
                                        <div class="input input-group">
                                            <input type="number" class="input form-control" id="rackQty" name="rackQty" placeholder="" value="0" min="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12 " id="zonePerRackDiv">
                                    <div class="mb-3">
                                        <label for="zonePerRack" class="form-label">Zone per Rack</label>
                                        <div class="input input-group">
                                            <input type="number" class="input form-control" id="zonePerRack" name="zonePerRack" placeholder="" value="0" min="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12 " id="trayQtyPerRackDiv">
                                    <div class="mb-3">
                                        <label for="trayQtyPerRack" class="form-label">Tray Qty per Rack</label>
                                        <div class="input input-group">
                                            <input type="number" class="input form-control" id="trayQtyPerRack" name="trayQtyPerRack" placeholder="" value="0" min="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12 " id="basketQtyPerRackDiv">
                                    <div class="mb-3">
                                        <label for="basketQtyPerRack" class="form-label">Crocodile Qty per Rack</label>
                                        <div class="input input-group">
                                            <input type="number" class="input form-control" id="basketQtyPerRack" name="basketQtyPerRack" placeholder="" value="0" min="0">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12 " id="trayQtyPerZoneDiv">
                                    <div class="mb-3">
                                        <label for="trayQtyPerZone" class="form-label">Tray Qty per Zone</label>
                                        <div class="input input-group">
                                            <input type="number" class="input form-control" id="trayQtyPerZone" name="trayQtyPerZone" placeholder="" value="0" min="0" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-sm-12 col-12" id="basketQtyPerZoneDiv">
                                    <div class="mb-3">
                                        <label for="basketQtyPerZone" class="form-label">Crocodile Qty per Zone</label>
                                        <div class="input input-group">
                                            <input type="number" class="input form-control" id="basketQtyPerZone" name="basketQtyPerZone" placeholder="" value="0" min="0" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group required col-xl-3 col-sm-12 col-12">
                                    <div class="mb-3">
                                        <label for="eqptType" class="form-label">CBMS Equipment</label>
                                        <div class="input input-group">
                                            <select class="input js-example-basic-single" id="cbmsType" name="cbmsType"
                                                    title="Select Eqpt Type" data-live-search="true" style="width: 100%">
                                                <option></option>
                                                <option value="1">Yes</option>
                                                <option value="0">No</option>
                                            </select>
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
                                                      rows="3"></textarea>
                                        </div>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-end gap-2">
                                    <button id="updateButton" type="submit" class="btn btn-primary">Save</button>
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
                                                        });

                                                        function toggleLinkVisibility() {
                                                            var select = document.getElementById('eqptType');
                                                            var slotQtyDiv = document.getElementById('slotQtyDiv');
                                                            var rackQtyDiv = document.getElementById('rackQtyDiv');
                                                            var zonePerRackDiv = document.getElementById('zonePerRackDiv');
                                                            var trayQtyPerRackDiv = document.getElementById('trayQtyPerRackDiv');
                                                            var basketQtyPerRackDiv = document.getElementById('basketQtyPerRackDiv');
                                                            var trayQtyPerZoneDiv = document.getElementById('trayQtyPerZoneDiv');
                                                            var basketQtyPerZoneDiv = document.getElementById('basketQtyPerZoneDiv');
                                                            var slotQty = document.getElementById('slotQty');
                                                            var rackQty = document.getElementById('rackQty');
                                                            var zonePerRack = document.getElementById('zonePerRack');
                                                            var trayQtyPerRack = document.getElementById('trayQtyPerRack');
                                                            var basketQtyPerRack = document.getElementById('basketQtyPerRack');
                                                            var trayQtyPerZone = document.getElementById('trayQtyPerZone');
                                                            var basketQtyPerZone = document.getElementById('basketQtyPerZone');

                                                            if (select.value === "1") {
                                                                slotQtyDiv.hidden = false; // Show 
                                                                rackQtyDiv.hidden = true;
                                                                zonePerRackDiv.hidden = true;
                                                                trayQtyPerRackDiv.hidden = true;
                                                                basketQtyPerRackDiv.hidden = true;
                                                                trayQtyPerZoneDiv.hidden = true;
                                                                basketQtyPerZoneDiv.hidden = true;

                                                                slotQty.value = 0;
                                                                rackQty.value = 0;
                                                                zonePerRack.value = 0;
                                                                trayQtyPerRack.value = 0;
                                                                basketQtyPerRack.value = 0;
                                                                trayQtyPerZone.value = 0;
                                                                basketQtyPerZone.value = 0;

                                                            } else if (select.value === "2") {
                                                                slotQtyDiv.hidden = true; // Hide
                                                                rackQtyDiv.hidden = false;
                                                                zonePerRackDiv.hidden = false;
                                                                trayQtyPerRackDiv.hidden = false;
                                                                basketQtyPerRackDiv.hidden = false;
                                                                trayQtyPerZoneDiv.hidden = false;
                                                                basketQtyPerZoneDiv.hidden = false;

                                                                slotQty.value = 0;
                                                                rackQty.value = 0;
                                                                zonePerRack.value = 0;
                                                                trayQtyPerRack.value = 0;
                                                                basketQtyPerRack.value = 0;
                                                                trayQtyPerZone.value = 0;
                                                                basketQtyPerZone.value = 0;
                                                            } else {
                                                                slotQtyDiv.hidden = true; // Hide
                                                                rackQtyDiv.hidden = true;
                                                                zonePerRackDiv.hidden = true;
                                                                trayQtyPerRackDiv.hidden = true;
                                                                basketQtyPerRackDiv.hidden = true;
                                                                trayQtyPerZoneDiv.hidden = true;
                                                                basketQtyPerZoneDiv.hidden = true;

                                                                slotQty.value = 0;
                                                                rackQty.value = 0;
                                                                zonePerRack.value = 0;
                                                                trayQtyPerRack.value = 0;
                                                                basketQtyPerRack.value = 0;
                                                                trayQtyPerZone.value = 0;
                                                                basketQtyPerZone.value = 0;
                                                            }
                                                        }

                                                        const zonePerRack = document.getElementById('zonePerRack');
                                                        const trayQtyPerRack = document.getElementById('trayQtyPerRack');
                                                        const basketQtyPerRack = document.getElementById('basketQtyPerRack');
                                                        const trayQtyPerZone = document.getElementById('trayQtyPerZone');
                                                        const basketQtyPerZone = document.getElementById('basketQtyPerZone');

                                                        // Add an event listener for the 'input' event
                                                        zonePerRack.addEventListener('input', function () {
                                                            if (zonePerRack.value == 0) {
                                                                trayQtyPerRack.disabled = false;
                                                                basketQtyPerRack.disabled = false;
                                                                trayQtyPerZone.disabled = true;
                                                                basketQtyPerZone.disabled = true;

                                                                trayQtyPerZone.value = 0;
                                                                basketQtyPerZone.value = 0;

                                                            } else if (zonePerRack.value > 0) {
                                                                trayQtyPerRack.disabled = true;
                                                                basketQtyPerRack.disabled = true;
                                                                trayQtyPerZone.disabled = false;
                                                                basketQtyPerZone.disabled = false;

                                                                trayQtyPerRack.value = 0;
                                                                basketQtyPerRack.value = 0;

                                                            } else {
                                                                trayQtyPerRack.disabled = false;
                                                                basketQtyPerRack.disabled = false;
                                                                trayQtyPerZone.disabled = true;
                                                                basketQtyPerZone.disabled = true;

                                                                trayQtyPerRack.value = 0;
                                                                basketQtyPerRack.value = 0;
                                                                trayQtyPerZone.value = 0;
                                                                basketQtyPerZone.value = 0;
                                                            }
                                                        });


        </script>
    </s:layout-component>
</s:layout-render>