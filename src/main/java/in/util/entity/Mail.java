package in.util.entity;


import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;

import in.util.UtilObjects;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class Mail {
	
	@Autowired
	Logger Log;
	@Resource(name="mail-config")
	private  Map<String, String> mailConfig;

    public Logger getLog() {
        return Log;
    }

    public Map<String, String> getMailConfig() {
        return mailConfig;
    }

    public static String getHostName() {
        return hostName;
    }

    public static String getPort() {
        return port;
    }

    public static String getFromEmailId() {
        return FromEmailId;
    }

    public static String getPassword() {
        return password;
    }
	
	private static String hostName;
	private static String port;
	private static String FromEmailId;
	private static String password;
	
	private static String FROMLepmpaServer;
	private static String ToFeedBackAdmin;
	
	 
	//private String subject=Strings.emailSubject;
	
	

//	static
//	{
//		Properties prop = new Properties();
//		InputStream input = null;
//
//		try {
//
//			//input = new FileInputStream("classpath:mailConfig.properties");
//			
//			// load a properties file
//			//prop.load(input);
//                        
////			hostName=prop.getProperty("hostName");
////			port=prop.getProperty("port");
////			FromEmailId=prop.getProperty("FromEmailId");
////			password=prop.getProperty("password");
//
//                        hostName=mailConfig.get("hostName");
//                         port=mailConfig.get("port");
//                         FromEmailId=mailConfig.get("FromEmailId");
//                          password=mailConfig.get("password");
//                         
//                        
//                        System.out.println("port "+port+"hostName "+hostName+" From Email Id "+FromEmailId);
//			setFROMLepmpaServer(prop.getProperty("FROMLepmpaServer"));
//			   setToFeedBackAdmin(prop.getProperty("ToFeedBackAdmin"));
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}  finally {
//				if (input != null) {
//					try {
//						input.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//		}
//	
	
	
	public HashMap<String, String> sendSimpleMail(String To,String message,String subject) throws Exception 
	{
		
		HashMap<String, String> result=new HashMap<String, String>();
    	
		Email email = new SimpleEmail();
		try {
                    
                    System.out.println("mailconfig :: "+mailConfig);
			email.setHostName(mailConfig.get("hostName"));
			Integer Iport=Integer.parseInt(mailConfig.get("port"));
			email.setSmtpPort(Iport.intValue());
			email.setAuthenticator(new DefaultAuthenticator(mailConfig.get("FromEmailId"),mailConfig.get("password")));
			email.setSSL(true);
			email.setFrom(mailConfig.get("FromEmailId"));
			email.setSubject(subject);
			email.setMsg(message);
			System.out.println("sendign mail to "+To);
			email.addTo(To);
			email.send();
		} catch (EmailException ex) {
			Log.info("MAil.Java Exception "+ex.getMessage());
        	Log.info("mail exception  "+ ex);
        	ex.printStackTrace();
            System.out.println("The email was not sent.");
            Log.info("The email was not sent.");
            message=message+"The email was not sent.";
            System.out.println("Error message: " + ex.getMessage());
            message=message+"Error message: " + ex.toString();
            result.put("mailStatus", "N");
            result.put("mailMessage", Strings.Mailfailed+"message :: "+ex.getMessage());
            
			ex.printStackTrace();
			throw new Exception("failed to send Email");
		} finally
        {
           
        }
		return result;
	}



	public static String getFROMLepmpaServer() {
		return FROMLepmpaServer;
	}



	public static void setFROMLepmpaServer(String fROMLepmpaServer) {
		FROMLepmpaServer = fROMLepmpaServer;
	}



	public static String getToFeedBackAdmin() {
		return ToFeedBackAdmin;
	}



	public static void setToFeedBackAdmin(String toFeedBackAdmin) {
		ToFeedBackAdmin = toFeedBackAdmin;
	}
	
	
	
}
