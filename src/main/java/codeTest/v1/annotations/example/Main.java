package codeTest.v1.annotations.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import codeTest.v1.annotations.example.anno.Column;
import codeTest.v1.annotations.example.anno.Table;

public class Main {
	/**
	 * ���ݹ�����Ϣ, ���в�ѯ
	 * userFilter ����ֶβ�Ϊ��, ��sql����ӹ�������
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void query(UserFilter userFilter) throws IllegalArgumentException, IllegalAccessException{
		Class c = userFilter.getClass();
		StringBuilder sql = new StringBuilder();
		
		// ��ȡtableע��ʵ��, ���ñ���Ϣ
		if(c.isAnnotationPresent(Table.class)){
			Table table = (Table) c.getAnnotation(Table.class);
			String tableName = table.value();
			
			sql.append("select * from ").append(tableName).append(" where 1=1 ");
		}
		
		// ��ȡ�ֶ�ע����Ϣ(getDeclaredFields��ȡ����, ������˽��)
		Field[] fields = c.getDeclaredFields();
		for(Field field : fields){
			// ����˽������ֵ�ɴ�, �����޷����ݷ����ȡ�ֶ�ֵ
			field.setAccessible(true);
			Object fieldObj = field.get(userFilter);
			String fieldVal = null;
			if(fieldObj != null){
				fieldVal = String.valueOf(fieldObj);
			}
			if(field.isAnnotationPresent(Column.class) && 
					fieldVal != null){
				Column column = field.getAnnotation(Column.class);
				String columnName = column.value();
				sql.append(" and ").append(columnName).append("=").append(fieldVal);
			}
		}
		
		System.out.println(sql.toString());
	}
	
	public static void main(String[] args) {
		UserFilter userFilter = new UserFilter();
		userFilter.setUserName("maj");
		userFilter.setAge(28);
		
		try {
			query(userFilter);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
