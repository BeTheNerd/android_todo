package com.manning.gia.todo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Very low level helper methods that deal with the database.
 * @author jkeam
 *
 */
public class ItemContentProviderHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "items";
	public static final String ID_COLUMN = "_id";
	public static final String NAME_COLUMN = "name";
	public static final String[] allColumns = { ItemContentProviderHelper.ID_COLUMN, ItemContentProviderHelper.NAME_COLUMN };

	private static final String DATABASE_NAME = "manning.gia";
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "create table items(_id integer primary key autoincrement, name text not null);";
	private static final String DROP_TABLE = "drop table if exists items";
	
	public ItemContentProviderHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public ItemContentProviderHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ItemContentProviderHelper.class.getName(), "Upgrading from " + oldVersion + " to " + newVersion);
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
