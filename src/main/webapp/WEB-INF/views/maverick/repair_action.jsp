<%-- 
    Document   : repair_action
    Created on : Oct 7, 2026, 11:28:25 AM
    Author     : zbqb9x
--%>

<div class="col-sm-4 col-12">
    <div class="card mb-4">
        <div class="card-header">
            <h5 class="card-title">Repair Action</h5>
        </div>
        <div class="card-body">
            <form class="row g-3 align-items-center" role="form" action="${contextPath}/maverick/updateRepairStatus" method="post">
                <div class="row gx-3">
                    <input type="hidden" name="mibItemId" id="mibItemId" value="${mibItemId}">
                    <input type="type" name="mavId" id="mavId" value="${id}">
                    <div class="col-12">
                        <div class="mb-3">
                            <label for="repairRemark" class="form-label">Repair Remark</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-pencil"></i></span>
                                <textarea class="form-control" id="repairRemark" name="repairRemark" placeholder="Enter Remark" rows="3"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-between gap-2">
                    <button type="submit" class="btn btn-danger" name="status" value="failed">Repair Failed</button>
                    <button type="submit" class="btn btn-success" name="status" value="success">Item Repaired</button>
                </div>
            </form>
        </div>
    </div>
</div>