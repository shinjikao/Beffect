package com.example.mtbf;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asus.mtbf.model.MTBF;
import com.example.beffect.R;

public class CustListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<MTBF> mtbfItem;

	public CustListAdapter(Context context, List<MTBF> mtbf) {
		this.context = context;
		this.mtbfItem = mtbf;

	}

	@Override
	public int getCount() {
		return mtbfItem.size();
	}

	@Override
	public MTBF getItem(int location) {
		return mtbfItem.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
			convertView = inflater.inflate(R.layout.mtbf_list, null);

		TextView number = (TextView) convertView.findViewById(R.id.number);
		TextView name = (TextView) convertView.findViewById(R.id.name);

		// number.setText(mtbf.getMTBFNumber());
		// name.setText(mtbf.getMTBFName());
		return convertView;
	}

}
