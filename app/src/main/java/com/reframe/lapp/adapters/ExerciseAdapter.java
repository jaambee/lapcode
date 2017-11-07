package com.reframe.lapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reframe.lapp.R;
import com.reframe.lapp.models.Exercise;
import com.reframe.lapp.models.Group;

import java.util.List;

/**
 * Created by Aldo on 27-09-2017 to Lapp.
 */

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

	public ExerciseAdapter(@NonNull Context context, @NonNull List<Exercise> objects) {
		super(context, 0, objects);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		Exercise exercise = getItem(position);

		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_item, null);
		}

		TextView groupName = (TextView) convertView.findViewById(R.id.exerciseName);
		groupName.setText(exercise.getName());

		return convertView;
	}
}
