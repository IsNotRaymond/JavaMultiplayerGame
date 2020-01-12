package com.art.game.net.packet;

import com.art.game.net.GameClient;
import com.art.game.net.GameServer;

public abstract class Packet {
	
	public static enum PacketTypes {
		INVALIDO(-1), LOGIN(00), DESCONECTAR(01), MOVER(02); 
		
		private int packetID;
		
		private PacketTypes(int id) {
			this.packetID = id;
		}
		
		public int getId() {
			return packetID;
		}
	}
	
	public byte packetID;
	
	public Packet(int packetID) {
		this.packetID = (byte) packetID;
	}
	
	public abstract void escrever(GameClient client);
	
	public abstract void escrever(GameServer server);
	
	public abstract byte[] getData();
	
	public String lerDados(byte[] data) {
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	public static PacketTypes verificarPacket(String packetId) {
		try {
			return verificarPacket(Integer.parseInt(packetId));
		} catch(NumberFormatException e) {
			return PacketTypes.INVALIDO;
		}
	}
	
	public static PacketTypes verificarPacket(int id) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getId() == id) {
				return p;
			}
		}
		
		return PacketTypes.INVALIDO;
	}
}
