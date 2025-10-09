<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_title">
        <f:message key="general.label.home"/>
    </s:layout-component>
    <s:layout-component name="page_css">
<<<<<<< HEAD
        <!--<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"/>-->
        <!-- tagsCloud Keywords CSS -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/tagsCloud/tagsCloud.css">
=======
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/fullcalendar.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/fullcalendar.print.css" type="text/css" media="print" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/compiled/calendar.css" type="text/css" media="screen" />
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772
    </s:layout-component>
    <s:layout-component name="page_header">
        <f:message key="general.label.dashboard"/>
    </s:layout-component>
    <s:layout-component name="page_container">
<<<<<<< HEAD
        <!-- Content wrapper start -->
        <div class="content-wrapper">

            <!-- Row start -->
            <div class="row gx-4">
                <div class="col-xl-3 col-sm-6 col-12">
                    <div class="card mb-4 border-0 shadow-sm hover-shadow-md transition-300">
                        <div class="card-body p-3">
                            <div class="d-flex align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1 fw-light">Completed</h6>
                                    <h3 class="fw-bold mb-1">60</h3>
                                    <span class="badge bg-success-subtle text-success rounded-pill px-2 py-1">
                                        <i class="bi bi-arrow-up-right"></i> 12%
                                    </span>
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
                                    <h6 class="text-muted mb-1 fw-light">Pending</h6>
                                    <h3 class="fw-bold mb-1">20</h3>
                                    <span class="badge bg-warning-subtle text-warning rounded-pill px-2 py-1">
                                        <i class="bi bi-arrow-down-right"></i> 5%
                                    </span>
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
                                    <h6 class="text-muted mb-1 fw-light">Progress</h6>
                                    <h3 class="fw-bold mb-1">30</h3>
                                    <span class="badge bg-primary-subtle text-primary rounded-pill px-2 py-1">
                                        <i class="bi bi-arrow-up-right"></i> 8%
                                    </span>
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
                                    <h6 class="text-muted mb-1 fw-light">Closed</h6>
                                    <h3 class="fw-bold mb-1">80</h3>
                                    <span class="badge bg-info-subtle text-info rounded-pill px-2 py-1">
                                        <i class="bi bi-arrow-up-right"></i> 15%
                                    </span>
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
                    <div class="card mb-4">
                        <div class="card-header">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="m-0 fw-semibold">Compare</h5>
                                <div class="btn-group rounded-pill p-1 bg-light gap-1" role="group">
                                    <button type="button" class="btn btn-sm rounded-pill px-3 btn-outline-primary">Week</button>
                                    <button type="button" class="btn btn-sm rounded-pill px-3 btn-primary">Month</button>
                                    <button type="button" class="btn btn-sm rounded-pill px-3 btn-outline-primary">Year</button>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">

                            <!-- Row starts -->
                            <div class="row gx-4">
                                <div class="col-xxl-2 col-lg-3 col-sm-12 col-12">
                                    <div class="card border-0 h-100 shadow-sm hover-shadow-lg">
                                        <div class="card-body p-4">
                                            <div class="text-center">
                                                <h5 class="mb-4 fw-semibold text-muted">Weekly Stats</h5>
                                                <div class="my-4 p-3 rounded-4 bg-primary-subtle">
                                                    <h3 class="text-primary fw-bold mb-1">4,000</h3>
                                                    <h6 class="m-0 text-primary fw-medium">Claimed</h6>
                                                    <div class="mt-2">
                                                        <span class="badge bg-white text-primary rounded-pill px-3 py-2">
                                                            <i class="bi bi-arrow-up-right me-1"></i>12% Growth
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="my-4 p-3 rounded-4 bg-danger-subtle">
                                                    <h3 class="text-danger fw-bold mb-1">2,000</h3>
                                                    <h6 class="m-0 text-danger">Expired</h6>
                                                    <div class="mt-2">
                                                        <span class="badge bg-white text-danger rounded-pill px-3 py-2">
                                                            <i class="bi bi-arrow-down-right me-1"></i>5% Decline
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xxl-8 col-lg-6 col-sm-12 col-12">
                                    <div class="overflow-hidden">
                                        <div id="graph4" class="mt-2"></div>
                                    </div>
                                </div>
                                <div class="col-xxl-2 col-lg-3 col-sm-12 col-12">
                                    <div class="card border-0 h-100 shadow-sm hover-shadow-lg transition-300">
                                        <div class="card-body p-4">
                                            <div class="text-center">
                                                <h5 class="mb-4 fw-semibold text-muted">Monthly Stats</h5>
                                                <div class="my-4 p-3 rounded-4 bg-primary-subtle">
                                                    <h3 class="text-primary fw-bold mb-1">36,000</h3>
                                                    <h6 class="m-0 text-primary fw-medium">Claimed</h6>
                                                    <div class="mt-2">
                                                        <span class="badge bg-white text-primary rounded-pill px-3 py-2">
                                                            <i class="bi bi-arrow-up-right me-1"></i>18% Growth
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="my-4 p-3 rounded-4 bg-danger-subtle">
                                                    <h3 class="text-danger fw-bold mb-1">22,000</h3>
                                                    <h6 class="m-0 text-danger">Expired</h6>
                                                    <div class="mt-2">
                                                        <span class="badge bg-white text-danger rounded-pill px-3 py-2">
                                                            <i class="bi bi-arrow-down-right me-1"></i>7% Decline
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Row end -->

                        </div>
                    </div>
                </div>
                <div class="col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Compare</h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-4">
                                <div class="col-xxl-2 col-lg-3 col-sm-12 col-12">
                                    <div class="border rounded-2 p-3 h-100 d-flex justify-content-center align-items-center">
                                        <div class="text-center">
                                            <h5 class="mb-5">Africa</h5>
                                            <div class="my-4">
                                                <h4 class="text-primary">88M</h4>
                                                <h6 class="m-0">Visitors</h6>
                                            </div>
                                            <div class="my-4">
                                                <h4 class="text-secondary">$96B</h4>
                                                <h6 class="m-0">Sales</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xxl-8 col-lg-6 col-sm-12 col-12">
                                    <!-- Row start -->
                                    <div class="row align-items-center">
                                        <div class="col-sm-5 col-12">
                                            <div id="mapAfrica" class="chart-height-xl"></div>
                                        </div>
                                        <div class="col-sm-2 col-12">
                                            <div class="vs"></div>
                                        </div>
                                        <div class="col-sm-5 col-12">
                                            <div id="mapEurope" class="chart-height-xl"></div>
                                        </div>
                                    </div>
                                    <!-- Row end -->
                                </div>
                                <div class="col-xxl-2 col-lg-3 col-sm-12 col-12">
                                    <div class="border rounded-2 p-3 h-100 d-flex justify-content-center align-items-center">
                                        <div class="text-center">
                                            <h5 class="mb-5">Europe</h5>
                                            <div class="my-4">
                                                <h4 class="text-primary">48M</h4>
                                                <h6 class="m-0">Visitors</h6>
                                            </div>
                                            <div class="my-4">
                                                <h4 class="text-secondary">$69B</h4>
                                                <h6 class="m-0">Sales</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xxl-8 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Sales</h5>
                        </div>
                        <div class="card-body">
                            <div class="row gx-4">
                                <div class="col-lg-3 col-sm-12 col-12">
                                    <div class="border rounded-2 p-3 h-100 d-flex justify-content-center align-items-center">
                                        <div class="text-center">
                                            <h5 class="mb-5">Weekly</h5>
                                            <div class="my-4">
                                                <h4 class="text-primary">$3,200</h4>
                                                <h6 class="m-0">Direct</h6>
                                            </div>
                                            <div class="my-4">
                                                <h4 class="text-secondary">$7,100</h4>
                                                <h6 class="m-0">Online</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-sm-12 col-12">
                                    <div class="border rounded-2 p-3">
                                        <div class="scroll250">

                                            <div class="d-grid gap-3">
                                                <div class="d-flex">
                                                    <div>Apple Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>46,540</span>
                                                        <span class="text-primary">+2.005</span>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Google Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-down-right text-secondary"></i>
                                                        <span>8219</span>
                                                        <small class="text-secondary">-4.031</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Yahoo Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>3388</span>
                                                        <small class="text-primary">+7.652</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Facebook Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>4654</span>
                                                        <small class="text-primary">+11.98</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Ebay Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-down-right text-secondary"></i>
                                                        <span>2893</span>
                                                        <small class="text-secondary">-5.281</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Amazon Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-down-right text-secondary"></i>
                                                        <span>27880</span>
                                                        <small class="text-secondary">+7.318</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Microsoft Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>68964</span>
                                                        <small class="text-primary">+4.980</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Apple Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>46,540</span>
                                                        <span class="text-primary">+2.005</span>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Google Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-down-right text-secondary"></i>
                                                        <span>8219</span>
                                                        <small class="text-secondary">-4.031</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Yahoo Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>3388</span>
                                                        <small class="text-primary">+7.652</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Facebook Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>4654</span>
                                                        <small class="text-primary">+11.98</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Ebay Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-down-right text-secondary"></i>
                                                        <span>2893</span>
                                                        <small class="text-secondary">-5.281</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Amazon Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-down-right text-secondary"></i>
                                                        <span>27880</span>
                                                        <small class="text-secondary">+7.318</small>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <div>Microsoft Inc</div>
                                                    <div class="ms-auto d-flex gap-2">
                                                        <i class="bi bi-arrow-up-right text-primary"></i>
                                                        <span>68964</span>
                                                        <small class="text-primary">+4.980</small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-12 col-12">
                                    <div class="border rounded-2 p-3 h-100 d-flex justify-content-center align-items-center">
                                        <div class="text-center">
                                            <h5 class="mb-5">Monthly</h5>
                                            <div class="my-4">
                                                <h4 class="text-primary">$18,300</h4>
                                                <h6 class="m-0">Direct</h6>
                                            </div>
                                            <div class="my-4">
                                                <h4 class="text-secondary">$35,700</h4>
                                                <h6 class="m-0">Online</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xxl-4 col-lg-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Chart with Alert</h5>
                        </div>
                        <div class="card-body">
                            <div class="position-relative">
                                <span class="badge bg-primary position-absolute top-0 end-0 mt-n3">
                                    7 Orders Pending
                                </span>
                            </div>
                            <div id="graph1"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Chart with Buttons</h5>
                        </div>
                        <div class="card-body">
                            <div class="d-flex justify-content-end">
                                <div class="btn-group" role="group" aria-label="Basic outlined example">
                                    <button type="button" class="btn btn-outline-light">Month</button>
                                    <button type="button" class="btn btn-outline-light">Week</button>
                                    <button type="button" class="btn btn-outline-light">Today</button>
                                </div>
                            </div>
                            <div id="graph2"></div>
                        </div>
                    </div>
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Chart with Icons</h5>
                        </div>
                        <div class="card-body">
                            <div class="d-flex justify-content-end">
                                <div class="btn-group" role="group" aria-label="Basic outlined example">
                                    <button type="button" class="btn btn-outline-light">
                                        <i class="bi bi-cloud-download"></i>
                                    </button>
                                    <button type="button" class="btn btn-outline-light">
                                        <i class="bi bi-printer"></i>
                                    </button>
                                </div>
                            </div>
                            <div id="graph3"></div>
                        </div>
                    </div>
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div id="graph6"></div>
                            <!-- Row start -->
                            <div class="row gx-4">
                                <div class="col-sm-6 col-6">
                                    <div class="text-center">
                                        <h5>Claimed</h5>
                                        <h3 class="text-primary">3200</h3>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-6">
                                    <div class="text-center">
                                        <h5>Expired</h5>
                                        <h3 class="text-secondary">1500</h3>
                                    </div>
                                </div>
                            </div>
                            <!-- Row end -->
                        </div>
                    </div>
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div id="graph7"></div>
                            <div class="text-center">
                                <h3>7520</h3>
                                <h5 class="text-truncate fw-light">26% higher than last month.</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xxl-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Cutomers</h5>
                        </div>
                        <div class="card-body">

                            <div class="scroll300">
                                <!-- Row start -->
                                <div class="row gx-4">
                                    <div class="col-sm-6 col-12">
                                        <div class="d-grid gap-4 my-5">
                                            <div class="d-flex align-items-center">
                                                <i class="bi bi-person fs-1 text-primary"></i>
                                                <div class="ms-3">
                                                    <h5>Current Customers</h5>
                                                    <p class="m-0">Active 74%</p>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <i class="bi bi-person-check fs-1 text-secondary"></i>
                                                <div class="ms-3">
                                                    <h5>New Customers</h5>
                                                    <p class="m-0">Increased 21%</p>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <i class="bi bi-person-plus fs-1 text-secondary"></i>
                                                <div class="ms-3">
                                                    <h5>Targeted Customers</h5>
                                                    <p class="m-0">Increased 38%</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div id="graph8"></div>
                                    </div>
                                </div>
                                <!-- Row end -->
                            </div>

                        </div>
                    </div>
                </div>
                <div class="col-xxl-3 col-sm-6 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Logs</h5>
                        </div>
                        <div class="card-body">
                            <div class="scroll300">
                                <div class="d-grid gap-3">
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">New item sold</div>
                                        <div class="ms-auto">10:10</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Notification from bank</div>
                                        <div class="ms-auto">05:25</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Transaction success alert</div>
                                        <div class="ms-auto">09:45</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Your item has been updated</div>
                                        <div class="ms-auto">06:50</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">New order</div>
                                        <div class="ms-auto">12:30</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Item bought</div>
                                        <div class="ms-auto">04:22</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">New sale: Messi Wills</div>
                                        <div class="ms-auto">10:10</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Order received</div>
                                        <div class="ms-auto">12:55</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Service information</div>
                                        <div class="ms-auto">09:12</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Message from Wilson</div>
                                        <div class="ms-auto">09:27</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">New item sale: Joy Root</div>
                                        <div class="ms-auto">02:39</div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="bi bi-play-circle-fill text-primary"></div>
                                        <div class="ms-2">Product update</div>
                                        <div class="ms-auto">08:22</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xxl-3 col-sm-6 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Keywords</h5>
                        </div>
                        <div class="card-body">
                            <div id="tagscloud">
                                <a href="reports.html" class="tagc1">Analytics</a>
                                <a href="reports.html" class="tagc2">Tasks</a>
                                <a href="index.html" class="tagc3">Sales</a>
                                <a href="#" class="tagc4">Bootstrap</a>
                                <a href="#" class="tagc1">Scss</a>
                                <a href="#" class="tagc2">Bootstrap</a>
                                <a href="index.html" class="tagc3">Admin</a>
                                <a href="index.html" class="tagc4">Dashboard</a>
                                <a href="#" class="tagc1">Creative</a>
                                <a href="#" class="tagc2">Rising Stars</a>
                                <a href="reports.html" class="tagc3">BS Admin</a>
                                <a href="#" class="tagc4">Top Rated</a>
                                <a href="#" class="tagc1">Admin</a>
                                <a href="#" class="tagc2">Creative</a>
                                <a href="#" class="tagc3">Best Selling</a>
                                <a href="#" class="tagc4">Awesome</a>
                                <a href="#" class="tagc1">jQuery</a>
                                <a href="#" class="tagc2">Hot Under $19</a>
                                <a href="reports.html" class="tagc3">High</a>
                                <a href="#" class="tagc4">Low Price</a>
                                <a href="#" class="tagc1">Top Selling</a>
                                <a href="index.html" class="tagc2">Best Admin</a>
                                <a href="#" class="tagc3">Popular</a>
                                <a href="#" class="tagc1">Best Sellers</a>
                                <a href="index.html" class="tagc2">eCommerce</a>
                                <a href="reports.html" class="tagc3">Analytics</a>
                                <a href="#" class="tagc4">Rising Stars</a>
                                <a href="tasks.html" class="tagc1">Crm</a>
                                <a href="#" class="tagc2">Sass</a>
                                <a href="#" class="tagc3">Template Monster</a>
                                <a href="index.html" class="tagc4">Dashboard</a>
                                <a href="#" class="tagc1">Admin</a>
                                <a href="reports.html" class="tagc2">Creative</a>
                                <a href="#" class="tagc3">Template Monster</a>
                                <a href="#" class="tagc4">Theme</a>
                                <a href="#" class="tagc1">Dashboard</a>
                                <a href="#" class="tagc2">Rising stars</a>
                                <a href="#" class="tagc3">Template</a>
                                <a href="index.html" class="tagc4">Top Rated</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Earnings</h5>
                        </div>
                        <div class="card-body">
                            <!-- Row start -->
                            <div class="row gx-4">
                                <div class="col-xxl-8 col-sm-8 col-12">
                                    <div id="world-map-markers2" class="chart-height-xl"></div>
                                </div>
                                <div class="col-sm-4 col-12">
                                    <div class="d-flex align-items-center mb-2">
                                        <i class="bi bi-globe display-5 text-primary me-2"></i>
                                        <h3 class="m-0">$6,99,000</h3>
                                        <i class="bi bi-arrow-up-right text-primary ms-2"></i>
                                    </div>
                                    <p>This dashboard unquestionably the largest visitors in the world with TWO million monthly active
                                        users and ONE million daily active.</p>
                                    <a href="index.html" class="btn btn-primary">View Stats</a>
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
            <span>© HEATs 2025</span>
        </div>
        <!-- App footer end -->
    </s:layout-component>
    <s:layout-component name="page_js">

        <!-- Apex Charts -->
        <script src="${contextPath}/resources/statflow/vendor/apex/apexcharts.min.js"></script>
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

    </s:layout-component>
    <s:layout-component name="page_js_inline">

<!--        <script>
            var options1 = {
                series: [{
                        name: 'Completed',
                        data: [1, 2, 3, 2, 3]
                    }],
                chart: {
                    type: 'line',
                    width: 130,
                    height: 75,
                    sparkline: {
                        enabled: true
                    },
                },
                colors: ['#507dff'],
                stroke: {
                    curve: 'smooth',
                    width: 7,
                },
                fill: {
                    type: 'gradient',
                    gradient: {
                        shade: 'light',
                        type: 'vertical',
                        shadeIntensity: 0.5,
                        gradientToColors: ['#8e9fff'],
                        inverseColors: false,
                        opacityFrom: 0.8,
                        opacityTo: 0.2,
                    }
                },
                tooltip: {
                    fixed: {
                        enabled: false
                    },
                    x: {
                        show: false
                    },
                    marker: {
                        show: false
                    }
                },
                xaxis: {
                    type: 'day',
                    categories: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val
                        }
                    }
                },
            };
            var chart1 = new ApexCharts(document.querySelector("#taskStats1"), options1);
            chart1.render();
        </script>-->

=======
        <div class="col-lg-12">
            <div class="main-box">
                <div id="calendar"></div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/jquery-ui.custom.min.js"></script>
        <script src="${contextPath}/resources/private/js/fullcalendar.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                /* initialize the external events
                 -----------------------------------------------------------------*/

                $('#external-events div.external-event').each(function () {

                    // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
                    // it doesn't need to have a start or end
                    var eventObject = {
                        title: $.trim($(this).text()) // use the element's text as the event title
                    };

                    // store the Event Object in the DOM element so we can get to it later
                    $(this).data('eventObject', eventObject);

                    // make the event draggable using jQuery UI
                    $(this).draggable({
                        zIndex: 999,
                        revert: true, // will cause the event to go back to its
                        revertDuration: 0  //  original position after the drag
                    });

                });


                /* initialize the calendar
                 -----------------------------------------------------------------*/

                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();

                var calendar = $('#calendar').fullCalendar({
                    contentHeight: 600,
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    selectable: true,
                    selectHelper: true,
                    select: function (start, end, allDay) {
                        var title = prompt('Event Title:');
                        if (title) {
                            calendar.fullCalendar('renderEvent',
                                    {
                                        title: title,
                                        start: start,
                                        end: end,
                                        allDay: allDay
                                    },
                            true // make the event "stick"
                                    );
                        }
                        calendar.fullCalendar('unselect');
                    },
                    editable: true,
                    droppable: true, // this allows things to be dropped onto the calendar !!!
                    drop: function (date, allDay) { // this function is called when something is dropped

                        // retrieve the dropped element's stored Event Object
                        var originalEventObject = $(this).data('eventObject');

                        // we need to copy it, so that multiple events don't have a reference to the same object
                        var copiedEventObject = $.extend({}, originalEventObject);

                        // assign it the date that was reported
                        copiedEventObject.start = date;
                        copiedEventObject.allDay = allDay;

                        // copy label class from the event object
                        var labelClass = $(this).data('eventclass');

                        if (labelClass) {
                            copiedEventObject.className = labelClass;
                        }

                        // render the event on the calendar
                        // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                        $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

                        // is the "remove after drop" checkbox checked?
                        if ($('#drop-remove').is(':checked')) {
                            // if so, remove the element from the "Draggable Events" list
                            $(this).remove();
                        }

                    },
                    buttonText: {
                        prev: '<i class="fa fa-chevron-left"></i>',
                        next: '<i class="fa fa-chevron-right"></i>'
                    },
                    events: [
                        <c:forEach items="${courseRegisterList}" var="courseRegister">
                            {
                                title: '${courseRegister.courseCode} - ${courseRegister.title}',
                                start: '${courseRegister.startDate}',
                                end: '${courseRegister.endDate}',
                                className: '${courseRegister.calendarLabel}'
                            },
                        </c:forEach>
                    ]
                });
            });
        </script>
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772
    </s:layout-component>
</s:layout-render>