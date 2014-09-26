package com.tripvi.drawerlayout;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;

@Kroll.module(name = "Drawerlayout", id = "com.tripvi.drawerlayout")
public class DrawerlayoutModule extends KrollModule {
	public DrawerlayoutModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

}
