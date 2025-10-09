package com.onsemi.mib.controller;

import com.onsemi.mib.dao.FTPDao;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.InventoryDAO;
import com.onsemi.mib.dao.InventoryMgtDAO;
import com.onsemi.mib.dao.LogDAO;
import com.onsemi.mib.dao.RequestDAO;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.Inventory;
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.model.Log;
import com.onsemi.mib.model.Request;
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
@RequestMapping(value = "/sr/inventory")
@SessionAttributes({"userSession"})
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String inventory(
            @ModelAttribute UserSession userSession,
            Model model
    ) {

        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
//        LOGGER.info("groupId = " + groupId);
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventoryList = inventoryDAO.getInventoryList();
        model.addAttribute("inventoryList", inventoryList);
        return "inventory/inventory";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "inventory/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String boxId,
            @RequestParam(required = false) String mthToScrap,
            @RequestParam(required = false) String inventoryRack,
            @RequestParam(required = false) String inventoryShelf,
            @RequestParam(required = false) String inventoryBy,
            @RequestParam(required = false) String inventoryDate,
            @RequestParam(required = false) String inventoryRemarks,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String createdBy
    ) {
        Inventory inventory = new Inventory();
        inventory.setReqId(reqId);
//        inventory.setBoxId(boxId);
        inventory.setMthToScrap(mthToScrap);
        inventory.setInventoryRack(inventoryRack);
        inventory.setInventoryShelf(inventoryShelf);
        inventory.setInventoryBy(inventoryBy);
        inventory.setInventoryDate(inventoryDate);
        inventory.setInventoryRemarks(inventoryRemarks);
        inventory.setStatus(status);
        inventory.setFlag(flag);
        inventory.setModifiedDate(modifiedDate);
        inventory.setModifiedBy(modifiedBy);
        inventory.setCreatedDate(createdDate);
        inventory.setCreatedBy(createdBy);
        InventoryDAO inventoryDAO = new InventoryDAO();
        QueryResult queryResult = inventoryDAO.insertInventory(inventory);
        args = new String[1];
        args[0] = reqId + " - " + boxId;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("inventory", inventory);
            return "inventory/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/sr/inventory/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{reqId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("reqId") String reqId
    ) {
//        InventoryDAO inventoryDAO = new InventoryDAO();
//        Inventory inventory = inventoryDAO.getInventory(inventoryId);
        RequestDAO reqD = new RequestDAO();
        Request request = reqD.getRequestWithFtpAndInventory(reqId);

        model.addAttribute("request", request);
        return "inventory/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String invId,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String currentShelf
    ) {

        //check if same shelf id or not
        if (currentShelf.equals(shelf)) {
            //do nothing
            redirectAttrs.addFlashAttribute("error", "Same shelf ID as current location.");
            return "redirect:/sr/inventory/edit/" + reqId;
        } else {

            //get detail for old location
            InventoryMgtDAO invMgtD = new InventoryMgtDAO();
            InventoryMgt invM = invMgtD.getInventoryMgtByShelf(currentShelf);

            //get detail for new location
            invMgtD = new InventoryMgtDAO();
            InventoryMgt invM2 = invMgtD.getInventoryMgtByShelf(shelf);

            //check if shelf is available or not
            InventoryDAO invD = new InventoryDAO();
            Integer count = invD.getCountAvailableShelf(shelf);
            if (count == 1) { //proceed when shelf is available
                Inventory inventory = new Inventory();
                inventory.setId(invId);
                inventory.setReqId(reqId);
                inventory.setInventoryShelf(shelf);
                inventory.setInventoryBy(userSession.getFullname());
                inventory.setStatus("In Inventory");
                inventory.setFlag("0");
                inventory.setModifiedBy(userSession.getFullname());
                inventory.setShelfId(invM2.getId());
                InventoryDAO inventoryDAO = new InventoryDAO();
                QueryResult queryResult = inventoryDAO.updateInventoryLocation(inventory);

                //count old shelfId in inventory table
                invMgtD = new InventoryMgtDAO();
                int countShelfId = invMgtD.getCountShelfIdInInventoryTable(invM.getId());

                //update old location inventory mgmt
                InventoryMgt inv = new InventoryMgt();
                inv.setId(invM.getId());
                if (countShelfId > 0) {
                    inv.setStatus("Occupied");
                    inv.setFlag("1");
                } else {
                    inv.setStatus("Shelf Available");
                    inv.setFlag("0");
                }
                invMgtD = new InventoryMgtDAO();
                QueryResult queryResultInvMgt = invMgtD.updateInventoryMgtAfterRequest(inv);

                //update latest location inventory mgmt
                InventoryMgt invm = new InventoryMgt();
                invm.setId(invM2.getId());
                invm.setStatus("Occupied");
                invm.setFlag("1");
                invMgtD = new InventoryMgtDAO();
                QueryResult invQ = invMgtD.updateInventoryMgtAfterRequest(invm);

                //update log
                Log log = new Log();
                log.setRequestId(reqId);
                log.setDetail("Change Inventory Location");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult logQ = logD.insertLog(log);

                redirectAttrs.addFlashAttribute("success", "Change shelf location to : " + shelf);
//                return "redirect:/inventory";
                return "redirect:/sr/request/view/" + reqId;

            } else {
                redirectAttrs.addFlashAttribute("error", "Shelf : " + shelf + " is not available. Pls assign with a different shelf ID");
                return "redirect:/sr/inventory/edit/" + reqId;
            }

        }

    }

    @RequestMapping(value = "/delete/{inventoryId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("inventoryId") String inventoryId
    ) {
        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory inventory = inventoryDAO.getInventory(inventoryId);
        inventoryDAO = new InventoryDAO();
        QueryResult queryResult = inventoryDAO.deleteInventory(inventoryId);
        args = new String[1];
        args[0] = inventory.getReqId() + " - " + inventory.getInventoryRack();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/sr/inventory";
    }

    @RequestMapping(value = "/view/{inventoryId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("inventoryId") String inventoryId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/sr/inventory/viewInventoryPdf/" + inventoryId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/inventory";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.inventory");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewInventoryPdf/{inventoryId}", method = RequestMethod.GET)
    public ModelAndView viewInventoryPdf(
            Model model,
            @PathVariable("inventoryId") String inventoryId
    ) {
        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory inventory = inventoryDAO.getInventory(inventoryId);
        return new ModelAndView("inventoryPdf", "inventory", inventory);
    }
}
