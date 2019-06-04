package codeTest.v1.data.struct.str;

public class TrieTree {
	
	private Node root;
	public TrieTree() {
		root = new Node();
	}
	
	/**
	 * 插入一个字符串
	 */
	public void insert(String str) {
        if (str == null || str.length() == 0)
        {
            return;
        }
        Node node = root;
        //将目标单词转换为字符数组
        char[] letters = str.toCharArray();
        for (int i = 0, len = str.length(); i < len; i++)
        {
        	//pos: 字符是第几个字母
            int pos = letters[i] - 'a';
            //如果当前节点的儿子节点中没有该字符，则构建一个TrieNode并复值该字符
            if (node.son[pos] == null)  
            {
                node.son[pos] = new Node();
                node.son[pos].val = letters[i];
            } 
            //如果已经存在，则将由根至该儿子节点组成的字符串模式出现的次数+1
            else   
            {
                node.son[pos].num++;
            }
            node = node.son[pos];
        }
        node.isEnd = true;
	}
	
	/**
	 * 判断单词是否出现
	 */
	public boolean isHas(String str) {
        
		boolean isHas = false;
		
		Node node = getNodeByStr(str);
		//如果存在这个串且最后一个字符是最后一个, 则此单词存在
		if(node != null && node.isEnd) {
			isHas = true;
		}
		
		return isHas;
	}
	
	/**
	 * 查找字符串终点在字典树中的节点对象
	 */
	public Node getNodeByStr(String str) {
        if(str==null||str.length()==0)
        {
            return null;
        }
        
		char[] letters = str.toCharArray();
		Node node = root;
		
		int i = 0;
		for(; i < letters.length; ++i) {
			int pos = letters[i] - 'a';
			if(node.son[pos] == null) {
				break;
			}else {
				node = node.son[pos];
			}
		}
		//如果找到了最后一位, 说明字符串是存在于字典树中的
		if(i == letters.length) {
			return node;
		}
		return null;
	}
	
	/**
	 * 打印指定前缀的单词
	 */
	public void hasPrefix(String prefix) {
		Node node = getNodeByStr(prefix);
		if(node != null) {
			preTraverse(node, prefix);
		}else {
			System.out.println("不存在");
		}
	}
	
	/**
	 * 遍历经过此节点的单词
	 * @param node 经过的节点
	 * @param prefix 经过此节点的前缀
	 */
	public void preTraverse(Node node, String prefix) {
		if(node.isEnd) {
			System.out.println(prefix);
		}
		for(Node child : node.son) {
			if(child != null) {
				preTraverse(child, prefix + child.val);
			}
		}
	}
	
	/**
	 * 求出单词出现次数
	 */
	public Integer countStr(String str) {
		Node node = getNodeByStr(str);
		Integer count = null;
		//如果单词出现了
		//单词出现的次数为: 最后一个字母出现的次数 - 之后的单词中出现的次数
		int subSum = 0;
		if(node.isEnd) {
			for(Node child : node.son) {
				if(child != null) {
					subSum += child.num;
				}
			}
			count = node.num - subSum;
		}
		return count;
	}
	
	public static void main(String[] args) {
		String[] dictionaryData= {"hello","student","computer","sorry","acm","people","experienced","who",
				"reminds","everyday","almost", "alm", "alm", "abc","algo"};
		
		TrieTree tree = new TrieTree();
		for(String str : dictionaryData) {
			tree.insert(str);
		}
		
//		System.out.println(tree.isHas("alm"));
//		tree.hasPrefix("a");
		System.out.println(tree.countStr("almos"));
	}
	
	class Node{
		/**有多少个单词通过这个节点,即由根至该节点组成的字符串模式出现的次数*/
		private int num;
		/**所有的儿子节点, 儿子节点最多有26个(26个字母)*/
		private Node[] son;
		/**是不是最后一个节点*/
		private boolean isEnd;
		/**节点的值*/
		private char val;
		
		public Node() {
			num = 1;
			son = new Node[26];
			isEnd = false;
		}
	}
}










































