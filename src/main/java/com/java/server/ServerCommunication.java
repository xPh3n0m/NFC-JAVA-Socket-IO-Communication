package com.java.server;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ServerCommunication {
	
	private Socket socket;
	private String sessionId;
	
	public ServerCommunication(String sessionId) throws URISyntaxException, JSONException {
		this.sessionId = sessionId;
		
		socket = IO.socket("http://ec2-54-200-101-206.us-west-2.compute.amazonaws.com:3000");
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

		  public void call(Object... args) {
			  System.out.println("Connected");
		  }

		}).on("message", new Emitter.Listener() {

		  public void call(Object... args) {
			  String message = (String)args[0];
			  System.out.println(message);
		  }

		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

		  public void call(Object... args) {
			  System.out.println("Disconnected");
		  }

		});
	}
	
	public void connect() {
		socket.connect();
		socket.emit("subscribe", sessionId);
	}
	
	public void sendConnectionMessage(byte[] uid) {		
		JSONObject obj = new JSONObject();
		try {
			obj.put("uid", uid);
			obj.put("room", sessionId);
			socket.emit("nfc_card_connected", obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendDisconnectionMessage(byte[] uid) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("uid", uid);
			obj.put("room", sessionId);
			socket.emit("nfc_card_disconnected", obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
