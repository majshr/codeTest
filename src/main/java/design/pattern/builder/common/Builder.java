package design.pattern.builder.common;
/**
 * 建造者抽象类, 确定产品由两个部分partA和partB组成, 并声明一个得到产品建造结果的方法getResult
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 上午10:46:31
 */
public abstract class Builder {
	public abstract void buildA();
	
	public abstract void buildB();
	
	public abstract Product getResult();
	
}
