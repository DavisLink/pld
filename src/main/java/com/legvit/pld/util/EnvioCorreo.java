package com.legvit.pld.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase de utiler&iacute;a para realizar env&iacute;os de mail.
 * 
 * @author aaviac
 * @version 1.0, 15102107
 */
public class EnvioCorreo {
	
	private String username;
	private String password;
	private String host;
	private String starttls;
	private String authentication;
	private boolean Debug;
	private String port;
	private Session session;
	
	/**
	 * Constructor de la clase;
	 * 
	 * @throws GeneralSecurityException
	 */
	public EnvioCorreo() throws GeneralSecurityException {
        try{
                username = PorpertiesLoaderUtil.getInstance().getValue("pld.mail.email");
                password = PorpertiesLoaderUtil.getInstance().getValue("pld.mail.password");
                host = PorpertiesLoaderUtil.getInstance().getValue("pld.mail.host");
                port = PorpertiesLoaderUtil.getInstance().getValue("pld.mail.port");
                starttls = PorpertiesLoaderUtil.getInstance().getValue("pld.mail.starttls");
                authentication = PorpertiesLoaderUtil.getInstance().getValue("pld.mail.auth");
               // Debug = "";

                Properties mailProps = new Properties();
                mailProps.put("mail.smtp.transport.protocol", "smtp");
                mailProps.put("mail.smtp.host", host);
                mailProps.put("mail.smtp.from", username);
                mailProps.put("mail.smtp.starttls.enable", starttls);
                mailProps.put("mail.smtp.port", port);
                mailProps.put("mail.smtp.auth", authentication);
                mailProps.put("mail.debug", "false");

                final PasswordAuthentication usernamePassword = new PasswordAuthentication(username, password);
                Authenticator auth = new Authenticator() {

                        protected PasswordAuthentication getPasswordAuthentication() {
                                return usernamePassword;
                        }
                };

                session = Session.getInstance(mailProps, auth);
                //session.setDebug(Debug);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Realiza el env&iacute;o de un mail con un archivo adjunto.
	 * 
	 * @param from
	 *            quien env&iacute;a el mail
	 * @param to
	 *            a quien se le enviar&aacute; el mail.
	 * @param filename
	 *            ruta del archivo log generado por el proceso batch.
	 */
	public void enviaMailAttachment(String from, String to, String filename) {
	    try {
	         Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	         message.setSubject("Proceso Batch Finalizado");
	         BodyPart messageBodyPart = new MimeBodyPart();

	         String texto = "<html><head>"
             + "<title></title>"
             + "</head>\n"
             + "<body>"
             + "</div><br>"
             + "<span style='color: #4a0061; font-family:arial;'>T&eacute;rmino de Ejecuci&oacute;n del Proceso Batch</span>"
             + "<p>&nbsp;</p>"
             + "<p><span style='color: #4a0061; font-family:arial;'>Le notificamos que el proceso Batch ha terminado, adjunto encontrar&aacute; el log de la ejecuci&oacute;n</span>"
             + "</p>"
             + "</body></html>";
	         
	         messageBodyPart.setText(texto);
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         //Attachment
	         messageBodyPart = new MimeBodyPart();
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);
	         message.setContent(multipart);
	         Transport.send(message);
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	}
}
