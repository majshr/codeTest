package codeTest.v1.annotations.example;

import codeTest.v1.annotations.example.anno.Column;
import codeTest.v1.annotations.example.anno.Table;

/**
 * 定义类实体，在根据类做查询时，可以根据注解的信息，自己拼接sql语句
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年6月2日
 */
@Table("user")
public class UserFilter {
	@Column("id")
	private Integer id;
	@Column("userName")
	private String userName;
	@Column("age")
	private Integer age;
	@Column("city")
	private String city;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
