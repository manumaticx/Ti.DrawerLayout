##
## Build an Appcelerator Android Module
## Then copy it to the default module directory
##
## (c) Napp ApS
## Mads MÃ¸ller
##


## HOW TO GUIDE
## 1. Copy this script into the root of your android module project
## 2. Open terminal and navigate to the root of your android module project
## 3. write: bash ./build-module-android.sh


## Compile the module
ant dist

## Path to the module manifest
FILENAME='./manifest'

## FIND MODULE ID
MODULE_ID=$(grep 'moduleid' $FILENAME -m 1)
MODULE_ID=${MODULE_ID#*: } # Remove everything up to a colon and space

## FIND MODULE VERSION
MODULE_VERSION=$(grep 'version' $FILENAME -m 1) ## only one match
MODULE_VERSION=${MODULE_VERSION#*: } # Remove everything up to a colon and space

## unzip compiled module - and override if existing
unzip -o ./dist/$MODULE_ID-android-$MODULE_VERSION.zip -d /Users/$USER/Library/Application\ Support/Titanium


## Optional: You could run a app now - using your new module
#PROJECT_PATH='/path/to/your/project/projectname'
#cd $PROJECT_PATH

#titanium clean
#titanium build -p android -T device --device-id <DEVICE_ID>
