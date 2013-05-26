package com.manning.gia.todo.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manning.gia.todo.R;
import com.manning.gia.todo.data.ItemRepository;
import com.manning.gia.todo.model.Item;

/**
 * Detail todo activity view
 * @author jkeam
 *
 */
public class TodoDetailActivity extends Activity {
	private Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_edit);
		uri = getUri(savedInstanceState);
		Item item = getItem(uri);

		Button confirmButton = (Button)findViewById(R.id.todoEditConfirmButton);
		EditText editText = (EditText)findViewById(R.id.editText);
		editText.setText(item.getName());

		attachOnClickHandler(confirmButton, editText);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState(uri);
		outState.putParcelable(ItemRepository.getContentItemType(), uri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState(uri);
	}
	
	protected void saveState(Uri uri) {
		EditText editText = (EditText)findViewById(R.id.editText);
		Item item = new Item(editText.getText().toString());
		if (uri == null) {
			ItemRepository.save(getContentResolver(), item);
		}
		else {
			ItemRepository.update(getContentResolver(), uri, item);
		}
	}

	protected void attachOnClickHandler(Button button, final EditText editText) {
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Item item = new Item(editText.getText().toString());
				String errorMessage = item.validate();
				if (errorMessage != null) {
					showValidation(errorMessage);
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
	}

	private void showValidation(String errorMessage) {
		Toast.makeText(TodoDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
	}

	protected Item getItem(Uri uri) {
		return ItemRepository.getItemFromContentItemUri(getContentResolver(), uri);
	}

	protected Uri getUri(Bundle savedInstanceState) {
		Uri uri = null;

		Bundle bundleFromIntent = getIntent().getExtras();
		if (bundleFromIntent != null) {
			uri = bundleFromIntent.getParcelable(ItemRepository.getContentItemType());
		}
		else if (savedInstanceState != null) {
			uri = savedInstanceState.getParcelable(ItemRepository.getContentItemType());
		}

		return uri;
	}
}
