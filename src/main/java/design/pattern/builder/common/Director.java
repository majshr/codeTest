package design.pattern.builder.common;
/**
 * 指挥者类
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 上午11:04:53
 */
public class Director {
	public Product construct(Builder builder) {
		builder.buildA();
		builder.buildB();;
		return builder.getResult();
	}
}
