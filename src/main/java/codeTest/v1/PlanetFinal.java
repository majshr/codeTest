package codeTest.v1;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class PlanetFinal {
	/**
	 * 不可变属性
	 */
	private final double fMass;
	private final String fName;
	/**
	 * 可变对象属性，这种情况下，这个可变属性只能被这个类改变，（其它情况下，允许在原生类外部改变一个属性是很有意义的，
	 * 这种情况就是当属性作为其它地方创建的一个对象引用）
	 */
	private final Date fDate;

	public PlanetFinal(double fMass, String fName, Date fDate) {
		super();
		this.fMass = fMass;
		this.fName = fName;
		// 创建fDate的一个私有拷贝，这是保持fDate属性为private的唯一方式，并且保护这个类不受调用者
		// 对原始fDate对象所做任何改变的影响
		this.fDate = new Date(fDate.getTime());
	}

	/**
	 * 返回属性的一个保护性拷贝值
	 * 
	 * @return
	 */
	public Date getDate() {
		return new Date(fDate.getTime());
	}

}

/**
 * 不带虚拟节点的一致性Hash算法
 *
 */
class ConsistentHashingWithoutVirtualNode {
	/**
	 * 待添加入Hash环的服务器列表
	 */
	private static String[] servers = { "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111",
			"192.168.0.4:111" };
	/**
	 * key表示服务器的hash值，value表示服务器的名称
	 */
	private static SortedMap sortedMap = new TreeMap();
	/**
	 * 程序初始化，将所有的服务器放入sortedMap中
	 */
	static {
		for (int i = 0; i < servers.length; i++) {
			int hash = getHash(servers[i]);
			System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
			sortedMap.put(hash, servers[i]);
		}
		System.out.println();
	}

	/**
	 * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
	 */
	private static int getHash(String str){
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < str.length(); i++)
			hash = (hash ^ str.charAt(i)) * p;
		
		hash += hash;
		hash ^= hash >> 7;
		hash += hash;
		hash ^= hash >> 17;
		hash += hash;
		
		// 如果算出来的值为负数则取其绝对值
		if (hash < 0)
			hash = Math.abs(hash);
		return hash;
	}

	/**
	 * 得到应当路由到的结点
	 */
	private static String getServer(String node){
		// 得到带路由的结点的Hash值
		int hash = getHash(node);

		// 得到大于该Hash值的所有Map
		SortedMap subMap = sortedMap.tailMap(hash);

		// 第一个Key就是顺时针过去离node最近的那个结点
		Integer i = (Integer) subMap.firstKey();

		// 返回对应的服务器名称
		return (String) subMap.get(i);
	}

	public static void main(String[] args){
		String[] nodes = { "127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333" };
		for (int i = 0; i < nodes.length; i++)
			System.out.println("[" + nodes[i] + "]的hash值为" +
					getHash(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");
	}

}
