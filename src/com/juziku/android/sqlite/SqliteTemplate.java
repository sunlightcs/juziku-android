package com.juziku.android.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.juziku.android.util.StringUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class SqliteTemplate<T> {
	private static final String TAG = SqliteTemplate.class.getSimpleName();
	
	private volatile static DatabaseHelper dbHelper;
	private volatile static SQLiteDatabase database;
	
	
	public SqliteTemplate(Context context){
		//初始化数据库配置信息
		new DBConfig(context);
		
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
	}
	
	private Class<?> getTableClass(){
		Type type = getClass().getGenericSuperclass();
		Class<?> clazz = null;
		if (type instanceof ParameterizedType) {
			clazz = (Class<?>)((ParameterizedType)type).getActualTypeArguments()[0];
		}
		return clazz;
	}
	
	public String getTableName() {
		return getTableClass().getSimpleName().toLowerCase();
	}
	
	
	public long save(T t){
		long flag = -1;
		Field []fields = t.getClass().getDeclaredFields();
		ContentValues values = new ContentValues(fields.length);
		try {
			for(Field field : fields){
				if(field.isAnnotationPresent(PrimaryKey.class)&&field.isAnnotationPresent(GeneratedId.class))
					continue;
				if(!field.isAccessible())
					field.setAccessible(true);
				values.put(field.getName(), String.valueOf(field.get(t)));
			}
		} catch (IllegalAccessException e) {
			Log.e(TAG, e.getMessage());
		}
		
		flag = database.insert(getTableName(), null, values);
		
		return flag;
	}
	
	public void save(String sql, String[] bindArgs){
		database.execSQL(sql, bindArgs);
	}
	
	
	public int update(T t){
		int flag = -1;
		Field []fields = t.getClass().getDeclaredFields();
		ContentValues values = new ContentValues(fields.length);
		String key = null;
		try {
			for(Field field : fields){
				if(field.isAnnotationPresent(PrimaryKey.class)){
					key = field.getName();
				}
				
				if(!field.isAccessible())
					field.setAccessible(true);
				values.put(field.getName(), String.valueOf(field.get(t)));
			}
			
			if(StringUtils.isEmpty(key))
				throw new NullPointerException("没有定义主键,不能执行此方法");
		} catch (IllegalAccessException e) {
			Log.e(TAG, e.getMessage());
		}
		
		flag = database.update(getTableName(), values, key + " = ?", new String[] {String.valueOf(values.get(key))});
		
		return flag;
	}
	
	public void update(String sql, String[] bindArgs){
		database.execSQL(sql, bindArgs);
	}
	
	
	public int delete(String key, Object value){
		int flag = -1;
		flag = database.delete(getTableName(), key + " = ?", new String[] {String.valueOf(value)});
		
		return flag;
	}
	
	public void delete(String sql, String[] bindArgs){
		database.execSQL(sql, bindArgs);
	}
	
	public Cursor query(String sql, String[] bindArgs){
		return database.rawQuery(sql, bindArgs);
	}
	
	@SuppressWarnings("unchecked")
	public T queryForObject(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		T t = null;
		if(cursor.moveToFirst()){
			try{
				t = (T)getTableClass().newInstance();
				
				//把列的值，转换成对象里属性的值
				columnToField(t, cursor);
			}catch(Exception e){
				Log.e(TAG, e.getMessage());
			}
		}
		cursor.close();
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> queryForList(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		List<T> list = new ArrayList<T>(cursor.getCount());
		T t = null;
		while (cursor.moveToNext()) {
			try{
				t = (T)getTableClass().newInstance();
				
				//把列的值，转换成对象里属性的值
				columnToField(t, cursor);
				
				list.add(t);
			}catch(Exception e){
				Log.e(TAG, e.getMessage());
			}
		}
		cursor.close();
		return list;
	}
	
	public List<T> queryForAll(){
		return this.queryForList("select * from " + getTableName(), null);
	}
	
	
	public int queryForInt(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		int result = 0;
		if(cursor.moveToFirst())
			result = cursor.getInt(0);
		
		cursor.close();
		return result;
	}
	
	public long queryForLong(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		long result = 0;
		if(cursor.moveToFirst())
			result = cursor.getLong(0);
		
		cursor.close();
		return result;
	}
	
	
	public short queryForShort(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		short result = 0;
		if(cursor.moveToFirst())
			result = cursor.getShort(0);
		
		cursor.close();
		return result;
	}
	
	public float queryForFloat(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		float result = 0;
		if(cursor.moveToFirst())
			result = cursor.getFloat(0);
		
		cursor.close();
		return result;
	}
	
	
	public double queryForDouble(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		double result = 0;
		if(cursor.moveToFirst())
			result = cursor.getDouble(0);
		
		cursor.close();
		return result;
	}
	
	
	public String queryForString(String sql, String[] bindArgs){
		Cursor cursor = this.query(sql, bindArgs);
		String result = null;
		if(cursor.moveToFirst())
			result = cursor.getString(0);
		
		cursor.close();
		return result;
	}
	
	
	private void columnToField(T t, Cursor c){
		Field[] field = t.getClass().getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			int columnIndex = c.getColumnIndex(field[i].getName());
			//如果为-1，表示不存在此列
			if(columnIndex == -1){
				continue;
			}
			
			Class<?>  classz = field[i].getType();
			if(!field[i].isAccessible())
				field[i].setAccessible(true);
			
			try {
				if(classz == Integer.TYPE) {   //int
					field[i].set(t, c.getInt(columnIndex));
				}else if(classz == String.class){   //String
					field[i].set(t, c.getString(columnIndex));
				}else if(classz == Long.TYPE){   //long
					field[i].set(t, c.getLong(columnIndex));
				}else if(classz == byte[].class){   //byte
					field[i].set(t, c.getBlob(columnIndex));
				}else if(classz == Float.TYPE){   //float
					field[i].set(t, c.getFloat(columnIndex));
				}else if(classz == Double.TYPE){   //double
					field[i].set(t, c.getDouble(columnIndex));
				}else if(classz == Short.TYPE){   //short
					field[i].set(t, c.getShort(columnIndex));
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}
	
	
	
	public void beginTransaction() {
		database.beginTransaction();
	}

	public void endTransaction() {
		database.setTransactionSuccessful();
		database.endTransaction();
	}
	
}
