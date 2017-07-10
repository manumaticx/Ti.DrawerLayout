package com.tripvi.drawerlayout;

import org.appcelerator.titanium.view.TiCompositeLayout;

import android.content.Context;
import android.util.AttributeSet;

public class ContentFrame extends TiCompositeLayout {
	private static final String TAG = "TripviContentFrame";

	public ContentFrame(Context context) {
		super(context);
	}
	
	public ContentFrame(Context context, AttributeSet set) {
		super(context, set);
		setId(Drawer.id_content_frame);
		
	}

	
	
}
