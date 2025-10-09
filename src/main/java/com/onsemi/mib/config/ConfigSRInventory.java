package com.onsemi.mib.config;

import com.onsemi.mib.dao.SRInventoryMgtDAO;
import com.onsemi.mib.model.SRInventoryMgt;
import com.onsemi.mib.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ConfigSRInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigSRInventory.class);
    String[] args = {};

    @Autowired
    ServletContext servletContext;

    String fileLocation = "";

//    @Scheduled(cron = "0 0 6 * * ?") //every 2 minute - cron (sec min hr daysOfMth month daysOfWeek year(optional))
    public void cronRun() {
        String targetLocation = "D:\\Data\\OSTORMS\\Config File\\"; 
        File folder = new File(targetLocation);
        File[] listOfFiles = folder.listFiles();

        int check = 0;
        
        if(listOfFiles.length!=0) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    if(listOfFile.getName().equals("new_sr_rack.csv")) {
                        check = 1;
                        fileLocation = targetLocation + listOfFile.getName();
//                        LOGGER.info("Rack file found.");

                        CSVReader csvReader = null;      
                        try {
                            csvReader = new CSVReader(new FileReader(fileLocation), ',', '"', 0);
                            String[] ionicFtp = null;

                            String shelfId = "";
                            String rackId = "";
                            String month = "";
                            int kira = 0;
                            while ((ionicFtp = csvReader.readNext()) != null) {
                                int x=0;
                                while(x<18) { //no of column in file is 18
                                    shelfId = ionicFtp[x];
                                    rackId = shelfId.substring(0,7);
                                    month = shelfId.substring(2,5);
//                                    LOGGER.info(rackId + " >> " + month + " >> " + shelfId);
                                    
                                    if(month.equals("EXC")) { month = "13-Excess"; }
                                    else if(month.equals("JAN")) { month = "01-January"; }
                                    else if(month.equals("FEB")) { month = "02-February"; }
                                    else if(month.equals("MAR")) { month = "03-March"; }
                                    else if(month.equals("APR")) { month = "04-April"; }
                                    else if(month.equals("MAY")) { month = "05-May"; }
                                    else if(month.equals("JUN")) { month = "06-June"; }
                                    else if(month.equals("JUL")) { month = "07-July"; }
                                    else if(month.equals("AUG")) { month = "08-August"; }
                                    else if(month.equals("SEP")) { month = "09-September"; }
                                    else if(month.equals("OCT")) { month = "10-October"; }
                                    else if(month.equals("NOV")) { month = "11-November"; }
                                    else if(month.equals("DEC")) { month = "12-December"; }
                                    
                                    if(month.equals(check)) {}
                                    
                                    SRInventoryMgt  ftp = new SRInventoryMgt();
                                    ftp.setRackId(rackId);
                                    ftp.setRackMonth(month);
                                    ftp.setShelfId(shelfId);
                                    ftp.setOuterId("Empty");
                                    ftp.setStatus("Shelf Available");
                                    ftp.setFlag("0");
                                    
                                    SRInventoryMgtDAO inventoryMgtDao = new SRInventoryMgtDAO();
                                    int count = inventoryMgtDao.getCountShelf(shelfId);
                                    if (count == 0) {
                                        kira++;
                                        LOGGER.info("add new data from csv rack ... #" + kira);
                                        SRInventoryMgtDAO InventoryMgtDAO = new SRInventoryMgtDAO();
                                        QueryResult queryResult1 = InventoryMgtDAO.insertInventoryDetails(ftp);
                                    } else {}
                                    x++;
                                }
                                
                            }
                        } catch (Exception ee) {
                            LOGGER.info("Error while reading new_sr_rack.csv");
                            ee.printStackTrace();
                        }
                    } 
                }
            } if (check == 0) {
                LOGGER.info("new_sr_rack.csv file not found");
            }
        } else {
            LOGGER.info("Folder Empty");
        }
    }
}