package effective.java;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 重用对象
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月28日 下午7:09:05
 */
public class ReusingObj {
	// 下边创建一个字符串, 并且可以保证, 对于所有同一虚拟机运行代码, 包含相同字符串常量, 就会被重用, 
	// 而下边边是创建两个, 不会重用
	String str1 = new String("abc");
	String str2 = "abc";
}

class Person{
	private final Date birthDate;
	public Person(Date birthDate) {
		super();
		this.birthDate = birthDate;
	}

	// 求一个时间是否在两个确定时间之间, 两个时间对象只需要确定一次即可
	// 只在初始化的时候创建这几个对象, 都实例化一次, 而不是每次比较都创建
	private static final Date BOOM_START;
	private static final Date BOOM_END;
	static {
		Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
		BOOM_START = gmtCal.getTime();
		gmtCal.set(1945, Calendar.JANUARY, 1, 0, 0, 0);
		BOOM_END = new Date(1999);
	}
	
	/**
	 * 比较时间是否在之间
	 * @return
	 * boolean 
	 * @date: 2019年2月28日 下午7:20:34
	 */
	public boolean isBabyBoomer() {
		return birthDate.compareTo(BOOM_START) >= 0 &&
				birthDate.compareTo(BOOM_END) <= 0;
	}
	
	/**
	 * 
	 * 
	 * void 
	 * @date: 2019年2月28日 下午7:31:22
	 */
	public static void sum() {
		// 声明为Long, 意味着要构造大约2^32个多余long实例; 优先使用基本类型而不是装箱基本类型, 要当心自动装箱
		Long sum = 0L;
		for(long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}

























