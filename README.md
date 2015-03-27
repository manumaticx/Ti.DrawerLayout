# Ti.DrawerLayout [![Titanium](http://www-static.appcelerator.com/badges/titanium-git-badge-sq.png)](http://www.appcelerator.com/titanium/)

[![Join the chat at https://gitter.im/manumaticx/Ti.DrawerLayout](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/manumaticx/Ti.DrawerLayout?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

> Native Android [Navigation Drawer](http://developer.android.com/design/patterns/navigation-drawer.html) for [Titanium](http://www.appcelerator.com/titanium/)

_This is a fork of [Tripvi/Ti.DrawerLayout](https://github.com/Tripvi/Ti.DrawerLayout). I temporarily picked it up since the author is busy these days. I've activated issues and pull requests here, so feel free to contribute._

## Overview

![](https://developer.android.com/design/media/navigation_drawer_holo_dark_light.png)

This module adds support for using the [DrawerLayout](http://developer.android.com/reference/android/support/v4/widget/DrawerLayout.html) in Titanium Apps.

The Drawer Layout is a view that can be pulled from the edge of a window. This can answer various purposes. The most common use case is the Navigation Drawer as seen in the above screenshot. The Navigation Drawer displays navigation options in a drawer which slides in from the left edge.

To expand the drawer the user can either touch the app icon or swipe from the left edge. The navigation drawer overlays the content but not the action bar.


## Installation

* Grab the latest package from the [dist](dist) folder
* Install it following [this guide](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module)

## Usage

Here's an example of how to use the module. Please note the links for Demo App and API Docs below.

```javascript
// Load module
var TiDrawerLayout = require('com.tripvi.drawerlayout');

// define menu and main content view
var menuTable = Ti.UI.createTableView();
var contentView = Ti.UI.createView();

// create the Drawer
var drawer = TiDrawerLayout.createDrawer({
    leftView: menuTable,
    centerView: contentView,
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
        actionBar.displayHomeAsUp = true;
        
        // open and close with the app icon
        actionBar.onHomeIconItemSelected = function() {
            drawer.toggleLeftWindow();
        };
    }
});

// open the window
win.open();
```

#### [API Documentation](documentation/index.md)
#### [Demo App](https://github.com/manumaticx/Ti.DrawerLayout-Demo-Alloy-App)

## Known Issues

* ~~MapView is not working with centerView~~
* TabGroup is not working with Drawer
* ActionBar SearchView seems to make problems too

If you are facing other issues please add them to the issues. If you can solve one of them, please send a pull request.

## License

_[MIT](LICENSE), Copyright 2013 Tripvi.Inc_
