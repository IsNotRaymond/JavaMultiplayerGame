package com.art.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.art.game.Game;

public class GameServer extends Thread {
	private final int MAX_DATAGRAM_PACKET = 1024;
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;
	
	// private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	
	public GameServer(Game game) {
		this.game = game;
		
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			byte[] data = new byte[MAX_DATAGRAM_PACKET];
			DatagramPacket packet = new DatagramPacket(data, MAX_DATAGRAM_PACKET);
			
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			
			
			String message = new String(packet.getData());
			System.out.println("CLIENT ["+ packet.getAddress().getHostAddress() +":" +packet.getPort() + "]> " + new String(packet.getData()));
			
			if (message.trim().equalsIgnoreCase("ping")) {
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
			
		}
	}
	 /*
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String dataMessage = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(Integer.parseInt(dataMessage.substring(0, 2)));
		Packet packet = null;
		
		switch (type) {
			default:
			case INVALID:
				break;
			case LOGIN:
				packet = new Packet00Login(data);
				System.out.println("["+ address.getHostAddress() +":" + port + "]" + ((Packet00Login) packet).getUserName() + "has connected");
			
				PlayerMP player = new PlayerMP(((Packet00Login) packet).getUserName(), game.playerSpawn.getX(), game.playerSpawn.getY(), address, port);
				addConnection(player, ((Packet00Login) packet));
				
				if (player != null) {
					connectedPlayers.add(player);
					game.level.add(player);
					game.player = player;
				}
					
			case DISCONNECT:
				break;
		}
	}


	private void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		
		for (PlayerMP p : connectedPlayers) {
			if (player.getName().equalsIgnoreCase(p.getName())) {
				if (p.ipAddress == null) {
					p.ipAddress = player.ipAddress;
				}
				
				if (p.port == -1) {
					p.port = player.port;
				}
				
				alreadyConnected = true;
			} 
		}
		
		if (!alreadyConnected) {
			this.connectedPlayers.add(player);
			packet.writeData(this);
		}
	}
	*/

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : connectedPlayers) {
			sendData(data, p.ipAddress, p.port);
		}
		
	}
	*/
}
