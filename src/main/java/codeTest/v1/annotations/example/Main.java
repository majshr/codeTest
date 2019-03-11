package codeTest.v1.annotations.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import codeTest.v1.annotations.example.anno.Column;
import codeTest.v1.annotations.example.anno.Table;

public class Main {
	/**
	 * 根据过滤信息, 进行查询
	 * userFilter 如果字段不为空, 在sql中添加过滤条件
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void query(UserFilter userFilter) throws IllegalArgumentException, IllegalAccessException{
		Class c = userFilter.getClass();
		StringBuilder sql = new StringBuilder();
		
		// 获取table注解实例, 设置表信息
		if(c.isAnnotationPresent(Table.class)){
			Table table = (Table) c.getAnnotation(Table.class);
			String tableName = table.value();
			
			sql.append("select * from ").append(tableName).append(" where 1=1 ");
		}
		
		// 获取字段注解信息(getDeclaredFields获取所有, 包括公私有)
		Field[] fields = c.getDeclaredFields();
		for(Field field : fields){
			// 设置私有属性值可达, 否则无法根据反射获取字段值
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
