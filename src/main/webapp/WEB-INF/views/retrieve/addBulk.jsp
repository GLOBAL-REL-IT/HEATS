<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <!--<link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />-->
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

            span.tab-space {
                padding-left:20em;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>Sample Retention</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box">
                        <h2>Sample Retrieval - RMS Detail</h2>
                        <!--<hr/>-->
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/saveBulk" method="post" style="width: 100%">

                            <div class="form-group" id="rmsIdDiv" >
                                <label for="rmsLotEventList" class="col-lg-1 control-label">RMS Event</label>
                                <div class="col-lg-4">
                                    <select id="rmsLotEventList" name="rmsLotEventList" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <c:forEach items="${inventoryList}" var="invInner">
                                            <option 
                                                invIdValue="${invInner.id}" 
                                                reqIdValue="${invInner.reqId}" 
                                                rmsLotEventValue="${invInner.rmsLotEvent}" 
                                                locationValue="${invInner.inventoryShelf}" 
                                                rmsIdValue="${invInner.rmsId}" 
                                                lotValue="${invInner.lot}" 
                                                rmsEventValue="${invInner.rmsEvent}" 
                                                pkgNameValue="${invInner.packageName}" 
                                                pkgFamilyValue="${invInner.packageFamily}" 
                                                qtyValue="${invInner.qty}" 
                                                completeDateValue="${invInner.completeDate}" 
                                                mthToScrapValue="${invInner.mthToScrap}"
                                                value="${invInner.rmsLotEvent}">${invInner.rmsLotEvent}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="rmsLotEventList" class="col-lg-1 control-label">Returnable</label>
                                <div class="col-lg-2">
                                    <select id="returnable" name="returnable" class="js-example-basic-single" style="width: 100%">
                                        <option value = ""></option>
                                        <option value = "Returnable">Returnable</option>
                                        <option value = "Non-Returnable">Non-Returnable</option>
                                    </select>
                                </div>
                                <label for="location" class="col-lg-1 control-label">Location</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="location" style="width: 100%" name="location" placeholder="" value="" readonly>
                                </div>
                            </div>

                            <div class="form-group" id="requestDiv">
                                <label for="rmsId" class="col-lg-1 control-label">RMS#</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="rmsId" style="width: 100%" name="rmsId" placeholder="" value="" readonly>
                                    <input type="hidden" class="form-control" id="reqId" style="width: 100%" name="reqId" placeholder="" value="">
                                    <input type="hidden" class="form-control" id="rmsLotEvent" style="width: 100%" name="rmsLotEvent" placeholder="" value="">
                                    <input type="hidden" class="form-control" id="invId" style="width: 100%" name="invId" placeholder="" value="">
                                    <input type="hidden" class="form-control" id="bulkId" style="width: 100%" name="bulkId" placeholder="" value="${bulkId}">
                                    <input type="hidden" class="form-control" id="user" style="width: 100%" name="user" placeholder="" value="${user}">
                                </div>
                                <label for="rmsEvent" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsEvent" style="width: 100%" name="rmsEvent" placeholder="" value="" readonly>
                                </div>
                                <label for="lotType" class="col-lg-1 control-label">Lot</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lot" style="width: 100%" name="lot" placeholder="" value="" readonly>
                                </div>
                                <label for="finalQty" class="col-lg-2 control-label">Quantity</label>
                                <div class="col-lg-2">
                                    <input type="number" class="form-control" id="qty" style="width: 100%" name="qty" placeholder="" value="" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="requestDiv">
                                <label for="pkgFamily" class="col-lg-1 control-label">Package Family</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pkgFamily" style="width: 100%" name="pkgFamily" placeholder="" value="" readonly>
                                </div>
                                <label for="pkgName" class="col-lg-1 control-label">Package Name</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pkgName" style="width: 100%" name="pkgName" placeholder="" value="" readonly>
                                </div>
                                <label for="completeDate" class="col-lg-1 control-label">compl. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="completeDate" style="width: 100%" name="completeDate" placeholder="" value="" readonly>
                                </div>
                                <label for="mthToScrap" class="col-lg-1 control-label">Scrap Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="mthToScrap" style="width: 100%" name="mthToScrap" placeholder="" value="" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="remarksDiv">
                                <label for="remarks" class="col-lg-1 control-label">Remarks</label>
                                <div class="col-lg-10">
                                    <textarea class="form-control" id="reqRemarks" name="reqRemarks" maxlength="200" placeholder="Type in your message" rows="5"></textarea>
                                    <span class="pull-right label label-default" id="count_message"></span>
                                </div>
                            </div>

                            <a href="${contextPath}/sr/retrieve" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-solid fa-plus"></i> Add</button>
                            </div> 
                            <div class="clearfix"></div>
                        </form>
                    </div>

                    <!--table-->
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Retrieval List</h2>
                            <div class="filter-block pull-right">
                                <c:if test="${countDetail != 0}">
                                <!--<a href="${contextPath}/sr/retrieve/sendRequest/${bulkId}/${user}" class="btn btn-primary pull-right">-->
                                <a href="#confirmation_modal" data-toggle="modal" class="btn btn-primary pull-right" onclick="modalSendRequest(this);" >
                                    <i class="fa fa-mail-bulk"></i> Submit Request
                                </a>
                                </c:if>
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
                                        <th align = "center"><span>RMSLotEvent</span></th>
                                        <th align = "center"><span>Pkg Name</span></th>
                                        <th align = "center"><span>Mth To Scrap</span></th>
                                        <th align = "center"><span>Qty</span></th>
                                        <th align = "center"><span>Request Date</span></th>
                                        <th align = "center"><span>Shelf ID</span></th>
                                        <th align = "center"><span>Returnable</span></th>
                                        <th align = "center"><span>Remark</span></th>
                                        <th align = "center"><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${bulkRetrieveDetail}" var="req" varStatus="reqLoop">
                                    <tr>
                                        <td align = "center"><c:out value="${reqLoop.index+1}"/></td>
                                    <td><c:out value="${req.rmsLotEvent}"/></td>   
                                    <td><c:out value="${req.pkgName}"/></td> 
                                    <td><c:out value="${req.scrapDate}"/></td>
                                    <td><c:out value="${req.qty}"/></td>
                                    <td><c:out value="${req.createdDate}"/></td>
                                    <td><c:out value="${req.location}"/></td>
                                    <td><c:out value="${req.returnable}"/></td>
                                    <td><c:out value="${req.remarks}"/></td>
                                    <td align="center">
                                        <a modaldeleteid="${req.id}" data-toggle="modal" href="#confirmation_modal" class="table-link danger group_delete" title="Cancel Request" onclick="modalDelete(this);">
                                            <!--<i class="bi bi-x-square h3" style="color:red"></i>-->
                                            <i class="fa fa-solid fa-trash" style="color:red"></i>
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
            <!-- -->
            <!-- -->        
            <!-- --> 
        </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
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


                                                $('#rmsLotEventList').change(function () {
                                                    $('#invId').val($('option:selected', this).attr('invIdValue'));
                                                    $('#reqId').val($('option:selected', this).attr('reqIdValue'));
                                                    $('#rmsId').val($('option:selected', this).attr('rmsIdValue'));
                                                    $('#lot').val($('option:selected', this).attr('lotValue'));
                                                    $('#rmsEvent').val($('option:selected', this).attr('rmsEventValue'));
                                                    $('#rmsLotEvent').val($('option:selected', this).attr('rmsLotEventValue'));
                                                    $('#location').val($('option:selected', this).attr('locationValue'));
                                                    $('#pkgFamily').val($('option:selected', this).attr('pkgFamilyValue'));
                                                    $('#pkgName').val($('option:selected', this).attr('pkgNameValue'));
                                                    $('#completeDate').val($('option:selected', this).attr('completeDateValue'));
                                                    $('#qty').val($('option:selected', this).attr('qtyValue'));
                                                    $('#mthToScrap').val($('option:selected', this).attr('mthToScrapValue'));
                                                });

                                                $(".js-example-basic-single").select2({
                                                    placeholder: "Choose one",
                                                    allowClear: true
                                                });

                                                var text_max = 200;
                                                $('#count_message').html('0 / ' + text_max);

                                                $('#reqRemarks').keyup(function () {
                                                    var text_length = $('#reqRemarks').val().length;
                                                    var text_remaining = text_max - text_length;

                                                    $('#count_message').html(text_length + ' / ' + text_max);
                                                });

                                                var validator = $("#edit_hardwarequest_form").validate({
                                                    rules: {
                                                        rmsLotEventList: {
                                                            required: true
                                                        },
                                                        returnable: {
                                                            required: true
                                                        }
                                                    }
                                                });

                                            });

                                            function modalDelete(e) {
                                                var deleteId = $(e).attr("modaldeleteid");
                                                var user = $('#user').val();
                                                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                var deleteUrl = "${contextPath}/sr/retrieve/cancelBulk/" + deleteId + '/' + user;
                                                var deleteMsg = "Are you sure want to cancel this sample?";
                                                $("#confirmation_modal .modal-body").html(deleteMsg);
                                                $("#modal_button").attr("href", deleteUrl);
                                            }

                                            function modalSendRequest(e) {
                                                var bulkId = $('#bulkId').val();
                                                var user = $('#user').val();
                                                var deleteUrl = "${contextPath}/sr/retrieve/sendRequest/" + bulkId + '/' + user;
                                                var deleteMsg = "Please click 'Confirm' button to proceed";
                                                $("#confirmation_modal .modal-body").html(deleteMsg);
                                                $("#modal_button").attr("href", deleteUrl);
                                            }
        </script>
    </s:layout-component>
</s:layout-render>