package com.tripvi.drawerlayout;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.TiDimension;
import android.util.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import ti.modules.titanium.ui.WindowProxy;

import android.app.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


public class Drawer extends TiUIView {

	private DrawerLayout layout;
	private FrameLayout content;
	private FrameLayout menu;
	private FrameLayout filter;
	private TiViewProxy leftView;
	private TiViewProxy rightView;
	private TiViewProxy centerView;
	private int menuWidth;
	private int filterWidth;
	
	private boolean hasMenu = false;
	private boolean hasFilter = false;
	
	// Static Properties
	public static final String PROPERTY_LEFT_VIEW = "leftView";
	public static final String PROPERTY_CENTER_VIEW = "centerView";
	public static final String PROPERTY_RIGHT_VIEW = "rightView";
	public static final String PROPERTY_LEFT_VIEW_WIDTH = "leftDrawerWidth";
	public static final String PROPERTY_RIGHT_VIEW_WIDTH = "rightDrawerWidth";
	
	
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
		
		
		// menu: left drawer
		menu = new FrameLayout(activity);
		LayoutParams menuLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		menuLayout.gravity = Gravity.LEFT;
		menu.setLayoutParams(menuLayout);
		
		
		// filter: right drawer
		filter = new FrameLayout(activity);
		LayoutParams filterLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		filterLayout.gravity = Gravity.RIGHT;
		filter.setLayoutParams(filterLayout);
		
		
		// menu and filter will be added when they are needed
		
		setNativeView(layout);


		// event listener
		layout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				if (proxy.hasListeners("drawerslide")) {
					KrollDict options = new KrollDict();
					options.put("offset", slideOffset);
					if (drawerView == menu) {
						options.put("drawer", "left");
					} else if (drawerView == filter) {
						options.put("drawer", "right");
					}
					proxy.fireEvent("drawerslide", options);
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				if (proxy.hasListeners("draweropen")) {
					KrollDict options = new KrollDict();
					if (drawerView == menu) {
						options.put("drawer", "left");
					} else if (drawerView == filter) {
						options.put("drawer", "right");
					}
					proxy.fireEvent("draweropen", options);
				}
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				if (proxy.hasListeners("drawerclose")) {
					KrollDict options = new KrollDict();
					if (drawerView == menu) {
						options.put("drawer", "left");
					} else if (drawerView == filter) {
						options.put("drawer", "right");
					}
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
	public void toggleRightDrawer() {
		if (layout.isDrawerOpen(Gravity.RIGHT)) {
			layout.closeDrawer(Gravity.RIGHT);
		} else {
			layout.openDrawer(Gravity.RIGHT);
		}
	}
	public void openRightDrawer() {
		layout.openDrawer(Gravity.RIGHT);
	}
	public void closeRightDrawer() {
		layout.closeDrawer(Gravity.RIGHT);
	}


	// drawer가 필요할때 그때그때 추가
	private void initLeftDrawer() {
		if (hasMenu) return;
		
		Log.d(TAG, "initialized left drawer");
		
		layout.addView(menu);
		hasMenu = true;
	}
	private void initRightDrawer() {
		if (hasFilter) return;
		
		Log.d(TAG, "initialized right drawer");
		
		layout.addView(filter);
		hasFilter = true;
	}


	
	@Override
	public void processProperties(KrollDict d) {
		if (d.containsKey(PROPERTY_LEFT_VIEW)) {
			Object leftView = d.get(PROPERTY_LEFT_VIEW);
			if (leftView != null && leftView instanceof TiViewProxy) {
				if (leftView instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot add window as a child view of other window");
				//
				this.leftView = (TiViewProxy)leftView;
				this.initLeftDrawer();
				this.menu.addView(this.leftView.getOrCreateView().getOuterView());
			} else {
				Log.e(TAG, "[ERROR] Invalid type for leftView");
			}
		}
		if (d.containsKey(PROPERTY_RIGHT_VIEW)) {
			Object rightView = d.get(PROPERTY_RIGHT_VIEW);
			if (rightView != null && rightView instanceof TiViewProxy) {
				if (rightView instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot add window as a child view of other window");
				//
				this.rightView = (TiViewProxy)rightView;
				this.initRightDrawer();
				this.filter.addView(this.rightView.getOrCreateView().getOuterView());
			} else {
				Log.e(TAG, "[ERROR] Invalid type for rightView");
			}
		}
		if (d.containsKey(PROPERTY_CENTER_VIEW)) {
			Object centerView = d.get(PROPERTY_CENTER_VIEW);
			if (centerView != null && centerView instanceof TiViewProxy) {
				if (centerView instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot use window as a child view of other window");
				//
				this.centerView = (TiViewProxy)centerView;
				this.content.addView(this.centerView.getOrCreateView().getOuterView());
			} else {
				Log.e(TAG, "[ERROR] Invalid type for centerView");
			}
		}
		if (d.containsKey(PROPERTY_LEFT_VIEW_WIDTH)) {
			menuWidth = getDevicePixels(d.get(PROPERTY_LEFT_VIEW_WIDTH));
			
			Log.d(TAG, "set menuWidth = " + d.get(PROPERTY_LEFT_VIEW_WIDTH) + " in pixel: " + menuWidth);
			
			menu.getLayoutParams().width = menuWidth;
		}
		if (d.containsKey(PROPERTY_RIGHT_VIEW_WIDTH)) {
			filterWidth = getDevicePixels(d.get(PROPERTY_RIGHT_VIEW_WIDTH));
			
			Log.d(TAG, "set filterWidth = " + d.get(PROPERTY_RIGHT_VIEW_WIDTH) + " in pixel: " + filterWidth);
			
			filter.getLayoutParams().width = filterWidth;
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
				initLeftDrawer();
				this.menu.addView(newProxy.getOrCreateView().getOuterView(), index);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for leftView");
			}
			if (this.leftView != null) {
				this.menu.removeView(this.leftView.getOrCreateView().getNativeView());
			}
			this.leftView = newProxy;
		}
		else if (key.equals(PROPERTY_RIGHT_VIEW)) {
			if (newValue == this.rightView) return;
			TiViewProxy newProxy = null;
			int index = 0;
			if (this.rightView != null) {
				index = this.menu.indexOfChild(this.rightView.getOrCreateView().getNativeView());
			}
			if (newValue != null && newValue instanceof TiViewProxy) {
				if (newValue instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot add window as a child view of other window");
				newProxy = (TiViewProxy)newValue;
				initRightDrawer();
				this.filter.addView(newProxy.getOrCreateView().getOuterView(), index);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for rightView");
			}
			if (this.rightView != null) {
				this.filter.removeView(this.rightView.getOrCreateView().getNativeView());
			}
			this.rightView = newProxy;
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
			
			Log.d(TAG, "change menuWidth = " + newValue + " in pixel: " + menuWidth);
			
			initLeftDrawer();
			
			LayoutParams menuLayout = new LayoutParams(menuWidth, LayoutParams.MATCH_PARENT);
			menuLayout.gravity = Gravity.LEFT;
			this.menu.setLayoutParams(menuLayout);
		}
		else if (key.equals(PROPERTY_RIGHT_VIEW_WIDTH)) {
			filterWidth = getDevicePixels(newValue);
			
			Log.d(TAG, "change filterWidth = " + newValue + " in pixel: " + filterWidth);
			
			initRightDrawer();
			
			LayoutParams filterLayout = new LayoutParams(filterWidth, LayoutParams.MATCH_PARENT);
			filterLayout.gravity = Gravity.RIGHT;
			this.filter.setLayoutParams(filterLayout);
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
