package com.onsemi.mib.controller;

import com.onsemi.mib.dao.EmailHwReplacementDAO;
import com.onsemi.mib.dao.EmailVmFailDAO;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemHardwareDAO;
import com.onsemi.mib.dao.ItemLogDAO;
import com.onsemi.mib.dao.ItemMaverickDAO;
import com.onsemi.mib.dao.ItemVisualInspectionDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingDetailHwReplacementDAO;
import com.onsemi.mib.dao.RmsBookingHardwareDAO;
import com.onsemi.mib.dao.RmsBookingHardwareGroupDAO;
import com.onsemi.mib.dao.RmsBookingHardwareGroupLogDAO;
import com.onsemi.mib.dao.RmsBookingLogDAO;
import com.onsemi.mib.dao.RmsBookingMaverickDAO;
import com.onsemi.mib.dao.RmsBookingVisualInspectionDAO;
import com.onsemi.mib.model.EmailHwReplacement;
import com.onsemi.mib.model.EmailVmFail;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemHardware;
import com.onsemi.mib.model.ItemLog;
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.model.ItemVisualInspection;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingDetailHwReplacement;
import com.onsemi.mib.model.RmsBookingHardware;
import com.onsemi.mib.model.RmsBookingHardwareGroup;
import com.onsemi.mib.model.RmsBookingHardwareGroupLog;
import com.onsemi.mib.model.RmsBookingLog;
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.model.RmsBookingVisualInspection;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.HimsRetrieve;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSWebService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
@RequestMapping(value = "/rmsbookingDetail")
@SessionAttributes({"userSession"})
public class RmsBookingDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingDetailController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\HEATS\\VI-Attachment\\Before_Loading\\"; //server

    private static final int BUFFER_SIZE = 4096;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String rmsbookingDetail(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws IOException {
        //get cmbs booking detail
        JSONArray getRMSBooking = SPTSWebService.getBookedEqptFOLFiles(false);

        int count = 0;
        int countAdd = 0;
        int countUpdate = 0;
        String pkid = "0";
        String event = "";
        String eventStartDate = "";
        String ActStartDate = "";

        //to add bookingpkid from fol report to new list (to remove bookingpkid that not available in fol report)
        List<String> list = new ArrayList<>();

        //insert into database
        for (int i = 0; i < getRMSBooking.length(); i++) {

            event = "";
            eventStartDate = "";
            ActStartDate = "";

            if (getRMSBooking.getJSONObject(i).has("BookingPKID")) {
                pkid = Integer.toString(getRMSBooking.getJSONObject(i).getInt("BookingPKID"));
                list.add(Integer.toString(getRMSBooking.getJSONObject(i).getInt("BookingPKID")));
            } else {
                pkid = "0";
                list.add("null");
            }
            RmsBookingDetail eqpt = new RmsBookingDetail();
            eqpt.setBookingPkid(pkid);
            if (getRMSBooking.getJSONObject(i).has("RMSNo")) {
                Object RMSNo = getRMSBooking.getJSONObject(i).get("RMSNo");
                if (RMSNo instanceof String) {
                    eqpt.setRmsNo(getRMSBooking.getJSONObject(i).getString("RMSNo"));
                } else {
                    eqpt.setRmsNo(Integer.toString(getRMSBooking.getJSONObject(i).getInt("RMSNo")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("EventNameCode")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("EventNameCode");
                if (assembly instanceof String) {
                    eqpt.setEvent(getRMSBooking.getJSONObject(i).getString("EventNameCode"));
                    event = getRMSBooking.getJSONObject(i).getString("EventNameCode");
                } else {
                    eqpt.setEvent(Integer.toString(getRMSBooking.getJSONObject(i).getInt("EventNameCode")));
                    event = Integer.toString(getRMSBooking.getJSONObject(i).getInt("EventNameCode"));
                }

            }
            if (getRMSBooking.getJSONObject(i).has("Device")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("Device");
                if (assembly instanceof String) {
                    eqpt.setDevice(getRMSBooking.getJSONObject(i).getString("Device"));
                } else {
                    eqpt.setDevice(Integer.toString(getRMSBooking.getJSONObject(i).getInt("Device")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("Package")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("Package");
                if (assembly instanceof String) {
                    eqpt.setPackages(getRMSBooking.getJSONObject(i).getString("Package"));
                } else {
                    eqpt.setPackages(Integer.toString(getRMSBooking.getJSONObject(i).getInt("Package")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("EventStartDate")) {
                eventStartDate = getRMSBooking.getJSONObject(i).getString("EventStartDate").substring(0, 10) + " " + getRMSBooking.getJSONObject(i).getString("EventStartDate").substring(11, 19);
                eqpt.setEventStartDate(eventStartDate);
            }
            if (getRMSBooking.getJSONObject(i).has("RMSStatus")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("RMSStatus");
                if (assembly instanceof String) {
                    eqpt.setRmsStatus(getRMSBooking.getJSONObject(i).getString("RMSStatus"));
                } else {
                    eqpt.setRmsStatus(Integer.toString(getRMSBooking.getJSONObject(i).getInt("RMSStatus")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("EventBeginStatus")) {
                eqpt.setEventBeginStatus(getRMSBooking.getJSONObject(i).getString("EventBeginStatus"));
            }
            if (getRMSBooking.getJSONObject(i).has("EventEndStatus")) {
                eqpt.setEventEndStatus(getRMSBooking.getJSONObject(i).getString("EventEndStatus"));
            }
            if (getRMSBooking.getJSONObject(i).has("EquipmentLocation")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("EquipmentLocation");
                if (assembly instanceof String) {
                    eqpt.setEquipmentLocation(getRMSBooking.getJSONObject(i).getString("EquipmentLocation"));
                } else {
                    eqpt.setEquipmentLocation(Integer.toString(getRMSBooking.getJSONObject(i).getInt("EquipmentLocation")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("EstStartDate")) {
                String estStartDate = getRMSBooking.getJSONObject(i).getString("EstStartDate").substring(0, 10) + " " + getRMSBooking.getJSONObject(i).getString("EstStartDate").substring(11, 19);
                eqpt.setEstStartDate(estStartDate);
            }
            if (getRMSBooking.getJSONObject(i).has("ActStartDate")) {
                ActStartDate = getRMSBooking.getJSONObject(i).getString("ActStartDate").substring(0, 10) + " " + getRMSBooking.getJSONObject(i).getString("ActStartDate").substring(11, 19);
                eqpt.setActStartDate(ActStartDate);
            }
            if (getRMSBooking.getJSONObject(i).has("FOLFilename")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("FOLFilename");
                if (assembly instanceof String) {
                    eqpt.setFolFilename(getRMSBooking.getJSONObject(i).getString("FOLFilename"));
                } else {
                    eqpt.setFolFilename(Integer.toString(getRMSBooking.getJSONObject(i).getInt("FOLFilename")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("TotalBooking")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("TotalBooking");
                if (assembly instanceof String) {
                    eqpt.setTotalBooking(getRMSBooking.getJSONObject(i).getString("TotalBooking"));
                } else {
                    eqpt.setTotalBooking(Integer.toString(getRMSBooking.getJSONObject(i).getInt("TotalBooking")));
                }
            }
            if (getRMSBooking.getJSONObject(i).has("NoCurrentFTP")) {
                Object assembly = getRMSBooking.getJSONObject(i).get("NoCurrentFTP");
                if (assembly instanceof String) {
                    eqpt.setNoCurrentFtp(getRMSBooking.getJSONObject(i).getString("NoCurrentFTP"));
                } else {
                    eqpt.setNoCurrentFtp(Boolean.toString(getRMSBooking.getJSONObject(i).getBoolean("NoCurrentFTP")));
                }
            }

            LocalDate dateBefore = LocalDate.parse(getRMSBooking.getJSONObject(i).getString("ActStartDate").substring(0, 10));
            LocalDate dateAfter = LocalDate.parse(getRMSBooking.getJSONObject(i).getString("EventStartDate").substring(0, 10));

            // Calculate the number of days between the two dates
            long daysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

            eqpt.setDaysToEventStart(Long.toString(daysBetween));
            if (eqpt.getFolFilename() == null || "".equals(eqpt.getFolFilename()) || "null".equals(eqpt.getFolFilename())) { //only display rms with no bib test file yet

                eqpt.setStatus("New");
                eqpt.setFlag("0");
            } else { // no need to display rms with bib test file.

            }

            eqpt.setPriority("999"); // default

            //only check for Life Test
            if (!event.contains("TC") && !event.contains("HTSL") && !event.contains("THS") && !event.contains("UHAST") && !event.contains("AC")) {

                //check need to insert or update
                RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
                int countBookingId = rmsD.getCountBookingId(pkid);
                if (countBookingId == 0) { //insert
                    rmsD = new RmsBookingDetailDAO();
                    QueryResult q = rmsD.insertRmsBookingDetail(eqpt);
                    countAdd += q.getResult();

                    //update log
                    RmsBookingLog log = new RmsBookingLog();
                    log.setBookingId(q.getGeneratedKey());
                    log.setDetail("Added From CBMS");
                    log.setCreatedBy(userSession.getFullname());
                    RmsBookingLogDAO logD = new RmsBookingLogDAO();
                    QueryResult logQ = logD.insertRmsBookingLog(log);

                } else if (countBookingId == 1) { //update
                    //only update if flag = 0
                    rmsD = new RmsBookingDetailDAO();
                    int countBookingIdFlagZero = rmsD.getCountBookingIdFlagZero(pkid);
                    if (countBookingIdFlagZero == 1) {
                        rmsD = new RmsBookingDetailDAO();
                        QueryResult q = rmsD.updateRmsBookingDetailFromCBMSByPkid(eqpt);
                        countUpdate += q.getResult();

                        String fol = eqpt.getFolFilename();
                        if (fol != null) {
                            fol = fol.trim();
                            if (!fol.isEmpty() && !"null".equalsIgnoreCase(fol)) {
                                // change status to removed and flag 99 if rms already has bib test file
//                                LOGGER.info("eqpt.getFolFilename(): " + eqpt.getFolFilename());

                                RmsBookingDetail eqpt2 = new RmsBookingDetail();
                                eqpt2.setBookingPkid(pkid);
                                eqpt2.setStatus("Removed");
                                eqpt2.setFlag("99");
                                RmsBookingDetailDAO rmsBookingDetailD = new RmsBookingDetailDAO();
                                QueryResult q2 = rmsBookingDetailD.updateRmsBookingDetailForFlagAndStatus(eqpt2);
                                countUpdate += q2.getResult();

                                //update log
                                RmsBookingLog log = new RmsBookingLog();
                                log.setBookingId(pkid);
                                log.setDetail("Removed from Active List");
                                log.setCreatedBy(userSession.getFullname());
                                RmsBookingLogDAO logD = new RmsBookingLogDAO();
                                QueryResult logQ = logD.insertRmsBookingLog(log);
                            }
                        }

                    }

                }
            }
            count += 1;
        }
        LOGGER.info("Total data: " + count);
        LOGGER.info("Total insert: " + countAdd);
        LOGGER.info("Total update: " + countUpdate);

        //cross check booking pkid in HEATS table with FOL report
        RmsBookingDetailDAO rmsBookingDetailD = new RmsBookingDetailDAO();
        List<RmsBookingDetail> rms = rmsBookingDetailD.getBookingPkidwithFlagZero();
        for (int i = 0; i < rms.size(); i++) {

            if (!list.contains(rms.get(i).getBookingPkid())) {
//                LOGGER.info("BookingPkid: " + rms.get(i).getBookingPkid());
                //change flag to 99 and status = 'Remove'
                RmsBookingDetail rmsDetail = new RmsBookingDetail();
                rmsDetail.setBookingPkid(rms.get(i).getBookingPkid());
                rmsDetail.setFlag("99");
                rmsDetail.setStatus("Removed");
                rmsBookingDetailD = new RmsBookingDetailDAO();
                QueryResult q = rmsBookingDetailD.updateRmsBookingDetailForFlagAndStatus(rmsDetail);

                //update log
                RmsBookingLog log = new RmsBookingLog();
                log.setBookingId(rms.get(i).getId());
                log.setDetail("Removed from Active List");
                log.setCreatedBy(userSession.getFullname());
                RmsBookingLogDAO logD = new RmsBookingLogDAO();
                QueryResult logQ = logD.insertRmsBookingLog(log);
            }
        }

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        List<RmsBookingDetail> booking = rmsD.getRmsBookingDetailListFlagZero();

        model.addAttribute(
                "booking", booking);

        rmsD = new RmsBookingDetailDAO();
        int countBooking = rmsD.getCountBookingFlagZero();

        model.addAttribute(
                "countBooking", countBooking);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> priorityList = pD.getGroupParameterDetailListForPriorityBooking("", "019");

        model.addAttribute(
                "priorityList", priorityList);

        return "rmsbookingDetail/rmsbookingDetail";
    }

    @RequestMapping(value = "/priorityDetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RmsBookingDetail getPriorityDetail(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String id
    ) throws IOException {

//        LOGGER.info("id: " + id);
        RmsBookingDetailDAO rmsd = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsd.getRmsBookingDetail(id);

        return rms;
    }

    @RequestMapping(value = "/savePriority", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String remarks
    ) throws IOException {

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        RmsBookingDetail rms1 = rmsD.getRmsBookingDetail(id);

        RmsBookingDetail rms = new RmsBookingDetail();
        rms.setId(id);
        rms.setPriority(priority);
        rms.setPriorityRemarks(remarks);
        rms.setPriorityBy(userSession.getFullname());
        rmsD = new RmsBookingDetailDAO();
        QueryResult q = rmsD.updateRmsBookingDetailForPriority(rms);
        if (q.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", "Succesfully add priority for " + rms1.getRmsNo() + "_" + rms1.getEvent());
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to add priority for " + rms1.getRmsNo() + "_" + rms1.getEvent() + ". Pls contact system admin.");
        }
        return "redirect:/rmsbookingDetail";
    }

    @RequestMapping(value = "/cancelPriority/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String cancelPriority(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id) {

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        RmsBookingDetail rms1 = rmsD.getRmsBookingDetail(id);

        RmsBookingDetail rms = new RmsBookingDetail();
        rms.setId(id);
        rms.setPriority("999");
        rms.setPriorityRemarks(null);
        rms.setPriorityBy(null);
        rmsD = new RmsBookingDetailDAO();
        QueryResult q = rmsD.updateRmsBookingDetailForPriority(rms);
        if (q.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", "Succesfully removed priority for " + rms1.getRmsNo() + "_" + rms1.getEvent());
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to remove priority for " + rms1.getRmsNo() + "_" + rms1.getEvent() + ". Pls contact system admin.");
        }
        return "redirect:/rmsbookingDetail";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(Model model,
            @PathVariable("id") String id,
            @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

        //to cross check with existing hardware booked
        List<String> list = new ArrayList<>();

        RmsBookingDetailDAO rmsd = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsd.getRmsBookingDetail(id);
        model.addAttribute("rms", rms);

        int onhandQty = 0;
        int requestQty = 0;

        //add hardware detail from spts
        int bookingPkid = Integer.parseInt(rms.getBookingPkid());
        JSONArray getItemByParamV = SPTSWebService.getBookingDetailByPKID(bookingPkid);
        for (int i = 0; i < getItemByParamV.length(); i++) {

//            LOGGER.info("1st step: " + LocalDateTime.now());
            list.add(Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));

            String itemType = "";
            if (getItemByParamV.getJSONObject(i).getString("field_name").contains("Motherboard")) {
                itemType = "Motherboard";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("Tester")) {
                itemType = "Tester";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("Remarks")) {
                itemType = "Remarks";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("PowerSupply")) {
                itemType = "Power Supply";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("ProgramCard")) {
                itemType = "Program Card";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("LoadCard")) {
                itemType = "Load Card";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("DUTCard")) {
                itemType = "DUT Card";
            } else if (getItemByParamV.getJSONObject(i).getString("field_name").contains("Solder")) {
                itemType = "Solder Type";
            } else {
                itemType = "";
            }

            RmsBookingHardware rmsH = new RmsBookingHardware();
            rmsH.setBookingPkid(Integer.toString(getItemByParamV.getJSONObject(i).getInt("booking_pkid")));
            rmsH.setPkid(Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
            rmsH.setItemType(itemType);
            if (getItemByParamV.getJSONObject(i).has("field_value")) {
                Object assembly = getItemByParamV.getJSONObject(i).get("field_value");
                if (assembly instanceof String) {
                    rmsH.setItemId(getItemByParamV.getJSONObject(i).getString("field_value"));
                } else {
                    rmsH.setItemId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("field_value")));
                }
            }
            if (getItemByParamV.getJSONObject(i).has("field_quantity")) {
                rmsH.setQty(Integer.toString(getItemByParamV.getJSONObject(i).getInt("field_quantity")));
                requestQty = getItemByParamV.getJSONObject(i).getInt("field_quantity");
            } else {
                requestQty = 0;
            }
            rmsH.setReadiness(Boolean.toString(getItemByParamV.getJSONObject(i).getBoolean("field_readiness")));
            rmsH.setFlag("0");
            rmsH.setCreatedBy(userSession.getFullname());
            rmsH.setModifiedBy(userSession.getFullname());

            //get itempkid and check qty if available or not (for bib and bibcard only)
//            LOGGER.info("rmsH.getItemType(): " + rmsH.getItemType());
//            LOGGER.info("rmsH.getItemId(): " + rmsH.getItemId());
            if ("Motherboard".equals(rmsH.getItemType()) || "Load Card".equals(rmsH.getItemType()) || "Program Card".equals(rmsH.getItemType())) {
                if (!"NA".equals(rmsH.getItemId())) {

                    JSONObject paramV = new JSONObject();
                    paramV.put("itemID", rmsH.getItemId());
                    JSONArray getItemByParam = SPTSWebService.getItemByParam(paramV);
                    for (int x = 0; x < getItemByParam.length(); x++) {

                        rmsH.setItemPkid(Integer.toString(getItemByParam.getJSONObject(x).getInt("PKID")));
                        onhandQty = getItemByParam.getJSONObject(x).getInt("OnHandQty");
                        if (onhandQty >= requestQty) {
                            rmsH.setStatus("Available");
                            if ("Motherboard".equals(rmsH.getItemType())) {
                                //check status if requested for replacement or not
                                RmsBookingHardwareDAO rmsBH = new RmsBookingHardwareDAO();
                                int count = rmsBH.getCountBookingId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("booking_pkid")), Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
//                                LOGGER.info("countbookingwithbookingpkidandpkid: " + count);
                                if (count == 1) {
                                    rmsBH = new RmsBookingHardwareDAO();
                                    RmsBookingHardware rmsB = rmsBH.getRmsBookingHardwareByPkid(Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
//                                    LOGGER.info("rmsB.getSubStatus(): " + rmsB.getSubStatus());
                                    rmsH.setSubStatus(rmsB.getSubStatus());
                                } else {
                                    rmsH.setSubStatus("Pending HW Registration");
                                }
                            }
                        } else {
                            //check status if requested for replacement or not
                            RmsBookingHardwareDAO rmsBH = new RmsBookingHardwareDAO();
                            int count = rmsBH.getCountBookingId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("booking_pkid")), Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
                            if (count == 1) {
                                rmsBH = new RmsBookingHardwareDAO();
                                RmsBookingHardware rmsB = rmsBH.getRmsBookingHardwareByPkid(Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
                                if (rmsB.getStatus().contains("Request for Replacement") || rmsB.getStatus().contains("Recall from Storage Factory")) {
                                    rmsH.setStatus(rmsB.getStatus());
                                } else {
                                    rmsH.setStatus("Not Available - " + getItemByParam.getJSONObject(x).getString("StatusName"));
                                }
                            } else {
                                rmsH.setStatus("Not Available - " + getItemByParam.getJSONObject(x).getString("StatusName"));
                            }
                        }
                        if (getItemByParam.getJSONObject(x).has("StorageFactoryQty")) {
//                            LOGGER.info("StorageFactoryQty: " + getItemByParam.getJSONObject(x).getInt("StorageFactoryQty"));
                            if (getItemByParam.getJSONObject(x).getInt("StorageFactoryQty") > 0) {
                                rmsH.setRecall("Yes");
                            } else {
                                rmsH.setRecall("No");
                            }
                        } else {
                            rmsH.setRecall("No");
                        }

                    }
                } else {
                    rmsH.setItemPkid("0");
                    rmsH.setStatus("NA");
                    rmsH.setRecall("No");
                }
            } else {
                rmsH.setItemPkid("0");
                rmsH.setStatus("NA");
                rmsH.setRecall("No");
            }

            RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
            int count = rmsHD.getCountBookingId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("booking_pkid")), Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
            if (count == 0) { //add new record
                rmsHD = new RmsBookingHardwareDAO();
                QueryResult q = rmsHD.insertRmsBookingHardware(rmsH);
            } else if (count == 1) { //update existing hardware
                rmsHD = new RmsBookingHardwareDAO();
                QueryResult q = rmsHD.updateRmsBookingHardwareByPkidAndBookingPkid(rmsH);
            }

//            System.out.println(getItemByParamV.getJSONObject(i));
        }
        //update inactive/replaced hardware 
        RmsBookingHardwareDAO rmsH = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> hw = rmsH.getRmsBookingHardwareListByBookingPkidWithFlagZero(Integer.toString(bookingPkid));
        for (int i = 0; i < hw.size(); i++) {
            if (!list.contains(hw.get(i).getPkid())) {

                RmsBookingHardware h = new RmsBookingHardware();
                h.setId(hw.get(i).getId());
                h.setFlag("99");
                h.setStatus("Removed");
                h.setSubStatus(null);
                h.setModifiedBy("HEATS");
                rmsH = new RmsBookingHardwareDAO();
                QueryResult q = rmsH.updateRmsBookingHardwareForFlagAndStatusById(h);
                LOGGER.info("pkid removed: " + hw.get(i).getPkid());
            }
        }

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

        return "rmsbookingDetail/detail";
    }

    @RequestMapping(value = "/addHwReplacement", method = RequestMethod.POST)
    public String addHwReplacement(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id3,
            @RequestParam(required = false) String hwReplacement,
            @RequestParam(required = false) String remarks
    ) {
        RmsBookingHardwareDAO rmsBD = new RmsBookingHardwareDAO();
        RmsBookingHardware bookH = rmsBD.getRmsBookingHardwareByPkid(hwReplacement);

        RmsBookingDetailHwReplacement replace = new RmsBookingDetailHwReplacement();
        replace.setBookingPkid(bookH.getBookingPkid());
        replace.setBookingHwPkid(hwReplacement);
        replace.setItemPkid(bookH.getItemPkid());
        replace.setItemId(bookH.getItemId());
        replace.setRemarks(remarks);
        replace.setCreatedBy(userSession.getFullname());
        replace.setFlag("0");
        RmsBookingDetailHwReplacementDAO replaceD = new RmsBookingDetailHwReplacementDAO();
        QueryResult queryResult = replaceD.insertRmsBookingDetailHwReplacement(replace);

        if (queryResult.getResult() == 1) {

            redirectAttrs.addFlashAttribute("success", "Item succesfully added into the list");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to add item into the list. pls contact system admin.");
        }
        return "redirect:/rmsbookingDetail/detail/" + id3 + "?saved=1";
    }

    @RequestMapping(value = "/deleteHwReplacement/{id}/{bookingDetailId}", method = RequestMethod.GET)
    public String deleteHwId(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id,
            @PathVariable("bookingDetailId") String bookingDetailId
    ) {

        RmsBookingDetailHwReplacementDAO hwD = new RmsBookingDetailHwReplacementDAO();
        RmsBookingDetailHwReplacement hw = hwD.getRmsBookingDetailHwReplacement(id);

        hwD = new RmsBookingDetailHwReplacementDAO();
        QueryResult queryResult = hwD.deleteRmsBookingDetailHwReplacement(id);

        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", hw.getItemId() + " is successfully deleted.");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to delete " + hw.getItemId() + ". Pls contact system admin.");
        }
        return "redirect:/rmsbookingDetail/detail/" + bookingDetailId + "?saved=1";
    }

    @RequestMapping(value = "/sendEmailReplacementByGroup/{bookingPkid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendEmailReplacementByGroup(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("bookingPkid") String bookingPkid
    ) {

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsD.getRmsBookingDetailByBookingPkid(bookingPkid);

        RmsBookingDetailHwReplacementDAO replaceD = new RmsBookingDetailHwReplacementDAO();
        List<RmsBookingDetailHwReplacement> replace = replaceD.getRmsBookingDetailHwReplacementListByBookingPkid(bookingPkid);

        String text = "";

        //update rmsBookingHardware table
        for (int i = 0; i < replace.size(); i++) {

            int index = i + 1;
            text = text + "<tr align = \"center\">";
            text = text + "<td>" + index + "</td>";
            text = text + "<td>" + replace.get(i).getItemType() + "</td>";
            text = text + "<td>" + replace.get(i).getItemId() + "</td>"; //
            text = text + "<td>" + replace.get(i).getQty() + "</td>"; //
            text = text + "<td>" + replace.get(i).getCreatedBy() + "</td>"; //
            text = text + "<td>" + replace.get(i).getRemarks() + "</td>"; //
            text = text + "</tr>";

            //update flag
            RmsBookingDetailHwReplacement replace2 = new RmsBookingDetailHwReplacement();
            replace2.setFlag("1");
            replace2.setId(replace.get(i).getId());
            replaceD = new RmsBookingDetailHwReplacementDAO();
            QueryResult queryResult = replaceD.updateRmsBookingDetailHwReplacementFlag(replace2);

//            LOGGER.info("replace.get(i).getBookingHwId(): " + replace.get(i).getBookingHwId());
            RmsBookingHardware h = new RmsBookingHardware();
            h.setRequestReplacementRemarks(replace.get(i).getRemarks());
            h.setStatus("Request for Replacement");
            h.setRequestReplacementBy(userSession.getFullname());
            h.setId(replace.get(i).getBookingHwId());
            RmsBookingHardwareDAO hD = new RmsBookingHardwareDAO();
            QueryResult q = hD.updateRmsBookingHardwareForRequestReplacement(h);

            if (q.getResult().equals("0")) {
                redirectAttrs.addFlashAttribute("error", "Failed to update bookingHwId: " + replace.get(i).getBookingHwId() + ". Pls contact system admin.");
                return "redirect:/rmsbookingDetail/detail/" + rms.getId();
            }
        }
        //send email
        EmailHwReplacementDAO userDao = new EmailHwReplacementDAO();
        List<EmailHwReplacement> userRecipientsList = userDao.getEmailHwReplacementList();

        String[] to = new String[userRecipientsList.size()];
        for (int x = 0; x < userRecipientsList.size(); x++) {
            to[x] = userRecipientsList.get(x).getEmail();
        }

        //get current date and time
        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedString = formatter.format(instance); //15-02-2022 12:43

        //gethostname
        HostnameDAO hostnameD = new HostnameDAO();
        Hostname host = hostnameD.getHostnameFlagZero();
        String hostname = host.getHostname();

        //send INFORMATION email
        LOGGER.info("######################### START EMAIL TO PIC ########################### ");
        EmailSender emailSender = new EmailSender();
        emailSender.htmlEmailTable(
                servletContext,
                "", //user name requestor
                to, //to
                //                        emailTo,
                "HW Prep for Loading - Request for HW replacement", //subject
                "<br />"
                + "Please be informed that the hardware below has been requested for replacement"
                + "<br /> "
                + "<br /> "
                + "RMS No: " + rms.getRmsNo()
                + "<br /> "
                + "RMS Event: " + rms.getEvent()
                + "<br /> "
                + "Requested Date: " + formattedString
                + "<br /> "
                + "<br /> "
                + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + rms.getId() + " \">HERE</a> for more detail."
                + "<br /> "
                + "<br /> "
                + "<style>table, th, td {border: 1px solid black; border-collapse: collapse;} th {background-color: #f06a0a;color: white;}</style>"
                + "<table style=\"width:100%\">" //tbl
                + "<tr>"
                + "<th>No.</th> "
                + "<th>Item Type</th> "
                + "<th>Item ID</th>"
                + "<th>Qty</th>"
                + "<th>Requested By</th>"
                + "<th>Remarks</th>"
                + "</tr>"
                //                + table(bookingPkid)
                + text
                + "</table>"
                + "<br /> "
                + "<br />Thank you." //msg
        );
        redirectAttrs.addFlashAttribute("success", "Email sent to planner.");
        return "redirect:/rmsbookingDetail/detail/" + rms.getId();

    }

    @RequestMapping(value = "/emailBody", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RmsBookingHardware emailBody(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String id
    ) throws IOException {

//        LOGGER.info("id: " + id);
        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        RmsBookingHardware rms = rmsHD.getRmsBookingHardware(id);
        LOGGER.info("itemId: " + rms.getItemId());

        return rms;
    }

    @RequestMapping(value = "/sendEmailReplacement", method = RequestMethod.POST)
    public String sendEmailReplacement(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id2,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String itemId2,
            @RequestParam(required = false) String remarks
    ) {

        RmsBookingHardwareDAO hD = new RmsBookingHardwareDAO();
        RmsBookingHardware hw = hD.getRmsBookingHardware(id2);

        RmsBookingHardware h = new RmsBookingHardware();
        h.setRequestReplacementRemarks(remarks);
        h.setStatus("Request for Replacement");
        h.setRequestReplacementBy(userSession.getFullname());
        h.setId(id2);
        hD = new RmsBookingHardwareDAO();
        QueryResult q = hD.updateRmsBookingHardwareForRequestReplacement(h);

        RmsBookingDetailDAO rmsD = new RmsBookingDetailDAO();
        RmsBookingDetail rms = rmsD.getRmsBookingDetailByBookingPkid(hw.getBookingPkid());

        if (q.getResult().equals("0")) {
            redirectAttrs.addFlashAttribute("error", "Failed to send email to planner. Pls contact system admin.");
            return "redirect:/rmsbookingDetail/detail/" + rms.getId();
        } else {

            //send email
            EmailHwReplacementDAO userDao = new EmailHwReplacementDAO();
            List<EmailHwReplacement> userRecipientsList = userDao.getEmailHwReplacementList();

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
            Hostname host = hostnameD.getHostnameFlagZero();
            String hostname = host.getHostname();

            //send INFORMATION email
            LOGGER.info("######################### START EMAIL TO PIC ########################### ");
            EmailSender emailSender = new EmailSender();
            emailSender.htmlEmailTable(
                    servletContext,
                    "", //user name requestor
                    to, //to
                    //                        emailTo,
                    "HW Prep for Loading - Request for HW replacement", //subject
                    "<br />"
                    + "Please be informed that the hardware below has been requested for replacement"
                    + "<br /> "
                    + "<br /> "
                    + "RMS No: " + rms.getRmsNo()
                    + "<br /> "
                    + "RMS Event: " + rms.getEvent()
                    + "<br /> "
                    + "Item Type: " + hw.getItemType()
                    + "<br /> "
                    + "Item ID: " + hw.getItemId()
                    + "<br /> "
                    + "Item Status: " + hw.getStatus()
                    + "<br /> "
                    + "Requested Date: " + formattedString
                    + "<br /> "
                    + "Remarks: " + remarks
                    + "<br /> "
                    + "<br /> "
                    + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/detail/" + rms.getId() + " \">HERE</a> for more detail."
                    + "<br /> "
                    + "<br />Thank you." //msg
            );

            redirectAttrs.addFlashAttribute("success", "Email sent to planner.");
            return "redirect:/rmsbookingDetail/detail/" + rms.getId();
        }
    }

    @RequestMapping(value = "/emailBodyByBookingkid", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<RmsBookingHardware> emailBodyByBookingkid(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String bookingPkid
    ) throws IOException {

//        LOGGER.info("id: " + id);
        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> rms = rmsHD.getRmsBookingHardwareListByBookingPkidWithFlagZeroForHwReplacement(bookingPkid);
//        LOGGER.info("itemId: " + rms.getItemId());

        return rms;
    }

    @RequestMapping(value = "/retrieveSF/{invId}/{pkid}/{id}/{rmsBookingId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String retrieveSF(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("invId") String invId,
            @PathVariable("pkid") String pkid,
            @PathVariable("id") String id,
            @PathVariable("rmsBookingId") String rmsBookingId
    ) throws ClassNotFoundException, SQLException {

        LOGGER.info("invId: " + invId);
        LOGGER.info("pkid: " + pkid);
        LOGGER.info("id: " + id);
        LOGGER.info("rmsBookingId: " + rmsBookingId);

        String himsRetrieve = HimsRetrieve.himsRetrieve(servletContext, userSession, invId);

        if (himsRetrieve.contains("Successfully")) {
            LOGGER.info("+++++++Retrieve Success+++++++");
            redirectAttrs.addFlashAttribute("success", "Item successfully recall from Storage Factory");

            //update item status
            RmsBookingHardware h = new RmsBookingHardware();
            h.setId(id);
            h.setStatus("Recall from Storage Factory");
            h.setRecallSfBy(userSession.getFullname());
            RmsBookingHardwareDAO hD = new RmsBookingHardwareDAO();
            QueryResult q = hD.updateRmsBookingHardwareForRecallSf(h);

        } else {
            LOGGER.info("+++++++Retrieve Failed+++++++");
            redirectAttrs.addFlashAttribute("error", "Failed to recall from Storage Factory. Pls contact system admin for more detail");
        }
        return "redirect:/rmsbookingDetail/detail/" + rmsBookingId;
    }

    //group
    @RequestMapping(value = "/groupDetail/{bookingId}/{itemPkid}", method = RequestMethod.GET)
    public String groupDetail(Model model,
            @PathVariable("bookingId") String bookingId,
            @PathVariable("itemPkid") String itemPkid,
            @ModelAttribute UserSession userSession) throws IOException {

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

        //vm tab
        RmsBookingVisualInspection itemVm = new RmsBookingVisualInspection();

        RmsBookingVisualInspectionDAO vmD = new RmsBookingVisualInspectionDAO();
        int count = vmD.getCountByGroupIdWithModuleBeforeLoading(groupId);
        if (count == 1) {
            vmD = new RmsBookingVisualInspectionDAO();
            itemVm = vmD.getRmsBookingVisualInspectionByGroupId(groupId);
        }
        model.addAttribute("itemVm", itemVm);

        LOGGER.info("itemVm.getPcbReject(): " + itemVm.getPcbReject());

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

        if (h.getSubStatus().contains("HW Registration")) {
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
        if (h.getSubStatus().contains("VM") || h.getSubStatus().contains("Visual Inspection")) {
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
        if (h.getSubStatus().contains("Test")) {
            String teActive = "active";
            String teActiveTab = "show active";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);
        } else {
            String teActive = "";
            String teActiveTab = "";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);
        }

        return "rmsbookingDetail/detail_group";
    }

    @RequestMapping(value = "/registerHwId", method = RequestMethod.POST)
    public String registerHwId(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String bookingPkid,
            @RequestParam(required = false) String motherboardId,
            @RequestParam(required = false) String hwId
    ) {

        //check hardware status first - must be available
        ItemHardwareDAO itemHwD = new ItemHardwareDAO();
        int countHwId = itemHwD.getCountAvailableHardwareId(hwId);
        if (countHwId == 0) {
            LOGGER.info("hwID status != Available");
            redirectAttrs.addFlashAttribute("error", hwId + " are not available. Pls register with another Hardware ID");
            return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
        } else {
            //check if active in rmsBookingHardwareGroup table (flag != 99)
            RmsBookingHardwareGroupDAO hwGroupD = new RmsBookingHardwareGroupDAO();
            int count = hwGroupD.getCountHwWithFlagNE99(hwId);
            if (count > 0) {
                LOGGER.info("hwID already active in rmsBookingHardwareGroup");
                redirectAttrs.addFlashAttribute("error", hwId + " already registered. Pls register with another Hardware ID");
                return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
            } else {
                //1st step to check qty requested. only can register if less than requested qty
                itemHwD = new ItemHardwareDAO();
                ItemHardware itemHw = itemHwD.getItemHardwareByHardwareId(hwId);
                ItemDAO itemD = new ItemDAO();
                Item item = itemD.getHardwareDetail(itemHw.getMibItemId());
                RmsBookingHardwareDAO rmsBookingHD = new RmsBookingHardwareDAO();
                int countRmsBookingHw = rmsBookingHD.getCountBookingPkidAndItemPkid(bookingPkid, item.getSptsPkid());
                if (countRmsBookingHw == 1) {
                    //get total qty per itemPkid and bookingPkid requested from booking_hardware table
                    rmsBookingHD = new RmsBookingHardwareDAO();
                    RmsBookingHardware rmsBookingH = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndItemPKid(bookingPkid, item.getSptsPkid());
                    int requestedQty = Integer.parseInt(rmsBookingH.getQty());

                    //get total qty register under same itemID and booking id (split from group id)
                    hwGroupD = new RmsBookingHardwareGroupDAO();
                    int totalQtyRegistered = hwGroupD.getCountHwWithinSameBookingPkidAndItemPkid(bookingPkid, item.getSptsPkid());

//                    LOGGER.info("totalQtyRegistered: " + totalQtyRegistered);
//                    LOGGER.info("requestedQty: " + requestedQty);
                    if (totalQtyRegistered >= requestedQty) {
                        LOGGER.info("totalQtyRegistered >= requestedQty");

                        redirectAttrs.addFlashAttribute("error", " You’ve already registered all the hardware allowed under Item ID: " + rmsBookingH.getItemId() + ". Total requested qty: " + requestedQty);
                        return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
                    } else {
                        //check if itemType = BIB. must be same with selected BIB Item ID
                        if ("BIB".equals(item.getItemType())) {
                            if (!motherboardId.equals(hwId)) {
                                LOGGER.info("BIB Hardware ID not same with group BIB Item ID");
                                redirectAttrs.addFlashAttribute("error", "Invalid Entry: This motherboard Hardware ID is not part of the selected hardware group.");
                                return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
                            }
                        }

                        //proceed to save to rms booking hardware group table
                        RmsBookingDetailDAO rmsBookingDAO = new RmsBookingDetailDAO();
                        RmsBookingDetail rmsBooking = rmsBookingDAO.getRmsBookingDetailByBookingPkid(bookingPkid);

                        RmsBookingHardwareGroup hwGroup = new RmsBookingHardwareGroup();
                        hwGroup.setGroupId(groupId);
                        hwGroup.setItemPkid(item.getSptsPkid());
                        hwGroup.setItemId(item.getItemId());
                        hwGroup.setItemType(item.getItemType());
                        hwGroup.setHardwarePkid(itemHw.getSptsPkid());
                        hwGroup.setHardwareId(hwId);
                        hwGroup.setRmsNo(rmsBooking.getRmsNo());
                        hwGroup.setEvent(rmsBooking.getEvent());
                        hwGroup.setSptsStatus(itemHw.getStatus());
                        hwGroup.setStatus("New");
                        hwGroup.setCreatedBy(userSession.getFullname());
                        hwGroup.setFlag("0");
                        hwGroupD = new RmsBookingHardwareGroupDAO();
                        QueryResult q = hwGroupD.insertRmsBookingHardwareGroup(hwGroup);
                        if (q.getResult() > 0) {

                            //update lc_qty/pc_qty at rmsbookinghardware table
                            String lcQty = "";
                            String pcQty = "";

                            String[] MbBookingHwPkid = groupId.split("/");
                            String mbBookingPkid = MbBookingHwPkid[1];

                            rmsBookingHD = new RmsBookingHardwareDAO();
                            int countBookingHwPkid = rmsBookingHD.getCountBookingPkidAndPkidForMotherboard(bookingPkid, mbBookingPkid);
                            if (countBookingHwPkid == 1) {
                                rmsBookingHD = new RmsBookingHardwareDAO();
                                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                                if (MbDetail.getLcQty() == null || "".equals(MbDetail.getLcQty())) {
                                    lcQty = "0";
                                } else {
                                    lcQty = MbDetail.getLcQty();
                                }
                                if (MbDetail.getPcQty() == null || "".equals(MbDetail.getPcQty())) {
                                    pcQty = "0";
                                } else {
                                    pcQty = MbDetail.getPcQty();
                                }
                                if ("Load Card".equals(rmsBookingH.getItemType())) {
                                    lcQty = String.valueOf(Integer.parseInt(lcQty) + 1);
                                } else if ("Program Card".equals(rmsBookingH.getItemType())) {
                                    pcQty = String.valueOf(Integer.parseInt(pcQty) + 1);
                                }

                                RmsBookingHardware hw = new RmsBookingHardware();
                                hw.setLcQty(lcQty);
                                hw.setPcQty(pcQty);
                                hw.setBookingPkid(bookingPkid);
                                hw.setPkid(mbBookingPkid);
                                rmsBookingHD = new RmsBookingHardwareDAO();
                                QueryResult qHw = rmsBookingHD.updateRmsBookingHardwareLcQtyAndPcQtyByBookingPkidAndPkid(hw);
                            }

                            //add log
                            RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
                            log.setGroupId(groupId);
                            log.setDetail("Register Item ID: " + item.getItemId());
                            log.setCreatedBy(userSession.getFullname());
                            RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
                            QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

                            redirectAttrs.addFlashAttribute("success", hwId + " is successfully registered.");
                            return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
                        } else {
                            LOGGER.info("Failed to insert into rmsBookingHardwareGroup table");
                            redirectAttrs.addFlashAttribute("error", "Failed to register. Pls contact system admin.");
                            return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
                        }
                    }
                } else {
                    LOGGER.info("No itemID under this bookingPkid");
                    redirectAttrs.addFlashAttribute("error", hwId + " not available. Pls register with another Hardware ID");
                    return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
                }
            }
        }
    }

    @RequestMapping(value = "/deleteHwId/{id}", method = RequestMethod.GET)
    public String deleteHwId(
            Model model,
            Locale locale,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id
    ) {
        RmsBookingHardwareGroupDAO hwD = new RmsBookingHardwareGroupDAO();
        RmsBookingHardwareGroup hw = hwD.getRmsBookingHardwareGroup(id);

        hwD = new RmsBookingHardwareGroupDAO();
        QueryResult queryResult = hwD.deleteRmsBookingHardwareGroup(id);

        if (queryResult.getResult() == 1) {

            String[] groupId = hw.getGroupId().split("/");
            String bookingPkid = groupId[0];
            String mbBookingPkid = groupId[1];

            //update lc_qty/pc_qty at rmsbookinghardware table
            String lcQty = "";
            String pcQty = "";

            RmsBookingHardwareDAO rmsBookingHD = new RmsBookingHardwareDAO();
            int countBookingHwPkid = rmsBookingHD.getCountBookingPkidAndPkidForMotherboard(bookingPkid, mbBookingPkid);
            if (countBookingHwPkid == 1) {
                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware MbDetail = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndPkid(bookingPkid, mbBookingPkid);

                if (MbDetail.getLcQty() == null || "".equals(MbDetail.getLcQty())) {
                    lcQty = "0";
                } else {
                    lcQty = MbDetail.getLcQty();
                }
                if (MbDetail.getPcQty() == null || "".equals(MbDetail.getPcQty())) {
                    pcQty = "0";
                } else {
                    pcQty = MbDetail.getPcQty();
                }

                rmsBookingHD = new RmsBookingHardwareDAO();
                RmsBookingHardware hardware = rmsBookingHD.getRmsBookingHardwareByBookingPkidAndItemPKid(bookingPkid, hw.getItemPkid());

                if ("Load Card".equals(hardware.getItemType())) {
                    lcQty = String.valueOf(Integer.parseInt(lcQty) - 1);
                } else if ("Program Card".equals(hardware.getItemType())) {
                    pcQty = String.valueOf(Integer.parseInt(pcQty) - 1);
                }

                RmsBookingHardware hw1 = new RmsBookingHardware();
                hw1.setLcQty(lcQty);
                hw1.setPcQty(pcQty);
                hw1.setBookingPkid(bookingPkid);
                hw1.setPkid(mbBookingPkid);
                rmsBookingHD = new RmsBookingHardwareDAO();
                QueryResult qHw = rmsBookingHD.updateRmsBookingHardwareLcQtyAndPcQtyByBookingPkidAndPkid(hw1);
            }

            //add log
            RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
            log.setGroupId(hw.getGroupId());
            log.setDetail("Removed Item ID: " + hw.getItemId());
            log.setCreatedBy(userSession.getFullname());
            RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
            QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

            redirectAttrs.addFlashAttribute("success", hw.getHardwareId() + " is successfully deleted.");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to delete " + hw.getHardwareId() + ". Pls contact system admin.");
        }
        return "redirect:/rmsbookingDetail/groupDetail/" + hw.getGroupId();
    }

    @RequestMapping(value = "/finalize/{bookingPkid}/{pkid}", method = RequestMethod.GET)
    public String finalize(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("bookingPkid") String bookingPkid,
            @PathVariable("pkid") String pkid
    ) {

        RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
        int countBookingHardware = booking.getCountBookingPkidAndPkidForMotherboard(bookingPkid, pkid);

        if (countBookingHardware == 1) {
            //update sub status to 'Pending VM'
            RmsBookingHardware bookHardware = new RmsBookingHardware();
            bookHardware.setBookingPkid(bookingPkid);
            bookHardware.setPkid(pkid);
            bookHardware.setSubStatus("Pending VM");
            booking = new RmsBookingHardwareDAO();
            QueryResult q = booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            if (q.getResult() == 1) {

                String groupId = bookingPkid + "/" + pkid;

                //add log
                RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
                log.setGroupId(groupId);
                log.setDetail("Finalized");
                log.setCreatedBy(userSession.getFullname());
                RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
                QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

                redirectAttrs.addFlashAttribute("success", "Finalization successful. Proceed to the next step (VM) when ready.");
                return "redirect:/rmsbookingDetail/groupDetail/" + bookingPkid + "/" + pkid;
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to finalize. Pls contact system admin.");
                return "redirect:/rmsbookingDetail/groupDetail/" + bookingPkid + "/" + pkid;
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to finalize. Pls contact system admin.");
            return "redirect:/rmsbookingDetail/groupDetail/" + bookingPkid + "/" + pkid;
        }

    }

    @RequestMapping(value = "/undoFinalize/{bookingPkid}/{pkid}", method = RequestMethod.GET)
    public String undoFinalize(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("bookingPkid") String bookingPkid,
            @PathVariable("pkid") String pkid
    ) {

        RmsBookingHardwareDAO booking = new RmsBookingHardwareDAO();
        int countBookingHardware = booking.getCountBookingPkidAndPkidForMotherboard(bookingPkid, pkid);

        if (countBookingHardware == 1) {
            //update sub status to 'Pending VM'
            RmsBookingHardware bookHardware = new RmsBookingHardware();
            bookHardware.setBookingPkid(bookingPkid);
            bookHardware.setPkid(pkid);
            bookHardware.setSubStatus("Pending HW Registration");
            booking = new RmsBookingHardwareDAO();
            QueryResult q = booking.updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(bookHardware);
            if (q.getResult() == 1) {

                String groupId = bookingPkid + "/" + pkid;

                //add log
                RmsBookingHardwareGroupLog log = new RmsBookingHardwareGroupLog();
                log.setGroupId(groupId);
                log.setDetail("Revert Finalization");
                log.setCreatedBy(userSession.getFullname());
                RmsBookingHardwareGroupLogDAO logD = new RmsBookingHardwareGroupLogDAO();
                QueryResult logQ = logD.insertRmsBookingHardwareGroupLog(log);

                redirectAttrs.addFlashAttribute("success", "Undo successful. This item group is now open for modifications.");
                return "redirect:/rmsbookingDetail/groupDetail/" + bookingPkid + "/" + pkid;
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to undo the finalization. Pls contact system admin.");
                return "redirect:/rmsbookingDetail/groupDetail/" + bookingPkid + "/" + pkid;
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to undo the finalization. Pls contact system admin.");
            return "redirect:/rmsbookingDetail/groupDetail/" + bookingPkid + "/" + pkid;
        }

    }

    @RequestMapping(value = "/vm/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemVmSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
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
            @RequestParam(required = false) MultipartFile winConnectorRejectUpload
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
        RmsBookingVisualInspection itemVm = new RmsBookingVisualInspection();
        itemVm.setGroupId(groupId);
        itemVm.setModule("Before Loading");
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

        if ("Fail".equals(pcb) || "Fail".equals(handle) || "Fail".equals(metalFrame) || "Fail".equals(hardwareFasterners) || "Fail".equals(clipHolder) || "Fail".equals(pcbEdgeFinger) || "Fail".equals(connector)
                || "Fail".equals(dutSockets) || "Fail".equals(edgeMbBanana) || "Fail".equals(electComponent) || "Fail".equals(solderJoint) || "Fail".equals(winConnector)) {
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
                maverick.setModule("Before Loading");
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
                        "Item Registration - Failed Visual Inspection", //subject
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
                        + "Please click <a href=\"http://" + hostname + "/HEATS/rmsbookingDetail/groupDetail/" + groupId + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("error", "Visual Inspection Fail. Pls go to Maverick Module for Corrective Action.");
                return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
            } else {
                redirectAttrs.addFlashAttribute("success", "Visual Inspection Pass.");
                return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to save Visual Inspection. Pls Contact System Admin");
            return "redirect:/rmsbookingDetail/groupDetail/" + groupId;
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
}
