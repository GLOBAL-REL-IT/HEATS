package com.onsemi.mib.config;

<<<<<<< HEAD:src/main/java/com/onsemi/mib/config/ConfigAutoScrap.java
import com.onsemi.mib.dao.FTPDao;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.InventoryDAO;
import com.onsemi.mib.dao.LogDAO;
import com.onsemi.mib.dao.RequestDAO;
import com.onsemi.mib.dao.SRInventoryDAO;
import com.onsemi.mib.dao.ScrapDAO;
import com.onsemi.mib.dao.UserDAO;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.Inventory;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.model.Log;
import com.onsemi.mib.model.Request;
import com.onsemi.mib.model.SRInventory;
import com.onsemi.mib.model.Scrap;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
=======
import com.onsemi.ostorms.dao.FTPDao;
import com.onsemi.ostorms.dao.HostnameDAO;
import com.onsemi.ostorms.dao.InventoryDAO;
import com.onsemi.ostorms.dao.LogDAO;
import com.onsemi.ostorms.dao.RequestDAO;
import com.onsemi.ostorms.dao.SRInventoryDAO;
import com.onsemi.ostorms.dao.ScrapDAO;
import com.onsemi.ostorms.dao.UserDAO;
import com.onsemi.ostorms.model.FTPdata;
import com.onsemi.ostorms.model.Hostname;
import com.onsemi.ostorms.model.Inventory;
import com.onsemi.ostorms.model.LDAPUser;
import com.onsemi.ostorms.model.Log;
import com.onsemi.ostorms.model.Request;
import com.onsemi.ostorms.model.SRInventory;
import com.onsemi.ostorms.model.Scrap;
import com.onsemi.ostorms.tools.EmailSender;
import com.onsemi.ostorms.tools.QueryResult;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/config/ConfigAutoScrap.java
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
public class ConfigAutoScrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigAutoScrap.class);
    String[] args = {};

    @Autowired
    ServletContext servletContext;

//    @Scheduled(cron = "0 40 14 * * *") //every 8 AM everyday - cron (sec min hr daysOfMth month daysOfWeek year(optional)) testing
<<<<<<< HEAD:src/main/java/com/onsemi/mib/config/ConfigAutoScrap.java
//    @Scheduled(cron = "0 5 9 1 * ?") //every 8 AM everyday - cron (sec min hr daysOfMth month daysOfWeek year(optional)) production
=======
    @Scheduled(cron = "0 5 9 1 * ?") //every 8 AM everyday - cron (sec min hr daysOfMth month daysOfWeek year(optional)) production
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/config/ConfigAutoScrap.java
    public void cronRun() {

        InventoryDAO invD = new InventoryDAO();
        List<Inventory> dueScrapList = invD.getInventoryListForScrap();

        boolean flag = false;

        if (dueScrapList != null) {
            for (int x = 0; x < dueScrapList.size(); x++) {
                flag = true;

                //add into scrap table
                Scrap scrap = new Scrap();
                scrap.setRequestId(dueScrapList.get(x).getReqId());
                scrap.setMonthScrap(dueScrapList.get(x).getMthToScrap());
//                scrap.setStatus("Ready for Scrap");
                scrap.setStatus("Pending Scrap");
                scrap.setCreatedBy("System Generated");
                scrap.setFlag("0");
                ScrapDAO scrapD = new ScrapDAO();
                QueryResult qScrap = scrapD.insertScrap(scrap);
                if (!qScrap.getGeneratedKey().equals("0")) {

                    flag = true;

                    //update req table
                    Request req = new Request();
                    req.setId(dueScrapList.get(x).getReqId());
//                    req.setStatus("Ready for Scrap");
                    req.setStatus("Pending Scrap");
                    req.setFlag("0");
                    req.setModifiedBy("System");
                    RequestDAO reqD = new RequestDAO();
                    QueryResult qReq = reqD.updateRequestStatusAndFlag(req);

                    //update inv table
                    Inventory inv = new Inventory();
                    inv.setId(dueScrapList.get(x).getId());
//                    inv.setStatus("Ready for Scrap");
                    inv.setStatus("Pending Scrap");
                    inv.setFlag("0");
                    inv.setModifiedBy("System");
                    InventoryDAO invD2 = new InventoryDAO();
                    QueryResult qInv = invD2.updateInventoryStatusAndFlag(inv);

                    //update ftp table
                    FTPdata ftp = new FTPdata();
                    ftp.setId(dueScrapList.get(x).getFtpId());
//                    ftp.setStatus("Ready for Scrap");
                    ftp.setStatus("Pending Scrap");
                    ftp.setFlag("1");
                    ftp.setModifiedBy("System");
                    FTPDao ftpD = new FTPDao();
                    QueryResult qFtp = ftpD.updateStatus(ftp);

                    //insert into log table
                    Log log = new Log();
                    log.setRequestId(dueScrapList.get(x).getReqId());
                    log.setDetail("Pending Scrap");
                    log.setCreatedBy("System");
                    LogDAO logD = new LogDAO();
                    QueryResult logQ = logD.insertLog(log);
                }

            }

            if (flag == true) {
                UserDAO userDao = new UserDAO();
                List<LDAPUser> userRecipientsList = userDao.getSREmailAutoScrapList();
                String[] to = new String[userRecipientsList.size()];
                for (int i = 0; i < userRecipientsList.size(); i++) {
                    to[i] = userRecipientsList.get(i).getEmail();
                }

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                //sent email to respective person
                LOGGER.info("send email to person in charge");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        "Sample Retention Due for Scrap", //subject
                        "<br />Below are the details for sample retentions that due for scrap: "
                        + "<br /> "
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/scrap/pendingList \">HERE</a> for further information."
                        + "<br /> "
                        + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                        + "<table style=\"width:100%\">" //tbl
                        + "<tr>"
                        + "<th>No.</th> "
                        + "<th>RMSLot_Event</th> "
                        + "<th>Shelf ID</th>"
                        + "<th>Inventory Date</th>"
                        + "<th>Scrap Date</th>"
                        + "</tr>"
                        + table()
                        + "</table>"
                        + "<br />Thank you." //msg
                );
            } else {
                LOGGER.info("No Scrap for this month");
            }
        }
    }

    private String table() {
        InventoryDAO invD = new InventoryDAO();
        List<Inventory> dueScrapList = invD.getInventoryListPendingForScrap();

        String invId = "";
        String text = "";

        for (int i = 0; i < dueScrapList.size(); i++) {
            invId = dueScrapList.get(i).getId();

//            SRInventoryDAO srInvDao = new SRInventoryDAO();
//            SRInventory srInv = srInvDao.getInventoryDetails(invId);
            InventoryDAO inventoryDAO = new InventoryDAO();
            Inventory inv = inventoryDAO.getInventoryPendingforScrapByInvId(invId);

            LOGGER.info("invId: " + dueScrapList.get(i).getId());
            LOGGER.info("getRmsLotEvent: " + inv.getRmsLotEvent());

            int index = i + 1;
            text = text + "<tr align = \"center\">";
            text = text + "<td>" + index + "</td>";
            text = text + "<td>" + inv.getRmsLotEvent() + "</td>";
            text = text + "<td>" + inv.getInventoryShelf() + "</td>"; //rackID
            text = text + "<td>" + inv.getInventoryDate() + "</td>"; //shelfID
            text = text + "<td>" + inv.getMthToScrap() + "</td>"; //inventoryDate
            text = text + "</tr>";
        }
        return text;
    }

}
