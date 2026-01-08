package com.monitoring.server.storage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import pourelle.Agent;
import pourelle.Alert;
import pourelle.SystemMetrics;

public class DataManager{
	public static int collectionIntervalSeconds;
	public static double cpu_Limit;
	public static double memory_Limit;
	public long AGENT_TIMEOUT_MS = (collectionIntervalSeconds)*(3000/2);
	private Map<String,Long> lasts = new ConcurrentHashMap<>();
	
	private Map<String, Agent> agents = new ConcurrentHashMap<>();
	private List<Alert> alerts = new CopyOnWriteArrayList<>();
	private Map<String, List<SystemMetrics>> metricsHistory = new ConcurrentHashMap<>();
	
	public void addOrUpdateAgent(String agentId, SystemMetrics metrics, String ipAddress) {
	    Agent agent = agents.get(agentId);
	    
	    if (agent != null) {
	        agent.setLastMetrics(metrics);
	    } else {
	        agent = new Agent(agentId, ipAddress, metrics);
	        System.out.println(agentId +" is Connected.");
	        agents.put(agentId, agent);
	    }
	    
	    if (metrics.getCpuUsage() > cpu_Limit || metrics.getMemoryUsage() > memory_Limit) {
	        agent.setStatus("ALERT");
	    } else {
	        agent.setStatus("ONLINE");
	    }
	    addToHistory(agentId, metrics);
	}
	
	public void checkOfflineAgents() {
		long now = System.currentTimeMillis();
		for (Agent agent : agents.values()) {
			if (now-agent.getLastUpdate()>AGENT_TIMEOUT_MS) {
				agent.setStatus("OFFLINE");
			}
		}
	}
	
	public void addAlert(String agentId, Alert alert) {
		Long last = lasts.get(agentId);
		if (last == null) {
			alerts.add(alert);
			lasts.put(agentId,alert.getTimestamp());
			return;
		}
		long interval = alert.getTimestamp()-last;
		if (interval==(collectionIntervalSeconds)*(1000)) {
			lasts.put(agentId,alert.getTimestamp());
		}
		else {
			alerts.add(alert);
			lasts.put(agentId,alert.getTimestamp());
		}
	}
	public List<Agent> getAllAgents(){
		List<Agent> list = new CopyOnWriteArrayList<>();
		for (Agent agent : agents.values()) {
			list.add(agent);
		}
		return list;
	}
	public List<Alert> getAllAlerts(){
		return alerts;
	}
	public List<SystemMetrics> getMetricsHistory(String agentId){
		return metricsHistory.get(agentId);
	}
	private void addToHistory(String agentId, SystemMetrics metrics) {
		List<SystemMetrics> list = metricsHistory.get(agentId);
		if (list == null) {
			list = new CopyOnWriteArrayList<>();
		}
		list.add(metrics);
		metricsHistory.put(agentId, list);
	}
}
