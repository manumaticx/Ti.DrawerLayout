# Ti.DrawerLayout
Native Android [Navigation Drawer](http://developer.android.com/design/patterns/navigation-drawer.html) for [Titanium](http://www.appcelerator.com/titanium/)

_This is a fork of [Tripvi/Ti.DrawerLayout](https://github.com/Tripvi/Ti.DrawerLayout)_

## Overview

<a href="https://www.google.com/design/spec/patterns/navigation-drawer.html"><img src="https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0Bzhp5Z4wHba3WUpVTktSZWk1YjQ/patterns_navdrawer_behavior_temporary2.png" width="480"></a>

This module adds support for using the [DrawerLayout](http://developer.android.com/reference/android/support/v4/widget/DrawerLayout.html) in Titanium Apps.

The Drawer Layout is a view that can be pulled from the edge of a window. This can answer various purposes. The most common use case is the Navigation Drawer as seen in the above screenshot. The Navigation Drawer displays navigation options in a drawer which slides in from the left edge.

To expand the drawer the user can either touch the app icon or swipe from the left edge. The navigation drawer overlays the content but not the action bar.

#### [API Documentation](documentation/index.md)
#### [Demo App](https://github.com/manumaticx/NavigationDrawer-Demo)

## Installation

* Grab the latest package from the [dist](dist) folder
* Install it following [this guide](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module)
* with [gittio](http://gitt.io/): `$ gittio install -g https://github.com/manumaticx/Ti.DrawerLayout/blob/master/dist/com.tripvi.drawerlayout-android-1.3.4.zip?raw=true`

## Usage

Here's an example of how to use the module. Please note the links for Demo App and API Docs below.

```javascript
// Load module
var TiDrawerLayout = require('com.tripvi.drawerlayout');

// define left and center view
var leftView = Ti.UI.createView({backgroundColor:'red'});
var centerView = Ti.UI.createView({backgroundColor:'green'});

// create the Drawer
var drawer = TiDrawerLayout.createDrawer({
    leftView: leftView,
    centerView: centerView,
    leftDrawerWidth: "240dp",
    width: Ti.UI.FILL,
    height: Ti.UI.FILL
});

// create a window
var win = Ti.UI.createWindow();

// add the drawer to the window
win.add(drawer);

// listen for the open event...
win.addEventListener('open', function(){
    
    // ...to access activity and action bar
    var activity = win.getActivity();
    var actionbar = activity.getActionBar();
    
    if (actionbar){
    
        // this makes the drawer indicator visible in the action bar
        actionbar.displayHomeAsUp = true;
        
        // open and close with the app icon
        actionbar.onHomeIconItemSelected = function() {
            drawer.toggleLeftWindow();
        };
    }
});

// open the window
win.open();
```


