package com.juziku.android.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for(Class<?> model: DBConfig.models) {
			String sql = DatabaseBuilder.createTableSQL(model);
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//删除后再增加
		if(DBConfig.DATABASE_UPDATE_STRATEGY){
			for(Class<?> model: DBConfig.models) {
				//删除表结构
				db.execSQL(DatabaseBuilder.dropTableSQL(model));
				//再增加表结构
				db.execSQL(DatabaseBuilder.createTableSQL(model));
			}
		}else{
			//只更新增加的表或列
			DatabaseBuilder.updateTables(db);
		}
	}

}
