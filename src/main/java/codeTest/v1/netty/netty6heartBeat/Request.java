package codeTest.v1.netty.netty6heartBeat;

import java.io.Serializable;
import java.util.HashMap;

/**
 * ���л�һ��Ҫʵ��Serializable�ӿ� ���������netty��Ŀ�����໥ͨ��, Req��Resp��������Ŀ���붼����,
 * �Ұ�·��������ͬ, �������л����ܳɹ�
 */
public class Request implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip ;
	/**
	 * cpu��Ϣ
	 */
	private HashMap<String, Object> cpuPercMap ;
	/**
	 * �ڴ���Ϣ
	 */
	private HashMap<String, Object> memoryMap;
	@Override
	public String toString() {
		return "[ " + ip + "  ,  " + cpuPercMap + "  ,  " +  memoryMap + " ]";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public HashMap<String, Object> getCpuPercMap() {
		return cpuPercMap;
	}

	public void setCpuPercMap(HashMap<String, Object> cpuPercMap) {
		this.cpuPercMap = cpuPercMap;
	}

	public HashMap<String, Object> getMemoryMap() {
		return memoryMap;
	}

	public void setMemoryMap(HashMap<String, Object> memoryMap) {
		this.memoryMap = memoryMap;
	}

}
