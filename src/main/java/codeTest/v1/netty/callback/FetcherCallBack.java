package codeTest.v1.netty.callback;
/**
 * �ص��ӿ�
 * @author maj
 *
 */
public interface FetcherCallBack {
	void onData(Data data);
	void onError(Throwable cause);
}
