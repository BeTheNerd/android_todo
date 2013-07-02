package com.manning.gia.todo.test.activity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

import com.manning.gia.todo.R;
import com.manning.gia.todo.activity.TodoActivity;

public class TodoActivityTest extends ActivityUnitTestCase<TodoActivity> {
	private int addButtonId;
	private TodoActivity activity;

	public TodoActivityTest() {
		super(TodoActivity.class);
	}
	
	public TodoActivityTest(Class<TodoActivity> activityClass) {
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), TodoActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
	}

	@SmallTest
	public void testLayout() {
		addButtonId = R.id.add;
		Button view = (Button) activity.findViewById(addButtonId);
		assertNotNull("Button should not be null", view);
		assertEquals("Wrong label of the add button", "Add", view.getText());
	}

	@SmallTest
	public void testIntentTriggerViaOnClick() {
		addButtonId = R.id.add;
		Button view = (Button) activity.findViewById(addButtonId);

		//click
		getActivity().onClickAdd(view);
		
		//TODO: assert new element is in list
		
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

}
