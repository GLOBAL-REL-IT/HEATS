<%-- 
    Document   : dispo_info
    Created on : Oct 1, 2026, 11:38:34 AM
    Author     : zbqb9x
--%>
<div class="col-xl-4 col-sm-12 col-12">
    <div class="mb-3">
        <label for="hardwareType" class="form-label">Hardware Type</label>
        <input type="text" class="form-control" id="hardwareType" name="hardwareType" value="${data.itemType}">
    </div>
</div>
<div class="col-xl-8 col-sm-12 col-12">
    <div class="mb-3">
        <label for="hardwareId" class="form-label">Hardware ID</label>
        <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${data.itemId}">
    </div>
</div>
<div class="col-xl-4 col-sm-12 col-12">
    <div class="mb-3">
        <label for="module" class="form-label">Module</label>
        <input type="text" class="form-control" id="module" name="module" value="${data.module}">
    </div>
</div>
<div class="col-xl-8 col-sm-12 col-12">
    <div class="mb-3">
        <label for="subModule" class="form-label">Sub Module</label>
        <input type="text" class="form-control" id="subModule" name="subModule" value="${data.submodule}">
    </div>
</div>
<div class="col-xl-4 col-sm-12 col-12">
    <div class="mb-3">
        <label for="hardwareDate" class="form-label">Date</label>
        <input type="text" class="form-control" id="hardwareDate" name="hardwareDate" value="${data.createdDate}">
    </div>
</div>
<div class="col-xl-8 col-sm-12 col-12 mb-3">
    <div class="mb-3">
        <label for="hardwareStatus" class="form-label">Status</label>
        <input type="text" class="form-control" id="hardwareStatus" name="hardwareStatus" value="${data.status}">
    </div>
</div>
<div class="col-12">
    <div class="section-header" data-bs-toggle="collapse" data-bs-target="#dispositionInformation" aria-expanded="false" aria-controls="dispositionInformation">
        <span>DISPOSITION INFORMATION</span>
        <i class="bi bi-chevron-down section-arrow"></i>
    </div>
</div>
<div class="collapse col-12" id="dispositionInformation">
    <div class="section-content">
        <div class="row gx-3">
            <div class="col-md-6 col-12 disposition-left">
                <div class="mb-3">
                    <label for="disposition1" class="form-label">Disposition 1</label>
                    <input type="text" class="form-control" id="disposition1" name="disposition1" value="${data.disposition1}" readonly>
                </div>
                <div class="mb-3">
                    <label for="dispositionBy1" class="form-label">Disposition By 1</label>
                    <input type="text" class="form-control" id="dispositionBy1" name="dispositionBy1" value="${data.disposition1By}" readonly>
                </div>
                <div class="mb-3">
                    <label for="dispositionDate1" class="form-label">Disposition Date 1</label>
                    <input type="text" class="form-control" id="dispositionDate1" name="dispositionDate1" value="${data.disposition1Date}" readonly>
                </div>
                <div class="mb-3">
                    <label for="remarks1" class="form-label">Remarks 1</label>
                    <textarea class="form-control" id="remarks1" name="remarks1" rows="5" readonly>${data.dispositionRemarks1}</textarea>
                </div>
            </div>
            <div class="col-md-6 col-12 disposition-right">
                <div class="mb-3">
                    <label for="disposition2" class="form-label">Disposition 2</label>
                    <input type="text" class="form-control" id="disposition2" name="disposition2" value="${data.disposition2}" readonly>
                </div>
                <div class="mb-3">
                    <label for="dispositionBy2" class="form-label">Disposition By 2</label>
                    <input type="text" class="form-control" id="dispositionBy2" name="dispositionBy2" value="${data.disposition2By}" readonly>
                </div>
                <div class="mb-3">
                    <label for="dispositionDate2" class="form-label">Disposition Date 2</label>
                    <input type="text" class="form-control" id="dispositionDate2" name="dispositionDate2" value="${data.disposition2Date}" readonly>
                </div>
                <div class="mb-3">
                    <label for="remarks2" class="form-label">Remarks 2</label>
                    <textarea class="form-control" id="remarks2" name="remarks2" rows="5" readonly>${data.disposition2Remarks}</textarea>
                </div>
            </div>
        </div>
    </div>
</div>