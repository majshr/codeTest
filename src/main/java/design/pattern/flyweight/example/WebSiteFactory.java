package design.pattern.flyweight.example;
/**
 * 网站工厂
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月13日 下午5:55:50
 */

import java.util.HashMap;

public class WebSiteFactory {
	private HashMap<String, WebSite> map = new HashMap<>();
	
	/**
	 * 获得网站, 没有的话创建, 有的话, 获取
	 * 这样所有用于"产品展示"的, 都是一个网站; 所有用于"博客"的, 都是一个网站
	 */
	public WebSite getWebSite(String name) {
		if(!map.containsKey(name)) {
			map.put(name, new ConcreteWebSite(name));
		}
		return map.get(name);
	}
}
