package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.EquipmentMonitoringDAO;
import com.onsemi.mib.model.EquipmentMonitoring;
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
@RequestMapping(value = "/equipmentmonitoring")
@SessionAttributes({"userSession"})public class EquipmentMonitoringController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentMonitoringController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String equipmentmonitoring(
			Model model
	) {
		EquipmentMonitoringDAO equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		List<EquipmentMonitoring> equipmentmonitoringList = equipmentmonitoringDAO.getEquipmentMonitoringList();
		model.addAttribute("equipmentmonitoringList", equipmentmonitoringList);
		return "equipmentmonitoring/equipmentmonitoring";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "equipmentmonitoring/add";
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
		EquipmentMonitoring equipmentmonitoring = new EquipmentMonitoring();
		equipmentmonitoring.setSptsPkid(sptsPkid);
		equipmentmonitoring.setName(name);
		EquipmentMonitoringDAO equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		QueryResult queryResult = equipmentmonitoringDAO.insertEquipmentMonitoring(equipmentmonitoring);
		args = new String[1];
		args[0] = sptsPkid + " - " + name;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("equipmentmonitoring", equipmentmonitoring);
			return "equipmentmonitoring/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/equipmentmonitoring/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{equipmentmonitoringId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("equipmentmonitoringId") String equipmentmonitoringId
	) {
		EquipmentMonitoringDAO equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		EquipmentMonitoring equipmentmonitoring = equipmentmonitoringDAO.getEquipmentMonitoring(equipmentmonitoringId);
		model.addAttribute("equipmentmonitoring", equipmentmonitoring);
		return "equipmentmonitoring/edit";
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
		EquipmentMonitoring equipmentmonitoring = new EquipmentMonitoring();
		equipmentmonitoring.setId(id);
		equipmentmonitoring.setSptsPkid(sptsPkid);
		equipmentmonitoring.setName(name);
		EquipmentMonitoringDAO equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		QueryResult queryResult = equipmentmonitoringDAO.updateEquipmentMonitoring(equipmentmonitoring);
		args = new String[1];
		args[0] = sptsPkid + " - " + name;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/equipmentmonitoring/edit/" + id;
	}

	@RequestMapping(value = "/delete/{equipmentmonitoringId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("equipmentmonitoringId") String equipmentmonitoringId
	) {
		EquipmentMonitoringDAO equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		EquipmentMonitoring equipmentmonitoring = equipmentmonitoringDAO.getEquipmentMonitoring(equipmentmonitoringId);
		equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		QueryResult queryResult = equipmentmonitoringDAO.deleteEquipmentMonitoring(equipmentmonitoringId);
		args = new String[1];
		args[0] = equipmentmonitoring.getSptsPkid() + " - " + equipmentmonitoring.getName();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/equipmentmonitoring";
	}

	@RequestMapping(value = "/view/{equipmentmonitoringId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("equipmentmonitoringId") String equipmentmonitoringId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/equipmentmonitoring/viewEquipmentMonitoringPdf/" + equipmentmonitoringId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/equipmentmonitoring";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.equipmentmonitoring");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewEquipmentMonitoringPdf/{equipmentmonitoringId}", method = RequestMethod.GET)
	public ModelAndView viewEquipmentMonitoringPdf(
			Model model, 
			@PathVariable("equipmentmonitoringId") String equipmentmonitoringId
	) {
		EquipmentMonitoringDAO equipmentmonitoringDAO = new EquipmentMonitoringDAO();
		EquipmentMonitoring equipmentmonitoring = equipmentmonitoringDAO.getEquipmentMonitoring(equipmentmonitoringId);
		return new ModelAndView("equipmentmonitoringPdf", "equipmentmonitoring", equipmentmonitoring);
	}
}