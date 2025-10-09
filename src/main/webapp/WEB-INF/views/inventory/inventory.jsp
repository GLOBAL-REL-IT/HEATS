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
                            <h2 class="pull-left">Inventory List</h2>

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
                                        <th align = "center"><span>RMSLotEvent</span></th>
                                        <th align = "center"><span>Pkg Family</span></th>
                                        <th align = "center"><span>Pkg Name</span></th>
                                        <th align = "center"><span>Qty</span></th>
                                        <th align = "center"><span>Mth To Scrap</span></th>
                                        <th align = "center"><span>Scrap Due</span></th>
                                        <th align = "center"><span>Inventory Date</span></th>
                                        <th align = "center"><span>Shelf ID</span></th>
                                        <th align = "center"><span>Status</span></th>
                                        <th align = "center"><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${inventoryList}" var="req" varStatus="reqLoop">
                                        <tr>
                                            <td align = "center"><c:out value="${reqLoop.index+1}"/></td>
                                            <td><c:out value="${req.rmsLotEvent}"/></td>   
                                            <td><c:out value="${req.packageFamily}"/></td> 
                                            <td><c:out value="${req.packageName}"/></td> 
                                            <td><c:out value="${req.qty}"/></td> 
                                            <td><c:out value="${req.mthToScrap}"/></td>
                                            <td><c:out value="${req.aging} Days"/>
                                                <c:if test="${req.aging <= 0}">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-exclamation-triangle fa-stack-1x" style="color:red"></i>
                                                    </span>
                                                </c:if>
                                            </td>
                                            <td><c:out value="${req.inventoryDate}"/></td>
                                            <td><c:out value="${req.inventoryShelf}"/></td>
                                            <td><c:out value="${req.status}"/></td>
                                            <td align="center">
                                                <c:if test="${groupId != '3'}">
                                                    <c:if test="${req.status != 'In Inventory'}">
                                                        <a class="table-link" id="barcode" title="Barcode Sticker">
                                                            <i class="bi bi-upc-scan h3" style="color:gray"></i>
                                                        </a>
                                                        <a class="table-link" id="location" title="Change Location">
                                                            <i class="bi bi-pencil-square h3" style="color:gray"></i>
                                                        </a>
                                                        <a class="table-link" id="retrieve" title="Request for Retrieve">
                                                            <i class="bi bi-box-arrow-right h3" style="color:gray"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${req.status == 'In Inventory'}">
                                                        <a class="table-link" id="barcode" href="#" title="Barcode Sticker" onclick="window.open('${contextPath}/sr/request/viewBarcodeStickerPdf/${req.reqId}', 'Barcode Sticker', 'width=800,height=900').print()">
                                                            <i class="bi bi-upc-scan h3"></i>
                                                        </a>
                                                        <a class="table-link" id="location" href="${contextPath}/sr/inventory/edit/${req.reqId}" title="Change Location">
                                                            <i class="bi bi-pencil-square h3"></i>
                                                        </a>
                                                        <a class="table-link" id="retrieve" href="${contextPath}/sr/retrieve/add1/${req.id}" title="Request for Retrieve">
                                                            <i class="bi bi-box-arrow-right h3"></i>
                                                        </a> 
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${groupId == '3'}">
                                                    <c:if test="${req.status != 'In Inventory'}">
                                                        <a class="table-link" id="barcode" title="Barcode Sticker">
                                                            <i class="bi bi-upc-scan h3" style="color:gray"></i>
                                                        </a>
                                                        <a class="table-link" id="location" title="Change Location">
                                                            <i class="bi bi-pencil-square h3" style="color:gray"></i>
                                                        </a>
                                                        <a class="table-link" id="retrieve" title="Request for Retrieve">
                                                            <i class="bi bi-box-arrow-right h3" style="color:gray"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${req.status == 'In Inventory'}">
                                                        <a class="table-link" id="barcode" title="Barcode Sticker">
                                                            <i class="bi bi-upc-scan h3" style="color:gray"></i>
                                                        </a>
                                                        <a class="table-link" id="location" title="Change Location">
                                                            <i class="bi bi-pencil-square h3" style="color:gray"></i>
                                                        </a>
                                                        <a class="table-link" id="retrieve" href="${contextPath}/sr/retrieve/add1/${req.id}" title="Request for Retrieve">
                                                            <i class="bi bi-box-arrow-right h3"></i>
                                                        </a> 
                                                    </c:if>  
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