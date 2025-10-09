package com.onsemi.mib.controller;

import com.onsemi.mib.dao.DOListDAO;
import com.onsemi.mib.dao.LogDAO;
import com.onsemi.mib.dao.LogModuleDAO;
import com.onsemi.mib.dao.SampleRequestDAO;
import com.onsemi.mib.model.DOList;
import com.onsemi.mib.model.Log;
import com.onsemi.mib.model.LogOuterBox;
import com.onsemi.mib.model.SampleRequest;
import com.onsemi.mib.model.UserSession;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/sr/query")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class SRQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRQueryController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String rmsevent,
            @RequestParam(required = false) String packageFamily,
            @RequestParam(required = false) String inventory,
            @RequestParam(required = false) String monthScrap,
            @RequestParam(required = false) String inventoryDate,
            @RequestParam(required = false) String retrieveDate,
            @RequestParam(required = false) String status) {

        String query = "";
        int count = 0;

        SampleRequestDAO srD = new SampleRequestDAO();
//        List<SampleRequest> srPkgFamily = srD.getDistinctPkgFamilyList();
        List<SampleRequest> srPkgName = srD.getPackageList();
        model.addAttribute("srPkgName", srPkgName);

        srD = new SampleRequestDAO();
        List<SampleRequest> srStatus = srD.getRequestStatus();
        model.addAttribute("srStatus", srStatus);

        srD = new SampleRequestDAO();
        List<SampleRequest> srRms = srD.getAllRms();
        model.addAttribute("srRms", srRms);

//        srD = new SampleRequestDAO();
//        List<SampleRequest> srEvent = srD.getDistinctEventList();
//        model.addAttribute("srEvent", srEvent);
//        DOListDAO doListDao = new DOListDAO();
//        List<DOList> gtsList = doListDao.getAllGtsNo();
//        model.addAttribute("gtsList", gtsList);
//        if (boxNo != null) {
//            if (!boxNo.equals("")) {
//                count++;
//                if (count == 1) {
//                    query = query + " WHERE r.req_box_id LIKE '" + boxNo + "%'";
//                } else if (count > 1) {
//                    query = query + " AND r.req_box_id LIKE '" + boxNo + "%'";
//                }
//            }
//        }
        if (rms != null) {
            if (!rms.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE rms_id LIKE '" + rms + "%'";
                } else if (count > 1) {
                    query = query + " AND rms_id LIKE '" + rms + "%'";
                }
            }
        }

        if (rmsevent != null) {
            if (!rmsevent.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE rmslot_event LIKE \'%" + rmsevent + "%\' ";
                } else if (count > 1) {
                    query = query + " AND rmslot_event LIKE \'%" + rmsevent + "%\' ";
                }
            }
        }

        if (packageFamily != null) {
            if (!packageFamily.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE pkg_name = \'" + packageFamily + "\' ";
                } else if (count > 1) {
                    query = query + " AND pkg_name = \'" + packageFamily + "\' ";
                }
            }
        }

        if (inventory != null) {
            if (!inventory.equals("")) {
                count++;
                LOGGER.info("inventory...." + inventory);
                if (count == 1) {
                    query = query + " WHERE inventory_shelf LIKE \'" + inventory + "%' ";
                } else if (count > 1) {
                    query = query + " AND inventory_shelf LIKE \'" + inventory + "%' ";
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

        if (inventoryDate != null) {
            if (!inventoryDate.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE inventory_date LIKE \'" + inventoryDate + "%' ";
                } else if (count > 1) {
                    query = query + " AND inventory_date LIKE \'" + inventoryDate + "%' ";
                }
            }
        }

        if (retrieveDate != null) {
            if (!retrieveDate.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE rt.req_date LIKE \'" + retrieveDate + "%' ";
                } else if (count > 1) {
                    query = query + " AND rt.req_date LIKE \'" + retrieveDate + "%' ";
                }
            }
        }

//        if (gtsNo != null) {
//            if (!gtsNo.equals("")) {
//                count++;
//                if (count == 1) {
//                    query = query + " WHERE s.gts_no = '" + gtsNo + "\' ";
//                } else if (count > 1) {
//                    query = query + " AND s.gts_no = '" + gtsNo + "\' ";
//                }
//            }
//        }
        if (status != null) {
            if (!status.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE rq.`status` = '" + status + "\' ";
                } else if (count > 1) {
                    query = query + " AND rq.`status` = '" + status + "\' ";
                }
            }
        }

        String finalQuery = "";

        if (count != 0) {
            finalQuery = "SELECT ft.rms_id, ft.rms_event, ft.id AS ftpId, ft.lot_type, ft.actual_qty, ft.p_status, ft.pkg_family, ft.pkg_name, ft.rmslot_event, "
                    + "DATE_FORMAT(ft.mth_to_scrap,'%M %Y') AS mthToScrapView, ft.completed_date, "
                    + "rq.id AS reqId, rq.flag, rq.`status`, iv.id AS invId, iv.inventory_shelf, "
                    + "DATE_FORMAT(iv.inventory_date,'%d %M %Y') AS inventoryDate , rt.id AS retId, "
                    + "DATE_FORMAT(rt.req_date,'%d %M %Y') AS retrieveDate, rt.requestor_name, "
                    + "sr.id AS scId, sr.scrap_by, DATE_FORMAT(sr.scrap_date,'%d %M %Y') AS scrapDate "
                    + "FROM sr_request rq "
                    + "INNER JOIN sr_ftp_data ft ON rq.ftp_id = ft.id "
                    + "LEFT JOIN sr_inventory iv ON rq.inv_id = iv.id "
                    + "LEFT JOIN sr_retrieve rt ON rt.req_id = rq.id "
                    + "LEFT JOIN sr_scrap sr ON sr.request_id = rq.id "
                    + query + " GROUP BY reqId";

        } else {
            finalQuery = "SELECT * FROM sr_request WHERE flag = '1000'";
        }

        System.out.println("finalQuery: " + finalQuery);

        srD = new SampleRequestDAO();
        List<SampleRequest> SrQuery = srD.getAllQuery(finalQuery);
        model.addAttribute("SrQuery", SrQuery);

        return "srQuery/srQuery";
    }

    @RequestMapping(value = "/detail/{reqId}", method = RequestMethod.GET)
    public String whShipping(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("reqId") String reqId) {

        LogDAO logD = new LogDAO();
        List<Log> log = logD.getLogList(reqId);
        model.addAttribute("log", log);

        return "srQuery/srQueryDetail";
    }

}
