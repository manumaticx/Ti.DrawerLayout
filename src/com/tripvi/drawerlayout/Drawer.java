package com.tripvi.drawerlayout;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.TiDimension;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;
import org.appcelerator.titanium.view.TiUIView;

import ti.modules.titanium.ui.WindowProxy;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class Drawer extends TiUIView {
	
	private DrawerLayout layout;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private FrameLayout menu; /* left drawer */
	private FrameLayout filter; /* right drawer */
	private int menuWidth;
	private int filterWidth;
	private boolean hasMenu = false;
	private boolean hasFilter = false;
	
	private TiViewProxy leftView;
	private TiViewProxy rightView;
	private TiViewProxy centerView;
	
	
	// Static Properties
	public static final String PROPERTY_LEFT_VIEW = "leftView";
	public static final String PROPERTY_CENTER_VIEW = "centerView";
	public static final String PROPERTY_RIGHT_VIEW = "rightView";
	public static final String PROPERTY_LEFT_WINDOW = "leftWindow";
	public static final String PROPERTY_CENTER_WINDOW = "centerWindow";
	public static final String PROPERTY_RIGHT_WINDOW = "rightWindow";
	public static final String PROPERTY_LEFT_VIEW_WIDTH = "leftDrawerWidth";
	public static final String PROPERTY_RIGHT_VIEW_WIDTH = "rightDrawerWidth";
    public static final String PROPERTY_DRAWER_INDICATOR_ENABLED = "drawerIndicatorEnabled";
	
	private static final String TAG = "TripviDrawer";
	
	int drawable_ic_drawer = 0;
	int string_drawer_open = 0;
	int string_drawer_close = 0;
	int layout_content_fragment = 0;
	int layout_drawer_main = 0;
	int id_content_frame = 0;
	
	
	public Drawer(final DrawerProxy proxy) {
		super(proxy);
		
		try {
			drawable_ic_drawer = TiRHelper.getResource("drawable.ic_drawer");
			string_drawer_open = TiRHelper.getResource("string.drawer_open");
			string_drawer_close = TiRHelper.getResource("string.drawer_close");
			layout_content_fragment = TiRHelper.getResource("layout.content_fragment");
			layout_drawer_main = TiRHelper.getResource("layout.drawer_main");
			id_content_frame = TiRHelper.getResource("id.content_frame");
		}
		catch (ResourceNotFoundException e) {
			Log.e(TAG, "XML resources could not be found!!!");
		}
		
		
		ActionBarActivity activity = (ActionBarActivity) proxy.getActivity();
		
		// DrawerLayout을 생성한다.
		LayoutInflater inflater = LayoutInflater.from(activity);
		layout = (DrawerLayout) inflater.inflate(layout_drawer_main, null, false);
		
		// TiUIView
		setNativeView(layout);

	}
	
	
	
	/**
	* Open/Close/Toggle drawers
	*/
	public void toggleLeftDrawer() {
		if (layout.isDrawerOpen(Gravity.LEFT)) {
			closeLeftDrawer();
		} else {
			openLeftDrawer();
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
			closeRightDrawer();
		} else {
			openRightDrawer();
		}
	}
	public void openRightDrawer() {
		layout.openDrawer(Gravity.RIGHT);
	}
	public void closeRightDrawer() {
		layout.closeDrawer(Gravity.RIGHT);
	}
	
	private void initDrawerToggle() {
		
		ActionBarActivity activity = (ActionBarActivity) proxy.getActivity();
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
		activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		activity.getSupportActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(activity, layout, drawable_ic_drawer, string_drawer_open, string_drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				if (drawerView.equals(menu)){
					super.onDrawerClosed(drawerView);
					if (proxy.hasListeners("drawerclose")) {
						KrollDict options = new KrollDict();
						options.put("drawer", "left");
						proxy.fireEvent("drawerclose", options);
					}
				}
			}
			@Override
			public void onDrawerOpened(View drawerView) {
				if (drawerView.equals(menu)){
					super.onDrawerOpened(drawerView);
					if (proxy.hasListeners("draweropen")) {
						KrollDict options = new KrollDict();
						options.put("drawer", "left");
						proxy.fireEvent("draweropen", options);
					}
				}
			}
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				if (drawerView.equals(menu)){
					super.onDrawerSlide(drawerView, slideOffset);
					if (proxy.hasListeners("drawerslide")) {
						KrollDict options = new KrollDict();
						options.put("offset", slideOffset);
						options.put("drawer", "left");
						proxy.fireEvent("drawerslide", options);
					}
				}
			}
			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
				
				if (proxy.hasListeners("change")) {
					KrollDict options = new KrollDict();
					options.put("state", newState);
					options.put("idle", (newState == 0 ? 1 : 0));
					options.put("dragging", (newState == 1 ? 1 : 0));
					options.put("settling", (newState == 2 ? 1 : 0));
					proxy.fireEvent("change", options);
				}
			}
		};
		// Set the drawer toggle as the DrawerListener
		layout.setDrawerListener(mDrawerToggle);
		
		// onPostCreate 대신에
		layout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});
	}
	
	/**
	* drawer가 필요할때 그때그때 추가
	*/
	private void initLeftDrawer() {
		if (hasMenu) return;
		
		Log.d(TAG, "initializing left drawer");
		
		// menu: left drawer
		menu = new FrameLayout(proxy.getActivity());
		LayoutParams menuLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		menuLayout.gravity = Gravity.LEFT;
		menu.setLayoutParams(menuLayout);
		
		layout.addView(menu);
		
		hasMenu = true;
		
		initDrawerToggle();
	}
	private void initRightDrawer() {
		if (hasFilter) return;
		
		Log.d(TAG, "initializing right drawer");
		
		// filter: right drawer
		filter = new FrameLayout(proxy.getActivity());
		LayoutParams filterLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		filterLayout.gravity = Gravity.RIGHT;
		filter.setLayoutParams(filterLayout);
		
		layout.addView(filter);
		
		hasFilter = true;
	}
	
	
	/**
	* centerView 변경
	*/
	private void replaceCenterView(TiViewProxy viewProxy) {
		if (viewProxy == this.centerView) {
			Log.d(TAG, "centerView was not changed");
			return;
		}
		if (viewProxy == null) {
			return;
		}
		
		// update the main content by replacing fragments
		View contentView = viewProxy.getOrCreateView().getOuterView();
		Fragment fragment = new ContentWrapperFragment(contentView);
		
		FragmentManager fragmentManager = ((ActionBarActivity)proxy.getActivity()).getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(id_content_frame, fragment).commit();
		// fragmentManager.beginTransaction().replace(id_content_frame, fragment).commitAllowingStateLoss();
		
		this.centerView = viewProxy;
	}
	
	public static class ContentWrapperFragment extends Fragment {
		View mContentView;
		
		public ContentWrapperFragment(View cv) {
			mContentView = cv;
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
			v.addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return view;
		}
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
		if (d.containsKey(PROPERTY_LEFT_WINDOW)) {
			Object leftView = d.get(PROPERTY_LEFT_WINDOW);
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
		if (d.containsKey(PROPERTY_RIGHT_WINDOW)) {
			Object rightView = d.get(PROPERTY_RIGHT_WINDOW);
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
				replaceCenterView((TiViewProxy)centerView);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for centerView");
			}
		}
		if (d.containsKey(PROPERTY_CENTER_WINDOW)) {
			Object centerView = d.get(PROPERTY_CENTER_WINDOW);
			if (centerView != null && centerView instanceof TiViewProxy) {
				if (centerView instanceof WindowProxy)
					throw new IllegalStateException("[ERROR] Cannot use window as a child view of other window");
				//
				replaceCenterView((TiViewProxy)centerView);
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
        if (d.containsKey(PROPERTY_DRAWER_INDICATOR_ENABLED)) {
            boolean b = TiConvert.toBoolean(d, PROPERTY_DRAWER_INDICATOR_ENABLED);
            
            if (mDrawerToggle != null){
            	mDrawerToggle.setDrawerIndicatorEnabled(b);
            }
        }
		
		super.processProperties(d);
	}
	
	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
		
		Log.d(TAG, "propertyChanged  Property: " + key + " old: " + oldValue + " new: " + newValue);
		
		if (key.equals(PROPERTY_LEFT_VIEW) || key.equals(PROPERTY_LEFT_WINDOW)) {
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
		else if (key.equals(PROPERTY_RIGHT_VIEW) || key.equals(PROPERTY_RIGHT_WINDOW)) {
			if (newValue == this.rightView) return;
			TiViewProxy newProxy = null;
			int index = 0;
			if (this.rightView != null) {
				index = this.filter.indexOfChild(this.rightView.getOrCreateView().getNativeView());
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
		else if (key.equals(PROPERTY_CENTER_VIEW) || key.equals(PROPERTY_CENTER_WINDOW)) {
			TiViewProxy newProxy = (TiViewProxy) newValue;
			replaceCenterView(newProxy);
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
        else if (key.equals(PROPERTY_DRAWER_INDICATOR_ENABLED)) {
            boolean b = (Boolean)newValue;
            mDrawerToggle.setDrawerIndicatorEnabled(b);
        }
		else {
			super.propertyChanged(key, oldValue, newValue, proxy);
		}
	}
	
	/**
	* Helpers
	*/
	public int getDevicePixels(Object value) {
		return TiConvert.toTiDimension(TiConvert.toString(value), TiDimension.TYPE_WIDTH).getAsPixels(layout);
	}
	
	// private Drawable getDrawableFromUrl(String url)
	// {
	// 	TiUrl imageUrl = new TiUrl((String) url);
	// 	TiFileHelper tfh = new TiFileHelper(TiApplication.getInstance());
	// 	return tfh.loadDrawable(imageUrl.resolve(), false);
	// }
	
}
