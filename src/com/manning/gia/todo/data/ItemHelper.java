package com.manning.gia.todo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "ITEMS";
	public static final String ID_COLUMN = "_ID";
	public static final String NAME_COLUMN = "NAME";

	private static final String DATABASE_NAME = "MANNING.GIA";
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE ITEMS(_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL);";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS ITEMS";
	
	public ItemHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public ItemHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ItemHelper.class.getName(), "Upgrading from " + oldVersion + " to " + newVersion);
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
