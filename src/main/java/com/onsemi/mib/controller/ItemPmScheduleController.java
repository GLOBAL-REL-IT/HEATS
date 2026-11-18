package com.onsemi.mib.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.ItemPmScheduleDAO;
import com.onsemi.mib.model.ItemPmSchedule;
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
@RequestMapping(value = "/itempmSchedule")
@SessionAttributes({"userSession"})public class ItemPmScheduleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemPmScheduleController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String itempmSchedule(
			Model model
	) {
		ItemPmScheduleDAO itempmScheduleDAO = new ItemPmScheduleDAO();
		List<ItemPmSchedule> itempmScheduleList = itempmScheduleDAO.getItemPmScheduleList();
		model.addAttribute("itempmScheduleList", itempmScheduleList);
		return "itempmSchedule/itempmSchedule";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "itempmSchedule/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String pmId,
			@RequestParam(required = false) String activityType,
			@RequestParam(required = false) String activity,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String actualStartDate,
			@RequestParam(required = false) String actualEndDate,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String doneBy,
			@RequestParam(required = false) String doneDate,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String estimateNextDate,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String flag
	) {
		ItemPmSchedule itempmSchedule = new ItemPmSchedule();
		itempmSchedule.setPmId(pmId);
		itempmSchedule.setActivityType(activityType);
		itempmSchedule.setActivity(activity);
		itempmSchedule.setStartDate(startDate);
		itempmSchedule.setEndDate(endDate);
		itempmSchedule.setActualStartDate(actualStartDate);
		itempmSchedule.setActualEndDate(actualEndDate);
		itempmSchedule.setRemarks(remarks);
		itempmSchedule.setDoneBy(doneBy);
		itempmSchedule.setDoneDate(doneDate);
		itempmSchedule.setStatus(status);
		itempmSchedule.setCreatedBy(createdBy);
		itempmSchedule.setEstimateNextDate(estimateNextDate);
		itempmSchedule.setCreatedDate(createdDate);
		itempmSchedule.setFlag(flag);
		ItemPmScheduleDAO itempmScheduleDAO = new ItemPmScheduleDAO();
		QueryResult queryResult = itempmScheduleDAO.insertItemPmSchedule(itempmSchedule);
		args = new String[1];
		args[0] = pmId + " - " + activityType;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("itempmSchedule", itempmSchedule);
			return "itempmSchedule/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/itempmSchedule/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{itempmScheduleId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("itempmScheduleId") String itempmScheduleId
	) {
		ItemPmScheduleDAO itempmScheduleDAO = new ItemPmScheduleDAO();
		ItemPmSchedule itempmSchedule = itempmScheduleDAO.getItemPmSchedule(itempmScheduleId);
		model.addAttribute("itempmSchedule", itempmSchedule);
		return "itempmSchedule/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String pmId,
			@RequestParam(required = false) String activityType,
			@RequestParam(required = false) String activity,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String actualStartDate,
			@RequestParam(required = false) String actualEndDate,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String doneBy,
			@RequestParam(required = false) String doneDate,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String estimateNextDate,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String flag
	) {
		ItemPmSchedule itempmSchedule = new ItemPmSchedule();
		itempmSchedule.setId(id);
		itempmSchedule.setPmId(pmId);
		itempmSchedule.setActivityType(activityType);
		itempmSchedule.setActivity(activity);
		itempmSchedule.setStartDate(startDate);
		itempmSchedule.setEndDate(endDate);
		itempmSchedule.setActualStartDate(actualStartDate);
		itempmSchedule.setActualEndDate(actualEndDate);
		itempmSchedule.setRemarks(remarks);
		itempmSchedule.setDoneBy(doneBy);
		itempmSchedule.setDoneDate(doneDate);
		itempmSchedule.setStatus(status);
		itempmSchedule.setCreatedBy(createdBy);
		itempmSchedule.setEstimateNextDate(estimateNextDate);
		itempmSchedule.setCreatedDate(createdDate);
		itempmSchedule.setFlag(flag);
		ItemPmScheduleDAO itempmScheduleDAO = new ItemPmScheduleDAO();
		QueryResult queryResult = itempmScheduleDAO.updateItemPmSchedule(itempmSchedule);
		args = new String[1];
		args[0] = pmId + " - " + activityType;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/itempmSchedule/edit/" + id;
	}

	@RequestMapping(value = "/delete/{itempmScheduleId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("itempmScheduleId") String itempmScheduleId
	) {
		ItemPmScheduleDAO itempmScheduleDAO = new ItemPmScheduleDAO();
		ItemPmSchedule itempmSchedule = itempmScheduleDAO.getItemPmSchedule(itempmScheduleId);
		itempmScheduleDAO = new ItemPmScheduleDAO();
		QueryResult queryResult = itempmScheduleDAO.deleteItemPmSchedule(itempmScheduleId);
		args = new String[1];
		args[0] = itempmSchedule.getPmId() + " - " + itempmSchedule.getActivityType();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/itempmSchedule";
	}

	@RequestMapping(value = "/view/{itempmScheduleId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("itempmScheduleId") String itempmScheduleId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/itempmSchedule/viewItemPmSchedulePdf/" + itempmScheduleId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/itempmSchedule";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.itempmSchedule");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewItemPmSchedulePdf/{itempmScheduleId}", method = RequestMethod.GET)
	public ModelAndView viewItemPmSchedulePdf(
			Model model, 
			@PathVariable("itempmScheduleId") String itempmScheduleId
	) {
		ItemPmScheduleDAO itempmScheduleDAO = new ItemPmScheduleDAO();
		ItemPmSchedule itempmSchedule = itempmScheduleDAO.getItemPmSchedule(itempmScheduleId);
		return new ModelAndView("itempmSchedulePdf", "itempmSchedule", itempmSchedule);
	}
}