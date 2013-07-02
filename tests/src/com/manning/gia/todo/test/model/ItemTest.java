package com.manning.gia.todo.test.model;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.manning.gia.todo.model.Item;

public class ItemTest extends AndroidTestCase {

	@SmallTest
	public void testItemValidateError() {
		String expected = Item.MISSING_TEXT_MESSAGE;
		Item item = new Item();
		item.setName(null);
		String actual = item.validate();
		assertEquals(expected, actual);
	}
	
	@SmallTest
	public void testItemValidate() {
		String expected = null;
		Item item = new Item();
		item.setName("Buy milk");
		String actual = item.validate();
		assertEquals(expected, actual);
	}
}
