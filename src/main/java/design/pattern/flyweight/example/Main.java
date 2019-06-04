package design.pattern.flyweight.example;

public class Main {
	public static void main(String[] args) {
		WebSite webSite1 = new ConcreteWebSite("博客");
		WebSite webSite2 = new ConcreteWebSite("管理系统");
		
		webSite1.use(new User("maj"));
	}
}
