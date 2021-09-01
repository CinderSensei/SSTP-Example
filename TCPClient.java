

import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String args[]) throws Exception {
		
		String sentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 8888);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
	
			
			
		
			while(true) {
			System.out.println("please enter your words:");
			sentence = inFromUser.readLine();
			
			if(sentence.equals("!quit") || sentence.equals("!QUIT")) {
				break;}
			
			outToServer.writeBytes(sentence + '\n');
			String response = inFromServer.readLine();
			System.out.println("Response:" + response +"\n");
			
			}
		
	}

}

