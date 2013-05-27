package com.manning.gia.todo.repository;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Provides lower level access to items.
 * @author jkeam
 *
 */
public class ItemContentProvider extends ContentProvider {
	private ItemContentProviderHelper database;

	private static final int ITEMS = 10;
	private static final int ITEM_ID = 20;

	private static final String AUTHORITY = "com.manning.gia.todo.repository.contentprovider";
	private static final String BASE_PATH = "items";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/todos";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todo";

	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(AUTHORITY, BASE_PATH, ITEMS);
		uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ITEM_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase writeableDatabase = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case ITEMS:
			rowsDeleted = writeableDatabase.delete(ItemContentProviderHelper.TABLE_NAME, selection, selectionArgs);
			break;
		case ITEM_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = writeableDatabase.delete(ItemContentProviderHelper.TABLE_NAME, ItemContentProviderHelper.ID_COLUMN + "=" + id, null);
			} else {
				rowsDeleted = writeableDatabase.delete(ItemContentProviderHelper.TABLE_NAME, ItemContentProviderHelper.ID_COLUMN + "=" + id + " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			id = sqlDB.insert(ItemContentProviderHelper.TABLE_NAME, null, contentValues);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public boolean onCreate() {
		database = new ItemContentProviderHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(ItemContentProviderHelper.TABLE_NAME);

		int uriType = uriMatcher.match(uri);
		switch (uriType) {
		case ITEMS:
			break;
		case ITEM_ID:
			queryBuilder.appendWhere(ItemContentProviderHelper.ID_COLUMN + "=" + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase writeableDatabase = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(writeableDatabase, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
		SQLiteDatabase writeableDatabase = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			rowsUpdated = writeableDatabase.update(ItemContentProviderHelper.TABLE_NAME, contentValues, selection, selectionArgs);
			break;
		case ITEM_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = writeableDatabase.update(ItemContentProviderHelper.TABLE_NAME, contentValues, ItemContentProviderHelper.ID_COLUMN + "=" + id,  null);
			} else {
				rowsUpdated = writeableDatabase.update(ItemContentProviderHelper.TABLE_NAME, contentValues, ItemContentProviderHelper.ID_COLUMN + "=" + id + " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

}
