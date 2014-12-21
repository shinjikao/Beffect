package com.asus.mtbf.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asus.mtbf.model.MTBF;
import com.example.beffect.R;

public class MTBFAdapter extends ArrayAdapter<MTBF> {

	public MTBFAdapter(Context context, ArrayList<MTBF> mtbf) {
		super(context, 0, mtbf);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MTBF mtbf = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.mtbf_list, parent, false);
		}
		
		
		TextView tv_number = (TextView) convertView.findViewById(R.id.number);
		TextView tv_name = (TextView) convertView.findViewById(R.id.name);
		TextView tv_success= (TextView) convertView.findViewById(R.id.successrate);
		
		tv_number.setText(mtbf.getMTBFNumber());
		tv_name.setText(mtbf.getMTBFName());
		tv_success.setText(mtbf.getMTBFSuccessRate());
		return convertView;
	}

}
