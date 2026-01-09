package com.monitoring.agent.collector;

import java.io.File;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import pourelle.SystemMetrics;

public class MetricsCollector{
	private OperatingSystemMXBean mxBean = (OperatingSystemMXBean) 
			ManagementFactory.getOperatingSystemMXBean();
	private File file = new File("C:\\");
	
	private double getCpuUsage() {
		double cpuUsage = mxBean.getSystemCpuLoad();
		if (cpuUsage<0) return 0.0;
		return cpuUsage*100;
	}

	private double getMemoryUsage() {
		double total = mxBean.getTotalPhysicalMemorySize();
		long free  = mxBean.getFreePhysicalMemorySize();
		double memoryUsage = ((total-free)/total)*100;
		return memoryUsage;
	}


	private long getTimestamp() {
		return System.currentTimeMillis();
	}

	public SystemMetrics collectMetrics(String agentId) {
		
		return new SystemMetrics(agentId, getCpuUsage(), getMemoryUsage(), getTimestamp());
	}
}
