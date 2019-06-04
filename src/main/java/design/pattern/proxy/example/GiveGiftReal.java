package design.pattern.proxy.example;
/**
 * 送礼物真实的人
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午11:23:56
 */
public class GiveGiftReal implements GiveGift{

	private Girl girl;
	
	public GiveGiftReal(Girl girl) {
		this.girl = girl;
	}
	
	@Override
	public void giveDolls() {
		System.out.println("送 dolls " + girl.getName());
	}

	@Override
	public void giveFlowers() {
		System.out.println("送 flowers " + girl.getName());
	}

	@Override
	public void giveCoffee() {
		System.out.println("送 coffee " + girl.getName());
	}

}
