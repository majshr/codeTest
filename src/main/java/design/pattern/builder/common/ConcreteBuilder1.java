package design.pattern.builder.common;

/**
 * 具体建造者1
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 上午11:03:27
 */
public class ConcreteBuilder1 extends Builder{

	private Product product = new Product();
	
	@Override
	public void buildA() {
		product.addPart("部件--A--1");
	}

	@Override
	public void buildB() {
		product.addPart("部件--B--1");
	}

	@Override
	public Product getResult() {
		return product;
	}

}
