package com.example.AutomatedMailSystem.MailProject;

import com.example.AutomatedMailSystem.Util.ConsoleColors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailBySite {
    public static void SendMail(String Path, String Filename) {

        String to="sarangag@mobios.lk";//change accordingly
        final String user="alisastarkhackx@gmail.com";//change accordingly
        final String password="sadhghajsdghajsdgsahd";//change accordingly

        //1) get the session object
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.debug", "true");

        properties.put("mail.smtp.EnableSSL.enable","true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");


        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //2) compose message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(" Manual  Report");

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("This Is a Automated Mail");


            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            //String filename = "E://Reports//test.txt";//change accordingly
            String filename = Path+Filename;
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart );

            //7) send message
            String TimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            Transport.send(message);
            System.out.println(ConsoleColors.CYAN_BRIGHT + " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"+ ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BRIGHT + "|  "+TimeStamp+"                                                                |"+ ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BRIGHT + "| Automated Mail Has Been Sent Successfully  |"+ ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BRIGHT + " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"+ ConsoleColors.RESET);

        }catch (MessagingException ex) {ex.printStackTrace();}
    }
}