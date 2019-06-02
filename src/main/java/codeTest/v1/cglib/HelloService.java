package codeTest.v1.cglib;

import org.springframework.cglib.proxy.Enhancer;

public class HelloService {
	public HelloService(){
		System.out.println("构造HelloService");
	}
	
	final public String sayOthersFinal(String name){
		System.out.println("HelloService:sayOthers>>" + name);
		return "HelloService:sayOthers>>" + name;
	}
	
	public void sayHelloPub() {
		System.out.println("HelloService:sayHelloPublic");
	}
	
	private void sayHelloPri(){
		System.out.println("HelloService:sayHelloPrivate");
	}
	
	private void sayHelloPro(){
		System.out.println("HelloService:sayHelloProtected");
	}
	
	void sayHelloDefault(){
		System.out.println("HelloService:sayHelloProtected");
	}
	
	public static void main(String[] args) throws InterruptedException {
		// 通过CGLIB动态代理获取代理对象的过程
	    Enhancer enhancer = new Enhancer();
	    
	    // 设置enhancer对象的父类
	    enhancer.setSuperclass(HelloService.class);
	    // 设置enhancer的回调对象
	    enhancer.setCallback(new MyMethodInterceptor());
	    // 创建代理对象
	    HelloService proxy = (HelloService)enhancer.create();
	    // 通过代理对象调用目标方法
	    proxy.sayHelloPub();
	    proxy.sayHelloPri();
	    proxy.sayHelloPro();
	    proxy.sayOthersFinal("mengaijun");
	    proxy.sayHelloDefault();
	    Thread.sleep(Integer.MAX_VALUE);
	}
	
}




























