package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.EquipmentRelTestGroupDAO;
import com.onsemi.mib.model.EquipmentRelTestGroup;
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
@RequestMapping(value = "/equipmentrelTestGroup")
@SessionAttributes({"userSession"})public class EquipmentRelTestGroupController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentRelTestGroupController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String equipmentrelTestGroup(
			Model model
	) {
		EquipmentRelTestGroupDAO equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		List<EquipmentRelTestGroup> equipmentrelTestGroupList = equipmentrelTestGroupDAO.getEquipmentRelTestGroupList();
		model.addAttribute("equipmentrelTestGroupList", equipmentrelTestGroupList);
		return "equipmentrelTestGroup/equipmentrelTestGroup";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "equipmentrelTestGroup/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String sptsPkid,
			@RequestParam(required = false) String relTestGroupName
	) {
		EquipmentRelTestGroup equipmentrelTestGroup = new EquipmentRelTestGroup();
		equipmentrelTestGroup.setSptsPkid(sptsPkid);
		equipmentrelTestGroup.setRelTestGroupName(relTestGroupName);
		EquipmentRelTestGroupDAO equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		QueryResult queryResult = equipmentrelTestGroupDAO.insertEquipmentRelTestGroup(equipmentrelTestGroup);
		args = new String[1];
		args[0] = sptsPkid + " - " + relTestGroupName;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("equipmentrelTestGroup", equipmentrelTestGroup);
			return "equipmentrelTestGroup/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/equipmentrelTestGroup/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{equipmentrelTestGroupId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("equipmentrelTestGroupId") String equipmentrelTestGroupId
	) {
		EquipmentRelTestGroupDAO equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		EquipmentRelTestGroup equipmentrelTestGroup = equipmentrelTestGroupDAO.getEquipmentRelTestGroup(equipmentrelTestGroupId);
		model.addAttribute("equipmentrelTestGroup", equipmentrelTestGroup);
		return "equipmentrelTestGroup/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String sptsPkid,
			@RequestParam(required = false) String relTestGroupName
	) {
		EquipmentRelTestGroup equipmentrelTestGroup = new EquipmentRelTestGroup();
		equipmentrelTestGroup.setId(id);
		equipmentrelTestGroup.setSptsPkid(sptsPkid);
		equipmentrelTestGroup.setRelTestGroupName(relTestGroupName);
		EquipmentRelTestGroupDAO equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		QueryResult queryResult = equipmentrelTestGroupDAO.updateEquipmentRelTestGroup(equipmentrelTestGroup);
		args = new String[1];
		args[0] = sptsPkid + " - " + relTestGroupName;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/equipmentrelTestGroup/edit/" + id;
	}

	@RequestMapping(value = "/delete/{equipmentrelTestGroupId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("equipmentrelTestGroupId") String equipmentrelTestGroupId
	) {
		EquipmentRelTestGroupDAO equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		EquipmentRelTestGroup equipmentrelTestGroup = equipmentrelTestGroupDAO.getEquipmentRelTestGroup(equipmentrelTestGroupId);
		equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		QueryResult queryResult = equipmentrelTestGroupDAO.deleteEquipmentRelTestGroup(equipmentrelTestGroupId);
		args = new String[1];
		args[0] = equipmentrelTestGroup.getSptsPkid() + " - " + equipmentrelTestGroup.getRelTestGroupName();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/equipmentrelTestGroup";
	}

	@RequestMapping(value = "/view/{equipmentrelTestGroupId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("equipmentrelTestGroupId") String equipmentrelTestGroupId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/equipmentrelTestGroup/viewEquipmentRelTestGroupPdf/" + equipmentrelTestGroupId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/equipmentrelTestGroup";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.equipmentrelTestGroup");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewEquipmentRelTestGroupPdf/{equipmentrelTestGroupId}", method = RequestMethod.GET)
	public ModelAndView viewEquipmentRelTestGroupPdf(
			Model model, 
			@PathVariable("equipmentrelTestGroupId") String equipmentrelTestGroupId
	) {
		EquipmentRelTestGroupDAO equipmentrelTestGroupDAO = new EquipmentRelTestGroupDAO();
		EquipmentRelTestGroup equipmentrelTestGroup = equipmentrelTestGroupDAO.getEquipmentRelTestGroup(equipmentrelTestGroupId);
		return new ModelAndView("equipmentrelTestGroupPdf", "equipmentrelTestGroup", equipmentrelTestGroup);
	}
}