import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Send_Email {
	
	private static Desktop desktop;

	public boolean sendToSt(String compName,String stName,int rank,int id) throws URISyntaxException, IOException {
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			compName = compName.replace(" ", "%20");
			String subject = "Congratulation%20on%20achieving%20" + rank +"%20place%20in%20" + compName;
			String message = "Dear "+ stName +",\r\n"
					+ "\r\n"
					+ "Conguratulation on your achievement in " + compName +". This achievement is deeply appreciated by the unversity and we will announce it in the approbrite medias.\r\n"
					+ "\r\n"
					+ "In case you have Photos you want to share with the news post, reply to this email with the photos.\r\n"
					+ "\r\n"
					+ "Regards and Congrats,\r\n"
					+ "KFUPM News Team";
			message = message.replace(" ", "%20").replace("\r\n", "%0d%0a");
			
			URI mailto = new URI("mailto:s" + id + "@kfupm.edu.sa,?body="+ message + "&subject=" + subject);
			desktop.mail(mailto);
			return true;			
		} 
		else 	  
			return false;			
	}
	
	public boolean sendToTeam(String compName,String teamName,int rank,int[] ides) throws URISyntaxException, IOException {
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			compName = compName.replace(" ", "%20");
			String subject = "Congratulation%20on%20achieving%20" + rank +"%20place%20in%20" + compName;
			String emails = "";
			for(int i = 0 ; i < ides.length;i++) 
				emails = emails + "s" + ides[i] + "@kfupm.edu.sa,";
			
			String message = "Dear "+ teamName +",\r\n"
					+ "\r\n"
					+ "Conguratulation on your achievement in " + compName +". This achievement is deeply appreciated by the unversity and we will announce it in the approbrite medias.\r\n"
					+ "\r\n"
					+ "In case you have Photos you want to share with the news post, reply to this email with the photos.\r\n"
					+ "\r\n"
					+ "Regards and Congrats,\r\n"
					+ "KFUPM News Team";
			message = message.replace(" ", "%20").replace("\r\n", "%0d%0a");
			
			URI mailto = new URI("mailto:" + emails + "?body="+ message +"&subject=" + subject);
			desktop.mail(mailto);
			return true;			
		} 
		else 	  
			return false;			
	}


	
}
