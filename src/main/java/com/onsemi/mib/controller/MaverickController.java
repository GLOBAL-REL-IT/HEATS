/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.controller;

import com.onsemi.mib.dao.ItemFunctionalTestDAO;
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
import com.onsemi.mib.model.ItemFunctionalTest;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        // HARDWARE REGISTRATION
        if (!module.equalsIgnoreCase("Hardware Registration")) {
            redirectAttrs.addFlashAttribute("error", "This record does not belong to Hardware Registration page, please contact HEATS admin");
            return "redirect:/maverick/list";
        }
        
        // FLAG 0 - New maverick data - DONE
        // FLAG 1 - Complete Maverick / Bypass - DONE
        // FLAG 2 - Repair
        // FLAG 3 - Scrap
        // FLAG 4 - 

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
            List<ParameterDetails> pcbReject = pD.getGroupParameterDetailList(itemVm.getPcbReject(), "003");
            model.addAttribute("pcbReject", pcbReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> handleReject = pD.getGroupParameterDetailList(itemVm.getHandleReject(), "004");
            model.addAttribute("handleReject", handleReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> metalFrameReject = pD.getGroupParameterDetailList(itemVm.getMetalFrameReject(), "005");
            model.addAttribute("metalFrameReject", metalFrameReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> hardwareFasternersReject = pD.getGroupParameterDetailList(itemVm.getHardwareFasternersReject(), "006");
            model.addAttribute("hardwareFasternersReject", hardwareFasternersReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> clipHolderReject = pD.getGroupParameterDetailList(itemVm.getClipHolderReject(), "007");
            model.addAttribute("clipHolderReject", clipHolderReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> pcbEdgeFingerReject = pD.getGroupParameterDetailList(itemVm.getPcbEdgeFingerReject(), "008");
            model.addAttribute("pcbEdgeFingerReject", pcbEdgeFingerReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> connectorReject = pD.getGroupParameterDetailList(itemVm.getConnectorReject(), "009");
            model.addAttribute("connectorReject", connectorReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> dutSocketsReject = pD.getGroupParameterDetailList(itemVm.getDutSocketsReject(), "010");
            model.addAttribute("dutSocketsReject", dutSocketsReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> edgeMbBananaReject = pD.getGroupParameterDetailList(itemVm.getEdgeMbBananaReject(), "011");
            model.addAttribute("edgeMbBananaReject", edgeMbBananaReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> electComponentReject = pD.getGroupParameterDetailList(itemVm.getElectComponentReject(), "012");
            model.addAttribute("electComponentReject", electComponentReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> solderJointReject = pD.getGroupParameterDetailList(itemVm.getSolderJointReject(), "014");
            model.addAttribute("solderJointReject", solderJointReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> winConnectorReject = pD.getGroupParameterDetailList(itemVm.getWinConnectorReject(), "015");
            model.addAttribute("winConnectorReject", winConnectorReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> teflonConnectorReject = pD.getGroupParameterDetailList(itemVm.getTeflonConnectorReject(), "020");
            model.addAttribute("teflonConnectorReject", teflonConnectorReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> pogoReceptaclesPinReject = pD.getGroupParameterDetailList(itemVm.getPogoReceptaclesPinReject(), "021");
            model.addAttribute("pogoReceptaclesPinReject", pogoReceptaclesPinReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> cableWiredCopperWireReject = pD.getGroupParameterDetailList(itemVm.getCableWiredCopperWireReject(), "022");
            model.addAttribute("cableWiredCopperWireReject", cableWiredCopperWireReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> labelIdentificationReject = pD.getGroupParameterDetailList(itemVm.getLabelIdentificationReject(), "023");
            model.addAttribute("labelIdentificationReject", labelIdentificationReject);

            model.addAttribute("itemVm", itemVm);
            jenis = "VM";
        }

        ItemFunctionalTestDAO itemdao2 = new ItemFunctionalTestDAO();
        ItemFunctionalTest itemdata2 = itemdao2.getItemActivityByItemId(id2);

        if (itemdata2 != null) {
            model.addAttribute("dataTest", itemdata2);

            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList(itemdata2.getBibStatus(), "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> daqResultData = pDx.getGroupParameterDetailList(itemdata2.getBibDaqStatus(), "016");
            model.addAttribute("daqResultData", daqResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList(itemdata2.getLeakStatus(), "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList(itemdata2.getPsStatus(), "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList(itemdata2.getWinStatus(), "016");
            model.addAttribute("winResultData", winResultData);
        } else {
            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> daqResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("daqResultData", daqResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("winResultData", winResultData);
        }
        
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
        // FLAG 4 - 

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
            redirectAttrs.addFlashAttribute("error", "Disposition already completed. This should not happen. Pls contact system admin. Maverick ID :: "+mavId);
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
            List<ParameterDetails> pcbReject = pD.getGroupParameterDetailList(rmsvm.getPcbReject(), "003");
            model.addAttribute("pcbReject", pcbReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> handleReject = pD.getGroupParameterDetailList(rmsvm.getHandleReject(), "004");
            model.addAttribute("handleReject", handleReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> metalFrameReject = pD.getGroupParameterDetailList(rmsvm.getMetalFrameReject(), "005");
            model.addAttribute("metalFrameReject", metalFrameReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> hardwareFasternersReject = pD.getGroupParameterDetailList(rmsvm.getHardwareFasternersReject(), "006");
            model.addAttribute("hardwareFasternersReject", hardwareFasternersReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> clipHolderReject = pD.getGroupParameterDetailList(rmsvm.getClipHolderReject(), "007");
            model.addAttribute("clipHolderReject", clipHolderReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> pcbEdgeFingerReject = pD.getGroupParameterDetailList(rmsvm.getPcbEdgeFingerReject(), "008");
            model.addAttribute("pcbEdgeFingerReject", pcbEdgeFingerReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> connectorReject = pD.getGroupParameterDetailList(rmsvm.getConnectorReject(), "009");
            model.addAttribute("connectorReject", connectorReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> dutSocketsReject = pD.getGroupParameterDetailList(rmsvm.getDutSocketsReject(), "010");
            model.addAttribute("dutSocketsReject", dutSocketsReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> edgeMbBananaReject = pD.getGroupParameterDetailList(rmsvm.getEdgeMbBananaReject(), "011");
            model.addAttribute("edgeMbBananaReject", edgeMbBananaReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> electComponentReject = pD.getGroupParameterDetailList(rmsvm.getElectComponentReject(), "012");
            model.addAttribute("electComponentReject", electComponentReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> solderJointReject = pD.getGroupParameterDetailList(rmsvm.getSolderJointReject(), "014");
            model.addAttribute("solderJointReject", solderJointReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> winConnectorReject = pD.getGroupParameterDetailList(rmsvm.getWinConnectorReject(), "015");
            model.addAttribute("winConnectorReject", winConnectorReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> teflonConnectorReject = pD.getGroupParameterDetailList(rmsvm.getTeflonConnectorReject(), "020");
            model.addAttribute("teflonConnectorReject", teflonConnectorReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> pogoReceptaclesPinReject = pD.getGroupParameterDetailList(rmsvm.getPogoReceptaclesPinReject(), "021");
            model.addAttribute("pogoReceptaclesPinReject", pogoReceptaclesPinReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> cableWiredCopperWireReject = pD.getGroupParameterDetailList(rmsvm.getCableWiredCopperWireReject(), "022");
            model.addAttribute("cableWiredCopperWireReject", cableWiredCopperWireReject);

            pD = new ParameterDetailsDAO();
            List<ParameterDetails> labelIdentificationReject = pD.getGroupParameterDetailList(rmsvm.getLabelIdentificationReject(), "023");
            model.addAttribute("labelIdentificationReject", labelIdentificationReject);

            model.addAttribute("itemVm", rmsvm);
            jenis = "VM";
        }

        // UPDATE THIS FUNCTION TO READ FROM RMS_BOOKING_FUNCTIONAL_TEST
//        ItemFunctionalTestDAO itemdao2 = new ItemFunctionalTestDAO();
//        ItemFunctionalTest itemdata2 = itemdao2.getItemActivityByItemId(groupId);
        
        // Before Loading has no issue, but unloading per SI - how to cater this one
        RmsBookingFunctionalTestDAO rmsftdao = new RmsBookingFunctionalTestDAO();
        RmsBookingFunctionalTest rmsft = rmsftdao.getFuncTestResultByModule(groupId, module);
        
        if (rmsft != null) {
            model.addAttribute("dataTest", rmsft);

            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList(rmsft.getBibStatus(), "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> daqResultData = pDx.getGroupParameterDetailList(rmsft.getBibDaqStatus(), "016");
            model.addAttribute("daqResultData", daqResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList(rmsft.getLeakStatus(), "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList(rmsft.getPsStatus(), "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList(rmsft.getWinStatus(), "016");
            model.addAttribute("winResultData", winResultData);
        } else {
            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> daqResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("daqResultData", daqResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("winResultData", winResultData);
        }
        
        model.addAttribute("jenis", jenis);

        // UPDATE THIS FUNCTION TO READ FROM RMS_BOOKING_LOG
//        ItemLogDAO itemlogdao = new ItemLogDAO();
//        List<ItemLog> itemlog1 = itemlogdao.getItemLogListByItemId(groupId);
        
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
        // FLAG 4 - 

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
            redirectAttrs.addFlashAttribute("error", "Disposition already completed. This should not happen. Pls contact system admin. Maverick ID :: "+mavId);
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
    public String maverickVM(@PathVariable String id) {
        return "maverick/maverick_vmft";
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

}