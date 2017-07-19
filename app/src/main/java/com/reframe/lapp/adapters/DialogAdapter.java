package com.reframe.lapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.reframe.lapp.R;

import org.w3c.dom.Text;

/**
 * Created by Aldo on 18-07-2017 to Lapp.
 */

public class DialogAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private boolean isEmail;

	public DialogAdapter(Context context, boolean isEmail) {
		layoutInflater = LayoutInflater.from(context);
		this.isEmail = isEmail;
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;

		if (view == null) {
			if (isEmail) {
				view = layoutInflater.inflate(R.layout.dialog_email_change, parent, false);
			} else {
				view = layoutInflater.inflate(R.layout.dialog_email_change, parent, false);
			}

			viewHolder = new ViewHolder();
			viewHolder.email = (TextView) view.findViewById(R.id.email);
			viewHolder.confirm = (TextView) view.findViewById(R.id.confirm);
			viewHolder.save = (Button) view.findViewById(R.id.save);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		Context context = parent.getContext();

		return view;
	}

	static class ViewHolder {
		TextView email;
		TextView confirm;
		Button save;
	}
}