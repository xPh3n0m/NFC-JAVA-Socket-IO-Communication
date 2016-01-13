package com.kiwi.app;

import java.net.URISyntaxException;
import java.util.Scanner;

import org.json.JSONException;

import com.java.server.ServerCommunication;
import com.kiwi.nfc.NFCCommunication;

public class SimpleNFCApp {

	public static void main(String[] args) throws URISyntaxException, JSONException {
		System.out.print("Enter the session key provided by the browser app: ");
		
		Scanner scanner = new Scanner(System.in);
		String sessionId = scanner.nextLine();
		
        ServerCommunication serverComm = new ServerCommunication(sessionId);
        serverComm.connect();
        		
		NFCCommunication nfcComm = new NFCCommunication(serverComm);
		
		(new Thread(nfcComm)).start();
	}

}
