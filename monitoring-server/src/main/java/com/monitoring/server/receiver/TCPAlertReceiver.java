package com.monitoring.server.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.monitoring.server.storage.DataManager;

import pourelle.Alert;

public class TCPAlertReceiver implements Runnable{
	private int port;
    private DataManager dataManager;
    private ServerSocket serverSocket;
    
    public TCPAlertReceiver(int port, DataManager dataManager) {
        this.port = port;
        this.dataManager = dataManager;
    }

	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				String message = in.readLine();
				String[] mess = message.split("=>");
				Alert alert = new Alert(mess[1], message, System.currentTimeMillis());
	
				dataManager.addAlert(mess[1], alert);
				//System.out.println(" ALERT : " + message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
