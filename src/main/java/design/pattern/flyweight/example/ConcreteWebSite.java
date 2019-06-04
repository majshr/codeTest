package design.pattern.flyweight.example;
/**
 * 具体网站类(共享享元类)
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月13日 下午5:52:50
 */
public class ConcreteWebSite implements WebSite {

	private String name;
	
	public ConcreteWebSite(String name) {
		super();
		this.name = name;
	}

	@Override
	public void use(User user) {
		System.out.println("网站分类: " + name + ", 被用户使用: " + user.getName());
	}

}
