/**
 * 
 */
package fr.marvinlabs.selectablelisttutorial;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.marvinlabs.selectablelisttutorial.pojo.Item;
import fr.marvinlabs.widget.CheckableRelativeLayout;

/**
 * Adapter that allows us to render a list of items
 * 
 * @author marvinlabs
 */
public class ItemListAdapter extends ArrayAdapter<Item> {

	/**
	 * Constructor from a list of items
	 */
	public ItemListAdapter(Context context, List<Item> items) {
		super(context, 0, items);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// The item we want to get the view for
		// --
		final Item item = getItem(position);

		// Re-use the view if possible
		// --
		ViewHolder holder;
		if (convertView == null) {
			convertView = li.inflate(R.layout.item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(R.id.holder, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.holder);
		}

		// Set some view properties
		holder.id.setText("#" + item.getId());
		holder.caption.setText(item.getCaption());

		// Restore the checked state properly
		final ListView lv = (ListView) parent;
		holder.layout.setChecked(lv.isItemChecked(position));

		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	private LayoutInflater li;

	private static class ViewHolder {
		public ViewHolder(View root) {
			id = (TextView) root.findViewById(R.id.itemId);
			caption = (TextView) root.findViewById(R.id.itemCaption);
			layout = (CheckableRelativeLayout) root.findViewById(R.id.layout);
		}

		public TextView id;
		public TextView caption;
		public CheckableRelativeLayout layout;
	}
}
