package com.onsemi.mib.controller;

import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemMaverickDAO;
import com.onsemi.mib.dao.LDAPUserDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import com.onsemi.mib.dao.RetrieveDAO;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingLogDAO;
import com.onsemi.mib.dao.SRInventoryMgtDAO;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingLog;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSWebService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("userSession")
@PropertySource("classpath:ldap.properties")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String selectedProgram) throws IOException {

        HttpSession currentSession = request.getSession();
        UserSession userSession = (UserSession) currentSession.getAttribute("userSession");

        LocalDateTime instance = LocalDateTime.now();
        Integer month = Integer.valueOf(instance.toString().substring(5, 7));
//        Integer month = 12;
        Integer year = Integer.valueOf(instance.toString().substring(0, 4));
//        Integer year = 2027;
        List<String> monthNameList = new ArrayList<String>();

        String yearLabel = "";
        if (month < 12) {
            yearLabel = (year - 1) + "/" + (year);
        } else if (month == 12) {
            yearLabel = year.toString();
        }

        for (int x = 1; x <= 12; x++) {
            if (month < 1) {
//                year = year - 1;
                year -= 1;
                month = 12;
            }
            Month monthN = Month.of(month);
            String monthNameFull = monthN.name();
            String monthName = monthNameFull.substring(0, 3);
            String year1 = year.toString().substring(2, 4);
            String fullMonthYear = "'" + monthName + " " + year1 + "'";
            monthNameList.add(fullMonthYear);
            month -= 1;
        }
        Collections.reverse(monthNameList);
//        LOGGER.info("monthNameList " + monthNameList);
        model.addAttribute("monthNameList", monthNameList);
        model.addAttribute("yearLabel", yearLabel);

        ItemDAO itemDao = new ItemDAO();
        int countItemPending = itemDao.getCountItemWithFlagZero();
        model.addAttribute("countItemPending", countItemPending);

        ItemMaverickDAO itemMaverickD = new ItemMaverickDAO();
        int countMaverick = itemMaverickD.getCountFlagZero();
        model.addAttribute("countMaverick", countMaverick);

        itemMaverickD = new ItemMaverickDAO();
        List<ItemMaverick> maverickList = itemMaverickD.getItemMaverickListFlagZero();
        model.addAttribute("maverickList", maverickList);

        if (userSession != null) {

            ItemDAO itemD = new ItemDAO();
            List<Item> item = itemD.getItemListPendingVMFunctionalTest();
            model.addAttribute("item", item);

            String groupId = userSession.getGroup();
            model.addAttribute("groupId", groupId);

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

            List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();

            //save to user table if not registered yet
            if ("0".equals(groupId)) {
                //Start Retrieve LDAP Users
                Hashtable h = new Hashtable();
                h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

                DirContext ctx = null;
                NamingEnumeration results = null;

                try {
                    ctx = new InitialDirContext(h);
                    SearchControls controls = new SearchControls();
                    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                    String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid", "onoraclelocation"};
                    controls.setReturningAttributes(attrIDs);
                    //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
                    //Onsemi
//                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
                    results = ctx.search("ou=ONSemi", "(cn=" + userSession.getLoginId() + ")", controls);

                    while (results.hasMore()) {
                        SearchResult searchResult = (SearchResult) results.next();
                        Attributes attributes = searchResult.getAttributes();

                        LDAPUser user = new LDAPUser();

                        Enumeration e = attributes.getIDs();
                        while (e.hasMoreElements()) {
                            String key = (String) e.nextElement();
                            if (key.equalsIgnoreCase("givenName")) {
                                user.setFirstname(attributes.get(key).get().toString());
                            }
                            if (key.equalsIgnoreCase("sn")) {
                                user.setLastname(attributes.get(key).get().toString());
                            }
                            if (key.equalsIgnoreCase("title")) {
                                user.setTitle(attributes.get(key).get().toString());
                            }
                            if (key.equalsIgnoreCase("cn")) {
                                user.setLoginId(attributes.get(key).get().toString());
                            }
                            if (key.equalsIgnoreCase("mail")) {
                                user.setEmail(attributes.get(key).get().toString());
                            }
                            if (key.equalsIgnoreCase("oncid")) {
                                user.setOncid(attributes.get(key).get().toString());
                            }
//                            System.out.println("onoraclelocation: " + attributes.get(key).get().toString());
//                        LOGGER.info("onoraclelocation: " + attributes.get(key).get().toString());
                        }

                        ldapUserList.add(user);
                    }
                } catch (NamingException e) {
                    LOGGER.error(e.getMessage());
                } finally {
                    if (results != null) {
                        try {
                            results.close();
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                    if (ctx != null) {
                        try {
                            ctx.close();
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                }
                //End Retrieve LDAP Users

                for (int i = 0; i < ldapUserList.size(); i++) {

                    LDAPUser ldap = new LDAPUser();
                    ldap.setLoginId(ldapUserList.get(i).getLoginId());
                    ldap.setOncid(ldapUserList.get(i).getOncid());
                    ldap.setFirstname(ldapUserList.get(i).getFirstname());
                    ldap.setLastname(ldapUserList.get(i).getLastname());
                    ldap.setEmail(ldapUserList.get(i).getEmail());
                    ldap.setTitle(ldapUserList.get(i).getTitle());
                    ldap.setGroupId("3");
                    ldap.setRequestAccess("No");
                    LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
                    QueryResult queryResult = ldapUserDAO.insert(ldap);
//                    System.out.println("++queryResult : " + queryResult.getGeneratedKey());
                    return "redirect:/";
                }

            } else if ("3".equals(groupId)) { //return different page if group - fresh user
//                model.addAttribute("groupId", groupId);
                return "home/index1";
            }

            //Anything for Dashboard
            return "home/index";
        } else {
            return "home/index";
        }
    }

    @RequestMapping(value = "/sentAccessRequest/{loginId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String sentAccessRequest(Model model,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId) {

        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        LDAPUser ldapUser = ldapUserDAO.getByLoginId(loginId);

        //update user table
        LDAPUser lu = new LDAPUser();
        lu.setRequestAccess("Yes");
        lu.setLoginId(loginId);
        ldapUserDAO = new LDAPUserDAO();
        QueryResult q = ldapUserDAO.updateRequestAccessByLoginId(lu);

        //send email to global-rel-it to manual sync global table via SPTS 
        List<String> emails = new ArrayList<String>();
        emails.add("global-rel-it@onsemi.com"); // add email requestor to the list

        String[] myArray = new String[emails.size()];
        String[] emailTo = emails.toArray(myArray);
        //get current date and time
        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedString = formatter.format(instance); //15-02-2022 12:43

        //gethostname
        HostnameDAO hostnameD = new HostnameDAO();
        Hostname h = hostnameD.getHostnameFlagZero();
        String hostname = h.getHostname();

        //send INFORMATION email
        LOGGER.info("######################### START EMAIL TO PIC ########################### ");
        EmailSender emailSender = new EmailSender();
        emailSender.htmlEmailTable(
                servletContext,
                "", //user name requestor
                //                    to, //to
                emailTo,
                "New User Request", //subject
                "<br />"
                + "Pls be informed that new user access has been requested thru HEATS."
                + "<br /> "
                + "<br /> "
                + "Login ID: " + loginId
                + "<br /> "
                + "Full Name: " + ldapUser.getFirstname() + " " + ldapUser.getLastname()
                + "<br /> "
                + "Title: " + ldapUser.getTitle()
                + "<br /> "
                + "Request Date: " + formattedString
                + "<br /> "
                + "<br /> "
                + "Please click <a href=\"http://" + hostname + "/HEATS/admin/user/edit/" + ldapUser.getId() + " \">HERE</a> to update user account."
                + "<br /> "
                + "<br />Thank you." //msg
        );

        return "redirect:/";
    }

    @RequestMapping(value = "/srReady", method = {RequestMethod.GET, RequestMethod.POST})
    public String srReady(Model model, HttpServletRequest request) {
        SRInventoryMgtDAO invDao = new SRInventoryMgtDAO();
        int countNewRms = invDao.getCountReady();
        model.addAttribute("countNewReady", countNewRms);
        return "home/srReady";
    }

    @RequestMapping(value = "/srRetrieve", method = {RequestMethod.GET, RequestMethod.POST})
    public String srRetrieve(Model model, HttpServletRequest request) {
        SRInventoryMgtDAO invDao = new SRInventoryMgtDAO();
        int countNewRetrieve = invDao.getCountRetrieval();
        model.addAttribute("countNewRetrieve", countNewRetrieve);
        return "home/srRetrieve";
    }

    @RequestMapping(value = "/srSample", method = {RequestMethod.GET, RequestMethod.POST})
    public String srSample(Model model, HttpServletRequest request) {
        SRInventoryMgtDAO invDao = new SRInventoryMgtDAO();
        int countNewSample = invDao.getCountSample();
        model.addAttribute("countNewSample", countNewSample);
        return "home/srSample";
    }

    @RequestMapping(value = "/srShelf", method = {RequestMethod.GET, RequestMethod.POST})
    public String srShelff(Model model, HttpServletRequest request) {
        SRInventoryMgtDAO invDao = new SRInventoryMgtDAO();
        int countNewShelf = invDao.getCountShelf();
        model.addAttribute("countNewShelf", countNewShelf);
        return "home/srShelf";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String loginError(
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            Locale locale) {

        LOGGER.info("Login LDAP Error! Please retry using Non-LDAP ID and Password. If problem persist, please contact Admin.");
        LOGGER.info(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString());
        redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Login LDAP Error! Please retry using Non-LDAP ID and Password. If problem persist, contact Admin.", args, locale));
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttrs, Locale locale) {
        redirectAttrs.addFlashAttribute("logout", messageSource.getMessage("general.label.logout", args, locale));
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(Model model, HttpServletRequest request) {
        return "home/register";
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model, HttpServletRequest request) {

        HttpSession currentSession = request.getSession();
        UserSession userSession = (UserSession) currentSession.getAttribute("userSession");

        String fullDebit = "";
        String fullCredit = "";
        Integer totalFrom = 0;
        Integer totalTo = 0;
        int countRmsReady = 0;
        int countRetrieval = 0;
        int countSample = 0;
        int countShelf = 0;

        SRInventoryMgtDAO invDao01 = new SRInventoryMgtDAO();
        countRmsReady = invDao01.getCountReady();

        SRInventoryMgtDAO invDao02 = new SRInventoryMgtDAO();
        countRetrieval = invDao02.getCountRetrieval();

        SRInventoryMgtDAO invDao03 = new SRInventoryMgtDAO();
        countSample = invDao03.getCountSample();

        SRInventoryMgtDAO invDao04 = new SRInventoryMgtDAO();
        countShelf = invDao04.getCountShelf();

        model.addAttribute("totalTo", totalTo);
        model.addAttribute("totalFrom", totalFrom);
        model.addAttribute("fullDebit", fullDebit);
        model.addAttribute("fullCredit", fullCredit);
        model.addAttribute("countRmsReady", countRmsReady);
        model.addAttribute("countRetrieval", countRetrieval);
        model.addAttribute("countSample", countSample);
        model.addAttribute("countShelf", countShelf);

        int year = LocalDate.now().getYear();
        model.addAttribute("year", year);

        List<InventoryMgt> shipmentInAndOut = new ArrayList<InventoryMgt>();
        InventoryMgt dataload;
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int x = 1; x < 13; x++) {
            RetrieveDAO graph0 = new RetrieveDAO();
            Integer countInventory = graph0.getCountInventory(Integer.toString(x), String.valueOf(year));
            RetrieveDAO graph1 = new RetrieveDAO();
            Integer countRetrieve = graph1.getCountRetrieve(Integer.toString(x), String.valueOf(year));

            fullDebit = fullDebit.concat(countInventory.toString()).concat(",");
            fullCredit = fullCredit.concat(countRetrieve.toString()).concat(",");
            totalFrom += countInventory;
            totalTo += countRetrieve;
            String month = monthName[x - 1];

            dataload = new InventoryMgt();
            dataload.setRackMonth(month);
            dataload.setRack(countInventory.toString());
            dataload.setShelf(countRetrieve.toString());
            shipmentInAndOut.add(dataload);
        }

        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
//        LOGGER.info("groupId = " + groupId);

        //sample retention
        model.addAttribute("totalTo", totalTo);
        model.addAttribute("totalFrom", totalFrom);
        model.addAttribute("shipmentInAndOut", shipmentInAndOut);
        model.addAttribute("fullDebit", fullDebit);
        model.addAttribute("fullCredit", fullCredit);

//        if (userSession != null) {
//            String groupId = userSession.getGroup();
//            model.addAttribute("groupId", groupId);
//            //Anything for Dashboard
////            return "home/index";
//        } else {
////            return "home/index";
//        }
        return "home/home";
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
        return "redirect:/";
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
        return "redirect:/";
    }

}
