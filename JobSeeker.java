// Java implementation for a client 
// Save file as Client.java 
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

// Client class 
public class JobSeeker 
{ 
	public static void main(String[] args) throws IOException 
	{ 
	
		try
		{ 
			Scanner scn = new Scanner(System.in); 
			
			// getting localhost ip 
			InetAddress ip = InetAddress.getByName("localhost"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	
			// the following loop performs the exchange of 
			// information between client and client handler 
			while (true) 
			{ 
				System.out.println(dis.readUTF()); 
				String tosend = scn.nextLine(); 
				dos.writeUTF(tosend); 
				
				// If client sends exit,close this connection 
				// and then break from the while loop 
				if(tosend.equals("Exit")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 
				
				// closing connection as job rejected by Jobcreater 
				String received = dis.readUTF(); 
				System.out.println(received); 
				if(received.equals("Job Application Rejected")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				}
				System.out.println(dis.readUTF());
				String job = scn.nextLine();
				char jobNum = job.charAt(0);
				boolean isOnline;
				String ipAddr;
				int prt;			
				
				switch(jobNum){
					case 1 :
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();	 
							if(Character.isDigit(ipAddr.charAt(0))){
								if(InetAddress.getByName(ipAddr).isReachable(5000))
									isOnline = true;
								else 
									isOnline = false;
								} else {
									if(InetAddress.getByName(ipAddr).isReachable(5000)){
										isOnline = true;
									} else {
										isOnline = false;
									}

								}
								dos.writeBoolean(isOnline);
								break;
					case 2 : 
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();
							if (InetAddress.getByName(ipAddr).isReachable(prt)) {
								isOnline = true;
							} else{
								isOnline = false;
							}
							dos.writeBoolean(isOnline);
							break;
					case 3 : 
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();
                        	tcpAttack(ipAddr, prt);
                                tosend = " Job Done.";
                                dos.writeUTF(tosend);
                                break;
					case 4 :
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();							
                        	icmpAttack(ipAddr);                            
                                tosend = " Job Done.";
                                dos.writeUTF(tosend);
                                break;
                    case 5 :
                    		break;
                    case 6 :
                    		String jobSeekerIp = (InetAddress.getLocalHost().getHostAddress()).trim();//JobSeeker IP address
        					NetworkInterface jobSeekerNet = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        					//convert to hexadecimal MAC address string
        					byte[] nAddr = jobSeekerNet.getHardwareAddress();
        					String[] hex = new String[nAddr.length];
        					for (int x = 0; x < nAddr.length; x++) {
            				hex[x] = String.format("%02x", nAddr[x]);
        					}
        					String jobSeekerMac = String.join("-", hex);
       
        					System.out.println("All JobSeekers are on the same device, and therefore the same LAN, as the JobCreator.");
        					System.out.println("The JobSeeker has IP Address " + jobSeekerIp + " aand MAC Address " + jobSeekerMac);
        					//get subnet of seekerIp
        					String subnet = jobSeekerIp.substring(0, jobSeekerIp.lastIndexOf("."));
        					spyNbrs(subnet);//spy on neighbors
    
					default : 		

							}

			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close(); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	}
	public static void spyNbrs(String lan) throws IOException {
        for (int i = 1; i < 255; i++) {
            String hostIp = lan + "." + i;//get ip address
            if (InetAddress.getByName(hostIp).isReachable(5000)) {
                System.out.println("IP Address " + hostIp + " is a live host the same LAN as JobSeeker.");
            }
        }
    } 
} 
