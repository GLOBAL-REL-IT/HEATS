<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <!--<h1>Edit User</h1>-->
            <div class="row">
                <div class="col-lg-7">
                    <div class="main-box">
                        <h2>User Information</h2>
                        <form id="edit_user_form" class="form-horizontal" role="form" action="${contextPath}/admin/ldap_user/update" method="post">
                            <input type="hidden" name="userId" value="${user.id}">
                            <div class="form-group">
                                <label for="loginId" class="col-lg-4 control-label">Login ID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="loginId" name="loginId" placeholder="Login ID" value="${user.loginId}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="fullname" class="col-lg-4 control-label">Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Name" value="${user.firstname} ${user.lastname}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-lg-4 control-label">Email *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="email" name="email" placeholder="Email" value="${user.email}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="groupId" class="col-lg-4 control-label">Group *</label>
                                <div class="col-lg-8">
                                    <select id="groupId" name="groupId" class="form-control">
                                        <option value="" selected="">Select Group...</option>
                                        <c:forEach items="${userGroupList}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.code} - ${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="isActive" class="col-lg-4 control-label">Status *</label>
                                <div class="col-lg-8">
                                    <select id="isActive" name="isActive" class="form-control">
                                        <option value="0" <c:if test="${user.isActive == '0'}">selected=""</c:if>>Inactive</option>
                                        <option value="1" <c:if test="${user.isActive == '1'}">selected=""</c:if>>Active</option>
                                        </select>
                                    </div>
                                </div>
                                <a href="${contextPath}/admin/user" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <c:if test="${user.password!=null && sessionId=='1'}">
                    <div class="col-lg-5">
                        <div class="main-box">
                            <h2>Change Password</h2>
                            <form id="password_user_form" class="form-horizontal" role="form" action="${contextPath}/admin/user/password" method="post">
                                <input type="hidden" name="userId" value="${user.id}">
                                <div class="form-group">
                                    <label for="currentPassword" class="col-lg-4 control-label">Current Password *</label>
                                    <div class="col-lg-8">
                                        <input type="password" class="form-control" id="currentPassword" name="currentPassword" placeholder="Current Password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-lg-4 control-label">Password *</label>
                                    <div class="col-lg-8">
                                        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="confirmPassword" class="col-lg-4 control-label">Confirm Password *</label>
                                    <div class="col-lg-8">
                                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password">
                                    </div>
                                </div>
                                <div class="pull-right">
                                    <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                    <button type="submit" class="btn btn-primary">Change Password</button>
                                </div>
                                <div class="clearfix"></div>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="row">
                <div class="col-lg-7">
                    <div class="main-box">
                        <h2>Email Notification</h2>
                        <form id="auth_access_frm" class="form-horizontal" role="form" action="${contextPath}/admin/ldap_user/authAccess" method="post">
                            <input type="hidden" name="userLdapId" value="${user.id}">
                            <div class="form-group">
                                <label for="sr_retrieve_email" class="col-lg-5 control-label"><b>Retrieve Notification Email</b></label>
                                <div class="col-lg-6">
                                    <input type="radio" class="custom-control-input" id="sr_retrieve_email_active" name="radioSrRetrieveEmail" value="Active" ${srEmailRetrieveActive}>
                                    <label class="custom-control-label" for="defaultActive">Active</label>
                                    <input type="radio" class="custom-control-input" id="sr_retrieve_email_inactive" name="radioSrRetrieveEmail" value="Inactive" ${srEmailRetrieveInactive}>
                                    <label class="custom-control-label" for="defaultInactive">Inactive</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sr_ship_rl_email" class="col-lg-5 control-label"><b>Scrap Notification Email</b></label>
                                <div class="col-lg-6">
                                    <input type="radio" class="custom-control-input" id="sr_scrap_email_active" name="radioSrScrapEmail" value="Active" ${srEmailScrapActive}>
                                    <label class="custom-control-label" for="defaultActive">Active</label>
                                    <input type="radio" class="custom-control-input" id="sr_scrap_inactive" name="radioSrScrapEmail" value="Inactive" ${srEmailScrapInactive}>
                                    <label class="custom-control-label" for="defaultInactive">Inactive</label>
                                </div>
                            </div>
<!--                            <div class="form-group">
                                <label for="features_test_email" class="col-lg-5 control-label"><b>Ability to Send Test Email</b></label>
                                <div class="col-lg-6">
                                    <c:if test="${sessionId=='1'}">
                                        <input type="radio" class="custom-control-input" id="features_test_email_active" name="radioFeaturesTestEmail" value="Active" ${featuresTestEmailActive}>
                                        <label class="custom-control-label" for="defaultActive">Active</label>
                                        <input type="radio" class="custom-control-input" id="features_test_email_inactive" name="radioFeaturesTestEmail" value="Inactive" ${featuresTestEmailInactive}>
                                        <label class="custom-control-label" for="defaultInactive">Inactive</label>
                                    </c:if>
                                    <c:if test="${sessionId!='1'}">
                                        <input type="radio" class="custom-control-input" id="features_test_email_active" name="radioFeaturesTestEmail" value="Active" disabled="" ${featuresTestEmailActive}>
                                        <label class="custom-control-label" for="defaultActive">Active</label>
                                        <input type="radio" class="custom-control-input" id="features_test_email_inactive" name="radioFeaturesTestEmail" value="Inactive" disabled="" ${featuresTestEmailInactive}>
                                        <label class="custom-control-label" for="defaultInactive">Inactive</label>
                                    </c:if>    
                                </div>
                            </div>-->
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Update</button>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                var edit_user_form = $("#edit_user_form").validate({
                    rules: {
                        loginId: {
                            required: true,
                            alphanumeric: true,
                            minlength: 2
                        },
                        fullname: {
                            required: true,
                            letterspace: true,
                            minlength: 2
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        groupId: {
                            required: true
                        }
                    }
                });
                var password_user_form = $("#password_user_form").validate({
                    rules: {
                        currentPassword: {
                            required: true
                        },
                        password: {
                            required: true,
                            minlength: 8
                        },
                        confirmPassword: {
                            required: true,
                            minlength: 8,
                            equalTo: password
                        }
                    }
                });
                $("#edit_user_form .cancel").click(function () {
                    edit_user_form.resetForm();
                });
                $("#password_user_form .cancel").click(function () {
                    password_user_form.resetForm();
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>