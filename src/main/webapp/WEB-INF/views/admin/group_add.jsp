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
                            <h5 class="card-title">Group - Add</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form id="add_group_form" class="row g-3 align-items-center" role="form" action="${contextPath}/admin/group/save" method="post">
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="groupCode">Code</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="groupCode" name="groupCode" placeholder="Code" value="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-1 col-form-label fw-semibold" for="groupName">Name</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="groupName" name="groupName" placeholder="Name" value="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Form actions start -->
                                <div class="col-md-12">
                                    <!--<div class="justify-content-end">-->
                                    <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                    <!--</div>-->
                                    <!--<div class="justify-content-start">-->
                                    <!--<button type="button" class="btn btn-light">Cancel</button>-->
                                    <a href="${contextPath}/admin/group" class="btn btn-dark float-start">Back</a>
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
        <!--</div>-->
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
        
         <!-- JQuery Validation -->
    <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>

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
                var validator = $("#add_group_form").validate({
                    rules: {
                        groupCode: {
                            required: true
                        },
                        groupName: {
                            required: true
                        }
                    }
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>