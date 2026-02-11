<%-- 
    Document   : create_hardware_id
    Created on : Feb 9, 2026, 4:05:28 PM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <!--<link rel="stylesheet" href="${contextPath}/resources/"/>-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/"/>-->
        <!--<link rel="stylesheet" href="${contextPath}/resources/"/>-->
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            input[readonly] {
                border: none;
                /*background-color:#f0f0f0;*/
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-12 ps-2">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="card-title">${itemType} - <span style="color:#D97D55"> Hardware ID Creation</span></h5>
                </div>
                <c:set var="hardwareconfig" value="${maklumatconfig}"/>
                <c:choose>
                    <c:when test="${not empty hardwareconfig}">
                        <form class="row gx-3 " role="form" action="${contextPath}/hw/item/hardware/create" method="post">
                            <div class="card-body">
                                <div class="p-3 mb-4 rounded-3">
                                    <div class="row gx-3">
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="itemType">Item Type </label>
                                                <input type="text" class="form-control" id="itemType" name="itemType" value="${item.itemType}" readonly>
                                                <input type="hidden" name="sptsId" value="${sptsId}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="subType">Sub Type</label>
                                                <input type="text" class="form-control" id="subType" name="subType" placeholder="No Sub Type" value="${item.subType}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="itemId">Item ID </label>
                                                <input type="text" class="form-control" id="itemId" name="itemId" placeholder="Item ID" value="${item.itemId}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="itemName">Item Name </label>
                                                <input type="text" class="form-control" id="itemName" name="itemName" placeholder="Item Name" value="${item.itemName}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="bg-light rounded-3">&nbsp;</div>
                                <div class="p-3 mb-4 rounded-3">
                                    <div class="row gx-4">
                                        <c:if test="${hardwareconfig.sameItemId == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="sameItemId">Same Item ID<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="sameItemId" name="sameItemId" placeholder="Same Item ID" value="${hardwareconfig.sameItemId}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.supplier == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="supplier">Supplier<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="supplier" name="supplier" placeholder="Key in supplier" value="${hardwareconfig.supplier}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.assemblyNo == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="assemblyNo">Assembly Number<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="assemblyNo" name="assemblyNo" placeholder="Key in assembly number" value="${hardwareconfig.assemblyNo}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.revision == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="revision">Revision<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="revision" name="revision" placeholder="Key in revision" value="${hardwareconfig.revision}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.mfgDate == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="mfgDate">Mfg Date<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="mfgDate" name="mfgDate" placeholder="Key in mfg date format" value="${hardwareconfig.mfgDate}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.component == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="component">Component<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="component" name="component" placeholder="Key in component?" value="${hardwareconfig.component}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.event == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="event">Event<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="event" name="event" placeholder="Key in Event" value="${hardwareconfig.event}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.partNumber == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="partNo">Part Number<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="partNo" name="partNo" placeholder="Key in Part Number" value="${hardwareconfig.partNumber}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.alu == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="alu">ALU?<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="alu" name="alu" placeholder="Calculate ALU?" value="${hardwareconfig.alu}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${hardwareconfig.shelfTime == 'Yes'}">
                                            <div class="col-sm-4 col-12">
                                                <div class="mb-3">
                                                    <label class="form-label" for="shelfTime">Shelf Time?<span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="shelfTime" name="shelfTime" placeholder="Calculate Shelf Time?" value="${hardwareconfig.shelfTime}" required>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="row gx-4">
                                        <div class="col-sm-4 col-12">
                                            <div class="mb-3">
                                                <label class="form-label" for="runningNumber">Running Number<span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" id="runningNumber" name="runningNumber" placeholder="Key in running number">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="d-flex gap-2 justify-content-end">
                                    <button type="button" class="btn btn-light" onclick="window.location.href='${contextPath}/hw/${sptsId}'">Cancel</button>
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="card-body">
                            <div class="alert bg-danger alert-dismissible d-flex fade show text-white" role="alert">
                                <i class="bi bi-x-circle-fill fs-3 me-3 lh-1"></i>
                                <div class="d-flex flex-column">
                                    <h6>Missing Hardware ID Configuration.</h6>
                                    <p>We couldn't find the necessary Hardware ID configuration to complete this action. Please contact your System Administrator to set up the Hardware ID Configuration.</p>
                                    <p>ITEM TYPE : ${item.itemType}<c:if test="${not empty item.subType}"> - ${subType}</c:if>.</p>
                                    <p>SUB TYPE : ${item.subType}</p>
                                    <div class="d-flex gap-4">
                                        <!--<a class="text-decoration-underline" href="#"> Link to something</a>-->
                                        <button class="btn btn-info" onclick="javascript:window.location.href='${contextPath}/hw/${sptsId}'">Go Back</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <!--<script src="${contextPath}/resources/"></script>-->
        <!--<script src="${contextPath}/resources/"></script>-->
        <!--<script src="${contextPath}/resources/"></script>-->
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            
        </script>
    </s:layout-component>
</s:layout-render>