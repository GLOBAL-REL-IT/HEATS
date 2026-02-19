<%-- 
    Document   : create_hardware_id
    Created on : Feb 9, 2026, 4:05:28 PM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css"/>-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/"/>-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/"/>-->
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            input[readonly] {
                border: none;
                /*background-color:#f0f0f0;*/
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-12 ps-2">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="card-title">${itemType} - <span style="color:#D97D55"> Hardware ID Creation</span></h5>
                </div>
                <c:set var="hardwareconfig" value="${maklumatconfig}"/>
                <c:choose>
                    <c:when test="${not empty hardwareconfig}">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/hardware/create" method="post">
                            <div class="card-body">
                                <div class="p-3 mb-4 rounded-3">
                                    <div class="row gx-3">
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="itemType">Item Type </label>
                                                <input type="text" class="form-control" id="itemType" name="itemType" value="${item.itemType}" readonly>
                                                <input type="hidden" name="sptsId" value="${sptsId}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="subType">Sub Type</label>
                                                <input type="text" class="form-control" id="subType" name="subType" placeholder="No Sub Type" value="${item.subType}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="itemId">Item ID </label>
                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="Item ID" value="${item.itemId}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="itemName">Item Name </label>
                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="Item Name" value="${item.itemName}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="rounded-3">
                                    <h6 class="fw-semibold mb-3 border-success text-info ps-2">
                                        <i class="bi bi-card-checklist m-2"></i>Hardware ID Information
                                    </h6>
                                </div>
                                <div class="p-3 mb-4 rounded-3">
                                    <div class="row gx-4">
                                        <div class="col-sm-6 col-12">
                                            <c:if test="${hardwareconfig.sameItemId == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="sameItemId">Same Item ID<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="sameItemId" name="sameItemId" placeholder="Same Item ID" value="${item.itemId}" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.supplier == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="supplier">Supplier<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="supplier" name="supplier" placeholder="Key in supplier" value="" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.assemblyNo == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="assemblyNo">Assembly Number<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="assemblyNo" name="assemblyNo" placeholder="Key in assembly number" value="${item.assemblyId}" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.revision == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="revision">Revision<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="revision" name="revision" placeholder="Key in revision" value="" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.mfgDate == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label for="mfgDate" class="form-label">Mfg Date<span class="text-danger"> *</span></label>
                                                        <input type="month" class="form-control" id="mfgDate" name="mfgDate" required >
                                                        <div class="mb-3" hidden>
                                                            <label class="form-label">msgDate (mmyy)</label>
                                                            <input type="text" id="viewMessage" name="viewMessage" class="form-control" readonly value=""/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.component == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="component">Component<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="component" name="component" placeholder="Key in component?" value="" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.event == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="eventStress">Event<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="eventStress" name="eventStress" placeholder="Key in Event" value="${item.stressType}" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.partNumber == 'Yes'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="partNo">Part Number<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="partNo" name="partNo" placeholder="Key in Part Number" value="" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.alu == 'YesA'}">  <!-- LETAK NI SEBAB TAK NAK DIA KELUAR BUAT MASA SEKARANG 20260213 -->
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="alu">ALU?<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="alu" name="alu" placeholder="Calculate ALU?" value="" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.shelfTime == 'YesA'}">  <!-- LETAK NI SEBAB TAK NAK DIA KELUAR BUAT MASA SEKARANG 20260213 -->
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="shelfTime">Shelf Time?<span class="text-danger"> *</span></label>
                                                        <input type="text" class="form-control" id="shelfTime" name="shelfTime" placeholder="Calculate Shelf Time?" value="" required>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${hardwareconfig.sameItemId == 'No'}">
                                                <div class="col-sm-8 col-12">
                                                    <div class="mb-3">
                                                        <label class="form-label" for="runningNumber">Running Number<span class="text-danger"> *</span></label>
                                                        <input type="number" class="form-control" id="runningNumber" name="runningNumber" min="1" step="1" placeholder="Key in running number">
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="col-sm-6 col-12" hidden>
                                            <label class="form-label">Preview</label>
                                            <pre id="preview" class="form-control monospace preview-box"></pre>
                                        </div>
                                    </div>
                                </div>

                                <div class="d-flex gap-2 mb-3">
                                    <button hidden id="btnGenerate" class="btn btn-primary">Generate</button>
                                    <button hidden id="btnCopy" class="btn btn-outline-secondary" disabled>Copy All</button>
                                    <button hidden id="btnDownload" class="btn btn-outline-secondary" disabled>Download TXT</button>
                                </div>

                                <div class="d-flex gap-2 justify-content-between">
                                    <c:choose>
                                        <c:when test="${hardwareconfig.sameItemId eq 'No' and hardwareconfig.supplier eq 'No'
                                                        and hardwareconfig.assemblyNo eq 'No' and hardwareconfig.revision eq 'No'
                                                        and hardwareconfig.mfgDate eq 'No' and hardwareconfig.component eq 'No'
                                                        and hardwareconfig.event eq 'No' and hardwareconfig.partNumber eq 'No' }">
                                                <button type="button" class="btn btn-light" onclick="window.location.href = '${contextPath}/hw/${sptsId}'">Back</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" class="btn btn-light" onclick="window.location.href = '${contextPath}/hw/${sptsId}'">Back</button>
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="card-body">
                            <div class="alert bg-danger alert-dismissible d-flex fade show text-white" role="alert">
                                <i class="bi bi-x-circle-fill fs-3 me-3 lh-1"></i>
                                <div class="d-flex flex-column">
                                    <h6>Missing Hardware ID Configuration.</h6>
                                    <p>We couldn't find the necessary Hardware ID configuration to complete this action. Please contact your System Administrator to set up the Hardware ID Configuration.</p>
                                    <p>ITEM TYPE : ${item.itemType}<c:if test="${not empty item.subType}"> - ${subType}</c:if>.</p>
                                    <p>SUB TYPE : ${item.subType}</p>
                                    <div class="d-flex gap-4">
                                        <button class="btn btn-info" onclick="javascript:window.location.href = '${contextPath}/hw/${sptsId}'">Go Back</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <!--<script src="${contextPath}/resources/vendor/DataTables/customitem/bootstrap.bundle.min.js"></script>-->
        <!--<script src="${contextPath}/resources/"></script>-->
        <!--<script src="${contextPath}/resources/"></script>-->
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                function monthToMMYY(yyyyDashMM) {
                    // Expecting "YYYY-MM"
                    if (!yyyyDashMM || !/^\d{4}-\d{2}$/.test(yyyyDashMM))
                        return "";
                    const [yyyy, mm] = yyyyDashMM.split("-");
                    const yy = yyyy.slice(-2);      // last two digits of year
                    return mm + yy;                 // "mmyy"
                }

                function buildLines( { assembly, mmyy, eventName, count }) {
                    const out = [];
                    console.log("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    console.log("assembly >>>> " + assembly);
                    console.log("mmyy >>>> " + mmyy);
                    console.log("eventName >>>> " + eventName);
                    console.log("count >>>> " + count);
                    for (let i = 1; i <= count; i++) {
                        out.push(`${assembly}-${mmyy}-${eventName}-${i}`);
                                    }
                                    return out;
                                }

                                function readInputs() {
                                    const assembly = document.getElementById("assemblyNo").value.trim();
                                    const yyyyDashMM = document.getElementById("mfgDate").value;
                                    const mmyy = monthToMMYY(yyyyDashMM);   // this is your msgDate
                                    const eventName = document.getElementById("eventStress").value.trim();
                                    const count = Number(document.getElementById("runningNumber").value);
                                    console.log("SINI DIA BACA SEMAU INPUT");
                                    console.log("assembly >> " + assembly);
                                    console.log("yyyyDashMM >> " + yyyyDashMM);
                                    console.log("mmyy >> " + mmyy);
                                    console.log("eventName >> " + eventName);
                                    console.log("assembly >> " + assembly);
                                    return {assembly, mmyy, eventName, count};
                                }

                                function validate( { assembly, mmyy, eventName, count }) {
                                    const errors = [];
                                    if (!assembly)
                                        errors.push("Assembly Number is required.");
                                    if (!mmyy || !/^(0[1-9]|1[0-2])\d{2}$/.test(mmyy))
                                        errors.push("Valid Mfg Date (mmyy) is required.");
                                    if (!eventName)
                                        errors.push("Event is required.");
                                    if (!Number.isInteger(count) || count < 1)
                                        errors.push("Running Number must be an integer ≥ 1.");
                                    return errors;
                                }

                                const previewEl = document.getElementById("preview");
                                const btnGenerate = document.getElementById("btnGenerate");
                                const btnCopy = document.getElementById("btnCopy");
                                const btnDownload = document.getElementById("btnDownload");
                                const mfgMonthEl = document.getElementById("mfgDate");
                                const msgDateEl = document.getElementById("viewMessage");

                                console.log("-----------------");
                                console.log("previewEl >>> " + previewEl);
                                console.log("btnCopy >>> " + btnCopy);
                                console.log("btnDownload >>> " + btnDownload);
                                console.log("mfgMonthEl >>> " + mfgMonthEl);
                                console.log("msgDateEl >>> " + msgDateEl);
                                console.log("-----------------");

                                function updateMsgDate() {
                                    msgDateEl.value = monthToMMYY(mfgMonthEl.value) || "";
                                }

                                function generate() {
                                    const data = readInputs();
                                    // keep msgDate display in sync
                                    msgDateEl.value = data.mmyy;

                                    const errors = validate(data);
                                    if (errors.length) {
                                        previewEl.textContent = "⚠ " + errors.join("\n");
                                        btnCopy.disabled = true;
                                        btnDownload.disabled = true;
                                        return;
                                    }

                                    const lines = buildLines(data);
                                    console.log("lines >> " + lines);
                                    previewEl.textContent = lines.join("\n");
                                    btnCopy.disabled = lines.length === 0;
                                    btnDownload.disabled = lines.length === 0;
                                    console.log("GENERATE");
                                }

                                // Wire events
                                btnGenerate.addEventListener("click", generate);
                                ["assemblyNo", "mfgDate", "eventStress", "runningNumber"].forEach(id => {
                                    document.getElementById(id).addEventListener("input", () => {
                                        // light debounce
                                        clearTimeout(window.__genTimer);
                                        window.__genTimer = setTimeout(() => {
                                            updateMsgDate();
                                            generate();
                                        }, 120);
                                    });
                                });

                                // Copy all
                                btnCopy.addEventListener("click", async () => {
                                    const text = previewEl.textContent.trim();
                                    if (!text)
                                        return;
                                    try {
                                        await navigator.clipboard.writeText(text);
                                        btnCopy.textContent = "Copied!";
                                        setTimeout(() => (btnCopy.textContent = "Copy All"), 1200);
                                    } catch {
                                        // Fallback
                                        const sel = window.getSelection();
                                        const range = document.createRange();
                                        range.selectNodeContents(previewEl);
                                        sel.removeAllRanges();
                                        sel.addRange(range);
                                        document.execCommand("copy");
                                        sel.removeAllRanges();
                                        btnCopy.textContent = "Copied!";
                                        setTimeout(() => (btnCopy.textContent = "Copy All"), 1200);
                                    }
                                });

                                // Download TXT
                                btnDownload.addEventListener("click", () => {
                                    const text = previewEl.textContent.trim();
                                    if (!text)
                                        return;
                                    const blob = new Blob([text + "\n"], {type: "text/plain;charset=utf-8"});
                                    const url = URL.createObjectURL(blob);
                                    const a = document.createElement("a");
                                    const base = (document.getElementById("assemblyNo").value || "codes").replace(/[^a-z0-9-_]+/gi, "_");
                                    a.href = url;
                                    a.download = `${base}.txt`;
                                    document.body.appendChild(a);
                                    a.click();
                                    URL.revokeObjectURL(url);
                                    a.remove();
                                });

                                // Initialize
                                updateMsgDate();
                                generate();
                            });
        </script>
    </s:layout-component>
</s:layout-render>