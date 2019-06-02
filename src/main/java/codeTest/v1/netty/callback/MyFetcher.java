package codeTest.v1.netty.callback;

/**
 * Fetcher实现
 * @author maj
 *
 */
public class MyFetcher implements Fetcher {
	final Data data;
	
	public MyFetcher(Data data) {
		this.data = data;
	}
	
	/**
	 * 方法执行成功或失败，会进行回调
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 * @see codeTest.v1.netty.callback.Fetcher#fetchData(codeTest.v1.netty.callback.FetcherCallBack)
	 */
	public void fetchData(FetcherCallBack callback) {
		try{
			callback.onData(data);
		}catch(Exception e){
			callback.onError(e);
		}
	}

}
