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

            span.tab-space {padding-left:20em;}
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Inner Request Details</h1>
            <div class="row">
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Outer Package Details</h2>
                        <hr/>
                        <form id="outerDetailsForm" class="form-horizontal" role="form"  method="post" style="width: 100%">
                            <div class="form-group" id="requestDiv">
                                <label for="reqType" class="col-lg-1 control-label">Req. Type</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="reqType" style="width: 100%" name="reqType" placeholder="" value="${sr.reqType}"  readonly>
                                </div>
                                
                                <label for="reqDetails" class="col-lg-1 control-label">Req. Details</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="reqDetails" style="width: 100%" name="reqDetails" placeholder="" value="${sr.reqDetails}"  readonly>
                                </div>
                            </div>
                            <div class="form-group" id="othersReasonDiv">
                                <label for="othersReason" class="col-lg-1 control-label">Remarks</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" id="othersReason" style="width: 100%" name="othersReason" placeholder="" value="${sr.reqRemarks}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="shipNewFormDiv">
                                <label for="pkgFamily" class="col-lg-1 control-label">Pkg Family</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="pkgFamily" style="width: 100%" name="pkgFamily" placeholder="" value="${sampleReq.pkgFamily}"  readonly>
                                </div>
                                
                                <label for="event" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="event" style="width: 100%" name="event" placeholder="" value="${sampleReq.event}"  readonly>
                                </div>
                                
                                <label for="mthToScrap" class="col-lg-2 control-label">Mth to Scrap</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="mthToScrap" style="width: 100%" name="mthToScrap" placeholder="" value="${sampleReq.mthToScrap}"  readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="outerId" class="col-lg-1 control-label">Outer ID</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="outerId" style="width: 100%" name="outerId" placeholder="" value="${sampleReq.reqBoxId}"  readonly>
                                </div>
                                
                                <label for="reqDate" class="col-lg-1 control-label">Req. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="reqDate" style="width: 100%" name="reqDate" placeholder="" value="${sr.createdDate}"  readonly>
                                </div>
                                
                                <label for="aging" class="col-lg-2 control-label">Days Left to Scrap</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="aging" style="width: 100%" name="aging" placeholder="" value="${sampleReq.aging}"  readonly>
                                </div>
                            </div>
                                
                            <div class="form-group">
                                <label for="status" class="col-lg-1 control-label">Status</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="status" style="width: 100%" name="status" placeholder="" value="${sr.status}"  readonly>
                                </div>
                                <label for="reqBy" class="col-lg-1 control-label">Req. By</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="reqBy" style="width: 100%" name="reqBy" placeholder="" value="${sr.createdBy}"  readonly>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Inner Details</h2>
                        <hr/>
                        <form id="innerDetailsForm" class="form-horizontal" role="form" method="post" style="width: 100%">
                            <div class="form-group" id="requestDiv">
                                <label for="rmsNo" class="col-lg-1 control-label">RMS No.</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsNo" style="width: 100%" name="rmsNo" placeholder="" value="${sampInner.rmsNo}" readonly>
                                </div>
                                
                                <label for="lot" class="col-lg-1 control-label">Lot #</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lot" style="width: 100%" name="lot" placeholder="" value="${sampInner.lot}" readonly>
                                </div>
                                
                                <label for="event" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="event" style="width: 100%" name="event" placeholder="" value="${sampInner.event}" readonly>
                                </div>
                                <label for="compDate" class="col-lg-1 control-label">Comp. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="compDate" style="width: 100%" name="compDate" placeholder="" value="${sampInner.compDate}" readonly>
                                </div>
                                
                                
                            </div>
                            <div class="form-group" id="requestDiv">
                                
                            </div>
                            <div class="form-group" id="shipNewFormDiv">
<!--                                <label for="rmslotevent" class="col-lg-1 control-label">RMSLot_Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmslotevent" style="width: 100%" name="rmslotevent" placeholder="" value="${sampInner.rmsLotEvent}" autofocus="autofocus" readonly>
                                </div>
                                -->
                                <label for="pkgName" class="col-lg-1 control-label">Pkg Name</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="pkgName" style="width: 100%" name="event" placeholder="" value="${sampInner.pkgName}" readonly>
                                </div>
                                
                                <label for="innerId" class="col-lg-1 control-label">Inner ID</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="innerId" style="width: 100%" name="innerId" placeholder="" value="${sampInner.innerNo}" readonly>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Inner Verification</h2>
                        <hr/>
                        <form id="innerVerificationForm" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/verifyReceived/${sampInner.id}" method="post" style="width: 100%">
                            <div class="form-group" id="requestDiv">
                                <label for="rmsQty" class="col-lg-1 control-label">Inner ID</label>
                                <div class="col-lg-3">
                                    <input type="text" id="innerNo" style="width: 100%" name="innerNo" value="${sampInner.innerNo}" autofocus="autofocus" disabled="" hidden>
                                        <c:if test="${sampInner.flag == '5'}">
                                            <input type="text" class="form-control" id="innerId" style="width: 100%" name="innerId" value="${sampInner.innerNo}" autofocus="autofocus" disabled="">
                                        </c:if>
                                        <c:if test="${sampInner.flag != '5'}">
                                            <input type="text" class="form-control" id="innerId" style="width: 100%" name="innerId" placeholder="${sampInner.innerNo}" autofocus="autofocus">
                                        </c:if>  
                                </div>
                            </div>
                            
                            <a href="${contextPath}/sr/retrieve/verifyO/${sampInner.reqId}" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <c:if test="${sampInner.flag != '5'}">
                                    <button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-check-double"></i> Verify</button>
                                </c:if>
                                <c:if test="${sampInner.flag == '5'}">
                                    <button type="submit" id="submit" class="btn btn-primary" disabled=""><i class="fa fa-check-double"></i> Verify</button>
                                </c:if>    
                            </div>    
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>                
                <!--add tempp.jsp nye data-->       
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
            $(document).ready(function () {
                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                
                $('#boxId').bind('copy paste cut', function (e)  {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste  
                });
                
                var innerNo = $("#innerNo");
                var validator2 = $("#innerVerificationForm").validate({
                    rules: {
                        innerId: {
                            required: true,
                            equalTo: innerNo
                        }
                    }
                });
                
                var oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                    .css('font-size', '10pt')
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }
                        }
                    ]
                });
                
                $('#dt_spml_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
                });
                
                $('#actQty').on('change', function () {
                    var actQty = parseInt($('#actQty').val());
                    var rmsQty = parseInt($('#rmsQty').val());
                    
                    if(actQty === '') {
                    } else if(actQty !== '') {
                        if(rmsQty > actQty) {
                            $("#qtyRemarks").val("").trigger('change');
                            $("#qtyRemarksDiv").show();
                        } else if (rmsQty < actQty) {
                            $("#qtyRemarks").val("").trigger('change');
                            $("#qtyRemarksDiv").hide();
                        }
                    }
                });
            
                $('#qtyRemarks').on('change', function () {
                    if ($(this).val() === "Spilled") {
                        $("#spilledTypeDiv").show();
                        $("#spilledType").val("").trigger('change');
                    } else {
                        $("#spilledType").val("").trigger('change');
                        $("#spilledTypeDiv").hide();
                    }
                });  
            });
        </script>
    </s:layout-component>
</s:layout-render>