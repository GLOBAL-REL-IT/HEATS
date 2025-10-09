<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>Update Item Information</h2>
                        <form id="add_item_type_form" class="form-horizontal" role="form" action="${contextPath}/admin/hwItemMgt/edit/save" method="post">
                            <input type="hidden" name="hwItemId" id="hwItemId" value="${hwItem.id}">
                            <div class="form-group">
                                <label for="itemCategory" class="col-lg-4 control-label">Item Category</label>
                                <div class="col-lg-7">
                                    <input type="text" class="form-control" id="itemCategory" name="itemCategory" value="${hwItem.itemCategory}" placeholder="${hwItem.itemCategory}" required="" readonly="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sptsItemType" class="col-lg-4 control-label">Item Type as in SPTS</label>
                                <div class="col-lg-7">
                                    <input type="text" class="form-control" id="sptsItemType" name="sptsItemType" value="${hwItem.sptsItemType}" placeholder="${hwItem.sptsItemType}" required="" readonly="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sptsSubItemType" class="col-lg-4 control-label">Sub Item Type as in SPTS (optional)</label>
                                <div class="col-lg-7">
                                    <input type="text" class="form-control" id="sptsSubItemType" name="sptsSubItemType" value="${hwItem.sptsSubItemType}" placeholder="${hwItem.sptsSubItemType}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="modelType" class="col-lg-4 control-label">Wildcard Model Type in SPTS (optional)</label>
                                <div class="col-lg-7">
                                    <input type="text" class="form-control" id="modelType" name="modelType" value="${hwItem.sptsModelContain}" placeholder="${hwItem.sptsModelContain}">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="sptsItemId" class="col-lg-4 control-label">Wildcard Item ID in SPTS (optional)</label>
                                <div class="col-lg-7">
                                    <input type="text" class="form-control" id="sptsItemId" name="sptsItemId" value="${hwItem.sptsItemId}" placeholder="${hwItem.sptsItemId}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rackId" class="col-lg-4 control-label">Current Rack Identification</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rackId" name="rackId" value="${hwItem.rackIdentification}" readonly="">
                                </div>
                                <label for="rackIdentification" class="col-lg-2 control-label">Update Rack Identification?</label>
                                <div class="col-lg-3">
                                    <select id="rackIdentification" name="rackIdentification" class="js-example-basic-single" style="width: 100%" >
                                        <option value = "" selected></option>
                                        <c:forEach items="${hwRackList}" var="hwRack">
                                            <option value="${hwRack.id}">${hwRack.rackId} [${hwRack.rackCategory}]</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="transferStatus" class="col-lg-4 control-label">Current Status</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="transferStatus" name="transferStatus" value="${hwItem.status}" readonly="">
                                </div>
                                <label for="activeStatus" class="col-lg-2 control-label">Update Status?</label>
                                <div class="col-lg-3">
                                    <select id="activeStatus" name="activeStatus" class="js-example-basic-single" style="width: 100%" autofocus="autofocus" required="">
                                        <option value = "" selected></option>
                                        <option value = "Active">Yes (Active)</option>
                                        <option value = "Inactive">No (Inactive)</option>
                                    </select>
                                </div>
                            </div>  
                            <a href="${contextPath}/admin/hwItemMgt" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(".js-example-basic-single").select2({
                placeholder: "Choose one",
                allowClear: true
            });
            $(document).ready(function () {
                var validator = $("#add_item_type_form").validate({
                    rules: {
                        itemCategory: {
                            required: true,
                        },
                        sptsItemType: {
                            required: true,
                            minlength: 3,
                            maxlength: 50
                        },
                        sptsSubItemType: {
                            minlength: 0,
                            maxlength: 50
                        },
                        modelType: {
                            minlength: 0,
                            maxlength: 50
                        },
                        sptsItemId: {
                            minlength: 0,
                            maxlength: 50
                        },
                        activeStatus: {
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