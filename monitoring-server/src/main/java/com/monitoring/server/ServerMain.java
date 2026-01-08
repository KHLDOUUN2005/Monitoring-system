package com.monitoring.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.monitoring.server.receiver.TCPAlertReceiver;
import com.monitoring.server.receiver.UDPReceiver;
import com.monitoring.server.rmi.MonitoringServiceImpl;
import com.monitoring.server.storage.AgentHealthChecker;
import com.monitoring.server.storage.DataManager;

public class ServerMain {
    public static void main(String[] args) {
    	DataManager.collectionIntervalSeconds = 2;
    	DataManager.cpu_Limit = 65;
    	DataManager.memory_Limit = 90;
        DataManager dataManager = new DataManager();
        
        int udpPort = 9000;  
        int tcpPort = 8000;
        int rmiPort = 7000;

        Thread udpThread = new Thread(new UDPReceiver(udpPort, dataManager));
        Thread tcpThread = new Thread(new TCPAlertReceiver(tcpPort, dataManager));
        Thread cheking = new Thread(new AgentHealthChecker(dataManager));
        
        udpThread.start();
        tcpThread.start();
        cheking.start();

        try {
        	
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            
            MonitoringServiceImpl server = new MonitoringServiceImpl(dataManager);
            registry.rebind("MonitoringService", server);
            System.out.println("Serveur central démarré !");
            System.out.println("  UDP (métriques) : port " + udpPort);
            System.out.println("  TCP (alertes)  : port " + tcpPort);
            System.out.println("  RMI             : rmi://localhost:" + rmiPort + "/MonitoringService");
            
            System.out.println("**********\n**********\n**********\n");
            
        } catch (Exception e) {
            System.err.println(" Erreur RMI : " + e.getMessage());
            e.printStackTrace();
        }
    }
}