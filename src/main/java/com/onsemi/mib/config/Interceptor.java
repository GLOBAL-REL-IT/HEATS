package com.onsemi.mib.config;

import com.onsemi.mib.dao.LDAPUserDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.onsemi.mib.dao.MenuDAO;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.model.Menu;
import com.onsemi.mib.model.User;
import com.onsemi.mib.model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

public class Interceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interceptor.class);
    String[] args = {};

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession currentSession = request.getSession();
        if (!auth.getName().equals("anonymousUser")) {
            LOGGER.info("preHandle|LOGGED_IN - Login ID: {}", auth.getName());
            //User Session
            User user = setUserSession(auth, currentSession);
            //Menu
            setUserMenu(request, user, currentSession);
        } else {
            LOGGER.info("preHandle|LOGGED_OUT - Login ID: {}", auth.getName());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mapAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
    }

    private User setUserSession(Authentication auth, HttpSession currentSession) {
        //Normal User Session
//        UserDAO userDAO = new UserDAO();
//        User user = userDAO.getUserByLoginId(auth.getName());
//        UserSession userSession = new UserSession();
//        userSession.setId(user.getId());
//        userSession.setLoginId(user.getLoginId());
//        userSession.setFullname(user.getFullname());
//        userSession.setEmail(user.getEmail());
//        userSession.setGroup(user.getGroupId());

        //LDAP User Session
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        LDAPUser ldapUser = ldapUserDAO.getByLoginId(auth.getName());

        User user = new User();
        UserSession userSession = new UserSession();
        if (ldapUser.getFirstname() == null) {
            //Set Unregistered User
            user.setId("0");
            user.setLoginId(auth.getName());
            user.setFullname(auth.getName());
            user.setEmail("N/A");
            user.setGroupId("0");
            user.setFirstName(ldapUser.getFirstname());
            //new for UAC
            user.setItemAdd("No");
            user.setItemEdit("No");
            user.setItemDelete("No");
            user.setItemActivityConfig("No");
            user.setItemActivityAdd("No");
            user.setItemActivityEdit("No");
            user.setItemHardwareAdd("No");
            user.setItemHardwareEdit("No");
            user.setItemHardwareDelete("No");
            user.setItemMovementAdd("No");
            user.setItemSfRecall("No");
            //Set User Session
            userSession.setId(user.getId());
            userSession.setLoginId(user.getLoginId());
            userSession.setFullname(user.getFullname());
            userSession.setEmail(user.getEmail());
            userSession.setGroup(user.getGroupId());
            userSession.setFirstName(user.getFirstName());
            //new for UAC
            userSession.setItemAdd(user.getItemAdd());
            userSession.setItemEdit(user.getItemEdit());
            userSession.setItemDelete(user.getItemDelete());
            userSession.setItemActivityConfig(user.getItemActivityConfig());
            userSession.setItemActivityAdd(user.getItemActivityAdd());
            userSession.setItemActivityEdit(user.getItemActivityEdit());
            userSession.setItemHardwareAdd(user.getItemHardwareAdd());
            userSession.setItemHardwareEdit(user.getItemHardwareEdit());
            userSession.setItemHardwareDelete(user.getItemHardwareDelete());
            userSession.setItemMovementAdd(user.getItemMovementAdd());
            userSession.setItemSfRecall(user.getItemSfRecall());
        } else {
            //Set Registered User (Remain Using User Model)
            user.setId(ldapUser.getId());
            user.setLoginId(ldapUser.getLoginId());
            user.setFullname(ldapUser.getFirstname() + " " + ldapUser.getLastname());
            user.setEmail(ldapUser.getEmail());
            user.setGroupId(ldapUser.getGroupId());
            user.setFirstName(ldapUser.getFirstname());
            //new for UAC
            user.setItemAdd(ldapUser.getItemAdd());
            user.setItemEdit(ldapUser.getItemEdit());
            user.setItemDelete(ldapUser.getItemDelete());
            user.setItemActivityConfig(ldapUser.getItemActivityConfig());
            user.setItemActivityAdd(ldapUser.getItemActivityAdd());
            user.setItemActivityEdit(ldapUser.getItemActivityEdit());
            user.setItemHardwareAdd(ldapUser.getItemHardwareAdd());
            user.setItemHardwareEdit(ldapUser.getItemHardwareEdit());
            user.setItemHardwareDelete(ldapUser.getItemHardwareDelete());
            user.setItemMovementAdd(ldapUser.getItemMovementAdd());
            user.setItemSfRecall(ldapUser.getItemSfRecall());
            //Set User Session
            userSession.setId(user.getId());
            userSession.setLoginId(user.getLoginId());
            userSession.setFullname(user.getFullname());
            userSession.setEmail(user.getEmail());
            userSession.setGroup(user.getGroupId());
            userSession.setFirstName(user.getFirstName());
            //new for UAC
            userSession.setItemAdd(user.getItemAdd());
            userSession.setItemEdit(user.getItemEdit());
            userSession.setItemDelete(user.getItemDelete());
            userSession.setItemActivityConfig(user.getItemActivityConfig());
            userSession.setItemActivityAdd(user.getItemActivityAdd());
            userSession.setItemActivityEdit(user.getItemActivityEdit());
            userSession.setItemHardwareAdd(user.getItemHardwareAdd());
            userSession.setItemHardwareEdit(user.getItemHardwareEdit());
            userSession.setItemHardwareDelete(user.getItemHardwareDelete());
            userSession.setItemMovementAdd(user.getItemMovementAdd());
            userSession.setItemSfRecall(user.getItemSfRecall());

        }

        currentSession.setAttribute("userSession", userSession);
        return user;
    }

    private void setUserMenu(HttpServletRequest request, User user, HttpSession currentSession) {
        Locale locale = RequestContextUtils.getLocale(request);
        String currentPath = request.getRequestURI().substring(request.getContextPath().length());
        String path[] = currentPath.split("\\/");
        String parentPath = "/";
        String name = "";
        if (path.length != 0) {
            parentPath += path[1];
        }
        String newCurrentPath = currentPath;
        if (path.length > 3) {
            newCurrentPath = "/" + path[1] + "/" + path[2];
        }
        Connection conn = null;
        String menu = ""
                //                + "<ul class=\"nav nav-pills nav-stacked\">"; //original
                + "<ul>";
        try {
            conn = dataSource.getConnection();
            MenuDAO menuDAO = new MenuDAO(conn);
            List<Menu> parentMenuList = menuDAO.getMenuAccess("0", parentPath, user.getGroupId());
            for (int i = 0; i < parentMenuList.size(); i++) {
                Menu parentMenu = parentMenuList.get(i);
                List<Menu> childMenuList = menuDAO.getMenuAccess(parentMenu.getCode(), newCurrentPath, user.getGroupId());
//                String li = "<li " + parentMenu.getClassActive() + " style=\"border-bottom-style: ridge; border-color: #f06a0a;\">";
                String li = "<li " + parentMenu.getClassActive() + " style=\"border: 1px; border-top-style: ridge; border-bottom-style: ridge; border-color: grey; border-radius: 7px;\">";
                String aHref = "<a href=\"" + request.getContextPath() + parentMenu.getUrlPath() + "\">";
                String caret = "";
                if (!childMenuList.isEmpty()) {
                    if (!parentMenu.getClassActive().equals("")) {
                        li = "<li class=\"sidebar-dropdown active\" style=\"border: 1px; border-top-style: ridge; border-bottom-style: ridge; border-color: grey; border-radius: 7px;\">";
                    } else {
                        li = "<li class=\"sidebar-dropdown\" style=\"border: 1px; border-top-style: ridge; border-bottom-style: ridge; border-color: grey; border-radius: 7px;\">";
                    }
                    aHref = "<a href=\"#\">";
//                    caret = "<i class=\"fa fa-chevron-circle-down drop-icon\"></i>";
                }
                menu += li
                        + aHref
                        + "<i class=\"bi " + parentMenu.getIcon() + "\"></i>"
                        //+ "<span>" + parentMenu.getName() + "</span>"
                        + "<span class=\"menu-text\">" + messageSource.getMessage(parentMenu.getLabelKey(), args, locale) + "</span>"
                        //                        + caret
                        + "</a>";
                if (!childMenuList.isEmpty()) {
                    String subNav = " <div class=\"sidebar-submenu\"> <ul>";
//                    if (parentPath.equals(parentMenu.getUrlPath())) {
//                        subNav = "<ul>";
//                    }
                    menu += subNav;
                    menu += childMenu(menuDAO, childMenuList, request, locale, newCurrentPath, parentPath, currentPath);
                    menu += "</ul>";
                }
                menu += "</li>";

                name = messageSource.getMessage(parentMenu.getLabelKey(), args, locale);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        menu += "</ul>";
        currentSession.setAttribute("userMenu", menu);
        currentSession.setAttribute("name", name);
    }

    private String childMenu(MenuDAO menuDAO, List<Menu> childMenuList, HttpServletRequest request, Locale locale, String currentPath, String parentPath, String oriPath) {
        String subNav = "";
        for (int j = 0; j < childMenuList.size(); j++) {
            Menu childMenu = childMenuList.get(j);
            String subNavActive = "";
            if (!childMenu.getClassActive().equals("")) {
                subNavActive = "class=\"current-page\"";
            } else if (!menuDAO.menuExist(currentPath)) {
                if (parentPath.equals(childMenu.getUrlPath())) {
                    subNavActive = "class=\"current-page\"";
                } else if (oriPath.equals(childMenu.getUrlPath())) {
                    subNavActive = "class=\"current-page\"";
                }
            }
            subNav += "<li>";
            subNav += "<a href=\"" + request.getContextPath() + childMenu.getUrlPath() + "\" " + subNavActive + ">"
                    //+ "<i class=\"fa " + childMenu.getIcon() + "\"></i>"
                    //+ childMenu.getName()
                    + messageSource.getMessage(childMenu.getLabelKey(), args, locale)
                    + "</a>";
            subNav += "</li>";
        }
        return subNav;
    }
}
