package design.pattern.factory.method;

import design.pattern.factory.Operator;
import design.pattern.factory.OperatorAdd;

public class FactoryAdd implements IFactory {

	@Override
	public Operator createOperator() {
		return new OperatorAdd();
	}

}
