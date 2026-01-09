package com.monitoring.agent.sender;

import java.io.PrintWriter;
import java.net.Socket;

import com.monitoring.agent.model.AgentConfig;

import pourelle.SystemMetrics;

public class TCPAlertSender {
	public void sendAlert(SystemMetrics metrics, AgentConfig config)  {
		try {
			boolean cpuAlert = metrics.getCpuUsage() > config.getCpuAlertThreshold();
			boolean memoryAlert = metrics.getMemoryUsage() > config.getMemoryAlertThreshold();
			
			if (!cpuAlert && !memoryAlert) {
			    return; 
			}
			String alertMessage = "ALERTE =>"+metrics.getAgentId()+"=> ";
			
			if (cpuAlert) {
			    alertMessage += "CPU="+String.format("%.2f", metrics.getCpuUsage())+"% ";
			}
			if (memoryAlert) {
			    alertMessage += "RAM="+String.format("%.2f", metrics.getMemoryUsage())+"% ";
			}
			
			Socket socket = new Socket(config.getServerHost(),config.getServerTcpPort());
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println(alertMessage);
			socket.close();
		}  catch (Exception e) {
            System.err.println("Erreur lors de l'envoi TCP : " + e.getMessage());
        }	
	}
}
