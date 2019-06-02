package codeTest.v1.socket.s01address;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetAddress {
	public static void main(String[] args) {
		try {
			Enumeration<NetworkInterface> inList = NetworkInterface.getNetworkInterfaces();
			if(inList != null){
				while(inList.hasMoreElements()){
					NetworkInterface in = inList.nextElement();
					System.out.println(in.getName() + ":");
					
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
}























