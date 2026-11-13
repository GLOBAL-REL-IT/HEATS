package com.onsemi.mib.config;

import com.onsemi.mib.dao.LDAPUserDAO;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.tools.QueryResult;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:ldap.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private DataSource dataSource;

    @Value("${ldap.url:}")
    private String ldapUrl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @PostConstruct
    public void validateLdapProperties() {
        if (ldapUrl == null || ldapUrl.trim().isEmpty()) {
            throw new IllegalStateException("FATAL: 'ldap.url' is null or empty! Check classpath:ldap.properties");
        }
    }

    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String rawUsername = authentication.getName();
                String rawPassword = (authentication.getCredentials() != null) ? authentication.getCredentials().toString() : "";

                String cleanUsername = (rawUsername != null && rawUsername.contains("@")) ? rawUsername.substring(0, rawUsername.indexOf("@")).trim() : (rawUsername != null ? rawUsername.trim() : "");

                String[] bindFormats = {
                    cleanUsername + "@ad.onsemi.com"
                };

                javax.naming.directory.DirContext ctx = null;
                String successfulBindUser = null;
                Exception lastException = null;

                for (String bindPrincipal : bindFormats) {
                    try {
                        java.util.Hashtable<String, String> env = new java.util.Hashtable<>();
//                        java.util.Hashtable<String, String> env = new java.util.Hashtable<String, String>();
                        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                        env.put(javax.naming.Context.PROVIDER_URL, ldapUrl.trim());
                        env.put(javax.naming.Context.SECURITY_AUTHENTICATION, "simple");
                        env.put(javax.naming.Context.SECURITY_PRINCIPAL, bindPrincipal);
                        env.put(javax.naming.Context.SECURITY_CREDENTIALS, rawPassword);

                        LOGGER.info("=== TRYING AD BIND WITH: [" + bindPrincipal + "] ===");
                        ctx = new javax.naming.directory.InitialDirContext(env);
                        successfulBindUser = bindPrincipal;
                        LOGGER.info("=== SUCCESSFUL AD BIND WITH: [" + bindPrincipal + "] ===");
                        LOGGER.info("=== BIND SUCCESSFUL! PROVIDER_URL is: " + env.get(Context.PROVIDER_URL) + " ===");
                        
                        // check if da ada rekod dlm database
                        LDAPUser ldap = new LDAPUser();
                        LDAPUserDAO ldapdao = new LDAPUserDAO();
                        
                        ldap = ldapdao.getByLoginId(cleanUsername);
                        if (ldap == null) {
                            ctx.close();
                            // Keep your existing PROVIDER_URL: ldap://adldap.ad.onsemi.com:389/o=ondex
                            env.put(Context.PROVIDER_URL, "ldap://adldap.ad.onsemi.com:389");
                            env.put(Context.REFERRAL, "ignore");
                            ctx = new InitialDirContext(env);

                            // 1. Ask adldap.ad.onsemi.com what root namingContexts it actually hosts!
                            Attributes rootAttrs = ctx.getAttributes("", new String[]{"defaultNamingContext", "namingContexts"});
                            LOGGER.info("=== ROOT DSE ATTRS: " + rootAttrs + " ===");

                            // 2. Pick defaultNamingContext if present, otherwise use "" (empty string)
                            String searchBase = "";
                            if (rootAttrs.get("defaultNamingContext") != null) {
                                searchBase = rootAttrs.get("defaultNamingContext").get().toString();
                            } else if (rootAttrs.get("namingContexts") != null) {
                                searchBase = rootAttrs.get("namingContexts").get().toString();
                            }

                            // 3. Search using that exact searchBase
                            String searchFilter = "(|(sAMAccountName=" + cleanUsername + ")(cn=" + cleanUsername + ")(uid=" + cleanUsername + "))";
                            SearchControls controls = new SearchControls();
                            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                            controls.setReturningAttributes(new String[]{"sAMAccountName", "cn", "uid", "sn", "givenName", "employeeNumber", "mail", "title", "distinguishedName"});

                            NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, controls);

                            if (!results.hasMore()) {
                                LOGGER.warn("=== LDAP SEARCH RETURNED 0 RESULTS for: " + cleanUsername + " ===");
                            }

                            while (results.hasMore()) {
                                SearchResult searchResult = (SearchResult) results.next();
                                Attributes attributes = searchResult.getAttributes();

                                String samVal = (attributes.get("sAMAccountName") != null) ? attributes.get("sAMAccountName").get().toString() : "NULL";
                                String cnVal = (attributes.get("cn") != null) ? attributes.get("cn").get().toString() : "NULL";
                                String oncidVal = (attributes.get("employeeNumber") != null) ? attributes.get("employeeNumber").get().toString() : "NULL";
                                String dnVal = (attributes.get("distinguishedName") != null) ? attributes.get("distinguishedName").get().toString() : "NULL";

                                // Log both values side by side
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
                                    if (key.equalsIgnoreCase("employeeNumber")) {
                                        user.setOncid(attributes.get(key).get().toString());
                                    }
                                }
                                ldap = new LDAPUser();

                                ldap.setLoginId(cleanUsername);
                                ldap.setOncid(user.getOncid());
                                ldap.setFirstname(user.getFirstname());
                                ldap.setLastname(user.getLastname());
                                ldap.setEmail(user.getEmail());
                                ldap.setTitle(user.getTitle());
                                ldap.setGroupId("3");
                                ldap.setRequestAccess("No");

                                LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
                                QueryResult queryResult = ldapUserDAO.insert(ldap);
                            }
                        }

                        ctx.close();
                        break;
                    } catch (Exception e) {
                        LOGGER.error("=== FAILED BIND WITH [" + bindPrincipal + "]: " + e.getMessage());
                        lastException = e;
                    }
                }

                if (successfulBindUser == null) {
                    throw new org.springframework.security.authentication.BadCredentialsException("AD Bind failed for all formats", lastException);
                }

                // Now fetch user status and roles from user_ldap table
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT group_id, is_active FROM user_ldap WHERE login_id = ?", cleanUsername);

                if (rows == null || rows.isEmpty()) {
                    throw new UsernameNotFoundException("User [" + cleanUsername + "] authenticated in AD but is not registered in user_ldap.");
                }

                Object activeObj = rows.get(0).get("is_active");
                boolean isActive = activeObj != null && ("1".equals(activeObj.toString()) || "true".equalsIgnoreCase(activeObj.toString()));

                if (!isActive) {
                    throw new DisabledException("User [" + cleanUsername + "] is disabled in user_ldap.");
                }

                List<GrantedAuthority> dbAuthorities = rows.stream()
                        .filter(row -> row.get("group_id") != null)
                        .map(row -> new SimpleGrantedAuthority(row.get("group_id").toString()))
                        .collect(Collectors.toList());
//                List<GrantedAuthority> dbAuthorities = new ArrayList<GrantedAuthority>();
//                for (Map<String, Object> row : rows) {
//                    if (row.get("group_id") != null) {
//                        dbAuthorities.add(new SimpleGrantedAuthority(row.get("group_id").toString()));
//                    }
//                }

                if (dbAuthorities.isEmpty()) {
                    dbAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                UserDetails userDetails = new User(cleanUsername, "N/A", isActive, true, true, true, dbAuthorities);
                return new UsernamePasswordAuthenticationToken(userDetails, "N/A", dbAuthorities);
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Authenticate strictly against Active Directory (no DB password check)
        auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/logout/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .failureUrl("/error")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/");
    }

}