package com.example.appelprojet.util;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.EtudiantDAO;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Transaction;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.EtudiantDAO;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Scolarite;
import com.example.appelprojet.mertier.Utilisateur;
import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Transaction;

import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class FontionsUtiles {
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()){
//            return false;
//        }
//        return true;
    }
//
//    public static void sendMail(String to, String objet, String contenue_message) {
//        String from = "appelprojet1@gmail.com";
//        // Assuming you are sending email from through gmails smtp
//        String host = "smtp.gmail.com";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.auth.plain.disable", "true");
//
//        // Get the Session object.// and pass username and password
//        javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
//
//            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication("appelprojet1@gmail.com", "A123456789&");
//
//            }
//        });
//        // Used to debug SMTP issues
//        session.setDebug(true);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//            System.out.println("Message Start");
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//            System.out.println("Set from");
//
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject(objet);
//            System.out.println("Set Objet");
//
//            // Now set the actual message
//            message.setContent(contenue_message,"text/html") ;
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//
//    }

    public static void notifyAbsenceSeanceByEtu(Etudiant etudiant , Seance seance) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("EEEE, dd/MM");
        System.out.println("Send mail to Strudent " +etudiant.getIdU());
        String to = etudiant.getEmail();
        System.out.println("Send mail to Etudiant " + etudiant.getEmail());
        String objet = "[Capitole UT1] Notification d'absence";
        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Message</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body><p>Bonjour,</p><p>Vous avez été absent au cours de : "+seance.getCours().getNomCours() +" le " + displayFormat.format(seance.getDateDebut())+"</p>";
        System.out.println(message);
        try {
            sendEmail(objet,message,to);
        }catch (Exception e){
            System.out.println(e);
        }

    }


    public static void notifyAbsenceSeance(List<Presence> presences) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("EEEE, dd/MM");
        for (Presence p : presences) {
            System.out.println("Etat Presence " + p.getEtatPresence());
            if (p.getEtatPresence().equals(EtatPresence.ABSENCE)) {
                System.out.println("Send mail to Strudent " + p.getEtudiant().getIdU());
                String to = p.getEtudiant().getEmail();
                System.out.println("Send mail to Etudiant " + p.getEtudiant().getEmail());
                String objet = "[Capitole UT1] Notification d'absence";
                String message = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Message</title>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "</head>\n" +
                        "<body><p>Bonjour,</p><p>Vous avez été absent au cours de : "+ p.getSeance().getCours().getNomCours() +" le " + displayFormat.format(p.getSeance().getDateDebut())+"</p>";
                System.out.println(message);
                try {
                    sendEmail(objet,message,to);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }


    public static void sendEmail(String sujet, String texte, String toMail){

        if (toMail == null && toMail.equals("")) {
            System.out.println("The mail of recipient is missed.");
        }else {
            Properties props = new Properties();
            props.put("mail.host", "smtp.gmail.com");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.debug", "true");
            props.setProperty("mail.smtp.timeout", "1000");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(props);
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress("appelprojet1@gmail.com"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                message.setSubject(sujet);
                message.setContent(texte, "text/html;charset=UTF-8");
                message.setSentDate(new Date());
                message.saveChanges();

                // send Email
                Transport transport = session.getTransport();
                transport.connect("appelprojet1@gmail.com", "A123456789&");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }

    }

    public static void sendEmail1(String sujet, String texte, String toMail) throws Exception, MessagingException {

        if (toMail == null && toMail.equals("")) {
            System.out.println("The mail of recipient is missed.");
        } else {
            Properties props = new Properties();
            props.put("mail.host", "smtp.gmail.com");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.debug", "true");
            props.setProperty("mail.smtp.timeout", "1000");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(props);
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("appelprojet1@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            message.setSubject(sujet);
            message.setContent(texte, "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            message.saveChanges();

            // send Email
            Transport transport = session.getTransport();
            transport.connect("appelprojet1@gmail.com", "A123456789&");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
    }

    // send justificatif alterté email
    public static void sendJustiMail(Scolarite scolarite) throws Exception {
        String toMail = "";
        String sujet = "Receive of justificatif";
        String text = "You receive a justificatif of absence. Please go to Appel System to check.";

        try {

            toMail = scolarite.getEmail();
        }catch (Exception e){
            System.out.println(e);
        }

        sendEmail1(sujet, text, toMail);

    }

    public static String findPhotoEtu(Etudiant etudiant){
        String imagePath = ".\\src\\main\\webapp";
        String idE = String.valueOf(etudiant.getIdU());
        File root = new File(imagePath);
        File[] files = root.listFiles();
        for (File file : files){

            //System.out.println(file.getAbsolutePath());
            if(FilenameUtils.getBaseName(file.getName()).equals(idE)){
                imagePath = file.getName();
                break;
            }
        }

        return imagePath==""?"logo.png":imagePath;
    }




//                        if (etudiant.getEmail() == null && etudiant.getEmail().equals("")) {
//                            System.out.println("The mail of recipient is missed.");
//                        }else {
//                            String objet = "[Capitole UT1] Notification d'absence";
//                            SimpleDateFormat displayFormat = new SimpleDateFormat("EEEE, dd/MM");
//                            String text = "<!DOCTYPE html>\n" +
//                                    "<html>\n" +
//                                    "<head>\n" +
//                                    "    <title>Message</title>\n" +
//                                    "    <meta charset=\"UTF-8\">\n" +
//                                    "</head>\n" +
//                                    "<body><p>Bonjour,</p><p>Vous avez été absent au cours de : "+ seance.getCours().getNomCours() +" le " + displayFormat.format(seance.getDateDebut())+"</p>";
//
//                            Properties props = new Properties();
//                            props.put("mail.host", "smtp.gmail.com");
//                            props.put("mail.transport.protocol", "smtp");
//                            props.put("mail.smtp.auth", "true");
//                            props.put("mail.smtp.ssl.enable", "true");
//                            props.setProperty("mail.smtp.starttls.enable", "true");
//                            props.setProperty("mail.debug", "true");
//                            props.setProperty("mail.smtp.timeout", "1000");
//                            props.setProperty("mail.smtp.socketFactory.port", "465");
//                            props.setProperty("mail.smtp.socketFactory.fallback", "false");
//                            props.put("mail.smtp.starttls.required", "true");
//                            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//                            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//                            javax.mail.Session session = Session.getInstance(props);
//                            session.setDebug(true);
//
//                            MimeMessage message = new MimeMessage(session);
//                            message.setFrom(new InternetAddress("appelprojet1@gmail.com"));
//                            message.setRecipient(Message.RecipientType.TO, new InternetAddress(etudiant.getEmail()));
//                            message.setSubject(objet);
//                            message.setContent(text, "text/html;charset=UTF-8");
//                            message.setSentDate(new Date());
//                            message.saveChanges();
    //
//                            // send Email
//                            Transport transport = session.getTransport();
//                            transport.connect("appelprojet1@gmail.com", "A123456789&");
//                            transport.sendMessage(message, message.getAllRecipients());
//                            transport.close();
//                        }



    public static void main(String[] args) throws Exception {

        // test sendJustiMail
        try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = sn.beginTransaction();
            // enregistrer le justificatif
            Etudiant etudiant = sn.get(Etudiant.class, 8l);
            t.commit();
            Scolarite scolarite = new EtudiantDAO().findScoByEtu(etudiant);
            System.out.println("-------");
            System.out.println(scolarite.getIdU());
            sendJustiMail(scolarite);
            sn.close();
        }
//
//        // test findPhotoEtu
//        org.hibernate.Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction tc = ses.beginTransaction() ;
//        Etudiant etudiant = ses.get(Etudiant.class,7l);
//        System.out.println(FunctionsUtil.findPhotoEtu(etudiant));

//                // test sendMail for Absence
//        try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
//            Transaction t = sn.beginTransaction();
//            // enregistrer le justificatif
//            Etudiant etudiant = sn.get(Etudiant.class, 7l);
//            t.commit();
//            Scolarite scolarite = new EtudiantDAO().findScoByEtu(etudiant);
//
//            sendJustiMail(scolarite);
//            sn.close();
//        }
//
//        // test findPhotoEtu
//        org.hibernate.Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction tc = ses.beginTransaction() ;
//        Etudiant etudiant = ses.get(Etudiant.class,7l);
//        System.out.println(FunctionsUtil.findPhotoEtu(etudiant));
    }
}
