package design.pattern.combination.example;
/**
 * 财务部管理
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月30日 下午3:11:13
 */
public class FinanceDepartment extends Company {

	public FinanceDepartment(String name) {
		super(name);
	}

	@Override
	public void add(Company c) {
		throw new RuntimeException("不支持此操作!");
	}

	@Override
	public void remove(Company c) {
		throw new RuntimeException("不支持此操作!");
	}

	@Override
	public void display(int depth) {
		System.out.println(getStr(depth) + name);
	}

	@Override
	public void lineOfDuty() {
		System.out.println(name + " 对公司财务进行管理!");
	}

}
