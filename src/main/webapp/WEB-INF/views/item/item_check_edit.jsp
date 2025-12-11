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
        <link rel="stylesheet" href="${contextPath}/resources/css/item_check_edit.css">
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
                                                <input type="hidden" class="form-control" id="userItemActEdit" name="userItemActEdit" value="${userItemActEdit}">
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
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="activity">Activity</label>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="row g-6">
                                            <label for="viCheck" class="form-label">Visual Inspection</label>
                                            <div class="input-group form-check form-switch">
                                                <input class="form-check-input" type="checkbox" role="switch" id="viCheck" name="viCheck" <c:if test="${item.vi == 'Yes'}">checked</c:if> >
                                                </div>
                                                <label for="bibTestCheck" class="form-label">Bib Test</label>
                                                <div class="input-group form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="bibTestCheck" name="bibTestCheck" <c:if test="${item.bibTest == 'Yes'}">checked</c:if> >
                                                </div>
                                                <label for="manualTestCheck" class="form-label">Manual Test</label>
                                                <div class="input-group form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="manualTestCheck" name="manualTestCheck" <c:if test="${item.manualTest == 'Yes'}">checked</c:if> >
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

                                    <c:if test="${item.manualTest == 'Yes'}">
                                        <div class="col-xl-6 col-sm-12 col-12">
                                            <div class="static-fields">
                                                <div>
                                                    <label for="inputQuantity">Quantity :</label>
                                                    <input type="number" id="inputQuantity" name="inputQuantity" required min="1" value="${qty}">
                                                </div>
                                                <div>
                                                    <label for="inputDUT">DUT #:</label>
                                                    <input type="number" id="inputDUT" name="inputDUT" required min="1" value="${dut}">
                                                </div>
                                                <div>
                                                    <label for="componentField">Components:</label>
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
                                                            <td><c:out value="${manualList.componentType}"/></td>
                                                            <td><c:out value="${manualList.componentName}"/></td>
                                                            <c:choose>
                                                                <%-- Condition 3: Fuse -> Combined value/percentage/limits into one cell, text changes to CLOSED/OPEN --%>
                                                                <c:when test="${manualList.componentType == 'Fuse'}">
                                                                    <td style="text-align: center;" colspan="4">OPEN / CLOSED</td>
                                                                </c:when>
                                                                <%-- Condition 4: Zener or Diode -> View value, combined percentage/limits, text update to OPEN/CLOSED --%>
                                                                <c:when test="${manualList.componentType == 'Zener' || manualList.componentType == 'Diode'}">
                                                                    <td><c:out value="${manualList.componentValue}"/></td>
                                                                    <td style="text-align: center;" colspan="3">OPEN / CLOSED</td>
                                                                </c:when>
                                                                <%-- Optional: Default case if the component type doesn't match any specific rule --%>
                                                                <c:otherwise>
                                                                    <td><c:out value="${manualList.componentValue}"/></td>
                                                                    <td><c:out value="${manualList.percentage}"/></td>
                                                                    <td><c:out value="${manualList.lowerLimit}"/></td>
                                                                    <td><c:out value="${manualList.upperLimit}"/></td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <td><button class="delete-btn" onclick="deleteRow(this)">Delete</button></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <!--<div class="justify-content-end">-->
                                    <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end" <c:if test="${userItemActEdit ne 'Yes'}">disabled</c:if> >Save</button>
                                        <!--</div>-->
                                        <!--<div class="justify-content-start">-->
                                        <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                        <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                    <!--</div>-->
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

        var userItemActEdit = document.getElementById("userItemActEdit");
        if (userItemActEdit.value !== "Yes") {
            var allInputs = document.getElementsByTagName("input");
            for (var i = 0; i < allInputs.length; i++) {
                allInputs[i].disabled = true;
            }
        }
        
        document.addEventListener('DOMContentLoaded', (event) => {
            
        });
        
        function addRow() {
            const quantity = document.getElementById("inputQuantity").value || 1;
            const dut = document.getElementById("inputDUT").value || 1;

            if (!quantity || !dut) {
                alert("Please enter valid Quantity and DUT values.");
                return;
            }

            const tableBody = document.getElementById("tableBody");
            const newRow = tableBody.insertRow(-1);
            // newRow does not get an ID assigned here.

            const typeCell = newRow.insertCell(0);
            const nameCell = newRow.insertCell(1);
            const valueCell = newRow.insertCell(2);
            const percentageCell = newRow.insertCell(3);
            const lowerLimitCell = newRow.insertCell(4);
            const upperLimitCell = newRow.insertCell(5);
            const actionCell = newRow.insertCell(6);

            // --- Create Input Elements ---
            const typeSelect = document.createElement("select");
            typeSelect.innerHTML = `
                <option value="Capacitor">Capacitor</option>
                <option value="Resistor">Resistor</option>
                <option value="Zener">Zener</option>
                <option value="Fuse">Fuse</option>
                <option value="Diode">Diode</option>
            `;
            typeCell.appendChild(typeSelect);

            const nameInput = document.createElement("input");
            nameInput.type = "text";
            nameInput.placeholder = "Name";
            nameInput.style.width = '80px';
            nameInput.requied = true;
            nameCell.appendChild(nameInput);

            const valueInput = document.createElement("input");
            valueInput.type = "number";
            valueInput.step = "any";
            valueInput.placeholder = "Value";
            valueInput.style.width = '70px';
            valueInput.required = true;
            valueCell.appendChild(valueInput);

            const percentageInput = document.createElement("input");
            percentageInput.type = "number";
            percentageInput.step = "any";
            percentageInput.placeholder = "%";
            percentageInput.style.width = '50px';
            percentageCell.appendChild(percentageInput);

            const lowerLimitDisplay = document.createElement("span");
            lowerLimitDisplay.innerText = "N/A";
            lowerLimitCell.appendChild(lowerLimitDisplay);

            const upperLimitDisplay = document.createElement("span");
            upperLimitDisplay.innerText = "N/A";
            upperLimitCell.appendChild(upperLimitDisplay);

            const deleteButton = document.createElement("button");
            deleteButton.innerText = "Delete";
            deleteButton.className = "delete-btn";

            // The delete handler uses DOM traversal (closest('tr'))
            deleteButton.onclick = function () {
                this.closest('tr').remove();
            };
            actionCell.appendChild(deleteButton);

            // --- Calculation and Type Handling Logic ---

            // These functions use closures to access the variables defined above them
            const calculateLimits = () => {
                if (typeSelect.value === "Capacitor" || typeSelect.value === "Resistor") {
                    const val = parseFloat(valueInput.value);
                    const pcnt = parseFloat(percentageInput.value);

                    if (!isNaN(val) && !isNaN(pcnt)) {
                        const toleranceAmount = (val * pcnt) / 100;
                        lowerLimitDisplay.innerText = (val - toleranceAmount).toFixed(3);
                        upperLimitDisplay.innerText = (val + toleranceAmount).toFixed(3);
                    } else {
                        lowerLimitDisplay.innerText = "N/A";
                        upperLimitDisplay.innerText = "N/A";
                    }
                }
            };

            const updateRowState = () => {
                const type = typeSelect.value;

                // Reset state
                newRow.querySelectorAll('.status-text').forEach(el => el.remove());
                valueCell.style.display = '';
                valueCell.colSpan = 1;
                percentageCell.style.display = '';
                percentageCell.colSpan = 1;
                lowerLimitCell.style.display = '';
                upperLimitCell.style.display = '';
                valueInput.style.display = '';
                percentageInput.style.display = '';
                lowerLimitDisplay.style.display = '';
                upperLimitDisplay.style.display = '';

                if (type === "Fuse") {
                    // FUSE LOGIC: Combine Component Value, %, Lower, Upper
                    valueInput.style.display = 'none';
                    percentageInput.style.display = 'none';
                    percentageCell.style.display = 'none';
                    lowerLimitCell.style.display = 'none';
                    upperLimitCell.style.display = 'none';
                    valueCell.colSpan = 4;

                    const statusSpan = document.createElement('span');
                    statusSpan.className = 'status-text';
                    statusSpan.innerText = "OPEN / CLOSED";
                    valueCell.appendChild(statusSpan);

                } else if (type === "Diode" || type === "Zener") {
                    // Diode/Zener Logic
                    percentageInput.value = 0;
                    percentageInput.style.display = 'none';
                    percentageCell.colSpan = 3;
                    lowerLimitCell.style.display = 'none';
                    upperLimitCell.style.display = 'none';

                    const statusSpan = document.createElement('span');
                    statusSpan.className = 'status-text';
                    statusSpan.innerText = "OPEN / CLOSED";
                    percentageCell.appendChild(statusSpan);

                } else {
                    // Standard components
                    calculateLimits();
                }
            };

            // Attach event listeners using the local function references
            valueInput.addEventListener('input', calculateLimits);
            percentageInput.addEventListener('input', calculateLimits);
            typeSelect.addEventListener('change', updateRowState);

            // Set initial state
            updateRowState();
        }
        
        function deleteRow(buttonElement) {
            // 1. Get the parent cell (<td>)
            let tableCell = buttonElement.parentNode;
            // 2. Get the parent row (<tr>)
            let tableRow = tableCell.parentNode;
            // 3. Get the parent body (<tbody>) to remove the row from
            let tableBody = tableRow.parentNode;

            // 4. Remove the row element from the table body
            tableBody.removeChild(tableRow);

            // Optional: Add confirmation dialogue
//             if (confirm("Are you sure you want to delete this row?")) {
//                 tableBody.removeChild(tableRow);
//             } else {
//                 
//             }
        }
        
//        $(document).ready(function () {

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

//        });
    </script>
</s:layout-component>
</s:layout-render>