package design.pattern.builder.common;

/**
 * 具体建造者2
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 上午11:03:38
 */
public class ConcreteBuilder2 extends Builder{
	private Product product = new Product();
	
	@Override
	public void buildA() {
		product.addPart("部件--A--2");
	}

	@Override
	public void buildB() {
		product.addPart("部件--B--2");
	}

	@Override
	public Product getResult() {
		return product;
	}
}
