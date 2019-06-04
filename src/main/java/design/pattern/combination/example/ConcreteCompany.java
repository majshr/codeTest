package design.pattern.combination.example;

import java.util.ArrayList;
import java.util.List;
/**
 * 具体公司类
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月30日 上午11:31:23
 */
public class ConcreteCompany extends Company {

	private List<Company> childrens = new ArrayList<Company>();
	
	public ConcreteCompany(String name) {
		super(name);
	}

	@Override
	public void add(Company c) {
		childrens.add(c);
	}

	@Override
	public void remove(Company c) {
		childrens.remove(c);
	}

	@Override
	public void display(int depth) {
		// 显示自己
		System.out.println(getStr(depth) + name);
		// 显示子节点
		for(Company c : childrens) {
			c.display(depth + 2); 
		}
	}

	@Override
	public void lineOfDuty() {
		for(Company c : childrens) {
			c.lineOfDuty();; 
		}
	}

}
