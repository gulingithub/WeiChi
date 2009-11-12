package com.game.weichi;

import java.net.*;
import java.io.*;

import android.util.*;

public class Network {
	Socket sock;
	InetSocketAddress serverAddr;
	BufferedReader reader;
	PrintWriter writer;
	
	static final int NET_FAILED = 0;
	static final int NET_CONNECTED = 1;
	static final int NET_MATCHED = 2;
	static final int NET_MATCHNUM = 10;
	static final int NET_CONNECTNUM = 10;
	
	public Network(){
		serverAddr = new InetSocketAddress("128.143.67.102", 5000);
		sock = new Socket();
		
		Log.i("INFO", "in Network()");
		
	}
	
	public Network(String host, int port){
		serverAddr = new InetSocketAddress(host, port);
		sock = new Socket();
	}
	
	public int connect(){
		try {
			
			Log.i("INFO", "in connect()");
			
			sock.connect(serverAddr);
			
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new PrintWriter(sock.getOutputStream());
			
			String	msg = reader.readLine();
			
			Log.i("INFO", msg);
			if (Integer.parseInt(msg)== NET_CONNECTED)
				return NET_CONNECTED;
			else if (Integer.parseInt(msg)==NET_MATCHED)
				return NET_MATCHED;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NET_FAILED;
	}
	
	public int match(){

		try {
			String msg = reader.readLine();
			if(Integer.parseInt(msg)== NET_MATCHED)
				return NET_MATCHED;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		return NET_FAILED;
		
	}
	
	public void sendRemoteMove(GoMove gm){
		writer.println(""+gm);
		writer.flush();
	}
	
	public GoMove getRemoteMove(){
		GoMove gm = null;
		try {
			String str = reader.readLine();
			gm = parseMove(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gm;
	}

	private GoMove parseMove(String str) throws NumberFormatException {
		// TODO Auto-generated method stub
		String[] move = str.split(" ");
		int x = Integer.parseInt(move[0]);
		int y = Integer.parseInt(move[1]);
		return new GoMove(x, y);
	}

}
