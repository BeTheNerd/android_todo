package com.manning.gia.todo.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.manning.gia.todo.model.Item;

public class ItemDAO {
	  private SQLiteDatabase database;
	  private ItemHelper helper;
	  private static final String[] allColumns = { ItemHelper.ID_COLUMN, ItemHelper.NAME_COLUMN};

	  public ItemDAO(Context context) {
	    helper = new ItemHelper(context);
	  }

	  public void open() throws SQLException {
	    database = helper.getWritableDatabase();
	  }

	  public void close() {
	    helper.close();
	  }

	  public Item create(String item) {
	    ContentValues values = new ContentValues();
	    values.put(ItemHelper.NAME_COLUMN, item);
	    long insertId = database.insert(ItemHelper.TABLE_NAME, null, values);
	    Cursor cursor = database.query(ItemHelper.TABLE_NAME, allColumns, ItemHelper.ID_COLUMN + " = " + insertId, null, null, null, null);
	    cursor.moveToFirst();
	    Item insertedItem = getItem(cursor);
	    cursor.close();
	    return insertedItem;
	  }

	  public void delete(Item item) {
	    long id = item.getId();
	    database.delete(ItemHelper.TABLE_NAME, ItemHelper.ID_COLUMN + " = " + id, null);
	  }

	  public List<Item> retrieve() {
	    List<Item> items = new ArrayList<Item>();
	    Cursor cursor = database.query(ItemHelper.TABLE_NAME, allColumns, null, null, null, null, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      items.add(getItem(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	    return items;
	  }

	  private Item getItem(Cursor cursor) {
	    Item item = new Item();
	    item.setId(cursor.getLong(0));
	    item.setName(cursor.getString(1));
	    return item;
	  }
}
