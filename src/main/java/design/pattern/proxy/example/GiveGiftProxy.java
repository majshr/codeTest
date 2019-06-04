package design.pattern.proxy.example;
/**
 * 代理送礼物者
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 下午2:39:39
 */
public class GiveGiftProxy implements GiveGift{

	private GiveGift giveGift;
	
	public GiveGiftProxy(Girl girl) {
		this.giveGift = new GiveGiftReal(girl);
	}
	
	@Override
	public void giveDolls() {
		giveGift.giveDolls();
	}

	@Override
	public void giveFlowers() {
		giveGift.giveFlowers();
	}

	@Override
	public void giveCoffee() {
		giveGift.giveFlowers();
	}

}
