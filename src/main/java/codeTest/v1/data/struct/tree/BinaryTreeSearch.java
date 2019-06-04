package codeTest.v1.data.struct.tree;

public class BinaryTreeSearch {
	class Node{
		Integer val;
		Node left;
		Node right;
		public Node(Integer val, Node left, Node right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
		@Override
		public String toString() {
			return String.valueOf(val);
		}
	}
	
	private Node root;
	
	/**
	 * 二叉排序树查找
	 * @param val
	 */
	public Node search(Integer val) {
		Node node = root;
		while(node != null) {
			System.out.println(node.val);
			if(node.val > val) {
				node = node.left;
			}else if(node.val < val) {
				node = node.right;
			}else {
				break;
			}
		}
		return node;
	}
	
	/**
	 * 查找排序树中最小元素
	 */
	public Node searchMin() {
		return searchMin(root);
	}
	
	/**
	 * 查找指定子树的最小节点
	 */
	private Node searchMin(Node node) {
		if(node == null) {
			return null;
		}
		
		while(node.left != null) {
			node = node.left;
		}
		return node;
	}
	
	
	
	/**
	 * 二叉排序树插入
	 * 从根节点开始, 遇到值大的就向左走, 遇到键值小的就向右走, 一直到末端, 就是插入点
	 */
	public void insert(Integer val) {
		//如果根节点为空
		if(root == null) {
			root = new Node(val, null, null);
			return;
		}
		
		Node node = root;//遍历的当前node节点
		Node pre = null; //当前node节点的父节点
		while(node != null) {
			pre = node;
			if(node.val > val) {
				node = node.left;
			}else if(node.val < val) {
				node = node.right;
			}else {
				//如果有这个值了, 不需要插入, 直接退出
				return;
			}
		}
		if(pre.val > val) {
			pre.left = new Node(val, null, null);
		}else {
			pre.right = new Node(val, null, null);
		}
	}
	
	/**
	 * 二叉排序树删除节点
	 * (1) 删除节点没有子节点, 直接删除
	 * (1) 删除节点只有一个子节点, 将该子节点替代删除的节点
	 * (2) 删除节点有两个子节点, 我们就以右子树内的最小节点取代要删除节点，怎么得最小节点，前有有说。
	 * @param val    
	 */
	public void deleteNode(Integer val) {
		if(root == null) {
			return;
		}
		/*1,先找到该节点*/
		Node node = root;//遍历的当前node节点
		Node pre = null; //当前node节点的父节点
		boolean isRight = false; //node是否是pre的右节点
		while(node != null) {
			if(node.val > val) {
				pre = node;
				node = node.left;
				isRight = false;
			}else if(node.val < val) {
				pre = node;
				isRight = true;
				node = node.right;
			}else {
				//找到了这个值
				break;
			}
		}
		
		/*2,如果该节点存在, 删除操作*/
		if(node != null) {
			if(node.right != null && node.left != null) {
				Node minNode = searchMin(node.right);
				Integer minVal = minNode.val;
				deleteNode(minVal);
				node.val = minNode.val;
			}else if(node.right != null && node.left == null) {
				if(isRight) {
					pre.right = node.right;
				}else {
					pre.left = node.right;
				}
			}else if(node.left != null && node.right == null) {
				if(isRight) {
					pre.right = node.left;
				}else {
					pre.left = node.left;
				}
			}else {
				if(isRight) {
					pre.right = null;
				}else {
					pre.left = null;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int arr[] = { 17,12,19,10,15,18,25,8,11,13,16,22};
		
		BinaryTreeSearch tree = new BinaryTreeSearch();		
		for(int val : arr) {
			tree.insert(val);
		}
		tree.deleteNode(17);
		tree.search(22);
	}
}















































