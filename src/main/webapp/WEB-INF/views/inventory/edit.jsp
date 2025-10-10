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
            <!--<h1>Sample Retention Request</h1>-->
            <div class="row">
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Inventory - RMS Detail</h2>
                        <hr/>
                        <!--<div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>-->
                        <!--<b>*Please verify the RMS information accordingly before proceeding with the inventory.</b>-->

                        <!--</div>-->    
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/sr/inventory/update" method="post" style="width: 100%">
                            <div class="form-group" id="requestDiv">
                                <label for="rmsId" class="col-lg-1 control-label">RMS#</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="rmsId" style="width: 100%" name="rmsId" placeholder="" value="${request.rmsId}" readonly>
                                    <input type="hidden" class="form-control" id="rmsLotEvent" style="width: 100%" name="rmsLotEvent" placeholder="" value="${request.rmsLotEvent}">
                                    <input type="hidden" class="form-control" id="invId" style="width: 100%" name="invId" placeholder="" value="${request.invId}">
                                    <input type="hidden" class="form-control" id="reqId" style="width: 100%" name="reqId" placeholder="" value="${request.id}">
                                    <input type="hidden" class="form-control" id="ftpId" style="width: 100%" name="ftpId" placeholder="" value="${request.ftpId}">
                                </div>
                                <label for="rmsEvent" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsEvent" style="width: 100%" name="rmsEvent" placeholder="" value="${request.rmsEvent}" readonly>
                                </div>
                                <label for="lotType" class="col-lg-1 control-label">Lot</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lotType" style="width: 100%" name="lotType" placeholder="" value="${request.lotType}" readonly>
                                </div>
                                <label for="finalQty" class="col-lg-2 control-label">Quantity</label>
                                <div class="col-lg-2">
                                    <input type="number" class="form-control" id="finalQty" style="width: 100%" name="finalQty" placeholder="" value="${request.lotQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="requestDiv">
                                <label for="pkgFamily" class="col-lg-1 control-label">Package Family</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pkgFamily" style="width: 100%" name="pkgFamily" placeholder="" value="${request.pkgFamily}" readonly>
                                </div>
                                <label for="pkgName" class="col-lg-1 control-label">Package Name</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pkgName" style="width: 100%" name="pkgName" placeholder="" value="${request.pkgName}" readonly>
                                </div>
                                <label for="completeDate" class="col-lg-1 control-label">compl. Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="completeDate" style="width: 100%" name="completeDate" placeholder="" value="${request.completedDate}" readonly>
                                </div>
                                <label for="mthToScrap" class="col-lg-1 control-label">Scrap Date</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="mthToScrap" style="width: 100%" name="mthToScrap" placeholder="" value="${request.mthToScrap}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="testDiv">
                                <label for="stressTypeMidPoint" class=" col-lg-1 control-label text-left" >Stress Type Mid - Point</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="stressTypeMidPoint" name="stressTypeMidPoint" value="${request.stressTypeMidPoint}" readonly>
                                </div>
                            </div>
                            <hr class="separator">
                            <div class="form-group col-lg-10" style="font-style: italic; color: green;" >
                                * Scan Shelf Barcode for Inventory.</font
                                <br />
                            </div>
                            <div class="form-group col-lg-12" id = "alert_placeholder"></div>
                            <div class="form-group" id="requestDiv">
                                <label for="shelf" class="col-lg-1 control-label">Shelf</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="shelf" style="width: 100%" name="shelf" placeholder="" value="" >
                                    <input type="hidden" class="form-control" id="currentShelf" style="width: 100%" name="currentShelf" placeholder="" value="${request.shelf}">
                                    <small id="noteBsEmail" class="form-text text-muted">Current Location : ${request.shelf}</small>
                                </div>

                            <!--<a href="${contextPath}/request/" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>-->
                                <button type="submit" id="submit" class="btn btn-primary">Update and Print Barcode Sticker</button>
                                <!--<div class="pull-right">-->
                                <!--<button type="submit" id="submit" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Save</button>-->
                            </div>
                            <!--<a href="${contextPath}/sr/retrieve" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>-->
                            <a href="${contextPath}/sr/inventory" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="clearfix"></div>
                        </form>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>

            $(document).ready(function () {
                $('#Shelf').bind('copy paste cut', function (e) {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste  
                });

                var validator = $("#edit_hardwarequest_form").validate({
                    rules: {
                        finalQty: {
                            required: true,
                            number: true
                        },
                        shelf: {
                            required: true
                        }
                    }
                });

            });
        </script>
    </s:layout-component>
</s:layout-render>