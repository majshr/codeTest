package codeTest.v1.watch.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Java 提供了 WatchService 接口，这个接口是利用操作系统本身的文件监控器对目录和文件进行监控，当被监控对象发生变化时，
 * 会有信号通知，从而可以高效的发现变化。
 * 
 * 大致的原理：先根据操作系统 new 一个监控器（ WatchService ），然后选择要监控的配置文件所在目录或文件，然后订阅要监控的事件，
 * 例如创建、删除、编辑，最后向被监控位置注册这个监控器。一旦触发对应我们所订阅的事件时，执行相应的逻辑即可。
 * 
 * 
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年3月12日 下午5:38:00
 */
public class WatchFile {
	
	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		// 项目所在根目录
		URL url = WatchFile.class.getResource("/");
		Path path = Paths.get(url.toURI());
		// 监控创建和修改事件
		path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE);

		// 钩子
		Thread hook = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("文件监测要关闭了!");
					watchService.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Runtime.getRuntime().addShutdownHook(hook);
		
		Thread watchThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// 死循环中, 不断监控
				while(true) {
					try {
						// 尝试获取监控器中的改变, 如果一直没有, 等待
						WatchKey key = watchService.take();
						for(WatchEvent<?> event : key.pollEvents()) {
							String editFileName = event.context().toString();
							
							System.out.println(editFileName + "文件被修改!");
							// 重新加载文件
							BufferedReader reader = new BufferedReader(new FileReader(path.toString() + 
									File.separator + editFileName));
							String line = null;
							while((line = reader.readLine()) != null) {
								System.out.println("	" + line);
							}
						}
						
						// 完成一次监控, 就要重置监控器一次
						key.reset();
					} catch (InterruptedException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		watchThread.setDaemon(true);
		watchThread.start();
		
		Thread.sleep(Integer.MAX_VALUE);
	}
}


















