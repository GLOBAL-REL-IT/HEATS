<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <!--<link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/datatables.min.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/css/buttons.dataTables.min.css" />
        <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" type="text/css" />
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
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
            /*            .dataTables_wrapper .dt-buttons {
                            float:none;
                            text-align:right;
                        }*/

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>Sample Retention</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Shelf Management List</h2>
<div class="filter-block pull-right">
                                <a href="${contextPath}/sr/inventoryMgt/add/new" class="btn btn-primary pull-right">
                                    <!--<a href="${contextPath}/sr/retrieve/add" class="btn btn-primary pull-right">-->
                                    <i class="bi bi-plus h4"></i> Add New
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
                                    <tr>
                                        <th>No.</th>
                                        <th><span>Shelf ID</span></th>
                                        <th align = "center"><span>Status</span></th>
                                        <th align = "center"><span>RMSLot_Event</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${inventoryMgtList}" var="req" varStatus="reqLoop">
                                        <tr>
                                            <td><c:out value="${reqLoop.index+1}"/></td>
                                            <td><c:out value="${req.shelf}"/></td>   
                                            <td><c:out value="${req.status}"/></td> 
                                            <td><c:out value="${req.rmsLotEvent}"/></td> 
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
        <!--<script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>-->
        <!--<script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>-->
        <!--<script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>-->
        <!--<script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>-->
        <!--<script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>-->
        <script src="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/js/buttons.dataTables.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/datatables.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                            
                                            $(document).ready(function () {
                                                
                                                oTable = $('#dt_spml').DataTable({
                                                    dom: 'Brtip',
                                                    buttons: [
                                                        'copy', 'csv', 'print'
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