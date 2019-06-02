package codeTest.v1.netty.callback;

/**
 * 数据，不用关心
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年6月2日
 */
public class Data {
	private int m;
	private int n;
	public Data(int m, int n){
		this.m = m;
		this.n = n;
	}
	@Override
	public String toString() {
		return "Data [m=" + m + ", n=" + n + "]";
	}
}
