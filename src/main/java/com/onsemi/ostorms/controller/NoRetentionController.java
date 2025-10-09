package com.onsemi.ostorms.controller;

import com.onsemi.ostorms.dao.FTPDao;
import com.onsemi.ostorms.dao.LogFtpDAO;
import com.onsemi.ostorms.dao.SRArchiveDAO;
import com.onsemi.ostorms.dao.SampleRequestDAO;
import com.onsemi.ostorms.model.FTPdata;
import com.onsemi.ostorms.model.LogFtp;
import com.onsemi.ostorms.model.SRArchive;
import com.onsemi.ostorms.model.UserSession;
import com.onsemi.ostorms.tools.QueryResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/sr/noRetention")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class NoRetentionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoRetentionController.class);
    String[] args = {};
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String noRetention(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
        SRArchiveDAO srachivedao = new SRArchiveDAO();
        List<SRArchive> srArchiveList = srachivedao.getAllDataViewLatest();

        String groupId = userSession.getGroup();

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm a");
        Date date = new Date();
        String nowDate = dateFormat.format(date);

        model.addAttribute("userSession", userSession);
        model.addAttribute("srArchiveList", srArchiveList);
        model.addAttribute("groupId", groupId);
        model.addAttribute("nowDate", nowDate);

        return "noRetention/noRetention";
    }

    @RequestMapping(value = "/addReq", method = RequestMethod.GET)
    public String addReq(
            Model model,
            Locale locale,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession
    ) {
//        String groupId = userSession.getGroup();

        SampleRequestDAO rmsDAO = new SampleRequestDAO();
        List<FTPdata> rmsList = rmsDAO.getAllDistinctRMSNoOnly();

        String username = userSession.getFullname();

        model.addAttribute("username", username);
        model.addAttribute("rmsList", rmsList);
        model.addAttribute("userSession", userSession);

        return "noRetention/addReq";
    }

    @RequestMapping(value = "/addReq/test/{rmsNo}", method = RequestMethod.GET)
    public @ResponseBody
    List test(
            Model model,
            @PathVariable("rmsNo") String rmsNo,
            @ModelAttribute UserSession userSession
    ) {
        FTPDao ftpDAO = new FTPDao();
        List<FTPdata> eventList = ftpDAO.getAllEventPerRms(rmsNo);
        model.addAttribute("eventList", eventList);

        return eventList;
    }

    @RequestMapping(value = "/addReq/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public String addReqSubmit(
            Model model,
            Locale locale,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String reasonsExclude,
            @RequestParam(required = false) String othersDetails,
            @RequestParam(required = false) String requestorName,
            @RequestParam(required = false) String relRequestorName,
            @RequestParam(required = false) String relDateRequest,
            @RequestParam(required = false) String rmsNo,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String remarks,
            @ModelAttribute UserSession userSession
    ) {
        String redirect = "";

        if (reasonsExclude.equals("Others")) {
            reasonsExclude = reasonsExclude + " - " + othersDetails;
        }

        if (relDateRequest == null) {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Invalid Rel Date Request. Please try again.", args, locale));
            redirect = "redirect:/sr/noRetention/addReq";
        } else {
            if (relDateRequest.equals("")) {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Invalid Rel Date Request. Please try again.", args, locale));
                redirect = "redirect:/sr/noRetention/addReq";
            } else {
                FTPDao ftpDao = new FTPDao();
                List<FTPdata> detailsList = null;
                if (event.equals("All")) {
                    detailsList = ftpDao.getAllActualDetailsPerRmsExtQuery(rmsNo, "");
                } else {
                    String extQuery = " AND rms_event = '" + event + "' ";
                    detailsList = ftpDao.getAllActualDetailsPerRmsExtQuery(rmsNo, extQuery);
                }

                for (int x = 0; x < detailsList.size(); x++) {
                    String ftpId = detailsList.get(x).getId();
                    String groupId = detailsList.get(x).getGroupId();

                    FTPdata ftpdata = new FTPdata();
                    ftpdata.setStatus("New Record - No Retention");
                    ftpdata.setFlag("1");
                    ftpdata.setModifiedBy(userSession.getFullname());
                    ftpdata.setId(ftpId);
                    ftpDao = new FTPDao();
                    QueryResult qr = ftpDao.updateStatus(ftpdata);

                    if (qr.getResult() == 1) {

                        LogFtp log = new LogFtp();
                        log.setFtpId(ftpId);
                        log.setDetail("New Record - No Retention");
                        log.setCreatedBy(userSession.getFullname());
                        LogFtpDAO logD = new LogFtpDAO();
                        QueryResult logQ = logD.insertLogFtp(log);

                        SRArchiveDAO srachivedao = new SRArchiveDAO();
                        int kira = srachivedao.getCountExistingGroupId(Integer.parseInt(groupId));
                        if (kira == 0) {
                            SRArchive srArchive = new SRArchive();
                            srArchive.setGroupId(groupId);
                            srArchive.setReqType(requestType);
                            srArchive.setReasonsExc(reasonsExclude);
                            srArchive.setReqName(requestorName);
                            srArchive.setRelReqName(relRequestorName);
                            srArchive.setRelDateReq(relDateRequest);
                            srArchive.setRemarks(remarks);
                            srArchive.setModifiedBy(requestorName);
                            srArchive.setCreatedBy(requestorName);
                            srArchive.setStatus("Archived");
                            srArchive.setFlag("0");
                            srachivedao = new SRArchiveDAO();
                            QueryResult qrArch = srachivedao.insertArchive(srArchive);
                            if (qrArch.getResult() != 1) {
                                LOGGER.info("Failed to add into Archive table");
                            } else {
                            }
                        }
                        redirectAttrs.addFlashAttribute("success", messageSource.getMessage("New record for No Retention Plan has been Added.", args, locale));
                    }
                }
                redirect = "redirect:/sr/noRetention";
            }
        }

        SampleRequestDAO rmsDAO = new SampleRequestDAO();
        List<FTPdata> rmsList = rmsDAO.getAllDistinctRMSNoOnly();

        model.addAttribute("rmsList", rmsList);
        model.addAttribute("userSession", userSession);

        return redirect;
    }

    @RequestMapping(value = "/revertCancel/{id}", method = RequestMethod.GET)
    public String cancelRetention(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id
    ) {

        SRArchiveDAO srD = new SRArchiveDAO();
        SRArchive sr = srD.getDataById(id);

        LOGGER.info("FTP ID : " + sr.getFtpId());

        FTPDao ftpDao = new FTPDao();
        int count = ftpDao.getCountFtpById(sr.getFtpId());

        if (count != 0) {
            FTPdata ftpdata = new FTPdata();
            ftpdata.setStatus("New Record");
            ftpdata.setFlag("0");
            ftpdata.setModifiedBy(userSession.getFullname());
            ftpdata.setId(sr.getFtpId());
            ftpDao = new FTPDao();
            QueryResult qr = ftpDao.updateStatusbyFtpId(ftpdata);
            if (qr.getResult() != 0) {

                LogFtp log = new LogFtp();
                log.setFtpId(sr.getFtpId());
                log.setDetail("Revert Cancellation.");
                log.setCreatedBy(userSession.getFullname());
                LogFtpDAO logD = new LogFtpDAO();
                QueryResult logQ = logD.insertLogFtp(log);

                SRArchive srArchive = new SRArchive();
                srArchive.setId(id);
                srArchive.setModifiedBy(userSession.getFullname());
                srArchive.setStatus("Removed from Archived");
                srArchive.setFlag("9");
                SRArchiveDAO srAchiveDao = new SRArchiveDAO();
                QueryResult qrArch = srAchiveDao.updateStatusPerId(srArchive);
                if (qrArch.getResult() != 0) {
                    redirectAttrs.addFlashAttribute("success", "Request has been removed from No Retention Plan List");
                } else {
                    redirectAttrs.addFlashAttribute("error", "Failed to removed the request from No Retention Plan List. Please try again.");
                }
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to removed the request from No Retention Plan List. Please try again.");
        }

        return "redirect:/sr/noRetention";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id) {

        SRArchiveDAO srachivedao = new SRArchiveDAO();
        SRArchive arc = srachivedao.getDataById(id);

        SRArchive arc1 = new SRArchive();
        arc1.setId(id);
        arc1.setFlag("99");
        arc1.setStatus("Deleted");
        arc1.setModifiedBy(userSession.getFullname());

        srachivedao = new SRArchiveDAO();
        QueryResult queryResult = srachivedao.updateStatusPerId(arc1);

        LogFtp log = new LogFtp();
        log.setFtpId(arc.getFtpId());
        log.setDetail("Deleted from No Retention List");
        log.setCreatedBy(userSession.getFullname());
        LogFtpDAO logD = new LogFtpDAO();
        QueryResult logQ = logD.insertLogFtp(log);

        FTPDao ftpDao = new FTPDao();
        FTPdata ftp = ftpDao.getFtpDataById(arc.getFtpId());

        args = new String[1];
        args[0] = ftp.getRmsLotEvent() + " - " + ftp.getPkgName();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/sr/noRetention";
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String lot,
            @RequestParam(required = false) String pkgFamily,
            @RequestParam(required = false) String pkgName,
            @RequestParam(required = false) String monthScrap,
            @RequestParam(required = false) String status
    ) {
        String query = "";
        int count = 0;

        SRArchiveDAO arcD = new SRArchiveDAO();
        List<SRArchive> statusList = arcD.getDistinctStatus();
        model.addAttribute("statusList", statusList);

        arcD = new SRArchiveDAO();
        List<SRArchive> lotList = arcD.getDistinctLotType();
        model.addAttribute("lotList", lotList);

        arcD = new SRArchiveDAO();
        List<SRArchive> eventList = arcD.getDistinctEvent();
        model.addAttribute("eventList", eventList);

        arcD = new SRArchiveDAO();
        List<SRArchive> pkgNameList = arcD.getDistinctPkgName();
        model.addAttribute("pkgNameList", pkgNameList);

        arcD = new SRArchiveDAO();
        List<SRArchive> pkgFamilyList = arcD.getDistinctPkgFamily();
        model.addAttribute("pkgFamilyList", pkgFamilyList);

        arcD = new SRArchiveDAO();
        List<SRArchive> rmsList = arcD.getDistinctRmsNo();
        model.addAttribute("rmsList", rmsList);

        if (rms != null) {
            if (!rms.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ft.rms_id LIKE '" + rms + "%'";
                } else if (count > 1) {
                    query = query + " AND ft.rms_id LIKE '" + rms + "%'";
                }
            }
        }

        if (lot != null) {
            if (!lot.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ft.lot_type = '" + lot + "\' ";
                } else if (count > 1) {
                    query = query + " AND ft.lot_type = '" + lot + "\' ";
                }
            }
        }

        if (event != null) {
            if (!event.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ft.rms_event = '" + event + "\' ";
                } else if (count > 1) {
                    query = query + " AND ft.rms_event = '" + event + "\' ";
                }
            }
        }

        if (pkgFamily != null) {
            if (!pkgFamily.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ft.pkg_family = \'" + pkgFamily + "\' ";
                } else if (count > 1) {
                    query = query + " AND ft.pkg_family = \'" + pkgFamily + "\' ";
                }
            }
        }

        if (monthScrap != null) {
            if (!monthScrap.equals("")) {
                count++;
                LOGGER.info("monthScrap...." + monthScrap);
                if (count == 1) {
                    query = query + " WHERE ft.mth_to_scrap LIKE \'" + monthScrap + "%' ";
                } else if (count > 1) {
                    query = query + " AND ft.mth_to_scrap LIKE \'" + monthScrap + "%' ";
                }
            }
        }

        if (pkgName != null) {
            if (!pkgName.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ft.pkg_name = '" + pkgName + "\' ";
                } else if (count > 1) {
                    query = query + " AND ft.pkg_name = '" + pkgName + "\' ";
                }
            }
        }

        if (status != null) {
            if (!status.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ar.status = '" + status + "\' ";
                } else if (count > 1) {
                    query = query + " AND ar.status = '" + status + "\' ";
                }
            }
        }

        String finalQuery = "";

        if (count != 0) {

            finalQuery = "SELECT ar.*, ft.rms_id, ft.rms_event, ft.lot_type, ft.rmslot_event, ft.pkg_family, ft.pkg_name, "
                    + "UPPER(DATE_FORMAT(ft.mth_to_scrap,'%b %y')) AS mth_to_scrap_view "
                    + "FROM sr_archive ar "
                    + "LEFT JOIN sr_ftp_data ft ON ft.id = ar.ftp_id " + query + " GROUP BY ftp_id";

        } else {
            finalQuery = "SELECT * FROM sr_archive WHERE flag = '1000'";
        }

        System.out.println("finalQuery: " + finalQuery);

        arcD = new SRArchiveDAO();
        List<SRArchive> SrQuery = arcD.getAllQueryList(finalQuery);
        model.addAttribute("SrQuery", SrQuery);

        return "noRetention/query";
    }

    @RequestMapping(value = "/query/detail/{ftpId}", method = RequestMethod.GET)
    public String whShipping(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("ftpId") String ftpId
    ) {
        LogFtpDAO logD = new LogFtpDAO();
        List<LogFtp> log = logD.getLogFtpListByFtpId(ftpId);

        FTPDao ftpDao = new FTPDao();
        FTPdata ftp = ftpDao.getFtpDataById(ftpId);

        model.addAttribute("log", log);
        model.addAttribute("rmsLotEvent", ftp.getRmsLotEvent());
        return "noRetention/queryDetail";
    }

}
