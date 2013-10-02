package com.tripvi.drawerlayout;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiCompositeLayout;
import org.appcelerator.titanium.view.TiCompositeLayout.LayoutArrangement;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;
import android.os.Message;


@Kroll.proxy(creatableInModule=DrawerlayoutModule.class)
public class DrawerProxy extends TiViewProxy {
	
	private static final String TAG = "TripviDrawerProxy";
	
	private static final int MSG_FIRST_ID = TiViewProxy.MSG_LAST_ID + 1;

	private static final int MSG_TOGGLE_LEFT_VIEW = MSG_FIRST_ID + 100;
	private static final int MSG_TOGGLE_RIGHT_VIEW = MSG_FIRST_ID + 101;
	private static final int MSG_OPEN_LEFT_VIEW = MSG_FIRST_ID + 102;
	private static final int MSG_OPEN_RIGHT_VIEW = MSG_FIRST_ID + 103;
	private static final int MSG_CLOSE_LEFT_VIEW = MSG_FIRST_ID + 104;
	private static final int MSG_CLOSE_RIGHT_VIEW = MSG_FIRST_ID + 105;
	private static final int MSG_CLOSE_VIEWS = MSG_FIRST_ID + 106;

	protected static final int MSG_LAST_ID = MSG_FIRST_ID + 999;
	
	private Drawer drawer;
	
	
	public DrawerProxy() {
		super();
	}

	@Override
	public TiUIView createView(Activity activity) {
		drawer = new Drawer(this);
		return drawer;
	}
	
	
	// UI를 직접 제어하려면, View를 생성한 Thread에서만 가능함
	// 따라서 TiUIThread에서 처리하도록 Message를 보냄
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case MSG_TOGGLE_LEFT_VIEW: {
				handleToggleLeftView();
				return true;
			}
			case MSG_OPEN_LEFT_VIEW: {
				handleOpenLeftView();
				return true;
			}
			case MSG_CLOSE_LEFT_VIEW: {
				handleCloseLeftView();
				return true;
			}
			default : {
				return super.handleMessage(msg);
			}
		}
	}
	
	private void handleToggleLeftView() {
		drawer.toggleLeftDrawer();
	}
	private void handleOpenLeftView() {
		drawer.openLeftDrawer();
	}
	private void handleCloseLeftView() {
		drawer.closeLeftDrawer();
	}


	@Kroll.method
	public void toggleLeftWindow(@Kroll.argument(optional = true) Object obj) {
		if (TiApplication.isUIThread()) {
			handleToggleLeftView();
			return;
		}
		Message message = getMainHandler().obtainMessage(MSG_TOGGLE_LEFT_VIEW);
		message.sendToTarget();
	}

	@Kroll.method
	public void openLeftWindow() {
		if (TiApplication.isUIThread()) {
			handleOpenLeftView();
			return;
		}
		Message message = getMainHandler().obtainMessage(MSG_OPEN_LEFT_VIEW);
		message.sendToTarget();
	}

	@Kroll.method
	public void closeLeftWindow() {
		if (TiApplication.isUIThread()) {
			handleCloseLeftView();
			return;
		}
		Message message = getMainHandler().obtainMessage(MSG_CLOSE_LEFT_VIEW);
		message.sendToTarget();
	}
	

	@Kroll.method @Kroll.setProperty
	public void setLeftDrawerWidth(Object arg) {
		setPropertyAndFire(Drawer.PROPERTY_LEFT_VIEW_WIDTH, arg);
	}
	
	@Kroll.method @Kroll.setProperty
	public void setLeftView(Object arg) {
		setPropertyAndFire(Drawer.PROPERTY_LEFT_VIEW, arg);
	}
	
	@Kroll.method @Kroll.setProperty
	public void setCenterView(Object arg) {
		setPropertyAndFire(Drawer.PROPERTY_CENTER_VIEW, arg);
	}

}
