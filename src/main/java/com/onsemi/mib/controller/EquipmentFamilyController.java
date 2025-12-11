package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.EquipmentFamilyDAO;
import com.onsemi.mib.model.EquipmentFamily;
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
@RequestMapping(value = "/equipmentfamily")
@SessionAttributes({"userSession"})
public class EquipmentFamilyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentFamilyController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String equipmentfamily(
            Model model
    ) {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        List<EquipmentFamily> equipmentfamilyList = equipmentfamilyDAO.getEquipmentFamilyList();
        model.addAttribute("equipmentfamilyList", equipmentfamilyList);
        return "equipmentfamily/equipmentfamily";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "equipmentfamily/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String sptsPkid,
            @RequestParam(required = false) String familyName
    ) {
        EquipmentFamily equipmentfamily = new EquipmentFamily();
        equipmentfamily.setSptsPkid(sptsPkid);
        equipmentfamily.setFamilyName(familyName);
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        QueryResult queryResult = equipmentfamilyDAO.insertEquipmentFamily(equipmentfamily);
        args = new String[1];
        args[0] = sptsPkid + " - " + familyName;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("equipmentfamily", equipmentfamily);
            return "equipmentfamily/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/equipmentfamily/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{equipmentfamilyId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        EquipmentFamily equipmentfamily = equipmentfamilyDAO.getEquipmentFamily(equipmentfamilyId);
        model.addAttribute("equipmentfamily", equipmentfamily);
        return "equipmentfamily/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String sptsPkid,
            @RequestParam(required = false) String familyName
    ) {
        EquipmentFamily equipmentfamily = new EquipmentFamily();
        equipmentfamily.setId(id);
        equipmentfamily.setSptsPkid(sptsPkid);
        equipmentfamily.setFamilyName(familyName);
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        QueryResult queryResult = equipmentfamilyDAO.updateEquipmentFamily(equipmentfamily);
        args = new String[1];
        args[0] = sptsPkid + " - " + familyName;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/equipmentfamily/edit/" + id;
    }

    @RequestMapping(value = "/delete/{equipmentfamilyId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        EquipmentFamily equipmentfamily = equipmentfamilyDAO.getEquipmentFamily(equipmentfamilyId);
        equipmentfamilyDAO = new EquipmentFamilyDAO();
        QueryResult queryResult = equipmentfamilyDAO.deleteEquipmentFamily(equipmentfamilyId);
        args = new String[1];
        args[0] = equipmentfamily.getSptsPkid() + " - " + equipmentfamily.getFamilyName();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/equipmentfamily";
    }

    @RequestMapping(value = "/view/{equipmentfamilyId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/equipmentfamily/viewEquipmentFamilyPdf/" + equipmentfamilyId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/equipmentfamily";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.equipmentfamily");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewEquipmentFamilyPdf/{equipmentfamilyId}", method = RequestMethod.GET)
    public ModelAndView viewEquipmentFamilyPdf(
            Model model,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        EquipmentFamily equipmentfamily = equipmentfamilyDAO.getEquipmentFamily(equipmentfamilyId);
        return new ModelAndView("equipmentfamilyPdf", "equipmentfamily", equipmentfamily);
    }
}
