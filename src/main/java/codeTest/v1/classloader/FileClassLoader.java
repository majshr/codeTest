package codeTest.v1.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 加载指定文件下的类
 * 
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年3月21日 下午7:27:26
 */
public class FileClassLoader extends ClassLoader{
    /**
     * 特定目录, 加载此目录下的类文件
     */
	public String rootDir;

	public FileClassLoader(String rootDir) {
		super();
		this.rootDir = rootDir;
	}
	
	/**
	 * name为类全名
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			// 获取.class文件字节码数组
			byte[] classData = getClassData(name);
			if(classData != null) {
				return defineClass(name, classData, 0, classData.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new ClassNotFoundException("class文件获取失败!");
	}

	private byte[] getClassData(String name) throws IOException {
		
        String path = rootDir + name.replace(".", File.separator) + ".class";
		
		FileInputStream fis = new FileInputStream(path);
		byte[] buf = new byte[fis.available()];
		
		fis.read(buf);
		
		return buf;
	}

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        // String rootPath = FileClassLoader.class.getResource("/").getPath();
        String rootPath = "E:\\gitlab\\antTest\\bin";

        // FileClassLoader loader = new FileClassLoader(rootPath);
        // Class clazz =
        // loader.loadClass("codeTest.v1.classloader.MyClassLoader");

        File file = new File(rootPath);
        URL url = file.toURL();
        URL[] urls = { url };
        FileClassLoader2 loader = new FileClassLoader2(urls);

        Class clazz = loader.loadClass("antTest.HelloWorld");

        System.out.println(clazz.getName());
    }

}

/**
 * 加载指定文件下的类 URLClassLoader帮我们实现了根据URL加载class文件的方法, 只需要初始化时传入URL路径,
 * 加载类时根据类全名加载即可
 */
class FileClassLoader2 extends URLClassLoader {

    public FileClassLoader2(URL[] urls) {
        super(urls);
    }


}


























