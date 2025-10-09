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
            <div class="clearfix">
                <h1 class="pull-left">Completed RMS Event Pending for Retention as per ${revDate}</h1>
            </div>
            <!--New Tab Menu-->
            <!--<hr class="separator">-->
            <div class="col-lg-12">
                <div class="row">
                    <ul class="nav nav-tabs">
                        <li class="${allActive}"><a data-toggle="tab" href="#all">RMS List Pending for Retention</a></li>
                    </ul>

                    <div class="tab-content">
                        <!--Tab for all active-->
                        <div id="all" class="tab-pane fade ${allActiveTab}">
                            <h6></h6>
                            <div class="col-lg-12">
                                <div class="main-box">
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
                                                <tr>
                                                    <th align = "center">No</th>
                                                    <th align = "center">RMS#</th> 
                                                    <th align = "center">Event</th>
                                                    <th align = "center">Lot Type</th>
                                                    <th align = "center">Mth to Scrap</th>
                                                    <th align = "center">Pkg Family</th>
                                                    <th align = "center">Pkg Name</th>
                                                    <th align = "center">Compl. Date</th>
                                                    <th align = "center">Pending Inventory</th>
                                                    <th align = "center">Days Left to Scrap</th>
                                                    <th align = "center">RMS Status</th>
                                                    <th align = "center">Manage</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${ftpDataList}" var="ftpdata" varStatus="ftpListLoop">
                                                    <tr>                                            
                                                        <td><c:out value="${ftpListLoop.index+1}"/></td>
                                                        <td><c:out value="${ftpdata.rmsId}"/></td>
                                                        <td><c:out value="${ftpdata.event}"/></td>
                                                        <td><c:out value="${ftpdata.lotType}"/></td>
                                                        <td><c:out value="${ftpdata.mthToScrap}"/></td>
                                                        <td><c:out value="${ftpdata.pkgFamily}"/></td>
                                                        <td><c:out value="${ftpdata.pkgName}"/></td>
                                                        <td><c:out value="${ftpdata.completeDate}"/></td>
                                                        <td align="center"><c:out value="${ftpdata.packingDay}"/> Days</td>
                                                        <td align="center"><c:out value="${ftpdata.aging}"/> Days</td>
                                                        <td><c:out value="${ftpdata.processStatus}"/></td>
                                                        <td align="center">    
                                                            <a modaldeleteid="${ftpdata.id}" title="Cancel Retention" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                                    <i style = "color:red;" class="bi bi-x-circle danger h3"></i>
                                                            </a>
                                                                <a class="table-link" id="manage" href="${contextPath}/sr/request/add/${ftpdata.rmsLotEvent}" title="Inventory">
                                                    <i class="bi bi-arrow-right-circle h3"></i>
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
                </div>
            </div>

        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <!--print-->
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

                                                function modalDelete(e) {
                                                    var deleteId = $(e).attr("modaldeleteid");
                                                    var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                    var deleteUrl = "${contextPath}/sr/srFtp/cancelRetention/" + deleteId;
//                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                    var deleteMsg = "Are you sure want to cancel this RMS for retention?";
                                                    $("#delete_modal .modal-body").html(deleteMsg);
                                                    $("#modal_delete_button").attr("href", deleteUrl);
                                                }
            </script>
    </s:layout-component>
</s:layout-render>