package codeTest.v1.netty2.ssy07thrift;

import org.apache.thrift.TException;

import codeTest.v1.netty2.ssy07thrift.entity.DataException;
import codeTest.v1.netty2.ssy07thrift.entity.Person;
import codeTest.v1.netty2.ssy07thrift.entity.PersonService;

/**
 * 实现接口的方式
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月30日
 */
public class PersonalServiceImpl implements PersonService.Iface{

	@Override
	public Person getPersonByUsername(String username) throws DataException, TException {
		System.out.println(username);
		
		Person person = new Person();
		person.setAge(25);
		person.setUsername("aaa");
		return person;
	}

	@Override
	public void savePerson(Person person) throws DataException, TException {
		System.out.println(person.getUsername() + "====" + person.getAge());
	}

}
