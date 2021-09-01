

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;



public class SSLSocketClient {

	public static void main(String[] args) throws Exception {

		Properties systemProps = System.getProperties();

		systemProps.put("javax.net.ssl.trustStore", "keystore.ImportKey");

		try {
			SSLSocketFactory factory = getSSLSocketFactory("TLS");
			ServerSocket welcomeSocket = new ServerSocket(8888);
			
			SSLSocket socket = (SSLSocket) factory.createSocket("192.168.0.14", 443);			
			Socket connectionSocket = welcomeSocket.accept();
			socket.startHandshake();	
		
				
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			BufferedReader inFromClient2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			DataOutputStream out2 = new DataOutputStream(connectionSocket.getOutputStream());
			
			String str;
			String response;
		while((str = inFromClient.readLine()) != null ) {	
			out.print(str + '\n');
			out.flush();
			
			response = inFromClient2.readLine();
			out2.writeBytes(response + "\n");
			
			if (out.checkError() ) {
				System.out.println("SSLSocketClient:  java.io.PrintWriter error");
				break;
			}
		}
			out.close();
			socket.close();
			welcomeSocket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static SSLSocketFactory getSSLSocketFactory(String type) {
		if (type.equals("TLS")) {
			SocketFactory ssf = null;
			try {
				SSLContext ctx;
				KeyManagerFactory kmf;
				KeyStore ks;
				char[] passphrase = "importkey".toCharArray();

				ctx = SSLContext.getInstance("TLS");
				kmf = KeyManagerFactory.getInstance("SunX509");
				ks = KeyStore.getInstance("JKS");

				ks.load(new FileInputStream("keystore.ImportKey"), passphrase);
				kmf.init(ks, passphrase);

				ctx.init(kmf.getKeyManagers(), null, null);

				ssf = ctx.getSocketFactory();
				return (SSLSocketFactory) ssf;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return (SSLSocketFactory) SSLSocketFactory.getDefault();
		}
		return null;
	}
}
