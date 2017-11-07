package com.reframe.lapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.reframe.lapp.R;
import com.reframe.lapp.models.Exercise;
import com.reframe.lapp.models.VideoFeedback;

import java.util.List;

/**
 * Created by Aldo on 27-09-2017 to Lapp.
 */

public class VideoFeedbackitemAdapter extends ArrayAdapter<VideoFeedback> {

	public VideoFeedbackitemAdapter(@NonNull Context context, @NonNull List<VideoFeedback> objects) {
		super(context, 0, objects);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		VideoFeedback videoFeedback = getItem(position);
		Log.d("HOLA", new Gson().toJson(videoFeedback));

		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.videofeedback_item, null);
		}

		TextView groupName = (TextView) convertView.findViewById(R.id.exerciseName);
		ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
		groupName.setText(videoFeedback.getTitle());
		Glide.with(getContext())
				.load(videoFeedback.getThumbnail())
				.centerCrop()
				.into(thumbnail);

		return convertView;
	}
}
