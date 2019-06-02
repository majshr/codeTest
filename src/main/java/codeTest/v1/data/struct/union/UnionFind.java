package codeTest.v1.data.struct.union;

/**
 * 数组表示的并查集
 * @author maj
 *
 */
public class UnionFind {
	// 下标表示元素, val表示元素所属组
	private int[] id;
	private int count;
	
	public UnionFind(int n) {
		count = n;
		// 初始所有元素都没有连接
		id = new int[n];
		for(int i = 0; i < n; i++){
			id[i] = i;
		}
	}
	
	/**
	 * 查找与p节点连接的值
	 * @param p
	 * @return
	 */
	public int find(int p){
		return id[p];
	}
	
	/**
	 * 判断两个元素是否连接
	 * @param p
	 * @param q
	 * @return
	 */
	boolean isConnected(int p, int q){
		return id[p] == id[q];
	}
	
	/**
	 * 连接两个元素
	 */
	void unionelements(int p, int q){
		int pId = find(p);
		int qId = find(q);
		
		if(pId == qId){
			return;
		}
		
		// 需要将p所在的组(p所有已连接的值), 与q连接
		for(int i = 0; i < count; i++){
			if(id[i] == pId){
				id[i] = qId;
			}
		}
	}
	
}
































