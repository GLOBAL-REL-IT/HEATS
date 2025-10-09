<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <style>
            .highlight {
                border-color: red;
                box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(126, 239, 104, 0.6);
                outline: 0 none;
            }
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
        <div class="col-lg-12">
            <h1>Retrieval Process</h1>
            <div class="row">
                <div class="col-lg-10">
                    <div class="main-box">
                        <h2>RMS Details</h2>
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/test" method="post" style="width: 100%">

                            <div class="form-group" id="requestDiv">
                                <label for="rmsId" class="col-lg-1 control-label">RMS#</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="rmsId" style="width: 100%" name="rmsId" placeholder="" value="${retrieve.rmsId}" readonly>
                                    <input type="hidden" class="form-control" id="reqId" style="width: 100%" name="reqId" placeholder="" value="${retrieve.reqId}">
                                    <input type="hidden" class="form-control" id="rmsLotEvent" style="width: 100%" name="rmsLotEvent" placeholder="" value="${retrieve.rmsLotEvent}">
                                    <input type="hidden" class="form-control" id="invId" style="width: 100%" name="invId" placeholder="" value="${retrieve.id}">
                                </div>
                                <label for="rmsEvent" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsEvent" style="width: 100%" name="rmsEvent" placeholder="" value="${retrieve.rmsEvent}" readonly>
                                </div>
                                <label for="lotType" class="col-lg-1 control-label">Lot</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lot" style="width: 100%" name="lot" placeholder="" value="${retrieve.lot}" readonly>
                                </div>
                                <label for="finalQty" class="col-lg-2 control-label">Quantity</label>
                                <div class="col-lg-2">
                                    <input type="number" class="form-control" id="qty" style="width: 100%" name="qty" placeholder="" value="${retrieve.qty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="requestDiv">
                                <label for="pkgFamily" class="col-lg-1 control-label">Package Family</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pkgFamily" style="width: 100%" name="pkgFamily" placeholder="" value="${retrieve.packageFamily}" readonly>
                                </div>
                                <label for="pkgName" class="col-lg-1 control-label">Package Name</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pkgName" style="width: 100%" name="pkgName" placeholder="" value="${retrieve.packageName}" readonly>
                                </div>
                                <label for="completeDate" class="col-lg-1 control-label">compl. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="completeDate" style="width: 100%" name="completeDate" placeholder="" value="${retrieve.completeDate}" readonly>
                                </div>
                                <label for="mthToScrap" class="col-lg-1 control-label">Scrap Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="mthToScrap" style="width: 100%" name="mthToScrap" placeholder="" value="${retrieve.mthToScrap}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="rmsIdDiv" >
                                <label for="location" class="col-lg-1 control-label">Location</label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="location" style="width: 100%" name="location" placeholder="" value="${retrieve.shelf}" readonly>
                                </div>
                                <label for="rmsLotEventList" class="col-lg-1 control-label">Returnable</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="location" style="width: 100%" name="location" placeholder="" value="${retrieve.returnable}" readonly>
                                </div>
                                <label for="rmsLotEventList" class="col-lg-1 control-label">Status</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="status" style="width: 100%" name="status" placeholder="" value="${retrieve.status}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="remarksDiv">
                                <label for="remarks" class="col-lg-1 control-label">Remarks</label>
                                <div class="col-lg-10">
                                    <textarea class="form-control" id="reqRemarks" name="reqRemarks" maxlength="200" placeholder="Type in your message" rows="5" readonly>${retrieve.reqRemarks}</textarea>
                                </div>
                            </div>

                            <a href="${contextPath}/sr/retrieve" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <!--                            <div class="pull-right">
                                                            <button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-mail-bulk"></i> Send Request</button>
                                                        </div> -->
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
        <hr class="separator">
        <div class="col-lg-12">
            <br>
            <div class="row">
                <ul class="nav nav-tabs">
                    <li class="${reActive}"><a data-toggle="tab" href="#re">Verification </a></li>
                    <li class="${ldActive}"><a data-toggle="tab" href="#ld">Receipt/Return </a></li>
                    <li class="${udActive}"><a data-toggle="tab" href="#ud">Inventory</a></li>
                </ul>
                <div class="tab-content">

                    <!--tab for received-->

                    <div id="re" class="tab-pane fade ${reActiveTab}">
                        <br>
                        <h6></h6>
                        <div class="col-lg-10">
                            <div class="main-box">
                                <h2>Verification Details</h2>
                                <form id="ver_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/VerifiyUpdate" method="post">
                                    <input type="hidden" name="id" id="id" value="${retrieve.id}" />
                                    <input type="hidden" name="reqId" id="reqId" value="${retrieve.reqId}" />
                                    <input type="hidden" name="tab" value="${reActive}" />
                                    <c:if test="${retrieve.status == 'Request for Retrieval'}">
                                        <div class="form-group">
                                            <label for="ReceivedDate" class="col-lg-1 control-label">RMSLotEvent </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="verification"  name="verification" placeholder="Pls scan box barcode sticker" value="" autofocus="">
                                                <input type="hidden" name="rmsLotEventValue1" id="rmsLotEventValue1" value="${retrieve.rmsLotEvent}" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="verificationStatus" class="col-lg-1 control-label">Shelf ID </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="shelfIdVerification" name="shelfIdVerification" placeholder="Pls scan shelf ID" value="" >
                                                <input type="hidden" name="shelfIdValue" id="shelfIdValue" value="${retrieve.shelf}" />
                                            </div>
                                        </div>
                                        <div class="pull-right">
                                            <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                            <button type="submit" id="verify" name="verify" class="btn btn-primary">Verify</button>
                                        </div>
                                    </c:if>
                                    <c:if test="${retrieve.status != 'Request for Retrieval'}"> 
                                        <div class="form-group">
                                            <label for="verificationBy" class="col-lg-1 control-label">Verification By </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="verificationBy"  name="verificationBy" value="${retrieve.verificationBy}" readonly> 
                                            </div>
                                            <label for="verificationDate" class="col-lg-1 control-label">Verification Date </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="verificationDate" name="verificationDate"value="${retrieve.verificationDate}" readonly>
                                            </div>
                                        </div>
                                        <!--                                        <div class="form-group">
                                                                                    <label for="verificationDate" class="col-lg-1 control-label">Verification Date </label>
                                                                                    <div class="col-lg-3">
                                                                                        <input type="text" class="form-control" id="verificationDate" name="verificationDate"value="${retrieve.verificationDate}" readonly>
                                                                                    </div>
                                                                                </div>-->
                                    </c:if>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for receipt/return-->

                    <div id="ld" class="tab-pane fade ${ldActiveTab}">
                        <br>
                        <h6></h6>
                        <div class="col-lg-10">
                            <div class="main-box">
                                <h2>Receipt Details</h2>
                                <form id="rec_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/receiveUpdate" method="post">
                                    <input type="hidden" name="id" id="id" value="${retrieve.id}" />
                                    <input type="hidden" name="reqId" id="reqId" value="${retrieve.reqId}" />
                                    <input type="hidden" name="tab" value="${reActive}" />

                                    <c:if test="${retrieve.status == 'Verified. Ready for Pickup'}">
                                        <div class="form-group">
                                            <label for="ReceivedDate" class="col-lg-1 control-label">RMSLotEvent </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="receipt"  name="receipt" placeholder="Pls scan box barcode sticker" value="" autofocus="">
                                                <input type="hidden" name="rmsLotEventValue2" id="rmsLotEventValue2" value="${retrieve.rmsLotEvent}" />
                                            </div>
                                            <label for="returnable" class="col-lg-1 control-label">Returnable</label>
                                            <div class="col-lg-3">
                                                <select id="returnable" name="returnable" class="js-example-basic-single" style="width: 100%">
                                                    <option value = ""></option>
                                                    <option value = "Returnable">Returnable</option>
                                                    <option value = "Non-Returnable">Non-Returnable</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="pull-right">
                                            <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                            <button type="submit" id="verify" name="verify" class="btn btn-primary">Save</button>
                                        </div>
                                    </c:if>
                                    <c:if test="${retrieve.status != 'Verified. Ready for Pickup'}">
                                        <div class="form-group">
                                            <label for="verificationBy" class="col-lg-1 control-label">Received By </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="verificationBy1"  name="verificationBy1" value="${retrieve.rlReceivedBy}" readonly> 
                                            </div>
                                            <label for="verificationDate" class="col-lg-1 control-label">Received Date </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="verificationDate1" name="verificationDate1" value="${retrieve.rlReceivedDate}" readonly>
                                            </div>
                                        </div>
                                        <!--                                        <div class="form-group">
                                                                                    <div class="col-lg-2">
                                                                                        <input type="text" class="form-control" id="location" style="width: 100%" name="location" placeholder="" value="${retrieve.returnable}" readonly>
                                                                                    </div>
                                                                                </div>-->
                                    </c:if>
                                    <div class="clearfix"></div>
                                    <hr/>
                                </form>
                            </div>
                            <c:if test="${retrieve.returnable == 'Returnable'}">
                                <div class="main-box">
                                    <h2>Return Details</h2>
                                    <form id="ret_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/returnUpdate" method="post">
                                        <input type="hidden" name="id" id="id" value="${retrieve.id}" />
                                        <input type="hidden" name="reqId" id="reqId" value="${retrieve.reqId}" />
                                        <input type="hidden" name="retId" id="retId" value="${retrieve.id}" />
                                        <input type="hidden" name="tab" value="${reActive}" />

                                        <c:if test="${retrieve.status == 'Received'}">
                                            <div class="form-group">
                                                <label for="ReceivedDate" class="col-lg-1 control-label">RMSLotEvent </label>
                                                <div class="col-lg-3">
                                                    <input type="text" class="form-control" id="returnV"  name="returnV" placeholder="Pls scan box barcode sticker" value="" autofocus="">
                                                    <input type="hidden" name="rmsLotEventValue3" id="rmsLotEventValue3" value="${retrieve.rmsLotEvent}" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="ReceivedDate" class="col-lg-1 control-label">Qty Return </label>
                                                <div class="col-lg-1">
                                                    <input type="text" class="form-control" id="returnQty"  name="returnQty" placeholder="" value="">
                                                    <!--<input type="text" class="form-control" id="qtyV" style="width: 100%" name="qtyV" placeholder="" value="${retrieve.qty}">-->
                                                </div>
                                            </div>
                                            <div class="filter-block pull-left">
                                                <a modaldeleteid="${req.id}" data-toggle="modal" href="#confirmation_modal" class="btn btn-secondary pull-left text-danger" title="Cancel Return" onclick="modalDelete(this);">
                                                    <i class="bi bi-file-plus h4"></i> Cancel Return
                                                </a>
                                            </div>
                                            <div class="pull-right">
                                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                                <button type="submit" id="verify" name="verify" class="btn btn-primary">Save</button>
                                            </div>
                                        </c:if>
                                        <c:if test="${retrieve.status != 'Received'}">
                                            <div class="form-group">
                                                <label for="ReceivedDate" class="col-lg-1 control-label">Return By </label>
                                                <div class="col-lg-3">
                                                    <input type="text" class="form-control" id="returnBy1"  name="returnBy1" value="${retrieve.returnBy}" readonly>
                                                </div>
                                                <label for="ReceivedDate" class="col-lg-1 control-label">Return Date </label>
                                                <div class="col-lg-3">
                                                    <input type="text" class="form-control" id="returnDate1"  name="returnDate1" value="${retrieve.returnDate}" readonly>
                                                </div>
                                                <label for="ReceivedDate" class="col-lg-1 control-label">Qty Return </label>
                                                <div class="col-lg-1">
                                                    <input type="text" class="form-control" id="returnQty1"  name="returnQty1" value="${retrieve.qty}" readonly>
                                                </div>
                                            </div>
                                            <!--                                            <div class="form-group">
                                                                                            <label for="ReceivedDate" class="col-lg-1 control-label">Qty Return </label>
                                                                                            <div class="col-lg-1">
                                                                                                <input type="text" class="form-control" id="returnQty1"  name="returnQty1" value="${retrieve.qty}" readonly>
                                                                                            </div>
                                                                                        </div>-->
                                        </c:if>
                                        <div class="clearfix"></div>
                                        <hr/>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!--tab for re-inventory-->

                    <div id="ud" class="tab-pane fade ${udActiveTab}">
                        <br>
                        <h6></h6>
                        <div class="col-lg-10">
                            <div class="main-box">
                                <h2>Inventory Details</h2>
                                <form id="inv_form" class="form-horizontal" role="form" action="${contextPath}/sr/retrieve/reInventoryUpdate" method="post">
                                    <input type="hidden" name="id" id="id" value="${retrieve.id}" />
                                    <input type="hidden" name="reqId" id="reqId" value="${retrieve.reqId}" />
                                    <input type="hidden" name="tab" value="${reActive}" />
                                    <c:if test="${retrieve.status == 'Return for Inventory'}">
                                        <div class="form-group">
                                            <label for="reInventory" class="col-lg-1 control-label">Shelf ID</label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="reInventory" name="reInventory" placeholder="Pls scan shelf ID" value="" autofocus="">
                                                <small id="noteBsEmail" class="form-text text-muted">Original Location: ${retrieve.shelf}</small>
                                                <input type="hidden" name="shelfIdValue2" id="shelfIdValue2" value="${retrieve.shelf}" />
                                            </div>
                                        </div>
                                        <div class="pull-right">
                                            <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                            <button type="submit" id="verify" name="verify" class="btn btn-primary">Save</button>
                                        </div>
                                    </c:if>
                                    <c:if test="${retrieve.status != 'Return for Inventory'}">
                                        <div class="form-group">
                                            <label for="reInventory" class="col-lg-1 control-label">Shelf ID</label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="reInventory2" name="reInventory2" value="" readonly>
                                                <small id="noteBsEmail" class="form-text text-muted">Original Location: ${retrieve.shelf}</small>
                                                <input type="hidden" name="shelfIdValue2" id="shelfIdValue2" value="${retrieve.shelf}" />
                                            </div>
                                        </div>
                                    </c:if>

                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                                    $(document).ready(function () {
                                                        
                                                          $(".js-example-basic-single").select2({
                                                placeholder: "Choose one",
                                                allowClear: true
                                            });


                                                        //                $('#closedVerification').bind('copy paste cut', function (e) {
                                                        $('#verification').bind('copy paste cut', function (e) {
                                                            e.preventDefault(); //this line will help us to disable cut,copy,paste		
                                                        });
                                                        $('#shelfIdVerification').bind('copy paste cut', function (e) {
                                                            e.preventDefault(); //this line will help us to disable cut,copy,paste		
                                                        });
                                                        $('#receipt').bind('copy paste cut', function (e) {
                                                            e.preventDefault(); //this line will help us to disable cut,copy,paste		
                                                        });
                                                        $('#returnV').bind('copy paste cut', function (e) {
                                                            e.preventDefault(); //this line will help us to disable cut,copy,paste		
                                                        });
                                                        $('#returnQty').bind('copy paste cut', function (e) {
                                                            e.preventDefault(); //this line will help us to disable cut,copy,paste		
                                                        });
                                                        $('#reInventory').bind('copy paste cut', function (e) {
                                                            e.preventDefault(); //this line will help us to disable cut,copy,paste		
                                                        });

                                                        jQuery.extend(jQuery.validator.messages, {
                                                            required: "This field is required.",
                                                            equalTo: "Value is not match! Please re-scan.",
                                                            email: "Please enter a valid email."
                                                        });


                                                        var validator1 = $("#ver_form").validate({
                                                            rules: {
                                                                verification: {
                                                                    required: true,
                                                                    equalTo: "#rmsLotEventValue1"
                                                                },
                                                                shelfIdVerification: {
                                                                    required: true,
                                                                    equalTo: "#shelfIdValue"
                                                                }
                                                            }
                                                        });

                                                        var validator2 = $("#rec_form").validate({
                                                            rules: {
                                                                receipt: {
                                                                    required: true,
                                                                    equalTo: "#rmsLotEventValue2"
                                                                },
                                                                returnable: {
                                                                    required: true
                                                                }
                                                            }
                                                        });

                                                        var validator3 = $("#ret_form").validate({
                                                            rules: {
                                                                returnV: {
                                                                    required: true,
                                                                    equalTo: "#rmsLotEventValue3"
                                                                },
                                                                returnQty: {
                                                                    required: true,
                                                                    number: true
                                                                }
                                                            }
                                                        });

                                                        var validator4 = $("#inv_form").validate({
                                                            rules: {
                                                                reInventory: {
                                                                    required: true,
                                                                    equalTo: "#shelfIdValue2"
                                                                }
                                                            }
                                                        });

                                                        $(".cancel").click(function () {
                                                            validator1.resetForm();
                                                        });
                                                        $(".cancel").click(function () {
                                                            validator2.resetForm();
                                                        });
                                                        $(".cancel").click(function () {
                                                            validator3.resetForm();
                                                        });
                                                        $(".cancel").click(function () {
                                                            validator4.resetForm();
                                                        });
                                                    });
                                                    
                                                       function modalDelete(e) {
                                                            var deleteId = $('#retId').val();
                                                            var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                            var deleteUrl = "${contextPath}/sr/retrieve/cancelReturn/" + deleteId;
                                                            var deleteMsg = "Are you certain you want to cancel the return of this sample?";
                                                            $("#confirmation_modal .modal-body").html(deleteMsg);
                                                            $("#modal_button").attr("href", deleteUrl);
                                                        }
                                                        

        </script>
    </s:layout-component>
</s:layout-render>