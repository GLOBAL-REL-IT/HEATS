<%-- 
    Document   : log
    Created on : Oct 1, 2026, 11:42:46 AM
    Author     : zbqb9x
--%>

<div class="col-12">
    <div class="section-header" data-bs-toggle="collapse" data-bs-target="#logDetails" aria-expanded="false" aria-controls="logDetails">
        <span>LOG DETAILS</span>
        <i class="bi bi-chevron-down section-arrow"></i>
    </div>
</div>
<div class="collapse col-12" id="logDetails">
    <div class="section-content">
        <div class="row gx-3">
            <div class="table-responsive">
                <table id="customButtons1" class="table custom-table pending">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Detail</th>
                            <th>Action By</th>
                            <th>Action Date</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${itemlog}" var="parameterMaster" varStatus="parameterMasterLoop">
                        <tr>
                            <td><c:out value="${parameterMasterLoop.index+1}"/></td>
                        <td><c:out value="${parameterMaster.detail}"/></td>
                        <td><c:out value="${parameterMaster.createdBy}"/></td>
                        <td style="font-size: 1.2em;"><span class="badge bg-primary"><c:out value="${parameterMaster.createdDate}"/></span></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>