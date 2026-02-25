package com.onsemi.mib.controller;

import com.onsemi.mib.dao.EmailHwReplacementDAO;
import com.onsemi.mib.dao.EmailVmFailDAO;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingHardwareDAO;
import com.onsemi.mib.dao.RmsBookingLogDAO;
import com.onsemi.mib.model.EmailHwReplacement;
import com.onsemi.mib.model.EmailVmFail;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingHardware;
import com.onsemi.mib.model.RmsBookingLog;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSWebService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.servlet.ServletContext;
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
            eqpt.setStatus("New");
            eqpt.setFlag("0");
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
                LOGGER.info("BookingPkid: " + rms.get(i).getBookingPkid());
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
        model.addAttribute("booking", booking);

        rmsD = new RmsBookingDetailDAO();
        int countBooking = rmsD.getCountBookingFlagZero();
        model.addAttribute("countBooking", countBooking);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> priorityList = pD.getGroupParameterDetailListForPriorityBooking("", "019");
        model.addAttribute("priorityList", priorityList);

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
                        } else {
                            //check status if requested for replacement or not
                            RmsBookingHardwareDAO rmsBH = new RmsBookingHardwareDAO();
                            int count = rmsBH.getCountBookingId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("booking_pkid")), Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
                            if (count == 1) {
                                rmsBH = new RmsBookingHardwareDAO();
                                RmsBookingHardware rmsB = rmsBH.getRmsBookingHardwareByPkid(Integer.toString(getItemByParamV.getJSONObject(i).getInt("pkid")));
                                if (rmsB.getStatus().contains("Request for Replacement")) {
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

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "rmsbookingDetail/add";
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
