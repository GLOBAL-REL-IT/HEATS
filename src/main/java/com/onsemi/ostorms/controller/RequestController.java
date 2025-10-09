package com.onsemi.ostorms.controller;

import com.onsemi.ostorms.dao.FTPDao;
import com.onsemi.ostorms.dao.InventoryDAO;
import com.onsemi.ostorms.dao.InventoryMgtDAO;
import com.onsemi.ostorms.dao.LogDAO;
import com.onsemi.ostorms.dao.LogFtpDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.ostorms.dao.RequestDAO;
import com.onsemi.ostorms.model.FTPdata;
import com.onsemi.ostorms.model.Inventory;
import com.onsemi.ostorms.model.InventoryMgt;
import com.onsemi.ostorms.model.Log;
import com.onsemi.ostorms.model.LogFtp;
import com.onsemi.ostorms.model.Request;
import com.onsemi.ostorms.model.UserSession;
import com.onsemi.ostorms.tools.QueryResult;
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
@RequestMapping(value = "/sr/request")
@SessionAttributes({"userSession"})
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String request(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
//        RequestDAO requestDAO = new RequestDAO();
//        List<Request> requestList = requestDAO.getRequestListJoinWithFtpAndInventory();
//        model.addAttribute("userSession", userSession.getFullname());;
        return "request/request";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rmsLotEvent
    ) {

        FTPDao ftpD = new FTPDao();
        Integer countRmsEvent = ftpD.getCountRMSLotEventWithFlagZero(rmsLotEvent);

        if (countRmsEvent == 1) {
            return "redirect:/sr/request/add/" + rmsLotEvent;
        } else {
            redirectAttrs.addFlashAttribute("error", rmsLotEvent + " is not available for inventory.");
            return "redirect:/sr/request/";
        }

    }

    @RequestMapping(value = "/add/{rmslotevent}", method = RequestMethod.GET)
    public String add(Model model,
            @PathVariable("rmslotevent") String rmslotevent) {

        FTPDao ftpD = new FTPDao();
        FTPdata ftp = ftpD.getFtpDataPerRmsLotEventFlagZero(rmslotevent);
        model.addAttribute("ftp", ftp);

        return "request/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String ftpId,
            @RequestParam(required = false) String rmsLotEvent,
            @RequestParam(required = false) String finalQty,
            @RequestParam(required = false) String requestBy,
            @RequestParam(required = false) String requestDate,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String checkboxPS,
            @RequestParam(required = false) String stressTypeMidPoint
    ) {

        //check inventory if available or not
        InventoryDAO invD = new InventoryDAO();
        Integer count = invD.getCountAvailableShelf(shelf);

//        LOGGER.info("checkboxPS: " + checkboxPS);
        //1 location can have multiple location 1 July 2025
        if (count == 1) { //proceed if shelf available
            RequestDAO reqD = new RequestDAO();
            Integer countFtp = reqD.getCountFTPIdWithFlagZero(ftpId);
            if (countFtp > 0) { //check if FTP ID with flag zero available in req table or not

                redirectAttrs.addFlashAttribute("error", "RMSLotEvent : " + rmsLotEvent + " already in Inventory.");
                return "redirect:/sr/request/add/" + rmsLotEvent;

            } else {
                Request request = new Request();
                request.setFtpId(ftpId);
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
                    return "request/add";
                } else {

                    //update inventoryMgt
                    InventoryMgtDAO invmD = new InventoryMgtDAO();
                    InventoryMgt inv = invmD.getInventoryMgtByShelf(shelf);

//                    String rmsLotEventConcat = "";
//
//                    if (inv.getRmsLotEvent() != null && inv.getRmsLotEvent().length() > 5) {
//                        rmsLotEventConcat = inv.getRmsLotEvent() + "," + rmsLotEvent;
//                    } else {
//                        rmsLotEventConcat = rmsLotEvent;
//                    }
                    InventoryMgt invm = new InventoryMgt();
                    invm.setId(inv.getId());
//                    invm.setRmsLotEvent(rmsLotEventConcat); // 1 shelf can have multiple rmsLotEvent
//                    invm.setReqId(queryResult.getGeneratedKey()); // replace with rmsLotEvent
                    invm.setStatus("Occupied");
                    invm.setFlag("1");
                    invmD = new InventoryMgtDAO();
                    QueryResult invQ = invmD.updateInventoryMgtAfterRequest(invm);

                    FTPDao ftpD = new FTPDao();
                    FTPdata ftpdata = ftpD.getFtpDataById(ftpId);

                    //insert into inventory table
                    Inventory inventory = new Inventory();
                    inventory.setReqId(queryResult.getGeneratedKey());
                    inventory.setMthToScrap(ftpdata.getMthToScrap());
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

                    //save actual qty to ftp table
                    FTPdata ftp = new FTPdata();
                    ftp.setActualQty(finalQty);
                    ftp.setStatus("In Inventory");
                    ftp.setFlag("1");
                    ftp.setId(ftpId);
                    ftpD = new FTPDao();
                    QueryResult qFtp = ftpD.updateActualQtyFlagAndStatus(ftp);

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
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Shelf : " + shelf + " is not available. Pls assign with a different shelf ID");
            return "redirect:/sr/request/add/" + rmsLotEvent;
        }
    }

    @RequestMapping(value = "/view/{reqId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("reqId") String reqId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/sr/request/viewBarcodeStickerPdf/" + reqId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Barcode Sticker");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewBarcodeStickerPdf/{reqId}", method = RequestMethod.GET)
    public ModelAndView viewWhBarcodeStickerPdf(
            Model model,
            @PathVariable("reqId") String reqId
    ) {

        RequestDAO reqD = new RequestDAO();
        Request request = reqD.getRequestWithFtpAndInventory(reqId);

        return new ModelAndView("barcodeStickerPdf", "request", request);
    }

    //new record - not in FTP table
    @RequestMapping(value = "/addNew", method = RequestMethod.GET)
    public String addNew(Model model,
            @ModelAttribute UserSession userSession) {

        return "srFtp/add";
    }

    @RequestMapping(value = "/saveNew", method = RequestMethod.POST)
    public String saveNew(
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

//        LOGGER.info("checkboxPS: " + checkboxPS);
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

            LogFtp logFtp = new LogFtp();
            logFtp.setFtpId(qu.getGeneratedKey());
            logFtp.setDetail("Added into OSTORMS Database (Manual)");
            logFtp.setCreatedBy(userSession.getFullname());
            LogFtpDAO logDFtp = new LogFtpDAO();
            QueryResult logQF = logDFtp.insertLogFtp(logFtp);

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

    @RequestMapping(value = "/addLocation//{reqId}", method = RequestMethod.GET)
    public String addLocation(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("reqId") String reqId
    ) {

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithFtpAndInventory(reqId);

        model.addAttribute("req", req);
        return "srFtp/addShelf";
    }

    @RequestMapping(value = "/saveLocation", method = RequestMethod.POST)
    public String saveLocation(
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

//        LOGGER.info("checkboxPS: " + checkboxPS);
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

            LogFtp logFtp = new LogFtp();
            logFtp.setFtpId(qu.getGeneratedKey());
            logFtp.setDetail("Added into OSTORMS Database (Manual)");
            logFtp.setCreatedBy(userSession.getFullname());
            LogFtpDAO logDFtp = new LogFtpDAO();
            QueryResult logQF = logDFtp.insertLogFtp(logFtp);

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

}
