package codeTest.v1.classloader.hotload.self;

public interface IClassLoader {
	
	/**
	 * 可以加载指定目录jar文件的类
	 * @param parentClassLoader
	 * @param paths
	 * @return
	 */
	ClassLoader createClassLoader(ClassLoader parentClassLoader, String... paths);
}
