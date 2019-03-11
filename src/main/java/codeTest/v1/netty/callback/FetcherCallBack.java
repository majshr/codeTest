package codeTest.v1.netty.callback;
/**
 * 回调接口
 * @author maj
 *
 */
public interface FetcherCallBack {
	void onData(Data data);
	void onError(Throwable cause);
}
