<%-- 
    Document   : scan
    Created on : Jun 8, 2026, 11:22:12 AM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">-->
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Scan Module</h5>
                        </div>
                        <div class="card-body">
                            <form id="scanForm" class="row g-3 align-items-center" role="form" method="post">
                                <div class="row mb-3">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold">Scan QR Here</label>
                                    <div class="col-sm-3 col-md-3">
                                        <input type="text" class="form-control" name="scan_qr" id="scan_qr">
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-primary">Go</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="app-footer">
                <img class="img3" src="${contextPath}/resources/onsemi logo.webp" alt="onsemi">
                <span>© HEATs 2025</span>
            </div>
        </div>
</s:layout-component>
<s:layout-component name="page_js">
    <!-- Data Tables -->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.bootstrap.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/custom/custom-datatables.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/jszip.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.buttons.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/pdfmake.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/vfs_fonts.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.html5.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.print.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/datatables/buttons/buttons.colVis.min.js"></script>-->

    <!-- Bootstrap Select JS -->
    <!--<script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select.min.js"></script>-->
    <!--<script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select-custom.js"></script>-->
</s:layout-component>
<s:layout-component name="page_js_inline">
    <script>
        
        document.getElementById("scanForm").addEventListener("submit", function(e) {
            e.preventDefault();
            const scanValue = document.getElementById("scan_qr").value.trim();
            if (scanValue) {
                window.location.href = "${contextPath}" + scanValue;
            }
        });

    </script>
</s:layout-component>
</s:layout-render>