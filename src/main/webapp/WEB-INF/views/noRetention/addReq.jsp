<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
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

            /*span.tab-space {padding-left:20em;}*/
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Sample Retention Request</h1>
            <div class="row">
                <div class="col-lg-12">                            
                    <div class="col-lg-8">
                        <div class="main-box">
                            <h2>Create New Request</h2>
                            <hr/>
                            <form id="requestForm" class="form-horizontal" role="form" action="${contextPath}/sr/noRetention/addReq/submit" method="post" style="width: 100%">
                                <div class="form-group" id="requestTypeDiv">
                                    <label for="requestType" class="col-lg-3 control-label">Request Type</label>
                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="requestType" style="width: 100%" name="requestType" placeholder="" value="No Retention Plan" readonly="">
                                    </div>
                                </div>

                                <!--if request type = No Retention-->
                                <div class="form-group" id="reasonsExcludeDiv">
                                    <label for="reasonsExclude" class="col-lg-3 control-label">Reasons for Exclusions</label>
                                    <div class="col-lg-8">
                                        <select id="reasonsExclude" name="reasonsExclude" class="js-example-basic-single" style="width: 90%" autofocus="autofocus">
                                        <!--<select type="text" class="form-control" id="reasonsRecall" name="reasonsRecall" autofocus="autofocus" >-->
                                            <option value = "" selected></option>
                                            <option value = "Scrap">Scrap</option>
                                            <option value = "Return">Return to Requestor</option>
                                            <option value = "Continue">RMS Continue New Run</option>
                                            <option value = "Activities">Activities on Completed RMS (No Retention)</option>
                                            <option value = "Others">Others</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" id="othersDetailsDiv" hidden="">
                                    <label for="othersDetails" class="col-lg-3 control-label">Define 'Others'</label>
                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="othersDetails" style="width: 100%" name="othersDetails" value = "" placeholder="" autofocus="autofocus">
                                    </div>
                                </div>
                                
                                <div class="form-group" id="requestorNameDiv">
                                    <label for="requestorName" class="col-lg-3 control-label">Requestor Name</label>
                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="requestorName" style="width: 100%" name="requestorName" placeholder="" autofocus="autofocus">
                                        
                                        
                                    </div>
                                </div>
                                
                                <div class="form-group" id="relRequestorNameDiv">
                                    <label for="relRequestorName" class="col-lg-3 control-label">Rel Lab Requestor</label>
                                    <div class="col-lg-7">
                                        <c:if test="${username != null}">
                                            <input type="text" class="form-control" id="relRequestorName" style="width: 100%" name="relRequestorName" placeholder="${username}" value="${username}" readonly="">
                                        </c:if>
                                        <c:if test="${username == null}">
                                            <input type="text" class="form-control" id="relRequestorName" style="width: 100%" name="relRequestorName" placeholder="" autofocus="autofocus">
                                        </c:if> 
                                        
                                    </div>
                                </div>
                                
                                
                                <div class="form-group" id="relDateRequestDiv">
                                    <label for="relDateRequest" class="col-lg-3 control-label">Request Date</label>
                                    <div class="col-lg-5" id="relDateRequestDiv2">
                                        <div class="col-lg-3 input-group date" id="relDateRequestDiv3">
                                            <input type="text" class="form-control datepicker" id="relDateRequest" name="relDateRequest" readonly style="width: 150px;" required="" />
                                            <span class="input-group-addon date">
                                                <i class="fa fa-calendar"></i>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group" id="rmsNoDiv">
                                    <label for="rmsNo" class="col-lg-3 control-label">RMS No.</label>
                                    <div class="col-lg-3">
                                        <select id="rmsNo" name="rmsNo" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                            <option value = "" selected></option>
                                            <c:forEach items="${rmsList}" var="initData">
                                                <option value="${initData.rmsId}">${initData.rmsId}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    
                                    <label for="event" class="col-lg-2 control-label">Event *</label>
                                    <div class="col-lg-3" >
                                        <select id="event" name="event" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                            <option value = "" selected></option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" id="remarksDiv">
                                    <label for="remarks" class="col-lg-3 control-label">Remarks/Details</label>
                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="remarks" style="width: 100%" name="remarks" placeholder="" autofocus="autofocus"> 
                                        
                                    </div>
                                </div>
                                <a href="${contextPath}/sr/noRetention" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                                <div class="pull-right">
                                    <button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-check-double"></i> Submit</button>
                                </div>
                                <div class="clearfix"></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>  
        <script src="${contextPath}/resources/validation/bootstrap-datepicker.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {                
                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                
                var validator = $("#requestForm").validate({
                    rules: {
                        requestType: {
                            required: true
                        },
                        reasonsExclude: {
                            required: true
                        },
                        othersDetails: {
                            required: true
                        }, 
                        requestorName: {
                            required: true
                        },
                        relRequestorName: {
                            required: true
                        },
                        relDateRequest: {
                            required: true
                        },
                        rmsNo: {
                            required: true
                        },
                        event: {
                            required: true
                        }, 
                        remarks: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
                
            });
            
            
            $(function () {
                $('.datepicker').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    clearBtn: true,
                    todayHighlight: true,
                    maxViewMode: 'years',
                    minViewMode: 'day',
                    startDate: '-365d',
                    endDate: 'd',
//                    datesDisabled: '+365d',
                    required: true
                });
            });
            
            $('#rmsNo').on('change', function () {
//                alert ($(this).val());
                let dropdown = $('#event');
                dropdown.empty();
                dropdown.append('<option value="" selected=""></option>');
                dropdown.append('<option value="All" >All Available Event</option>');
                dropdown.prop('selectedIndex', 0);
                const url = '${contextPath}/sr/noRetention/addReq/test/' + $(this).val();
                // Populate dropdown with list of provinces
                
//                var size = '';
                $.getJSON(url, function (data) {
                    $.each(data, function (key, entry) {
                        dropdown.append($('<option></option>').attr('value', entry.event).text(entry.event));
//                        size = key;
                    })
//                    
//                    if(size == '0') {
//                        $("#subEventDiv").hide();
//                    } else {
//                        $("#subEventDiv").show();
//                    }
                });
            });
            
            $('#reasonsExclude').on('change', function () {
                if ($(this).val() === "Others") {
                    $("#othersDetailsDiv").show();
                    $("#othersDetails").val("").trigger('change');
                } else {
                    $("#othersDetailsDiv").hide();
                    $("#othersDetails").val("").trigger('change');
                }
            });
        </script>
    </s:layout-component>
</s:layout-render>