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
                        <a href="${contextPath}/rmsbookingDetail" class="btn btn-outline-warning me-2" role="button">
                            <i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">HW Prep For Loading Module - <span style="color:#D97D55">Detail</span></h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/xde apa pon" method="post">
                                <div class="row mb-3">
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">RMS</label>
                                            <div class="input input-group">
                                                <input type="hidden" class="form-control" id="id" name="id" placeholder="" value="${rms.id}">
                                                <input type="hidden" class="form-control" id="bookingPkid" name="bookingPkid" placeholder="" value="${rms.bookingPkid}">
                                                <input type="text" class="form-control" id="rmsNo" name="rmsNo" placeholder="" value="${rms.rmsNo}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Event</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="event" name="event" placeholder="" value="${rms.event}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Device</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="device" name="device" placeholder="" value="${rms.device}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Package</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="packages" name="packages" placeholder="" value="${rms.packages}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Actual Start Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="actStartDate" name="actStartDate" placeholder="" value="${rms.actStartDate}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">RMS Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="rmsStatus" name="rmsStatus" placeholder="" value="${rms.rmsStatus}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Equipment Location</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="equipmentLocation" name="equipmentLocation" placeholder="" value="${rms.equipmentLocation}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Est Event Start Date</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="eventStartDate" name="eventStartDate" placeholder="" value="${rms.eventStartDate}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Days to Event Start</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="daysToEventStart" name="daysToEventStart" placeholder="" value="${rms.daysToEventStart}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-2 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Status</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${rms.status}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-1 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Priority</label>
                                            <div class="input input-group">
                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="" value="${rms.priority}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">Priority Remarks</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="5" id="remarks" name="remarks" readonly>${rms.priorityRemarks}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3 col-sm-12 col-12">
                                        <div class="mb-1">
                                            <label for="itemId" class="form-label">FOL filename</label>
                                            <div class="input input-group">
                                                <textarea class="form-control" rows="5" id="remarks" name="remarks" readonly>${rms.folFilename}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <!-- Form actions start -->
                                <!--                                <div class="col-md-12">
                                                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Search</button>
                                                                </div>-->
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

                <div class="col-sm-5 col-12">

                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-list-ul me-2"></i>List of Hardware (Motherboard)
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons1" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <!--<th class="col-12">Site</th>-->
                                                    <th>No</th>
                                                    <th>Item Type</th>
                                                    <th>Item ID</th>
                                                    <th>Status</th>
                                                    <th>Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
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
                <div class="col-sm-7 col-12">
                    <div class="card mb-4">
                        <div class="card-body">

                            <!-- Row start -->
                            <div class="row gx-3">
                                <!-- Personal Information Section -->
                                <div class="col-12 mb-3">
                                    <h6 class="fw-semibold mb-3 border-start border-primary ps-2"
                                        style="border-left-width: 3px !important;">
                                        <i class="bi bi-list-ul me-2"></i>List of Hardware (Other Support Items}
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="customButtons2" class="table custom-table pending">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Item Type</th>
                                                    <th>Sub Type</th>
                                                    <th>Item ID</th>
                                                    <th>Qty</th>
                                                    <th>Status</th>
                                                    <th>Detail</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!-- Row end -->
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

        $(document).ready(function () {
            $('.js-example-basic-single').select2();
        });

        $(function () {
            $("#customButtons1").DataTable({
                lengthMenu: [
                    [10, 25, 50],
                    [10, 25, 50, "All"],
                ],
                language: {
                    lengthMenu: "Display _MENU_ Records Per Page",
                    info: "Showing Page _PAGE_ of _PAGES_",
                },
                dom: "Blfrtip",
                buttons: ["copy", "csv", "pdf", "print"],
            });
        });

        $(function () {
            $("#customButtons2").DataTable({
                lengthMenu: [
                    [10, 25, 50],
                    [10, 25, 50, "All"],
                ],
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