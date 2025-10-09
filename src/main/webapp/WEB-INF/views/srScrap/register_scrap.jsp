<%-- 
    Document   : register_scrap
    Created on : Jan 8, 2025, 4:44:27 PM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
<!--        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" type="text/css" />-->

        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/css/buttons.dataTables.min.css" />
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
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>Create Scrap List</h1>-->
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Scrap - RMS Event Detail</h2>
                        <form id="add_mp_list_form" class="form-horizontal" role="form" action="${contextPath}/sr/scrap/readyScrap" method="post">
                            <div class="form-group">
                                <label for="rmsEvent" class="col-lg-4 control-label">Barcode Sticker (RMS_Event#) *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="rmsEvent" name="rmsEvent" placeholder="RMS_Lot_Event" value="" autofocus="autofocus">
                                </div>
                            </div>
                            <a href="${contextPath}/sr/scrap/pendingList" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Add</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Scrap List</h2>

                            <c:choose>
                                <c:when test="${count == '0'}">
                                    <div class="filter-block pull-right">
                                        <a title="Scrap" data-toggle="modal" href="#" style="color:grey" class="btn disabled warning pull-right">
                                            <i class="bi bi-trash-fill"></i>Remove All Scrap Data
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="filter-block pull-right">
                                        <a title="Scrap" data-toggle="modal" href="#delete_modal" class="btn btn-primary pull-right group_delete" onclick="modalDelete2(this)">
                                            <i class="bi bi-trash-fill"></i>Remove All Scrap Data
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <hr/>
                        <div class="clearfix">
                            <div class="form-group pull-left">
                                <select id="dt_spml_rows" class="form-control">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
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
                            <table id="dt_spml" class="table align-center">
                                <thead title="Scrap">
                                    <tr>
                                        <!--                                        <th class="col-lg-1"><span>No</span></th>
                                                                                <th class="col-lg-2"><span>RMS_Event</span></th>
                                                                                <th class="col-lg-2"><span>Intervals</span></th>
                                                                                <th class="col-lg-2"><span>Quantity</span></th>
                                                                                <th class="col-lg-2"><span>GTS No</span></th>
                                                                                <th class="col-lg-2"><span>Shipment Date</span></th>-->
                                        <th><span>No</span></th>
                                        <th class="col-lg-2"><span>Scrap Month</span></th>
                                        <th class="col-lg-2"><span>Inventory</span></th>
                                        <th class="col-lg-2"><span>RMS Lot Event</span></th>
                                        <th class="col-lg-2"><span>Package Name</span></th>
                                        <th class="col-lg-2"><span>Status</span></th>
                                        <th class="col-lg-1"><span>Revert</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${srScrapList}" var="scrapList" varStatus="whMpListLoop">
                                        <tr>
                                            <td class="col-lg-1 align-center"><c:out value="${whMpListLoop.index+1}"/></td>
                                            <td class="col-lg-2"><c:out value="${scrapList.monthScrap}"/></td>
                                            <td class="col-lg-2"><c:out value="${scrapList.shelf}"/></td>
                                            <td class="col-lg-2"><c:out value="${scrapList.rmsLotEvent}"/></td>
                                            <td class="col-lg-2"><c:out value="${scrapList.packageName}"/></td>
                                            <td class="col-lg-2"><c:out value="${scrapList.status}"/></td>
                                            <td align="left">
                                                <c:if test="${scrapList.flag == '0'}">
                                                    <a modaldeleteid="${scrapList.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete1(this);">
                                                        <!--                                                <span class="fa-stack">
                                                                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                                                                            <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                                                                        </span>-->
                                                        <i class='bx bx-recycle h4'></i>
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
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
<!--        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>-->

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

                var validator = $("#add_mp_list_form").validate({
                    rules: {
                        rmsEvent: {
                            required: true
                        }
                    }
                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });

            });

            function modalDelete1(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/sr/scrap/revertScrapStatus/" + deleteId;
                var deleteMsg = "Are you sure want to revert this sample?";
                $("#delete_modal .modal-body").html(deleteMsg);
                $("#modal_delete_button").attr("href", deleteUrl);
            }
            
            function modalDelete2(e) {
                var deleteUrl2 = "${contextPath}/sr/scrap/scrap_all/";
                var deleteMsg2 = "Are you sure want to scrap all registered sample?";
                $("#delete_modal .modal-body").html(deleteMsg2);
                $("#modal_delete_button").attr("href", deleteUrl2);
            }
        </script>
    </s:layout-component>
</s:layout-render>