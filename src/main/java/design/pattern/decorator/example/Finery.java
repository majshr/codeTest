package design.pattern.decorator.example;
/**
 * 服饰装饰器抽象类
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:50:19
 */
public class Finery extends Person{

	protected Person basePerson;
	
	public void setPerson(Person person) {
		this.basePerson = person;
	}
	
}
