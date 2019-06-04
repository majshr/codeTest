package design.pattern.state.example;
/**
 * 中午行为
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 上午11:14:31
 */
public class NoonState implements State{

	@Override
	public void writeProgram(Work w) {
		// 符合状态, 进行行为
		if(w.getHour() > 12 && w.getHour() < 14) {
			System.out.println("中午要睡觉!");
		}else { // 进行下一个状态
			w.setState(new AfternoonState());
			w.writeProgram();
		}
	}

}
