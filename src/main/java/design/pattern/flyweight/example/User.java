package design.pattern.flyweight.example;
/**
 * 外部状态, 网站用户账号, 网站的外部状态
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月14日 上午10:03:14
 */
public class User {
	private String name;

	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
