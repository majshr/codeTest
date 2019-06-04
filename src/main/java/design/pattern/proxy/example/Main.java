package design.pattern.proxy.example;

public class Main {
	public static void main(String[] args) {
		// 收礼的人
		Girl girl = new Girl("aaa");
		
		// 代理送礼的人
		GiveGift proxy = new GiveGiftProxy(girl);
		
		proxy.giveCoffee();
	}
}
