package com.monitoring.server.storage;

public class AgentHealthChecker implements Runnable {
	private DataManager dataManager;
	public AgentHealthChecker(DataManager dataManager) {
		super();
		this.dataManager = dataManager;
	}
	@Override
	public void run() {
		while (true) {
			dataManager.checkOfflineAgents();
			try {
				Thread.sleep(dataManager.AGENT_TIMEOUT_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
