package design.pattern.factory.method;

import design.pattern.factory.Operator;
import design.pattern.factory.OperatorSub;

public class FactorySub implements IFactory {

	@Override
	public Operator createOperator() {
		return new OperatorSub();
	}

}
