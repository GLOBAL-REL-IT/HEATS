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
                <div class="col-lg-7">
                    <div class="main-box" id ="shipNewDiv">
                        <h2>New Shelf Detail</h2>
                        <form id="innerVerificationForm" class="form-horizontal" role="form" action="${contextPath}/sr/inventoryMgt/save" method="post" style="width: 100%">
                            <div class="form-group" id="rmsIdDiv">
                                <label for="stress" class="col-lg-2 control-label">Stress/Unstress</label>
                                <div class="col-lg-3">
                                    <!--<input type="text" class="form-control" id="rms" style="width: 100%" name="rms" value="">-->
                                    <select id="stress" name="stress" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <option value = "Stress">Stress</option>
                                        <option value = "Unstress">Unstress</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv">
                                <label for="month" class="col-lg-2 control-label">Month</label>
                                <div class="col-lg-3">
                                    <!--<input type="text" class="form-control" id="rms" style="width: 100%" name="rms" value="">-->
                                    <select id="month" name="month" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <option value = "JAN">JAN</option>
                                        <option value = "FEB">FEB</option>
                                        <option value = "MAR">MAR</option>
                                        <option value = "APR">APR</option>
                                        <option value = "MAY">MAY</option>
                                        <option value = "JUN">JUN</option>
                                        <option value = "JUL">JUL</option>
                                        <option value = "AUG">AUG</option>
                                        <option value = "SEP">SEP</option>
                                        <option value = "OCT">OCT</option>
                                        <option value = "NOV">NOV</option>
                                        <option value = "DEC">DEC</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv">
                                <label for="cabinet" class="col-lg-2 control-label">Cabinet#</label>
                                <div class="col-lg-2">
                                    <input type="number" class="form-control" id="cabinet" style="width: 100%" name="cabinet" value="">
                                    <small id="noteBsEmail" class="form-text text-muted">Max : 99</small>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv">
                                <label for="qty" class="col-lg-2 control-label">Total Qty</label>
                                <div class="col-lg-2">
                                    <input type="number" class="form-control" id="qty" style="width: 100%" name="qty" value="">
                                    <small id="noteBsEmail" class="form-text text-muted">Max : 9999</small>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <button tabindex=""me="submit" id="submit" class="btn btn-primary pull-right" ><i class="bi bi-arrow-right-circle h5"></i> Generate</button>
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

               var validator1 = $("#innerVerificationForm").validate({
                    rules: {
                        stress: {
                            required: true
                        },
                        month: {
                            required: true
                        },
                        cabinet: {
                            required: true,
                            number: true,
                            max: 99,
                            min: 1
                        },
                        qty: {
                            required: true,
                            number: true,
                            max: 9999,
                            min: 1
                        }
                    }
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