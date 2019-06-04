package design.pattern.combination.example;

/**
 * hr管理
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月30日 下午3:11:25
 */
public class HRDepartment extends Company{

	public HRDepartment(String name) {
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
		System.out.println(name + " 对员工进行培训管理!");
	}

}
