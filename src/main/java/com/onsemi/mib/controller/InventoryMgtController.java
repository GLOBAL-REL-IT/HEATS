package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.InventoryMgtDAO;
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.QueryResult;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(value = "/sr/inventoryMgt")
@SessionAttributes({"userSession"})
public class InventoryMgtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryMgtController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String inventoryMgt(
            Model model
    ) {
        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        List<InventoryMgt> inventoryMgtList = inventoryMgtDAO.getInventoryMgtWithInventoryAndRequestAndFtpTable();
        model.addAttribute("inventoryMgtList", inventoryMgtList);
        return "inventoryMgt/inventoryMgt";
    }

    @RequestMapping(value = "/add/{rack}", method = RequestMethod.GET)
    public String add(Model model,
            @PathVariable("rack") String rack,
            @ModelAttribute UserSession userSession) {

        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        List<InventoryMgt> inventoryMgtList = inventoryMgtDAO.getInventoryMgtWithInventoryAndRequestAndFtpTableByRack(rack);
        model.addAttribute("inventoryMgtList", inventoryMgtList);

        return "inventoryMgt/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String stress,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String cabinet,
            @RequestParam(required = false) String qty
    ) {

        String cabinetPad = StringUtils.leftPad(cabinet, 2, "0");
        
        String stress2 = "";
        if ("Stress".equals(stress)) {
            stress2 = "S";
        } else {
            stress2 = "US";
        }
        String rack = stress2 + "-" + month + cabinetPad;
        String shelf = "";
        Integer total = 0;
        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        String seqcount = inventoryMgtDAO.getMaxShelfNumber(rack);
        if (!"0".equals(seqcount)) {
            seqcount = seqcount.substring(seqcount.length() - 4);
//            LOGGER.info("seqcount: " + seqcount);
        } else {
//            LOGGER.info("seqcount0: " + seqcount);
        }
        int seqcountInt = Integer.parseInt(seqcount);
        int qtyInt = Integer.parseInt(qty);

        for (int i = 0; i < qtyInt; i++) {

            seqcountInt = seqcountInt + 1;
            String counts = Integer.toString(seqcountInt);
            shelf = rack + "-" + StringUtils.leftPad(counts, 4, "0");

            inventoryMgtDAO = new InventoryMgtDAO();
            Integer count = inventoryMgtDAO.getCountShelfId(shelf);
            if (count == 0) {
                InventoryMgt inventoryMgt = new InventoryMgt();
                inventoryMgt.setRack(rack);
                inventoryMgt.setShelf(shelf);
                inventoryMgt.setStatus("Shelf Available");
                inventoryMgt.setFlag("0");
                inventoryMgtDAO = new InventoryMgtDAO();
                QueryResult queryResult = inventoryMgtDAO.insertInventoryMgt(inventoryMgt);

                total = total + 1;
            }
        }
        args = new String[1];
        args[0] = rack;
        if (total == 0) {
            model.addAttribute("error", "Failed to generate shelf ID for " + rack);
            return "redirect:/sr/inventoryMgt/add/" + rack;
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/sr/inventoryMgt/add/" + rack;
        }
    }

    @RequestMapping(value = "/edit/{inventoryMgtId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("inventoryMgtId") String inventoryMgtId
    ) {
        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        InventoryMgt inventoryMgt = inventoryMgtDAO.getInventoryMgt(inventoryMgtId);
        model.addAttribute("inventoryMgt", inventoryMgt);
        return "inventoryMgt/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String rackMonth,
            @RequestParam(required = false) String rack,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String dateCreated
    ) {
        InventoryMgt inventoryMgt = new InventoryMgt();
        inventoryMgt.setId(id);
        inventoryMgt.setReqId(reqId);
        inventoryMgt.setRackMonth(rackMonth);
        inventoryMgt.setRack(rack);
        inventoryMgt.setShelf(shelf);
        inventoryMgt.setStatus(status);
        inventoryMgt.setFlag(flag);
        inventoryMgt.setModifiedDate(modifiedDate);
        inventoryMgt.setDateCreated(dateCreated);
        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        QueryResult queryResult = inventoryMgtDAO.updateInventoryMgt(inventoryMgt);
        args = new String[1];
        args[0] = reqId + " - " + rackMonth;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/inventoryMgt/edit/" + id;
    }

    @RequestMapping(value = "/delete/{inventoryMgtId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("inventoryMgtId") String inventoryMgtId
    ) {
        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        InventoryMgt inventoryMgt = inventoryMgtDAO.getInventoryMgt(inventoryMgtId);
        inventoryMgtDAO = new InventoryMgtDAO();
        QueryResult queryResult = inventoryMgtDAO.deleteInventoryMgt(inventoryMgtId);
        args = new String[1];
        args[0] = inventoryMgt.getReqId() + " - " + inventoryMgt.getRackMonth();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/inventoryMgt";
    }

    @RequestMapping(value = "/view/{inventoryMgtId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("inventoryMgtId") String inventoryMgtId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/inventoryMgt/viewInventoryMgtPdf/" + inventoryMgtId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/inventoryMgt";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.inventoryMgt");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewInventoryMgtPdf/{inventoryMgtId}", method = RequestMethod.GET)
    public ModelAndView viewInventoryMgtPdf(
            Model model,
            @PathVariable("inventoryMgtId") String inventoryMgtId
    ) {
        InventoryMgtDAO inventoryMgtDAO = new InventoryMgtDAO();
        InventoryMgt inventoryMgt = inventoryMgtDAO.getInventoryMgt(inventoryMgtId);
        return new ModelAndView("inventoryMgtPdf", "inventoryMgt", inventoryMgt);
    }
}
