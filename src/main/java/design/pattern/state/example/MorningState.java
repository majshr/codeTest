package design.pattern.state.example;
/**
 * 上午状态对应行为
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 上午10:56:10
 */
public class MorningState implements State {

	@Override
	public void writeProgram(Work w) {
		// 上午干的活, 符合自己的行为
		if(w.getHour() < 12) {
			System.out.println("上午在干活");
		}else { // 不属于上午, 判断下一步的操作
			w.setState(new NoonState());
			w.writeProgram();
		}
	}

}
