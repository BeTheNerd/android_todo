package com.manning.gia.todo.data;

import com.manning.gia.todo.model.Item;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * High level item manipulation.
 * @author jkeam
 *
 */
public class ItemRepository {
	//Convenience passthrough methods
	public static Uri getUri(long id) {
		return Uri.parse(ItemContentProvider.CONTENT_URI + "/" + id);
	}
	
	public static Uri getContentUri() {
		return ItemContentProvider.CONTENT_URI;
	}
	
	public static String getContentType() {
		return ItemContentProvider.CONTENT_TYPE;
	}
	
	public static String getContentItemType() {
		return ItemContentProvider.CONTENT_ITEM_TYPE;
	}
	
	public static String[] getAllColumns() {
		return ItemContentHelper.allColumns;
	}
	
	public static String getNameColumn() {
		return ItemContentHelper.NAME_COLUMN;
	}

	//Data manipulation methods
	public static void delete(ContentResolver contentResolver, long id) {
		contentResolver.delete(ItemRepository.getUri(id), null, null);	
	}
	
	public static void save(ContentResolver contentResolver, Item item) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ItemContentHelper.NAME_COLUMN, item.getName());
		contentResolver.insert(ItemContentProvider.CONTENT_URI, contentValues);
	}
	
	public static void update(ContentResolver contentResolver, Uri uri, Item item) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ItemContentHelper.NAME_COLUMN, item.getName());
		contentResolver.update(uri, contentValues, null, null);
	}
	
	public static Item getItemFromContentItemUri(ContentResolver contentResolver, Uri uri) {
		Cursor cursor = contentResolver.query(uri, ItemContentHelper.allColumns, null, null, null);
		cursor.moveToFirst();
		
		Item item = new Item();
		item.setId(cursor.getLong(cursor.getColumnIndex(ItemContentHelper.ID_COLUMN)));
		item.setName(cursor.getString(cursor.getColumnIndex(ItemContentHelper.NAME_COLUMN)));
		
		cursor.close();
		return item;
	}
}
