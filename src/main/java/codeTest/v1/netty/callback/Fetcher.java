package codeTest.v1.netty.callback;

/**
 * 接口，方法参数为回调实现类，如果接口成功或失败，会调用传入参数的回调
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年6月2日
 */
public interface Fetcher {
	void fetchData(FetcherCallBack callback);
}
