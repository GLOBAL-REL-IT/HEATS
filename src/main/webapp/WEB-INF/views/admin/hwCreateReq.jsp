<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
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
/*            tr:nth-child(even) {
                background-color: #eee;
            }*/
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-11">
                    <div class="main-box">
                        <h2>Upload File to Create Initial Request</h2>
                        <form id="upload_hwbarcode_file_frm" class="form-horizontal" role="form" action="${contextPath}/admin/hwCreateReq/upload" method="post"  enctype="multipart/form-data">
                            <div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <!--<span class="fa-stack"><i class="fa fa-exclamation-triangle fa-stack-2x" style="color:red"></i></span>-->
                                <b><i>Mandatory Requirement</i> >> File Type :</b> .csv || <b>Date Format :</b> yyyy-mm-dd hh:mm:ss || <b>Record Limit :</b> Per GTS No. || 
                                <p><b>Column :</b> req_type, box_id, pack_type, item_type, item_id, item_qty, pcbB_id, pcbB_qty, pcbC_id, pcbC_qty, pcbCtr_id, pcbCtr_qty, lc_id, lc_qty, pc_id, pc_qty, remarks, price, weight, gts, ship_date
                                
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label" for="fileUpload">Upload File (.csv format only)</label>
                                <div class="col-lg-5">
                                    <input type="file" class="filestyle" data-buttonBefore="true" data-buttonBefore="true" data-iconName="glyphicon glyphicon-inbox" data-buttonName="btn-info" id="fileUpload" name="fileUpload" />
                                </div>
                            </div>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary"><i class="fa fa-barcode fa-lg"></i> Create Request</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                var validator = $("#upload_hwbarcode_file_frm").validate({
                    rules: {
                        fileUpload: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });
            </script>
    </s:layout-component>
</s:layout-render>