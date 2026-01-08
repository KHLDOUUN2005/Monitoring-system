package pourelle;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MonitoringService extends Remote{
	public List<Agent> getAllAgents() throws RemoteException;
	public List<Alert> getAllAlerts() throws RemoteException;
	public List<SystemMetrics> getMetricsHistory(String agentId) throws RemoteException;
	
}
