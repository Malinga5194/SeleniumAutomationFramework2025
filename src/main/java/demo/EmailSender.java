package demo;

import java.io.File;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.PasswordAuthentication;

public class EmailSender {

	public static void main(String[] args) {
		final String senderEmail="malinga5194@gmail.com";
		final String appPassword="bskjslrynzsxxhiq";
		final String recipientEmail="dsmperera@gmail.com";
		//SMTP server properties
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		
		//Create a session with authentication
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, appPassword);
			}
		
		});
		session.setDebug(true);
		
		try {
			//Create email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("Test Email From QA Automation");
			//message.setText("Hello \n This is a test email from Java \n Regards,\nQA Team");	
			
			//Email Body Part
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText("Hello \n This is a test email from Java \n Regards,\nQA Team");
			
			//Attachment Part
			MimeBodyPart attachmentPart = new MimeBodyPart();
			String filePath = System.getProperty("user.dir")+"/reports/ExtentReport.html";
			System.out.println("Attachmnet path is - "+filePath);
			attachmentPart.attachFile(new File(filePath));
			
			//Combine body and attachment parts
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(attachmentPart);
			message.setContent(multipart);
			
			//Send email
			Transport.send(message);
			System.out.println("Email sent successfully with attachment!");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
