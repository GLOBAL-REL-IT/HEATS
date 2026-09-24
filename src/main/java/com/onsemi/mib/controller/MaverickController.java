/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.controller;

import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemFunctionalTestDAO;
import com.onsemi.mib.dao.ItemMaverickDAO;
import com.onsemi.mib.dao.ItemVisualInspectionDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingMaverickDAO;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemFunctionalTest;
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.model.ItemVisualInspection;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.model.UserSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
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
        LOGGER.info("MASUK DEKAT ADMIN CONTGROLLER");
        return "maverick/test";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String maverickList(Model model, @ModelAttribute UserSession userSession) {
        LOGGER.info("KITA MASUK KE MAVERICK LISTING");
//        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
//        List<RmsBookingDetail> booking = rmsD.getRmsBookingDetailListFlagZero();

        RmsBookingMaverickDAO rbmdao = new RmsBookingMaverickDAO();
        List<RmsBookingMaverick> rbmList = rbmdao.getItemMaverickUnionRmsBookingMaverick();

        model.addAttribute("maverickList", rbmList);
//        model.addAttribute("booking", booking);

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
            RedirectAttributes redirectAttrs,
            @PathVariable String id,
            @PathVariable String id2,
            @PathVariable String module) throws SQLException {
        
        // HARDWARE REGISTRATION
        
        if (!module.equalsIgnoreCase("Hardware Registration")) {
            redirectAttrs.addFlashAttribute("error", "This record does not belong to Hardware Registration page, please contact HEATS admin");
            return "redirect:/maverick/list"; 
        }
        
        ItemMaverick itemmav = new ItemMaverick();
        ItemMaverickDAO itemmavdao = new ItemMaverickDAO();
        itemmav = itemmavdao.getItemMaverickById(id);
        model.addAttribute("data", itemmav);
        model.addAttribute("mibItemId", id2);
        model.addAttribute("id", id);
        
        String submodule = itemmav.getSubmodule();
        String returnPage = "maverick/registration_ft";
        
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
            returnPage = "maverick/registration_vm";
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

        // type = "Hardware Registration"
        return returnPage;
    }

    @RequestMapping(value = "/maverickdetails/{id}/{module}/{bookid}/{pkid}", method = RequestMethod.GET)
    public String maverickDetails2(
            @PathVariable String id,
            @PathVariable String module,
            @PathVariable String bookid,
            @PathVariable String pkid) {
        
        // BEFORE & AFTER LOADING

        LOGGER.info("MASUK FUNCTION 02");
        LOGGER.info("nk tengok value id main  :::::: " + id);
        LOGGER.info("nk tengok value module   :::::: " + module);
        LOGGER.info("nk tengok value id table :::::: " + bookid);
        LOGGER.info("nk tengok value id table :::::: " + pkid);

        // type = "Hardware Registration"
        return "maverick/test";
    }
    
    @RequestMapping(value = "/repair/{id}", method=RequestMethod.GET)
    public String maverickRepair(@PathVariable String id) {
        
        LOGGER.info("SINI KITA MASUK KE FUNCTION NK REPAIR");
        
        return "maverick/repair";
    }
    
    @RequestMapping(value = "/scrap/{id}", method=RequestMethod.GET)
    public String maverickScrap(@PathVariable String id) {
        
        LOGGER.info("SINI KITA MASUK KE FUNCTION NK SCRAP MOTHERBOARD NI");
        
        return "maverick/scrap";
    }
    
    @RequestMapping(value = "/bypass/{id}", method=RequestMethod.GET)
    public String maverickBypass(@PathVariable String id) {
        
        LOGGER.info("SINI KITA MASUK KE FUNCTION PATAH BALIK KE FUNCTION YANG ASAL");
        
        
        return "maverick/test";
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