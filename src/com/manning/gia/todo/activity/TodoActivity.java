package com.manning.gia.todo.activity;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.manning.gia.todo.data.ItemDAO;
import com.manning.gia.todo.model.Item;
import com.manning.todo.R;

public class TodoActivity extends ListActivity {
	private ItemDAO datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		datasource = new ItemDAO(this);
		datasource.open();

		List<Item> values = datasource.retrieve();
		ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

	}

	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Item> adapter = (ArrayAdapter<Item>) getListAdapter();
		Item item = null;
		switch (view.getId()) {
		case R.id.add:
			EditText editText = (EditText) findViewById(R.id.editText);
			String text = editText.getText().toString();
			item = datasource.create(text);
			adapter.add(item);
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
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
