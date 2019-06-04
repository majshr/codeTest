package codeTest.v1.data.struct.heap;

import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        // 优先级队列为小顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        // add和offer相同，add内部其实是调用了offer方法
        queue.add(9);
        queue.add(5);
        queue.add(7);
        queue.add(2);
        queue.add(8);

        int size = queue.size();
        for (int i = 0; i < size; i++) {
            // poll删除头结点，remove无参方法也是，有参数方法可以删除第一次出现的元素
            System.out.println(queue.poll());
        }

        // 优先级队列为小顶堆，可以创建时改变比较方法，来变成大顶堆
    }
}

