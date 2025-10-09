<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_title">
        <f:message key="general.label.home"/>
    </s:layout-component>
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"/>-->
        <!-- tagsCloud Keywords CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/tagsCloud/tagsCloud.css">
        <!-- Data Tables -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .font-link-rms {
                font-weight: bold;
                color:blue;
                font-style: italic;
                text-decoration: underline;
            }

            .pending thead th {
                background-color: #f06a0a; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

            .after thead th {
                background-color: #D97D55; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

            .mpe thead th {
                background-color: #5ec3f1; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

            .new thead th {
                background-color: #59AC77; /* Light blue */
                color: #FFFFFF; /* White text for contrast */
            }

/*            .img3 {
                width: 55px;  Sets a fixed width 
                height: 18px;  Sets a fixed height 
            }

            .app-footer2 {
                position: fixed;
                bottom: 0;
                right: 0;
                font-size: 0.7rem;
                margin: 0;
                padding: 15px 20px 0 20px;
                display: flex;
                justify-content: flex-end;
            }*/
        </style>
    </s:layout-component>
    <s:layout-component name="page_header">
        <f:message key="general.label.dashboard"/>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-xl-3 col-sm-6 col-12">
                    <div class="card mb-4 border-0 shadow-sm hover-shadow-md transition-300">
                        <div class="card-body p-3">
                            <div class="d-flex align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1 fw-light">RMS Pending for Loading</h6>
                                    <h3 class="fw-bold mb-1">20</h3>
                                    <!--                                    <span class="badge bg-success-subtle text-success rounded-pill px-2 py-1">
                                                                            <i class="bi bi-arrow-up-right"></i> 12%
                                                                        </span>-->
                                </div>
                                <div id="taskStats1" class="ms-auto"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-sm-6 col-12">
                    <div class="card mb-4 border-0 shadow-sm hover-shadow-md transition-300">
                        <div class="card-body p-3">
                            <div class="d-flex align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1 fw-light">RMS Return from Loading</h6>
                                    <h3 class="fw-bold mb-1">7</h3>
                                    <!--                                    <span class="badge bg-warning-subtle text-warning rounded-pill px-2 py-1">
                                                                            <i class="bi bi-arrow-down-right"></i> 5%
                                                                        </span>-->
                                </div>
                                <div id="taskStats2" class="ms-auto"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-sm-6 col-12">
                    <div class="card mb-4 border-0 shadow-sm hover-shadow-md transition-300">
                        <div class="card-body p-3">
                            <div class="d-flex align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1 fw-light">MPE</h6>
                                    <h3 class="fw-bold mb-1">4</h3>
                                    <!--                                    <span class="badge bg-primary-subtle text-primary rounded-pill px-2 py-1">
                                                                            <i class="bi bi-arrow-up-right"></i> 8%
                                                                        </span>-->
                                </div>
                                <div id="taskStats3" class="ms-auto"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-sm-6 col-12">
                    <div class="card mb-4 border-0 shadow-sm hover-shadow-md transition-300">
                        <div class="card-body p-3">
                            <div class="d-flex align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1 fw-light">New HW Fabrication</h6>
                                    <h3 class="fw-bold mb-1">3</h3>
                                    <!--                                    <span class="badge bg-info-subtle text-info rounded-pill px-2 py-1">
                                                                            <i class="bi bi-arrow-up-right"></i> 15%
                                                                        </span>-->
                                </div>
                                <div id="taskStats4" class="ms-auto"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Row end -->

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">RMS <span style="color:#50589C">Pending for</span> Loading (CBMS)</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVertical" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>RMS No</th>
                                            <th>Actual Start Date</th>
                                            <th>Device</th>
                                            <th>Package</th>
                                            <th>Event</th>
                                            <th>Est Event Start Date</th>
                                            <th>RMS Status</th>
                                            <th>Event Begin Status</th>
                                            <th>Days to Event Start</th>
                                            <!--<th>Manage</th>-->
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>25-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>30-Sep-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>5</td>
                                            <!--                                            <td>
                                                                                            <a class="btn btn-primary btn-sm me-1" href="#"><i class="bi bi-pencil"></i></a>
                                                                                        </td>-->
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106696</a></td>
                                            <td>25-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTRB</td>
                                            <td>30-Sep-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>5</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106696</a></td>
                                            <td>25-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>IOL</td>
                                            <td>12-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>17</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S107174</a></td>
                                            <td>27-Sep-25</td>
                                            <td>SNXH150B120H3Q2F2PG</td>
                                            <td>PIM40 93*47 (PRESS FIT)</td>
                                            <td>H3TRB</td>
                                            <td>01-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>4</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106511</a></td>
                                            <td>28-Sep-25</td>
                                            <td>ENGNVMFS1D8N10XT1G-TST</td>
                                            <td>SO8FL - CLIP DFN 6 5*6*1MM PBFR</td>
                                            <td>HTRB</td>
                                            <td>01-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>3</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106992</a></td>
                                            <td>28-Sep-25</td>
                                            <td>NCV2561SQT1G</td>
                                            <td>SC-88 6 EPO SNGL PB FREE</td>
                                            <td>HAST</td>
                                            <td>19-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>21</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106992</a></td>
                                            <td>28-Sep-25</td>
                                            <td>NCV2561SQT1G</td>
                                            <td>SC-88 6 EPO SNGL PB FREE</td>
                                            <td>HTOL</td>
                                            <td>05-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>7</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104684</a></td>
                                            <td>29-Sep-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HAST</td>
                                            <td>14-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>15</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104684</a></td>
                                            <td>29-Sep-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HTGB</td>
                                            <td>05-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>6</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104684</a></td>
                                            <td>29-Sep-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HTGBx</td>
                                            <td>03-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>4</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104684</a></td>
                                            <td>29-Oct-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HTRB</td>
                                            <td>02-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>3</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104684</a></td>
                                            <td>29-Oct-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>IOL</td>
                                            <td>15-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>16</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S105952</a></td>
                                            <td>30-Sep-25</td>
                                            <td>PCNVSR19S120M3ECPE-08</td>
                                            <td>* NOT APPLICABLE *</td>
                                            <td>HTRB</td>
                                            <td>02-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>2</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S105952</a></td>
                                            <td>30-Sep-25</td>
                                            <td>PCNVSR19S120M3ECPE-08</td>
                                            <td>* NOT APPLICABLE *</td>
                                            <td>HTRBx</td>
                                            <td>02-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>2</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106877</a></td>
                                            <td>30-Sep-25</td>
                                            <td>SURS8340T3G-GA01</td>
                                            <td>SMC</td>
                                            <td>h3TRB</td>
                                            <td>17-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>17</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S106877</a></td>
                                            <td>30-Sep-25</td>
                                            <td>SURS8340T3G-GA01</td>
                                            <td>SMC</td>
                                            <td>IOL</td>
                                            <td>17-Oct-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>17</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104795</a></td>
                                            <td>01-Oct-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HAST</td>
                                            <td>26-Sep-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>-5</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104795</a></td>
                                            <td>01-Oct-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HTGB</td>
                                            <td>26-Sep-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>-5</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104795</a></td>
                                            <td>01-Oct-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HTGBx</td>
                                            <td>26-Sep-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>-5</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href="">S104795</a></td>
                                            <td>01-Oct-25</td>
                                            <td>NVTFWS005N08XLTAG</td>
                                            <td>WDFN8 3.3*3.3*0.65</td>
                                            <td>HTGBy</td>
                                            <td>26-Sep-25</td>
                                            <td>IN PROCESS</td>
                                            <td>NOT START</td>
                                            <td>-5</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <div class="col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">RMS <span style="color:#D97D55">Return from</span> Loading (LRT)</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVertical2" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>RMS No</th>
                                            <th>Event Start Date</th>
                                            <th>Event End Date</th>
                                            <th>Device</th>
                                            <th>Package</th>
                                            <th>Event</th>
                                            <th>RMS Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                            <!--                                            <td>
                                                                                            <a class="btn btn-primary btn-sm me-1" href="#"><i class="bi bi-pencil"></i></a>
                                                                                        </td>-->
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> S106696</a></td>
                                            <td>01-Sep-25</td>
                                            <td>30-Sep-25</td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>IN PROCESS</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <div class="col-sm-12 col-md-6">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <!--<h5 class="card-title" style="color:#5ec3f1">MPE</h5>-->
                            <h5 class="card-title">MPE</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVertical3" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>Hardware Type</th>
                                            <th>Hardware ID</th>
                                            <th>MPE Category</th>
                                            <th>MPE Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Motherboard</td>
                                            <td><a class="font-link-rms" href=""> AVI111-01</a></td>
                                            <td>VM After Loading</td>
                                            <td>30-Sep-25</td>
                                            <td>Pending Disposition</td>
                                        </tr>
                                        <tr>
                                            <td>Motherboard</td>
                                            <td><a class="font-link-rms" href=""> AVI111-01</a></td>
                                            <td>VM After Loading</td>
                                            <td>30-Sep-25</td>
                                            <td>Pending Disposition</td>
                                        </tr>
                                        <tr>
                                            <td>Motherboard</td>
                                            <td><a class="font-link-rms" href=""> AVI111-01</a></td>
                                            <td>VM After Loading</td>
                                            <td>30-Sep-25</td>
                                            <td>Pending Disposition</td>
                                        </tr>
                                        <tr>
                                            <td>Motherboard</td>
                                            <td><a class="font-link-rms" href=""> AVI111-01</a></td>
                                            <td>VM After Loading</td>
                                            <td>30-Sep-25</td>
                                            <td>Pending Disposition</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <div class="col-sm-12 col-md-6">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">New HW Fabrication</h5>
                            <!--<h5 class="card-title" style="color:#59AC77">New HW Fabrication</h5>-->
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="scrollVertical4" class="table pending custom-table">
                                    <thead>
                                        <tr>
                                            <th>Hardware Type</th>
                                            <th>Device</th>
                                            <th>Package</th>
                                            <th>Event</th>
                                            <th>Requestor</th>
                                            <th>Request Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> Motherboard</a></td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>Atiqah</td>
                                            <td>01-09-2025</td>
                                            <td>Pending PR</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> Motherboard</a></td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>IOL</td>
                                            <td>Hakim</td>
                                            <td>01-09-2025</td>
                                            <td>Pending PO</td>
                                        </tr>
                                        <tr>
                                            <td><a class="font-link-rms" href=""> Motherboard</a></td>
                                            <td>NVTFWS027N10MCLTAG</td>
                                            <td>S08FL</td>
                                            <td>HTGB</td>
                                            <td>Atiqah</td>
                                            <td>01-09-2025</td>
                                            <td>Fabrication in Process</td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Card end -->
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">MPE 2025</h5>
                        </div>
                        <div class="card-body">

                            <div class="scroll370">
                                <div class="overflow-hidden">
                                    <div id="demography2" class="auto-align-graph"></div>
                                </div>

                                <!--                                <div class="bg-light rounded-5 p-3 mt-3">
                                                                    <div class="d-flex align-items-center">
                                                                        <div class="icon-box sm bg-primary rounded-5 me-2">
                                                                            <i class="bi bi-percent"></i>
                                                                        </div>
                                                                        <p class="m-0">Conversion rate is 20% higher than last week.</p>
                                                                    </div>
                                                                </div>-->
                            </div>

                        </div>
                    </div>
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">RMS Before & After Loading 2025</h5>
                        </div>
                        <div class="card-body">
                            <div id="basic-bar-graph-grouped2"></div>
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
        <!-- App footer end -->
    </s:layout-component>
    <s:layout-component name="page_js">

        <!-- Apex Charts -->
        <script src="${contextPath}/resources/statflow/vendor/apex/apexcharts.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/repotrs/demography.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/examples/bar/basic-bar-graph-grouped.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph1.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph2.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph3.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph4.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph5.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph6.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph7.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/apex/custom/widgets/graph8.js"></script>

        <!-- jVector Maps -->
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/jquery-jvectormap-2.0.5.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/gdp-data.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/world-mill-en.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/africa-mill.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/europe-mill.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/custom/map-europe.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/custom/map-africa.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/jvectormap/custom/world-map-markers2.js"></script>

        <!-- jQcloud Keywords -->
        <script src="${contextPath}/resources/statflow/vendor/tagsCloud/tagsCloud.js"></script>

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

    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            
            //RMS Return from Loading
            $(function () {
$("#scrollVertical2").DataTable({
        scrollY: "207px",
        scrollCollapse: false,
        paging: false,
        bInfo: false,
});
});

       $(function () {
$("#scrollVertical3").DataTable({
        scrollY: "150px",
        scrollCollapse: false,
        paging: false,
        bInfo: false,
});
});

       $(function () {
$("#scrollVertical4").DataTable({
        scrollY: "150px",
        scrollCollapse: false,
        paging: false,
        bInfo: false,
});
});

var options = {
  series: [42, 47, 52, 58, 65],
  chart: {
    width: 370,
    height: 370,
    type: 'polarArea',
    fontFamily: 'Poppins, sans-serif',
    toolbar: {
      show: false
    },
    animations: {
      enabled: true,
      easing: 'easeinout',
      speed: 800
    }
  },
  labels: ['VM Before Loading', 'BIB Test before Loading', 'Abnormal Loading', 'Ionic Test', 'VM After Loading'],
  fill: {
    opacity: 0.85,
    gradient: {
      enabled: true
    }
  },
  stroke: {
    width: 2,
    colors: ['#ffffff']
  },
  colors: ["#50589C", "#D97D55", "#5ec3f1", "#9DB6FF", "#59AC77"],
  yaxis: {
    show: false
  },
  legend: {
    position: 'bottom',
    fontSize: '14px',
    markers: {
      radius: 3
    }
  },
  tooltip: {
    y: {
      formatter: function (val) {
//        return val + " Million"
         return val
      }
    },
    theme: 'dark'
  },
  dataLabels: {
    enabled: true,
    formatter: function (val) {
      return Math.round(val) + "%"
    },
    style: {
      fontSize: '12px',
      fontWeight: 'bold'
    }
  },
  plotOptions: {
    polarArea: {
      rings: {
        strokeWidth: 0
      },
      spokes: {
        strokeWidth: 0
      },
      offsetY: 0,
      offsetX: 0
    }
  },
  responsive: [{
    breakpoint: 480,
    options: {
      chart: {
        width: 280
      },
      legend: {
        position: 'bottom'
      }
    }
  }]
};

var chart = new ApexCharts(document.querySelector("#demography2"), options);
chart.render();

var optionsss = {
  chart: {
    height: 355,
    type: 'bar',
    toolbar: {
      show: false,
    },
  },
  plotOptions: {
    bar: {
      horizontal: true,
      dataLabels: {
        position: 'top',
      },
    }
  },
  dataLabels: {
    enabled: true,
    offsetX: -6,
    style: {
      fontSize: '12px',
      colors: ['#fff']
    }
  },
  stroke: {
    show: true,
    width: 0,
  },
  series: [{
    data: [44, 55, 41, 64, 22,11,43,50,66,12,33,45]
  }, {
    data: [53, 32, 33, 44, 32,32,12,5,34,12,47,6]
  }],
  xaxis: {
    categories: ["Jan", "Feb", "Mar", "Apr", "May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
  },
  grid: {
    borderColor: '#ffe4a7',
    strokeDashArray: 5,
    xaxis: {
      lines: {
        show: true
      }
    },
    yaxis: {
      lines: {
        show: false,
      }
    },
    padding: {
      top: 0,
      right: 0,
      bottom: 0,
      left: 0
    },
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
}
var chartss = new ApexCharts(
  document.querySelector("#basic-bar-graph-grouped2"),
  optionsss
);
chartss.render();

//                    var options1 = {
//                        series: [{
//                                name: 'Completed',
//                                data: [1, 2, 3, 2, 3]
//                            }],
//                        chart: {
//                            type: 'line',
//                            width: 130,
//                            height: 75,
//                            sparkline: {
//                                enabled: true
//                            },
//                        },
//                        colors: ['#507dff'],
//                        stroke: {
//                            curve: 'smooth',
//                            width: 7,
//                        },
//                        fill: {
//                            type: 'gradient',
//                            gradient: {
//                                shade: 'light',
//                                type: 'vertical',
//                                shadeIntensity: 0.5,
//                                gradientToColors: ['#8e9fff'],
//                                inverseColors: false,
//                                opacityFrom: 0.8,
//                                opacityTo: 0.2,
//                            }
//                        },
//                        tooltip: {
//                            fixed: {
//                                enabled: false
//                            },
//                            x: {
//                                show: false
//                            },
//                            marker: {
//                                show: false
//                            }
//                        },
//                        xaxis: {
//                            type: 'day',
//                            categories: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
//                        },
//                        tooltip: {
//                            y: {
//                                formatter: function (val) {
//                                    return val
//                                }
//                            }
//                        },
//                    };
//                    var chart1 = new ApexCharts(document.querySelector("#taskStats1"), options1);
//                    chart1.render();
        </script>

    </s:layout-component>
</s:layout-render>