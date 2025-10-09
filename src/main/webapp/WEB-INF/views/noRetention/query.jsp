<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
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
                        <h2>Query</h2>
                        <form id="innerVerificationForm" class="form-horizontal" role="form" action="${contextPath}/sr/noRetention/query" method="post" style="width: 100%">
                            <div class="form-group" id="rmsIdDiv">
                                <label for="rms" class="col-lg-1 control-label">RMS</label>
                                <div class="col-lg-2">
                                    <!--<input type="text" class="form-control" id="rms" style="width: 100%" name="rms" value="">-->
                                    <select id="rms" name="rms" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${rmsList}" var="doList">
                                            <option value="${doList.rmsId}" >${doList.rmsId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="lot" class="col-lg-1 control-label">Lot</label>
                                <div class="col-lg-1">
                                    <select id="lot" name="lot" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${lotList}" var="doList">
                                            <option value="${doList.lotType}" >${doList.lotType}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="rmsEvent" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-3">
                                    <select id="rmsEvent" name="rmsEvent" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${eventList}" var="doList">
                                            <option value="${doList.rmsEvent}" >${doList.rmsEvent}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="pkgFamily" class="col-lg-1 control-label">Pkg Family</label>
                                <div class="col-lg-2">
                                    <select id="pkgFamily" name="packageFamily" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${pkgFamilyList}" var="doList">
                                            <option value="${doList.pkgFamily}" >${doList.pkgFamily}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv">
                                 <label for="packageFamily" class="col-lg-1 control-label">Pkg Name</label>
                                <div class="col-lg-2">
                                    <select id="pkgName" name="pkgName" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${pkgNameList}" var="doList">
                                            <option value="${doList.pkgName}" >${doList.pkgName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="monthScrap" class="col-lg-1 control-label">Month Scrap </label>
                                <div class="col-lg-2">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="monthScrap" class="form-control" id="monthScrap" value="">
                                    </div>
                                </div>
                                <label for="status" class="col-lg-1 control-label">Status</label>
                                <div class="col-lg-2">
                                    <select id="status" name="status" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${statusList}" var="stList">
                                            <option value="${stList.status}" >${stList.status}</option>
                                        </c:forEach>
                                    </select>
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
                            <table id="example" class="display" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <!--<th></th>-->
                                        <th>No.</th>
                                        <th align = "center">RMS</th>
                                        <th align = "center">Lot</th>
                                        <th align = "center">Event</th>
                                        <th align = "center">Pkg Family</th>
                                        <th align = "center">Pkg Name</th>
                                        <th align = "center">Mth to Scrap</th>
                                        <th align = "center">Status</th>
                                        <th align = "center">Manage</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${SrQuery}" var="doList" varStatus="ftpListLoop">
                                    <tr>
                                        <!--<td class="details-control"></td>-->
                                        <td><c:out value="${ftpListLoop.index+1}"/></td>
                                        <td><c:out value="${doList.rmsId}"/></td>
                                        <td><c:out value="${doList.lotType}"/></td>
                                        <td><c:out value="${doList.rmsEvent}"/></td>
                                        <td><c:out value="${doList.pkgFamily}"/></td>
                                        <td><c:out value="${doList.pkgName}"/></td>
                                        <td><c:out value="${doList.mthToScrap}"/></td>
                                        <td><c:out value="${doList.status}"/></td>
                                        <td align="center">
<<<<<<< HEAD
                                            <a href="${contextPath}/sr/noRetention/query/detail/${doList.ftpId}" id="editB" class="table-link" title="Detail">
=======
                                                <a onclick="window.open(this.href,'_blank');return false;" href="${contextPath}/sr/noRetention/query/detail/${doList.ftpId}" id="editB" class="table-link" title="Details">
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772
                                                <span class="fa-stack">
                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                    <i class="fa fa-arrow-circle-right fa-stack-1x fa-inverse"></i>
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
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/validation/bootstrap-datepicker.js"></script>
        
        <script src="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/js/buttons.dataTables.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/datatables.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script type="text/javascript">

            $(document).ready(function () {
                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });

                $("#monthScrap").datepicker({
                    format: "yyyy-mm",
                    viewMode: "months",
                    minViewMode: "months"
                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });

                  oTable = $('#example').DataTable({
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