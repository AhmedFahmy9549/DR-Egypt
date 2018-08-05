package com.example.gmsproduction.dregypt.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmsproduction.dregypt.R;


public class CustomToast {

	// Custom Toast Method
	public void Show_Toast(Context context, View view, String error) {

		// Layout Inflater for inflating custom view
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// inflate the layout over view
		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) view.findViewById(R.id.toast_root));

		// Get TextView id and set error
		TextView text = (TextView) layout.findViewById(R.id.toast_error);
		text.setText(error);

		Toast toast = new Toast(context);// Get Toast Context
		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
																		// Toast
																		// gravity
																		// and
																		// Fill
																		// Horizoontal

		toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
		toast.setView(layout); // Set Custom View over toast

		toast.show();// Finally show toast
	}

	public void Show_Toast_Success(Context context, View view, String msg) {

		// Layout Inflater for inflating custom view
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// inflate the layout over view
		View layout = inflater.inflate(R.layout.custom_toast_success,
				(ViewGroup) view.findViewById(R.id.toast_root_success));

		// Get TextView id and set error
		TextView text = (TextView) layout.findViewById(R.id.toast_success);
		text.setText("Success"+"\n\n"+msg);

		Toast toast = new Toast(context);// Get Toast Context
		toast.setGravity(Gravity.FILL, 0, 0);// Set
		// Toast
		// gravity
		// and
		// Fill
		// Horizoontal

		toast.setDuration(Toast.LENGTH_LONG);// Set Duration
		toast.setView(layout); // Set Custom View over toast

		toast.show();// Finally show toast
	}

	public void Show_Toast_Fail(Context context, View view, String msg) {

		// Layout Inflater for inflating custom view
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// inflate the layout over view
		View layout = inflater.inflate(R.layout.custom_toast_fail,
				(ViewGroup) view.findViewById(R.id.toast_root_fail));

		// Get TextView id and set error
		TextView text = (TextView) layout.findViewById(R.id.toast_fail);
		text.setText("Fail"+"\n\n"+msg);

		Toast toast = new Toast(context);// Get Toast Context
		toast.setGravity(Gravity.FILL, 0, 0);// Set
		// Toast
		// gravity
		// and
		// Fill
		// Horizoontal

		toast.setDuration(Toast.LENGTH_LONG);// Set Duration
		toast.setView(layout); // Set Custom View over toast

		toast.show();// Finally show toast
	}


}
