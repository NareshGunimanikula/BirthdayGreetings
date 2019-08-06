package birthday.wishes;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class BirthdayWishes {

	
	public static void main(String[] args) throws Exception { 
		  String name = null;
		  Date date = null;
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/birthdays","root","root");
//			System.out.println("Connection Successful");
			String query = "Select * from birthdays";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				int id = rs.getInt("id");
				name = rs.getString("Name");
				date = rs.getDate("DOB");
				
				System.out.format("%s, %s, %s\n", id, name,date);
			}
			st.close();
		}catch(Exception e) {
			System.out.println(e);
		}
			final String senderEmail = "gunimanikala@gmail.com";
			final String password = "Omnamashivaya@god";
			
			Properties properties = new Properties();
			properties.put("mail.smtp.auth","true");
			properties.put("mail.smtp.starttls.enable","true");
			properties.put("mail.smtp.host","smtp.gmail.com");
			properties.put("mail.smtp.port","587");
 

			// creating session object to get properties 
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(senderEmail, password);
				}
			}); 

			try
			{ 
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				   LocalDateTime now = LocalDateTime.now();
				   System.out.println(date);
				   
				   String format = dtf.format(now);
					System.out.println(format);
					
				   String string = date.toString();
				   System.out.println(string);
				   
				if(format == string) {
					   System.out.println("success");
				   }
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress("gunimanikala@gmail.com")); 
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("gunimanikala@gmail.com, kbkteja5@gmail.com")); 
				message.setSubject("This is Suject"); 
//				message.setText(name + " Happy Birthday");
//				//message.
//				//message.setFileName("C:\\Users\\Naresh Gunimanikula\\Desktop\\wishes.png");
//				MimeMultipart multipart = new MimeMultipart("related");
//				BodyPart msgBodyPart = new MimeBodyPart();
//				msgBodyPart = new MimeBodyPart();
//				DataSource fds = new FileDataSource("C:\\Users\\Naresh Gunimanikula\\Desktop\\wishes.png");
//				msgBodyPart.setDataHandler(new DataHandler(fds));
//				msgBodyPart.setHeader("Content-ID", "<image>");
//				multipart.addBodyPart(msgBodyPart);
//				message.setContent(multipart);
				MimeMultipart multipart = new MimeMultipart("related");
		         // first part (the html)
		         BodyPart messageBodyPart = new MimeBodyPart();
		         String htmlText ="<H6>Many more Happy returns of the day </H6>" + name + "<img src=\"cid:image\">";
		         messageBodyPart.setContent(htmlText, "text/html");
		         multipart.addBodyPart(messageBodyPart);
		         messageBodyPart = new MimeBodyPart();
		         DataSource fds = new FileDataSource(
		            "C:\\Users\\Naresh Gunimanikula\\Desktop\\wishes.png");
		         messageBodyPart.setDataHandler(new DataHandler(fds));
		         messageBodyPart.setHeader("Content-ID", "<image>");
		         multipart.addBodyPart(messageBodyPart);
		         message.setContent(multipart);
				Transport.send(message); 
				System.out.println("Mail successfully sent"); 
			} 
			catch (MessagingException mex) 
			{ 
				mex.printStackTrace(); 
			} 
		} 
		} 
	
