package design.pattern.combination.example;
/**
 * 公司类(可能有总公司, xxx地方子公司)
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月30日 上午11:22:05
 */
public abstract class Company {
	protected String name;
	public Company(String name) {
		this.name = name;
	}
	
	protected String getStr(int size) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; i++) {
			sb.append("-");
		}
		return sb.toString();
	}
	
	/**
	 * 增加
	 * @param c
	 * @date: 2019年1月30日 上午11:24:39
	 */
	public abstract void add(Company c);
	
	/**
	 * 移除
	 * @param c
	 * @date: 2019年1月30日 上午11:25:02
	 */
	public abstract void remove(Company c); 
	
	/**
	 * 显示
	 * @param depth
	 * @date: 2019年1月30日 上午11:25:37
	 */
	public abstract void display(int depth);
	
	/**
	 * 履行职责, 不同部门需要履行不同的职责
	 * @date: 2019年1月30日 上午11:25:57
	 */
	public abstract void lineOfDuty();
}






























