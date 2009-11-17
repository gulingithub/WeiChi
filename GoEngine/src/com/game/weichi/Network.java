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
	
	boolean playComputer;
	int boardSize;
	GoPlayer gp;
	
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
			Thread.sleep(5000);
			writer1.println(NET_CONNECTED);
			writer1.flush();
			
			String msg = reader1.readLine();
			System.out.println(msg);
			String s[]=msg.split(" ");
			if(s[0].equals("true")){
				playComputer = true;
			}else
				playComputer = false;
			boardSize = Integer.parseInt(s[1]);
			
			System.out.println(""+playComputer);
			
			if (!playComputer) {
				client2 = server.accept();
				writer2 = new PrintWriter(client2.getOutputStream());
				reader2 = new BufferedReader(new InputStreamReader(client2
						.getInputStream()));
				System.out.println("Having another player...");
				Thread.sleep(5000);
				writer1.println(NET_MATCHED);
				writer1.flush();
				writer2.println(NET_MATCHED);
				writer2.flush();
			}
			
			
			System.out.println("Successfully match two players!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void goPlayer(){
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
	
	public void goComputer(GoPlayer gp){
		this.gp = gp;
		while(true){
			GoMove gm1 = getRemoteMove();
			GoMove gm2 = this.gp.receiveMoveAndFindBestMove(gm1);
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			sendRemoteMove(gm2);
		}
	}
	
	public void sendRemoteMove(GoMove gm){
		writer1.println(""+gm);
		writer1.flush();
	}
	
	public GoMove getRemoteMove(){
		GoMove gm = null;
		try {
			String str = reader1.readLine();
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
	
	public int getBoardSize(){
		return boardSize;
	}
	
	public boolean getGameMode(){
		return playComputer;
	}
}
