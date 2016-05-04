package com.tripvi.drawerlayout;

import android.support.v7.widget.Toolbar;
import android.view.*;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.TiDimension;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;
import org.appcelerator.titanium.view.TiCompositeLayout;
import org.appcelerator.titanium.view.TiUIView;

import ti.modules.titanium.ui.WindowProxy;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.widget.FrameLayout;
import java.lang.reflect.Field;

public class Drawer extends TiUIView {

	private DrawerLayout layout;
	private ActionBarDrawerToggle mDrawerToggle;

	private FrameLayout menu; /* left drawer */
	private FrameLayout filter; /* right drawer */
	private int menuWidth;
	private int filterWidth;
	private boolean hasMenu = false;
	private boolean hasFilter = false;
	private boolean hasToggle = true;
	private boolean hideToolbar = false;

	private TiViewProxy leftView;
	private TiViewProxy rightView;
	private TiViewProxy centerView;
	private Toolbar toolbar;

	// Static Properties
	public static final String PROPERTY_LEFT_VIEW = "leftView";
	public static final String PROPERTY_CENTER_VIEW = "centerView";
	public static final String PROPERTY_RIGHT_VIEW = "rightView";
	public static final String PROPERTY_LEFT_VIEW_WIDTH = "leftDrawerWidth";
	public static final String PROPERTY_RIGHT_VIEW_WIDTH = "rightDrawerWidth";
	public static final String PROPERTY_DRAWER_INDICATOR_ENABLED = "drawerIndicatorEnabled";
	public static final String PROPERTY_DRAWER_INDICATOR_IMAGE = "drawerIndicatorImage";
	public static final String PROPERTY_DRAWER_LOCK_MODE = "drawerLockMode";
	public static final String PROPERTY_HIDE_TOOLBAR = "hideToolbar";
	public static final String PROPERTY_SWIPE_AREA_WIDTH = "dragMargin";

	private static final String TAG = "TripviDrawer";

	int string_drawer_open = 0;
	int string_drawer_close = 0;
	int layout_drawer_main = 0;
	public static int id_content_frame = 0;
	public static int id_main_container = 0;
	public static int id_toolbar = 0;

	public Drawer(final DrawerProxy proxy) {
		super(proxy);

		try {
			string_drawer_open = TiRHelper.getResource("string.drawer_open");
			string_drawer_close = TiRHelper.getResource("string.drawer_close");
			layout_drawer_main = TiRHelper.getResource("layout.drawer_main");
			id_content_frame = TiRHelper.getResource("id.content_frame");
			id_main_container = TiRHelper.getResource("id.main_container");
			id_toolbar = TiRHelper.getResource("id.toolbar");
		} catch (ResourceNotFoundException e) {
			Log.e(TAG, "XML resources could not be found!!!");
		}

		AppCompatActivity activity = (AppCompatActivity) proxy.getActivity();

		// DrawerLayout
		LayoutInflater inflater = LayoutInflater.from(activity);
		layout = (DrawerLayout) inflater.inflate(layout_drawer_main, null,
				false);

		layout.setDrawerListener(new DrawerListener());

		toolbar = (Toolbar)layout.findViewById(id_toolbar);
		// If no actionbar exists,
		if (activity.getSupportActionBar() == null && activity.getActionBar() == null) {
			activity.setSupportActionBar(toolbar);
			if (!hideToolbar) {
				setToolbarVisible(true);
			}
		}

		// TiUIView
		setNativeView(layout);

	}

	private void setToolbarVisible(boolean isVisible) {
		if (isVisible) {
			toolbar.setVisibility(View.VISIBLE);
		} else {
			toolbar.setVisibility(View.GONE);
		}
	}

	private class DrawerListener implements DrawerLayout.DrawerListener {

		@Override
		public void onDrawerClosed(View drawerView) {
			if (proxy.hasListeners("drawerclose")) {
				KrollDict options = new KrollDict();
				if (drawerView.equals(menu)) {
					options.put("drawer", "left");
				} else if (drawerView.equals(filter)) {
					options.put("drawer", "right");
				}
				proxy.fireEvent("drawerclose", options);
			}
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			if (proxy.hasListeners("draweropen")) {
				KrollDict options = new KrollDict();
				if (drawerView.equals(menu)) {
					options.put("drawer", "left");
				} else if (drawerView.equals(filter)) {
					options.put("drawer", "right");
				}
				proxy.fireEvent("draweropen", options);
			}
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			if (proxy.hasListeners("drawerslide")) {
				KrollDict options = new KrollDict();
				options.put("offset", slideOffset);
				if (drawerView.equals(menu)) {
					options.put("drawer", "left");
				} else if (drawerView.equals(filter)) {
					options.put("drawer", "right");
				}
				proxy.fireEvent("drawerslide", options);
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
	}

	/**
	 * Open/Close/Toggle drawers
	 */
	public void toggleLeftDrawer() {
		if (layout.isDrawerOpen(Gravity.START)) {
			closeLeftDrawer();
		} else {
			openLeftDrawer();
		}
	}

	public void openLeftDrawer() {
		layout.openDrawer(Gravity.START);
	}

	public void closeLeftDrawer() {
		layout.closeDrawer(Gravity.START);
	}

	public void toggleRightDrawer() {
		if (layout.isDrawerOpen(Gravity.END)) {
			closeRightDrawer();
		} else {
			openRightDrawer();
		}
	}

	public void openRightDrawer() {
		layout.openDrawer(Gravity.END);
	}

	public void closeRightDrawer() {
		layout.closeDrawer(Gravity.END);
	}

	public boolean isLeftDrawerOpen() {
		return layout.isDrawerOpen(Gravity.START);
	}

	public boolean isRightDrawerOpen() {
		return layout.isDrawerOpen(Gravity.END);
	}

	public boolean isLeftDrawerVisible() {
		return layout.isDrawerVisible(Gravity.START);
	}

	public boolean isRightDrawerVisible() {
		return layout.isDrawerVisible(Gravity.END);
	}

	private void initDrawerToggle() {

		AppCompatActivity activity = (AppCompatActivity) proxy.getActivity();

		if (activity.getSupportActionBar() == null) {
			return;
		}

		// enable ActionBar app icon to behave as action to toggle nav
		// drawer
		activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		activity.getSupportActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(activity, layout,
				string_drawer_open, string_drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				if(!drawerView.equals(menu)){
					return;
				}
				super.onDrawerClosed(drawerView);
				if (proxy.hasListeners("drawerclose")) {
					KrollDict options = new KrollDict();
					if (drawerView.equals(menu)) {
						options.put("drawer", "left");
					} else if (drawerView.equals(filter)) {
						options.put("drawer", "right");
					}
					proxy.fireEvent("drawerclose", options);
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				if(!drawerView.equals(menu)){
					return;
				}
				super.onDrawerOpened(drawerView);
				if (proxy.hasListeners("draweropen")) {
					KrollDict options = new KrollDict();
					if (drawerView.equals(menu)) {
						options.put("drawer", "left");
					} else if (drawerView.equals(filter)) {
						options.put("drawer", "right");
					}
					proxy.fireEvent("draweropen", options);
				}
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				
				if(!drawerView.equals(menu)){
					return;
				}
				
				super.onDrawerSlide(drawerView, slideOffset);
				if (proxy.hasListeners("drawerslide")) {
					KrollDict options = new KrollDict();
					options.put("offset", slideOffset);
					if (drawerView.equals(menu)) {
						options.put("drawer", "left");
					} else if (drawerView.equals(filter)) {
						options.put("drawer", "right");
					}
					proxy.fireEvent("drawerslide", options);
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

		// onPostCreate
		layout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});
	}

	/**
	 * drawer
	 */
	private void initLeftDrawer() {
		if (hasMenu) {
			return;
		}

		Log.d(TAG, "initializing left drawer");

		// menu: left drawer
		menu = new FrameLayout(proxy.getActivity());
		LayoutParams menuLayout = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		menuLayout.gravity = Gravity.START;
		menu.setLayoutParams(menuLayout);

		layout.addView(menu);

		hasMenu = true;

		if (hasToggle) {
			initDrawerToggle();
		}
	}

	private void initRightDrawer() {
		if (hasFilter) {
			return;
		}

		Log.d(TAG, "initializing right drawer");

		// filter: right drawer
		filter = new FrameLayout(proxy.getActivity());
		LayoutParams filterLayout = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		filterLayout.gravity = Gravity.END;
		filter.setLayoutParams(filterLayout);

		layout.addView(filter);

		hasFilter = true;
	}

	/**
	 * centerView
	 */
	public void replaceCenterView(TiViewProxy viewProxy) {
		if (viewProxy == this.centerView) {
			Log.d(TAG, "centerView was not changed");
			return;
		}
		if (viewProxy == null) {
			return;
		}

		viewProxy.setActivity(proxy.getActivity());
		TiUIView contentView = viewProxy.getOrCreateView();

		View view = contentView.getOuterView();
		TiCompositeLayout fL = (TiCompositeLayout)layout.findViewById(id_content_frame);
		ViewParent viewParent = view.getParent();
		if (viewParent == null) {
			fL.addView(view, contentView.getLayoutParams());
		}
		if (viewParent instanceof ViewGroup && viewParent != fL) {
			((ViewGroup)viewParent).removeView(view);
			fL.addView(view, contentView.getLayoutParams());
		}
		if (this.centerView != null) {
			fL.removeView(this.centerView.getOrCreateView().getNativeView());
		}
		this.centerView = viewProxy;
	}
	
	public void setArrowState (Float state){
		// leaving this here for now, maybe replace it later
	}
	
	private void setSwipeArea (Integer width){
		try{
			Field mDragger = layout.getClass().getDeclaredField("mLeftDragger");
			mDragger.setAccessible(true);
			ViewDragHelper draggerObj = (ViewDragHelper) mDragger.get(layout);
			Field mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
			mEdgeSize.setAccessible(true);
			mEdgeSize.setInt(draggerObj, width);
		}catch(NoSuchFieldException e){
			Log.e(TAG, e.toString());
		}catch(IllegalAccessException e){
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public void processProperties(KrollDict d) {
		if (d.containsKey(PROPERTY_DRAWER_INDICATOR_ENABLED)) {
			hasToggle = TiConvert.toBoolean(d,
					PROPERTY_DRAWER_INDICATOR_ENABLED);
		}

		if (d.containsKey(PROPERTY_LEFT_VIEW)) {
			Object leftView = d.get(PROPERTY_LEFT_VIEW);
			if (leftView != null && leftView instanceof TiViewProxy) {
				if (leftView instanceof WindowProxy)
					throw new IllegalStateException(
							"[ERROR] Cannot add window as a child view of other window");
				//
				this.leftView = (TiViewProxy) leftView;
				this.initLeftDrawer();
				this.menu.addView(getNativeView(this.leftView));
			} else {
				Log.e(TAG, "[ERROR] Invalid type for leftView");
			}
		}
		if (d.containsKey(PROPERTY_RIGHT_VIEW)) {
			Object rightView = d.get(PROPERTY_RIGHT_VIEW);
			if (rightView != null && rightView instanceof TiViewProxy) {
				if (rightView instanceof WindowProxy)
					throw new IllegalStateException(
							"[ERROR] Cannot add window as a child view of other window");
				this.rightView = (TiViewProxy) rightView;
				this.initRightDrawer();
				this.filter.addView(getNativeView(this.rightView));
			} else {
				Log.e(TAG, "[ERROR] Invalid type for rightView");
			}
		}
		if (d.containsKey(PROPERTY_CENTER_VIEW)) {
			Object centerView = d.get(PROPERTY_CENTER_VIEW);
			if (centerView != null && centerView instanceof TiViewProxy) {
				if (centerView instanceof WindowProxy)
					throw new IllegalStateException(
							"[ERROR] Cannot use window as a child view of other window");
				replaceCenterView((TiViewProxy) centerView);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for centerView");
			}
		}
		if (d.containsKey(PROPERTY_LEFT_VIEW_WIDTH)) {
			
			if (menu != null){
				if (d.get(PROPERTY_LEFT_VIEW_WIDTH).equals(TiC.LAYOUT_SIZE)) {
					menu.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
				} else if (d.get(PROPERTY_LEFT_VIEW_WIDTH).equals(TiC.LAYOUT_FILL)) {
					menu.getLayoutParams().width = LayoutParams.MATCH_PARENT;
				} else if (!d.get(PROPERTY_LEFT_VIEW_WIDTH).equals(TiC.SIZE_AUTO)) {
					menuWidth = getDevicePixels(d.get(PROPERTY_LEFT_VIEW_WIDTH));
					menu.getLayoutParams().width = menuWidth;
				}	
			}
		} else {
			if (menu != null){
				menu.getLayoutParams().width = LayoutParams.MATCH_PARENT;
			}
		}
		if (d.containsKey(PROPERTY_RIGHT_VIEW_WIDTH)) {
			
			if (filter != null){
				if (d.get(PROPERTY_RIGHT_VIEW_WIDTH).equals(TiC.LAYOUT_SIZE)) {
					filter.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
				} else if (d.get(PROPERTY_RIGHT_VIEW_WIDTH).equals(TiC.LAYOUT_FILL)) {
					filter.getLayoutParams().width = LayoutParams.MATCH_PARENT;
				} else if (!d.get(PROPERTY_RIGHT_VIEW_WIDTH).equals(TiC.SIZE_AUTO)) {
					filterWidth = getDevicePixels(d.get(PROPERTY_RIGHT_VIEW_WIDTH));
					filter.getLayoutParams().width = filterWidth;
				}
			}
		} else {
			if (filter != null){
				filter.getLayoutParams().width = LayoutParams.MATCH_PARENT;
			}
		}
		if (d.containsKey(PROPERTY_DRAWER_LOCK_MODE)) {
			layout.setDrawerLockMode(TiConvert.toInt(d
					.get(PROPERTY_DRAWER_LOCK_MODE)));
		}
		if (d.containsKey(PROPERTY_HIDE_TOOLBAR)) {
			hideToolbar = (TiConvert.toBoolean(d.get(PROPERTY_HIDE_TOOLBAR)));
			if (hideToolbar) {
				setToolbarVisible(false);
			} else {
				setToolbarVisible(true);
			}
		}
		if (d.containsKey(PROPERTY_SWIPE_AREA_WIDTH)){
			setSwipeArea(getDevicePixels(d.get(PROPERTY_SWIPE_AREA_WIDTH)));
		}

		super.processProperties(d);
	}

	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue,
			KrollProxy proxy) {

		Log.d(TAG, "propertyChanged  Property: " + key + " old: " + oldValue
				+ " new: " + newValue);

		if (key.equals(PROPERTY_LEFT_VIEW)) {
			if (newValue == this.leftView)
				return;
			TiViewProxy newProxy = null;
			int index = 0;
			if (this.leftView != null) {
				index = this.menu.indexOfChild(this.leftView.getOrCreateView()
						.getNativeView());
			}
			if (newValue != null && newValue instanceof TiViewProxy) {
				if (newValue instanceof WindowProxy)
					throw new IllegalStateException(
							"[ERROR] Cannot add window as a child view of other window");
				newProxy = (TiViewProxy) newValue;
				initLeftDrawer();
				this.menu.addView(newProxy.getOrCreateView().getOuterView(),
						index);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for leftView");
			}
			if (this.leftView != null) {
				this.menu.removeView(this.leftView.getOrCreateView()
						.getNativeView());
			}
			this.leftView = newProxy;
		} else if (key.equals(PROPERTY_RIGHT_VIEW)) {
			if (newValue == this.rightView)
				return;
			TiViewProxy newProxy = null;
			int index = 0;
			if (this.rightView != null) {
				index = this.filter.indexOfChild(this.rightView
						.getOrCreateView().getNativeView());
			}
			if (newValue != null && newValue instanceof TiViewProxy) {
				if (newValue instanceof WindowProxy)
					throw new IllegalStateException(
							"[ERROR] Cannot add window as a child view of other window");
				newProxy = (TiViewProxy) newValue;
				initRightDrawer();
				this.filter.addView(newProxy.getOrCreateView().getOuterView(),
						index);
			} else {
				Log.e(TAG, "[ERROR] Invalid type for rightView");
			}
			if (this.rightView != null) {
				this.filter.removeView(this.rightView.getOrCreateView()
						.getNativeView());
			}
			this.rightView = newProxy;
		} else if (key.equals(PROPERTY_CENTER_VIEW)) {
			TiViewProxy newProxy = (TiViewProxy) newValue;
			replaceCenterView(newProxy);
		} else if (key.equals(PROPERTY_LEFT_VIEW_WIDTH)) {
			
			if (menu == null){
				return;
			}
			
			initLeftDrawer();
			
			if (newValue.equals(TiC.LAYOUT_SIZE)) {
				menuWidth = LayoutParams.WRAP_CONTENT;
			} else if (newValue.equals(TiC.LAYOUT_FILL)) {
				menuWidth = LayoutParams.MATCH_PARENT;
			} else if (!newValue.equals(TiC.SIZE_AUTO)) {
				menuWidth = getDevicePixels(newValue);
			}

			LayoutParams menuLayout = new LayoutParams(menuWidth,
					LayoutParams.MATCH_PARENT);
			menuLayout.gravity = Gravity.START;
			this.menu.setLayoutParams(menuLayout);
			
		} else if (key.equals(PROPERTY_RIGHT_VIEW_WIDTH)) {
			
			if (filter == null){
				return;
			}
			
			initRightDrawer();
			
			if (newValue.equals(TiC.LAYOUT_SIZE)) {
				filterWidth = LayoutParams.WRAP_CONTENT;
			} else if (newValue.equals(TiC.LAYOUT_FILL)) {
				filterWidth = LayoutParams.MATCH_PARENT;
			} else if (!newValue.equals(TiC.SIZE_AUTO)) {
				filterWidth = getDevicePixels(newValue);
			}

			LayoutParams filterLayout = new LayoutParams(filterWidth,
					LayoutParams.MATCH_PARENT);
			filterLayout.gravity = Gravity.END;
			this.filter.setLayoutParams(filterLayout);
			
		} else if (key.equals(PROPERTY_DRAWER_LOCK_MODE)) {
			layout.setDrawerLockMode(TiConvert.toInt(newValue));
		} else if (key.equals(PROPERTY_DRAWER_INDICATOR_ENABLED)) {
			boolean b = (Boolean) newValue;
			if (mDrawerToggle != null){
				mDrawerToggle.setDrawerIndicatorEnabled(b);
			}
		} else if (key.equals(PROPERTY_HIDE_TOOLBAR)) {
			hideToolbar = (TiConvert.toBoolean(newValue));
			if (hideToolbar) {
				setToolbarVisible(false);
			} else {
				setToolbarVisible(true);
			}
		} else if (key.equals(PROPERTY_SWIPE_AREA_WIDTH)){
			setSwipeArea(getDevicePixels(newValue));
		} else {
			super.propertyChanged(key, oldValue, newValue, proxy);
		}
	}

	/**
	 * Helpers
	 */
	public int getDevicePixels(Object value) {
		TiDimension nativeSize = TiConvert.toTiDimension(TiConvert.toString(value), TiDimension.TYPE_WIDTH);
		return nativeSize.getAsPixels(layout);
	}

	private View getNativeView(TiViewProxy viewProxy) {
		View nativeView = viewProxy.getOrCreateView().getOuterView();
		ViewGroup parentViewGroup = (ViewGroup) nativeView.getParent();
		if (parentViewGroup != null) {
			parentViewGroup.removeAllViews();
		}
		return nativeView;
	}

}
