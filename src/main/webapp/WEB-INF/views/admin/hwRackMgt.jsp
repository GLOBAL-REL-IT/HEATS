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
/*            tr:nth-child(even) {
                background-color: #eee;
            }*/
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Item Management List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/admin/hwItemMgt/addRack" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add Item
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
                            <div class="filter-block pull-right">
                                <div class="form-group pull-left" style="margin-right: 0px;">
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>Rack ID</span></th>
                                        <th><span>Rack Category</span></th>
                                        <th><span>Total Shelves</span></th>
                                        <th><span>Status</span></th>
                                        <th><span>Last Modified</span></th>
                                        <th><span>Created By</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${hwRackList}" var="rack" varStatus="rackLoop">
                                        <tr>
                                            <td><c:out value="${rackLoop.index+1}"/></td>
                                            <td><c:out value="${rack.rackId}"/></td>
                                            <td><c:out value="${rack.rackCategory}"/></td>
                                            <td><c:out value="${rack.rackTotalShelves}"/></td>
                                            <td><c:out value="${rack.status}"/></td>
                                            <td><c:out value="${rack.modifiedDate}"/> by <c:out value="${rack.modifiedBy}"/></td>
                                            <td><c:out value="${rack.createdBy}"/></td>
                                            <td align="center">
                                                <a href="${contextPath}/admin/hwRackMgt/edit/${rack.id}" class="table-link" title="Edit">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil-alt fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a modaldeleteid="${rack.id}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" title="Delete Request" onclick="modalDelete(this);">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x" style="color:red"></i>
                                                        <i class="fa fa-trash-alt fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
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
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                var table = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    columnDefs : [{
                        sortable : false,
                        targets : [ 7 ]
                    }],
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6 ]
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
            
            function modalDelete(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/admin/hwRackMgt/delete/" + deleteId;
                var deleteMsg = "Are you confirm you want to delete this item category?";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }
            
            </script>
    </s:layout-component>
</s:layout-render>