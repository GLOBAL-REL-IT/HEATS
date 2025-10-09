package com.onsemi.mib.controller;

import com.onsemi.mib.dao.LDAPUserDAO;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.MenuDAO;
import com.onsemi.mib.dao.SREventListDAO;
import com.onsemi.mib.dao.UserDAO;
import com.onsemi.mib.dao.UserGroupAccessDAO;
import com.onsemi.mib.dao.UserGroupDAO;
import com.onsemi.mib.dao.UserManualDAO;
import com.onsemi.mib.model.EventGroup;
import com.onsemi.mib.model.JSONResponse;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.model.Menu;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.model.User;
import com.onsemi.mib.model.UserGroup;
import com.onsemi.mib.model.UserGroupAccess;
import com.onsemi.mib.model.UserManual;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.SpmlUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
        String testEmailAccess = ldapUser.getFeaturesTestEmail();

        model.addAttribute("testEmailAccess", testEmailAccess);
        model.addAttribute("userList", ldapUserList);
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("selectedGroup", selectedGroup);
        return "admin/ldap_user";
    }

    @RequestMapping(value = "/user/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String userAdd(
            Model model,
            @RequestParam(required = false) String loginId
    ) {
        LOGGER.info("Login Id: " + loginId);
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
            @RequestParam(required = false) String radioSrScrapEmail
    ) {
//        LOGGER.info("radioFeaturesTestEmail >> " + radioFeaturesTestEmail);
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
            @RequestParam(required = false) String groupId
    ) {
//        LOGGER.info("sblum masuk sqlllllllllllllllll");
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
        User user = userDAO.getUser(userId);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(user.getGroupId());
        model.addAttribute("user", user);
        model.addAttribute("userGroupList", userGroupList);
        return "admin/user_edit";
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

//    @RequestMapping(value = "/srInventoryMgt", method = RequestMethod.GET)
//    public String srInventoryMgt(
//            Model model
//    ) {
//        SRInventoryMgtDAO srInvMgtDAO = new SRInventoryMgtDAO();
//        List<SRInventoryMgt> srInvMgtList = srInvMgtDAO.getInventoryList();
//
//        model.addAttribute("srInvMgtList", srInvMgtList);
//        return "admin/srInventoryMgt";
//    }
//
//    @RequestMapping(value = "/hwInventoryMgt", method = RequestMethod.GET)
//    public String hwInventoryMgt(
//            Model model
//    ) {
//        HWInventoryMgtDAO hwInvMgtDAO = new HWInventoryMgtDAO();
//        List<HWInventoryMgt> hwInvMgtList = hwInvMgtDAO.getInventoryList();
//
//        model.addAttribute("hwInvMgtList", hwInvMgtList);
//        return "admin/hwInventoryMgt";
//    }
//
//    @RequestMapping(value = "/testEmail", method = {RequestMethod.GET, RequestMethod.POST})
//    public String email(
//            Model model,
//            HttpServletRequest request,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession
//    ) throws IOException {
//        EmailDAO emailDao = new EmailDAO();
//        List<UserEmail> doLists = emailDao.getEmailNotifyRelatedPerson();
//        String[] to = new String[doLists.size()];
//        for (int i = 0; i < doLists.size(); i++) {
//            to[i] = doLists.get(i).getEmail();
//        }
//
//        LOGGER.info("send email to person in charge");
//        EmailSender emailSender = new EmailSender();
//        emailSender.htmlEmailTable(
//                servletContext,
//                "", //user name requestor
//                to, //to
//                "OSTORMS - TEST EMAIL", //subject
//                "<br /> "
//                + "TEST PURPOSE ONLY"
//                + "<br /> "
//                + "Please ignore this email."
//                + "<br /> "
//                + "<br />Thank you." //msg
//        );
//        return "redirect:/admin/user";
//    }
//
//    @RequestMapping(value = "/hwItemMgt", method = RequestMethod.GET)
//    public String itemMgt(
//            Model model
//    ) {
//        HWItemMgtDAO hwItemMgtDAO = new HWItemMgtDAO();
//        List<HWItemMgt> hwItemList = hwItemMgtDAO.getHWItemMgtList();
//
//        model.addAttribute("hwItemList", hwItemList);
//        return "admin/hwItemMgt";
//    }
//
//    @RequestMapping(value = "/hwItemMgt/addItem", method = RequestMethod.GET)
//    public String addItem(
//            Model model
//    ) {
//        HWItemMgtDAO hwItemMgtDAO = new HWItemMgtDAO();
//        List<HWItemMgt> hwItemList = hwItemMgtDAO.getHWItemMgtList();
//
//        HWRackMgtDAO hwRackMgtDAO = new HWRackMgtDAO();
//        List<HWRackMgt> hwRackList = hwRackMgtDAO.getHWCategoryList();
//
//        model.addAttribute("hwRackList", hwRackList);
//        model.addAttribute("hwItemList", hwItemList);
//        return "admin/hwItemMgt_add";
//    }
//
//    @RequestMapping(value = "/hwItemMgt/edit/{id}", method = RequestMethod.GET)
//    public String editItem(
//            Model model,
//            @ModelAttribute UserSession userSession,
//            @PathVariable("id") String id
//    ) {
//        HWItemMgtDAO hwItemMgtDAO = new HWItemMgtDAO();
//        HWItemMgt hwItem = hwItemMgtDAO.getHWItemMgtListPerId(id);
//
//        HWRackMgtDAO hwRackMgtDAO = new HWRackMgtDAO();
//        List<HWRackMgt> hwRackList = hwRackMgtDAO.getHWCategoryList();
//
//        model.addAttribute("hwRackList", hwRackList);
//        model.addAttribute("hwItem", hwItem);
//        return "admin/hwItemMgt_edit";
//    }
//
//    @RequestMapping(value = "/hwItemMgt/edit/save", method = {RequestMethod.GET, RequestMethod.POST})
//    public String hwItemMgtEditSave(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession,
//            @RequestParam(required = false) String hwItemMgtId,
//            @RequestParam(required = false) String itemCategory,
//            @RequestParam(required = false) String sptsItemType,
//            @RequestParam(required = false) String sptsSubItemType,
//            @RequestParam(required = false) String sptsItemId,
//            @RequestParam(required = false) String modelType,
//            @RequestParam(required = false) String rackIdentification,
//            @RequestParam(required = false) String activeStatus
//    ) {
//        String redirect = "";
//        String sptsSubItemTypeQuery = "";
//        String sptsItemIdQuery = "";
//        String modelTypeQuery = "";
//
//        HWItemMgtDAO hwItemMgtDAO = new HWItemMgtDAO();
//        int findItemId = hwItemMgtDAO.getCountId(hwItemMgtId);
//
//        if (sptsSubItemType.equals("")) {
//            sptsSubItemTypeQuery = "IS NULL";
//            sptsSubItemType = null;
//        } else {
//            sptsSubItemTypeQuery = " = '" + sptsSubItemTypeQuery + "' ";
//        }
//
//        if (sptsItemId.equals("")) {
//            sptsItemIdQuery = "IS NULL";
//            sptsItemId = null;
//        } else {
//            sptsItemIdQuery = " = '" + sptsItemId + "' ";
//        }
//
//        if (modelType.equals("")) {
//            modelTypeQuery = "IS NULL";
//            modelType = null;
//        } else {
//            modelTypeQuery = " = '" + modelType + "' ";
//        }
//        hwItemMgtDAO = new HWItemMgtDAO();
//        int findItemDetails = hwItemMgtDAO.getCountExistingSptsDataExceptSelected(sptsItemType, sptsSubItemTypeQuery, sptsItemIdQuery, modelTypeQuery, hwItemMgtId);
//
//        QueryResult queryResult = null;
//        if (findItemId == 1 && findItemDetails < 4) {
//            String flag = "";
//            String status = activeStatus;
//            if (status.equals("Active")) {
//                flag = "0";
//            } else {
//                flag = "1";
//            }
//
//            HWRackMgtDAO hwRackMgtDAO = new HWRackMgtDAO();
//            HWRackMgt hwRack = hwRackMgtDAO.getHWRackMgtListPerId(itemCategory);
//            String rackId = hwRack.getRackId();
//            itemCategory = hwRack.getRackCategory();
//
//            HWItemMgt hwItemMgt = new HWItemMgt();
//            hwItemMgt.setItemCategory(itemCategory);
//            hwItemMgt.setSptsItemType(sptsItemType);
//            hwItemMgt.setSptsSubItemType(sptsSubItemType);
//            hwItemMgt.setSptsItemId(sptsItemId);
//            hwItemMgt.setSptsModelContain(modelType);
//            hwItemMgt.setRackIdentification(rackId);
//            hwItemMgt.setFlag(flag);
//            hwItemMgt.setStatus(status);
//            hwItemMgt.setModifiedBy(userSession.getFullname());
//            hwItemMgt.setCreatedBy(userSession.getFullname());
//            hwItemMgtDAO = new HWItemMgtDAO();
//            queryResult = hwItemMgtDAO.insertHWItemMgt(hwItemMgt);
//            if (queryResult.getResult() == 1) {
//                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
//                redirect = "redirect:/admin/hwItemMgt/";
//            } else {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
//                redirect = "redirect:/admin/hwItemMgt/addItem";
//            }
//        } else {
//            if (findItemId != 1) {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Item Management ID not existed. Please re-check.", args, locale));
//            } else {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Item details existed. Please create new item details.", args, locale));
//            }
//            redirect = "redirect:/admin/hwItemMgt/addItem";
//
//        }
//        return redirect;
//    }
//
//    @RequestMapping(value = "/hwItemMgt/addItem/save", method = {RequestMethod.GET, RequestMethod.POST})
//    public String hwItemMgtAdd(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession,
//            @RequestParam(required = false) String itemCategory,
//            @RequestParam(required = false) String sptsItemType,
//            @RequestParam(required = false) String sptsSubItemType,
//            @RequestParam(required = false) String sptsItemId,
//            @RequestParam(required = false) String modelType,
//            //            @RequestParam(required = false) String rackIdentification,
//            @RequestParam(required = false) String activeStatus
//    ) {
//        String redirect = "";
//        String sptsSubItemTypeQuery = "";
//        String sptsItemIdQuery = "";
//        String modelTypeQuery = "";
//
//        HWItemMgtDAO hwItemMgtDAO = new HWItemMgtDAO();
//        int findItemCategory = hwItemMgtDAO.getCountExistingCategory(itemCategory);
//
//        if (sptsSubItemType.equals("")) {
//            sptsSubItemTypeQuery = "IS NULL";
//            sptsSubItemType = null;
//        } else {
//            sptsSubItemTypeQuery = " = '" + sptsSubItemTypeQuery + "' ";
//        }
//
//        if (sptsItemId.equals("")) {
//            sptsItemIdQuery = "IS NULL";
//            sptsItemId = null;
//        } else {
//            sptsItemIdQuery = " = '" + sptsItemId + "' ";
//        }
//
//        if (modelType.equals("")) {
//            modelTypeQuery = "IS NULL";
//            modelType = null;
//        } else {
//            modelTypeQuery = " = '" + modelType + "' ";
//        }
//        hwItemMgtDAO = new HWItemMgtDAO();
//        int findItemDetails = hwItemMgtDAO.getCountExistingSptsData(sptsItemType, sptsSubItemTypeQuery, sptsItemIdQuery, modelTypeQuery);
//
//        QueryResult queryResult = null;
//        if (findItemCategory == 0 && findItemDetails < 4) {
//            String flag = "";
//            String status = activeStatus;
//            if (status.equals("Active")) {
//                flag = "0";
//            } else {
//                flag = "1";
//            }
//
//            HWRackMgtDAO hwRackMgtDAO = new HWRackMgtDAO();
//            HWRackMgt hwRack = hwRackMgtDAO.getHWRackMgtListPerId(itemCategory);
//            String rackIdentification = hwRack.getRackId();
//            itemCategory = hwRack.getRackCategory();
//
//            HWItemMgt hwItemMgt = new HWItemMgt();
//            hwItemMgt.setItemCategory(itemCategory);
//            hwItemMgt.setSptsItemType(sptsItemType);
//            hwItemMgt.setSptsSubItemType(sptsSubItemType);
//            hwItemMgt.setSptsItemId(sptsItemId);
//            hwItemMgt.setSptsModelContain(modelType);
//            hwItemMgt.setRackIdentification(rackIdentification);
//            hwItemMgt.setFlag(flag);
//            hwItemMgt.setStatus(status);
//            hwItemMgt.setModifiedBy(userSession.getFullname());
//            hwItemMgt.setCreatedBy(userSession.getFullname());
//            hwItemMgtDAO = new HWItemMgtDAO();
//            queryResult = hwItemMgtDAO.insertHWItemMgt(hwItemMgt);
//            if (queryResult.getResult() == 1) {
//                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
//                redirect = "redirect:/admin/hwItemMgt/";
//            } else {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
//                redirect = "redirect:/admin/hwItemMgt/addItem";
//            }
//        } else {
//            if (findItemCategory != 0) {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Item Category existed. Please create new item category.", args, locale));
//            } else {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("Item details existed. Please create new item details.", args, locale));
//            }
//            redirect = "redirect:/admin/hwItemMgt/addItem";
//
//        }
//        return redirect;
//    }
//
//    @RequestMapping(value = "/hwItemMgt/delete/{itemId}", method = {RequestMethod.GET, RequestMethod.POST})
//    public String hwItemMgtDelete(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @PathVariable("itemId") String itemId
//    ) {
//        HWItemMgtDAO hwItemMgtDAO = new HWItemMgtDAO();
//        QueryResult queryResult = hwItemMgtDAO.deleteHWItemMgt(itemId);
//        if (queryResult.getResult() == 1) {
//            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.delete.success", args, locale));
//        } else {
//            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.delete.error", args, locale));
//        }
//        return "redirect:/admin/hwItemMgt";
//    }
//
//    @RequestMapping(value = "/hwRackMgt", method = RequestMethod.GET)
//    public String rackMgt(
//            Model model
//    ) {
//        HWRackMgtDAO hwRackMgtDAO = new HWRackMgtDAO();
//        List<HWRackMgt> hwRackList = hwRackMgtDAO.getHWRackMgtList();
//
//        model.addAttribute("hwRackList", hwRackList);
//        return "admin/hwRackMgt";
//    }
//
//    @RequestMapping(value = "/hwEntryFile", method = RequestMethod.GET)
//    public String hwEntryFile(
//            Model model
//    ) {
//        return "admin/hwEntryFile";
//    }
//
//    @RequestMapping(value = "/hwEntryFile/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView hwEntryFileUpload(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession,
//            @RequestParam(required = false) MultipartFile fileUpload
//    ) {
//        String stringPath = "";
//        String returnPath = "";
//
//        List<HWRequest> hwReqList = new ArrayList<HWRequest>();
//        if (fileUpload.isEmpty()) {
//            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
//        } else {
//            if (fileUpload.getOriginalFilename().contains(".csv")) {
//                try {
//                    File folder = new File(UPLOADED_FOLDER);
//                    File[] verifyFiles = folder.listFiles();
//                    int maxIndex = 0;
//                    if (verifyFiles.length != 0) {
//                        for (File listOfFile : verifyFiles) {
//                            if (listOfFile.isFile()) {
//                                maxIndex++;
//                            }
//                        }
//                    }
//                    String index = String.format("%05d", maxIndex + 1);
//                    // Get the file and save it somewhere
//                    byte[] bytes = fileUpload.getBytes();
//                    Path path = Paths.get(UPLOADED_FOLDER + index + "_HWBarcode_" + fileUpload.getOriginalFilename());
//                    Files.write(path, bytes);
//                    stringPath = path.toString();
//
//                    folder = new File(UPLOADED_FOLDER);
//                    File[] listOfFiles = folder.listFiles();
//
//                    boolean readFile = false;
//                    String fileName = fileUpload.getOriginalFilename();
//                    if (listOfFiles.length != 0) {
//                        for (File listOfFile : listOfFiles) {
//                            if (listOfFile.isFile()) {
//                                if (listOfFile.getName().equals(index + "_HWBarcode_" + fileName)) {
//                                    readFile = true;
//                                    CSVReader csvReader = null;
//                                    try {
//                                        csvReader = new CSVReader(new FileReader(stringPath), ',', '"', 1);
//                                        String[] fileContents = null;
//                                        List<HWFileBCImport> importList = new ArrayList<HWFileBCImport>();
//                                        while ((fileContents = csvReader.readNext()) != null) {
//                                            HWFileBCImport hwFileImport = new HWFileBCImport(
//                                                    fileContents[0], fileContents[1], fileContents[2], //boxId, eqptType, eqptId 
//                                                    fileContents[3], fileContents[4] //qty, reqDate
//                                            );
//                                            importList.add(hwFileImport);
//                                        }
//                                        int y = 1;
//                                        for (HWFileBCImport r : importList) {
//                                            y++;
//                                            HWRequest hwreq = new HWRequest();
//                                            hwreq.setBoxId(r.getBoxId());
//                                            hwreq.setItemType(r.getEqptType());
//                                            hwreq.setItemId(r.getEqptId());
//                                            hwreq.setTotalQty(r.getQty());
//                                            hwreq.setReqDate(r.getReqDate());
//                                            hwReqList.add(hwreq);
//                                        }
//                                    } catch (Exception ee) {
//                                        LOGGER.info("File Reading Error : Error while reading " + fileName + ".");
//                                        ee.printStackTrace();
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                }
//            } else {
//                redirectAttrs.addFlashAttribute("message", "Only .csv ALLOWED.");
//                returnPath = "redirect:/admin/hwEntryFile";
//            }
//            returnPath = "admin/hwEntryFile";
//        }
//        return new ModelAndView("sptsItemMultipleBarcodePdf", "hwReqList", hwReqList);
//    }
//    @RequestMapping(value = "/hwShipFile/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView hwShipFileUpload(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession,
//            @RequestParam(required = false) MultipartFile fileShipUpload
//    ) {
//        String stringPath = "";
//
//        List<HWShipping> doList = new ArrayList<HWShipping>();
//        if (fileShipUpload.isEmpty()) {
//            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
//        } else if (fileShipUpload.getOriginalFilename().contains(".csv")) {
//            try {
//                File folder = new File(UPLOADED_FOLDER);
//                File[] verifyFiles = folder.listFiles();
//                int maxIndex = 0;
//                if (verifyFiles.length != 0) {
//                    for (File listOfFile : verifyFiles) {
//                        if (listOfFile.isFile()) {
//                            maxIndex++;
//                        }
//                    }
//                }
//                String index = String.format("%05d", maxIndex + 1);
//                // Get the file and save it somewhere
//                byte[] bytes = fileShipUpload.getBytes();
//                Path path = Paths.get(UPLOADED_FOLDER + index + "_HWShip_" + fileShipUpload.getOriginalFilename());
//                Files.write(path, bytes);
//                stringPath = path.toString();
//
//                folder = new File(UPLOADED_FOLDER);
//                File[] listOfFiles = folder.listFiles();
//
//                boolean readFile = false;
//                String fileName = fileShipUpload.getOriginalFilename();
//                if (listOfFiles.length != 0) {
//                    for (File listOfFile : listOfFiles) {
//                        if (listOfFile.isFile()) {
//                            if (listOfFile.getName().equals(index + "_HWShip_" + fileName)) {
//                                readFile = true;
//                                CSVReader csvReader = null;
//                                try {
//                                    csvReader = new CSVReader(new FileReader(stringPath), ',', '"', 1);
//                                    String[] fileContents = null;
//                                    List<HWFileSLImport> importList = new ArrayList<HWFileSLImport>();
//                                    while ((fileContents = csvReader.readNext()) != null) {
//                                        HWFileSLImport hwFileImport = new HWFileSLImport(
//                                                fileContents[0], fileContents[1], fileContents[2], //gtsNo, shipDate,boxId
//                                                fileContents[3], fileContents[4], fileContents[5], //itemType, itemId,weight
//                                                fileContents[6], fileContents[7], fileContents[8] //price, totalWeight,totalPrice
//                                        );
//                                        importList.add(hwFileImport);
//                                    }
//                                    int y = 1;
//                                    for (HWFileSLImport r : importList) {
//                                        y++;
//                                        HWShipping hwship = new HWShipping();
//                                        hwship.setGtsNo(r.getGtsNo());
//                                        Date sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(r.getShipDate());
//                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
//                                        String shipDate = formatter.format(sdf);
//                                        hwship.setShippingDate(shipDate);
//                                        hwship.setBoxId(r.getBoxId());
//                                        hwship.setItemCategory(r.getItemType());
//                                        hwship.setItemId(r.getItemId());
//                                        hwship.setWeight(r.getWeight());
//                                        hwship.setPrice(r.getPrice());
//                                        hwship.setTotalWeight(r.getTotalWeight());
//                                        hwship.setTotalPrice(r.getTotalPrice());
//                                        doList.add(hwship);
//                                    }
//                                } catch (Exception ee) {
//                                    LOGGER.info("File Reading Error : Error while reading " + fileName + ".");
//                                    ee.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//            }
//        } else {
//            redirectAttrs.addFlashAttribute("message", "Only .csv ALLOWED.");
//        }
//        return new ModelAndView("doHWListPdf", "doList", doList);
//    }
//
//    @RequestMapping(value = "/hwCreateReq", method = RequestMethod.GET)
//    public String hwCreateReq(
//            Model model
//    ) {
//        return "admin/hwCreateReq";
//    }
//    @RequestMapping(value = "/hwCreateReq/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    public String hwCreateReqUpload(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession,
//            @RequestParam(required = false) MultipartFile fileUpload
//    ) {
//        String stringPath = "";
//
//        List<HWRequest> hwReqList = new ArrayList<HWRequest>();
//        if (fileUpload.isEmpty()) {
//            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
//        } else if (fileUpload.getOriginalFilename().contains(".csv")) {
//            try {
//                File folder = new File(UPLOADED_FOLDER);
//                File[] verifyFiles = folder.listFiles();
//                int maxIndex = 0;
//                if (verifyFiles.length != 0) {
//                    for (File listOfFile : verifyFiles) {
//                        if (listOfFile.isFile()) {
//                            maxIndex++;
//                        }
//                    }
//                }
//                String index = String.format("%05d", maxIndex + 1);
//                // Get the file and save it somewhere
//                byte[] bytes = fileUpload.getBytes();
//                Path path = Paths.get(UPLOADED_FOLDER + index + "_HWInitial_" + fileUpload.getOriginalFilename());
//                Files.write(path, bytes);
//                stringPath = path.toString();
//
//                folder = new File(UPLOADED_FOLDER);
//                File[] listOfFiles = folder.listFiles();
//                String fileName = fileUpload.getOriginalFilename();
//                if (listOfFiles.length != 0) {
//                    for (File listOfFile : listOfFiles) {
//                        if (listOfFile.isFile()) {
//                            if (listOfFile.getName().equals(index + "_HWInitial_" + fileName)) {
//                                CSVReader csvReader = null;
//                                try {
//                                    csvReader = new CSVReader(new FileReader(stringPath), ',', '"', 1);
//                                    String[] fileContents = null;
//                                    List<HWFileRequestImport> importList = new ArrayList<HWFileRequestImport>();
//                                    while ((fileContents = csvReader.readNext()) != null) {
//                                        HWFileRequestImport hwFileImport = new HWFileRequestImport(
//                                                fileContents[0], fileContents[1], fileContents[2], //reqType, boxId, packagingType 
//                                                fileContents[3], fileContents[4], fileContents[5], //itemType, itemId,itemQty
//                                                fileContents[6], fileContents[7], fileContents[8], //pcbBId,pcbBQty,pcbCId
//                                                fileContents[9], fileContents[10], fileContents[11], //pcbCQty,pcbCtrId,pcbCtrQty
//                                                fileContents[12], fileContents[13], fileContents[14], //lcId,lcQty,pcId
//                                                fileContents[15], fileContents[16], fileContents[17], //pcQty,remarks,price
//                                                fileContents[18], fileContents[19], fileContents[20], //weight,gtsNo,shipDate
//                                                fileContents[21] //totalBox
//                                        );
//                                        importList.add(hwFileImport);
//                                    }
//                                    csvReader.close();
//
//                                    int countTotalQty = 0;
////                                        HWShipping hwshipping = new HWShipping();
////                                        List<HWShipping> gtsNoList = new ArrayList<HWShipping>();
//
//                                    for (HWFileRequestImport r : importList) {
//                                        HWRequest hwRequest = new HWRequest();
//                                        hwRequest.setReqType(r.getReqType());
//                                        hwRequest.setBoxId(r.getBoxId());
//                                        hwRequest.setPackagingType(r.getPackagingType());
//                                        hwRequest.setItemType(r.getItemType());
//
//                                        HWItemMgtDAO hwitemdao = new HWItemMgtDAO();
//                                        HWItemMgt hwitem = hwitemdao.getHWItemMgtListPerCategory(r.getItemType());
//                                        if (hwitem != null) {
//                                            hwRequest.setItemCategoryId(hwitem.getId());
//                                            hwRequest.setItemId(r.getItemId());
//                                            JSONObject params0 = new JSONObject();
//                                            params0.put("itemID", r.getItemId());
//                                            JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
//                                            int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
//                                            String itemName = getItemByParam.getJSONObject(0).getString("ItemName");
//                                            hwRequest.setPkid(Integer.toString(itempkid));
//                                            hwRequest.setItemName(itemName);
//                                            countTotalQty = countTotalQty + Integer.parseInt(r.getItemQty());
//                                            hwRequest.setItemQty(r.getItemQty());
//                                            hwRequest.setServiceDate(r.getServiceDate());
//                                            if (r.getPcbBId().equals("null") || r.getPcbBId().equals("")) {
//                                                hwRequest.setPcbBId(null);
//                                                hwRequest.setPcbBName(null);
//                                                hwRequest.setPcbBQty(null);
//                                            } else {
//                                                params0 = new JSONObject();
//                                                params0.put("itemID", r.getPcbBId());
//                                                getItemByParam = SPTSWebService.getItemByParam(params0);
//                                                itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
//                                                itemName = getItemByParam.getJSONObject(0).getString("ItemName");
//                                                hwRequest.setPkidB(Integer.toString(itempkid));
//                                                hwRequest.setPcbBId(r.getPcbBId());
//                                                hwRequest.setPcbBName(itemName);
//                                                countTotalQty = countTotalQty + Integer.parseInt(r.getPcbBQty());
//                                                hwRequest.setPcbBQty(r.getPcbBQty());
//                                            }
//                                            if (r.getPcbCId().equals("null") || r.getPcbCId().equals("")) {
//                                                hwRequest.setPcbCId(null);
//                                                hwRequest.setPcbCName(null);
//                                                hwRequest.setPcbCQty(null);
//                                            } else {
//                                                params0 = new JSONObject();
//                                                params0.put("itemID", r.getPcbCId());
//                                                getItemByParam = SPTSWebService.getItemByParam(params0);
//                                                itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
//                                                itemName = getItemByParam.getJSONObject(0).getString("ItemName");
//                                                hwRequest.setPkidC(Integer.toString(itempkid));
//                                                hwRequest.setPcbCId(r.getPcbCId());
//                                                hwRequest.setPcbCName(itemName);
//                                                countTotalQty = countTotalQty + Integer.parseInt(r.getPcbCQty());
//                                                hwRequest.setPcbCQty(r.getPcbCQty());
//                                            }
//                                            if (r.getPcbCtrId().equals("null") || r.getPcbCtrId().equals("")) {
//                                                hwRequest.setPcbCtrId(null);
//                                                hwRequest.setPcbCtrName(null);
//                                                hwRequest.setPcbCtrQty(null);
//                                            } else {
//                                                params0 = new JSONObject();
//                                                params0.put("itemID", r.getPcbCtrId());
//                                                getItemByParam = SPTSWebService.getItemByParam(params0);
//                                                itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
//                                                itemName = getItemByParam.getJSONObject(0).getString("ItemName");
//                                                hwRequest.setPkidCtr(Integer.toString(itempkid));
//                                                hwRequest.setPcbCtrId(r.getPcbCtrId());
//                                                hwRequest.setPcbCtrName(itemName);
//                                                countTotalQty = countTotalQty + Integer.parseInt(r.getPcbCtrQty());
//                                                hwRequest.setPcbCtrQty(r.getPcbCtrQty());
//                                            }
//                                            if (r.getPcId().equals("null") || r.getPcId().equals("")) {
//                                                hwRequest.setPcId(null);
//                                                hwRequest.setPcName(null);
//                                                hwRequest.setPcQty(null);
//                                            } else {
//                                                params0 = new JSONObject();
//                                                params0.put("itemID", r.getPcId());
//                                                getItemByParam = SPTSWebService.getItemByParam(params0);
//                                                itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
//                                                itemName = getItemByParam.getJSONObject(0).getString("ItemName");
//                                                hwRequest.setPkidPc(Integer.toString(itempkid));
//                                                hwRequest.setPcId(r.getPcId());
//                                                hwRequest.setPcName(itemName);
//                                                countTotalQty = countTotalQty + Integer.parseInt(r.getPcQty());
//                                                hwRequest.setPcQty(r.getPcQty());
//                                            }
//                                            if (r.getLcId().equals("null") || r.getLcId().equals("")) {
//                                                hwRequest.setLcId(null);
//                                                hwRequest.setLcName(null);
//                                                hwRequest.setLcQty(null);
//                                            } else {
//                                                params0 = new JSONObject();
//                                                params0.put("itemID", r.getLcId());
//                                                getItemByParam = SPTSWebService.getItemByParam(params0);
//                                                itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
//                                                itemName = getItemByParam.getJSONObject(0).getString("ItemName");
//                                                hwRequest.setPkidLc(Integer.toString(itempkid));
//                                                hwRequest.setLcId(r.getLcId());
//                                                hwRequest.setLcName(itemName);
//                                                countTotalQty = countTotalQty + Integer.parseInt(r.getLcQty());
//                                                hwRequest.setLcQty(r.getLcQty());
//                                            }
//
//                                            hwRequest.setTotalQty(Integer.toString(countTotalQty));
//                                            hwRequest.setRemarks(r.getRemarks());
//                                            hwRequest.setReqBy("Auto Request Cron");
//                                            hwRequest.setStatus("Ship to Sendayan");
//                                            hwRequest.setFlag("9");
//                                            hwRequest.setModifiedBy("Auto Request Cron");
//                                            hwRequest.setCreatedBy("Auto Request Cron");
//                                            HWRequestDAO hwReqDao = new HWRequestDAO();
//                                            int count = hwReqDao.getBoxIdExist(r.getBoxId());
//                                            boolean testExist = true;
//                                            if (count == 0) {
//                                                testExist = false;
//                                            } else {
//                                                testExist = true;
//                                            }
//
//                                            if (testExist == false) {
//                                                HWShippingDAO shipdao = new HWShippingDAO();
//                                                count = shipdao.getCountSameGtsNo(r.getGtsNo());
//
//                                                hwReqDao = new HWRequestDAO();
//                                                QueryResult qr = hwReqDao.insertReq(hwRequest);
//
//                                                if (qr.getResult() == 1) {
////                                                        hwshipping.setGtsNo(r.getGtsNo());
////                                                        gtsNoList.add(hwshipping);
//
//                                                    String reqId = qr.getGeneratedKey();
//                                                    HWShipping hwShipping = new HWShipping();
//                                                    hwShipping.setBoxId(r.getBoxId());
//                                                    hwShipping.setReqId(reqId);
//                                                    hwShipping.setPrice(r.getPrice());
//                                                    hwShipping.setWeight(r.getWeight());
//                                                    hwShipping.setDoAddedBy("Auto Request Cron");
//                                                    hwShipping.setIndexCount(reqId);
//                                                    hwShipping.setGtsNo(r.getGtsNo());
//                                                    hwShipping.setShippingDate(r.getShippingDate());
//                                                    hwShipping.setTotalBox(r.getTotalBox());
//                                                    hwShipping.setStatus("Ship to Sendayan");
//                                                    hwShipping.setFlag("9");
//                                                    hwShipping.setModifiedBy("Auto Request Cron");
//                                                    hwShipping.setCreatedBy("Auto Request Cron");
//                                                    HWShippingDAO hwShippingDAO = new HWShippingDAO();
//                                                    QueryResult qr2 = hwShippingDAO.insertAutoShipping(hwShipping);
//
//                                                    if (qr2.getResult() != 0) {
//                                                        updateStatus(reqId);
//                                                    } else {
//                                                        LOGGER.info("Failed to update Shipping for " + r.getBoxId());
//                                                    }
//                                                } else {
//                                                    LOGGER.info("Failed to update Request for " + r.getBoxId());
//                                                }
//                                            } else {
//                                                LOGGER.info("Box Id Exist >> " + r.getBoxId());
//                                            }
//                                        } else {
//                                            LOGGER.info("Invalid item category >> " + r.getBoxId());
//                                        }
//                                    }
//                                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("Item has been added into request shipping list.", args, locale));
//                                } catch (IOException ee) {
//                                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("File Reading Error : " + ee.getMessage(), args, locale));
//                                } catch (JSONException ee) {
//                                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("File Reading Error : " + ee.getMessage(), args, locale));
//                                } catch (NoSuchMessageException ee) {
//                                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("File Reading Error : " + ee.getMessage(), args, locale));
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("File Reading Error : " + e.getMessage(), args, locale));
//            }
//        } else {
//            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("File in a wrong format. Please upload in .CSV format ONLY.", args, locale));
//        }
//        return "redirect:/admin/hwCreateReq";
//    }
//    public void updateStatus(String requestId) {
//        /*create csv & email*/
//        File file = new File("D:\\Data\\OSTORMS\\RL\\hw_request.csv");//utk server baru
//
//        HWShippingDAO shippingDao = new HWShippingDAO();
//        HWShipping shipping = shippingDao.getShippingPerReqIdDb(requestId);
//
//        if (file.exists()) { //create csv file
//            FileWriter fileWriter = null;
//            try {
//                fileWriter = new FileWriter("D:\\Data\\OSTORMS\\RL\\hw_request.csv", true);
//                //New Line after the header
//                fileWriter.append(LINE_SEPARATOR);
//                fileWriter.append(shipping.getReqId());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getBoxId());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getServiceDate());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getItemCategory());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getShippingDate());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getGtsNo());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append("1");
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getPrice());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getWeight());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.close();
//                System.out.println("Success to append hw_request.csv for reqId : " + shipping.getReqId());
//            } catch (Exception ee) {
//            } finally {
//                try {
//                    fileWriter.close();
//                } catch (IOException ie) {
//                    System.out.println("Error occured while closing the fileWriter");
//                }
//            }
//        } else {
//            FileWriter fileWriter = null;
//            try {
//                fileWriter = new FileWriter("D:\\Data\\OSTORMS\\RL\\hw_request.csv", true);
////                LOGGER.info("Create hw_request.csv");
//                //Adding the header
//                fileWriter.append(HEADER_REQUEST);
//                //New Line after the header
//                fileWriter.append(LINE_SEPARATOR);
//                fileWriter.append(shipping.getReqId());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getBoxId());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getServiceDate());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getItemCategory());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getShippingDate());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getGtsNo());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append("1");
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getPrice());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(shipping.getWeight());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.close();
//                System.out.println("Success to write new hw_request.csv for reqId : " + shipping.getReqId());
//            } catch (Exception ee) {
//            } finally {
//                try {
//                    fileWriter.close();
//                } catch (IOException ie) {
//                    System.out.println("Error occured while closing the fileWriter");
//                }
//            }
//        }
//    }
}
