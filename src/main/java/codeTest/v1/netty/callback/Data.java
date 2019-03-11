package codeTest.v1.netty.callback;

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
