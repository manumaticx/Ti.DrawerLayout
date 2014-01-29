Ti.DrawerLayout
===============

titanium module: android drawer layout


사용법은 example/app.js를 참조바람


#### demo app: https://github.com/ricardoalcocer/Ti.DrawerLayout-Demo-Alloy-App


Trouble Shooting: android support library version
=================================================

Check android support library v4 version in the titanium sdk installation,
and replace android support v4 library jar file to the latest version.

for osx
~/Library/Application Support/Titanium/mobilesdk/osx/3.2.0.GA/android

the file size of android-support-v4.jar is 554kb
(old version is 340kb)


support library would be found in your android sdk path:
{install path}/android/extras/android/support/v4/android-support-v4.jar




## Usage (Alloy)

### index.xml
```
</Alloy>
    <Window id="mainWindow" platform="android" navBarHidden="true">
    	<!-- add header bar here -->
    </Window>
</Alloy>
```

### menu.xml
```
<Alloy>
	<TableView id="menuTable">
		<TableViewRow>Item 1</TableViewRow>
		<TableViewRow>Item 2</TableViewRow>
	</TableView>
</Alloy>
```
### main.xml
```
<Alloy>
	<View>
		<!-- main page content here -->
	</View>
</Alloy>
```

### index.js
```
// Android only
if (OS_ANDROID) {
	// Load module
	var TiDrawerLayout = require('com.tripvi.drawerlayout');
	
	// define menu and main content view
	var menuTable = Alloy.createController('menu').getView();
	var contentView = Alloy.createController('main').getView();
	
	var drawer = TiDrawerLayout.createDrawer({
	        leftView: menuTable,
	        centerView: contentView,
	        leftDrawerWidth: "240dp",
	        width: Ti.UI.FILL,
	        height: Ti.UI.FILL
	});

	drawer.addEventListener('draweropen', function(e) {
	        // drawer is open
	});

	drawer.addEventListener('drawerclose', function(e) {
	        // drawer is closed
	});
	
	drawer.addEventListener('drawerslide', function(e) {
			// drawer is sliding
	        // slide offset: e.offset
	});
	
	$.mainWindow.add(drawer);
	$.mainWindow.open();
}
```
