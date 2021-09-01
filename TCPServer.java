

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {

	public static void main(String args[]) throws Exception {

			ServerSocket welcomeSocket = new ServerSocket(8888);

			while (true) {
				
				Socket connectionSocket = welcomeSocket.accept();
				
				new Thread(new ServerHandler(connectionSocket)).start();

			}
			
			
	}
	

}


class ServerHandler implements Runnable {
	private Socket con;
	
	public ServerHandler(Socket s) {	
		
		con = s;

	}

	@Override
	public void run() {

		try {
			System.out.println("Incoming connection from: \n" + con.getRemoteSocketAddress());
			
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(con.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));
			
			String response = null;
			String str = null;
			while((str = inFromClient.readLine()) != null) {
				
			System.out.print(str + "\n");
			
			System.out.print("Write your response:\n");
			response = inFromServer.readLine();
			outToClient.writeBytes(response + "\n");
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
