package codeTest.v1.nio.sys02charset;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetTest {
	public static void main(String[] args) throws IOException {
		showAllCharsets();
		
	}
	
	private static void showAllCharsets(){
		Charset.availableCharsets().forEach((k, v) -> {
			System.out.println(k + ", " + v);
		});
	}
	
	private static void charSetTest1() throws IOException{
		String inputFile = "";
		String outputFile = "";
		
		RandomAccessFile inputReaderAccessFile = new RandomAccessFile(inputFile, "r");
		RandomAccessFile outputWriterAccessFile = new RandomAccessFile(outputFile, "rw");
		
		FileChannel inChannel = inputReaderAccessFile.getChannel();
		FileChannel outChannel = outputWriterAccessFile.getChannel();
		
		MappedByteBuffer mappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inputReaderAccessFile.length());
		
		// 解码：将文件内容解析成字符串； 编码：转换成字符串
		Charset charset = Charset.forName("utf-8");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		/**
		 * 每个磁盘上的文件都会有一个编码
		 */
		
		// 编码
		CharBuffer charBuffer = decoder.decode(mappedByteBuffer);
		// 解码
		ByteBuffer outputData = encoder.encode(charBuffer);
		
		outChannel.write(outputData);
		
		inputReaderAccessFile.close();
		outputWriterAccessFile.close();
	}
}































