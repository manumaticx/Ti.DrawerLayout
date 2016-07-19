# Ti.DrawerLayout [![gittio](http://img.shields.io/badge/gittio-1.4.2-00B4CC.svg)](http://gitt.io/component/com.tripvi.drawerlayout)
Native Android [Navigation Drawer](http://developer.android.com/design/patterns/navigation-drawer.html) for [Titanium](http://www.appcelerator.com/titanium/)

- [Overview](#overview)
- [Installation](#installation)
- [Usage](#usage)
- [Docs](documentation/index.md)
- [Demo](https://github.com/manumaticx/NavigationDrawer-Demo)
- [License](#license)


_This is a fork of [Tripvi/Ti.DrawerLayout](https://github.com/Tripvi/Ti.DrawerLayout)_

## Overview

<a href="https://www.google.com/design/spec/patterns/navigation-drawer.html"><img src="https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0Bzhp5Z4wHba3WUpVTktSZWk1YjQ/patterns_navdrawer_behavior_temporary2.png" width="280"></a>

This module adds support for using the [DrawerLayout](http://developer.android.com/reference/android/support/v4/widget/DrawerLayout.html) in Titanium Apps.

The Drawer Layout is a view that can be pulled from the edge of a window. This can answer various purposes. The most common use case is the Navigation Drawer as seen in the above screenshot. The Navigation Drawer displays navigation options in a drawer which slides in from the left edge.

To expand the drawer the user can either touch the app icon or swipe from the left edge. The navigation drawer overlays the content but not the action bar.

## Installation

* Grab the latest package from the [dist](android/dist) folder
* Install it following [this guide](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module)
* with [gittio](http://gitt.io/): `$ gittio install com.tripvi.drawerlayout`

## Usage

Here's an example of how to use the module.

> **Please note:** This module requires a Theme without ActionBar such as `Theme.AppCompat.Light.NoActionBar` since it adds a Toolbar to its own layout. If you do not want the Toolbar, just pass the `hideToolbar` property at creation-time.

```javascript
// Load module
var TiDrawerLayout = require('com.tripvi.drawerlayout');

// define left and center view
var leftView = Ti.UI.createView({backgroundColor:'gray'});
var centerView = Ti.UI.createView({backgroundColor:'white'});

// create the Drawer
var drawer = TiDrawerLayout.createDrawer({
    leftView: leftView,
    centerView: centerView
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

#### [API Documentation](documentation/index.md)
#### [Demo App](https://github.com/manumaticx/NavigationDrawer-Demo)

## License

MIT license, see [LICENSE](LICENSE)

Copyright (c) 2013 - 2014 by Tripvi Inc., 2015 - 2016 by Manuel Lehner

