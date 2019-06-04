package design.pattern.command.example;
/**
 * 烤肉串命令实现(实际是存储一个receiver对象, 有receiver的方法来烤)
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午4:05:48
 */
public class BakeMuttonCommand extends Command{

	public BakeMuttonCommand(Barbecuer receiver) {
		super(receiver);
	}

	@Override
	public void executeCommond() {
		receiver.bakeMutton();
	}
}
