package com.onsemi.mib.controller;

import com.onsemi.mib.dao.FTPDao;
import com.onsemi.mib.dao.InventoryDAO;
import com.onsemi.mib.dao.InventoryMgtDAO;
import com.onsemi.mib.dao.LogDAO;
import com.onsemi.mib.dao.RequestDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.ScrapDAO;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.Inventory;
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.model.Log;
import com.onsemi.mib.model.Request;
import com.onsemi.mib.model.Scrap;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.QueryResult;
import java.time.LocalTime;
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
@RequestMapping(value = "/sr/scrap")
@SessionAttributes({"userSession"})
public class ScrapController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String scrap(Model model) {

        ScrapDAO scrapDAO = new ScrapDAO();
        List<Scrap> scrapList = scrapDAO.getScrapList();
        model.addAttribute("scrapList", scrapList);
        return "scrap/scrap";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "scrap/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String boxId,
            @RequestParam(required = false) String monthScrap,
            @RequestParam(required = false) String scrapBy,
            @RequestParam(required = false) String scrapDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag) {

        Scrap scrap = new Scrap();
        scrap.setRequestId(requestId);
        scrap.setBoxId(boxId);
        scrap.setMonthScrap(monthScrap);
        scrap.setScrapBy(scrapBy);
        scrap.setScrapDate(scrapDate);
        scrap.setStatus(status);
        scrap.setCreatedBy(createdBy);
        scrap.setCreatedDate(createdDate);
        scrap.setFlag(flag);
        ScrapDAO scrapDAO = new ScrapDAO();
        QueryResult queryResult = scrapDAO.insertScrap(scrap);
        args = new String[1];
        args[0] = requestId + " - " + boxId;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("scrap", scrap);
            return "scrap/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/sr/scrap/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{scrapId}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("scrapId") String scrapId) {

        ScrapDAO scrapDAO = new ScrapDAO();
        Scrap scrap = scrapDAO.getScrap(scrapId);
        model.addAttribute("scrap", scrap);
        return "scrap/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String boxId,
            @RequestParam(required = false) String monthScrap,
            @RequestParam(required = false) String scrapBy,
            @RequestParam(required = false) String scrapDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag) {

        Scrap scrap = new Scrap();
        scrap.setId(id);
        scrap.setRequestId(requestId);
        scrap.setBoxId(boxId);
        scrap.setMonthScrap(monthScrap);
        scrap.setScrapBy(scrapBy);
        scrap.setScrapDate(scrapDate);
        scrap.setStatus(status);
        scrap.setCreatedBy(createdBy);
        scrap.setCreatedDate(createdDate);
        scrap.setFlag(flag);
        ScrapDAO scrapDAO = new ScrapDAO();
        QueryResult queryResult = scrapDAO.updateScrap(scrap);
        args = new String[1];
        args[0] = requestId + " - " + boxId;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/sr/scrap/edit/" + id;
    }

    @RequestMapping(value = "/delete/{scrapId}", method = RequestMethod.GET)
    public String delete(Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("scrapId") String scrapId) {

        ScrapDAO scrapDAO = new ScrapDAO();
        Scrap scrap = scrapDAO.getScrap(scrapId);
        scrapDAO = new ScrapDAO();
        QueryResult queryResult = scrapDAO.deleteScrap(scrapId);
        args = new String[1];
        args[0] = scrap.getRequestId() + " - " + scrap.getBoxId();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/sr/scrap";
    }

    @RequestMapping(value = "/view/{scrapId}", method = RequestMethod.GET)
    public String view(Model model,
            HttpServletRequest request,
            @PathVariable("scrapId") String scrapId) throws UnsupportedEncodingException {

        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/sr/scrap/viewScrapPdf/" + scrapId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/sr/scrap";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.scrap");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewScrapPdf/{scrapId}", method = RequestMethod.GET)
    public ModelAndView viewScrapPdf(Model model, @PathVariable("scrapId") String scrapId) {

        ScrapDAO scrapDAO = new ScrapDAO();
        Scrap scrap = scrapDAO.getScrap(scrapId);
        return new ModelAndView("scrapPdf", "scrap", scrap);
    }

    @RequestMapping(value = "/pendingList", method = RequestMethod.GET)
    public String srScrapPendingList(Model model, @ModelAttribute UserSession userSession) {

        ScrapDAO scrapDao = new ScrapDAO();
        List<Scrap> scrapList = scrapDao.getPendingScrapList();
        String groupId = userSession.getGroup();

        model.addAttribute("userSession", userSession);
        model.addAttribute("srScrapList", scrapList);
        model.addAttribute("groupId", groupId);
        return "srScrap/pending_list";
    }

    @RequestMapping(value = "/register_scrap", method = RequestMethod.GET)
    public String registerScrap(Model model, @ModelAttribute UserSession userSession) {

        ScrapDAO scrapDao = new ScrapDAO();
        List<Scrap> scrapList = scrapDao.getReadyScrapList();
        String groupId = userSession.getGroup();

        ScrapDAO scrapDa = new ScrapDAO();
        Integer count = scrapDa.getCountReadyForScrap();

        model.addAttribute("userSession", userSession);
        model.addAttribute("srScrapList", scrapList);
        model.addAttribute("groupId", groupId);
        model.addAttribute("count", count);
        return "srScrap/register_scrap";
    }

    @RequestMapping(value = "/revertScrapStatus/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String revertScrapStatus(Model model,
            Locale locale,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id,
            @ModelAttribute UserSession userSession) {

        ScrapDAO scrapD = new ScrapDAO();
        Scrap scrap = scrapD.getScrapById(id);

        //update scrap table
        scrapD = new ScrapDAO();
        QueryResult queryResult = scrapD.revertScrap(id);

        args = new String[1];
        args[0] = "";
        if (queryResult.getResult() == 1) {

            //update request table
            Request req = new Request();
            req.setId(scrap.getRequestId());
            req.setFlag("0");
            req.setStatus("Pending Scrap");
            req.setModifiedBy(userSession.getFullname());
            RequestDAO reqD = new RequestDAO();
            QueryResult reqQ = reqD.updateRequestStatusAndFlag(req);

            //update inventory table
            Inventory inv = new Inventory();
            inv.setId(scrap.getInvId());
            inv.setFlag("0");
            inv.setStatus("Pending Scrap");
            inv.setModifiedBy(userSession.getFullname());
            InventoryDAO invD = new InventoryDAO();
            QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

            //update ftp table
            FTPdata ftp = new FTPdata();
            ftp.setId(scrap.getFtpId());
            ftp.setFlag("0");
            ftp.setStatus("Pending Scrap");
            ftp.setModifiedBy(userSession.getFullname());
            FTPDao fptD = new FTPDao();
            QueryResult ftpQ = fptD.updateStatusAndFlagbyFtpId(ftp);

            //insert into log table
            Log log = new Log();
            log.setRequestId(scrap.getRequestId());
            log.setDetail("Pending Scrap [Reverted]");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult logQ = logD.insertLog(log);

            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("You have successfully removed these scrap data.", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("We were unable to remove the scrap data. Please try again.", args, locale));
        }
        return "redirect:/sr/scrap/register_scrap";
    }

    @RequestMapping(value = "/readyScrap", method = {RequestMethod.GET, RequestMethod.POST})
    public String readyScrapStatus(Model model,
            Locale locale,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String rmsEvent,
            @ModelAttribute UserSession userSession) {

        ScrapDAO scrapDa = new ScrapDAO();
        Integer count = scrapDa.getCountPendingScrap(rmsEvent);

        if (count == 1) { //check if rmslotevent is Ready for Scrap
            ScrapDAO scrapD = new ScrapDAO();
            Scrap scrap = scrapD.getScrapbyRmsLotEvent(rmsEvent);

            //update scrap table
            ScrapDAO scrap2 = new ScrapDAO();
            QueryResult q1 = scrap2.updateReadyScrap(scrap.getId());

            args = new String[1];
            args[0] = "";
            if (q1.getResult() == 1) {

                //update request table
                Request req = new Request();
                req.setId(scrap.getRequestId());
                req.setFlag("0");
                req.setStatus("Ready for Scrap");
                req.setModifiedBy(userSession.getFullname());
                RequestDAO reqD = new RequestDAO();
                QueryResult reqQ = reqD.updateRequestStatusAndFlag(req);

                //update inventory table
                Inventory inv = new Inventory();
                inv.setId(scrap.getInvId());
                inv.setFlag("0");
                inv.setStatus("Ready for Scrap");
                inv.setModifiedBy(userSession.getFullname());
                InventoryDAO invD = new InventoryDAO();
                QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

                //update ftp table
                FTPdata ftp = new FTPdata();
                ftp.setId(scrap.getFtpId());
                ftp.setFlag("0");
                ftp.setStatus("Ready for Scrap");
                ftp.setModifiedBy(userSession.getFullname());
                FTPDao fptD = new FTPDao();
                QueryResult ftpQ = fptD.updateStatusAndFlagbyFtpId(ftp);

                //insert into log table
                Log log = new Log();
                log.setRequestId(scrap.getRequestId());
                log.setDetail("Ready for Scrap");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult logQ = logD.insertLog(log);

                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("Scanned data has been flagged for scrap. Review the list below.", args, locale));
            } else {
                String data = "Failed to scan RMS Event " + rmsEvent + " for scrap";
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage(data, args, locale));
            }
            return "redirect:/sr/scrap/register_scrap";
        } else {
            String data = "Failed to scan RMS Event " + rmsEvent + " for scrap";
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage(data, args, locale));
            return "redirect:/sr/scrap/register_scrap";
        }

    }

    @RequestMapping(value = "/scrap_all", method = {RequestMethod.GET, RequestMethod.POST})
    public String scrapAll(Model model,
            Locale locale,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession) {

        ScrapDAO scrapDao = new ScrapDAO();
        List<Scrap> scrapList = scrapDao.getReadyScrapList();

        Scrap scrap = new Scrap();
        scrap.setFlag("1");
        scrap.setStatus("Scrapped");
        scrap.setScrapBy(userSession.getFullname());
        ScrapDAO scrapD = new ScrapDAO();
        QueryResult q1 = scrapD.scrapAll(scrap);

        LOGGER.info("LOGGER FOR :: " + q1.getResult());

        args = new String[1];
        args[0] = "";
        if (q1.getResult() >= 1) {

            for (int i = 0; i < scrapList.size(); i++) {

                //update request table
                Request req = new Request();
                req.setId(scrapList.get(i).getRequestId());
                req.setFlag("1");
                req.setStatus("Scrapped");
                req.setModifiedBy(userSession.getFullname());
                RequestDAO reqD = new RequestDAO();
                QueryResult reqQ = reqD.updateRequestStatusAndFlag(req);

                //update inventory table
                Inventory inv = new Inventory();
                inv.setId(scrapList.get(i).getInvId());
                inv.setFlag("1");
                inv.setStatus("Scrapped");
                inv.setModifiedBy(userSession.getFullname());
                InventoryDAO invD = new InventoryDAO();
                QueryResult invQ = invD.updateInventoryStatusAndFlag(inv);

                //update ftp table
                FTPdata ftp = new FTPdata();
                ftp.setId(scrapList.get(i).getFtpId());
                ftp.setFlag("1");
                ftp.setStatus("Scrapped");
                ftp.setModifiedBy(userSession.getFullname());
                FTPDao fptD = new FTPDao();
                QueryResult ftpQ = fptD.updateStatusAndFlagbyFtpId(ftp);

                //insert into log table
                Log log = new Log();
                log.setRequestId(scrapList.get(i).getRequestId());
                log.setDetail("Scrapped");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult logQ = logD.insertLog(log);

                //update shelf mgmt table
//                InventoryMgtDAO invmD = new InventoryMgtDAO();
//                InventoryMgt invMgt = invmD.getInventoryMgtByReqId(scrapList.get(i).getRequestId());
                InventoryDAO invD2 = new InventoryDAO();
                Inventory inv2 = invD2.getInventory(scrapList.get(i).getInvId());

                InventoryMgtDAO invMgtD = new InventoryMgtDAO();
                int countShelfId = invMgtD.getCountShelfIdInInventoryTable(inv2.getShelfId());

                InventoryMgt invm = new InventoryMgt();
                invm.setId(inv2.getShelfId());
                if (countShelfId > 0) {
                    invm.setStatus("Occupied");
                    invm.setFlag("1");
                } else {
                    invm.setStatus("Shelf Available");
                    invm.setFlag("0");
                }
                InventoryMgtDAO invmD = new InventoryMgtDAO();
                QueryResult invMgmt = invmD.updateInventoryMgtAfterRequest(invm);

            }

            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("All selected data has been successfully scrapped.", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Failed to scrap all data, please try again", args, locale));
        }
        return "redirect:/sr/scrap/register_scrap";
    }

}