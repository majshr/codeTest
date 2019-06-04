package design.pattern.builder.common;

import java.util.ArrayList;
import java.util.List;
/**
 * 产品类, 有多个部件parts组成
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 上午10:45:55
 */
public class Product {
	List<String> parts = new ArrayList<>();
	
	public void addPart(String part) {
		parts.add(part);
	}
	
	public void show() {
		System.out.println(parts);
	}
}






















