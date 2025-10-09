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
            td.details-control {
                background: url(${contextPath}/resources/details_open.png) no-repeat center center;
                cursor: pointer;
            }
            tr.shown td.details-control {
                background: url(${contextPath}/resources/details_close.png) no-repeat center center;
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
            <!--<h1>Inner Request Details</h1>-->
            <div class="row">
                <div class="col-lg-5">
                    <div class="main-box">
                        <table id="rack_data" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr align = "center">
                                    <th>No.</th>
                                    <th>Month</th>
                                    <th>Used Slot</th>
                                    <th>Free Slot</th>
                                    <th>Total Slot</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${inventoryDataList}" var="inventory" varStatus="invListLoop">
                                    <tr>
                                        <td align = "center"><c:out value="${inventory.intMth}"/></td>
                                        <td><c:out value="${inventory.rackMonth}"/></td>
                                        <td align = "center">
                                            <c:out value="${inventory.shelfUsed}"/>
                                            <a href="${contextPath}/sr/inventory/inventoryList/used/${inventory.rackMonth}" id="add" name="add" class="table-link" title="View Details for All Slot">
                                                <span class="fa-stack">
                                                    <i class="fa fa-search-plus fa-stack-1x" style="color:tomato"></i>
                                                </span>
                                            </a>
                                        </td>
                                        <td align = "center">
                                            <c:out value="${inventory.shelfFree}"/>
                                            <a href="${contextPath}/sr/inventory/inventoryList/free/${inventory.rackMonth}" id="add" name="add" class="table-link" title="View Details for All Slot">
                                                <span class="fa-stack">
                                                    <i class="fa fa-search-plus fa-stack-1x" style="color:seagreen"></i>
                                                </span>
                                            </a>
                                        </td>
                                        <td align = "center">
                                            <c:out value="${inventory.totalShelf}"/>
                                            <a href="${contextPath}/sr/inventory/inventoryList/total/${inventory.rackMonth}" id="add" name="add" class="table-link" title="View Details for All Slot">
                                                <span class="fa-stack">
                                                    <i class="fa fa-search-plus fa-stack-1x" style="color:royalblue"></i>
                                                </span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>  
                <div class="col-lg-7">
                    <div class="main-box">
                        <div class="clearfix">
                            <h2 class="pull-left">Chart of Used Shelf per Rack Category</h2>
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/sr/inventory" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            </div>
                        </div>
                        <hr/>
                        <div id="chartUsed" style="height: 565px; width: 98%; margin: 0px;"></div>
                        <input id="janUsed" type="text" value="${janUsed}" placeholder="${janUsed}" hidden="">
                        <input id="febUsed" type="text" value="${febUsed}" placeholder="${febUsed}" hidden="">
                        <input id="marUsed" type="text" value="${marUsed}" placeholder="${marUsed}" hidden="">
                        <input id="aprUsed" type="text" value="${aprUsed}" placeholder="${aprUsed}" hidden="">
                        <input id="mayUsed" type="text" value="${mayUsed}" placeholder="${mayUsed}" hidden="">
                        <input id="junUsed" type="text" value="${junUsed}" placeholder="${junUsed}" hidden="">
                        <input id="julUsed" type="text" value="${julUsed}" placeholder="${julUsed}" hidden="">
                        <input id="augUsed" type="text" value="${augUsed}" placeholder="${augUsed}" hidden="">
                        <input id="sepUsed" type="text" value="${sepUsed}" placeholder="${sepUsed}" hidden="">
                        <input id="octUsed" type="text" value="${octUsed}" placeholder="${octUsed}" hidden="">
                        <input id="novUsed" type="text" value="${novUsed}" placeholder="${novUsed}" hidden="">
                        <input id="decUsed" type="text" value="${decUsed}" placeholder="${decUsed}" hidden="">
                        <input id="excUsed" type="text" value="${excUsed}" placeholder="${excUsed}" hidden="">
                    </div>
                </div>     
                <c:if test="${availability == 1}">
                    <div class="col-lg-12">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="main-box clearfix">
                                    <div class="clearfix">
                                        <h2 class="pull-left">View Inventory Details</h2>
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
                                        <div class="filter-block pull-left">
                                            <button id="btn-show-all-children" type="button" class="btn btn-navbar pull-left"><i class="fa fa-box" style="color:darkcyan"></i> Collapse / <i class="fa fa-box-open" style="color:orangered"></i> Expand All</button>
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
                                        <table id="dt_spml" class="display" cellspacing="0" width="100%">
                                            <thead>
                                                <tr align = "center">
                                                    <th></th>
                                                    <th>No.</th>
                                                    <th>Rack ID</th>
                                                    <th>Shelf ID</th>
                                                    <th>Month</th>
                                                    <th>Outer ID</th>
                                                    <th>Inner Qty</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${inventoryMgtList}" var="inventory" varStatus="invListLoop">
                                                    <tr data-child-value="<br>${inventory.rmsLotEventConcat}">
                                                        <td class="details-control"></td>
                                                        <td align = "center"><c:out value="${invListLoop.index+1}"/></td>
                                                        <td><c:out value="${inventory.rackId}"/></td>
                                                        <td><c:out value="${inventory.shelfId}"/></td>
                                                        <td><c:out value="${inventory.rackMonth}"/></td>
                                                        <td><c:out value="${inventory.outerId}"/></td>
                                                        <td><c:out value="${inventory.countLot}"/></td>
                                                        <td><c:out value="${inventory.status}"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>                
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/js/jquery.canvasjs.min.js"></script>
        <script src="${contextPath}/resources/private/js/canvasjs.min.js"></script>
        <!--<script src="${contextPath}/resources/private/chart/js/chart.bundle.js"></script>-->
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            function format(value) {
                return '<div style=\"background-color:#eee; padding: .5em;\"><b>List of RMSLot_Event :</b> ' + value + '</div>'
            }
            $(document).ready(function () {
                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                
                var pTable = $('#rack_data').DataTable({
                    dom: 'Brtip',
                    paging: false,
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3 ]
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
                
                var oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5 ]
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
                
                
                // Add event listener for opening and closing details
                $('#dt_spml').on('click', 'td.details-control', function () {
                    var tr = $(this).closest('tr');
                    var row = oTable.row(tr);

                    if (row.child.isShown()) {
                        // This row is already open - close it
                        row.child.hide();
                        
                        tr.removeClass('shown');
                    } else {
                        // Open this row
                        row.child(format(tr.data('child-value'))).show();
                        tr.addClass('shown');
                    }
                });
                
                // Handle click on "Expand All" button
                $('#btn-show-all-children').on('click', function(){
                    // Expand row details
                    oTable.rows(':not(.parent)').nodes().to$().find('td:first-child').trigger('click');
                });

                // Handle click on "Collapse All" button
                $('#btn-hide-all-children').on('click', function(){
                    // Collapse row details
                    oTable.rows('.parent').nodes().to$().find('td:first-child').trigger('click');
                });
            });
            
            $(function () {  
                var janUsed = parseInt($('#janUsed').val());
                var febUsed = parseInt($('#febUsed').val());
                var marUsed = parseInt($('#marUsed').val());
                var aprUsed = parseInt($('#aprUsed').val());
                var mayUsed = parseInt($('#mayUsed').val());
                var junUsed = parseInt($('#junUsed').val());
                var julUsed = parseInt($('#julUsed').val());
                var augUsed = parseInt($('#augUsed').val());
                var sepUsed = parseInt($('#sepUsed').val());
                var octUsed = parseInt($('#octUsed').val());
                var novUsed = parseInt($('#novUsed').val());
                var decUsed = parseInt($('#decUsed').val());
                var excUsed = parseInt($('#excUsed').val());
                
                var chart = new CanvasJS.Chart("chartUsed", {
                    theme: "light1", zoomEnabled: true, animationEnabled: true,
                    options: {
                        scaleShowValues: true,
                        scales: {
                          yAxes: [{
                            ticks: {
                              beginAtZero: true
                            }
                          }],
                          xAxes: [{
                            ticks: {
                              autoSkip: false
                            }
                          }]
                        }
                      },
                    data: [{
                        type: "column",
                        indexLabelPlacement: "inside",
                        indexLabel: "{y}",
                        indexLabelOrientation: "horizontal",  // "horizontal", "vertical"
                        datasets: [
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false},
                            { autoSkip: false}
                        ],
                        dataPoints: [
                            {  y: janUsed, label: "JAN", autoSkip: false},
                            {  y: febUsed, label: "FEB", autoSkip: false},
                            {  y: marUsed, label: "MAR"},
                            {  y: aprUsed, label: "APR"},
                            {  y: mayUsed, label: "MAY"},
                            {  y: junUsed, label: "JUN"},
                            {  y: julUsed, label: "JUL"},
                            {  y: augUsed, label: "AUG"},
                            {  y: sepUsed, label: "SEP"},
                            {  y: octUsed, label: "OCT"},
                            {  y: novUsed, label: "NOV"},
                            {  y: decUsed, label: "DEC"},
                            {  y: excUsed, label: "EXC"}
                        ]
                    }]
                });
                chart.render();
            });
        </script>
    </s:layout-component>
</s:layout-render>