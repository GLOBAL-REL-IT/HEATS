<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
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
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box" id ="shipNewDiv">
                        <h2>DO Details</h2>
                        <form id="innerVerificationForm" class="form-horizontal" role="form" action="${contextPath}/sr/srShipping/trackGts/search" method="post" style="width: 100%">
                            <div class="form-group" id="rmsIdDiv">
                                <c:if test="${gtsNumber == null}">
                                    <label for="gtsNo" class="col-lg-2 control-label">GTS No</label>
                                    <div class="col-lg-9">
                                        <select id="gtsNo" name="gtsNo" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                            <option value = ""></option>
                                            <c:forEach items="${gtsList}" var="doList">
                                                <option value="${doList.gtsNo}" >${doList.gtsNo}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </c:if>
                                <c:if test="${gtsNumber != null}">
                                    <div class="col-lg-9">
                                        <label for="gtsNo" class="col-lg-2 control-label">GTS No</label>
                                        <div class="col-lg-9">
                                            <input type="number" class="form-control" id="gtsNo" style="width: 100%" name="gtsNo" placeholder="${gtsNumber}" value="${gtsNumber}" autofocus="autofocus" required="" disabled="">
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-lg-12">
                                <c:if test="${gtsNumber == null}">
                                    <a href="${contextPath}/sr/srShipping" class="btn btn-info pull-left" id="cancel"><i class="fa fa-reply"></i> Back</a>
                                    <button tabindex=""me="submit" id="submit" class="btn btn-primary pull-right" ><i class="fa fa-search"></i> Search</button>
                                </c:if>
                                <c:if test="${gtsNumber != null}">
                                    <a href="${contextPath}/sr/srShipping/trackGts" class="btn btn-info pull-left" id="cancel"><i class="fa fa-reply"></i> Back</a>
                                    <button tabindex=""me="submit" id="submit" class="btn btn-primary pull-right" disabled=""><i class="fa fa-search"></i> Search</button>
                                </c:if>    
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>    
                </div>       
            </div>                  
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">DO List</h2>
                            <div class="filter-block pull-right">
                                <c:if test="${gtsNumber != null}">
                                    <a href="${contextPath}/sr/srShipping/printDoPerGts/${gtsNumber}" class="btn btn-primary pull-right" id="print" name="print">
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
                                        <th align = "center">Total Weight (kg)</th>
                                        <th align = "center">Unit Price (USD)</th>
                                        <th align = "center">GTS No.</th>
                                        <th align = "center">Est. Collect Date</th>
                                        <th align = "center">Manage</th>
                                        <th align = "center">rmsLotEventConcat</th>
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
                                            <td><c:out value="${doList.totalWeight}"/></td>
                                            <td><c:out value="${doList.unitPrice}"/></td>
                                            <td><c:out value="${doList.gtsNo}"/></td>
                                            <td><c:out value="${doList.shipDate}"/></td>
                                            <td align="center">
                                                <a class="table-link" href="#" title="Barcode Sticker" onclick="window.open('${contextPath}/sr/srShipping/viewOuterBarcodePdf/${doList.reqId}', 'Barcode Sticker', 'width=800,height=900').print()">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-print fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>    
                                            </td>
                                            <td><c:out value="${doList.rmsLotEvent}"/></td>
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
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script type="text/javascript">
            $(document).ready(function () {                
                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                
                var validator = $("#innerVerificationForm").validate({
                    rules: {
                        gtsNo: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });

                var oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    columnDefs : [{
                        sortable : false,
                        targets : [ 10 ]
                    }],
                    columns: [
                        { data : 'no' },
                        { data : 'outerId' },
                        { data : 'pkgFamily' },
                        { data : 'event' },
                        { data : 'mthToScrap' },
                        { data : 'totalUnits' },
                        { data : 'totalWeight' },
                        { data : 'unitPrice' },
                        { data : 'gtsNo' },
                        { data : 'estShip' },
                        { data : 'manage' },
                        { data : 'rmsLotEventConcat',
                          visible : false
                        }
                    ],
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
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
        </script>
    </s:layout-component>
</s:layout-render>