/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.mib.config;

import com.onsemi.mib.dao.EmailCcDAO;
import com.onsemi.mib.model.EmailCc;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.tools.QueryResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author fg79cj
 */
@Configuration
@EnableScheduling
public class EmailCcConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCcConfig.class);

    @Autowired
    ServletContext servletContext;

//    @Scheduled(fixedRate = 60000)
//    @Scheduled(cron = "0 0 1 * * *") //everyday at 1am --production
//    @Scheduled(cron = "0 */2 * * * *") //every 2 minutes //testing
    @Scheduled(cron = "0 50 15 * * *") //configurable //testing
    public void cronReadUserMYSE02() throws FileNotFoundException, IOException, ParseException {
        LOGGER.info("Start cronReadUserMYSE02");
        ReadLDAP("MYSE02");
    }

//    @Scheduled(cron = "0 10 1 * * *") //everyday at 1am --production
    //    @Scheduled(cron = "0 */2 * * * *") //every 2 minutes //testing
    @Scheduled(cron = "0 47 15 * * *") //configurable //testing
    public void cronReadUserMYSE01() throws FileNotFoundException, IOException, ParseException {
        LOGGER.info("Start cronReadUserMYSE01");
        ReadLDAP("MYSE01");
    }

//    @Scheduled(cron = "0 20 1 * * *") //everyday at 1am --production
    public void cronReadUserCNSU01() throws FileNotFoundException, IOException, ParseException {
        ReadLDAP("CNSU01");
    }

//    @Scheduled(cron = "0 30 1 * * *") //everyday at 1am --production
    public void cronReadUserPHCA01() throws FileNotFoundException, IOException, ParseException {
        ReadLDAP("PHCA01");
    }

//    @Scheduled(cron = "0 40 1 * * *") //everyday at 1am --production
    public void cronReadUserPHCE01() throws FileNotFoundException, IOException, ParseException {
        ReadLDAP("PHCE01");
    }

//    @Scheduled(cron = "0 50 1 * * *") //everyday at 1am --production
    public void cronReadUserVNBH01() throws FileNotFoundException, IOException, ParseException {
        ReadLDAP("VNBH01");
    }

    public void ReadLDAP(String Location) throws IOException, ParseException {

//       add email list into db
        List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();
        Integer totalAdd = 0;

        Hashtable h = new Hashtable();
        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        h.put(Context.PROVIDER_URL, "ldap://ldap.onsemi.com:389/o=ondex");

        DirContext ctx = null;
        NamingEnumeration results = null;

        try {
            ctx = new InitialDirContext(h);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"givenname", "sn", "cn", "mail", "onoraclelocation"};
            controls.setReturningAttributes(attrIDs);
            //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
            //Onsemi
            String loc = "onoraclelocation=" + Location;
            results = ctx.search("ou=ONSemi", "(&(mail=*)(onemployeestatus=A)(onoraclelocation=*)(" + loc + "))", controls);

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
                    if (key.equalsIgnoreCase("cn")) {
                        user.setLoginId(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("mail")) {
                        user.setEmail(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("onoraclelocation")) {
                        user.setLocation(attributes.get(key).get().toString());
                    }
                }
                ldapUserList.add(user);

            }
            for (int i = 0; i < ldapUserList.size(); i++) {

                if (ldapUserList.get(i).getEmail() != null) {
                    EmailCcDAO emD = new EmailCcDAO();
                    Integer countEmail = emD.getCountEmail(ldapUserList.get(i).getEmail());

                    if (countEmail == 0) {
                        EmailCc emailcc = new EmailCc();
                        emailcc.setCid(ldapUserList.get(i).getLoginId());
                        emailcc.setName(ldapUserList.get(i).getFirstname() + " " + ldapUserList.get(i).getLastname());
                        emailcc.setLocation(ldapUserList.get(i).getLocation());
                        emailcc.setEmail(ldapUserList.get(i).getEmail());
                        emailcc.setFlag("0");

                        emD = new EmailCcDAO();
                        QueryResult emaD = emD.insertEmailCc(emailcc);
                        totalAdd = totalAdd + 1;
                    }
                }

            }

            LOGGER.info("Total New Email " + Location + "  = " + totalAdd);

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

    }
}
