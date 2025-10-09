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
            <!--<h1>Sample Retention Request</h1>-->
            <div class="row">
            <div class="col-lg-12">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="main-box clearfix">
                            <div class="clearfix">
                                <h2 class="pull-left">Pending Request Summary</h2>
                            </div>
                            <!--<hr/>-->
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
                                    <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 2px;">
                                    </div>
                                    <div class="form-group pull-left" style="margin-right: 0px;">
                                        <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                        <i class="fa fa-search search-icon"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table id="dt_details" cellspacing="0" width="100%">
                                    <thead>
                                        <tr align = "center">
                                            <!--<th></th>-->
                                            <th>No.</th>
                                            <th align = "center">Package Family</th>
                                            <th align = "center">Event</th>
                                            <th align = "center">Total Lot</th>
                                            <th align = "center">Manage</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${summList}" var="summ" varStatus="summLoop">
                                            <tr>
                                                <td align = "center"><c:out value="${summLoop.index+1}"/></td>
                                                <td align = "center"><c:out value="${summ.pkgFamily}"/></td>   
                                                <td align = "center"><c:out value="${summ.event}"/></td> 
                                                <td align = "center"><c:out value="${summ.totalLot}"/></td>
                                                <td align = "center">
                                                    <a href="${contextPath}/sr/sampleReq/addReq/${summ.pkgFamily}/${summ.event}" id="createReq" name="createReq" class="table-link" title="Create Request">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-plus-circle fa-stack-2x "></i>
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
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>        
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {                
                var oTable = $('#dt_details').DataTable({
//                    dom: 'Brtip',
                    columnDefs : [{
                        sortable : false,
                        targets : [ 4 ]
                    }]
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