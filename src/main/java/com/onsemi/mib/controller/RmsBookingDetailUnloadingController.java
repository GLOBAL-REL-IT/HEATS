package com.onsemi.mib.controller;

import com.google.gson.Gson;
import com.onsemi.mib.dao.EmailHwReturnFromStagingDAO;
import com.onsemi.mib.dao.EmailVmFailDAO;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.ItemActivityConfigDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemHardwareDAO;
import com.onsemi.mib.dao.ItemHardwareMovementDAO;
import com.onsemi.mib.dao.ItemMaverickDAO;
import com.onsemi.mib.dao.ItemTransactionDAO;
import com.onsemi.mib.dao.ManualTestDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingDetailHwReplacementDAO;
import com.onsemi.mib.dao.RmsBookingFunctionalTestDAO;
import com.onsemi.mib.dao.RmsBookingHardwareDAO;
import com.onsemi.mib.dao.RmsBookingHardwareGroupDAO;
import com.onsemi.mib.dao.RmsBookingHardwareGroupLogDAO;
import com.onsemi.mib.dao.RmsBookingIonicConfigDAO;
import com.onsemi.mib.dao.RmsBookingIonicDAO;
import com.onsemi.mib.dao.RmsBookingLogDAO;
import com.onsemi.mib.dao.RmsBookingMaverickDAO;
import com.onsemi.mib.dao.RmsBookingVisualInspectionDAO;
import com.onsemi.mib.model.EmailHwReturnFromStaging;
import com.onsemi.mib.model.EmailVmFail;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemActivityConfig;
import com.onsemi.mib.model.ItemHardware;
import com.onsemi.mib.model.ItemHardwareMovement;
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.model.ItemTransaction;
import com.onsemi.mib.model.ManualTest;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingDetailHwReplacement;
import com.onsemi.mib.model.RmsBookingFunctionalTest;
import com.onsemi.mib.model.RmsBookingHardware;
import com.onsemi.mib.model.RmsBookingHardwareGroup;
import com.onsemi.mib.model.RmsBookingHardwareGroupLog;
import com.onsemi.mib.model.RmsBookingIonic;
import com.onsemi.mib.model.RmsBookingIonicConfig;
import com.onsemi.mib.model.RmsBookingLog;
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.model.RmsBookingVisualInspection;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSResponse;
import com.onsemi.mib.tools.SPTSStatus;
import com.onsemi.mib.tools.SPTSWebService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/rmsbookingDetailUnloading")
@SessionAttributes({"userSession"})
public class RmsBookingDetailUnloadingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingDetailUnloadingController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\HEATS\\VI-Attachment\\After_Loading\\"; //server
    private static final String UPLOADED_FOLDER_IONIC = "\\\\mysed-rel-app05\\f$\\HEATS\\Ionic-Attachment\\"; //server
    private static final String FOLDER_TEST_AL = "\\\\mysed-rel-app05\\f$\\HEATS\\FTAL\\"; //server
    private static final String FOLDER_TEST_BL = "\\\\mysed-rel-app05\\f$\\HEATS\\FTBL\\"; //server

    private static final int BUFFER_SIZE = 4096;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String rmsbookingDetail(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws IOException {

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        List<RmsBookingDetail> booking = rmsD.getRmsBookingDetailListWithHwGroupAfterLoading();

        model.addAttribute("booking", booking);

        rmsD = new RmsBookingDetailDAO();
        int countBooking = rmsD.getCountBookingFlagZero();

        model.addAttribute("countBooking", countBooking);

        return "rmsbookingDetailUnloading/rmsbookingDetailUnloading";
    }

    @RequestMapping(value = "/rmsDetailForHwReturn", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RmsBookingDetail rmsDetailForHwReturn(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String groupId
    ) throws IOException {

//        LOGGER.info("id: " + id);
        RmsBookingDetailDAO rmsd = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsd.getRmsBookingDetailWithHwGroupAfterLoadingByGroupId(groupId);

        return rms;
    }

    @RequestMapping(value = "/updateReturn", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String bookingHwGroupId,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String event
    ) throws IOException {

        RmsBookingHardwareGroup rms = new RmsBookingHardwareGroup();
        rms.setGroupId(groupId);
        rms.setReturnBy(userSession.getFullname());
        RmsBookingHardwareGroupDAO rmsD = new RmsBookingHardwareGroupDAO();
        QueryResult q = rmsD.updateRmsBookingHardwareGroupReturnByAndReturnDateByGroupId(rms);
        if (q.getResult() > 0) {

            //update substatus in rms_booking_hardware table
            LOGGER.info("groupId: " + groupId);
            LOGGER.info("event: " + event);
            String[] groupIdSplit = groupId.split("/");
            String bookingPkid = groupIdSplit[0];
            String mbBookingPkid = groupIdSplit[1];

            RmsBookingHardwareDAO rmsBookingHD = new RmsBookingHardwareDAO();
            int countBookingHwPkid = rmsBookingHD.getCountBookingPkidAndPkidForMotherboard(bookingPkid, mbBookingPkid);
            if (countBookingHwPkid == 1) {
                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                //get ionic config
                String subStatus = "";

                RmsBookingIonicConfigDAO ionicConfigD = new RmsBookingIonicConfigDAO();
                Integer countIonic = ionicConfigD.getCountByEvent(event);

                if (countIonic == 0) {
                    subStatus = "Pending VM";
                } else {
                    subStatus = "Pending Ionic Test";
                }

                RmsBookingHardware hwBook = new RmsBookingHardware();
                hwBook.setId(MbDetail.getId());
                hwBook.setSubStatus(subStatus);
                rmsBookingHD = new RmsBookingHardwareDAO();
                QueryResult qHwBook = rmsBookingHD.updateRmsBookingHardwareSubStatusById(hwBook);
            }

            //add log
            RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
            log.setGroupId(groupId);
            log.setDetail("Return to MB Room / Ionic Area");
            log.setCreatedBy(userSession.getFullname());
            RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
            QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

            redirectAttrs.addFlashAttribute("success", "Pls Return the HW to MB Room / Ionic Area");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to update. Pls contact system admin.");
        }
        return "redirect:/rmsbookingDetailUnloading";
    }

    //group
    @RequestMapping(value = "/groupDetail/{bookingId}/{itemPkid}", method = RequestMethod.GET)
    public String groupDetail(Model model,
            @PathVariable("bookingId") String bookingId,
            @PathVariable("itemPkid") String itemPkid,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession) throws IOException {

        String currentStatus = "";

        String teActive = "";
        String teActiveTab = "";

        String groupId = bookingId + "/" + itemPkid;
        model.addAttribute("groupId", groupId);
        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

        RmsBookingDetailDAO rmsd = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsd.getRmsBookingDetailByBookingPkid(bookingId);
        model.addAttribute("rms", rms);

        RmsBookingHardwareDAO hD = new RmsBookingHardwareDAO();
        RmsBookingHardware h = hD.getRmsBookingHardwareByPkid(itemPkid);
        model.addAttribute("motherboardId", h.getItemId());
        model.addAttribute("subStatus", h.getSubStatus());
        model.addAttribute("status", h.getStatus());

        RmsBookingHardwareGroupDAO h2D = new RmsBookingHardwareGroupDAO();
        List<RmsBookingHardwareGroup> hwGroupList = h2D.getRmsBookingHardwareGroupListByGroupId(groupId);
        model.addAttribute("hwGroupList", hwGroupList);

        h2D = new RmsBookingHardwareGroupDAO();
        RmsBookingHardwareGroup hwGroup = h2D.getUnloadingDateByGroupId(groupId);
        String unloadingDate = hwGroup.getUnloadingDate();
        model.addAttribute("unloadingDate", unloadingDate);

        //get booking remarks
        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        int countRemarks = rmsHD.getCountHwWithRemarksByBookingPkid(bookingId);
        if (countRemarks == 0) {
            model.addAttribute("rmsRemarks", "");
        } else {
            rmsHD = new RmsBookingHardwareDAO();
            RmsBookingHardware rmsRemarks = rmsHD.getRmsBookingHardwareRemarksByBookingPkid(bookingId);
            model.addAttribute("rmsRemarks", rmsRemarks.getItemId());
        }

        currentStatus = h.getSubStatus();

        //vm tab
        RmsBookingVisualInspectionDAO vmD = new RmsBookingVisualInspectionDAO();
        RmsBookingVisualInspection itemVm = vmD.getRmsBookingVisualInspectionByGroupIdAndStatus(groupId, h.getStatus());

        if (itemVm != null) {
            model.addAttribute("itemVm", itemVm);
        } else {
            itemVm = new RmsBookingVisualInspection();
            model.addAttribute("itemVm", itemVm);
        }

        if (itemVm.getPcbHardwareId() != null && !"".equals(itemVm.getPcbHardwareId())) {
            String[] pcbHardwareIdList = itemVm.getPcbHardwareId().split(",");
            model.addAttribute("valueJsonPcb", new Gson().toJson(pcbHardwareIdList));
        } else {
            model.addAttribute("valueJsonPcb", new Gson().toJson(itemVm.getPcbHardwareId()));
        }
        if (itemVm.getHandleHardwareId() != null && !"".equals(itemVm.getHandleHardwareId())) {
            String[] handleHardwareIdList = itemVm.getHandleHardwareId().split(",");
            model.addAttribute("valueJsonHandle", new Gson().toJson(handleHardwareIdList));
        } else {
            model.addAttribute("valueJsonHandle", new Gson().toJson(itemVm.getHandleHardwareId()));
        }
        if (itemVm.getMetalFrameHardwareId() != null && !"".equals(itemVm.getMetalFrameHardwareId())) {
            String[] metalFrameHardwareIdList = itemVm.getMetalFrameHardwareId().split(",");
            model.addAttribute("valueJsonMetalFrame", new Gson().toJson(metalFrameHardwareIdList));
        } else {
            model.addAttribute("valueJsonMetalFrame", new Gson().toJson(itemVm.getMetalFrameHardwareId()));
        }
        if (itemVm.getHardwareFasternersHardwareId() != null && !"".equals(itemVm.getHardwareFasternersHardwareId())) {
            String[] hardwareFasternersHardwareIdList = itemVm.getHardwareFasternersHardwareId().split(",");
            model.addAttribute("valueJsonHardwareFasterners", new Gson().toJson(hardwareFasternersHardwareIdList));
        } else {
            model.addAttribute("valueJsonHardwareFasterners", new Gson().toJson(itemVm.getHardwareFasternersHardwareId()));
        }
        if (itemVm.getClipHolderHardwareId() != null && !"".equals(itemVm.getClipHolderHardwareId())) {
            String[] clipHolderHardwareIdList = itemVm.getClipHolderHardwareId().split(",");
            model.addAttribute("valueJsonClipHolder", new Gson().toJson(clipHolderHardwareIdList));
        } else {
            model.addAttribute("valueJsonClipHolder", new Gson().toJson(itemVm.getClipHolderHardwareId()));
        }
        if (itemVm.getPcbEdgeFingerHardwareId() != null && !"".equals(itemVm.getPcbEdgeFingerHardwareId())) {
            String[] pcbEdgeFingerHardwareIdList = itemVm.getPcbEdgeFingerHardwareId().split(",");
            model.addAttribute("valueJsonPcbEdgeFinger", new Gson().toJson(pcbEdgeFingerHardwareIdList));
        } else {
            model.addAttribute("valueJsonPcbEdgeFinger", new Gson().toJson(itemVm.getPcbEdgeFingerHardwareId()));
        }
        if (itemVm.getConnectorHardwareId() != null && !"".equals(itemVm.getConnectorHardwareId())) {
            String[] connectorHardwareIdList = itemVm.getConnectorHardwareId().split(",");
            model.addAttribute("valueJsonConnector", new Gson().toJson(connectorHardwareIdList));
        } else {
            model.addAttribute("valueJsonConnector", new Gson().toJson(itemVm.getConnectorHardwareId()));
        }
        if (itemVm.getDutSocketsHardwareId() != null && !"".equals(itemVm.getDutSocketsHardwareId())) {
            String[] dutSocketsHardwareIdList = itemVm.getDutSocketsHardwareId().split(",");
            model.addAttribute("valueJsonDutSockets", new Gson().toJson(dutSocketsHardwareIdList));
        } else {
            model.addAttribute("valueJsonDutSockets", new Gson().toJson(itemVm.getDutSocketsHardwareId()));
        }
        if (itemVm.getEdgeMbBananaHardwareId() != null && !"".equals(itemVm.getEdgeMbBananaHardwareId())) {
            String[] edgeMbBananaHardwareIdList = itemVm.getEdgeMbBananaHardwareId().split(",");
            model.addAttribute("valueJsonEdgeMbBanana", new Gson().toJson(edgeMbBananaHardwareIdList));
        } else {
            model.addAttribute("valueJsonEdgeMbBanana", new Gson().toJson(itemVm.getEdgeMbBananaHardwareId()));
        }
        if (itemVm.getElectComponentHardwareId() != null && !"".equals(itemVm.getElectComponentHardwareId())) {
            String[] electComponentHardwareIdList = itemVm.getElectComponentHardwareId().split(",");
            model.addAttribute("valueJsonElectComponent", new Gson().toJson(electComponentHardwareIdList));
        } else {
            model.addAttribute("valueJsonElectComponent", new Gson().toJson(itemVm.getElectComponentHardwareId()));
        }
        if (itemVm.getSolderJointHardwareId() != null && !"".equals(itemVm.getSolderJointHardwareId())) {
            String[] solderJointHardwareIdList = itemVm.getSolderJointHardwareId().split(",");
            model.addAttribute("valueJsonSolderJoint", new Gson().toJson(solderJointHardwareIdList));
        } else {
            model.addAttribute("valueJsonSolderJoint", new Gson().toJson(itemVm.getSolderJointHardwareId()));
        }
        if (itemVm.getWinConnectorHardwareId() != null && !"".equals(itemVm.getWinConnectorHardwareId())) {
            String[] winConnectorHardwareIdList = itemVm.getWinConnectorHardwareId().split(",");
            model.addAttribute("valueJsonWinConnector", new Gson().toJson(winConnectorHardwareIdList));
        } else {
            model.addAttribute("valueJsonWinConnector", new Gson().toJson(itemVm.getWinConnectorHardwareId()));
        }
        if (itemVm.getTeflonConnectorHardwareId() != null && !"".equals(itemVm.getTeflonConnectorHardwareId())) {
            String[] teflonConnectorHardwareIdList = itemVm.getTeflonConnectorHardwareId().split(",");
            model.addAttribute("valueJsonTeflonConnector", new Gson().toJson(teflonConnectorHardwareIdList));
        } else {
            model.addAttribute("valueJsonTeflonConnector", new Gson().toJson(itemVm.getTeflonConnectorHardwareId()));
        }
        if (itemVm.getPogoReceptaclesPinHardwareId() != null && !"".equals(itemVm.getPogoReceptaclesPinHardwareId())) {
            String[] pogoReceptaclesPinHardwareIdList = itemVm.getPogoReceptaclesPinHardwareId().split(",");
            model.addAttribute("valueJsonPogoReceptaclesPin", new Gson().toJson(pogoReceptaclesPinHardwareIdList));
        } else {
            model.addAttribute("valueJsonPogoReceptaclesPin", new Gson().toJson(itemVm.getPogoReceptaclesPinHardwareId()));
        }
        if (itemVm.getCableWiredCopperWireHardwareId() != null && !"".equals(itemVm.getCableWiredCopperWireHardwareId())) {
            String[] cableWiredCopperWireHardwareIdList = itemVm.getCableWiredCopperWireHardwareId().split(",");
            model.addAttribute("valueJsonCableWiredCopperWire", new Gson().toJson(cableWiredCopperWireHardwareIdList));
        } else {
            model.addAttribute("valueJsonCableWiredCopperWire", new Gson().toJson(itemVm.getCableWiredCopperWireHardwareId()));
        }
        if (itemVm.getLabelIdentificationHardwareId() != null && !"".equals(itemVm.getLabelIdentificationHardwareId())) {
            String[] labelIdentificationHardwareIdList = itemVm.getLabelIdentificationHardwareId().split(",");
            model.addAttribute("valueJsonLabelIdentification", new Gson().toJson(labelIdentificationHardwareIdList));
        } else {
            model.addAttribute("valueJsonLabelIdentification", new Gson().toJson(itemVm.getLabelIdentificationHardwareId()));
        }

//        LOGGER.info("itemVm.getPcbReject(): " + itemVm.getPcbReject());
        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> BibPassFail = pD.getGroupParameterDetailList("", "016");
        model.addAttribute("BibPassFail", BibPassFail);

        pD = new ParameterDetailsDAO();
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

        //get ionic config
        String hasIonic = "";
        String ionicPassValue = "0";

        RmsBookingIonicConfigDAO ionicConfigD = new RmsBookingIonicConfigDAO();
        Integer countIonicConfig = ionicConfigD.getCountByEvent(rms.getEvent());

        if (countIonicConfig == 0) {
            hasIonic = "No";
            model.addAttribute("ionicPassValue", ionicPassValue);
        } else {
            ionicConfigD = new RmsBookingIonicConfigDAO();
            RmsBookingIonicConfig ionicConfig = ionicConfigD.getRmsBookingIonicConfigByEvent(rms.getEvent());
            ionicPassValue = ionicConfig.getPassValue();
            model.addAttribute("ionicPassValue", ionicPassValue);
            hasIonic = "Yes";
        }

        //get ionic data
        RmsBookingIonicDAO ioncD = new RmsBookingIonicDAO();
        RmsBookingIonic ionic = ioncD.getRmsBookingIonicbyGroupId(groupId);

        if (ionic != null) {
            model.addAttribute("ionic", ionic);
        } else {
            model.addAttribute("ionic", new RmsBookingIonic());
        }

        //check which tab should active
        if (currentStatus.contains("Ionic")) {
            String ionicActive = "active";
            String ionicActiveTab = "show active";
//            String requiredDisable = "required";
//            String disabledUpload = "";
            model.addAttribute("requiredDisable", !currentStatus.contains("Failed") ? "required" : "disabled");
            model.addAttribute("disabledUpload", !currentStatus.contains("Failed") ? "" : "disabled");
            model.addAttribute("ionicActive", ionicActive);
            model.addAttribute("ionicActiveTab", ionicActiveTab);
//            model.addAttribute("requiredDisable", requiredDisable);
//            model.addAttribute("disabledUpload", disabledUpload);
        } else {
            String ionicActive = "";
            String ionicActiveTab = "";
            String requiredDisable = "disabled";
            String disabledUpload = "disabled";
            model.addAttribute("ionicActive", ionicActive);
            model.addAttribute("ionicActiveTab", ionicActiveTab);
            model.addAttribute("requiredDisable", requiredDisable);
            model.addAttribute("disabledUpload", disabledUpload);
        }
        if (currentStatus.contains("VM") || currentStatus.contains("Visual Inspection")) {
            String vmActive = "active";
            String vmActiveTab = "show active";
//            String buttonVm = "";
            model.addAttribute("vmActive", vmActive);
            model.addAttribute("vmActiveTab", vmActiveTab);
//            model.addAttribute("buttonVm", buttonVm);
            model.addAttribute("buttonVm", !currentStatus.contains("Failed") ? "" : "disabled");
        } else {
            String vmActive = "";
            String vmActiveTab = "";
            String buttonVm = "disabled";
            model.addAttribute("vmActive", vmActive);
            model.addAttribute("vmActiveTab", vmActiveTab);
            model.addAttribute("buttonVm", buttonVm);
        }

        if (!currentStatus.contains("Ionic") && !currentStatus.contains("VM") && !currentStatus.contains("Visual Inspection")) { //default active HW tab
            String hwActive = "active";
            String hwActiveTab = "show active";
            model.addAttribute("hwActive", hwActive);
            model.addAttribute("hwActiveTab", hwActiveTab);
        } else {
            String hwActive = "";
            String hwActiveTab = "";
            model.addAttribute("hwActive", hwActive);
            model.addAttribute("hwActiveTab", hwActiveTab);
        }

        model.addAttribute("currentStatus", currentStatus);
        model.addAttribute("teActive", teActive);
        model.addAttribute("teActiveTab", teActiveTab);

        return "rmsbookingDetailUnloading/detail_group";
    }

    @RequestMapping(value = "/ionic/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String ionicSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String hwStatus,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String bibResult,
            @RequestParam(required = false) String bibStatus,
            @RequestParam(required = false) MultipartFile bibUpload,
            @RequestParam(required = false) String bibCardResult,
            @RequestParam(required = false) String bibCardStatus,
            @RequestParam(required = false) MultipartFile bibCardUpload
    ) throws IOException {

        String finalStatus = "";
        String stringPathBib = "";
        String stringPathBibCard = "";
        String emailBodyFail = "";

        RmsBookingIonic ionic = new RmsBookingIonic();
        ionic.setGroupId(groupId);
        ionic.setModule(hwStatus);
        ionic.setBibResult(bibResult);
        ionic.setBibStatus(bibStatus);
        ionic.setBibCardResult(bibCardResult);
        ionic.setBibCardStatus(bibCardStatus);

        if ("FAIL".equals(bibStatus)) {
            emailBodyFail = "Bib Result : " + bibResult + "<br /> ";
        }
        if ("FAIL".equals(bibCardStatus)) {
            emailBodyFail = "Bib Card Result : " + bibCardResult + "<br /> ";
        }

        if ("FAIL".equals(bibStatus) || "FAIL".equals(bibCardStatus)) {
            finalStatus = "FAIL";
            ionic.setFlag("99");
        } else {
            finalStatus = "PASS";
            ionic.setFlag("0");
        }
        ionic.setStatus(finalStatus);
        ionic.setCreatedBy(userSession.getFullname());

        RmsBookingIonicDAO ionicD = new RmsBookingIonicDAO();
        QueryResult q = ionicD.insertRmsBookingIonic(ionic);
        if (!"0".equals(q.getGeneratedKey())) {

            ionic = new RmsBookingIonic();
            LOGGER.info("bibUpload: " + bibUpload);

            //check if user upload any attachment
//            if (!pcbRejectUpload.isEmpty()) {
            if (!bibUpload.isEmpty()) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesPcb = bibUpload.getBytes();
                    Path pathBib = Paths.get(UPLOADED_FOLDER_IONIC + q.getGeneratedKey() + "_bib_" + bibUpload.getOriginalFilename());
                    Files.write(pathBib, bytesPcb);
                    stringPathBib = pathBib.toString();
                    LOGGER.info("pathBib : " + pathBib);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ionic.setBibUpload(stringPathBib);
            }
            if (!bibCardUpload.isEmpty()) {
                LOGGER.info("bibCardUpload : " + bibCardUpload);
                try {
                    // Get the file and save it somewhere
                    byte[] bytesHandle = bibCardUpload.getBytes();
                    Path pathBibCard = Paths.get(UPLOADED_FOLDER_IONIC + q.getGeneratedKey() + "_bibCard_" + bibCardUpload.getOriginalFilename());
                    Files.write(pathBibCard, bytesHandle);
                    stringPathBibCard = pathBibCard.toString();
                    LOGGER.info("pathBibCard : " + pathBibCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ionic.setBibCardUpload(stringPathBibCard);
            }
            ionic.setId(q.getGeneratedKey());
            ionicD = new RmsBookingIonicDAO();
            QueryResult q3 = ionicD.updateRmsBookingIonicForUploadFile(ionic);

            //update substatus in rmsBookingHardware table
            String[] groupIdSplit = groupId.split("/");
            String bookingPkid = groupIdSplit[0];
            String mbBookingPkid = groupIdSplit[1];

            RmsBookingHardwareDAO rmsBookingHD = new RmsBookingHardwareDAO();
            int countBookingHwPkid = rmsBookingHD.getCountBookingPkidAndPkidForMotherboard(bookingPkid, mbBookingPkid);
            if (countBookingHwPkid == 1) {
                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                RmsBookingHardware hwBook = new RmsBookingHardware();
                hwBook.setId(MbDetail.getId());
                if ("FAIL".equals(finalStatus)) {
                    hwBook.setSubStatus("Failed Ionic Test (Waiting Maverick CA)");
                } else {
                    hwBook.setSubStatus("Pending VM");
                }
                rmsBookingHD = new RmsBookingHardwareDAO();
                QueryResult qHwBook = rmsBookingHD.updateRmsBookingHardwareSubStatusById(hwBook);
            }

            //add log
            RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
            log.setGroupId(groupId);
            log.setDetail("Ionic Test Completed (" + finalStatus + ")");
            log.setCreatedBy(userSession.getFullname());
            RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
            QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

            if ("FAIL".equals(finalStatus)) {

                //save to maverick table
                RmsBookingMaverick maverick = new RmsBookingMaverick();
                maverick.setGroupId(groupId);
                maverick.setModule(hwStatus);
                maverick.setSubmodule("Ionic Test");
                maverick.setStatus("Failed Ionic Test");
                maverick.setFlag("0");
                maverick.setCreatedBy(userSession.getFullname());
                RmsBookingMaverickDAO maverickD = new RmsBookingMaverickDAO();
                QueryResult maverickAdd = maverickD.insertRmsBookingMaverick(maverick);

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

                RmsBookingDetailDAO rmsBookingD = new RmsBookingDetailDAO();
                RmsBookingDetail rmsBooking = rmsBookingD.getRmsBookingDetailByBookingPkid(bookingPkid);

                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                //send INFORMATION email
                LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        hwStatus + " - Failed Ionic Test", //subject
                        "<br />"
                        + "Please be informed that the item below failed the Ionic Test."
                        + "<br /> "
                        + "<br /> "
                        + "RMS No: " + rmsBooking.getRmsNo()
                        + "<br /> "
                        + "Event: " + rmsBooking.getEvent()
                        + "<br /> "
                        + "Motherboard ID: " + MbDetail.getItemId()
                        + "<br /> "
                        + "Inspection Date: " + formattedString
                        + "<br /> "
                        + "<br /> "
                        + "Detail: <br />" + emailBodyFail
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetailUnloading/groupDetail/" + groupId + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("error", "Ionic Test Fail. Pls go to Maverick Module for Corrective Action.");
                return "redirect:/rmsbookingDetailUnloading/groupDetail/" + groupId;
            } else {
                redirectAttrs.addFlashAttribute("success", "Ionic Test Pass. Pls Proceed with VM");
                return "redirect:/rmsbookingDetailUnloading/groupDetail/" + groupId;
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to save Ionic Test Result. Pls Contact System Admin");
            return "redirect:/rmsbookingDetailUnloading/groupDetail/" + groupId;
        }
    }

    @RequestMapping(value = "/ionic/downloadAttach/{id}/{type}", method = RequestMethod.GET)
    public void downloadAttachmentIonic(HttpServletRequest request,
            @PathVariable("type") String type,
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        LOGGER.info("id: " + id);
        LOGGER.info("type: " + type);

        RmsBookingIonicDAO ionicD = new RmsBookingIonicDAO();
        RmsBookingIonic ionic = ionicD.getRmsBookingIonic(id);

        String attachment = "";
        switch (type) {
            case "bib":
                attachment = ionic.getBibUpload();
                break;
            case "bibCard":
                attachment = ionic.getBibCardUpload();
                break;
            default:
                attachment = "";
                break;
        }

        // construct the complete absolute path of the file
        String fullPath = attachment;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = servletContext.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    @RequestMapping(value = "/vm/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemVmSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String hwStatus,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String itemStatus,
            @RequestParam(required = false) String pcb,
            @RequestParam(required = false) String pcbHardwareId,
            @RequestParam(required = false) String handleHardwareId,
            @RequestParam(required = false) String metalFrameHardwareId,
            @RequestParam(required = false) String hardwareFasternersHardwareId,
            @RequestParam(required = false) String clipHolderHardwareId,
            @RequestParam(required = false) String pcbEdgeFingerHardwareId,
            @RequestParam(required = false) String connectorHardwareId,
            @RequestParam(required = false) String dutSocketsHardwareId,
            @RequestParam(required = false) String edgeMbBananaHardwareId,
            @RequestParam(required = false) String electComponentHardwareId,
            @RequestParam(required = false) String solderJointHardwareId,
            @RequestParam(required = false) String winConnectorHardwareId,
            @RequestParam(required = false) String teflonConnectorHardwareId,
            @RequestParam(required = false) String pogoReceptaclesPinHardwareId,
            @RequestParam(required = false) String cableWiredCopperWireHardwareId,
            @RequestParam(required = false) String labelIdentificationHardwareId,
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
    ) throws IOException {

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

//        if (null == itemStatus) {
//            itemVm.setModule("Before Loading");
//        } else {
//            switch (itemStatus) {
//                case "Pending Visual Inspection":
//                    itemVm.setModule("Item Registration");
//                    break;
//                case "Pending Visual Inspection (from Maverick)":
//                    itemVm.setModule("Item Registration (2nd Visual Inspection");
//                    break;
//                default:
//                    itemVm.setModule("Item Registration");
//                    break;
//            }
//        }
        LOGGER.info("pcbHardwareId[]: " + pcbHardwareId);
//        LOGGER.info("Arrays.toString(pcbHardwareId): " + Arrays.toString(pcbHardwareId));
        RmsBookingVisualInspection itemVm = new RmsBookingVisualInspection();
        itemVm.setGroupId(groupId);
        itemVm.setModule(hwStatus);
        itemVm.setPcb(pcb);
        itemVm.setPcbHardwareId(pcbHardwareId);
        itemVm.setHandleHardwareId(handleHardwareId);
        itemVm.setMetalFrameHardwareId(metalFrameHardwareId);
        itemVm.setHardwareFasternersHardwareId(hardwareFasternersHardwareId);
        itemVm.setClipHolderHardwareId(clipHolderHardwareId);
        itemVm.setPcbEdgeFingerHardwareId(pcbEdgeFingerHardwareId);
        itemVm.setConnectorHardwareId(connectorHardwareId);
        itemVm.setDutSocketsHardwareId(dutSocketsHardwareId);
        itemVm.setEdgeMbBananaHardwareId(edgeMbBananaHardwareId);
        itemVm.setElectComponentHardwareId(electComponentHardwareId);
        itemVm.setSolderJointHardwareId(solderJointHardwareId);
        itemVm.setWinConnectorHardwareId(winConnectorHardwareId);
        itemVm.setTeflonConnectorHardwareId(teflonConnectorHardwareId);
        itemVm.setPogoReceptaclesPinHardwareId(pogoReceptaclesPinHardwareId);
        itemVm.setCableWiredCopperWireHardwareId(cableWiredCopperWireHardwareId);
        itemVm.setLabelIdentificationHardwareId(labelIdentificationHardwareId);
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

        if ("Pass".equals(pcb) || "NA".equals(pcb)) {
            itemVm.setPcbRejectQty("0");
        } else {
            itemVm.setPcbRejectQty(pcbRejectQty);
            emailBodyFail = "PCB Fail : " + pcbReject + "<br /> ";
        }
        if ("Pass".equals(handle) || "NA".equals(handle)) {
            itemVm.setHandleRejectQty("0");
        } else {
            itemVm.setHandleRejectQty(handleRejectQty);
            emailBodyFail += "Handle Fail : " + handleReject + "<br /> ";
        }
        if ("Pass".equals(metalFrame) || "NA".equals(metalFrame)) {
            itemVm.setMetalFrameRejectQty("0");
        } else {
            itemVm.setMetalFrameRejectQty(metalFrameRejectQty);
            emailBodyFail += "MetalFrame Fail : " + metalFrameReject + "<br /> ";
        }
        if ("Pass".equals(hardwareFasterners) || "NA".equals(hardwareFasterners)) {
            itemVm.setHardwareFasternersRejectQty("0");
        } else {
            itemVm.setHardwareFasternersRejectQty(hardwareFasternersRejectQty);
            emailBodyFail += "Hardware Fasterner Fail : " + hardwareFasternersReject + "<br /> ";
        }
        if ("Pass".equals(clipHolder) || "NA".equals(clipHolder)) {
            itemVm.setClipHolderRejectQty("0");
        } else {
            itemVm.setClipHolderRejectQty(clipHolderRejectQty);
            emailBodyFail += "Clip Holder Fail : " + clipHolderReject + "<br /> ";
        }
        if ("Pass".equals(pcbEdgeFinger) || "NA".equals(pcbEdgeFinger)) {
            itemVm.setPcbEdgeFingerRejectQty("0");
        } else {
            itemVm.setPcbEdgeFingerRejectQty(pcbEdgeFingerRejectQty);
            emailBodyFail += "PCB Edge Finger Fail : " + pcbEdgeFingerReject + "<br /> ";
        }
        if ("Pass".equals(connector) || "NA".equals(connector)) {
            itemVm.setConnectorRejectQty("0");
        } else {
            itemVm.setConnectorRejectQty(connectorRejectQty);
            emailBodyFail += "Connector Fail : " + connectorReject + "<br /> ";
        }
        if ("Pass".equals(dutSockets) || "NA".equals(dutSockets)) {
            itemVm.setDutSocketsRejectQty("0");
        } else {
            itemVm.setDutSocketsRejectQty(dutSocketsRejectQty);
            emailBodyFail += "DUT Socket Fail : " + dutSocketsReject + "<br /> ";
        }
        if ("Pass".equals(edgeMbBanana) || "NA".equals(edgeMbBanana)) {
            itemVm.setEdgeMbBananaRejectQty("0");
        } else {
            itemVm.setEdgeMbBananaRejectQty(edgeMbBananaRejectQty);
            emailBodyFail += "Edge MB Banana Fail : " + edgeMbBananaReject + "<br /> ";
        }
        if ("Pass".equals(electComponent) || "NA".equals(electComponent)) {
            itemVm.setElectComponentRejectQty("0");
        } else {
            itemVm.setElectComponentRejectQty(electComponentRejectQty);
            emailBodyFail += "Elect Component Fail : " + electComponentReject + "<br /> ";
        }
        if ("Pass".equals(solderJoint) || "NA".equals(solderJoint)) {
            itemVm.setSolderJointRejectQty("0");
        } else {
            itemVm.setSolderJointRejectQty(solderJointRejectQty);
            emailBodyFail += "Solder Joint Fail : " + solderJointReject + "<br /> ";
        }
        if ("Pass".equals(winConnector) || "NA".equals(winConnector)) {
            itemVm.setWinConnectorRejectQty("0");
        } else {
            itemVm.setWinConnectorRejectQty(winConnectorRejectQty);
            emailBodyFail += "Win Connector Fail : " + winConnectorReject + "<br /> ";
        }
        if ("Pass".equals(teflonConnector) || "NA".equals(teflonConnector)) {
            itemVm.setTeflonConnectorRejectQty("0");
        } else {
            itemVm.setTeflonConnectorRejectQty(teflonConnectorRejectQty);
            emailBodyFail += "Teflon Connector Fail : " + teflonConnectorReject + "<br /> ";
        }
        if ("Pass".equals(pogoReceptaclesPin) || "NA".equals(pogoReceptaclesPin)) {
            itemVm.setPogoReceptaclesPinRejectQty("0");
        } else {
            itemVm.setPogoReceptaclesPinRejectQty(pogoReceptaclesPinRejectQty);
            emailBodyFail += "Pogo / Receptacles Pin Fail : " + pogoReceptaclesPinReject + "<br /> ";
        }
        if ("Pass".equals(cableWiredCopperWire) || "NA".equals(cableWiredCopperWire)) {
            itemVm.setCableWiredCopperWireRejectQty("0");
        } else {
            itemVm.setCableWiredCopperWireRejectQty(cableWiredCopperWireRejectQty);
            emailBodyFail += "Cable/Wired/Copper Wire Fail : " + cableWiredCopperWireReject + "<br /> ";
        }
        if ("Pass".equals(labelIdentification) || "NA".equals(labelIdentification)) {
            itemVm.setLabelIdentificationRejectQty("0");
        } else {
            itemVm.setLabelIdentificationRejectQty(labelIdentificationRejectQty);
            emailBodyFail += "Label & Identification Fail : " + labelIdentificationReject + "<br /> ";
        }

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

        RmsBookingVisualInspectionDAO itemVmD = new RmsBookingVisualInspectionDAO();
        QueryResult q = itemVmD.insertRmsBookingVisualInspection(itemVm);
        if (!"0".equals(q.getGeneratedKey())) {

            itemVm = new RmsBookingVisualInspection();
            LOGGER.info("pcbRejectUpload: " + pcbRejectUpload);

            //check if user upload any attachment
//            if (!pcbRejectUpload.isEmpty()) {
            if (pcbRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesPcb = pcbRejectUpload.getBytes();
                    Path pathPcb = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_pcb_" + pcbRejectUpload.getOriginalFilename());
                    Files.write(pathPcb, bytesPcb);
                    stringPathPcb = pathPcb.toString();
                    LOGGER.info("pathPcb : " + pathPcb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setPcbRejectUpload(stringPathPcb);
            }
            if (handleRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesHandle = handleRejectUpload.getBytes();
                    Path pathHandle = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_handle_" + handleRejectUpload.getOriginalFilename());
                    Files.write(pathHandle, bytesHandle);
                    stringPathHandle = pathHandle.toString();
                    LOGGER.info("pathHandle : " + pathHandle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setHandleRejectUpload(stringPathHandle);
            }
            if (metalFrameRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesMetalFrame = metalFrameRejectUpload.getBytes();
                    Path pathMetalFrame = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_metalFrame_" + metalFrameRejectUpload.getOriginalFilename());
                    Files.write(pathMetalFrame, bytesMetalFrame);
                    stringPathmetalFrame = pathMetalFrame.toString();
                    LOGGER.info("pathMetalFrame : " + pathMetalFrame);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setMetalFrameRejectUpload(stringPathmetalFrame);
            }
            if (hardwareFasternersRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesHardwareFasterners = hardwareFasternersRejectUpload.getBytes();
                    Path pathHardwareFasterners = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_hardwareFasteners_" + hardwareFasternersRejectUpload.getOriginalFilename());
                    Files.write(pathHardwareFasterners, bytesHardwareFasterners);
                    stringPathHardwareFasterners = pathHardwareFasterners.toString();
                    LOGGER.info("pathHardwareFasterners : " + pathHardwareFasterners);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setHardwareFasternersRejectUpload(stringPathHardwareFasterners);
            }
            if (clipHolderRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesClipHolder = clipHolderRejectUpload.getBytes();
                    Path pathClipHolder = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_clipHolder_" + clipHolderRejectUpload.getOriginalFilename());
                    Files.write(pathClipHolder, bytesClipHolder);
                    stringPathclipHolder = pathClipHolder.toString();
                    LOGGER.info("pathClipHolder : " + pathClipHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setClipHolderRejectUpload(stringPathclipHolder);
            }

            if (pcbEdgeFingerRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesPcbEdgeFinger = pcbEdgeFingerRejectUpload.getBytes();
                    Path pathPcbEdgeFinger = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_pcbEdgeFinger_" + pcbEdgeFingerRejectUpload.getOriginalFilename());
                    Files.write(pathPcbEdgeFinger, bytesPcbEdgeFinger);
                    stringPathPcbEdgeFinger = pathPcbEdgeFinger.toString();
                    LOGGER.info("pathPcbEdgeFinger : " + pathPcbEdgeFinger);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setPcbEdgeFingerRejectUpload(stringPathPcbEdgeFinger);
            }
            if (connectorRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesConnector = connectorRejectUpload.getBytes();
                    Path pathConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_connector_" + connectorRejectUpload.getOriginalFilename());
                    Files.write(pathConnector, bytesConnector);
                    stringPathConnector = pathConnector.toString();
                    LOGGER.info("pathConnector : " + pathConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setConnectorRejectUpload(stringPathConnector);
            }
            if (dutSocketsRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesDutSockets = dutSocketsRejectUpload.getBytes();
                    Path pathDutSockets = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_dutSockets_" + dutSocketsRejectUpload.getOriginalFilename());
                    Files.write(pathDutSockets, bytesDutSockets);
                    stringPathDutSockets = pathDutSockets.toString();
                    LOGGER.info("pathDutSockets : " + pathDutSockets);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setDutSocketsRejectUpload(stringPathDutSockets);
            }
            if (edgeMbBananaRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesEdgeMbBanana = edgeMbBananaRejectUpload.getBytes();
                    Path pathEdgeMbBanana = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_edgeMbBanana_" + edgeMbBananaRejectUpload.getOriginalFilename());
                    Files.write(pathEdgeMbBanana, bytesEdgeMbBanana);
                    stringPathEdgeMbBanana = pathEdgeMbBanana.toString();
                    LOGGER.info("pathEdgeMbBanana : " + pathEdgeMbBanana);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setEdgeMbBananaRejectUpload(stringPathEdgeMbBanana);
            }
            if (electComponentRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesElectComponent = electComponentRejectUpload.getBytes();
                    Path pathElectComponent = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_electComponent_" + electComponentRejectUpload.getOriginalFilename());
                    Files.write(pathElectComponent, bytesElectComponent);
                    stringPathElectComponent = pathElectComponent.toString();
                    LOGGER.info("pathElectComponent : " + pathElectComponent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setElectComponentRejectUpload(stringPathElectComponent);
            }
            if (solderJointRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesSolderJoint = solderJointRejectUpload.getBytes();
                    Path pathSolderJoint = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_solderJoint_" + solderJointRejectUpload.getOriginalFilename());
                    Files.write(pathSolderJoint, bytesSolderJoint);
                    stringPathSolderJoint = pathSolderJoint.toString();
                    LOGGER.info("pathSolderJoint : " + pathSolderJoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setSolderJointRejectUpload(stringPathSolderJoint);
            }

            if (winConnectorRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesWinConnector = winConnectorRejectUpload.getBytes();
                    Path pathWinConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_winConnector_" + winConnectorRejectUpload.getOriginalFilename());
                    Files.write(pathWinConnector, bytesWinConnector);
                    stringPathWinConnector = pathWinConnector.toString();
                    LOGGER.info("pathWinConnector : " + pathWinConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setWinConnectorRejectUpload(stringPathWinConnector);
            }
            if (teflonConnectorRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesTeflonConnector = teflonConnectorRejectUpload.getBytes();
                    Path pathTeflonConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_teflonConnector_" + teflonConnectorRejectUpload.getOriginalFilename());
                    Files.write(pathTeflonConnector, bytesTeflonConnector);
                    stringPathTeflonConnector = pathTeflonConnector.toString();
                    LOGGER.info("pathTeflonConnector : " + pathTeflonConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setTeflonConnectorRejectUpload(stringPathTeflonConnector);
            }
            if (pogoReceptaclesPinRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesPogoConnector = pogoReceptaclesPinRejectUpload.getBytes();
                    Path pathPogoConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_pogoReceptaclesPin_" + pogoReceptaclesPinRejectUpload.getOriginalFilename());
                    Files.write(pathPogoConnector, bytesPogoConnector);
                    stringPathPogoReceptaclesPin = pathPogoConnector.toString();
                    LOGGER.info("pathPogoConnector : " + pathPogoConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setPogoReceptaclesPinRejectUpload(stringPathPogoReceptaclesPin);
            }
            if (cableWiredCopperWireRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesCableConnector = cableWiredCopperWireRejectUpload.getBytes();
                    Path pathCableConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_cableWiredCopperWire_" + cableWiredCopperWireRejectUpload.getOriginalFilename());
                    Files.write(pathCableConnector, bytesCableConnector);
                    stringPathCableWiredCopperWire = pathCableConnector.toString();
                    LOGGER.info("pathCableConnector : " + pathCableConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setCableWiredCopperWireRejectUpload(stringPathCableWiredCopperWire);
            }
            if (labelIdentificationRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesLabelConnector = labelIdentificationRejectUpload.getBytes();
                    Path pathLabelConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_labelIdentification_" + labelIdentificationRejectUpload.getOriginalFilename());
                    Files.write(pathLabelConnector, bytesLabelConnector);
                    stringPathLabelIdentification = pathLabelConnector.toString();
                    LOGGER.info("pathLabelConnector : " + pathLabelConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setLabelIdentificationRejectUpload(stringPathLabelIdentification);
            }
            itemVm.setId(q.getGeneratedKey());
            itemVmD = new RmsBookingVisualInspectionDAO();
            QueryResult q3 = itemVmD.updateItemVisualInspectionForAttachment(itemVm);

            //update Item DB
            String[] groupIdSplit = groupId.split("/");
            String bookingPkid = groupIdSplit[0];
            String mbBookingPkid = groupIdSplit[1];

            RmsBookingHardwareDAO rmsBookingHD = new RmsBookingHardwareDAO();
            int countBookingHwPkid = rmsBookingHD.getCountBookingPkidAndPkidForMotherboard(bookingPkid, mbBookingPkid);
            if (countBookingHwPkid == 1) {
                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                RmsBookingHardware hwBook = new RmsBookingHardware();
                hwBook.setId(MbDetail.getId());
                if ("Fail".equals(finalStatus)) {
                    hwBook.setSubStatus("Failed Visual Inspection (Waiting Maverick CA)");
                } else {
                    hwBook.setSubStatus("Pending Functional Test");
                }
                rmsBookingHD = new RmsBookingHardwareDAO();
                QueryResult qHwBook = rmsBookingHD.updateRmsBookingHardwareSubStatusById(hwBook);
            }

            //add log
            RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
            log.setGroupId(groupId);
            log.setDetail("VM Completed (" + finalStatus + ")");
            log.setCreatedBy(userSession.getFullname());
            RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
            QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

            if ("Fail".equals(finalStatus)) {

                //save to maverick table
                RmsBookingMaverick maverick = new RmsBookingMaverick();
                maverick.setGroupId(groupId);
                maverick.setModule(hwStatus);
                maverick.setSubmodule("Visual Inspection");
                maverick.setStatus("Failed Visual Inspection");
                maverick.setFlag("0");
                maverick.setCreatedBy(userSession.getFullname());
                RmsBookingMaverickDAO maverickD = new RmsBookingMaverickDAO();
                QueryResult maverickAdd = maverickD.insertRmsBookingMaverick(maverick);

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

                RmsBookingDetailDAO rmsBookingD = new RmsBookingDetailDAO();
                RmsBookingDetail rmsBooking = rmsBookingD.getRmsBookingDetailByBookingPkid(bookingPkid);

                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                //send INFORMATION email
                LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        hwStatus + " - Failed Visual Inspection", //subject
                        "<br />"
                        + "Please be informed that the item below failed the visual inspection."
                        + "<br /> "
                        + "<br /> "
                        + "RMS No: " + rmsBooking.getRmsNo()
                        + "<br /> "
                        + "Event: " + rmsBooking.getEvent()
                        + "<br /> "
                        + "Motherboard ID: " + MbDetail.getItemId()
                        + "<br /> "
                        + "Inspection Date: " + formattedString
                        + "<br /> "
                        + "<br /> "
                        + "Detail: <br />" + emailBodyFail
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetailUnloading/groupDetail/" + groupId + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("error", "Visual Inspection Fail. Pls go to Maverick Module for Corrective Action.");
                return "redirect:/rmsbookingDetailUnloading/groupDetail/" + groupId;
            } else {
                redirectAttrs.addFlashAttribute("success", "Visual Inspection Pass.");
                return "redirect:/rmsbookingDetailUnloading/groupDetail/" + groupId;
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to save Visual Inspection. Pls Contact System Admin");
            return "redirect:/rmsbookingDetailUnloading/groupDetail/" + groupId;
        }
    }

    @RequestMapping(value = "/vm/downloadAttach/{id}/{type}", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletRequest request,
            @PathVariable("type") String type,
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        RmsBookingVisualInspectionDAO vmD = new RmsBookingVisualInspectionDAO();
        RmsBookingVisualInspection item = vmD.getRmsBookingVisualInspection(id);

        String attachment = "";
        switch (type) {
            case "pcb":
                attachment = item.getPcbRejectUpload();
                break;
            case "handle":
                attachment = item.getHandleRejectUpload();
                break;
            case "metalFrame":
                attachment = item.getMetalFrameRejectUpload();
                break;
            case "hardwareFasterners":
                attachment = item.getHardwareFasternersRejectUpload();
                break;
            case "clipHolder":
                attachment = item.getClipHolderRejectUpload();
                break;
            case "pcbEdgeFinger":
                attachment = item.getPcbEdgeFingerRejectUpload();
                break;
            case "connector":
                attachment = item.getConnectorRejectUpload();
                break;
            case "dutSockets":
                attachment = item.getDutSocketsRejectUpload();
                break;
            case "edgeMbBanana":
                attachment = item.getEdgeMbBananaRejectUpload();
                break;
            case "electComponent":
                attachment = item.getElectComponentRejectUpload();
                break;
            case "solderJoint":
                attachment = item.getSolderJointRejectUpload();
                break;
            case "winConnector":
                attachment = item.getWinConnectorRejectUpload();
                break;
            case "teflonConnector":
                attachment = item.getTeflonConnectorRejectUpload();
                break;
            case "pogoReceptaclesPin":
                attachment = item.getPogoReceptaclesPinRejectUpload();
                break;
            case "cableWiredCopperWire":
                attachment = item.getCableWiredCopperWireRejectUpload();
                break;
            case "labelIdentification":
                attachment = item.getLabelIdentificationRejectUpload();
                break;
            default:
                attachment = "";
                break;
        }

        // construct the complete absolute path of the file
        String fullPath = attachment;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = servletContext.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String bookingPkid,
            @RequestParam(required = false) String rmsNo,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String device,
            @RequestParam(required = false) String packages,
            @RequestParam(required = false) String eventStartDate,
            @RequestParam(required = false) String rmsStatus,
            @RequestParam(required = false) String eventBeginStatus,
            @RequestParam(required = false) String eventEndStatus,
            @RequestParam(required = false) String noCurrentFtp,
            @RequestParam(required = false) String equipmentLocation,
            @RequestParam(required = false) String estStartDate,
            @RequestParam(required = false) String actStartDate,
            @RequestParam(required = false) String daysToEventStart,
            @RequestParam(required = false) String folFilename,
            @RequestParam(required = false) String totalBooking,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String priorityRemarks,
            @RequestParam(required = false) String priorityBy,
            @RequestParam(required = false) String priorityDate,
            @RequestParam(required = false) String flag
    ) {
        RmsBookingDetail rmsbookingDetail = new RmsBookingDetail();
        rmsbookingDetail.setBookingPkid(bookingPkid);
        rmsbookingDetail.setRmsNo(rmsNo);
        rmsbookingDetail.setEvent(event);
        rmsbookingDetail.setDevice(device);
        rmsbookingDetail.setPackages(packages);
        rmsbookingDetail.setEventStartDate(eventStartDate);
        rmsbookingDetail.setRmsStatus(rmsStatus);
        rmsbookingDetail.setEventBeginStatus(eventBeginStatus);
        rmsbookingDetail.setEventEndStatus(eventEndStatus);
        rmsbookingDetail.setNoCurrentFtp(noCurrentFtp);
        rmsbookingDetail.setEquipmentLocation(equipmentLocation);
        rmsbookingDetail.setEstStartDate(estStartDate);
        rmsbookingDetail.setActStartDate(actStartDate);
        rmsbookingDetail.setDaysToEventStart(daysToEventStart);
        rmsbookingDetail.setFolFilename(folFilename);
        rmsbookingDetail.setTotalBooking(totalBooking);
        rmsbookingDetail.setCreatedDate(createdDate);
        rmsbookingDetail.setModifiedDate(modifiedDate);
        rmsbookingDetail.setStatus(status);
        rmsbookingDetail.setPriority(priority);
        rmsbookingDetail.setPriorityRemarks(priorityRemarks);
        rmsbookingDetail.setPriorityBy(priorityBy);
        rmsbookingDetail.setPriorityDate(priorityDate);
        rmsbookingDetail.setFlag(flag);
        RmsBookingDetailDAO rmsbookingDetailDAO = new RmsBookingDetailDAO();
        QueryResult queryResult = rmsbookingDetailDAO.insertRmsBookingDetail(rmsbookingDetail);
        args = new String[1];
        args[0] = bookingPkid + " - " + rmsNo;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("rmsbookingDetail", rmsbookingDetail);
            return "rmsbookingDetail/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/rmsbookingDetail/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{rmsbookingDetailId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("rmsbookingDetailId") String rmsbookingDetailId
    ) {
        RmsBookingDetailDAO rmsbookingDetailDAO = new RmsBookingDetailDAO();
        RmsBookingDetail rmsbookingDetail = rmsbookingDetailDAO.getRmsBookingDetail(rmsbookingDetailId);
        model.addAttribute("rmsbookingDetail", rmsbookingDetail);
        return "rmsbookingDetail/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String bookingPkid,
            @RequestParam(required = false) String rmsNo,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String device,
            @RequestParam(required = false) String packages,
            @RequestParam(required = false) String eventStartDate,
            @RequestParam(required = false) String rmsStatus,
            @RequestParam(required = false) String eventBeginStatus,
            @RequestParam(required = false) String eventEndStatus,
            @RequestParam(required = false) String noCurrentFtp,
            @RequestParam(required = false) String equipmentLocation,
            @RequestParam(required = false) String estStartDate,
            @RequestParam(required = false) String actStartDate,
            @RequestParam(required = false) String daysToEventStart,
            @RequestParam(required = false) String folFilename,
            @RequestParam(required = false) String totalBooking,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String priorityRemarks,
            @RequestParam(required = false) String priorityBy,
            @RequestParam(required = false) String priorityDate,
            @RequestParam(required = false) String flag
    ) {
        RmsBookingDetail rmsbookingDetail = new RmsBookingDetail();
        rmsbookingDetail.setId(id);
        rmsbookingDetail.setBookingPkid(bookingPkid);
        rmsbookingDetail.setRmsNo(rmsNo);
        rmsbookingDetail.setEvent(event);
        rmsbookingDetail.setDevice(device);
        rmsbookingDetail.setPackages(packages);
        rmsbookingDetail.setEventStartDate(eventStartDate);
        rmsbookingDetail.setRmsStatus(rmsStatus);
        rmsbookingDetail.setEventBeginStatus(eventBeginStatus);
        rmsbookingDetail.setEventEndStatus(eventEndStatus);
        rmsbookingDetail.setNoCurrentFtp(noCurrentFtp);
        rmsbookingDetail.setEquipmentLocation(equipmentLocation);
        rmsbookingDetail.setEstStartDate(estStartDate);
        rmsbookingDetail.setActStartDate(actStartDate);
        rmsbookingDetail.setDaysToEventStart(daysToEventStart);
        rmsbookingDetail.setFolFilename(folFilename);
        rmsbookingDetail.setTotalBooking(totalBooking);
        rmsbookingDetail.setCreatedDate(createdDate);
        rmsbookingDetail.setModifiedDate(modifiedDate);
        rmsbookingDetail.setStatus(status);
        rmsbookingDetail.setPriority(priority);
        rmsbookingDetail.setPriorityRemarks(priorityRemarks);
        rmsbookingDetail.setPriorityBy(priorityBy);
        rmsbookingDetail.setPriorityDate(priorityDate);
        rmsbookingDetail.setFlag(flag);
        RmsBookingDetailDAO rmsbookingDetailDAO = new RmsBookingDetailDAO();
        QueryResult queryResult = rmsbookingDetailDAO.updateRmsBookingDetail(rmsbookingDetail);
        args = new String[1];
        args[0] = bookingPkid + " - " + rmsNo;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/rmsbookingDetail/edit/" + id;
    }

    @RequestMapping(value = "/delete/{rmsbookingDetailId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("rmsbookingDetailId") String rmsbookingDetailId
    ) {
        RmsBookingDetailDAO rmsbookingDetailDAO = new RmsBookingDetailDAO();
        RmsBookingDetail rmsbookingDetail = rmsbookingDetailDAO.getRmsBookingDetail(rmsbookingDetailId);
        rmsbookingDetailDAO = new RmsBookingDetailDAO();
        QueryResult queryResult = rmsbookingDetailDAO.deleteRmsBookingDetail(rmsbookingDetailId);
        args = new String[1];
        args[0] = rmsbookingDetail.getBookingPkid() + " - " + rmsbookingDetail.getRmsNo();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/rmsbookingDetail";
    }

    @RequestMapping(value = "/view/{rmsbookingDetailId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("rmsbookingDetailId") String rmsbookingDetailId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/rmsbookingDetail/viewRmsBookingDetailPdf/" + rmsbookingDetailId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/rmsbookingDetail";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.rmsbookingDetail");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewRmsBookingDetailPdf/{rmsbookingDetailId}", method = RequestMethod.GET)
    public ModelAndView viewRmsBookingDetailPdf(
            Model model,
            @PathVariable("rmsbookingDetailId") String rmsbookingDetailId
    ) {
        RmsBookingDetailDAO rmsbookingDetailDAO = new RmsBookingDetailDAO();
        RmsBookingDetail rmsbookingDetail = rmsbookingDetailDAO.getRmsBookingDetail(rmsbookingDetailId);
        return new ModelAndView("rmsbookingDetailPdf", "rmsbookingDetail", rmsbookingDetail);
    }

    @RequestMapping(value = "/ftest/save/{jenis}", method = {RequestMethod.GET, RequestMethod.POST})
    public String bookingFunctionalTest(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("jenis") String jenis,
            //            @PathVariable("bookId") String bookId,
            //            @PathVariable("itemPkid") String itemPkid,
            @RequestParam(required = false) String bookId,
            @RequestParam(required = false) String motherboardId,
            @RequestParam(required = false) String itemPkid,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String totalQty,
            @RequestParam(required = false) String bibResult,
            @RequestParam(required = false) MultipartFile bibUpload,
            @RequestParam(required = false) String leakResult,
            @RequestParam(required = false) MultipartFile leakUpload,
            @RequestParam(required = false) String psResult,
            @RequestParam(required = false) MultipartFile psUpload,
            @RequestParam(required = false) String winResult,
            @RequestParam(required = false) MultipartFile winUpload,
            @RequestParam(required = false) String bibDaqResult,
            @RequestParam(required = false) MultipartFile bibDaqUpload,
            @RequestParam(required = false) String leakHardware,
            @RequestParam(required = false) String bibHardware,
            @RequestParam(required = false) String bibDaqHardware,
            @RequestParam(required = false) String psHardware,
            @RequestParam(required = false) String winHardware,
            HttpServletResponse response
    ) throws IOException {

        String gotoMn = "Pending Functional Test - Manual Test";
        String gotoBib = "Pending Functional Test - BIB Test";
        String gotoDaq = "Pending Functional Test - BIB DAQ Test";
        String gotoPS = "Pending Functional Test - Power Supply Leakage Test";
        String gotoWin = "Pending Functional Test - Winchester Chamber Leakage Test";
        String goReady = "Pending Release to Production";

        String checkLeak = "No";
        String checkManual = "No";
        String checkBib = "No";
        String checkDaq = "No";
        String checkPs = "No";
        String checkWin = "No";
        String linkUpload = "";

        String itemIdMB = "";
        String mbSptsPkid = "";
        String itemIdLC = "";
        String groupId = bookId + "/" + motherboardId;

        String username = userSession.getFullname();
        String newStatus = "";
        String latestResult = "";
        String target_location = "redirect:/rmsbookingDetail/groupDetail/" + bookId + "/" + motherboardId;

        RmsBookingHardwareDAO bookdao = new RmsBookingHardwareDAO();
        Integer checkMb = bookdao.checkMotherboardData(bookId);
        bookdao = new RmsBookingHardwareDAO();
        Integer checkLc = bookdao.checkCardData(bookId);

        if (checkMb == 0) {
            redirectAttrs.addFlashAttribute("error", "No motherboard configured");
        } else {
            bookdao = new RmsBookingHardwareDAO();
            mbSptsPkid = bookdao.getSptsPkidForItemIdMb(bookId, motherboardId);
            ItemDAO itemdao = new ItemDAO();
            itemIdMB = itemdao.getMibItemIdBySptsPkId(mbSptsPkid);
            ItemActivityConfigDAO itemactdao = new ItemActivityConfigDAO();
            ItemActivityConfig itemactmb = itemactdao.getItemActivityByItemId(itemIdMB);
            if (itemactmb != null) {
                checkLeak = itemactmb.getLeakageTest();
                checkPs = itemactmb.getPsLeakageTest();
                checkWin = itemactmb.getWinchesterChamberLeakageTest();
                model.addAttribute("configMotherboard", "");
            } else {
                model.addAttribute("configMotherboard", "TRIGGERERROR");
                model.addAttribute("itemIdMB", itemIdMB);
                model.addAttribute("itemIdLC", itemIdLC);
                redirectAttrs.addFlashAttribute("error", "No motherboard configuration configured");

                checkLeak = "No";
                checkManual = "No";
                checkBib = "No";
                checkDaq = "No";
                checkPs = "No";
                checkWin = "No";
            }

            if (checkLc == 0) {
                // SINI TAKDE LC
                redirectAttrs.addFlashAttribute("error", "No load card configured");
            } else {
                // SINI DUA2 ADA
                bookdao = new RmsBookingHardwareDAO();
                itemIdLC = bookdao.getSptsPkidForItemIdLC(bookId);
                itemactdao = new ItemActivityConfigDAO();
                ItemActivityConfig itemactlc = itemactdao.getItemActivityByItemId(itemIdLC);

                if (itemactlc != null) {
                    checkBib = itemactlc.getBibTest();
                    checkDaq = itemactlc.getBibDaqTest();
                    checkManual = itemactlc.getManualTest();
                } else {
                    redirectAttrs.addFlashAttribute("error", "No load card configuration configured");
                }
            }
            model.addAttribute("itemIdMB", itemIdMB);
            model.addAttribute("itemIdLC", itemIdLC);
        }

        checkInsertFunctionalTestResult(groupId, userSession.getLoginId());

        if (jenis.equals("leakTest")) {
            if (leakUpload != null) {
                try {
                    byte[] bytesConnector = leakUpload.getBytes();
                    Path pathConnector = Paths.get(FOLDER_TEST_BL + "_leakageTest_" + leakUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                    if (leakUpload.getOriginalFilename() == null || leakUpload.getOriginalFilename().equalsIgnoreCase("")) {
                        //
                    } else {
                        Files.write(pathConnector, bytesConnector);
                        linkUpload = pathConnector.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (leakResult.equals("Fail")) {
                // INSERT MASUK KE MAVERICK
                // UPDATE rms_booking_hardware status by itemPkidMb
                // UPDATE rms_booking_hardware_group by hardwareId
                saveToMaverickFunctionalTest("Leakage", username, groupId, leakHardware);
                newStatus = "Failed Functional Test - Leakage Test";
            } else {
                if (checkManual.equals("Yes")) {
                    newStatus = gotoMn;
                } else if (checkBib.equals("Yes")) {
                    newStatus = gotoBib;
                } else if (checkDaq.equals("Yes")) {
                    newStatus = gotoDaq;
                } else if (checkPs.equals("Yes")) {
                    newStatus = gotoPS;
                } else if (checkWin.equals("Yes")) {
                    newStatus = gotoWin;
                } else {
                    newStatus = goReady;
                }
                // SINI PASS MACAM BIASA, UPDATE THE STATUS to next Functional Test
                RmsBookingHardware bookHardware = new RmsBookingHardware();
                bookHardware.setBookingPkid(bookId);
                bookHardware.setPkid(motherboardId);
                bookHardware.setSubStatus(newStatus);
                RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
                booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            }
            RmsBookingFunctionalTest ftest = new RmsBookingFunctionalTest();
            ftest.setFinalStatus(newStatus);
            ftest.setLeakHwid(leakHardware);
            ftest.setLeakQty(totalQty);
            ftest.setLeakStatus(leakResult);
            ftest.setLeakUpload(linkUpload);
            ftest.setRemark("");
            ftest.setFlag("0");
            ftest.setGroupId(groupId);
            RmsBookingFunctionalTestDAO ftestdao = new RmsBookingFunctionalTestDAO();
            ftestdao.updateLeakageTest(ftest);
        } else if (jenis.equals("manTest")) {

        } else if (jenis.equals("bibTest")) {
            if (bibUpload != null) {
                try {
                    byte[] bytesConnector = bibUpload.getBytes();
                    Path pathConnector = Paths.get(FOLDER_TEST_BL + "_bibTest_" + bibUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                    if (bibUpload.getOriginalFilename() == null || bibUpload.getOriginalFilename().equalsIgnoreCase("")) {
                        //
                    } else {
                        Files.write(pathConnector, bytesConnector);
                        linkUpload = pathConnector.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bibResult.equals("Fail")) {
                saveToMaverickFunctionalTest("BIB", username, groupId, bibHardware);
                newStatus = "Failed Functional Test - BIB Test";
            } else {
                if (checkDaq.equals("Yes")) {
                    newStatus = gotoDaq;
                } else if (checkPs.equals("Yes")) {
                    newStatus = gotoPS;
                } else if (checkWin.equals("Yes")) {
                    newStatus = gotoWin;
                } else {
                    newStatus = goReady;
                }
                // UPDATE THE STATUS
                RmsBookingHardware bookHardware = new RmsBookingHardware();
                bookHardware.setBookingPkid(bookId);
                bookHardware.setPkid(motherboardId);
                bookHardware.setSubStatus(newStatus);
                RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
                booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            }
            RmsBookingFunctionalTest ftest = new RmsBookingFunctionalTest();
            ftest.setFinalStatus(newStatus);
            ftest.setBibHwid(bibHardware);
            ftest.setBibQty(totalQty);
            ftest.setBibStatus(bibResult);
            ftest.setBibUpload(linkUpload);
            ftest.setRemark("");
            ftest.setFlag("0");
            ftest.setGroupId(groupId);
            RmsBookingFunctionalTestDAO ftestdao = new RmsBookingFunctionalTestDAO();
            ftestdao.updateBibTest(ftest);
        } else if (jenis.equals("bibDaqTest")) {
            if (bibDaqUpload != null) {
                try {
                    byte[] bytesConnector = bibDaqUpload.getBytes();
                    Path pathConnector = Paths.get(FOLDER_TEST_BL + "_bibDaqTest_" + bibDaqUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                    if (bibDaqUpload.getOriginalFilename() == null || bibDaqUpload.getOriginalFilename().equalsIgnoreCase("")) {
                        //
                    } else {
                        Files.write(pathConnector, bytesConnector);
                        linkUpload = pathConnector.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bibDaqResult.equals("Fail")) {
                saveToMaverickFunctionalTest("BIBDAQ", username, groupId, bibDaqHardware);
                newStatus = "Failed Functional Test - BIB DAQ Test";
            } else {
                if (checkPs.equals("Yes")) {
                    newStatus = gotoPS;
                } else if (checkWin.equals("Yes")) {
                    newStatus = gotoWin;
                } else {
                    newStatus = goReady;
                }
                // UPDATE THE STATUS
                RmsBookingHardware bookHardware = new RmsBookingHardware();
                bookHardware.setBookingPkid(bookId);
                bookHardware.setPkid(motherboardId);
                bookHardware.setSubStatus(newStatus);
                RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
                booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            }
            RmsBookingFunctionalTest ftest = new RmsBookingFunctionalTest();
            ftest.setFinalStatus(newStatus);
            ftest.setBibDaqHwid(bibDaqHardware);
            ftest.setBibDaqQty(totalQty);
            ftest.setBibDaqStatus(bibDaqResult);
            ftest.setBibDaqUpload(linkUpload);
            ftest.setRemark("");
            ftest.setFlag("0");
            ftest.setGroupId(groupId);
            RmsBookingFunctionalTestDAO ftestdao = new RmsBookingFunctionalTestDAO();
            ftestdao.updateBibDaqTest(ftest);
        } else if (jenis.equals("psTest")) {
            if (psUpload != null) {
                try {
                    byte[] bytesConnector = psUpload.getBytes();
                    Path pathConnector = Paths.get(FOLDER_TEST_BL + "_psTest_" + psUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                    if (psUpload.getOriginalFilename() == null || psUpload.getOriginalFilename().equalsIgnoreCase("")) {
                        //
                    } else {
                        Files.write(pathConnector, bytesConnector);
                        linkUpload = pathConnector.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (psResult.equals("Fail")) {
                saveToMaverickFunctionalTest("Power", username, groupId, psHardware);
                newStatus = "Failed Functional Test - Power Supply Leakage Test";
            } else {
                if (checkWin.equals("Yes")) {
                    newStatus = gotoWin;
                } else {
                    newStatus = goReady;
                }
                //UPDATE LATEST STATUS
                RmsBookingHardware bookHardware = new RmsBookingHardware();
                bookHardware.setBookingPkid(bookId);
                bookHardware.setPkid(motherboardId);
                bookHardware.setSubStatus(newStatus);
                RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
                booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            }
            RmsBookingFunctionalTest ftest = new RmsBookingFunctionalTest();
            ftest.setFinalStatus(newStatus);
            ftest.setPsHwid(psHardware);
            ftest.setPsQty(totalQty);
            ftest.setPsStatus(psResult);
            ftest.setPsUpload(linkUpload);
            ftest.setRemark("");
            ftest.setFlag("0");
            ftest.setGroupId(groupId);
            RmsBookingFunctionalTestDAO ftestdao = new RmsBookingFunctionalTestDAO();
            ftestdao.updatePowerTest(ftest);
        } else if (jenis.equals("winTest")) {
            if (winUpload != null) {
                try {
                    byte[] bytesConnector = winUpload.getBytes();
                    Path pathConnector = Paths.get(FOLDER_TEST_BL + "_winTest_" + winUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                    if (winUpload.getOriginalFilename() == null || winUpload.getOriginalFilename().equalsIgnoreCase("")) {
                        //
                    } else {
                        Files.write(pathConnector, bytesConnector);
                        linkUpload = pathConnector.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (winResult.equals("Fail")) {
                // MASUK MAVERICK
                saveToMaverickFunctionalTest("Winchester", username, groupId, winHardware);
                newStatus = "Failed Functional Test - Winchester Chamber Leakage Test";
            } else {
                // UPDATE STATUS
                newStatus = goReady;
                // UPDATE LATEST STATUS
                RmsBookingHardware bookHardware = new RmsBookingHardware();
                bookHardware.setBookingPkid(bookId);
                bookHardware.setPkid(motherboardId);
                bookHardware.setSubStatus(newStatus);
                RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
                booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            }
            RmsBookingFunctionalTest ftest = new RmsBookingFunctionalTest();
            ftest.setFinalStatus(newStatus);
            ftest.setWinHwid(winHardware);
            ftest.setWinQty(totalQty);
            ftest.setWinStatus(winResult);
            ftest.setWinUpload(linkUpload);
            ftest.setRemark("");
            ftest.setFlag("0");
            ftest.setGroupId(groupId);
            RmsBookingFunctionalTestDAO ftestdao = new RmsBookingFunctionalTestDAO();
            ftestdao.updateWinchesterTest(ftest);
        } else {

        }
        return target_location;
    }

    @RequestMapping(value = "/updateStatusFailed/{groupid}", method = RequestMethod.GET)
    public String updateStatusFailed(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("groupid") String groupid) {

        String username = userSession.getFullname();
        String manual = "Manual";
        String status = "Failed Functional Test - Manual Test - Waiting Maverick CA";

        RmsBookingHardwareDAO rmsdao = new RmsBookingHardwareDAO();
        String lcItemId = rmsdao.getLcMibItemIdFromGroupId(groupid);
        rmsdao = new RmsBookingHardwareDAO();
        String mbItemId = rmsdao.getMbMibItemIdFromGroupId(groupid);

        // UPDATE DATA rms_booking_hardware - START
        RmsBookingHardware rmsbook = new RmsBookingHardware();
        String[] parts = groupid.split("/");
        String bookId = parts[0];
        String pkid = parts[1];

        rmsbook.setBookingPkid(bookId);
        rmsbook.setSubStatus(status);
        rmsbook.setPkid(pkid);
        rmsdao = new RmsBookingHardwareDAO();
        rmsdao.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(rmsbook);
        // UPDATE DATA rms_booking_hardware - END

        // UPDATE DATA rms_functional_test - START
        ManualTestDAO testdao = new ManualTestDAO();
        Integer qty = testdao.getQuantityBeforeLoading(lcItemId);

        RmsBookingFunctionalTest rmsfun = new RmsBookingFunctionalTest();
        rmsfun.setManualQty(String.valueOf(qty));
        rmsfun.setManualStatus("Fail");
        rmsfun.setRemark("");
        rmsfun.setFinalStatus(status);
        rmsfun.setFlag("0");

        RmsBookingFunctionalTestDAO rmsfuncdao = new RmsBookingFunctionalTestDAO();
        rmsfuncdao.updateManualTest(rmsfun);
        // UPDATE DATA rms_functional_test - END

        // PLEASE CHECK IF THERE IS ANYTHING LEFT NOT UPDATED HERE
        // UPDATE MAVERICK INFORMATION FOR MANUAL TEST FAILED
        updateMaverickAndEmail(mbItemId, username, manual);
        return "redirect:/groupDetail/" + groupid;
    }

    // FUNCTION NK DAPATKAN SEMUA MAKLUMAT TEST CONFIG
    private ItemActivityConfig getMaklumatTest(String itemId) {
        ItemActivityConfig item = new ItemActivityConfig();
        ItemActivityConfigDAO itemactdao = new ItemActivityConfigDAO();
        ItemActivityConfig itemact = itemactdao.getItemActivityByItemId(itemId);
        if (itemact == null) {

        } else {
            item.setLeakageTest(itemact.getLeakageTest());
            item.setManualTest(itemact.getManualTest());
            item.setBibTest(itemact.getBibTest());
            item.setBibDaqTest(itemact.getBibDaqTest());
            item.setPsLeakageTest(itemact.getPsLeakageTest());
            item.setWinchesterChamberLeakageTest(itemact.getWinchesterChamberLeakageTest());
        }
        return item;
    }

    public void updateMaverickAndEmail(String mibItemId, String username, String jenis) {

        String module = "Hardware Registration";
        String sub = "";
        String status = "Failed Functional Test";

        switch (jenis) {
            case "Leakage":
                sub = "Leakage Test";
                break;
            case "Manual":
                sub = "Manual Test";
                break;
            case "BIB":
                sub = "BIB Test";
                break;
            case "DAQ":
                sub = "BIB DAQ Test";
                break;
            case "Power":
                sub = "Power Supply Leakage Test";
                break;
            case "Winchester":
                sub = "Winchester Chamber Leakage Test";
                break;
            default:
                break;
        }

        ItemMaverick maverick = new ItemMaverick();
        maverick.setMibItemId(mibItemId);
        maverick.setModule(module);
        maverick.setSubmodule(sub);
        maverick.setStatus(status + " - " + sub);
        maverick.setFlag("0");
        maverick.setCreatedBy(username);
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
        LOGGER.info("######################### START MAVERICK EMAIL ########################### ");
        EmailSender emailSender = new EmailSender();
        emailSender.htmlEmailTable(
                servletContext,
                "", //user name requestor
                to, //to
                //                        emailTo,
                "Hardware Registration - " + status + " [" + sub + "]", //subject
                "<br />"
                + "Please be informed that the hardware below failed the functional test inspection."
                + "<br /> "
                + "<br /> "
                + "Item ID: " + item2.getItemId()
                + "<br /> "
                + "Inspection Date: " + formattedString
                + "<br /> "
                + "<br /> "
                + "Please click <a href=\"http://" + hostname + "/HEATS/hw/item/add2/" + mibItemId + " \">HERE</a> for more detail."
                + "<br /> "
                + "<br />Thank you." //msg
        );

    }

    //function for hw released
    @RequestMapping(value = "/release/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String release(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id) throws IOException {

        LOGGER.info("id: " + id);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        String date1 = formattedDate.substring(0, 10);
        String time = formattedDate.substring(11, 23);
        String completeDateTime = date1 + "T" + time;

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        RmsBookingDetail rms1 = rmsD.getRmsBookingDetail(id);

        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> hardware = rmsHD.getRmsBookingHardwareListByBookingPkidWithFlagZeroAndStatusNotNA(rms1.getBookingPkid());

        for (int i = 0; i < hardware.size(); i++) {

            LOGGER.info("hardware.get(i).getId(): " + hardware.get(i).getId());
            //update movement in SPTS for Item ID first before update HEATS DB
            JSONObject params2 = new JSONObject();
            params2.put("dateTime", completeDateTime);
            params2.put("itemsPKID", hardware.get(i).getItemPkid());
            params2.put("transType", "25");
            params2.put("transQty", hardware.get(i).getQty());
            params2.put("remarks", "Out to Production Staging through HEATS");

            SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);

            if (TransPkid.getResponseId() > 0) {

                //add transaction to DB
                ItemTransaction item = new ItemTransaction();
                item.setSptsPkid(TransPkid.getResponseId().toString());
                item.setItemPkid(hardware.get(i).getItemPkid());
                item.setSiteName("Seremban");
                item.setDateTime(date1 + " " + time);
                item.setTransType("25");
                item.setTransTypeName("Out For Production Staging");
                item.setTransQty(hardware.get(i).getQty());
                item.setTransOutQty(hardware.get(i).getQty());
                item.setRemarks("Out to Production Staging through HEATS");

                ItemTransactionDAO itemD = new ItemTransactionDAO();
                QueryResult qI = itemD.insertItemTransaction(item);

                RmsBookingHardware hardware1 = new RmsBookingHardware();
                hardware1.setId(hardware.get(i).getId());
                hardware1.setFlag("1");
                hardware1.setModifiedBy(userSession.getFullname());
                if ("Motherboard".equals(hardware.get(i).getItemType())) {
                    hardware1.setStatus(hardware.get(i).getStatus());
                    hardware1.setSubStatus("Released to Production");
                } else if ("Load Card".equals(hardware.get(i).getItemType()) || "Program Card".equals(hardware.get(i).getItemType())) {
                    hardware1.setStatus("Released to Production");
                }
                rmsHD = new RmsBookingHardwareDAO();
                QueryResult q2 = rmsHD.updateRmsBookingHardwareForFlagAndStatusById(hardware1);
            } else {
                LOGGER.info("Fail to insert transaction for Item ID: " + hardware.get(i).getItemId());

                String[] to = {"global-rel-it@onsemi.com"};

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        "HW Release to Production - Failed to Insert SPTS Transaction", //subject
                        "<br />"
                        + "Please be informed that the item below failed to insert SPTS transaction (Out to Production Staging)."
                        + "<br /> "
                        + "<br /> "
                        + "RMS No: " + rms1.getRmsNo()
                        + "<br /> "
                        + "Event: " + rms1.getEvent()
                        + "<br /> "
                        + "Item ID: " + hardware.get(i).getItemId()
                        + "<br /> "
                        + "Transaction Date: " + completeDateTime
                        + "<br /> "
                        + "<br /> "
                        + "Detail: Failed to insert SPTS Transaction (Out to Production Staging)"
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + id + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );
            }

        }

        RmsBookingHardwareGroupDAO groupD = new RmsBookingHardwareGroupDAO();
        List<RmsBookingHardwareGroup> group = groupD.getRmsBookingHardwareGroupListByBookingPkid(rms1.getBookingPkid());

        for (int x = 0; x < group.size(); x++) {
            LOGGER.info("group.get(x).getId(): " + group.get(x).getId());

            //update movement in SPTS for Hardware ID first before update HEATS DB
            JSONObject params = new JSONObject();
            params.put("transDate", completeDateTime);
            params.put("itemHardwarePKID", group.get(x).getHardwarePkid());
            params.put("transType", "25");
            params.put("rmsEvent", group.get(x).getRmsNo() + "_" + group.get(x).getEvent());
            params.put("remarks", "Out to Production Staging through HEATS");
            params.put("createdBy", userSession.getFullname());

            SPTSResponse TransHwPkid = SPTSWebService.insertTransactionHwId(params);

            if (TransHwPkid.getResponseId() > 0) {

                //add transaction to item_hardware_movement
                ItemHardwareDAO itemD = new ItemHardwareDAO();
                ItemHardware itemHw = itemD.getItemHardwareByHardwareId(group.get(x).getHardwareId());

                ItemHardwareMovement itemHwMovement = new ItemHardwareMovement();
                itemHwMovement.setMibHardwareId(itemHw.getId());
                itemHwMovement.setSptsPkid(TransHwPkid.getResponseId().toString());
                itemHwMovement.setTransType("25");
                itemHwMovement.setRmsEvent(group.get(x).getRmsNo() + "_" + group.get(x).getEvent());
                itemHwMovement.setCreatedBy(userSession.getFullname());
                ItemHardwareMovementDAO itD = new ItemHardwareMovementDAO();
                QueryResult q2 = itD.insertItemHardwareMovement(itemHwMovement);

                String sptsStatus = "";

                //update table item_hardware
                JSONObject paramsItem = new JSONObject();
                paramsItem.put("pkid", group.get(x).getHardwarePkid());
                JSONArray getRMSBooking = SPTSWebService.getHardwareIdByPKID(paramsItem);
                for (int i = 0; i < getRMSBooking.length(); i++) {

                    ItemHardware itemH = new ItemHardware();
                    itemH.setSptsPkid(group.get(x).getHardwarePkid());
                    itemH.setHardwareId(group.get(x).getHardwareId());
                    //  Scrapped = -1, No_Stock = 0,Good = 1,Production = 2,Repair = 3,Others = 4,Quarantine = 5,External_Cleaning = 6,External_Re_Cleaning = 7,Internal_Cleaning = 8,Internal_Re_Cleaning = 9,
//Storage_Factory = 10,Shipped_To_Other_ON_Semi_Site = 11,Shipped_To_Vendor = 12,Out_For_Production_Staging = 13

//                    sptsStatus = sptsStatus(getRMSBooking.getJSONObject(i).getInt("HardwareStatus"));
                    SPTSStatus spts = new SPTSStatus();
                    sptsStatus = spts.sptsStatus(getRMSBooking.getJSONObject(i).getInt("HardwareStatus"));

                    itemH.setStatus(sptsStatus);
                    if (getRMSBooking.getJSONObject(i).has("ALU")) {
                        Object alu = getRMSBooking.getJSONObject(i).get("ALU");
                        if (alu instanceof String) {
                            itemH.setAlu(getRMSBooking.getJSONObject(i).getString("ALU"));
                        } else {
                            itemH.setAlu(Integer.toString(getRMSBooking.getJSONObject(i).getInt("ALU")));
                        }
                    }
                    if (getRMSBooking.getJSONObject(i).has("RMS_Event")) {
                        Object RMS_Event = getRMSBooking.getJSONObject(i).get("RMS_Event");
                        if (RMS_Event instanceof String) {
                            itemH.setRmsEvent(getRMSBooking.getJSONObject(i).getString("RMS_Event"));
                        } else {
                            itemH.setRmsEvent(Integer.toString(getRMSBooking.getJSONObject(i).getInt("RMS_Event")));
                        }
                    }
                    if (getRMSBooking.getJSONObject(i).has("ShelfTime")) {
                        Object ShelfTime = getRMSBooking.getJSONObject(i).get("ShelfTime");
                        if (ShelfTime instanceof String) {
                            itemH.setShelfTime(getRMSBooking.getJSONObject(i).getString("ShelfTime"));
                        } else {
                            itemH.setShelfTime(Integer.toString(getRMSBooking.getJSONObject(i).getInt("ShelfTime")));
                        }
                    }

                    LOGGER.info("hardwarePKID: " + itemH.getSptsPkid());
                    LOGGER.info("hardware ID: " + itemH.getHardwareId());
                    LOGGER.info("sptsStatus: " + itemH.getStatus());
                    LOGGER.info("ALU: " + itemH.getAlu());
                    LOGGER.info("RMS_Event: " + itemH.getRmsEvent());
                    LOGGER.info("ShelfTime: " + itemH.getShelfTime());

                    itemD = new ItemHardwareDAO();
                    QueryResult ItemDq = itemD.updateItemHardwareFromSPTS(itemH);
                    LOGGER.info("ItemDq.getResult(): " + ItemDq.getResult());
                }

                RmsBookingHardwareGroup group1 = new RmsBookingHardwareGroup();
                group1.setId(group.get(x).getId());
                group1.setStatus("Released to Production");
                group1.setSptsStatus(sptsStatus); //waiting confirmation from JFLim 30.04.26
                group1.setFlag("1");
                groupD = new RmsBookingHardwareGroupDAO();
                QueryResult q3 = groupD.updateRmsBookingHardwareGroupStatusAndSptsStatusAndFlag(group1);

                //add log
                RmsBookingHardwareGroupLog log2 = new RmsBookingHardwareGroupLog();
                log2.setGroupId(group.get(x).getGroupId());
                log2.setDetail("Released to Production: " + group.get(x).getHardwareId());
                log2.setCreatedBy(userSession.getFullname());
                RmsBookingHardwareGroupLogDAO logD2 = new RmsBookingHardwareGroupLogDAO();
                QueryResult logQ2 = logD2.insertRmsBookingHardwareGroupLog(log2);

            } else {
                LOGGER.info("Fail to insert transaction for Hardware ID: " + group.get(x).getHardwareId());

                String[] to = {"global-rel-it@onsemi.com"};

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        "HW Release to Production - Failed to Insert SPTS Transaction", //subject
                        "<br />"
                        + "Please be informed that the Hardware ID below failed to insert SPTS transaction (Out to Production Staging)."
                        + "<br /> "
                        + "<br /> "
                        + "RMS No: " + rms1.getRmsNo()
                        + "<br /> "
                        + "Event: " + rms1.getEvent()
                        + "<br /> "
                        + "Hardware ID: " + group.get(x).getHardwareId()
                        + "<br /> "
                        + "Transaction Date: " + completeDateTime
                        + "<br /> "
                        + "<br /> "
                        + "Detail: Failed to insert SPTS Transaction (Out to Production Staging)"
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + id + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );
            }

        }

        //update status
        RmsBookingDetail rms = new RmsBookingDetail();
        rms.setId(id);
        rms.setStatus("Released to Production");
        rms.setFlag("1");
        rmsD = new RmsBookingDetailDAO();
        QueryResult q = rmsD.updateRmsBookingDetailForStatusAndFlag(rms);
        if (q.getResult() > 0) {

            //update log
            RmsBookingLog log = new RmsBookingLog();
            log.setBookingId(id);
            log.setDetail("Released to Production");
            log.setCreatedBy(userSession.getFullname());
            RmsBookingLogDAO logD = new RmsBookingLogDAO();
            QueryResult logQ = logD.insertRmsBookingLog(log);

            redirectAttrs.addFlashAttribute("success", "Successfully Release to Production");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to Release to Production. Pls contact system admin.");
        }
        return "redirect:/rmsbookingDetail/rmsReleased/detail/" + id;
//          return "redirect:/rmsbookingDetail";
    }

    @RequestMapping(value = "/rmsReleased", method = RequestMethod.GET)
    public String rmsReleased(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws IOException {

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        List<RmsBookingDetail> booking = rmsD.getRmsBookingDetailListReleased();

        model.addAttribute("booking", booking);

        rmsD = new RmsBookingDetailDAO();
        int countBooking = rmsD.getCountBookingReleasedProduction();

        model.addAttribute("countBooking", countBooking);

        return "rmsbookingDetail/rms_released";
    }

    @RequestMapping(value = "/rmsReleased/detail/{id}", method = RequestMethod.GET)
    public String rmsReleasedDetail(Model model,
            @PathVariable("id") String id,
            @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

        RmsBookingDetailDAO rmsd = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsd.getRmsBookingDetail(id);
        model.addAttribute("rms", rms);

        //add hardware detail from spts
        int bookingPkid = Integer.parseInt(rms.getBookingPkid());

        //get motherboard detail
        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> BibList = rmsHD.getRmsBookingHardwareListForMotherboardByBookingPkid(Integer.toString(bookingPkid));
        model.addAttribute("BibList", BibList);

        //get other hw detail
        rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> otherList = rmsHD.getRmsBookingHardwareListForOtherHwByBookingPkid(Integer.toString(bookingPkid));
        model.addAttribute("otherList", otherList);

        //get all hw detail for request replacement form
        rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> hwList = rmsHD.getRmsBookingHardwareListByBookingPkidWithFlagZeroForHwReplacement(Integer.toString(bookingPkid));
        model.addAttribute("hwList", hwList);

        RmsBookingDetailHwReplacementDAO hwReplaceD = new RmsBookingDetailHwReplacementDAO();
        List<RmsBookingDetailHwReplacement> listHwReplace = hwReplaceD.getRmsBookingDetailHwReplacementListByBookingPkid(Integer.toString(bookingPkid));
        model.addAttribute("listHwReplace", listHwReplace);

        hwReplaceD = new RmsBookingDetailHwReplacementDAO();
        int countHwReplace = hwReplaceD.getCountBookingId(Integer.toString(bookingPkid));
        model.addAttribute("countHwReplace", countHwReplace);

        hwReplaceD = new RmsBookingDetailHwReplacementDAO();
        int countHwReplaceFlagZero = hwReplaceD.getCountFlagZero();
        model.addAttribute("countHwReplaceFlagZero", countHwReplaceFlagZero);

        //get booking remarks
        rmsHD = new RmsBookingHardwareDAO();
        int countRemarks = rmsHD.getCountHwWithRemarksByBookingPkid(Integer.toString(bookingPkid));
        if (countRemarks == 0) {
            model.addAttribute("rmsRemarks", "");
        } else {
            rmsHD = new RmsBookingHardwareDAO();
            RmsBookingHardware rmsRemarks = rmsHD.getRmsBookingHardwareRemarksByBookingPkid(Integer.toString(bookingPkid));
            model.addAttribute("rmsRemarks", rmsRemarks.getItemId());
        }

        return "rmsbookingDetail/detail_released";
    }

    @RequestMapping(value = "/rmsReleased/groupDetail/{bookingId}/{itemPkid}", method = RequestMethod.GET)
    public String rmsReleasedGroupDetail(Model model,
            @PathVariable("bookingId") String bookingId,
            @PathVariable("itemPkid") String itemPkid,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession) throws IOException {

        String currentStatus = "";
        String leakTest = "";
        String manTest = "";
        String bibTest = "";
        String daqTest = "";
        String psTest = "";
        String winTest = "";
        String groupId = bookingId + "/" + itemPkid;
        model.addAttribute("groupId", groupId);
        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

        RmsBookingDetailDAO rmsd = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsd.getRmsBookingDetailByBookingPkid(bookingId);
        model.addAttribute("rms", rms);

        RmsBookingHardwareDAO hD = new RmsBookingHardwareDAO();
        RmsBookingHardware h = hD.getRmsBookingHardwareByPkid(itemPkid);
        model.addAttribute("motherboardId", h.getItemId());
        model.addAttribute("subStatus", h.getSubStatus());

        RmsBookingHardwareGroupDAO h2D = new RmsBookingHardwareGroupDAO();
        List<RmsBookingHardwareGroup> hwGroupList = h2D.getRmsBookingHardwareGroupListByGroupId(groupId);
        model.addAttribute("hwGroupList", hwGroupList);

        //get booking remarks
        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        int countRemarks = rmsHD.getCountHwWithRemarksByBookingPkid(bookingId);
        if (countRemarks == 0) {
            model.addAttribute("rmsRemarks", "");
        } else {
            rmsHD = new RmsBookingHardwareDAO();
            RmsBookingHardware rmsRemarks = rmsHD.getRmsBookingHardwareRemarksByBookingPkid(bookingId);
            model.addAttribute("rmsRemarks", rmsRemarks.getItemId());
        }

        currentStatus = h.getSubStatus();

        if (currentStatus.equalsIgnoreCase("Pending Functional Test")) {
            // CHECK AND UPDATE THE FIRST TEST
//            currentStatus = checkStatusFTestBeforeLoading(bookingId, itemPkid);

            String itemIdMB = "";
            String mbSptsPkid = "";
            String itemIdLC = "";

            RmsBookingHardwareDAO bookdao = new RmsBookingHardwareDAO();
            Integer checkMb = bookdao.checkMotherboardData(bookingId);
            bookdao = new RmsBookingHardwareDAO();
            Integer checkLc = bookdao.checkCardData(bookingId);

            if (checkMb == 0) {
                redirectAttrs.addFlashAttribute("error", "No motherboard configured");
            } else {
                // SINI ADA MB
                bookdao = new RmsBookingHardwareDAO();
                mbSptsPkid = bookdao.getSptsPkidForItemIdMb(bookingId, itemPkid);
                ItemDAO itemdao = new ItemDAO();
                itemIdMB = itemdao.getMibItemIdBySptsPkId(mbSptsPkid);
                ItemActivityConfigDAO itemactdao = new ItemActivityConfigDAO();
                ItemActivityConfig itemactmb = itemactdao.getItemActivityByItemId(itemIdMB);
                if (itemactmb != null) {
                    leakTest = itemactmb.getLeakageTest();
                    psTest = itemactmb.getPsLeakageTest();
                    winTest = itemactmb.getWinchesterChamberLeakageTest();
                    model.addAttribute("configMotherboard", "");
                } else {
                    model.addAttribute("configMotherboard", "TRIGGERERROR");
                    redirectAttrs.addFlashAttribute("error", "No motherboard configuration configured");
                }

                if (checkLc == 0) {
                    // SINI TAKDE LC
                    redirectAttrs.addFlashAttribute("error", "No load card configured");
                } else {
                    // SINI DUA2 ADA
                    bookdao = new RmsBookingHardwareDAO();
                    itemIdLC = bookdao.getSptsPkidForItemIdLC(bookingId);
                    itemactdao = new ItemActivityConfigDAO();
                    ItemActivityConfig itemactlc = itemactdao.getItemActivityByItemId(itemIdLC);

                    if (itemactlc != null) {
                        bibTest = itemactlc.getBibTest();
                        daqTest = itemactlc.getBibDaqTest();
                        manTest = itemactlc.getManualTest();
                    } else {
                        redirectAttrs.addFlashAttribute("error", "No load card configuration configured");
                    }
                }
                model.addAttribute("itemIdMB", itemIdMB);
                model.addAttribute("itemIdLC", itemIdLC);
            }

            if (leakTest.contains("Yes")) {
                currentStatus = "Pending Functional Test - Leakage Test";
            } else if (manTest.contains("Yes")) {
                currentStatus = "Pending Functional Test - Manual Test";
            } else if (bibTest.contains("Yes")) {
                currentStatus = "Pending Functional Test - BIB Test";
            } else if (daqTest.contains("Yes")) {
                currentStatus = "Pending Functional Test - BIB DAQ Test";
            } else if (psTest.contains("Yes")) {
                currentStatus = "Pending Functional Test - Power Supply Leakage Test";
            } else if (winTest.contains("Yes")) {
                currentStatus = "Pending Functional Test - Winchester Chamber Leakage Test";
            } else {
                currentStatus = "Pending Release to Production";
            }
        } else {
            // DO NOTHING HERE
        }

        model.addAttribute("leakCheck", leakTest);
        model.addAttribute("manCheck", manTest);
        model.addAttribute("bibCheck", bibTest);
        model.addAttribute("bibDaqCheck", daqTest);
        model.addAttribute("psCheck", psTest);
        model.addAttribute("winCheck", winTest);

        ParameterDetailsDAO pDx = new ParameterDetailsDAO();
        List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList("", "016");
        model.addAttribute("bibResultData", bibResultData);

        pDx = new ParameterDetailsDAO();
        List<ParameterDetails> bibDaqResultData = pDx.getGroupParameterDetailList("", "016");
        model.addAttribute("bibDaqResultData", bibDaqResultData);

        pDx = new ParameterDetailsDAO();
        List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList("", "016");
        model.addAttribute("leakResultData", leakResultData);

        pDx = new ParameterDetailsDAO();
        List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList("", "016");
        model.addAttribute("psResultData", psResultData);

        pDx = new ParameterDetailsDAO();
        List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList("", "016");
        model.addAttribute("winResultData", winResultData);

        //vm tab
        RmsBookingVisualInspection itemVm = new RmsBookingVisualInspection();

        RmsBookingVisualInspectionDAO vmD = new RmsBookingVisualInspectionDAO();
        int count = vmD.getCountByGroupIdWithModuleBeforeLoading(groupId);
        if (count == 1) {
            vmD = new RmsBookingVisualInspectionDAO();
            itemVm = vmD.getRmsBookingVisualInspectionByGroupId(groupId);
        }
        model.addAttribute("itemVm", itemVm);

        if (itemVm.getPcbHardwareId() != null && !"".equals(itemVm.getPcbHardwareId())) {
            String[] pcbHardwareIdList = itemVm.getPcbHardwareId().split(",");
            model.addAttribute("valueJsonPcb", new Gson().toJson(pcbHardwareIdList));
        } else {
            model.addAttribute("valueJsonPcb", new Gson().toJson(itemVm.getPcbHardwareId()));
        }
        if (itemVm.getHandleHardwareId() != null && !"".equals(itemVm.getHandleHardwareId())) {
            String[] handleHardwareIdList = itemVm.getHandleHardwareId().split(",");
            model.addAttribute("valueJsonHandle", new Gson().toJson(handleHardwareIdList));
        } else {
            model.addAttribute("valueJsonHandle", new Gson().toJson(itemVm.getHandleHardwareId()));
        }
        if (itemVm.getMetalFrameHardwareId() != null && !"".equals(itemVm.getMetalFrameHardwareId())) {
            String[] metalFrameHardwareIdList = itemVm.getMetalFrameHardwareId().split(",");
            model.addAttribute("valueJsonMetalFrame", new Gson().toJson(metalFrameHardwareIdList));
        } else {
            model.addAttribute("valueJsonMetalFrame", new Gson().toJson(itemVm.getMetalFrameHardwareId()));
        }
        if (itemVm.getHardwareFasternersHardwareId() != null && !"".equals(itemVm.getHardwareFasternersHardwareId())) {
            String[] hardwareFasternersHardwareIdList = itemVm.getHardwareFasternersHardwareId().split(",");
            model.addAttribute("valueJsonHardwareFasterners", new Gson().toJson(hardwareFasternersHardwareIdList));
        } else {
            model.addAttribute("valueJsonHardwareFasterners", new Gson().toJson(itemVm.getHardwareFasternersHardwareId()));
        }
        if (itemVm.getClipHolderHardwareId() != null && !"".equals(itemVm.getClipHolderHardwareId())) {
            String[] clipHolderHardwareIdList = itemVm.getClipHolderHardwareId().split(",");
            model.addAttribute("valueJsonClipHolder", new Gson().toJson(clipHolderHardwareIdList));
        } else {
            model.addAttribute("valueJsonClipHolder", new Gson().toJson(itemVm.getClipHolderHardwareId()));
        }
        if (itemVm.getPcbEdgeFingerHardwareId() != null && !"".equals(itemVm.getPcbEdgeFingerHardwareId())) {
            String[] pcbEdgeFingerHardwareIdList = itemVm.getPcbEdgeFingerHardwareId().split(",");
            model.addAttribute("valueJsonPcbEdgeFinger", new Gson().toJson(pcbEdgeFingerHardwareIdList));
        } else {
            model.addAttribute("valueJsonPcbEdgeFinger", new Gson().toJson(itemVm.getPcbEdgeFingerHardwareId()));
        }
        if (itemVm.getConnectorHardwareId() != null && !"".equals(itemVm.getConnectorHardwareId())) {
            String[] connectorHardwareIdList = itemVm.getConnectorHardwareId().split(",");
            model.addAttribute("valueJsonConnector", new Gson().toJson(connectorHardwareIdList));
        } else {
            model.addAttribute("valueJsonConnector", new Gson().toJson(itemVm.getConnectorHardwareId()));
        }
        if (itemVm.getDutSocketsHardwareId() != null && !"".equals(itemVm.getDutSocketsHardwareId())) {
            String[] dutSocketsHardwareIdList = itemVm.getDutSocketsHardwareId().split(",");
            model.addAttribute("valueJsonDutSockets", new Gson().toJson(dutSocketsHardwareIdList));
        } else {
            model.addAttribute("valueJsonDutSockets", new Gson().toJson(itemVm.getDutSocketsHardwareId()));
        }
        if (itemVm.getEdgeMbBananaHardwareId() != null && !"".equals(itemVm.getEdgeMbBananaHardwareId())) {
            String[] edgeMbBananaHardwareIdList = itemVm.getEdgeMbBananaHardwareId().split(",");
            model.addAttribute("valueJsonEdgeMbBanana", new Gson().toJson(edgeMbBananaHardwareIdList));
        } else {
            model.addAttribute("valueJsonEdgeMbBanana", new Gson().toJson(itemVm.getEdgeMbBananaHardwareId()));
        }
        if (itemVm.getElectComponentHardwareId() != null && !"".equals(itemVm.getElectComponentHardwareId())) {
            String[] electComponentHardwareIdList = itemVm.getElectComponentHardwareId().split(",");
            model.addAttribute("valueJsonElectComponent", new Gson().toJson(electComponentHardwareIdList));
        } else {
            model.addAttribute("valueJsonElectComponent", new Gson().toJson(itemVm.getElectComponentHardwareId()));
        }
        if (itemVm.getSolderJointHardwareId() != null && !"".equals(itemVm.getSolderJointHardwareId())) {
            String[] solderJointHardwareIdList = itemVm.getSolderJointHardwareId().split(",");
            model.addAttribute("valueJsonSolderJoint", new Gson().toJson(solderJointHardwareIdList));
        } else {
            model.addAttribute("valueJsonSolderJoint", new Gson().toJson(itemVm.getSolderJointHardwareId()));
        }
        if (itemVm.getWinConnectorHardwareId() != null && !"".equals(itemVm.getWinConnectorHardwareId())) {
            String[] winConnectorHardwareIdList = itemVm.getWinConnectorHardwareId().split(",");
            model.addAttribute("valueJsonWinConnector", new Gson().toJson(winConnectorHardwareIdList));
        } else {
            model.addAttribute("valueJsonWinConnector", new Gson().toJson(itemVm.getWinConnectorHardwareId()));
        }
        if (itemVm.getTeflonConnectorHardwareId() != null && !"".equals(itemVm.getTeflonConnectorHardwareId())) {
            String[] teflonConnectorHardwareIdList = itemVm.getTeflonConnectorHardwareId().split(",");
            model.addAttribute("valueJsonTeflonConnector", new Gson().toJson(teflonConnectorHardwareIdList));
        } else {
            model.addAttribute("valueJsonTeflonConnector", new Gson().toJson(itemVm.getTeflonConnectorHardwareId()));
        }
        if (itemVm.getPogoReceptaclesPinHardwareId() != null && !"".equals(itemVm.getPogoReceptaclesPinHardwareId())) {
            String[] pogoReceptaclesPinHardwareIdList = itemVm.getPogoReceptaclesPinHardwareId().split(",");
            model.addAttribute("valueJsonPogoReceptaclesPin", new Gson().toJson(pogoReceptaclesPinHardwareIdList));
        } else {
            model.addAttribute("valueJsonPogoReceptaclesPin", new Gson().toJson(itemVm.getPogoReceptaclesPinHardwareId()));
        }
        if (itemVm.getCableWiredCopperWireHardwareId() != null && !"".equals(itemVm.getCableWiredCopperWireHardwareId())) {
            String[] cableWiredCopperWireHardwareIdList = itemVm.getCableWiredCopperWireHardwareId().split(",");
            model.addAttribute("valueJsonCableWiredCopperWire", new Gson().toJson(cableWiredCopperWireHardwareIdList));
        } else {
            model.addAttribute("valueJsonCableWiredCopperWire", new Gson().toJson(itemVm.getCableWiredCopperWireHardwareId()));
        }
        if (itemVm.getLabelIdentificationHardwareId() != null && !"".equals(itemVm.getLabelIdentificationHardwareId())) {
            String[] labelIdentificationHardwareIdList = itemVm.getLabelIdentificationHardwareId().split(",");
            model.addAttribute("valueJsonLabelIdentification", new Gson().toJson(labelIdentificationHardwareIdList));
        } else {
            model.addAttribute("valueJsonLabelIdentification", new Gson().toJson(itemVm.getLabelIdentificationHardwareId()));
        }

//        LOGGER.info("itemVm.getPcbReject(): " + itemVm.getPcbReject());
        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> BibPassFail = pD.getGroupParameterDetailList("", "016");
        model.addAttribute("BibPassFail", BibPassFail);

        pD = new ParameterDetailsDAO();
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

        if (currentStatus.contains("HW Registration")) {
            String hwActive = "active";
            String hwActiveTab = "show active";
            model.addAttribute("hwActive", hwActive);
            model.addAttribute("hwActiveTab", hwActiveTab);
        } else {
            String hwActive = "";
            String hwActiveTab = "";
            model.addAttribute("hwActive", hwActive);
            model.addAttribute("hwActiveTab", hwActiveTab);
        }
        if (currentStatus.contains("VM") || currentStatus.contains("Visual Inspection")) {
            String vmActive = "active";
            String vmActiveTab = "show active";
            model.addAttribute("vmActive", vmActive);
            model.addAttribute("vmActiveTab", vmActiveTab);
        } else {
            String vmActive = "";
            String vmActiveTab = "";
            model.addAttribute("vmActive", vmActive);
            model.addAttribute("vmActiveTab", vmActiveTab);
        }
        String teActive = "";
        String teActiveTab = "";
        if (currentStatus.contains("Test")) {
            teActive = "active";
            teActiveTab = "show active";
            if (currentStatus.contains("- Leakage Test")) {
                model.addAttribute("leakshow", teActiveTab);
            } else if (currentStatus.contains("Manual")) {
                model.addAttribute("manshow", teActiveTab);
            } else if (currentStatus.contains("BIB Test")) {
                model.addAttribute("bibshow", teActiveTab);
            } else if (currentStatus.contains("BIB DAQ")) {
                model.addAttribute("bibDshow", teActiveTab);
            } else if (currentStatus.contains("Power Supply")) {
                model.addAttribute("psshow", teActiveTab);
            } else if (currentStatus.contains("Winchester")) {
                model.addAttribute("winshow", teActiveTab);
            }
        } else {
            // DO NOTHING HERE
        }
        model.addAttribute("teActive", teActive);
        model.addAttribute("teActiveTab", teActiveTab);

        return "rmsbookingDetail/detail_group_released";
    }

    //function for hw recall from production
    @RequestMapping(value = "/recall", method = {RequestMethod.GET, RequestMethod.POST})
    public String recall(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String recallRemarks) throws IOException {

        LOGGER.info("id: " + id);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        String date1 = formattedDate.substring(0, 10);
        String time = formattedDate.substring(11, 23);
        String completeDateTime = date1 + "T" + time;

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        RmsBookingDetail rms1 = rmsD.getRmsBookingDetail(id);

        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> hardware = rmsHD.getRmsBookingHardwareListByBookingPkidWithFlagOneAndStatusNotNA(rms1.getBookingPkid());

        for (int i = 0; i < hardware.size(); i++) {
            LOGGER.info("hardware.get(i).getId(): " + hardware.get(i).getId());

            //update movement in SPTS for Item ID first before update HEATS DB
            JSONObject params2 = new JSONObject();
            params2.put("dateTime", completeDateTime);
            params2.put("itemsPKID", hardware.get(i).getItemPkid());
            params2.put("transType", "26");
            params2.put("transQty", hardware.get(i).getQty());
            params2.put("remarks", "Return from Production Staging through HEATS");

            SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);

            if (TransPkid.getResponseId() > 0) {

                //add transaction to DB
                ItemTransaction item = new ItemTransaction();
                item.setSptsPkid(TransPkid.getResponseId().toString());
                item.setItemPkid(hardware.get(i).getItemPkid());
                item.setSiteName("Seremban");
                item.setDateTime(date1 + " " + time);
                item.setTransType("26");
                item.setTransTypeName("Return from Production Staging");
                item.setTransQty(hardware.get(i).getQty());
                item.setTransOutQty(hardware.get(i).getQty());
                item.setRemarks("Return from Production Staging through HEATS");

                ItemTransactionDAO itemD = new ItemTransactionDAO();
                QueryResult qI = itemD.insertItemTransaction(item);

                RmsBookingHardware hardware1 = new RmsBookingHardware();
                hardware1.setId(hardware.get(i).getId());
                hardware1.setFlag("0");
                hardware1.setModifiedBy(userSession.getFullname());
                if ("Motherboard".equals(hardware.get(i).getItemType())) {
                    hardware1.setStatus(hardware.get(i).getStatus());
                    hardware1.setSubStatus("Pending Release to Production");
                } else if ("Load Card".equals(hardware.get(i).getItemType()) || "Program Card".equals(hardware.get(i).getItemType())) {
                    hardware1.setStatus("Pending Release to Production");
                }
                rmsHD = new RmsBookingHardwareDAO();
                QueryResult q2 = rmsHD.updateRmsBookingHardwareForFlagAndStatusById(hardware1);

            } else {
                LOGGER.info("Fail to insert transaction for Item ID: " + hardware.get(i).getItemId());

                String[] to = {"global-rel-it@onsemi.com"};

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        "HW Release to Production - Failed to Insert SPTS Transaction (Recall)", //subject
                        "<br />"
                        + "Please be informed that the item below failed to insert SPTS transaction (Return from Production Staging)."
                        + "<br /> "
                        + "<br /> "
                        + "RMS No: " + rms1.getRmsNo()
                        + "<br /> "
                        + "Event: " + rms1.getEvent()
                        + "<br /> "
                        + "Item ID: " + hardware.get(i).getItemId()
                        + "<br /> "
                        + "Transaction Date: " + completeDateTime
                        + "<br /> "
                        + "<br /> "
                        + "Detail: Failed to insert SPTS Transaction (Return from Production Staging)"
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + id + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );
            }
        }

        RmsBookingHardwareGroupDAO groupD = new RmsBookingHardwareGroupDAO();
        List<RmsBookingHardwareGroup> group = groupD.getRmsBookingHardwareGroupListByBookingPkidWithFlagOne(rms1.getBookingPkid());

        for (int x = 0; x < group.size(); x++) {
            LOGGER.info("group.get(x).getId(): " + group.get(x).getId());

            //update movement in SPTS for Hardware ID first before update HEATS DB
            JSONObject params = new JSONObject();
            params.put("transDate", completeDateTime);
            params.put("itemHardwarePKID", group.get(x).getHardwarePkid());
            params.put("transType", "26");
//            params.put("rmsEvent", group.get(x).getRmsNo() + "_" + group.get(x).getEvent());
            params.put("rmsEvent", "NONE");
            params.put("remarks", "Return from Production Staging through HEATS");
            params.put("createdBy", userSession.getFullname());

            SPTSResponse TransHwPkid = SPTSWebService.insertTransactionHwId(params);

            if (TransHwPkid.getResponseId() > 0) {

                //add transaction to item_hardware_movement
                ItemHardwareDAO itemD = new ItemHardwareDAO();
                ItemHardware itemHw = itemD.getItemHardwareByHardwareId(group.get(x).getHardwareId());

                ItemHardwareMovement itemHwMovement = new ItemHardwareMovement();
                itemHwMovement.setMibHardwareId(itemHw.getId());
                itemHwMovement.setSptsPkid(TransHwPkid.getResponseId().toString());
                itemHwMovement.setTransType("26");
                itemHwMovement.setRmsEvent(group.get(x).getRmsNo() + "_" + group.get(x).getEvent());
                itemHwMovement.setCreatedBy(userSession.getFullname());
                ItemHardwareMovementDAO itD = new ItemHardwareMovementDAO();
                QueryResult q2 = itD.insertItemHardwareMovement(itemHwMovement);

                String sptsStatus = "";

                //update table item_hardware
                JSONObject paramsItem = new JSONObject();
                paramsItem.put("pkid", group.get(x).getHardwarePkid());
                JSONArray getRMSBooking = SPTSWebService.getHardwareIdByPKID(paramsItem);
                for (int i = 0; i < getRMSBooking.length(); i++) {

                    ItemHardware itemH = new ItemHardware();
                    itemH.setSptsPkid(group.get(x).getHardwarePkid());
                    itemH.setHardwareId(group.get(x).getHardwareId());
                    //  Scrapped = -1, No_Stock = 0,Good = 1,Production = 2,Repair = 3,Others = 4,Quarantine = 5,External_Cleaning = 6,External_Re_Cleaning = 7,Internal_Cleaning = 8,Internal_Re_Cleaning = 9,
//Storage_Factory = 10,Shipped_To_Other_ON_Semi_Site = 11,Shipped_To_Vendor = 12,Out_For_Production_Staging = 13

//                    sptsStatus = sptsStatus(getRMSBooking.getJSONObject(i).getInt("HardwareStatus")); //call function to replace SPTS int hardware status to string status
                    SPTSStatus spts = new SPTSStatus(); //call function to replace SPTS int hardware status to string status
                    sptsStatus = spts.sptsStatus(getRMSBooking.getJSONObject(i).getInt("HardwareStatus"));

                    itemH.setStatus(sptsStatus);
//                     itemH.setStatus(getRMSBooking.getJSONObject(i).getString("Status"));
                    if (getRMSBooking.getJSONObject(i).has("ALU")) {
                        Object alu = getRMSBooking.getJSONObject(i).get("ALU");
                        if (alu instanceof String) {
                            itemH.setAlu(getRMSBooking.getJSONObject(i).getString("ALU"));
                        } else {
                            itemH.setAlu(Integer.toString(getRMSBooking.getJSONObject(i).getInt("ALU")));
                        }
                    }
                    if (getRMSBooking.getJSONObject(i).has("RMS_Event")) {
                        Object RMS_Event = getRMSBooking.getJSONObject(i).get("RMS_Event");
                        if (RMS_Event instanceof String) {
                            itemH.setRmsEvent(getRMSBooking.getJSONObject(i).getString("RMS_Event"));
                        } else {
                            itemH.setRmsEvent(Integer.toString(getRMSBooking.getJSONObject(i).getInt("RMS_Event")));
                        }
                    }
                    if (getRMSBooking.getJSONObject(i).has("ShelfTime")) {
                        Object ShelfTime = getRMSBooking.getJSONObject(i).get("ShelfTime");
                        if (ShelfTime instanceof String) {
                            itemH.setShelfTime(getRMSBooking.getJSONObject(i).getString("ShelfTime"));
                        } else {
                            itemH.setShelfTime(Integer.toString(getRMSBooking.getJSONObject(i).getInt("ShelfTime")));
                        }
                    }
                    itemD = new ItemHardwareDAO();
                    QueryResult ItemDq = itemD.updateItemHardwareFromSPTS(itemH);
                }

                RmsBookingHardwareGroup group1 = new RmsBookingHardwareGroup();
                group1.setId(group.get(x).getId());
                group1.setStatus("Pending Release to Production");
                group1.setSptsStatus(sptsStatus); //waiting confirmation from JFLim 30.04.26
                group1.setFlag("0");
                groupD = new RmsBookingHardwareGroupDAO();
//                QueryResult q3 = groupD.updateRmsBookingHardwareGroupStatusAndFlag(group1);
                QueryResult q3 = groupD.updateRmsBookingHardwareGroupStatusAndSptsStatusAndFlag(group1);

                //add log
                RmsBookingHardwareGroupLog log2 = new RmsBookingHardwareGroupLog();
                log2.setGroupId(group.get(x).getGroupId());
                log2.setDetail("Return to MB Room: " + group.get(x).getHardwareId());
                log2.setCreatedBy(userSession.getFullname());
                RmsBookingHardwareGroupLogDAO logD2 = new RmsBookingHardwareGroupLogDAO();
                QueryResult logQ2 = logD2.insertRmsBookingHardwareGroupLog(log2);
            } else {
                LOGGER.info("Fail to insert transaction for Hardware ID: " + group.get(x).getHardwareId());

                String[] to = {"global-rel-it@onsemi.com"};

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        "HW Release to Production - Failed to Insert SPTS Transaction (Recall)", //subject
                        "<br />"
                        + "Please be informed that the Hardware ID below failed to insert SPTS transaction (Return from Production Staging)."
                        + "<br /> "
                        + "<br /> "
                        + "RMS No: " + rms1.getRmsNo()
                        + "<br /> "
                        + "Event: " + rms1.getEvent()
                        + "<br /> "
                        + "Hardware ID: " + group.get(x).getHardwareId()
                        + "<br /> "
                        + "Transaction Date: " + completeDateTime
                        + "<br /> "
                        + "<br /> "
                        + "Detail: Failed to insert SPTS Transaction (Return from Production Staging)"
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + id + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );
            }

        }

        //update status
        RmsBookingDetail rms = new RmsBookingDetail();
        rms.setId(id);
//        rms.setStatus("Pending Release to Production");
        rms.setStatus("New");
        rms.setFlag("0");
        rms.setReturnBy(userSession.getFullname());
        rms.setReturnRemarks(recallRemarks);
        rmsD = new RmsBookingDetailDAO();
        QueryResult q = rmsD.updateRmsBookingDetailForReturn(rms);
        if (q.getResult() > 0) {

            //update log
            RmsBookingLog log = new RmsBookingLog();
            log.setBookingId(id);
            log.setDetail("Return to MB Room");
            log.setCreatedBy(userSession.getFullname());
            RmsBookingLogDAO logD = new RmsBookingLogDAO();
            QueryResult logQ = logD.insertRmsBookingLog(log);

            redirectAttrs.addFlashAttribute("success", "Successfully update the hardware status. Please return the hardware to MB room.");

            //send email to team when successfully recall
            EmailHwReturnFromStagingDAO userDao = new EmailHwReturnFromStagingDAO();
            List<EmailHwReturnFromStaging> userRecipientsList = userDao.getEmailHwReturnFromStagingList();

            String[] to = new String[userRecipientsList.size()];
            for (int x = 0; x < userRecipientsList.size(); x++) {
                to[x] = userRecipientsList.get(x).getEmail();
            }

            //gethostname
            HostnameDAO hostnameD = new HostnameDAO();
            Hostname h = hostnameD.getHostnameFlagZero();
            String hostname = h.getHostname();

            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    to, //to
                    //                        emailTo,
                    "[Action Required]HW Release – Reverted to Motherboard Room", //subject
                    "<br />"
                    + "Please be informed that the HW for below RMS_Event have been reverted by Loading Tech."
                    + "<br /> "
                    + "<br /> "
                    + "RMS No: " + rms1.getRmsNo()
                    + "<br /> "
                    + "Event: " + rms1.getEvent()
                    + "<br /> "
                    + "Returned By: " + userSession.getFullname()
                    + "<br /> "
                    + "Transaction Date: " + completeDateTime
                    + "<br /> "
                    + "Remark: " + recallRemarks
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + id + " \">HERE</a> for more detail."
                    + "<br /> "
                    + "<br />Thank you." //msg
            );

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to update the hardware status. Pls contact system admin.");
        }
//        return "redirect:/rmsbookingDetail/detail/" + id;
        return "redirect:/rmsbookingDetail";
    }

    @RequestMapping(value = "/createManualTest", method = {RequestMethod.GET, RequestMethod.POST})
    public String bookingFunctionalTest(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String totalQty,
            @RequestParam(required = false) String mbItemId,
            @RequestParam(required = false) String lcItemId,
            @RequestParam(required = false) String groupId) {

        String path = "";

        // SINI KITA HARDCODE UNTUK PERGI KE PHP PROUJECT LINK FOR HEATS
//        String link = "http://zbqb9x-7jwwld4:86/Tutorial/sample-heat/manual_test_before_loading.php?id=" + lcItemId + "&groupId=" + groupId;
        String link = "https://mysed-rel-app05/HEATS-mini/manual_test_before_loading.php?id=" + lcItemId + "&groupId=" + groupId;
        model.addAttribute("link", link);

        ManualTestDAO testdao = new ManualTestDAO();
        ManualTest mantest = testdao.getComponentConfigBeforeByItemId(lcItemId);

        if (mantest != null) {
            path = "rmsbookingDetail/goto_manual_test";
            redirectAttrs.addFlashAttribute("success", "Please perform manual test.");

            testdao = new ManualTestDAO();
            List<ManualTest> listManual = testdao.getAllComponentConfigBeforeLoading(lcItemId);

            if (listManual.size() == 0) {
                int saizQty = Integer.parseInt(totalQty);
                int saizDut = Integer.parseInt(mantest.getDut());
                int saizCom = Integer.parseInt(mantest.getComponent());

                // FUNCTION UPDATE THE LATEST QUANTITY - START
                testdao = new ManualTestDAO();
                testdao.updateItemActivityConfig(String.valueOf(saizQty), String.valueOf(saizDut), String.valueOf(saizCom), mantest.getId());
                // FUNCTION UPDATE THE LATEST QUANTITY - END

                testdao = new ManualTestDAO();
                List<ManualTest> listComponent = testdao.getAllComponentConfigBefore(lcItemId);

                for (int i = 0; i < listComponent.size(); i++) {
                    String compType = listComponent.get(i).getComponentType();
                    String compName = listComponent.get(i).getComponentName();
                    String compValue = listComponent.get(i).getComponentValue();
                    String minValue = listComponent.get(i).getLowerLimit();
                    String maxValue = listComponent.get(i).getUpperLimit();
                    String percentage = listComponent.get(i).getPercentage();

                    for (int c1 = 1; c1 <= saizQty; c1++) {
                        for (int c2 = 1; c2 <= saizDut; c2++) {
                            testdao = new ManualTestDAO();
                            QueryResult qr = testdao.insertManualBeforeLoading(lcItemId, String.valueOf(c1), String.valueOf(c2), compType, compName, compValue, minValue, maxValue, percentage, "", "", "1");
                        }
                    }
                }
            } else {
                // WHAT NEED TO BE DONE HERE? SINCE THERE ALREADY DATA, WE SKIP INSERTING A NEW ONE
            }
        } else {
            path = "redirect:/rmsbookingDetail/groupDetail/" + groupId;
            redirectAttrs.addFlashAttribute("error", "Please check the manual test configuration for the ITEM ID " + mbItemId);
        }

        // BERJAYA 
//        path = "rmsbookingDetail/goto_manual_test";
        // GAGAL
//        path = "redirect:/rmsbookingDetail/groupDetail/"+groupId;
        return path;
    }

    @RequestMapping(value = "/sendEmail/{jenis}/{itemId}/{bookId}/{pkid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendEmailConfig(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("jenis") String jenis,
            @PathVariable("itemId") String itemId,
            @PathVariable("bookId") String bookId,
            @PathVariable("pkid") String pkid,
            @RequestParam(required = false) String recallRemarks) throws IOException {

        String path = "redirect:/rmsbookingDetail/groupDetail/" + bookId + "/" + pkid;
        String tajukEmail = "";
        String emailBody = "";

        EmailVmFailDAO userDao = new EmailVmFailDAO();
        List<EmailVmFail> emailList = null;
        switch (jenis) {
            case "MB":
                tajukEmail = "Item Activity Config Missing (Motherboard)";
                emailList = userDao.getEmailMotherboardTechnicianMb();
                break;
            case "LC":
                tajukEmail = "Item Activity Config Missing (Load Card)";
                emailList = userDao.getEmailMotherboardTechnicianLc();
                break;
            default:
                break;
        }

        String[] to = new String[emailList.size()];
        for (int i = 0; i < emailList.size(); i++) {
            to[i] = emailList.get(i).getEmail();
        }

        Item item = new Item();
        ItemDAO itemdao = new ItemDAO();
        String itemName = itemdao.getItemIdById(itemId);
        emailBody = itemName + " [" + itemId + "]";

        HostnameDAO hostnameD = new HostnameDAO();
        Hostname h = hostnameD.getHostnameFlagZero();
        String hostname = h.getHostname();

        EmailSender emailSender = new EmailSender();
        emailSender.htmlEmailFT(
                servletContext,
                "",
                to,
                tajukEmail,
                "<br />"
                + "Please be informed that the item below missing Item Activity Configuration."
                + "<br /> "
                + "<br /> "
                + "Detail: " + emailBody
                + "<br /> "
                + "<br /> "
                //                + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/groupDetail/" + bookId + "/" + pkid + " \">HERE</a> for more detail."
                + "Please click <a href=\"http://" + hostname + "/HEATS/hw/item/addActivity/" + itemId + " \">HERE</a> for more detail."
                + "<br /> "
                + "<br />Thank you."
        );
        redirectAttrs.addFlashAttribute("success", "Email notification successfully sent!");

        return path;
    }

    private String saveToMaverickFunctionalTest(String jenistest, String user, String groupId, String hardwareId) {
        String data = "";

        String[] MbBookingHwPkid = groupId.split("/");
        String bookingPkid = MbBookingHwPkid[0];
        String mbBookingPkid = MbBookingHwPkid[1];

        String emailBodyFail = "";
        String currentmodule = "Before Loading";
        String currentsubmodule = "Functional Test";
        String tajukEmail = "Failed Functional Test";

        if (jenistest.contains("Leakage")) {
            emailBodyFail = "Failed Functional Test - Leakage Test";
        } else if (jenistest.contains("Manual")) {
            emailBodyFail = "Failed Functional Test - Manual Test";
        } else if (jenistest.contains("BIB")) {
            emailBodyFail = "Failed Functional Test - BIB Test";
        } else if (jenistest.contains("BIBDAQ")) {
            emailBodyFail = "Failed Functional Test - BIB DAQ Test";
        } else if (jenistest.contains("Power")) {
            emailBodyFail = "Failed Functional Test - Power Supply Leakage Test";
        } else if (jenistest.contains("Winchester")) {
            emailBodyFail = "Failed Functional Test - Winchester Chamber Leakage Test";
        }

        // UPDATE SUB STATUS rms_booking_hardware START
        RmsBookingHardware bookHardware = new RmsBookingHardware();
        bookHardware.setBookingPkid(bookingPkid);
        bookHardware.setPkid(mbBookingPkid);
        bookHardware.setSubStatus(emailBodyFail);
        RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
        booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
        // UPDATE SUB STATUS rms_booking_hardware END

        // UPDATE STATUS FOR EACH HARDWARE ID IN rms_booking_hardware_group START
        if (hardwareId != null && !hardwareId.trim().isEmpty()) {
            String[] parts = hardwareId.split(",");
            for (String id : parts) {
                String hwid = id.trim();
                RmsBookingHardwareGroupDAO bookgroupdao = new RmsBookingHardwareGroupDAO();
                bookgroupdao.updateGroupStatus(emailBodyFail, groupId, hwid);
            }
        }
        // UPDATE STATUS FOR EACH HARDWARE ID IN rms_booking_hardware_group END

        RmsBookingMaverick maverick = new RmsBookingMaverick();
        maverick.setGroupId(groupId);
        maverick.setModule(currentmodule);
        maverick.setSubmodule(currentsubmodule);
        maverick.setStatus(emailBodyFail);
        maverick.setFlag("0");
        maverick.setCreatedBy(user);
        RmsBookingMaverickDAO maverickD = new RmsBookingMaverickDAO();
        QueryResult maverickAdd = maverickD.insertRmsBookingMaverick(maverick);

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

        RmsBookingDetailDAO rmsBookingD = new RmsBookingDetailDAO();
        RmsBookingDetail rmsBooking = rmsBookingD.getRmsBookingDetailByBookingPkid(bookingPkid);

        RmsBookingHardwareDAO rmsBookingHD = new RmsBookingHardwareDAO();
        RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

        //send INFORMATION email
        EmailSender emailSender = new EmailSender();
        emailSender.htmlEmailFT(
                servletContext,
                "",
                to,
                tajukEmail,
                "<br />"
                + "Please be informed that the hardware for RMS below failed the visual inspection."
                + "<br /> "
                + "<br /> "
                + "RMS No: " + rmsBooking.getRmsNo()
                + "<br /> "
                + "Event: " + rmsBooking.getEvent()
                + "<br /> "
                + "Motherboard ID: " + MbDetail.getItemId()
                + "<br /> "
                + "Inspection Date: " + formattedString
                + "<br /> "
                + "Detail: " + emailBodyFail
                + "<br /> "
                + "<br /> "
                + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/groupDetail/" + groupId + " \">HERE</a> for more detail."
                + "<br /> "
                + "<br />Thank you."
        );
        return data;
    }

    public void checkInsertFunctionalTestResult(String groupId, String username) {

        RmsBookingFunctionalTestDAO testdao = new RmsBookingFunctionalTestDAO();
        Integer checkData = testdao.getCountTestResultByGroupId(groupId);

        if (checkData == 0) {
            // INSERT BARU
            RmsBookingFunctionalTest ftest = new RmsBookingFunctionalTest();
            ftest.setGroupId(groupId);
            ftest.setCreatedBy(username);
            ftest.setFinalStatus("Pending Functional Test");
            ftest.setFlag("0");
            testdao = new RmsBookingFunctionalTestDAO();
            testdao.insertRmsBookingFunctionalTest(ftest);
        } else {
            // BOLE UPDATE NANTI
        }
    }

}
