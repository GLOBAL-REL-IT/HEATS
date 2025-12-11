<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
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
        <link rel="stylesheet" href="${contextPath}/resources/css/item_check.css"/>
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
                display: none;
            }
            input[readonly] {
                background-color: #e9e9e9;
                cursor: not-allowed;
            }
            .form-actions {
                margin-top: 20px;
            }
            /* Helper class to hide elements */
            .hidden {
                display: none !important;
            }
            input.complete-input {
                display: block;
                width: 95%; /* Adjust width for the merged cell span */
                padding: 4px;
                font-weight: bold;
                text-align: center;
                background-color: #e0e0e0;
                border: 1px solid #ccc;
            }
            .delete-btn {
                background-color: #FFE5B4;
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
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">Activity Configuration (BIB / BIB Card)</span></h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/hw/item/addActivity/save" method="POST">
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="fName">Item Type</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="itemId" name="itemId" value="${item.itemType}" disabled>
                                                <input type="hidden" class="form-control" id="mibItemId" name="mibItemId" value="${item.id}">
                                                <input type="hidden" class="form-control" id="userItemActAdd" name="userItemActAdd" value="${userItemActAdd}">
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
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="activity">Activity</label>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="row g-6">
                                            <c:forEach items="${activity}" var="xtvt">
                                                <label for="${xtvt.remarks}" class="form-label">${xtvt.name}</label>
                                                <div class="input-group form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="${xtvt.remarks}" name="${xtvt.remarks}" 
                                                           <c:if test="${xtvt.remarks eq 'viCheck'}"> checked onclick="return false;"</c:if>
                                                           <c:if test="${xtvt.remarks eq 'manualTestCheck'}"> onchange="toggleVisibility()""</c:if>>
                                                    </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-sm-12 col-12">
                                        <div id="additionalInputs" class="hidden">
                                            <div class="static-fields">
                                                <div>
                                                    <label class="col-2" for="qtyField">Quantity :</label>
                                                    <input class="col-1" type="number" id="qtyField" name="qtyField" min="1" required>
                                                </div>
                                                <div>
                                                    <label class="col-2" for="dutField">DUT #:</label>
                                                    <input class="col-1" type="number" id="dutField" name="dutField" min="1" required>
                                                </div>
                                                <div>
                                                    <label class="col-2" for="manComp">Components :</label>
                                                    <input class="col-1" type="number" id="manComp" name="manComp" min="1" required>
                                                    <button type="button" onclick="createRows()">Create Components</button>
                                                </div>
                                            </div>

                                            <table id="manual_test_component">
                                                <thead>
                                                    <tr>
                                                        <th>Item #</th>
                                                        <th>Component Type</th>
                                                        <th>Component Name</th>
                                                        <th>Value</th>
                                                        <th>Percentage</th>
                                                        <th>Lower Limit</th>
                                                        <th>Upper Limit</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end" <c:if test="${userItemActAdd ne 'Yes'}">disabled</c:if>>Save</button>
                                    <a href="${contextPath}/hw/item/pending" class="btn btn-dark float-start">Back</a>
                                </div>
                                <!-- Form actions end -->
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

        var userItemActAdd = document.getElementById("userItemActAdd");
        if (userItemActAdd.value !== "Yes") {
//                                                            alert();
            var allInputs = document.getElementsByTagName("input");
            for (var i = 0; i < allInputs.length; i++) {
                allInputs[i].disabled = true;
            }
        }

        function createRows() {
            const numRows = document.getElementById('manComp').value;
            const tableBody = document.querySelector('#manual_test_component tbody');
            tableBody.innerHTML = '';

            for (let w = 1; w <= numRows; w++) {
                const newRow = tableBody.insertRow();
                newRow.id = 'row_' + w;

                // Item #
                newRow.insertCell(0).textContent = w;

                // Type (component_type)
                const selectList = document.createElement("select");
                selectList.name = 'component_type[]';
                selectList.className = 'standard-input';
                selectList.onchange = () => handleDropdownChange(w);
                const options = ['Resistor', 'Capacitor', 'Zener', 'Fuse', 'Diode'];
                options.forEach(optionText => {
                    const option = document.createElement("option");
                    option.value = optionText;
                    option.text = optionText;
                    selectList.appendChild(option);
                });
                newRow.insertCell(1).appendChild(selectList);

                // Name (component_name) - Now required
                const inputName = document.createElement('input');
                inputName.type = 'text';
                inputName.name = 'component_name[]';
                inputName.className = 'standard-input';
                inputName.required = true; // Added required attribute
                newRow.insertCell(2).appendChild(inputName);

                // Value Cell (actual_value)
                const valueCell = newRow.insertCell(3);
                valueCell.id = 'value_cell_' + w;
                const inputValue = document.createElement('input');
                inputValue.type = 'number';
                inputValue.value = 0;
                inputValue.id = 'value_input_' + w;
                inputValue.name = 'actual_value[]';
                inputValue.className = 'standard-input';
                inputValue.required = true; // Added required attribute
                valueCell.appendChild(inputValue);

                const completeInput = document.createElement('input');
                completeInput.type = 'text';
                completeInput.value = 'SHORT / OPEN'; // Set the display value
                completeInput.id = 'complete_input_' + w;
                completeInput.name = 'actual_value_hide[]';
                completeInput.className = 'complete-input hidden';
                completeInput.readOnly = true; // Make the visual display read-only
                valueCell.appendChild(completeInput);

                // Percentage Cell (percentage)
                const percentCell = newRow.insertCell(4);
                percentCell.id = 'percent_cell_' + w;
                const inputPercent = document.createElement('input');
                inputPercent.type = 'number';
                inputPercent.value = 0;
                inputPercent.id = 'percent_input_' + w;
                inputPercent.name = 'percentage[]';
                inputPercent.className = 'standard-input';
                inputPercent.required = true; // Added required attribute
                percentCell.appendChild(inputPercent);
                
                const completeInput2 = document.createElement('input');
                completeInput2.type = 'text';
                completeInput2.value = 'SHORT / OPEN'; // Set the display value
                completeInput2.id = 'complete_input2_' + w;
                completeInput2.name = 'actual_value_hide[]';
                completeInput2.className = 'complete-input hidden';
                completeInput2.readOnly = true; // Make the visual display read-only
                percentCell.appendChild(completeInput2);

                // Lower Limit Cell (lower)
                const lowerCell = newRow.insertCell(5);
                lowerCell.id = 'lower_cell_' + w;
                const inputLower = document.createElement('input');
                inputLower.type = 'number';
                inputLower.id = 'lower_input_' + w;
                inputLower.readOnly = true;
                inputLower.name = 'lower[]';
                inputLower.className = 'standard-input';
                lowerCell.appendChild(inputLower);

                // Upper Limit Cell (upper)
                const upperCell = newRow.insertCell(6);
                upperCell.id = 'upper_cell_' + w;
                const inputUpper = document.createElement('input');
                inputUpper.type = 'number';
                inputUpper.id = 'upper_input_' + w;
                inputUpper.readOnly = true;
                inputUpper.name = 'upper[]';
                inputUpper.className = 'standard-input';
                upperCell.appendChild(inputUpper);

                // Add event listeners for calculation
                inputValue.addEventListener('input', () => calculate(w));
                inputPercent.addEventListener('input', () => calculate(w));

                handleDropdownChange(w);
                
                const deleteCell = newRow.insertCell(7);
                const deleteButton = document.createElement("button");
                deleteButton.innerText = "Delete";
                deleteButton.className = "delete-btn";
                // Add an event listener to the button to remove its parent row
                deleteButton.onclick = function () {
                    // 'this' refers to the button element clicked
                    // .parentNode is the <td> (cell)
                    // .parentNode.parentNode is the <tr> (row)
                    this.parentNode.parentNode.remove();
                };
                deleteCell.appendChild(deleteButton);
            }
        }

        function calculate(rowId) {
            const selectElement = document.querySelector('#row_' + rowId + ' select[name="component_type[]"]');

            if (selectElement.value === 'Fuse') {
                return;
            }

            const actualValue = parseFloat(document.getElementById('value_input_' + rowId).value) || 0;
            const percentage = parseFloat(document.getElementById('percent_input_' + rowId).value) || 0;

            const toleranceAmount = (actualValue * percentage) / 100;
            const lowerLimit = actualValue - toleranceAmount;
            const upperLimit = actualValue + toleranceAmount;

            document.getElementById('lower_input_' + rowId).value = lowerLimit.toFixed(2);
            document.getElementById('upper_input_' + rowId).value = upperLimit.toFixed(2);
        }

        function handleDropdownChange(rowId) {
            const selectElement = document.querySelector('#row_' + rowId + ' select[name="component_type[]"]');

            // Input references
            const valueInput = document.getElementById('value_input_' + rowId);
            const percentInput = document.getElementById('percent_input_' + rowId);
            const completeInput = document.getElementById('complete_input_' + rowId);
            const completeInput2 = document.getElementById('complete_input2_' + rowId);
            const lowerInput = document.getElementById('lower_input_' + rowId);
            const upperInput = document.getElementById('upper_input_' + rowId);

            // Cell references
            const valueCell = document.getElementById('value_cell_' + rowId);
            const percentCell = document.getElementById('percent_cell_' + rowId);
            const lowerCell = document.getElementById('lower_cell_' + rowId);
            const upperCell = document.getElementById('upper_cell_' + rowId);

            if (selectElement.value === 'Fuse') {
                // Set required values for hidden inputs
                // We clear the numerical input value since the string input will carry the name
                valueInput.value = '1';
                percentInput.value = 0;
                lowerInput.value = 0;
                upperInput.value = 1;

                // Remove 'required' from hidden standard inputs
                valueInput.removeAttribute('required');
                percentInput.removeAttribute('required');
                // Add 'required' to the visible "COMPLETE" input if needed (though it has a default value)
                // completeInput.setAttribute('required', true); // Optional, since value is hardcoded

                // Hide standard inputs and cells
                valueInput.classList.add('hidden');
                percentInput.classList.add('hidden');
                lowerInput.classList.add('hidden');
                upperInput.classList.add('hidden');
                
                percentCell.classList.add('hidden');
                lowerCell.classList.add('hidden');
                upperCell.classList.add('hidden');

                // Show the 'COMPLETE' input field in the value cell
                completeInput.classList.remove('hidden');
                completeInput2.classList.add('hidden');

                // Merge the value cell across the 4 columns
                valueCell.colSpan = 4;
            } else if (selectElement.value === 'Zener' || selectElement.value === 'Diode') {
                valueInput.value = '1';
                percentInput.value = 0;
                lowerInput.value = 0;
                upperInput.value = 1;
                valueInput.removeAttribute('required');
                percentInput.removeAttribute('required');
                
                valueInput.classList.remove('hidden');
                percentInput.classList.add('hidden');
                lowerInput.classList.add('hidden');
                upperInput.classList.add('hidden');
                
                valueCell.classList.remove('hidden');
                percentCell.classList.remove('hidden');
                lowerCell.classList.add('hidden');
                upperCell.classList.add('hidden');

                // Show the 'COMPLETE' input field in the value cell
                completeInput.classList.add('hidden');
                completeInput2.classList.remove('hidden');

                // Merge the value cell across the 4 columns
                percentCell.colSpan = 3;
                valueCell.colSpan = 1;

            } else {
                valueInput.value = 1; // Clear numerical value input
                percentInput.value = 0;
                upperInput.value = 1;
                lowerInput.value = 0;
                // Restore 'required' for standard inputs
                valueInput.setAttribute('required', true);
                percentInput.setAttribute('required', true);
                // completeInput.removeAttribute('required'); // If added above

                // Show standard inputs and restore cell visibility
                valueInput.classList.remove('hidden');
                percentInput.classList.remove('hidden');
                lowerInput.classList.remove('hidden');
                upperInput.classList.remove('hidden');
                valueCell.classList.remove('hidden');
                percentCell.classList.remove('hidden');
                lowerCell.classList.remove('hidden');
                upperCell.classList.remove('hidden');

                // Hide the 'COMPLETE' input
                completeInput.classList.add('hidden');
                completeInput2.classList.add('hidden');

                // Reset colspan
                valueCell.colSpan = 1;
                percentCell.colSpan = 1;

                calculate(rowId);
            }
        }

        function toggleVisibility() {
            var checkbox = document.getElementById("manualTestCheck");
            var inputContainer = document.getElementById("additionalInputs");

            if (checkbox.checked) {
                inputContainer.classList.remove("hidden");
            } else {
                inputContainer.classList.add("hidden");
            }
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            createRows(); // Create initial rows on page load
            toggleVisibility();
        });
        
        function removeRow(buttonElement) {
            const rowToRemove = buttonElement.closest('tr');
            rowToRemove.parentNode.removeChild(rowToRemove);
        }
    </script>
</s:layout-component>
</s:layout-render>