<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>

<s:layout-definition>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <title>HEATs</title>

            <!-- Animated css -->
            <link rel="stylesheet" href="${contextPath}/resources/statflow/css/animate.css">

            <!-- Bootstrap font icons css -->
            <link rel="stylesheet" href="${contextPath}/resources/statflow/fonts/bootstrap/bootstrap-icons.css">

            <!-- Main css -->
            <link rel="stylesheet" href="${contextPath}/resources/statflow/css/main.css">
             <!--<link rel="stylesheet" href="${contextPath}/resources/statflow/css/main.min.css">-->


            <!-- *************
                                ************ Vendor Css Files *************
                        ************ -->

            <!-- Scrollbar CSS -->
            <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/overlay-scroll/OverlayScrollbars.min.css">
            
            <!-- select2 CSS -->
            <!--<link rel="stylesheet" href="${contextPath}/resources/select2/css/select2.min.css">-->
            <link rel="stylesheet" href="${contextPath}/resources/vendor/select2-4.0.13/css/select2.min.css">

            <!-- this page specific styles -->
            <s:layout-component name="page_css">
            </s:layout-component>

            <style>
                label.error, select.error {
                    color: #DD504C;
                }
                input.error, select.error, textarea.error {
                    border-color: #DD504C;
                }
                input.error::-webkit-input-placeholder, select.error::-webkit-input-placeholder, textarea.error::-webkit-input-placeholder {
                    color: #DD504C;
                }
                input.error:-moz-placeholder, select.error:-moz-placeholder, textarea.error:-moz-placeholder {
                    /* FF 4-18 */
                    color: #DD504C;
                }
                input.error::-moz-placeholder, select.error::-moz-placeholder, textarea.error::-moz-placeholder {
                    /* FF 19+ */
                    color: #DD504C;
                }
                input.error:-ms-input-placeholder, select.error:-ms-input-placeholder, textarea.error:-ms-input-placeholder {
                    /* IE 10+ */
                    color: #DD504C;
                }
                input.error:focus, select.error:focus, textarea.error:focus {
                    border-color: #DD504C;
                    box-shadow: 0 0 5px rgba(221, 80, 76, 1);
                }
                /*                //select2 error
                                //div.select2-container.error {
                                    //border-color: #DD504C;
                                //}*/
                .modal-dialog {
                    position: absolute;
                    top: 30% !important;
                    left: 20% !important;
                    right: 20% !important;
                    bottom: 35% !important;
                }

                .modal-lg {
                    top: 10% !important;
                }
                .modal-small {
                    top: 10% !important;
                }

                .newNavigate {
                    /*background-color: #F9F3EF;*/
                    /*background-color: #F2F2F2;*/
                    /*background-color: #EEEEEE;*/
                    /*background-color: #FFFDF6;*/
                    /*background-color: #F6F0F0;*/
                    /*background-color: #FBF8EF;*/
                    /*background-color: #FFF7F3;*/
                    /*background-color: #f0f4fa;*/
                    /*background-color: #17313E;*/
                    /*background-color: #93DA97;*/
                    /*background: linear-gradient(180deg,rgba(222, 113, 18, 1) 39%, rgba(252, 176, 69, 1) 100%);*/
                    background-color: #1e3547;
                    /*color: white;*/
                }
                .menu-text {
                    /*background: #17313E;*/
                    color: white;
                }
                .sidebar-menu ul li a i {
                    color: white;
                    border-width: 3px;
                    border-style: ridge;
                    /*border-width: 2px;*/
                    /*background-color: #415E72;*/
                    /*width:70px;*/
                    /*height:50px;*/
                    box-shadow: inset 0 3px 6px rgba(0,0,0,0.16), 0 4px 6px rgba(0,0,0,0.45);
                    /*border-radius: 15px;*/
                }
                .sidebar-menu ul li.active a {
                    /*background: #507dff;*/
                    color: #ffffff;
                    /*border-color: #507dff;*/
                    background-color: #415E72;
                    /*box-shadow: 0 4px 10px rgba(80, 125, 255, 0.25);*/
                }
                .sidebar-menu ul li a:hover {
                    color: #507dff;
                    background-color: #415E72;
                }
                .sidebar-menu .sidebar-dropdown .sidebar-submenu ul li a {
                    color: white
                }
                .sidebar-menu .sidebar-dropdown.active > a {
                    background-color: #415E72;
                }
                .sidebar-menu .sidebar-dropdown .sidebar-submenu ul li a:hover {
                    color: #ff8666;
                    background: white;
                    background-color: #415E72;
                }
                .sidebar-menu ul li.active a.current-page {
                    position: relative;
                    color: #ff8666;
                    background-color: #415E72;
                    font-weight: 600;
                }
                .sidebar-menu .sidebar-dropdown.active .sidebar-submenu ul li a {
                    position: relative;
                    background-color: #1e3547;
                    font-weight: 600;
                }

                .sidebar-menu ul li.active-page-link a i {
                    color: #ffffff;
                    background-color: #f06a0a;
                    border-color: #f06a0a;
                    box-shadow: 0 5px 10px rgba(80, 125, 255, 0.3);
                }
                .sidebar-menu ul li.active a i {
                    background: #f06a0a;
                    color: #ffffff;
                    border-color: #f06a0a;
                    box-shadow: 0 4px 10px rgba(80, 125, 255, 0.25);
                }
                .sidebar-menu ul li.active a.current-page {
                    position: relative;
                    color: #f06a0a;
                    background-color: #f06a0a;
                    font-weight: 600;
                }
                .sidebar-menu ul li a:hover i {
                    transform: translateY(-2px);
                    box-shadow: 0 4px 8px rgba(80, 125, 255, 0.2);
                    border-color: #f06a0a;
                    color: #f06a0a;
                }
                .sidebar-menu ul li.active a:hover i {
                    transform: translateY(-2px);
                    box-shadow: 0 4px 8px rgba(80, 125, 255, 0.2);
                    border-color: #ffffff;
                    color: #ffffff;
                }
                .img2 {
                    width: 50px; /* Sets a fixed width */
                    height: 50px; /* Sets a fixed height */
                }

                .img3 {
                    width: 55px; /* Sets a fixed width */
                    height: 18px; /* Sets a fixed height */
                }

                .app-footer {
                    position: fixed;
                    bottom: 0;
                    right: 0;
                    font-size: 0.7rem;
                    margin: 0;
                    padding: 15px 20px 0 20px;
                    display: flex;
                    justify-content: flex-end;
                }

                .sidebar-menu {
                    padding: 15px 0;
                    height: 100%;
                }

                .text3d {
                    position: relative;
                    text-align: center;
                    /*color: #f06a0a;*/
                    font-size: 1.2em;
                    transition: 0.5s;
                    font-family: Arial, Helvetica, sans-serif;
                    text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc,
                        0 3px 0 #ccc, 0 4px 0 #ccc,
                        0 5px 0 #ccc, 0 6px 0 #ccc,
                        0 7px 0 #ccc, 0 8px 0 #ccc,
                        0 9px 0 #ccc, 0 10px 0 #ccc,
                        0 11px 0 #ccc, 0 12px 0 #ccc,
                        0 20px 30px rgba(0, 0, 0, 0.5);
                }

                /*/*        .text3d:hover {
                            text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc,
                                0 3px 0 #ccc, 0 4px 0 #ccc,
                                0 5px 0 #ccc, 0 6px 0 #ccc,
                                0 7px 0 #ccc, 0 8px 0 #ccc,
                                0 9px 0 #ccc, 0 10px 0 #ccc,
                                0 11px 0 #ccc, 0 12px 0 #ccc,
                                0 20px 30px rgba(0, 0, 0, 0.5);
                        }*/


            </style>

            <s:layout-component name="page_css_inline">
            </s:layout-component>

            <!-- Favicon -->
            <link type="image/x-icon" href="${contextPath}/resources/vendor/login/img/heat.png" rel="shortcut icon"/>

            <!-- google font libraries -->
            <!--<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400' rel='stylesheet' type='text/css'>-->
            <!--<link href='${contextPath}/resources/css/google-font-2.css' rel='stylesheet' type='text/css'>-->

            <!--[if lt IE 9]>
                    <script src="${contextPath}/resources/private/js/html5shiv.js"></script>
                    <script src="${contextPath}/resources/private/js/respond.min.js"></script>
            <![endif]-->
            <!--[if lt IE 8]>
                    <link href="${contextPath}/resources/private/css/libs/font-awesome-ie7.css" type="text/css" rel="stylesheet" />
            <![endif]-->
        </head>
        <body>

            <!-- Loading wrapper start -->
            <div id="loading-wrapper">
                <div class="spinner-container">
                    <div class="spinner-border text-primary spinner"></div>
                    <div class="spinner-border text-secondary spinner reverse"></div>
                </div>
            </div>
            <!-- Loading wrapper end -->

            <!-- Page wrapper start -->
            <div class="page-wrapper">
                <nav class="sidebar-wrapper newNavigate">
                    <!--<nav class="sidebar-wrapper">-->
                    <!--<nav class="sidebar-wrapper" style="background:#faf1f0">-->


                    <!-- Sidebar brand starts -->
                    <!--                    <div class="sidebar-brand">
                                            <a href="${contextPath}/" class="logo">
                                                <img src="${contextPath}/resources/vendor/login/img/heat.png" alt="HEATs">
                                            </a>
                                        </div>-->
                    <!-- Sidebar brand starts -->

                    <!-- Sidebar menu starts -->
                    <div class="sidebar-menu">
                        <div class="sidebarMenuScroll">
                            <ul>${userMenu}</ul>
                        </div>
                    </div>
                </nav>

                <div class="main-container">

                    <!-- Page header starts -->
                    <div class="page-header d-flex align-items-center">

                        <!-- Toggle sidebar start -->
                        <button type="button" class="toggle-sidebar btn btn-danger" id="toggle-sidebar"><i
                                class="bi bi-list"></i></button>
                        <!-- Toggle sidebar end -->

                        <!-- Logo sm starts -->
                        <!--                        <a href="index.html" class="d-lg-none d-md-block">
                                                    <img src="${contextPath}/resources/vendor/login/img/mib5.png" class="logo-sm" alt="MIB">
                                                </a>-->
                        <!-- Logo sm ends -->

                        <!-- Breadcrumb start -->
                        <ol class="breadcrumb d-lg-flex d-none">
                            <li class="breadcrumb-item breadcrumb-active" aria-current="page" >
                                <!--<i class="bi bi-house"></i>-->
                                <img class="img2" src="${contextPath}/resources/vendor/login/img/heat.png" alt="HEATs">
                                <a href="${contextPath}/" class="text3d"> &nbsp;&nbsp;Reliability Lab Hardware & Equipment Activity Tracking System</a>
                            </li>
                            <!--                                <a href="index.html">Home</a>
                                                        </li>
                                                        <li class="breadcrumb-item breadcrumb-active" aria-current="page">${name}</li>-->
                        </ol>
                        <!-- Breadcrumb end -->

                        <!-- Header actions container start -->
                        <div class="d-flex align-items-center gap-3 ms-auto">

                            <!-- Leads start -->
                            <span class="d-none d-md-block" style="font-weight:bold">Welcome ${sessionScope.userSession.fullname}</span>
                            <span class="d-lg-none d-md-block" style="font-weight:bold">Welcome ${sessionScope.userSession.firstName}</span>
                            <a href="${contextPath}/logout" class="leads rounded-3 d-xxl-flex d-none">
                                <i class='bi bi-box-arrow-right' style='color:#ffffff' ></i>&nbsp;&nbsp;Logout
                            </a>
                            <a href="${contextPath}/logout" class="d-lg-none d-md-block h3">
                                <i class='bi bi-box-arrow-right' style='color:blue' ></i>
                            </a>
                        </div>
                        <!-- Header actions container end -->

                    </div>
                    <!-- Page header ends -->

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                            <strong>${error}</strong>
                        </div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                            <strong>${success}</strong>
                        </div>
                    </c:if>
                    <s:layout-component name="page_container">
                    </s:layout-component>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="delete_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <!--<button type="button" class="btn-close" data-bs-dismiss=="modal" aria-hidden="true">&times;</button>-->
                            <h4 class="modal-title"><i class="bi bi-exclamation-diamond" style="color:red"></i>  Delete Confirmation</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-bs-dismiss="modal">Cancel</button>
                            <a id="modal_delete_button" href="#" class="btn btn-danger"><i class="bi bi-trash"></i>  Delete</a>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            <div class="modal fade" id="req_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <!--<i class="fa fa-exclamation-triangle fa-stack-1x fa-inverse" style="color:yellow"></i>-->
                            <h4 class="modal-title"><i class="fa fa-exclamation-triangle" style="color:goldenrod"></i> Verification on Inner List</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times-circle"></i> No</button>
                            <a id="modal_qty_button" href="#" class="btn btn-primary"><i class="fa fa-check-circle"></i> Yes</a>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            <div class="modal fade" id="qty_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <!--<i class="fa fa-exclamation-triangle fa-stack-1x fa-inverse" style="color:yellow"></i>-->
                            <h4 class="modal-title"><i class="fa fa-exclamation-triangle" style="color:goldenrod"></i> Confirmation on Unit Quantity</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <a id="modal_qty_button" href="#" class="btn btn-primary"><i class="fa fa-check"></i> Confirm</a>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="confirmation_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <!--<i class="fa fa-exclamation-triangle fa-stack-1x fa-inverse" style="color:yellow"></i>-->
                            <h4 class="modal-title"><i class="fa fa-exclamation-triangle" style="color:goldenrod"></i> Action Verification</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <a id="modal_button" href="#" class="btn btn-primary"><i class="fa fa-check"></i> Confirm</a>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->

            <div class="modal fade" id="photo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 id="modal_photo_title" class="modal-title">Photo Title</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div class="modal fade" id="photo_modal_small" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-small">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 id="modal_photo_small_title" class="modal-title">Photo Title</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->



            <!-- *************
                            ************ Required JavaScript Files *************
                    ************* -->
            <!-- Required jQuery first, then Bootstrap Bundle JS -->
            <script src="${contextPath}/resources/statflow/js/jquery.min.js"></script>
            <script src="${contextPath}/resources/statflow/js/bootstrap.bundle.min.js"></script>
            <script src="${contextPath}/resources/statflow/js/modernizr.js"></script>
            <script src="${contextPath}/resources/statflow/js/moment.js"></script>
            
            <!-- Select2 Js -->
            <!--<script src="${contextPath}/resources/select2/js/select2.min.js"></script>-->
            <script src="${contextPath}/resources/vendor/select2-4.0.13/js/select2.min.js"></script>

            <!-- *************
                                ************ Vendor Js Files *************
                        ************* -->

            <s:layout-component name="page_js">
            </s:layout-component>

            <!-- Overlay Scroll JS -->
            <script src="${contextPath}/resources/statflow/vendor/overlay-scroll/jquery.overlayScrollbars.min.js"></script>
            <script src="${contextPath}/resources/statflow/vendor/overlay-scroll/custom-scrollbar.js"></script>

            <!-- Main Js Required -->
            <script src="${contextPath}/resources/statflow/js/main.js"></script>
            
            <!-- Swal -->
            <script src="${contextPath}/resources/private/js/sweetalert.min.js"></script>


            <script>
                $(document).ready(function () {
                    $("button[type=back]").click(function () {
                        history.go(-1);
                    });
                });

                function AlertInfo() {
                    //                    alert("Developed on 2018 by Nur Amanina Ahmad Sandara Lela Putera, System Analyst from Reliability Lab, ON Semiconductor.");
                    alert("Developed on 2025 by Mohd Farhan, Programmer from Reliability Lab, ON Semiconductor.");
                    //                    var answer = confirm ("Developed on 2018 by Nur Amanina Ahmad Sandara Lela Putera, System Analyst from Reliability Lab, ON Semiconductor.");
                    //                    if (answer)
                    //                        window.location="http://www.onsemi.com";
                }
                function AlertContact() {
                    alert("Any issues regarding to the system, please contact;\nOffice : 06-6823088 ext. 2732 OR ext. 2731\nH/P : 013-2839488 OR ABB. 54655");
                    //                    var answer = confirm ("Developed on 2018 by Nur Amanina Ahmad Sandara Lela Putera, System Analyst from Reliability Lab, ON Semiconductor.");
                    //                    if (answer)
                    //                        window.location="http://www.onsemi.com";
                }
            </script>

            <!-- this page specific inline scripts -->
            <s:layout-component name="page_js_inline">
            </s:layout-component>

        </body>
    </html>

</s:layout-definition>