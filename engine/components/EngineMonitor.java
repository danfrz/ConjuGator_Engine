package engine.components;

public interface EngineMonitor
{
	public static final int ERR = -1, NRML = 0, SCC = 1;
	public void sendMessage(String msg);
	public void sendMessage(String msg, int code);
	public void sendMessage(String msg, int code, Throwable e);
	public void clear();
}
