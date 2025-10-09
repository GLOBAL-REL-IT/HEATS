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
            <div class="row">
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Request Details</h2>
                        <hr/>
                        <form id="requestForm1" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/save/${sampleReq.id}" method="post" style="width: 100%">
                            <div class="form-group" id="requestDiv">
                                <label for="reqDate" class="col-lg-1 control-label">Req. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="reqDate" style="width: 100%" name="reqDate" placeholder="" value="${sr.createdDate}"  readonly>
                                </div>
                                <label for="reqType" class="col-lg-1 control-label">Req. Type</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="reqType" style="width: 100%" name="reqType" placeholder="" value="${sr.reqType}"  readonly>
                                </div>
                            </div>
                            <div class="form-group" id="reqDetailsDiv">    
                                <label for="reqDetails" class="col-lg-1 control-label">Req. Details</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" id="reqDetails" style="width: 100%" name="reqDetails" placeholder="" value="${sr.reqDetails}"  readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="reqBy" class="col-lg-1 control-label">Req. By</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="reqBy" style="width: 100%" name="reqBy" placeholder="" value="${sr.createdBy}"  readonly>
                                </div>
                                <label for="outerId" class="col-lg-1 control-label">Outer ID</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="outerId" style="width: 100%" name="outerId" placeholder="" value="${sampleReq.reqBoxId}"  readonly>
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
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pkgFamily" style="width: 100%" name="pkgFamily" placeholder="" value="${sampleReq.pkgFamily}"  readonly>
                                </div>

                                <label for="event" class="col-lg-2 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="event" style="width: 100%" name="event" placeholder="" value="${sampleReq.event}"  readonly>
                                </div>
                                <label for="status" class="col-lg-1 control-label">Status</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="status" style="width: 100%" name="status" placeholder="" value="${sr.status}"  readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="mthToScrap" class="col-lg-1 control-label">Mth to Scrap</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="mthToScrap" style="width: 100%" name="mthToScrap" placeholder="" value="${sampleReq.mthToScrap}"  readonly>
                                </div>
                                <label for="aging" class="col-lg-2 control-label">Days Left to Scrap</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="aging" style="width: 100%" name="aging" placeholder="" value="${sampleReq.aging}"  readonly>
                                </div>
                            </div>
                            <div class="form-group" id="shipDiv">
                                <label for="shipDate" class="col-lg-1 control-label">Shipment Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="shipDate" style="width: 100%" name="shipDate" placeholder="" value="${sr.shipDate}"  readonly>
                                </div>
                                <label for="invNo" class="col-lg-2 control-label">Invoice Number</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="invNo" style="width: 100%" name="invNo" placeholder="" value="${sr.invoiceNo}"  readonly>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <!-- -->
                <!-- -->        
                <!-- --> 
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Receiving Verification</h2>
                        <hr/>
                        <form id="requestForm2" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/save/${sampleReq.id}" method="post" style="width: 100%">
                            <div class="form-group" id="rmsDetailsFormDiv">
                                <c:if test="${sr.flag != '2'}">
                                    <label for="boxId" class="col-lg-1 control-label">Outer ID</label>
                                    <div class="col-lg-3">
                                        <input type="text" id="outerId" style="width: 100%" name="boxId" value="${sampleReq.reqBoxId}" autofocus="autofocus" disabled="" hidden>
                                        <input type="text" class="form-control" id="boxId" style="width: 100%" name="boxId" placeholder="${sampleReq.reqBoxId}" autofocus="autofocus">
                                    </div>
                                    <!--New request Mar 2024-->
                                    <label for="shipStatus" class="col-lg-1 control-label">Shipment Status</label>
                                    <div class="col-lg-2">
                                        <select id="shipStatus" name="shipStatus" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                            <option value = ""></option>
                                            <option value = "Pass">Pass</option>
                                            <option value = "Fail">Fail</option>
                                        </select>
                                    </div>
                            </div>
                            <div class="form-group">
                                <!--<div class="form-group">--> 
                                <label for="shipRemark" class="col-lg-1 control-label">Remarks</label>
                                <div class="col-lg-6">
                                    <textarea class="form-control" rows="5" id="shipRemark" name="shipRemark" maxlength="200"></textarea>
                                    <span class="pull-right label label-default" id="count_message"></span>
                                </div>
                            </div>
                            </c:if>

                            <div class="form-group" id="rmsDetailsFormDiv">
                                <c:if test="${sr.flag == '2'}">
                                    <label for="shipStatusView" class="col-lg-1 control-label">Shipment Status</label>
                                    <div class="col-lg-3">
                                        <input type="text" class="form-control" id="shipStatusView" style="width: 100%" name="shipStatusView" value="${sr.shipStatus}" disabled="">
                                    </div>
                            </div>
                            <div class="form-group">
                                <label for="shipRemarkView" class="col-lg-1 control-label">Remarks</label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="shipRemarkView" name="shipRemarkView" readonly>${sr.shipRemark}</textarea>
                                </div>
                            </div>
                            <div class="form-group" id="rmsDetailsFormDiv">
                                <label for="verifyBy" class="col-lg-1 control-label">Verify By</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="verifyBy" style="width: 100%" name="verifyBy" value="${sr.rlReceivedBy}" autofocus="autofocus" disabled="">
                                </div>
                                </c:if>
                                <c:if test="${sr.flag == '2'}">
                                    <label for="verifyDate" class="col-lg-2 control-label">Verification Timestamp</label>
                                    <div class="col-lg-3">
                                        <input type="text" class="form-control" id="verifyDate" style="width: 100%" name="verifyDate" value="${sr.rlReceivedDate}" autofocus="autofocus" disabled="">
                                    </div>   
                                </c:if>  
                            </div>
                            <a href="${contextPath}/sr/retrieve" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <c:if test="${sr.flag != '2'}">
                                    <button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-check-double"></i> Verify</button>
                                </c:if>
                                <c:if test="${sr.flag == '2'}">
                                    <button type="submit" id="submit" class="btn" style="color: black" disabled=""><i class="fa fa-check-double"></i> Verify</button>
                                </c:if>    
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <!-- -->        
                <!-- -->
                <!--utk box qty details-->
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="main-box clearfix">
                                <div class="clearfix">
                                    <h2 class="pull-left">Inner List</h2>
                                </div>
                                <hr/>
                                <div class="clearfix">
                                    <div class="form-group pull-left">
                                        <select id="dt_spml_rows" class="form-control">
                                            <option value="10">10</option>
                                            <option value="25">25</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            <option value="-1">All</option>
                                        </select>
                                    </div>
                                    <div class="filter-block pull-right">
                                        <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 5px;">
                                        </div>
                                        <div class="form-group pull-left" style="margin-right: 0px;">
                                            <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                            <i class="fa fa-search search-icon"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="dt_spml" class="display nowrap" cellspacing="0" width="100%">
                                        <thead>
                                            <tr align = "center">
                                                <th>No.</th>
                                                <th><span>RMSLot_Event</span></th>
                                                <th><span>Compl. Date</span></th>
                                                <th><span>Created Date</span></th>
                                                <th><span>Qty</span></th>
                                                <th><span>Inner ID</span></th>
                                                <th><span>Status</span></th>
                                                <th><span>Manage</span></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${innerList}" var="inner" varStatus="innerLoop">
                                            <tr>
                                                <td align = "center"><c:out value="${innerLoop.index+1}"/></td>
                                            <td><c:out value="${inner.rmsLotEvent}"/></td>
                                            <td><c:out value="${inner.compDate}"/></td>
                                            <td><c:out value="${inner.createdDate}"/></td>
                                            <td align = "center">
                                            <c:if test="${inner.qty == null}">
                                                <form id="verifiedQty" class="form-horizontal" role="form" action="${contextPath}/sr/sampleReq/updateQty/${inner.id}" method="post" style="width: 100%">
                                                    <div class="pull-right">
                                                        <input type="number" id="rmsQty" style="width: 60%" name="rmsQty" value="${inner.rmsQty}" hidden >
                                                        <input type="number" id="unitQty" style="width: 60%" name="unitQty" placeholder="${inner.rmsQty}" value="" autofocus="autofocus" required="" min="1">
                                                        <button type="submit" id="submit" title="Verified Qty"><i class="fa fa-check-circle fa-inverse" style="color:green" onclick="modalQty(this);"></i></button>
                                                    </div>
                                                </form>
                                            </c:if>
                                            <c:if test="${inner.qty != null}">
                                                <input type="number" class="form-control" id="unitQty2" style="width: 100%" name="unitQty2" placeholder="${inner.qty}" value="${inner.qty}" autofocus="autofocus" readonly="">
                                            </c:if>
                                            </td>
                                            <td align = "center"><c:out value="${inner.innerNo}"/></td>
                                            <td><c:out value="${inner.status}"/></td>
                                            <td>
                                            <c:if test="${sr.flag == '2'}">
                                                <a href="${contextPath}/sr/retrieve/verifyI/${inner.id}" id="verifyInner" name="verifyInner" class="table-link" title="Verify Inner">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x" style="color:steelblue"></i>
                                                        <i class="fa fa-check-double fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>  
                                            </c:if>
                                            <c:if test="${sr.flag == '1'}">
                                                <a title="Verify Inner">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x" style="color:gainsboro"></i>
                                                        <i class="fa fa-check-double fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>  
                                            </c:if> 
                                            </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
//            function format(value) {
//                return '<div><b>List of RMSLot_Event :</b> ' + value + '</div>';
//            }

                                                            $(document).ready(function () {

                                                                var text_max = 200;
                                                                $('#count_message').html('0 / ' + text_max);

                                                                $('#shipRemark').keyup(function () {
                                                                    var text_length = $('#shipRemark').val().length;
                                                                    var text_remaining = text_max - text_length;

                                                                    $('#count_message').html(text_length + ' / ' + text_max);
                                                                });


                                                                $(".js-example-basic-single").select2({
                                                                    placeholder: "Choose one",
                                                                    allowClear: true
                                                                });

                                                                $('#boxId').bind('copy paste cut', function (e) {
                                                                    e.preventDefault(); //this line will help us to disable cut,copy,paste  
                                                                });

                                                                var outerId = $("#outerId");
                                                                var validator2 = $("#requestForm2").validate({
                                                                    rules: {
                                                                        boxId: {
                                                                            required: true,
                                                                            equalTo: outerId
                                                                        },
                                                                        shipStatus: {
                                                                            required: true
                                                                        },
                                                                        shipRemark: {
                                                                            maxlength: 200,
                                                                            required: function () {
                                                                                if ($('#shipStatus').val() === "Fail") {
                                                                                    return true;
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                });

                                                                $(".cancel").click(function () {
                                                                    validator2.resetForm();
                                                                });

                                                                var oTable = $('#dt_spml').DataTable({
                                                                    dom: 'Brtip',
                                                                    columnDefs: [{
                                                                            sortable: false,
                                                                            targets: [7]
                                                                        }],
                                                                    aoColumns: [
                                                                        {sWidth: '4%'},
                                                                        {sWidth: '13%'},
                                                                        {sWidth: '12%'},
                                                                        {sWidth: '15%'},
                                                                        {sWidth: '10%'},
                                                                        {sWidth: '15%'},
                                                                        {sWidth: '25%'},
                                                                        {sWidth: '6%'}
                                                                    ],
                                                                    buttons: [
                                                                        {
                                                                            extend: 'copy',
                                                                            exportOptions: {
                                                                                columns: [1, 2, 3, 4, 5, 6, 7, 8, 9]
                                                                            }
                                                                        },
                                                                        {
                                                                            extend: 'excel',
                                                                            exportOptions: {
                                                                                columns: [1, 2, 3, 4, 5, 6, 7, 8, 9]
                                                                            }
                                                                        },
                                                                        {
                                                                            extend: 'pdf',
                                                                            exportOptions: {
                                                                                columns: [1, 2, 3, 4, 5, 6, 7, 8, 9]
                                                                            }
                                                                        },
                                                                        {
                                                                            extend: 'print',
                                                                            exportOptions: {
                                                                                columns: [1, 2, 3, 4, 5, 6, 7, 8, 9]
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

                                                                $('#barcode').on('click', function () {
                                                                    location.reload();
                                                                });
                                                            });
        </script>
    </s:layout-component>
</s:layout-render>