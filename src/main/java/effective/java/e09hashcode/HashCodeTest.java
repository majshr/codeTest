package effective.java.e09hashcode;

public class HashCodeTest {
	/**
	 * 实现了equals方法, 必须实现hashCode方法, 否则
	 * 如果将对象用于Map的key, 可能两个对象equals相等, 但是由于hashCode值不同, 在map中查不到
	 */
	/**
	 * 不要试图从散列码计算中排除一个对象的关键部分来提高性能.
	 * java1.2实现String散列表, 从中均匀选出16个字符, 来计算; 像URL这种层状, 字符串, 就不行了
	 */
}
