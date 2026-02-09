<%-- 
    Document   : hw_id_add
    Created on : Feb 5, 2026, 10:37:56 AM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/statflow/vendor/bs-select/bs-select.css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">\

        <div class="content-wrapper">
            <div class="row gx-4">
                <div class="col-sm-12 col-12">
                    <!-- Card start -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title">Configuration - <span style="color:#D97D55">Add Hardware ID</span></h5>
                        </div>

                        <div class="card-body">
                            <form class="row g-3 align-items-center" role="form" id="hw-id-add" action="${contextPath}/admin/hw/save" method="post">
                                <div class="row mb-2">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="itemType">Item Type</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <select class="js-example-basic-single" id="itemType" name="itemType" style="width: 100%">
                                                    <option value="" selected="">Select Item Type</option>
                                                    <c:forEach items="${itemTypeList}" var="group">
                                                        <option value="${group.name}" ${group.selected}>${group.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="subType">Sub Type</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <select class="js-example-basic-single" id="subType" name="subType" style="width: 100%">
                                                    <option value="" selected="">Select...</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="itemId">Same Item ID</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="itemId" name="itemId">
                                                    <!--<label class="form-check-label" for="itemId">Default switchS</label>-->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
<!--                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="supplier">Supplier</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="supplier" name="supplier">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="assemblyno">Assembly Number</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="assemblyno" name="assemblyno">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
<!--                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="revision">Revision</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="revision" name="revision">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="mfgdate">Manufacturing Date</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="mfgdate" name="mfgdate">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
<!--                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="component">Component</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="component" name="component">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="event">Event</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="event" name="event">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="partnumber">Part Number</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input toggle-input" type="checkbox" role="switch" id="partnumber" name="partnumber">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="alu">ALU?</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="alu" name="alu">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label class="col-sm-2 col-md-2 col-form-label fw-semibold" for="shelf">Shelf Time?</label>
                                    <div class="col-lg-5">
                                        <div class="row g-2">
                                            <div class="col-sm-6">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="shelf" name="shelf">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <button type="submit" id="submit" id="submit" class="btn btn-primary float-end">Save</button>
                                    <a href="${contextPath}/admin/hw" class="btn btn-dark float-start">Back</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/vendor/DataTables/customitem/jquery-3.7.1.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select.min.js"></script>
        <script src="${contextPath}/resources/statflow/vendor/bs-select/bs-select-custom.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                $('.js-example-basic-single').select2();
            });
            
            $(function () {
                const $same = $('#itemId');
                const $alu = $('#alu');
                const $shelf = $('#shelf');

                function $lockables() {
                    return $('#hw-id-add .toggle-input').not('#alu, #shelf');
                }

                function applyLock(locked) {
                    if (locked) {
                        $lockables().prop('checked', false).prop('disabled', true).attr('aria-disabled', 'true');
                        $alu.add($shelf).prop('disabled', false).removeAttr('aria-disabled');
                    } else {
                        $lockables().prop('disabled', false).removeAttr('aria-disabled');
                        $alu.add($shelf).prop('disabled', false).removeAttr('aria-disabled');
                    }
                }

                $same.on('change', function () {
                    applyLock(this.checked);
                });

                var ctx = '${contextPath}';
                var $itemType = $('#itemType');
                var $subType  = $('#subType');

                function resetSubType(disabled, message) {
                    $subType.prop('disabled', !!disabled).html('<option value="">Please select Item Type</option>');
                }
                
                function renderSubTypes(list) {
                    var $subType = $('#subType');
                    $subType.html('<option value="">Select Sub Type</option>');
                    if (!Array.isArray(list) || list.length === 0) {
                        $subType.prop('disabled', false);
                        return;
                    }

                    list.forEach(function (v) {
                        if (v && typeof v === 'object') {
                            var optValue = (v.text ?? v.value ?? '').toString().trim();
                            var optText  = (v.text ?? v.value ?? '').toString().trim();
                            if (optText.length > 0) {
                                $subType.append($('<option/>', { value: optValue, text: optText }));
                            }
                        }
                    });
                    
                    if ($subType.hasClass('select2-hidden-accessible')) {
                        $subType.trigger('change.select2');
                    }
                    $subType.prop('disabled', false);
                }

                function loadSubTypes(itemType) {
                    $subType.prop('disabled', true).html('<option value="">Loading...</option>');

                    $.ajax({
                        url: ctx + '/admin/hw/ajaxSample/'+encodeURIComponent(itemType),
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            renderSubTypes(data);
                        },
                        error: function () {
                            renderSubTypes([]);
                        }
                    })
                    .done(function (data) {
                        var list = Array.isArray(data) ? data : (data.subTypes || []);
                        renderSubTypes(list);
                    })
                    .fail(function (xhr) {
                        console.error('getSubType failed', xhr.status, xhr.responseText);
                        resetSubType(false, 'Failed to load sub types. You can proceed without a selection.');
                    });
                }

                $itemType.on('change', function () {
                    var v = $(this).val();
                    if (v) loadSubTypes(v);
                    else resetSubType(true, '');
                });

                resetSubType(true, '');
                if ($itemType.val()) loadSubTypes($itemType.val());
                
                $('#subType').prop('disabled', true);
            });
            
            $('#subType').trigger('change');
        </script>
    </s:layout-component>
</s:layout-render>