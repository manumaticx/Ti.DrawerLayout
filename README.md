# Ti.DrawerLayout [![Titanium](http://www-static.appcelerator.com/badges/titanium-git-badge-sq.png)](http://www.appcelerator.com/titanium/)

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
if (OS_ANDROID) {

    // Load module
    var TiDrawerLayout = require('com.tripvi.drawerlayout');

    // define menu and main content view
    var menuTable = Alloy.createController('menu').getView();
    var contentView = Alloy.createController('main').getView();

    // create the Drawer
    var drawer = TiDrawerLayout.createDrawer({
            leftView: menuTable,
            centerView: contentView,
            leftDrawerWidth: "240dp",
            width: Ti.UI.FILL,
            height: Ti.UI.FILL
    });

    // add some listeners
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

    // add the drawer to your root window
    $.index.add(drawer);

    // open the window
    $.index.open();
}
```

#### [API Documentation](documentation/index.md)
#### [Demo App](https://github.com/manumaticx/Ti.DrawerLayout-Demo-Alloy-App)

## Known Issues

* MapView is not working with centerView
* TabGroup is not working with Drawer
* ActionBar SearchView seems to make problems too

If you are facing other issues please add them to the issues. If you can solve one of them, please send a pull request.

## License

_[MIT](LICENSE), Copyright 2013 Tripvi.Inc_
