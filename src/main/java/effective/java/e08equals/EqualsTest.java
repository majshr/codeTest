package effective.java.e08equals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**类具有自己逻辑相等的概念, 并且超类没有实现该功能, 就需要实现equals方法*/
/**
 * 自反性, 任何非空引用值, x.equals(x)必须返回true
 * 对称性, 任何非空x和y, x.equals(y)为true, y.equals(x)就需要为true
 * 传递性, 任何非空x, y, z, x和y为true且y和z为true, 那么x和z必须为true
 * 一致性, 对于任何非空的引用x和y, 只要equals中所用信息没有被改变, 多次调用x.equals(y)会一致返回结果
 * 对任何非空引用x, x.equals(null)总返回false
 */
public class EqualsTest {
	/**1,***********违反对称性************/
	static class CaseInsensitiveString{
		private final String s;
		public CaseInsensitiveString(String s) {
			if(s == null) {
				throw new NullPointerException();
			}
			this.s = s;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof CaseInsensitiveString) {
				return s.equalsIgnoreCase(((CaseInsensitiveString)o).s);
			}
			// 把String类型操作去掉才符合规则
/*			if(o instanceof String) {
				return s.equalsIgnoreCase((String)o);
			}*/
			return false;
		}
		
		public static void main(String[] args) {
			CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
			String s = "polish";
			cis.equals(s); // true;
			s.equals(cis); // false, s是String类型的对象, 会直接调用equals方法
		}
	}
	
	/**2, **************违反传递性, 子类的增加信息会影响这个类****************/
	static class Point {
		protected final int x;
		protected final int y;
		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public boolean equals(Object o) {
			if(!(o instanceof Point)) {
				return false;
			}
			Point p = (Point)o;
			return p.x == x && p.y == y;
		}
	}
	/**
	 * 新的子类加了属性, 如果equals方法不重写, 使用父类的, 是不违反equals约定的, 但颜色属性被忽略了;
	 */
	static class ColorPoint extends Point{
		private int color;
		public ColorPoint(int x, int y, int color) {
			super(x, y);
			this.color = color;
		}
		
		/**
		 * 情况1: 如果重写equals方法
		 * Color.equals(ColorPoint) 可能返回true 
		 * 而ColorPoint.equals(Color)总返回false, 因为参数类型不对
		 */
		public boolean equals1(Object o) {
			if(o instanceof ColorPoint) {
				return false;
			}
			return super.equals(o) && this.color == ((ColorPoint)o).color;
		}
		
		/**
		 * 情况2:
		 * 尝试修正上边问题, 让ColorPoint.equals在进行"混合比较"时, 忽略颜色信息*/
		/**这种提供了对称性, 却牺牲了传递性; p1和p2相等, p1和p3相等, 可是可能p2和p3不等, 前两种比较不考虑颜色信息, 第三种比较则考虑*/
		public boolean equals2(Object o) {
			if(o instanceof Point) {
				return super.equals(o);
			}else if(o instanceof ColorPoint) {
				return super.equals(o) && this.color == ((ColorPoint)o).color;
			}else {
				return false;
			}
		}
		
		/**
		 * 事实上, 这是面向对象语言中关于等价关系的一个基本问题.
		 * 我们无法在扩展可实例化的类的同时, 既增加新的值组件, 同时又保留equals约定, 除非愿意放弃面向对象的抽象所带来的优势
		 */
		
		/**可能有人会说, 在equals方法中用getClass测试替代instanceof测试, 可以扩展可实例化的类和增加新的值组件, 同时保留equals约定*/
		/**这样只有对象具有相同的实现时, 才能使对象相等.*/
		@Override
		public boolean equals(Object o) {
			if(o == null || o.getClass() != this.getClass()) {
				return false;
			}
			Point p = (Point)o;
			return p.x == x && p.y == y;
		}
		/**假设编写一个方法, 检验某个整值点是否在单位圆中*/
		private static final Set<Point> unitCircle;
		static {
			unitCircle = new HashSet<>();
			unitCircle.add(new Point(1, 0));
			unitCircle.add(new Point(0, 1));
			unitCircle.add(new Point(-1, 0));
			unitCircle.add(new Point(0, -1));
		}
		public static boolean onUnitCircle(Point p) {
			return unitCircle.contains(p);
		}
	}
	/**不添加值组件的方式扩展了point, 让它的构造器记录创建了多少个实例*/
	/**
	 * 里氏替换原则, 一个类型的任何重要属性也将适用于它的子类型, 因此为该类型编写的任何方法, 在它的子类型上同样运行的很好
	 * 但是假设将CounterPoint实例传递给了onUnitCircle方法, 如果Point类用了基于getClass的equals方法, 都会返回false
	 */
	static class counterPoint extends Point{
		private static final AtomicInteger counter = new AtomicInteger();
		public counterPoint(int x, int y) {
			super(x, y);
			counter.incrementAndGet();
		}
		public int numberCreated() {return counter.get();}
	}

	
	/**
	 * 虽然没有一种令人满意的办法可以既扩展不可实例化的类, 又增加值组件, 但还有一种权宜之计, 复合优先于继承
	 * 在ColorPoint中加入一个私有的Point域, 以及一个共有的视图方法, 此方法返回一个与该有色点处在相同位置的普通Point对象
	 */
	static class Color{
		int color;
		@Override
		public boolean equals(Object o) {
			if(o instanceof Color) {
				Color c = (Color) o;
				return color == c.color;
			}
			return false;
		}
	}
	static class ColorPointUpdate{
		private final Point point;
		private final Color color;
		public ColorPointUpdate(int x, int y, Color color) {
			if(color == null) {
				throw new NullPointerException();
			}
			this.point = new Point(x, y);
			this.color = color;
		}
		
		public Point asPoint() {
			return point;
		}
		@Override
		public boolean equals(Object o) {
			if(o instanceof ColorPointUpdate) {
				ColorPointUpdate cp = (ColorPointUpdate) o;
				return cp.asPoint().equals(point) && cp.color.equals(color);
			}
			return false;
		}
		
	}
	
	
	/**可以在一个抽象类的子类中增加新的值组件, 而不违反equals约定. 只要不能直接创建超类的实例, 前面所述种种问题都不会发生*/
	
	/**
	 * instanceof  如果第一个参数为null, 第二个参数无论为什么, 都返回false
	 */
	
	/**
	 * **************************如何实现一个equals方法建议****************************
	 * ==操作符检查"参数是否为这个对象的引用", 如果是, 返回true
	 * instanceof检查"参数是否为正确的类型", 一般来说, 正确的类指equals方法所在的那个类, 有些情况, 是指该类所实现的某个接口
	 * 把参数转换成正确的类型 
	 * 对类中每个关键域, 检查参数中的域是否与对象中对应的域匹配; 优先比较最有可能不一致的域, 或开销最低的域
	 * 不要企图让equals方法过于只能
	 */
	public boolean equals(Object o){
		if(o == this) {
			return true;
		}
		if(o instanceof EqualsTest) {
			return false;
		}
		// 各个字段的比较
		return true;
	}
}
