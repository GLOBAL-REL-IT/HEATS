package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.EquipmentTechDAO;
import com.onsemi.mib.model.EquipmentTech;
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
@RequestMapping(value = "/equipmenttech")
@SessionAttributes({"userSession"})public class EquipmentTechController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentTechController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String equipmenttech(
			Model model
	) {
		EquipmentTechDAO equipmenttechDAO = new EquipmentTechDAO();
		List<EquipmentTech> equipmenttechList = equipmenttechDAO.getEquipmentTechList();
		model.addAttribute("equipmenttechList", equipmenttechList);
		return "equipmenttech/equipmenttech";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "equipmenttech/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String sptsPkid,
			@RequestParam(required = false) String name
	) {
		EquipmentTech equipmenttech = new EquipmentTech();
		equipmenttech.setSptsPkid(sptsPkid);
		equipmenttech.setName(name);
		EquipmentTechDAO equipmenttechDAO = new EquipmentTechDAO();
		QueryResult queryResult = equipmenttechDAO.insertEquipmentTech(equipmenttech);
		args = new String[1];
		args[0] = sptsPkid + " - " + name;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("equipmenttech", equipmenttech);
			return "equipmenttech/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/equipmenttech/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{equipmenttechId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("equipmenttechId") String equipmenttechId
	) {
		EquipmentTechDAO equipmenttechDAO = new EquipmentTechDAO();
		EquipmentTech equipmenttech = equipmenttechDAO.getEquipmentTech(equipmenttechId);
		model.addAttribute("equipmenttech", equipmenttech);
		return "equipmenttech/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String sptsPkid,
			@RequestParam(required = false) String name
	) {
		EquipmentTech equipmenttech = new EquipmentTech();
		equipmenttech.setId(id);
		equipmenttech.setSptsPkid(sptsPkid);
		equipmenttech.setName(name);
		EquipmentTechDAO equipmenttechDAO = new EquipmentTechDAO();
		QueryResult queryResult = equipmenttechDAO.updateEquipmentTech(equipmenttech);
		args = new String[1];
		args[0] = sptsPkid + " - " + name;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/equipmenttech/edit/" + id;
	}

	@RequestMapping(value = "/delete/{equipmenttechId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("equipmenttechId") String equipmenttechId
	) {
		EquipmentTechDAO equipmenttechDAO = new EquipmentTechDAO();
		EquipmentTech equipmenttech = equipmenttechDAO.getEquipmentTech(equipmenttechId);
		equipmenttechDAO = new EquipmentTechDAO();
		QueryResult queryResult = equipmenttechDAO.deleteEquipmentTech(equipmenttechId);
		args = new String[1];
		args[0] = equipmenttech.getSptsPkid() + " - " + equipmenttech.getName();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/equipmenttech";
	}

	@RequestMapping(value = "/view/{equipmenttechId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("equipmenttechId") String equipmenttechId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/equipmenttech/viewEquipmentTechPdf/" + equipmenttechId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/equipmenttech";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.equipmenttech");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewEquipmentTechPdf/{equipmenttechId}", method = RequestMethod.GET)
	public ModelAndView viewEquipmentTechPdf(
			Model model, 
			@PathVariable("equipmenttechId") String equipmenttechId
	) {
		EquipmentTechDAO equipmenttechDAO = new EquipmentTechDAO();
		EquipmentTech equipmenttech = equipmenttechDAO.getEquipmentTech(equipmenttechId);
		return new ModelAndView("equipmenttechPdf", "equipmenttech", equipmenttech);
	}
}