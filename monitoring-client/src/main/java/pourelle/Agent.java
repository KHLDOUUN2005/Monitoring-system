package pourelle;

import java.io.Serializable;

public class Agent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String agentId;
    private String ipAddress;
    private SystemMetrics lastMetrics;
    private long lastUpdate;
    private String status;
    
    public Agent(String agentId, String ipAddress, SystemMetrics lastMetrics) {
        this.agentId = agentId;
        this.ipAddress = ipAddress;
        this.lastMetrics = lastMetrics;
        this.lastUpdate = System.currentTimeMillis();
        this.status = "ONLINE";
    }
    
    // Getters
    public String getAgentId() {
        return agentId;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public SystemMetrics getLastMetrics() {
        return lastMetrics;
    }
    
    public long getLastUpdate() {
        return lastUpdate;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setters
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public void setLastMetrics(SystemMetrics lastMetrics) {
        this.lastMetrics = lastMetrics;
        this.lastUpdate = System.currentTimeMillis();
    }
    
    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Agent [agentId=" + agentId + ", ipAddress=" + ipAddress + 
               ", lastMetrics=" + lastMetrics + ", lastUpdate=" + lastUpdate + 
               ", status=" + status + "]";
    }
}