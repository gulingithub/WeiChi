package com.game.weichi;


import java.net.*;
import java.io.*;

public class Network {

	ServerSocket server;
	Socket client1;
	Socket client2;
	int port;
	
	BufferedReader reader1;
	BufferedReader reader2;
	
	PrintWriter writer1;
	PrintWriter writer2;
	
	static final int NET_FAILED = 0;
	static final int NET_CONNECTED = 1;
	static final int NET_MATCHED = 2;
	static final int NET_MATCHNUM = 10;
	static final int NET_CONNECTNUM = 10;
	
	public Network() {
		port = 5000;
		try {
			server = new ServerSocket(port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Network(int port){
		this.port = port;
		try {
			server = new ServerSocket(port);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void waitConnect(){
		try {
			System.out.println("Starting to listen...");
			
			client1 = server.accept();
			writer1 = new PrintWriter(client1.getOutputStream());
			reader1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			System.out.println("Having one player...");
			Thread.sleep(10000);
			writer1.println(NET_CONNECTED);
			writer1.flush();
			
			client2 = server.accept();
			writer2 = new PrintWriter(client2.getOutputStream());
			reader2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			System.out.println("Having another player...");
			Thread.sleep(10000);
			writer1.println(NET_MATCHED);
			writer1.flush();
			writer2.println(NET_MATCHED);
			writer2.flush();
			
			System.out.println("Successfully match two players!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void go(){
		while(true){
			try {
				String sMove1 = reader1.readLine();
				System.out.println(sMove1);
				writer2.println(sMove1);
				writer2.flush();
				String sMove2 = reader2.readLine();
				System.out.println(sMove2);
				writer1.println(sMove2);
				writer1.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
