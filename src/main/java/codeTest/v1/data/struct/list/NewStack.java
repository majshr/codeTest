package codeTest.v1.data.struct.list;
import java.util.Stack;

public class NewStack {
	/**
	 思路:模拟这个栈的push  pop过程
	 如:push序列[1, 2, 3, 4, 5] 和  pop序列[4, 5, 3, 2, 1]
	 向栈中push元素, 根据pop序列可知, push到元素4时, 就需要pop弹出4了; 接着看栈顶是否为5, 不是继续push元素, 当栈顶为5时, pop弹出
	 
	 当pop序列判断完毕时, 就说明是正确的了
	 如果pop序列中的元素取不到, 就是不正确的了
	 
	 */
	
	public static boolean check(int[] push, int[] pop) {
		boolean isRight = false;
		Stack<Integer> stack = new Stack<Integer>();
		int indexP = 0;
		for(int i = 0; i < push.length; ) {
			//如果栈顶元素为pop序列当前元素, 出栈
			if(!stack.isEmpty() && stack.peek() == pop[indexP]) {
				stack.pop();
				indexP++;
				if(indexP == pop.length) {
					isRight = true;
					break;
				}
			}
			//如果栈顶元素不等于pop序列当前元素, 或栈为空, 继续往栈里push元素
			else {
				stack.push(push[i]);
				i++;
			}
		}
		
		//如果所有元素都入栈了, 就只剩出栈了, 此时出栈顺序应该和剩余的pop序列值相同
		if(!stack.isEmpty() && indexP != pop.length) {
			while(indexP != pop.length) {
				if(stack.pop() == pop[indexP]) {
					indexP++;
				}else {
					break;
				}
			}
			if(indexP == pop.length) {
				isRight = true;
			}
		}
		
		return isRight;
	}
	
	
	public static void main(String[] args) {
		System.out.println("hlelo");
		check(new int[] {1, 2, 3, 4, 5}, new int[] {4, 5, 3, 1, 2});;
	}
}

/**
 * 验证栈的push, pop序列是否一致
 */

class CheckStack{

	
}



























