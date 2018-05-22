/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Windows
 */
public class M7Email {

    public boolean sendEmail(String titulo,String mensagem,String email){

        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("salvocalfonseca@gmail.com", "ssbwar86");
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress("salvocalfonseca@gmail.com", "Solinftec GFC")); //Remetente
            } catch (UnsupportedEncodingException ex) {
                    return false;
            }

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(email);
            

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(titulo);//Assunto
            message.setText(mensagem);
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);
            return true;
       
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
