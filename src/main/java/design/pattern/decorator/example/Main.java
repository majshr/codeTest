package design.pattern.decorator.example;

public class Main {
	public static void main(String[] args) {
		Person person = new Person("maj");
		
		// 装饰tshirts
		TShirts tShirts = new TShirts();
		tShirts.setPerson(person);
		
		// 装饰大垮裤
		BigTrouser bigTrouser = new BigTrouser();
		bigTrouser.setPerson(tShirts);
		
		bigTrouser.show();
	}
}
