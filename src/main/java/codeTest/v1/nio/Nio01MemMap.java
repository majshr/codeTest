package codeTest.v1.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Nio01MemMap {
	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("D:\\CanGuanGuanLiXiTong\\codeTest\\src\\main\\java\\codeTest\\v1\\nio\\text.txt", "rw");
		// 内存映射buf是在堆外内存的
		MappedByteBuffer buf = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 5);
		// 修改内存中的信息就能同步到文件
		for(int i = 0; i < buf.capacity(); i++){
			buf.put(i, (byte)'b');
		}

		for(int i = 0; i < buf.capacity(); i++){
			System.out.println((char)buf.get(i));
		}
		
		System.out.println("修改外城");
		file.close();
		
		
		fileLockTest();
	}
	
	public static void fileLockTest() throws IOException{
		RandomAccessFile file = new RandomAccessFile("text.txt", "rw");
		FileChannel channel = file.getChannel();
		// true：共享锁； false：排它锁
		FileLock lock = channel.lock(3, 6, true);
		System.out.println("valid: " + lock.isValid());
		System.out.println("lock type: " + lock.isShared());
		
		lock.release();
		
		file.close();
	}
	
	public static void scatterGatherTest(){
		
	}
}























