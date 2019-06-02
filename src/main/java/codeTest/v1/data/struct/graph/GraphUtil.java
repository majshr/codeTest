package codeTest.v1.data.struct.graph;

import java.util.Iterator;

public class GraphUtil {
	/**
	 * 生成邻接矩阵无向图
	 * @return
	 */
	public static GraphInterface generateGraphAdjacencyMatrix(){
		GraphInterface graph = new GraphAdjacencyMatrix(7);
		
		graph.addEdgeNo(0, 2);
		graph.addEdgeNo(0, 3);
		graph.addEdgeNo(2, 3);
		graph.addEdgeNo(2, 1);
		graph.addEdgeNo(0, 5);
		graph.addEdgeNo(5, 6);
		graph.addEdgeNo(6, 4);
		
		return graph;
	}
	
	public static void visitGraphInterface(){
		GraphInterface gr = generateGraphAdjacencyMatrix();
//		gr.print();
//		System.out.println("===============================");
//		for(int i = 0; i < gr.size(); i++){
//			Iterator<Integer> ite =  gr.getIterator(i);
//			while(ite.hasNext()){
//				System.out.print(ite.next());
//			}
//			System.out.println();
//		}
		
		new VisitGraph(gr);
	}
	
	public static void main(String[] args) {
		visitGraphInterface();
	}
}
