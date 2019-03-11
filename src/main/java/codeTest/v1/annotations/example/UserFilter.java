package codeTest.v1.annotations.example;

import codeTest.v1.annotations.example.anno.Column;
import codeTest.v1.annotations.example.anno.Table;

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
