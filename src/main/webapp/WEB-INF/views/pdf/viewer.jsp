<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_title">
        <f:message key="${pageTitle}"/>
    </s:layout-component>
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_header">
        <f:message key="${pageTitle}"/>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix ps-2">
                        <div class="clearfix">
                            <h2 class="pull-left"><f:message key="${pageTitle}"/></h2>
                            <div class="filter-block pull-right">
                                <c:if test="${empty backUrl}">
                                    <button type="back" class="btn btn-info" onclick="window.history.back();"><i class="bi bi-arrow-left-short"></i> Back</button>
                                </c:if>
                                <c:if test="${not empty backUrl}">
                                    <a href="${backUrl}" class="btn btn-info"><i class="bi bi-arrow-left-circle"></i> Back</a>
                                </c:if>
                            </div>
                        </div>
                        <hr/>
                        <iframe src="${contextPath}/resources/pdfjs/web/viewer.html?file=${pdfUrl}" style="border: none; width: 100%; height: 1460px;"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
    </s:layout-component>
</s:layout-render>