package design.pattern.command.example;

/**
 * 烤鸡翅命令实现
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午4:07:43
 */
public class BakeChickenWingCommand extends Command{

	public BakeChickenWingCommand(Barbecuer receiver) {
		super(receiver);
	}

	@Override
	public void executeCommond() {
		receiver.bakeChickenWing();
	}
}
