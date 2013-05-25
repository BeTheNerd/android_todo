package com.manning.gia.todo.activity;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import com.manning.gia.todo.R;
import com.manning.gia.todo.data.ItemContentProvider;
import com.manning.gia.todo.data.ItemHelper;

public class TodoActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private SimpleCursorAdapter adapter;
	private static final int DELETE_ID = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_list);
		this.getListView().setDividerHeight(2);

		setupAdapter();
		registerForContextMenu(getListView());
	}
	
	protected void setupAdapter() {
		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, new String[]{ItemHelper.NAME_COLUMN}, new int[]{R.id.itemText}, 0);
		setListAdapter(adapter);
	}

	public void onClick(View view) {
		SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
		switch (view.getId()) {
		case R.id.add:
			EditText editText = (EditText) findViewById(R.id.editText);
			String text = editText.getText().toString();

			ContentValues contentValues = new ContentValues();
			contentValues.put(ItemHelper.NAME_COLUMN, text);
			getContentResolver().insert(ItemContentProvider.CONTENT_URI, contentValues);
			editText.setText("");
			break;
			/*
		    case R.id.delete:
		      if (getListAdapter().getCount() > 0) {
		        item = (Item) getListAdapter().getItem(0);
		        datasource.delete(item);
		        adapter.remove(item);
		      }
		      break;
			 */
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, ItemContentProvider.CONTENT_URI, ItemHelper.allColumns, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

	@Override
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo) {
		super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
		contextMenu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.delete_item_text);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			Uri uri = Uri.parse(ItemContentProvider.CONTENT_URI + "/" + info.id);
			getContentResolver().delete(uri, null, null);
			setupAdapter();
			return true;
		}
		return super.onContextItemSelected(item);
	}
}
