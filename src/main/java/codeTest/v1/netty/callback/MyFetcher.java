package codeTest.v1.netty.callback;

/**
 * Fetcher µœ÷
 * @author maj
 *
 */
public class MyFetcher implements Fetcher {
	final Data data;
	
	public MyFetcher(Data data) {
		this.data = data;
	}
	
	public void fetchData(FetcherCallBack callback) {
		try{
			callback.onData(data);
		}catch(Exception e){
			callback.onError(e);
		}
	}

}
