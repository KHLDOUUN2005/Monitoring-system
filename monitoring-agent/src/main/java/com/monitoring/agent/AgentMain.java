package com.monitoring.agent;

import com.monitoring.agent.collector.MetricsCollector;
import com.monitoring.agent.model.AgentConfig;
import com.monitoring.agent.sender.TCPAlertSender;
import com.monitoring.agent.sender.UDPSender;

import pourelle.SystemMetrics;

public class AgentMain {
	public static void main(String[] args) {
		String agentId = "AGENT-001";
		String serverHost = "localhost";
		int serverUdpPort = 9000;
		int serverTcpPort = 8000;
		int collectionIntervalSeconds = 2;
		double cpuAlertThreshold = 65;
		double memoryAlertThreshold = 90; 
		
		AgentConfig config = new AgentConfig(agentId, serverHost, serverUdpPort, 
				serverTcpPort, collectionIntervalSeconds, cpuAlertThreshold,
				memoryAlertThreshold);
		SystemMetrics sysMet;
		MetricsCollector metColl = new MetricsCollector();
		UDPSender udp = new UDPSender();
		TCPAlertSender tcp = new TCPAlertSender();
		
		System.out.println("Agent " + agentId + " démarré...");
		System.out.println("Serveur: " + serverHost);
		System.out.println("UDP Port: " + serverUdpPort + " | TCP Port: " + serverTcpPort);
		System.out.println("Intervalle: " + collectionIntervalSeconds + " secondes\n");
		
		while (true) {
			sysMet = metColl.collectMetrics(agentId);
			udp.sendMetrics(sysMet, serverHost, serverUdpPort);
			tcp.sendAlert(sysMet, config);
			try {
				Thread.sleep(collectionIntervalSeconds*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
