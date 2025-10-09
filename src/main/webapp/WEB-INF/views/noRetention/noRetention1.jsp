<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
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
                            <h2 class="pull-left">Sample Exclude from Retention</h2>
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/sr/noRetention/addReq" class="btn btn-primary pull-right">
                                    <i class="fa fa-search-plus"></i> Add New Exclusion Request
                                </a>
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
                            <table id="dt_spml" class="display" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th align = "center"><span>No.</span></th>
                                        <th align = "center"><span>RMS_Event</span></th>
                                        <!--<th align = "center"><span>Event</span></th>-->
                                        <th align = "center"><span>Lot Type</span></th>
                                        <th align = "center"><span>Mth to Scrap</span></th>
                                        <th align = "center"><span>Pkg Family</span></th>
                                        <th align = "center"><span>Aging</span></th>
                                        <th align = "center"><span>Request By</span></th>
                                        <th align = "center"><span>Request Date</span></th>
                                        <th align = "center"><span>Manage</span></th>
                                        <th align = "center"><span>reqType</span></th>
                                        <th align = "center"><span>reasonsExc</span></th>
                                        <th align = "center"><span>reqName</span></th>
                                        <th align = "center"><span>relReqName</span></th>
                                        <th align = "center"><span>relDateReq</span></th>
                                        <th align = "center"><span>createdDate</span></th>
                                        <th align = "center"><span>remarks</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${srArchiveList}" var="srArchive" varStatus="srArchiveLoop">
                                        <tr data-child-value="${srArchive.reqType}^${srArchive.reasonsExc}^${srArchive.reqName}^${srArchive.relReqName} (${srArchive.relDateReq})^${srArchive.createdDate}^${srArchive.remarks}">
                                            <td class="details-control"></td>
                                            <td align = "center"><c:out value="${srArchiveLoop.index+1}"/></td>
                                            <td><c:out value="${srArchive.rmsId}_${srArchive.rmsEvent}"/></td> 
                                            <td><c:out value="${srArchive.lotConcat}"/></td>
                                            <td><c:out value="${srArchive.mthToScrap}"/></td>
                                            <td><c:out value="${srArchive.pkgFamily}"/></td>
                                            <td><c:out value="${srArchive.aging}"/></td>
                                            <td><c:out value="${srArchive.reqName}"/></td>
                                            <td><c:out value="${srArchive.relDateReq}"/></td>
                                            <td align = "center">
                                                <a modaldeleteid="${srArchive.groupId}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" title="Delete Request" onclick="modalDelete(this);">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x" style="color:red"></i>
                                                        <i class="fa fa-trash-alt fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                            </td>
                                            <td><c:out value="${srArchive.reqType}"/></td>
                                            <td><c:out value="${srArchive.reasonsExc}"/></td>
                                            <td><c:out value="${srArchive.reqName}"/></td>
                                            <td><c:out value="${srArchive.relReqName}"/></td>
                                            <td><c:out value="${srArchive.relDateReq}"/></td>
                                            <td><c:out value="${srArchive.createdDate}"/></td>
                                            <td><c:out value="${srArchive.remarks}"/></td>
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
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            function format(value) {
                var str = value;
                var split = str.split("^");
                
//                return '<div style=\"background-color:#eee; padding: .5em;\"><b>List of RMSLot_Event :</b> ' + value + '</div>'
                return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
                       '<tr><td>Request Type</td><td>:</td><td>'+split[0]+'</td></tr>'+
                       '<tr><td>Reasons Exclude</td><td>:</td><td>'+split[1]+'</td></tr>'+
                       '<tr><td>Requestor Name</td><td>:</td><td>'+split[2]+'</td></tr>'+
                       '<tr><td>Rel Lab Req. Name</td><td>:</td><td>'+split[3]+'</td></tr>'+
                       '<tr><td>Date Request</td><td>:</td><td>'+split[4]+'</td></tr>'+
                       '<tr><td>Remarks</td><td>:</td><td>'+split[5]+'</td></tr>'+
                       '</table>';
            }
            $(document).ready(function () {
                var table = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    columnDefs : [{
                        sortable : false,
                        targets : [ 0, 9 ]
                    }],
                    columns: [
                        { class : 'details-control',
                          orderable : false,
                          data : null,
                          defaultContent: ''
                        },
                        { data : 'no' },
//                        { data : 'rmsId' },
                        { data : 'rmsEvent' },
                        { data : 'lotConcat' },
                        { data : 'mthToScrap' },
                        { data : 'pkgFamily' },
                        { data : 'aging' },
                        { data : 'reqName' },
                        { data : 'status' },
                        { data : 'manage' },
                        { data : 'reqType',
                          visible : false
                        },
                        { data : 'reasonsExc',
                          visible : false
                        },
                        { data : 'reqName',
                          visible : false
                        },
                        { data : 'relReqName',
                          visible : false
                        },
                        { data : 'relDateReq',
                          visible : false
                        },
                        { data : 'createdDate',
                          visible : false
                        },
                        { data : 'remarks',
                          visible : false
                        }
                    ],
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 1, 2, 3, 4, 5, 6, 7, 8 ]
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
                $('#dt_spml').on('click', 'td.details-control', function () {
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
//                    $("#iconClose").hide();
//                    $("#iconsOpen").show();
                });

                // Handle click on "Collapse All" button
                $('#btn-hide-all-children').on('click', function(){
                    // Collapse row details
                    table.rows('.parent').nodes().to$().find('td:first-child').trigger('click');
//                    $("#iconsOpen").hide();
//                    $("#iconClose").show();
                });
            });
            
            function modalDelete(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/sr/noRetention/delReq/" + deleteId;
                var deleteMsg = "Are you sure want to delete all? All related data will be deleted.";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }
        </script>
    </s:layout-component>
</s:layout-render>