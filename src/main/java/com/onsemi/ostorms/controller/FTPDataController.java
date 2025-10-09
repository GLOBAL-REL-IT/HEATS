package com.onsemi.ostorms.controller;

import com.onsemi.ostorms.dao.FTPDao;
import com.onsemi.ostorms.dao.InventoryDAO;
import com.onsemi.ostorms.dao.InventoryMgtDAO;
import com.onsemi.ostorms.dao.LogDAO;
import com.onsemi.ostorms.dao.LogFtpDAO;
import com.onsemi.ostorms.dao.RequestDAO;
import com.onsemi.ostorms.dao.SRArchiveDAO;
import com.onsemi.ostorms.model.FTPdata;
import com.onsemi.ostorms.model.Inventory;
import com.onsemi.ostorms.model.InventoryMgt;
import com.onsemi.ostorms.model.Log;
import com.onsemi.ostorms.model.LogFtp;
import com.onsemi.ostorms.model.Request;
import com.onsemi.ostorms.model.SRArchive;
import java.util.List;
import com.onsemi.ostorms.model.UserSession;
import com.onsemi.ostorms.tools.QueryResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/sr/srFtp")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class FTPDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTPDataController.class);
    String[] args = {};
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String ftpList(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
        FTPDao ftpDAO1 = new FTPDao();
        List<FTPdata> ftpDataList = ftpDAO1.getAllFtpDataLatest();

        String groupId = userSession.getGroup();

        FTPDao ftpDAO = new FTPDao();
        String revDate = ftpDAO.getLatestRevDate();

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm a");
        Date date = new Date();
        String nowDate = dateFormat.format(date);

        model.addAttribute("userSession", userSession);
        model.addAttribute("ftpDataList", ftpDataList);
        model.addAttribute("groupId", groupId);
        model.addAttribute("revDate", revDate);

        String allActive = "active";
        String allActiveTab = "in active";
        model.addAttribute("allActive", allActive);
        model.addAttribute("allActiveTab", allActiveTab);

        return "srFtp/ftpData";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model,
            @ModelAttribute UserSession userSession) {

        return "srFtp/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rmsId,
            @RequestParam(required = false) String rmsEvent,
            @RequestParam(required = false) String lotType,
            @RequestParam(required = false) String finalQty,
            @RequestParam(required = false) String pkgFamily,
            @RequestParam(required = false) String pkgName,
            @RequestParam(required = false) String completeDate,
            @RequestParam(required = false) String mthToScrap,
            @RequestParam(required = false) String checkboxPS,
            @RequestParam(required = false) String stressTypeMidPoint,
            @RequestParam(required = false) String shelf
    ) {

        String rmsLotEvent = rmsId + lotType + "_" + rmsEvent;

        //check inventory if available or not
        InventoryDAO invD = new InventoryDAO();
        Integer count = invD.getCountAvailableShelf(shelf);

        LOGGER.info("checkboxPS: " + checkboxPS);

        //remove shelf checking 1 July 2025
        if (count == 1) { //proceed if shelf available

            //create new data in ftp table
            FTPdata ftp = new FTPdata();
            ftp.setRmsId(rmsId);
            ftp.setEvent(rmsEvent);
            ftp.setRmsLotEvent(rmsLotEvent);
            ftp.setLotType(lotType);
            ftp.setActualQty(finalQty);
            ftp.setUnitQty(finalQty);
            ftp.setPkgFamily(pkgFamily);
            ftp.setPkgName(pkgName);
            ftp.setCompleteDate(completeDate);
            ftp.setMthToScrap(mthToScrap);
            ftp.setScrapDate(mthToScrap);
            ftp.setStatus("In Inventory");
            ftp.setFlag("1");
            ftp.setCreatedBy(userSession.getFullname());
            ftp.setCreator("User");
            FTPDao ftpD = new FTPDao();
            QueryResult qu = ftpD.insertFTPdata(ftp);

            Request request = new Request(); //create at request table
            request.setFtpId(qu.getGeneratedKey());
            request.setFinalQty(finalQty);
            request.setRequestBy(userSession.getFullname());
            request.setFlag("0");
            request.setStatus("In Inventory");

            if (checkboxPS != null) {
                request.setStressTypeMidPoint(stressTypeMidPoint);
            }
            RequestDAO requestDAO = new RequestDAO();
            QueryResult queryResult = requestDAO.insertRequest(request);
            args = new String[1];
            args[0] = rmsLotEvent;
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                model.addAttribute("request", request);
                return "srFtp/add";
            } else {

                //update inventoryMgt
                InventoryMgtDAO invmD = new InventoryMgtDAO();
                InventoryMgt inv = invmD.getInventoryMgtByShelf(shelf);

//                String rmsLotEventConcat = inv.getRmsLotEvent() + "," + rmsLotEvent;
                InventoryMgt invm = new InventoryMgt();

//                invm.setRmsLotEvent(rmsLotEventConcat); // 1 shelf can have multiple rmsLotEvent
//                    invm.setReqId(queryResult.getGeneratedKey()); // replace with rmsLotEvent
                invm.setId(inv.getId());
                invm.setStatus("Occupied");
                invm.setFlag("1");
                invmD = new InventoryMgtDAO();
                QueryResult invQ = invmD.updateInventoryMgtAfterRequest(invm);

                //insert into inventory table
                Inventory inventory = new Inventory();
                inventory.setReqId(queryResult.getGeneratedKey());
                inventory.setMthToScrap(mthToScrap);
                inventory.setInventoryShelf(shelf);
                inventory.setInventoryBy(userSession.getFullname());
                inventory.setStatus("In Inventory");
                inventory.setFlag("0");
                inventory.setCreatedBy(userSession.getFullname());
                inventory.setShelfId(inv.getId());
                invD = new InventoryDAO();
                QueryResult invQuery = invD.insertInventory(inventory);

                //update inventory id into request table
                Request req1 = new Request();
                req1.setInvId(invQuery.getGeneratedKey());
                req1.setId(queryResult.getGeneratedKey());
                requestDAO = new RequestDAO();
                QueryResult queryResultRequest = requestDAO.updateRequestForInvId(req1);

                //insert into log table
                Log log = new Log();
                log.setRequestId(queryResult.getGeneratedKey());
                log.setDetail("In Inventory");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult logQ = logD.insertLog(log);

                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                return "redirect:/sr/request/view/" + queryResult.getGeneratedKey();
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Shelf : " + shelf + " is not available. Pls assign with a different shelf ID");
            return "redirect:/sr/srFtp/add";
        }
    }

    @RequestMapping(value = "/cancelRetention/{id}", method = RequestMethod.GET)
    public String cancelRetention(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id
    ) {

        FTPdata ftp = new FTPdata();
        ftp.setCancelBy(userSession.getFullname());
        ftp.setStatus("New Record - Cancel Retention");
        ftp.setFlag("1");
        ftp.setId(id);
        FTPDao ftpD = new FTPDao();
        QueryResult query = ftpD.updateCancelRetention(ftp);

//        ftpD = new FTPDao();
//        FTPdata ftpData = ftpD.getFtpDataById(id);
        if (query.getResult() > 0) {

            LogFtp log = new LogFtp();
            log.setFtpId(id);
            log.setDetail("New Record - Cancel Retention");
            log.setCreatedBy(userSession.getFullname());
            LogFtpDAO logD = new LogFtpDAO();
            QueryResult logQ = logD.insertLogFtp(log);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String nowDate = dateFormat.format(date);
            //insert into sr_archive
            SRArchive srArchive = new SRArchive();
            srArchive.setFtpId(id);
            srArchive.setReqType("No Retention Plan");
            srArchive.setReasonsExc("Cancel Retention");
            srArchive.setReqName(userSession.getFullname());
            srArchive.setRelReqName(userSession.getFullname());
            srArchive.setRelDateReq(nowDate);
            srArchive.setRemarks("Cancel Retention");
            srArchive.setStatus("Archived");
            srArchive.setFlag("0");
            srArchive.setModifiedBy(userSession.getFullname());
            srArchive.setCreatedBy(userSession.getFullname());
            SRArchiveDAO srArchDao = new SRArchiveDAO();
            QueryResult qrArch = srArchDao.insertArchive(srArchive);

            redirectAttrs.addFlashAttribute("success", "Cancellation is successful");
        } else {
            redirectAttrs.addFlashAttribute("error", "Cancellation Failed");
        }
        return "redirect:/sr/srFtp";
    }
}
