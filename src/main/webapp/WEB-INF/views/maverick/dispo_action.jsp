<%-- 
    Document   : dispo_action
    Created on : Oct 7, 2026, 11:24:21 AM
    Author     : zbqb9x
--%>
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