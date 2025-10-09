package com.onsemi.mib.config;

<<<<<<< HEAD:src/main/java/com/onsemi/mib/config/FtpConfig.java
import com.onsemi.mib.dao.FTPDao;
import com.onsemi.mib.dao.LogFtpDAO;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.FtpFile;
import com.onsemi.mib.model.LogFtp;
import com.onsemi.mib.tools.QueryResult;
=======
import com.onsemi.ostorms.dao.FTPDao;
import com.onsemi.ostorms.dao.LogFtpDAO;
import com.onsemi.ostorms.model.FTPdata;
import com.onsemi.ostorms.model.FtpFile;
import com.onsemi.ostorms.model.LogFtp;
import com.onsemi.ostorms.tools.QueryResult;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/config/FtpConfig.java
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class FtpConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpConfig.class);
    String[] args = {};

    @Autowired
    ServletContext servletContext;

    String fileLocation = "";

//    @Scheduled(cron = "0 35 9 * * ?") //every 5 minute - cron (sec min hr daysOfMth month daysOfWeek year(optional)) //testing
<<<<<<< HEAD:src/main/java/com/onsemi/mib/config/FtpConfig.java
//    @Scheduled(cron = "0 30 0 * * ?") //every 5 minute - cron (sec min hr daysOfMth month daysOfWeek year(optional)) production
=======
    @Scheduled(cron = "0 30 0 * * ?") //every 5 minute - cron (sec min hr daysOfMth month daysOfWeek year(optional)) production
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/config/FtpConfig.java
    public void cronRun() {
//        String username = System.getProperty("user.name");
//        String targetLocation = "C:\\OSTORM TEST FTP\\";  //local
//        String targetLocation = "\\\\mysed-rel-app05\\c$\\OSTORM TEST FTP\\";   //testing
        String targetLocation = "\\\\phcad-relost01\\c$\\OSTORMS FTP\\";   //server

        File folder = new File(targetLocation);
        File[] listOfFiles = folder.listFiles();

        int countInsert = 0;
        int countUpdatePkgFamily = 0;
        int countUpdateMthToScrap = 0;
        int countUpdateQty = 0;
        int countInsertArchive = 0;

        boolean readFile = false;

        if (listOfFiles.length != 0) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    if (listOfFile.getName().equals("OSTORMS_FTP.csv")) {
                        readFile = true;
                        fileLocation = targetLocation + listOfFile.getName();
                        LOGGER.info("FTP file found.");

                        CSVReader csvReader = null;
                        try {
                            csvReader = new CSVReader(new FileReader(fileLocation), ',', '"', 1);
                            String[] ionicFtp = null;
                            List<FtpFile> requestList = new ArrayList<FtpFile>();

                            while ((ionicFtp = csvReader.readNext()) != null) {
                                FtpFile ftpFile = new FtpFile(
                                        //BARU
                                        ionicFtp[0], ionicFtp[1], ionicFtp[2], //rmsNo,pkgName,event, 
                                        ionicFtp[3], ionicFtp[4], ionicFtp[5], //lotType, pkgFamily, compDate,
                                        ionicFtp[6], ionicFtp[7], ionicFtp[8], //scrapDate,mthToScrap,pStatus
                                        ionicFtp[9] //qty
                                /*lama
                                    ionicFtp[0], ionicFtp[1], ionicFtp[2], //eventComp, rmsNo, pkgName
                                    ionicFtp[3], ionicFtp[4], ionicFtp[5], //event, lotType, pStatusDate
                                    ionicFtp[6], ionicFtp[7], ionicFtp[8], //pkgFamily, compDate, scrapDate
                                    ionicFtp[9], ionicFtp[10], ionicFtp[11] //mthToScrap, pStatus, qty
                                 */
                                );
                                requestList.add(ftpFile);
                            }
                            int y = 1;
                            for (FtpFile r : requestList) {
                                y++;

                                String qty = "";
                                if (r.getUnitQty().equals("")) {
                                    qty = "0";
                                } else {
                                    qty = r.getUnitQty();
                                }

                                String mthToScrap = r.getMthToScrap();
                                if (!mthToScrap.equals("null")) {
                                } else {
                                    mthToScrap = null;
                                }

                                FTPdata ftp = new FTPdata();
                                ftp.setRmsId(r.getRmsId());
                                String newPkgName = r.getPkgName();
                                if (newPkgName.contains(",")) {
                                    newPkgName = newPkgName.replace(",", " ");
                                }
                                ftp.setPkgName(newPkgName);
                                ftp.setEvent(r.getEvent());
                                ftp.setLotType(r.getLotType());
                                String newPkgFmly = r.getPkgFamily();
                                if (newPkgFmly.equals("BUMP/0201")) {
                                    newPkgFmly = newPkgFmly.replace("/", "-");
                                }
                                ftp.setPkgFamily(newPkgFmly);
                                ftp.setCompleteDate(r.getCompleteDate());
                                ftp.setScrapDate(r.getScrapDate());
                                ftp.setMthToScrap(mthToScrap);
                                ftp.setProcessStatus(r.getProcessStatus());
                                ftp.setUnitQty(qty);

                                ftp.setRmsLotEvent(r.getRmsId() + r.getLotType() + "_" + r.getEvent());
                                ftp.setModifiedBy("FTP Config Cron");
                                ftp.setCreatedBy("FTP Config Cron");

                                Date tarikh = new Date();
                                DateFormat dateFormatM = new SimpleDateFormat("MM");
                                DateFormat dateFormatY = new SimpleDateFormat("YYYY");
                                int nowMonth = Integer.parseInt(dateFormatM.format(tarikh));
                                int nowYear = Integer.parseInt(dateFormatY.format(tarikh));
                                int bulanKenaScrap = 0;
                                int tahunKenaScrap = 0;
                                boolean flags = false;

                                String rmslotevent = r.getRmsId() + r.getLotType() + "_" + r.getEvent();

                                if (mthToScrap != null) {
                                    String mthToScrapSplit[] = mthToScrap.split("-");
                                    bulanKenaScrap = Integer.parseInt(mthToScrapSplit[1]);
                                    tahunKenaScrap = Integer.parseInt(mthToScrapSplit[0]);

                                    if (tahunKenaScrap == nowYear) {
                                        if (bulanKenaScrap >= nowMonth) {
                                            flags = true;
                                        } else {
                                            flags = false;
                                        }
                                    } else if (tahunKenaScrap > nowYear) {
                                        flags = true;
                                    } else {
                                        flags = false;
                                    }
                                } else {
                                    flags = false;
                                }

                                FTPDao ftpdao = new FTPDao();
//                                int count = ftpdao.getCountExistingData(r.getRmsId(), r.getEvent(), r.getLotType());
                                int count = ftpdao.getCountExistingDataNew(r.getRmsId(), r.getEvent(), r.getLotType()); //with creator = 'FTP' in where clause
                                if (count == 0 && flags == true) {
//                                    LOGGER.info(r.getRmsId() + r.getLotType() + "_" + r.getEvent() + " >> Mth to Scrap : " + r.getMthToScrap() + " >> bulanKenaScrap : " + bulanKenaScrap + " >> tahunKenaScrap : " + tahunKenaScrap + " >> nowYear : " + nowYear + " >> nowMonth : " + nowMonth);
                                    String flag = "";
                                    String status = "";

                                    flag = "0";
                                    status = "New Record";

                                    if (flag.equals("0") && r.getProcessStatus().contains("CANCEL")) {
                                        flag = "0";
                                        status = "New Record - Cancelled Lot";
                                    }

                                    ftp.setFlag(flag);
                                    ftp.setStatus(status);
                                    ftp.setCreator("FTP"); // to differentiate data from manual or ftp entry

                                    ftpdao = new FTPDao();
                                    QueryResult queryResult1 = ftpdao.insertFTPdata(ftp);
                                    if (queryResult1.getResult() == 1) {
                                        countInsert++;

                                        LogFtp log = new LogFtp();
                                        log.setFtpId(queryResult1.getGeneratedKey());
                                        log.setDetail("Added into OSTORMS Database (FTP)");
                                        log.setCreatedBy("OSTORMS Config");
                                        LogFtpDAO logD = new LogFtpDAO();
                                        QueryResult logQ = logD.insertLogFtp(log);

//                                        LOGGER.info("FTP Config : Insert data from FTP-SUCCESS");
                                    } else {
                                        LOGGER.info("FTP Config : Insert data from FTP-FAILURE: " + queryResult1.getResult());
                                    }

                                } else if (count == 1) {
                                    ftpdao = new FTPDao();
                                    FTPdata ftpData = ftpdao.getFtpDataPerRmsLotEvent(rmslotevent);

                                    if (!ftpData.getPkgFamily().equals(newPkgFmly)) {
                                        ftpdao = new FTPDao();
                                        int kira = ftpdao.getCountExistingRMSLotEventFlagZeroOrOne(rmslotevent);

                                        if (kira == 1) {
                                            FTPdata ftpData2 = new FTPdata();
                                            ftpData2.setPkgFamily(newPkgFmly);
                                            ftpData2.setModifiedBy("FTP Config Cron");
                                            ftpData2.setId(ftpData.getId());
                                            ftpdao = new FTPDao();
                                            QueryResult qr = ftpdao.updatePkgFamily(ftpData2);
                                            if (qr.getResult() == 1) {
                                                countUpdatePkgFamily++;
                                            } else {
                                                LOGGER.info("FAILURE : Update pkg fmly for ftpId " + ftpData.getId());
                                            }
                                        }
                                    }

                                    String quantity = r.getUnitQty();
                                    if (quantity.equals("")) {
                                        quantity = "0";
                                    }

                                    String newQty = "0";
                                    if (ftpData.getUnitQty() == null) {
                                        newQty = "0";
                                    } else {
                                        newQty = ftpData.getUnitQty();
                                    }

                                    if (!newQty.equals(quantity)) {

                                        FTPdata ftpData2 = new FTPdata();
                                        ftpData2.setUnitQty(quantity);
                                        ftpData2.setModifiedBy("FTP Config Cron");
                                        ftpData2.setId(ftpData.getId());
                                        ftpdao = new FTPDao();
                                        QueryResult qr = ftpdao.updateQty(ftpData2);
                                        if (qr.getResult() == 1) {
                                            countUpdateQty++;
                                        } else {
                                            LOGGER.info("FAILURE : Update qty for ftpId " + ftpData.getId());
                                        }
                                    }

                                    if (!ftpData.getMthToScrap().equals(mthToScrap)) {
                                        ftpdao = new FTPDao();
                                        int kira = ftpdao.getCountExistingRMSLotEventFlagZeroOrOne(rmslotevent);

                                        if (kira == 1) {
                                            FTPdata ftpData2 = new FTPdata();
                                            ftpData2.setCompleteDate(r.getCompleteDate());
                                            ftpData2.setScrapDate(r.getScrapDate());
                                            ftpData2.setMthToScrap(mthToScrap);
                                            ftpData2.setModifiedBy("FTP Config Cron");
                                            ftpData2.setId(ftpData.getId());
                                            ftpdao = new FTPDao();
                                            QueryResult qr = ftpdao.updateMthToScrap(ftpData2);
                                            if (qr.getResult() == 1) {
                                                countUpdateMthToScrap++;
                                            } else {
                                                LOGGER.info("FAILURE : Update mth to scrap for ftpId " + ftpData.getId());
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ee) {
                            LOGGER.info("FTP Config : Error while reading Sendayan_FTP.csv");
                            ee.printStackTrace();
                        }
                    }
                }
            }
            if (readFile == false) {
                LOGGER.info("FTP Config : File Sendayan_FTP.csv does not exist");
            } else {
                int count = countInsert + countInsertArchive + countUpdatePkgFamily + countUpdateMthToScrap + countUpdateQty;
                if (count > 0) {
                    System.out.println("Total new lot added : " + countInsert + "\n"
                            + "Total new lot archive added : " + countInsertArchive + "\n"
                            + "Total lot update pkgFamily : " + countUpdatePkgFamily + "\n"
                            + "Total lot update mth to scrap : " + countUpdateMthToScrap + "\n"
                            + "Total lot update qty : " + countUpdateQty + "\n"
                            + "TOTAL AFFECTED LOT(S) : " + count);
                } else {
                }
            }
        } else {
            LOGGER.info("FTP Config : Folder Empty");
        }
    }
}
