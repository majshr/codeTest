package design.pattern.prototype.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 简历对象  clone原型
 * 序列化和反序列话实现
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 下午5:24:02
 */
public class Resume implements Serializable{
	private static final long serialVersionUID = 6833304455275066279L;
	private String name;
	private int age;
	
	private int[] arrs;
	
	private Date date;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		String jsonStr = JSONObject.toJSONString(this);
		Resume resume = JSONObject.parseObject(jsonStr, Resume.class);
		return resume;
	}
	
	public static void main(String[] args) {
		Resume re = new Resume();
		re.name = "maj";
		re.age = 27;
		re.arrs = new int[] {1, 2, 3};
		re.date = new Date();
		System.out.println(JSONObject.toJSONString(re));
		try {
			Resume copy = (Resume) re.clone();
			boolean isEqual = (copy.arrs == re.arrs);
			isEqual = copy.date == re.date;
			System.out.println(isEqual);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "Resume [name=" + name + ", age=" + age + 
				", arrs=" + Arrays.toString(arrs) + ", date=" + date + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int[] getArrs() {
		return arrs;
	}

	public void setArrs(int[] arrs) {
		this.arrs = arrs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
