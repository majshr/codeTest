package design.pattern.state.example;
/**
 * 下午状态对应行为
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 上午11:13:28
 */
public class AfternoonState implements State {

	@Override
	public void writeProgram(Work w) {
		if(w.getHour() > 14) {
			System.out.println("下午在干活");
		}
	}

}
