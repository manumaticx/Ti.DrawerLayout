package com.tripvi.drawerlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContentWrapperFragment extends Fragment {

	View mContentView;

	public ContentWrapperFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return mContentView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroy();

		if (mContentView != null) {
			((ViewGroup) mContentView.getParent()).removeView(mContentView);
		}
	}

	public void setContentView(View cv) {
		mContentView = cv;
	}
}
