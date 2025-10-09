package com.onsemi.mib.controller;

import com.onsemi.mib.dao.BulkRetrieveDAO;
import com.onsemi.mib.dao.BulkRetrieveDetailDAO;
import com.onsemi.mib.dao.FTPDao;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.InventoryDAO;
import com.onsemi.mib.dao.InventoryMgtDAO;
import com.onsemi.mib.dao.LogDAO;
import com.onsemi.mib.dao.RequestDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RetrieveDAO;
import com.onsemi.mib.dao.UserDAO;
import com.onsemi.mib.model.BulkRetrieve;
import com.onsemi.mib.model.BulkRetrieveDetail;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.Inventory;
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.model.Log;
import com.onsemi.mib.model.Request;
import com.onsemi.mib.model.Retrieve;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/sr/retrieve")
@SessionAttributes({"userSession"})
public class RetrieveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String retrieve(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String user = userSession.getLoginId();
        model.addAttribute("user", user);

        RetrieveDAO retrieveDAO = new RetrieveDAO();
        List<Retrieve> retrieveList = retrieveDAO.getRetrieveList();
        model.addAttribute("retrieveList", retrieveList);
        return "retrieve/retrieve";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventoryList = inventoryDAO.getInventoryListActive();
        model.addAttribute("inventoryList", inventoryList);
        return "retrieve/add";
    }

    @RequestMapping(value = "/add1/{invId}", method = RequestMethod.GET)
    public String add1(Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("invId") String invId) {

        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory inv = inventoryDAO.getInventoryListActiveByInvId(invId);
        model.addAttribute("inv", inv);
        return "retrieve/add1";
    }

    @RequestMapping(value = "/addBulk/{user}", method = RequestMethod.GET)
    public String addBulk(Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("user") String user) {

        String bulkId = "0";
        int countDetail = 0;

        List<BulkRetrieveDetail> bulkRetrieveDetail = new ArrayList<BulkRetrieveDetail>();

        BulkRetrieveDAO bulkD = new BulkRetrieveDAO();
        int count = bulkD.getCountBulkByUserIdWithFlagZero(user);

        if (count > 0) {
            bulkD = new BulkRetrieveDAO();
            BulkRetrieve bulk = bulkD.getBulkRetrieveByUserIdAndFlagZero(user);

            bulkId = bulk.getId();

            BulkRetrieveDetailDAO bulkDetailD = new BulkRetrieveDetailDAO();
            countDetail = bulkDetailD.getCountBulkDetailByBulkId(bulk.getId());

            if (countDetail > 0) {
                bulkDetailD = new BulkRetrieveDetailDAO();
                bulkRetrieveDetail = bulkDetailD.getBulkRetrieveDetailListWithOtherTable(bulk.getId());
            }
        }
        model.addAttribute("countDetail", countDetail);
        model.addAttribute("bulkRetrieveDetail", bulkRetrieveDetail);

        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventoryList = inventoryDAO.getInventoryListActive();
        model.addAttribute("inventoryList", inventoryList);
        model.addAttribute("bulkId", bulkId);
        model.addAttribute("user", user);

        return "retrieve/addBulk";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String invId,
            @RequestParam(required = false) String returnable,
            @RequestParam(required = false) String rmsLotEvent,
            @RequestParam(required = false) String reqRemarks
    ) {
        Retrieve retrieve = new Retrieve();
        retrieve.setReqId(reqId);
        retrieve.setReturnable(returnable);
        retrieve.setRequestorName(userSession.getFullname());
        retrieve.setRequestorEmail(userSession.getEmail());
        retrieve.setReqRemarks(reqRemarks);
        retrieve.setCreatedBy(userSession.getFullname());
        retrieve.setStatus("Request for Retrieval");
        retrieve.setFlag("0");
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.insertRetrieve(retrieve);
        args = new String[1];
        args[0] = rmsLotEvent;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("retrieve", retrieve);
            return "retrieve/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));

            String statusInv = "";
            if ("Returnable".equals(returnable)) {
                statusInv = "Requested for Retrieval (Returnable)";
            } else {
                statusInv = "Requested for Retrieval (Non-Returnable)";
            }

            //update status for inventory table
            Inventory inv = new Inventory();
            inv.setId(invId);
            inv.setStatus(statusInv);
            inv.setModifiedBy(userSession.getFullname());
            inv.setFlag("0");
            InventoryDAO invD = new InventoryDAO();
            QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

            //update request table
            Request req = new Request();
            req.setStatus(statusInv);
            req.setFlag("0");
            req.setModifiedBy(userSession.getFullname());
            req.setId(reqId);
            RequestDAO reqD = new RequestDAO();
            QueryResult reqQ = reqD.updateRequestStatusAndFlag(req);

            //update log
            Log log = new Log();
            log.setRequestId(reqId);
            log.setDetail(statusInv);
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            String requestorId = userSession.getLoginId();
            UserDAO userDao = new UserDAO();
            List<LDAPUser> userRecipientsList = userDao.getSREmailRetrieveList(requestorId);

            String[] to = new String[userRecipientsList.size()];
            for (int i = 0; i < userRecipientsList.size(); i++) {
                to[i] = userRecipientsList.get(i).getEmail();
            }

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    to, //to
                    //                    emailTo, //to
                    "Box Request for Retrieval", //subject
                    "<br />"
                    + "Below are the request details."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + userSession.getFullname()
                    + "<br /> "
                    + "Request Date: " + formattedString
                    + "<br /> "
                    + "Returnable: " + returnable
                    + "<br /> "
                    + "Remarks: " + reqRemarks
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(reqId)
                    + "</table>"
                    + "<br />Thank you." //msg
            );

//            return "redirect:/retrieve/edit/" + queryResult.getGeneratedKey();
            return "redirect:/sr/retrieve"; //back to homepage
        }
    }

    @RequestMapping(value = "/saveBulk", method = RequestMethod.POST)
    public String saveBulk(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String bulkId,
            @RequestParam(required = false) String returnable,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String reqRemarks
    ) {

        BulkRetrieveDAO bulkD = new BulkRetrieveDAO();
        int countBulkId = bulkD.getCountBulkId(bulkId);

        if (countBulkId == 0) {
            BulkRetrieve bulk = new BulkRetrieve();
            bulk.setRequestor(user);
            bulk.setFlag("0");
            bulkD = new BulkRetrieveDAO();
            QueryResult queryBulk = bulkD.insertBulkRetrieve(bulk);

            if (!queryBulk.getGeneratedKey().equals("0")) {

                BulkRetrieveDetailDAO bulkDetailD = new BulkRetrieveDetailDAO();
                int count = bulkDetailD.getCountBulkDetailByReqIdAndFlagZero(reqId);

                if (count > 0) {
                    redirectAttrs.addFlashAttribute("error", "Sample Already Requested for Retrieval.");
                    return "redirect:/sr/retrieve/addBulk/" + user; //back to homepage
                }

                BulkRetrieveDetail bulkDetail = new BulkRetrieveDetail();
                bulkDetail.setBulkId(queryBulk.getGeneratedKey());
                bulkDetail.setReqId(reqId);
                bulkDetail.setReturnable(returnable);
                bulkDetail.setRemarks(reqRemarks);
                bulkDetail.setFlag("0");

                bulkDetailD = new BulkRetrieveDetailDAO();
                QueryResult queryDetail = bulkDetailD.insertBulkRetrieveDetail(bulkDetail);

                //update log
                Log log = new Log();
                log.setRequestId(reqId);
                log.setDetail("Added into Bulk Retrieve List");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult logQ = logD.insertLog(log);
            } else {
                redirectAttrs.addFlashAttribute("error", "Fail to create Retrieval List.");
                return "redirect:/sr/retrieve/addBulk/" + user; //back to homepage
            }
        } else {

            BulkRetrieveDetailDAO bulkDetailD = new BulkRetrieveDetailDAO();
//            int count = bulkDetailD.getCountBulkDetailByBulkIdAndReqIdAndFlagZero(bulkId, reqId);
            int count = bulkDetailD.getCountBulkDetailByReqIdAndFlagZero(reqId);

            if (count > 0) {
                redirectAttrs.addFlashAttribute("error", "Sample Already Requested for Retrieval.");
                return "redirect:/sr/retrieve/addBulk/" + user; //back to homepage
            }

            BulkRetrieveDetail bulkDetail = new BulkRetrieveDetail();
            bulkDetail.setBulkId(bulkId);
            bulkDetail.setReqId(reqId);
            bulkDetail.setReturnable(returnable);
            bulkDetail.setRemarks(reqRemarks);
            bulkDetail.setFlag("0");

            bulkDetailD = new BulkRetrieveDetailDAO();
            QueryResult queryDetail = bulkDetailD.insertBulkRetrieveDetail(bulkDetail);

            //update log
            Log log = new Log();
            log.setRequestId(reqId);
            log.setDetail("Added into Bulk Retrieve List");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);
        }

        return "redirect:/sr/retrieve/addBulk/" + user; //back to homepage
    }

    @RequestMapping(value = "/sendRequest/{bulkId}/{user}", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendRequest(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("bulkId") String bulkId,
            @PathVariable("user") String user,
            @ModelAttribute UserSession userSession
    ) {

//        LOGGER.info("bulkId ==> " + bulkId);
//        LOGGER.info("user ==> " + user);
        //update bulk retrieve flag 1
        BulkRetrieve bulk = new BulkRetrieve();
        bulk.setId(bulkId);
        bulk.setFlag("1");
        BulkRetrieveDAO bulkD = new BulkRetrieveDAO();
        QueryResult qB = bulkD.updateBulkRetrieveForFlag(bulk);

        if (qB.getResult() > 0) {

            BulkRetrieveDetailDAO bulkDetailD = new BulkRetrieveDetailDAO();
            List<BulkRetrieveDetail> bulkDetailList = bulkDetailD.getBulkRetrieveDetailListWithOtherTable(bulkId);

            for (int i = 0; i < bulkDetailList.size(); i++) {

                //update flag to 1 for bulk retrieve detail table
                BulkRetrieveDetail bulkDetail = new BulkRetrieveDetail();
                bulkDetail.setId(bulkDetailList.get(i).getId());
                bulkDetail.setFlag("1");
                BulkRetrieveDetailDAO bulkDetailD2 = new BulkRetrieveDetailDAO();
                QueryResult qBD = bulkDetailD2.updateBulkRetrieveDetailForFlag(bulkDetail);

                //add request to retrieve table
                Retrieve retrieve = new Retrieve();
                retrieve.setReqId(bulkDetailList.get(i).getReqId());
                retrieve.setReturnable(bulkDetailList.get(i).getReturnable());
                retrieve.setRequestorName(userSession.getFullname());
                retrieve.setRequestorEmail(userSession.getEmail());
                retrieve.setReqRemarks(bulkDetailList.get(i).getRemarks());
                retrieve.setCreatedBy(userSession.getFullname());
                retrieve.setStatus("Request for Retrieval");
                retrieve.setFlag("0");
                RetrieveDAO retrieveDAO = new RetrieveDAO();
                QueryResult queryResult = retrieveDAO.insertRetrieve(retrieve);

                String statusInv = "";
                if ("Returnable".equals(bulkDetailList.get(i).getReturnable())) {
                    statusInv = "Requested for Retrieval (Returnable)";
                } else {
                    statusInv = "Requested for Retrieval (Non-Returnable)";
                }

                //update status for inventory table
                Inventory inv = new Inventory();
                inv.setId(bulkDetailList.get(i).getInvId());
                inv.setStatus(statusInv);
                inv.setModifiedBy(userSession.getFullname());
                inv.setFlag("0");
                InventoryDAO invD = new InventoryDAO();
                QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

                //update request table
                Request req = new Request();
                req.setStatus(statusInv);
                req.setFlag("0");
                req.setModifiedBy(userSession.getFullname());
                req.setId(bulkDetailList.get(i).getReqId());
                RequestDAO reqD = new RequestDAO();
                QueryResult reqQ = reqD.updateRequestStatusAndFlag(req);

                //update log
                Log log = new Log();
                log.setRequestId(bulkDetailList.get(i).getReqId());
                log.setDetail(statusInv);
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult logQ = logD.insertLog(log);
            }

            //send email
            String requestorId = userSession.getLoginId();
            UserDAO userDao = new UserDAO();
            List<LDAPUser> userRecipientsList = userDao.getSREmailRetrieveList(requestorId);

            String[] to = new String[userRecipientsList.size()];
            for (int ii = 0; ii < userRecipientsList.size(); ii++) {
                to[ii] = userRecipientsList.get(ii).getEmail();
            }

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    to, //to
                    //                    emailTo, //to
                    "Box Request for Retrieval", //subject
                    "<br />"
                    + "Below are the request details."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + userSession.getFullname()
                    + "<br /> "
                    + "Request Date: " + formattedString
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "<th>Returnable/Non-Returnable</th>"
                    + "<th>Remarks</th>"
                    + "</tr>"
                    + tableBulk(bulkId)
                    + "</table>"
                    + "<br />Thank you." //msg
            );
        } else {
            redirectAttrs.addFlashAttribute("error", "Fail to submit request. Pls Contact Admin");
            return "redirect:/sr/retrieve/addBulk/" + user;
        }
        redirectAttrs.addFlashAttribute("success", "Request Sent.");
        return "redirect:/sr/retrieve"; //back to homepage
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public String cancel(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id
    ) {
        RetrieveDAO retD = new RetrieveDAO();
        Retrieve ret = retD.getRetrieve(id);

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(ret.getReqId());

        Retrieve retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setStatus("Cancel Request");
        retrieve.setFlag("1");
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.updateRetrieveStatusAndFlag(retrieve);
        args = new String[1];
        args[0] = req.getRmsLotEvent();
        if (queryResult.getResult() == 0) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
//            model.addAttribute("retrieve", retrieve);
            return "redirect:/retrieve"; //
        } else {
            //update status for inventory table
            Inventory inv = new Inventory();
            inv.setId(req.getInvId());
            inv.setStatus("In Inventory");
            inv.setModifiedBy(userSession.getFullname());
            inv.setFlag("0");
            InventoryDAO invD = new InventoryDAO();
            QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

            //update request table
            Request req2 = new Request();
            req2.setStatus("In Inventory");
            req2.setFlag("0");
            req2.setModifiedBy(userSession.getFullname());
            req2.setId(ret.getReqId());
            RequestDAO reqD2 = new RequestDAO();
            QueryResult reqQ = reqD2.updateRequestStatusAndFlag(req2);

            //update log
            Log log = new Log();
            log.setRequestId(ret.getReqId());
            log.setDetail("Cancel Retrieval Request");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            //send email
            List<String> emails = new ArrayList<String>();
            emails.add(ret.getRequestorEmail()); // add email requestor to the list

            String requestorId1 = userSession.getLoginId();
            UserDAO userDao1 = new UserDAO();
            List<LDAPUser> userRecipientsList1 = userDao1.getSREmailRetrieveList(requestorId1);

            for (int i = 0; i < userRecipientsList1.size(); i++) {
                emails.add(userRecipientsList1.get(i).getEmail());
            }

            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    //                    to, //to
                    emailTo,
                    "[CANCEL] Box Request for Retrieval", //subject
                    "<br />"
                    + "Pls be informed that below request has been cancelled."
                    + "<br /> "
                    + "<br /> "
                    + "Cancellation By: " + userSession.getFullname()
                    + "<br /> "
                    + "Cancellation Date: " + formattedString
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(ret.getReqId())
                    + "</table>"
                    + "<br />Thank you." //msg
            );

            redirectAttrs.addFlashAttribute("success", "Cancellation Successful.");
//            return "redirect:/retrieve/edit/" + queryResult.getGeneratedKey();
            return "redirect:/sr/retrieve"; //back to homepage
        }
    }

    @RequestMapping(value = "/cancelBulk/{id}/{user}", method = RequestMethod.GET)
    public String cancelBulk(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id,
            @PathVariable("user") String user
    ) {

        BulkRetrieveDetailDAO bulkD = new BulkRetrieveDetailDAO();
        QueryResult quer = bulkD.deleteBulkRetrieveDetail(id);

        redirectAttrs.addFlashAttribute("success", "Cancellation Successful.");
//            return "redirect:/retrieve/edit/" + queryResult.getGeneratedKey();
        return "redirect:/sr/retrieve/addBulk/" + user; //back to homepage
    }

    @RequestMapping(value = "/verify/{retrieveId}", method = RequestMethod.GET)
    public String verify(
            Model model,
            @PathVariable("retrieveId") String retrieveId
    ) {

        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve retrieve = retrieveDAO.getRetrieveWithAllDetail(retrieveId);

        if (retrieve.getStatus().contains("Request for Retrieval")) {

            String reActive = "active";
            String reActiveTab = "in active";
            model.addAttribute("reActive", reActive);
            model.addAttribute("reActiveTab", reActiveTab);
        } else {
            String reActive = "";
            String reActiveTab = "";
            model.addAttribute("reActive", reActive);
            model.addAttribute("reActiveTab", reActiveTab);
        }
        if ("Verified. Ready for Pickup".equals(retrieve.getStatus()) || "Received".equals(retrieve.getStatus())) {

            String ldActive = "active";
            String ldActiveTab = "in active";
            model.addAttribute("ldActive", ldActive);
            model.addAttribute("ldActiveTab", ldActiveTab);
        } else {
            String ldActive = "";
            String ldActiveTab = "";
            model.addAttribute("ldActive", ldActive);
            model.addAttribute("ldActiveTab", ldActiveTab);
        }
        if (retrieve.getStatus().contains("Return for Inventory")) {
            //as requested 2/11/16
            String udActive = "active";
            String udActiveTab = "in active";
            model.addAttribute("udActive", udActive);
            model.addAttribute("udActiveTab", udActiveTab);
        } else {
            String udActive = "";
            String udActiveTab = "";
            model.addAttribute("udActive", udActive);
            model.addAttribute("udActiveTab", udActiveTab);
        }
        model.addAttribute("retrieve", retrieve);
        return "retrieve/manage";
    }

    @RequestMapping(value = "/VerifiyUpdate", method = RequestMethod.POST)
    public String verifyUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String reqId
    ) {

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(reqId);

        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve ret = retrieveDAO.getRetrieveWithAllDetail(id);

        Retrieve retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setVerificationBy(userSession.getFullname());
        retrieve.setStatus("Verified. Ready for Pickup");
        retrieve.setFlag("0");
        retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.updateRetrieveVerification(retrieve);
        args = new String[1];
        args[0] = req.getRmsLotEvent();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update log
            Log log = new Log();
            log.setRequestId(reqId);
            log.setDetail("Verified. Ready for Pickup");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            List<String> emails = new ArrayList<String>();
            emails.add(ret.getRequestorEmail()); // add email requestor to the list

            String requestorId1 = userSession.getLoginId();
            UserDAO userDao1 = new UserDAO();
            List<LDAPUser> userRecipientsList1 = userDao1.getSREmailRetrieveList(requestorId1);

            for (int i = 0; i < userRecipientsList1.size(); i++) {
                emails.add(userRecipientsList1.get(i).getEmail());
            }

            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    //                    to, //to
                    emailTo,
                    "Box Request for Retrieval - Verified and Ready for Pickup", //subject
                    "<br />"
                    + "Pls be informed that below request has been verified and ready for pickup."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + ret.getRequestorName()
                    + "<br /> "
                    + "Request Date: " + ret.getReqDate()
                    + "<br /> "
                    + "Returnable: " + ret.getReturnable()
                    + "<br /> "
                    + "Remarks: " + ret.getReqRemarks()
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve/verify/" + id + " \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(reqId)
                    + "</table>"
                    + "<br />Thank you." //msg
            );

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/sr/retrieve/";
    }

    @RequestMapping(value = "/receive/{retrieveId}", method = RequestMethod.GET)
    public String receive(
            Model model,
            @PathVariable("retrieveId") String retrieveId
    ) {
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve retrieve = retrieveDAO.getRetrieveWithAllDetail(retrieveId);
        model.addAttribute("retrieve", retrieve);
        return "retrieve/receive";
    }

    @RequestMapping(value = "/receiveUpdate", method = RequestMethod.POST)
    public String receiveUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String returnable
    ) {

        Retrieve retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setReturnable(returnable);
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        QueryResult q = retrieveDAO.updateRetrieveReturnable(retrieve);

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(reqId);

        retrieveDAO = new RetrieveDAO();
        Retrieve ret = retrieveDAO.getRetrieveWithAllDetail(id);

        retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setRlReceivedBy(userSession.getFullname());
        if ("Returnable".equals(ret.getReturnable())) { //check if returnable or not
            retrieve.setStatus("Received");
            retrieve.setFlag("0");
        } else {
            retrieve.setStatus("Closed");
            retrieve.setFlag("1");
        }
        retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.updateRetrieveReceived(retrieve);
        args = new String[1];
        args[0] = req.getRmsLotEvent();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update request, inventory and inventorymgt table if non-returnable
            if ("Non-Returnable".equals(ret.getReturnable())) {

                //update status for inventory table
                Inventory inv = new Inventory();
                inv.setId(req.getInvId());
                inv.setStatus("Closed");
                inv.setModifiedBy(userSession.getFullname());
                inv.setFlag("1");
                InventoryDAO invD = new InventoryDAO();
                QueryResult invQ2 = invD.updateInventoryStatusAndFlag(inv);

                InventoryMgtDAO invmD = new InventoryMgtDAO();
                InventoryMgt invMgt = invmD.getInventoryMgtByShelf(req.getShelf());

                invmD = new InventoryMgtDAO();
                int countShelfId = invmD.getCountShelfIdInInventoryTable(invMgt.getId());

                //update inventory management table
                InventoryMgt invm = new InventoryMgt();
                invm.setId(invMgt.getId());
                if (countShelfId > 0) {
                    invm.setStatus("Occupied");
                    invm.setFlag("1");
                } else {
                    invm.setStatus("Shelf Available");
                    invm.setFlag("0");
                }
                invmD = new InventoryMgtDAO();
                QueryResult invQ = invmD.updateInventoryMgtAfterRequest(invm);

                //update request table
                Request req2 = new Request();
                req2.setStatus("Closed");
                req2.setFlag("1");
                req2.setModifiedBy(userSession.getFullname());
                req2.setId(reqId);
                RequestDAO reqD2 = new RequestDAO();
                QueryResult reqQ = reqD2.updateRequestStatusAndFlag(req2);
            }

            //update log
            Log log = new Log();
            log.setRequestId(reqId);
            if ("Returnable".equals(ret.getReturnable())) { //check if returnable or not
                log.setDetail("Received");
            } else {
                log.setDetail("Closed");
            }
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            List<String> emails = new ArrayList<String>();
            emails.add(ret.getRequestorEmail()); // add email requestor to the list

            String requestorId1 = userSession.getLoginId();
            UserDAO userDao1 = new UserDAO();
            List<LDAPUser> userRecipientsList1 = userDao1.getSREmailRetrieveList(requestorId1);

            for (int i = 0; i < userRecipientsList1.size(); i++) {
                emails.add(userRecipientsList1.get(i).getEmail());
            }

            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            String subject = "";
            String body = "";
            String status = "";
            if ("Returnable".equals(ret.getReturnable())) { //check if returnable or not
                subject = "Box Request for Retrieval - Received";
                body = "received";
                status = "Received";
            } else {
                subject = "Box Request for Retrieval - Closed";
                body = "closed";
                status = "Closed";
            }

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    //                    to, //to
                    emailTo,
                    subject,
                    "<br />"
                    + "Pls be informed that below request has been " + body + " by the requestor."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + ret.getRequestorName()
                    + "<br /> "
                    + status + " Date: " + formattedString + " (By: " + userSession.getFullname() + ")"
                    + "<br /> "
                    + "Returnable: " + ret.getReturnable()
                    + "<br /> "
                    + "Remarks: " + ret.getReqRemarks()
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve/verify/" + id + " \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(reqId)
                    + "</table>"
                    + "<br />Thank you." //msg
            );

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/sr/retrieve/";
    }

    @RequestMapping(value = "/return/{retrieveId}", method = RequestMethod.GET)
    public String returns(
            Model model,
            @PathVariable("retrieveId") String retrieveId
    ) {
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve retrieve = retrieveDAO.getRetrieveWithAllDetail(retrieveId);
        model.addAttribute("retrieve", retrieve);
        return "retrieve/return";
    }

    @RequestMapping(value = "/returnUpdate", method = RequestMethod.POST)
    public String returnUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String returnQty
    ) {

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(reqId);

        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve ret = retrieveDAO.getRetrieveWithAllDetail(id);

        Retrieve retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setReturnBy(userSession.getFullname());
        retrieve.setStatus("Return for Inventory");
        retrieve.setFlag("0");
        retrieve.setReturnQty(returnQty);
        retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.updateRetrieveReturn(retrieve);
        args = new String[1];
        args[0] = req.getRmsLotEvent();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", "Please return the sample for inventory");

            //update log
            Log log = new Log();
            log.setRequestId(reqId);
            log.setDetail("Return for Inventory (qty: " + returnQty + ")");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            //update ftp actual qty
            FTPdata ftp = new FTPdata();
            ftp.setActualQty(returnQty);
            ftp.setId(req.getFtpId());
            ftp.setModifiedBy(userSession.getFullname());
            FTPDao ftpD = new FTPDao();
            QueryResult ftpQ = ftpD.updateActualQty(ftp);

            List<String> emails = new ArrayList<String>();
            emails.add(ret.getRequestorEmail()); // add email requestor to the list

            String requestorId1 = userSession.getLoginId();
            UserDAO userDao1 = new UserDAO();
            List<LDAPUser> userRecipientsList1 = userDao1.getSREmailRetrieveList(requestorId1);

            for (int i = 0; i < userRecipientsList1.size(); i++) {
                emails.add(userRecipientsList1.get(i).getEmail());
            }

            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    //                    to, //to
                    emailTo,
                    "Box Request for Retrieval - Return for Inventory", //subject
                    "<br />"
                    + "Pls be informed that below request will be return for inventory."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + ret.getRequestorName()
                    + "<br /> "
                    + "Return Date: " + formattedString + " (By: " + userSession.getFullname() + ")"
                    + "<br /> "
                    + "Returnable: " + ret.getReturnable()
                    + "<br /> "
                    + "Remarks: " + ret.getReqRemarks()
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve/verify/" + id + " \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(reqId)
                    + "</table>"
                    + "<br />Thank you." //msg
            );

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/sr/retrieve/";
    }

    @RequestMapping(value = "/reInventory/{retrieveId}", method = RequestMethod.GET)
    public String reInventory(
            Model model,
            @PathVariable("retrieveId") String retrieveId
    ) {
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve retrieve = retrieveDAO.getRetrieveWithAllDetail(retrieveId);
        model.addAttribute("retrieve", retrieve);
        return "retrieve/return";
    }

    @RequestMapping(value = "/reInventoryUpdate", method = RequestMethod.POST)
    public String reInventoryUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String reqId
    ) {

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(reqId);

        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve ret = retrieveDAO.getRetrieveWithAllDetail(id);

        Retrieve retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setReInventoryBy(userSession.getFullname());
        retrieve.setStatus("Closed");
        retrieve.setFlag("1");
        retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.updateRetrieveReInventory(retrieve);
        args = new String[1];
        args[0] = req.getRmsLotEvent();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update status for inventory table
            Inventory inv = new Inventory();
            inv.setId(req.getInvId());
            inv.setStatus("In Inventory");
            inv.setModifiedBy(userSession.getFullname());
            inv.setFlag("0");
            InventoryDAO invD = new InventoryDAO();
            QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

            //update request table
            Request req2 = new Request();
            req2.setStatus("In Inventory");
            req2.setFlag("0");
            req2.setModifiedBy(userSession.getFullname());
            req2.setId(ret.getReqId());
            RequestDAO reqD2 = new RequestDAO();
            QueryResult reqQ = reqD2.updateRequestStatusAndFlag(req2);

            //update log
            Log log = new Log();
            log.setRequestId(reqId);
            log.setDetail("Inventoried. Closed");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            List<String> emails = new ArrayList<String>();
            emails.add(ret.getRequestorEmail()); // add email requestor to the list

            String requestorId1 = userSession.getLoginId();
            UserDAO userDao1 = new UserDAO();
            List<LDAPUser> userRecipientsList1 = userDao1.getSREmailRetrieveList(requestorId1);

            for (int i = 0; i < userRecipientsList1.size(); i++) {
                emails.add(userRecipientsList1.get(i).getEmail());
            }

            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    //                    to, //to
                    emailTo,
                    "Box Request for Retrieval - Re-inventory (Closed)", //subject
                    "<br />"
                    + "Pls be informed that below request has been inventoried and closed."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + ret.getRequestorName()
                    + "<br /> "
                    + "Inventory Date: " + formattedString + " (By: " + userSession.getFullname() + ")"
                    + "<br /> "
                    + "Returnable: " + ret.getReturnable()
                    + "<br /> "
                    + "Remarks: " + ret.getReqRemarks()
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/inventory \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(reqId)
                    + "</table>"
                    + "<br />Thank you." //msg
            );

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/sr/retrieve/";
    }

    @RequestMapping(value = "/cancelReturn/{id}", method = RequestMethod.GET)
    public String cancelReturn(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id
    ) {

        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve ret = retrieveDAO.getRetrieveWithAllDetail(id);

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(ret.getReqId());

        Retrieve retrieve = new Retrieve();
        retrieve.setId(id);
        retrieve.setStatus("Closed (Cancel Return)");
        retrieve.setFlag("1");
        retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.updateRetrieveStatusAndFlag(retrieve);
        args = new String[1];
        args[0] = req.getRmsLotEvent();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update status for inventory table
            Inventory inv = new Inventory();
            inv.setId(req.getInvId());
            inv.setStatus("Closed");
            inv.setModifiedBy(userSession.getFullname());
            inv.setFlag("1");
            InventoryDAO invD = new InventoryDAO();
            QueryResult invQ2 = invD.updateInventoryStatusAndFlag(inv);

            InventoryMgtDAO invmD = new InventoryMgtDAO();
            InventoryMgt invMgt = invmD.getInventoryMgtByShelf(req.getShelf());

            invmD = new InventoryMgtDAO();
            int countShelfId = invmD.getCountShelfIdInInventoryTable(invMgt.getId());

            InventoryMgt invm = new InventoryMgt();
            invm.setId(invMgt.getId());
            if (countShelfId > 0) {
                invm.setStatus("Occupied");
                invm.setFlag("1");
            } else {
                invm.setStatus("Shelf Available");
                invm.setFlag("0");
            }
            invmD = new InventoryMgtDAO();
            QueryResult invQ = invmD.updateInventoryMgtAfterRequest(invm);

            //update request table
            Request req2 = new Request();
            req2.setStatus("Closed");
            req2.setFlag("1");
            req2.setModifiedBy(userSession.getFullname());
            req2.setId(req.getId());
            RequestDAO reqD2 = new RequestDAO();
            QueryResult reqQ = reqD2.updateRequestStatusAndFlag(req2);

            //update log
            Log log = new Log();
            log.setRequestId(req.getId());
            log.setDetail("Closed (Cancel Return)");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            List<String> emails = new ArrayList<String>();
            emails.add(ret.getRequestorEmail()); // add email requestor to the list

            String requestorId1 = userSession.getLoginId();
            UserDAO userDao1 = new UserDAO();
            List<LDAPUser> userRecipientsList1 = userDao1.getSREmailRetrieveList(requestorId1);

            for (int i = 0; i < userRecipientsList1.size(); i++) {
                emails.add(userRecipientsList1.get(i).getEmail());
            }

            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            //get current date and time
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
            String formattedString = formatter.format(instance); //15-02-2022 12:43

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    //                    to, //to
                    emailTo,
                    "Box Request for Retrieval - Closed (Cancel Return)", //subject
                    "<br />"
                    + "Pls be informed that below request has been closed (Cancel Return)."
                    + "<br /> "
                    + "<br /> "
                    + "Requestor: " + ret.getRequestorName()
                    + "<br /> "
                    + "Cancellation Date: " + formattedString + " (By: " + userSession.getFullname() + ")"
                    + "<br /> "
                    + "Returnable: " + ret.getReturnable()
                    + "<br /> "
                    + "Remarks: " + ret.getReqRemarks()
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/OSTORMS/sr/retrieve \">HERE</a> for further information."
                    + "<br /> "
                    + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr bgcolor=\"#F2F2F2\">"
                    + "<th>No.</th> "
                    + "<th>RMSLotEvent</th> "
                    + "<th>Package Family</th>"
                    + "<th>Package Name</th>"
                    + "<th>Location</th>"
                    + "</tr>"
                    + table(ret.getReqId())
                    + "</table>"
                    + "<br />Thank you." //msg
            );

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/sr/retrieve/";
    }

    @RequestMapping(value = "/delete/{retrieveId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("retrieveId") String retrieveId
    ) {
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve retrieve = retrieveDAO.getRetrieve(retrieveId);
        retrieveDAO = new RetrieveDAO();
        QueryResult queryResult = retrieveDAO.deleteRetrieve(retrieveId);
        args = new String[1];
        args[0] = retrieve.getReqId() + " - " + retrieve.getBoxId();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/sr/retrieve";
    }

    @RequestMapping(value = "/view/{retrieveId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("retrieveId") String retrieveId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/sr/retrieve/viewRetrievePdf/" + retrieveId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/retrieve";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.retrieve");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewRetrievePdf/{retrieveId}", method = RequestMethod.GET)
    public ModelAndView viewRetrievePdf(
            Model model,
            @PathVariable("retrieveId") String retrieveId
    ) {
        RetrieveDAO retrieveDAO = new RetrieveDAO();
        Retrieve retrieve = retrieveDAO.getRetrieve(retrieveId);
        return new ModelAndView("retrievePdf", "retrieve", retrieve);
    }

    private String table(String reqId) {

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(reqId);

        String text = "";

        text = text + "<tr align = \"center\">";
        text = text + "<td>1</td>";
        text = text + "<td>" + req.getRmsLotEvent() + "</td>";
        text = text + "<td>" + req.getPkgFamily() + "</td>"; //rackID
        text = text + "<td>" + req.getPkgName() + "</td>"; //shelfID
        text = text + "<td>" + req.getShelf() + "</td>"; //inventoryDate
        text = text + "</tr>";

        return text;
    }

    private String tableBulk(String bulkId) {

        BulkRetrieveDetailDAO bulkDetailD = new BulkRetrieveDetailDAO();
        List<BulkRetrieveDetail> bulkDetailList = bulkDetailD.getBulkRetrieveDetailListWithOtherTable(bulkId);

        String text = "";

        for (int i = 0; i < bulkDetailList.size(); i++) {

            int no = i + 1;
            text = text + "<tr align = \"center\">";
            text = text + "<td>" + no + "</td>";
            text = text + "<td>" + bulkDetailList.get(i).getRmsLotEvent() + "</td>";
            text = text + "<td>" + bulkDetailList.get(i).getPkgFamily() + "</td>";
            text = text + "<td>" + bulkDetailList.get(i).getPkgName() + "</td>";
            text = text + "<td>" + bulkDetailList.get(i).getLocation() + "</td>";
            text = text + "<td>" + bulkDetailList.get(i).getReturnable() + "</td>";
            text = text + "<td>" + bulkDetailList.get(i).getRemarks() + "</td>";
            text = text + "</tr>";
        }
        return text;
    }
}
