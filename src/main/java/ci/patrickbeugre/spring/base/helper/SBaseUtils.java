package ci.patrickbeugre.spring.base.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import ci.patrickbeugre.spring.base.helper.contrat.Response;

@Component
public class SBaseUtils {
	
	private Response response;
	@Autowired
	private ParamsUtils paramsUtils;
	
	private static final Logger slf4jLogger=LoggerFactory.getLogger(SBaseUtils.class);
	
	
	public static String encrypt(String str) throws Exception {
		 MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        byte[] hashedBytes = digest.digest(str.getBytes("UTF-8"));
	 
	        return convertByteArrayToHexString(hashedBytes);
	}
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
	
	public static String  generateAlphanumericCode(Integer nbreCaractere){
		String formatted = null;
		formatted = RandomStringUtils.randomAlphanumeric(nbreCaractere).toLowerCase();
		return formatted;
	}
	 public Response sendEmail(Map<String, String> from, List<Map<String, String>> toRecipients, String subject,String body, List<String> attachmentsFilesAbsolutePaths)
		{
			response=new Response();
			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
			
		    
		   String smtpServer = paramsUtils.getSmtpHost();
		    if(smtpServer != null )
		    {
		    	Boolean auth = false;
		    	javaMailSender.setHost(smtpServer);
		        javaMailSender.setPort(paramsUtils.getSmtpPort());
		        javaMailSender.setUsername(paramsUtils.getSmtpUsername());
			    javaMailSender.setPassword(paramsUtils.getSmtpPassword());
			    auth = true;
		        
		        javaMailSender.setJavaMailProperties(getMailProperties(paramsUtils.getSmtpHost(), auth));
			
			    MimeMessage message = javaMailSender.createMimeMessage();
			    try {
			        MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE);
			        
			    	//sender
			        	helper.setFrom(new InternetAddress(from.get("email"), from.get("user")));
			        //recipients
					List <InternetAddress> to = new ArrayList<InternetAddress>();
					for (Map<String, String> recipient : toRecipients)
						to.add(new InternetAddress(recipient.get("email"), recipient.get("user")));
			        helper.setTo(to.toArray(new InternetAddress[0]));
			        
			        //Subject and body
			        helper.setSubject(subject);
			        helper.setText(body);
			     // Attachments
					if (attachmentsFilesAbsolutePaths != null && !attachmentsFilesAbsolutePaths.isEmpty())
						for (String attachmentPath : attachmentsFilesAbsolutePaths) {
							File pieceJointe = new File(attachmentPath);
							FileSystemResource file = new FileSystemResource(attachmentPath);
							if (pieceJointe.exists() && pieceJointe.isFile()) {
								helper.addAttachment(file.getFilename(), file);
							}
						}
					     
					
			        javaMailSender.send(message);
			        response.setHasError(Boolean.FALSE);
			        
			        
			    } catch (MessagingException e) {
			        e.printStackTrace();
			        response.setHasError(Boolean.TRUE);
			    } catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					response.setHasError(Boolean.TRUE);
				} 
		    }
		    return response;
		}
		    private Properties getMailProperties(String host, Boolean auth) {
		        Properties properties = new Properties();
		        properties.setProperty("mail.transport.protocol", "smtp");
		        properties.setProperty("mail.smtp.auth", auth.toString());
		        properties.setProperty("mail.smtp.starttls.enable", "true");  
		        properties.setProperty("mail.smtp.starttls.required", "true");
		        properties.setProperty("mail.debug", "true");
		        if(host.equals("smtp.gmail.com"))
		        	properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		        return properties;
		    }
		    
		    public static boolean isValidEmail(String email){
		    	String regex = "^(.+)@(.+)$";
		    	Pattern pattern = Pattern.compile(regex);
		    	Matcher matcher = pattern.matcher(email);
		    	
		    	return matcher.matches();
		    }
		    
		    
		
		 

}
