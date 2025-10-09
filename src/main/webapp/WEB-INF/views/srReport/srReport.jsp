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
            .dataTables_wrapper .dt-buttons {
                float:none;
                text-align:right;
            }
            td.details-control {
                background: url(${contextPath}/resources/details_open.png) no-repeat center center;
                cursor: pointer;
            }
            tr.shown td.details-control {
                background: url(${contextPath}/resources/details_close.png) no-repeat center center;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>List of Packaging</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box" id ="shipNewDiv">
                        <h2>Pending Shipment Report</h2>
                        <form id="monthlyReportForm" class="form-horizontal" role="form" action="${contextPath}/sr/srReport" method="post" style="width: 100%">
                            <div class="form-group" id="rmsIdDiv">
                                <label for="fromDate" class="col-lg-2 control-label">Completion Date From</label>
                                <div class="col-lg-2">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="fromDate" class="form-control" id="fromDate" value="">
                                    </div>
                                </div>
                                <label for="toDate" class="col-lg-1 control-label">To</label>
                                <div class="col-lg-2">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="toDate" class="form-control" id="toDate" value="">
                                    </div>
                                </div>
                            </div> 

                            <div class="col-lg-12">
                                <button tabindex=""me="submit" id="submit" class="btn btn-primary pull-right" ><i class="fa fa-search"></i> Search</button>
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
                            <h2 class="pull-left">Detail</h2>

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
                            <table id="dt_spml1" class="display" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th align = "center">No</th>
                                        <th align = "center">RMS#</th> 
                                        <th align = "center">Event</th>
                                        <th align = "center">Lot Type</th>
                                        <th align = "center">Mth to Scrap</th>
                                        <th align = "center">Pkg Family</th>
                                        <th align = "center">Pkg Name</th>
                                        <th align = "center">Compl. Date</th>
                                        <th align = "center" style="color:red">Pending Shipment Day</th>
                                        <th align = "center">Days Left to Scrap</th>
                                        <th align = "center">RMS Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${ftpDataList}" var="ftpdata" varStatus="ftpListLoop">
                                    <tr>                                            
                                        <td><c:out value="${ftpListLoop.index+1}"/></td>
                                    <td><c:out value="${ftpdata.rmsId}"/></td>
                                    <td><c:out value="${ftpdata.event}"/></td>
                                    <td><c:out value="${ftpdata.concatLot}"/></td>
                                    <td><c:out value="${ftpdata.mthToScrap}"/></td>
                                    <td><c:out value="${ftpdata.pkgFamily}"/></td>
                                    <td><c:out value="${ftpdata.pkgName}"/></td>
                                    <td><c:out value="${ftpdata.completeDate}"/></td>
                                    <td align="center" style="color:red"><b><c:out value="${ftpdata.packingDay}"/></b></td>
                                    <td align="center"><c:out value="${ftpdata.aging}"/></td>
                                    <td><c:out value="${ftpdata.processStatus}"/></td>
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
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/validation/bootstrap-datepicker.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script type="text/javascript">

            $(document).ready(function () {

                var validator = $("#monthlyReportForm").validate({
                    rules: {
                        fromDate: {
                            required: true
                        },
                        toDate: {
                            required: true
                        }
                    }
                });

                $("#fromDate").datepicker({
                    format: "yyyy-mm",
                    viewMode: "months",
                    minViewMode: "months"
                });

                $("#toDate").datepicker({
                    format: "yyyy-mm",
                    viewMode: "months",
                    minViewMode: "months"
                });

                var oTable = $('#dt_spml1').DataTable({
                    dom: 'Brtip',
                    columnDefs: [{
                            sortable: false,
                            targets: [10]
                        }],
                    columns: [
                        {data: 'no'},
                        {data: 'rmsId'},
                        {data: 'event'},
                        {data: 'concatLot'},
                        {data: 'mthToScrap'},
                        {data: 'pkgFamily'},
                        {data: 'pkgName'},
                        {data: 'completeDate'},
                        {data: 'packingDay'},
                        {data: 'aging'},
                        {data: 'processStatus'}
                    ],
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                            }
                        },
                        {
                            extend: 'csvHtml5',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
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
                    table.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    table.page.len($(this).val()).draw();
                });

            });


        </script>
    </s:layout-component>
</s:layout-render>