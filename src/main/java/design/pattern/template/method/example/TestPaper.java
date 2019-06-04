package design.pattern.template.method.example;
/**
 * 试卷类
 * 试卷内容都是一样的, 只是每个人的答案不一样
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月24日 上午10:01:59
 */
public abstract class TestPaper {
	/**每个人题目是一样的*/
	public void question1() {
		System.out.println("多大了啊?");
		System.out.println("   " + getResult1());
	}
	public void question2() {
		System.out.println("在哪上班啊?");
		System.out.println("   " + getResult2());
	}
	
	/**题目的答案每个人不同*/
	protected abstract String getResult1(); 
	protected abstract String getResult2(); 
}






















