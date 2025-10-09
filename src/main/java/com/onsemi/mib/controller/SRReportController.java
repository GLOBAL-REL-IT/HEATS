package com.onsemi.mib.controller;

import com.onsemi.mib.dao.DOListDAO;
import com.onsemi.mib.dao.FTPDao;
import com.onsemi.mib.dao.LogModuleDAO;
import com.onsemi.mib.dao.SampleRequestDAO;
import com.onsemi.mib.model.DOList;
import com.onsemi.mib.model.FTPdata;
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
@RequestMapping(value = "sr/srReport")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class SRReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRReportController.class);
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
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate
    ) {

        fromDate = fromDate + "-01";
        toDate = toDate + "-01";
//        System.out.println("fromDate: " + fromDate);
//        System.out.println("toDate: " + toDate);

        FTPDao ftpDAO1 = new FTPDao();
        List<FTPdata> ftpDataList = ftpDAO1.getAllFtpDataforMonhtlyReport(fromDate, toDate);
        model.addAttribute("ftpDataList", ftpDataList);

        return "srReport/srReport";
    }

}
