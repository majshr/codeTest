package design.pattern.command.common;

public class Main {
	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		
		Command c = new ConcreteCommand(receiver);
		
		Invoker i = new Invoker();
		
		i.setCommand(c);
		i.executeCommand();
		
	}
}
