package pourelle;

import java.io.Serializable;

public class SystemMetrics implements Serializable{
	private static final long serialVersionUID = 1L;
	private String agentId;
	private double cpuUsage;
	private double memoryUsage;
	private long timestamp;
	public SystemMetrics(String agentId, double cpuUsage,
			double memoryUsage,  long timestamp) {
		super();
		this.agentId = agentId;
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.timestamp = timestamp;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public double getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public double getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "SystemMetrics [agentId=" + agentId + ", cpuUsage=" + cpuUsage +
				", memoryUsage=" + memoryUsage + ", timestamp=" + timestamp + "]";
	}
	
}
