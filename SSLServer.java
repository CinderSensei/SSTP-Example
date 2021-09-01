

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;

public class SSLServer {

	public static void main(String[] args) throws Exception {

		ServerSocketFactory ssf = SSLServer.getServerSocketFactory("TLS");
		ServerSocket ss = ssf.createServerSocket(443);
		Socket clientSocket = new Socket("localhost", 8888);
	
		
		Socket s = ss.accept();
		System.out.printf("ip:"+s.getRemoteSocketAddress());
	
		
		
		BufferedReader bR = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dOS = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader bR2 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter dOS2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
		
		String line;
		String response;
		
		while((line = bR.readLine()) != null) {
		
		dOS.writeBytes(line + "\n");
		
		response = bR2.readLine();
		dOS2.print(response + "\n");
		dOS2.flush();
		
		}
		
		
		

		

		s.close();

		ss.close();
		clientSocket.close();
		
		dOS.close();

	}

	private static ServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
		    SSLServerSocketFactory ssf = null;
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

		        ssf = ctx.getServerSocketFactory();
		        return ssf;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		} else {
		    return ServerSocketFactory.getDefault();
		}
		return null;
        }
}
