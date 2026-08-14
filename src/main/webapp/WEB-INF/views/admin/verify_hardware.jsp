<%-- 
    Document   : verify_hardware
    Created on : Aug 14, 2026, 9:30:33 AM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <!-- Content wrapper start -->
        <div class="content-wrapper">
            <div id="resultMsg"></div>
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Scan Hardware ID</h5>
                        </div>
                        <div class="card-body">
                            <form id="scanForm" class="row g-3 align-items-center" role="form" action="${contextPath}/hw/item/hardware/verifyLagi" method="post">
                                <div class="row mb-3">
                                    <label class="col-sm-1 col-md-1 col-form-label fw-semibold">Scan HW ID Here</label>
                                    <div class="col-sm-3 col-md-3">
                                        <input type="text" class="form-control" name="scanqr" id="scanqr">
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-primary">Verify</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="app-footer">
                <img class="img3" src="${contextPath}/resources/onsemi logo.webp" alt="onsemi">
                <span>© HEATs 2025</span>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">

    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            
        </script>
    </s:layout-component>
</s:layout-render>