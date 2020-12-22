// Java implementation for JobSeeker 
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

// JobSeeker class 
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
				
				// If JobSeeker sends exit,close this connection 
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
} 
