package com.onsemi.ostorms.config;

import com.onsemi.ostorms.dao.FTPDao;
import com.onsemi.ostorms.dao.LogFtpDAO;
import com.onsemi.ostorms.dao.LogModuleDAO;
import com.onsemi.ostorms.dao.SRArchiveDAO;
import com.onsemi.ostorms.model.FTPdata;
import com.onsemi.ostorms.model.LogFtp;
import com.onsemi.ostorms.model.LogRmsLot;
import com.onsemi.ostorms.model.SRArchive;
import com.onsemi.ostorms.tools.QueryResult;
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
public class ConfigFtpStatus {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFtpStatus.class);
    String[] args = {};
    
    @Autowired
    ServletContext servletContext;
    
    String fileLocation = "";
    
    @Scheduled(cron = "0 10 0 * * ?") //every 2 minute - cron (sec min hr daysOfMth month daysOfWeek year(optional)) new and active cron
    public void cronRun() {
        FTPdata ftp = new FTPdata();
        
        FTPDao ftpdao = new FTPDao();
        List<FTPdata> ftpList = ftpdao.getAllExpiredFtpDataNew();
        int size = ftpList.size();
        
        int count = 0;
        
        for (int x = 0; x < size; x++) {
            String id = ftpList.get(x).getId();
//            String groupId = ftpList.get(x).getGroupId();

            ftp.setStatus("Expired Lot");
            ftp.setFlag("99");
            ftp.setModifiedBy("Status Config");
            ftp.setId(id);
            ftp.setCancelBy("OSTORMS Config");
            ftpdao = new FTPDao();
            QueryResult qr = ftpdao.updateSelectedExpiredLot(ftp);
            
            if (qr.getResult() == 1) {
                //update log
                LogFtp log = new LogFtp();
                log.setFtpId(id);
                log.setDetail("Expired Lot. Transferred to No Retention List");
                log.setCreatedBy("OSTORMS Config");
                LogFtpDAO logD = new LogFtpDAO();
                QueryResult logQ = logD.insertLogFtp(log);

                //insert to archive table
                SRArchive srArchive = new SRArchive();
                srArchive.setFtpId(id);
                srArchive.setReqType("No Retention Plan");
                srArchive.setReasonsExc("Expired Lot");
                srArchive.setReqName("OSTORMS Config");
                srArchive.setRemarks("Expired Lot");
                srArchive.setCreatedBy("OSTORMS Config");
                srArchive.setStatus("Expired Lot");
                srArchive.setFlag("0");
                SRArchiveDAO srAchiveDao = new SRArchiveDAO();
                QueryResult qrArch = srAchiveDao.insertArchive(srArchive);
                if (qr.getResult() != 1) {
                    LOGGER.info("Failure : update status for expired lot");
                }
                count++;
            } else {
                //nothing
            }
        }
        if (count == 0) {
            LOGGER.info("No expired status config update.");
        } else {
            LOGGER.info("Expired status config updated.");
        }
    }
}
