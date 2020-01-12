package view;
// Java program to find IP address of your computer 
// java.net.InetAddress class provides method to get 
// IP of any host name 
import java.net.*; 
import java.io.*; 
import java.util.*; 
import java.net.InetAddress; 

public class GetIPAddress {
    public static void main(String args[]) throws Exception 
    { 
//        // Returns the instance of InetAddress containing 
//        // local host name and address 
//        InetAddress localhost = InetAddress.getLocalHost(); 
//        System.out.println("System IP Address : " + 
//                      (localhost.getHostAddress()).trim()); 
//  
//        // Find public IP address 
//        String systemipaddress = ""; 
//        try
//        { 
//            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
//  
//            BufferedReader sc = 
//            new BufferedReader(new InputStreamReader(url_name.openStream())); 
//  
//            // reads system IPAddress 
//            systemipaddress = sc.readLine().trim(); 
//        } 
//        catch (Exception e) 
//        { 
//            systemipaddress = "Cannot Execute Properly"; 
//        } 
//        System.out.println("Public IP Address: " + systemipaddress +"\n"); 
//    } 
//} 
    	
    	//maybe store mac address in the local database (sqlite) and keep saved weather info based off that
    InetAddress ip;
	try {
			
		ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());
		
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			
		byte[] mac = network.getHardwareAddress();
			
		System.out.print("Current MAC address : ");
			
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		System.out.println(sb.toString());
			
	} catch (UnknownHostException e) {
		
		e.printStackTrace();
		
	} catch (SocketException e){
			
		e.printStackTrace();
			
	}
	    
   }

}


