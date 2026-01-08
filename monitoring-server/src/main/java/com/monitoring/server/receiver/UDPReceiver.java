package com.monitoring.server.receiver;

import java.io.ByteArrayInputStream;

import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.monitoring.server.storage.DataManager;

import pourelle.SystemMetrics;

public class UDPReceiver implements Runnable {
    
    private int port;
    private DataManager dataManager;
    
    public UDPReceiver(int port, DataManager dataManager) {
        this.port = port;
        this.dataManager = dataManager;
    }
    
    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(port)){
 
            System.out.println("UDP Receiver écoute sur le port " + port);
            while (true) {
            	byte[] buffer = new byte[4096];
            	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            	socket.receive(packet);
            	String ip = packet.getAddress().getHostAddress();     
            	int dataLength = packet.getLength();
            	ByteArrayInputStream bais = new ByteArrayInputStream(buffer, 0, dataLength);
                ObjectInputStream ois = new ObjectInputStream(bais);
                SystemMetrics metrics = (SystemMetrics) ois.readObject();
                dataManager.addOrUpdateAgent(metrics.getAgentId(), metrics, ip);
                //System.out.println("UDP " + metrics.getTimestamp()/1000 + " de " + metrics.getAgentId());
            }
            
        } catch (Exception e) {
        	System.err.println("Erreur dans UDPReceiver (continuation...) : " +
        e.getClass().getSimpleName() + ": " + e.getMessage());    
        }
    }
}
