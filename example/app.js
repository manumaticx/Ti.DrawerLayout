// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var TiDrawerLayout = require('com.tripvi.drawerlayout');

var win = Ti.UI.createWindow({
	backgroundColor: 'white',
});

var menuTable = Ti.UI.createTableView({
	data: [
		{ title: "menu A" },
		{ title: "menu B" },
		{ title: "menu C" },
		{ title: "menu D" },
		{ title: "menu E" },
		{ title: "menu F" },
		{ title: "menu A" },
		{ title: "menu B" },
		{ title: "menu C" },
		{ title: "menu D" },
		{ title: "menu E" },
		{ title: "menu F" },
		{ title: "menu A" },
		{ title: "menu B" },
		{ title: "menu C" },
		{ title: "menu D" },
		{ title: "menu E" },
		{ title: "menu F" },
		{ title: "menu A" },
		{ title: "menu B" },
		{ title: "menu C" },
		{ title: "menu D" },
		{ title: "menu E" },
		{ title: "menu F" },
		{ title: "menu A" },
		{ title: "menu B" },
		{ title: "menu C" },
		{ title: "menu D" },
		{ title: "menu E" },
		{ title: "menu F" },
		{ title: "menu A" },
		{ title: "menu B" },
		{ title: "menu C" },
		{ title: "menu D" },
		{ title: "menu E" },
		{ title: "menu F" },
	],
	backgroundColor: "#ddd",
});
menuTable.addEventListener("click", function(ev) {
	var cv = Ti.UI.createView({
		backgroundColor: 'orange',
	});
	drawer.centerView = cv;
	win.title = "ROW " + ev.index;
	drawer.closeLeftWindow();
});

var contentView = Ti.UI.createScrollView({
	backgroundColor: "#fff",
	layout: "vertical",
});

var title = Ti.UI.createLabel({
	top: "25dp",
	text: "Main Content View",
	font: {
		fontSize: 24,
	},
	color: "#000",
});
contentView.add(title);

var changeDrawerWidthButton = Ti.UI.createButton({
	top: "25dp",
	title: "drawer width: 120",
});
changeDrawerWidthButton.addEventListener("click", function(e) {
	drawer.leftDrawerWidth = "120dp";
});
contentView.add(changeDrawerWidthButton);

var openNewWindowButton = Ti.UI.createButton({
	top: "75dp",
	title: "open new window",
});
openNewWindowButton.addEventListener("click", function(e) {
	var w = Ti.UI.createWindow({
		backgroundColor: 'orange',
		title: "COOL"
	});
	w.addEventListener('open', function() {
		w.activity.actionBar.logo = "";
		w.activity.actionBar.displayHomeAsUp = true;
		w.activity.actionBar.subtitle = "hello actionBar";
	});
	w.open();
});
contentView.add(openNewWindowButton);

var enableRightDrawerButton = Ti.UI.createButton({
	top: "125dp",
	title: "enable right drawer",
});
enableRightDrawerButton.addEventListener("click", function(e) {
	var rv = Ti.UI.createView({
		backgroundColor: "#def"
	});
	
	var caption = Ti.UI.createLabel({
		text: "Filter",
		font: {
			fontSize: 24,
			fontWeight: 'bold',
		},
		color: "#000",
	});
	rv.add(caption);
	
	drawer.rightView = rv;
	drawer.rightDrawerWidth = "80dp";
});
contentView.add(enableRightDrawerButton);


var drawer = TiDrawerLayout.createDrawer({
	leftWindow: menuTable,
	centerWindow: contentView,
	leftDrawerWidth: "280dp",
	width: Ti.UI.FILL,
	height: Ti.UI.FILL,
    // drawerIndicatorEnabled: false
});
drawer.addEventListener('draweropen', function(e) {
	win.title = "open " + e.drawer;
});
drawer.addEventListener('drawerclose', function(e) {
	win.title = "close";
});
drawer.addEventListener('drawerslide', function(e) {
	win.title = "slide: " + e.offset.toFixed(2);
});

win.addEventListener("open", function() {
	win.activity.actionBar.onHomeIconItemSelected = function() {
		drawer.toggleLeftWindow();
	};
});

win.add(drawer);
win.open();
