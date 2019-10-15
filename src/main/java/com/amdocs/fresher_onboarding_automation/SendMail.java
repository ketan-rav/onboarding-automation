package com.amdocs.fresher_onboarding_automation;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class SendMail{
	
	public boolean send_welcome_mail(ArrayList<String> recipient_email) {
		
		Scanner pwdInput = new Scanner(System.in);
		String MAILER_PASS = pwdInput.next();
		String MAILER_HOST ="tlv.amdocs.com";
	
		int MAILER_PORT = 25;
		//String MAILER_PASS = "";
		String MAILER_USER = "ravketan";
		
		Properties props = new Properties();
		
	
		String msg = "Hi All,\n"+
	
	       "Welcome to Digital CC. Please find attached welcome kit that explains the following :\n"+
	       "	1.AD - New - Joiners - Welcome Kit – 1 :\n"+
	       "		a.	Get to know about Digital CC \n"+
	       "		b.	Bootcamp scheduled for Freshers and Laterals\n"+
	       "		c.	Technologies being used in CC\n"+
	       "		d.	Mandatory and Self Learning courses for Backend + UI + Amdocs Products\n"+
	       "		e.	Focal and FAQ\n"+
	       "	2.AD - New - Joiners - Welcome Kit – 2 :\n"+
	       "		a.	Digital Product Overview\n"+
	
	       "For any doubts please reach out to your mentors.";
		
	
		//Creating session object and passing props
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
	
		//creating an instance of MimeMessage and passing session object into it
		try {
			InternetAddress[] sendTo = new InternetAddress[recipient_email.size()];
			for (int i = 0; i < recipient_email.size(); i++) {
				System.out.println("Send to:" + recipient_email.get(i));
				sendTo[i] = new InternetAddress(recipient_email.get(i));
			}
			MimeMessage message = new MimeMessage(session);
			
			// Create a multiple part message for multiple body
			Multipart multipart = new MimeMultipart();
			
			//creating  of BodyPart instance for message body
			BodyPart messageBodyPart = new MimeBodyPart();
			BodyPart messageBodyPart1 = new MimeBodyPart();
			//passing message to be sent as text
			messageBodyPart.setText(msg);
			
			// adding text message as one body 
			multipart.addBodyPart(messageBodyPart);
		
			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "AD - New - Joiners - Welcome Kit - 1.pptx";
			
			messageBodyPart1 = new MimeBodyPart();
			// giving data source
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			
			String filename2 = "AD - New - Joiners - Welcome Kit - 2.pptx";
			
			// giving data source
			DataSource source1 = new FileDataSource(filename2);
			messageBodyPart1.setDataHandler(new DataHandler(source1));
			messageBodyPart1.setFileName(filename2);
			
			//adding attachment 
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(messageBodyPart1);
			// Send the complete message parts
			message.setContent(multipart);
			
			
			//setting the sender address
			message.setFrom("ravi.ketan2@amdocs.com");
			
			//setting receiver address
			message.setRecipients(Message.RecipientType.TO, sendTo);
			
			//setting subject for mail
			message.setSubject("A Test email ");
			
			//creating an Transport object
			Transport transport = session.getTransport("smtp");
			
			//connecting to service
			transport.connect(MAILER_HOST, MAILER_PORT, MAILER_USER, MAILER_PASS);
			
			//sending the message
			transport.sendMessage(message, message.getAllRecipients());
		  
			transport.close();
			return true;
		}
		
		catch (MessagingException e) {
			throw new RuntimeException(e);
		 }
	}

}