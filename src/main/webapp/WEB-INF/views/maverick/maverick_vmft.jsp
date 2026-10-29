<%-- 
    Document   : maverick_vmft
    Created on : Oct 14, 2026, 10:02:45 AM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/daterange/daterange.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/css/animate.css">
        <link rel="stylesheet" href="${contextPath}/resources/css/maverick_002.css"/>
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Hardware Module - <span style="color:#D97D55">New Hardware Registration</span></h5>
                        </div>
                        <div class="card-body">
                            <div class="custom-tabs-container">
                                <ul class="fs-6 nav nav-tabs justify-content-center" id="customTab4" role="tablist">
                                    <li>
                                        <a class="nav-link" href="${contextPath}/maverick/maverickdetails/${itemmaverick.id}/${itemmaverick.module}/${itemmaverick.mibItemId}"><i class="bi bi-person-badge"></i>Maverick Details</a>
                                    </li>
                                    <li>
                                        <a class="nav-link" id="tab-twoAAA" data-bs-toggle="tab" href="#twoAAA" role="tab" aria-controls="twoAAA" aria-selected="false"><i class="bi bi-search"></i>Visual Inspection Check</a>
                                    </li>
                                    <li class="disabled">
                                        <a class="nav-link" id="tab-threeAAA" data-bs-toggle="tab" href="#threeAAA" role="tab" aria-controls="threeAAA" aria-selected="false"><i class="bi bi-clipboard-check"></i>Functional Test</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="customTabContent">
                                    <div class="tab-pane fade" id="oneAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            &nbsp;
                                        </div> 
                                    </div> 
                                    <div class="tab-pane active" id="twoAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <input type="hidden" name="maverickId" value="${itemmaverick.id}">
                                            <input type="hidden" name="module" value="${itemmaverick.module}">
                                            <input type="hidden" name="mibItemId" value="${itemmaverick.mibItemId}">
                                            <input type="hidden" name="itemStatus" value="Pending Visual Inspection (from Maverick)">
                                            <%@ include file="redo_vm.jsp" %>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="threeAAA" role="tabpanel">
                                        <div class="row gx-4">
                                            <div class="col-12">
                                                &nbsp;
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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

        <!-- Date Range JS -->
        <script src="${contextPath}/resources/statflow/vendor/daterange/daterange.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/daterange/custom-daterange.js"></script>

        <!-- Data Tables -->
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/dataTables.bootstrap.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/datatables/custom/custom-datatables.js"></script>
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
            $(document).on("change", "input[type='radio']", function () {
                const section = $(this).attr("name");
                const isFail = $('input[name="' + section + '"]:checked').val() === "Fail";
                $('input[name="' + section + 'RejectQty"]').prop("disabled", !isFail).prop("required", isFail);
                $("#" + section + "Reject").prop("disabled", !isFail).prop("required", isFail);
                $("#" + section + "RejectUpload").prop("disabled", !isFail).prop("required", isFail);
            });
            
            const sections = [
                "pcb",
                "handle",
                "metalFrame",
                "hardwareFasterners",
                "clipHolder",
                "pcbEdgeFinger",
                "connector",
                "dutSockets",
                "edgeMbBanana",
                "electComponent",
                "solderJoint",
                "winConnector",
                "teflonConnector",
                "pogoReceptaclesPin",
                "cableWiredCopperWire",
                "labelIdentification"
            ];

            function checkAllRadioSelected() {
                let allSelected = true;
                sections.forEach(function(section) {
                    if (!$('input[name="' + section + '"]:checked').length) {
                        allSelected = false;
                    }
                });
                $("#submit").prop("disabled", !allSelected);
            }

            $(document).on("change", "input[type='radio']", function () {
                checkAllRadioSelected();
            });

            // Initial check
            checkAllRadioSelected();
            
//            $(document).ready(function () {
//                var element = $('#groupId');
//                if (element.val() !== '1' && element.val() !== '2' && element.val() !== '12' && element.val() !== '13') {
//                    $("#submitHw").attr("disabled", true);
//                } else {
//                    $("#submitHw").removeAttr('disabled');
//                }
//
//                var element2 = $('#viId');
//                if (element2.val()) {
//                    $("#submitVm").attr("disabled", true);
//                }
//            });
        </script>
    </s:layout-component>
</s:layout-render>