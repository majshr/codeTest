package design.pattern.state.example;
/**
 * 工作当前状态类, 对应Context
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 上午10:40:44
 */
public class Work {
	/**不同时间的行文类*/
	private State state;
	/**时间*/
	private double hour;
	private boolean isFinish;
	/**
	 * 根据状态处理
	 * 
	 * void 
	 * @date: 2019年1月29日 上午10:55:12
	 */
	public void writeProgram() {
		state.writeProgram(this);
	}
	
	/**测试*/
	public static void main(String[] args) {
		Work w = new Work();
		w.setHour(15);
		w.setState(new MorningState());
		
		w.writeProgram();
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public double getHour() {
		return hour;
	}
	public void setHour(double hour) {
		this.hour = hour;
	}
	public boolean isFinish() {
		return isFinish;
	}
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
}
