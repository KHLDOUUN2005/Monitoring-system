package com.monitoring.client;

import javax.swing.SwingUtilities;

import com.monitoring.client.ui.MainFrame;

public class ClientMain {
	
	public static void main(String[] args) {		
		SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
	}
}
