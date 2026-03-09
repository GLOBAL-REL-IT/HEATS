<%-- 
    Document   : verify_hardware_id
    Created on : Mar 6, 2026, 4:06:22 PM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css"/>-->
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            input[readonly] {
                border: none;
            }
            .input {
                border: 1.5px solid #000;
                border-radius: 0.5rem;
                box-shadow: 2.5px 3px 0 #000 !important;
                outline: none;
                transition: ease 0.25s;
            }
            .input:focus {
                box-shadow: 5.5px 7px 0 black;
            }
            .hidden {
                display: none;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-12 ps-2">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="card-title mb-3"><span style="color:#D97D55"> Hardware ID Creation</span> - [${itemId}]</h5>
                    <form class="row gx-3 needs-validation" role="form" action="${contextPath}/hw/item/hardware/verify" method="post" novalidate>
                        <div class="row">
                            <div class="form-group required col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="itemType" class="form-label">Item Type</label>
                                    <div class="input input-group">
                                        <input type="text" class="form-control" id="itemType" name="itemType" placeholder="" value="${item.itemType}" readonly required>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-2 col-sm-12 col-12">
                                <div class="mb-3">
                                    <label for="subType" class="form-label">Sub Type</label>
                                    <div class="input input-group">
                                        <input type="text" class="form-control" id="subType" name="subType" placeholder="" value="${item.subType}" readonly>
                                        <input type="hidden" class="form-control" id="sptsPkid" name="sptsPkid" placeholder="" value="${item.sptsPkid}" readonly>
                                        <input type="hidden" class="form-control" id="mibId" name="mibId" placeholder="" value="${item.id}" readonly>
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
                            <div class="form-group required col-xl-4 col-sm-12 col-12 hidden">
                                <div class="mb-3">
                                    <label for="hardwareId" class="form-label">Hardware ID</label>
                                    <div class="input input-group">
                                        <input type="hidden" name="hwid" value="${itemhwidid}">
                                        <input type="hidden" name="hwidname" value="${itemhwid}">
                                        <input type="text" class="form-control" id="hardwareId" name="hardwareId" placeholder="" value="${itemhwid}" readonly required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group required col-xl-6 col-sm-12 col-12">
                                <div class="card mb-4">
                                    <div class="card-header d-flex justify-content-between align-items-center">
                                        <h5 class="card-title mb-0">Hardware ID List</h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="status-list">
                                            ${maklumatList}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="rounded-3">
                            <h6 class="fw-semibold mb-3 border-success text-info ps-2">
                                <i class="bi bi-card-checklist m-2"></i>Verify Hardware ID Information
                            </h6>
                        </div>
                        <div class="form-group required col-xl-6 col-sm-12 col-12">
                            <div class="mb-3">
                                <label for="itemName" class="form-label">Scan Hardware ID Here</label>
                                <div class="input input-group">
                                    <input type="text" class="form-control" id="scanInput" name="scanInput" placeholder="Scan barcode here" autofocus>
                                </div>
                                <p id="message"></p>
                            </div>
                        </div>
                        <div>
                            <button type="submit" id="submitButton" class="btn btn-primary" disabled>Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <!--<script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>-->
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                
            });
            
            document.addEventListener('DOMContentLoaded', (event) => {
                const inputElement = document.getElementById('scanInput');
                const messageElement = document.getElementById('message');
                const requiredLength = 15;
                const timeLimit = 1000; // 1 second in milliseconds
                const myButton = document.getElementById("submitButton");

                let timeoutId;
                let startTime;
                let charCount = 0;

                // Focus the input field automatically for immediate scanning
                inputElement.focus();

                inputElement.addEventListener('keydown', (e) => {
                    if (timeoutId) { clearTimeout(timeoutId); }
                    if (charCount === 0) { startTime = new Date().getTime(); }
                    charCount++;

                    timeoutId = setTimeout(() => {
                        const endTime = new Date().getTime();
                        const timeElapsed = endTime - startTime;
                        const value = inputElement.value;

                        // Check if the input meets the criteria for a scan
                        if (value.length >= requiredLength && timeElapsed <= timeLimit) {
                            const dataCheck = document.getElementById('hardwareId');
                            if (inputElement.value === dataCheck.value) {
                                messageElement.textContent = 'Hardware ID data matched. Verification Complete.';
                                messageElement.style.color = 'green';
                                myButton.disabled = false;
                            } else {
                                messageElement.textContent = 'You scan wrong Hardware ID ['+inputElement.value+']';
                                messageElement.style.color = 'red';
                                myButton.disabled = true;
                            }
                        } else {
                            inputElement.value = '';
                            messageElement.textContent = 'Invalid input (Please scan Hardware ID properly). Value reset.';
                            messageElement.style.color = 'red';
                            myButton.disabled = true;
                        }
                        charCount = 0;
                        startTime = 0;
                    }, 50);
                });
            });

        </script>
    </s:layout-component>
</s:layout-render>