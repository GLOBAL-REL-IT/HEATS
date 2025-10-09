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
            <!--<h1>Edit Event</h1>-->
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Event Information</h2>
                        <form id="add_group_form" class="form-horizontal" role="form" action="${contextPath}/admin/event/update" method="post">
                            <div class="form-group">
                                <label for="groupCode" class="col-lg-4 control-label">Event Code *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="eventCode" name="eventCode" value="${event.eventCode}">
                                    <input type="text" id="eventId" name="eventId" value="${event.eventId}" hidden>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="eventName" class="col-lg-4 control-label">Event Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="eventName" name="eventName" placeholder="Details" value="${event.eventName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requirementStatus" class="col-lg-4 control-label">Retention Required? *</label>
                                <div class="col-lg-8">
                                    <select id="requirementStatus" name="requirementStatus" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = "" selected></option>
                                        <option value = "Yes">Yes</option>
                                        <option value = "No">No</option>
                                    </select>
                                </div>
                            </div>  
                            <div class="form-group">
                                <label for="eventGroupId" class="col-lg-4 control-label">Event Group *</label>
                                <div class="col-lg-8">
                                    <select id="eventGroupId" name="eventGroupId" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value = "" selected></option>
                                        <option value = "0">No Event Group</option>
                                        <c:forEach items="${eventGroupList}" var="eventGroup">
                                            <option value="${eventGroup.groupId}">${eventGroup.eventGroupCode}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>      
                            <a href="${contextPath}/admin/event" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                        eventCode: {
                            required: true,
                            minlength: 2,
                            maxlength: 10
                        },
                        eventName: {
                            required: true,
                            minlength: 2
                        },
                        requirementStatus: {
                            required: true
                        },
                        eventGroupId: {
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