package com.onsemi.mib.config;

import com.onsemi.mib.dao.DOListDAO;
import com.onsemi.mib.dao.EmailDAO;
import com.onsemi.mib.dao.LogModuleDAO;
import com.onsemi.mib.dao.SRShippingDao;
import com.onsemi.mib.dao.SampleRequestDAO;
import com.onsemi.mib.model.LogOuterBox;
import com.onsemi.mib.model.SampleRequest;
import com.onsemi.mib.model.SRShipping;
import com.onsemi.mib.model.UserEmail;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ConfigAutoRemoval {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigAutoRemoval.class);
    String[] args = {};

    @Autowired
    ServletContext servletContext;

//    @Scheduled(cron = "0 5 10 1 * ?") //every 8 AM everyday - cron (sec min hr daysOfMth month daysOfWeek year(optional)) new and active cron
    public void cronRun() {
        SampleRequestDAO srDao = new SampleRequestDAO();
        List<SampleRequest> expReqList = srDao.getAllExpReqMergeInner();
        
        boolean flag = false;
        
        if(expReqList != null) {
            for(int x=0; x<expReqList.size(); x++) {
                String reqId = expReqList.get(x).getId();
                String boxId = expReqList.get(x).getReqBoxId();
                
                DateFormat df = new SimpleDateFormat("MMM-yy");
                Date date = new Date();
                String nowMonthYear = df.format(date);
                
                if(x == expReqList.size()-1) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
            
            if(flag == true) {
                EmailDAO emailDao = new EmailDAO();
                List<UserEmail> emailList = emailDao.getEmailRL();
                String[] to = new String[emailList.size()];
                for(int i=0; i<emailList.size(); i++) {
                    to[i] = emailList.get(i).getEmail();
                }

                //sent email to respective person
                LOGGER.info("send email to person in charge");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    to, //to
                    "STORMS Request List (Due Scrap)", //subject
                    "<br />Please be informed that below request has been removed automatically by system. Please scrap the lot as it has been due for scrap. "
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse; font-family: Arial; font-size: 10pt;} </style>"
                    + "<table style=\"width:100%\" >" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>Request Box ID</th> "
                    + "<th>Event</th>"
                    + "<th>Pkg Family</th>"
                    + "<th>Created By</th>"
                    + "<th>Current Status</th>"
                    + "<th>Mth to Scrap</th>"
                    + "<th>Days Overdue</th>"
                    + "<th>Total of Lot</th>"
                    + "</tr>"
                    + table()
                    + "</table>"
                    + "<br />Thank you." //msg
                );
            }
        }
    }
    
    private String table() {
        SampleRequestDAO srDao = new SampleRequestDAO();
        List<SampleRequest> expReqList = srDao.getAllExpReqMergeInner();

        String text = "";
        for (int i = 0; i < expReqList.size(); i++) {
            String reqId = expReqList.get(i).getId();
            String boxId = expReqList.get(i).getReqBoxId();
            
            int index = i + 1;
            text = text + "<tr align = \"center\" >";
            text = text + "<td rowspan= \"2\">" + index + "</td>";
            text = text + "<td>" + boxId + "</td>";
            text = text + "<td>" + expReqList.get(i).getEvent() + "</td>";
            text = text + "<td>" + expReqList.get(i).getPkgFamily() + "</td>";
            text = text + "<td>" + expReqList.get(i).getCreatedBy() + "</td>";
            text = text + "<td>" + expReqList.get(i).getStatus()+ "</td>";
            text = text + "<td>" + expReqList.get(i).getMthToScrap()+ "</td>";
            text = text + "<td>" + expReqList.get(i).getAging()+ "</td>";
            text = text + "<td>" + expReqList.get(i).getLotQty()+ "</td>";
            text = text + "</tr>";
            text = text + "<tr align = \"left\">";
            text = text + "<td colspan= \"8\"><font size=\"2\" face=\"Courier New\"><b>List of RMSLot_Event: </b>" + expReqList.get(i).getRmsLotEventConcat() + "</font></td>";
            text = text + "</tr>";
            
            SampleRequest sampReq = new SampleRequest();
            sampReq.setStatus("Removed from Req List (Auto Scrap)");
            sampReq.setFlag("99");
            sampReq.setModifiedBy("Config Auto Removal");
            sampReq.setId(reqId);
            srDao = new SampleRequestDAO();
            QueryResult qr = srDao.updateRequestStatus(sampReq);
            
            if(qr.getResult() == 1) {
                LogModuleDAO logDao = new LogModuleDAO();
                LogOuterBox logOuter = new LogOuterBox();
                logOuter.setOuterId(reqId);
                logOuter.setModuleId(reqId);
                logOuter.setModuleName("sr_request");
                logOuter.setStatus("Removed from Req List (Auto Scrap)");
                logOuter.setCreatedBy("Config Auto Removal");
                QueryResult queryResultLog = logDao.insertOuterLog(logOuter);
                
            
                SRShippingDao shipDao = new SRShippingDao();
                int count = shipDao.getCountExistingDataPerReqId(reqId);
                if(count == 1) {
                    shipDao = new SRShippingDao();
                    SRShipping shipping = shipDao.getShipping(reqId);
                    
                    if(shipping.getStatus().equals("Pending Shipment")) {
                        DOListDAO dolistdao = new DOListDAO();
                        int kira = dolistdao.getCountExistingRequest(reqId);
                        
                        if(kira != 0) {
                            dolistdao = new DOListDAO();
                            QueryResult qrDoList = dolistdao.deleteReq(reqId);           
                        }
                    }
                    
                    SRShipping ship = new SRShipping();
                    ship.setOuterPkgNo(reqId);
                    ship.setStatus("Removed from Req List (Auto Scrap)");
                    ship.setFlag("99");
                    ship.setModifiedBy("Config Auto Removal");
                    shipDao = new SRShippingDao();
                    QueryResult qrShip = shipDao.updateShipStatus(ship);
                    
                    if(qrShip.getResult() == 1) {
                        logDao = new LogModuleDAO();
                        logOuter = new LogOuterBox();
                        logOuter.setOuterId(reqId);
                        logOuter.setModuleId(shipping.getId());
                        logOuter.setModuleName("sr_shipping_list");
                        logOuter.setStatus("Removed from Shipping List (Auto Scrap)");
                        logOuter.setCreatedBy("Config Auto Removal");
                        QueryResult qrLog = logDao.insertOuterLog(logOuter);
                    }
                }
            }
        }
        return text;
    }
}