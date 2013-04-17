package fr.marvinlabs.selectablelisttutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import fr.marvinlabs.selectablelisttutorial.pojo.Item;

/**
 * Our main activity, show a list of items and allows to select some of them
 * 
 * @author marvinlabs
 */
public class MainActivity extends ListActivity {

	private List<Item> data;
	private ListView listView;
	private ItemListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create some sample data and sort it alphabetically
		// --
		data = new ArrayList<Item>(10);
		data.add(new Item(10, "France"));
		data.add(new Item(11, "United Kingdom"));
		data.add(new Item(12, "Ireland"));
		data.add(new Item(13, "Germany"));
		data.add(new Item(14, "Belgium"));
		data.add(new Item(15, "Luxembourg"));
		data.add(new Item(16, "Netherlands"));
		data.add(new Item(17, "Italy"));
		data.add(new Item(18, "Denmark"));
		data.add(new Item(19, "Spain"));
		data.add(new Item(20, "Portugal"));
		data.add(new Item(21, "Greece"));
		Collections.sort(data, new Comparator<Item>() {
			public int compare(Item i1, Item i2) {
				return i1.getCaption().compareTo(i2.getCaption());
			}
		});

		// Create the adapter to render our data
		// --
		adapter = new ItemListAdapter(this, data);
		setListAdapter(adapter);

		// Get some views for later use
		// --
		listView = getListView();
		listView.setItemsCanFocus(false);
	}

	/**
	 * Called when the user presses one of the buttons in the main view
	 */
	public void onButtonClick(View v) {
		switch (v.getId()) {
		case R.id.viewCheckedIdsButton:
			showSelectedItemIds();
			break;
		case R.id.viewCheckedItemsButton:
			showSelectedItems();
			break;
		case R.id.toggleChoiceModeButton:
			toggleChoiceMode();
			break;
		}
	}

	/**
	 * Change the list selection mode
	 */
	private void toggleChoiceMode() {
		clearSelection();

		final int currentMode = listView.getChoiceMode();
		switch (currentMode) {
		case ListView.CHOICE_MODE_NONE:
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			Toast.makeText(this, "List choice mode: SINGLE", Toast.LENGTH_SHORT)
					.show();
			break;
		case ListView.CHOICE_MODE_SINGLE:
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			Toast.makeText(this, "List choice mode: MULTIPLE",
					Toast.LENGTH_SHORT).show();
			break;
		case ListView.CHOICE_MODE_MULTIPLE:
			listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
			Toast.makeText(this, "List choice mode: NONE", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	/**
	 * Show a message giving the selected item captions
	 */
	private void showSelectedItems() {
		final StringBuffer sb = new StringBuffer("Selection: ");

		// Get an array that tells us for each position whether the item is
		// checked or not
		// --
		final SparseBooleanArray checkedItems = listView
				.getCheckedItemPositions();
		if (checkedItems == null) {
			Toast.makeText(this, "No selection info available",
					Toast.LENGTH_LONG).show();
			return;
		}

		// For each element in the status array
		// --
		boolean isFirstSelected = true;
		final int checkedItemsCount = checkedItems.size();
		for (int i = 0; i < checkedItemsCount; ++i) {
			// This tells us the item position we are looking at
			// --
			final int position = checkedItems.keyAt(i);

			// This tells us the item status at the above position
			// --
			final boolean isChecked = checkedItems.valueAt(i);

			if (isChecked) {
				if (!isFirstSelected) {
					sb.append(", ");
				}
				sb.append(data.get(position).getCaption());
				isFirstSelected = false;
			}
		}

		// Show a message with the countries that are selected
		// --
		Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
	}

	/**
	 * Show a message giving the selected item IDs. There seems to be a bug with
	 * ListView#getCheckItemIds() on Android 1.6 at least @see
	 * http://code.google.com/p/android/issues/detail?id=6609
	 */
	private void showSelectedItemIds() {
		final StringBuffer sb = new StringBuffer("Selection: ");

		// Get an array that contains the IDs of the list items that are checked
		// --
		final long[] checkedItemIds = listView.getCheckItemIds();
		if (checkedItemIds == null) {
			Toast.makeText(this, "No selection", Toast.LENGTH_LONG).show();
			return;
		}

		// For each ID in the status array
		// --
		boolean isFirstSelected = true;
		final int checkedItemsCount = checkedItemIds.length;
		for (int i = 0; i < checkedItemsCount; ++i) {
			if (!isFirstSelected) {
				sb.append(", ");
			}
			sb.append(checkedItemIds[i]);
			isFirstSelected = false;
		}

		// Show a message with the country IDs that are selected
		// --
		Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
	}

	/**
	 * Uncheck all the items
	 */
	private void clearSelection() {
		final int itemCount = listView.getCount();
		for (int i = 0; i < itemCount; ++i) {
			listView.setItemChecked(i, false);
		}
	}
}