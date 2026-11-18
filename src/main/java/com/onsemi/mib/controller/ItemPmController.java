package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.ItemPmDAO;
import com.onsemi.mib.model.ItemPm;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.QueryResult;
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
@RequestMapping(value = "/itempm")
@SessionAttributes({"userSession"})
public class ItemPmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPmController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String itempm(
            Model model
    ) {
        ItemPmDAO itempmDAO = new ItemPmDAO();
        List<ItemPm> itempmList = itempmDAO.getItemPmList();
        model.addAttribute("itempmList", itempmList);
        return "itempm/itempm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "itempm/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String activity,
            @RequestParam(required = false) String activityDurationMin,
            @RequestParam(required = false) String activityIntervalDay,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String estimateNextDate,
            @RequestParam(required = false) String contactPerson,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag
    ) {
        ItemPm itempm = new ItemPm();
        itempm.setItemId(itemId);
        itempm.setActivityType(activityType);
        itempm.setActivity(activity);
        itempm.setActivityDurationMin(activityDurationMin);
        itempm.setActivityIntervalDay(activityIntervalDay);
        itempm.setStartDate(startDate);
        itempm.setEndDate(endDate);
        itempm.setEstimateNextDate(estimateNextDate);
        itempm.setContactPerson(contactPerson);
        itempm.setStatus(status);
        itempm.setCreatedBy(createdBy);
        itempm.setCreatedDate(createdDate);
        itempm.setFlag(flag);
        ItemPmDAO itempmDAO = new ItemPmDAO();
        QueryResult queryResult = itempmDAO.insertItemPm(itempm);
        args = new String[1];
        args[0] = itemId + " - " + activityType;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("itempm", itempm);
            return "itempm/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/itempm/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{itempmId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("itempmId") String itempmId
    ) {
        ItemPmDAO itempmDAO = new ItemPmDAO();
        ItemPm itempm = itempmDAO.getItemPm(itempmId);
        model.addAttribute("itempm", itempm);
        return "itempm/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String activity,
            @RequestParam(required = false) String activityDurationMin,
            @RequestParam(required = false) String activityIntervalDay,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String estimateNextDate,
            @RequestParam(required = false) String contactPerson,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag
    ) {
        ItemPm itempm = new ItemPm();
        itempm.setId(id);
        itempm.setItemId(itemId);
        itempm.setActivityType(activityType);
        itempm.setActivity(activity);
        itempm.setActivityDurationMin(activityDurationMin);
        itempm.setActivityIntervalDay(activityIntervalDay);
        itempm.setStartDate(startDate);
        itempm.setEndDate(endDate);
        itempm.setEstimateNextDate(estimateNextDate);
        itempm.setContactPerson(contactPerson);
        itempm.setStatus(status);
        itempm.setCreatedBy(createdBy);
        itempm.setCreatedDate(createdDate);
        itempm.setFlag(flag);
        ItemPmDAO itempmDAO = new ItemPmDAO();
        QueryResult queryResult = itempmDAO.updateItemPm(itempm);
        args = new String[1];
        args[0] = itemId + " - " + activityType;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/itempm/edit/" + id;
    }

    @RequestMapping(value = "/delete/{itempmId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("itempmId") String itempmId
    ) {
        ItemPmDAO itempmDAO = new ItemPmDAO();
        ItemPm itempm = itempmDAO.getItemPm(itempmId);
        itempmDAO = new ItemPmDAO();
        QueryResult queryResult = itempmDAO.deleteItemPm(itempmId);
        args = new String[1];
        args[0] = itempm.getItemId() + " - " + itempm.getActivityType();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/itempm";
    }

    @RequestMapping(value = "/view/{itempmId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("itempmId") String itempmId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/itempm/viewItemPmPdf/" + itempmId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/itempm";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.itempm");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewItemPmPdf/{itempmId}", method = RequestMethod.GET)
    public ModelAndView viewItemPmPdf(
            Model model,
            @PathVariable("itempmId") String itempmId
    ) {
        ItemPmDAO itempmDAO = new ItemPmDAO();
        ItemPm itempm = itempmDAO.getItemPm(itempmId);
        return new ModelAndView("itempmPdf", "itempm", itempm);
    }
}
