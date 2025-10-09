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
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Sample Retrieval - RMS Detail</h2>
                        <!--<hr/>-->
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/save" method="post" style="width: 100%">

                            <div class="form-group" id="requestDiv">
                                <label for="rmsId" class="col-lg-1 control-label">RMS#</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="rmsId" style="width: 100%" name="rmsId" placeholder="" value="${inv.rmsId}" readonly>
                                    <input type="hidden" class="form-control" id="reqId" style="width: 100%" name="reqId" placeholder="" value="${inv.reqId}">
                                    <input type="hidden" class="form-control" id="rmsLotEvent" style="width: 100%" name="rmsLotEvent" placeholder="" value="${inv.rmsLotEvent}">
                                    <input type="hidden" class="form-control" id="invId" style="width: 100%" name="invId" placeholder="" value="${inv.id}">
                                </div>
                                <label for="rmsEvent" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsEvent" style="width: 100%" name="rmsEvent" placeholder="" value="${inv.rmsEvent}" readonly>
                                </div>
                                <label for="lotType" class="col-lg-1 control-label">Lot</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lot" style="width: 100%" name="lot" placeholder="" value="${inv.lot}" readonly>
                                </div>
                                <label for="finalQty" class="col-lg-2 control-label">Quantity</label>
                                <div class="col-lg-2">
                                    <input type="number" class="form-control" id="qty" style="width: 100%" name="qty" placeholder="" value="${inv.qty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="requestDiv">
                                <label for="pkgFamily" class="col-lg-1 control-label">Package Family</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pkgFamily" style="width: 100%" name="pkgFamily" placeholder="" value="${inv.packageFamily}" readonly>
                                </div>
                                <label for="pkgName" class="col-lg-1 control-label">Package Name</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pkgName" style="width: 100%" name="pkgName" placeholder="" value="${inv.packageName}" readonly>
                                </div>
                                <label for="completeDate" class="col-lg-1 control-label">compl. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="completeDate" style="width: 100%" name="completeDate" placeholder="" value="${inv.completeDate}" readonly>
                                </div>
                                <label for="mthToScrap" class="col-lg-1 control-label">Scrap Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="mthToScrap" style="width: 100%" name="mthToScrap" placeholder="" value="${inv.mthToScrap}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv" >
                                <label for="location" class="col-lg-1 control-label">Location</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="location" style="width: 100%" name="location" placeholder="" value="${inv.inventoryShelf}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv" >
                                <label for="rmsLotEventList" class="col-lg-1 control-label">Returnable</label>
                                <div class="col-lg-2">
                                    <select id="returnable" name="returnable" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = ""></option>
                                        <option value = "Returnable">Returnable</option>
                                        <option value = "Non-Returnable">Non-Returnable</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="remarksDiv">
                                <label for="remarks" class="col-lg-1 control-label">Remarks</label>
                                <div class="col-lg-10">
                                    <textarea class="form-control" id="reqRemarks" name="reqRemarks" maxlength="200" placeholder="Type in your message" rows="5"></textarea>
                                    <span class="pull-right label label-default" id="count_message"></span>
                                </div>
                            </div>

                            <a href="${contextPath}/sr/inventory" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-mail-bulk"></i> Send Request</button>
                            </div> 
                            <div class="clearfix"></div>
                        </form>
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
                    returnable: {
                        required: true
                    }
                }
            });

        });
    </script>
</s:layout-component>
</s:layout-render>