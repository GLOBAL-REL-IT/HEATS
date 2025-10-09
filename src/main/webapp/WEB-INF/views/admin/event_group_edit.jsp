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
            <!--<h1>Edit Event Group</h1>-->
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Event Group Information</h2>
                        <form id="add_group_form" class="form-horizontal" role="form" action="${contextPath}/admin/eventGroup/update" method="post">
                            <input type="hidden" name="groupId" value="${eventGroup.groupId}">
                            <div class="form-group">
                                <label for="groupCode" class="col-lg-4 control-label">Code *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="groupCode" name="groupCode" placeholder="Code" value="${eventGroup.eventGroupCode}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="groupDetails" class="col-lg-4 control-label">Group Details *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="groupDetails" name="groupDetails" placeholder="Details" value="${eventGroup.eventGroupDetails}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="groupStatus" class="col-lg-4 control-label">Group Status *</label>
                                <div class="col-lg-8">
                                    <select id="groupStatus" name="groupStatus" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = "" selected></option>
                                        <option value = "Active">Active</option>
                                        <option value = "Inactive">Inactive</option>
                                    </select>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/eventGroup" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                var validator = $("#add_group_form").validate({
                    rules: {
                        groupCode: {
                            required: true,
                            alphanumericdu: true,
                            minlength: 2,
                            maxlength: 10
                        },
                        groupName: {
                            required: true,
                            minlength: 2
                        },
                        groupStatus: {
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