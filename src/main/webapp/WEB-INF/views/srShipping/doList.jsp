<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <!--<link rel="stylesheet" href="${contextPath}/resources/private/css/libs/jquery.datetimepicker.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-timepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
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
            
            .fa-stack2 {
                color: green;
            }
            .table-link2 {
                color: green;
            }
            .fa-stack3 {
                color: red;
            }
            .table-link3 {
                color: red;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>List of Packaging</h1>-->
            <c:if test="${createGtsAccess=='Active'}">
                <div class="row">
                    <div class="col-lg-11">
                        <div class="main-box" id ="shipNewDiv">
                            <h2>DO Details</h2>
                            <form id="innerVerificationForm" class="form-horizontal" role="form" action="${contextPath}/sr/srShipping/doList/save" method="post" style="width: 100%">
                                <div class="form-group" id="shipNewFormDiv">
                                    <label for="gtsNo" class="col-lg-1 control-label">GTS No</label>
                                    <div class="col-lg-2">
                                        <input type="number" class="form-control" id="gtsNo" style="width: 100%" name="gtsNo" placeholder="Insert GTS Number" value="" autofocus="autofocus" required="">
                                    </div>

                                    <label for="shippingDate" class="col-lg-2 control-label">Est. Collection Date</label>
                                    <div class="col-lg-2" id="shippingDateDiv1">
                                        <div class="col-lg-2 input-group time" id="shippingDateDiv">
                                            <input type="text" class="form-control datepicker" id="shippingDate" name="shippingDate" readonly style="width: 150px;" required="" />
                                            <span class="input-group-addon date">
                                                <i class="fa fa-calendar"></i>
                                            </span>
                                        </div>
                                    </div>

                                    <label for="shippingTime" class="col-lg-2 control-label">Est. Collection Time</label>
                                    <div class="col-lg-2" id="shippingTimeDiv1">
                                        <div class="col-lg-2 input-group date" id="shippingTimeDiv">
                                            <input type="text" class="form-control timepicker" id="shippingTime" name="shippingTime" readonly style="width: 150px;" required="" />
                                            <span class="input-group-addon date">
                                                <i class="fa fa-clock"></i>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <a href="${contextPath}/sr/srShipping" class="btn btn-info pull-left" id="cancel"><i class="fa fa-reply"></i> Back</a>
                                    <c:if test="${kira == 0 && indexCount != 0}">
                                        <button type="submit" name="submit" id="submit" class="btn btn-primary pull-right" disabled="">Save <i class="fa fa-chevron-right"></i></button>
                                    </c:if>
                                    <c:if test="${kira != 0 && indexCount != 0}">
                                        <button type="submit" name="submit" id="submit" class="btn btn-primary pull-right" disabled="">Save <i class="fa fa-chevron-right"></i></button>
                                    </c:if>    
                                    <c:if test="${kira != 0 && indexCount == 0}">
                                        <button type="submit" name="submit" id="submit" class="btn btn-primary pull-right" >Save <i class="fa fa-chevron-right"></i></button>
                                    </c:if>    
                                </div>
                                <div class="clearfix"></div>
                            </form>
                        </div>    
                    </div>       
                </div> 
            </c:if>
            <div class="row">
                <c:if test="${indexCount != 0}">
                    <div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <span class="fa-stack"><i class="fa fa-exclamation-triangle fa-stack-2x" style="color:red"></i></span>
                        <b>Previous GTS list has not been printed yet. Please print the previous GTS before proceed to the new one.</b>
                    </div>
                </c:if>
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">DO List</h2>
                            <div class="filter-block pull-right">
                                <c:if test="${count != 0}">
                                    <a href="${contextPath}/sr/srShipping/email" class="btn btn-primary pull-right" id="print" name="print">
                                        <i class="fa fa-print fa-lg"> </i> Print DO List
                                    </a>
                                </c:if>
                            </div>

                            <div class="filter-block pull-right">
                            </div>
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
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>" autofocus="autofocus" >
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table id="dt_spml" class="display" cellspacing="0" width="100%">
                                <thead>
                                    <tr align = "center">
                                        <th align = "center">No.</th>
                                        <th align = "center">Outer ID</th>
                                        <th align = "center">Pkg Family</th>
                                        <th align = "center">Event</th>
                                        <th align = "center">Mth to Scrap</th>
                                        <th align = "center">Total Units</th>
                                        <!--<th align = "center">Total Weight (kg)</th>-->
                                        <!--<th align = "center">Unit Price (USD)</th>-->
                                        <th align = "center">GTS No.</th>
                                        <th align = "center">Est. Collect Date/Time</th>
                                        <th align = "center">Manage</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${doList}" var="doList" varStatus="ftpListLoop">
                                        <tr>
                                            <td><c:out value="${ftpListLoop.index+1}"/></td>
                                            <td><c:out value="${doList.boxId}"/></td>
                                            <td><c:out value="${doList.pkgFamily}"/></td>
                                            <td><c:out value="${doList.event}"/></td>
                                            <td><c:out value="${doList.mthToScrap}"/></td>
                                            <td><c:out value="${doList.totalUnits}"/></td>
<!--                                            <td><c:out value="${doList.totalWeight}"/></td>
                                            <td><c:out value="${doList.unitPrice}"/></td>-->
                                            <td><c:out value="${doList.gtsNo}"/></td>
                                            <td><c:out value="${doList.shipDate}"/></td>
                                            <td align="center">
                                                <a class="table-link" href="#" title="Barcode Sticker" onclick="window.open('${contextPath}/sr/srShipping/viewOuterBarcodePdf/${doList.reqId}', 'Barcode Sticker', 'width=800,height=900').print()">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-print fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a> 
                                                <c:if test="${doList.status == 'Pending Shipment Details'}">    
                                                    <a modaldeleteid="${doList.reqId}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" title="Delete Request" onclick="modalDelete(this);">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x" style="color:red"></i>
                                                            <i class="fa fa-trash-alt fa-stack-1x fa-inverse"></i>
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
    </s:layout-component>
    <s:layout-component name="page_js">
        <!--print-->
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <!--<script src="${contextPath}/resources/validation/additional-methods.js"></script>-->
        <script src="${contextPath}/resources/validation/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/validation/bootstrap-timepicker.js"></script>
        <!--<script src="${contextPath}/resources/validation/jquery.ui.datepicker.validation.js"></script>-->
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script type="text/javascript">
            $(document).ready(function () {
                var validator = $("#innerVerificationForm").validate({
                    rules: {
                        gtsNo: {
                            required: true,
                            minlength: 9,
                            maxlength: 9
                        },
                        shippingDate: {
                            required: true
                        },
                        shippingTime: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });

                oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    aoColumns : [
                      { sWidth: '5%' },
                      { sWidth: '10%' },
                      { sWidth: '10%' },
                      { sWidth: '10%' },
                      { sWidth: '10%' },
                      { sWidth: '7%' },
                      { sWidth: '10%' },
                      { sWidth: '15%' },
                      { sWidth: '3%' }
                    ],
                    columnDefs : [{
                        sortable : false,
                        targets : [ 8 ]
                    }],
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
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
            });

            $('#shippingTime').timepicker({
                appendWidgetTo: 'body',
                showSeconds: false,
                showMeridian: false,
                defaultTime: false,
                timeFormat: 'HH:mm:ss',
                clearBtn: true,
            });
            
            $(function () {
                $('.datepicker').datepicker({
                    format: 'yyyy-mm-dd',
                    startDate: 'd',
                    autoclose: true,
                    clearBtn: true,
                    todayHighlight: true,
                    maxViewMode: 'years',
                    minViewMode: 'day',
                    endDate: '+365d',
                    datesDisabled: '+365d',
                    required: true
                });
            });
            
            function modalDelete(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/sr/srShipping/doList/delete/" + deleteId;
                var deleteMsg = "Are you sure want to delete this box?";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }
        </script>
    </s:layout-component>
</s:layout-render>