# Ti.DrawerLayout Documentation

You can create a Drawer by calling `createDrawer` on the module. It must be added as a top-level view to a Ti.UI.Window. You may want to pass a `contentView` property at creation-time. In addition, you can also pass either a `leftView` or `rightView` property to add an actual drawer view to your layout. All of them (`contentView`, `leftView` and `rightView`) must be instances of Ti.UI.View.

Here's an example:

```javascript
var TiDrawerLayout = require('com.tripvi.drawerlayout');
var contentView = Ti.UI.createView();
var leftView = Ti.UI.createTableView({ backgroundColor: '#ccc' });

var drawer = TiDrawerLayout.createDrawer({
	contentView: contentView,
	leftView: leftView
});
```

And this is how you'd do it in Alloy:

```xml
<Alloy>
  <Window>
		<Drawer id="drawer" module="com.tripvi.drawerlayout" />
  </Window>
</Alloy>
```

```javascript
var menu = Alloy.createController('menu');
var main = Alloy.createController('main');

$.drawer.setLeftView( menu.getView() );
$.drawer.setContentView( main.getView() );

```

## Properties

* `leftView` _(Ti.UI.View)_ - sets the left drawer
* `rightView` _(Ti.UI.View)_ - sets the right drawer
* `centerView` _(Ti.UI.View)_ - sets the center View
* `isLeftDrawerOpen` _(Boolean)_ - wether the left drawer is currently in an open state or not
* `isLeftDrawerVisible` _(Boolean)_ - wether the left drawer is currently visible on-screen or not
* `isRightDrawerOpen` _(Boolean)_ - wether the right drawer is currently in an open state or not
* `isRightDrawerVisible` _(Boolean)_ - wether the right drawer is currently visible on-screen or not
* `leftDrawerWidth` _(Number/String)_ - sets the width of the left drawer
* `rightDrawerWidth` _(Number/String)_ - sets the width of the right drawer
* `drawerIndicatorEnabled` _(Boolean)_ - wether it should use the ActionBarDrawerToggle or not
* `drawerIndicatorImage` _(String)_ - path to a custom drawer indicator image
* `drawerLockMode` _(int)_ - sets the lock mode constant. TiDrawerLayout.LOCK_MODE_UNLOCKED (default), TiDrawerLayout.LOCK_MODE_LOCKED_CLOSED, TiDrawerLayout.LOCK_MODE_LOCKED_OPEN
* `drawerArrowIcon` _(Boolean)_ - set this to `true` to get that fancy animation
* `drawerArrowIconColor` _(String)_ - sets the color of the drawerIcon animation

## Methods

* `setLeftView()` - sets the value for the `leftView` property
* `setRightView()` - sets the value for the `rightView` property
* `setCenterView()` - sets the value for the `centerView` property
* `replaceCenterView(view, backstack)` - same as `setCenterView` but with second parameter
	* `view` _(Ti.UI.View)_ - the new centerView
	* `backstack` _(Boolean)_ - set this to `true` if you want to add this to the backstack
* `toggleLeftWindow()` - opens or closes the left drawer
* `openLeftWindow()` - opens the left drawer
* `closeLeftWindow()` - closes the left drawer
* `toggleRightWindow()` - opens or closes the right drawer
* `openRightWindow()` - opens the right drawer
* `closeRightWindow()` - closes the right drawer
* `getIsLeftDrawerOpen()` - returns the value of the `isLeftDrawerOpen` property
* `getIsLeftDrawerVisible()` - returns the value of the `isLeftDrawerVisible` property
* `getIsRightDrawerOpen()` - returns the value of the `isRightDrawerOpen` property
* `getIsRightDrawerVisible()` - returns the value of the `isRightDrawerVisible` property
* `setLeftDrawerWidth()` - sets the value for the `leftDrawerWidth` property
* `setRightDrawerWidth()` - sets the value for the `rightDrawerWidth` property
* `setDrawerIndicatorEnabled()` - sets the value for the `drawerIndicatorEnabled` property
* `setDrawerIndicatorImage()` - sets the value for the `drawerIndicatorImage` property
* `setDrawerLockMode()` - sets the value for the `drawerLockMode` property
* `setDrawerArrowIcon()` - sets the value for the `drawerArrowIcon` property
* `setDrawerArrowIconColor()` - sets the value for the `drawerArrowIconColor` property

## Events

* `change` - fires when the drawer motion state changes
	* `state` _(Number)_ - the new drawer motion state
	* `idle` _(Boolean)_ - indicates that any drawers are in an idle, settled state. No animation is in progress
	* `dragging` _(Boolean)_ - indicates that a drawer is currently being dragged by the user
	* `settling` _(Boolean)_ - indicates that a drawer is in the process of settling to a final position

* `drawerslide` - fires when a drawer's position changes
	* `offset` _(Number)_ - the new offset of this drawer within its range, from 0-1
	* `drawer` _(String)_ - left or right

* `draweropen` - fires when the drawer motion state changes
	* `drawer` _(String)_ - left or right

* `drawerclose` - fires when the drawer motion state changes
	* `drawer` _(String)_ - left or right
