/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsemi.mib.dao.EmailVmFailDAO;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.ItemActivityConfigDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemFunctionalTestDAO;
import com.onsemi.mib.dao.ItemHardwareDAO;
import com.onsemi.mib.dao.ItemLogDAO;
import com.onsemi.mib.dao.ItemMaverickDAO;
import com.onsemi.mib.dao.ItemScrapDAO;
import com.onsemi.mib.dao.ItemVisualInspectionDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingFunctionalTestDAO;
import com.onsemi.mib.dao.RmsBookingLogDAO;
import com.onsemi.mib.dao.RmsBookingMaverickDAO;
import com.onsemi.mib.dao.RmsBookingVisualInspectionDAO;
import com.onsemi.mib.model.EmailVmFail;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemActivityConfig;
import com.onsemi.mib.model.ItemFunctionalTest;
import com.onsemi.mib.model.ItemHardware;
import com.onsemi.mib.model.ItemLog;
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.model.ItemScrap;
import com.onsemi.mib.model.ItemVisualInspection;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingFunctionalTest;
import com.onsemi.mib.model.RmsBookingLog;
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.model.RmsBookingVisualInspection;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSResponse;
import com.onsemi.mib.tools.SPTSWebService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author zbqb9x
 */
@Controller
@RequestMapping("/maverick")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class MaverickController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaverickController.class);
    String[] args = {};

    private static final int BUFFER_SIZE = 4096;

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\HEATS\\VI-Attachment\\HW_Registration\\"; //server

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String maverickHome(Model model) {
        return "maverick/test";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String maverickList(Model model, @ModelAttribute UserSession userSession) {
        // FLAG 0 - New maverick data - DONE
        // FLAG 1 - Complete Maverick - DONE
        // FLAG 2 - Repair
        // FLAG 3 - Scrap
        // FLAG 4 - Bypass -> VM
        // FLAG 5 - FT
        RmsBookingMaverickDAO rbmdao = new RmsBookingMaverickDAO();
        List<RmsBookingMaverick> rbmList = rbmdao.getItemMaverickUnionRmsBookingMaverick();
        model.addAttribute("maverickList", rbmList);
        return "maverick/list";
    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String maverickTest(Model model, @ModelAttribute UserSession userSession) {
        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        List<RmsBookingDetail> booking = rmsD.getRmsBookingDetailListFlagZero();
        model.addAttribute("booking", booking);
        return "maverick/test";
    }

    @RequestMapping(value = "/maverickdetails/{id}/{module}/{id2}", method = RequestMethod.GET)
    public String maverickDetails(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable String id,
            @PathVariable String id2,
            @PathVariable String module) throws SQLException, ServletException, IOException {

        if (id == null || id2 == null || module == null) {
            return "redirect:/maintenance";
        }

        // HARDWARE REGISTRATION
        if (!module.equalsIgnoreCase("Hardware Registration")) {
            redirectAttrs.addFlashAttribute("error", "This record does not belong to Hardware Registration page, please contact HEATS admin");
            return "redirect:/maverick/list";
        }

        // FLAG 0 - New maverick data - DONE
        // FLAG 1 - Complete Maverick / Bypass - DONE
        // FLAG 2 - Repair
        // FLAG 3 - Scrap
        // FLAG 4 - Bypass -> VM
        // FLAG 5 - FT
//        String username = userSession.getFullname();
        ItemMaverick itemmav = new ItemMaverick();
        ItemMaverickDAO itemmavdao = new ItemMaverickDAO();
        itemmav = itemmavdao.getItemMaverickById(id);
        model.addAttribute("data", itemmav);
        model.addAttribute("mibItemId", id2);
        model.addAttribute("groupId", "0");
        model.addAttribute("id", id);

        String submodule = itemmav.getSubmodule();
        String returnPage = "maverick/registration";
        String jenis = "FT";

        model.addAttribute("leakCheck", "No");
        model.addAttribute("manCheck", "No");
        model.addAttribute("bibCheck", "No");
        model.addAttribute("daqCheck", "No");
        model.addAttribute("psCheck", "No");
        model.addAttribute("winCheck", "No");

        if (submodule.equals("Leakage Test")) {
            model.addAttribute("leakCheck", "Yes");
        } else if (submodule.contains("Manual Test")) {
            model.addAttribute("manCheck", "Yes");
        } else if (submodule.contains("BIB Test")) {
            model.addAttribute("bibCheck", "Yes");
        } else if (submodule.contains("BIB DAQ Test")) {
            model.addAttribute("daqCheck", "Yes");
        } else if (submodule.contains("Power Supply Leakage Test")) {
            model.addAttribute("psCheck", "Yes");
        } else if (submodule.contains("Winchester Chamber Leakage Test")) {
            model.addAttribute("winCheck", "Yes");
        } else if (submodule.contains("Visual Inspection")) {
            ItemVisualInspection itemVm = new ItemVisualInspection(); //declare new model to prevent null pointer exception

            ItemVisualInspectionDAO itemVmD = new ItemVisualInspectionDAO(); //check if already have VM data
            int count = itemVmD.getCountItemIdWithModuleItemRegistration(id2);

            if (count == 1) { // assigned itemVm model with data
                itemVmD = new ItemVisualInspectionDAO();
                itemVm = itemVmD.getItemVisualInspectionByMibItemIdWithModuleItemRegistration(id2);
            }

            ParameterDetailsDAO pD = new ParameterDetailsDAO();
            addRejectAttribute(model, pD, itemVm.getPcbReject(), "003", "pcbReject");
            addRejectAttribute(model, pD, itemVm.getHandleReject(), "004", "handleReject");
            addRejectAttribute(model, pD, itemVm.getMetalFrameReject(), "005", "metalFrameReject");
            addRejectAttribute(model, pD, itemVm.getHardwareFasternersReject(), "006", "hardwareFasternersReject");
            addRejectAttribute(model, pD, itemVm.getClipHolderReject(), "007", "clipHolderReject");
            addRejectAttribute(model, pD, itemVm.getPcbEdgeFingerReject(), "008", "pcbEdgeFingerReject");
            addRejectAttribute(model, pD, itemVm.getConnectorReject(), "009", "connectorReject");
            addRejectAttribute(model, pD, itemVm.getDutSocketsReject(), "010", "dutSocketsReject");
            addRejectAttribute(model, pD, itemVm.getEdgeMbBananaReject(), "011", "edgeMbBananaReject");
            addRejectAttribute(model, pD, itemVm.getElectComponentReject(), "012", "electComponentReject");
            addRejectAttribute(model, pD, itemVm.getSolderJointReject(), "014", "solderJointReject");
            addRejectAttribute(model, pD, itemVm.getWinConnectorReject(), "015", "winConnectorReject");
            addRejectAttribute(model, pD, itemVm.getTeflonConnectorReject(), "020", "teflonConnectorReject");
            addRejectAttribute(model, pD, itemVm.getPogoReceptaclesPinReject(), "021", "pogoReceptaclesPinReject");
            addRejectAttribute(model, pD, itemVm.getCableWiredCopperWireReject(), "022", "cableWiredCopperWireReject");
            addRejectAttribute(model, pD, itemVm.getLabelIdentificationReject(), "023", "labelIdentificationReject");
            model.addAttribute("itemVm", itemVm);
            jenis = "VM";
        }

        ItemFunctionalTestDAO itemdao2 = new ItemFunctionalTestDAO();
        ItemFunctionalTest itemdata2 = itemdao2.getItemActivityByItemId(id2);

        ParameterDetailsDAO pDx = new ParameterDetailsDAO();

        if (itemdata2 != null) {
            model.addAttribute("dataTest", itemdata2);
        }

        addParameterDetail(model, pDx, itemdata2 != null ? itemdata2.getBibStatus() : "", "016", "bibResultData");
        addParameterDetail(model, pDx, itemdata2 != null ? itemdata2.getBibDaqStatus() : "", "016", "daqResultData");
        addParameterDetail(model, pDx, itemdata2 != null ? itemdata2.getLeakStatus() : "", "016", "leakResultData");
        addParameterDetail(model, pDx, itemdata2 != null ? itemdata2.getPsStatus() : "", "016", "psResultData");
        addParameterDetail(model, pDx, itemdata2 != null ? itemdata2.getWinStatus() : "", "016", "winResultData");
        model.addAttribute("jenis", jenis);

        ItemLogDAO itemlogdao = new ItemLogDAO();
        List<ItemLog> itemlog = itemlogdao.getItemLogListByItemId(id2);
        model.addAttribute("itemlog", itemlog);

        return returnPage;
    }

    @RequestMapping(value = "/updateMaverick", method = RequestMethod.POST)
    public String maverickupdate(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String mavId,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String submodule,
            @RequestParam(required = false) String newDisposition,
            @RequestParam(required = false) String newDispositionRemark) {

        // FLAG 0 - New maverick data
        // FLAG 1 - Complete Maverick / Bypass
        // FLAG 2 - Repair
        // FLAG 3 - Scrap
        // FLAG 4 - Bypass -> VM
        // FLAG 5 - FT
        String returnPage = "redirect:/maverick/list";

        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = formatter.format(instance);
        String username = userSession.getFullname();

        ItemMaverickDAO imdao = new ItemMaverickDAO();
        ItemMaverick itemmaverick = imdao.getItemMaverick(mavId);

        String disposition1 = itemmaverick.getDisposition1();
        String disposition2 = itemmaverick.getDisposition2();

        // Update item_maverick table - ALL SCENARIO WILL UPDATE
        if (disposition2 == null || disposition2.trim().isEmpty()) {
            if (disposition1 == null || disposition1.trim().isEmpty()) {
                itemmaverick.setDisposition1(newDisposition);
                itemmaverick.setDisposition1By(username);
                itemmaverick.setDisposition1Date(formattedString);
                itemmaverick.setDispositionRemarks1(newDispositionRemark);
            } else {
                itemmaverick.setDisposition2(newDisposition);
                itemmaverick.setDisposition2By(username);
                itemmaverick.setDisposition2Date(formattedString);
                itemmaverick.setDisposition2Remarks(newDispositionRemark);
            }
        } else {
            // IF ALREADY HAS DATA, SUPPOSE CANNOT GET IN HERE
            redirectAttrs.addFlashAttribute("error", "Disposition already completed. This should not happen. Pls contact system admin. Maverick ID :: " + mavId);
        }

        if (newDisposition.equalsIgnoreCase("Repair")) {
            // KES REPAIR
            itemmaverick.setFlag("2");
            imdao = new ItemMaverickDAO();
            imdao.updateItemMaverick(itemmaverick);
            returnPage = "redirect:/maverick/list";
        } else if (newDisposition.equalsIgnoreCase("Scrap")) {
            // KES SCRAP
            itemmaverick.setFlag("3");
            imdao = new ItemMaverickDAO();
            imdao.updateItemMaverick(itemmaverick);

            ItemScrapDAO isdao = new ItemScrapDAO();
            ItemScrap scrap = new ItemScrap();

            scrap.setMibItemId(mibItemId);
            scrap.setGroupId(groupId);
            scrap.setModule(module);
            scrap.setSubModule(submodule);
            scrap.setStatus("New");
            scrap.setRemark("-");
            scrap.setCreatedBy(username);
            scrap.setFlag("0");

            isdao = new ItemScrapDAO();
            isdao.insertScrap(scrap);

            returnPage = "redirect:/maverick/list";
        } else if (newDisposition.equalsIgnoreCase("Bypass")) {
            // KES BYPASS
            itemmaverick.setFlag("1");
            imdao = new ItemMaverickDAO();
            imdao.updateItemMaverick(itemmaverick);
            
            
            
            returnPage = "redirect:/maverick/list";
        }

        return returnPage;
    }

    @RequestMapping(value = "/updateRepairStatus", method = RequestMethod.POST)
    public String processAction(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String mavId,
            @RequestParam(required = false) String status) {

        ItemMaverickDAO imdao = new ItemMaverickDAO();
        ItemMaverick itemmaverick = imdao.getItemMaverick(mavId);
        ItemMaverick itemmav = new ItemMaverick();

        itemmav.setItemId(itemmaverick.getItemId());
        itemmav.setModule(itemmaverick.getModule());
        itemmav.setSubmodule(itemmaverick.getSubmodule());
        itemmav.setDisposition1(itemmaverick.getDisposition1());
        itemmav.setDispositionRemarks1(itemmaverick.getDispositionRemarks1());
        itemmav.setDisposition1By(itemmaverick.getDisposition1By());
        itemmav.setDisposition1Date(itemmaverick.getDisposition1Date());
        itemmav.setDisposition2(itemmaverick.getDisposition2());
        itemmav.setDisposition2Remarks(itemmaverick.getDisposition2Remarks());
        itemmav.setDisposition2By(itemmaverick.getDisposition2By());
        itemmav.setDisposition2Date(itemmaverick.getDisposition2Date());
        itemmav.setStatus(itemmaverick.getStatus());
        itemmav.setCreatedBy(itemmaverick.getCreatedBy());
        itemmav.setCreatedDate(itemmaverick.getCreatedDate());
        itemmav.setId(mavId);

        if ("approve".equals(status)) {
            // Logic for approval - sample ONLY
        } else if ("reject".equals(status)) {
            // Logic for rejection - sample ONLY
        } else if ("failed".equals(status)) {
            // THIS IS WHERE REPAIR DOES NOT WORK
            // KITA PULANGKAN DIA JADI FLAG 0, BAGI DIA BUAT DISPOSITION LAGI SEKALI
            itemmav.setFlag("0");
        } else if ("success".equals(status)) {
            // REPAIR SUCCESS
            // KITA UPDATE FLAG JADI 1, SO, DIA BOLE PROCEED BUAT VM BALIK
            itemmav.setFlag("1");
            // sini kita nk kena buat new vm dengan functional test? so, kita akan update flag asal kepada?? 
        }

        imdao = new ItemMaverickDAO();
        imdao.updateItemMaverick(itemmav);

        return "redirect:/maverick/list";
    }

    @RequestMapping(value = "/maverickdetails/{id}/{module}/{bookid}/{pkid}", method = RequestMethod.GET)
    public String maverickDetails2(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable String id,
            @PathVariable String module,
            @PathVariable String bookid,
            @PathVariable String pkid) throws SQLException {

        if (id == null || bookid == null || pkid == null || module == null) {
            return "redirect:/maintenance";
        }

        // BEFORE & AFTER LOADING
        if (module.equalsIgnoreCase("Before Loading")) {

        } else if (module.equalsIgnoreCase("Unloading")) {

        } else {
            redirectAttrs.addFlashAttribute("error", "This record does not belong to Loading / Unloading page, please contact HEATS admin");
            return "redirect:/maverick/list";
        }

//        if (!module.contains("Loading")) {
//            redirectAttrs.addFlashAttribute("error", "This record does not belong to Loading / Unloading page, please contact HEATS admin");
//            return "redirect:/maverick/list";
//        }
        String returnPage = "maverick/loading";
        String jenis = "FT";
        String groupId = bookid + "/" + pkid;

        RmsBookingMaverick itemmav = new RmsBookingMaverick();
        RmsBookingMaverickDAO itemmavdao = new RmsBookingMaverickDAO();
        itemmav = itemmavdao.getRmsBookingMaverickByGroupId(groupId);

//        ItemMaverick itemmav = new ItemMaverick();
//        ItemMaverickDAO itemmavdao = new ItemMaverickDAO();
//        itemmav = itemmavdao.getItemMaverickById(id);
        String submodule = (itemmav.getSubmodule() == null) ? "" : itemmav.getSubmodule();

        model.addAttribute("data", itemmav);
        model.addAttribute("mibItemId", "0");
        model.addAttribute("groupId", groupId);
        model.addAttribute("id", id);
        model.addAttribute("leakCheck", "No");
        model.addAttribute("manCheck", "No");
        model.addAttribute("bibCheck", "No");
        model.addAttribute("daqCheck", "No");
        model.addAttribute("psCheck", "No");
        model.addAttribute("winCheck", "No");

        if (submodule.equals("Leakage Test")) {
            model.addAttribute("leakCheck", "Yes");
        } else if (submodule.contains("Manual Test")) {
            model.addAttribute("manCheck", "Yes");
        } else if (submodule.contains("BIB Test")) {
            model.addAttribute("bibCheck", "Yes");
        } else if (submodule.contains("BIB DAQ Test")) {
            model.addAttribute("daqCheck", "Yes");
        } else if (submodule.contains("Power Supply Leakage Test")) {
            model.addAttribute("psCheck", "Yes");
        } else if (submodule.contains("Winchester Chamber Leakage Test")) {
            model.addAttribute("winCheck", "Yes");
        } else if (submodule.contains("Visual Inspection")) {
//            ItemVisualInspection itemVm = new ItemVisualInspection(); //declare new model to prevent null pointer exception
//            ItemVisualInspectionDAO itemVmD = new ItemVisualInspectionDAO(); //check if already have VM data

            // CHANGE THIS FUNCTION TO READ FROM RMS_BOOKING_VISUAL_INSPECTION
            RmsBookingVisualInspection rmsvm = new RmsBookingVisualInspection();
            RmsBookingVisualInspectionDAO rmsdao = new RmsBookingVisualInspectionDAO();

//            int count2 = itemVmD.getCountItemIdWithModuleItemRegistration(groupId);
            int count = rmsdao.getCountByGroupIdWithModuleBeforeLoading(groupId);

            if (count == 1) { // assigned itemVm model with data
                // MACAM MANA KITA NK CATER UNTUK DATA BEFORE LOADING DENGAN UNLOADING????? 
//                if (module.equalsIgnoreCase("Before Loading")){
//                    
//                } else if (module.equalsIgnoreCase("Unloading")){
//                    
//                }
                rmsdao = new RmsBookingVisualInspectionDAO();
                // THIS ONE ALREADY CATER module = Before Loading / Unloading -
                // Before Loading has no issue, but unloading per SI - how to cater this one
                rmsvm = rmsdao.getRmsBookingVisualInspectionByGroupIdAndStatus(groupId, module);
            }

            ParameterDetailsDAO pD = new ParameterDetailsDAO();
            addRejectAttribute(model, pD, rmsvm.getPcbReject(), "003", "pcbReject");
            addRejectAttribute(model, pD, rmsvm.getHandleReject(), "004", "handleReject");
            addRejectAttribute(model, pD, rmsvm.getMetalFrameReject(), "005", "metalFrameReject");
            addRejectAttribute(model, pD, rmsvm.getHardwareFasternersReject(), "006", "hardwareFasternersReject");
            addRejectAttribute(model, pD, rmsvm.getClipHolderReject(), "007", "clipHolderReject");
            addRejectAttribute(model, pD, rmsvm.getPcbEdgeFingerReject(), "008", "pcbEdgeFingerReject");
            addRejectAttribute(model, pD, rmsvm.getConnectorReject(), "009", "connectorReject");
            addRejectAttribute(model, pD, rmsvm.getDutSocketsReject(), "010", "dutSocketsReject");
            addRejectAttribute(model, pD, rmsvm.getEdgeMbBananaReject(), "011", "edgeMbBananaReject");
            addRejectAttribute(model, pD, rmsvm.getElectComponentReject(), "012", "electComponentReject");
            addRejectAttribute(model, pD, rmsvm.getSolderJointReject(), "014", "solderJointReject");
            addRejectAttribute(model, pD, rmsvm.getWinConnectorReject(), "015", "winConnectorReject");
            addRejectAttribute(model, pD, rmsvm.getTeflonConnectorReject(), "020", "teflonConnectorReject");
            addRejectAttribute(model, pD, rmsvm.getPogoReceptaclesPinReject(), "021", "pogoReceptaclesPinReject");
            addRejectAttribute(model, pD, rmsvm.getCableWiredCopperWireReject(), "022", "cableWiredCopperWireReject");
            addRejectAttribute(model, pD, rmsvm.getLabelIdentificationReject(), "023", "labelIdentificationReject");
            model.addAttribute("itemVm", rmsvm);
            jenis = "VM";
        }

        // UPDATE THIS FUNCTION TO READ FROM RMS_BOOKING_FUNCTIONAL_TEST
        // Before Loading has no issue, but unloading per SI - how to cater this one
        RmsBookingFunctionalTestDAO rmsftdao = new RmsBookingFunctionalTestDAO();
        RmsBookingFunctionalTest rmsft = rmsftdao.getFuncTestResultByModule(groupId, module);

        ParameterDetailsDAO pDx = new ParameterDetailsDAO();

        if (rmsft != null) {
            model.addAttribute("dataTest", rmsft);
        }

        addParameterDetail(model, pDx, rmsft != null ? rmsft.getBibStatus() : "", "016", "bibResultData");
        addParameterDetail(model, pDx, rmsft != null ? rmsft.getBibDaqStatus() : "", "016", "daqResultData");
        addParameterDetail(model, pDx, rmsft != null ? rmsft.getLeakStatus() : "", "016", "leakResultData");
        addParameterDetail(model, pDx, rmsft != null ? rmsft.getPsStatus() : "", "016", "psResultData");
        addParameterDetail(model, pDx, rmsft != null ? rmsft.getWinStatus() : "", "016", "winResultData");
        model.addAttribute("jenis", jenis);

        // UPDATE THIS FUNCTION TO READ FROM RMS_BOOKING_LOG
        // KENA CREATE SATU FUNCTION UNTUK DAPATKAN id dari RMS_BOOKING DETAILS, BY booking_id, dapat dari GROUP_ID
        // THEN KENA CREATE SATU LAGI FUNCTION UNTUK DAPATKAN ALL LOG by booking_id
        RmsBookingLogDAO logdao = new RmsBookingLogDAO();
        List<RmsBookingLog> itemlog = logdao.getRmsBookingLogByBookingId(bookid);
        model.addAttribute("itemlog", itemlog);

        return returnPage;
    }

    @RequestMapping(value = "/updateMaverickLoad", method = RequestMethod.POST)
    public String maverickupdate02(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String mavId,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String submodule,
            @RequestParam(required = false) String newDisposition,
            @RequestParam(required = false) String newDispositionRemark) {

        // FLAG 0 - New maverick data
        // FLAG 1 - Complete Maverick / Bypass
        // FLAG 2 - Repair
        // FLAG 3 - Scrap
        // FLAG 4 - Bypass -> VM
        // FLAG 5 - FT
        String returnPage = "redirect:/maverick/list";

        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = formatter.format(instance);
        String username = userSession.getFullname();

        RmsBookingMaverickDAO rmsdao = new RmsBookingMaverickDAO();
        RmsBookingMaverick rmsmav = rmsdao.getRmsBookingMaverickById(mavId);

        String disposition1 = rmsmav.getDisposition1();
        String disposition2 = rmsmav.getDisposition2();

        // Update item_maverick table - ALL SCENARIO WILL UPDATE
        if (disposition2 == null || disposition2.trim().isEmpty()) {
            if (disposition1 == null || disposition1.trim().isEmpty()) {
                rmsmav.setDisposition1(newDisposition);
                rmsmav.setDisposition1By(username);
                rmsmav.setDisposition1Date(formattedString);
                rmsmav.setDispositionRemarks1(newDispositionRemark);
            } else {
                rmsmav.setDisposition2(newDisposition);
                rmsmav.setDisposition2By(username);
                rmsmav.setDisposition2Date(formattedString);
                rmsmav.setDisposition2Remarks(newDispositionRemark);
            }
        } else {
            // IF ALREADY HAS DATA, SUPPOSE CANNOT GET IN HERE
            redirectAttrs.addFlashAttribute("error", "Disposition already completed. This should not happen. Pls contact system admin. Maverick ID :: " + mavId);
        }

        if (newDisposition.equalsIgnoreCase("Repair")) {
            // KES REPAIR
            rmsmav.setFlag("2");
            rmsdao = new RmsBookingMaverickDAO();
            rmsdao.updateRmsBookingMaverick(rmsmav);
            returnPage = "redirect:/maverick/list";
        } else if (newDisposition.equalsIgnoreCase("Scrap")) {
            // KES SCRAP
            rmsmav.setFlag("3");
            rmsdao = new RmsBookingMaverickDAO();
            rmsdao.updateRmsBookingMaverick(rmsmav);

            ItemScrapDAO isdao = new ItemScrapDAO();
            ItemScrap scrap = new ItemScrap();

            scrap.setMibItemId(mibItemId);
            scrap.setGroupId(groupId);
            scrap.setModule(module);
            scrap.setSubModule(submodule);
            scrap.setStatus("New");
            scrap.setRemark("-");
            scrap.setCreatedBy(username);
            scrap.setFlag("0");

            isdao = new ItemScrapDAO();
            isdao.insertScrap(scrap);

            returnPage = "redirect:/maverick/list";
        } else if (newDisposition.equalsIgnoreCase("Bypass")) {
            // KES BYPASS
            rmsmav.setFlag("1");
            rmsdao = new RmsBookingMaverickDAO();
            rmsdao.updateRmsBookingMaverick(rmsmav);
            returnPage = "redirect:/maverick/list";
        }

        return returnPage;
    }

    @RequestMapping(value = "/updateRepairLoadStatus", method = RequestMethod.POST)
    public String processActionLoad(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String mavId,
            @RequestParam(required = false) String status) {

//        ItemMaverickDAO imdao = new ItemMaverickDAO();
//        ItemMaverick itemmaverick = imdao.getItemMaverick(mavId);
//        ItemMaverick itemmav = new ItemMaverick();
        RmsBookingMaverickDAO rmsdao = new RmsBookingMaverickDAO();
        RmsBookingMaverick rmsmav = rmsdao.getRmsBookingMaverickById(mavId);
        RmsBookingMaverick itemmav = new RmsBookingMaverick();

        itemmav.setItemId(rmsmav.getItemId());
        itemmav.setModule(rmsmav.getModule());
        itemmav.setSubmodule(rmsmav.getSubmodule());
        itemmav.setDisposition1(rmsmav.getDisposition1());
        itemmav.setDispositionRemarks1(rmsmav.getDispositionRemarks1());
        itemmav.setDisposition1By(rmsmav.getDisposition1By());
        itemmav.setDisposition1Date(rmsmav.getDisposition1Date());
        itemmav.setDisposition2(rmsmav.getDisposition2());
        itemmav.setDisposition2Remarks(rmsmav.getDisposition2Remarks());
        itemmav.setDisposition2By(rmsmav.getDisposition2By());
        itemmav.setDisposition2Date(rmsmav.getDisposition2Date());
        itemmav.setStatus(rmsmav.getStatus());
        itemmav.setCreatedBy(rmsmav.getCreatedBy());
        itemmav.setCreatedDate(rmsmav.getCreatedDate());
        itemmav.setId(mavId);

        if ("approve".equals(status)) {
            // Logic for approval - sample ONLY
        } else if ("reject".equals(status)) {
            // Logic for rejection - sample ONLY
        } else if ("failed".equals(status)) {
            // THIS IS WHERE REPAIR DOES NOT WORK
            // KITA PULANGKAN DIA JADI FLAG 0, BAGI DIA BUAT DISPOSITION LAGI SEKALI
            itemmav.setFlag("0");
        } else if ("success".equals(status)) {
            // REPAIR SUCCESS
            // KITA UPDATE FLAG JADI 1, SO, DIA BOLE PROCEED BUAT VM BALIK
            itemmav.setFlag("1");
            // sini kita nk kena buat new vm dengan functional test? so, kita akan update flag asal kepada?? 
        }

        rmsdao = new RmsBookingMaverickDAO();
        rmsdao.updateRmsBookingMaverick(itemmav);

//        imdao = new ItemMaverickDAO();
//        imdao.updateItemMaverick(itemmav);
        return "redirect:/maverick/list";
    }

    @RequestMapping(value = "/scrap/{id}", method = RequestMethod.GET)
    public String maverickScrap(@PathVariable String id) {
        return "maverick/scrap";
    }

    @RequestMapping(value = "/repair/{id}", method = RequestMethod.GET)
    public String maverickRepair(@PathVariable String id) {
        return "maverick/repair";
    }

    @RequestMapping(value = "/redo/{id}", method = RequestMethod.GET)
    public String maverickVMFT(Model model, @PathVariable String id) throws SQLException {
        ItemMaverick itemmaverick = new ItemMaverick();
        ItemMaverickDAO itemdao = new ItemMaverickDAO();
        itemmaverick = itemdao.getItemMaverickById(id);
        String mibItemId = itemmaverick.getMibItemId();
        model.addAttribute("itemmaverick", itemmaverick);

        // KENA BUAT CHECKING KALAU VISUAL_INSPECTION DATA DA CREATE BARU, IF NOT PLEASE CREATE
        // CHECK ALSO 
//        model.addAttribute("item", "");
//        model.addAttribute("itemVm", "");
//        return "maverick/maverick_registration";
        ItemDAO itemD = new ItemDAO();
        Item item = itemD.getHardwareDetail(mibItemId);
        ItemVisualInspection itemVm = new ItemVisualInspection(); //declare new model to prevent null pointer exception

        ItemVisualInspectionDAO itemVmD = new ItemVisualInspectionDAO(); //check if already have VM data
        int count = itemVmD.getCountItemIdWithModuleItemRegistration(mibItemId);

        if (count == 1) { // assigned itemVm model with data
            itemVmD = new ItemVisualInspectionDAO();
            itemVm = itemVmD.getItemVisualInspectionByMibItemIdWithModuleItemRegistration(mibItemId);
        }

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        addParameterDetail(model, pD, item.getItemUsage(), "001", "paramItemUsage");
        addParameterDetail(model, pD, itemVm.getPcbReject(), "003", "pcbReject");
        addParameterDetail(model, pD, itemVm.getHandleReject(), "004", "handleReject");
        addParameterDetail(model, pD, itemVm.getMetalFrameReject(), "005", "metalFrameReject");
        addParameterDetail(model, pD, itemVm.getHardwareFasternersReject(), "006", "hardwareFasternersReject");
        addParameterDetail(model, pD, itemVm.getClipHolderReject(), "007", "clipHolderReject");
        addParameterDetail(model, pD, itemVm.getPcbEdgeFingerReject(), "008", "pcbEdgeFingerReject");
        addParameterDetail(model, pD, itemVm.getConnectorReject(), "009", "connectorReject");
        addParameterDetail(model, pD, itemVm.getDutSocketsReject(), "010", "dutSocketsReject");
        addParameterDetail(model, pD, itemVm.getEdgeMbBananaReject(), "011", "edgeMbBananaReject");
        addParameterDetail(model, pD, itemVm.getElectComponentReject(), "012", "electComponentReject");
        addParameterDetail(model, pD, item.getItemUsage(), "013", "cableWireReject");
        addParameterDetail(model, pD, itemVm.getSolderJointReject(), "014", "solderJointReject");
        addParameterDetail(model, pD, itemVm.getWinConnectorReject(), "015", "winConnectorReject");
        addParameterDetail(model, pD, itemVm.getTeflonConnectorReject(), "020", "teflonConnectorReject");
        addParameterDetail(model, pD, itemVm.getPogoReceptaclesPinReject(), "021", "pogoReceptaclesPinReject");
        addParameterDetail(model, pD, itemVm.getCableWiredCopperWireReject(), "022", "cableWiredCopperWireReject");
        addParameterDetail(model, pD, itemVm.getLabelIdentificationReject(), "023", "labelIdentificationReject");

        return "maverick/maverick_vmft";
    }

    @RequestMapping(value = "/vm/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemVmSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String maverickId,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String itemStatus,
            @RequestParam(required = false) String pcb,
            @RequestParam(required = false) String pcbReject,
            @RequestParam(required = false) String handle,
            @RequestParam(required = false) String handleReject,
            @RequestParam(required = false) String metalFrame,
            @RequestParam(required = false) String metalFrameReject,
            @RequestParam(required = false) String hardwareFasterners,
            @RequestParam(required = false) String hardwareFasternersReject,
            @RequestParam(required = false) String clipHolder,
            @RequestParam(required = false) String clipHolderReject,
            @RequestParam(required = false) String pcbEdgeFinger,
            @RequestParam(required = false) String pcbEdgeFingerReject,
            @RequestParam(required = false) String connector,
            @RequestParam(required = false) String connectorReject,
            @RequestParam(required = false) String dutSockets,
            @RequestParam(required = false) String dutSocketsReject,
            @RequestParam(required = false) String edgeMbBanana,
            @RequestParam(required = false) String edgeMbBananaReject,
            @RequestParam(required = false) String electComponent,
            @RequestParam(required = false) String electComponentReject,
            @RequestParam(required = false) String solderJoint,
            @RequestParam(required = false) String solderJointReject,
            @RequestParam(required = false) String winConnector,
            @RequestParam(required = false) String winConnectorReject,
            @RequestParam(required = false) String teflonConnector,
            @RequestParam(required = false) String teflonConnectorReject,
            @RequestParam(required = false) String pogoReceptaclesPin,
            @RequestParam(required = false) String pogoReceptaclesPinReject,
            @RequestParam(required = false) String cableWiredCopperWire,
            @RequestParam(required = false) String cableWiredCopperWireReject,
            @RequestParam(required = false) String labelIdentification,
            @RequestParam(required = false) String labelIdentificationReject,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String pcbRejectQty,
            @RequestParam(required = false) MultipartFile pcbRejectUpload,
            @RequestParam(required = false) String handleRejectQty,
            @RequestParam(required = false) MultipartFile handleRejectUpload,
            @RequestParam(required = false) String metalFrameRejectQty,
            @RequestParam(required = false) MultipartFile metalFrameRejectUpload,
            @RequestParam(required = false) String hardwareFasternersRejectQty,
            @RequestParam(required = false) MultipartFile hardwareFasternersRejectUpload,
            @RequestParam(required = false) String clipHolderRejectQty,
            @RequestParam(required = false) MultipartFile clipHolderRejectUpload,
            @RequestParam(required = false) String pcbEdgeFingerRejectQty,
            @RequestParam(required = false) MultipartFile pcbEdgeFingerRejectUpload,
            @RequestParam(required = false) String connectorRejectQty,
            @RequestParam(required = false) MultipartFile connectorRejectUpload,
            @RequestParam(required = false) String dutSocketsRejectQty,
            @RequestParam(required = false) MultipartFile dutSocketsRejectUpload,
            @RequestParam(required = false) String edgeMbBananaRejectQty,
            @RequestParam(required = false) MultipartFile edgeMbBananaRejectUpload,
            @RequestParam(required = false) String electComponentRejectQty,
            @RequestParam(required = false) MultipartFile electComponentRejectUpload,
            @RequestParam(required = false) String solderJointRejectQty,
            @RequestParam(required = false) MultipartFile solderJointRejectUpload,
            @RequestParam(required = false) String winConnectorRejectQty,
            @RequestParam(required = false) MultipartFile winConnectorRejectUpload,
            @RequestParam(required = false) String teflonConnectorRejectQty,
            @RequestParam(required = false) MultipartFile teflonConnectorRejectUpload,
            @RequestParam(required = false) String pogoReceptaclesPinRejectQty,
            @RequestParam(required = false) MultipartFile pogoReceptaclesPinRejectUpload,
            @RequestParam(required = false) String cableWiredCopperWireRejectQty,
            @RequestParam(required = false) MultipartFile cableWiredCopperWireRejectUpload,
            @RequestParam(required = false) String labelIdentificationRejectQty,
            @RequestParam(required = false) MultipartFile labelIdentificationRejectUpload
    ) throws IOException, SQLException {

        LOGGER.info("KITA NK MASUK KE DALAM FUNCTION SAVE VM YANG BARU - SELEPAS MAVERICK");
        LOGGER.info("itemStatus                 ::: " + itemStatus);
        LOGGER.info("pcb                        ::: " + pcb);
        LOGGER.info("pcbReject                  ::: " + pcbReject);
        LOGGER.info("handle                     ::: " + handle);
        LOGGER.info("handleReject               ::: " + handleReject);
        LOGGER.info("metalFrame                 ::: " + metalFrame);
        LOGGER.info("metalFrameReject           ::: " + metalFrameReject);
        LOGGER.info("hardwareFasterners         ::: " + hardwareFasterners);
        LOGGER.info("hardwareFasternersReject   ::: " + hardwareFasternersReject);
        LOGGER.info("clipHolder                 ::: " + clipHolder);
        LOGGER.info("clipHolderReject           ::: " + clipHolderReject);
        LOGGER.info("pcbEdgeFinger              ::: " + pcbEdgeFinger);
        LOGGER.info("pcbEdgeFingerReject        ::: " + pcbEdgeFingerReject);
        LOGGER.info("connector                  ::: " + connector);
        LOGGER.info("connectorReject            ::: " + connectorReject);
        LOGGER.info("dutSockets                 ::: " + dutSockets);
        LOGGER.info("dutSocketsReject           ::: " + dutSocketsReject);
        LOGGER.info("edgeMbBanana               ::: " + edgeMbBanana);
        LOGGER.info("edgeMbBananaReject         ::: " + edgeMbBananaReject);
        LOGGER.info("electComponent             ::: " + electComponent);
        LOGGER.info("electComponentReject       ::: " + electComponentReject);
        LOGGER.info("solderJoint                ::: " + solderJoint);
        LOGGER.info("solderJointReject          ::: " + solderJointReject);
        LOGGER.info("winConnector               ::: " + winConnector);
        LOGGER.info("winConnectorReject         ::: " + winConnectorReject);
        LOGGER.info("teflonConnector            ::: " + teflonConnector);
        LOGGER.info("teflonConnectorReject      ::: " + teflonConnectorReject);
        LOGGER.info("pogoReceptaclesPin         ::: " + pogoReceptaclesPin);
        LOGGER.info("pogoReceptaclesPinReject   ::: " + pogoReceptaclesPinReject);
        LOGGER.info("cableWiredCopperWire       ::: " + cableWiredCopperWire);
        LOGGER.info("cableWiredCopperWireReject ::: " + cableWiredCopperWireReject);
        LOGGER.info("labelIdentification        ::: " + labelIdentification);
        LOGGER.info("labelIdentificationReject  ::: " + labelIdentificationReject);
        LOGGER.info("remarks                    ::: " + remarks);
        LOGGER.info("pcbRejectQty               ::: " + pcbRejectQty);
        LOGGER.info("pcbRejectUpload            ::: " + pcbRejectUpload);
        LOGGER.info("handleRejectQty            ::: " + handleRejectQty);
        LOGGER.info("handleRejectUpload         ::: " + handleRejectUpload);
        LOGGER.info("metalFrameRejectQty        ::: " + metalFrameRejectQty);
        LOGGER.info("metalFrameRejectUpload     ::: " + metalFrameRejectUpload);
        LOGGER.info("hardwareFasternersRejectQty    ::: " + hardwareFasternersRejectQty);
        LOGGER.info("hardwareFasternersRejectUpload ::: " + hardwareFasternersRejectUpload);
        LOGGER.info("clipHolderRejectQty            ::: " + clipHolderRejectQty);
        LOGGER.info("clipHolderRejectUpload         ::: " + clipHolderRejectUpload);
        LOGGER.info("pcbEdgeFingerRejectQty         ::: " + pcbEdgeFingerRejectQty);
        LOGGER.info("pcbEdgeFingerRejectUpload      ::: " + pcbEdgeFingerRejectUpload);
        LOGGER.info("connectorRejectQty             ::: " + connectorRejectQty);
        LOGGER.info("connectorRejectUpload          ::: " + connectorRejectUpload);
        LOGGER.info("dutSocketsRejectQty            ::: " + dutSocketsRejectQty);
        LOGGER.info("dutSocketsRejectUpload         ::: " + dutSocketsRejectUpload);
        LOGGER.info("edgeMbBananaRejectQty          ::: " + edgeMbBananaRejectQty);
        LOGGER.info("edgeMbBananaRejectUpload       ::: " + edgeMbBananaRejectUpload);
        LOGGER.info("electComponentRejectQty        ::: " + electComponentRejectQty);
        LOGGER.info("electComponentRejectUpload     ::: " + electComponentRejectUpload);
        LOGGER.info("solderJointRejectQty           ::: " + solderJointRejectQty);
        LOGGER.info("solderJointRejectUpload        ::: " + solderJointRejectUpload);
        LOGGER.info("winConnectorRejectQty          ::: " + winConnectorRejectQty);
        LOGGER.info("winConnectorRejectUpload       ::: " + winConnectorRejectUpload);
        LOGGER.info("teflonConnectorRejectQty       ::: " + teflonConnectorRejectQty);
        LOGGER.info("teflonConnectorRejectUpload    ::: " + teflonConnectorRejectUpload);
        LOGGER.info("pogoReceptaclesPinRejectQty    ::: " + pogoReceptaclesPinRejectQty);
        LOGGER.info("pogoReceptaclesPinRejectUpload ::: " + pogoReceptaclesPinRejectUpload);
        LOGGER.info("cableWiredCopperWireRejectQty  ::: " + cableWiredCopperWireRejectQty);
        LOGGER.info("cableWiredCopperWireRejectUpload   ::: " + cableWiredCopperWireRejectUpload);
        LOGGER.info("labelIdentificationRejectQty       ::: " + labelIdentificationRejectQty);
        LOGGER.info("labelIdentificationRejectUpload    ::: " + labelIdentificationRejectUpload);

        LOGGER.info("MIB ITEM ID     :::: " + mibItemId);
        LOGGER.info("MODULE          :::: " + module);
        LOGGER.info("MAVERICK ID     :::: " + maverickId);

//        saveAndSetFile(pcbRejectUpload, "pcb", q.getGeneratedKey(), itemVm::setPcbRejectUpload);
        String finalStatus = "";
        String stringPathPcb = "";
        String stringPathHandle = "";
        String stringPathmetalFrame = "";
        String stringPathHardwareFasterners = "";
        String stringPathclipHolder = "";
        String stringPathPcbEdgeFinger = "";
        String stringPathConnector = "";
        String stringPathDutSockets = "";
        String stringPathEdgeMbBanana = "";
        String stringPathElectComponent = "";
        String stringPathSolderJoint = "";
        String stringPathWinConnector = "";
        String stringPathTeflonConnector = "";
        String stringPathPogoReceptaclesPin = "";
        String stringPathCableWiredCopperWire = "";
        String stringPathLabelIdentification = "";
        String emailBodyFail = "";

        ItemVisualInspection itemVm = new ItemVisualInspection();

        itemVm.setMibItemId(mibItemId);
        if (null == itemStatus) {
            itemVm.setModule("Item Registration");
        } else {
            switch (itemStatus) {
                case "Pending Visual Inspection":
                    itemVm.setModule("Item Registration");
                    break;
                case "Pending Visual Inspection (from Maverick)":
                    itemVm.setModule("Item Registration (2nd Visual Inspection");
                    break;
                default:
                    itemVm.setModule("Item Registration");
                    break;
            }
        }
        itemVm.setPcb(pcb);
        itemVm.setPcbReject(pcbReject);
        itemVm.setHandle(handle);
        itemVm.setHandleReject(handleReject);
        itemVm.setMetalFrame(metalFrame);
        itemVm.setMetalFrameReject(metalFrameReject);
        itemVm.setHardwareFasterners(hardwareFasterners);
        itemVm.setHardwareFasternersReject(hardwareFasternersReject);
        itemVm.setClipHolder(clipHolder);
        itemVm.setClipHolderReject(clipHolderReject);
        itemVm.setPcbEdgeFinger(pcbEdgeFinger);
        itemVm.setPcbEdgeFingerReject(pcbEdgeFingerReject);
        itemVm.setConnector(connector);
        itemVm.setConnectorReject(connectorReject);
        itemVm.setDutSockets(dutSockets);
        itemVm.setDutSocketsReject(dutSocketsReject);
        itemVm.setEdgeMbBanana(edgeMbBanana);
        itemVm.setEdgeMbBananaReject(edgeMbBananaReject);
        itemVm.setElectComponent(electComponent);
        itemVm.setElectComponentReject(electComponentReject);
        itemVm.setSolderJoint(solderJoint);
        itemVm.setSolderJointReject(solderJointReject);
        itemVm.setWinConnector(winConnector);
        itemVm.setWinConnectorReject(winConnectorReject);
        itemVm.setTeflonConnector(teflonConnector);
        itemVm.setTeflonConnectorReject(teflonConnectorReject);
        itemVm.setPogoReceptaclesPin(pogoReceptaclesPin);
        itemVm.setPogoReceptaclesPinReject(pogoReceptaclesPinReject);
        itemVm.setCableWiredCopperWire(cableWiredCopperWire);
        itemVm.setCableWiredCopperWireReject(cableWiredCopperWireReject);
        itemVm.setLabelIdentification(labelIdentification);
        itemVm.setLabelIdentificationReject(labelIdentificationReject);
        itemVm.setRemarks(remarks);

        emailBodyFail += processRejectItem(pcb, pcbRejectQty, pcbReject, "PCB", itemVm::setPcbRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(handle, handleRejectQty, handleReject, "Handle", itemVm::setHandleRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(metalFrame, metalFrameRejectQty, metalFrameReject, "MetalFrame", itemVm::setMetalFrameRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(hardwareFasterners, hardwareFasternersRejectQty, hardwareFasternersReject, "Hardware Fasterner", itemVm::setHardwareFasternersRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(clipHolder, clipHolderRejectQty, clipHolderReject, "Clip Holder", itemVm::setClipHolderRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(pcbEdgeFinger, pcbEdgeFingerRejectQty, pcbEdgeFingerReject, "PCB Edge Finger", itemVm::setPcbEdgeFingerRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(connector, connectorRejectQty, connectorReject, "Connector", itemVm::setConnectorRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(dutSockets, dutSocketsRejectQty, dutSocketsReject, "DUT Socket", itemVm::setDutSocketsRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(edgeMbBanana, edgeMbBananaRejectQty, edgeMbBananaReject, "Edge MB Banana", itemVm::setEdgeMbBananaRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(electComponent, electComponentRejectQty, electComponentReject, "Elect Component", itemVm::setElectComponentRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(solderJoint, solderJointRejectQty, solderJointReject, "Solder Joint", itemVm::setSolderJointRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(winConnector, winConnectorRejectQty, winConnectorReject, "Win Connector", itemVm::setWinConnectorRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(teflonConnector, teflonConnectorRejectQty, teflonConnectorReject, "Teflon Connector", itemVm::setTeflonConnectorRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(pogoReceptaclesPin, pogoReceptaclesPinRejectQty, pogoReceptaclesPinReject, "Pogo / Receptacles Pin", itemVm::setPogoReceptaclesPinRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(cableWiredCopperWire, cableWiredCopperWireRejectQty, cableWiredCopperWireReject, "Cable/Wired/Copper Wire", itemVm::setCableWiredCopperWireRejectQty, emailBodyFail);
        emailBodyFail += processRejectItem(labelIdentification, labelIdentificationRejectQty, labelIdentificationReject, "Label & Identification", itemVm::setLabelIdentificationRejectQty, emailBodyFail);

        if ("Fail".equals(pcb) || "Fail".equals(handle) || "Fail".equals(metalFrame) || "Fail".equals(hardwareFasterners) || "Fail".equals(clipHolder) || "Fail".equals(pcbEdgeFinger) || "Fail".equals(connector)
                || "Fail".equals(dutSockets) || "Fail".equals(edgeMbBanana) || "Fail".equals(electComponent) || "Fail".equals(solderJoint) || "Fail".equals(winConnector)
                || "Fail".equals(teflonConnector) || "Fail".equals(pogoReceptaclesPin) || "Fail".equals(cableWiredCopperWire) || "Fail".equals(labelIdentification)) {
            finalStatus = "Fail";
            itemVm.setFlag("99");
        } else {
            finalStatus = "Pass";
            itemVm.setFlag("0");
        }
        itemVm.setFinalStatus(finalStatus);
        itemVm.setCreatedBy(userSession.getFullname());

        ItemVisualInspectionDAO itemVmD = new ItemVisualInspectionDAO();
        QueryResult q = itemVmD.insertItemVisualInspection(itemVm);

        if (!"0".equals(q.getGeneratedKey())) {

            itemVm = new ItemVisualInspection();
            Long uploadId = Long.parseLong(q.getGeneratedKey());

            saveAndSetFile(pcbRejectUpload, "pcb", uploadId, itemVm::setPcbRejectUpload);
            saveAndSetFile(handleRejectUpload, "handle", uploadId, itemVm::setHandleRejectUpload);
            saveAndSetFile(metalFrameRejectUpload, "metalFrame", uploadId, itemVm::setMetalFrameRejectUpload);
            saveAndSetFile(hardwareFasternersRejectUpload, "hardwareFasteners", uploadId, itemVm::setHardwareFasternersRejectUpload);
            saveAndSetFile(clipHolderRejectUpload, "clipHolder", uploadId, itemVm::setClipHolderRejectUpload);
            saveAndSetFile(pcbEdgeFingerRejectUpload, "pcbEdgeFinger", uploadId, itemVm::setPcbEdgeFingerRejectUpload);
            saveAndSetFile(connectorRejectUpload, "connector", uploadId, itemVm::setConnectorRejectUpload);
            saveAndSetFile(dutSocketsRejectUpload, "dutSockets", uploadId, itemVm::setDutSocketsRejectUpload);
            saveAndSetFile(edgeMbBananaRejectUpload, "edgeMbBanana", uploadId, itemVm::setEdgeMbBananaRejectUpload);
            saveAndSetFile(electComponentRejectUpload, "electComponent", uploadId, itemVm::setElectComponentRejectUpload);
            saveAndSetFile(solderJointRejectUpload, "solderJoint", uploadId, itemVm::setSolderJointRejectUpload);
            saveAndSetFile(winConnectorRejectUpload, "winConnector", uploadId, itemVm::setWinConnectorRejectUpload);
            saveAndSetFile(teflonConnectorRejectUpload, "teflonConnector", uploadId, itemVm::setTeflonConnectorRejectUpload);
            saveAndSetFile(pogoReceptaclesPinRejectUpload, "pogoReceptaclesPin", uploadId, itemVm::setPogoReceptaclesPinRejectUpload);
            saveAndSetFile(cableWiredCopperWireRejectUpload, "cableWiredCopperWire", uploadId, itemVm::setCableWiredCopperWireRejectUpload);
            saveAndSetFile(labelIdentificationRejectUpload, "labelIdentification", uploadId, itemVm::setLabelIdentificationRejectUpload);

            itemVm.setId(q.getGeneratedKey());
            itemVmD = new ItemVisualInspectionDAO();
            QueryResult q3 = itemVmD.updateItemVisualInspectionForAttachment(itemVm);

            //update Item DB
            String status = "";
            String flag = "";
            Item item = new Item();
            item.setId(mibItemId);
            if ("Fail".equals(finalStatus)) {
                status = "Failed Visual Inspection - Waiting Maverick CA";
                flag = "0";
            } else {
                status = getCurrentStatus(mibItemId);
                if (status.equals("Good")) {
                    flag = "1";
//                    insertSPTSData(mibItemId, userSession.getFullname()); //insert to SPTS if no functional test
                } else {
                    flag = "0";
                }
//                insertSPTSData(mibItemId, userSession.getFullname());
            }
            item.setStatus(status);
            item.setFlag(flag);
            ItemDAO iD = new ItemDAO();
            QueryResult q2 = iD.updateItemStatusAndFlag(item);

            //update log
            ItemLog log = new ItemLog();
            log.setItemId(mibItemId);
            log.setDetail("VM Data Added");
            log.setCreatedBy(userSession.getFullname());
            ItemLogDAO logD = new ItemLogDAO();
            QueryResult logQ = logD.insertItemLog(log);

            if ("Fail".equals(finalStatus)) {

                //save to maverick table
                ItemMaverick maverick = new ItemMaverick();
                maverick.setMibItemId(mibItemId);
                maverick.setModule("Hardware Registration");
                maverick.setSubmodule("Visual Inspection");
                maverick.setStatus("Failed Visual Inspection");
                maverick.setFlag("0");
                maverick.setCreatedBy(userSession.getFullname());
                ItemMaverickDAO maverickD = new ItemMaverickDAO();
                QueryResult maverickAdd = maverickD.insertItemMaverick(maverick);

                EmailVmFailDAO userDao = new EmailVmFailDAO();
                List<EmailVmFail> userRecipientsList = userDao.getEmailVmFailList();

                String[] to = new String[userRecipientsList.size()];
                for (int i = 0; i < userRecipientsList.size(); i++) {
                    to[i] = userRecipientsList.get(i).getEmail();
                }

                //get current date and time
                LocalDateTime instance = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedString = formatter.format(instance); //15-02-2022 12:43

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                ItemDAO itemD = new ItemDAO();
                Item item2 = itemD.getHardwareDetail(mibItemId);

                //send INFORMATION email
                LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        "Item Registration - Failed Visual Inspection", //subject
                        "<br />"
                        + "Please be informed that the item below failed the visual inspection."
                        + "<br /> "
                        + "<br /> "
                        + "Item ID: " + item2.getItemId()
                        + "<br /> "
                        + "Inspection Date: " + formattedString
                        + "<br /> "
                        + "<br /> "
                        + "Detail: <br />" + emailBodyFail
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/hw/item/add2/" + mibItemId + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("error", "Visual Inspection Fail. Pls go to Maverick Module for Corrective Action.");
//                return "redirect:/hw/item/add2/" + mibItemId;
                return "redirect:/maverick/maverickdetails/" + maverickId + "/" + module + "/" + mibItemId;
            } else {
                redirectAttrs.addFlashAttribute("success", "Visual Inspection Pass.");
                if (status.equals("Good")) {
//                    return "redirect:/hw/item/pending";
                    return "redirect:/maverick/maverickdetails/" + maverickId + "/" + module + "/" + mibItemId;
                } else {
//                    return "redirect:/hw/item/add2/" + mibItemId;
                    return "redirect:/maverick/maverickdetails/" + maverickId + "/" + module + "/" + mibItemId;
                }

            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to save Visual Inspection. Pls Contact System Admin");
//            return "redirect:/hw/item/add2/" + mibItemId;
            return "redirect:/maverick/maverickdetails/" + maverickId + "/" + module + "/" + mibItemId;
        }

//        return "redirect:/maverick/maverickdetails/" + maverickId + "/" + module + "/" + mibItemId;
    }

    private void saveAndSetFile(MultipartFile file, String prefix, Long generatedKey, Consumer<String> setter) {
        String path = saveUploadedFile(file, prefix, generatedKey);
        if (path != null) {
            setter.accept(path);
        }
    }

    private String saveUploadedFile(MultipartFile file, String prefix, Long generatedKey) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + generatedKey + "_" + prefix + "_" + file.getOriginalFilename());
            Files.write(path, bytes);
            LOGGER.info("Saved file [{}] at [{}]", prefix, path);
            return path.toString();
        } catch (IOException e) {
            LOGGER.error("Error saving {} file", prefix, e);
            return null;
        }
    }

    @RequestMapping(value = "/redoload/{id}", method = RequestMethod.GET)
    public String maverickVMFTLoad(@PathVariable String id) {

        return "maverick/maverick_loading";
    }

    @RequestMapping(value = "/checkfile/{type}/{itemid}", method = RequestMethod.GET)
    public void downloadAttachmentTest(HttpServletRequest request,
            @PathVariable("type") String type,
            @PathVariable("itemid") String itemid,
            HttpServletResponse response) throws IOException, SQLException {

        ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
        ItemFunctionalTest itemf = itemdao.getItemActivityByItemId(itemid);

        String attachment = "";
        switch (type) {
            case "bibtest":
                attachment = itemf.getBibUpload();
                break;
            case "bibdaqtest":
                attachment = itemf.getBibDaqUpload();
                break;
            case "leaktest":
                attachment = itemf.getLeakUpload();
                break;
            case "pstest":
                attachment = itemf.getPsUpload();
                break;
            case "wintest":
                attachment = itemf.getWinUpload();
                break;
            default:
                attachment = "";
                break;
        }

        String fullPath = attachment;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        String mimeType = servletContext.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        response.setHeader(headerKey, headerValue);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    public String getCurrentStatus(String itemId) {
        String status = "No setup found";
        String statusVi = "";
        String statusLeak = "";
        String statusMan = "";
        String statusBib = "";
        String statusDaq = "";
        String statusPs = "";
        String statusWin = "";
        ItemActivityConfig iac = new ItemActivityConfig();
        ItemActivityConfigDAO iacdao = new ItemActivityConfigDAO();

        iac = iacdao.getItemActivityByItemId(itemId);
        if (iac != null) {
            statusVi = iac.getVi();
            statusLeak = iac.getLeakageTest();
            statusMan = iac.getManualTest();
            statusBib = iac.getBibTest();
            statusDaq = iac.getBibDaqTest();
            statusPs = iac.getPsLeakageTest();
            statusWin = iac.getWinchesterChamberLeakageTest();
        }

        // HANYA UNTUK FIRST TIME SELEPAS DIA COMPLETE KAN VISUAL INSPECTION
        if (statusLeak.equals("Yes")) {
            status = "Pending Functional Test - Leakage Test";
        } else if (statusMan.equals("Yes")) {
            status = "Pending Functional Test - Manual Test";
        } else if (statusBib.equals("Yes")) {
            status = "Pending Functional Test - BIB Test";
        } else if (statusDaq.equals("Yes")) {
            status = "Pending Functional Test - BIB DAQ Test";
        } else if (statusPs.equals("Yes")) {
            status = "Pending Functional Test - Power Supply Test";
        } else if (statusWin.equals("Yes")) {
            status = "Pending Functional Test - Winchester Chamber Test";
        } else {
            status = "Good";
        }

        return status;
    }

    public void insertSPTSData(String mibItemId, String username) throws IOException, SQLException {

        ItemDAO itemdao = new ItemDAO();
        Item item = itemdao.getHardwareDetail(mibItemId);

        String itemId = StringEscapeUtils.escapeXml10(item.getItemId());
        String itemName = StringEscapeUtils.escapeXml10(item.getItemName());
        String onHandQty = item.getOnHandQty();
        String productionQty = item.getProductionQty();
        String repairQty = item.getRepairQty();
        String otherQty = item.getOtherQty();
        String quarantineQty = item.getQuarantineQty();
        String externalCleanQty = item.getExternalCleanQty();
        String externalRecleanQty = item.getExternalRecleanQty();
        String internalCleanQty = item.getInternalCleanQty();
        String internalRecleanQty = item.getInternalRecleanQty();
        String storageFactoryQty = item.getStorageFactoryQty();
        String productionStagingQty = item.getProductionStagingQty();
        String otherOnsemiQty = item.getOtherOnsemiQty();
        String vendorQty = item.getVendorQty();
        String minQty = item.getMinQty();
        String maxQty = item.getMaxQty();
        String unitCost = item.getUnitCost();
        String rack = StringEscapeUtils.escapeXml10(item.getRack());
        String shelf = StringEscapeUtils.escapeXml10(item.getShelf());
        String model = StringEscapeUtils.escapeXml10(item.getModel());
        String manufacturer = StringEscapeUtils.escapeXml10(item.getManufacturer());
        String equipmentType = StringEscapeUtils.escapeXml10(item.getEquipmentType());
        String equipmentModel = StringEscapeUtils.escapeXml10(item.getEquipmentModel());
        String equipmentManufacturer = StringEscapeUtils.escapeXml10(item.getEquipmentManufacturer());
        String stressType = StringEscapeUtils.escapeXml10(item.getStressType());
        String isConsumable = item.getIsConsumable();
        String itemTypeRead = StringEscapeUtils.escapeXml10(item.getItemType());
        String subType = StringEscapeUtils.escapeXml10(item.getSubType());
        String assemblyId = StringEscapeUtils.escapeXml10(item.getAssemblyId());
        String remarks = StringEscapeUtils.escapeXml10(item.getRemarks());
        String expirationDate = item.getExpirationDate();
        String downtimeValue = "";
        String downtimeUnit = "";
        String implementationCost = "";
        String manpowerValue = "";
        String manpowerUnit = "";
        String completeDateTime = "";

        JSONObject addItem = new JSONObject();
        addItem.put("itemID", itemId);
        addItem.put("itemName", itemName);
        addItem.put("onHandQty", onHandQty);
        addItem.put("prodQty", productionQty);
        addItem.put("repairQty", repairQty);
        addItem.put("otherQty", otherQty);
        addItem.put("quarantineQty", quarantineQty);
        addItem.put("externalCleaningQty", externalCleanQty);
        addItem.put("externalRecleaningQty", externalRecleanQty);
        addItem.put("internalCleaningQty", internalCleanQty);
        addItem.put("internalRecleaningQty", internalRecleanQty);
        addItem.put("storageFactoryQty", storageFactoryQty);
        addItem.put("prodStagingQty", productionStagingQty);
        addItem.put("otherONQty", otherOnsemiQty);
        addItem.put("vendorQty", vendorQty);
        addItem.put("minQty", minQty);
        addItem.put("maxQty", maxQty);
        addItem.put("unit", "pcs");
        addItem.put("unitCost", unitCost);
        addItem.put("rack", rack);
        addItem.put("shelf", shelf);
        addItem.put("model", model);
        addItem.put("manufacturer", manufacturer);
        addItem.put("equipmentType", equipmentType);
        addItem.put("equipmentModel", equipmentModel);
        addItem.put("equipmentManufacturer", equipmentManufacturer);
        addItem.put("stressType", stressType);
        addItem.put("isCritical", "0");
        if ("true".equals(isConsumable)) {
            addItem.put("isConsumeable", "1");
        } else {
            addItem.put("isConsumeable", "0");
        }
        addItem.put("itemType", itemTypeRead);
        addItem.put("subType", subType);
        addItem.put("assemblyID", assemblyId);
        addItem.put("remarks", remarks);

        if (expirationDate == null || "".equals(expirationDate)) {

        } else {
            String date1 = expirationDate.substring(0, 10);
            String time = expirationDate.substring(11, 19);
            completeDateTime = date1 + "T" + time;
            addItem.put("expirationDate", completeDateTime);
        }

        addItem.put("complexityScore", "0");

        SPTSResponse sr = SPTSWebService.insertItem(addItem);

        if (sr.getStatus()) {

            //update log
            ItemLog log = new ItemLog();
//            log = new ItemLog();
            log.setItemId(mibItemId);
            log.setDetail("Successfully Added into SPTS");
            log.setCreatedBy(username);
            ItemLogDAO logD = new ItemLogDAO();
            QueryResult logQ2 = logD.insertItemLog(log);

            //update SPTS PKID into MIB DB
            LOGGER.info("sr.getResponseId().toString(): " + sr.getResponseId().toString());
            item = new Item();
            item.setSptsPkid(sr.getResponseId().toString());
            item.setId(mibItemId);

            ItemDAO itemD = new ItemDAO();
            QueryResult i2 = itemD.updateItemSPTSPKID(item);

            //create Hardware ID if item type = BIB 13.08.2026
            if ("BIB".equals(itemTypeRead)) {
                LOGGER.info("++++++++++++createhardwareid+++++++++++++");
                ItemHardwareDAO itemHD = new ItemHardwareDAO();
                int countHwId = itemHD.getCountHardwareId(itemId);
                if (countHwId == 0) {
                    ItemHardware itemhardware = new ItemHardware();
                    itemhardware.setMibItemId(mibItemId);
                    itemhardware.setHardwareId(itemId);
                    itemhardware.setStatus("Pending Verification");
                    itemhardware.setCreatedBy(username);
                    itemhardware.setFlag("0");

                    ItemHardwareDAO dao2 = new ItemHardwareDAO();
                    QueryResult q = dao2.insertHardwareID(itemhardware);
                }
            }

        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(addItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
        }
    }

    private String processRejectItem(String result, String rejectQty, String rejectReason, String itemName, Consumer<String> setter, String emailBodyFail) {
        if ("Pass".equals(result) || "NA".equals(result)) {
            setter.accept("0");
        } else {
            setter.accept(rejectQty);
            emailBodyFail += itemName + " Fail : " + rejectReason + "<br />";
        }
        return emailBodyFail;
    }

    private void addRejectAttribute(Model model, ParameterDetailsDAO dao, String value, String groupCode, String attributeName) {
        List<ParameterDetails> list = dao.getGroupParameterDetailList(value, groupCode);
        model.addAttribute(attributeName, list);
    }

    private void addParameterDetail(Model model, ParameterDetailsDAO dao, String value, String groupCode, String attributeName) {
        model.addAttribute(attributeName, dao.getGroupParameterDetailList(value, groupCode));
    }

}