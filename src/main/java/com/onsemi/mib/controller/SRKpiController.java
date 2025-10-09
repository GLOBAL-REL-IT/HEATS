package com.onsemi.mib.controller;

import com.onsemi.mib.dao.SRKpiDAO;
import com.onsemi.mib.model.SRKpi;
import com.onsemi.mib.model.SRRetrieve;
import java.util.List;
import com.onsemi.mib.model.UserSession;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "/sr/srKpi")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class SRKpiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTPDataController.class);
    String[] args = {};
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    
    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String kpiList(
            Model model,
            @ModelAttribute UserSession userSession
    ) {        
        model.addAttribute("userSession", userSession);
        
        //scrap
        SRKpiDAO srKpiDAO = new SRKpiDAO();
        List<SRKpi> scrapChartList = srKpiDAO.getAllScrapDataPerMth();
        model.addAttribute("scrapChartList", scrapChartList); /*updated*/
        
        srKpiDAO = new SRKpiDAO(); 
        List<SRRetrieve> scrapDataList = srKpiDAO.getAllScrapData();
        model.addAttribute("scrapDataList", scrapDataList);
        
        srKpiDAO = new SRKpiDAO();
        int mthToScrapVsReqDateGoal = srKpiDAO.getGoal("Scrap Mth to Scrap Date vs Request Date");
        model.addAttribute("mthToScrapVsReqDateGoal", mthToScrapVsReqDateGoal); 
        
        srKpiDAO = new SRKpiDAO();
        int reqDateVsShipDateGoal = srKpiDAO.getGoal("Scrap Req Date VS Shipping Date");
        model.addAttribute("reqDateVsShipDateGoal", reqDateVsShipDateGoal);
        
        srKpiDAO = new SRKpiDAO();
        List<SRKpi> mthToScrapVsReqDateList = srKpiDAO.getMthToScrapVsReqDateData(Integer.toString(mthToScrapVsReqDateGoal),Integer.toString(reqDateVsShipDateGoal));
        model.addAttribute("mthToScrapVsReqDateList", mthToScrapVsReqDateList); /*updated*/
        
        //retrieval
        srKpiDAO = new SRKpiDAO();
        List<SRKpi> retrievalChartList = srKpiDAO.getAllRetrieveDataPerMth();
        model.addAttribute("retrievalChartList", retrievalChartList); /*updated*/
        
        srKpiDAO = new SRKpiDAO();
        List<SRKpi> retrievalDataList = srKpiDAO.getAllRetrieveData();
        model.addAttribute("retrievalDataList", retrievalDataList);
        
        srKpiDAO = new SRKpiDAO();
        int actReqDateVsShipDateGoal = srKpiDAO.getGoal("Activities Req Date VS Shipping Date");
        model.addAttribute("actReqDateVsShipDateGoal", actReqDateVsShipDateGoal);
        
        srKpiDAO = new SRKpiDAO();
        List<SRKpi> actReqDateVSshipDateList = srKpiDAO.getActivityReqDateVSShipDateData(Integer.toString(actReqDateVsShipDateGoal));
        model.addAttribute("actReqDateVSshipDateList", actReqDateVSshipDateList); /*updated*/
        
        
        String scrap = "active";
        String scrapTab = "in active";
        model.addAttribute("scrap", scrap);
        model.addAttribute("scrapTab", scrapTab);
        String retrieve = "";
        String retrieveTab = "";
        model.addAttribute("retrieve", retrieve);
        model.addAttribute("retrieveTab", retrieveTab);
        String inventoryAudit = "";
//        String inventoryAuditTab = "";
//        model.addAttribute("inventoryAudit", inventoryAudit);
//        model.addAttribute("inventoryAuditTab", inventoryAuditTab);

        
        return "srKpi/srKpi";
    }
    
}

