package com.monitoring.agent.sender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import pourelle.SystemMetrics;

public class UDPSender {
	public void sendMetrics(SystemMetrics metrics, String host, int port) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(metrics);
			oos.flush();
			byte[] data = baos.toByteArray();
			InetAddress address = InetAddress.getByName(host);
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
