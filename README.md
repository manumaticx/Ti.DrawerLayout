Ti.DrawerLayout
===============

Appcelerator Titanium module adding native Android drawer layout support


사용법은 example/app.js를 참조바람


## Quick Start

### Get it [![gitTio](http://gitt.io/badge.png)](http://gitt.io/component/com.tripvi.drawerlayout)
Download the latest distribution ZIP-file from [Releases](https://github.com/Tripvi/Ti.DrawerLayout/releases) and consult the [Titanium Documentation](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module) on how install it, or simply use the [gitTio CLI](http://gitt.io/cli):

`$ gittio install com.tripvi.drawerlayout`

## Example

Ricardo Alcocer has created a demo app: https://github.com/ricardoalcocer/Ti.DrawerLayout-Demo-Alloy-App

## Features

### Drawer indicator ('Hamburger') 
**Titanium SDK >= 3.3.0**

From Titanium SDK 3.3.0 and up it is possible to enable the hamburger button next to the app icon in the actionbar. Just add `drawerIndicatorEnabled` to the configuration object when creating the drawer:

```javascript
var drawer = TiDrawerLayout.createDrawer({
    ...
    drawerIndicatorEnabled: true
});
```

### Native ActionBar  
**version >= 1.1.0**

The ActionBar home Icon `click` event is handled outside of the module, because the Activity should be sub-classed to override onOptionsItemSelected(). To open the left drawer add the following code in the `open` eventlistener function of your Window:

```javascript
win.activity.actionBar.onHomeIconItemSelected = function() {
	drawer.toggleLeftWindow();
}
```

Requires Titanium Mobile SDK >= 3.3.0


### Left and right drawer  
**version >=1.0.2**

Both left and right drawers are supported. To enable right drawer support, set 'rightView' and 'rightDrawerWidth' of drawer view:

```javascript
var drawer = TiDrawerLayout.createDrawer({
    leftView: menuTable,
    centerView: contentView,
	rightView: filterView,
    leftDrawerWidth: "240dp",
	rightDrawerWidth: "120dp",
    width: Ti.UI.FILL,
    height: Ti.UI.FILL
});
```

You can assign these properties at any time. Right drawer will only be created when it is needed. (Same applies to left drawer support).

```javascript
var drawer = TiDrawerLayout.createDrawer({
    leftView: menuTable,
    centerView: contentView,
    leftDrawerWidth: "240dp",
});

drawer.rightView = filterView;
drawer.rightDrawerWidth = "120dp";
```

The `draweropen` event specifies which drawer has been opened:

```javascript
drawer.addEventListener('draweropen', function(e) {
	if (e.drawer == "left") {
        // left drawer is open
	} else if (e.drawer == "right") {
        // right drawer is open
	}
});
```


## Troubleshooting: Android support library version (only for Ti SDK < 3.3.0)

Check android support library v4 version in the titanium sdk installation,
and replace android support v4 library jar file to the latest version.

for osx
~/Library/Application Support/Titanium/mobilesdk/osx/3.2.0.GA/android

the current (2014/april/15) file size of android-support-v4.jar is 648kb


support library would be found in your android sdk path:
{install path}/android/extras/android/support/v4/android-support-v4.jar

PS: Titanium SDK >= 3.3 already use the new android-support-v4.jar


##Building module in Titanium Studio

- Clone repository
- Create .project folder
- Create .apt_generated
- Create build/.apt_generated
- Create .classpath file using .classpath.example as template and:
	- replace {ANDROID_SDK_PATH} by your Android SDK folder
	- replace {APPLICATION_SUPPORT_PATH_WHERE_TITANIUM_LIVES} by appropriated path (ex: /Users/dirlei/Library/Application Support)
	- replace {TITANIUM_SDK_VERSION} by your desired Ti SDK version (ex: 3.3.0.RC)
- Create build.properties using build.properties.example as template and:
	- replace {ANDROID_SDK_PATH} by your Android SDK folder
	- replace {APPLICATION_SUPPORT_PATH_WHERE_TITANIUM_LIVES} by appropriated path (ex: /Users/dirlei/Library/Application Support)
	- replace {TITANIUM_SDK_VERSION} by your desired Ti SDK version (ex: 3.3.0.RC)
- Import project on Titanium Studio (File/Import/Existing Projects into Workspace, select folder where you cloned repository, Finish)
- Build module (select module project, right click, Publish/Package)


## Usage (Alloy)

### index.xml
```
<Alloy>
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
