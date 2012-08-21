package com.juziku.android.sqlite;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.juziku.android.util.StringUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseBuilder {
	
	private static final String getTableName(Class<?> clazz) {
		return clazz.getSimpleName().toLowerCase();
	}
	
	/**
	 * 根据实体类，获得创建表的SQL语句
	 */
	protected static String createTableSQL(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(getTableName(clazz)).append(" (");
		for (Field column : clazz.getDeclaredFields()) {
			String columnType = getColumnType(column.getType());
			//判断是否主键
			if(column.isAnnotationPresent(PrimaryKey.class)){
				sb.append(column.getName()).append(" ").append(columnType).append(" primary key ");
				//判断是否自增长
				if(column.isAnnotationPresent(GeneratedId.class))
					sb.append(" autoincrement ");
				sb.append(",");
			}else{
				sb.append(column.getName()).append(" ").append(columnType).append(",");
			}
		}
		
		String sql = sb.toString();
		sql = sql.substring(0, sql.length()-1) + ")";
		return sql;
	}
	
	/**
	 * 根据实体类，获得删除表的SQL语句
	 */
	protected static String dropTableSQL(Class<?> clazz) {
		return "drop table if exists " + getTableName(clazz);
	}
	
	
	protected static String getColumnType(Class<?> clazz) {
		String type = null;
		
		if(clazz == Integer.TYPE ) {   //int 
			return "integer";
		}else if(clazz == String.class){   //String
			type = "text";
		}else if(clazz == Long.TYPE){   //long
			type = "integer";
		}else if(clazz == Short.TYPE){   //short
			type = "integer";
		}else if(clazz == Boolean.TYPE){   //boolean
			type = "integer";
		}else if(clazz == Float.TYPE){   //float
			type = "real";
		}else if(clazz == Double.TYPE){   //double
			type = "real";
		}else if(clazz == byte[].class){   //byte
			type = "blob";
		}
		
		return type;
	}
	
	
	private static HashMap<String, String> getAllTables(SQLiteDatabase db) {
		String sql = "select name,sql from sqlite_master where name!='android_metadata' and name!='sqlite_sequence'";
		Cursor cursor = db.rawQuery(sql, null);
		HashMap<String, String> tables = new HashMap<String, String>(cursor.getCount());
		try {
			while (cursor.moveToNext()) {
				String tableName = cursor.getString(0);
				
				String columnName = cursor.getString(1);
				columnName = columnName.substring(columnName.indexOf("(")+1, columnName.length()-1);
				String columns[] = columnName.split(",");
				StringBuilder sb = new StringBuilder();
				for(String column : columns){
					sb.append(column.split("\\s+")[0]).append(" ");
				}
				
				tables.put(tableName, sb.toString());
			}
		} finally {
			cursor.close();
		}
		return tables;
	}
	
	
	protected static void updateTables(SQLiteDatabase db){
		//查询已存在的所有表
		HashMap<String, String> tables = getAllTables(db);
		for(Class<?> model: DBConfig.models) {
			String tableName = getTableName(model);
			String columns = tables.get(tableName);
			//如果表不存在，则增加此表
			if(StringUtils.isNotEmpty(columns)){
				Field fields[] = model.getDeclaredFields();
				String columnsArray[] = columns.split("\\s+");
				for(Field field : fields){
					if(!isColumnExits(field.getName(), columnsArray))
						db.execSQL("alter table " + tableName + " add column " + field.getName() + " " + getColumnType(field.getType()) + ";");
				}
			}else{
				db.execSQL(createTableSQL(model));
			}
		}
	}
	
	
	private static boolean isColumnExits(String fieldName, String columns[]){
		//判断该属于是否存在   true:存在  false:不存在
		boolean flag = false;
		for(String column : columns){
			if(fieldName.equalsIgnoreCase(column)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	
}
