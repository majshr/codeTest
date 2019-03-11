package codeTest.v1.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * �Զ��������ݽ���ѹ���ͽ�ѹ��������
 * @author maj
 */
public class GzipUtils {
	/**
	 * ѹ���ֽ�����
	 * @param data
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] gzip(byte[] data) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data);
		gzip.finish();
		gzip.close();
		
		byte[] ret = bos.toByteArray();
		bos.close();
		return ret;
	}
	
	/**
	 * ��ѹ�����ݽ��н�ѹ��
	 * @param data ѹ���������
	 * @return byte[] ԭ��������
	 * @throws IOException 
	 */
	public static byte[] ungzip(byte[] data) throws IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		GZIPInputStream gzip = new GZIPInputStream(bis);
		
		byte[] buf = new byte[1024];
		int num = -1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((num = gzip.read(buf, 0, buf.length)) != -1){
			// ����ѹ������ֽ�����ƴ������
			bos.write(buf, 0, num);
		}
		
		gzip.close();
		bis.close();
		
		byte[] ret = bos.toByteArray();
		bos.flush();
		bos.close();
		return ret;
	}
	
    public static void main(String[] args) throws Exception{
		
    	//��ȡ�ļ�
    	String readPath = "C:\\Users\\maj\\Desktop\\spring.java";
        File file = new File(readPath);  
        FileInputStream in = new FileInputStream(file);  
        byte[] data = new byte[in.available()];  
        in.read(data);  
        in.close();  
        
        System.out.println("�ļ�ԭʼ��С:" + data.length);
        //����ѹ��
        
        byte[] ret1 = GzipUtils.gzip(data);
        System.out.println("ѹ��֮���С:" + ret1.length);
        
        byte[] ret2 = GzipUtils.ungzip(ret1);
        System.out.println("��ԭ֮���С:" + ret2.length);
        
        //д���ļ�
        String writePath = "C:\\Users\\maj\\Desktop\\spring_copy.java";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(ret2);
        fos.close();    	
    	
    	
	}
}









































