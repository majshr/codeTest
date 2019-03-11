package codeTest.v1.netty.callback;

public class Main {
	public static void main(String[] args) {
		Fetcher fetcher = new MyFetcher(new Data(1, 2));
		fetcher.fetchData(new FetcherCallBack() {
			
			public void onError(Throwable cause) {
				System.out.println("���ճ���" + cause.getMessage());
			}
			
			public void onData(Data data) {
				System.out.println("���յ�����" + data);
			}
		});
	}
}
