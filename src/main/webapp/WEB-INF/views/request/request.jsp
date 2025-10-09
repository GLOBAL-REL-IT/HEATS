<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <!--<link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />-->
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
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>Sample Retention</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Preparation for Sample Retention</h2>
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/sr/request/addNew" class="btn btn-primary pull-right">
                                    <!--<a href="${contextPath}/sr/retrieve/add" class="btn btn-primary pull-right">-->
                                    <i class="bi bi-file-plus h4"></i> Create New
                                </a>
                            </div>
                        </div>
                        <hr/>
                        <form id="requestForm1" class="form-horizontal" role="form" action="${contextPath}/sr/request/search" method="post" style="width: 100%">
                            <div class="form-group" id="requestDiv">
                                <label for="rmsLotEvent" class="col-lg-2 control-label">RMS Lot#_Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsLotEvent" style="width: 100%" name="rmsLotEvent" placeholder="O12345A_TC" value="">
                                </div>
                            </div>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" id="submit" class="btn btn-primary">Search</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <!-- -->
                <!-- -->        
                <!-- --> 
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
//            function format(value) {
//                return '<div><b>List of RMSLot_Event :</b> ' + value + '</div>';
//            }

            $(document).ready(function () {
                $('#rmsLotEvent').bind('copy paste cut', function (e) {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste  
                });

                var validator1 = $("#requestForm1").validate({
                    rules: {
                        rmsLotEvent: {
                            required: true
                        }
                    }
                });


//                var delay = (function () {
//                    var timer = 0;
//                    return function (callback, ms) {
//                        clearTimeout(timer);
//                        timer = setTimeout(callback, ms);
//                    };
//                })();
//
//                $("#rmsLotEvent").on("input", function () {
//                    delay(function () {
//                        if ($("#rmsLotEvent").val().length < 8) {
//                            $("#rmsLotEvent").val("");
//                        }
//                    }, 50);
//                });

            });
        </script>
    </s:layout-component>
</s:layout-render>