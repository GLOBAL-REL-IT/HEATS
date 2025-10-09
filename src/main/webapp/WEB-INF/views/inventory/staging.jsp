<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
<!--        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />-->
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

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>List of Packaging</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Staging List</h2>
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/sr/inventory" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                            <div class="filter-block pull-left">
                                <button id="btn-show-all-children" type="button" class="btn btn-navbar pull-left"><i class="fa fa-box" style="color:darkcyan"></i> Collapse / <i class="fa fa-box-open" style="color:orangered"></i> Expand All</button>
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
                            <table id="example" class="display" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Outer ID</th>
                                        <th>Pkg Family</th>
                                        <th>Event</th>
                                        <th>Inner Qty</th>
                                        <th>Mth to Scrap</th>
                                        <th>GTS No.</th>
                                        <th>Est. Collection Date</th>
                                        <th>Days Left to Scrap</th>
                                        <th>Status</th>
                                        <th>Manage</th>
                                        <th>rmsLotEventConcat</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${shippingList}" var="shipping" varStatus="ftpListLoop">
                                        <tr data-child-value="<br>${shipping.rmsLotEvent}">
                                            <td class="details-control"></td>
                                            <td><c:out value="${shipping.outerId}"/></td>
                                            <td><c:out value="${shipping.pkgFamily}"/></td>
                                            <td><c:out value="${shipping.event}"/></td>
                                            <td><c:out value="${shipping.countLot}"/></td>
                                            <td><c:out value="${shipping.mthToScrap}"/></td>
                                            <td><c:out value="${shipping.gtsNo}"/></td>
                                            <td><c:out value="${shipping.shippingDate}"/></td>
                                            <td><c:out value="${shipping.aging}"/>
                                                <c:if test="${shipping.aging <= 0}">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-exclamation-triangle fa-stack-1x" style="color:red"></i>
                                                    </span>
                                                </c:if>
                                            </td>
                                            <td><c:out value="${shipping.status}"/></td>
                                            <td align="left">
                                                <c:if test="${groupId == '1'}">
                                                    <a href="${contextPath}/sr/srShipping/edit/${shipping.outerPkgNo}" class="table-link" id="edit" title="Update">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                </c:if>
                                                
                                                <a class="table-link" href="#" title="Barcode Sticker" onclick="window.open('${contextPath}/sr/srShipping/viewOuterBarcodePdf/${shipping.outerPkgNo}', 'Barcode Sticker', 'width=800,height=900').print()">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x" style="color:seagreen"></i>
                                                        <i class="fa fa-print fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <c:if test="${shipping.flag == '0'}">
                                                    <a href="${contextPath}/sr/srShipping/add/${shipping.outerPkgNo}" id="add" name="add" class="table-link" title="Add into DO">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-plus-circle fa-stack-2x " style="color:steelblue"></i>
                                                        </span>
                                                    </a>
                                                </c:if>
                                            </td>
                                            <td><c:out value="${shipping.rmsLotEvent}"/></td>
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
<!--        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.tableTools.js"></script>-->

        <!--print-->
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>

            function format(value) {
                return '<div><b>List of RMSLot_Event :</b> ' + value + '</div>';
            }
            $(document).ready(function () {
                var table = $('#example').DataTable({
                    dom: 'Brtip',
                    columnDefs : [{
                        sortable : false,
                        targets : [ 0, 10 ]
                    }],
                    columns: [
                        { class : 'details-control',
                          orderable : false,
                          data : null,
                          defaultContent: ''
                        },
                        { data : 'outerId' },
                        { data : 'pkgFamily' },
                        { data : 'event' },
                        { data : 'innerQty' },
                        { data : 'mthToScrap' },
                        { data : 'gtsNo' },
                        { data : 'estShip' },
                        { data : 'aging' },
                        { data : 'status' },
                        { data : 'manage' },
                        { data : 'rmsLotEventConcat',
                          visible : false
                        }
                    ],
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
                    table.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    table.page.len($(this).val()).draw();
                });

                // Add event listener for opening and closing details
                $('#example').on('click', 'td.details-control', function () {
                    var tr = $(this).closest('tr');
                    var row = table.row(tr);

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
                    table.rows(':not(.parent)').nodes().to$().find('td:first-child').trigger('click');
                });

                // Handle click on "Collapse All" button
                $('#btn-hide-all-children').on('click', function(){
                    // Collapse row details
                    table.rows('.parent').nodes().to$().find('td:first-child').trigger('click');
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>