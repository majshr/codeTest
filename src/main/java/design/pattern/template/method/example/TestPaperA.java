package design.pattern.template.method.example;

/**
 * 具体解答A
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月24日 上午10:07:28
 */
public class TestPaperA extends TestPaper{

	@Override
	protected String getResult1() {
		return "27";
	}

	@Override
	protected String getResult2() {
		return "bj";
	}

}
