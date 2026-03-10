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
        <!--<link rel="stylesheet" href="${contextPath}/resources/css/item_check_edit.css">-->
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
            .hidden {
                display: none !important;
            }
            input[type="number"], input[type="text"], select, button {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
                transition: border-color 0.3s;
            }
            .static-fields {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                gap: 20px;
                margin-bottom: 30px;
            }
            .add-row-btn {
                background-color: #28a745;
                margin-top: 5px;
            }
            table#dataTable thead th {
                text-transform: capitalize;
            }
            input.standard-input-read {
                background-color: lightgray;
            }
            /* --- Table Styling --- */
            table#dataTable {
                width: 100%;
                border-collapse: collapse; /* Removes space between borders */
                margin-top: 4px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            }
            table#dataTable th, table#dataTable td {
                padding: 4px 5px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            table#dataTable thead th {
                background-color: #D2D7EB;
                color: darkslategray;
                font-weight: 600;
                text-transform: uppercase;
            }
            table#dataTable tbody tr:nth-child(even) {
                background-color: #f9f9f9; /* Zebra striping for readability */
            }
            table#dataTable tbody tr:hover {
                background-color: #f1f1f1;
            }
            table#dataTable input {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                border-collapse: collapse;
            }
            table#dataTable button {
                background-color: #FFE5B4;
                color: darkslategray;
                padding: 8px 12px;
                width: auto;
            }
            table#dataTable button:hover {
                background-color: #ffca68;
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
                            <h5 class="card-title">Hardware Module - Activity Selection (BIB / BIB Card)</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/admin/bibActivity/update" method="post">
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="fName">Item Type</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="itemId" name="itemId" value="${item.itemType}" disabled>
                                                <input type="hidden" class="form-control" id="id" name="id" value="${item.id}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="subType">Sub Type</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="subType" name="subType" value="${item.subType}" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="itemId">item ID</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="itemId" name="itemId" value="${item.itemId}" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="itemName">Item Name</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="itemName" name="itemName" value="${item.itemName}" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="assemblyId">Assembly Id</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="assemblyId" name="assemblyId" value="${item.assemblyId}" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                            <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="stressType">Stress Type</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="stressType" name="stressType" value="${item.stressType}" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="assemblyId">Activity</label>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="row g-1">
                                            <label for="viCheck" class="form-label">Visual Inspection</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" id="viCheck" name="viCheck"  <c:if test="${item.vi == 'Yes'}">checked</c:if> >
                                            </div>
                                            <label for="bibTestCheck" class="form-label">Bib Test</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" id="bibTestCheck" name="bibTestCheck" <c:if test="${item.bibTest == 'Yes'}">checked</c:if> >
                                            </div>
                                            <label for="manualTestCheck" class="form-label">Manual Test</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" onchange="toggleVisibility()" id="manualTestCheck" name="manualTestCheck" <c:if test="${item.manualTest == 'Yes'}">checked</c:if> >
                                            </div>
                                            <label for="leakageTestCheck" class="form-label">Leakage Test</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" id="leakageTestCheck" name="leakageTestCheck" <c:if test="${item.leakageTest == 'Yes'}">checked</c:if> >
                                            </div>
                                            <label for="psLeakageTestCheck" class="form-label">Power Supply Leakage Test</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" id="psLeakageTestCheck" name="psLeakageTestCheck" <c:if test="${item.psLeakageTest == 'Yes'}">checked</c:if> >
                                            </div>
                                            <label for="winchesterChamberLeakageTest" class="form-label">Winchester Chamber Leakage Test</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" id="winchesterChamberLeakageTest" name="winchesterChamberLeakageTest" <c:if test="${item.winchesterChamberLeakageTest == 'Yes'}">checked</c:if> >
                                            </div>
                                        </div>
                                    </div>
                                            
                                    <c:choose>
                                        <c:when test="${item.manualTest == 'Yes'}">
                                            <div class="col-xl-6 col-sm-12 col-12" id="manual_page_control">
                                                <div class="static-fields">
                                                    <div>
                                                        <label for="inputDUT">DUT #:</label>
                                                        <input type="number" id="inputDUT" name="inputDUT" required min="1" value="${dut}">
                                                        <button type="button" class="add-row-btn" onclick="addRow()">Add Component</button>
                                                    </div>
                                                </div>

                                                <table id="dataTable">
                                                    <thead>
                                                        <tr>
                                                            <th>Component Type</th>
                                                            <th>Component Name</th>
                                                            <th class="header-value">Value</th>
                                                            <th class="header-percent">Percentage</th>
                                                            <th>Lower Limit</th>
                                                            <th>Upper Limit</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="tableBody">
                                                        <c:forEach items="${listData}" var="manualList" varStatus="manualLoop">
                                                            <tr>
                                                                <td><input type="text" class="standard-input-read" name="component_type[]" value="<c:out value="${manualList.componentType}"/>" readonly></td>
                                                                <td><input type="text" class="standard-input-read" name="component_name[]" value="<c:out value="${manualList.componentName}"/>" readonly></td>
                                                                <c:choose>
                                                                    <c:when test="${manualList.componentType == 'Fuse'}">
                                                                        <td class="status2-text" style="text-align: center;" colspan="4">OPEN / SHORT</td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td><input type="text" class="standard-input-read" name="actual_value[]" value="<c:out value="${manualList.componentValue}"/>" readonly></td>
                                                                        <td><input type="text" class="standard-input-read" name="percentage[]" value="<c:out value="${manualList.percentage}"/>" readonly></td>
                                                                        <td><input type="text" class="standard-input-read" name="lower[]" value="<c:out value="${manualList.lowerLimit}"/>" readonly></td>
                                                                        <td><input type="text" class="standard-input-read" name="upper[]" value="<c:out value="${manualList.upperLimit}"/>" readonly></td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <td><button class="delete-btn" onclick="deleteRow(this)"><i class="bi bi-trash h3" style="color: gray;"></i></button></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-xl-6 col-sm-12 col-12 hidden" id="manual_page_control">
                                                <div class="static-fields">
                                                    <div>
                                                        <label for="inputDUT">DUT #:</label>
                                                        <input type="number" id="inputDUT" name="inputDUT" required min="1" value="${dut}">
                                                        <button type="button" class="add-row-btn" onclick="addRow()">Add Component</button>
                                                    </div>
                                                </div>

                                                <table id="dataTable">
                                                    <thead>
                                                        <tr>
                                                            <th>Component Type</th>
                                                            <th>Component Name</th>
                                                            <th class="header-value">Value</th>
                                                            <th class="header-percent">Percentage</th>
                                                            <th>Lower Limit</th>
                                                            <th>Upper Limit</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="tableBody">
                                                        <c:forEach items="${listData}" var="manualList" varStatus="manualLoop">
                                                            <tr>
                                                                <td><input type="text" class="standard-input-read" name="component_type[]" value="<c:out value="${manualList.componentType}"/>" readonly></td>
                                                                <td><input type="text" class="standard-input-read" name="component_name[]" value="<c:out value="${manualList.componentName}"/>" readonly></td>
                                                                <c:choose>
                                                                    <c:when test="${manualList.componentType == 'Fuse'}">
                                                                        <td class="status2-text" style="text-align: center;" colspan="4">OPEN / SHORT</td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td><input type="text" class="standard-input-read" name="actual_value[]" value="<c:out value="${manualList.componentValue}"/>" readonly></td>
                                                                        <td><input type="text" class="standard-input-read" name="percentage[]" value="<c:out value="${manualList.percentage}"/>" readonly></td>
                                                                        <td><input type="text" class="standard-input-read" name="lower[]" value="<c:out value="${manualList.lowerLimit}"/>" readonly></td>
                                                                        <td><input type="text" class="standard-input-read" name="upper[]" value="<c:out value="${manualList.upperLimit}"/>" readonly></td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <td><button class="delete-btn" onclick="deleteRow(this)"><i class="bi bi-trash h3" style="color: gray;"></i></button></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end" <c:if test="${uac.itemActivityEdit ne 'Yes'}">disabled</c:if>>Save</button>
                                    <a href="${contextPath}/admin/bibActivity" class="btn btn-dark float-start">Back</a>
                                </div>
                            </form>
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
            $(document).ready(function () {
//                $('#inputDUT').removeAttr('required');
                var inputContainer = document.getElementById("manual_page_control");
                var checkbox = document.getElementById("manualTestCheck");
                if (checkbox.checked) {
                    inputContainer.classList.remove("hidden");
                    document.getElementById("inputDUT").setAttribute("required", "required");
                } else {
                    inputContainer.classList.add("hidden");
                    document.getElementById("inputDUT").removeAttribute("required");
                }
                //            $('#onHandQty').change(function () {
                ////                    $('#totalQty').val(parseInt($('#onHandQty').val()) + parseInt($('#productionQty').val()) + parseInt($('#productionStagingQty').val()) + parseInt($('#repairQty').val()));
                //                $('#totalQty').val(parseInt($('#onHandQty').val()));
                //            });
//                var element = $('#itemTypeRead');
//                if (!element.val()) {
//                    //                        alert();
//                    $("#submit").attr("disabled", true);
//                } else {
//                    $("#submit").removeAttr('disabled');
//                }
            });
            
            function toggleVisibility() {
                var checkbox = document.getElementById("manualTestCheck");
                var inputContainer = document.getElementById("manual_page_control");
                var checkDut = document.getElementById("inputDUT");

                if (checkbox.checked) {
                    inputContainer.classList.remove("hidden");
                    document.getElementById("inputDUT").setAttribute("required", "required");
                } else {
                    inputContainer.classList.add("hidden");
                    document.getElementById("inputDUT").removeAttribute("required");
                }
            }
            
            function addRow() {
                const dut = document.getElementById("inputDUT").value || 1;

                const tableBody = document.getElementById("tableBody");
                const newRow = tableBody.insertRow(-1);

                const typeCell = newRow.insertCell(0);
                const nameCell = newRow.insertCell(1);
                const valueCell = newRow.insertCell(2);
                const percentageCell = newRow.insertCell(3);
                const lowerLimitCell = newRow.insertCell(4);
                const upperLimitCell = newRow.insertCell(5);
                const actionCell = newRow.insertCell(6);

                // --- Component Type Select ---
                const typeSelect = document.createElement("select");
                typeSelect.name = 'component_type[]';
                typeSelect.innerHTML = `
                    <option value="Capacitor">Capacitor</option>
                    <option value="Resistor">Resistor</option>
                    <option value="Zener">Zener</option>
                    <option value="Fuse">Fuse</option>
                    <option value="Diode">Diode</option>
                `;
                typeSelect.name = 'component_type[]';
                typeCell.appendChild(typeSelect);

                // --- Name Input ---
                const nameInput = document.createElement("input");
                nameInput.type = "text";
                nameInput.name = 'component_name[]';
                nameInput.placeholder = "Name";
                nameInput.className = 'standard-input';
    //            nameInput.style.width = '90px';
                nameInput.required = true;
                nameCell.appendChild(nameInput);

                // --- Value Input ---
                const valueInput = document.createElement("input");
                valueInput.type = "number";
                valueInput.name = 'actual_value[]';
                valueInput.step = "any";
                valueInput.placeholder = "Value";
                valueInput.className = 'standard-input';
    //            valueInput.style.width = '70px';
                valueInput.required = true;
                valueInput.step = '0.01';
                valueInput.addEventListener('blur', function() {
                    if (this.value) {
                        this.value = parseFloat(this.value).toFixed(2);
                    }
                });
                valueCell.appendChild(valueInput);

                // --- Percentage Input ---
                const percentageInput = document.createElement("input");
                percentageInput.type = "number";
                percentageInput.name = 'percentage[]';
                percentageInput.step = "any";
                percentageInput.placeholder = "%";
                percentageInput.className = 'standard-input';
    //            percentageInput.style.width = '70px';
                percentageInput.step = '0.01';
                percentageInput.addEventListener('blur', function() {
                    if (this.value) {
                        this.value = parseFloat(this.value).toFixed(2);
                    }
                });
                percentageCell.appendChild(percentageInput);

                // --- Lower Limit (Input for Java compatibility) ---
                const lowerLimitInput = document.createElement("input");
                lowerLimitInput.type = "text";
                lowerLimitInput.name = "lower[]";
                lowerLimitInput.readOnly = true; // Prevents manual editing
                lowerLimitInput.className = "standard-input";
    //            lowerLimitInput.style.width = "70px";
                lowerLimitInput.value = "N/A";
                lowerLimitInput.addEventListener('blur', function() {
                    if (this.value) {
                        this.value = parseFloat(this.value).toFixed(2);
                    }
                });
                lowerLimitCell.appendChild(lowerLimitInput);

                // --- Upper Limit (Input for Java compatibility) ---
                const upperLimitInput = document.createElement("input");
                upperLimitInput.type = "text";
                upperLimitInput.name = "upper[]";
                upperLimitInput.readOnly = true;
                upperLimitInput.className = "standard-input";
    //            upperLimitInput.style.width = "70px";
                upperLimitInput.value = "N/A";
                upperLimitInput.addEventListener('blur', function() {
                    if (this.value) {
                        this.value = parseFloat(this.value).toFixed(2);
                    }
                });
                upperLimitCell.appendChild(upperLimitInput);

                const deleteButton = document.createElement("button");
                const trashIcon = document.createElement("i");
                trashIcon.className = "bi bi-trash h3";
                trashIcon.style.color = "gray";
                deleteButton.innerText = "";
                deleteButton.appendChild(trashIcon);
                deleteButton.className = "delete-btn";
                deleteButton.onclick = function () { this.closest('tr').remove(); };
                actionCell.appendChild(deleteButton);

                // --- Calculation Logic ---
                const calculateLimits = () => {
                    const type = typeSelect.value;
                    if (type !== "Fuse") {
                        const val = parseFloat(valueInput.value);
                        const pcnt = parseFloat(percentageInput.value);

                        if (!isNaN(val) && !isNaN(pcnt)) {
                            const tolerance = (val * pcnt) / 100;
                            lowerLimitInput.value = (val - tolerance).toFixed(2);
                            upperLimitInput.value = (val + tolerance).toFixed(2);
                        } else {
                            lowerLimitInput.value = "N/A";
                            upperLimitInput.value = "N/A";
                        }
                    }
                };

                const updateRowState = () => {
                    const type = typeSelect.value;
                    newRow.querySelectorAll('.status-text').forEach(el => el.remove());

                    // Reset visibility
                    [valueCell, percentageCell, lowerLimitCell, upperLimitCell].forEach(c => c.style.display = '');
                    valueCell.colSpan = 1; percentageCell.colSpan = 1;
                    valueInput.style.display = ''; percentageInput.style.display = '';
                    lowerLimitInput.style.display = ''; upperLimitInput.style.display = '';

                    if (type === "Fuse") {
                        valueInput.style.display = 'none';
                        percentageCell.style.display = 'none';
                        lowerLimitCell.style.display = 'none';
                        upperLimitCell.style.display = 'none';
                        valueInput.value = 1.0;
                        percentageInput.value = 0.0;
                        lowerLimitInput.value = 1.0;
                        upperLimitInput.value = 1.0;
                        valueCell.colSpan = 4;
                        const span = document.createElement('span');
                        span.className = 'status-text';
                        span.innerText = "OPEN / SHORT";
                        valueCell.appendChild(span);
                    } else {
                        calculateLimits();
                    }
                };

                // --- Key Event Listeners for Live Updates ---
                valueInput.addEventListener('input', calculateLimits);      // Updates as user types
                percentageInput.addEventListener('input', calculateLimits); // Updates as user types
                typeSelect.addEventListener('change', updateRowState);      // Updates on dropdown change

                updateRowState();
            }
            
            function deleteRow(buttonElement) {
                let tableCell = buttonElement.parentNode;
                let tableRow = tableCell.parentNode;
                let tableBody = tableRow.parentNode;
                tableBody.removeChild(tableRow);
            }
        </script>
    </s:layout-component>
</s:layout-render>