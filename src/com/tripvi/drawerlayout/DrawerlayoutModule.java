package com.tripvi.drawerlayout;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;

import android.support.v4.widget.DrawerLayout;

@Kroll.module(name = "Drawerlayout", id = "com.tripvi.drawerlayout")
public class DrawerlayoutModule extends KrollModule {
    // constants
    @Kroll.constant public static final int LOCK_MODE_LOCKED_CLOSED = DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    @Kroll.constant public static final int LOCK_MODE_LOCKED_OPEN = DrawerLayout.LOCK_MODE_LOCKED_OPEN;
    @Kroll.constant public static final int LOCK_MODE_UNLOCKED = DrawerLayout.LOCK_MODE_UNLOCKED;

    public DrawerlayoutModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

}
