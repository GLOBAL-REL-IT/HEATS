/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.controller;

import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author zbqb9x
 */
@Controller
@RequestMapping(value = "/scanqr")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class ScanQRController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanQRController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @Autowired
    ServletContext servletContext;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String scanQr(Model model) {
        LOGGER.info("KITA MASUK KE PAGE YANG BETUL");
        return "/admin/scan";
    }
    
}