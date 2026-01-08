package pourelle;

import java.io.Serializable;

public class Alert implements Serializable {
    private static final long serialVersionUID = 1L;
	private String agentId;
	private String message;
	private long timestamp;
	
	public Alert(String agentId, String message, long timestamp) {
		super();
		this.agentId = agentId;
		this.message = message;
		this.timestamp = timestamp;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Alert [agentId=" + agentId + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
	
}
