package com.sogeti.siboubib.spingroupone;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sogeti.siboubib.spingroupone.model.Checkins;

public class MySimpleArrayAdapter extends ArrayAdapter<Checkins> {
	private final Context context;
	private final List<Checkins> values;

	public MySimpleArrayAdapter(Context context, List<Checkins> values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return position % 2;
	}

	public static class ViewHolder {
		public TextView t;
		public TextView i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = null;

		if (convertView != null) {
			rowView = convertView;
		} else {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.rowlayout, parent, false);

			TextView textView = (TextView) rowView.findViewById(R.id.label);
			TextView imageView = (TextView) rowView.findViewById(R.id.icon);
			ViewHolder holder = new ViewHolder();
			holder.t = textView;
			holder.i = imageView;
			rowView.setTag(holder);

		}
		ViewHolder tag = (ViewHolder) rowView.getTag();

		tag.t.setText(values.get(position).getTitle().toUpperCase());
		return rowView;
	}
}
