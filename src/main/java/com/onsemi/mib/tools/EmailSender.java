package com.onsemi.mib.tools;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import com.onsemi.mib.dao.EmailDAO;
import com.onsemi.mib.dao.HimsEmailDAO;
import com.onsemi.mib.model.Email;
import com.onsemi.mib.model.User;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class EmailSender extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private final String emailTemplate = "resources/email";
    private final String logoPath = "/resources/vendor/login/img/heat.png";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public EmailSender() {
    }

    public void textEmail(final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(env.getProperty("mail.sender"));
                message.setTo(to);
                message.setSubject(subject);
                message.setText(msg);
                mailSender.send(message);
            }
        }).start();
    }

    public void mimeEmail(final ServletContext servletContext, final User user, final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MimeMessagePreparator preparator = new MimeMessagePreparator() {
                    @Override
                    public void prepare(MimeMessage mimeMessage) throws Exception {
                        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                        message.setTo(to);
                        message.setFrom(env.getProperty("mail.sender"));
                        message.setSubject(subject);
                        Map model = new HashMap();
                        model.put("user", user.getFullname());
                        model.put("subject", subject);
                        model.put("message", msg);
                        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                        freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                        String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                        message.setText(text, true);
                    }
                };
                mailSender.send(preparator);
            }
        }).start();
    }

    public void htmlEmail(final ServletContext servletContext, final User user, final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmail2(final ServletContext servletContext, final String user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user);
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailWithAttachment(final ServletContext servletContext, final User user, final String[] to, final File file, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
//                    EmailDAO emailDAO = new EmailDAO(); //original
                    HimsEmailDAO emailDAO = new HimsEmailDAO(); 
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);
                    htmlEmail.setSSLOnConnect(false);

//                    File file = new File("C:\\test.csv");
                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);
                    htmlEmail.embed(file);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    public void htmlEmailManyTo(final ServletContext servletContext, final User user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
//                    EmailDAO emailDAO = new EmailDAO();
                    HimsEmailDAO emailDAO = new HimsEmailDAO(); 
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);
                    htmlEmail.setSSLOnConnect(false);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void htmlEmailWithAttachmentAndInlineImage(
            final ServletContext servletContext, final String user, final String[] to, final String subject, final File file,
            final String msg1,
            //        final File png1, 
            final String msg2,
            //        final File png2, 
            final String msg3,
            //        final File png3, 
            final String msg4,
            //        final File png4, 
            final String msg5,
            //        final File png5, 
            final String msg6,
            //        final File png6, 
            final String msg7,
            //        final File png7, 
            final String msg8,
            //        final File png8,
            final String msg
    ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);
                    htmlEmail.embed(file);
//                    htmlEmail.embed(png1);
//                    htmlEmail.embed(png2);
//                    htmlEmail.embed(png3);
//                    htmlEmail.embed(png4);
//                    htmlEmail.embed(png5);
//                    htmlEmail.embed(png6);
//                    htmlEmail.embed(png7);
//                    htmlEmail.embed(png8);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);
//                    String img1Cid = htmlEmail.embed(png1);
//                    String img2Cid = htmlEmail.embed(png2);
//                    String img3Cid = htmlEmail.embed(png3);
//                    String img4Cid = htmlEmail.embed(png4);
//                    String img5Cid = htmlEmail.embed(png5);
//                    String img6Cid = htmlEmail.embed(png6);
//                    String img7Cid = htmlEmail.embed(png7);
//                    String img8Cid = htmlEmail.embed(png8);

                    Map model = new HashMap();
                    model.put("user", user);
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("message1", msg1);
                    model.put("message2", msg2);
                    model.put("message3", msg3);
                    model.put("message4", msg4);
                    model.put("message5", msg5);
                    model.put("message6", msg6);
                    model.put("message7", msg7);
                    model.put("message8", msg8);
//                    model.put("img1Cid", img1Cid);
//                    model.put("img2Cid", img2Cid);
//                    model.put("img3Cid", img3Cid);
//                    model.put("img4Cid", img4Cid);
//                    model.put("img5Cid", img5Cid);
//                    model.put("img6Cid", img6Cid);
//                    model.put("img7Cid", img7Cid);
//                    model.put("img8Cid", img8Cid);
                    model.put("logoCid", logoCid);

                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template_perf.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailTable(final ServletContext servletContext, final String user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user);
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }
    public void htmlEmailTableForHwReplacement(final ServletContext servletContext, final String user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user);
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template_hw_replace.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

//    private HashMap<String,String> downloadInLineImage(Item item, String dynamicOutputDirectory) throws Exception, ServiceLocalException {
//        //create output directory if not present
//        //bind the item to a new email message. if you do not bind, then the getHasAttachments() function will fail
//        EmailMessage mostRecentMatch = (EmailMessage)item;
//        String from = mostRecentMatch.getFrom().getAddress();
//        String user =StringUtils.substringBefore(from, "@");
//        AttachmentCollection collection=item.getAttachments();
//
//        HashMap<String,String> inlineFiles=new HashMap<String,String>();
//
//        if(collection.getCount()>0)
//        {
//            for (Attachment attachment : collection.getItems()) {
//
//                if(attachment.getIsInline())
//                {
//
//                    FileAttachment currentFile = (FileAttachment) attachment;
//                    String filePath=dynamicOutputDirectory+"/"+user+currentFile.getName();
//                    File file=new File(filePath);
//                    FileOutputStream fio=new FileOutputStream(file);
//                    currentFile.load(fio);
//                    inlineFiles.put(currentFile.getContentId(), filePath);
//                    fio.close();
//                }
//            }
//    }
    
    public void htmlEmailFT(final ServletContext servletContext, final String user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user);
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template_functional_test.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }
    
}