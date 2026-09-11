/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.controller;

import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingMaverickDAO;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.model.UserSession;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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

}