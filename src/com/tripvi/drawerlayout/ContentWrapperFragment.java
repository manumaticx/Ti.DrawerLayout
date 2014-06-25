package com.tripvi.drawerlayout;

import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ContentWrapperFragment extends Fragment {
	
	private static final String TAG = "TripviContentFragment";
	
	View mContentView;
	
	public ContentWrapperFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int layout_content_fragment = 0;
		int id_content_frame = 0;
		try {
			layout_content_fragment = TiRHelper.getResource("layout.content_fragment");
			id_content_frame = TiRHelper.getResource("id.content_frame");
		}
		catch (ResourceNotFoundException e) {
			Log.e(TAG, "XML resources could not be found!!!");
		}
		
		View view = inflater.inflate(layout_content_fragment, container, false);
		FrameLayout v = (FrameLayout) view.findViewById(id_content_frame);
		if (mContentView != null){
			v.addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		return view;
	}
	
	public void setContentView(View cv){
		mContentView = cv;
	}
}
