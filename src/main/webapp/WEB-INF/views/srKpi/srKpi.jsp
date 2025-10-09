<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/jquery.datetimepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
                table thead {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
                table tbody {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
            }
            .dataTables_wrapper .dt-buttons {
                float:none;  
                text-align:right;
            }
            
            .fa-stack2 {
                color: green;
            }
            .table-link2 {
                color: green;
            }
            .fa-stack3 {
                color: red;
            }
            .table-link3 {
                color: red;
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
    <div class="col-lg-12">
        <div class="clearfix"></div>
        <!--New Tab Menu-->
        <div class="col-lg-12">
            <div class="row">
                <ul class="nav nav-tabs">
                    <li class="${scrap}"><a data-toggle="tab" href="#scrap">Returning for Scrap</a></li>
                    <li class="${retrieve}"><a data-toggle="tab" href="#retrieve">Retrieval Time</a></li>
                    <!--<li class="${inventoryAudit}"><a data-toggle="tab" href="#inventoryAudit">Inventory Audit</a></li>-->
                </ul>
                
                <div class="tab-content">
                    <!--Tab for scrap-->
                    <div id="scrap" class="tab-pane fade ${scrapTab}">
                        <h6></h6>
                        <div class="col-lg-12">
                            <div class="main-box">
                                <div class="clearfix">
                                    <h2 class="pull-left">Return for Scrap Chart</h2>
                                </div>
                                <div>
                                    <canvas id="scrapChart" width="88%" height="30%"></canvas>
                                    <c:forEach items="${scrapChartList}" var="scrapChart" varStatus="scrapChartListLoop">
                                        <input id="scrap_${scrapChartListLoop.index+1}" type="text" value="${scrapChart.scrapCount}" placeholder="${scrapChart.scrapMthYrReq}" hidden="">
                                    </c:forEach>
                                </div>
                                <hr/>
                            </div>
                            <!--Return for Scrap Record Table-->
                            <div class="main-box clearfix">
                                <div class="clearfix">
                                    <h2 class="pull-left">Return for Scrap Record</h2>
                                    <div class="filter-block pull-right">
                                    </div>
                                </div>
                                <hr/>
                                <div class="clearfix">
                                    <div class="form-group pull-left">
                                        <select id="dt_scrap_rc_rows" class="form-control">
                                            <option value="10">10</option>
                                            <option value="25">25</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            <option value="-1">All</option>
                                        </select>
                                    </div>
                                    <div class="filter-block pull-right">
                                        <div id="dt_scrap_rc_sc" class="form-group pull-left" style="margin-right: 5px;">
                                        </div>
                                        <div class="form-group pull-left" style="margin-right: 0px;">
                                            <input id="dt_scrap_rc_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>" autofocus="autofocus" >
                                            <i class="fa fa-search search-icon"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="dt_scrap_rc" class="display" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Month Request</th>
                                                <c:forEach items="${mthToScrapVsReqDateList}" var="scrapMth" varStatus="scrapMthLoop">
                                                    <th align = "center">${scrapMth.scrapMthYrReq}</th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th>No. of Request</th>
                                                <c:forEach items="${mthToScrapVsReqDateList}" var="noOfReq" varStatus="noOfReqLoop">
                                                     <td align = "center">${noOfReq.scrapCount}</td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td><b>Cycle Time #1</b><br>Mth to Scrap VS Req. Date <= ${mthToScrapVsReqDateGoal} day(s)</td>
                                                <c:forEach items="${mthToScrapVsReqDateList}" var="goalMeet" varStatus="goalMeetLoop">
                                                     <td align = "center">${goalMeet.mthToScrapVsReqPass}</td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td><b>Percentage Time #1</b><br>Mth to Scrap VS Req. Date <= ${mthToScrapVsReqDateGoal} day(s)</td>
                                                <c:forEach items="${mthToScrapVsReqDateList}" var="percent" varStatus="percentLoop">
                                                    <td align = "center">${percent.mthToScrapVsReqPercent} %</td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td><b>Cycle Time #2</b><br>Req. Date VS Shipping Date <= ${reqDateVsShipDateGoal} day(s)</td>
                                                <c:forEach items="${mthToScrapVsReqDateList}" var="goalMeet" varStatus="goalMeetLoop">
                                                     <td align = "center">${goalMeet.reqVsShipDatePass}</td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td><b>Percentage Time #2</b><br>Req. Date VS Shipping Date <= ${reqDateVsShipDateGoal} day(s)</td>
                                                <c:forEach items="${mthToScrapVsReqDateList}" var="percent" varStatus="percentLoop">
                                                    <td align = "center">${percent.reqVsShipDatePercent} %</td>
                                                </c:forEach>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!--Return for Return Scrap Table-->                  
                            <div class="main-box clearfix">
                                <div class="clearfix">
                                    <h2 class="pull-left">Return for Scrap Data List</h2>
                                    <div class="filter-block pull-right">
                                    </div>
                                </div>
                                <hr/>
                                <div class="clearfix">
                                    <div class="form-group pull-left">
                                        <select id="dt_spml1_rows" class="form-control">
                                            <option value="10">10</option>
                                            <option value="25">25</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            <option value="-1">All</option>
                                        </select>
                                    </div>
                                    <div class="filter-block pull-right">
                                        <div id="dt_spml1_sc" class="form-group pull-left" style="margin-right: 5px;">
                                        </div>
                                        <div class="form-group pull-left" style="margin-right: 0px;">
                                            <input id="dt_spml1_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>" autofocus="autofocus" >
                                            <i class="fa fa-search search-icon"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="dt_spml1" class="display" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th align = "center">No.</th>
                                                <th align = "center">Mth to Scrap</th> 
                                                <th align = "center">RMS Event (Lot)</th>
                                                <th align = "center">Pkg Family</th>
                                                <th align = "center">Request Date</th>
                                                <th align = "center">Cycle Time 1 (day)</th>
                                                <th align = "center">Ship. Date</th>
                                                <th align = "center">Cycle Time 2 (day)</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${scrapDataList}" var="scrapData" varStatus="scrapDataLoop">
                                                <tr>                                            
                                                    <td><c:out value="${scrapDataLoop.index+1}"/></td>
                                                    <td><c:out value="${scrapData.mthToScrap}"/></td>
                                                    <td><c:out value="${scrapData.rmsNo}"/> <c:out value="${scrapData.event}"/> (<c:out value="${scrapData.lotConcat}"/>)</td>
                                                    <td><c:out value="${scrapData.pkgFamily}"/></td>
                                                    <td><c:out value="${scrapData.createdDate}"/></td>
                                                    <td>
                                                        <c:if test="${scrapData.cycleTime1 <= mthToScrapVsReqDateGoal}">
                                                            <c:out value="${scrapData.cycleTime1}"/>
                                                        </c:if>
                                                        <c:if test="${scrapData.cycleTime1 > mthToScrapVsReqDateGoal}">
                                                            <font color="#FF0000"><c:out value="${scrapData.cycleTime1}"/></font>
                                                        </c:if>
                                                    </td>
                                                    <td><c:out value="${scrapData.shipDate}"/></td>
                                                    <td>
                                                        <c:if test="${scrapData.cycleTime2 != 'N/A' && scrapData.cycleTime2 <= reqDateVsShipDateGoal}">
                                                            <c:out value="${scrapData.cycleTime2}"/>
                                                        </c:if>
                                                        <c:if test="${scrapData.cycleTime2 != 'N/A' && scrapData.cycleTime2 > reqDateVsShipDateGoal}">
                                                            <font color="#FF0000"><c:out value="${scrapData.cycleTime2}"/></font>
                                                        </c:if>
                                                        <c:if test="${scrapData.cycleTime2 == 'N/A'}">
                                                            <font color="#CACACA"><c:out value="${scrapData.cycleTime2}"/></font>
                                                        </c:if>    
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>	
                        </div>
                    </div>
                    <!--Tab for retrieval-->
                    <div id="retrieve" class="tab-pane fade ${retrieveTab}">
                        <h6></h6>
                        <div class="col-lg-12">
                            <div class="main-box">
                                <div class="clearfix">
                                    <h2 class="pull-left">Retrieval Request Chart</h2>
                                </div>
                                <div>
                                    <canvas id="retrievalChart" width="88%" height="30%"></canvas>
                                    <c:forEach items="${retrievalChartList}" var="retrieval" varStatus="retrievalLoop">
                                        <input id="ret_${retrievalLoop.index+1}" type="text" value="${retrieval.scrapCount}" placeholder="${retrieval.scrapMthYrReq}" hidden="">
                                    </c:forEach>
                                </div>
                                <hr/>
                            </div>
                            <!--Retrieval Request Record Table-->
                            <div class="main-box clearfix">
                                <div class="clearfix">
                                    <h2 class="pull-left">Retrieval Request Record</h2>
                                    <div class="filter-block pull-right">
                                    </div>
                                </div>
                                <hr/>
                                <div class="clearfix">
                                    <div class="form-group pull-left">
                                        <select id="dt_activities_rc_rows" class="form-control">
                                            <option value="10">10</option>
                                            <option value="25">25</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            <option value="-1">All</option>
                                        </select>
                                    </div>
                                    <div class="filter-block pull-right">
                                        <div id="dt_activities_rc_sc" class="form-group pull-left" style="margin-right: 5px;">
                                        </div>
                                        <div class="form-group pull-left" style="margin-right: 0px;">
                                            <input id="dt_activities_rc_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>" autofocus="autofocus" >
                                            <i class="fa fa-search search-icon"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="dt_activities_rc" class="display" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Month Request</th>
                                                <c:forEach items="${actReqDateVSshipDateList}" var="scrapMth" varStatus="scrapMthLoop">
                                                    <th align = "center">${scrapMth.activityMthYrReq}</th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th>No. of Request</th>
                                                <c:forEach items="${actReqDateVSshipDateList}" var="noOfReq" varStatus="noOfReqLoop">
                                                     <td align = "center">${noOfReq.activityCount}</td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td><b>Cycle Time #1</b><br>Req. Date VS Shipping Date <= ${actReqDateVsShipDateGoal} day(s)</td>
                                                <c:forEach items="${actReqDateVSshipDateList}" var="goalMeet" varStatus="goalMeetLoop">
                                                     <td align = "center">${goalMeet.activityReqVsShipDatePass}</td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td><b>Percentage Time #1</b><br>Req. Date VS Shipping Date <= ${actReqDateVsShipDateGoal} day(s)</td>
                                                <c:forEach items="${actReqDateVSshipDateList}" var="percent" varStatus="percentLoop">
                                                    <td align = "center">${percent.activityReqVsShipDatePercent} %</td>
                                                </c:forEach>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!--Retrieval Request Data Table-->
                            <div class="main-box">
                                <div class="clearfix">
                                    <h2 class="pull-left">Retrieval Request Data</h2>
                                    <div class="filter-block pull-right">
                                    </div>
                                </div>
                                <hr/>
                                <div class="clearfix">
                                    <div class="form-group pull-left">
                                        <select id="dt_spml2_rows" class="form-control">
                                            <option value="10">10</option>
                                            <option value="25">25</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            <option value="-1">All</option>
                                        </select>
                                    </div>
                                    <div class="filter-block pull-right">
                                        <div id="dt_spml2_rt" class="form-group pull-left" style="margin-right: 5px;">
                                        </div>
                                        <div class="form-group pull-left" style="margin-right: 0px;">
                                            <input id="dt_spml2_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>" autofocus="autofocus" >
                                            <i class="fa fa-search search-icon"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="dt_spml2" class="display" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th align = "center">No.</th>
                                                <th align = "center">Mth to Scrap</th> 
                                                <th align = "center">RMS Event (Lot)</th>
                                                <th align = "center">Pkg Family</th>
                                                <th align = "center">Request Details</th>
                                                <th align = "center">Est. Shipped Date</th>
                                                <th align = "center">Cycle Time 1 (day)</th>
                                                <th align = "center">Received Details</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${retrievalDataList}" var="retrievalData" varStatus="retrievalDataLoop">
                                                <tr>                                            
                                                    <td><c:out value="${retrievalDataLoop.index+1}"/></td>
                                                    <td><c:out value="${retrievalData.mthToScrap}"/></td>
                                                    <td><c:out value="${retrievalData.rmsNo}"/> <c:out value="${retrievalData.event}"/> (<c:out value="${retrievalData.lotConcat}"/>)</td>
                                                    <td><c:out value="${retrievalData.pkgFamily}"/></td>
                                                    <td><c:out value="${retrievalData.requestDate}"/> by <c:out value="${retrievalData.requestBy}"/> : <c:out value="${retrievalData.reasonRecall}"/></td>
                                                    <td><c:out value="${retrievalData.shipDate}"/></td>
                                                    <td>
                                                        <c:if test="${scrapData.cycleTime1 != 'N/A' && scrapData.cycleTime1 <= reqDateVsShipDateGoal}">
                                                            <c:out value="${scrapData.cycleTime1}"/>
                                                        </c:if>
                                                        <c:if test="${scrapData.cycleTime1 != 'N/A' && scrapData.cycleTime1 > reqDateVsShipDateGoal}">
                                                            <font color="#FF0000"><c:out value="${scrapData.cycleTime2}"/></font>
                                                        </c:if>
                                                        <c:if test="${scrapData.cycleTime1 == 'N/A'}">
                                                            <font color="#CACACA"><c:out value="${scrapData.cycleTime1}"/></font>
                                                        </c:if>    
                                                    </td>
                                                    <td><c:out value="${retrievalData.relReceivedDate} by ${retrievalData.relReceivedBy} "/></td>
                                                    
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>	
                        </div>
                    </div>  
                </div>
            </div>
        </div>
        
    </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/private/js/Chart.min.js"></script>
        <script src="${contextPath}/resources/private/js/utils.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                <%--Return for Scrap Record Table--%>
                var oTable2 = $('#dt_scrap_rc').DataTable({
                    dom: 'Brtip',
                    aaSorting: [],
                    bSort: false,
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                    .css('font-size', '10pt')
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }
                        }
                    ]
                });
                
                $('#dt_scrap_rc_search').keyup(function () {
                    oTable2.search($(this).val()).draw();
                });
                
                $("#dt_scrap_rc_rows").change(function () {
                    oTable2.page.len($(this).val()).draw();
                });
                
                <%--Return for Scrap Record Table--%>
                var oTable = $('#dt_spml1').DataTable({
                    dom: 'Brtip',
                    aaSorting: [],
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                    .css('font-size', '10pt')
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }
                        }
                    ]
                });
                
                $('#dt_spml1_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });
                
                $("#dt_spml1_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
                });
                
                <%--Retrieval Request Record Table--%>
                var table2 = $('#dt_activities_rc').DataTable({
                    dom: 'Brtip',
                    bSort: false,
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                    .css('font-size', '10pt')
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }
                        }
                    ]
                });
                
                $('#dt_activities_rc_search').keyup(function () {
                    table2.search($(this).val()).draw();
                });
                
                $("#dt_activities_rc_rows").change(function () {
                    table2.page.len($(this).val()).draw();
                });
                
                <%--Retrieval Request Data Table--%>
                var table1 = $('#dt_spml2').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                    .css('font-size', '10pt')
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }
                        }
                    ]
                });
                
                $('#dt_spml2_search').keyup(function () {
                    table1.search($(this).val()).draw();
                });
                
                $("#dt_spml2_rows").change(function () {
                    table1.page.len($(this).val()).draw();
                });
                
            });
            
            
            var one = parseInt($('#scrap_1').val());
            var two = parseInt($('#scrap_2').val());
            var three = parseInt($('#scrap_3').val());
            var four = parseInt($('#scrap_4').val());
            var five = parseInt($('#scrap_5').val());
            var six = parseInt($('#scrap_6').val());
            var seven = parseInt($('#scrap_7').val());
            var eight = parseInt($('#scrap_8').val());
            var nine = parseInt($('#scrap_9').val());
            var ten = parseInt($('#scrap_10').val());
            var eleven = parseInt($('#scrap_11').val());
            var twelve = parseInt($('#scrap_12').val());

            var mthOne = $('#scrap_1').attr('placeholder');
            var mthTwo = $('#scrap_2').attr('placeholder');
            var mthThree = $('#scrap_3').attr('placeholder');
            var mthFour = $('#scrap_4').attr('placeholder');
            var mthFive = $('#scrap_5').attr('placeholder');
            var mthSix = $('#scrap_6').attr('placeholder');
            var mthSeven = $('#scrap_7').attr('placeholder');
            var mthEight = $('#scrap_8').attr('placeholder');
            var mthNine = $('#scrap_9').attr('placeholder');
            var mthTen = $('#scrap_10').attr('placeholder');
            var mthEleven = $('#scrap_11').attr('placeholder');
            var mthTwelve = $('#scrap_12').attr('placeholder');
            
            var ctx = document.getElementById('scrapChart').getContext('2d');
            var scrapChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [mthOne,mthTwo,mthThree,mthFour,mthFive,mthSix,mthSeven,mthEight,mthNine,mthTen,mthEleven,mthTwelve],
                    datasets: [{
                        data: [one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve],
                        backgroundColor: [
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)',
                            'rgba(0,128,128, 0.2)'
                        ],
                        borderColor: [
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)',
                            'rgba(0,128,128,1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
            
            var retY1 = parseInt($('#ret_1').val());
            var retY2 = parseInt($('#ret_2').val());
            var retY3 = parseInt($('#ret_3').val());
            var retY4 = parseInt($('#ret_4').val());
            var retY5 = parseInt($('#ret_5').val());
            var retY6 = parseInt($('#ret_6').val());
            var retY7 = parseInt($('#ret_7').val());
            var retY8 = parseInt($('#ret_8').val());
            var retY9 = parseInt($('#ret_9').val());
            var retY10 = parseInt($('#ret_10').val());
            var retY11 = parseInt($('#ret_11').val());
            var retY12 = parseInt($('#ret_12').val());
            
            var retX1 = $('#ret_1').attr('placeholder');
            var retX2 = $('#ret_2').attr('placeholder');
            var retX3 = $('#ret_3').attr('placeholder');
            var retX4 = $('#ret_4').attr('placeholder');
            var retX5 = $('#ret_5').attr('placeholder');
            var retX6 = $('#ret_6').attr('placeholder');
            var retX7 = $('#ret_7').attr('placeholder');
            var retX8 = $('#ret_8').attr('placeholder');
            var retX9 = $('#ret_9').attr('placeholder');
            var retX10 = $('#ret_10').attr('placeholder');
            var retX11 = $('#ret_11').attr('placeholder');
            var retX12 = $('#ret_12').attr('placeholder');

            var ctx = document.getElementById('retrievalChart').getContext('2d');
            var retrievalChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [retX1,retX2,retX3,retX4,retX5,retX6,retX7,retX8,retX9,retX10,retX11,retX12],
                    datasets: [{
                        data: [retY1,retY2,retY3,retY4,retY5,retY6,retY7,retY8,retY9,retY10,retY11,retY12],
                        backgroundColor: [
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(153, 102, 255, 0.2)'
                        ],
                        borderColor: [
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(153, 102, 255, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
        </script>
    </s:layout-component>
</s:layout-render>