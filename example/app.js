// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var TiDrawerLayout = require('com.tripvi.drawerlayout');

var win = Ti.UI.createWindow({
	backgroundColor: 'white',
	navBarHidden: true,
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

var changeViewButton = Ti.UI.createButton({
	top: "125dp",
	title: "replace content view",
});
changeViewButton.addEventListener("click", function(e) {
	var rv = Ti.UI.createLabel({
		top: "100dp",
		text: "Changed VIEW",
		font: {
			fontSize: 24,
			fontWeight: 'bold',
		},
		color: "#000",
	});
	drawer.centerView = rv;
});
contentView.add(changeViewButton);



var drawer = TiDrawerLayout.createDrawer({
	leftView: menuTable,
	centerView: contentView,
	leftDrawerWidth: "240dp",
	top: "50dp",
	width: Ti.UI.FILL,
	height: Ti.UI.FILL,
});
drawer.addEventListener('draweropen', function(e) {
	menuTitle.text = "open";
});
drawer.addEventListener('drawerclose', function(e) {
	menuTitle.text = "close";
});
drawer.addEventListener('drawerslide', function(e) {
	menuTitle.text = "slide: " + e.offset.toFixed(2);
});

var actionBar = Ti.UI.createView({
	top: 0,
	height: "50dp",
	backgroundColor: "#333",
});

var menuButton = Ti.UI.createButton({
	left: "15dp",
	title: "menu",
});
actionBar.add(menuButton);

menuButton.addEventListener("click", function(e) {
	drawer.toggleLeftWindow();
});

var menuTitle = Ti.UI.createLabel({
	color: "#fff",
	font: {
		fontSize: 20,
		fontWeight: "bold",
	},
	text: "Drawer Example",
	textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
});
actionBar.add(menuTitle);


win.addEventListener('open', function(e) {
	console.log("OPEN");
});
win.addEventListener('close', function(e) {
	console.log("CLOSE");
});


win.add(actionBar);
win.add(drawer);
win.open();
