package com.onsemi.mib.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.onsemi.mib.dao.ItemActivityConfigDAO;
import com.onsemi.mib.dao.ItemAluConfigDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemHardwareConfigDAO;
import com.onsemi.mib.dao.LDAPUserDAO;
import com.onsemi.mib.dao.ManualTestDAO;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.MenuDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import com.onsemi.mib.dao.SREventListDAO;
import com.onsemi.mib.dao.UserAccessControlDAO;
import com.onsemi.mib.dao.UserDAO;
import com.onsemi.mib.dao.UserGroupAccessDAO;
import com.onsemi.mib.dao.UserGroupDAO;
import com.onsemi.mib.dao.UserManualDAO;
import com.onsemi.mib.model.EventGroup;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemActivityConfig;
import com.onsemi.mib.model.ItemAluConfig;
import com.onsemi.mib.model.ItemHardwareConfig;
import com.onsemi.mib.model.JSONResponse;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.model.ManualTest;
import com.onsemi.mib.model.Menu;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.model.User;
import com.onsemi.mib.model.UserAccessControl;
import com.onsemi.mib.model.UserGroup;
import com.onsemi.mib.model.UserGroupAccess;
import com.onsemi.mib.model.UserManual;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.SPTSResponse;
import com.onsemi.mib.tools.SPTSWebService;
import com.onsemi.mib.tools.SpmlUtil;
import com.onsemi.mib.tools.SystemUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
@RequestMapping(value = "/admin")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    String[] args = {};

    private static final int BUFFER_SIZE = 4096;
    private static final String UPLOADED_FOLDER_MB = "\\\\phcad-relost01\\d$\\OSTORMS\\OSTORMS MANUAL OPERATION v26 May 1.pdf";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Model model) {
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(
            Model model,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String selectedGroup
    ) {
        selectedGroup = SpmlUtil.nullToEmptyString(selectedGroup);
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        List<LDAPUser> ldapUserList = ldapUserDAO.listByGroupId(selectedGroup);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(selectedGroup);

        String loginId = userSession.getLoginId();
        UserDAO userDao = new UserDAO();
        LDAPUser ldapUser = userDao.getUserAccess(loginId);
//        String testEmailAccess = ldapUser.getFeaturesTestEmail();

//        model.addAttribute("testEmailAccess", testEmailAccess);
        model.addAttribute("userList", ldapUserList);
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("selectedGroup", selectedGroup);
        return "admin/ldap_user";
    }

    @RequestMapping(value = "/user/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String userAdd(
            Model model,
            @RequestParam(required = false) String loginId) {

        List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();

        if (loginId != null) {
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
                String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid"};
                controls.setReturningAttributes(attrIDs);
                //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
                //Onsemi
//                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
                results = ctx.search("ou=ONSemi", "(cn=" + loginId + ")", controls);

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
        }

        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("userList", ldapUserList);
        return "admin/ldap_user_add";
    }

    @RequestMapping(value = "/ldap_user/edit/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String ldapUserEdit(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("userId") String userId
    ) {
        String sessionId = userSession.getId();
        model.addAttribute("sessionId", sessionId);

        LDAPUserDAO userDAO = new LDAPUserDAO();
        LDAPUser user = userDAO.get(userId);
        model.addAttribute("user", user);

        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(user.getGroupId());
        model.addAttribute("userGroupList", userGroupList);

        //getAuthorityAccessStatus: SR Retrieve from SY
        String srEmailRetrieveActive = "";
        String srEmailRetrieveInactive = "";
        if (user.getSrEmailRetrieve().equals("Active")) {
            srEmailRetrieveActive = "checked=\"\"";
        } else {
            srEmailRetrieveInactive = "checked=\"\"";
        }
        model.addAttribute("srEmailRetrieveActive", srEmailRetrieveActive);
        model.addAttribute("srEmailRetrieveInactive", srEmailRetrieveInactive);

        //getAuthorityAccessStatus: SR Retrieve Plan from SY
        String srEmailScrapActive = "";
        String srEmailScrapInactive = "";
        if (user.getScrap().equals("Active")) {
            srEmailScrapActive = "checked=\"\"";
        } else {
            srEmailScrapInactive = "checked=\"\"";
        }
        model.addAttribute("srEmailScrapActive", srEmailScrapActive);
        model.addAttribute("srEmailScrapInactive", srEmailScrapInactive);

        return "admin/ldap_user_edit";
    }

    @RequestMapping(value = "/ldap_user/authAccess", method = {RequestMethod.GET, RequestMethod.POST})
    public String ldapUserAuthAccess(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userLdapId,
            @RequestParam(required = false) String radioSrRetrieveEmail,
            @RequestParam(required = false) String radioSrScrapEmail) {

        LDAPUser user = new LDAPUser();
        user.setId(userLdapId);
        user.setScrap(radioSrScrapEmail);
        user.setSrEmailRetrieve(radioSrRetrieveEmail);
        user.setModifiedBy(userSession.getId());

        LDAPUserDAO userDAO = new LDAPUserDAO();
        QueryResult queryResult = userDAO.updateAuth2(user);

        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/ldap_user/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String ldapUserUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String isActive
    ) {
        User user = new User();
        user.setId(userId);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setIsActive(isActive);
        user.setModifiedBy(userSession.getId());
        user.setGroupId(groupId);

        UserDAO userDAO = new UserDAO();
        QueryResult queryResult = userDAO.updateUser(user);
        if (queryResult.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
//        return "redirect:/admin/user/edit/" + userId;
        return "redirect:/admin/user/";
    }

    @RequestMapping(value = "/user/loginid/{loginId}", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse userLoginId(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        JSONResponse response = new JSONResponse();
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        LDAPUser ldapUser = ldapUserDAO.getByLoginId(loginId);
        if (ldapUser.getFirstname() == null) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage("User not registered!");
            response.setResult(ldapUser);
        } else {
            response.setStatus(Boolean.TRUE);
            response.setStatusMessage("User already registered!");
            response.setResult(ldapUser);
        }
        return response;
    }

    @RequestMapping(value = "/user/ldap/save", method = RequestMethod.POST)
    public @ResponseBody
    JSONResponse userLDAPSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String oncid,
            @RequestParam(required = false) String groupId) {

        JSONResponse response = new JSONResponse();
        LDAPUser ldapUser = new LDAPUser();
        ldapUser.setLoginId(loginId);
        ldapUser.setOncid(oncid);
        ldapUser.setFirstname(firstname);
        ldapUser.setLastname(lastname);
        ldapUser.setEmail(email);
        ldapUser.setTitle(title);
        ldapUser.setGroupId(groupId);
        ldapUser.setCreatedBy(userSession.getId());
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        QueryResult queryResult = ldapUserDAO.insert(ldapUser);
        if (queryResult.getResult() <= 0) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage(queryResult.getErrorMessage());
            response.setResult(ldapUser);
        } else {
            response.setStatus(Boolean.TRUE);
            response.setStatusMessage("User added!");
            response.setResult(ldapUser);
        }
        return response;
    }

    @RequestMapping(value = "/user/sync/{loginId}", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse userSync(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        //Start Retrieve LDAP Users
        Hashtable h = new Hashtable();
        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

        DirContext ctx = null;
        NamingEnumeration results = null;
        LDAPUser ldapUser = new LDAPUser();

        try {
            ctx = new InitialDirContext(h);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid"};
            controls.setReturningAttributes(attrIDs);
            //Local
//            results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
            //Onsemi
//            results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
            results = ctx.search("ou=ONSemi", "(cn=" + loginId + ")", controls);

            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();

                Enumeration e = attributes.getIDs();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    if (key.equalsIgnoreCase("givenName")) {
                        ldapUser.setFirstname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("sn")) {
                        ldapUser.setLastname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("title")) {
                        ldapUser.setTitle(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("cn")) {
                        ldapUser.setLoginId(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("mail")) {
                        ldapUser.setEmail(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("oncid")) {
                        ldapUser.setOncid(attributes.get(key).get().toString());
                    }
                }
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

        JSONResponse response = new JSONResponse();
        if (ldapUser.getFirstname() == null) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage("Unable to retrieve user info from LDAP for " + loginId + "!");
            response.setResult(ldapUser);
        } else {
            LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
            LDAPUser dbLdapUser = ldapUserDAO.getByLoginId(loginId);
            if (dbLdapUser.getFirstname() == null) {
                response.setStatus(Boolean.FALSE);
                response.setStatusMessage("Unable to retrieve user info from Database for " + loginId + "!");
                response.setResult(ldapUser);
            } else {
                LDAPUser updateUser = new LDAPUser();
                updateUser.setId(dbLdapUser.getId());
                updateUser.setOncid(ldapUser.getOncid());
                updateUser.setFirstname(ldapUser.getFirstname());
                updateUser.setLastname(ldapUser.getLastname());
                updateUser.setEmail(ldapUser.getEmail());
                updateUser.setTitle(ldapUser.getTitle());
                updateUser.setGroupId(dbLdapUser.getGroupId());
                updateUser.setModifiedBy(userSession.getId());
                QueryResult queryResult = ldapUserDAO.update(updateUser);
                if (queryResult.getResult() <= 0) {
                    response.setStatus(Boolean.FALSE);
                    response.setStatusMessage(queryResult.getErrorMessage());
                    response.setResult(ldapUser);
                } else {
                    response.setStatus(Boolean.TRUE);
                    response.setStatusMessage("User updated!");
                    response.setResult(updateUser);
                }
            }
        }
        return response;
    }

    @RequestMapping(value = "/user/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByLoginId(loginId);
        if (user == null) {
            user = new User();
            user.setLoginId(loginId);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setGroupId(groupId);
            user.setCreatedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.insertUser(user);
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("admin.label.user.save.error", args, locale));
                model.addAttribute("loginId", loginId);
                model.addAttribute("fullname", fullname);
                model.addAttribute("email", email);
                UserGroupDAO userGroupDAO = new UserGroupDAO();
                List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
                model.addAttribute("userGroupList", userGroupList);
                return "admin/user_add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.save.success", args, locale));
                return "redirect:/admin/user/edit/" + queryResult.getGeneratedKey();
            }
        } else {
            args = new String[1];
            args[0] = loginId;
            model.addAttribute("error", messageSource.getMessage("general.label.exist.success", args, locale));
            model.addAttribute("loginId", loginId);
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
            model.addAttribute("userGroupList", userGroupList);
            return "admin/user_add";
        }
    }

    @RequestMapping(value = "/user/edit/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userEdit(
            Model model,
            @PathVariable("userId") String userId
    ) {
        UserDAO userDAO = new UserDAO();
//        User user = userDAO.getUser(userId);
//        LDAPUser user = userDAO.getUserDetailById(userId);
        LDAPUser user = userDAO.getUserAccess(userId);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(user.getGroupId());
        model.addAttribute("user", user);
        model.addAttribute("userGroupList", userGroupList);
//        return "admin/user_edit";
        return "admin/ldap_user_edit";
    }

    @RequestMapping(value = "/user/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String userUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String isActive
    ) {
        User user = new User();
        user.setId(userId);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setIsActive(isActive);
        user.setModifiedBy(userSession.getId());
        user.setGroupId(groupId);

        UserDAO userDAO = new UserDAO();
        QueryResult queryResult = userDAO.updateUser(user);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/updateAccess", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateAccess(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String itemAdd,
            @RequestParam(required = false) String itemEdit,
            @RequestParam(required = false) String itemDelete,
            @RequestParam(required = false) String itemHwAdd,
            @RequestParam(required = false) String itemHwEdit,
            @RequestParam(required = false) String itemHwDelete,
            @RequestParam(required = false) String itemActConfig,
            @RequestParam(required = false) String itemActAdd,
            @RequestParam(required = false) String itemActEdit,
            @RequestParam(required = false) String itemMovementAdd,
            @RequestParam(required = false) String itemSfRecall,
            @RequestParam(required = false) String eqptAdd,
            @RequestParam(required = false) String eqptEdit,
            @RequestParam(required = false) String eqptDelete,
            @RequestParam(required = false) String eqptFamilyAdd,
            @RequestParam(required = false) String eqptFamilyDelete,
            @RequestParam(required = false) String eqptRelTestGroupAdd,
            @RequestParam(required = false) String eqptRelTestGroupDelete,
            @RequestParam(required = false) String eqptTechAdd,
            @RequestParam(required = false) String eqptTechDelete,
            @RequestParam(required = false) String eqptMonAdd,
            @RequestParam(required = false) String eqptMonDelete,
            @RequestParam(required = false) String eqptViMonAdd,
            @RequestParam(required = false) String eqptViMonDelete,
            @RequestParam(required = false) String eqptFamilyAddGlobal,
            @RequestParam(required = false) String eqptRelTestGroupAddGlobal
    ) {
        UserAccessControl uac = new UserAccessControl();
        uac.setUserId(userId);
        uac.setItemAdd(itemAdd);
        uac.setItemEdit(itemEdit);
        uac.setItemDelete(itemDelete);
        uac.setItemHardwareAdd(itemHwAdd);
        uac.setItemHardwareEdit(itemHwEdit);
        uac.setItemHardwareDelete(itemHwDelete);
        uac.setItemActivityConfig(itemActConfig);
        uac.setItemActivityAdd(itemActAdd);
        uac.setItemActivityEdit(itemActEdit);
        uac.setItemMovementAdd(itemMovementAdd);
        uac.setItemSfRecall(itemSfRecall);
        uac.setEqptAdd(eqptAdd);
        uac.setEqptEdit(eqptEdit);
        uac.setEqptDelete(eqptDelete);
        uac.setEqptFamilyAdd(eqptFamilyAdd);
        uac.setEqptFamilyDelete(eqptFamilyDelete);
        uac.setEqptRelTestGroupAdd(eqptRelTestGroupAdd);
        uac.setEqptRelTestGroupDelete(eqptRelTestGroupDelete);
        uac.setEqptTechAdd(eqptTechAdd);
        uac.setEqptTechDelete(eqptTechDelete);
        uac.setEqptMonAdd(eqptMonAdd);
        uac.setEqptMonDelete(eqptMonDelete);
        uac.setEqptViMonAdd(eqptViMonAdd);
        uac.setEqptViMonDelete(eqptViMonDelete);
        uac.setEqptFamilyAddGlobal(eqptFamilyAddGlobal);
        uac.setEqptRelTestGroupAddGlobal(eqptRelTestGroupAddGlobal);

        UserAccessControlDAO uacD = new UserAccessControlDAO();
        int count = uacD.getCountByUserId(userId);

        uacD = new UserAccessControlDAO();
        QueryResult q = new QueryResult();
        if (count == 0) { //insert
            q = uacD.insertUserAccessControl(uac);
        } else {
            q = uacD.updateUserAccessControlByUserId(uac);
        }

        if (q.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/password", method = {RequestMethod.GET, RequestMethod.POST})
    public String userPassword(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmPassword
    ) {
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getUser(userId);
        if (BCrypt.checkpw(currentPassword, currentUser.getPassword())) {
            User user = new User();
            user.setId(userId);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setModifiedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.updatePassword(user);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.password.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.password.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.current_password.error", args, locale));
        }

        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/delete/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("userId") String userId
    ) {
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        QueryResult queryResult = ldapUserDAO.delete(userId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.delete.error", args, locale));
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String group(
            Model model
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        return "admin/group";
    }

    @RequestMapping(value = "/group/menu/{groupId}", method = RequestMethod.GET)
    public String groupMenu(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupAccessDAO userGroupAccessDAO = new UserGroupAccessDAO();
        List<UserGroupAccess> userGroupAccessList = userGroupAccessDAO.getUserGroupAccess(groupId);
        model.addAttribute("userGroupAccessList", userGroupAccessList);
        model.addAttribute("groupId", groupId);
        return "admin/group_menu";
    }

    @RequestMapping(value = "/group/menu/save", method = {RequestMethod.POST})
    public String groupMenuSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String[] groupAccess
    ) {
        groupAccess = SpmlUtil.nullToEmptyString(groupAccess);
        UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
        //should use batch insert for performance
        QueryResult addQueryResult = new QueryResult();
        addQueryResult.setResult(0);
        for (String access : groupAccess) {
            UserGroupAccessDAO addUserGroupAccessDAO = new UserGroupAccessDAO();
            addQueryResult = addUserGroupAccessDAO.addAccess(groupId, access);
        }
        QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccess(groupId, groupAccess);
        int result = addQueryResult.getResult() + remQueryResult.getResult();
        if (result != 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.access.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.access.error", args, locale));
        }
        return "redirect:/admin/group/menu/" + groupId;
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String groupAdd(Model model) {
        return "admin/group_add";
    }

    @RequestMapping(value = "/group/edit/{groupId}", method = RequestMethod.GET)
    public String group_edit(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        UserGroup userGroup = userGroupDAO.getGroup(groupId);
        model.addAttribute("userGroup", userGroup);
        return "admin/group_edit";
    }

    @RequestMapping(value = "/group/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setCreatedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.insertGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + queryResult.getGeneratedKey();
    }

    @RequestMapping(value = "/group/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(groupId);
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setModifiedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.updateGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.update.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + groupId;
    }

    @RequestMapping(value = "/group/delete/{groupId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("groupId") String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        int userCount = userDAO.getCountByGroupId(groupId);
        if (userCount == 0) {
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            QueryResult queryResult = userGroupDAO.deleteGroup(groupId);
            UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
            QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccessByGroupId(groupId);
            int result = queryResult.getResult() + remQueryResult.getResult();
            if (result != 0) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.delete.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.error", args, locale));
            }
        } else {
            args = new String[]{Integer.toString(userCount)};
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.have_user.error", args, locale));
        }
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(
            Model model
    ) {
        MenuDAO menuDAO = new MenuDAO();
        List<Menu> parentMenuList = menuDAO.getMenuList("0");
        String tbody = "<tbody>";
        String menuOption = "";
        for (int i = 0; i < parentMenuList.size(); i++) {
            Menu parentMenu = parentMenuList.get(i);
            tbody += "<tr><td>&nbsp;</td><td><i class='fa " + parentMenu.getIcon() + "'></i>&nbsp;" + parentMenu.getName() + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
            menuOption += "<option value='" + parentMenu.getId() + "'>" + parentMenu.getName() + "</option>";
            List<Menu> childMenuList = menuDAO.getMenuList(parentMenu.getCode());
            if (!childMenuList.isEmpty()) {
                for (int j = 0; j < childMenuList.size(); j++) {
                    Menu childMenu = childMenuList.get(j);
                    tbody += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td><i class='fa fa-minus'></i>&nbsp;" + childMenu.getName() + "</td><td>&nbsp;</td></tr>";
                    menuOption += "<option value='" + childMenu.getId() + "'>&nbsp;&nbsp;&nbsp;&nbsp;" + childMenu.getName() + "</option>";
                }
            }
        }
        tbody += "</tbody>";
        model.addAttribute("tbody", tbody);
        model.addAttribute("menuOption", menuOption);
        return "admin/menu";
    }

    @RequestMapping(value = "/eventGroup", method = RequestMethod.GET)
    public String eventGroup(
            Model model
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        List<EventGroup> eventGroupList = eventGroupDAO.getGroupList();
        model.addAttribute("eventGroupList", eventGroupList);
        return "admin/eventGroup";
    }

    @RequestMapping(value = "/eventGroup/add", method = RequestMethod.GET)
    public String eventGroupAdd(Model model) {
        return "admin/event_group_add";
    }

    @RequestMapping(value = "/eventGroup/edit/{groupId}", method = RequestMethod.GET)
    public String eventGroupEdit(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        EventGroup eventGroup = eventGroupDAO.getGroup(groupId);
        model.addAttribute("eventGroup", eventGroup);
        return "admin/event_group_edit";
    }

    @RequestMapping(value = "/eventGroup/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String eventGroupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupDetails,
            @RequestParam(required = false) String groupStatus
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        int findGroupCode = eventGroupDAO.getCountByEventGroupCode(groupCode);

        QueryResult queryResult = null;
        if (findGroupCode == 0) {
            String flag = "";
            if (groupStatus.equals("Active")) {
                flag = "0";
            } else {
                flag = "1";
            }

            EventGroup eventGroup = new EventGroup();
            eventGroup.setEventGroupCode(groupCode);
            eventGroup.setEventGroupDetails(groupDetails);
            eventGroup.setEventGroupStatus(groupStatus);
            eventGroup.setEventGroupFlag(flag);
            eventGroup.setGroupModifiedBy(userSession.getFullname());
            eventGroup.setGroupCreatedBy(userSession.getFullname());
            eventGroupDAO = new SREventListDAO();
            queryResult = eventGroupDAO.insertEventGroup(eventGroup);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
        }
        return "redirect:/admin/eventGroup/edit/" + queryResult.getGeneratedKey();
    }

    @RequestMapping(value = "/eventGroup/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String eventGroupUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupDetails,
            @RequestParam(required = false) String groupStatus
    ) {
        String flag = "";
        if (groupStatus.equals("Active")) {
            flag = "0";
        } else {
            flag = "1";
        }

        EventGroup eventGroup = new EventGroup();
        eventGroup.setEventGroupCode(groupCode);
        eventGroup.setEventGroupDetails(groupDetails);
        eventGroup.setEventGroupStatus(groupStatus);
        eventGroup.setEventGroupFlag(flag);
        eventGroup.setGroupModifiedBy(userSession.getFullname());
        eventGroup.setGroupId(groupId);
        SREventListDAO eventGroupDAO = new SREventListDAO();
        QueryResult queryResult = eventGroupDAO.updateGroupEvent(eventGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.update.error", args, locale));
        }
        return "redirect:/admin/eventGroup";
    }

    @RequestMapping(value = "/eventGroup/delete/{groupId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String eventGroupDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("groupId") String groupId
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        int userCount = eventGroupDAO.getCountByEventGroupId(groupId);
        if (userCount == 1) {
            eventGroupDAO = new SREventListDAO();
            QueryResult queryResult = eventGroupDAO.deleteGroupEvent(groupId);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.delete.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Failed to delete the selected group code.", args, locale));
        }
        return "redirect:/admin/eventGroup";
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public String event(
            Model model
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        List<EventGroup> eventList = eventGroupDAO.getEventList();
        model.addAttribute("eventList", eventList);
        return "admin/event";
    }

    @RequestMapping(value = "/event/add", method = RequestMethod.GET)
    public String eventAdd(Model model) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        List<EventGroup> eventGroupList = eventGroupDAO.getGroupList();

        model.addAttribute("eventGroupList", eventGroupList);

        return "admin/event_add";
    }

    @RequestMapping(value = "/event/edit/{eventId}", method = RequestMethod.GET)
    public String eventEdit(
            Model model,
            @PathVariable("eventId") String eventId
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        EventGroup event = eventGroupDAO.getEventDetails(eventId);

        eventGroupDAO = new SREventListDAO();
        List<EventGroup> eventGroupList = eventGroupDAO.getGroupList();

        model.addAttribute("eventGroupList", eventGroupList);
        model.addAttribute("event", event);

        return "admin/event_edit";
    }

    @RequestMapping(value = "/event/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String eventSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String eventName,
            @RequestParam(required = false) String requirementStatus,
            @RequestParam(required = false) String eventGroupId
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        int findEventCode = eventGroupDAO.getCountByEventCode(eventCode);
        QueryResult queryResult = null;
        if (findEventCode == 0) {
            EventGroup event = new EventGroup();
            event.setEventGroupId(eventGroupId);
            event.setEventCode(eventCode);
            event.setEventName(eventName);
            event.setRequirementStatus(requirementStatus);
            event.setEventModifiedBy(userSession.getFullname());
            event.setEventCreatedBy(userSession.getFullname());
            eventGroupDAO = new SREventListDAO();
            queryResult = eventGroupDAO.insertEvent(event);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
        }
        return "redirect:/admin/event/";
    }

    @RequestMapping(value = "/event/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String eventUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String eventId,
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String eventName,
            @RequestParam(required = false) String requirementStatus,
            @RequestParam(required = false) String eventGroupId
    ) {
        EventGroup event = new EventGroup();
        event.setEventGroupId(eventGroupId);
        event.setEventCode(eventCode);
        event.setEventName(eventName);
        event.setRequirementStatus(requirementStatus);
        event.setEventModifiedBy(userSession.getFullname());
        event.setEventCreatedBy(userSession.getFullname());
        event.setEventId(eventId);
        SREventListDAO eventGroupDAO = new SREventListDAO();
        QueryResult queryResult = eventGroupDAO.updateEvent(event);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage(eventCode + " event has been updated successfully.", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Unable to update " + eventCode + " event. Please try again.", args, locale));
        }
        return "redirect:/admin/event";
    }

    @RequestMapping(value = "/event/delete/{eventId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String eventDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("eventId") String eventId
    ) {
        SREventListDAO eventGroupDAO = new SREventListDAO();
        QueryResult queryResult = eventGroupDAO.deleteEvent(eventId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("Selected event has been deleted successfully.", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Failed to delete the selected event. Please try again.", args, locale));
        }
        return "redirect:/admin/event";
    }

    @RequestMapping(value = "/userManual/donwload/", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletRequest request,
            //            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        // construct the complete absolute path of the file
        UserManualDAO uD = new UserManualDAO();
        UserManual u = uD.getUserManual();
        String fullPath = u.getPath() + u.getFilename();
//        String fullPath = UPLOADED_FOLDER_MB;
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

    @RequestMapping(value = "/bibActivity", method = {RequestMethod.GET, RequestMethod.POST})
    public String bibActivity(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        model.addAttribute("userItemAdd", userSession.getItemAdd());

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        List<ItemActivityConfig> item = itemD.getItemActivityConfigListWithItemDetailForBib(); //default display for motherboard only
        model.addAttribute("item", item);

        return "admin/bib_config";
    }

    @RequestMapping(value = "/bibActivity/bib", method = {RequestMethod.GET, RequestMethod.POST})
    public String bibActivityBib(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        model.addAttribute("userItemAdd", userSession.getItemAdd());

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        List<ItemActivityConfig> item = itemD.getItemActivityConfigListWithItemDetailForBib();
        model.addAttribute("item", item);

        return "admin/bib_config";
    }

    @RequestMapping(value = "/bibActivity/bibCard", method = {RequestMethod.GET, RequestMethod.POST})
    public String bibActivityBibCard(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        model.addAttribute("userItemAdd", userSession.getItemAdd());

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        List<ItemActivityConfig> item = itemD.getItemActivityConfigListWithItemDetailForBibCard();
        model.addAttribute("item", item);

        return "admin/bib_config";
    }

    @RequestMapping(value = "/bibActivity/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String bibActivityAdd(
            Model model,
            @ModelAttribute UserSession userSession) {

        String dut = "";

        List<ManualTest> itemB1 = new ArrayList<>();

        model.addAttribute("dut", dut);
        model.addAttribute("listData", itemB1);
        return "admin/bib_config_add";
    }

    @RequestMapping(value = "/bibActivity/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String bibActivityEdit(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id) {

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        ItemActivityConfig item = itemD.getItemActivityConfigWithItemDetail(id);
        model.addAttribute("item", item);

        // CHECK IF DATA EXIST - DO NOTHING IF NO DATA FOUND
        ManualTestDAO itemA = new ManualTestDAO();
        ManualTest itemA1 = itemA.getComponentConfigBefore(id);
        if (itemA1 == null) {
            // DO NOTHING HERE?
        } else {
            String dut = itemA1.getDut();

            ItemActivityConfigDAO itemactdao = new ItemActivityConfigDAO();
            String mibItemId = itemactdao.getItemIdByConfigId(id);
            ManualTestDAO itemB = new ManualTestDAO();
            List<ManualTest> itemB1 = itemB.getAllComponentConfigBefore(mibItemId);

            model.addAttribute("dut", dut);
            model.addAttribute("listData", itemB1);
        }
        return "admin/bib_config_edit";
    }

    @RequestMapping(value = "/bibActivity/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String addActivitySave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String viCheck,
            @RequestParam(required = false) String bibTestCheck,
            @RequestParam(required = false) String manualTestCheck,
            @RequestParam(required = false) String leakageTestCheck,
            @RequestParam(required = false) String psLeakageTestCheck,
            @RequestParam(required = false) String winchesterChamberLeakageTest,
            @RequestParam(required = false) String inputDUT,
            @RequestParam(required = false, value = "component_name[]") List<String> compName,
            @RequestParam(required = false, value = "component_type[]") List<String> type,
            @RequestParam(required = false, value = "actual_value[]") List<String> compValue,
            @RequestParam(required = false, value = "percentage[]") List<String> percentageValue,
            @RequestParam(required = false, value = "lower[]") List<String> lowerValue,
            @RequestParam(required = false, value = "upper[]") List<String> upperValue) {

        int saiz = 0;
        int inputQuantity = 1;

        ItemActivityConfig itemA = new ItemActivityConfig();
        itemA.setId(id);
        if ("on".equals(viCheck)) {
            itemA.setVi("Yes");
        } else {
            itemA.setVi("No");
        }
        if ("on".equals(bibTestCheck)) {
            itemA.setBibTest("Yes");
        } else {
            itemA.setBibTest("No");
        }
        if ("on".equals(manualTestCheck)) {
            itemA.setManualTest("Yes");
            saiz = compName.size();
            String flag = "1";
            String status = "";
            int saizDut = Integer.parseInt(inputDUT);
            String user = userSession.getLoginId();

            ItemActivityConfigDAO itemactdao = new ItemActivityConfigDAO();
            String itemId = itemactdao.getItemIdByConfigId(id);

            ManualTestDAO test = new ManualTestDAO();
            Integer check1 = test.getManualTestCurrentRecord(itemId);

            if ("0".equals(check1)) {
                test = new ManualTestDAO();
                QueryResult q0 = test.insertManualTestBeforeLoading(itemId, id, String.valueOf(inputQuantity), inputDUT, String.valueOf(saiz), user, flag);

                if (!"0".equals(q0.getGeneratedKey())) {
                    String configId = q0.getGeneratedKey();
                    for (int c1 = 0; c1 < saiz; c1++) {
                        test = new ManualTestDAO();
                        QueryResult q3 = test.insertManualTestBeforeLoadingSub(itemId, configId, inputDUT, type.get(c1), compName.get(c1), compValue.get(c1), percentageValue.get(c1), lowerValue.get(c1), upperValue.get(c1), user, flag);
                    }
                }
            } else {
                test = new ManualTestDAO();
                Integer configId = test.getConfigIdByItemId(itemId);
                test = new ManualTestDAO();
                QueryResult q0 = test.updateItemActivityConfig(String.valueOf(inputQuantity), inputDUT, String.valueOf(saiz), String.valueOf(configId));

                // FUNCTION TO REMOVE PREVIOUS COMPONENT, AND THEN SAVE THE NEW ONE
                test = new ManualTestDAO();
                test.removeCurrentDataBefore(String.valueOf(configId), itemId);

                for (int c1 = 0; c1 < saiz; c1++) {
                    test = new ManualTestDAO();
                    QueryResult q3 = test.insertManualTestBeforeLoadingSub(itemId, String.valueOf(configId), inputDUT, type.get(c1), compName.get(c1), compValue.get(c1), percentageValue.get(c1), lowerValue.get(c1), upperValue.get(c1), user, flag);
                }
            }
        } else {
            itemA.setManualTest("No");
        }
        if ("on".equals(leakageTestCheck)) {
            itemA.setLeakageTest("Yes");
        } else {
            itemA.setLeakageTest("No");
        }
        if ("on".equals(psLeakageTestCheck)) {
            itemA.setPsLeakageTest("Yes");
        } else {
            itemA.setPsLeakageTest("No");
        }
        if ("on".equals(winchesterChamberLeakageTest)) {
            itemA.setWinchesterChamberLeakageTest("Yes");
        } else {
            itemA.setWinchesterChamberLeakageTest("No");
        }
        itemA.setFlag("0");
        itemA.setStatus("New Config");

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        QueryResult itemQ = itemD.updateItemActivityConfig(itemA);
        if (!"0".equals(itemQ.getResult())) {
//            redirectAttrs.addFlashAttribute("success", "Activity Configuration Succesfully Updated.");
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("item.label.configuration.bib.success", args, locale));
            return "redirect:/admin/bibActivity";
//            return "redirect:/hw//item/pending";
        } else {
//            redirectAttrs.addFlashAttribute("error", "Failed to update Activity Configuration. Pls Contact System Admin");
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("item.label.configuration.bib.error", args, locale));
            return "redirect:/admin/bibActivity/edit/" + id;
        }
    }

    @RequestMapping(value = "/aluConfig", method = {RequestMethod.GET, RequestMethod.POST})
    public String aluConfig(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws IOException {

        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();

        for (int i = 0; i < getItemTypeAll.length(); i++) {

            ParameterDetailsDAO pD = new ParameterDetailsDAO();
            String masterCode = "002";
            String detailcode = pD.getNextDetailCode(masterCode);
            pD = new ParameterDetailsDAO();
            int count = pD.getCountMasterCodeAndName(masterCode, getItemTypeAll.getJSONObject(i).getString("ItemType"));

            if (count == 0) {
                ParameterDetails param = new ParameterDetails();
                param.setMasterCode(masterCode);
                param.setDetailCode(detailcode);
                param.setName(getItemTypeAll.getJSONObject(i).getString("ItemType"));
                param.setCreatedBy(userSession.getId());
                pD = new ParameterDetailsDAO();
                QueryResult q = pD.insertParameterDetails(param);
            }
        }

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemType = pD.getGroupParameterDetailList("", "002");
        model.addAttribute("paramItemType", paramItemType);

        ItemAluConfigDAO itemD = new ItemAluConfigDAO();
        List<ItemAluConfig> Item = itemD.getItemAluConfigList();
        model.addAttribute("Item", Item);

        return "admin/aluConfig";
    }

    @RequestMapping(value = "/aluConfig/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String aluConfigSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemType
    ) {
        ItemAluConfigDAO itemD = new ItemAluConfigDAO();
        int count = itemD.getCountItemType(itemType);

        if (count == 0) {

            ItemAluConfig item = new ItemAluConfig();
            item.setItemType(itemType);
            item.setCreatedBy(userSession.getFullname());

            itemD = new ItemAluConfigDAO();
            QueryResult q = itemD.insertItemAluConfig(item);
            if (q.getResult() > 0) {
                redirectAttrs.addFlashAttribute("success", "Successfully registered " + itemType + " into the ALU Config List");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to register " + itemType + " into the list. Pls Contact System Admin for more detail");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", itemType + " already registered. Pls select another Item Type");
        }

        return "redirect:/admin/aluConfig";
    }

    @RequestMapping(value = "/aluConfig/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String aluConfigDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id
    ) {

        ItemAluConfigDAO itemD = new ItemAluConfigDAO();
        QueryResult queryResult = itemD.deleteItemAluConfig(id);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("Selected Item Type has been deleted successfully.", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Failed to delete the selected Item Type. Please try again.", args, locale));
        }
        return "redirect:/admin/aluConfig";
    }

    @RequestMapping(value = "/hw", method = {RequestMethod.GET, RequestMethod.POST})
    public String hardware(
            Model model,
            @ModelAttribute UserSession userSession) throws IOException {

        ItemHardwareConfigDAO itemdao = new ItemHardwareConfigDAO();
        List<ItemHardwareConfig> itemList = itemdao.getItemHardwareConfigList();
        model.addAttribute("itemList", itemList);

        return "admin/hw_id_list";
    }

    @RequestMapping(value = "/hw/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addhardware(
            Model model,
            @RequestParam(required = false) String loginId) {

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemType = pD.getGroupParameterDetailList("", "002");
        model.addAttribute("itemTypeList", paramItemType);

        return "admin/hw_id_add";
    }

    @RequestMapping(value = "/hw/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String hwAddSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String assemblyno,
            @RequestParam(required = false) String revision,
            @RequestParam(required = false) String mfgdate,
            @RequestParam(required = false) String component,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String partnumber,
            @RequestParam(required = false) String alu,
            @RequestParam(required = false) String shelf) throws IOException {

        itemId = onToYesNo(itemId);
        supplier = onToYesNo(supplier);
        assemblyno = onToYesNo(assemblyno);
        revision = onToYesNo(revision);
        mfgdate = onToYesNo(mfgdate);
        component = onToYesNo(component);
        event = onToYesNo(event);
        partnumber = onToYesNo(partnumber);
        alu = onToYesNo(alu);
        shelf = onToYesNo(shelf);

        ItemHardwareConfigDAO itemdao = new ItemHardwareConfigDAO();
        ItemHardwareConfig item = itemdao.getConfigItem(itemType, subType);
        if (item != null) {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.hardware.exist", args, locale));
            return "redirect:/admin/hw/add";
        } else {
            item = new ItemHardwareConfig();
            item.setItemType(itemType);
            item.setSubType(subType);
            item.setSameItemId(itemId);
            item.setSupplier(supplier);
            item.setAssemblyNo(assemblyno);
            item.setRevision(revision);
            item.setMfgDate(mfgdate);
            item.setComponent(component);
            item.setEvent(event);
            item.setPartNumber(partnumber);
            item.setAlu(alu);
            item.setShelfTime(shelf);
            // PLEASE NOTE THAT THERE WILL BE ADDED COLUMN, PLEASE HARDCODE THEM HERE IF ANY
            item.setSptsPkid("0");
            item.setCreatedBy(userSession.getLoginId());
            item.setFlag("1");

            itemdao = new ItemHardwareConfigDAO();
            QueryResult queryResult = itemdao.insertItemHardwareConfig(item);

            if (!"0".equals(queryResult.getGeneratedKey())) {

                LocalDateTime instance = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                String formattedString = formatter.format(instance);

                // INSERT INTO SPTS function
                String status = insertHardwareConfigIntoSPTS(queryResult.getGeneratedKey(), itemType, subType, itemId, supplier, assemblyno, revision, mfgdate, component, event, partnumber, alu, shelf, formattedString, userSession.getLoginId(), "1");
                if (status.equals("SUCCESS")) {
                    redirectAttrs.addFlashAttribute("success", "SPTS data created: Item Hardware Configuration [" + itemType + "] ");
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.hardware.success", args, locale));
                    return "redirect:/admin/hw";
                } else {
                    model.addAttribute("error", status);
                    redirectAttrs.addFlashAttribute("error", status);
                    return "redirect:/admin/hw/add";
                }
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.hardware.error", args, locale));
                return "redirect:/admin/hw/add";
            }
        }
    }

    private static String onToYesNo(String s) {
        return (s != null && "on".equalsIgnoreCase(s.trim())) ? "Yes" : "No";
    }

    @RequestMapping(value = "/hw/ajaxSample/{itemType}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, String>> ajaxSample(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @PathVariable("itemType") String itemType) {

        ItemDAO item = new ItemDAO();
        List<Item> itemList = item.getItemSubType("", itemType);

        return itemList.stream().map(itm -> {
            Map<String, String> m = new HashMap<>();
            m.put("id", Strings.nullToEmpty(itm.getId()));
            m.put("text", Strings.nullToEmpty(itm.getSubType()));
            m.put("value", Strings.nullToEmpty(itm.getSubType()));
            return m;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/hw/edit/{configId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String hwConfigEdit(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("configId") String configId) {

        ItemHardwareConfigDAO itemdao = new ItemHardwareConfigDAO();
        ItemHardwareConfig item = itemdao.getItemHardwareConfig(configId);
        model.addAttribute("itemdata", item);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemType = pD.getGroupParameterDetailList(item.getItemType(), "002");
        model.addAttribute("itemTypeList", paramItemType);

        ItemDAO items = new ItemDAO();
        List<Item> itemList = items.getItemSubType02(item.getSubType(), item.getItemType());
        model.addAttribute("subTypeList", itemList);

        return "admin/hw_id_edit";
    }

    @RequestMapping(value = "/hw/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String hwAddUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String spts_pkid,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String assemblyno,
            @RequestParam(required = false) String revision,
            @RequestParam(required = false) String mfgdate,
            @RequestParam(required = false) String component,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String partnumber,
            @RequestParam(required = false) String alu,
            @RequestParam(required = false) String shelf) throws IOException {

        itemId = onToYesNo(itemId);
        supplier = onToYesNo(supplier);
        assemblyno = onToYesNo(assemblyno);
        revision = onToYesNo(revision);
        mfgdate = onToYesNo(mfgdate);
        component = onToYesNo(component);
        event = onToYesNo(event);
        partnumber = onToYesNo(partnumber);
        alu = onToYesNo(alu);
        shelf = onToYesNo(shelf);

        ItemHardwareConfig itemupdate = new ItemHardwareConfig();
        itemupdate.setId(id);
        itemupdate.setSptsPkid(spts_pkid);
        itemupdate.setItemType(itemType);
        itemupdate.setSubType(subType);
        itemupdate.setSameItemId(itemId);
        itemupdate.setSupplier(supplier);
        itemupdate.setAssemblyNo(assemblyno);
        itemupdate.setRevision(revision);
        itemupdate.setMfgDate(mfgdate);
        itemupdate.setComponent(component);
        itemupdate.setEvent(event);
        itemupdate.setPartNumber(partnumber);
        itemupdate.setAlu(alu);
        itemupdate.setShelfTime(shelf);
        itemupdate.setUpdatedBy(userSession.getLoginId());
        itemupdate.setFlag("1");

        ItemHardwareConfigDAO itemdao = new ItemHardwareConfigDAO();
        QueryResult qr = itemdao.updateItemHardwareConfig(itemupdate);

        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = formatter.format(instance);

        if (spts_pkid.equals("0")) {
            String status = insertHardwareConfigIntoSPTS(id, itemType, subType, itemId, supplier, assemblyno, revision, mfgdate, component, event, partnumber, alu, shelf, formattedString, userSession.getLoginId(), "1");
            if (status.equals("SUCCESS")) {
                redirectAttrs.addFlashAttribute("success", "SPTS data created: Item Hardware Configuration [" + itemType + "] ");
            } else {
                model.addAttribute("error", status);
                redirectAttrs.addFlashAttribute("error", status);
                return "redirect:/admin/hw/add";
            }
        } else {
            String sptsVersion = "";
            String sptsItemType = "";
            String sptsSubType = "";
            JSONObject params = new JSONObject();
            params.put("pkid", spts_pkid);
            JSONArray getItemByPKID = SPTSWebService.getHardwareIdConfigByPKID(params);
            int checkdata = getItemByPKID.length();
            for (int i = 0; i < getItemByPKID.length(); i++) {
                sptsVersion = getItemByPKID.getJSONObject(i).getString("Version");
                sptsItemType = getItemByPKID.getJSONObject(i).getString("ItemType");
            }

            if (checkdata == 0) {
//                // INSERT A NEW / EXISTING DATA INTO SPTS
                String status = insertHardwareConfigIntoSPTS(id, itemType, subType, itemId, supplier, assemblyno, revision, mfgdate, component, event, partnumber, alu, shelf, formattedString, userSession.getLoginId(), "1");
                if (status.equals("SUCCESS")) {
                    redirectAttrs.addFlashAttribute("success", "SPTS data created: Item Hardware Configuration [" + itemType + "] ");
                } else {
                    model.addAttribute("error", status);
                    redirectAttrs.addFlashAttribute("error", status);
                    return "redirect:/admin/hw/add";
                }
            } else {
                // UPDATE SPTS DATA
                String status = updateHardwareConfig(id, sptsVersion, spts_pkid, itemType, subType, itemId, supplier, assemblyno, revision, mfgdate, component, event, partnumber, alu, shelf, formattedString, userSession.getLoginId(), "1");
                if (status.equals("SUCCESS")) {
                    redirectAttrs.addFlashAttribute("success", "Hardware ID Config data successfully updated!");
                } else {
                    model.addAttribute("error", status);
                    redirectAttrs.addFlashAttribute("error", status);
                }
            }
        }

        if (qr.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.hardware.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.hardware.update.error", args, locale));
        }

        return "redirect:/admin/hw";
    }

    @RequestMapping(value = "/hw/delete/{hwid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String hwConfigDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("hwid") String hwid) throws IOException {

        ItemHardwareConfigDAO itemdao = new ItemHardwareConfigDAO();
        String sptsId = itemdao.getSptsId(hwid);
        sptsId = SystemUtil.nullToZero(sptsId);

        // MUST GET THE LATEST DATA FROM THE SPTS DATABASE FIRST
        JSONObject params = new JSONObject();
        params.put("pkid", sptsId);
        JSONArray getItemByPKID = SPTSWebService.getHardwareIdConfigByPKID(params);

        String sptsVersion = "";
        Integer pkid = 0;
        int checkdata = getItemByPKID.length();
        for (int i = 0; i < getItemByPKID.length(); i++) {
            sptsVersion = getItemByPKID.getJSONObject(i).getString("Version").toUpperCase();
            pkid = getItemByPKID.getJSONObject(i).getInt("PKID");
        }

        // THEN USE THAT DATA TO DO THE DELETE FUNCTION - OR ELSE CANNOT DELETE
        itemdao = new ItemHardwareConfigDAO();
        QueryResult queryResult = itemdao.deleteItemHardwareConfig(hwid);

        if (sptsId.equals("0")) {
            // DO NOTHING - SINCE THE DATA ALREADY NON-EXIST
        } else {
            JSONObject param = new JSONObject();
            param.put("pkid", pkid);
            param.put("version", sptsVersion);
            SPTSResponse deleteEqpt = SPTSWebService.deleteHardwareIdConfigByPKID(param);
        }

        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.hardware.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.hardware.delete.error", args, locale));
        }
        return "redirect:/admin/hw";
    }

    public String insertHardwareConfigIntoSPTS(String id, String itemType, String subType, String itemId, String supplier, String assemblyno, String revision, String mfgdate, String component, String event, String partnumber, String alu, String shelf, String datenow, String user, String flag) throws IOException {
        String status = "SUCCESS";

        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateNow = formatter.format(instance);

        JSONObject addHwIdConfig = new JSONObject();
        addHwIdConfig.put("itemType", itemType);
        addHwIdConfig.put("subType", subType);
        addHwIdConfig.put("sameItemID", itemId);
        addHwIdConfig.put("supplier", supplier);
        addHwIdConfig.put("assemblyNo", assemblyno);
        addHwIdConfig.put("revision", revision);
        addHwIdConfig.put("mfgDate", mfgdate);
        addHwIdConfig.put("component", component);
        addHwIdConfig.put("evt", event);
        addHwIdConfig.put("partNumber", partnumber);
        addHwIdConfig.put("alu", alu);
        addHwIdConfig.put("shelfTime", shelf);
        addHwIdConfig.put("createdDate", dateNow);
        addHwIdConfig.put("createdBy", user);
        addHwIdConfig.put("flag", "1");

        SPTSResponse sr = SPTSWebService.insertItemHardwareConfig(addHwIdConfig);

        if (sr.getStatus()) {
            status = "SUCCESS";
            ItemHardwareConfig item = new ItemHardwareConfig();
            item.setSptsPkid(sr.getResponseId().toString());
            item.setId(id);

            ItemHardwareConfigDAO itemdao = new ItemHardwareConfigDAO();
            QueryResult qr2 = itemdao.updateSPTSPKID_HardwareId(item);
        } else {
            status = "FAILED";
            LinkedHashMap<String, String> itemhmap;
            ObjectMapper mapper = new ObjectMapper();
            itemhmap = mapper.readValue(addHwIdConfig.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                status += " - " + errorMessage;
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                status += " - " + errorMessage;
            }
        }
        return status;
    }

    public String updateHardwareConfig(String id, String version, String sptsId, String itemType, String subType, String itemId, String supplier, String assemblyno, String revision, String mfgdate, String component, String event, String partnumber, String alu, String shelf, String datenow, String user, String flag) throws IOException {
        String status = "SUCCESS";
        JSONObject updateHardware = new JSONObject();
        updateHardware.put("pkid", sptsId);
        updateHardware.put("version", version);
        updateHardware.put("itemType", itemType);
        updateHardware.put("subType", subType);
        updateHardware.put("sameItemID", itemId);
        updateHardware.put("supplier", supplier);
        updateHardware.put("assemblyNo", assemblyno);
        updateHardware.put("revision", revision);
        updateHardware.put("mfgDate", mfgdate);
        updateHardware.put("component", component);
        updateHardware.put("evt", event);
        updateHardware.put("partNumber", partnumber);
        updateHardware.put("alu", alu);
        updateHardware.put("shelfTime", shelf);

        SPTSResponse sr = SPTSWebService.updateHardwareIdConfig(updateHardware);
        if (sr.getStatus()) {
            status = "SUCCESS";
        } else {
            status = "FAILED";
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(updateHardware.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                status += " - " + errorMessage;
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                status += " - " + errorMessage;
            }
            LOGGER.info("sr.getErrorCode(): " + sr.getErrorCode());
            LOGGER.info("sr.getErrorMessage(): " + sr.getErrorMessage());
            LOGGER.info("sr.getErrorDetail(): " + sr.getErrorDetail());
            LOGGER.info("errorMessage: " + errorMessage);
            LOGGER.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }

        return status;
    }

}
