package com.art.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.art.game.util.BinaryWriter;

public class Client {
	
	private final static byte[] PACKET_HEADER = new byte[] {0x40, 0x40};
	private final static byte PACKET_TYPE_CONNECT = 0x01;
	
	public enum Error {
		NONE, INVALID_HOST, SOCKET_EXCEPTION
	}

	private String ipAddress;
	private InetAddress serverAddress;
	private int port;
	
	private Error errorCode = Error.NONE;
	
	private DatagramSocket socket;
	
	
	/**
	 * @param host
	 * 			  Ex: 192.168.1.1:5000
	 */
	public Client(String host) {
		String[] pieces = host.split(":");
		
		if (pieces.length != 2) {
			errorCode = Error.INVALID_HOST;
			return;
		}
		
		ipAddress = pieces[0];
		
		try {
			port = Integer.parseInt(pieces[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			errorCode = Error.INVALID_HOST;
			
			return;
		}
		
	}
	
	/**
	 * 
	 * @param host 
	 * 			  Ex: 192.168.1.1
	 * @param port 
	 * 			  Ex: 5000
	 */
	public Client(String host, int port) {
		this.ipAddress = host;
		this.port = port;
	}
	
	public boolean connect() {
		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			e.getLocalizedMessage();
			errorCode = Error.INVALID_HOST;
			
			return false;
		}
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			errorCode = Error.SOCKET_EXCEPTION;
			
			return false;
		}
		sendConnectionPacket();
		// TODO: WAIT THE SERVER;
		return true;
	}
	
	public void send(byte[] data) {
		assert(socket.isConnected());
		
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void sendConnectionPacket() {
		BinaryWriter writer = new BinaryWriter();
		writer.write(PACKET_HEADER);
		writer.write(PACKET_TYPE_CONNECT);
		
		send(writer.getBuffer());
	}
	
	public Error getErrorCode() {
		return errorCode;
	}
	
}
