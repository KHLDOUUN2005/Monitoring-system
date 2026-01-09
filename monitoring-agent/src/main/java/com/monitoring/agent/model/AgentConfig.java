package com.monitoring.agent.model;

public class AgentConfig {
	private String agentId;
	private String serverHost;
	private int serverUdpPort;
	private int serverTcpPort;
	private int collectionIntervalSeconds;
	private double cpuAlertThreshold;
	private double memoryAlertThreshold;
	public AgentConfig(String agentId, String serverHost, int serverUdpPort, int serverTcpPort,
			int collectionIntervalSeconds, double cpuAlertThreshold, double memoryAlertThreshold) {
		super();
		this.agentId = agentId;
		this.serverHost = serverHost;
		this.serverUdpPort = serverUdpPort;
		this.serverTcpPort = serverTcpPort;
		this.collectionIntervalSeconds = collectionIntervalSeconds;
		this.cpuAlertThreshold = cpuAlertThreshold;
		this.memoryAlertThreshold = memoryAlertThreshold;
	}
	public String getAgentId() {
		return agentId;
	}
	public String getServerHost() {
		return serverHost;
	}
	public int getServerUdpPort() {
		return serverUdpPort;
	}
	public int getServerTcpPort() {
		return serverTcpPort;
	}
	public int getCollectionIntervalSeconds() {
		return collectionIntervalSeconds;
	}
	public double getCpuAlertThreshold() {
		return cpuAlertThreshold;
	}
	public double getMemoryAlertThreshold() {
		return memoryAlertThreshold;
	}
	
	
}
