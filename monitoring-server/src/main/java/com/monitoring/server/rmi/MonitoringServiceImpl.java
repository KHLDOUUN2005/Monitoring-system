package com.monitoring.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.monitoring.server.storage.DataManager;

import pourelle.Agent;
import pourelle.Alert;
import pourelle.MonitoringService;
import pourelle.SystemMetrics;

public class MonitoringServiceImpl extends UnicastRemoteObject implements MonitoringService{
	private DataManager dataManager;
	
	protected MonitoringServiceImpl() throws RemoteException {
		super();
	}
	
	public MonitoringServiceImpl(DataManager dataManager) throws RemoteException{
		this.dataManager = dataManager;
	}

	@Override
	public List<Agent> getAllAgents() throws RemoteException {
		return dataManager.getAllAgents();
	}

	@Override
	public List<Alert> getAllAlerts() throws RemoteException {
		return dataManager.getAllAlerts();
	}

	@Override
	public List<SystemMetrics> getMetricsHistory(String agentId) throws RemoteException {
		return dataManager.getMetricsHistory(agentId);
	}

}
