package com.tripvi.drawerlayout;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
//import org.appcelerator.kroll.annotations.Kroll;
//import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiDimension;
import android.util.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.proxy.TiViewProxy;
//import org.appcelerator.titanium.view.TiCompositeLayout;
//import org.appcelerator.titanium.view.TiCompositeLayout.LayoutArrangement;
import org.appcelerator.titanium.view.TiUIView;

import ti.modules.titanium.ui.WindowProxy;

import android.app.Activity;

//import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


public class Drawer extends TiUIView {

	private DrawerLayout layout;
	private FrameLayout content;
	private FrameLayout menu;
	private TiViewProxy leftView;
	private TiViewProxy centerView;
	private int menuWidth;
	
	// Static Properties
	public static final String PROPERTY_LEFT_VIEW = "leftView";
	public static final String PROPERTY_CENTER_VIEW = "centerView";
	public static final String PROPERTY_RIGHT_VIEW = "rightView";
	public static final String PROPERTY_LEFT_VIEW_WIDTH = "leftDrawerWidth";
	
	
	private static final String TAG = "TripviDrawer";
	
	public Drawer(final DrawerProxy proxy) {
		super(proxy);
		
		Activity activity = proxy.getActivity();
		layout = new DrawerLayout(activity);
		
		
		// content
		content = new FrameLayout(activity);
		LayoutParams contentLayout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		content.setLayoutParams(contentLayout);
		
		layout.addView(content);


		// menu
		menu = new FrameLayout(activity);
		LayoutParams menuLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		menuLayout.gravity = Gravity.LEFT;
		menu.setLayoutParams(menuLayout);
		
		layout.addView(menu);

		
		setNativeView(layout);


		// event listener
		layout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				if (proxy.hasListeners("drawerslide")) {
					KrollDict options = new KrollDict();
					options.put("offset", slideOffset);
					proxy.fireEvent("drawerslide", options);
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				if (proxy.hasListeners("draweropen")) {
					KrollDict options = new KrollDict();
					proxy.fireEvent("draweropen", options);
				}
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				if (proxy.hasListeners("drawerclose")) {
					KrollDict options = new KrollDict();
					proxy.fireEvent("drawerclose", options);
				}
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				if (proxy.hasListeners("change")) {
					KrollDict options = new KrollDict();
					options.put("state", newState);
					options.put("idle", (newState == 0 ? 1 : 0));
					options.put("dragging", (newState == 1 ? 1 : 0));
					options.put("settling", (newState == 2 ? 1 : 0));
					proxy.fireEvent("change", options);
				}
			}
		});
	}
	
	public void toggleLeftDrawer() {
		if (layout.isDrawerOpen(Gravity.LEFT)) {
			layout.closeDrawer(Gravity.LEFT);
		} else {
			layout.openDrawer(Gravity.LEFT);
		}
	}
	public void openLeftDrawer() {
		layout.openDrawer(Gravity.LEFT);
	}
	public void closeLeftDrawer() {
		layout.closeDrawer(Gravity.LEFT);
	}

	
	@Override
	public void processProperties(KrollDict d) {
		if (d.containsKey(PROPERTY_LEFT_VIEW)) {
			Object leftView = d.get(PROPERTY_LEFT_VIEW);
			if (leftView != null && leftView instanceof TiViewProxy) {
				if (leftView instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot add window as a child view of other window");
				this.leftView = (TiViewProxy)leftView;
				//
				this.menu.addView(this.leftView.getOrCreateView().getOuterView());
			} else {
				Log.e(TAG, "[ERROR] Invalid type for leftView");
			}
		}
		if (d.containsKey(PROPERTY_CENTER_VIEW)) {
			Object centerView = d.get(PROPERTY_CENTER_VIEW);
			if (centerView != null && centerView instanceof TiViewProxy) {
				if (centerView instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot use window as a child view of other window");
				this.centerView = (TiViewProxy)centerView;
				//
				this.content.addView(this.centerView.getOrCreateView().getOuterView());
			} else {
				Log.e(TAG, "[ERROR] Invalid type for centerView");
			}
		}
		if (d.containsKey(PROPERTY_LEFT_VIEW_WIDTH)) {
			menuWidth = getDevicePixels(d.get(PROPERTY_LEFT_VIEW_WIDTH));
			
			Log.e(TAG, "set menuWidth = " + d.get(PROPERTY_LEFT_VIEW_WIDTH) + " in pixel: " + menuWidth);
			
			menu.getLayoutParams().width = menuWidth;
		}
		
		super.processProperties(d);
	}
	
	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
		
		Log.d(TAG, "propertyChanged  Property: " + key + " old: " + oldValue + " new: " + newValue);
		
		if (key.equals(PROPERTY_LEFT_VIEW)) {
			if (newValue == this.leftView) return;
			TiViewProxy newProxy = null;
			int index = 0;
			if (this.leftView != null) {
				index = this.menu.indexOfChild(this.leftView.getOrCreateView().getNativeView());
			}
			if (newValue != null && newValue instanceof TiViewProxy) {
				if (newValue instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot add window as a child view of other window");
				newProxy = (TiViewProxy)newValue;
				this.menu.addView(newProxy.getOrCreateView().getOuterView(), index);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for leftView");
			}
			if (this.leftView != null) {
				this.menu.removeView(this.leftView.getOrCreateView().getNativeView());
			}
			this.leftView = newProxy;
		}
		else if (key.equals(PROPERTY_CENTER_VIEW)) {
			if (newValue == this.centerView) return;
			TiViewProxy newProxy = null;
			int index = 0;
			if (this.centerView != null) {
				index = this.content.indexOfChild(this.centerView.getOrCreateView().getNativeView());
			}
			if (newValue != null && newValue instanceof TiViewProxy) {
				if (newValue instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot add window as a child view of other window");
				newProxy = (TiViewProxy)newValue;
				this.content.addView(newProxy.getOrCreateView().getOuterView(), index);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for centerView");
			}
			if (this.centerView != null) {
				this.content.removeView(this.centerView.getOrCreateView().getNativeView());
			}
			this.centerView = newProxy;	
		}
		else if (key.equals(PROPERTY_LEFT_VIEW_WIDTH)) {
			menuWidth = getDevicePixels(newValue);
			
			Log.e(TAG, "change menuWidth = " + newValue + " in pixel: " + menuWidth);
			
			LayoutParams menuLayout = new LayoutParams(menuWidth, LayoutParams.MATCH_PARENT);
			menuLayout.gravity = Gravity.LEFT;
			this.menu.setLayoutParams(menuLayout);
			
			// menu.getLayoutParams().width = menuWidth;
		}
		else {
			super.propertyChanged(key, oldValue, newValue, proxy);
		}
	}
	
	// helper
	public int getDevicePixels(Object value) {
		return TiConvert.toTiDimension(TiConvert.toString(value), TiDimension.TYPE_WIDTH).getAsPixels(layout);
	}
	
}
