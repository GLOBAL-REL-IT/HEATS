<%-- 
    Document   : repair
    Created on : Sep 23, 2026, 3:39:35 PM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/datatables/buttons/dataTables.bs5-custom.css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .content-wrapper {
                padding: 0 20px;
                height: calc(100vh - 10px);
                overflow: auto;
            }
            .section-header {
                background-color: #eaedf8;
                min-height: 40px;
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                position: relative;
                padding: 8px 45px;
                margin-bottom: 15px;
                font-weight: 600;
                text-align: center;
                border-radius: 3px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }
            .section-header:hover {
                background-color: #dce2f3;
            }
            .section-header .section-arrow {
                position: absolute;
                right: 15px;
                font-size: 16px;
                transition: transform 0.25s ease;
            }
            .section-header[aria-expanded="true"] .section-arrow {
                transform: rotate(180deg);
            }
            .section-content {
                padding-bottom: 5px;
            }
            @media (min-width: 768px) {
                .disposition-left {
                    padding-right: 20px;
                }
                .disposition-right {
                    padding-left: 20px;
                }
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="content-wrapper">
            <div class="row">
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid justify-content-start">
                        <a href="${contextPath}/maverick/list" class="btn btn-outline-warning me-2" role="button"><i class='bi bi-arrow-bar-left'></i>&nbsp;&nbsp;Back</a>
                    </div>
                </nav>
                <div class="col-sm-8 col-12">
                    <div class="card mb-4">
                        <div class="card-header"><h5 class="card-title">Maverick Details - <span style="color:#D97D55">${data.module} [FT]</span></h5></div>
                        <div class="card-body">
                            <div class="row gx-3">
                                <%@ include file="dispo_info.jsp" %>
                                <%@ include file="ft.jsp" %>
                                <%@ include file="log.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Disposition Action</h5>
                        </div>
                        <div class="card-body">
                            <form class="row g-3 align-items-center" role="form" action="${contextPath}/maverick/updateMaverick" method="post">
                            <div class="row gx-3">
                                <input type="hidden" name="mibItemId" id="mibItemId" value="${mibItemId}">
                                <input type="hidden" name="mavId" id="mavId" value="${id}">
                                <div class="col-12">
                                    <div class="mb-3">
                                        <label for="newDisposition" class="form-label">Disposition</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-gear"></i></span>
                                            <select class="form-select" id="newDisposition" name="newDisposition" required>
                                                <option value="">Select Disposition</option>
                                                <option value="Repair" ${empty data.disposition1Date or empty data.disposition2Date ? '' : 'disabled title="Done Disposition"'}>Repair</option>
                                                <option value="Scrap">Scrap</option>
                                                <option value="Bypass">Bypass</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 d-none">
                                    <div class="mb-3">
                                        <label for="newDispositionBy" class="form-label">Disposition By</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                            <input type="text" class="form-control" id="newDispositionBy" name="newDispositionBy" placeholder="Enter Full Name">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 d-none">
                                    <div class="mb-3">
                                        <label for="newDispositionDate" class="form-label">Disposition Date</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                                            <input type="date" class="form-control" id="newDispositionDate" name="newDispositionDate">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="mb-3">
                                        <label for="newDispositionRemark" class="form-label">Disposition Remark</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                            <textarea class="form-control" id="newDispositionRemark" name="newDispositionRemark" placeholder="Enter Remark" rows="3"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end gap-2">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="app-footer">
                <span>© Bootstrap Gallery 2025</span>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
        </script>
    </s:layout-component>
</s:layout-render>